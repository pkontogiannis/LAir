package controllers.dtos

import domain.Runway
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{JsPath, Reads, Writes}

case class RunwayDto(airportId: String, surface: String, leIdent: Option[String], heIdent: Option[String],
                     lighted: Boolean, closed: Boolean, lengthFt: Option[String], widthFt: Option[String]) {

  def this(runway: Runway) {
    this(runway.airportId, runway.surface, runway.leIdent, runway.heIdent, runway.lighted,
      runway.closed, runway.lengthFt, runway.widthFt)
  }

}

object RunwayDto {

  implicit val runwayDtoRead: Reads[RunwayDto] = (
    (JsPath \ "airportId").read[String] and
      (JsPath \ "surface").read[String] and
      (JsPath \ "leIdent").readNullable[String] and
      (JsPath \ "heIdent").readNullable[String] and
      (JsPath \ "lighted").read[Boolean] and
      (JsPath \ "closed").read[Boolean] and
      (JsPath \ "lengthFt").readNullable[String] and
      (JsPath \ "widthFt").readNullable[String]
    ) (RunwayDto.apply _)

  implicit val runwayDtoWrite: Writes[RunwayDto] = (
    (JsPath \ "airportId").write[String] and
      (JsPath \ "surface").write[String] and
      (JsPath \ "leIdent").writeNullable[String] and
      (JsPath \ "heIdent").writeNullable[String] and
      (JsPath \ "lighted").write[Boolean] and
      (JsPath \ "closed").write[Boolean] and
      (JsPath \ "lengthFt").write[Option[String]] and
      (JsPath \ "widthFt").write[Option[String]]
    ) (unlift(RunwayDto.unapply))
}
