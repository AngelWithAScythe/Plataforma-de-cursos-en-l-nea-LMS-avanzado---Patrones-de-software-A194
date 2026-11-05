@echo off
start javaw -jar Proyecto-LMS-PDS-1.0.jar
timeout /t 15 /nobreak > nul
start http://localhost:6767
exit