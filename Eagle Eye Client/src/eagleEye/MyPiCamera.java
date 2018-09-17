package eagleEye;

import static eagleEye.EagleEye.ee;

import java.io.IOException;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

public class MyPiCamera {

	MotionDetector md;
	private boolean nightMode = false;
	
	
	public MyPiCamera(){
	
		Process proc = null;
		try{
		proc = Runtime.getRuntime().exec(new String[]{"modprobe", "bcm2835-v4l2"});
		if(proc != null)
		{
			proc.waitFor();
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}
	
	public void startNightMode()
	{
		System.out.println("NightMode Started");
		try {
			Process proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/LedOn.py"});
			if(proc!=null)
			{
				proc.waitFor();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		md = new MotionDetector();
		md.t.start();
		nightMode = true;
	}
	
	@SuppressWarnings("deprecation")
	public void stopNightMode()
	{
		if(nightMode==true)
		{
			md.t.stop();
		}
		System.out.println("NightMode Stopped");
	}
	
	public class MotionDetector implements Runnable
	{

		Thread t ;
		
		public  MotionDetector()
		{
			t = new Thread (this);
		}
		
		@Override
		public void run() {
			Webcam cam = Webcam.getDefault();
			
			WebcamMotionDetector d = new WebcamMotionDetector(cam);
			d.setInterval(100);
			d.start();
			System.out.println("Here");
			int i=0;
			while(true)
			{
				if(d.isMotion())
				{
					System.out.println("Motion" + ++i);
					ee.sender.send("MOTION_DETECTED");
					try {
						Process p = Runtime.getRuntime().exec("raspivid -t 0 -h 480 -w 620 -o /home/pi vid-here.h264");
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
			
			t.stop();
			
			
		}
		
		
		
	}

}
