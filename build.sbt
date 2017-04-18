organization := "com.nitendragautam"

name := "KafkaStreamingJob"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
mainClass in assembly := Some("com.nitendragautam.kafkastreamingjob.main.Boot")
assemblyJarName in assembly := "kafkastreamingjob.jar"
libraryDependencies ++= {
  val akkaV       = "2.4.11"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" % "akka-stream_2.11" % akkaV,
    "com.typesafe.akka" % "akka-stream-kafka_2.11" % "0.13",
    "com.lightbend.akka" %% "akka-stream-alpakka-file" % "0.6",
    "commons-io" % "commons-io" % "2.5",
    "org.slf4j" % "slf4j-log4j12" % "1.7.21",
    "log4j" % "log4j" % "1.2.17"
  )
}



