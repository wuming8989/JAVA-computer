# Java Calculator 单元测试配置说明

## 测试框架配置

### JUnit 5
- 版本：5.8.2
- 用于编写和执行单元测试
- 支持注解驱动的测试

### JaCoCo 代码覆盖率
- 版本：0.8.7
- 用于监测代码覆盖率
- 生成详细的HTML报告

## 测试脚本使用说明

### Windows 环境
```bash
# 运行测试脚本
run-tests.bat

# 或者直接使用Maven命令
E:\maven\mvn\bin\mvn.cmd clean test jacoco:report
```

### Linux/Mac 环境
```bash
# 给脚本执行权限
chmod +x run-tests.sh

# 运行测试脚本
./run-tests.sh

# 或者直接使用Maven命令
mvn clean test jacoco:report
```

## 测试覆盖范围

### 主要测试类
- `CalculatorTest.java` - 计算器核心功能测试

### 测试用例覆盖
1. **基本运算测试**
   - 加法运算
   - 减法运算
   - 乘法运算
   - 除法运算
   - 模运算

2. **高级功能测试**
   - 表达式解析
   - 运算符优先级
   - 内存功能（MS、MR、MC、M+）
   - 错误处理

3. **边界条件测试**
   - 除零处理
   - 模零处理
   - 空表达式
   - 大数运算
   - 小数运算

4. **UI交互测试**
   - 输入功能
   - 显示功能
   - 按钮响应

## 覆盖率报告

### 报告位置
- HTML报告：`target/site/jacoco/index.html`
- XML报告：`target/site/jacoco/jacoco.xml`
- CSV报告：`target/site/jacoco/jacoco.csv`
- 执行数据：`target/jacoco.exec`

### 覆盖率指标
- **行覆盖率**：代码行的执行覆盖率
- **分支覆盖率**：条件分支的覆盖率
- **方法覆盖率**：方法的调用覆盖率
- **类覆盖率**：类的使用覆盖率

## 测试命令详解

### 完整测试流程
```bash
# 1. 清理项目
mvn clean

# 2. 编译项目
mvn compile

# 3. 运行测试
mvn test

# 4. 生成覆盖率报告
mvn jacoco:report

# 5. 一键执行所有步骤
mvn clean test jacoco:report
```

### 跳过测试编译
```bash
mvn package -DskipTests
```

### 只运行特定测试
```bash
mvn test -Dtest=CalculatorTest
mvn test -Dtest=CalculatorTest#testCalculateAddition
```

## 持续集成配置

### GitHub Actions 示例
```yaml
name: Test with Coverage
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 8
      uses: actions/setup-java@v2
      with:
        java-version: '8'
        distribution: 'adopt'
    - name: Run tests with coverage
      run: mvn clean test jacoco:report
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v1
```

## 故障排除

### 常见问题
1. **Maven路径问题**
   - 确保Maven正确安装
   - 检查环境变量配置

2. **Java版本问题**
   - 确保使用Java 8
   - 检查JAVA_HOME设置

3. **测试失败**
   - 检查测试日志
   - 验证测试数据

4. **覆盖率报告未生成**
   - 确保JaCoCo插件配置正确
   - 检查target目录权限

### 调试命令
```bash
# 详细输出
mvn test -X

# 跳过测试但编译测试代码
mvn test-compile

# 查看依赖树
mvn dependency:tree
```
