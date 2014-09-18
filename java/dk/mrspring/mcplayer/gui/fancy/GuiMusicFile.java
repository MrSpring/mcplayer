package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.ColorScheme;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

/**
 * Created by MrSpring on 17-09-2014 for MC Music Player.
 */
public class GuiMusicFile extends Gui
{
	int xPos, yPos, width, height;
	MusicFile toDisplay;

	public GuiMusicFile(MusicFile file, int x, int y, int w, int h)
	{
		this.xPos = x;
		this.yPos = y;

		this.width = w;
		this.height = h;

		this.toDisplay = file;
	}

	public void draw(Minecraft minecraft, boolean isHighlighted)
	{
		ColorScheme scheme = LiteModMCPlayer.config.getColorScheme();

		this.drawBack(isHighlighted, scheme);


		this.toDisplay.bindCover(minecraft);
		DrawingHelper.drawTexturedRect(this.xPos + 6, this.yPos + 6, this.height - 12, this.height - 12, 0, 0, 512, 512, 1F);


		FontRenderer renderer = minecraft.fontRenderer;

		String title = this.toDisplay.getTitle();
		String album = this.toDisplay.getAlbum();
		String artist = this.toDisplay.getArtist();

		int maxTextLength = this.width - this.height - 12;
		int textHeight = 15;

		renderer.drawSplitString(title, this.xPos + 1 + this.height, textHeight + this.yPos + 2 + 1, maxTextLength, 0x333333);
		renderer.drawSplitString(title, this.xPos + this.height, textHeight + this.yPos + 2, maxTextLength, 0xFFFFFF);

		if (renderer.getStringWidth(title) > maxTextLength)
			textHeight += 10;

		textHeight += 15;

		renderer.drawSplitString(album, this.xPos + 1 + this.height, textHeight + this.yPos + 2, maxTextLength, 0x333333);
		renderer.drawSplitString(album, this.xPos + this.height, textHeight + this.yPos + 2, maxTextLength, 0xFFFFFF);

		if (renderer.getStringWidth(title) > maxTextLength)
			textHeight += 10;

		textHeight += 15;

		renderer.drawSplitString(artist, this.xPos + 1 + this.height, textHeight + this.yPos + 2, maxTextLength, 0x333333);
		renderer.drawSplitString(artist, this.xPos + this.height, textHeight + this.yPos + 2, maxTextLength, 0xFFFFFF);
	}

	public void drawBack(boolean isHighlighted, ColorScheme scheme)
	{
		float alpha = scheme.getBaseAlpha();
		if (isHighlighted)
			alpha += .25F;

		DrawingHelper.drawRect(this.xPos + 1, this.yPos + 1, this.width - 2, this.height - 2, scheme.getBaseColor(), alpha);

		if (isHighlighted)
		{
			DrawingHelper.drawRect(this.xPos, this.yPos, this.width, 1, scheme.getOutlineColor(), 1F);
			DrawingHelper.drawRect(this.xPos, this.yPos + this.height - 1, this.width, 1, scheme.getOutlineColor(), 1F);

			DrawingHelper.drawRect(this.xPos, this.yPos, 1, this.height, scheme.getOutlineColor(), 1F);
			DrawingHelper.drawRect(this.xPos + this.width - 1, this.yPos, 1, this.height, scheme.getOutlineColor(), 1F);
		}
	}

	public GuiMusicFile setPosition(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
		return this;
	}

	public GuiMusicFile setDimensions(int width, int height)
	{
		this.width = width;
		this.height = height;
		return this;
	}

	public MusicFile setFileToDisplay(MusicFile file)
	{
		MusicFile oldFile = this.toDisplay;
		this.toDisplay = file;
		return oldFile;
	}
}
