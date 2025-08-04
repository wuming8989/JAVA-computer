@echo off
echo ========================================
echo 快速覆盖率检查
echo ========================================

:: Set Maven path
set MAVEN_PATH=E:\maven\mvn\bin\mvn.cmd

:: 一键执行覆盖率检查
echo [INFO] 执行快速覆盖率检查...
"%MAVEN_PATH%" clean test jacoco:report -q

if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] 覆盖率检查完成!
    echo [INFO] 报告位置: target\site\jacoco\index.html
    
    :: 自动打开报告
    if exist "target\site\jacoco\index.html" (
        start "" "target\site\jacoco\index.html"
    )
) else (
    echo [ERROR] 覆盖率检查失败!
    echo [INFO] 请查看详细日志或使用 run-coverage-analysis.bat
)

pause
