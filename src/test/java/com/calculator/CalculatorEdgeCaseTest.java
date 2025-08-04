package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

@DisplayName("Calculator 边界条件和异常测试")
public class CalculatorEdgeCaseTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("数值边界测试")
    class NumericalBoundaryTests {

        @Test
        @DisplayName("测试零值运算")
        void testZeroOperations() {
            calculator.pdone("0+0");
            assertEquals(0.0, calculator.calculate(), 0.001);
            
            calculator = new Calculator();
            calculator.pdone("0*100");
            assertEquals(0.0, calculator.calculate(), 0.001);
            
            calculator = new Calculator();
            calculator.pdone("100-100");
            assertEquals(0.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试极大数值运算")
        void testLargeNumberOperations() {
            calculator.pdone("999999*999999");
            double result = calculator.calculate();
            assertEquals(999998000001.0, result, 0.001);
        }

        @Test
        @DisplayName("测试极小数值运算")
        void testSmallNumberOperations() {
            calculator.pdone("0.001*0.001");
            double result = calculator.calculate();
            assertEquals(0.000001, result, 0.0000001);
        }

        @Test
        @DisplayName("测试负数运算")
        void testNegativeNumberOperations() {
            calculator.pdone("0-5");
            assertEquals(-5.0, calculator.calculate(), 0.001);
            
            calculator = new Calculator();
            calculator.pdone("0-3*2");
            assertEquals(-6.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试小数精度")
        void testDecimalPrecision() {
            calculator.pdone("0.1+0.2");
            double result = calculator.calculate();
            assertEquals(0.3, result, 0.001);
            
            calculator = new Calculator();
            calculator.pdone("1.5*2.5");
            assertEquals(3.75, calculator.calculate(), 0.001);
        }
    }

    @Nested
    @DisplayName("表达式边界测试")
    class ExpressionBoundaryTests {

        @Test
        @DisplayName("测试单个数字")
        void testSingleNumber() {
            calculator.pdone("42");
            assertEquals(42.0, calculator.calculate(), 0.001);
            
            calculator = new Calculator();
            calculator.pdone("3.14159");
            assertEquals(3.14159, calculator.calculate(), 0.00001);
        }

        @Test
        @DisplayName("测试空表达式")
        void testEmptyExpression() {
            assertEquals(0.0, calculator.calculate(), 0.001);
        }

        @Test
        @DisplayName("测试只有小数点")
        void testOnlyDecimalPoint() {
            calculator.pdone(".");
            ArrayList<String> tokens = calculator.parseExpression();
            assertEquals(1, tokens.size());
            assertEquals(".", tokens.get(0));
        }

        @Test
        @DisplayName("测试多个小数点")
        void testMultipleDecimalPoints() {
            calculator.pdone("3.14.159");
            ArrayList<String> tokens = calculator.parseExpression();
            // 应该解析为多个token
            assertTrue(tokens.size() >= 2);
        }

        @Test
        @DisplayName("测试连续运算符")
        void testConsecutiveOperators() {
            calculator.pdone("5++3");
            ArrayList<String> tokens = calculator.parseExpression();
            assertEquals(4, tokens.size());
            assertEquals("5", tokens.get(0));
            assertEquals("+", tokens.get(1));
            assertEquals("+", tokens.get(2));
            assertEquals("3", tokens.get(3));
        }

        @Test
        @DisplayName("测试以运算符开头")
        void testStartingWithOperator() {
            calculator.pdone("+5");
            ArrayList<String> tokens = calculator.parseExpression();
            assertEquals(2, tokens.size());
            assertEquals("+", tokens.get(0));
            assertEquals("5", tokens.get(1));
        }

        @Test
        @DisplayName("测试以运算符结尾")
        void testEndingWithOperator() {
            calculator.pdone("5+");
            ArrayList<String> tokens = calculator.parseExpression();
            assertEquals(2, tokens.size());
            assertEquals("5", tokens.get(0));
            assertEquals("+", tokens.get(1));
        }
    }

    @Nested
    @DisplayName("异常处理测试")
    class ExceptionHandlingTests {

        @Test
        @DisplayName("测试除以零")
        void testDivisionByZero() {
            calculator.pdone("5/0");
            double result = calculator.calculate();
            assertEquals(Double.POSITIVE_INFINITY, result);
        }

        @Test
        @DisplayName("测试负数除以零")
        void testNegativeDivisionByZero() {
            calculator.pdone("0-5/0");
            double result = calculator.calculate();
            assertEquals(Double.NEGATIVE_INFINITY, result);
        }

        @Test
        @DisplayName("测试模零运算")
        void testModuloByZero() {
            calculator.pdone("5%0");
            double result = calculator.calculate();
            assertTrue(Double.isNaN(result));
        }

        @Test
        @DisplayName("测试零模零")
        void testZeroModuloZero() {
            calculator.pdone("0%0");
            double result = calculator.calculate();
            assertTrue(Double.isNaN(result));
        }

        @Test
        @DisplayName("测试无效数字格式")
        void testInvalidNumberFormat() {
            // 测试只有运算符的情况
            try {
                calculator.pdone("+");
                double result = calculator.calculate();
                // 应该返回0或抛出异常
                assertTrue(result == 0.0 || Double.isNaN(result));
            } catch (Exception e) {
                assertTrue(e instanceof NumberFormatException || e instanceof ArrayIndexOutOfBoundsException);
            }
        }
    }

    @Nested
    @DisplayName("内存功能边界测试")
    class MemoryBoundaryTests {

        @Test
        @DisplayName("测试内存空值处理")
        void testMemoryNullHandling() {
            assertNull(calculator.getMemoryValue());
            
            calculator.setMemoryValue(null);
            assertNull(calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存空字符串处理")
        void testMemoryEmptyStringHandling() {
            calculator.setMemoryValue("");
            assertEquals("", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存大数值")
        void testMemoryLargeValue() {
            calculator.setMemoryValue("999999999999");
            assertEquals("999999999999", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存小数值")
        void testMemoryDecimalValue() {
            calculator.setMemoryValue("3.141592653589793");
            assertEquals("3.141592653589793", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存负数值")
        void testMemoryNegativeValue() {
            calculator.setMemoryValue("-123.456");
            assertEquals("-123.456", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试内存零值")
        void testMemoryZeroValue() {
            calculator.setMemoryValue("0");
            assertEquals("0", calculator.getMemoryValue());
            
            calculator.setMemoryValue("0.0");
            assertEquals("0.0", calculator.getMemoryValue());
        }
    }

    @Nested
    @DisplayName("复杂表达式测试")
    class ComplexExpressionTests {

        @Test
        @DisplayName("测试长表达式")
        void testLongExpression() {
            StringBuilder expr = new StringBuilder();
            for (int i = 1; i <= 20; i++) {
                expr.append(i);
                if (i < 20) {
                    expr.append("+");
                }
            }
            calculator.pdone(expr.toString());
            double result = calculator.calculate();
            assertEquals(210.0, result, 0.001); // 1+2+...+20 = 210
        }

        @Test
        @DisplayName("测试混合运算优先级")
        void testMixedOperatorPrecedence() {
            calculator.pdone("2+3*4-10/2%3");
            double result = calculator.calculate();
            // 2 + (3*4) - (10/2)%3 = 2 + 12 - 5%3 = 2 + 12 - 2 = 12
            assertEquals(12.0, result, 0.001);
        }

        @Test
        @DisplayName("测试复杂小数运算")
        void testComplexDecimalOperations() {
            calculator.pdone("1.5*2.5+3.7-1.2/0.6");
            double result = calculator.calculate();
            // 1.5*2.5 + 3.7 - 1.2/0.6 = 3.75 + 3.7 - 2.0 = 5.45
            assertEquals(5.45, result, 0.001);
        }

        @Test
        @DisplayName("测试多重嵌套运算")
        void testMultipleNestedOperations() {
            calculator.pdone("10*2+5*3-4*2/2");
            double result = calculator.calculate();
            // 10*2 + 5*3 - 4*2/2 = 20 + 15 - 4 = 31
            assertEquals(31.0, result, 0.001);
        }

        @Test
        @DisplayName("测试所有运算符组合")
        void testAllOperatorCombination() {
            calculator.pdone("100+50-25*2/5%3");
            double result = calculator.calculate();
            // 100 + 50 - (25*2)/5%3 = 100 + 50 - 10%3 = 100 + 50 - 1 = 149
            assertEquals(149.0, result, 0.001);
        }
    }

    @Nested
    @DisplayName("性能和稳定性测试")
    class PerformanceStabilityTests {

        @Test
        @DisplayName("测试重复计算稳定性")
        void testRepeatedCalculationStability() {
            for (int i = 0; i < 50; i++) {
                Calculator calc = new Calculator();
                calc.pdone("2*3+1");
                assertEquals(7.0, calc.calculate(), 0.001);
            }
        }

        @Test
        @DisplayName("测试内存使用稳定性")
        void testMemoryUsageStability() {
            for (int i = 0; i < 100; i++) {
                calculator.setMemoryValue(String.valueOf(i));
                assertEquals(String.valueOf(i), calculator.getMemoryValue());
            }
        }

        @Test
        @DisplayName("测试大量表达式解析")
        void testMassiveExpressionParsing() {
            for (int i = 0; i < 50; i++) {
                Calculator calc = new Calculator();
                calc.pdone("1+2+3+4+5");
                ArrayList<String> tokens = calc.parseExpression();
                assertEquals(9, tokens.size());
            }
        }
    }
}
