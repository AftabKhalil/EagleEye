/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagleeye;

import static eagleeye.EagleEye.ee;
import java.io.BufferedOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

/**
 *
 * @author aftab
 */
public final class Network {
    
     private InetAddress  localAddress = null;
         private ServerSocket serverSocket = null;
         private int          port = 121;
         public Socket clientSocket = null;
         
         private DataInputStream input = null;
         private DataOutputStream output = null;
         
         private int acknowledge = -1;
         
         public Network()
        {    
             try
            {
                 localAddress = InetAddress.getLocalHost();
            } 
             catch (UnknownHostException ex)
            {
                 
            }
                 InetSocketAddress serverSocketAddress = new InetSocketAddress(localAddress,port);
             try 
            {
                 serverSocket = new ServerSocket();
            }
             catch (IOException ex)
            {
                 
            }
             try
            {
                 serverSocket.bind(serverSocketAddress,1);
                 System.out.println("Server Ready TO Listen At "+serverSocket.getLocalSocketAddress());
            } 
             catch (IOException ex)
            {
                
            }
             acceptClient();
        }
         
         public void acceptClient()
        {
             try
            {
                 clientSocket = serverSocket.accept();
                 System.out.println("Client received : "+serverSocket.toString());
            }
             catch (IOException ex)
            {
                System.out.println(ex);
            }
             try
            {
                 input = new DataInputStream(clientSocket.getInputStream());
            }
             catch (IOException ex)
            {
                System.out.println(ex);
            }
    
             Listener l = new Listener();
             Sender s = new Sender();
             
             l.listener.start();
        }
         
         public class Listener implements Runnable
        {   
             private Thread listener;
             public Listener()
            {
                 listener = new Thread(this);
            }
             @Override
             public void run()
            {
                 while(true)
                {
                     try
                    {
                        String message = input.readUTF();
                        
                        if(message.compareTo("I_AM_SENDING_VIDEO")==0)
                        {
                            System.out.println("EXPECTING VIDEO");
                            int size = Integer.valueOf(input.readUTF());
                            byte[] videoBytes = new byte[size];
                            
                            
                            System.out.println(size + " SR");
                            for (int i = 0; i < size; i++)
                            {
                                videoBytes[i]=input.readByte();
                                //System.out.println(i);
                                
                            }
                            System.out.println("FR");
                            File f = new File("E:\\EagleEye\\V\\EagleEyeVideo.h264");
                            FileOutputStream fos = new FileOutputStream(f);
                            
                            BufferedOutputStream bos = new BufferedOutputStream(fos);
                            bos.write(videoBytes);
                            bos.close();
                            fos.close();
                            
                            
                            
                             FileInputStream in = new FileInputStream(f);
                             int len =(int)f.length();
                             System.out.println(len);
                             byte[] b = new byte[len];
                             int d = in.read(b);
         
                             int i=0;
                             String stmt = "SELECT * FROM [VideoTable];";
                            try {
                                ee.sql.rs = ee.sql.stmnt.executeQuery(stmt);
                                while(ee.sql.rs.next())
                                {
                                    i++;
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                            }
                             
                             PreparedStatement ps = null;
                             try {
                                 ps = ee.sql.con.prepareStatement("INSERT INTO [VideoTable] "
                                        + "([VideoId],[DateTaken],[TimeTaken],[Reviewed],[VideoFile]) "
                                        + "VALUES (?,?,?,?,?);");
                                 ps.setString(1, "VV"+i);
                                 ps.setDate(2,(java.sql.Date) new Date(11,12,12));
                                 ps.setTime(3, new Time(System.currentTimeMillis()));
                                 ps.setInt(4,1);
                                 ps.setBytes(5, b);
        
                                 int aa = ps.executeUpdate();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR",ERROR_MESSAGE);
                            }
       
       
       
         
        //System.out.println(aa);*/
                           
                            System.out.println("Finished");
                           
                           
                        }
                        else if(message.compareTo("MOTION_DETECTED")==0)
                        {
                           
                            Thread t = new Thread()
                            {
                               @Override
                               public void run()
                               {
                                    ee.lp.nightModeDidabledByDevice();
                            
                                    ee.sp.PlaySound("E:\\EagleEye\\V\\Alarm.wav");
                                    acknowledge = JOptionPane.showConfirmDialog(null,"Motion Detected In Front Of Your Device At "+ new Date(),"Motion Detected",OK_CANCEL_OPTION,INFORMATION_MESSAGE);
                                    ee.sp.stopSound();
                                }
                            };
                            t.start();
                            System.out.println(message);
                        }
                       
                        System.out.println("I received from device : "+message);
                    }
                     catch (IOException ex)
                    {
                        System.out.println(ex);
                         System.exit(0);
                         
                         //To do
                    }
                }
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
                 catch (IOException ex)
                {
                     
                }
            }
             
             public void send(String message)
             {
                 this.message = message;
                 
                 if(ee.deviceConnected == true)
                 {
                    try {
                         output.writeUTF(this.message);
                         Thread.sleep(2000);
                     } catch (IOException | InterruptedException ex) {
                         Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
             }
        }
}
