package redis.api.geo

import akka.util.ByteString
import redis._
import redis.api.geo.DistUnits.Measurement
import redis.protocol.MultiBulk

object DistUnits{
  sealed trait Measurement{
    def value:String = {
      this match {
        case Meter => "m"
        case Kilometer => "km"
        case Mile => "mi"
        case Feet => "ft"
      }
    }
  }
  case object Meter extends Measurement
  case object Kilometer extends Measurement
  case object Mile extends Measurement
  case object Feet extends Measurement

}

object GeoOptions{
  sealed trait WithOption{
    def value:String = {
      this match {
        case WithDist => "WITHDIST"
        case WithCoord => "WITHCOORD"
        case WithHash => "WITHHASH"
      }
    }
  }
  case object WithDist extends WithOption
  case object WithCoord extends WithOption
  case object WithHash extends WithOption

}

case class GeoAdd[K](key: K, lat: Double, lng: Double, loc: String)(implicit redisKey: ByteStringSerializer[K]) extends RedisCommandIntegerBoolean {
  val isMasterOnly = false
  val encodedRequest: ByteString = encode("GEOADD", Seq(redisKey.serialize(key), ByteString(lng.toString), ByteString(lat.toString), ByteString(loc)))
}

case class GeoRadius[K](key: K, lat: Double, lng: Double, radius: Double, dim: String)(implicit redisKey: ByteStringSerializer[K]) extends RedisCommandMultiBulk[Seq[String]] {
  val isMasterOnly = false
  val encodedRequest: ByteString = encode("GEORADIUS", Seq(redisKey.serialize(key), ByteString(lng.toString), ByteString(lat.toString), ByteString(radius.toString), ByteString(dim)))

  def decodeReply(mb: MultiBulk) = MultiBulkConverter.toSeqString(mb)
}
