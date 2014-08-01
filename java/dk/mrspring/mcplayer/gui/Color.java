package dk.mrspring.mcplayer.gui;

/**
 * Created by MrSpring on 31-07-14 for MC Music Player.
 */
public class Color
{
	int r, g, b;
	String name;

	public static final Color BLACK = new Color("black", 0, 0, 0);
	public static final Color WHITE = new Color("white", 255, 255, 255);

	public static final Color RED = new Color("red", 255, 0, 0);
	public static final Color GREEN = new Color("green", 0, 255, 0);
	public static final Color BLUE = new Color("blue", 0, 0, 255);

	public static final Color ORANGE = new Color("orange", 255, 128, 0);
	public static final Color YELLOW = new Color("yellow", 255, 255, 0);
	public static final Color CYAN = new Color("cyan", 0, 255, 255);
	public static final Color PURPLE = new Color("purple", 255, 0, 255);

	public static final Color LTGREY = new Color("light_grey", 192, 192, 192);
	public static final Color DKGREY = new Color("dark_grey", 64, 64, 64);
	public static final Color GREY = new Color("grey", 128, 128, 128);

	public Color(String name, int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.name = name;
	}

	public int getRed()
	{
		return this.r;
	}

	public int getGreen()
	{
		return this.g;
	}

	public int getBlue()
	{
		return this.b;
	}

	public String getName()
	{
		return name;
	}
}
