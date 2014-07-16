package dk.mrspring.mcplayer.file;

import com.sun.istack.internal.NotNull;
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

/**
 * Created by Konrad on 16-07-2014 for MC Music Player.
 */
public class MusicFile
{
    public static final ResourceLocation DEFAULT_COVER_LOCATION = new ResourceLocation("mcplayer", "textures/cover/default.png");

    public static final int UNKNOWN = -1;
    public static final int MP3 = 0;

    protected File baseFile;
    protected int type = UNKNOWN;

    protected String title = "UNTITLED";
    protected String album = "UNTITLED";
    protected String artist = "UNTITLED";

    protected BufferedImage cover;
    protected int textureId;

    public MusicFile(@NotNull File file)
    {
        this.baseFile = file;

        // TODO Add extension recognition, and save it
    }

    public void bindCover(Minecraft minecraft)
    {
        if (this.getCover() == null)
            this.initializeCover();

        if (this.textureId != -1)
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        else
            minecraft.getTextureManager().bindTexture(DEFAULT_COVER_LOCATION);
    }

    public BufferedImage getCover()
    {
        if (this.cover == null)
            this.initializeCover();

        return this.cover;
    }

    public void initializeCover()
    {
        try
        {
            String fileName = this.getArtist() + "_" + this.getTitle() + ".png";
            File file = new File(LiteModMCPlayer.coverLocation.getAbsolutePath() + "/" + fileName);

            if (file.exists())
                this.cover = ImageIO.read(file);
            else
            {
                AudioFile f = AudioFileIO.read(this.baseFile);
                TagField coverArtField = f.getTag().getFirstField(FieldKey.COVER_ART);
                FrameBodyAPIC bodyAPIC = (FrameBodyAPIC) ((ID3v23Frame) coverArtField).getBody();
                byte[] rawImage = (byte[]) bodyAPIC.getObjectValue(DataTypes.OBJ_PICTURE_DATA);
                BufferedImage bufferedImage = ImageIO.read(ImageIO.createImageInputStream(new ByteArrayInputStream(rawImage)));
                file.createNewFile();
                this.cover = bufferedImage;
                ImageIO.write(bufferedImage, "png", file);
            }

            this.textureId = TextureLoader.loadTexture(this.cover);
        } catch(IOException e)
        {
            e.printStackTrace();
        } catch (TagException e)
        {
            e.printStackTrace();
        } catch (ReadOnlyFileException e)
        {
            e.printStackTrace();
        } catch (CannotReadException e)
        {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e)
        {
            e.printStackTrace();
        }
    }

    public File getBaseFile()
    {
        return this.baseFile;
    }

    public String getField(FieldKey key)
    {
        String temp = "UNTITLED";

        try
        {
            AudioFile file = AudioFileIO.read(this.getBaseFile());

            temp = file.getTag().getFirst(key);
            return temp.trim();
        } catch (CannotReadException e)
        {
            e.printStackTrace();
            return temp;
        } catch (IOException e)
        {
            e.printStackTrace();
            return temp;
        } catch (TagException e)
        {
            e.printStackTrace();
        } catch (ReadOnlyFileException e)
        {
            e.printStackTrace();
            return temp;
        } catch (InvalidAudioFrameException e)
        {
            e.printStackTrace();
            return temp;
        }

        return temp;
    }

    public String getTitle()
    {
        if (this.title.equals("UNTITLED"))
            this.title = this.getField(FieldKey.TITLE);

        return this.title;
    }

    public String getAlbum()
    {
        if (this.album.equals("UNTITLED"))
            this.album = this.getField(FieldKey.ALBUM);

        return this.album;
    }

    public String getArtist()
    {
        if (this.artist.equals("UNTITLED"))
            this.artist = this.getField(FieldKey.ARTIST);

        return this.album;
    }
}
