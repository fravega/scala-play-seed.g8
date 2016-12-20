
// ··· Project Info ···
 
name := ""

organization := ""

scalaVersion := "2.11.8"

// ··· Project Options ···

lazy val root = (project in file(".")).
                enablePlugins(PlayScala, BuildInfoPlugin).
                settings(
                  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion),
                  buildInfoObject := "ProjectInfo",
                  buildInfoPackage := "models",
                  buildInfoOptions += BuildInfoOption.ToJson
                )

scalacOptions ++= Seq(
    "-encoding",
    "utf8",
    "-feature",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-unchecked",
    "-deprecation"
)

scalacOptions in Test ++= Seq("-Yrangepos")

scalastyleConfigUrl := Some(url("https://raw.githubusercontent.com/fravega/coding-styles/master/scalastyle-config.xml"))

// ··· Project Repositories ···

resolvers ++= Seq("OSS" at "http://oss.sonatype.org/content/repositories/releases")

// ··· Project Dependancies ···

val vConfigs          = "0.4.4"
val vScalDI           = "0.5.15"
val vSlf4J            = "1.7.22"
val vLogback          = "1.1.8"
val vSpec2            = "3.8.6"
val vJUnit            = "4.12"

libraryDependencies ++= Seq(
  // --- Play --
  ws,
  cache,
  specs2 % Test,
  // --- Utils ---
  "org.scaldi"                    %% "scaldi-play"                        % vScalDI,
  "com.github.kxbmap"             %% "configs"                            % vConfigs,  
  // --- Logger ---
  "org.slf4j"                     %  "slf4j-api"                          % vSlf4J,
  "ch.qos.logback"                %  "logback-classic"                    % vLogback          %  "test",
  // --- Testing ---
  "org.specs2"                    %% "specs2-mock"                        % vSpec2            %  "test",
  "org.specs2"                    %% "specs2-junit"                       % vSpec2            %  "test",
  "junit"                         %  "junit"                              % vJUnit            %  "test"
)
