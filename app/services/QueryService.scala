package services

import javax.inject.Inject

import controllers.dtos.{AirportDto, CountryDto, RunwayDto}
import domain.Country
import play.api.Logger
import repositories.{AirportRepository, CountryRepository, RunwayRepository}

import scala.concurrent.Await
import scala.concurrent.duration._

class QueryService @Inject()(ar: AirportRepository, cr: CountryRepository, rr: RunwayRepository) {

  def searchQuery(query: String): Seq[Country] = {
    val cbc = Await.result(cr.getCountryByCode(query), 1 second)
    if (cbc.isEmpty) {
      val cbn = Await.result(cr.getCountryByName(query), 1 second)
      cbn
    } else {
      Logger.debug("cbc: " + cbc)
      cbc
    }
  }

  def getCountryDataFromString(query: String, offset: Int, limit: Int): Seq[AirportDto] = {
    val airports = Await.result(ar.getAirportsPerCountryObj(query, offset, limit), 1 second)
    airports.map(a => new AirportDto(a, getRunwaysPerAirport(a.ident)))
  }

  def getRunwaysPerAirport(query: String): Seq[RunwayDto] = {
    val tmp = Await.result(rr.getRunwaysPerAirportObj(query), 1 second)
    tmp.map(__ => new RunwayDto(__))
  }

  def getTopTenAirportCountries(order: String, offset: Int, limit: Int): Seq[CountryDto] = {

    var countries: Seq[(String, String, Int)] = Seq()

    if (order.equals("top"))
      countries = Await.result(cr.getTopTenAirportCountries(offset, limit), 1 second)
    else
      countries = Await.result(cr.getBottomTenAirportCountries(offset, limit), 1 second)

    val topTen = Await.result(cr.getTopTenAirportCountries(offset, limit), 1 second).map {
      tt => {
        CountryDto(tt._1, tt._2, tt._3,
          Await.result(rr.getRunwayTypesPerCountry(tt._1), 1 second),
          Await.result(rr.getMostCommonRunwayIdents(tt._1, 10), 1 second))
      }
    }
    topTen
  }

}
