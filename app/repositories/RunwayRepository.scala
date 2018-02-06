package repositories

import domain.Runway

import scala.concurrent.Future

trait RunwayRepository {

  def all(): Future[Seq[Runway]]

  def insert(runway: Runway): Future[Unit]

  def getRunwaysPerAirport(query: String): Future[Vector[(String, String, Boolean, Boolean)]]

  def getRunwaysPerAirportObj(query: String): Future[Seq[Runway]]

  def getRunwayTypesPerCountry(code: String): Future[Vector[(String)]]

  def getMostCommonRunwayIdents(code: String, numberNeeded: Int): Future[Vector[(String, Int)]]

}
