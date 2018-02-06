package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import services.QueryService

import scala.concurrent.ExecutionContext

class QueryController @Inject()(cc: ControllerComponents, qs: QueryService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action {
    Ok("Query Countries")
  }

  def searchQuery() = Action { request =>
    val query = request.queryString.get("q").flatMap(_.headOption).getOrElse("0")
    val offset = Integer.parseInt(request.queryString.get("offset").flatMap(_.headOption).getOrElse("0"))
    val limit = Integer.parseInt(request.queryString.get("limit").flatMap(_.headOption).getOrElse("50"))
    val results = qs.searchQuery(query)
    Ok(Json.toJson(results)).as("application/json")
  }

  def getCountryDataFromString(code: String) = Action { request =>
    val offset = Integer.parseInt(request.queryString.get("offset").flatMap(_.headOption).getOrElse("0"))
    val limit = Integer.parseInt(request.queryString.get("limit").flatMap(_.headOption).getOrElse("50"))
    val results = qs.getCountryDataFromString(code, offset, limit)
    Ok(Json.toJson(results)).as("application/json")
  }

}
