package dk.mrspring.mcplayer.thread;

import com.sun.istack.internal.NotNull;
import dk.mrspring.mcplayer.file.MusicFile;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileInputStream;

/**
 * Created by Konrad on 02-07-2014.
 */
public class MusicPlayerThread extends Thread
{
    protected final MusicFile playing;
    protected int playFrom = 0;
    protected int playTo = Integer.MAX_VALUE;
    protected MusicPlayer player;

    private static final int ERROR = -1;
    private static final int NOT_INITIALIZED = 0;
    private static final int INITIALIZED = 1;
    private static final int PLAYING = 2;
    private static final int FINISHED = 3;
    private static final int STOPPED = 4;

    protected int state = NOT_INITIALIZED;

    public MusicPlayerThread(@NotNull MusicFile file)
    {
        this(file, 0);
    }

    public MusicPlayerThread(@NotNull MusicFile file, @NotNull int startFrame)
    {
        this(file, startFrame, Integer.MAX_VALUE);
    }

    public MusicPlayerThread(@NotNull MusicFile file, @NotNull int startFrame, @NotNull int endFrame)
    {
        // Logger.logln("- Initialising new Player Thread, containing: " + file.toString() + ", from frame " + startFrame + ", to " + endFrame + ".");

        this.playing = file;

        this.playFrom = startFrame;
        this.playTo = endFrame;

        try
        {
            FileInputStream fileInputStream = new FileInputStream(this.playing.getBaseFile());
            this.player = new MusicPlayer(fileInputStream);

            this.state = INITIALIZED;
        }
        catch (Exception e)
        {
            this.state = ERROR;
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            this.state = PLAYING;
            this.player.play(playFrom, playTo);
            if (this.state != STOPPED)
                this.state = FINISHED;
        }
        catch (JavaLayerException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized int getPosition()
    {
        return (this.player.getPosition() / 26) + this.playFrom;
    }

    public synchronized boolean hasErrored()
    {
        return this.state == ERROR;
    }

    public synchronized boolean isPlaying()
    {
        return this.state == PLAYING;
    }

    public synchronized boolean hasFinished()
    {
        return this.state == FINISHED;
    }

    public synchronized int stopPlaying()
    {
        try
        {
            this.state = STOPPED;
            int lastPos = this.getPosition();
            this.player.close();
            return lastPos;
        }
        catch (Exception e)
        {
            this.player.close();
            e.printStackTrace();
            return 0;
        }
    }

    /*protected MediaPlayer player;
    protected File currentlyPlaying;
    protected int startFrame;
    public boolean isPlaying = false;

    public MusicPlayerThread(@NotNull File file, @NotNull int frame)
    {
        this.currentlyPlaying = file;
        this.startFrame = frame;
        this.isPlaying = true;
    }

    @Override
    public void run()
    {
        try
        {
            this.isPlaying = true;
            FileInputStream inputStream = new FileInputStream(this.currentlyPlaying);
            Media music = new Media(this.currentlyPlaying.getPath());
            this.player = new MediaPlayer(music);
            System.out.println(" Playing! Current position: " + this.startFrame);
            //this.player.play(30);
            // this.player.play(this.startFrame, Integer.MAX_VALUE);
            this.player.setStartTime(Duration.millis(this.startFrame));
            this.player.play();
            //System.out.println(" Stopped playing at " + this.player.getPosition() + "!");
            if (this.isPlaying)
                this.stopPlaying();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public MediaPlayer getPlayer()
    {
        return this.player;
    }

    /*public void run(File file)
    {
        this.run(file, 0);
    }

    public void run(File file, int frame)
    {
        this.currentlyPlaying = file;

        try
        {
            FileInputStream inputStream = new FileInputStream(file);
            this.player = new Player(inputStream);
            this.isPlaying = true;
            System.out.println(" Playing!");
            this.player.play(frame);
            System.out.println(" Stopped playing!");
            this.isPlaying = false;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (JavaLayerException e)
        {
            e.printStackTrace();
        }
    }*/

    /*public synchronized boolean getIsPlaying()
    {
        return this.isPlaying;
    }

    public double getPosition()
    {
        return this.player.getCurrentTime().toMillis();
    }

    /***
     *
     * @return Returns the Frame the player stopped playing. Useful for pausing!
     */
    /*public double stopPlaying()
    {
       // System.out.println(" Stopped at: " + this.getPosition());
        double lastPos = this.getPosition();
        System.out.println(" Stopped at: " + lastPos);
        this.player.stop();
        this.isPlaying = false;
        return lastPos;
    }*/
}