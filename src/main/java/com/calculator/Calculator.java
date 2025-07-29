package com.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator extends JFrame implements Runnable{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Calculator());
    }
    private JButton m, MS, a2Button, zf, a1Button, a0Button, a3Button, d, jian, a1X;
    private JButton jia, dy, cheng, quyu, a6Button, a5Button, a4Button, MC, MR;
    private JButton chu, a9Button, a8Button, a7Button, CE, back, C, sqrt;
    private JTextField display;
    private JPanel mainPanel;
    //M功能储存字符串
    private String memoryValue;

    Calculator(){
        // 初始化主面板
        mainPanel = new JPanel(new BorderLayout());

        // 创建显示框
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setEditable(false);
        mainPanel.add(display, BorderLayout.NORTH);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(5, 5, 5, 5));
        
        // 创建按钮
        a7Button = new JButton("7");
        a8Button = new JButton("8");
        a9Button = new JButton("9");
        chu = new JButton("/");
        CE = new JButton("CE");
        
        a4Button = new JButton("4");
        a5Button = new JButton("5");
        a6Button = new JButton("6");
        cheng = new JButton("*");
        back = new JButton("←");
        
        a1Button = new JButton("1");
        a2Button = new JButton("2");
        a3Button = new JButton("3");
        jian = new JButton("-");
        C = new JButton("C");
        
        a0Button = new JButton("0");
        d = new JButton(".");
        dy = new JButton("=");
        jia = new JButton("+");
        sqrt = new JButton("√");
        
        quyu = new JButton("%");
        a1X = new JButton("1/x");
        zf = new JButton("±");
        MC = new JButton("MC");
        MR = new JButton("MR");
        MS = new JButton("MS");
        m = new JButton("M+");
        
        // 添加按钮到面板
        buttonPanel.add(a7Button);
        buttonPanel.add(a8Button);
        buttonPanel.add(a9Button);
        buttonPanel.add(chu);
        buttonPanel.add(CE);
        
        buttonPanel.add(a4Button);
        buttonPanel.add(a5Button);
        buttonPanel.add(a6Button);
        buttonPanel.add(cheng);
        buttonPanel.add(back);
        
        buttonPanel.add(a1Button);
        buttonPanel.add(a2Button);
        buttonPanel.add(a3Button);
        buttonPanel.add(jian);
        buttonPanel.add(C);
        
        buttonPanel.add(a0Button);
        buttonPanel.add(d);
        buttonPanel.add(dy);
        buttonPanel.add(jia);
        buttonPanel.add(sqrt);
        
        buttonPanel.add(quyu);
        buttonPanel.add(a1X);
        buttonPanel.add(zf);
        buttonPanel.add(MC);
        buttonPanel.add(MR);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // 设置窗口属性
        this.setContentPane(mainPanel);
        this.setTitle("计算器");
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // 创建菜单
        JMenuBar cd = new JMenuBar();
        JMenu bianji = new JMenu("编辑");
        JMenu chakan = new JMenu("查看");
        JMenu bangzhu = new JMenu("帮助");
        cd.add(bianji);
        cd.add(chakan);
        cd.add(bangzhu);
        JMenuItem fuzi = new JMenuItem("复制(c)");
        //绑定快捷键
        fuzi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        fuzi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("复制了");
                String text = display.getText();
                // 获取系统剪贴板
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                // 创建要复制的内容
                StringSelection selection = new StringSelection(text);
                // 将内容设置到剪贴板
                clipboard.setContents(selection, null);
            }
        });
        JMenuItem zhantie = new JMenuItem("粘贴(v)");
        zhantie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        zhantie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("粘贴了");
                try {
                    String text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    display.setText(text);
                } catch (Exception ex) {
                    System.out.println("粘贴失败");
                }
            }
        });
        bianji.add(fuzi);
        bianji.add(zhantie);
        this.setJMenuBar(cd);
        //显示界面
        this.setVisible(true);
        //窗口关闭监听器
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void run() {
        //按钮监听
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("0");
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("1");
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("2");
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("3");
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("4");
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("5");
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("6");
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("7");
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("8");
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("9");
            }
        });
        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone(".");
            }
        });
        jia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("+");
            }
        });
        jian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("-");
            }
        });
        cheng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("*");
            }
        });
        chu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("/");
            }
        });
        quyu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdone("%");
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = display.getText();
                if (text.length() > 0) {
                    display.setText(text.substring(0, text.length()-1));
                }
            }
        });
        C.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText("");
            }
        });
        CE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = display.getText();
                for (int i = text.length()-1; i >=0 ; i--) {
                    if (text.charAt(i)=='+'||text.charAt(i)=='-'||text.charAt(i)=='*'||text.charAt(i)=='/'||text.charAt(i)=='%'){
                        display.setText(text.substring(0,i+1));
                        break;
                    }
                }
            }
        });
        dy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(String.valueOf(calculate()));
            }
        });
        a1X.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double number = calculate();
                if (number != 0) {
                    double reciprocal = 1.0 / number;
                    display.setText(String.valueOf(reciprocal));
                } else {
                    display.setText("错误：除以零");
                }
            }
        });
        sqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double number = calculate();
                if (number >= 0) {
                    display.setText(String.valueOf(Math.sqrt(number)));
                } else {
                    display.setText("错误：负数开方");
                }
            }
        });
        zf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double number = calculate();
                display.setText(String.valueOf(-number));
            }
        });
        MC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memoryValue = "";
            }
        });
        MR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (memoryValue != null && !memoryValue.isEmpty()) {
                    display.setText(memoryValue);
                }
            }
        });
        MS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memoryValue = display.getText();
            }
        });
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double currentValue = calculate();
                    double memValue = memoryValue != null && !memoryValue.isEmpty() ?
                        Double.parseDouble(memoryValue) : 0;
                    double sum = currentValue + memValue;
                    memoryValue = String.valueOf(sum);
                    display.setText(String.valueOf(sum));
                } catch (NumberFormatException ex) {
                    display.setText("错误：无效数字");
                }
            }
        });
    }

    void pdone(String text) {
        if (display.getText().isEmpty()) {
            display.setText(text);
        } else {
            display.setText(display.getText() + text);
        }
    }

    ArrayList<String> parseExpression(){
        //分割输入字符
        String text = display.getText();
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*|[\\+\\-\\*\\/\\%]");
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> result = new ArrayList<>();
        //添加到结果列表
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    double calculate(){
        double sum = 0;
        ArrayList<String> tokens = parseExpression();
        //判断有无运算符
        if (!tokens.contains("*")&&!tokens.contains("/")&&!tokens.contains("%")&&!tokens.contains("+")&&!tokens.contains("-")){
            try {
                return Double.parseDouble(display.getText());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        //先乘除
        while (tokens.contains("*")||tokens.contains("/")||tokens.contains("%")){
            int i = tokens.indexOf("*");
            int j = tokens.indexOf("/");
            int q = tokens.indexOf("%");

            int a = 0;
            //如果一个为-1 则直接选择另一个
            if (i != -1 && (j == -1 || i < j) && (q == -1 || i < q)) {
                a = i;
            } else if (j != -1 && (i == -1 || j < i) && (q == -1 || j < q)) {
                a = j;
            } else if (q != -1) {
                a = q;
            }
            double v;
            if (tokens.get(a).equals("*")) {
                v = Double.parseDouble(tokens.get(a - 1)) * Double.parseDouble(tokens.get(a + 1));
            }else if (tokens.get(a).equals("/")){
                if (Double.parseDouble(tokens.get(a + 1))==0){
                    return Double.POSITIVE_INFINITY; // 处理除零错误
                }
                v = Double.parseDouble(tokens.get(a - 1)) / Double.parseDouble(tokens.get(a + 1));
            }else {
                if (Double.parseDouble(tokens.get(a + 1))==0){
                    return Double.NaN; // 处理模零错误
                }
                v = Double.parseDouble(tokens.get(a - 1)) % Double.parseDouble(tokens.get(a + 1));
            }
            tokens.remove(a+1);
            tokens.set(a, String.valueOf(v));
            tokens.remove(a-1);
            sum=v;
        }
        //再加减
        while (tokens.contains("+")||tokens.contains("-")){
            int i = tokens.indexOf("+");
            int j = tokens.indexOf("-");
            int a;
            //如果一个为-1 则直接选择另一个
            if (i == -1) {
                a = j;
            } else if (j == -1) {
                a = i;
            } else {
                a = i > j ? j : i;
            }
            double v;
            if (tokens.get(a).equals("+")) {
                v = Double.parseDouble(tokens.get(a - 1)) + Double.parseDouble(tokens.get(a + 1));
            }else {
                v = Double.parseDouble(tokens.get(a - 1)) - Double.parseDouble(tokens.get(a + 1));
            }
            tokens.remove(a+1);
            tokens.set(a, String.valueOf(v));
            tokens.remove(a-1);
            sum=v;
        }
        return sum;
    }

    // 为测试提供访问display的方法
    public String getDisplayText() {
        return display.getText();
    }

    // 为测试提供访问memoryValue的方法
    public String getMemoryValue() {
        return memoryValue;
    }

    public void setMemoryValue(String value) {
        this.memoryValue = value;
    }

}
