package repositories.impl

import javax.inject.Inject

import domain.Country
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.CountryRepository
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class CountryRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with CountryRepository {

  import profile.api._

  private val countries = TableQuery[CountryTable]

  def all(): Future[Seq[Country]] = db.run {
    countries.result
  }

  def all2(): Future[Seq[Country]] = db.run {
    countries.filter(c => {
      c.code.toLowerCase === "ad"
    }).result
  }

  def insert(country: Country): Future[Unit] = {
    db.run(countries += country).map { _ => () }
  }

  def getCountryByCode(code: String): Future[Seq[Country]] = db.run(
    countries.filter(
      c => {
        c.code.toLowerCase === code.toLowerCase
      }).result
  )

  def getCountryByName(name: String): Future[Seq[Country]] = db.run(
    countries.filter(c => {
      c.name.toLowerCase like '%' + name.toLowerCase + '%'
    }).result
  )

  def getCountryDataForString(query: String): Future[Seq[Country]] = db.run(
    countries.filter {
      c =>
        (c.code.toLowerCase like query.toLowerCase) ||
          (c.name.toLowerCase like query.toLowerCase)
    }.result
  )

  def getBottomTenAirportCountries(offset: Int, limit: Int): Future[Seq[(String, String, Int)]] = {
    db.run(sql"""SELECT c.code, c.name, count(a.ident) cnt
                 FROM countries c INNER JOIN airports a ON c.code = a.isoCountry
                 GROUP BY c.code
                 ORDER BY cnt ASC
                 LIMIT $limit
                 OFFSET $offset""".as[(String, String, Int)])
  }

  def getTopTenAirportCountries(offset: Int, limit: Int): Future[Seq[(String, String, Int)]] = {
    db.run(sql"""SELECT c.code, c.name, count(a.ident) cnt
                 FROM countries c INNER JOIN airports a ON c.code = a.isoCountry
                 GROUP BY c.code
                 ORDER BY cnt DESC
                 LIMIT $limit
                 OFFSET $offset""".as[(String, String, Int)])
  }

  class CountryTable(tag: Tag) extends Table[Country](tag, "countries") {
    def * = (tid, countryId, code, name) <> ((Country.apply _).tupled, Country.unapply)

    def tid = column[Option[Long]]("tid", O.PrimaryKey, O.AutoInc)

    def countryId = column[String]("countryId")

    def code = column[String]("code")

    def name = column[String]("name")
  }

}
