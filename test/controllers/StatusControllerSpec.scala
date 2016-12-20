package controllers

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future

import scaldi.play._

import models._

@RunWith(classOf[JUnitRunner])
class StatusControllerSpec extends PlaySpecification with Results {

  "StatusController" should {

    s"return ${ProjectInfo.toJson} in the /system/status endpoint" in new WithApplicationLoader(new ScaldiApplicationLoader()) {
      val systemStatus = route(app, FakeRequest(GET, "/system/status")).get

      status(systemStatus) must be equalTo(OK)
      contentType(systemStatus) must beSome("application/json")
      contentAsString(systemStatus) must be equalTo (ProjectInfo.toJson)
    }

  }

}