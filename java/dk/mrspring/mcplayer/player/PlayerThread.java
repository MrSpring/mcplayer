package dk.mrspring.mcplayer.player;

import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.list.Playlist;

/**
 * Created by Konrad on 01-07-2014.
 */
public class PlayerThread extends Thread
{
    public static final int NOT_PLAYING = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;

    public int playingState = NOT_PLAYING;
    private boolean hasInitialized = false;

    public Playlist<MusicFile> queue;
    public int currentlyPlayingIndex = -1;
    public MusicPlayerThread currentlyPlayingThread;

    public boolean run = true;

    @Override
    public void run()
    {
        System.out.println(" Thread is Running!");

        while (run)
        {
            if (!hasInitialized && this.queue != null)
                this.initialize();

            if (hasInitialized)
            {
                if (this.playingState == NOT_PLAYING)
                {
                    this.playNextInQueue();
                }
            }
        }

        System.out.println(" Thread is Closing!");
    }

    public void initialize()
    {
        this.currentlyPlayingIndex = 0;
    }

    public void setQueue(Playlist<MusicFile> queue)
    {
        this.queue = queue;
    }

    public Playlist<MusicFile> getQueue()
    {
        return this.queue;
    }

    public MusicFile playInQueue(int toSkip)
    {
        MusicFile musicFile = this.queue.get(0);

        for (int i = 0; i < toSkip; i++)
        {
            musicFile = this.queue.remove(0);
            this.queue.add(musicFile);
            currentlyPlayingIndex++;
        }

        this.play(this.queue.get(0), 0);
        return musicFile;
    }

    /***
     *
     * @return Returns the next file, which is bering played, in the queue.
     */
    public MusicFile playNextInQueue()
    {
        //currentlyPlayingIndex++;
        /*MusicFile musicFile = this.queue.remove(0);
        this.queue.add(musicFile);
        currentlyPlayingIndex++;
        this.play(this.queue.get(0), 0);
        return this.queue.get(0);*/

        return this.playInQueue(1);
    }

    /***
     *
     * @param file The file to start playing.
     * @param frame The frame to start playing from.
     * @return Returns the next file in queue.
     */
    public MusicFile play(MusicFile file, int frame)
    {
        if (this.currentlyPlayingThread.getIsPlaying())
            this.currentlyPlayingThread.stopPlaying();

        this.currentlyPlayingThread = new MusicPlayerThread();
        this.currentlyPlayingThread.run(this.currentlyPlayingIndex, file, frame);

        return this.queue.get(1);
    }
}
