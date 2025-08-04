# JaCoCo 覆盖率报告分析工具
# PowerShell脚本用于解析和分析JaCoCo覆盖率报告

param(
    [string]$ReportPath = "target\site\jacoco\jacoco.xml",
    [string]$HtmlPath = "target\site\jacoco\index.html",
    [switch]$Detailed,
    [switch]$Export
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "JaCoCo 覆盖率报告分析工具" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查报告文件是否存在
if (-not (Test-Path $ReportPath)) {
    Write-Host "错误: 找不到覆盖率报告文件: $ReportPath" -ForegroundColor Red
    Write-Host "请先运行测试生成覆盖率报告" -ForegroundColor Yellow
    exit 1
}

try {
    # 解析XML报告
    Write-Host "正在分析覆盖率报告..." -ForegroundColor Yellow
    [xml]$report = Get-Content $ReportPath
    
    # 提取总体覆盖率数据
    $counters = $report.report.counter
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "总体覆盖率统计" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    
    foreach ($counter in $counters) {
        $type = $counter.type
        $missed = [int]$counter.missed
        $covered = [int]$counter.covered
        $total = $missed + $covered
        
        if ($total -gt 0) {
            $percentage = [math]::Round(($covered / $total) * 100, 2)
            $status = if ($percentage -ge 70) { "[OK]" } elseif ($percentage -ge 50) { "[WARN]" } else { "[FAIL]" }
            
            Write-Host "$status ${type} Coverage: $percentage% ($covered/$total)" -ForegroundColor $(
                if ($percentage -ge 70) { "Green" } 
                elseif ($percentage -ge 50) { "Yellow" } 
                else { "Red" }
            )
        }
    }
    
    # 分析包级别覆盖率
    if ($Detailed) {
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Blue
        Write-Host "包级别覆盖率详情" -ForegroundColor Blue
        Write-Host "========================================" -ForegroundColor Blue
        
        foreach ($package in $report.report.package) {
            $packageName = $package.name
            if ($packageName -eq "") { $packageName = "(default)" }
            
            Write-Host ""
            Write-Host "包: $packageName" -ForegroundColor Cyan
            
            foreach ($counter in $package.counter) {
                $type = $counter.type
                $missed = [int]$counter.missed
                $covered = [int]$counter.covered
                $total = $missed + $covered
                
                if ($total -gt 0) {
                    $percentage = [math]::Round(($covered / $total) * 100, 2)
                    Write-Host "  $type: $percentage% ($covered/$total)"
                }
            }
        }
        
        # 分析类级别覆盖率
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Blue
        Write-Host "类级别覆盖率详情" -ForegroundColor Blue
        Write-Host "========================================" -ForegroundColor Blue
        
        foreach ($package in $report.report.package) {
            foreach ($class in $package.class) {
                $className = $class.name -replace "/", "."
                
                Write-Host ""
                Write-Host "类: $className" -ForegroundColor Cyan
                
                foreach ($counter in $class.counter) {
                    $type = $counter.type
                    $missed = [int]$counter.missed
                    $covered = [int]$counter.covered
                    $total = $missed + $covered
                    
                    if ($total -gt 0) {
                        $percentage = [math]::Round(($covered / $total) * 100, 2)
                        $status = if ($percentage -ge 70) { "✓" } elseif ($percentage -ge 50) { "⚠" } else { "✗" }
                        Write-Host "  $status $type: $percentage% ($covered/$total)" -ForegroundColor $(
                            if ($percentage -ge 70) { "Green" } 
                            elseif ($percentage -ge 50) { "Yellow" } 
                            else { "Red" }
                        )
                    }
                }
                
                # 分析方法级别覆盖率
                if ($class.method) {
                    Write-Host "  方法覆盖率:" -ForegroundColor Gray
                    foreach ($method in $class.method) {
                        $methodName = $method.name
                        $lineCounter = $method.counter | Where-Object { $_.type -eq "LINE" }
                        
                        if ($lineCounter) {
                            $missed = [int]$lineCounter.missed
                            $covered = [int]$lineCounter.covered
                            $total = $missed + $covered
                            
                            if ($total -gt 0) {
                                $percentage = [math]::Round(($covered / $total) * 100, 2)
                                $status = if ($percentage -eq 100) { "✓" } elseif ($percentage -gt 0) { "⚠" } else { "✗" }
                                Write-Host "    $status $methodName: $percentage%" -ForegroundColor $(
                                    if ($percentage -eq 100) { "Green" } 
                                    elseif ($percentage -gt 0) { "Yellow" } 
                                    else { "Red" }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    # 生成覆盖率摘要
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Magenta
    Write-Host "覆盖率分析摘要" -ForegroundColor Magenta
    Write-Host "========================================" -ForegroundColor Magenta
    
    $lineCounter = $counters | Where-Object { $_.type -eq "LINE" }
    $branchCounter = $counters | Where-Object { $_.type -eq "BRANCH" }
    $methodCounter = $counters | Where-Object { $_.type -eq "METHOD" }
    $classCounter = $counters | Where-Object { $_.type -eq "CLASS" }
    
    if ($lineCounter) {
        $lineCoverage = [math]::Round(([int]$lineCounter.covered / ([int]$lineCounter.missed + [int]$lineCounter.covered)) * 100, 2)
        Write-Host "• 行覆盖率: $lineCoverage%" -ForegroundColor White
    }
    
    if ($branchCounter) {
        $branchCoverage = [math]::Round(([int]$branchCounter.covered / ([int]$branchCounter.missed + [int]$branchCounter.covered)) * 100, 2)
        Write-Host "• 分支覆盖率: $branchCoverage%" -ForegroundColor White
    }
    
    if ($methodCounter) {
        $methodCoverage = [math]::Round(([int]$methodCounter.covered / ([int]$methodCounter.missed + [int]$methodCounter.covered)) * 100, 2)
        Write-Host "• 方法覆盖率: $methodCoverage%" -ForegroundColor White
    }
    
    if ($classCounter) {
        $classCoverage = [math]::Round(([int]$classCounter.covered / ([int]$classCounter.missed + [int]$classCounter.covered)) * 100, 2)
        Write-Host "• 类覆盖率: $classCoverage%" -ForegroundColor White
    }
    
    # 覆盖率评估
    Write-Host ""
    Write-Host "覆盖率评估:" -ForegroundColor Yellow
    
    if ($lineCounter) {
        $lineCoverage = [math]::Round(([int]$lineCounter.covered / ([int]$lineCounter.missed + [int]$lineCounter.covered)) * 100, 2)
        
        if ($lineCoverage -ge 80) {
            Write-Host "✓ 优秀: 行覆盖率达到 $lineCoverage%" -ForegroundColor Green
        } elseif ($lineCoverage -ge 70) {
            Write-Host "⚠ 良好: 行覆盖率为 $lineCoverage%，建议提升至80%以上" -ForegroundColor Yellow
        } elseif ($lineCoverage -ge 50) {
            Write-Host "⚠ 一般: 行覆盖率为 $lineCoverage%，需要增加测试用例" -ForegroundColor Yellow
        } else {
            Write-Host "✗ 不足: 行覆盖率仅为 $lineCoverage%，急需改进" -ForegroundColor Red
        }
    }
    
    # 改进建议
    Write-Host ""
    Write-Host "改进建议:" -ForegroundColor Cyan
    Write-Host "1. 查看HTML报告了解未覆盖代码: $HtmlPath" -ForegroundColor White
    Write-Host "2. 重点关注分支覆盖率较低的方法" -ForegroundColor White
    Write-Host "3. 为边界条件和异常情况添加测试" -ForegroundColor White
    Write-Host "4. 考虑使用测试驱动开发(TDD)方法" -ForegroundColor White
    
    # 导出报告
    if ($Export) {
        $exportPath = "coverage-analysis-$(Get-Date -Format 'yyyyMMdd-HHmmss').txt"
        
        $exportContent = @"
JaCoCo 覆盖率分析报告
生成时间: $(Get-Date)
报告文件: $ReportPath

总体覆盖率统计:
"@
        
        foreach ($counter in $counters) {
            $type = $counter.type
            $missed = [int]$counter.missed
            $covered = [int]$counter.covered
            $total = $missed + $covered
            
            if ($total -gt 0) {
                $percentage = [math]::Round(($covered / $total) * 100, 2)
                $exportContent += "`n$type 覆盖率: $percentage% ($covered/$total)"
            }
        }
        
        $exportContent | Out-File -FilePath $exportPath -Encoding UTF8
        Write-Host ""
        Write-Host "分析报告已导出到: $exportPath" -ForegroundColor Green
    }
    
} catch {
    Write-Host "错误: 解析覆盖率报告时发生异常" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "分析完成!" -ForegroundColor Green
