name := "sinaaps"

version := "1.0-SNAPSHOT"


libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaJpa,
  //"com.typesafe.play" % "play-cache_2.10" % "2.4.0-M2",
  //"com.typesafe.play" % "play-java-jpa_2.10" % "2.4.0-M1",
  "com.typesafe" % "play-plugins-mailer_2.9.1" % "2.0.4",
  //"com.typesafe.play" % "play-java-ebean_2.10" % "2.3.4",
  //"com.typesafe.play" % "play-java-jdbc_2.10" % "2.4.1",
  "mysql" % "mysql-connector-java" % "5.1.29",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  "com.cloudinary" % "cloudinary" % "1.0.8",
  "com.google.apis" % "google-api-services-analytics" % "v3-rev86-1.18.0-rc",
  "com.google.http-client" % "google-http-client-jackson2" % "1.17.0-rc",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.17.0-rc",
  "com.google.api-client" % "google-api-client" % "1.18.0-rc",
  "com.google.http-client" % "google-http-client" % "1.17.0-rc",
  "com.cloudinary" % "cloudinary-http42" % "1.2.1",
  "org.json" % "json" % "20150729"
)     

play.Project.playJavaSettings

