#! /bin/bash
rm -rf target
export MAVEN_HOME=/home/wangwei/work/apache-maven-2.2.1
export PATH=$PATH:$MAVEN_HOME/bin
mvn  scm:bootstrap  -f pom-scm.xml
cd target/checkout/target/
cp ewcms-2.0.war ROOT.war

