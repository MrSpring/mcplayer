package dk.mrspring.mcplayer;

/**
 * Created by MrSpring on 25-07-14 for MC Music Player.
 */
public class Config
{
	public float overlay_transparency = 0.5F;
	public int percent_before_showing_next_up = 90;

	public float getOverlayAlpha()
	{
		return this.overlay_transparency;
	}

	public double getNextUpDecimal()
	{
		double temp = this.percent_before_showing_next_up;
		return temp / 100;
	}
}
