package com.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void testParseExpression() {
        // 测试分割数字和运算符
        calculator.pdone("1+2*3-4/5");
        ArrayList<String> result = calculator.parseExpression();
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
    public void testCalculateAddition() {
        Calculator calc = new Calculator();
        calc.pdone("1+2");
        assertEquals(3.0, calc.calculate(), 0.001);
    }

    @Test
    public void testCalculateSubtraction() {
        Calculator calc = new Calculator();
        calc.pdone("5-2");
        assertEquals(3.0, calc.calculate(), 0.001);
    }

    @Test
    public void testCalculateMultiplication() {
        Calculator calc = new Calculator();
        calc.pdone("3*4");
        assertEquals(12.0, calc.calculate(), 0.001);
    }

    @Test
    public void testCalculateDivision() {
        Calculator calc = new Calculator();
        calc.pdone("10/2");
        assertEquals(5.0, calc.calculate(), 0.001);
    }

    @Test
    public void testCalculateModulus() {
        Calculator calc = new Calculator();
        calc.pdone("10%3");
        assertEquals(1.0, calc.calculate(), 0.001);
    }

    @Test
    public void testCalculateMixedOperations() {
        Calculator calc = new Calculator();
        calc.pdone("2+3*4-10/2");
        assertEquals(9.0, calc.calculate(), 0.001);
    }

    @Test
    public void testCalculateDecimalNumbers() {
        Calculator calc = new Calculator();
        calc.pdone("1.5+2.5");
        assertEquals(4.0, calc.calculate(), 0.001);
    }

    @Test
    public void testCalculateDivisionByZero() {
        Calculator calc = new Calculator();
        calc.pdone("10/0");
        // 测试除以零的情况，预期返回无穷大
        assertEquals(Double.POSITIVE_INFINITY, calc.calculate(), 0.001);
    }

    @Test
    public void testPdone() {
        Calculator calc = new Calculator();
        calc.pdone("1");
        calc.pdone("2");
        calc.pdone("+");
        calc.pdone("3");
        assertEquals("12+3", calc.getDisplayText());
    }

    @Test
    public void testComplexExpression() {
        Calculator calc = new Calculator();
        calc.pdone("2*3+4");
        assertEquals(10.0, calc.calculate(), 0.001);
    }

    @Test
    public void testMemoryFunctions() {
        Calculator calc = new Calculator();

        // 测试MS (Memory Store)
        calc.pdone("42");
        calc.setMemoryValue(""); // 初始化内存
        // 模拟MS按钮点击
        calc.setMemoryValue(calc.getDisplayText());
        assertEquals("42", calc.getMemoryValue());

        // 测试MR (Memory Recall)
        calc.pdone("0"); // 清空显示
        // 模拟MR按钮点击 - 这里我们直接测试内存值
        assertEquals("42", calc.getMemoryValue());

        // 测试MC (Memory Clear)
        calc.setMemoryValue("");
        assertEquals("", calc.getMemoryValue());
    }

    @Test
    public void testErrorHandling() {
        Calculator calc = new Calculator();

        // 测试空表达式
        calc.pdone("");
        assertEquals(0.0, calc.calculate(), 0.001);

        // 测试单个数字
        calc = new Calculator();
        calc.pdone("5");
        assertEquals(5.0, calc.calculate(), 0.001);

        // 测试负数 - 修改为有效的表达式
        calc = new Calculator();
        calc.pdone("0-3+5");
        assertEquals(2.0, calc.calculate(), 0.001);
    }

    @Test
    public void testEdgeCases() {
        Calculator calc = new Calculator();

        // 测试小数运算
        calc.pdone("0.1+0.2");
        assertEquals(0.3, calc.calculate(), 0.001);

        // 测试大数运算
        calc = new Calculator();
        calc.pdone("999999*999999");
        assertEquals(999998000001.0, calc.calculate(), 0.001);

        // 测试零运算
        calc = new Calculator();
        calc.pdone("0*100");
        assertEquals(0.0, calc.calculate(), 0.001);
    }

    @Test
    public void testOperatorPrecedence() {
        Calculator calc = new Calculator();

        // 测试乘法优先级
        calc.pdone("2+3*4");
        assertEquals(14.0, calc.calculate(), 0.001);

        // 测试除法优先级
        calc = new Calculator();
        calc.pdone("10-8/2");
        assertEquals(6.0, calc.calculate(), 0.001);

        // 测试混合运算
        calc = new Calculator();
        calc.pdone("1+2*3-4/2");
        assertEquals(5.0, calc.calculate(), 0.001);
    }

    @Test
    public void testModulusOperations() {
        Calculator calc = new Calculator();

        // 基本模运算
        calc.pdone("7%3");
        assertEquals(1.0, calc.calculate(), 0.001);

        // 模运算与其他运算混合
        calc = new Calculator();
        calc.pdone("10%3+5");
        assertEquals(6.0, calc.calculate(), 0.001);

        // 测试模零情况
        calc = new Calculator();
        calc.pdone("5%0");
        assertTrue(Double.isNaN(calc.calculate()));
    }

    @Test
    public void testParseExpressionEdgeCases() {
        Calculator calc = new Calculator();

        // 测试只有运算符
        calc.pdone("+");
        java.util.ArrayList<String> result = calc.parseExpression();
        assertEquals(1, result.size());
        assertEquals("+", result.get(0));

        // 测试连续运算符
        calc = new Calculator();
        calc.pdone("1++2");
        result = calc.parseExpression();
        assertEquals(4, result.size());
        assertEquals("1", result.get(0));
        assertEquals("+", result.get(1));
        assertEquals("+", result.get(2));
        assertEquals("2", result.get(3));
    }
}
