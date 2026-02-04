@echo off
setlocal EnableExtensions EnableDelayedExpansion

REM ==========================================================
REM killport.bat
REM Kills the process(es) listening on a given TCP port.
REM Usage:
REM   killport.bat 5555
REM   killport.bat 4444
REM Optional:
REM   killport.bat 5555 -y   (no prompt)
REM ==========================================================

if "%~1"=="" (
  echo [ERROR] Port number is missing.
  echo Usage: %~nx0 ^<port^> [-y]
  exit /b 1
)

set "PORT=%~1"
set "NOPROMPT=%~2"

REM Validate port is numeric
for /f "delims=0123456789" %%A in ("%PORT%") do (
  echo [ERROR] Invalid port: %PORT%
  exit /b 1
)

echo ==========================================================
echo Searching for processes listening on TCP port %PORT% ...
echo ==========================================================

set "FOUND=0"

REM Get lines that match :PORT and are LISTENING
for /f "tokens=1,2,3,4,5" %%A in ('netstat -ano ^| findstr /R /C:":%PORT% *.*LISTENING"') do (
  set "FOUND=1"
  set "PID=%%E"
  echo Found PID: !PID!  (%%A %%B %%C %%D)

  REM Show process details (best effort)
  for /f "tokens=1,*" %%P in ('tasklist /FI "PID eq !PID!" /NH') do (
    echo Process: %%P %%Q
  )

  if /I "%NOPROMPT%"=="-y" (
    echo Killing PID !PID! ...
    taskkill /PID !PID! /F >nul 2>&1
    if !errorlevel! equ 0 (
      echo [OK] Killed PID !PID!
    ) else (
      echo [WARN] Failed to kill PID !PID! (try running CMD as Administrator)
    )
  ) else (
    choice /M "Kill PID !PID! ?"
    if !errorlevel! equ 1 (
      echo Killing PID !PID! ...
      taskkill /PID !PID! /F >nul 2>&1
      if !errorlevel! equ 0 (
        echo [OK] Killed PID !PID!
      ) else (
        echo [WARN] Failed to kill PID !PID! (try running CMD as Administrator)
      )
    ) else (
      echo Skipped PID !PID!
    )
  )
)

if "%FOUND%"=="0" (
  echo [INFO] No LISTENING process found on port %PORT%.
  exit /b 0
)

echo ==========================================================
echo Done.
echo ==========================================================
exit /b 0