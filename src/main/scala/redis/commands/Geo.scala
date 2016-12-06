package redis.commands

import redis.Request
import redis.api.geo.{GeoAdd, GeoRadius}

import scala.concurrent.Future

trait Geo extends Request {

  def geoadd[K](key: String, lat: Double, lng: Double, loc: String): Future[Boolean] =
    send(GeoAdd(key, lat, lng, loc))

  def georadius[K](key: String, lat: Double, lng: Double, radius: Double, dim: String): Future[Seq[String]] =
    send(GeoRadius(key, lat, lng, radius, dim))

}
