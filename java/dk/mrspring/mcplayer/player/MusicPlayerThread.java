package dk.mrspring.mcplayer.player;

import dk.mrspring.mcplayer.file.MusicFile;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Konrad on 01-07-2014.
 */
public class MusicPlayerThread extends Thread
{
    Player player;
    int currentlyPlayingIndex;
    MusicFile currentlyPlaying;
    public boolean isPlaying = false;

    public void run(int index, MusicFile file)
    {
        this.run(index, file, 0);
    }

    public void run(int index, MusicFile file, int frame)
    {
        this.currentlyPlayingIndex = index;
        this.currentlyPlaying = file;

        try
        {
            FileInputStream inputStream = new FileInputStream(file.getBaseFile());
            this.player = new Player(inputStream);
            isPlaying = true;
            this.player.play(frame);
            isPlaying = false;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (JavaLayerException e)
        {
            e.printStackTrace();
        }
    }

    public boolean getIsPlaying()
    {
        return this.isPlaying;
    }

    /***
     *
     * @return Returns the Frame the player stopped playing. Useful for pausing!
     */
    public int stopPlaying()
    {
        int frame = this.player.getPosition();
        this.player.close();
        return frame;
    }
}
