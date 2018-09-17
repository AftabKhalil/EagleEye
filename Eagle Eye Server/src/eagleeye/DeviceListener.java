package eagleeye;

import static eagleeye.EagleEye.ee;
import eagleeye.Network.Sender;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class DeviceListener implements Runnable
{
    private Thread thread;
    
    public DeviceListener()
    {
         thread = new Thread(this);
    }

    public void start()
    {
        this.thread.start();
    }
    
    @Override
    public void run()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(DeviceListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        showRedCircle();
        ee.network = new Network();
        ee.sender = ee.network.new Sender();
        ee.deviceConnected = true;
        showGreenCircle();
        
        while(true)
        {
            if(ee.deviceConnected==false)
            {
                showRedCircle();
                ee.network = new Network();
                ee.sender = ee.network.new Sender();
                ee.deviceConnected = true;
                showGreenCircle();
            }
            try {
                Thread.sleep(50000);
            } catch (InterruptedException ex) {
                JOptionPane.showConfirmDialog(null, ee,"ERROR_MESSAGE",ERROR_MESSAGE);
            }
        }
    }

    private void showRedCircle() {
        ee.fp.DeviceConnected.setBackground(Color.red);
        ee.fp.revalidate();
        ee.fp.repaint();
        
        ee.sip.DeviceConnected.setBackground(Color.red);
        ee.sip.revalidate();
        ee.sip.repaint();
        
        ee.sup.DeviceConnected.setBackground(Color.red);
        ee.sup.revalidate();
        ee.sup.repaint();
        
        ee.mf.DeviceConnected.setBackground(Color.red);
        ee.mf.revalidate();
        ee.mf.repaint();
        
        ee.lp.DeviceStatus.setText("Not Connected");
        ee.lp.DeviceStatus.setForeground(Color.red);
        ee.lp.revalidate();
        ee.lp.repaint();
    }

    private void showGreenCircle() {
       ee.fp.DeviceConnected.setBackground(Color.green);
        ee.fp.revalidate();
        ee.fp.repaint();
        
        ee.sip.DeviceConnected.setBackground(Color.green);
        ee.sip.revalidate();
        ee.sip.repaint();
        
        ee.sup.DeviceConnected.setBackground(Color.green);
        ee.sup.revalidate();
        ee.sup.repaint();
        
        ee.mf.DeviceConnected.setBackground(Color.green);
        ee.mf.revalidate();
        ee.mf.repaint();
        
        ee.lp.DeviceStatus.setText("Connected");
        ee.lp.DeviceStatus.setForeground(Color.green);
        ee.lp.revalidate();
        ee.lp.repaint();
    }
    
}
