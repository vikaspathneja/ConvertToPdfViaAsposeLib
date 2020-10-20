
import java.io.*;
import java.net.*;
import com.newgen.service.DataModel;


public class MyClient{
	public static void main(String[] args) {
		InputStream isInputstream = null;
		try {
			Socket sock = new Socket("localhost", 6789);
			ObjectOutputStream dout = new ObjectOutputStream(sock.getOutputStream());
			
			//PrintWriter pw = new PrintWriter(s.getOutputStream(),true);			
			
			String strInFile = "D:\\Office\\Aspose\\Vikas_Resume.docx";
			File file = new File(strInFile);
			isInputstream = new FileInputStream(strInFile);
			String strExtension = "docx";
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int ch;
			while ((ch = isInputstream.read()) != -1) {
				
				buf.write(ch);
			}
			byte[] docByteArray = buf.toByteArray();	
			/**********Sending to server ***********/
			System.out.print("Sending data object");
			DataModel cob = new DataModel();
			cob.setFileExtenion(strExtension);
			cob.setBuf(buf);
			cob.setDocByteArray(docByteArray);
			dout.writeObject(cob);
			System.out.println("Sending data Completed from client");

			
			
			/*********** Receiving data form server ***********/
			System.out.println("Recieving data from server ");
			DataModel dmInOb = new DataModel();
			ObjectInputStream din = new ObjectInputStream(sock.getInputStream());
			//din = new  ObjectInputStream(sock.getInputStream());
			dmInOb = (DataModel)din.readObject();
			// writing file to pdf
			String strOutFile = "D:\\Office\\Aspose\\convertedResume.pdf";
			//byte[] docByteArrayIn = boutStream.toByteArray();
			try(OutputStream fos = new FileOutputStream(strOutFile)){			
							
				fos.write(dmInOb.getDocByteArray());			
			}
			System.out.println("End reading data at client ");
		} catch (Exception e) {
			System.out.println(e);
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
}