package dk.mrspring.mcplayer;

/**
 * Created by MrSpring on 25-07-14 for MC Music Player.
 */
public class ModConfig
{
	public float overlay_transparency = 0.5F;
	public int percent_before_showing_next_up = 90;
    public boolean overlay_size = true;
    public String music_path = System.getProperty("user.home") + "\\Music";

	public float getOverlayAlpha()
	{
		return this.overlay_transparency;
	}

    public int getOverlayAlphaAsPercentage()
    {
        float temp = this.getOverlayAlpha() * 100;
        return (int) temp;
    }

	public double getNextUpDecimal()
	{
		double temp = this.percent_before_showing_next_up;
		return temp / 100;
	}

    public String getMusicPath()
    {
        return this.music_path;
    }

    public void setMusicPath(String path)
    {
        this.music_path = path;
    }

    public int getNextUpPercentage()
    {
        return this.percent_before_showing_next_up;
    }

    public void setNextUpPercentage(int percentage)
    {
        this.percent_before_showing_next_up = percentage;
    }

    public boolean getOverlaySize()
    {
        return this.overlay_size;
    }

    public void toggleOverlaySize()
    {
        this.overlay_size = !this.overlay_size;
    }

    public void setOverlayTransparency(int percentage)
    {
        float temp = percentage;
        this.setOverlayTransparency(temp / 100);
    }

    public void setOverlayTransparency(float value)
    {
        this.overlay_transparency = value;
    }
}
