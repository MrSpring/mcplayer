package dk.mrspring.mcplayer.thread;

import com.sun.istack.internal.NotNull;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by Konrad on 02-07-2014.
 */
public class MusicPlayerThread extends Thread
{
    protected final File playing;
    protected final Duration playFrom;

    protected MediaPlayer player;

    public MusicPlayerThread(@NotNull File toPlay)
    {
        this(toPlay, new Duration(0));
    }

    public MusicPlayerThread(@NotNull File toPlay, @NotNull Duration from)
    {
        this.playing = toPlay;
        this.playFrom = from;

        Media media = new Media(this.playing.toURI().toASCIIString());
        this.player = new MediaPlayer(media);
        this.player.setOnEndOfMedia(new Runnable()
        {
            @Override
            public void run()
            {
                LiteModMCPlayer.thread.onPlayerDonePlaying();
            }
        });
		this.player.setStartTime(this.playFrom);
    }

    @Override
    public void run()
    {
        this.player.play();
    }

    public synchronized Duration getPosition()
    {
        if (this.player != null)
            return this.player.getCurrentTime();
        else return Duration.ZERO;
    }

    public synchronized void pausePlaying()
    {
        this.player.pause();
    }

	public synchronized void resumePlaying(Duration playFrom)
	{
		Media media = new Media(this.playing.toURI().toASCIIString());
		this.player = new MediaPlayer(media);
		this.player.setOnEndOfMedia(new Runnable()
		{
			@Override
			public void run()
			{
				LiteModMCPlayer.thread.onPlayerDonePlaying();
			}
		});
		this.player.setStartTime(playFrom);
		this.player.play();
	}

    public synchronized void resumePlaying()
    {
        this.player.play();
    }

    public synchronized Duration stopPlaying()
    {
        Duration position = this.player.getCurrentTime();
        this.player.stop();
        return position;
    }

    public synchronized void setVolume(double volume)
    {
        this.player.setVolume(volume);
    }

    public synchronized Duration getMediaLength()
    {
		return this.player.getMedia().getDuration();
    }
}
