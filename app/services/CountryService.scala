package services

import javax.inject.Inject

import domain.Country
import repositories.CountryRepository

import scala.concurrent.Await
import scala.concurrent.duration._

class CountryService @Inject()(cr: CountryRepository) {

  def getAllCountries: Seq[Country] = {
    val countries = Await.result(cr.all2(), 1 second)
    countries
  }

}
