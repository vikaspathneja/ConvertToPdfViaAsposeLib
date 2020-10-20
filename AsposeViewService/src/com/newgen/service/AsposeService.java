/**
 * 
 */
package com.newgen.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import com.aspose.cells.LoadFormat;
import com.aspose.cells.TxtLoadOptions;

/**
 * @author vikas
 *
 */
public class AsposeService {
	
	public static void main(String[] args) {
		int k = 0;
		Socket server = null;
		ServerSocket listener = null;
		try{
			listener = new ServerSocket(6789) ;

			Semaphore sem = new Semaphore(6,true);
			while (true) {
				k = k + 1;
				// doComms connection;
				System.out.println("Waiting for Request Count--" + k);
				server = listener.accept();
				DoComms conn_c = new DoComms(sem,server);
				Thread t = new Thread(conn_c);
				t.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (listener != null) {
					listener.close();
					listener = null;
					System.out.println("Closing Listener");
				}
				if (server != null) {
					server.close();
					server = null;
					System.out.println("Closing Server Socket");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception " + e.toString());
			}
		}
	}

}

/**Communication Class* */
class DoComms implements Runnable{
	private Socket server;
	private String input;
	Semaphore sem;
	
	public static final String strLicensePath = System.getProperty("user.dir") + File.separator + "SignatureCertificate"
			+ File.separator + "Aspose.Total.Java.lic";
	
	DoComms(Semaphore sem,Socket server) {
		this.server = server;
		this.sem=sem;
	}
	

	
	
	@Override
	public void run() {
		System.out.println("certificate Path :: "+strLicensePath);
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		try {
			sem.acquire();
			
			DataModel dmObin = null;
			DataModel dmOut = null;
			System.out.println("==========Start Listening for local Port==========:" + server.getRemoteSocketAddress());
			in = new  ObjectInputStream(server.getInputStream());
			out = new ObjectOutputStream(server.getOutputStream());			
			
			// Get input from client 
			
			dmObin = (DataModel)in.readObject();
			
			System.out.println(dmObin.getFileExtenion());

			
			ByteArrayOutputStream boutStream = null;
			boutStream = ngAsposeConversion(dmObin.getDocByteArray(),dmObin.getFileExtenion());
			
			// writing file to pdf
			String strOutFile = "D:\\Office\\Aspose\\converted-doc.pdf";
			byte[] docByteArray = boutStream.toByteArray();
			try(OutputStream fos = new FileOutputStream(strOutFile)){			
							
				fos.write(docByteArray);			
			}
			
			/************* Writing respose to client ******************/
			
			System.out.println("writing output in server");
			dmOut = new DataModel();
			dmOut.setBuf(boutStream);
			dmOut.setDocByteArray(docByteArray);
			dmOut.setBuf(boutStream);
			
			
			out.writeObject(dmOut);
			System.out.println("writing output in server");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (server != null) {
					System.out.println("====Closing local Cient Socket=======" + server.getRemoteSocketAddress());
					server.close();
					server = null;
					System.out.println("====Successfuly Closed=======");

				}
			} catch (Exception e) {
				System.out.println("Exception " + e.toString());
			}
		}
		sem.release();

		
	}
	
 	public ByteArrayOutputStream ngAsposeConversion(byte[] docByteArray, String doctype) {

 		//byte[] docByteArray = boutStream.toByteArray();
 		ByteArrayOutputStream boutStream = null;
 		System.out.println("Conversion ");

 		if ("doc".equalsIgnoreCase(doctype) || "docx".equalsIgnoreCase(doctype)) {
 			System.out.println("Conversion doc");
 			InputStream inputStream = new ByteArrayInputStream(docByteArray);
 			boutStream = ngConvertWordToPDF(inputStream);
 		} else if ("xls".equalsIgnoreCase(doctype) || "xlsx".equalsIgnoreCase(doctype)) {
 			InputStream inputStream = new ByteArrayInputStream(docByteArray);
 			boutStream = ngConvertSpreadsheetToPDF(inputStream);
 		} else if ("csv".equalsIgnoreCase(doctype)) {
 			InputStream inputStream = new ByteArrayInputStream(docByteArray);
 			boutStream = ngConvertCsvToPDF(inputStream);
 		} else if ("ppt".equalsIgnoreCase(doctype) || "pptx".equalsIgnoreCase(doctype)) {
 			InputStream inputStream = new ByteArrayInputStream(docByteArray);
 			boutStream = ngConvertPptToPDF(inputStream);
 		}

 		return boutStream;

 	}
 	
