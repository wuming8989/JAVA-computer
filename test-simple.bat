@echo off
echo ========================================
echo Java Calculator Test Runner
echo ========================================
echo.

set MAVEN_PATH=E:\maven\mvn\bin\mvn.cmd

echo [STEP 1] Running tests with coverage...
"%MAVEN_PATH%" clean test jacoco:report

if %ERRORLEVEL% equ 0 (
    echo.
    echo ========================================
    echo Test Results
    echo ========================================
    echo [SUCCESS] All tests passed!
    echo [INFO] Coverage report: target\site\jacoco\index.html
    echo [INFO] Test reports: target\surefire-reports\
    echo.
    
    set /p OPEN_REPORT="Open coverage report? (y/n): "
    if /i "%OPEN_REPORT%"=="y" (
        start "" "target\site\jacoco\index.html"
    )
) else (
    echo.
    echo [ERROR] Tests failed! Check the output above.
)

pause
