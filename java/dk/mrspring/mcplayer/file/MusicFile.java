package dk.mrspring.mcplayer.file;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import net.minecraft.util.ResourceLocation;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
	protected ResourceLocation coverLocation = new ResourceLocation("mcplayer", "textures/cover/default.png");

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

    public String getStringField(FieldKey key) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotReadException
    {
        AudioFile f = AudioFileIO.read(this.baseFile);

		String temp = "UNTITLED";

		try
		{
			temp = f.getTag().getFields(key).get(0).toString();
		} catch (IndexOutOfBoundsException e)
		{
			System.out.println(" Unable to get requested info from tag.");
		}

        /*String temp = title.substring(6);
        int i = temp.lastIndexOf('"');
        temp = temp.substring(0, i - 1);*/

        return temp; // OLD return temp;
    }

	public ResourceLocation getCoverLocation()
	{
		return this.coverLocation;
	}

	public void getCover()
	{
		try
		{
			AudioFile f = AudioFileIO.read(this.baseFile);
			byte[] rawImage = f.getTag().getFirstField(FieldKey.COVER_ART).getRawContent();
			this.coverLocation = new ResourceLocation("", "textures/cover/cover.png");
			FileOutputStream fileOutputStream = new FileOutputStream(new File(this.coverLocation.getResourcePath()));
			fileOutputStream.write(rawImage);
			fileOutputStream.close();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (CannotReadException e)
		{
			e.printStackTrace();
		} catch (ReadOnlyFileException e)
		{
			e.printStackTrace();
		} catch (InvalidAudioFrameException e)
		{
			e.printStackTrace();
		} catch (TagException e)
		{
			e.printStackTrace();
		}
	}

	public String getArtistFromTag()
    {
        try
        {
            String temp = this.getStringField(FieldKey.ARTIST);

            temp = temp.substring(6);
            int i = temp.lastIndexOf('"');

			if (i == -1)
				return temp;

			temp = temp.substring(0, i);
            temp = temp.trim();

            return temp;

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

        return "UNTITLED";
    }

    public String getArtist()
    {
        if (this.artist.equals("UNTITLED"))
            this.artist = this.getArtistFromTag();

        return this.artist;
    }

    public String getAlbumFromTag()
    {
        try
        {
            String temp = this.getStringField(FieldKey.ALBUM);

            temp = temp.substring(6);
            int i = temp.lastIndexOf('"');

			if (i == -1)
				return temp;

            temp = temp.substring(0, i);
            temp = temp.trim();

            return temp;

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

        return "UNTITLED";
    }

    public String getAlbum()
    {
        if (this.album.equals("UNTITLED"))
            this.album = this.getAlbumFromTag();

        return this.album;
    }

    public String getTitleFromTag()
    {
        try
        {
            String temp = this.getStringField(FieldKey.TITLE);

            temp = temp.substring(6);
            int i = temp.lastIndexOf('"');

			if (i == -1)
				return temp;

			temp = temp.substring(0, i);
            temp = temp.trim();

            return temp;

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

        return "UNTITLED";
    }

    public String getTitle()
    {
        if (this.title.equals("UNTITLED"))
            this.title = this.getTitleFromTag();

        return this.title;
    }

    @Override
    public String toString()
    {
        return this.baseFile.toString();
    }
}
