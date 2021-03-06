package dk.mrspring.mcplayer.file;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.data.MusicData;
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

	public static final String USE_FIELD_KEY = "USE_FIELD_KEY";

	protected String title = "UNTITLED";
	protected String album = "UNTITLED";
	protected String artist = "UNTITLED";
	protected String genre = "UNAVAILABLE";
	protected String year = "UNAVAILABLE";
	protected String composer = "UNAVAILABLE";

	protected String customTitle = USE_FIELD_KEY;
	protected String customAlbum = USE_FIELD_KEY;
	protected String customArtist = USE_FIELD_KEY;

	protected BufferedImage cover;
	protected int textureId;

	public MusicFile(final File file)
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

	public boolean skip()
	{
		return false;
	}

	public void initializeCover()
	{
		try
		{
			String fileName = this.getArtist() + "_" + this.getTitle() + ".png";
			File file = new File(LiteModMCPlayer.coverLocation.getAbsolutePath() + "/" + fileName);

			if (file.exists())
			{
				this.cover = ImageIO.read(file);
				this.textureId = TextureLoader.loadTexture(this.cover);
			} else
			{
				AudioFile f = AudioFileIO.read(this.baseFile);
				TagField coverArtField = f.getTag().getFirstField(FieldKey.COVER_ART);
				if (coverArtField != null)
				{
					FrameBodyAPIC bodyAPIC = (FrameBodyAPIC) ((ID3v23Frame) coverArtField).getBody();
					byte[] rawImage = (byte[]) bodyAPIC.getObjectValue(DataTypes.OBJ_PICTURE_DATA);
					BufferedImage bufferedImage = ImageIO.read(ImageIO.createImageInputStream(new ByteArrayInputStream(rawImage)));
					file.createNewFile();
					this.cover = bufferedImage;
					ImageIO.write(bufferedImage, "png", file);
					this.textureId = TextureLoader.loadTexture(this.cover);
				}
			}

		} catch (IOException e)
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

    public String toKey()
    {
        String key;
        String artist = this.getArtistFromFieldKey(), title = this.getTitleFromFieldKey();
        key = (artist.toLowerCase().trim().replace(" ", "_") + "-" + title.toLowerCase().trim().replace(" ", "_"));
        return key;
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

	public String getGenre()
	{
		if (this.genre.equals("UNAVAILABLE"))
			this.genre = this.getField(FieldKey.GENRE);
		return this.genre;
	}

	public String getYear()
	{
		if (this.year.equals("UNAVAILABLE"))
			this.year = this.getField(FieldKey.YEAR);
		return this.year;
	}

	public String getComposer()
	{
		if (this.composer.equals("UNAVAILABLE"))
			this.composer = this.getField(FieldKey.COMPOSER);
		return this.composer;
	}

	private String getTitleFromFieldKey()
	{
		if (this.title.equals("UNTITLED"))
			this.title = this.getField(FieldKey.TITLE);
		return this.title;
	}

	public String getTitle()
	{
		if (this.customTitle.equals(USE_FIELD_KEY))
			return this.getTitleFromFieldKey();
		else return this.customTitle;
	}

	private String getAlbumFromFieldKey()
	{
		if (this.album.equals("UNTITLED"))
			this.album = this.getField(FieldKey.ALBUM);
		return this.album;
	}

	public String getAlbum()
	{
		if (this.customAlbum.equals(USE_FIELD_KEY))
			return this.getAlbumFromFieldKey();
		else return this.customAlbum;
	}

	private String getArtistFromFieldKey()
	{
		if (this.artist.equals("UNTITLED"))
			this.artist = this.getField(FieldKey.ARTIST);
		return this.artist;
	}

	public String getArtist()
	{
		if (this.customArtist.equals(USE_FIELD_KEY))
			return this.getArtistFromFieldKey();
		else return this.customArtist;
	}

	public void setCustomTitle(String customTitle)
	{
		this.customTitle = customTitle;
	}

	public void setCustomAlbum(String customAlbum)
	{
		this.customAlbum = customAlbum;
	}

	public void setCustomArtist(String customArtist)
	{
		this.customArtist = customArtist;
	}

	public void copyFromData(MusicData data)
	{
		this.setCustomTitle(data.title);
		this.setCustomAlbum(data.album);
		this.setCustomArtist(data.artist);
	}

	public MusicData copyToData()
	{
		MusicData data = new MusicData();
		data.title = this.customTitle;
		data.album = this.customAlbum;
		data.artist = this.customArtist;
		return data;
	}
}
