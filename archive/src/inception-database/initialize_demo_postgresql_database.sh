#!/bin/sh

# Execute the following command to allow the script to be executed on MacOS:
#   xattr -d com.apple.quarantine initialize_demo_postgresql_database.sh

liquibase --changeLogFile=../inception-core/src/main/resources/db/inception-core.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update

#liquibase --changeLogFile=../inception-audit/src/main/resources/db/inception-audit.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
#liquibase --changeLogFile=../inception-bmi/src/main/resources/db/inception-bmi.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-codes/src/main/resources/db/inception-codes.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-config/src/main/resources/db/inception-config.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-error/src/main/resources/db/inception-error.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-mail/src/main/resources/db/inception-mail.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-messaging/src/main/resources/db/inception-messaging.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-reporting/src/main/resources/db/inception-reporting.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-scheduler/src/main/resources/db/inception-scheduler.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-security/src/main/resources/db/inception-security.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-sms/src/main/resources/db/inception-sms.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update

liquibase --changeLogFile=../inception-reference/src/main/resources/db/inception-reference.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-reference/src/main/resources/db/inception-reference-data.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update

liquibase --changeLogFile=../inception-party/src/main/resources/db/inception-party.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
liquibase --changeLogFile=../inception-party/src/main/resources/db/inception-party-data.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update

liquibase --changeLogFile=../demo/src/main/resources/db/demo.changelog.xml --username=demo --password=demo --url=jdbc:postgresql://localhost:5432/demo --classpath=postgresql-42.3.3.jar --driver=org.postgresql.Driver --liquibaseSchemaName=liquibase update
