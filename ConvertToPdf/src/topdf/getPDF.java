
package topdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newgen.niplj.codec.pdf.*;
import com.newgen.niplj.NIPLJ;
import com.newgen.niplj.fileformat.Tif6;
import com.newgen.niplj.generic.NGIMException;
import com.newgen.niplj.io.RandomInputStreamSource;
import com.newgen.niplj.io.RandomOutputStreamSource;

/**
 * Servlet implementation class INIServlet
 */
// @WebServlet("/INIServlet")
// servelt for IVApplet
public class getPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public getPDF() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RandomInputStreamSource riss = null;
		ByteArrayOutputStream pdfStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ServletOutputStream sos = response.getOutputStream();
		RandomOutputStreamSource ros = new RandomOutputStreamSource(new ByteArrayOutputStream());
		NGIP_PDFImplementation pdfobj = new NGIP_PDFImplementation();;
		try {
			System.out.println(request.getQueryString());
			String strImageURL = request.getParameter("strImageURL");
			URL Imageurl = new URL(strImageURL);
			String pageNo = request.getParameter("PageNo");
			riss = new RandomInputStreamSource(Imageurl, 5000);
			Thread t = new Thread(riss);
			t.start();
			int iFileFormat = NIPLJ.getFileFormat(riss);
			System.out.println("fileFormat = " + iFileFormat);
			if (null == pageNo) { // full document to pdf
				if ((pdfStream = (pdfobj = new NGIP_PDFImplementation()).convertImageToPDF(Imageurl)) == null) {
					throw new NGIMException(-7081);
				}
				sos = response.getOutputStream();
				sos.write(pdfStream.toByteArray());
				pdfStream.close();
			} else {
				try {
					riss.getStream().setCurrentPosition(0);
					int iPageNo = Integer.parseInt(pageNo);
					if (NIPLJ.TIFF_FORMAT == iFileFormat) {
						Tif6.retrieveTif6Page(riss.getStream(), iPageNo, ros.getStream());
						outputStream.write(ros.getStream().getByteSource());
						ros.getStream().close();
						pdfStream = pdfobj.convertImageToPDF(outputStream);
						sos = response.getOutputStream();
						sos.write(pdfStream.toByteArray());
						riss.getStream().close();
						pdfStream.close();
						sos.close();
					} else if (NIPLJ.PDF_FORMAT == iFileFormat) {
						PDF pdfReader = PDF.getPDFInReadMode(Imageurl, null);
						try {
							int iNoOfPages = pdfReader.getNumberOfPages();
							if (iPageNo > iNoOfPages)
								throw new NGIMException(NGIMException.NGIM_ERR_INVALID_PAGE_NUMBER);
							pdfReader.extractPage(iPageNo, response.getOutputStream());
							pdfReader.close();
							System.out.println("PDF2\n");
						} catch (Exception e) {
							if (null != pdfReader) {
								pdfReader.close();
							}
							sos = response.getOutputStream();
							sos.write("Error occured on server while conversion".getBytes());
						}
					}
					else if (NIPLJ.JPEG_FORMAT == iFileFormat || NIPLJ.BMP_FORMAT == iFileFormat
							|| NIPLJ.PNG_FORMAT == iFileFormat || NIPLJ.GIF_FORMAT == iFileFormat)
					{
						pdfStream = pdfobj.convertImageToPDF(Imageurl);
						sos = response.getOutputStream();
						sos.write(pdfStream.toByteArray());
						sos.close();
					}
					else
					{
						sos = response.getOutputStream();
						sos.write("Error occured on server while conversion".getBytes());
						sos.close();
					}
				} catch (Exception e) {
					sos = response.getOutputStream();
					sos.write("Error occured on server while conversion".getBytes());
					sos.close();
					e.printStackTrace();
				}
			}
		} catch (Exception e)
		{
			sos = response.getOutputStream();
			sos.write("Error occured on server while conversion".getBytes());
			sos.close();
			e.printStackTrace();
		}
		finally
		{
			if (null != pdfStream)
			{
				pdfStream.close();
			}
			if (null != sos)
			{
				sos.close();
			}
			if (null != outputStream)
			{
				outputStream.close();
			}
			if (null != ros)
			{
				ros.getStream().close();
			}
			if (null != riss)
			{
				riss.getStream().close();
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		// TODO Auto-generated method stub
		// System.out.println("doPost\n");
		// String Input = request.getParameter("strInputFile");
		// System.out.println(Input+"\n");
		// String output=request.getParameter("strOutFile");
		// System.out.println(output+"\n");
		// try {
		//
		// com.itextpdf.text.Image image2 =
		// com.itextpdf.text.Image.getInstance("http://192.168.57.7:8080/images/Pageno=4&.jpg");
		// com.itextpdf.text.Rectangle rect1 = new
		// com.itextpdf.text.Rectangle(image2.getWidth(),image2.getHeight());
		// if(rect1.getWidth()>14400 || rect1.getHeight()>14400)
		// {
		// System.out.println("Width or height is greater than 14400 \n");
		// return;
		// }
		// ServletOutputStream out = response.getOutputStream();
		// response.setContentType("text/plain");
		// FileOutputStream fos = new FileOutputStream(output);
		// Document document = new Document(rect1,0,0,0, 0);
		// document.setPageSize(rect1);
		// System.out.println("Set Document size\n");
		// PdfWriter writer = PdfWriter.getInstance(document, out);
		// System.out.println("Open PDF Writer\n");
		// writer.open();
		// System.out.println("Open Document\n");
		// document.open();
		// System.out.println("Add image in document\n");
		// document.add(image2);
		// System.out.println("Close document\n");
		// document.close();
		// System.out.println("Close writer\n");
		// writer.close();
		// System.out.println("Completed\n");
		// }
		// catch (Exception e) {
		// System.out.println("Executed Catch block\n");
		// e.printStackTrace();
		// }

		// RandomInputStreamSource riss = null;
		// ByteArrayOutputStream pdfStream=null;
		//
		// try
		// {
		// System.out.println("doPOST\n");
		// System.out.println(request);
		// System.out.println(" input query string = " +
		// request.getQueryString());
		// String strImagetype = request.getParameter("strImageURL");
		// System.out.println(strImagetype + "\n");
		//
		// URL Imageurl=new URL(strImagetype);
		// System.out.println(Imageurl + "\n");
		// riss=new RandomInputStreamSource(Imageurl, 5000);
		// System.out.println( "first\n");
		//
		// Thread t=new Thread(riss);
		// t.start();
		// System.out.println("second\n");
		// int iFileFormat = NIPLJ.getFileFormat(Imageurl);
		// System.out.println("file format : "+ iFileFormat+ "\n");
		// int iPageNo=NIPLJ.getPageCount(riss);
		//
		// System.out.println("Pages in file : "+ iPageNo+ "\n");
		//
		// if(iFileFormat!=NIPLJ.TIFF_FORMAT)
		// iPageNo=1;
		// NGIP_PDFImplementation pdfobj=new NGIP_PDFImplementation();
		// pdfStream = pdfobj.convertImageToPDF(Imageurl);
		// if(pdfStream==null)
		// {
		// throw new NGIMException(NGIMException.NGIM_ERR_INVALID_PARAM2);
		// }
		// ServletOutputStream sos=response.getOutputStream();
		// sos.write(pdfStream.toByteArray());
		// pdfStream.close();
		//
		//
		// System.out.println("Completed");
		//
		// }
		// catch (Exception e) {
		// if(pdfStream!=null)
		// pdfStream.close();
		// if(riss!=null)
		// {
		// riss.getStream().close();
		// }
		// System.out.println("Executed Catch block\n");
		// e.printStackTrace();
		// }
		// finally
		// {
		// if(riss!=null)
		// {
		// riss.getStream().close();
		// }
		// if(pdfStream!=null)
		// pdfStream.close();
		//
		// }
	}

}
