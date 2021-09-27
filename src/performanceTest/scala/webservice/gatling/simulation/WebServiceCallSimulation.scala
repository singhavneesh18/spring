package webservice.gatling.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._


class WebServiceCallSimulation extends Simulation {

  val rampUpTimeSecs = 5
  val testTimeSecs = 20
  val noOfUsers = 100
  val minWaitMs = 1000 milliseconds
  val maxWaitMs = 3000 milliseconds

  val baseURL = "http://localhost:8080"
  val baseName = "webservice-call-greeting"
  val requestName = baseName + "-request"
  val scenarioName = baseName + "-scenario"
  val URI = "/api/getName"

  val httpConf: HttpProtocolBuilder = http.baseUrl(baseURL)

  val new_scn = scenario("NEW_SCN")
    .exec(
      http("new scneario exec")
        .get("/api/getName")
        .check(status.is(200))
    )

  setUp(
    new_scn.inject(
      nothingFor(4 seconds),
      atOnceUsers(noOfUsers),
      rampUsers(noOfUsers) during (5 seconds),
      constantUsersPerSec(noOfUsers) during (15 seconds),
      constantUsersPerSec(noOfUsers) during (15 seconds) randomized,
      rampUsersPerSec(noOfUsers) to 100 during (1 minutes),
      rampUsersPerSec(noOfUsers) to 100 during (1 minutes) randomized,
      heavisideUsers(1000) during (20 seconds)
    ),

    //For 200 TPS
    new_scn.inject(
      nothingFor(10 seconds),
      constantUsersPerSec(200) during (1 minutes)
    )
  ).protocols(httpConf)
}
