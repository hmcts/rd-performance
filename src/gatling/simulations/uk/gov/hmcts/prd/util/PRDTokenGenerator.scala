package uk.gov.hmcts.prd.util

import com.typesafe.config.ConfigFactory
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.restassured.RestAssured
import io.restassured.config.EncoderConfig
import io.restassured.http.ContentType

package object PRDTokenGenerator {

  private val config = ConfigFactory.load()

  val TOKEN_LEASE_URL = config.getString("s2sUrl")
  val USERTOKEN_SidAM_URL = config.getString("idam_api_url")

  /**
    * Kapil Jain: Helper function to optionally apply a proxy if set in the config - ToDo
    */

  val RD_URL = config.getString("baseUrl")

  def generateS2SToken() : String = {

     val authenticator: GoogleAuthenticator = new GoogleAuthenticator()

    val password = authenticator.getTotpPassword(config.getString("perftest_service.pass"))

    val jsonPayload: String = """{"microservice":"""" + config.getString("perftest_service.name") + """","oneTimePassword":"""" + password + """"}"""

    val s2sRequest = RestAssured.given
                    .contentType("application/json")
                    .accept("application/json")
                    .proxy("proxyout.reform.hmcts.net", 8080)
                    .body(jsonPayload)
                    .post(TOKEN_LEASE_URL +"/lease")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response()

    val token = s2sRequest.asString()

    System.out.println(token)

    token

  }

  //=======================================

  def generateSIDAMUserTokenInternal() : String = {
    return generateSIDAMUserTokenInternal("kapilPRD.jain@hmcts.net")
  }

  //perftest- kapilPRDADMIN.jain@hmcts.net
  //kapilPRD.jain@hmcts.net

  def generateSIDAMUserTokenInternal(userName : String) : String = {

    val authCodeRequest = RestAssured.given().config(RestAssured.config()
      .encoderConfig(EncoderConfig.encoderConfig()
        .encodeContentTypeAs("x-www-form-urlencoded",
          ContentType.URLENC)))
      .contentType("application/x-www-form-urlencoded; charset=UTF-8")
      .proxy("proxyout.reform.hmcts.net", 8080)
      .formParam("username", userName)
      .formParam("password", "Password12")
      .formParam("client_id", "rd-professional-api")
      .formParam("client_secret", "cc5f2a6-9690-11e9-bc42-526af7764f64")
      .formParam("redirect_uri", RD_URL + "/oauth2redirect")
      .formParam("grant_type", "password")
       .formParam("scope", "openid profile roles create-user manage-user search-user")
      .request()


    val response = authCodeRequest.post(USERTOKEN_SidAM_URL + ":443/o/token")

    val statusCode = response.getStatusCode()

    val tokenStr = response.asString()

    val tokenIndexStart = tokenStr.indexOf(":")

    val tokenIndexEnd = tokenStr.indexOf(",")

    val token =  tokenStr.substring(tokenIndexStart+2,tokenIndexEnd -1 )

    System.out.println(token)

    "Bearer " + token
  }


  def generateSIDAMUserTokenExternal() : String = {
    return generateSIDAMUserTokenExternal("tpALzz3BalrmKBR0Fa@email.co.uk")
  }

  //kapilPRDExt.jain@hmcts.net

  def generateSIDAMUserTokenExternal(userName : String) : String = {


    val authCodeRequest = RestAssured.given().config(RestAssured.config()
      .encoderConfig(EncoderConfig.encoderConfig()
        .encodeContentTypeAs("x-www-form-urlencoded",
          ContentType.URLENC)))
      .contentType("application/x-www-form-urlencoded; charset=UTF-8")
      .proxy("proxyout.reform.hmcts.net", 8080)
      .formParam("username", userName)
      .formParam("password", "Password12")
      .formParam("client_id", "rd-professional-api")
      .formParam("client_secret", "cc5f2a6-9690-11e9-bc42-526af7764f64")
      .formParam("redirect_uri", RD_URL + "/oauth2redirect")
      .formParam("grant_type", "password")
      .formParam("scope", "openid profile roles create-user manage-user search-user")
      .request();


    val response = authCodeRequest.post(USERTOKEN_SidAM_URL + ":443/o/token")

    val statusCode = response.getStatusCode()

    val tokenStr = response.asString()

    val tokenIndexStart = tokenStr.indexOf(":")

    val tokenIndexEnd = tokenStr.indexOf(",")

    val token =  tokenStr.substring(tokenIndexStart+2,tokenIndexEnd -1 )

    System.out.println(token)

    "Bearer " + token
  }

}
