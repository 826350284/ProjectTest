import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;//按钮
import javax.swing.JFrame;//window对象能够最大化、最小化、关闭
import javax.swing.JPanel;//面板容器类
import javax.swing.JTextField;//文本框

public class test extends JFrame implements ActionListener {

    String[] KEYS = { "1", "2", "3", "+", "4", "5", "6", "-",
            "7", "8", "9", "*", "0", ".", "/", "=" };
    JButton keys[] = new JButton[KEYS.length];
    JButton jbClean = new JButton("clean");
    JTextField resultText = new JTextField("0", 10);

    // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private boolean firstDigit = true;
    // 计算的中间结果。
    private double resultNum = 0.0;
    // 当前运算的运算符
    private String operator = "=";
    // 操作是否合法
    private boolean operateValidFlag = true;

    public test() {
        //初始化函数
        init();
        // 设置计算器的背景颜色
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("计算器");
        // 在屏幕(500, 300)坐标处显示计算器
        this.setLocation(500, 300);
        // 允许修改计算器的大小
        this.setResizable(true);
        //设置可见
        this.setVisible(true);
        // 使计算器中各组件大小合适
        this.pack();
    }

    private void init() {
        // 文本框中的内容采用右对齐方式
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        // 不允许修改结果文本框
        resultText.setEditable(false);
        // 设置文本框背景颜色为白色
        resultText.setBackground(Color.WHITE);

        JPanel toppanel=new JPanel();
        toppanel.add(resultText);
        jbClean.setForeground(Color.red);
        toppanel.add(jbClean);

        JPanel calckeysPanel = new JPanel();
        calckeysPanel.setLayout(new GridLayout(4, 4));
        for(int i=0;i<KEYS.length;i++) {
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setForeground(Color.blue);
        }
        //设置监听
        for(int i=0;i<KEYS.length;i++) {
            keys[i].addActionListener(this);
        }
        jbClean.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(toppanel,BorderLayout.NORTH);
        this.add(calckeysPanel, BorderLayout.CENTER);
    }



    //时间监听
    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取事件源的标签
        String label = e.getActionCommand();
        if ("0123456789.".indexOf(label) >= 0) {
            // 用户按了数字键或者小数点键
            handleNumber(label);

        } else if(label=="clean") {
            // 用户按了运算符键
            resultText.setText("");
        }else {
            //用户按下了运算符键
            handleOperator(label);
        }

    }


    /**
     * 处理数字键被按下的事件
     *
     * @param key
     */
    private void handleNumber(String key) {
        if (firstDigit) {
            // 输入的第一个数字
            resultText.setText(key);
        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")) {
            // 如果输入的不是小数点，则将数字附在结果文本框的后面
            resultText.setText(resultText.getText() + key);
        }
        // 以后输入的肯定不是第一个数字了
        firstDigit = false;
    }

    /**
     * 处理运算符键被按下的事件
     *
     * @param key
     */
    private void handleOperator(String key) {
        if (operator.equals("/")) {
            // 除法运算
            // 如果当前结果文本框中的值等于0
            if (getNumberFromText() == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("除数不能为零");
            } else {
                resultNum /= getNumberFromText();
            }
        }  else if (operator.equals("+")) {
            // 加法运算
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // 减法运算
            resultNum -= getNumberFromText();
        } else if (operator.equals("*")) {
            // 乘法运算
            resultNum *= getNumberFromText();
        } else if(operator.equalsIgnoreCase("/")) {
            //除法运算
            resultNum=getNumberFromText();
        }
        else if (operator.equals("=")) {
            // 赋值运算
            resultNum = getNumberFromText();
        }

        if (operateValidFlag) {
            // 双精度浮点数的运算
            long t1;
            double t2;
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(resultNum));
            }
        }
        // 运算符等于用户按的按钮
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }

    /**
     * 从结果文本框中获取数字
     *
     * @return
     */
    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }

    public static void main(String[] args) {
        new test();
    }
}


