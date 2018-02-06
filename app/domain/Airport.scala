package domain

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{JsPath, Reads, Writes}

case class Airport(tid: Option[Long], id: String, ident: String, airportType: String, name: String, isoCountry: String)

object Airport {

  implicit val airportRead: Reads[Airport] = (
    (JsPath \ "tid").readNullable[Long] and
      (JsPath \ "id").read[String] and
      (JsPath \ "ident").read[String] and
      (JsPath \ "airportType").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "isoCountry").read[String]
    ) (Airport.apply _)

  implicit val airportWrite: Writes[Airport] = (
    (JsPath \ "tid").write[Option[Long]] and
      (JsPath \ "id").write[String] and
      (JsPath \ "ident").write[String] and
      (JsPath \ "airportType").write[String] and
      (JsPath \ "name").write[String] and
      (JsPath \ "isoCountry").write[String]
    ) (unlift(Airport.unapply))
}
