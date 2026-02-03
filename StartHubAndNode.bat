
@echo off
setlocal EnableExtensions

REM ===== Project root = folder where this BAT is located =====
set "ROOT=%~dp0"

REM ===== Your folder name has a space: 'Supporting Files' =====
set "JAR=%ROOT%Supporting Files\selenium-server-4.40.0.jar"

REM ===== Sanity check =====
if not exist "%JAR%" (
  echo ERROR: Cannot find the jar file here:
  echo "%JAR%"
  echo.
  echo Fix: Check folder name and jar file name.
  pause
  exit /b 1
)

echo Using jar: "%JAR%"
echo.

REM ===== Start HUB in new window =====
start "Selenium HUB" cmd /k "java -jar "%JAR%" hub"

REM ===== Give hub time to start =====
timeout /t 3 /nobreak >nul

REM ===== Start NODE in new window =====
start "Selenium NODE" cmd /k "java -jar "%JAR%" node --hub http://localhost:4444"

echo.
echo Hub:  http://localhost:4444
echo UI:   http://localhost:4444/ui
echo.
endlocal
