package repositories.impl

import javax.inject.Inject

import domain.Runway
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.RunwayRepository
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class RunwayRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with RunwayRepository {

  import profile.api._

  private val runways = TableQuery[RunwayTable]

  def all(): Future[Seq[Runway]] = db.run {
    runways.result
  }

  def insert(runway: Runway): Future[Unit] = db.run(runways += runway).map { _ => () }

  def getRunwaysPerAirport(query: String): Future[Vector[(String, String, Boolean, Boolean)]] = {
    db.run(sql"""SELECT r.leIdent, r.surface, r.lighted, r.closed
                 FROM runways r
                 WHERE r.airportId = $query
              """.as[(String, String, Boolean, Boolean)])
  }

  def getRunwaysPerAirportObj(query: String): Future[Seq[Runway]] = db.run {
    runways.filter(r => r.airportId === query).result
  }

  def getRunwayTypesPerCountry(code: String): Future[Vector[(String)]] = {
    db.run(sql"""SELECT DISTINCT (r.surface)
                 FROM runways r INNER JOIN airports a ON r.airportId = a.ident
                 WHERE r.surface IS NOT NULL AND r.surface != '' AND a.isoCountry = $code
              """.as[(String)])
  }

  def getMostCommonRunwayIdents(code: String, numberNeeded: Int): Future[Vector[(String, Int)]] = {
    db.run(sql"""SELECT r.leIdent, count(leIdent) cnt
                 FROM runways r INNER JOIN airports a ON r.airportId = a.ident
                 WHERE a.isoCountry = $code
                 GROUP BY leIdent
                 ORDER BY cnt DESC
                 LIMIT $numberNeeded
              """.as[(String, Int)])
  }

  private class RunwayTable(tag: Tag) extends Table[Runway](tag, "runways") {
    def * = (tid, id, airportId, surface, leIdent, heIdent,
      lighted, closed, lengthFt, widthFt) <> ((Runway.apply _).tupled, Runway.unapply)

    def tid = column[Option[Long]]("tid", O.PrimaryKey, O.AutoInc)

    def id = column[String]("id")

    def airportId = column[String]("airportId")

    def surface = column[String]("surface")

    def leIdent = column[Option[String]]("leIdent")

    def heIdent = column[Option[String]]("heIdent")

    def lighted = column[Boolean]("lighted")

    def closed = column[Boolean]("closed")

    def lengthFt = column[Option[String]]("lengthFt")

    def widthFt = column[Option[String]]("widthFt")
  }

}
