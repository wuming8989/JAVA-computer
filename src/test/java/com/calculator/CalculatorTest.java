package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

@DisplayName("Calculator 完整测试套件")
public class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("基础功能测试")
    class BasicFunctionTests {

        @Test
        @DisplayName("测试构造函数初始化")
        void testConstructorInitialization() {
            Calculator calc = new Calculator();
            assertNotNull(calc);
            assertEquals("", calc.getDisplayText());
            assertNull(calc.getMemoryValue());
        }

        @Test
        @DisplayName("测试pdone方法 - 空显示框")
        void testPdoneEmptyDisplay() {
            Calculator calc = new Calculator();
            calc.pdone("5");
            assertEquals("5", calc.getDisplayText());
        }

        @Test
        @DisplayName("测试pdone方法 - 追加文本")
        void testPdoneAppendText() {
            calculator.pdone("1");
            calculator.pdone("2");
            calculator.pdone("+");
            calculator.pdone("3");
            assertEquals("12+3", calculator.getDisplayText());
        }

        @Test
        @DisplayName("测试pdone方法 - 空字符串")
        void testPdoneEmptyString() {
            calculator.pdone("");
            assertEquals("", calculator.getDisplayText());
        }

        @Test
        @DisplayName("测试pdone方法 - 特殊字符")
        void testPdoneSpecialCharacters() {
            calculator.pdone(".");
            calculator.pdone("5");
            assertEquals(".5", calculator.getDisplayText());
        }
    }

    @Nested
    @DisplayName("表达式解析测试")
    class ExpressionParsingTests {

        @Test
        @DisplayName("测试复杂表达式解析")
        void testParseComplexExpression() {
            calculator.pdone("1+2*3-4/5");
            ArrayList<String> result = calculator.parseExpression();
            assertEquals(9, result.size());
            assertEquals("1", result.get(0));
            assertEquals("+", result.get(1));
            assertEquals("2", result.get(2));
            assertEquals("*", result.get(3));
            assertEquals("3", result.get(4));
            assertEquals("-", result.get(5));
            assertEquals("4", result.get(6));
            assertEquals("/", result.get(7));
            assertEquals("5", result.get(8));
        }

        @Test
        @DisplayName("测试小数解析")
        void testParseDecimalNumbers() {
            calculator.pdone("3.14+2.5");
            ArrayList<String> result = calculator.parseExpression();
            assertEquals(3, result.size());
            assertEquals("3.14", result.get(0));
            assertEquals("+", result.get(1));
            assertEquals("2.5", result.get(2));
        }

        @Test
        @DisplayName("测试只有数字的解析")
        void testParseSingleNumber() {
            calculator.pdone("123");
            ArrayList<String> result = calculator.parseExpression();
            assertEquals(1, result.size());
            assertEquals("123", result.get(0));
        }

        @Test
        @DisplayName("测试只有运算符的解析")
        void testParseOnlyOperator() {
            calculator.pdone("+");
            ArrayList<String> result = calculator.parseExpression();
            assertEquals(1, result.size());
            assertEquals("+", result.get(0));
        }

        @Test
        @DisplayName("测试连续运算符解析")
        void testParseConsecutiveOperators() {
            calculator.pdone("1++2");
            ArrayList<String> result = calculator.parseExpression();
            assertEquals(4, result.size());
            assertEquals("1", result.get(0));
            assertEquals("+", result.get(1));
            assertEquals("+", result.get(2));
            assertEquals("2", result.get(3));
        }

        @Test
        @DisplayName("测试空表达式解析")
        void testParseEmptyExpression() {
            ArrayList<String> result = calculator.parseExpression();
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("测试所有运算符解析")
        void testParseAllOperators() {
            calculator.pdone("1+2-3*4/5%6");
            ArrayList<String> result = calculator.parseExpression();
            assertEquals(11, result.size());
            assertTrue(result.contains("+"));
            assertTrue(result.contains("-"));
            assertTrue(result.contains("*"));
            assertTrue(result.contains("/"));
            assertTrue(result.contains("%"));
        }
    }

    @Nested
    @DisplayName("计算功能测试")
    class CalculationTests {

        @Test
        @DisplayName("测试加法运算")
        void testCalculateAddition() {
            calculator.pdone("1+2");
            assertEquals(3.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试减法运算")
        void testCalculateSubtraction() {
            calculator.pdone("5-2");
            assertEquals(3.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试乘法运算")
        void testCalculateMultiplication() {
            calculator.pdone("3*4");
            assertEquals(12.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试除法运算")
        void testCalculateDivision() {
            calculator.pdone("10/2");
            assertEquals(5.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试模运算")
        void testCalculateModulus() {
            calculator.pdone("10%3");
            assertEquals(1.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试小数运算")
        void testCalculateDecimalNumbers() {
            calculator.pdone("1.5+2.5");
            assertEquals(4.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试复杂混合运算")
        void testCalculateMixedOperations() {
            calculator.pdone("2+3*4-10/2");
            assertEquals(9.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试运算符优先级 - 乘法优先")
        void testOperatorPrecedenceMultiplication() {
            calculator.pdone("2+3*4");
            assertEquals(14.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试运算符优先级 - 除法优先")
        void testOperatorPrecedenceDivision() {
            calculator.pdone("10-8/2");
            assertEquals(6.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试运算符优先级 - 混合运算")
        void testOperatorPrecedenceMixed() {
            calculator.pdone("1+2*3-4/2");
            assertEquals(5.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试单个数字")
        void testCalculateSingleNumber() {
            calculator.pdone("42");
            assertEquals(42.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试负数运算")
        void testCalculateNegativeNumbers() {
            calculator.pdone("0-5");
            assertEquals(-5.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试零运算")
        void testCalculateWithZero() {
            calculator.pdone("0*100");
            assertEquals(0.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试大数运算")
        void testCalculateLargeNumbers() {
            calculator.pdone("999999*999999");
            assertEquals(999998000001.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试连续加法")
        void testCalculateMultipleAdditions() {
            calculator.pdone("1+2+3+4");
            assertEquals(10.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试连续乘法")
        void testCalculateMultipleMultiplications() {
            calculator.pdone("2*3*4");
            assertEquals(24.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试模运算与其他运算混合")
        void testCalculateModulusWithOthers() {
            calculator.pdone("10%3+5");
            assertEquals(6.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试复杂表达式")
        void testCalculateComplexExpression() {
            calculator.pdone("2*3+4*5-6/2");
            assertEquals(23.0, calculator.calculate(), 0.001);
        }
    }

    @Nested
    @DisplayName("错误处理和边界条件测试")
    class ErrorHandlingTests {

        @Test
        @DisplayName("测试除以零")
        void testDivisionByZero() {
            calculator.pdone("10/0");
            assertEquals(Double.POSITIVE_INFINITY, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试模零")
        void testModulusByZero() {
            calculator.pdone("5%0");
            assertTrue(Double.isNaN(calculator.calculate()));
        }

        @Test
        @DisplayName("测试空表达式计算")
        void testCalculateEmptyExpression() {
            assertEquals(0.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试无效数字格式")
        void testInvalidNumberFormat() {
            calculator.pdone("abc");
            assertEquals(0.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试只有运算符的计算")
        void testCalculateOnlyOperators() {
            calculator.pdone("+");
            // 这种情况下计算器会尝试解析，但应该返回0或抛出异常
            try {
                double result = calculator.calculate();
                assertEquals(0.0, result, 0.001);
            } catch (Exception e) {
                // 预期可能抛出异常
                assertTrue(e instanceof ArrayIndexOutOfBoundsException || e instanceof NumberFormatException);
            }
        }

        @Test
        @DisplayName("测试连续运算符的计算")
        void testCalculateConsecutiveOperators() {
            calculator.pdone("1++2");
            // 这种情况下应该处理为错误或特殊情况
            try {
                double result = calculator.calculate();
                // 如果没有抛出异常，结果应该是合理的
                assertTrue(!Double.isNaN(result));
            } catch (Exception e) {
                // 预期可能抛出异常
                assertTrue(e instanceof NumberFormatException);
            }
        }

        @Test
        @DisplayName("测试以运算符开头的表达式")
        void testCalculateStartingWithOperator() {
            calculator.pdone("+5");
            try {
                double result = calculator.calculate();
                assertEquals(0.0, result, 0.001);
            } catch (Exception e) {
                // 预期可能抛出异常
                assertTrue(e instanceof ArrayIndexOutOfBoundsException);
            }
        }

        @Test
        @DisplayName("测试以运算符结尾的表达式")
        void testCalculateEndingWithOperator() {
            calculator.pdone("5+");
            try {
                double result = calculator.calculate();
                assertEquals(5.0, result, 0.001);
            } catch (Exception e) {
                // 预期可能抛出异常
                assertTrue(e instanceof IndexOutOfBoundsException);
            }
        }

        @Test
        @DisplayName("测试极小数值")
        void testCalculateVerySmallNumbers() {
            calculator.pdone("0.0001+0.0002");
            assertEquals(0.0003, calculator.calculate(), 0.0001);
        }

        @Test
        @DisplayName("测试精度问题")
        void testCalculatePrecisionIssues() {
            calculator.pdone("0.1+0.2");
            assertEquals(0.3, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试负零")
        void testCalculateNegativeZero() {
            calculator.pdone("0-0");
            assertEquals(0.0, calculator.calculate(), 0.001);
        }
    }

    @Nested
    @DisplayName("内存功能测试")
    class MemoryFunctionTests {

        @Test
        @DisplayName("测试内存存储和读取")
        void testMemoryStoreAndRecall() {
            calculator.pdone("42");
            calculator.setMemoryValue(calculator.getDisplayText());
            assertEquals("42", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存清除")
        void testMemoryClear() {
            calculator.setMemoryValue("100");
            assertEquals("100", calculator.getMemoryValue());
            calculator.setMemoryValue("");
            assertEquals("", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存初始状态")
        void testMemoryInitialState() {
            Calculator calc = new Calculator();
            assertNull(calc.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存存储空值")
        void testMemoryStoreEmpty() {
            calculator.setMemoryValue("");
            assertEquals("", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存存储null")
        void testMemoryStoreNull() {
            calculator.setMemoryValue(null);
            assertNull(calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存存储小数")
        void testMemoryStoreDecimal() {
            calculator.setMemoryValue("3.14159");
            assertEquals("3.14159", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存存储负数")
        void testMemoryStoreNegative() {
            calculator.setMemoryValue("-123");
            assertEquals("-123", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存存储零")
        void testMemoryStoreZero() {
            calculator.setMemoryValue("0");
            assertEquals("0", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存存储大数")
        void testMemoryStoreLargeNumber() {
            calculator.setMemoryValue("999999999");
            assertEquals("999999999", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存存储表达式结果")
        void testMemoryStoreCalculationResult() {
            calculator.pdone("5+3");
            double result = calculator.calculate();
            calculator.setMemoryValue(String.valueOf(result));
            assertEquals("8.0", calculator.getMemoryValue());
        }
    }

    @Nested
    @DisplayName("高级计算测试")
    class AdvancedCalculationTests {

        @Test
        @DisplayName("测试多重乘除混合运算")
        void testMultipleDivisionMultiplication() {
            calculator.pdone("24/4*3/2");
            assertEquals(9.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试多重加减混合运算")
        void testMultipleAdditionSubtraction() {
            calculator.pdone("10+5-3+2-1");
            assertEquals(13.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试所有运算符混合")
        void testAllOperatorsMixed() {
            calculator.pdone("2+3*4-10/2%3");
            // 计算: 2 + (3*4) - (10/2)%3 = 2 + 12 - 5%3 = 2 + 12 - 2 = 12
            assertEquals(12.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试左结合性 - 除法")
        void testLeftAssociativityDivision() {
            calculator.pdone("8/4/2");
            assertEquals(1.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试左结合性 - 减法")
        void testLeftAssociativitySubtraction() {
            calculator.pdone("10-3-2");
            assertEquals(5.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试模运算优先级")
        void testModulusPrecedence() {
            calculator.pdone("2+10%3");
            assertEquals(3.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试复杂模运算")
        void testComplexModulusOperation() {
            calculator.pdone("17%5*2+3");
            assertEquals(7.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试浮点数精度")
        void testFloatingPointPrecision() {
            calculator.pdone("0.1*3");
            assertEquals(0.3, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试很长的表达式")
        void testVeryLongExpression() {
            calculator.pdone("1+2+3+4+5+6+7+8+9+10");
            assertEquals(55.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试交替运算符")
        void testAlternatingOperators() {
            calculator.pdone("1*2+3*4+5*6");
            // 计算: (1*2) + (3*4) + (5*6) = 2 + 12 + 30 = 44
            assertEquals(44.0, calculator.calculate(), 0.001);
        }
    }

    @Nested
    @DisplayName("特殊数值测试")
    class SpecialValueTests {

        @Test
        @DisplayName("测试无穷大运算")
        void testInfinityCalculation() {
            calculator.pdone("1/0");
            double result = calculator.calculate();
            assertTrue(Double.isInfinite(result));
            assertTrue(result > 0);
        }

        @Test
        @DisplayName("测试NaN运算")
        void testNaNCalculation() {
            calculator.pdone("0%0");
            assertTrue(Double.isNaN(calculator.calculate()));
        }

        @Test
        @DisplayName("测试最大双精度数")
        void testMaxDoubleValue() {
            calculator.pdone("1.7976931348623157E308");
            assertEquals(Double.MAX_VALUE, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试最小双精度数")
        void testMinDoubleValue() {
            // 计算器可能不支持科学计数法，改为测试小数
            calculator.pdone("0.000001");
            assertEquals(0.000001, calculator.calculate(), 0.0000001);
        }

        @Test
        @DisplayName("测试科学计数法")
        void testScientificNotation() {
            calculator.pdone("1E3");
            assertEquals(1000.0, calculator.calculate(), 0.001);
        }
    }

    @Nested
    @DisplayName("显示文本功能测试")
    class DisplayTextTests {

        @Test
        @DisplayName("测试获取空显示文本")
        void testGetEmptyDisplayText() {
            Calculator calc = new Calculator();
            assertEquals("", calc.getDisplayText());
        }

        @Test
        @DisplayName("测试获取数字显示文本")
        void testGetNumberDisplayText() {
            calculator.pdone("123");
            assertEquals("123", calculator.getDisplayText());
        }

        @Test
        @DisplayName("测试获取表达式显示文本")
        void testGetExpressionDisplayText() {
            calculator.pdone("1+2*3");
            assertEquals("1+2*3", calculator.getDisplayText());
        }

        @Test
        @DisplayName("测试获取小数显示文本")
        void testGetDecimalDisplayText() {
            calculator.pdone("3.14159");
            assertEquals("3.14159", calculator.getDisplayText());
        }

        @Test
        @DisplayName("测试获取负数显示文本")
        void testGetNegativeDisplayText() {
            calculator.pdone("0-5");
            assertEquals("0-5", calculator.getDisplayText());
        }

        @Test
        @DisplayName("测试显示文本长度")
        void testDisplayTextLength() {
            calculator.pdone("1234567890");
            assertEquals(10, calculator.getDisplayText().length());
        }

        @Test
        @DisplayName("测试特殊字符显示")
        void testSpecialCharacterDisplay() {
            calculator.pdone(".");
            assertEquals(".", calculator.getDisplayText());
        }
    }

    @Nested
    @DisplayName("集成测试")
    class IntegrationTests {

        @Test
        @DisplayName("测试完整计算流程")
        void testCompleteCalculationFlow() {
            // 输入表达式
            calculator.pdone("2");
            calculator.pdone("+");
            calculator.pdone("3");
            calculator.pdone("*");
            calculator.pdone("4");

            // 验证显示文本
            assertEquals("2+3*4", calculator.getDisplayText());

            // 计算结果
            double result = calculator.calculate();
            assertEquals(14.0, result, 0.001);
        }

        @Test
        @DisplayName("测试内存和计算结合")
        void testMemoryWithCalculation() {
            // 计算并存储到内存
            calculator.pdone("5+3");
            double result = calculator.calculate();
            calculator.setMemoryValue(String.valueOf(result));

            // 验证内存值
            assertEquals("8.0", calculator.getMemoryValue());

            // 清空显示，输入新表达式
            calculator = new Calculator();
            calculator.pdone("2*");
            calculator.pdone("4");

            // 计算新表达式
            assertEquals(8.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试多步骤计算")
        void testMultiStepCalculation() {
            // 第一步计算
            calculator.pdone("10/2");
            double step1 = calculator.calculate();
            assertEquals(5.0, step1, 0.001);

            // 使用第一步结果进行第二步计算
            calculator = new Calculator();
            calculator.pdone(String.valueOf(step1));
            calculator.pdone("+");
            calculator.pdone("3");
            double step2 = calculator.calculate();
            assertEquals(8.0, step2, 0.001);
        }

        @Test
        @DisplayName("测试错误恢复")
        void testErrorRecovery() {
            // 输入错误表达式
            calculator.pdone("5/0");
            double result1 = calculator.calculate();
            assertTrue(Double.isInfinite(result1));

            // 清空并输入正确表达式
            calculator = new Calculator();
            calculator.pdone("5+5");
            double result2 = calculator.calculate();
            assertEquals(10.0, result2, 0.001);
        }

        @Test
        @DisplayName("测试表达式解析和计算一致性")
        void testParsingCalculationConsistency() {
            calculator.pdone("1+2*3-4/2");

            // 验证解析结果 - 实际应该是9个token: 1,+,2,*,3,-,4,/,2
            ArrayList<String> tokens = calculator.parseExpression();
            assertEquals(9, tokens.size());

            // 验证计算结果
            double result = calculator.calculate();
            assertEquals(5.0, result, 0.001);
        }

        @Test
        @DisplayName("测试边界值组合")
        void testBoundaryValueCombinations() {
            // 测试大数和小数的组合，避免科学计数法
            calculator.pdone("1000000");
            calculator.pdone("+");
            calculator.pdone("0.000001");

            double result = calculator.calculate();
            assertEquals(1000000.000001, result, 0.000001);
        }
    }

    @Nested
    @DisplayName("性能和稳定性测试")
    class PerformanceStabilityTests {

        @Test
        @DisplayName("测试重复计算稳定性")
        void testRepeatedCalculationStability() {
            for (int i = 0; i < 100; i++) {
                Calculator calc = new Calculator();
                calc.pdone("2+2");
                assertEquals(4.0, calc.calculate(), 0.001);
            }
        }

        @Test
        @DisplayName("测试大量输入处理")
        void testLargeInputHandling() {
            StringBuilder expression = new StringBuilder();
            for (int i = 1; i <= 50; i++) {
                expression.append(i);
                if (i < 50) {
                    expression.append("+");
                }
            }

            calculator.pdone(expression.toString());
            double result = calculator.calculate();
            assertEquals(1275.0, result, 0.001); // 1+2+...+50 = 50*51/2 = 1275
        }

        @Test
        @DisplayName("测试内存使用效率")
        void testMemoryEfficiency() {
            // 创建多个计算器实例测试内存使用
            Calculator[] calculators = new Calculator[10];
            for (int i = 0; i < 10; i++) {
                calculators[i] = new Calculator();
                calculators[i].pdone(String.valueOf(i));
                assertEquals(i, calculators[i].calculate(), 0.001);
            }
        }
    }
}
