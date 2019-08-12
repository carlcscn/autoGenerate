import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liu_changshi on 2019/6/10.
 */
public class copyAnotherExcel {
    public static void copy2(String readfile,String writeFile) {
        try {
            FileInputStream input = new FileInputStream(readfile);
            FileOutputStream output = new FileOutputStream(writeFile);
            int read = input.read();
            while ( read != -1 ) {
                output.write(read);
                read = input.read();
            }
            input.close();
            output.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
