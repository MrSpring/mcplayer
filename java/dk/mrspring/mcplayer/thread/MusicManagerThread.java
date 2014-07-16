package dk.mrspring.mcplayer.thread;

import com.sun.istack.internal.NotNull;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.list.Playlist;

/**
 * Created by Konrad on 02-07-2014.
 */
public class MusicManagerThread extends Thread
{
    protected Playlist<MusicFile> queue;

    public static final int NOT_INITIALIZED = -1;
    public static final int NOT_PLAYING = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED= 2;
    public static final int FINISHED = 3;
    public static final int STOPPED = 4;
    public static final int SCHEDULED_FOR_NEXT = 5;
    public static final int SCHEDULED_FOR_PREV = 6;

    public Thread playerThread;

    public int playingState = NOT_INITIALIZED;
    public int currentPosition = 0;

    // USED IN CLIENT RENDERING FOR MC MOD!
    public MusicFile currentlyPlaying;
    public MusicFile nextInQueue;

    public MusicManagerThread(Playlist<MusicFile> filesToPlay)
    {
        this.queue = filesToPlay;
        this.playingState = NOT_INITIALIZED;
    }

    @Override
    public void run()
    {
        if (this.playingState == NOT_INITIALIZED)
        {
            this.initialize();
        }

        while (!this.isStopped())
        {
            this.currentlyPlaying = this.queue.get(0);
            this.nextInQueue = this.queue.get(1);

            if (this.playerThread != null)
            {
                if (((MusicPlayerThread) this.playerThread).hasFinished())
                    this.playingState = FINISHED;
                else if (((MusicPlayerThread) this.playerThread).hasErrored())
                    this.playingState = NOT_PLAYING;

                if (this.playingState != PAUSED)
                    this.currentPosition = ((MusicPlayerThread) this.playerThread).getPosition();
            }

            switch (this.playingState)
            {
                case NOT_INITIALIZED: this.initialize(); this.playInQueue(0); break;
                case NOT_PLAYING: this.playInQueue(1); break;
                case PLAYING: break;
                case PAUSED: break;
                case FINISHED: this.playInQueue(1); break;
                case STOPPED: break;
                case SCHEDULED_FOR_NEXT:
                {
                    if (this.playerThread != null)
                       ((MusicPlayerThread) this.playerThread).stopPlaying();

                    this.playInQueue(1);
                    break;
                }
                case SCHEDULED_FOR_PREV:
                {
                    if (this.playerThread != null)
                        ((MusicPlayerThread) this.playerThread).stopPlaying();

                    this.playInQueue(-1);
                    break;
                }
            }

            /*if (this.playingState == NOT_PLAYING)
            {
                this.play(this.queue.get(0));
                continue;
            }

            if (this.playingState == PLAYING)
            {
                if (!((MusicPlayerThread) this.playerThread).isPlaying())
                {
                    if (((MusicPlayerThread) this.playerThread).hasErrored())
                    {
                        this.playInQueue(1);
                        continue;
                    }

                    if (((MusicPlayerThread) this.playerThread).hasFinished())
                    {
                        this.playingState = FINISHED;
                    }
                }
            }

            if (this.playingState == FINISHED)
            {
                this.playInQueue(1);
            }*/
        }
    }

    public MusicFile getCurrentlyPlaying()
    {
        return this.currentlyPlaying;
    }

    public MusicFile getNextInQueue()
    {
        return this.nextInQueue;
    }

    public synchronized void playInQueue(@NotNull int toSkip)
    {
        this.playInQueue(toSkip, 0);
    }

    public synchronized void playInQueue(@NotNull int toSkip, @NotNull int startFrame)
    {
        this.playInQueue(toSkip, startFrame, Integer.MAX_VALUE);
    }

    public synchronized void playInQueue(@NotNull int toSkip, @NotNull int startFrame, @NotNull int endFrame)
    {
        if (toSkip == 0)
        {
            this.play(this.queue.get(0), startFrame, endFrame);
        }

        if (toSkip > 0)
        {
            for (int i = 0; i < toSkip; i++)
            {
                MusicFile file = this.queue.remove(0);
                this.queue.add(file);
            }
        } else
        {
            for (int i = 0; i > toSkip; i--)
            {
                MusicFile file = this.queue.remove(this.queue.size() - 1);
                this.queue.add(0, file);
            }
        }

        this.play(this.queue.get(0), startFrame, endFrame);
    }

    public synchronized void play(@NotNull MusicFile musicFile)
    {
        this.play(musicFile, 0);
    }

    public synchronized void play(@NotNull MusicFile musicFile, @NotNull int startFrame)
    {
        this.play(musicFile, startFrame, Integer.MAX_VALUE);
    }

