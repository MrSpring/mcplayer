package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import javafx.util.Duration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.ReadableColor;

/**
 * Created by MrSpring on 28-07-14 for MC Music Player.
 */
public class GuiMusicScrubber extends Gui
{
	int posX, posY;
	int width, height;
	boolean showTitle;
	boolean mouseClicked = false;
	boolean isPlayheadClicked = false;
	boolean solidBackground = false;
	boolean showControls = false;
	float cubeAlpha = 0F;
	boolean wasMouseClicked = false;
	double playheadPosition;

	public GuiMusicScrubber(int x, int y, int width, int height, boolean showTitle)
	{
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.showTitle = showTitle;
		System.out.println(this.width);
	}

	public GuiMusicScrubber setSolidBackground()
	{
		this.solidBackground = true;
		return this;
	}

	public GuiMusicScrubber setTransparentBackground()
	{
		this.solidBackground = false;
		return this;
	}

	public GuiMusicScrubber enableControls()
	{
		this.showControls = true;
		return this;
	}

	public GuiMusicScrubber disableControls()
	{
		this.showControls = false;
		return this;
	}

	public void draw(Minecraft minecraft, int mouseX, int mouseY)
	{
		this.isPlayheadClicked = mouseX >= this.posX + 5 && mouseX < this.posX + this.width - 5 && Mouse.isButtonDown(0) && mouseY >= this.posY && mouseY < this.posY + this.height + 5;

		if (this.solidBackground)
			DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, ReadableColor.BLACK, 1F);
		else DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, ReadableColor.BLACK, 0.5F);

		DrawingHelper.drawRect(this.posX, this.posY, this.width, 1F, ReadableColor.WHITE, 1F);
		DrawingHelper.drawRect(this.posX, (this.posY + this.height) - 1F, this.width, 1F, ReadableColor.WHITE, 1F);

		DrawingHelper.drawRect(this.posX, this.posY, 1F, this.height, ReadableColor.WHITE, 1F);
		DrawingHelper.drawRect((this.posX + this.width) - 1F, this.posY, 1F, this.height, ReadableColor.WHITE, 1F);

		int barLength = this.width - 10;
		if (!this.isPlayheadClicked)
		{
			if (this.wasMouseClicked)
			{
				double posInMillis = playheadPosition * LiteModMCPlayer.thread.getLength().toMillis();
				Duration resumeFrom = new Duration(posInMillis);
				LiteModMCPlayer.thread.resumeFrom(resumeFrom);
				this.wasMouseClicked = false;
			}
			playheadPosition = LiteModMCPlayer.thread.getPosition().toMillis() / LiteModMCPlayer.thread.getLength().toMillis();
		}
		else
		{
			LiteModMCPlayer.thread.pauseMusic();
			playheadPosition = (mouseX - this.posX - 5) / (double) barLength;
			this.wasMouseClicked = true;
		}

		float barHeight = this.posY + 5 + (this.height / 2);

		if (this.showTitle)
			barHeight = this.posY + 5 + 8 + ((this.height - 8) / 2);

		if (this.showControls)
		{


			/*if (LiteModMCPlayer.thread.isPaused())
				DrawingHelper.drawPlayIcon(this.posX + 50, this.posY + 50, 20, 20, ReadableColor.WHITE, 1F, mouseX, mouseY);
			else DrawingHelper.drawPauseIcon(this.posX + 50, this.posY + 50, 20, 20, ReadableColor.WHITE, 1F, mouseX, mouseY);*/
		}

		DrawingHelper.drawRect(this.posX + 5, barHeight, this.width - 10, 1, ReadableColor.WHITE, 0.5F);
		DrawingHelper.drawRect(this.posX + 5, barHeight - 1, (this.width - 10) * (float) playheadPosition, 3, ReadableColor.WHITE, 1F);

		float playHeadPosX = this.posX + 3 + (float) (barLength * playheadPosition);
		float playHeadPosY = barHeight - 2;

		boolean isMouseHoveringPlayHead = ((mouseY >= playHeadPosY && mouseY < playHeadPosY + 5) && (mouseX >= playHeadPosX && mouseX < playHeadPosX + 5)) || isPlayheadClicked;

		if (isMouseHoveringPlayHead)
			this.cubeAlpha += 0.1F;
		else this.cubeAlpha -= 0.1F;

		if (this.cubeAlpha > 1F)
			this.cubeAlpha = 1F;
		else if (this.cubeAlpha < 0.5F)
			this.cubeAlpha = 0.5F;

		DrawingHelper.drawRect(playHeadPosX, playHeadPosY, 5, 5, ReadableColor.WHITE, this.cubeAlpha);

		if (this.showTitle)
		{
			String title = LiteModMCPlayer.thread.getCurrentlyPlaying().getTitle(), artist = LiteModMCPlayer.thread.getCurrentlyPlaying().getArtist();

			minecraft.fontRenderer.drawString(title, this.posX + 10, this.posY + 10, 0xFFFFFF, true);
			minecraft.fontRenderer.drawString(" by " + artist, this.posX + 10 + minecraft.fontRenderer.getStringWidth(title), this.posY + 10, 0xBBBBBB, true);
		}
	}


}
