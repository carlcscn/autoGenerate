import org.apache.pdfbox.exceptions.COSVisitorException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by liu_changshi on 2019/6/10.
 */
@SuppressWarnings("all")
public class MainFrame extends JFrame {
    static String path = null;
    static String targetPath = null;
    /*static String resultPath = null;*/
    static String scanGunContent=null;
    /*static int number=0;*/

    // 程序界面宽度
    public final int WIDTH = 1000;   /*530*/
    // 程序界面高度
    public final int HEIGHT = 850;   /*410*/
    private JTextField sourceExcelPathField = new JTextField();
    private JTextField targetPathField = new JTextField();
    private JTextField numberLocationRow = new JTextField();
    private JTextField numberLocationColumn = new JTextField();
    private JTextField resultLocationRow = new JTextField();
    private JTextField resultLocationColumn = new JTextField();
    private JTextField scanGunField = new JTextField();
    /*private JTextField numberOfProduntField = new JTextField();*/
    /*private JTextField resultPathField = new JTextField();*/
    private JTextField serialField = new JTextField();



    // 操作面板



    //基本设置区域
    /*"<html><body>"+"excel"+"<br>"+"文件路径"+"<body></html>"*/
    private JPanel basicSettingPanel = new JPanel();
    private JLabel sourceExcelFile = new JLabel("excel模板文件路径：");
    private JButton sourecExcelBtn1 = new JButton("浏览");
    private JButton sourceExcelBtn2 = new JButton("确定");
    private JLabel targetDicLabel = new JLabel("目标文件夹路径");
    private JButton targetDicBtn1 = new JButton("浏览");
    private JButton targetDicBtn2 = new JButton("确定");
    private JLabel numberLocationLabel = new JLabel("序列号填入位置 " +
            "如excel表格中对应5行c列，则分别填写5和3，务必分别回车确认");
    private JLabel numberLocationLabel0 = new JLabel("行");
    private JLabel numberLocationLabel1 = new JLabel("列");
    private JLabel resultLocationLabel = new JLabel("检测结果填入位置 要求同上");
    private JLabel resultLocationLabel0 = new JLabel("行");
    private JLabel resultLocationLabel1 = new JLabel("列");
    private JLabel serialLabel = new JLabel("查询并填入串口号 格式如COM1 注意字母全部大写 回车确认");


    //扫码枪输入区域
    private JPanel scanGunInputPanel  = new JPanel();
    private JLabel scanGunLabel = new JLabel("扫码枪输入数据" +
            "  F4键：获取当前器件报告" +
            "  F7键：将所有报告整合成一个文件");
    private JLabel numberOfProductLabel = new JLabel("当前器件序列号： " +
            "          注意整个工作过程中鼠标光标必须一直在方框内");
    private JLabel numberOfProductLabel2 = new JLabel("未扫描");
    /*private JLabel resultOfProductLabel = new JLabel("当前器件漏率值");
    private JLabel resultOfProductLabel2 = new JLabel("未获得漏率值");*/


    /*private JLabel resultFileLabel = new JLabel("设备测试结果路径");*/
    /*private JButton result1 = new JButton("浏览");
    private JButton result2 = new JButton("确定");*/

    //操作区域
    private JPanel operationPanel  = new JPanel();
    private JButton getTotalPdfFile = new JButton("将所有PDF文件整合");








    public MainFrame() {
        initView();
        initComponents();
        actionListener();


    }

    /**
     * 初始化窗口
     */
    private void initView() {

        // 关闭程序
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // 禁止窗口最大化
        setResizable(false);

        // 设置程序窗口居中显示
        Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setBounds(p.x - WIDTH / 2, p.y - HEIGHT / 2, WIDTH, HEIGHT);
        this.setLayout(null);
        setTitle("检漏仪结果自动输出程序   By lcs");
    }

