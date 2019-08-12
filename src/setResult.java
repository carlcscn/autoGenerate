import gnu.io.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * Created by liu_changshi on 2019/6/20.
 */
public class setResult {
    public static final ArrayList<String> findPorts() {
        // 获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<String>();
        // 将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

    public static final SerialPort openPort(String portName, int baudrate) throws PortInUseException {
        try {
            // 通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            // 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);
            // 判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                try {
                    // 设置一下串口的波特率等参数
                    // 数据位：8
                    // 停止位：1
                    // 校验位：None
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                }
                return serialPort;
            }
        } catch (NoSuchPortException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
        }
    }

    public static void sendToPort(SerialPort serialPort, byte[] order) {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = {};
        try {
            in = serialPort.getInputStream();
            // 缓冲区大小为一个字节
            byte[] readBuffer = new byte[1];
            int bytesNum = in.read(readBuffer);
            while (bytesNum > 0) {
                bytes = ArrayUtils.concat(bytes, readBuffer);
                bytesNum = in.read(readBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }


    public static void addListener(SerialPort serialPort, DataAvailableListener listener) {
        try {
            // 给串口添加监听器
            serialPort.addEventListener(new SerialPortListener(listener));
            // 设置当有数据到达时唤醒监听接收线程
            serialPort.notifyOnDataAvailable(true);
            // 设置当通信中断时唤醒中断线程
            serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }


    public static class SerialPortListener implements SerialPortEventListener {

        private DataAvailableListener mDataAvailableListener;

        public SerialPortListener(DataAvailableListener mDataAvailableListener) {
            this.mDataAvailableListener = mDataAvailableListener;
        }

        public void serialEvent(SerialPortEvent serialPortEvent) {
            switch (serialPortEvent.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE: // 1.串口存在有效数据
                    if (mDataAvailableListener != null) {
                        mDataAvailableListener.dataAvailable();
                    }
                    break;

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2.输出缓冲区已清空
                    break;

                case SerialPortEvent.CTS: // 3.清除待发送数据
                    break;

                case SerialPortEvent.DSR: // 4.待发送数据准备好了
                    break;

                case SerialPortEvent.RI: // 5.振铃指示
                    break;

                case SerialPortEvent.CD: // 6.载波检测
                    break;

                case SerialPortEvent.OE: // 7.溢位（溢出）错误
                    break;

                case SerialPortEvent.PE: // 8.奇偶校验错误
                    break;

                case SerialPortEvent.FE: // 9.帧错误
                    break;

                case SerialPortEvent.BI: // 10.通讯中断
                    /*ShowUtils.errorMessage("与串口设备通讯中断");*/
                    break;

                default:
                    break;
            }
        }
    }




    public interface DataAvailableListener {
        /**
         * 串口存在有效数据
         */
        String dataAvailable();
    }



    public static void read(String data1,String serialNum) {



        try {
            final SerialPort a = openPort(serialNum,9600);
            setResult.sendToPort(a, ByteUtils.hexStr2Byte(data1));
            setResult.addListener(a, new setResult.DataAvailableListener() {

                @Override
                public String dataAvailable() {
                    byte[] data = null;
                    String hexData=null;
                    try {
                        if (a == null) {

                        } else {
                            data = setResult.readFromPort(a);
                            hexData = ByteUtils.byteArrayToHexString(data);

                            if(hexData.length()>=12){
                                String b = hexData.substring(0,12);
                                String ascii = ArrayUtils.hexToAscii(b);
                                int chr0 = ascii.charAt(0);
                                int chr1 = ascii.charAt(1);
                                int chr2 = ascii.charAt(2);
                                int chr4 = ascii.charAt(4);
                                int chr5 = ascii.charAt(5);
                                if(chr0>=48&&chr0<=57&&chr1>=48&&chr1<=57&&chr2>=48&&chr2<=57&&chr4>=48&&chr4<=57&&chr5>=48&&chr5<=57){
                                    String bb = ArrayUtils.zhuanbiaozhun(ascii);
                                    int a1 = txtFileOperation.readIntFromTxt("NumLocationRow.txt") - 1;
                                    int b1 = txtFileOperation.readIntFromTxt("NumLocationColumn.txt")-1;
                                    int c = txtFileOperation.readIntFromTxt("ResultLocationRow.txt")-1;
                                    int d = txtFileOperation.readIntFromTxt("ResultLocationColumn.txt")-1;
                                    String path = txtFileOperation.readStringFromTxt("path.txt");
                                    String targetPath = txtFileOperation.readStringFromTxt("targetPath.txt");
                                    excelOperation.setExcel(path,c,d,bb);
                                    String SID = excelOperation.getExcel(path,a1,b1);
                                    copyAnotherExcel.copy2(path, targetPath+"/"+SID+".xls");

                                    Excel2PdfUtil.Ex2PDF(targetPath+"/"+SID+".xls",targetPath+"/"+SID+".pdf");
                                    closePort(a);
                                    File excel = new File(targetPath+"/"+SID+".xls");
                                    excel.delete();
                                    ShowUtils.message("写入成功"+"\t\n"+"产品序列号为： "+SID+"" +
                                            "\t\n"+"漏率值为： "+bb);

                                }
                                else{
                                    closePort(a);
                                    ShowUtils.errorMessage("检漏仪返回指令错误，请重新按F4键获取报告");
                                }


                            }
                            else{
                                closePort(a);
                                ShowUtils.errorMessage("检漏仪返回指令错误，请重新按F4键获取报告");
                            }










                        }
                    } catch (Exception e) {

                        // 发生读取错误时显示错误信息后退出系统
                        System.exit(0);
                    }
                    return hexData;
                }
            });
            System.out.println();
        } catch (PortInUseException e) {
            e.printStackTrace();

        }





    }


}
