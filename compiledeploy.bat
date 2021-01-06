cd "C:\apache-tomcat-9.0.41-windows-x64\apache-tomcat-9.0.41\bin\"
start cmd /c catalina.bat stop
pause

cd C:\Users\rharidas\Documents\NestThermostatReport\mynest\mynest\
start cmd /c compiledeploy.bat
pause
copy "target\mynest-0.0.1-SNAPSHOT.war"   "C:\apache-tomcat-9.0.41-windows-x64\apache-tomcat-9.0.41\webapps\mynest.war"
pause

cd ..\..\mynestreport
start cmd /c compiledeploy.bat
pause
copy "target\mynestreport.war"   "C:\apache-tomcat-9.0.41-windows-x64\apache-tomcat-9.0.41\webapps\mynestreport.war"
pause

cd ..\feedstore
start cmd /c compiledeploy.bat
pause
copy "target\feedstore-0.0.1-SNAPSHOT.war"   "C:\apache-tomcat-9.0.41-windows-x64\apache-tomcat-9.0.41\webapps\feedstore.war"
pause

cd ..\streamprocessor
start cmd /c compiledeploy.bat
pause
copy "target\streamprocessor-0.0.1-SNAPSHOT.war"   "C:\apache-tomcat-9.0.41-windows-x64\apache-tomcat-9.0.41\webapps\streamprocessor.war"
pause

cd "C:\apache-tomcat-9.0.41-windows-x64\apache-tomcat-9.0.41\bin\"
start cmd /c catalina.bat start
pause

cd C:\Users\rharidas\Documents\NestThermostatReport