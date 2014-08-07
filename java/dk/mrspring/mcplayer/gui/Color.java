package dk.mrspring.mcplayer.gui;

/**
 * Created by MrSpring on 31-07-14 for MC Music Player.
 */
public class Color
{
	float r, g, b;
	String name;

	public static final Color BLACK = new Color("black", 0F, 0F, 0F);
	public static final Color WHITE = new Color("white", 1F, 1F, 1F);

	public static final Color RED = new Color("red", 1F, 0F, 0F);
	public static final Color LTRED = new Color("light_red", 1F, .75F, .75F);
	public static final Color GREEN = new Color("green", 0F, 1F, 0F);
	public static final Color BLUE = new Color("blue", 0F, 0F, 1F);
	public static final Color LTBLUE = new Color("light_blue", .75F, .75F, 1F);

	public static final Color ORANGE = new Color("orange", 1F, .5F, 0F);
	public static final Color YELLOW = new Color("yellow", 1F, 1F, 0F);
	public static final Color CYAN = new Color("cyan", 0F, 1F, 1F);
	public static final Color PURPLE = new Color("purple", 1F, 0, 1F);

	public static final Color LTGREY = new Color("light_grey", .75F, .75F, .75F);
	public static final Color DKGREY = new Color("dark_grey", .25F, .25F, .25F);
	public static final Color GREY = new Color("grey", .5F, .5F, .5F);

	public Color(String name, float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.name = name;
	}

	public float getRed()
	{
		return this.r;
	}

	public float getGreen()
	{
		return this.g;
	}

	public float getBlue()
	{
		return this.b;
	}

	public String getName()
	{
		return name;
	}
}