    public synchronized void play(@NotNull MusicFile musicFile, @NotNull int startFrame, @NotNull int endFrame)
    {
        if (this.playerThread != null)
            if (((MusicPlayerThread) this.playerThread).isPlaying())
                ((MusicPlayerThread) this.playerThread).stopPlaying();

        this.playerThread = new MusicPlayerThread(musicFile, startFrame, endFrame);
        this.playerThread.start();
        this.playingState = PLAYING;
    }

    public void initialize()
    {
        this.currentlyPlaying = this.queue.get(0);
        this.nextInQueue = this.queue.get(1);
        this.playingState = NOT_PLAYING;
    }

    public synchronized boolean isStopped()
    {
        return this.playingState == STOPPED;
    }

    public synchronized void pausePlaying()
    {
        int fps = 26;
        ((MusicPlayerThread) this.playerThread).stopPlaying();
        //Logger.logln("PAUSING AT " + this.currentPosition + "!");
        this.playingState = PAUSED;
    }

    public synchronized void resumePlaying()
    {
        if (this.playingState != PAUSED)
        {
            this.play(this.currentlyPlaying);
            return;
        }


        this.play(this.currentlyPlaying, this.currentPosition);
        this.playingState = PLAYING;
    }

    public synchronized void stopPlaying()
    {
        this.playingState = STOPPED;
        ((MusicPlayerThread) this.playerThread).stopPlaying();
    }

    public synchronized void scheduleNext()
    {
        this.playingState = SCHEDULED_FOR_NEXT;
    }

    public synchronized void schedulePrev()
    {
        this.playingState = SCHEDULED_FOR_PREV;
    }

    public synchronized boolean isPlayingPaused()
    {
        return this.playingState == PAUSED;
    }

    /*public static final int NOT_PLAYING = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;

    public int playingState = NOT_PLAYING;

    public List<File> queue;
    public int currentlyPlayingIndex = -1;
    public MusicPlayerThread currentlyPlayingThread;
    private boolean print = false;
    private boolean print1 = false;
    private boolean print2 = false;
    private boolean print3 = false;

    public boolean run = true;

    public PlayerThread(List<File> files)
    {
        this.queue = files;
    }

    @Override
    public void run()
    {
        System.out.println(" Thread is Running!");
        this.initialize();

        while (run)
        {
            if (this.currentlyPlayingThread != null)
                if (this.currentlyPlayingThread.getIsPlaying())
                    this.playingState = PLAYING;
                else
                    this.playingState = NOT_PLAYING;
            else
                this.playingState = NOT_PLAYING;


            if (this.playingState == NOT_PLAYING)
                this.playNextInQueue(0);
        }

        System.out.println(" Thread is Closing!");
        if (this.currentlyPlayingThread.isAlive())
            this.currentlyPlayingThread.stopPlaying();
    }

    public void initialize()
    {
        System.out.println(" Initializing!");
        this.currentlyPlayingIndex = 0;
        //this.playInQueue(0);
    }

    public void setQueue(List<File> files)
    {
        this.queue = files;
    }

    public List<File> getQueue()
    {
        return this.queue;
    }

    public void printQueue()
    {
        for (int i = 0; i < this.queue.size(); i++)
        {
            File file = this.queue.get(i);
            System.out.println(i + " - " + file.toString());
        }
    }

    /***
     *
     * @return Returns the next file, which is bering played, in the queue.
     */
    /*public File playNextInQueue(int startFrame)
    {
        return this.playInQueue(1, startFrame);
    }

    public File playPrevInQueue(int startFrame)
    {
        return this.playInQueue(-1, startFrame);
    }

    public File playInQueue(int toSkip, int startFrame)
    {
        File musicFile = this.queue.get(0);

        if (toSkip >= 0)
        {
            for (int i = 0; i < toSkip; i++)
            {
                musicFile = this.queue.remove(0);
                this.queue.add(musicFile);
                currentlyPlayingIndex++;
            }
        }
        else
        {
            for (int i = 0; i > toSkip; i--)
            {
                musicFile = this.queue.remove(this.queue.size() - 1);
                this.queue.add(0, musicFile);
                //this.queue.set(0, musicFile);
                currentlyPlayingIndex--;
            }
        }

        this.play(this.queue.get(0), startFrame);
        return musicFile;
    }

    /***
     *
     * @param file The file to start playing.
     * @param frame The frame to start playing from.
     * @return Returns the next file in queue.
     */
    /*public File play(File file, int frame)
    {
        if (this.currentlyPlayingThread != null)
            if (this.currentlyPlayingThread.getIsPlaying())
                this.currentlyPlayingThread.stopPlaying();

        this.currentlyPlayingThread = new MusicPlayerThread(file, frame);
        this.currentlyPlayingThread.start();
        System.out.println(" Starting player thread!");
        //this.currentlyPlayingThread.run(this.currentlyPlayingIndex, file, frame);

        return this.queue.get(1);
    }*/
}