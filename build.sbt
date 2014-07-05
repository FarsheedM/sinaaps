name := "sinaaps"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.29",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  "com.cloudinary" % "cloudinary" % "1.0.8"
)     

play.Project.playJavaSettings
