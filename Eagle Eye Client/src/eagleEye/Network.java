package eagleEye;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import static eagleEye.EagleEye.ee;

 public class Network
{
	 InetAddress remoteIPAddress;
	 InetSocketAddress serverSocketAddress;
	 Socket clientSocket;
	 DataOutputStream output;
	 DataInputStream input;
	 Listener l;

	 public Network()
	{
		  try
		 {
			   remoteIPAddress = InetAddress.getByName("192.168.15.2");
		 }
		  catch (UnknownHostException e)
		 {
			   e.printStackTrace();
		 }
		

		 connect();
		
	 }

	  public void connect()
	 {
		   System.out.println("Connecting to server");
		  
		   Process Proc = null;
		   Process Proc2 = null;
		try {
			Proc  = Runtime.getRuntime().exec(new String[]{"python","/home/pi/ledOff.py"});
			Proc2 = Runtime .getRuntime().exec(new String[]{"python","/home/pi.servoMid.py"});
			if(Proc!=null)
			   {
				   Proc.waitFor();
			   }
			   if(Proc2!=null)
			   {
				   Proc2.waitFor();
			   }
			   
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		  
		   
		   
		   
		   try
		  {
			    serverSocketAddress = new InetSocketAddress(remoteIPAddress, 121);
			    clientSocket = new Socket();
			    clientSocket.connect(serverSocketAddress);
			    
			    Process proc = null;
			    try
			    {
			    	proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/greenOn.py"});
			    	if(proc!=null)
			    	{
			    		proc.waitFor();
			    	}
			    }
			    catch(Exception e)
			    {
			    	System.out.print(e);
			    	
			    }
			    l = new Listener();
				l.receiver.start();
			    
			    
		  }
		   catch (Exception e)
		  {
			   try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			    connect();
		  }
		   
		  
	 }

	   public class Listener implements Runnable
	  {
		    private Thread receiver;
		    private String message;

		    public Listener()
		   {
		    	 try
		    	{
		    		  input = new DataInputStream(clientSocket.getInputStream());
		    	}
		    	 catch (IOException e)
		    	{
		    		  e.printStackTrace();
		    	}
		    	 receiver = new Thread(this);
		    }
	  
			@Override
		     public void run()
		    {
		    	  while (true)
		    	 {
		    		   try
		    		  {
		    			    message = input.readUTF();
		    			    System.out.println("I REC : "+message);
		    			    if(message.compareTo("START_LIVE_STREAM")==0)
		    			    {
		    			    	
		    			    }
		    			    else if(message.compareTo("START_NIGHT_MODE")==0)
		    			    {
		    			    	ee.camera.startNightMode();
		    			    }
		    			    else if(message.compareTo("STOP_NIGHT_MODE")==0)
		    			    {
		    			    	ee.camera.stopNightMode();
		    			    }
		    			    else if(message.compareTo("LED_ON")==0)
		    			    {
		    			    	Process proc = null;
		    					try{
		    					proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/LedOn.py"});
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
		    			    else if(message.compareTo("LED_OFF")==0)
		    			    {
		    			    	Process proc = null;
		    					try{
		    					proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/LedOff.py"});
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
		    			    else if(message.compareTo("MOVE_LEFT")==0)
		    			    {
		    			    	Process proc = null;
		    					try{
		    					proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/servoLef.py"});
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
		    			    else if(message.compareTo("CENTER")==0)
		    			    {
		    			    	Process proc = null;
		    					try{
		    					proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/servoMid.py"});
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
		    			    else if(message.compareTo("MOVE_RIGHT")==0)
		    			    {
		    			    	Process proc = null;
		    					try{
		    					proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/servoRig.py"});
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
		    			    System.out.println(message);
		    		  }
		    		   catch (IOException e)
		    		  {
		    			     Process proc = null;
		    			     try
		    			    {
		    			    	  proc = Runtime.getRuntime().exec(new String[]{"python","/home/pi/greenOff.py"});
		    			    	  if(proc!=null)
		    			    	 {
		    			    		   proc.waitFor();
		    			    	 }
		    			    }
		    			      catch(Exception ex)
		    			     {
		    			    	  System.out.print(ex);
		    			     }
		    			     break;
		    		  }
		    	 }
		    	  connect();
		    }
	  }
	
	    public class Sender
	   {
	    	 String message;
	    	 
	    	 public Sender()
	    	{
	    		  try
	    		 {
	    			   output = new DataOutputStream(clientSocket.getOutputStream());
	    		 }
	    		  catch (IOException e)
	    		 {
	    			   e.printStackTrace();
	    		 }
	    	}
	    	 public void send(String message)
	    	{
	    		  this.message = message;
	    		  try
	    		 {
	    			   output.writeUTF(this.message);
	    		 }
	    		  catch (IOException e)
	    		 {
	    			   e.printStackTrace();
	    		 }
	    	}
	    	 
	    	 public void send(byte[] byteToSend)
	    	 {
	    		 try
	    		 {
	    			 for(int i=0; i<byteToSend.length; i++)
	    			 {
	    				 output.writeByte(byteToSend[i]);
	    			 }
	    		 }
	    		 catch(IOException e)
	    		 {
	    			 e.printStackTrace();
	    		 }
	    		 
	    	 }
	   }
}