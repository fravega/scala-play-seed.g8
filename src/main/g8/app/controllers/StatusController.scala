package controllers

import scaldi.{ Injector, Injectable }
import play.api._
import play.api.mvc._

import models.ProjectInfo

/**
 * This controllers return the healthcheck of the application.
 * The content of this is the name, organization and scala version of the project.
 *
 * @param inj ScalDI injector.
 *
 * @author fviale
 */
class StatusController(implicit inj: Injector) extends Controller with Injectable {

  /**
   * @return Project information in json format
   */
  def status: Action[AnyContent] = Action {
    Ok(ProjectInfo.toJson).as("application/json")
  }

}
