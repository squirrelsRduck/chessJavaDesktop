package andrewLuke.chess.audio;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class handles audio
 * @author Andrew
 */
public class Sound
{
    private static String path;

    /**
     * Creates a Sound object
     * @param path
     */
    public Sound(final String path)
    {
        Sound.path = path;
    }

    /**
     * Plays the sound
     */
    public void playSound()
    {
        new Thread(new Runnable() {

            @Override
            public void run()
            {
                Clip clip = null;
                try
                {
                    clip = AudioSystem.getClip();
                }
                catch (LineUnavailableException ex)
                {
                    Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
                }
                AudioInputStream audioInputStream = null;
                try
                {
                    audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(path));
                }
                catch (UnsupportedAudioFileException | IOException ex)
                {
                    Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
                }
                try
                {
                    clip.open(audioInputStream);
                }
                catch (LineUnavailableException | IOException ex)
                {
                    Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
                }
                clip.start();
            }
        }).start();
    }
}