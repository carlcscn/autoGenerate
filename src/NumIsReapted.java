import java.io.File;

/**
 * Created by carlcs on 2019/8/8.
 */
public class NumIsReapted {
    public static boolean isRepeated(String dic,String Num){
        File file = new File(dic);
        File[] tempList = file.listFiles();
        int fileNum = tempList.length;
        for(int i=0;i<fileNum;i++){
            String fileName = tempList[i].getName();
            int end = fileName.length()-4;
            String fileName1 = fileName.substring(0,end);
            if(Num.equals(fileName1)){
                return true;
            }
        }
        return false;
    }

}
