
@echo off
setlocal EnableExtensions EnableDelayedExpansion

echo.
set /p "PORT=Enter the port number to kill (e.g., 4444): "

REM Validate input: digits only
echo %PORT%| findstr /R "^[0-9][0-9]*$" >nul
if errorlevel 1 (
    echo.
    echo [ERROR] Invalid port number: "%PORT%"
    pause
    exit /b 1
)

echo.
echo Checking port %PORT% ...
echo.

set "FOUND="

REM Capture PIDs reliably: take the last token on the matching line (PID is last column in netstat -aon)
for /f "usebackq tokens=* delims=" %%L in (`netstat -aon ^| findstr /R /C:":%PORT% "`) do (
    set "LINE=%%L"
    for %%P in (!LINE!) do set "LAST=%%P"
    REM LAST token becomes PID (netstat last column)
    echo Found line: !LINE!
    echo PID: !LAST!
    set "FOUND=1"
    call :KILLPID !LAST!
    echo.
)

if not defined FOUND (
    echo [INFO] No process found using port %PORT%.
    echo.
    pause
    exit /b 0
)

echo Done.
pause
exit /b 0


:KILLPID
set "PID=%~1"

REM PID should be numeric
echo %PID%| findstr /R "^[0-9][0-9]*$" >nul
if errorlevel 1 (
    echo [WARN] Skipping invalid PID: %PID%
    goto :eof
)

echo Attempting to kill PID %PID% ...
taskkill /PID %PID% /F /T >nul 2>&1

if errorlevel 1 (
    echo [ERROR] Failed to kill PID %PID%. Try running as Administrator.
) else (
    echo [OK] Killed PID %PID%.
)
goto :eof
