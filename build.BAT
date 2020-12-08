@echo off
set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_91"
set TOMCAT_HOME="C:\Program Files\Apache Software Foundation\Tomcat 8.5"
set ANT_HOME="D:\projects\ibankinguser\ibankinguser\WEB-INF"
set LIB="D:\projects\ibankinguser\ibankinguser\WEB-INF\lib"
%JAVA_HOME%\bin\java -classpath %LIB%\struts.jar;%LIB%\log4j1.2.8.jar;%LIB%\servlet.jar;%LIB%\classes12.jar;%ANT_HOME%\lib\ant.jar;%LIB%\tools.jar org.apache.tools.ant.Main -Dtomcat.home=%TOMCAT_HOME%  %1 %2 

