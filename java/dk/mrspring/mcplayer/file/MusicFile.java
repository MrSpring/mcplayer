package dk.mrspring.mcplayer.file;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Konrad on 25-06-2014.
 */
public class MusicFile
{
    public static final int UNKNOWN = -1;
    public static final int MP3 = 1;
    public static final int WAV = 2;

    protected File baseFile;
    protected int type = UNKNOWN;

    protected String title = "UNTITLED";
    protected String album = "UNTITLED";
    protected String artist = "UNTITLED";

    public MusicFile(File base)
    {
        this.baseFile = base;

        int i = this.baseFile.toString().lastIndexOf('.');
        String extension = this.baseFile.toString().substring(i);

        if (LiteModMCPlayer.extensions.contains(extension))
            if (extension.equals(".mp3"))
                this.type = MP3;
            else if (extension.equals(".wav"))
                this.type = WAV;
    }

    public String getField(FieldKey key) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotReadException
    {
        AudioFile f = AudioFileIO.read(this.baseFile);

        String title = f.getTag().getFields(FieldKey.TITLE).get(0).toString();

        String temp = title.substring(6);
        int i = temp.lastIndexOf('"');
        temp = temp.substring(0, i - 1);

        return temp;
    }

    public String getTitle()
    {
        switch (this.type)
        {
            case MP3:
            {
                try
                {
                    return this.getField(FieldKey.TITLE);
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (TagException e)
                {
                    e.printStackTrace();
                } catch (ReadOnlyFileException e)
                {
                    e.printStackTrace();
                } catch (InvalidAudioFrameException e)
                {
                    e.printStackTrace();
                } catch (CannotReadException e)
                {
                    e.printStackTrace();
                }

                break;
            }
            // TODO Add WAV and other extensions
        }

        return "UNTITLED";
    }

    @Override
    public String toString()
    {
        return this.baseFile.toString();
    }
}
