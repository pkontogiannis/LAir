package repositories

import domain.Airport

import scala.concurrent.Future

trait AirportRepository {

  def all(): Future[Seq[Airport]]

  def insert(airport: Airport): Future[Unit]

  def getAirportsPerCountry(query: String, offset: Int, limit: Int): Future[Seq[(String, String, String, String, String)]]

  def getAirportsPerCountryObj(query: String, offset: Int, limit: Int): Future[Seq[Airport]]

}
