package controllers.dtos

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json._

case class CountryDto(countryName: String, countryCode: String, numberOfAirports: Int,
                      runwayTypes: Vector[String], runwayIdentifications: Vector[(String, Int)])

object CountryDto {

  implicit val countryDtoRead: Reads[CountryDto] = (
    (JsPath \ "countryName").read[String] and
      (JsPath \ "countryCode").read[String] and
      (JsPath \ "numberOfAirports").read[Int] and
      (JsPath \ "runwayTypes").read[Vector[String]] and
      (JsPath \ "runwayIdentifications").read[Vector[(String, Int)]]
    ) (CountryDto.apply _)

  implicit val countryDtoWrite: Writes[CountryDto] = (
    (JsPath \ "countryName").write[String] and
      (JsPath \ "countryCode").write[String] and
      (JsPath \ "numberOfAirports").write[Int] and
      (JsPath \ "runwayTypes").write[Vector[String]] and
      (JsPath \ "runwayIdentifications").write[Vector[(String, Int)]]
    ) (unlift(CountryDto.unapply))
}

