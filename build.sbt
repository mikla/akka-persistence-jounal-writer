import sbt._
import sbt.Keys._

name := "akka-persistence-journal-writer"

organization := "io.github.mikla"

scalaVersion := "2.13.1"

crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")

resolvers += Resolver.jcenterRepo
externalResolvers ++= Seq(
  "dnvriend" at "https://dl.bintray.com/dnvriend/maven"
)

libraryDependencies ++= {
  val akkaVersion = "2.5.29"
  val akkaPersistenceInMemoryVersion = "2.5.15.2"
  val commonIoVersion = "2.6"
  val leveldbVersion = "0.10"
  val leveldbJniVersion = "1.8"
  val scalaTestVersion = "3.1.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.github.dnvriend" %% "akka-persistence-inmemory" % akkaPersistenceInMemoryVersion % Test,
    "commons-io" % "commons-io" % commonIoVersion % Test,
    "org.iq80.leveldb" % "leveldb" % leveldbVersion % Test,
    "org.fusesource.leveldbjni" % "leveldbjni-all" % leveldbJniVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )
}

fork in Test := true

parallelExecution in Test := false

// enable scala code formatting //
import com.typesafe.sbt.SbtScalariform

import scalariform.formatter.preferences._

// Scalariform settings
SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentConstructorArguments, true)

// enable updating file headers //

import de.heikoseeberger.sbtheader._

headerLicense := Some(HeaderLicense.ALv2("2016", "Dennis Vriend"))

enablePlugins(AutomateHeaderPlugin)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/mikla/akka-persistence-jounal-writer"),
    "scm:git@github.com:mikla/akka-persistence-jounal-writer.git"
  )
)
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
publishMavenStyle := true
pomIncludeRepository := { _ => false }
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)