addSbtPlugin("com.eed3si9n"     % "sbt-assembly"        % "0.14.3")
addSbtPlugin("com.geirsson"     % "sbt-scalafmt"        % "0.3.0")
addSbtPlugin("com.lihaoyi"      % "scalatex-sbt-plugin" % "0.3.5")
addSbtPlugin("io.get-coursier"  % "sbt-coursier"        % "1.0.0-M13")
addSbtPlugin("org.brianmckenna" % "sbt-wartremover"     % "0.14")
addSbtPlugin("org.scoverage"    % "sbt-scoverage"       % "1.0.1")
addSbtPlugin("org.xerial.sbt"   % "sbt-pack"            % "0.8.0")
addSbtPlugin("com.geirsson" % "sbt-idea-plugin" % "0.4.2-RC3")

libraryDependencies += "org.scala-sbt" % "scripted-plugin" % sbtVersion.value
