name := "sinaaps"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.29",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  "com.cloudinary" % "cloudinary" % "1.0.8",
  "com.google.apis" % "google-api-services-analytics" % "v3-rev86-1.18.0-rc",
  "com.google.http-client" % "google-http-client-jackson2" % "1.17.0-rc",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.17.0-rc",
  "com.google.api-client" % "google-api-client" % "1.18.0-rc",
  "com.google.http-client" % "google-http-client" % "1.17.0-rc",
  "com.typesafe" %% "play-plugins-mailer" % "2.1.0",
  "com.cloudinary" % "cloudinary-http42" % "1.2.1",
  "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13",
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13"
)     

play.Project.playJavaSettings

