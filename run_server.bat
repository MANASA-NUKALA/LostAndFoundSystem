@echo off
echo Starting LostAndFoundServer...
java -cp .;lib\mysql-connector-j-9.3.0.jar server.LostAndFoundServer
pause