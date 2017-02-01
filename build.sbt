name := "sinaaps"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaJpa,
  "com.typesafe" % "play-plugins-mailer_2.9.1" % "2.0.4",
  "mysql" % "mysql-connector-java" % "5.1.29",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  "com.cloudinary" % "cloudinary" % "1.0.8",
  "com.google.apis" % "google-api-services-analytics" % "v3-rev86-1.18.0-rc",
  "com.google.http-client" % "google-http-client-jackson2" % "1.17.0-rc",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.17.0-rc",
  "com.google.api-client" % "google-api-client" % "1.18.0-rc",
  "com.google.http-client" % "google-http-client" % "1.17.0-rc",
  "com.cloudinary" % "cloudinary-http42" % "1.2.1",
  "org.json" % "json" % "20150729",
  "com.typesafe" %% "webdriver" % "1.0.0",
  "com.typesafe" %% "jse" % "1.0.0",
  "com.typesafe" %% "npm" % "1.0.0",
  "org.webjars" % "bootstrap" % "3.3.6"
)