	public ByteArrayOutputStream ngConvertPptToPDF(InputStream inputStream) {
 		ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
 		try {
 			com.aspose.slides.License license = new com.aspose.slides.License();
 			license.setLicense(strLicensePath);
 			com.aspose.slides.Presentation presentation = new com.aspose.slides.Presentation(inputStream);
 			presentation.save(dstStream, com.aspose.slides.SaveFormat.Pdf);
 		} catch (Exception ee) {
 			ee.getMessage();
 		}
 		return dstStream;
 	}

 	public ByteArrayOutputStream ngConvertSpreadsheetToPDF(InputStream inputStream) {
 		ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
 		try {
 			com.aspose.cells.License license = new com.aspose.cells.License();
 			license.setLicense(strLicensePath);
 			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(inputStream);

 			/**
 			 * Code Added by vikas for Showing Text Hidden in Spreadsheat to show in pdf
 			 **/
 			com.aspose.cells.AutoFitterOptions autofitteroptions = new com.aspose.cells.AutoFitterOptions();
 			// Keeps the hidden columns hidden
 			autofitteroptions.setIgnoreHidden(true);
 			// To show the hidden column content
 			int iSheetCount = workbook.getWorksheets().getCount();
 			for (int i = 0; i < iSheetCount; i++) {
 				workbook.getWorksheets().get(i).autoFitColumns(autofitteroptions);
 			}

 			/** ended by vikas */

 			/**
 			 * Code Added by vikas for pdf options for say save complete sheet as single
 			 * page , saving all columns of sheet in single page
 			 */
 			com.aspose.cells.PdfSaveOptions pdfsaveoptions = new com.aspose.cells.PdfSaveOptions();
 			// For saving all columns in a single page
 			// pdfsaveoptions.setAllColumnsInOnePagePerSheet(true);
 			// For save a complete worksheet as a single page
 			pdfsaveoptions.setOnePagePerSheet(true);
 			pdfsaveoptions.setCalculateFormula(true);
 			/** ended by vikas */

 			workbook.save(dstStream, pdfsaveoptions);
 			//workbook.save(dstStream, com.aspose.cells.SaveFormat.PDF);

 		} catch (Exception ee) {
 			ee.getMessage();
 		}
 		return dstStream;
 	}

 	// CsvToPdf
 	public ByteArrayOutputStream ngConvertCsvToPDF(InputStream inputStream) {
 		ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
 		try {
 			com.aspose.cells.License license = new com.aspose.cells.License();
 			license.setLicense(strLicensePath);

 			TxtLoadOptions opts = new TxtLoadOptions(LoadFormat.CSV);
 			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(inputStream, opts);

 			/** Code Added by vikas for Showing Text Hidden in csv to show in pdf **/
 			com.aspose.cells.AutoFitterOptions autofitteroptions = new com.aspose.cells.AutoFitterOptions();
 			// Keeps the hidden columns hidden
 			autofitteroptions.setIgnoreHidden(false);
 			// To show the hidden column content
 			int iSheetCount = workbook.getWorksheets().getCount();
 			for (int i = 0; i < iSheetCount; i++) {
 				workbook.getWorksheets().get(i).autoFitColumns(autofitteroptions);
 			}

 			/** ended by vikas */

 			/**
 			 * Code Added by vikas for pdf options for say save complete sheet as single
 			 * page , saving all columns of sheet in single page
 			 */
 			com.aspose.cells.PdfSaveOptions pdfsaveoptions = new com.aspose.cells.PdfSaveOptions();
 			// For saving all columns in a single page
 			// For save a complete worksheet as a single page
 			pdfsaveoptions.setOnePagePerSheet(true);
 			pdfsaveoptions.setCalculateFormula(true);
 			/** ended by vikas */

 			workbook.save(dstStream, pdfsaveoptions);

 			// logger.getStatusLog().info("after conversion/save of csv file as pdf ");
 		} catch (Exception ee) {
 			// logger.getStatusLog().info("exception in converting csv to pdf document");
 			ee.getMessage();
 		}
 		return dstStream;
 	}


 	public ByteArrayOutputStream ngConvertWordToPDF(InputStream isStream) {
 		ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
 		// logger.getStatusLog().info("inside ngConvertWordToPDF");
 		try {
 			// logger.getStatusLog().info("strLicensePath=="+strLicensePath);
 			com.aspose.words.License license = new com.aspose.words.License();
 			license.setLicense(strLicensePath);
 			// logger.getStatusLog().info("isStream bytes available to
 			// read="+isStream.available());
 			com.aspose.words.Document document = new com.aspose.words.Document(isStream);
 			document.save(dstStream, com.aspose.words.SaveFormat.PDF);
 			// logger.getStatusLog().info("after save of doc file as pdf in d path");
 		} catch (Exception ee) {
 			// logger.getStatusLog().info("exception in converting word to pdf document");
 			ee.getMessage();
 		}
 		return dstStream;
 	}
    
	
}



