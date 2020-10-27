package uk.gov.hmcts.prd.util

object Environment {

 val environment: String = System.getProperty("env")
 var env = "perftest"
  val idamURL = "https://idam-web-public.perftest.platform.hmcts.net"
 val IDAMUrl = "https://idam-api." + env + ".platform.hmcts.net"
 val S2SUrl = "http://rpe-service-auth-provider-" + env + ".service.core-compute-" + env + ".internal/testing-support"
 val BaseUrl = "http://rd-professional-api-" + env + ".service.core-compute-" + env + ".internal"
  val baseURL = "https://paybubble.perftest.platform.hmcts.net"
 val bulkScanURL="http://ccpay-bulkscanning-api-perftest.service.core-compute-perftest.internal"
 val paymentAPIURL="http://payment-api-perftest.service.core-compute-perftest.internal"
  val adminUserAO = ""
  val adminPasswordAO = ""

 val thinkTime = 10

  val minThinkTime = 5
  //10
  val maxThinkTime = 6
  //30

 val commonHeader = Map(
  "accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
  "accept-encoding" -> "gzip, deflate, br",
  "accept-language" -> "en-US,en;q=0.9",
  "sec-fetch-dest" -> "image",
  "sec-fetch-mode" -> "no-cors",
  "sec-fetch-site" -> "same-origin")
}
