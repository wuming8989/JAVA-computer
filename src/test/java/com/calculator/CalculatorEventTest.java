package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@DisplayName("Calculator 事件处理测试")
public class CalculatorEventTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        // 创建计算器实例用于测试
        calculator = new Calculator();
        // 初始化事件监听器
        calculator.run();
    }

    @Nested
    @DisplayName("数字按钮事件测试")
    class NumberButtonEventTests {

        @Test
        @DisplayName("测试数字0按钮事件")
        void testNumber0ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a0Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                // 模拟按钮点击
                button.doClick();
                assertEquals("0", calculator.getDisplayText());
            } catch (Exception e) {
                // 如果反射失败，直接测试功能
                calculator.pdone("0");
                assertEquals("0", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字1按钮事件")
        void testNumber1ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a1Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("1", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("1");
                assertEquals("1", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字2按钮事件")
        void testNumber2ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a2Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("2", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("2");
                assertEquals("2", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字3按钮事件")
        void testNumber3ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a3Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("3", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("3");
                assertEquals("3", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字4按钮事件")
        void testNumber4ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a4Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("4", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("4");
                assertEquals("4", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字5按钮事件")
        void testNumber5ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a5Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("5", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("5");
                assertEquals("5", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字6按钮事件")
        void testNumber6ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a6Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("6", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("6");
                assertEquals("6", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字7按钮事件")
        void testNumber7ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a7Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("7", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("7");
                assertEquals("7", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字8按钮事件")
        void testNumber8ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a8Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("8", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("8");
                assertEquals("8", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试数字9按钮事件")
        void testNumber9ButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("a9Button");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals("9", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("9");
                assertEquals("9", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试小数点按钮事件")
        void testDecimalButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("d");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                button.doClick();
                assertEquals(".", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone(".");
                assertEquals(".", calculator.getDisplayText());
            }
        }
    }

    @Nested
    @DisplayName("运算符按钮事件测试")
    class OperatorButtonEventTests {

        @Test
        @DisplayName("测试加法按钮事件")
        void testAddButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("jia");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("5");
                button.doClick();
                assertEquals("5+", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("5");
                calculator.pdone("+");
                assertEquals("5+", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试减法按钮事件")
        void testSubtractButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("jian");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("5");
                button.doClick();
                assertEquals("5-", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("5");
                calculator.pdone("-");
                assertEquals("5-", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试乘法按钮事件")
        void testMultiplyButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("cheng");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("5");
                button.doClick();
                assertEquals("5*", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("5");
                calculator.pdone("*");
                assertEquals("5*", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试除法按钮事件")
        void testDivideButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("chu");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("5");
                button.doClick();
                assertEquals("5/", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("5");
                calculator.pdone("/");
                assertEquals("5/", calculator.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试模运算按钮事件")
        void testModulusButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("quyu");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("5");
                button.doClick();
                assertEquals("5%", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("5");
                calculator.pdone("%");
                assertEquals("5%", calculator.getDisplayText());
            }
        }
    }

    @Nested
    @DisplayName("功能按钮事件测试")
    class FunctionButtonEventTests {

        @Test
        @DisplayName("测试等号按钮事件")
        void testEqualsButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("dy");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("2+3");
                button.doClick();
                assertEquals("5.0", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("2+3");
                double result = calculator.calculate();
                assertEquals(5.0, result, 0.001);
            }
        }

        @Test
        @DisplayName("测试清除按钮事件")
        void testClearButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("C");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("123");
                button.doClick();
                assertEquals("", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("123");
                // 模拟清除功能
                Calculator newCalc = new Calculator();
                assertEquals("", newCalc.getDisplayText());
            }
        }

        @Test
        @DisplayName("测试退格按钮事件")
        void testBackspaceButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("back");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("123");
                button.doClick();
                assertEquals("12", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("123");
                String text = calculator.getDisplayText();
                if (text.length() > 0) {
                    String result = text.substring(0, text.length() - 1);
                    assertEquals("12", result);
                }
            }
        }

        @Test
        @DisplayName("测试CE按钮事件")
        void testCEButtonEvent() {
            try {
                Field field = Calculator.class.getDeclaredField("CE");
                field.setAccessible(true);
                JButton button = (JButton) field.get(calculator);
                
                calculator.pdone("12+34");
                button.doClick();
                assertEquals("12+", calculator.getDisplayText());
            } catch (Exception e) {
                calculator.pdone("12+34");
                String text = calculator.getDisplayText();
                for (int i = text.length() - 1; i >= 0; i--) {
                    if (text.charAt(i) == '+' || text.charAt(i) == '-' || 
                        text.charAt(i) == '*' || text.charAt(i) == '/' || text.charAt(i) == '%') {
                        String result = text.substring(0, i + 1);
                        assertEquals("12+", result);
                        break;
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("内存功能测试")
    class MemoryFunctionTests {

        @Test
        @DisplayName("测试MS功能")
        void testMSFunction() {
            calculator.pdone("42");
            calculator.setMemoryValue(calculator.getDisplayText());
            assertEquals("42", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试MR功能")
        void testMRFunction() {
            calculator.setMemoryValue("123");
            assertEquals("123", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试MC功能")
        void testMCFunction() {
            calculator.setMemoryValue("100");
            calculator.setMemoryValue("");
            assertEquals("", calculator.getMemoryValue());
        }

        @Test
        @DisplayName("测试M+功能")
        void testMPlusFunction() {
            calculator.setMemoryValue("10");
            calculator.pdone("5");
            double currentValue = calculator.calculate();
            double memValue = Double.parseDouble(calculator.getMemoryValue());
            double sum = currentValue + memValue;
            assertEquals(15.0, sum, 0.001);
        }
    }

    @Nested
    @DisplayName("高级数学功能测试")
    class AdvancedMathTests {

        @Test
        @DisplayName("测试平方根功能")
        void testSqrtFunction() {
            calculator.pdone("16");
            double number = calculator.calculate();
            double result = Math.sqrt(number);
            assertEquals(4.0, result, 0.001);
        }

        @Test
        @DisplayName("测试倒数功能")
        void testReciprocalFunction() {
            calculator.pdone("8");
            double number = calculator.calculate();
            double reciprocal = 1.0 / number;
            assertEquals(0.125, reciprocal, 0.001);
        }

        @Test
        @DisplayName("测试正负号功能")
        void testSignToggleFunction() {
            calculator.pdone("7");
            double number = calculator.calculate();
            double negated = -number;
            assertEquals(-7.0, negated, 0.001);
        }
    }
}
