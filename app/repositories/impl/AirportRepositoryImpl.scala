package repositories.impl

import javax.inject.Inject

import domain.Airport
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.AirportRepository
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class AirportRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with AirportRepository {

  import profile.api._

  private val airports = TableQuery[AirportTable]

  def all(): Future[Seq[Airport]] = db.run {
    airports.result
  }

  def insert(airport: Airport): Future[Unit] = db.run(airports += airport).map { _ => () }

  def getAirportsPerCountry(query: String, offset: Int, limit: Int): Future[Vector[(String, String, String, String, String)]] = {
    db.run(sql"""SELECT a.id, a.ident, a.airportType, a.name, a.isoCountry
                 FROM airports a
                 WHERE a.isoCountry = $query
                 LIMIT $limit
                 OFFSET $offset""".as[(String, String, String, String, String)])
  }

  def getAirportsPerCountryObj(query: String, offset: Int, limit: Int): Future[Seq[Airport]] = db.run(
    airports.filter(a => {
      a.isoCountry.toLowerCase === query.toLowerCase
    }).drop(offset).take(limit).result
  )

  /**
    * Table Definition
    **/
  private class AirportTable(tag: Tag) extends Table[Airport](tag, "airports") {
    def * = (tid, id, ident, airportType, name, isoCountry) <> ((Airport.apply _).tupled, Airport.unapply)

    def tid = column[Option[Long]]("tid", O.PrimaryKey, O.AutoInc)

    def id = column[String]("id")

    def ident = column[String]("ident")

    def airportType = column[String]("airportType")

    def name = column[String]("name")

    def isoCountry = column[String]("isoCountry")
  }

}