    /**
     * 初始化控件
     */
    private void initComponents() {




        //基本设置区域设置
        basicSettingPanel.setBorder(BorderFactory.createTitledBorder("基本设置区域 （只需设置一次 如有更换另行设置）"));
        basicSettingPanel.setBounds(10,10, 970, 520);//220
        basicSettingPanel.setLayout(null);
        add(basicSettingPanel);

        //源excel文件相关设置
        sourceExcelFile.setFont(new Font("宋体",Font.PLAIN,25));
        /*sourceExcelFile.setForeground(Color.red);*/
        sourceExcelFile.setBounds(20,20,505,30);
        basicSettingPanel.add(sourceExcelFile);

        sourceExcelPathField.setFont(new Font("宋体",Font.PLAIN,25));
        sourceExcelPathField.setBounds(20,60,730,40);
        sourceExcelPathField.setVisible(true);
        basicSettingPanel.add(sourceExcelPathField);

        sourecExcelBtn1.setFont(new Font("宋体",Font.PLAIN,15));
        sourecExcelBtn1.setBounds(770, 60, 80, 40);
        sourceExcelBtn2.setFont(new Font("宋体",Font.PLAIN,15));
        sourceExcelBtn2.setBounds(870, 60, 80, 40);
        sourecExcelBtn1.setVisible(true);
        sourceExcelBtn2.setVisible(true);
        basicSettingPanel.add(sourecExcelBtn1);
        basicSettingPanel.add(sourceExcelBtn2);



        //目标文件夹相关设置
        targetDicLabel.setFont(new Font("宋体",Font.PLAIN,25));
        targetDicLabel.setBounds(20,120,505,30);
        basicSettingPanel.add(targetDicLabel);

        targetPathField.setFont(new Font("宋体",Font.PLAIN,25));
        targetPathField.setBounds(20,160,730,40);
        targetPathField.setVisible(true);
        basicSettingPanel.add(targetPathField);

        targetDicBtn1.setFont(new Font("宋体",Font.PLAIN,15));
        targetDicBtn1.setBounds(770, 160, 80, 40);
        targetDicBtn2.setFont(new Font("宋体",Font.PLAIN,15));
        targetDicBtn2.setBounds(870, 160, 80, 40);
        targetDicBtn1.setVisible(true);
        targetDicBtn2.setVisible(true);
        basicSettingPanel.add(targetDicBtn1);
        basicSettingPanel.add(targetDicBtn2);





        //序列号位置相关设置
        numberLocationLabel.setFont(new Font("宋体",Font.PLAIN,25));
        numberLocationLabel.setBounds(20,220,1000,30);
        basicSettingPanel.add(numberLocationLabel);

        numberLocationLabel0.setFont(new Font("宋体",Font.PLAIN,25));
        numberLocationLabel0.setBounds(20,260,50,30);
        basicSettingPanel.add(numberLocationLabel0);

        numberLocationLabel1.setFont(new Font("宋体",Font.PLAIN,25));
        numberLocationLabel1.setBounds(150,260,50,30);
        basicSettingPanel.add(numberLocationLabel1);

        numberLocationRow.setFont(new Font("宋体",Font.PLAIN,25));
        numberLocationRow.setBounds(50, 260, 60, 40);
        numberLocationRow.setVisible(true);
        basicSettingPanel.add(numberLocationRow);
        numberLocationColumn.setFont(new Font("宋体",Font.PLAIN,25));
        numberLocationColumn.setBounds(180, 260, 60, 40);
        numberLocationColumn.setVisible(true);
        basicSettingPanel.add(numberLocationColumn);

        //漏率结果相关设置
        resultLocationLabel.setFont(new Font("宋体",Font.PLAIN,25));
        resultLocationLabel.setBounds(20,320,1000,30);
        basicSettingPanel.add(resultLocationLabel);
        resultLocationLabel0.setFont(new Font("宋体",Font.PLAIN,25));
        resultLocationLabel0.setBounds(20,360,50,30);
        basicSettingPanel.add(resultLocationLabel0);
        resultLocationLabel1.setFont(new Font("宋体",Font.PLAIN,25));
        resultLocationLabel1.setBounds(150,360,50,30);
        basicSettingPanel.add(resultLocationLabel1);

        resultLocationRow.setFont(new Font("宋体",Font.PLAIN,25));
        resultLocationRow.setBounds(50, 360, 60, 40);
        resultLocationRow.setVisible(true);
        basicSettingPanel.add(resultLocationRow);
        resultLocationColumn.setFont(new Font("宋体",Font.PLAIN,25));
        resultLocationColumn.setBounds(180, 360, 60, 40);
        resultLocationColumn.setVisible(true);
        basicSettingPanel.add(resultLocationColumn);

        //串口名称相关设置
        serialLabel.setFont(new Font("宋体",Font.PLAIN,25));
        serialLabel.setBounds(20,420,900,30);
        basicSettingPanel.add(serialLabel);

        serialField.setFont(new Font("宋体",Font.PLAIN,25));
        serialField.setBounds(20,460,60,40);
        serialField.setVisible(true);
        basicSettingPanel.add(serialField);



        //扫码枪输入区域相关设置
        scanGunInputPanel.setBorder(BorderFactory.createTitledBorder("工作区域"));
        scanGunInputPanel.setBounds(10,550, 970, 250);//220
        scanGunInputPanel.setLayout(null);
        add(scanGunInputPanel);

        scanGunLabel.setFont(new Font("宋体",Font.PLAIN,25));
        scanGunLabel.setBounds(20,20,980,50);
        scanGunInputPanel.add(scanGunLabel);

        scanGunField.setFont(new Font("宋体",Font.PLAIN,25));
        scanGunField.setBounds(20,80,500,50);
        scanGunField.setVisible(true);
        scanGunInputPanel.add(scanGunField);

        numberOfProductLabel.setFont(new Font("宋体",Font.PLAIN,25));
        numberOfProductLabel.setBounds(20,140,980,50);
        scanGunInputPanel.add(numberOfProductLabel);

        numberOfProductLabel2.setFont(new Font("宋体",Font.PLAIN,25));
        numberOfProductLabel2.setBounds(20,180,980,50);
        scanGunInputPanel.add(numberOfProductLabel2);



    }









