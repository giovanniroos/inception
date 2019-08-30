-- -------------------------------------------------------------------------------------------------
-- CREATE SCHEMAS
-- -------------------------------------------------------------------------------------------------
CREATE SCHEMA sample;

-- -------------------------------------------------------------------------------------------------
-- CREATE TABLES
-- -------------------------------------------------------------------------------------------------
CREATE TABLE sample.data (
  id              BIGINT        NOT NULL,
  name            VARCHAR(4000) NOT NULL,
  integer_value   INTEGER,
  string_value    VARCHAR(4000),
  date_value      DATE,
  timestamp_value TIMESTAMP,

  PRIMARY KEY (id)
);

COMMENT ON COLUMN sample.data.id IS 'The ID used to uniquely identify the data';

COMMENT ON COLUMN sample.data.name IS 'The name for the data';

COMMENT ON COLUMN sample.data.integer_value IS 'The integer value for the data';

COMMENT ON COLUMN sample.data.string_value IS 'The string value for the data';

COMMENT ON COLUMN sample.data.date_value IS 'The date value for the data';

COMMENT ON COLUMN sample.data.timestamp_value IS 'The timestamp value for the data';

-- -------------------------------------------------------------------------------------------------
-- POPULATE TABLES
-- -------------------------------------------------------------------------------------------------
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b64d', 'Sample', 1);

INSERT INTO security.user_directories (id, type, name, configuration)
  VALUES ('34ccdbc9-4a01-46f5-a284-ba13e095675c', 'InternalUserDirectory', 'Sample Internal User Directory', '<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE userDirectory SYSTEM "UserDirectoryConfiguration.dtd"><userDirectory><parameter><name>MaxPasswordAttempts</name><value>5</value></parameter><parameter><name>PasswordExpiryMonths</name><value>12</value></parameter><parameter><name>PasswordHistoryMonths</name><value>24</value></parameter><parameter><name>MaxFilteredUsers</name><value>100</value></parameter></userDirectory>');

INSERT INTO security.user_directories (id, type, name, configuration)
  VALUES ('595d13ac-22d6-4ce2-b898-3add4658a748', 'LDAPUserDirectory', 'Sample LDAP User Directory', '<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE userDirectory SYSTEM "UserDirectoryConfiguration.dtd"><userDirectory><parameter><name>Host</name><value>localhost</value></parameter><parameter><name>Port</name><value>389</value></parameter><parameter><name>UseSSL</name><value>false</value></parameter><parameter><name>BindDN</name><value>cn=root,o=sample</value></parameter><parameter><name>BindPassword</name><value>Password1</value></parameter><parameter><name>BaseDN</name><value>ou=sample,ou=applications,o=sample</value></parameter><parameter><name>UserBaseDN</name><value>ou=users,ou=sample,ou=applications,o=sample</value></parameter><parameter><name>GroupBaseDN</name><value>ou=groups,ou=sample,ou=applications,o=sample</value></parameter><parameter><name>UserObjectClass</name><value>inetOrgPerson</value></parameter><parameter><name>UserUsernameAttribute</name><value>uid</value></parameter><parameter><name>UserFirstNameAttribute</name><value>givenName</value></parameter><parameter><name>UserLastNameAttribute</name><value>sn</value></parameter><parameter><name>UserFullNameAttribute</name><value>cn</value></parameter><parameter><name>UserPhoneNumberAttribute</name><value>telephoneNumber</value></parameter><parameter><name>UserFaxNumberAttribute</name><value>facsimileTelephoneNumber</value></parameter><parameter><name>UserMobileNumberAttribute</name><value>mobile</value></parameter><parameter><name>UserEmailAttribute</name><value>mail</value></parameter><parameter><name>UserDescriptionAttribute</name><value>cn</value></parameter><parameter><name>GroupObjectClass</name><value>groupOfNames</value></parameter><parameter><name>GroupNameAttribute</name><value>cn</value></parameter><parameter><name>GroupMemberAttribute</name><value>member</value></parameter><parameter><name>GroupDescriptionAttribute</name><value>description</value></parameter><parameter><name>MaxFilteredUsers</name><value>100</value></parameter><parameter><name>MaxFilteredGroups</name><value>100</value></parameter></userDirectory>');

INSERT INTO security.user_directory_to_organization_map (user_directory_id, organization_id)
  VALUES ('34ccdbc9-4a01-46f5-a284-ba13e095675c', '204e5b8f-48e7-4354-bd15-753e6543b64d');
