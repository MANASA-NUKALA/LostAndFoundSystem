@echo off
echo Compiling all Java files...

javac -cp .;lib\mysql-connector-j-9.3.0.jar client\*.java interfaces\*.java model\*.java server\*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Compilation successful!
pause
