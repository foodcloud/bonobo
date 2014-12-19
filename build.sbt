name := "foodcloud-server"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.joda" % "joda-money" % "0.9.1",
  "joda-time" % "joda-time" % "2.2",
  "com.novus" %% "salat" % "1.9.6",
  "se.radley" %% "play-plugins-salat" % "1.4.0",
  "org.scalaj" % "scalaj-time_2.10.2" % "0.7"
)

play.Project.playScalaSettings
