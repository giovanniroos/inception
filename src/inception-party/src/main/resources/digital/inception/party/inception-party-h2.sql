-- -------------------------------------------------------------------------------------------------
-- CREATE SCHEMAS
-- -------------------------------------------------------------------------------------------------
CREATE SCHEMA party;


-- -------------------------------------------------------------------------------------------------
-- CREATE TABLES
-- -------------------------------------------------------------------------------------------------
CREATE TABLE party.parties (
  id      UUID         NOT NULL,
  type    INTEGER      NOT NULL,
  name    VARCHAR(100) NOT NULL,
  created TIMESTAMP    NOT NULL,
  updated TIMESTAMP,

  PRIMARY KEY (id)
);

COMMENT ON COLUMN party.parties.id IS 'The Universally Unique Identifier (UUID) uniquely identifying the party';

COMMENT ON COLUMN party.parties.type IS 'The code identifying the type of party';

COMMENT ON COLUMN party.parties.type IS 'The name of the party';

COMMENT ON COLUMN party.parties.created IS 'The date and time the party was created';

COMMENT ON COLUMN party.parties.updated IS 'The date and time the party was last updated';


CREATE TABLE party.persons (
  id               UUID          NOT NULL,
  preferred_name   VARCHAR(100),
  title            VARCHAR(10),
  given_name       VARCHAR(100),
  middle_names     VARCHAR(100),
  initials         VARCHAR(20),
  surname          VARCHAR(100),
  maiden_name      VARCHAR(100),
  gender           VARCHAR(10),
  race             VARCHAR(10),
  marital_status   VARCHAR(10),
  marriage_type    VARCHAR(10),
  date_of_birth    DATE,
  date_of_death    DATE,
  country_of_birth VARCHAR(10),

  PRIMARY KEY (id),
  CONSTRAINT persons_party_fk FOREIGN KEY (id) REFERENCES party.parties(id) ON DELETE CASCADE
);

CREATE INDEX persons_date_of_birth_ix ON party.persons(date_of_birth);

COMMENT ON COLUMN party.persons.id IS 'The Universally Unique Identifier (UUID) uniquely identifying the person';

COMMENT ON COLUMN party.persons.preferred_name IS 'The optional preferred name for the person';

COMMENT ON COLUMN party.persons.title IS 'The optional code identifying the title for the person';

COMMENT ON COLUMN party.persons.given_name IS 'The optional given name for the person';

COMMENT ON COLUMN party.persons.middle_names IS 'The optional middle names for the person';

COMMENT ON COLUMN party.persons.maiden_name IS 'The optional initials for the person';

COMMENT ON COLUMN party.persons.surname IS 'The optional surname for the person';

COMMENT ON COLUMN party.persons.maiden_name IS 'The optional maiden name for the person';

COMMENT ON COLUMN party.persons.gender IS 'The optional code identifying the gender for the person';

COMMENT ON COLUMN party.persons.race IS 'The optional code identifying the race for the person';

COMMENT ON COLUMN party.persons.marital_status IS 'The optional code identifying the marital status for the person';

COMMENT ON COLUMN party.persons.marriage_type IS 'The optional code identifying the marriage type for the person if the person is married';

COMMENT ON COLUMN party.persons.date_of_birth IS 'The optional date of birth for the person';

COMMENT ON COLUMN party.persons.date_of_death IS 'The optional date of death for the person';

COMMENT ON COLUMN party.persons.country_of_birth IS 'The optional code identifying the country of birth for the person';


CREATE TABLE party.identity_documents (
  person_id        UUID        NOT NULL,
  type             VARCHAR(10) NOT NULL,
  date_of_issue    DATE        NOT NULL,
  date_of_expiry   DATE,
  country_of_issue VARCHAR(10) NOT NULL,
  number           VARCHAR(50) NOT NULL,
  created          TIMESTAMP   NOT NULL,
  updated          TIMESTAMP,

  PRIMARY KEY (person_id, type, country_of_issue, date_of_issue),
  CONSTRAINT identity_documents_person_fk FOREIGN KEY (person_id) REFERENCES party.persons(id) ON DELETE CASCADE
);

CREATE INDEX identity_documents_person_id_ix ON party.identity_documents(person_id);

COMMENT ON COLUMN party.identity_documents.person_id IS 'The Universally Unique Identifier (UUID) uniquely identifying the person the identity document is associated with';

COMMENT ON COLUMN party.identity_documents.type IS 'The code identifying the type of identity document';

COMMENT ON COLUMN party.identity_documents.date_of_issue IS 'The date of issue for the identity document';

COMMENT ON COLUMN party.identity_documents.date_of_expiry IS 'The optional date of expiry for the identity document';

COMMENT ON COLUMN party.identity_documents.country_of_issue IS 'The code identifying the country of issue for the identity document';

COMMENT ON COLUMN party.identity_documents.number IS 'The number for the identity document';

COMMENT ON COLUMN party.identity_documents.created IS 'The date and time the identity document was created';

COMMENT ON COLUMN party.identity_documents.updated IS 'The date and time the identity document was last updated';


CREATE TABLE party.organizations (
  id UUID NOT NULL,

  PRIMARY KEY (id),
  CONSTRAINT organizations_party_fk FOREIGN KEY (id) REFERENCES party.parties(id) ON DELETE CASCADE
);

COMMENT ON COLUMN party.organizations.id IS 'The Universally Unique Identifier (UUID) uniquely identifying the organization';


CREATE TABLE party.contact_mechanisms (
  party_id UUID         NOT NULL,
  type     INTEGER      NOT NULL,
  purpose  INTEGER      NOT NULL,
  value    VARCHAR(200) NOT NULL,
  created  TIMESTAMP    NOT NULL,
  updated  TIMESTAMP,

  PRIMARY KEY (party_id, type, purpose),
  CONSTRAINT contact_mechanisms_party_fk FOREIGN KEY (party_id) REFERENCES party.parties(id) ON DELETE CASCADE
);

CREATE INDEX contact_mechanisms_party_id_ix ON party.contact_mechanisms(party_id);

COMMENT ON COLUMN party.contact_mechanisms.party_id IS 'The Universally Unique Identifier (UUID) uniquely identifying the party the contact mechanism is associated with';

COMMENT ON COLUMN party.contact_mechanisms.type IS 'The code identifying the contact mechanism type';

COMMENT ON COLUMN party.contact_mechanisms.purpose IS 'The code identifying the contact mechanism purpose';

COMMENT ON COLUMN party.contact_mechanisms.value IS 'The value for the contact mechanism';

COMMENT ON COLUMN party.contact_mechanisms.created IS 'The date and time the contact mechanism was created';

COMMENT ON COLUMN party.contact_mechanisms.updated IS 'The date and time the contact mechanism was last updated';


-- -------------------------------------------------------------------------------------------------
-- POPULATE TABLES
-- -------------------------------------------------------------------------------------------------
