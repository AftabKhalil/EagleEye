/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagleeye;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author aftab
 */
public class SoundPlayer
    {
         private String adress;
         private Clip clip;
         Thread  play;
    
         SoundPlayer() {
    }
         
         public void PlaySound(String adress)
        {
             this.adress    = adress;
             play = new Thread()
            {
                 @Override
                 public void run()
                {
                     try
                    {
                         clip = AudioSystem.getClip();
                         clip.open(AudioSystem.getAudioInputStream(new File(adress)));
                         clip.start();
                    }
                     catch (Exception exc)
                    {
                         System.out.println(exc);
                    }
                }
            };
             play.run();
        }

         public void stopSound()
        {   
             clip.stop();
             play.stop();
        }
    }