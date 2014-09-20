package dk.mrspring.mcplayer.data;

import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.list.Playlist;

import java.util.HashMap;

/**
 * Created by Konrad on 19-09-2014 for MusicPlayer.
 */
public class Data
{
    public static final HashMap<String, MusicFile> allFiles = new HashMap<String, MusicFile>();

    public static Playlist<MusicFile> getAllFilesAsPlaylist()
    {
        return (Playlist<MusicFile>) allFiles.values();
    }
}
