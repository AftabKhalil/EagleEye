package eagleEye;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.omg.SendingContext.RunTime;


 public class EagleEye
{
	 public static EagleEye ee;
	 
	 Network network;
	 Network.Sender sender;
	 public Runtime r;
	 
	 public MyPiCamera camera;
	
	
	 public EagleEye()
	{
		  network = new Network();
		  sender = network.new Sender();
		 
		 camera = new MyPiCamera();
		
		 
		 
	}
	
	 public static void main(String[] args)
	{

		 ee = new EagleEye();
		 
		 /*JFileChooser j = new JFileChooser();
		 
		 j.showOpenDialog(null);
		 
		 File f = j.getSelectedFile();
		 FileInputStream fis = null;
		 
		 try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 BufferedInputStream bis = new BufferedInputStream(fis);
		
		 
		 
		 byte [] fileBytes = new byte[(int)f.length()];
		 
		 
		 
		 try {
			bis.read(fileBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		 
	
		 
		 
		 ee.sender.send("I_AM_SENDING_VIDEO");
		 ee.sender.send(String.valueOf(f.length()));
		
		 
		 
			 ee.sender.send(fileBytes);
		
		
		 System.out.print("FINISHED");*/
		 
		 Scanner in = new Scanner(System.in);
		 System.out.print("S it a");
		 in.hasNext();
		 
		
		 System.out.print("HERE");
	}
}
