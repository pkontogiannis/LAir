package controllers.dtos

import domain.Airport
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{JsPath, Reads, Writes}

case class AirportDto(id: String, ident: String, airportType: String,
                      name: String, isoCountry: String, runways: Seq[RunwayDto]) {

  def this(airport: Airport, runways: Seq[RunwayDto]) {
    this(airport.id, airport.ident, airport.airportType, airport.name, airport.isoCountry,
      runways)
  }

}

object AirportDto {

  implicit val airportDtoRead: Reads[AirportDto] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "ident").read[String] and
      (JsPath \ "airportType").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "isoCountry").read[String] and
      (JsPath \ "runways").read[Seq[RunwayDto]]
    ) (AirportDto.apply _)

  implicit val airportDtoWrite: Writes[AirportDto] = (
    (JsPath \ "id").write[String] and
      (JsPath \ "ident").write[String] and
      (JsPath \ "airportType").write[String] and
      (JsPath \ "name").write[String] and
      (JsPath \ "isoCountry").write[String] and
      (JsPath \ "runways").write[Seq[RunwayDto]]
    ) (unlift(AirportDto.unapply))

}