import java.io.*;

/**
 * Created by liu_changshi on 2019/6/10.
 */
public class txtFileOperation {

    public static String readStringFromTxt(String fileName){
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String path = br.readLine();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int readIntFromTxt(String fileName){
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            int columnNum = Integer.parseInt(br.readLine());

            return columnNum;
        } catch (IOException e) {
            e.printStackTrace();
        }
        int j = 0;
        return j;
    }

    public static void writeToTxt(String fileName,String content){
        try {
            File writeName = new File(fileName); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(content+"\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
