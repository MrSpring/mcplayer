package dk.mrspring.mcplayer.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 21-09-2014 for MC Music Player.
 */
public class MusicDataList
{
//	HashMap<String, MusicData> map = new HashMap<String, MusicData>();
	List<String> keys = new ArrayList<String>();
	List<MusicData> data = new ArrayList<MusicData>();

	public MusicData get(String key)
	{
		try
		{
			if (this.keys != null && this.data != null)
			{
				int indexInKeys = this.keys.indexOf(key);
				return this.data.get(indexInKeys);
			} else return new MusicData();
		}
		catch (Exception e)
		{
			return new MusicData();
		}
	}

	public void put(String key, MusicData value)
	{
		if (this.keys.contains(key))
		{
			int indexInKeys = this.keys.indexOf(key);
			this.data.set(indexInKeys, value);
		} else
		{
			this.keys.add(key);
			this.data.add(value);
		}
	}
}
