
// ··· Project Info ···

organization := "$organization$"

name := "$name$"

version := "$version$"

scalaVersion := "$scala_version$"

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

val configsV                = "0.4.4"
val scalDIV                 = "0.5.15"
val playJsonExtensionsV     = "0.8.0"
val slf4JV                  = "1.7.22"
val logbackV                = "1.2.1"
val spec2V                  = "3.8.8"
val jUnitV                  = "4.12"

libraryDependencies ++= Seq(
  // --- Play --
  ws,
  cache,
  filters,
  specs2 % Test,
  // --- Utils ---
  "org.scaldi"                    %% "scaldi-play"                        % scalDIV,
  "com.github.kxbmap"             %% "configs"                            % configsV,
  "ai.x"                          %% "play-json-extensions"               % playJsonExtensionsV,
  // --- Logger ---
  "org.slf4j"                     %  "slf4j-api"                          % slf4JV,
  "ch.qos.logback"                %  "logback-classic"                    % logbackV          %  "test",
  // --- Testing ---
  "org.specs2"                    %% "specs2-mock"                        % spec2V            %  "test",
  "org.specs2"                    %% "specs2-junit"                       % spec2V            %  "test",
  "junit"                         %  "junit"                              % jUnitV            %  "test"
)

// ··· Play Configuration ···

routesGenerator := InjectedRoutesGenerator
routesImport += "controllers._"
