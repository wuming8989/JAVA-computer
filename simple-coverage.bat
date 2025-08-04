@echo off
echo ========================================
echo JaCoCo Coverage Analysis
echo ========================================

set MAVEN_PATH=E:\maven\mvn\bin\mvn.cmd

echo [INFO] Starting coverage analysis...
echo.

echo [STEP 1] Cleaning project...
"%MAVEN_PATH%" clean -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Clean failed
    pause
    exit /b 1
)
echo [OK] Clean completed

echo.
echo [STEP 2] Running tests with coverage...
"%MAVEN_PATH%" test jacoco:report -q
if %ERRORLEVEL% neq 0 (
    echo WARNING: Some tests may have failed
) else (
    echo [OK] Tests completed successfully
)

echo.
echo [STEP 3] Analyzing coverage results...
if exist "target\site\jacoco\index.html" (
    echo [OK] Coverage report generated: target\site\jacoco\index.html
) else (
    echo [ERROR] Coverage report not found
)

if exist "target\jacoco.exec" (
    echo [OK] Coverage data: target\jacoco.exec
) else (
    echo [ERROR] Coverage data not found
)

echo.
echo ========================================
echo Coverage Analysis Summary
echo ========================================

if exist "target\site\jacoco\jacoco.xml" (
    echo [INFO] Running PowerShell analysis...
    powershell -ExecutionPolicy Bypass -File simple-coverage-analysis.ps1
) else (
    echo [WARNING] XML report not found, skipping detailed analysis
)

echo.
echo ========================================
echo Next Steps
echo ========================================
echo 1. Open HTML report: target\site\jacoco\index.html
echo 2. Review Calculator class: target\site\jacoco\com.calculator\Calculator.html
echo 3. Add tests for uncovered code
echo 4. Run coverage check: mvn jacoco:check

echo.
set /p OPEN_REPORT="Open coverage report now? (y/n): "
if /i "%OPEN_REPORT%"=="y" (
    if exist "target\site\jacoco\index.html" (
        start "" "target\site\jacoco\index.html"
    )
)

echo.
echo Coverage analysis completed!
pause
