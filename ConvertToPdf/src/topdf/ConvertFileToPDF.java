package topdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.aspose.cad.system.io.MemoryStream;
import com.aspose.cells.LoadFormat;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.TxtLoadOptions;
import com.aspose.email.EmlLoadOptions;
import com.aspose.email.MailMessage;
import com.aspose.email.MsgLoadOptions;
import com.aspose.email.SaveOptions;
import com.aspose.pdf.LoadOptions;
//import com.aspose.words.Document;
import com.aspose.words.Document;


public class ConvertFileToPDF {

	public static String strLicensePath = "D:\\office\\Aspose\\Aspose.Total.Java.lic";

	public static void main(String[] args) {
		InputStream isInputstream = null;
		try {
//			String strInFile = "D:\\office\\Aspose\\jdbc.ppt";
//			String strOutFile = "D:\\\\office\\\\Aspose\\\\jdbc.pdf";

			String strInFile = "D:\\office\\Aspose\\sample.msg";
			String strOutFile = "D:\\office\\Aspose\\samplemsg.pdf";

//			String strInFile = "D:\\office\\Aspose\\DummySheet.xlsx";
//			String strOutFile = "D:\\\\office\\\\Aspose\\\\DummySheet.pdf";

			
			isInputstream = new FileInputStream(strInFile);
			String strExtension = getExtension(strInFile);
			
//			System.out.println("strExtension=="+strExtension);
			
			// Get doc file formats
			// FileFormatInfo info = FileFormatUtil.detectFileFormat(isInputstream);
			// System.out.println("The document format is: " +
			// FileFormatUtil.loadFormatToExtension(info.getLoadFormat()));
			// System.out.println("Document is encrypted: " + info.isEncrypted());
			// System.out.println("Document has a digital signature: " +
			// info.hasDigitalSignature());
			// System.out.println("File Format of input file is : " + info.getLoadFormat());
			if(".msg".equalsIgnoreCase(strExtension)) {
				EmailToPdf(strInFile);
			}
			if (".doc".equalsIgnoreCase(strExtension) || ".docx".equalsIgnoreCase(strExtension)) {
				FileOutputStream fos = new FileOutputStream(strOutFile);
				ngConvertWordToPDF(isInputstream, fos);
				fos.close();
			}
			if (".xls".equalsIgnoreCase(strExtension) || ".xlsx".equalsIgnoreCase(strExtension)) {
				FileOutputStream fos = new FileOutputStream(strOutFile);
				ngConvertExcelToPDF(isInputstream, fos);
				fos.close();
			}
			if (".csv".equalsIgnoreCase(strExtension) ) {
				FileOutputStream fos = new FileOutputStream(strOutFile);
				ngConvertCsvToPDF(isInputstream, fos);
				fos.close();
			}
			if (".ppt".equalsIgnoreCase(strExtension) || ".pptx".equalsIgnoreCase(strExtension)) {
				FileOutputStream fos = new FileOutputStream(strOutFile);
				ngConvertPresentationToPDF(isInputstream, fos);
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != isInputstream) {
					isInputstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			isInputstream = null;
		}
	}

	public static void ngConvertFileToPDF(String strInFile, String strOutFile) throws FileNotFoundException {
		FileInputStream isInputstream = new FileInputStream(strInFile);
		try {
			String strExtension = getExtension(strInFile);
			if (".doc".equalsIgnoreCase(strExtension) || ".docx".equalsIgnoreCase(strExtension)) {
				FileOutputStream fos = new FileOutputStream(strOutFile);
				ngConvertWordToPDF(isInputstream, fos);
				fos.close();
			}
			if (".xls".equalsIgnoreCase(strExtension) || ".xlsx".equalsIgnoreCase(strExtension) || ".csv".equalsIgnoreCase(strExtension)) {
				FileOutputStream fos = new FileOutputStream(strOutFile);
				ngConvertExcelToPDF(isInputstream, fos);
				fos.close();
			}
			if (".ppt".equalsIgnoreCase(strExtension) || ".pptx".equalsIgnoreCase(strExtension)) {
				FileOutputStream fos = new FileOutputStream(strOutFile);
				ngConvertPresentationToPDF(isInputstream, fos);
				fos.close();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != isInputstream) {
					isInputstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			isInputstream = null;
		}
	}

	public static String getExtension(String filename) {
		String strExtension = null;
		if (filename.lastIndexOf('.') > 0) {
			// get last index for '.' char
			int lastIndex = filename.lastIndexOf('.');
			// get extension
			strExtension = filename.substring(lastIndex);
		}
		return strExtension;
	}

	// Input -
	// Microsoft Word: DOC, DOCX, RTF, DOT, DOTX, DOTM, DOCM FlatOPC,
	// FlatOpcMacroEnabled, FlatOpcTemplate, FlatOpcTemplateMacroEnabled
	// OpenOffice: ODT, OTT
	// WordprocessingML: WordML
	// Web: HTML, MHTML
	// Text: TXT
	// Output - PDF. DOCX, HTML are commented
	// https://products.aspose.com/words/java
	public static void ngConvertWordToPDF(InputStream isStream, OutputStream osStream) throws Exception {
		com.aspose.words.License license = new com.aspose.words.License();
		license.setLicense(strLicensePath);
		com.aspose.words.Document document = new com.aspose.words.Document(isStream);
		document.save(osStream, com.aspose.words.SaveFormat.PDF);
		// document.save(osStream, com.aspose.words.SaveFormat.DOCX);
		// document.save(osStream, com.aspose.words.SaveFormat.HTML);
	}

	// Input -
	// Microsoft Excel: XLS, XLT, XLSX, XLSB, XLTX, XLTM, XLSM, XML
	// OpenOffice: ODS
	// Text: CSV, TSV
	// Web: HTML, MHTML
	// Numbers: Apple's iWork office suite Numbers app documents
	// SXC, FODS
	// Output - PDF. XPS, HTML are commented
	// https://products.aspose.com/cells/java
	public static void ngConvertExcelToPDF(InputStream isStream, OutputStream osStream) throws Exception {
		com.aspose.cells.License license = new com.aspose.cells.License();
		license.setLicense(strLicensePath);
		com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(isStream);
		
		/**Code Added by vikas for Showing Text Hidden in Spreadsheat to show in pdf**/
		com.aspose.cells.AutoFitterOptions autofitteroptions = new com.aspose.cells.AutoFitterOptions();
		//Keeps the hidden columns hidden
		autofitteroptions.setIgnoreHidden(false);
		//To show the hidden column content
		int iSheetCount = workbook.getWorksheets().getCount();
		for (int i = 0; i < iSheetCount; i++) {
		workbook.getWorksheets().get(i).autoFitColumns(autofitteroptions);
		}
		
		/** ended by vikas*/
		

		/**Code Added by vikas for pdf options for say save complete sheet as single page , saving all columns of sheet in single page*/
		com.aspose.cells.PdfSaveOptions pdfsaveoptions = new com.aspose.cells.PdfSaveOptions();
//		For saving all columns in a single page
//		pdfsaveoptions.setAllColumnsInOnePagePerSheet(true);
//		For save a complete worksheet as a single page
		pdfsaveoptions.setOnePagePerSheet(true);
		pdfsaveoptions.setCalculateFormula(true);
		/** ended by vikas*/

		
		
		
		workbook.save(osStream, pdfsaveoptions);
//		workbook.save(osStream, com.aspose.cells.SaveFormat.PDF);
		// workbook.save(osStream, com.aspose.cells.SaveFormat.XPS);
		// workbook.save(osStream, com.aspose.cells.SaveFormat.HTML);
	}
	
	
	

	 static void EmailToPdf(String emailPath) throws Exception

	{
		 
		 
		 FileInputStream fstream=new FileInputStream(emailPath);

	       MailMessage eml = MailMessage.load(fstream);
	       ByteArrayOutputStream emlStream = new ByteArrayOutputStream();
	       System.out.println("before=="+emlStream.size());
	       eml.save(emlStream, SaveOptions.getDefaultMhtml());
	       System.out.println("emlStream==="+emlStream.size());
	       com.aspose.words.LoadOptions lo=new com.aspose.words.LoadOptions();
	       lo.setLoadFormat(com.aspose.words.LoadFormat.MHTML);
	       ByteArrayInputStream bis=new ByteArrayInputStream(emlStream.toByteArray()); 
	       System.out.println("bis size=="+bis.available());
	       Document doc = new Document(bis,lo);
	       System.out.println("doc Name=="+doc.getText());
	       doc.save("samplemsg.pdf", com.aspose.words.SaveFormat.PDF);
	       //	      FileInputStream fstream=new FileInputStream(emailPath);
//
//	     //EML load options
//	       EmlLoadOptions lo = new EmlLoadOptions();
//	       lo.setPreserveTnefAttachments(true);
//
////	       MailMessage eml = MailMessage.load(fstream, lo);
////
////	       //MSG load options
////	       MsgLoadOptions loMsg = new MsgLoadOptions();
////	       loMsg.setPreserveTnefAttachments(true);
////
////	       MailMessage msg = MailMessage.load(fstream, loMsg);
////	       
//	       
//	       
//	       MailMessage eml = MailMessage.load(fstream);
//
//	       //Save the Message to output stream in MHTML format
//
//	       ByteArrayOutputStream emlStream = new ByteArrayOutputStream();
//
//	       eml.save(emlStream, SaveOptions.getDefaultMhtml());
//
//	       //Load the stream in Word document
//
////	       LoadOptions lo = new LoadOptions();
//
//	       lo.setLoadFormat(LoadFormat.M_HTML);
//
//	       Document doc = new Document(
//	    		   new ByteArrayInputStream(emlStream.toByteArray()), lo);
//
//	       //Save to disc
//
//	       doc.save("About Aspose.Pdf", SaveFormat.PDF);
//
//	       //or Save to stream
//
//	       ByteArrayOutputStream foStream = new ByteArrayOutputStream();
//	       Document doc=new Document();
//	       doc.save(emlStream, SaveFormat.PDF);

	}


	
	
	
	
	
	public static void ngConvertCsvToPDF(InputStream isStream, OutputStream osStream) throws Exception {
		com.aspose.cells.License license = new com.aspose.cells.License();
		license.setLicense(strLicensePath);
		TxtLoadOptions opts = new TxtLoadOptions(LoadFormat.CSV);
		com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(isStream,opts);
		
		
		
		/**Code Added by vikas for Showing Text Hidden in Spreadsheat to show in pdf**/
		com.aspose.cells.AutoFitterOptions autofitteroptions = new com.aspose.cells.AutoFitterOptions();
		//Keeps the hidden columns hidden
		autofitteroptions.setIgnoreHidden(false);
		//To show the hidden column content
		int iSheetCount = workbook.getWorksheets().getCount();
		for (int i = 0; i < iSheetCount; i++) {
		workbook.getWorksheets().get(i).autoFitColumns(autofitteroptions);
		}
		
		/** ended by vikas*/
		

		/**Code Added by vikas for pdf options for say save complete sheet as single page , saving all columns of sheet in single page*/
		com.aspose.cells.PdfSaveOptions pdfsaveoptions = new com.aspose.cells.PdfSaveOptions();
		//For saving all columns in a single page
		pdfsaveoptions.setAllColumnsInOnePagePerSheet(true);
		//For save a complete worksheet as a single page
//		pdfsaveoptions.setOnePagePerSheet(true);
		pdfsaveoptions.setCalculateFormula(true);
		/** ended by vikas*/

		
		
		
		workbook.save(osStream, pdfsaveoptions);
//		workbook.save(osStream, com.aspose.cells.SaveFormat.PDF);
		// workbook.save(osStream, com.aspose.cells.SaveFormat.XPS);
		// workbook.save(osStream, com.aspose.cells.SaveFormat.HTML);
	}

	
	

	// Input -
	// Microsoft PowerPoint: PPT, PPTX, PPS, POT, PPSX, PPTM, PPSM, POTX, POTM
	// OpenOffice: ODP
	// Output - PDF. Xps, Tiff are commented
	// https://products.aspose.com/slides/java
	public static void ngConvertPresentationToPDF(InputStream isStream, OutputStream osStream) {
		com.aspose.slides.License license = new com.aspose.slides.License();
		license.setLicense(strLicensePath);
		com.aspose.slides.Presentation presentation = new com.aspose.slides.Presentation(isStream);
		presentation.save(osStream, com.aspose.slides.SaveFormat.Pdf);
		// presentation.save(osStream, com.aspose.slides.SaveFormat.Xps);
		// presentation.save(osStream, com.aspose.slides.SaveFormat.Tiff);
	}

	// Input -
	// Microsoft OneNote: ONE
	// Output - PDF. Jpeg, Tiff are commented
	// https://products.aspose.com/note/java
	public static void ngConvertOneNoteToPDF(InputStream isStream, OutputStream osStream) throws IOException {
		com.aspose.note.License license = new com.aspose.note.License();
		license.setLicense(strLicensePath);
		com.aspose.note.Document document = new com.aspose.note.Document(isStream);
		document.save(osStream, com.aspose.note.SaveFormat.Pdf);
		// document.save(osStream, com.aspose.note.SaveFormat.Jpeg);
		// document.save(osStream, com.aspose.note.SaveFormat.Tiff);
	}

	// Input -
	// Microsoft Project: MPP, MPT, MPX, XML
	// Output - PDF. HTML, TIFF are commented
	// https://products.aspose.com/tasks/java
	public static void ngConvertProjectToPDF(InputStream isStream, OutputStream osStream) throws Exception {
		com.aspose.tasks.License license = new com.aspose.tasks.License();
		license.setLicense(strLicensePath);
		com.aspose.tasks.Project project = new com.aspose.tasks.Project(isStream);
		project.save(osStream, com.aspose.tasks.SaveFileFormat.PDF);
		// project.save(osStream, com.aspose.tasks.SaveFileFormat.HTML);
		// project.save(osStream, com.aspose.tasks.SaveFileFormat.TIFF);
	}

	// Input -
	// PDF
	// Output - DOCX PPTX, HTML are commented
	// https://products.aspose.com/pdf/java
	public static void ngConvertPDF(InputStream isStream, OutputStream osStream) throws Exception {
		com.aspose.words.License license = new com.aspose.words.License();
		license.setLicense(strLicensePath);
		com.aspose.words.Document document = new com.aspose.words.Document(isStream);
		document.save(osStream, com.aspose.words.SaveFormat.DOCX);
		// document.save(osStream, com.aspose.words.SaveFormat.Pptx);
		// document.save(osStream, com.aspose.words.SaveFormat.Html);
	}

	// Input -
	// DWG Release 11, 12, 13, 14
	// DWG 2000/2000i/2002
	// DWG 2004/2005/2006
	// DWG 2010/2011/2012
	// DWG 2013/2014/2015/2016
	// AutoCAD DXF
	// Autodesk DWF
	// DGN
	// Industry Foundation Classes (IFC)
	// STereoLithography (STL)
	// Output - PDF
	// https://products.aspose.com/cad/java
	public static void ngConvertCADtoPDF(String inFile, String outFile) {
		com.aspose.cad.License license = new com.aspose.cad.License();
		license.setLicense(strLicensePath);
		// load CAD for conversion
		com.aspose.cad.Image image = com.aspose.cad.Image.load(inFile);
		// create instance & set resultant page size
		com.aspose.cad.imageoptions.CadRasterizationOptions rasterizationOptions = new com.aspose.cad.imageoptions.CadRasterizationOptions();
		rasterizationOptions.setPageSize(new com.aspose.cad.SizeF(1200, 1200));
		// save PDF
		com.aspose.cad.imageoptions.PdfOptions pdfOptions = new com.aspose.cad.imageoptions.PdfOptions();
		pdfOptions.setVectorRasterizationOptions(rasterizationOptions);
		image.save(outFile);
	}

	
	
//	public static void convertbyterArray(byte[] initialArray) {
//		 InputStream targetStream = new ByteArrayInputStream(initialArray);
//		Document doc = new Document(targetStream);
//		MemoryStream docStream = new MemoryStream();
//		doc.save(docStream, com.aspose.words.SaveFormat.DOC);
		// This represents the input byte array
//		byte[] docBytes = docStream.toArray();
		// Load Word Document from this byte array
//		Document loadedFromBytes = new Document(new MemoryStream(docBytes));
		// Save Word to PDF byte array
//		MemoryStream pdfStream = new MemoryStream();
//		loadedFromBytes.save(pdfStream, com.aspose.words.SaveFormat.PDF;
//		byte[] pdfBytes = pdfStream.toArray();
//		doc.save(stream, saveFormat)
//	}
	
	
	/**
	 * Converts an image to PDF using Aspose.Words for Java.
	 *
	 * @param inputFileName
	 *            File name of input image file.
	 * @param outputFileName
	 *            Output PDF file name.
	 * @throws Exception
	 */
	public static void ngConvertImageToPDF(String inputFileName, String outputFileName) throws Exception {
		com.aspose.words.License license = new com.aspose.words.License();
		license.setLicense(strLicensePath);
		// Create Aspose.Words.Document and DocumentBuilder.
		// The builder makes it simple to add content to the document.
		com.aspose.words.Document document = new com.aspose.words.Document();
		com.aspose.words.DocumentBuilder builder = new com.aspose.words.DocumentBuilder(document);

		// Load images from the disk using the appropriate reader.
		// The file formats that can be loaded depends on the image readers available on
		// the machine.
		ImageInputStream iis = ImageIO.createImageInputStream(new File(inputFileName));
		ImageReader reader = ImageIO.getImageReaders(iis).next();
		reader.setInput(iis, false);

		// Get the number of frames in the image.
		int framesCount = reader.getNumImages(true);

		// Loop through all frames.
		for (int frameIdx = 0; frameIdx < framesCount; frameIdx++) {
			// Insert a section break before each new page, in case of a multi-frame image.
			if (frameIdx != 0) {
				builder.insertBreak(com.aspose.words.BreakType.SECTION_BREAK_NEW_PAGE);
			}
			// Select active frame.
			BufferedImage image = reader.read(frameIdx);

			// We want the size of the page to be the same as the size of the image.
			// Convert pixels to points to size the page to the actual image size.
			com.aspose.words.PageSetup ps = builder.getPageSetup();
			ps.setPageWidth(com.aspose.words.ConvertUtil.pixelToPoint(image.getWidth()));
			ps.setPageHeight(com.aspose.words.ConvertUtil.pixelToPoint(image.getHeight()));

			// Insert the image into the document and position it at the top left corner of
			// the page.
			builder.insertImage(image, com.aspose.words.RelativeHorizontalPosition.PAGE, 0,
					com.aspose.words.RelativeVerticalPosition.PAGE, 0, ps.getPageWidth(), ps.getPageHeight(),
					com.aspose.words.WrapType.NONE);
		}

		if (iis != null) {
			iis.close();
			reader.dispose();
		}
		document.save(outputFileName);
	}
	// ExEnd:ConvertImageToPDF
}
