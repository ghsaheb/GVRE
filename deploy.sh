#!/bin/bash

TOMCAT_WEBAPPS=${CATALINA_HOME}/webapps
TOMCAT_START=${CATALINA_HOME}/bin/startup.sh
WAR_FILE=GVRE.war

mvn package
rm "${TOMCAT_WEBAPPS}/${WAR_FILE}"
cp "./target/${WAR_FILE}" "${TOMCAT_WEBAPPS}"

"${TOMCAT_START}"