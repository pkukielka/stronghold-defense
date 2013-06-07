import sbt._

import Keys._
import org.scalasbt.androidplugin._
import org.scalasbt.androidplugin.AndroidKeys._

object Settings {
  lazy val common = Defaults.defaultSettings ++ Seq(
    version := "0.1",
    scalaVersion := "2.10.1",
    libraryDependencies += "com.assembla.scala-incubator" % "graph-core_2.10" % "1.6.1",
    updateLibsTask
  )

  lazy val desktop = Settings.common ++ Seq(
    fork in Compile := true
  )

  lazy val android = Settings.common ++
    AndroidProject.androidSettings ++
    AndroidMarketPublish.settings ++ Seq(
    platformName in Android := "android-17",
    keyalias in Android := "change-me",
    mainAssetsPath in Android := file("common/src/main/resources"),
    unmanagedBase <<= baseDirectory(_ / "src/main/libs"),
    proguardOption in Android <<= (baseDirectory) {
      (b) => scala.io.Source.fromFile(b / "src/main/proguard.cfg").mkString
    }
  )

  val updateLibs = TaskKey[Unit]("update-libs", "Updates local libraries")

  val updateLibsTask = updateLibs <<= streams map {
    (s: TaskStreams) =>
      import Process._
      import java.io._
      import java.net.URL
      import java.util.regex.Pattern

      // Scala
    {
      // Declare names.
      val baseUrl = "http://www.scala-lang.org/downloads/distrib/files"
      val zipName = "scala-2.10.1.zip"

      // Fetch the scala file.
      s.log.info("Pulling %s" format (zipName))
      s.log.warn("This may take a few minutes...")
      val zipFile = new java.io.File(zipName)
      val url = new URL("%s/%s" format(baseUrl, zipName))
      IO.download(url, zipFile)

      // Extract jars into their respective lib folders.
      s.log.info("Extracting scala-library.jar")
      val androidDest = file("android/src/main/libs")
      val androidFilter = new ExactFilter("scala-2.10.1/lib/scala-library.jar")
      IO.unzip(zipFile, androidDest, androidFilter)

      // Destroy the file.
      zipFile.delete
    }

      // libGDX
    {
      // Declare names
      val baseUrl = "http://libgdx.badlogicgames.com/nightlies"
      val zipName = "libgdx-nightly-latest.zip"

      // Fetch the libgdx file.
      s.log.info("Pulling %s" format (zipName))
      s.log.warn("This may take a few minutes...")
      val zipFile = new java.io.File(zipName)
      val url = new URL("%s/%s" format(baseUrl, zipName))
      IO.download(url, zipFile)

      // Extract jars into their respective lib folders.
      s.log.info("Extracting common libs")
      val commonDest = file("common/lib")
      val commonFilter = new ExactFilter("gdx.jar")
      IO.unzip(zipFile, commonDest, commonFilter)

      s.log.info("Extracting desktop libs")
      val desktopDest = file("desktop/lib")
      val desktopFilter = new ExactFilter("gdx-natives.jar") |
        new ExactFilter("gdx-backend-lwjgl.jar") |
        new ExactFilter("gdx-backend-lwjgl-natives.jar")
      IO.unzip(zipFile, desktopDest, desktopFilter)

      s.log.info("Extracting ios libs")
      val iosDest = file("ios/libs")
      val iosFilter = GlobFilter("ios/*")
      IO.unzip(zipFile, iosDest, iosFilter)

      s.log.info("Extracting android libs")
      val androidDest = file("android/src/main/libs")
      val androidFilter = new ExactFilter("gdx-backend-android.jar") |
        new ExactFilter("extensions/gdx-jnigen/gdx-jnigen.jar") |
        new ExactFilter("armeabi/libgdx.so") |
        new ExactFilter("armeabi/libandroidgl20.so") |
        new ExactFilter("armeabi-v7a/libgdx.so") |
        new ExactFilter("armeabi-v7a/libandroidgl20.so")
      IO.unzip(zipFile, androidDest, androidFilter)

      // Destroy the file.
      zipFile.delete
    }

      s.log.info("Update complete")
  }
}

object LibgdxBuild extends Build {
  val common = Project(
    "common",
    file("common"),
    settings = Settings.common
  )

  lazy val desktop = Project(
    "desktop",
    file("desktop"),
    settings = Settings.desktop
  ) dependsOn common

  lazy val android = Project(
    "android",
    file("android"),
    settings = Settings.android
  ) dependsOn common
}