    /**
     * 按钮监听事件
     */
    private void actionListener() {
        // 串口



        sourecExcelBtn1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("请选择" +
                        "excel模板文件","xls");
                chooser.setFileFilter(filter);
                final int returnVal = chooser.showOpenDialog(sourecExcelBtn1);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    final File file = chooser.getSelectedFile();
                    path = file.getAbsolutePath();
                    sourceExcelPathField.setText(path);
                }
            }
        });



        sourceExcelBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                txtFileOperation.writeToTxt("path.txt",path);
                ShowUtils.message("excel模板加载成功");
            }
        });

        targetDicBtn1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser chooser1 = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","xls");
				/*chooser1.setFileFilter(filter);*/
                chooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                final int returnVal = chooser1.showOpenDialog(targetDicBtn1);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    final File file = chooser1.getSelectedFile();
                    targetPath = file.getAbsolutePath();
                    targetPathField.setText(targetPath);
                }
            }
        });



        targetDicBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                txtFileOperation.writeToTxt("targetPath.txt",targetPath);
                ShowUtils.message("目标文件夹设置成功");
            }
        });




        numberLocationRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String numberlocation;

                numberlocation = numberLocationRow.getText();
                txtFileOperation.writeToTxt("NumLocationRow.txt",numberlocation);
                ShowUtils.message("序列号行数设置成功");


            }
        });

        numberLocationColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String numberlocation;

                numberlocation = numberLocationColumn.getText();
                txtFileOperation.writeToTxt("NumLocationColumn.txt",numberlocation);
                ShowUtils.message("序列号列数设置成功");


            }
        });

        resultLocationRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String resultlocation;

                resultlocation = resultLocationRow.getText();
                txtFileOperation.writeToTxt("ResultLocationRow.txt",resultlocation);
                ShowUtils.message("漏率结果行数设置成功");


            }
        });

        resultLocationColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String resultlocation;

                resultlocation = resultLocationColumn.getText();
                txtFileOperation.writeToTxt("ResultLocationColumn.txt",resultlocation);
                ShowUtils.message("漏率结果列数设置成功");



            }
        });
        scanGunField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                return;
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if(KeyEvent.getKeyText(e.getKeyCode()).equals("F4")){
                    setResult.read("3F4C450D",txtFileOperation.readStringFromTxt("serialNum.txt"));




                }
                if(KeyEvent.getKeyText(e.getKeyCode()).equals("F7")){
                    try {
                        Excel2PdfUtil.getTotalPdf(txtFileOperation.readStringFromTxt("targetPath.txt"));
                    } catch (IOException e1) {
                        ShowUtils.errorMessage("合成pdf出错");
                        e1.printStackTrace();
                    } catch (COSVisitorException e1) {
                        ShowUtils.errorMessage("合成pdf出错");
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        ShowUtils.errorMessage("合成pdf出错");
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                return;
            }
        });
        scanGunField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String numberlocation;

                String scanGunContent = scanGunField.getText();



                numberOfProductLabel2.setText(scanGunContent);

                boolean isRepeated = NumIsReapted.isRepeated(txtFileOperation.readStringFromTxt("targetPath.txt"),scanGunContent);
                if(isRepeated==false){
                    int a1 = txtFileOperation.readIntFromTxt("NumLocationRow.txt")-1;
                    int b1 = txtFileOperation.readIntFromTxt("NumLocationColumn.txt")-1;
                    try {
                        excelOperation.setExcel(txtFileOperation.readStringFromTxt("path.txt"),a1,b1,scanGunContent);
                    } catch (IOException e1) {
                        ShowUtils.errorMessage("关闭模板excel文件");
                    }



                    scanGunField.setText("");

                }
                else{
                    ShowUtils.errorMessage("序列号重复！请检查后重新输入！");
                    scanGunField.setText("");
                }







            }
        });
        serialField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String serialContent = serialField.getText();
                txtFileOperation.writeToTxt("serialNum.txt",serialContent);
                ShowUtils.message("串口号设置成功");

            }
        });





    }



    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);

            }
        });

    }
}
