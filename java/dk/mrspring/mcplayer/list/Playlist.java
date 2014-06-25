package dk.mrspring.mcplayer.list;

import dk.mrspring.mcplayer.file.MusicFile;

import java.util.*;

/**
 * Created by Konrad on 25-06-2014.
 */
public class Playlist<MusicFile> implements List<MusicFile>
{
    protected List files = new ArrayList<MusicFile>();
    protected String name = "UNNAMED";

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
    public <T> T[] toArray(T[] a)
    {
        return (T[]) this.files.toArray(a);
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
    public boolean addAll(int index, Collection<? extends MusicFile> c)
    {
        return this.files.addAll(index, c);
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

    @Override
    public MusicFile get(int index)
    {
        return (MusicFile) this.files.get(index);
    }

    @Override
    public MusicFile set(int index, MusicFile element)
    {
        return (MusicFile) this.files.set(index, element);
    }

    @Override
    public void add(int index, MusicFile element)
    {
        this.files.add(index, element);
    }

    @Override
    public MusicFile remove(int index)
    {
        return (MusicFile) this.files.remove(index);
    }

    @Override
    public int indexOf(Object o)
    {
        return this.files.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return this.files.lastIndexOf(o);
    }

    @Override
    public ListIterator<MusicFile> listIterator()
    {
        return this.files.listIterator();
    }

    @Override
    public ListIterator<MusicFile> listIterator(int index)
    {
        return this.files.listIterator(index);
    }

    @Override
    public List<MusicFile> subList(int fromIndex, int toIndex)
    {
        return this.files.subList(fromIndex, toIndex);
    }
}
