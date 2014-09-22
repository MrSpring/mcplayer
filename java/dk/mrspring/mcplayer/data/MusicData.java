package dk.mrspring.mcplayer.data;

import dk.mrspring.mcplayer.file.MusicFile;

/**
 * Created by MrSpring on 21-09-2014 for MC Music Player.
 */
public class MusicData
{
	public String title = MusicFile.USE_FIELD_KEY, album = MusicFile.USE_FIELD_KEY, artist = MusicFile.USE_FIELD_KEY;

	public boolean isDefault()
	{
		return this.title.equals(MusicFile.USE_FIELD_KEY) && this.album.equals(MusicFile.USE_FIELD_KEY) && this.artist.equals(MusicFile.USE_FIELD_KEY);
	}
}
