@echo off
setlocal enabledelayedexpansion

echo ========================================
echo JaCoCo 代码覆盖率分析工具
echo ========================================
echo.

:: 设置颜色代码
set "GREEN=[92m"
set "RED=[91m"
set "YELLOW=[93m"
set "BLUE=[94m"
set "RESET=[0m"

:: Set Maven path
set MAVEN_PATH=E:\maven\mvn\bin\mvn.cmd

:: Check if Maven exists
if not exist "%MAVEN_PATH%" (
    echo %RED%ERROR: Maven not found at %MAVEN_PATH%%RESET%
    echo Please update the MAVEN_PATH variable in this script
    pause
    exit /b 1
)

echo %BLUE%[INFO] 开始JaCoCo代码覆盖率分析...%RESET%
echo.

:: 记录开始时间
set start_time=%time%

:: 步骤1: 清理之前的构建
echo %YELLOW%[STEP 1] 清理之前的构建...%RESET%
"%MAVEN_PATH%" clean -q
if %ERRORLEVEL% neq 0 (
    echo %RED%ERROR: Maven clean failed%RESET%
    pause
    exit /b 1
)
echo %GREEN%✓ 清理完成%RESET%

:: 步骤2: 准备JaCoCo代理
echo.
echo %YELLOW%[STEP 2] 准备JaCoCo代理...%RESET%
"%MAVEN_PATH%" jacoco:prepare-agent -q
if %ERRORLEVEL% neq 0 (
    echo %RED%ERROR: JaCoCo agent preparation failed%RESET%
    pause
    exit /b 1
)
echo %GREEN%✓ JaCoCo代理准备完成%RESET%

:: 步骤3: 编译项目
echo.
echo %YELLOW%[STEP 3] 编译项目...%RESET%
"%MAVEN_PATH%" compile -q
if %ERRORLEVEL% neq 0 (
    echo %RED%ERROR: Compilation failed%RESET%
    pause
    exit /b 1
)
echo %GREEN%✓ 编译完成%RESET%

:: 步骤4: 运行测试并收集覆盖率数据
echo.
echo %YELLOW%[STEP 4] 运行测试并收集覆盖率数据...%RESET%
"%MAVEN_PATH%" test -q
set test_result=%ERRORLEVEL%
if %test_result% neq 0 (
    echo %RED%WARNING: Some tests failed (exit code: %test_result%)%RESET%
    echo %YELLOW%继续生成覆盖率报告...%RESET%
) else (
    echo %GREEN%✓ 所有测试通过%RESET%
)

:: 步骤5: 生成覆盖率报告
echo.
echo %YELLOW%[STEP 5] 生成覆盖率报告...%RESET%
"%MAVEN_PATH%" jacoco:report -q
if %ERRORLEVEL% neq 0 (
    echo %RED%ERROR: Coverage report generation failed%RESET%
    pause
    exit /b 1
)
echo %GREEN%✓ 覆盖率报告生成完成%RESET%

:: 步骤6: 检查覆盖率阈值
echo.
echo %YELLOW%[STEP 6] 检查覆盖率阈值...%RESET%
"%MAVEN_PATH%" jacoco:check -q
set check_result=%ERRORLEVEL%
if %check_result% neq 0 (
    echo %RED%WARNING: 覆盖率未达到设定阈值%RESET%
) else (
    echo %GREEN%✓ 覆盖率达到设定阈值%RESET%
)

:: 记录结束时间
set end_time=%time%

echo.
echo ========================================
echo 覆盖率分析结果汇总
echo ========================================

:: 检查各种报告文件
echo.
echo %BLUE%[文件检查]%RESET%

if exist "target\jacoco.exec" (
    echo %GREEN%✓%RESET% JaCoCo执行数据: target\jacoco.exec
    for %%A in (target\jacoco.exec) do echo   文件大小: %%~zA 字节
) else (
    echo %RED%✗%RESET% JaCoCo执行数据未找到
)

if exist "target\site\jacoco\index.html" (
    echo %GREEN%✓%RESET% HTML覆盖率报告: target\site\jacoco\index.html
) else (
    echo %RED%✗%RESET% HTML覆盖率报告未找到
)

