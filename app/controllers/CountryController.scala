package controllers

import javax.inject._

import play.api.Configuration
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import services.CountryService

import scala.concurrent.ExecutionContext

class CountryController @Inject()(cc: ControllerComponents,
                                  configuration: Configuration,
                                  cs: CountryService
                                 )(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Country Controller")
  }

  def getAllCountries = Action { _ =>
    val results = cs.getAllCountries
    Ok(Json.toJson(results)).as("application/json")
  }

}
