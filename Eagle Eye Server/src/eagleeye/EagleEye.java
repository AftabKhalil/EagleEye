package eagleeye;

import eagleeye.Network.Sender;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * @author aftab
 */

 public class EagleEye
 {
     public static EagleEye ee;
     public MainFrame mf;
     public FirstPage fp;
     public SighnInPage sip;
     public SignUpPage sup;
     public LastPage lp;
     public LiveStreamControls lsc;
     public DeviceListener dl;
     
     public SQLObject sql;
     
     Network network ;
     Network.Sender sender;
     
     public SoundPlayer sp;
     
     public boolean sipReloaded = false;
     public boolean deviceConnected = false;
     
     static long a;
     static long b;
     public EagleEye()
    {
         try
        {  
             sql = new SQLObject("EagleEye","sa","Aftab02128061996");
        }
         catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"DataBase Error!",ERROR_MESSAGE);
        }
        
         sp = new SoundPlayer();
         //sp.PlaySound("E:\\EagleEye\\V\\alarm.wav");
        
        mf   = new MainFrame();
        fp   = new FirstPage();
        sip  = new SighnInPage();
        sup  = new SignUpPage();
        lp = new LastPage();
        lsc = new LiveStreamControls();
        
        dl = new DeviceListener();
        dl.start();
        
         mf.setVisible(true);
         
         
         
         
         
         /*File f = new File("E:\\Nani.mp4");
         FileInputStream in = new FileInputStream(f);
         int len =(int)f.length();
         System.out.println(len);
         byte[] b = new byte[len];
         int d = in.read(b);
        
         
         
       PreparedStatement ps =  sql.con.prepareStatement("INSERT INTO [VideoTable] "
                                               + "([VideoId],[DateTaken],[TimeTaken],[Reviewed],[VideoFile]) "
                                               + "VALUES (?,?,?,?,?);");
        ps.setString(1, "VV1");
        ps.setDate(2, new Date(11,12,12));
        ps.setTime(3, new Time(System.currentTimeMillis()));
        ps.setInt(4,1);
        ps.setBytes(5, b);
        
       int aa = ps.executeUpdate();
       
       
         
        //System.out.println(aa);*/
         
         
         
    }
     public static void main(String[] args) throws IOException, SQLException
    {
         a=System.nanoTime();
         ee = new EagleEye();
         b=System.nanoTime();
         System.out.println((b-a)/1000000000+" secounds to load.\n");
    }
     
    
}
