package services

import javax.inject.{Inject, Singleton}

import com.github.tototoshi.csv.CSVReader
import domain.{Airport, Country, Runway}
import play.api.Environment
import repositories.{AirportRepository, CountryRepository, RunwayRepository}

import scala.concurrent.Await
import scala.concurrent.duration._


@Singleton
class DbService @Inject()(cp: CountryRepository,
                          rp: RunwayRepository,
                          ap: AirportRepository,
                          environment: Environment) {


  val airports: List[Map[String, String]] = CSVReader.open(environment.getFile("conf/resources/airports.csv")).allWithHeaders()
  val countries: List[Map[String, String]] = CSVReader.open(environment.getFile("conf/resources/countries.csv")).allWithHeaders()
  val runways: List[Map[String, String]] = CSVReader.open(environment.getFile("conf/resources/runways.csv")).allWithHeaders()


  def dbInitialization(): Unit = {

    countries.foreach(c =>
      Await.result(cp.insert(Country(None, c("id"), c("code"), c("name"))), 1 second)
    )

    airports.foreach(a =>
      Await.result(ap.insert(Airport(None, a("id"), a("ident"), a("type"), a("name"), a("iso_country"))), 1 second)
    )

    runways.foreach(r =>
      Await.result(rp.insert(Runway(None, r("id"), r("airport_ident"),
        r("surface"), Some(r("le_ident")), Some(r("he_ident")),
        r("lighted").toInt != 0,
        r("closed").toInt != 0,
        Some(r("length_ft")),
        Some(r("width_ft"))
      )), 1 second)
    )
  }


}
