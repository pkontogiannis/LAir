package repositories

import domain.Country

import scala.concurrent.Future

trait CountryRepository {

  def all(): Future[Seq[Country]]

  def all2(): Future[Seq[Country]]

  def insert(country: Country): Future[Unit]

  def getCountryByCode(code: String): Future[Seq[Country]]

  def getCountryByName(name: String): Future[Seq[Country]]

  def getCountryDataForString(string: String): Future[Seq[Country]]

  def getBottomTenAirportCountries(offset: Int, limit: Int): Future[Seq[(String, String, Int)]]

  def getTopTenAirportCountries(offset: Int, limit: Int): Future[Seq[(String, String, Int)]]

}
