package controllers

import javax.inject._

import play.api.Configuration
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import services.QueryService

import scala.concurrent.ExecutionContext


class ReportController @Inject()(cc: ControllerComponents,
                                 configuration: Configuration,
                                 qs: QueryService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Query Countries")
  }


  def getTopTenAirportCountries = Action { request =>
    val offset = Integer.parseInt(request.queryString.get("offset").flatMap(_.headOption).getOrElse("0"))
    val limit = Integer.parseInt(request.queryString.get("limit").flatMap(_.headOption).getOrElse("50"))
    val order = request.queryString.get("order").flatMap(_.headOption).getOrElse("desc")
    val results = qs.getTopTenAirportCountries(order, offset, limit)
    Ok(Json.toJson(results)).as("application/json")
  }

}
