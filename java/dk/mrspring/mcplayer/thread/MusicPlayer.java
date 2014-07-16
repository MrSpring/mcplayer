package dk.mrspring.mcplayer.thread;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by Konrad on 02-07-2014.
 */
public class MusicPlayer extends AdvancedPlayer
{
    public MusicPlayer(InputStream stream) throws JavaLayerException
    {
        super(stream);
    }

    public int getPosition()
    {
        MusicPlayer musicPlayer = this;

        Field[] fields = musicPlayer.getClass().getSuperclass().getDeclaredFields();
        fields[2].setAccessible(true);
        try
        {
            Object field = fields[2].get(musicPlayer);
            AudioDevice audioDevice = (AudioDevice) field;
            if (field == null) return 0;
            return audioDevice.getPosition();

        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}
