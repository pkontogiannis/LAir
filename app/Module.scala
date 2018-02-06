import javax.inject.{Inject, Singleton}

import com.google.inject.AbstractModule
import play.api.inject.ApplicationLifecycle
import play.api.{Configuration, Environment}
import repositories.impl.{AirportRepositoryImpl, CountryRepositoryImpl, RunwayRepositoryImpl}
import repositories.{AirportRepository, CountryRepository, RunwayRepository}
import services.DbService

class Module extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[SystemGlobal]).asEagerSingleton()
    bind(classOf[CountryRepository]).to(classOf[CountryRepositoryImpl]).asEagerSingleton()
    bind(classOf[RunwayRepository]).to(classOf[RunwayRepositoryImpl]).asEagerSingleton()
    bind(classOf[AirportRepository]).to(classOf[AirportRepositoryImpl]).asEagerSingleton()
  }
}

@Singleton
class SystemGlobal @Inject()(appLifecycle: ApplicationLifecycle,
                             configuration: Configuration,
                             dbService: DbService,
                             environment: Environment) {
  val url: Boolean = configuration.get[Boolean]("application.mode.db")
  //  if (url) {
  dbService.dbInitialization()
  //  }
}

