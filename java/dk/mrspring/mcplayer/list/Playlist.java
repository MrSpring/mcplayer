package dk.mrspring.mcplayer.list;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.file.MusicFile;

import java.util.*;

/**
 * Created by Konrad on 16-07-2014 for MC Music Player.
 */
public class Playlist implements List<String>
{
    protected List files = new ArrayList<String>();
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
    public Iterator<String> iterator()
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
    public boolean add(String musicFile)
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
    public boolean addAll(Collection<? extends String> c)
    {
        return this.files.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c)
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
    public String get(int index)
    {
        return (String) this.files.get(index);
    }

	public MusicFile getFile(int index)
	{
		return LiteModMCPlayer.data.get(this.get(index));
	}

    @Override
    public String set(int index, String element)
    {
        return (String) this.files.set(index, element);
    }

    @Override
    public void add(int index, String element)
    {
        this.files.add(index, element);
    }

    @Override
    public String remove(int index)
    {
        return (String) this.files.remove(index);
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
    public ListIterator<String> listIterator()
    {
        return this.files.listIterator();
    }

    @Override
    public ListIterator<String> listIterator(int index)
    {
        return this.files.listIterator(index);
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex)
    {
        return this.files.subList(fromIndex, toIndex);
    }
}
