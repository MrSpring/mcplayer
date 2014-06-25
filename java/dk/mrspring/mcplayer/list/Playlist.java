package dk.mrspring.mcplayer.file;

import dk.mrspring.mcplayer.file.MusicFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Konrad on 25-06-2014.
 */
public class Playlist<MusicFile> implements Collection<MusicFile>
{
    protected List files = new ArrayList<MusicFile>();
    protected String name = "UNTITLED";

    public Playlist(String name)
    {
        this.name = name;
    }

    @Override
    public int size()
    {
        return this.files.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.files.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return this.files.contains(o);
    }

    @Override
    public Iterator<MusicFile> iterator()
    {
        return this.files.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return this.files.toArray();
    }

    @Override
    public <MusicFile> MusicFile[] toArray(MusicFile[] a)
    {
        return (MusicFile[]) this.files.toArray(a);
    }

    @Override
    public boolean add(MusicFile musicFile)
    {
        return this.files.add(musicFile);
    }

    @Override
    public boolean remove(Object o)
    {
        return this.files.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return this.files.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends MusicFile> c)
    {
        return this.files.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return this.files.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return this.files.retainAll(c);
    }

    @Override
    public void clear()
    {
        this.files.clear();
    }
}
