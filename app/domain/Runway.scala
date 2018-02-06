package domain


import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Runway(tid: Option[Long], id: String, airportId: String, surface: String, leIdent: Option[String],
                  heIdent: Option[String], lighted: Boolean, closed: Boolean,
                  lengthFt: Option[String], widthFt: Option[String])

object Runway {

  implicit val runwayRead: Reads[Runway] = (
    (JsPath \ "tid").readNullable[Long] and
      (JsPath \ "id").read[String] and
      (JsPath \ "airportId").read[String] and
      (JsPath \ "surface").read[String] and
      (JsPath \ "leIdent").readNullable[String] and
      (JsPath \ "heIdent").readNullable[String] and
      (JsPath \ "lighted").read[Boolean] and
      (JsPath \ "closed").read[Boolean] and
      (JsPath \ "lengthFt").readNullable[String] and
      (JsPath \ "widthFt").readNullable[String]
    ) (Runway.apply _)

  implicit val runwayWrite: Writes[Runway] = (
    (JsPath \ "tid").write[Option[Long]] and
      (JsPath \ "id").write[String] and
      (JsPath \ "airportId").write[String] and
      (JsPath \ "surface").write[String] and
      (JsPath \ "leIdent").write[Option[String]] and
      (JsPath \ "heIdent").write[Option[String]] and
      (JsPath \ "lighted").write[Boolean] and
      (JsPath \ "closed").write[Boolean] and
      (JsPath \ "lengthFt").write[Option[String]] and
      (JsPath \ "widthFt").write[Option[String]]
    ) (unlift(Runway.unapply))
}
