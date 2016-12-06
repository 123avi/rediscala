package redis.commands

import redis._

import scala.concurrent.{Await, Future}

import akka.util.ByteString

import scala.util.Success
import scala.sys.process.Process
import redis.api._

class GeoSpec extends RedisStandaloneServer {
  import concurrent.duration._
  "Geo commands " should {
    //GEOADD Sicily 13.361389 38.115556 "Palermo" 15.087269 37.502669 "Catania"
//    (integer) 2
    "GEOADD" in {
      val res = Await.result(redis.geoadd("Sicily", 13.361389, 38.115556, "Palermo"), 2 second)
      res shouldEqual 1

    }
  }

}
