# LunaAir SCHEMA

# --- !Ups

DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS airports;
DROP TABLE IF EXISTS runways;

DROP INDEX IF EXISTS countries_idx;
DROP INDEX IF EXISTS airports_idx;
DROP INDEX IF EXISTS airports_country_idx;
DROP INDEX IF EXISTS runways_idx;

CREATE TABLE IF NOT EXISTS countries (
  tid       INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  countryId VARCHAR(255) NOT NULL, --UNIQUE,
  code      VARCHAR(255) NOT NULL,
  name      VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS airports (
  tid         INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  id          VARCHAR(255) NOT NULL, --UNIQUE,
  ident       VARCHAR(255) NOT NULL,
  airportType VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  isoCountry  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS runways (
  tid       INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  id        VARCHAR(255) NOT NULL, --UNIQUE,
  airportId VARCHAR(255) NOT NULL,
  surface   VARCHAR(255) NOT NULL,
  leIdent   VARCHAR(255),
  heIdent   VARCHAR(255),
  lighted   BOOLEAN      NOT NULL,
  closed    BOOLEAN      NOT NULL,
  lengthFt  VARCHAR(255),
  widthFt   VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS countries_idx
  ON countries (code);
CREATE INDEX IF NOT EXISTS airports_idx
  ON airports (ident);
CREATE INDEX IF NOT EXISTS airports_country_idx
  ON airports (isoCountry);
CREATE INDEX IF NOT EXISTS runways_idx
  ON runways (airportId);

# --- !Downs

DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS airports;
DROP TABLE IF EXISTS runways;