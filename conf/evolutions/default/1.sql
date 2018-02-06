# LunaAir SCHEMA

# --- !Ups

CREATE TABLE countries (
  tid       INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  countryId VARCHAR(255) NOT NULL, --UNIQUE,
  code      VARCHAR(255) NOT NULL,
  name      VARCHAR(255) NOT NULL
);

CREATE TABLE airports (
  tid         INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  id          VARCHAR(255) NOT NULL, --UNIQUE,
  ident       VARCHAR(255) NOT NULL,
  airportType VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  isoCountry  VARCHAR(255) NOT NULL
);

CREATE TABLE runways (
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

CREATE INDEX countries_idx
  ON countries (code);
CREATE INDEX airports_idx
  ON airports (ident);
CREATE INDEX airports_country_idx
  ON airports (isoCountry);
CREATE INDEX runways_idx
  ON runways (airportId);

# --- !Downs

DROP TABLE countries;
DROP TABLE airports;
DROP TABLE runways;