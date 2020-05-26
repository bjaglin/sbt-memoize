package sbtmemoize

import sbt.Keys._
import sbt._
import sjsonnew.support.murmurhash.Hasher
import sjsonnew.{Builder, HashWriter}

object MemoizePlugin extends AutoPlugin {

  override def trigger = noTrigger

  object autoImport {
    val memoizeCacheKey =
      taskKey[Int]("Compute the cache key for the current state of the project")
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(Compile, Test).flatMap(inConfig(_)(memoizeConfigSettings))

  lazy val memoizeConfigSettings: Seq[Def.Setting[_]] =
    Seq(
      memoizeCacheKey := {
        // like FileInfo.hash but without the path (which is environment-specific)
        val hashWriter: HashWriter[Seq[File]] = new HashWriter[Seq[File]] {
          override def write[J](obj: Seq[File], builder: Builder[J]): Unit = {
            import sjsonnew.BasicJsonProtocol._
            builder.beginArray()
            obj.foreach { file =>
              builder.beginObject()
              builder.addField("hash", file.hash)
              builder.endObject()
            }
            builder.endArray()
          }
        }

        val srcs = sources.value
        val deps = dependencyClasspathAsJars.value.map(_.data)

        Hasher.hash(srcs ++ deps)(hashWriter).get
      }
    )

}
