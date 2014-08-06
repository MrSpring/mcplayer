package dk.mrspring.mcplayer;

import dk.mrspring.mcplayer.gui.Color;

/**
 * Created by MrSpring on 06-08-14 for MC Music Player.
 */
public enum ColorScheme
{
	HIGH_CONTRAST(Color.BLACK, Color.WHITE, Color.CYAN, .5F),
	RED(Color.RED, Color.RED, Color.ORANGE, .25F),
	NYAN(Color.CYAN, Color.BLUE, Color.GREEN, .25F);

	protected final Color baseColor, outlineColor, progressbarColor;
	protected final float baseAlpha;

	private ColorScheme(Color baseColor, Color outlineColor, Color barColor, float baseAlpha)
	{
		this.baseColor = baseColor;
		this.outlineColor = outlineColor;
		this.progressbarColor = barColor;
		this.baseAlpha = baseAlpha;
	}

	public Color getProgressbarColor()
	{
		return progressbarColor;
	}

	public Color getBaseColor()
	{
		return baseColor;
	}

	public Color getOutlineColor()
	{
		return outlineColor;
	}

	public float getBaseAlpha()
	{
		return baseAlpha;
	}
}
