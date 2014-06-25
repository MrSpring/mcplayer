package dk.mrspring.mcplayer.file;

import java.io.File;
import java.util.List;

/**
 * Created by Konrad on 25-06-2014.
 */
public class FileLoader
{
    public static void addFiles(String path, List<String> extensions, dk.mrspring.mcplayer.list.Playlist<MusicFile> fileList)
    {
        try
        {
            File directory = new File(path);
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
