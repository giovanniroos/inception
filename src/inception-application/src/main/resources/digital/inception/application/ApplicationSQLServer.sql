-- -------------------------------------------------------------------------------------------------
-- DROP TABLES
-- -------------------------------------------------------------------------------------------------
IF OBJECT_ID('"ERROR"."ERROR_REPORTS"', 'U') IS NOT NULL
DROP TABLE "ERROR"."ERROR_REPORTS";
IF OBJECT_ID('"SMS"."SMS"', 'U') IS NOT NULL
DROP TABLE "SMS"."SMS";
IF OBJECT_ID('"REPORTING"."REPORT_DEFINITIONS"', 'U') IS NOT NULL
DROP TABLE "REPORTING"."REPORT_DEFINITIONS";
IF OBJECT_ID('"MESSAGING"."ARCHIVED_MESSAGES"', 'U') IS NOT NULL
DROP TABLE "MESSAGING"."ARCHIVED_MESSAGES";
IF OBJECT_ID('"MESSAGING"."MESSAGE_PARTS"', 'U') IS NOT NULL
DROP TABLE "MESSAGING"."MESSAGE_PARTS";
IF OBJECT_ID('"MESSAGING"."MESSAGES"', 'U') IS NOT NULL
DROP TABLE "MESSAGING"."MESSAGES";
IF OBJECT_ID('"MESSAGING"."MESSAGE_STATUSES"', 'U') IS NOT NULL
DROP TABLE "MESSAGING"."MESSAGE_STATUSES";
IF OBJECT_ID('"MESSAGING"."MESSAGE_TYPES"', 'U') IS NOT NULL
DROP TABLE "MESSAGING"."MESSAGE_TYPES";
IF OBJECT_ID('"CODES"."CACHED_CODES"', 'U') IS NOT NULL
DROP TABLE "CODES"."CACHED_CODES";
IF OBJECT_ID('"CODES"."CACHED_CODE_CATEGORIES"', 'U') IS NOT NULL
DROP TABLE "CODES"."CACHED_CODE_CATEGORIES";
IF OBJECT_ID('"CODES"."CODES"', 'U') IS NOT NULL
DROP TABLE "CODES"."CODES";
IF OBJECT_ID('"CODES"."CODE_CATEGORIES"', 'U') IS NOT NULL
DROP TABLE "CODES"."CODE_CATEGORIES";
IF OBJECT_ID('"SCHEDULER"."JOB_PARAMETERS"', 'U') IS NOT NULL
DROP TABLE "SCHEDULER"."JOB_PARAMETERS";
IF OBJECT_ID('"SCHEDULER"."JOBS"', 'U') IS NOT NULL
DROP TABLE "SCHEDULER"."JOBS";
IF OBJECT_ID('"SECURITY"."ROLE_TO_GROUP_MAP"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."ROLE_TO_GROUP_MAP";
IF OBJECT_ID('"SECURITY"."FUNCTION_TO_ROLE_MAP"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."FUNCTION_TO_ROLE_MAP";
IF OBJECT_ID('"SECURITY"."ROLES"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."ROLES";
IF OBJECT_ID('"SECURITY"."FUNCTIONS"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."FUNCTIONS";
IF OBJECT_ID('"SECURITY"."GROUPS"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."GROUPS";
IF OBJECT_ID('"SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP";
IF OBJECT_ID('"SECURITY"."INTERNAL_GROUPS"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."INTERNAL_GROUPS";
IF OBJECT_ID('"SECURITY"."INTERNAL_USERS_PASSWORD_HISTORY"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."INTERNAL_USERS_PASSWORD_HISTORY";
IF OBJECT_ID('"SECURITY"."INTERNAL_USERS"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."INTERNAL_USERS";
IF OBJECT_ID('"SECURITY"."USER_DIRECTORY_TO_ORGANIZATION_MAP"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."USER_DIRECTORY_TO_ORGANIZATION_MAP";
IF OBJECT_ID('"SECURITY"."USER_DIRECTORIES"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."USER_DIRECTORIES";
IF OBJECT_ID('"SECURITY"."USER_DIRECTORY_TYPES"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."USER_DIRECTORY_TYPES";
IF OBJECT_ID('"SECURITY"."ORGANIZATIONS"', 'U') IS NOT NULL
DROP TABLE "SECURITY"."ORGANIZATIONS";
IF OBJECT_ID('"SERVICE_REGISTRY"."SERVICE_REGISTRY"', 'U') IS NOT NULL
DROP TABLE "SERVICE_REGISTRY"."SERVICE_REGISTRY";
IF OBJECT_ID('"CONFIG"."CONFIG"', 'U') IS NOT NULL
DROP TABLE "CONFIG"."CONFIG";
IF OBJECT_ID('"IDGENERATOR"."IDGENERATOR"', 'U') IS NOT NULL
DROP TABLE "IDGENERATOR"."IDGENERATOR";
IF OBJECT_ID('"TEST"."TEST_DATA"', 'U') IS NOT NULL
DROP TABLE "TEST"."TEST_DATA";
GO



-- -------------------------------------------------------------------------------------------------
-- CREATE SCHEMAS
-- -------------------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'CODES')
BEGIN
    EXEC('CREATE SCHEMA CODES')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'CONFIG')
BEGIN
    EXEC('CREATE SCHEMA CONFIG')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'IDGENERATOR')
BEGIN
    EXEC('CREATE SCHEMA IDGENERATOR')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'MESSAGING')
BEGIN
    EXEC('CREATE SCHEMA MESSAGING')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'REPORTING')
BEGIN
    EXEC('CREATE SCHEMA REPORTING')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'SCHEDULER')
BEGIN
    EXEC('CREATE SCHEMA SCHEDULER')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'SECURITY')
BEGIN
    EXEC('CREATE SCHEMA SECURITY')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'SERVICE_REGISTRY')
BEGIN
    EXEC('CREATE SCHEMA SERVICE_REGISTRY')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'SMS')
BEGIN
    EXEC('CREATE SCHEMA SMS')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'SMS')
BEGIN
    EXEC('CREATE SCHEMA ERROR')
END;
IF NOT EXISTS (SELECT 1 FROM SYS.SCHEMAS WHERE name = 'TEST')
BEGIN
    EXEC('CREATE SCHEMA TEST')
END;
GO



-- -------------------------------------------------------------------------------------------------
-- CREATE TABLES
-- -------------------------------------------------------------------------------------------------
CREATE TABLE "CODES"."CODE_CATEGORIES" (
  id      NVARCHAR(100) NOT NULL,
  name    NVARCHAR(100) NOT NULL,
  data    CLOB,
  updated DATETIME,

  PRIMARY KEY (id)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The ID used to uniquely identify the code category' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODE_CATEGORIES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the code category' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODE_CATEGORIES', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The code data for the code category' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODE_CATEGORIES', @level2type=N'COLUMN', @level2name=N'DATA';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the code category was updated' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODE_CATEGORIES', @level2type=N'COLUMN', @level2name=N'UPDATED';
GO



CREATE TABLE "CODES"."CODES" (
  id               NVARCHAR(100)  NOT NULL,
  code_category_id NVARCHAR(100)  NOT NULL,
  name             NVARCHAR(100)  NOT NULL,
  value            NVARCHAR(4000) NOT NULL,

  PRIMARY KEY (id, code_category_id),
  CONSTRAINT codes_code_category_fk FOREIGN KEY (code_category_id) REFERENCES "CODES"."CODE_CATEGORIES"(id) ON DELETE CASCADE
);

CREATE INDEX codes_code_category_id_ix ON "CODES"."CODES"(code_category_id);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The ID used to uniquely identify the code' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The ID used to uniquely identify the code category the code is associated with' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODES', @level2type=N'COLUMN', @level2name=N'CODE_CATEGORY_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the code' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODES', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The value for the code' ,
@level0type=N'SCHEMA', @level0name=N'CODES', @level1type=N'TABLE', @level1name=N'CODES', @level2type=N'COLUMN', @level2name=N'VALUE';
GO



CREATE TABLE "CONFIG"."CONFIG" (
  [KEY]        NVARCHAR(256) NOT NULL,
  [VALUE]      NVARCHAR(MAX) NOT NULL,
  DESCRIPTION  NVARCHAR(MAX) NOT NULL,

  PRIMARY KEY ([KEY])
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The key used to uniquely identify the configuration value' ,
@level0type=N'SCHEMA', @level0name=N'CONFIG', @level1type=N'TABLE', @level1name=N'CONFIG', @level2type=N'COLUMN', @level2name=N'KEY';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The value for the configuration value' ,
@level0type=N'SCHEMA', @level0name=N'CONFIG', @level1type=N'TABLE', @level1name=N'CONFIG', @level2type=N'COLUMN', @level2name=N'VALUE';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The description for the configuration value' ,
@level0type=N'SCHEMA', @level0name=N'CONFIG', @level1type=N'TABLE', @level1name=N'CONFIG', @level2type=N'COLUMN', @level2name=N'DESCRIPTION';
GO



CREATE TABLE "IDGENERATOR"."IDGENERATOR" (
  name NVARCHAR(256) NOT NULL, [
  CURRENT]
  BIGINT
  DEFAULT
  0,

  PRIMARY KEY (name)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name giving the type of entity associated with the generated ID' ,
@level0type=N'SCHEMA', @level0name=N'IDGENERATOR', @level1type=N'TABLE', @level1name=N'IDGENERATOR', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The current ID for the type' ,
@level0type=N'SCHEMA', @level0name=N'IDGENERATOR', @level1type=N'TABLE', @level1name=N'IDGENERATOR', @level2type=N'COLUMN', @level2name=N'CURRENT';
GO



CREATE TABLE "MESSAGING"."MESSAGE_TYPES" (
  id   UNIQUEIDENTIFIER NOT NULL,
  name NVARCHAR(256)    NOT NULL,

  PRIMARY KEY (id)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the message type' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_TYPES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the message type' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_TYPES', @level2type=N'COLUMN', @level2name=N'NAME';
GO



CREATE TABLE "MESSAGING"."MESSAGE_STATUSES" (
  code INTEGER       NOT NULL,
  name NVARCHAR(256) NOT NULL,

  PRIMARY KEY (code)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The code identifying the message status' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_STATUSES', @level2type=N'COLUMN', @level2name=N'CODE';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the message status' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_STATUSES', @level2type=N'COLUMN', @level2name=N'NAME';
GO



CREATE TABLE "MESSAGING"."MESSAGES" (
  id                UNIQUEIDENTIFIER NOT NULL,
  username          NVARCHAR(256)    NOT NULL,
  device_id         UNIQUEIDENTIFIER NOT NULL,
  type_id           UNIQUEIDENTIFIER NOT NULL,
  correlation_id    UNIQUEIDENTIFIER NOT NULL,
  priority          INTEGER          NOT NULL,
  status            INTEGER          NOT NULL,
  created           DATETIME         NOT NULL,
  persisted         DATETIME         NOT NULL,
  updated           DATETIME,
  send_attempts     INTEGER          NOT NULL,
  process_attempts  INTEGER          NOT NULL,
  download_attempts INTEGER          NOT NULL,
  lock_name         NVARCHAR(256),
  last_processed    DATETIME,
  data              VARBINARY(MAX),

  PRIMARY KEY (id),
  CONSTRAINT messages_message_type_fk FOREIGN KEY (type_id) REFERENCES "MESSAGING"."MESSAGE_TYPES"(id),
  CONSTRAINT messages_message_status_fk FOREIGN KEY (status) REFERENCES "MESSAGING"."MESSAGE_STATUSES"(code)
);

CREATE INDEX messages_username_ix ON "MESSAGING"."MESSAGES"(username);

CREATE INDEX messages_device_id_ix ON "MESSAGING"."MESSAGES"(device_id);

CREATE INDEX messages_type_id_ix ON "MESSAGING"."MESSAGES"(type_id);

CREATE INDEX messages_priority_ix ON "MESSAGING"."MESSAGES"(priority);

CREATE INDEX messages_status_ix ON "MESSAGING"."MESSAGES"(status);

CREATE INDEX messages_lock_name_ix ON "MESSAGING"."MESSAGES"(lock_name);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The username identifying the user associated with the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'USERNAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the device the message originated from' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'DEVICE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the type of message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'TYPE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to correlate the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'CORRELATION_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The message priority' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'PRIORITY';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The message status e.g. Initialised, QueuedForSending, etc' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'STATUS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the message was created' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'CREATED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the message was persisted' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'PERSISTED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the message was last updated' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'UPDATED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of times that the sending of the message was attempted' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'SEND_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of times that the processing of the message was attempted' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'PROCESS_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of times that an attempt was made to download the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'DOWNLOAD_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the entity that has locked the message for processing' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'LOCK_NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the last attempt was made to process the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'LAST_PROCESSED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The data for the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGES', @level2type=N'COLUMN', @level2name=N'DATA';
GO



CREATE TABLE "MESSAGING"."MESSAGE_PARTS" (
  id                 UNIQUEIDENTIFIER NOT NULL,
  part_no            INTEGER          NOT NULL,
  total_parts        INTEGER          NOT NULL,
  send_attempts      INTEGER          NOT NULL,
  download_attempts  INTEGER          NOT NULL,
  status             INTEGER          NOT NULL,
  persisted          DATETIME         NOT NULL,
  updated            DATETIME,
  msg_id             UNIQUEIDENTIFIER NOT NULL,
  msg_username       NVARCHAR(256)    NOT NULL,
  msg_device_id      UNIQUEIDENTIFIER NOT NULL,
  msg_type_id        UNIQUEIDENTIFIER NOT NULL,
  msg_correlation_id UNIQUEIDENTIFIER NOT NULL,
  msg_priority       INTEGER          NOT NULL,
  msg_created        DATETIME         NOT NULL,
  msg_data_hash      NVARCHAR(512),
  msg_encryption_iv  NVARCHAR(512)    NOT NULL,
  msg_checksum       NVARCHAR(512)    NOT NULL,
  lock_name          NVARCHAR(256),
  data               VARBINARY(MAX),

  PRIMARY KEY (id),
  CONSTRAINT message_parts_message_type_fk FOREIGN KEY (msg_type_id) REFERENCES "MESSAGING"."MESSAGE_TYPES"(id)
);

CREATE INDEX message_parts_status_ix ON "MESSAGING"."MESSAGE_PARTS"(status);

CREATE INDEX message_parts_msg_id_ix ON "MESSAGING"."MESSAGE_PARTS"(msg_id);

CREATE INDEX message_parts_msg_device_id_ix ON "MESSAGING"."MESSAGE_PARTS"(msg_device_id);

CREATE INDEX message_parts_msg_type_id_ix ON "MESSAGING"."MESSAGE_PARTS"(msg_type_id);

CREATE INDEX message_parts_lock_name_ix ON "MESSAGING"."MESSAGE_PARTS"(lock_name);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the message part' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of the message part in the set of message parts for the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'PART_NO';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The total number of parts in the set of message parts for the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'TOTAL_PARTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of times that the sending of the message part was attempted' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'SEND_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of times that an attempt was made to download the message part' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'DOWNLOAD_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The message part status e.g. Initialised, QueuedForSending, etc' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'STATUS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the message part was persisted' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'PERSISTED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the message part was last updated' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'UPDATED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The username identifying the user associated with the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_USERNAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the device the original message originated from' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_DEVICE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the type of the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_TYPE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to correlate the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_CORRELATION_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The priority for the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_PRIORITY';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the original message was created' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_CREATED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The hash of the unencrypted data for the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_DATA_HASH';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The base-64 encoded initialisation vector for the encryption scheme for the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_ENCRYPTION_IV';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The checksum for the original message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'MSG_CHECKSUM';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the entity that has locked the message part for processing' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'LOCK_NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The data for the message part' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'MESSAGE_PARTS', @level2type=N'COLUMN', @level2name=N'DATA';
GO



CREATE TABLE "MESSAGING"."ARCHIVED_MESSAGES" (
  id             UNIQUEIDENTIFIER NOT NULL,
  username       NVARCHAR(256)    NOT NULL,
  device_id      UNIQUEIDENTIFIER NOT NULL,
  type_id        UNIQUEIDENTIFIER NOT NULL,
  correlation_id UNIQUEIDENTIFIER NOT NULL,
  created        DATETIME         NOT NULL,
  archived       DATETIME         NOT NULL,
  data           VARBINARY(MAX),

  PRIMARY KEY (id),
  CONSTRAINT archived_messages_message_type_fk FOREIGN KEY (type_id) REFERENCES "MESSAGING"."MESSAGE_TYPES"(id)
);

CREATE INDEX archived_messages_username_ix ON "MESSAGING"."ARCHIVED_MESSAGES"(username);

CREATE INDEX archived_messages_device_id_ix ON "MESSAGING"."ARCHIVED_MESSAGES"(device_id);

CREATE INDEX archived_messages_type_id_ix ON "MESSAGING"."ARCHIVED_MESSAGES"(type_id);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The username identifying the user associated with the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'USERNAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the device the message originated from' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'DEVICE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the type of message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'TYPE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to correlate the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'CORRELATION_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the message was created' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'CREATED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the message was archived' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'ARCHIVED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The data for the message' ,
@level0type=N'SCHEMA', @level0name=N'MESSAGING', @level1type=N'TABLE', @level1name=N'ARCHIVED_MESSAGES', @level2type=N'COLUMN', @level2name=N'DATA';
GO



CREATE TABLE "REPORTING"."REPORT_DEFINITIONS" (
  id       UNIQUEIDENTIFIER NOT NULL,
  name     NVARCHAR(256)    NOT NULL,
  template VARBINARY(MAX)   NOT NULL,

  PRIMARY KEY (id)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the report definition' ,
@level0type=N'SCHEMA', @level0name=N'REPORTING', @level1type=N'TABLE', @level1name=N'REPORT_DEFINITIONS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the report definition' ,
@level0type=N'SCHEMA', @level0name=N'REPORTING', @level1type=N'TABLE', @level1name=N'REPORT_DEFINITIONS', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The JasperReports template for the report definition' ,
@level0type=N'SCHEMA', @level0name=N'REPORTING', @level1type=N'TABLE', @level1name=N'REPORT_DEFINITIONS', @level2type=N'COLUMN', @level2name=N'TEMPLATE';
GO



CREATE TABLE "SCHEDULER"."JOBS" (
  id                 UNIQUEIDENTIFIER NOT NULL,
  name               NVARCHAR(256)    NOT NULL,
  scheduling_pattern NVARCHAR(1024)   NOT NULL,
  job_class          NVARCHAR(1024)   NOT NULL,
  is_enabled         BIT              NOT NULL,
  status             INTEGER          NOT NULL DEFAULT 1,
  execution_attempts INTEGER          NOT NULL DEFAULT 0,
  lock_name          NVARCHAR(256),
  last_executed      DATETIME,
  next_execution     DATETIME,
  updated            DATETIME,

  PRIMARY KEY (id)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the job' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the job' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The cron-style scheduling pattern for the job' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'SCHEDULING_PATTERN';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The fully qualified name of the Java class that implements the job' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'JOB_CLASS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'Is the job enabled for execution' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'IS_ENABLED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The status of the job' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'STATUS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of times the current execution of the job has been attempted' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'EXECUTION_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the entity that has locked the job for execution' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'LOCK_NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the job was last executed' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'LAST_EXECUTED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time created the job will next be executed' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'NEXT_EXECUTION';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the job was updated' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOBS', @level2type=N'COLUMN', @level2name=N'UPDATED';
GO



CREATE TABLE "SCHEDULER"."JOB_PARAMETERS" (
  id     UNIQUEIDENTIFIER NOT NULL,
  job_id UNIQUEIDENTIFIER NOT NULL,
  name   NVARCHAR(256)    NOT NULL,
  value  NVARCHAR(MAX)    NOT NULL,

  PRIMARY KEY (id),
  CONSTRAINT job_parameters_job_fk FOREIGN KEY (job_id) REFERENCES "SCHEDULER"."JOBS"(id) ON DELETE CASCADE
);

CREATE INDEX job_parameters_job_id_ix ON "SCHEDULER"."JOB_PARAMETERS"(job_id);

CREATE INDEX job_parameters_name_ix ON "SCHEDULER"."JOB_PARAMETERS"(name);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the job parameter' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOB_PARAMETERS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the job' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOB_PARAMETERS', @level2type=N'COLUMN', @level2name=N'JOB_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the job parameter' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOB_PARAMETERS', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The value of the job parameter' ,
@level0type=N'SCHEMA', @level0name=N'SCHEDULER', @level1type=N'TABLE', @level1name=N'JOB_PARAMETERS', @level2type=N'COLUMN', @level2name=N'VALUE';
GO



CREATE TABLE "SECURITY"."ORGANIZATIONS" (
  id     UNIQUEIDENTIFIER NOT NULL,
  name   NVARCHAR(256)    NOT NULL,
  status INTEGER          NOT NULL,

  PRIMARY KEY (id)
);

CREATE INDEX organizations_name_ix ON "SECURITY"."ORGANIZATIONS"(name);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the organization' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ORGANIZATIONS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the organization' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ORGANIZATIONS', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The status for the organization' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ORGANIZATIONS', @level2type=N'COLUMN', @level2name=N'STATUS';
GO



CREATE TABLE "SECURITY"."USER_DIRECTORY_TYPES" (
  id                   UNIQUEIDENTIFIER NOT NULL,
  name                 NVARCHAR(256)    NOT NULL,
  user_directory_class NVARCHAR(1024)   NOT NULL,

  PRIMARY KEY (id)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the user directory type' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORY_TYPES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the user directory type' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORY_TYPES', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The fully qualified name of the Java class that implements the user directory type' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORY_TYPES', @level2type=N'COLUMN', @level2name=N'USER_DIRECTORY_CLASS';



CREATE TABLE "SECURITY"."USER_DIRECTORIES" (
  id      UNIQUEIDENTIFIER NOT NULL,
  type_id UNIQUEIDENTIFIER NOT NULL,
  name    NVARCHAR(256)    NOT NULL,
  config  NVARCHAR(MAX)    NOT NULL,

  PRIMARY KEY (id),
  CONSTRAINT user_directories_user_directory_type_fk FOREIGN KEY (type_id) REFERENCES "SECURITY"."USER_DIRECTORY_TYPES"(id) ON DELETE CASCADE
);

CREATE INDEX user_directories_name_ix ON "SECURITY"."USER_DIRECTORIES"(name);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the user directory' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORIES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the user directory type' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORIES', @level2type=N'COLUMN', @level2name=N'TYPE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the user directory' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORIES', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The XML configuration data for the user directory' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORIES', @level2type=N'COLUMN', @level2name=N'CONFIG';
GO



CREATE TABLE "SECURITY"."USER_DIRECTORY_TO_ORGANIZATION_MAP" (
  user_directory_id UNIQUEIDENTIFIER NOT NULL,
  organization_id   UNIQUEIDENTIFIER NOT NULL,

  PRIMARY KEY (user_directory_id, organization_id),
  CONSTRAINT user_directory_to_organization_map_user_directory_fk FOREIGN KEY (user_directory_id) REFERENCES "SECURITY"."USER_DIRECTORIES"(id) ON DELETE CASCADE,
  CONSTRAINT user_directory_to_organization_map_organization_fk FOREIGN KEY (organization_id) REFERENCES "SECURITY"."ORGANIZATIONS"(id) ON DELETE CASCADE
);

CREATE INDEX user_directory_to_organization_map_user_directory_id_ix ON "SECURITY"."USER_DIRECTORY_TO_ORGANIZATION_MAP"(user_directory_id);

CREATE INDEX user_directory_to_organization_map_organization_id_ix ON "SECURITY"."USER_DIRECTORY_TO_ORGANIZATION_MAP"(organization_id);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the user directory' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORY_TO_ORGANIZATION_MAP', @level2type=N'COLUMN', @level2name=N'USER_DIRECTORY_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the organization' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'USER_DIRECTORY_TO_ORGANIZATION_MAP', @level2type=N'COLUMN', @level2name=N'ORGANIZATION_ID';
GO



CREATE TABLE "SECURITY"."INTERNAL_USERS" (
  id                UNIQUEIDENTIFIER NOT NULL,
  user_directory_id UNIQUEIDENTIFIER NOT NULL,
  username          NVARCHAR(256)    NOT NULL,
  status            INTEGER          NOT NULL,
  first_name        NVARCHAR(256),
  last_name         NVARCHAR(256),
  phone             NVARCHAR(256),
  mobile            NVARCHAR(256),
  email             NVARCHAR(256),
  password          NVARCHAR(512),
  password_attempts INTEGER,
  password_expiry   DATETIME,

  PRIMARY KEY (id),
  CONSTRAINT internal_users_user_directory_fk FOREIGN KEY (user_directory_id) REFERENCES "SECURITY"."USER_DIRECTORIES"(id) ON DELETE CASCADE
);

CREATE INDEX internal_users_user_directory_id_ix ON "SECURITY"."INTERNAL_USERS"(user_directory_id);

CREATE UNIQUE INDEX internal_users_username_ix ON "SECURITY"."INTERNAL_USERS"(username);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the user directory the internal user is associated with' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'USER_DIRECTORY_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The username for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'USERNAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The status for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'STATUS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The password for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'PASSWORD';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The first name for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'FIRST_NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The last name for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'LAST_NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The phone number for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'PHONE';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The mobile number for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'MOBILE';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The e-mail address for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'EMAIL';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of failed attempts to authenticate the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'PASSWORD_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time that the internal user''s password expires' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS', @level2type=N'COLUMN', @level2name=N'PASSWORD_EXPIRY';
GO



CREATE TABLE "SECURITY"."INTERNAL_USERS_PASSWORD_HISTORY" (
  id               UNIQUEIDENTIFIER NOT NULL,
  internal_user_id UNIQUEIDENTIFIER NOT NULL,
  changed          DATETIME         NOT NULL,
  password         NVARCHAR(512),

  PRIMARY KEY (id),
  CONSTRAINT internal_users_password_history_internal_user_id_fk FOREIGN KEY (internal_user_id) REFERENCES "SECURITY"."INTERNAL_USERS"(id) ON DELETE CASCADE
);

CREATE INDEX internal_users_password_history_internal_user_id_ix ON "SECURITY"."INTERNAL_USERS_PASSWORD_HISTORY"(internal_user_id);

CREATE INDEX internal_users_password_history_changed_ix ON "SECURITY"."INTERNAL_USERS_PASSWORD_HISTORY"(changed);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the password history entry' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS_PASSWORD_HISTORY', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS_PASSWORD_HISTORY', @level2type=N'COLUMN', @level2name=N'INTERNAL_USER_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'When the password change took place for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS_PASSWORD_HISTORY', @level2type=N'COLUMN', @level2name=N'CHANGED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The password for the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USERS_PASSWORD_HISTORY', @level2type=N'COLUMN', @level2name=N'PASSWORD';
GO



CREATE TABLE "SECURITY"."INTERNAL_GROUPS" (
  id                UNIQUEIDENTIFIER NOT NULL,
  user_directory_id UNIQUEIDENTIFIER NOT NULL,
  groupname         NVARCHAR(256)    NOT NULL,
  description       NVARCHAR(512),

  PRIMARY KEY (id),
  CONSTRAINT internal_groups_user_directory_fk FOREIGN KEY (user_directory_id) REFERENCES "SECURITY"."USER_DIRECTORIES"(id) ON DELETE CASCADE
);

CREATE INDEX internal_groups_user_directory_id_ix ON "SECURITY"."INTERNAL_GROUPS"(user_directory_id);

CREATE INDEX internal_groups_groupname_ix ON "SECURITY"."INTERNAL_GROUPS"(groupname);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the internal group' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_GROUPS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the user directory the internal group is associated with' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_GROUPS', @level2type=N'COLUMN', @level2name=N'USER_DIRECTORY_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The group name for the internal group' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_GROUPS', @level2type=N'COLUMN', @level2name=N'GROUPNAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'A description for the internal group' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_GROUPS', @level2type=N'COLUMN', @level2name=N'DESCRIPTION';
GO



CREATE TABLE "SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP" (
  internal_user_id  UNIQUEIDENTIFIER NOT NULL,
  internal_group_id UNIQUEIDENTIFIER NOT NULL,

  PRIMARY KEY (internal_user_id, internal_group_id)
);

CREATE INDEX internal_user_to_internal_group_map_internal_user_id_ix ON "SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP"(internal_user_id);

CREATE INDEX internal_user_to_internal_group_map_internal_group_id_ix ON "SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP"(internal_group_id);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the internal user' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USER_TO_INTERNAL_GROUP_MAP', @level2type=N'COLUMN', @level2name=N'INTERNAL_USER_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the internal group' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'INTERNAL_USER_TO_INTERNAL_GROUP_MAP', @level2type=N'COLUMN', @level2name=N'INTERNAL_GROUP_ID';
GO

CREATE TRIGGER internal_groups_on_delete_internal_user_to_internal_group_map_trigger
  ON "SECURITY"."INTERNAL_GROUPS"
  FOR DELETE AS BEGIN DECLARE @ID UNIQUEIDENTIFIER DECLARE C CURSOR FOR
SELECT deleted.id
  FROM deleted open C FETCH NEXT
  FROM C
  INTO @ID WHILE @@FETCH_STATUS = 0 BEGIN
DELETE
  FROM "SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP"
  WHERE internal_group_id = @id FETCH NEXT
  FROM C
  INTO @ID END CLOSE C DEALLOCATE C END GO

CREATE TRIGGER internal_users_on_delete_internal_user_to_internal_group_map_trigger
  ON "SECURITY"."INTERNAL_USERS"
  FOR DELETE AS BEGIN DECLARE @ID UNIQUEIDENTIFIER DECLARE C CURSOR FOR
SELECT deleted.id
  FROM deleted open C FETCH NEXT
  FROM C
  INTO @ID WHILE @@FETCH_STATUS = 0 BEGIN
DELETE
  FROM "SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP"
  WHERE internal_user_id = @id FETCH NEXT
  FROM C
  INTO @ID END CLOSE C DEALLOCATE C END GO



CREATE TABLE "SECURITY"."GROUPS" (
  id                UNIQUEIDENTIFIER NOT NULL,
  user_directory_id UNIQUEIDENTIFIER NOT NULL,
  groupname         NVARCHAR(256)    NOT NULL,

  PRIMARY KEY (id)
);

CREATE INDEX groups_user_directory_id_ix ON "SECURITY"."GROUPS"(user_directory_id);

CREATE INDEX groups_groupname_ix ON "SECURITY"."GROUPS"(groupname);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the group' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'GROUPS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the user directory the group is associated with' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'GROUPS', @level2type=N'COLUMN', @level2name=N'USER_DIRECTORY_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The group name for the group' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'GROUPS', @level2type=N'COLUMN', @level2name=N'GROUPNAME';
GO



CREATE TABLE "SECURITY"."FUNCTIONS" (
  id          UNIQUEIDENTIFIER NOT NULL,
  code        NVARCHAR(256)    NOT NULL,
  name        NVARCHAR(256)    NOT NULL,
  description NVARCHAR(512),

  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX functions_code_ix ON "SECURITY"."FUNCTIONS"(code);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the function' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'FUNCTIONS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The unique code used to identify the function' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'FUNCTIONS', @level2type=N'COLUMN', @level2name=N'CODE';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the function' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'FUNCTIONS', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'A description for the function' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'FUNCTIONS', @level2type=N'COLUMN', @level2name=N'DESCRIPTION';
GO



CREATE TABLE "SECURITY"."ROLES" (
  id          UNIQUEIDENTIFIER NOT NULL,
  name        NVARCHAR(256)    NOT NULL,
  description NVARCHAR(512),

  PRIMARY KEY (id)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the role' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ROLES', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the role' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ROLES', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'A description for the role' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ROLES', @level2type=N'COLUMN', @level2name=N'DESCRIPTION';
GO



CREATE TABLE "SECURITY"."FUNCTION_TO_ROLE_MAP" (
  function_id UNIQUEIDENTIFIER NOT NULL,
  role_id     UNIQUEIDENTIFIER NOT NULL,

  PRIMARY KEY (function_id, role_id)
);

CREATE INDEX function_to_role_map_function_id_ix ON "SECURITY"."FUNCTION_TO_ROLE_MAP"(function_id);

CREATE INDEX function_to_role_map_role_id_ix ON "SECURITY"."FUNCTION_TO_ROLE_MAP"(role_id);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the function' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'FUNCTION_TO_ROLE_MAP', @level2type=N'COLUMN', @level2name=N'FUNCTION_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the role' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'FUNCTION_TO_ROLE_MAP', @level2type=N'COLUMN', @level2name=N'ROLE_ID';
GO

CREATE TRIGGER functions_on_delete_function_to_role_map_trigger
  ON "SECURITY"."FUNCTIONS"
  FOR DELETE AS BEGIN DECLARE @ID UNIQUEIDENTIFIER DECLARE C CURSOR FOR
SELECT deleted.id
  FROM deleted open C FETCH NEXT
  FROM C
  INTO @ID WHILE @@FETCH_STATUS = 0 BEGIN
DELETE
  FROM "SECURITY"."FUNCTION_TO_ROLE_MAP"
  WHERE function_id = @id FETCH NEXT
  FROM C
  INTO @ID END CLOSE C DEALLOCATE C END GO

CREATE TRIGGER roles_on_delete_function_to_role_map_trigger
  ON "SECURITY"."ROLES"
  FOR DELETE AS BEGIN DECLARE @ID UNIQUEIDENTIFIER DECLARE C CURSOR FOR
SELECT deleted.id
  FROM deleted open C FETCH NEXT
  FROM C
  INTO @ID WHILE @@FETCH_STATUS = 0 BEGIN
DELETE
  FROM "SECURITY"."FUNCTION_TO_ROLE_MAP"
  WHERE role_id = @id FETCH NEXT
  FROM C
  INTO @ID END CLOSE C DEALLOCATE C END GO



CREATE TABLE "SECURITY"."ROLE_TO_GROUP_MAP" (
  role_id  UNIQUEIDENTIFIER NOT NULL,
  group_id UNIQUEIDENTIFIER NOT NULL,

  PRIMARY KEY (role_id, group_id),
);

CREATE INDEX role_to_group_map_role_id_ix ON "SECURITY"."ROLE_TO_GROUP_MAP"(role_id);

CREATE INDEX role_to_group_map_group_id_ix ON "SECURITY"."ROLE_TO_GROUP_MAP"(group_id);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the role' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ROLE_TO_GROUP_MAP', @level2type=N'COLUMN', @level2name=N'ROLE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the group' ,
@level0type=N'SCHEMA', @level0name=N'SECURITY', @level1type=N'TABLE', @level1name=N'ROLE_TO_GROUP_MAP', @level2type=N'COLUMN', @level2name=N'GROUP_ID';
GO

CREATE TRIGGER roles_on_delete_role_to_group_map_trigger
  ON "SECURITY"."ROLES"
  FOR DELETE AS BEGIN DECLARE @ID UNIQUEIDENTIFIER DECLARE C CURSOR FOR
SELECT deleted.id
  FROM deleted open C FETCH NEXT
  FROM C
  INTO @ID WHILE @@FETCH_STATUS = 0 BEGIN
DELETE
  FROM "SECURITY"."ROLE_TO_GROUP_MAP"
  WHERE role_id = @id FETCH NEXT
  FROM C
  INTO @ID END CLOSE C DEALLOCATE C END GO

CREATE TRIGGER groups_on_delete_role_to_group_map_trigger
  ON "SECURITY"."GROUPS"
  FOR DELETE AS BEGIN DECLARE @ID UNIQUEIDENTIFIER DECLARE C CURSOR FOR
SELECT deleted.id
  FROM deleted open C FETCH NEXT
  FROM C
  INTO @ID WHILE @@FETCH_STATUS = 0 BEGIN
DELETE
  FROM "SECURITY"."ROLE_TO_GROUP_MAP"
  WHERE group_id = @id FETCH NEXT
  FROM C
  INTO @ID END CLOSE C DEALLOCATE C END GO



CREATE TABLE "SERVICE_REGISTRY"."SERVICE_REGISTRY" (
  name                 NVARCHAR(256)  NOT NULL,
  security_type        INTEGER        NOT NULL,
  supports_compression CHAR(1)        NOT NULL,
  endpoint             NVARCHAR(1024) NOT NULL,
  service_class        NVARCHAR(1024) NOT NULL,
  wsdl_location        NVARCHAR(1024) NOT NULL,
  username             NVARCHAR(256),
  password             NVARCHAR(256),

  PRIMARY KEY (name)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name used to uniquely identify the web service' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The type of security model implemented by the web service i.e. 0 = None, 1 = Mutual SSL, etc' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'SECURITY_TYPE';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'Does the web service support compression' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'SUPPORTS_COMPRESSION';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The endpoint for the web service' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'ENDPOINT';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The fully qualified name of the Java service class' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'SERVICE_CLASS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The location of the WSDL defining the web service on the classpath' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'WSDL_LOCATION';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The username to use created accessing a web service with username-password security enabled' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'USERNAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The password to use created accessing a web service with username-password security enabled' ,
@level0type=N'SCHEMA', @level0name=N'SERVICE_REGISTRY', @level1type=N'TABLE', @level1name=N'SERVICE_REGISTRY', @level2type=N'COLUMN', @level2name=N'PASSWORD';
GO



CREATE TABLE "SMS"."SMS" (
  id             BIGINT         NOT NULL,
  mobile_number  NVARCHAR(100)  NOT NULL,
  message        NVARCHAR(1024) NOT NULL,
  status         INTEGER        NOT NULL,
  send_attempts  INTEGER        NOT NULL,
  lock_name      NVARCHAR(256),
  last_processed DATETIME,

  PRIMARY KEY (id)
);

CREATE INDEX sms_mobile_number_ix ON "SMS"."SMS"(mobile_number);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The ID used to uniquely identify the SMS' ,
@level0type=N'SCHEMA', @level0name=N'SMS', @level1type=N'TABLE', @level1name=N'SMS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The mobile number to send the SMS to' ,
@level0type=N'SCHEMA', @level0name=N'SMS', @level1type=N'TABLE', @level1name=N'SMS', @level2type=N'COLUMN', @level2name=N'MOBILE_NUMBER';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The message to send' ,
@level0type=N'SCHEMA', @level0name=N'SMS', @level1type=N'TABLE', @level1name=N'SMS', @level2type=N'COLUMN', @level2name=N'MESSAGE';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The status of the SMS' ,
@level0type=N'SCHEMA', @level0name=N'SMS', @level1type=N'TABLE', @level1name=N'SMS', @level2type=N'COLUMN', @level2name=N'STATUS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The number of times that the sending of the SMS was attempted' ,
@level0type=N'SCHEMA', @level0name=N'SMS', @level1type=N'TABLE', @level1name=N'SMS', @level2type=N'COLUMN', @level2name=N'SEND_ATTEMPTS';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name of the entity that has locked the SMS for sending' ,
@level0type=N'SCHEMA', @level0name=N'SMS', @level1type=N'TABLE', @level1name=N'SMS', @level2type=N'COLUMN', @level2name=N'LOCK_NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the last attempt was made to send the SMS' ,
@level0type=N'SCHEMA', @level0name=N'SMS', @level1type=N'TABLE', @level1name=N'SMS', @level2type=N'COLUMN', @level2name=N'LAST_PROCESSED';
GO



CREATE TABLE "ERROR"."ERROR_REPORTS" (
  id                  UNIQUEIDENTIFIER NOT NULL,
  application_id      NVARCHAR(100)    NOT NULL,
  application_version NVARCHAR(50)     NOT NULL,
  description         NVARCHAR(4000)   NOT NULL,
  detail              NVARCHAR(4000)   NOT NULL,
  created             DATETIME         NOT NULL,
  who                 NVARCHAR(1000),
  device_id           NVARCHAR(50),
  feedback            NVARCHAR(4000),
  data                VARBINARY(MAX),

  PRIMARY KEY (id)
);

CREATE INDEX error_reports_application_id_ix ON "ERROR"."ERROR_REPORTS"(application_id);

CREATE INDEX error_reports_created_ix ON "ERROR"."ERROR_REPORTS"(created);

CREATE INDEX error_reports_who_ix ON "ERROR"."ERROR_REPORTS"(who);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The Universally Unique Identifier (UUID) used to uniquely identify the error report' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The ID used to uniquely identify the application that generated the error report' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'APPLICATION_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The version of the application that generated the error report' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'APPLICATION_VERSION';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The description of the error' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'DESCRIPTION';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The error detail e.g. a stack trace' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'DETAIL';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The date and time the error report was created' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'CREATED';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The optional username identifying the user associated with the error report' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'WHO';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The optional ID used to uniquely identify the device the error report originated from' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'DEVICE_ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The optional feedback provided by the user for the error' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'FEEDBACK';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The optional data associated with the error report' ,
@level0type=N'SCHEMA', @level0name=N'ERROR', @level1type=N'TABLE', @level1name=N'ERROR_REPORTS', @level2type=N'COLUMN', @level2name=N'DATA';
GO



CREATE TABLE "TEST"."TEST_DATA" (
  id    INTEGER        NOT NULL,
  name  NVARCHAR(4000) NOT NULL,
  value NVARCHAR(4000) NOT NULL,

  PRIMARY KEY (id)
);

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The ID used to uniquely identify the test data' ,
@level0type=N'SCHEMA', @level0name=N'TEST', @level1type=N'TABLE', @level1name=N'TEST_DATA', @level2type=N'COLUMN', @level2name=N'ID';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The name for the test data' ,
@level0type=N'SCHEMA', @level0name=N'TEST', @level1type=N'TABLE', @level1name=N'TEST_DATA', @level2type=N'COLUMN', @level2name=N'NAME';

EXEC sys.sp_addextendedproperty
@name=N'MS_Description', @value=N'The value for the test data' ,
@level0type=N'SCHEMA', @level0name=N'TEST', @level1type=N'TABLE', @level1name=N'TEST_DATA', @level2type=N'COLUMN', @level2name=N'VALUE';
GO



-- -------------------------------------------------------------------------------------------------
-- POPULATE TABLES
-- -------------------------------------------------------------------------------------------------
INSERT INTO "SECURITY"."ORGANIZATIONS" (id, name, status)
  VALUES ('c1685b92-9fe5-453a-995b-89d8c0f29cb5', 'MMP', 1);

INSERT INTO "SECURITY"."USER_DIRECTORY_TYPES" (id, name, user_directory_class)
  VALUES ('b43fda33-d3b0-4f80-a39a-110b8e530f4f', 'Internal User Directory', 'digital.inception.security.InternalUserDirectory');
INSERT INTO "SECURITY"."USER_DIRECTORY_TYPES" (id, name, user_directory_class)
  VALUES ('e5741a89-c87b-4406-8a60-2cc0b0a5fa3e', 'LDAP User Directory', 'digital.inception.security.LDAPUserDirectory');

INSERT INTO "SECURITY"."USER_DIRECTORIES" (id, type_id, name, config)
  VALUES ('4ef18395-423a-4df6-b7d7-6bcdd85956e4', 'b43fda33-d3b0-4f80-a39a-110b8e530f4f', 'Internal User Directory', '<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE userDirectory SYSTEM "UserDirectoryConfiguration.dtd"><userDirectory><parameter><name>MaxPasswordAttempts</name><value>5</value></parameter><parameter><name>PasswordExpiryMonths</name><value>12</value></parameter><parameter><name>PasswordHistoryMonths</name><value>24</value></parameter><parameter><name>MaxFilteredUsers</name><value>100</value></parameter></userDirectory>');

INSERT INTO "SECURITY"."USER_DIRECTORY_TO_ORGANIZATION_MAP" (user_directory_id, organization_id)
  VALUES ('4ef18395-423a-4df6-b7d7-6bcdd85956e4', 'c1685b92-9fe5-453a-995b-89d8c0f29cb5');

INSERT INTO "SECURITY"."INTERNAL_USERS" (id, user_directory_id, username, status, first_name, last_name, phone, mobile, email, password, password_attempts, password_expiry)
  VALUES ('b2bbf431-4af8-4104-b96c-d33b5f66d1e4', '4ef18395-423a-4df6-b7d7-6bcdd85956e4', 'Administrator', 1, '', '', '', '', '', 'GVE/3J2k+3KkoF62aRdUjTyQ/5TVQZ4fI2PuqJ3+4d0=', NULL, NULL);

INSERT INTO "SECURITY"."INTERNAL_GROUPS" (id, user_directory_id, groupname, description)
  VALUES ('a9e01fa2-f017-46e2-8187-424bf50a4f33', '4ef18395-423a-4df6-b7d7-6bcdd85956e4', 'Administrators', 'Administrators');
INSERT INTO "SECURITY"."INTERNAL_GROUPS" (id, user_directory_id, groupname, description)
  VALUES ('758c0a2a-f3a3-4561-bebc-90569291976e', '4ef18395-423a-4df6-b7d7-6bcdd85956e4', 'Organization Administrators', 'Organization Administrators');

INSERT INTO "SECURITY"."INTERNAL_USER_TO_INTERNAL_GROUP_MAP" (internal_user_id, internal_group_id)
  VALUES ('b2bbf431-4af8-4104-b96c-d33b5f66d1e4', 'a9e01fa2-f017-46e2-8187-424bf50a4f33');

INSERT INTO "SECURITY"."GROUPS" (id, user_directory_id, groupname)
  VALUES ('a9e01fa2-f017-46e2-8187-424bf50a4f33', '4ef18395-423a-4df6-b7d7-6bcdd85956e4', 'Administrators');
INSERT INTO "SECURITY"."GROUPS" (id, user_directory_id, groupname)
  VALUES ('758c0a2a-f3a3-4561-bebc-90569291976e', '4ef18395-423a-4df6-b7d7-6bcdd85956e4', 'Organization Administrators');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('2a43152c-d8ae-4b08-8ad9-2448ec5debd5', 'Application.SecureHome', 'Secure Home', 'Secure Home');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('f4e3b387-8cd1-4c56-a2da-fe39a78a56d9', 'Application.Dashboard', 'Dashboard', 'Dashboard');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('4e6bc7c4-ee29-4cd7-b4d7-3be42db73dd6', 'Codes.CodeAdministration', 'Code Administration', 'Code Administration');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('b233ed4a-b30f-4356-a5d3-1c660aa69f00', 'Configuration.ConfigurationAdministration', 'Configuration Administration', 'Configuration Administration');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('97f0f870-a871-48de-a3e0-a32a95770f12', 'Error.ErrorReportAdministration', 'Error Report Administration', 'Error Report Administration');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('180c84f9-9816-48d0-9762-dc753b2228b1', 'Process.ProcessDefinitionAdministration', 'Process Definition Administration', 'Process Definition Administration');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('d2854c65-9a59-40b8-9dc7-a882c64b2610', 'Process.ViewProcess', 'View Process', 'View Process');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('3a17959c-5dfc-43a2-9587-48a1eb95a22a', 'Reporting.ReportDefinitionAdministration', 'Report Definition Administration', 'Report Definition Administration');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('539fceb8-da82-4170-ab1a-ae6b04001c03', 'Reporting.ViewReport', 'View Report', 'View Report');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('4d60aed6-2d4b-4a91-a178-ac06d4b1769a', 'Scheduler.SchedulerAdministration', 'Scheduler Administration', 'Scheduler Administration');

INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('2d52b029-920f-4b15-b646-5b9955c188e3', 'Security.OrganizationAdministration', 'Organization Administration', 'Organization Administration');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('ef03f384-24f7-43eb-a29c-f5c5b838698d', 'Security.GroupAdministration', 'Group Administration', 'Group Administration');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('029b9a06-0241-4a44-a234-5c489f2017ba', 'Security.ResetUserPassword', 'Reset User Password', 'Reset User Password');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('9105fb6d-1629-4014-bf4c-1990a92db276', 'Security.SecurityAdministration', 'Security Administration', 'Security Administration');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('567d7e55-f3d0-4191-bc4c-12d357900fa3', 'Security.UserAdministration', 'User Administration', 'User Administration');
INSERT INTO "SECURITY"."FUNCTIONS" (id, code, name, description)
  VALUES ('7a54a71e-3680-4d49-b87d-29604a247413', 'Security.UserGroups', 'User Groups', 'User Groups');


INSERT INTO "SECURITY"."ROLES" (id, name, description)
  VALUES ('100fafb4-783a-4204-a22d-9e27335dc2ea', 'Administrator', 'Administrator');
INSERT INTO "SECURITY"."ROLES" (id, name, description)
  VALUES ('44ff0ad2-fbe1-489f-86c9-cef7f82acf35', 'Organization Administrator', 'Organization Administrator');

INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('2a43152c-d8ae-4b08-8ad9-2448ec5debd5', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Application.SecureHome
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('f4e3b387-8cd1-4c56-a2da-fe39a78a56d9', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Application.Dashboard
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('2d52b029-920f-4b15-b646-5b9955c188e3', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Security.OrganizationAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('567d7e55-f3d0-4191-bc4c-12d357900fa3', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Security.UserAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('ef03f384-24f7-43eb-a29c-f5c5b838698d', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Security.GroupAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('7a54a71e-3680-4d49-b87d-29604a247413', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Security.UserGroups
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('4e6bc7c4-ee29-4cd7-b4d7-3be42db73dd6', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Codes.CodeAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('029b9a06-0241-4a44-a234-5c489f2017ba', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Security.ResetUserPassword
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('9105fb6d-1629-4014-bf4c-1990a92db276', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Security.SecurityAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('b233ed4a-b30f-4356-a5d3-1c660aa69f00', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Configuration.ConfigurationAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('97f0f870-a871-48de-a3e0-a32a95770f12', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Error.ErrorReportAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('3a17959c-5dfc-43a2-9587-48a1eb95a22a', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Reporting.ReportDefinitionAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('539fceb8-da82-4170-ab1a-ae6b04001c03', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Reporting.ViewReport
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('180c84f9-9816-48d0-9762-dc753b2228b1', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Process.ProcessDefinitionAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('d2854c65-9a59-40b8-9dc7-a882c64b2610', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Process.ViewProcess
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('4d60aed6-2d4b-4a91-a178-ac06d4b1769a', '100fafb4-783a-4204-a22d-9e27335dc2ea'); -- Scheduler.SchedulerAdministration

INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('2a43152c-d8ae-4b08-8ad9-2448ec5debd5', '44ff0ad2-fbe1-489f-86c9-cef7f82acf35'); -- Application.SecureHome
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('f4e3b387-8cd1-4c56-a2da-fe39a78a56d9', '44ff0ad2-fbe1-489f-86c9-cef7f82acf35'); -- Application.Dashboard
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('567d7e55-f3d0-4191-bc4c-12d357900fa3', '44ff0ad2-fbe1-489f-86c9-cef7f82acf35'); -- Security.UserAdministration
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('7a54a71e-3680-4d49-b87d-29604a247413', '44ff0ad2-fbe1-489f-86c9-cef7f82acf35'); -- Security.UserGroups
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('029b9a06-0241-4a44-a234-5c489f2017ba', '44ff0ad2-fbe1-489f-86c9-cef7f82acf35'); -- Security.ResetUserPassword
INSERT INTO "SECURITY"."FUNCTION_TO_ROLE_MAP" (function_id, role_id)
  VALUES ('539fceb8-da82-4170-ab1a-ae6b04001c03', '44ff0ad2-fbe1-489f-86c9-cef7f82acf35'); -- Reporting.ViewReport

INSERT INTO "SECURITY"."ROLE_TO_GROUP_MAP" (role_id, group_id)
  VALUES ('100fafb4-783a-4204-a22d-9e27335dc2ea', 'a9e01fa2-f017-46e2-8187-424bf50a4f33');
INSERT INTO "SECURITY"."ROLE_TO_GROUP_MAP" (role_id, group_id)
  VALUES ('44ff0ad2-fbe1-489f-86c9-cef7f82acf35', '758c0a2a-f3a3-4561-bebc-90569291976e');

INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('d21fb54e-5c5b-49e8-881f-ce00c6ced1a3', 'AuthenticateRequest');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('82223035-1726-407f-8703-3977708e792c', 'AuthenticateResponse');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('cc005e6a-b01b-48eb-98a0-026297be69f3', 'CheckUserExistsRequest');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('a38bd55e-3470-46f1-a96a-a6b08a9adc63', 'CheckUserExistsResponse');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('94d60eb6-a062-492d-b5e7-9fb1f05cf088', 'GetCodeCategoryRequest');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('0336b544-91e5-4eb9-81db-3dd94e116c92', 'GetCodeCategoryResponse');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('3500a28a-6a2c-482b-b81f-a849c9c3ef79', 'GetCodeCategoryWithParametersRequest');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('12757310-9eee-4a3a-970c-9b4ee0e1108e', 'GetCodeCategoryWithParametersResponse');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('a589dc87-2328-4a9b-bdb6-970e55ca2323', 'TestRequest');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('a3bad7ba-f9d4-4403-b54a-cb1f335ebbad', 'TestResponse');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('e9918051-8ebc-48f1-bad7-13c59b550e1a', 'AnotherTestRequest');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('a714a9c6-2914-4498-ab59-64be9991bf37', 'AnotherTestResponse');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('ff638c33-b4f1-4e79-804c-9560da2543d6', 'SubmitErrorReportRequest');
INSERT INTO "MESSAGING"."MESSAGE_TYPES" (id, name)
  VALUES ('8be50cfa-2fb1-4634-9bfa-d01e77eaf766', 'SubmitErrorReportResponse');

INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (0, 'Initialised');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (1, 'QueuedForSending');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (2, 'QueuedForProcessing');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (3, 'Aborted');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (4, 'Failed');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (5, 'Processing');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (6, 'Sending');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (7, 'QueuedForDownload');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (8, 'Downloading');
INSERT INTO "MESSAGING"."MESSAGE_STATUSES" (code, name)
  VALUES (10, 'Processed');

INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (1, 'Sample Name 1', 'Sample Value 1');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (2, 'Sample Name 2', 'Sample Value 2');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (3, 'Sample Name 3', 'Sample Value 3');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (4, 'Sample Name 4', 'Sample Value 4');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (5, 'Sample Name 5', 'Sample Value 5');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (6, 'Sample Name 6', 'Sample Value 6');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (7, 'Sample Name 7', 'Sample Value 7');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (8, 'Sample Name 8', 'Sample Value 8');
INSERT INTO "TEST"."TEST_DATA" (id, name, value)
  VALUES (9, 'Sample Name 9', 'Sample Value 9');