INSERT INTO SECURITY.USER_DIRECTORY_TO_ORGANIZATION_MAP (USER_DIRECTORY_ID, ORGANIZATION_ID)
  VALUES ('595d13ac-22d6-4ce2-b898-3add4658a748', '204e5b8f-48e7-4354-bd15-753e6543b64d');

INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('54166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', 'joe', 1, 'Joe', 'Bloggs', '', '', 'joe@sample.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('00166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', 'sally', 1, 'Sally', 'Smith', '', '', 'sally@sample.guru', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('01166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Bob.Smith@peoples.com'), 1, 'Bob', 'Smith', '', '', 'Bob.Smith@peoples.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('02166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Jamiya.Stuart@advantageepic.com'), 1, 'Jamiya', 'Stuart', '', '', 'Jamiya.Stuart@advantageepic.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('03166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Melany.Reed@bulkmailsweetpotato.com'), 1, 'Melany', 'Reed', '', '', 'Melany.Reed@bulkmailsweetpotato.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('04166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Brendan.Best@chromaticvalley.com'), 1, 'Brendan', 'Best', '', '', 'Brendan.Best@chromaticvalley.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('05166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Reuben.Ali@cove.com'), 1, 'Reuben', 'Ali', '', '', 'Reuben.Ali@cove.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('06166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Jairo.Hull@turbineluster.com'), 1, 'Jairo', 'Hull', '', '', 'Jairo.Hull@turbineluster.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('07166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Nickolas.Sampson@leafblower.com'), 1, 'Nickolas', 'Sampson', '', '', 'Nickolas.Sampson@leafblower.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('08166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Amiah.Sanford@ dating.com'), 1, 'Amiah', 'Sanford', '', '', 'Amiah.Sanford@ dating.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('09166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Janae.Reeves@jollyfracture.com'), 1, 'Janae', 'Reeves', '', '', 'Janae.Reeves@jollyfracture.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('10166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Sara.Meza@bestsellerprices.com'), 1, 'Sara', 'Meza', '', '', 'Sara.Meza@bestsellerprices.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('11166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Gauge.Barrera@perspectivedealer.com'), 1, 'Gauge', 'Barrera', '', '', 'Gauge.Barrera@perspectivedealer.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('12166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Nickolas.Stone@healing.com'), 1, 'Nickolas', 'Stone', '', '', 'Nickolas.Stone@healing.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('13166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Nayeli.Mcknight@retro.com'), 1, 'Nayeli', 'Mcknight', '', '', 'Nayeli.Mcknight@retro.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('14166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Cristofer.Singleton@glowing.com'), 1, 'Cristofer', 'Singleton', '', '', 'Cristofer.Singleton@glowing.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('15166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Gunnar.Manning@assets.com'), 1, 'Gunnar', 'Manning', '', '', 'Gunnar.Manning@assets.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
 VALUES ('16166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Adalyn.Doyle@tadpole.com'), 1, 'Adalyn', 'Doyle', '', '', 'Adalyn.Doyle@tadpole.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('17166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Oliver.Hoffman@beam.com'), 1, 'Oliver', 'Hoffman', '', '', 'Oliver.Hoffman@beam.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('18166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Ayana.Mccarty@cucumber.com'), 1, 'Ayana', 'Mccarty', '', '', 'Ayana.Mccarty@cucumber.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('19166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Nigel.Cohen@recordplayer.com'), 1, 'Nigel', 'Cohen', '', '', 'Nigel.Cohen@recordplayer.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('20166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Andrew.Wheeler@greatplainsfinances.com'), 1, 'Andrew', 'Wheeler', '', '', 'Andrew.Wheeler@greatplainsfinances.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('21166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Amelia.May@opera.com'), 1, 'Amelia', 'May', '', '', 'Amelia.May@opera.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('22166574-6564-468a-b845-8a5c127a4345', '34ccdbc9-4a01-46f5-a284-ba13e095675c', LOWER('Amir.Wells@refrigeratorsignal.com'), 1, 'Amir', 'Wells', '', '', 'Amir.Wells@refrigeratorsignal.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);

INSERT INTO security.groups (id, user_directory_id, groupname, description)
  VALUES ('956c5550-cd3d-42de-8660-7749e1b4df52', '34ccdbc9-4a01-46f5-a284-ba13e095675c', 'Organization Administrators', 'Organization Administrators');
INSERT INTO security.groups (id, user_directory_id, groupname, description)
  VALUES ('146c5550-cd3d-42de-8660-7749e1b4df52', '34ccdbc9-4a01-46f5-a284-ba13e095675c', 'Password Resetters', 'Password Resetters');

INSERT INTO security.user_to_group_map (user_id, group_id)
  VALUES ('54166574-6564-468a-b845-8a5c127a4345', '956c5550-cd3d-42de-8660-7749e1b4df52');
INSERT INTO security.user_to_group_map (user_id, group_id)
  VALUES ('00166574-6564-468a-b845-8a5c127a4345', '146c5550-cd3d-42de-8660-7749e1b4df52');

INSERT INTO security.role_to_group_map (role_code, group_id)
  VALUES ('OrganizationAdministrator', '956c5550-cd3d-42de-8660-7749e1b4df52');
INSERT INTO security.role_to_group_map (role_code, group_id)
  VALUES ('PasswordResetter', '146c5550-cd3d-42de-8660-7749e1b4df52');

INSERT INTO sample.data (id, name, string_value)
  VALUES (1, 'Sample Name 1', 'Sample Value 1');
INSERT INTO sample.data (id, name, string_value)
  VALUES (2, 'Sample Name 2', 'Sample Value 2');
INSERT INTO sample.data (id, name, string_value)
  VALUES (3, 'Sample Name 3', 'Sample Value 3');
INSERT INTO sample.data (id, name, string_value)
  VALUES (4, 'Sample Name 4', 'Sample Value 4');
INSERT INTO sample.data (id, name, string_value)
  VALUES (5, 'Sample Name 5', 'Sample Value 5');
INSERT INTO sample.data (id, name, string_value)
  VALUES (6, 'Sample Name 6', 'Sample Value 6');
INSERT INTO sample.data (id, name, string_value)
  VALUES (7, 'Sample Name 7', 'Sample Value 7');
INSERT INTO sample.data (id, name, string_value)
  VALUES (8, 'Sample Name 8', 'Sample Value 8');
INSERT INTO sample.data (id, name, string_value)
  VALUES (9, 'Sample Name 9', 'Sample Value 9');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory01', 'Test Code Category 01', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode011', 'TestCodeCategory01', 'Test Code Name 1.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode012', 'TestCodeCategory01', 'Test Code Name 1.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode013', 'TestCodeCategory01', 'Test Code Name 1.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory02', 'Test Code Category 02', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode021', 'TestCodeCategory02', 'Test Code Name 2.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode022', 'TestCodeCategory02', 'Test Code Name 2.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode023', 'TestCodeCategory02', 'Test Code Name 2.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory03', 'Test Code Category 03', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode031', 'TestCodeCategory03', 'Test Code Name 3.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode032', 'TestCodeCategory03', 'Test Code Name 3.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode033', 'TestCodeCategory03', 'Test Code Name 3.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory04', 'Test Code Category 04', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode041', 'TestCodeCategory04', 'Test Code Name 4.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode042', 'TestCodeCategory04', 'Test Code Name 4.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode043', 'TestCodeCategory04', 'Test Code Name 4.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory05', 'Test Code Category 05', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode051', 'TestCodeCategory05', 'Test Code Name 5.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode052', 'TestCodeCategory05', 'Test Code Name 5.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode053', 'TestCodeCategory05', 'Test Code Name 5.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory06', 'Test Code Category 06', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode061', 'TestCodeCategory06', 'Test Code Name 6.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode062', 'TestCodeCategory06', 'Test Code Name 6.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode063', 'TestCodeCategory06', 'Test Code Name 6.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory07', 'Test Code Category 07', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode071', 'TestCodeCategory07', 'Test Code Name 7.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode072', 'TestCodeCategory07', 'Test Code Name 7.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode073', 'TestCodeCategory07', 'Test Code Name 7.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory08', 'Test Code Category 08', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode081', 'TestCodeCategory08', 'Test Code Name 8.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode082', 'TestCodeCategory08', 'Test Code Name 8.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode083', 'TestCodeCategory08', 'Test Code Name 8.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory09', 'Test Code Category 09', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode091', 'TestCodeCategory09', 'Test Code Name 9.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode092', 'TestCodeCategory09', 'Test Code Name 9.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode093', 'TestCodeCategory09', 'Test Code Name 9.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory10', 'Test Code Category 10', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode101', 'TestCodeCategory10', 'Test Code Name 10.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode102', 'TestCodeCategory10', 'Test Code Name 10.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode103', 'TestCodeCategory10', 'Test Code Name 10.3', 'Test Code Value 3');

INSERT INTO codes.code_categories (id, name, updated, data)
  VALUES ('TestCodeCategory11', 'Test Code Category 11', NOW(), '<codes><code name="name1" value="value1"/></codes>');

INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode111', 'TestCodeCategory11', 'Test Code Name 11.1', 'Test Code Value 1');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode112', 'TestCodeCategory11', 'Test Code Name 11.2', 'Test Code Value 2');
INSERT INTO codes.codes (id, code_category_id, name, value)
  VALUES ('TestCode113', 'TestCodeCategory11', 'Test Code Name 11.3', 'Test Code Value 3');


--INSERT INTO configuration.configuration (key, value, description) VALUES ('TestKey', 'TestValue', 'TestDescription');


INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b601', 'Test 1', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b602', 'Test 2', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b603', 'Test 3', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b604', 'Test 4', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b605', 'Test 5', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b606', 'Test 6', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b607', 'Test 7', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b608', 'Test 8', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b609', 'Test 9', 1);
INSERT INTO security.organizations (id, name, status)
  VALUES ('204e5b8f-48e7-4354-bd15-753e6543b610', 'Test 10', 1);
















INSERT INTO security.user_directories (id, type, name, configuration)
  VALUES ('2a935e0d-ac7e-44c4-aa44-e986aeb8c00e', 'InternalUserDirectory', 'Another Internal User Directory', '<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE userDirectory SYSTEM "UserDirectoryConfiguration.dtd"><userDirectory><parameter><name>MaxPasswordAttempts</name><value>5</value></parameter><parameter><name>PasswordExpiryMonths</name><value>12</value></parameter><parameter><name>PasswordHistoryMonths</name><value>24</value></parameter><parameter><name>MaxFilteredUsers</name><value>100</value></parameter></userDirectory>');

INSERT INTO security.user_directory_to_organization_map (user_directory_id, organization_id)
  VALUES ('2a935e0d-ac7e-44c4-aa44-e986aeb8c00e', '204e5b8f-48e7-4354-bd15-753e6543b64d');

INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('f4292748-7b98-4b6b-94bf-2abed5ccb0eb', '2a935e0d-ac7e-44c4-aa44-e986aeb8c00e', 'jonas', 1, 'Jonas', 'Lang', '', '', 'jonas@sample.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('963c604a-bcaf-4262-9e94-a2f2c55c029d', '2a935e0d-ac7e-44c4-aa44-e986aeb8c00e', 'denise', 1, 'Denise', 'Rasmussen', '', '', 'denise@sample.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);
INSERT INTO security.users (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts)
  VALUES ('606112a5-5a01-45d6-a29b-cea8d22e592d', '2a935e0d-ac7e-44c4-aa44-e986aeb8c00e', 'davis', 1, 'Davis', 'Parks', '', '', 'davis@sample.com', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', 0);

INSERT INTO security.groups (id, user_directory_id, groupname, description)
  VALUES ('69aea0f3-5798-4ee8-9082-6c41feba3472', '2a935e0d-ac7e-44c4-aa44-e986aeb8c00e', 'Organization Administrators', 'Organization Administrators');
INSERT INTO security.groups (id, user_directory_id, groupname, description)
  VALUES ('dd9c3e26-5c86-4ef9-b7c7-f7856c2f836d', '2a935e0d-ac7e-44c4-aa44-e986aeb8c00e', 'Password Resetters', 'Password Resetters');

INSERT INTO security.user_to_group_map (user_id, group_id)
  VALUES ('f4292748-7b98-4b6b-94bf-2abed5ccb0eb', '69aea0f3-5798-4ee8-9082-6c41feba3472');
INSERT INTO security.user_to_group_map (user_id, group_id)
  VALUES ('606112a5-5a01-45d6-a29b-cea8d22e592d', 'dd9c3e26-5c86-4ef9-b7c7-f7856c2f836d');

INSERT INTO security.role_to_group_map (role_code, group_id)
  VALUES ('OrganizationAdministrator', '69aea0f3-5798-4ee8-9082-6c41feba3472');
INSERT INTO security.role_to_group_map (role_code, group_id)
  VALUES ('PasswordResetter', 'dd9c3e26-5c86-4ef9-b7c7-f7856c2f836d');



