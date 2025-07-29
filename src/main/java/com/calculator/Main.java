package com.calculator;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(new Calculator());
        } catch (Exception e) {
            System.err.println("程序启动失败:");
            e.printStackTrace();
        }
    }
}
