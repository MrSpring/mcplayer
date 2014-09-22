package dk.mrspring.mcplayer.file;

import dk.mrspring.mcplayer.list.Playlist;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Konrad on 16-07-2014 for MC Music Player.
 */
public class FileLoader
{
    public static void addFiles(String path, List<String> extensions, List<MusicFile> fileList)
    {
        try
        {
			File directory = new File(path);
			System.out.println("Loading files from: " + directory.toURI().toASCIIString());
			System.out.println("Directory list size: " + directory.list().length);
			String[] directoryContents = directory.list();

            for (String fileName : directoryContents)
            {
                File temp = new File(String.valueOf(directory), fileName);
                int i = temp.toString().lastIndexOf('.');
                if (i > 0)
                {
                    String tempExtension = temp.toString().substring(i);
                    if (extensions.contains(tempExtension))
                    {
                        System.out.println(" Found file: " + temp.toString() + ". Adding it to the list.");
                        fileList.add(new MusicFile(temp));
                    }
                    else
                        System.out.println(" Found file: " + temp.toString() + ". Ignoring it.");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
