package dk.mrspring.mcplayer.file;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.datatype.DataTypes;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
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
	protected BufferedImage cover;
    protected int textureId = -1;

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

    public File getBaseFile()
    {
        return this.baseFile;
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

	public void loadCover()
	{
		try
		{
            System.out.println(" Test");

            String fileName = this.getTitle().toLowerCase();
            File file = new File(LiteModMCPlayer.coverDirectory.getPath() + "/" + fileName + ".png");

            if (file.exists())
            {
                this.cover = ImageIO.read(file);
            }
            else
            {
                AudioFile f = AudioFileIO.read(this.baseFile);
                TagField coverArtField = f.getTag().getFirstField(FieldKey.COVER_ART);
                FrameBodyAPIC body = (FrameBodyAPIC) ((ID3v23Frame) coverArtField).getBody();
                byte[] rawImage = (byte[]) body.getObjectValue(DataTypes.OBJ_PICTURE_DATA);
                BufferedImage bi = ImageIO.read(ImageIO.createImageInputStream(new ByteArrayInputStream(rawImage)));
                System.out.println(" File path: " + file.getAbsolutePath());
                file.createNewFile();
                System.out.println(" Creating new File, name: " + file.toString());
                this.cover = bi;
                ImageIO.write(bi, "png", file);
            }


            this.textureId = TextureLoader.loadTexture(this.cover);

			// else
				// TODO return the cover

			/*byte[] rawImage = f.getTag().getFirstField(FieldKey.COVER_ART).getRawContent();

			InputStream inputStream = new ByteArrayInputStream(rawImage);
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			ImageIO.write(bufferedImage, "png", new File("mcplayer/covers/cover.png"));*/
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

    public BufferedImage getCover()
    {
        if (this.cover == null)
            this.loadCover();

        return this.cover;
    }

    public void bindCover(Minecraft minecraft)
    {
        if (this.cover == null)
            this.loadCover();

        if (this.textureId != -1)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        }
        else
            minecraft.getTextureManager().bindTexture(new ResourceLocation("mcplayer", "textures/cover/default.png"));
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
