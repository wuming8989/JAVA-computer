# Simple JaCoCo Coverage Analysis Tool
param(
    [string]$ReportPath = "target\site\jacoco\jacoco.xml"
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "JaCoCo Coverage Report Analysis" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if report file exists
if (-not (Test-Path $ReportPath)) {
    Write-Host "ERROR: Coverage report not found: $ReportPath" -ForegroundColor Red
    Write-Host "Please run tests first to generate coverage report" -ForegroundColor Yellow
    exit 1
}

try {
    # Parse XML report
    Write-Host "Analyzing coverage report..." -ForegroundColor Yellow
    [xml]$report = Get-Content $ReportPath
    
    # Extract overall coverage data
    $counters = $report.report.counter
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "Overall Coverage Statistics" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    
    foreach ($counter in $counters) {
        $type = $counter.type
        $missed = [int]$counter.missed
        $covered = [int]$counter.covered
        $total = $missed + $covered
        
        if ($total -gt 0) {
            $percentage = [math]::Round(($covered / $total) * 100, 2)
            $status = if ($percentage -ge 70) { "[GOOD]" } elseif ($percentage -ge 50) { "[WARN]" } else { "[POOR]" }
            
            $color = if ($percentage -ge 70) { "Green" } elseif ($percentage -ge 50) { "Yellow" } else { "Red" }
            Write-Host "$status $type Coverage: $percentage% ($covered/$total)" -ForegroundColor $color
        }
    }
    
    # Package level analysis
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Blue
    Write-Host "Package Level Coverage" -ForegroundColor Blue
    Write-Host "========================================" -ForegroundColor Blue
    
    foreach ($package in $report.report.package) {
        $packageName = $package.name
        if ($packageName -eq "") { $packageName = "(default)" }
        
        Write-Host ""
        Write-Host "Package: $packageName" -ForegroundColor Cyan
        
        $lineCounter = $package.counter | Where-Object { $_.type -eq "LINE" }
        if ($lineCounter) {
            $missed = [int]$lineCounter.missed
            $covered = [int]$lineCounter.covered
            $total = $missed + $covered
            
            if ($total -gt 0) {
                $percentage = [math]::Round(($covered / $total) * 100, 2)
                $color = if ($percentage -ge 70) { "Green" } elseif ($percentage -ge 50) { "Yellow" } else { "Red" }
                Write-Host "  Line Coverage: $percentage% ($covered/$total)" -ForegroundColor $color
            }
        }
        
        $branchCounter = $package.counter | Where-Object { $_.type -eq "BRANCH" }
        if ($branchCounter) {
            $missed = [int]$branchCounter.missed
            $covered = [int]$branchCounter.covered
            $total = $missed + $covered
            
            if ($total -gt 0) {
                $percentage = [math]::Round(($covered / $total) * 100, 2)
                $color = if ($percentage -ge 60) { "Green" } elseif ($percentage -ge 40) { "Yellow" } else { "Red" }
                Write-Host "  Branch Coverage: $percentage% ($covered/$total)" -ForegroundColor $color
            }
        }
    }
    
    # Class level analysis
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Blue
    Write-Host "Class Level Coverage" -ForegroundColor Blue
    Write-Host "========================================" -ForegroundColor Blue
    
    foreach ($package in $report.report.package) {
        foreach ($class in $package.class) {
            $className = $class.name -replace "/", "."
            
            Write-Host ""
            Write-Host "Class: $className" -ForegroundColor Cyan
            
            $lineCounter = $class.counter | Where-Object { $_.type -eq "LINE" }
            if ($lineCounter) {
                $missed = [int]$lineCounter.missed
                $covered = [int]$lineCounter.covered
                $total = $missed + $covered
                
                if ($total -gt 0) {
                    $percentage = [math]::Round(($covered / $total) * 100, 2)
                    $status = if ($percentage -ge 70) { "[GOOD]" } elseif ($percentage -ge 50) { "[WARN]" } else { "[POOR]" }
                    $color = if ($percentage -ge 70) { "Green" } elseif ($percentage -ge 50) { "Yellow" } else { "Red" }
                    Write-Host "  $status Line Coverage: $percentage% ($covered/$total)" -ForegroundColor $color
                }
            }
            
            $branchCounter = $class.counter | Where-Object { $_.type -eq "BRANCH" }
            if ($branchCounter) {
                $missed = [int]$branchCounter.missed
                $covered = [int]$branchCounter.covered
                $total = $missed + $covered
                
                if ($total -gt 0) {
                    $percentage = [math]::Round(($covered / $total) * 100, 2)
                    $status = if ($percentage -ge 60) { "[GOOD]" } elseif ($percentage -ge 40) { "[WARN]" } else { "[POOR]" }
                    $color = if ($percentage -ge 60) { "Green" } elseif ($percentage -ge 40) { "Yellow" } else { "Red" }
                    Write-Host "  $status Branch Coverage: $percentage% ($covered/$total)" -ForegroundColor $color
                }
            }
        }
    }
    
    # Summary
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Magenta
    Write-Host "Coverage Summary" -ForegroundColor Magenta
    Write-Host "========================================" -ForegroundColor Magenta
    
    $lineCounter = $counters | Where-Object { $_.type -eq "LINE" }
    $branchCounter = $counters | Where-Object { $_.type -eq "BRANCH" }
    $methodCounter = $counters | Where-Object { $_.type -eq "METHOD" }
    $classCounter = $counters | Where-Object { $_.type -eq "CLASS" }
    
    if ($lineCounter) {
        $lineCoverage = [math]::Round(([int]$lineCounter.covered / ([int]$lineCounter.missed + [int]$lineCounter.covered)) * 100, 2)
        Write-Host "Line Coverage: $lineCoverage%" -ForegroundColor White
    }
    
    if ($branchCounter) {
        $branchCoverage = [math]::Round(([int]$branchCounter.covered / ([int]$branchCounter.missed + [int]$branchCounter.covered)) * 100, 2)
        Write-Host "Branch Coverage: $branchCoverage%" -ForegroundColor White
    }
    
    if ($methodCounter) {
        $methodCoverage = [math]::Round(([int]$methodCounter.covered / ([int]$methodCounter.missed + [int]$methodCounter.covered)) * 100, 2)
        Write-Host "Method Coverage: $methodCoverage%" -ForegroundColor White
    }
    
    if ($classCounter) {
        $classCoverage = [math]::Round(([int]$classCounter.covered / ([int]$classCounter.missed + [int]$classCounter.covered)) * 100, 2)
        Write-Host "Class Coverage: $classCoverage%" -ForegroundColor White
    }
    
    # Assessment
    Write-Host ""
    Write-Host "Coverage Assessment:" -ForegroundColor Yellow
    
    if ($lineCounter) {
        $lineCoverage = [math]::Round(([int]$lineCounter.covered / ([int]$lineCounter.missed + [int]$lineCounter.covered)) * 100, 2)
        
        if ($lineCoverage -ge 80) {
            Write-Host "[EXCELLENT] Line coverage is $lineCoverage%" -ForegroundColor Green
        } elseif ($lineCoverage -ge 70) {
            Write-Host "[GOOD] Line coverage is $lineCoverage%, recommend improving to 80%+" -ForegroundColor Yellow
        } elseif ($lineCoverage -ge 50) {
            Write-Host "[FAIR] Line coverage is $lineCoverage%, need more test cases" -ForegroundColor Yellow
        } else {
            Write-Host "[POOR] Line coverage is only $lineCoverage%, urgent improvement needed" -ForegroundColor Red
        }
    }
    
    # Recommendations
    Write-Host ""
    Write-Host "Recommendations:" -ForegroundColor Cyan
    Write-Host "1. View HTML report for uncovered code details" -ForegroundColor White
    Write-Host "2. Focus on methods with low branch coverage" -ForegroundColor White
    Write-Host "3. Add tests for edge cases and error conditions" -ForegroundColor White
    Write-Host "4. Consider using Test-Driven Development (TDD)" -ForegroundColor White
    
    Write-Host ""
    Write-Host "Report Files:" -ForegroundColor Cyan
    Write-Host "- HTML Report: target\site\jacoco\index.html" -ForegroundColor White
    Write-Host "- Calculator Class: target\site\jacoco\com.calculator\Calculator.html" -ForegroundColor White
    Write-Host "- XML Report: target\site\jacoco\jacoco.xml" -ForegroundColor White
    Write-Host "- CSV Report: target\site\jacoco\jacoco.csv" -ForegroundColor White
    
} catch {
    Write-Host "ERROR: Failed to parse coverage report" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Analysis completed!" -ForegroundColor Green
