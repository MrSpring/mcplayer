package dk.mrspring.mcplayer.gui.screen;

import dk.mrspring.mcplayer.ColorScheme;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import dk.mrspring.mcplayer.gui.fancy.GuiMusicFile;
import net.minecraft.client.gui.GuiScreen;

/**
 * Created by MrSpring on 20-09-2014 for Music Player for Music Player.
 */
public class GuiScreenAllMusic extends GuiScreen
{
	GuiMusicFile musicFile;

	@Override
	public void initGui()
	{
		this.musicFile = new GuiMusicFile(LiteModMCPlayer.thread.getCurrentlyPlaying(), 5, 5, 100, 20);
		this.musicFile.setDisplayType(GuiMusicFile.DisplayType.VERTICAL);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3)
	{
		ColorScheme scheme = LiteModMCPlayer.config.getColorScheme();

		DrawingHelper.drawRect(0F, 0F, (float) this.width, this.height, scheme.getBaseColor(), scheme.getBaseAlpha());

		DrawingHelper.drawRect(0F, 0F, (float) this.width, 50F, scheme.getBaseColor(), scheme.getBaseAlpha());
		DrawingHelper.drawRect(0F, (float) this.height - 50, (float) this.width, 50F, scheme.getBaseColor(), scheme.getBaseAlpha());

		DrawingHelper.drawRect(0F, 49F, (float) this.width, 1F, scheme.getOutlineColor(), 1F);
		DrawingHelper.drawRect(0F, (float) this.height - 50, (float) this.width, 1F, scheme.getOutlineColor(), 1F);

		this.musicFile.draw(mc, false);
	}
}
