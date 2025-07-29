#!/bin/bash

echo "========================================"
echo "Java Calculator Unit Test Runner"
echo "========================================"
echo

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}ERROR: Maven is not installed or not in PATH${NC}"
    echo "Please install Maven and ensure it's in your PATH"
    exit 1
fi

echo -e "${BLUE}[INFO] Starting unit tests with JaCoCo coverage...${NC}"
echo

# 清理之前的构建
echo -e "${YELLOW}[STEP 1] Cleaning previous build...${NC}"
mvn clean
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Maven clean failed${NC}"
    exit 1
fi

# 编译项目
echo
echo -e "${YELLOW}[STEP 2] Compiling project...${NC}"
mvn compile
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Compilation failed${NC}"
    exit 1
fi

# 运行测试并生成覆盖率报告
echo
echo -e "${YELLOW}[STEP 3] Running tests with coverage analysis...${NC}"
mvn test
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Tests failed${NC}"
    exit 1
fi

# 生成详细的覆盖率报告
echo
echo -e "${YELLOW}[STEP 4] Generating detailed coverage reports...${NC}"
mvn jacoco:report
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Coverage report generation failed${NC}"
    exit 1
fi

echo
echo "========================================"
echo "Test Results Summary"
echo "========================================"

# 检查测试报告文件
if ls target/surefire-reports/TEST-*.xml 1> /dev/null 2>&1; then
    echo -e "${GREEN}[✓] Unit test reports generated in: target/surefire-reports/${NC}"
else
    echo -e "${RED}[✗] Unit test reports not found${NC}"
fi

# 检查覆盖率报告文件
if [ -f "target/site/jacoco/index.html" ]; then
    echo -e "${GREEN}[✓] Coverage report generated in: target/site/jacoco/index.html${NC}"
    echo -e "${BLUE}[INFO] Open target/site/jacoco/index.html in your browser to view detailed coverage${NC}"
else
    echo -e "${RED}[✗] Coverage report not found${NC}"
fi

# 检查JaCoCo执行数据
if [ -f "target/jacoco.exec" ]; then
    echo -e "${GREEN}[✓] JaCoCo execution data: target/jacoco.exec${NC}"
else
    echo -e "${RED}[✗] JaCoCo execution data not found${NC}"
fi

echo
echo "========================================"
echo "Quick Coverage Summary"
echo "========================================"

# 尝试提取覆盖率信息
if [ -f "target/site/jacoco/index.html" ]; then
    echo -e "${GREEN}Coverage analysis completed successfully${NC}"
    echo -e "${BLUE}Run completed - check target/site/jacoco/index.html for detailed coverage${NC}"
fi

echo
echo -e "${GREEN}[INFO] Test execution completed!${NC}"
echo -e "${BLUE}[INFO] To view detailed coverage report, open: target/site/jacoco/index.html${NC}"
echo

# 询问是否打开覆盖率报告
read -p "Do you want to open the coverage report now? (y/n): " OPEN_REPORT
if [[ "$OPEN_REPORT" =~ ^[Yy]$ ]]; then
    if [ -f "target/site/jacoco/index.html" ]; then
        # 尝试不同的浏览器
        if command -v xdg-open &> /dev/null; then
            xdg-open "target/site/jacoco/index.html"
        elif command -v open &> /dev/null; then
            open "target/site/jacoco/index.html"
        else
            echo "Please manually open target/site/jacoco/index.html in your browser"
        fi
    else
        echo -e "${RED}Coverage report file not found!${NC}"
    fi
fi
