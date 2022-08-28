ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "bombastore"
  )

val Http4sVersion = "1.0.0-M21"
val CirceVersion = "0.14.0-M5"
libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"      %% "http4s-circe"        % Http4sVersion,
  "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
  "io.circe"        %% "circe-generic"       % CirceVersion,
  "io.circe"        %% "circe-generic"       % "0.9.1",
  "ch.qos.logback"  % "logback-classic"      % "1.2.10"
)

lazy val akkaHttpVersion = "10.2.8"
lazy val akkaVersion     = "2.6.9"
lazy val circeVersion    = "0.14.1"
lazy val slickVersion    = "3.3.3"
lazy val postgresVersion = "9.4-1206-jdbc42"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % postgresVersion,
  "org.typelevel" %% "cats-effect" % "3.3.14"//org.postgresql.ds.PGSimpleDataSource dependency
)

libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-http"                  % akkaHttpVersion,
//  "com.typesafe.akka" %% "akka-actor-typed"           % akkaVersion,
//  "com.typesafe.akka" %% "akka-stream"                % akkaVersion,
//  "com.typesafe.akka" %% "akka-persistence-typed"     % akkaVersion,
//  "com.datastax.oss"  %  "java-driver-core"           % "4.13.0",
//  "com.typesafe.akka" %% "akka-persistence-cassandra" % "1.0.5",
//  "io.circe"          %% "circe-core"                 % circeVersion,
  "io.circe"          %% "circe-generic"              % circeVersion
 // "io.circe"          %% "circe-parser"               % circeVersion,
//  "de.heikoseeberger" %% "akka-http-circe"            % "1.39.2",
//  "ch.qos.logback"    % "logback-classic"             % "1.2.10",

  // optional, if you want to add tests
//  "com.typesafe.akka" %% "akka-http-testkit"          % akkaHttpVersion % Test,
//  "com.typesafe.akka" %% "akka-actor-testkit-typed"   % akkaVersion     % Test,
//  "org.scalatest"     %% "scalatest"                  % "3.2.9"         % Test
)


val Http4sVersion1 = "1.0.0-M21"
val CirceVersion1 = "0.14.0-M5"
libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-blaze-server" % Http4sVersion1,
  "org.http4s"      %% "http4s-circe"        % Http4sVersion1,
  "org.http4s"      %% "http4s-dsl"          % Http4sVersion1,
  "io.circe"        %% "circe-generic"       % CirceVersion1
)

