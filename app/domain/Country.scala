package domain

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Country(tid: Option[Long], countryId: String, code: String, name: String)

object Country {

  implicit val countryRead: Reads[Country] = (
    (JsPath \ "tid").readNullable[Long] and
      (JsPath \ "countryId").read[String] and
      (JsPath \ "code").read[String] and
      (JsPath \ "name").read[String]
    ) (Country.apply _)

  implicit val countryWrite: Writes[Country] = (
    (JsPath \ "tid").write[Option[Long]] and
      (JsPath \ "countryId").write[String] and
      (JsPath \ "code").write[String] and
      (JsPath \ "name").write[String]
    ) (unlift(Country.unapply))
}
