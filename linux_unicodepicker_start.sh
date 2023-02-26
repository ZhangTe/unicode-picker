#!/bin/sh
export JAVA_HOME={Path to your JDK like home/username/Public/jdk1.8.0_202/}
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
java -jar {path to your downloaded jar like ~/Downloads/codechart-540p-jre-1.8.jar}