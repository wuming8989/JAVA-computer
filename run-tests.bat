@echo off
echo ========================================
echo Java Calculator Unit Test Runner
echo ========================================
echo.

:: Set Maven path
set MAVEN_PATH=E:\maven\mvn\bin\mvn.cmd

:: Check if Maven exists
if not exist "%MAVEN_PATH%" (
    echo ERROR: Maven not found at %MAVEN_PATH%
    echo Please update the MAVEN_PATH variable in this script
    pause
    exit /b 1
)

echo [INFO] Starting unit tests with JaCoCo coverage...
echo.

:: 清理之前的构建
echo [STEP 1] Cleaning previous build...
"%MAVEN_PATH%" clean
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven clean failed
    pause
    exit /b 1
)

:: 编译项目
echo.
echo [STEP 2] Compiling project...
"%MAVEN_PATH%" compile
if %ERRORLEVEL% neq 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

:: 运行测试并生成覆盖率报告
echo.
echo [STEP 3] Running tests with coverage analysis...
"%MAVEN_PATH%" test
if %ERRORLEVEL% neq 0 (
    echo ERROR: Tests failed
    pause
    exit /b 1
)

:: 生成详细的覆盖率报告
echo.
echo [STEP 4] Generating detailed coverage reports...
"%MAVEN_PATH%" jacoco:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Coverage report generation failed
    pause
    exit /b 1
)

echo.
echo ========================================
echo Test Results Summary
echo ========================================

:: 检查测试报告文件
if exist "target\surefire-reports\TEST-*.xml" (
    echo [✓] Unit test reports generated in: target\surefire-reports\
) else (
    echo [✗] Unit test reports not found
)

:: 检查覆盖率报告文件
if exist "target\site\jacoco\index.html" (
    echo [✓] Coverage report generated in: target\site\jacoco\index.html
    echo [INFO] Open target\site\jacoco\index.html in your browser to view detailed coverage
) else (
    echo [✗] Coverage report not found
)

:: 检查JaCoCo执行数据
if exist "target\jacoco.exec" (
    echo [✓] JaCoCo execution data: target\jacoco.exec
) else (
    echo [✗] JaCoCo execution data not found
)

echo.
echo ========================================
echo Quick Coverage Summary
echo ========================================

:: 尝试从控制台输出中提取覆盖率信息
findstr /C:"Analyzed bundle" target\*.log 2>nul
if %ERRORLEVEL% equ 0 (
    echo Coverage analysis completed successfully
) else (
    echo Run completed - check target\site\jacoco\index.html for detailed coverage
)

echo.
echo [INFO] Test execution completed!
echo [INFO] To view detailed coverage report, open: target\site\jacoco\index.html
echo.

:: 询问是否打开覆盖率报告
set /p OPEN_REPORT="Do you want to open the coverage report now? (y/n): "
if /i "%OPEN_REPORT%"=="y" (
    if exist "target\site\jacoco\index.html" (
        start "" "target\site\jacoco\index.html"
    ) else (
        echo Coverage report file not found!
    )
)

pause
