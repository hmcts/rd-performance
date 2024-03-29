package scenarios.external

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils._
import scala.concurrent.duration._
import scala.util.Random

object External_AddMultiplePBAs {

  val config: Config = ConfigFactory.load()
  private val rng: Random = new Random()
  private def newpaymentAccount1(): String = rng.alphanumeric.take(7).mkString
  private def newpaymentAccount2(): String = rng.alphanumeric.take(7).mkString
  private def newpaymentAccount3(): String = rng.alphanumeric.take(7).mkString

  val editPbasString = "{ \"paymentAccounts\": [ \"PBA#{PaymentAccount1}\",\"PBA#{PaymentAccount2}\" ]}"

  val createAccounts = 
  
    exec(_.setAll(
      ("PaymentAccount1",newpaymentAccount1()),
      ("PaymentAccount2",newpaymentAccount2())
      ))

  val AddMultiplePbas = 
  
    exec(http("RD25_External_AddMultiplePBAs")
      .post("/refdata/external/v1/organisations/pba")
      .header("Authorization", "Bearer #{accessToken}")
      .header("ServiceAuthorization", "Bearer #{s2sToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(editPbasString)))

    .pause(Environment.thinkTime)
}