if exist "target\site\jacoco\jacoco.xml" (
    echo %GREEN%✓%RESET% XML覆盖率报告: target\site\jacoco\jacoco.xml
) else (
    echo %RED%✗%RESET% XML覆盖率报告未找到
)

if exist "target\site\jacoco\jacoco.csv" (
    echo %GREEN%✓%RESET% CSV覆盖率报告: target\site\jacoco\jacoco.csv
) else (
    echo %RED%✗%RESET% CSV覆盖率报告未找到
)

if exist "target\surefire-reports\TEST-*.xml" (
    echo %GREEN%✓%RESET% 测试报告: target\surefire-reports\
    for /f %%i in ('dir /b target\surefire-reports\TEST-*.xml 2^>nul ^| find /c /v ""') do echo   测试文件数量: %%i
) else (
    echo %RED%✗%RESET% 测试报告未找到
)

:: 尝试提取覆盖率摘要信息
echo.
echo %BLUE%[覆盖率摘要]%RESET%

if exist "target\site\jacoco\index.html" (
    echo 正在分析覆盖率数据...
    
    :: 使用PowerShell解析HTML文件获取覆盖率信息
    powershell -Command "& {
        try {
            $content = Get-Content 'target\site\jacoco\index.html' -Raw
            if ($content -match 'Total.*?(\d+)%%.*?(\d+)%%.*?(\d+)%%.*?(\d+)%%.*?(\d+)%%') {
                Write-Host '  指令覆盖率: '$matches[1]'%%'
                Write-Host '  分支覆盖率: '$matches[2]'%%'
                Write-Host '  行覆盖率: '$matches[3]'%%'
                Write-Host '  方法覆盖率: '$matches[4]'%%'
                Write-Host '  类覆盖率: '$matches[5]'%%'
            } else {
                Write-Host '  无法解析覆盖率数据，请查看HTML报告'
            }
        } catch {
            Write-Host '  解析覆盖率数据时出错'
        }
    }"
) else (
    echo %YELLOW%请查看 target\site\jacoco\index.html 获取详细覆盖率信息%RESET%
)

:: 显示测试执行摘要
echo.
echo %BLUE%[测试执行摘要]%RESET%

if exist "target\surefire-reports" (
    for /f "tokens=*" %%a in ('dir /b target\surefire-reports\*.txt 2^>nul') do (
        echo 正在分析测试结果: %%a
        findstr /C:"Tests run:" "target\surefire-reports\%%a" 2>nul
    )
)

:: 显示执行时间
echo.
echo %BLUE%[执行时间]%RESET%
echo 开始时间: %start_time%
echo 结束时间: %end_time%

:: 显示后续操作建议
echo.
echo ========================================
echo 后续操作建议
echo ========================================

if %test_result% neq 0 (
    echo %RED%⚠ 测试失败处理:%RESET%
    echo   1. 查看测试日志: target\surefire-reports\
    echo   2. 修复失败的测试用例
    echo   3. 重新运行覆盖率分析
    echo.
)

if %check_result% neq 0 (
    echo %YELLOW%⚠ 覆盖率改进建议:%RESET%
    echo   1. 查看未覆盖代码: target\site\jacoco\com.calculator\Calculator.html
    echo   2. 添加针对性测试用例
    echo   3. 考虑调整覆盖率阈值配置
    echo.
)

echo %GREEN%✓ 推荐操作:%RESET%
echo   1. 打开HTML报告查看详细覆盖率: target\site\jacoco\index.html
echo   2. 分析未覆盖的代码路径
echo   3. 编写额外的测试用例提高覆盖率
echo   4. 将覆盖率报告集成到CI/CD流程

echo.
echo %BLUE%[快速链接]%RESET%
echo   主报告: target\site\jacoco\index.html
echo   Calculator类: target\site\jacoco\com.calculator\Calculator.html
echo   测试报告: target\surefire-reports\

:: 询问是否打开报告
echo.
set /p OPEN_REPORT="是否现在打开覆盖率报告? (y/n): "
if /i "%OPEN_REPORT%"=="y" (
    if exist "target\site\jacoco\index.html" (
        echo %GREEN%正在打开覆盖率报告...%RESET%
        start "" "target\site\jacoco\index.html"
    ) else (
        echo %RED%覆盖率报告文件未找到!%RESET%
    )
)

echo.
echo %GREEN%覆盖率分析完成!%RESET%
pause
