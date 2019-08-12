import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFMergerUtility;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Excel2PdfUtil {


	public static void  Ex2PDF(String inputFile, String pdfFile) {

		final int xlTypePDF = 0;

		ComThread.InitSTA(true);
		ActiveXComponent ax = new ActiveXComponent("Excel.Application");
		long date = new Date().getTime();
		ax.setProperty("Visible", new Variant(false));
		ax.setProperty("AutomationSecurity", new Variant(3)); // ���ú�
		try {
			Dispatch excels = ax.getProperty("Workbooks").toDispatch();
			Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method, new Object[] { inputFile, new Variant(false), new Variant(false) },
					new int[9]).toDispatch();
			File tofile = new File(pdfFile);
			if (tofile.exists()) {
				tofile.delete();
			}
			// ת����ʽ
			Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[] {
					new Variant(0), // PDF��ʽ=0
					pdfFile, new Variant(xlTypePDF), Variant.VT_MISSING, Variant.VT_MISSING, Variant.VT_MISSING, Variant.VT_MISSING,
					new Variant(false), Variant.VT_MISSING }, new int[1]);


		} catch (Exception e) {
			e.printStackTrace();
			/*return -1;*/
		} finally {
			if (ax != null) {
				ax.invoke("Quit", new Variant[] {});
				ax = null;
			}
			ComThread.Release();
		}
	}


	public static void getTotalPdf(String dic) throws IOException, COSVisitorException, InterruptedException {
		File preTotal = new File(dic+"\\total.pdf");
		if(preTotal.exists()){
			preTotal.delete();
		}

		File file = new File(dic);
		File[] tempList = file.listFiles();
		int fileNum = tempList.length;
		PDDocument[] doc = new PDDocument[fileNum];
		for(int i = 0;i<fileNum;i++){
			doc[i] = PDDocument.load(tempList[i]);
		}
		PDFMergerUtility PDFmerger = new PDFMergerUtility();
		PDFmerger.setDestinationFileName(dic+"\\"+"total.pdf");
		for(int i = 0;i<fileNum;i++){
			PDFmerger.addSource(tempList[i]);
		}
		PDFmerger.mergeDocuments();
		for(int i = 0;i<fileNum;i++){
			doc[i].close();
		}
		ShowUtils.message("合并所有测试结果成功"+"\t\n"+"结果文件文件为total.pdf");



	}





}
