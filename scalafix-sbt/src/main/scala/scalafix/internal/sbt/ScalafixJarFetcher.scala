package scalafix.internal.sbt

import coursier._

import sbt.ModuleID

import java.io.File
import java.io.OutputStreamWriter

private[scalafix] object ScalafixJarFetcher {
  private val SonatypeSnapshots: MavenRepository =
    MavenRepository("https://oss.sonatype.org/content/repositories/snapshots")
  private val MavenCentral: MavenRepository =
    MavenRepository("https://repo1.maven.org/maven2")

  def fetchJars(dependencies: Set[Dependency], isSnapshot: Boolean): List[File] =
    this.synchronized {
      val start = Resolution(dependencies)
      val repositories: List[Repository] = List(
        Some(Cache.ivy2Local),
        Some(MavenCentral),
        if (isSnapshot) Some(SonatypeSnapshots)
        else None
      ).flatten

      val logger = new TermDisplay(new OutputStreamWriter(System.err), true)
      logger.init()
      val fetch = Fetch.from(repositories, Cache.fetch(logger = Some(logger)))
      val resolution = start.process.run(fetch).unsafePerformSync
      val errors = resolution.metadataErrors
      if (errors.nonEmpty) {
        sys.error(errors.mkString("\n"))
      }
      val localArtifacts = scalaz.concurrent.Task
        .gatherUnordered(
          resolution.artifacts.map(Cache.file(_).run)
        )
        .unsafePerformSync
        .map(_.toEither)
      val failures = localArtifacts.collect { case Left(e) => e }
      if (failures.nonEmpty) {
        sys.error(failures.mkString("\n"))
      } else {
        val jars = localArtifacts.collect {
          case Right(file) if file.getName.endsWith(".jar") =>
            file
        }
        jars
      }
    }

  def fetchJars(org: String, artifact: String, version: String): List[File] =
    fetchJars(Set(Dependency(Module(org, artifact), version)), isSnapshot = version.endsWith("-SNAPSHOT"))

  def fetchJars(dependencies: Seq[ModuleID], scalafixScalaBinaryVersion: String): List[File] = {
    val converted =
      dependencies.map(dep =>
        Dependency(
          Module(
            dep.organization,
            dep.name + "_" + scalafixScalaBinaryVersion
          ),
          dep.revision
        )
      )

    fetchJars(converted.toSet, isSnapshot = false)
  }

}
