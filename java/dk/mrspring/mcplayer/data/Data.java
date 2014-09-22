package dk.mrspring.mcplayer.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.mrspring.mcplayer.file.FileLoader;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.list.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MrSpring on 21-09-2014 for MC Music Player.
 */
public class Data
{
	protected File dataDirectory;
	protected String musicPath;
	protected List<String> supportedExtensions = new ArrayList<String>();
	protected HashMap<String, MusicFile> allFiles = new HashMap<String, MusicFile>();

	public Data(String musicLocation, File dataLocation)
	{
		this.musicPath = musicLocation;
		this.dataDirectory = dataLocation;
	}

	public void addExtension(String extension)
	{
		if (!this.supportedExtensions.contains(extension))
			this.supportedExtensions.add(extension);
	}

	public MusicFile get(String name)
	{
		return this.allFiles.get(name);
	}

	public Collection<MusicFile> values()
	{
		return this.allFiles.values();
	}

	public int size()
	{
		return this.allFiles.size();
	}

	public boolean setCustomName(String key, String customName)
	{
		if (this.allFiles.containsKey(key))
		{
			MusicFile file = this.allFiles.get(key);
			file.setCustomTitle(customName);
			this.allFiles.put(key, file);
			return true;
		} else return false;
	}

	public Playlist createDefaultQueue()
	{
		Playlist queue = new Playlist("QUEUE");
		for (MusicFile file : this.allFiles.values())
		{
			queue.add(file.getTitleFromFieldKey());
			System.out.println("Adding " + file.getTitle() + " to default Playlist.");
		}
		return queue;
	}

	public void load() throws IOException
	{
		List<MusicFile> files = new ArrayList<MusicFile>();
		FileLoader.addFiles(this.musicPath, this.supportedExtensions, files);

		File jsonFile = new File(dataDirectory.getAbsolutePath() + "\\music_data.json");
		System.out.println("Music Data file: " + jsonFile.getAbsolutePath());

		if (!jsonFile.exists())
		{
//			jsonFile.mkdirs();
			jsonFile.createNewFile();
		}
		else
		{
//			jsonFile.mkdirs();
//			jsonFile.createNewFile();
			Gson gson = new Gson();

			BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
			MusicDataList list = gson.fromJson(reader, MusicDataList.class);

			for (MusicFile value : files)
			{
				String key = value.getTitleFromFieldKey();
				if (list != null)
				{
					MusicData data = list.get(key);
					value.copyFromData(data);
				}
				this.allFiles.put(key, value);
			}
		}
	}

	public void updateSong(MusicFile musicFile)
	{
		System.out.println(" Updating song title. Changing " + musicFile.getTitleFromFieldKey() + " to " + musicFile.getTitle());
		this.allFiles.put(musicFile.getTitleFromFieldKey(), musicFile);
		try
		{
			this.saveChangedNames();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void saveChangedNames() throws IOException
	{
		File jsonFile = new File(dataDirectory.getAbsolutePath() + "\\music_data.json");
		/*if (!jsonFile.exists())
			jsonFile.createNewFile();*/

		System.out.println("Updating names...");

		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

		Gson gson = builder.create();
		boolean changesFound = false;

		BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
		MusicDataList list = gson.fromJson(reader, MusicDataList.class);

		if (list == null)
			list = new MusicDataList();

		for (MusicFile value : this.allFiles.values())
		{
			System.out.println("Reloading " + value.getTitleFromFieldKey());
			String key = value.getTitleFromFieldKey();
			MusicData data = value.copyToData();
			if (!data.isDefault())
			{
				System.out.println("Data was not default...");
				list.put(key, value.copyToData());
				changesFound = true;
			} else System.out.println("Data was default. Title: " + data.title + ", Album: " + data.album + ", Artist: " + data.artist);

			/*String key = value.getTitleFromFieldKey();
			System.out.println("Updating " + key);
			if (list != null)
			{
				System.out.println("List is not null");
				boolean changed = false;
				MusicData data = list.get(key);
				MusicData fromValue = value.copyToData();
				if (!data.title.equals(fromValue.title))
				{
					data.title = fromValue.title;
					changed = true;
				}
				if (!data.album.equals(fromValue.album))
				{
					data.album = fromValue.album;
					changed = true;
				}
				if (!data.artist.equals(fromValue.artist))
				{
					data.artist = fromValue.artist;
					changed = true;
				}

				if (changed)
				{
					list.put(key, data);
					changesFound = true;
				}
			} else
			{
				System.out.println("List is null. Creating new...");
				list = new MusicDataList();
				MusicData data = new MusicData();
				boolean changed = false;
				if (!(value.getTitle().equals(value.getTitleFromFieldKey())))
				{
					data.title = value.getTitle();
					changed = true;
				}
				if (!(value.getAlbum().equals(value.getAlbumFromFieldKey())))
				{
					data.album = value.getAlbum();
					changed = true;
				}
				if (!(value.getArtist().equals(value.getArtistFromFieldKey())))
				{
					data.artist = value.getArtist();
					changed = true;
				}

				if (changed)
				{
					list.put(value.getTitle(), data);
					changesFound = true;
				}
			}*/
		}

		if (changesFound)
		{
			System.out.println("Updating Json file...");
			String jsonCode = gson.toJson(list);
			FileWriter writer = new FileWriter(jsonFile);
			writer.write(jsonCode);
			writer.close();
		}
	}
}
