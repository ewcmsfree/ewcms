#! /bin/bash

CHECKOUT_DIR=target/checkout

rm -rf $CHECKOUT_DIR

MAVEN_HOME=/home/wangwei/work/apache-maven-2.2.1
PATH=$PATH:$MAVEN_HOME/bin
export MAVEN_HOME PATH

mvn  scm:bootstrap  -f pom-scm.xml

cd $CHECKOUT_DIR/target/
cp ewcms-*.war ROOT.war

