package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.gui.Color;
import dk.mrspring.mcplayer.gui.DrawingHelper;
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

	double playHeadPosition = 0;

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

	public boolean mousePressed(int mouseX, int mouseY)
	{
		if (mouseX >= this.posX && mouseX < this.posX + this.width && mouseY >= this.posY && mouseY < this.posY + height)
		{
			int controllerHeight = 15;

			if (mouseX >= this.posX + 5 && mouseX < this.posX + 5 + controllerHeight && mouseY >= this.posY + this.height - 5 - controllerHeight && mouseY < this.posY + this.height - 5)
			{
				LiteModMCPlayer.thread.togglePausePlay();
				return true;
			}
			else if (mouseX >= this.posX + 5 && mouseX < this.posX + 10 + (controllerHeight * 2) && mouseY >= this.posY + this.height - 5 - controllerHeight && mouseY < this.posY + this.height - 5)
			{
				LiteModMCPlayer.thread.schedulePrev();
				return true;
			}
			else if (mouseX >= this.posX + 5 && mouseX < this.posX + 15 + (controllerHeight * 3) && mouseY >= this.posY + this.height - 5 - controllerHeight && mouseY < this.posY + this.height - 5)
			{
				LiteModMCPlayer.thread.scheduleNext();
				return true;
			} else return false;
		}
		else return false;
	}

	public void draw(Minecraft minecraft, int mouseX, int mouseY)
	{
		float barLength = this.width - 10;

		float barPosX = this.posX + 5;
		float barPosY = this.posY + (this.height / 2);


		float playHeadDiameter = 5;

		float playHeadPosX;
		float playHeadPosY;


		float controlDiameter = 15;

		if (this.showTitle)
			barPosY = this.posY + 5 + 8 + ((this.height - 8) / 2);

		if (this.showControls)
		{
			barLength -= ((controlDiameter + 5) * 3);
			barPosX += ((controlDiameter + 5) * 3);
		}

		this.isPlayheadClicked = mouseX >= barPosX && mouseX < barPosX + barLength && Mouse.isButtonDown(0) && mouseY >= this.posY && mouseY < this.posY + this.height + 5;

		if (!this.isPlayheadClicked)
		{
			if (this.wasMouseClicked)
			{
				double posInMillis = playHeadPosition * LiteModMCPlayer.thread.getLength().toMillis();
				Duration resumeFrom = new Duration(posInMillis);
				LiteModMCPlayer.thread.resumeFrom(resumeFrom);
				this.wasMouseClicked = false;
			}
			playHeadPosition = LiteModMCPlayer.thread.getPosition().toMillis() / LiteModMCPlayer.thread.getLength().toMillis();
		} else
		{
			LiteModMCPlayer.thread.pauseMusic();
			playHeadPosition = (mouseX - barPosX) / (double) barLength;

			if (playHeadPosition > 1)
				playHeadPosition = 1;
			else if (playHeadPosition < 0)
				playHeadPosition = 0;

			this.wasMouseClicked = true;
		}

		playHeadPosX = barPosX + (float) (barLength * playHeadPosition) - (playHeadDiameter / 2);
		playHeadPosY = barPosY - (playHeadDiameter / 2) + .5F;

		boolean isMouseHoveringPlayHead = ((mouseY >= playHeadPosY && mouseY < playHeadPosY + 5) && (mouseX >= playHeadPosX && mouseX < playHeadPosX + 5)) || this.isPlayheadClicked;

		if (isMouseHoveringPlayHead)
			this.cubeAlpha += 0.1F;
		else this.cubeAlpha -= 0.1F;

		if (this.cubeAlpha > 1F)
			this.cubeAlpha = 1F;
		else if (this.cubeAlpha < 0.5F)
			this.cubeAlpha = 0.5F;


		if (this.solidBackground)
			DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, Color.BLACK, 1F);
		else DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, Color.BLACK, 0.5F);

		DrawingHelper.drawRect(this.posX, this.posY, this.width, 1F, Color.WHITE, 1F);
		DrawingHelper.drawRect(this.posX, (this.posY + this.height) - 1F, this.width, 1F, Color.WHITE, 1F);

		DrawingHelper.drawRect(this.posX, this.posY, 1F, this.height, Color.WHITE, 1F);
		DrawingHelper.drawRect((this.posX + this.width) - 1F, this.posY, 1F, this.height, Color.WHITE, 1F);

		DrawingHelper.drawRect(barPosX, barPosY, barLength, 1, Color.WHITE, 0.5F);
		DrawingHelper.drawRect(barPosX, barPosY - 1, barLength * (float) playHeadPosition, 3, Color.WHITE, 1F);

		DrawingHelper.drawRect(playHeadPosX, playHeadPosY, 5, 5, Color.WHITE, this.cubeAlpha);

		if (this.showTitle)
		{
			String title = LiteModMCPlayer.thread.getCurrentlyPlaying().getTitle(), artist = LiteModMCPlayer.thread.getCurrentlyPlaying().getArtist();

			minecraft.fontRenderer.drawString(title, this.posX + 10, this.posY + 10, 0xFFFFFF, true);
			minecraft.fontRenderer.drawString(" by " + artist, this.posX + 10 + minecraft.fontRenderer.getStringWidth(title), this.posY + 10, 0xBBBBBB, true);
		}

		if (this.showControls)
		{
			float controlPosX = this.posX + 5;
			float controlPosY = barPosY - (controlDiameter / 2);

			this.drawPlayPauseControl(controlPosX, controlPosY, controlDiameter, controlDiameter, Color.WHITE, mouseX, mouseY);

			controlPosX += controlDiameter + 5;

			this.drawPlayPreviousControl(controlPosX, controlPosY, controlDiameter, controlDiameter, Color.WHITE, mouseX, mouseY);

			controlPosX += controlDiameter + 5;

			this.drawPlayNextControl(controlPosX, controlPosY, controlDiameter, controlDiameter, Color.WHITE, mouseX, mouseY);
		}

		/*this.isPlayheadClicked = mouseX >= this.posX + 5 && mouseX < this.posX + this.width - 5 && Mouse.isButtonDown(0) && mouseY >= this.posY && mouseY < this.posY + this.height + 5;

		if (this.solidBackground)
			DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, Color.BLACK, 1F);
		else DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, Color.BLACK, 0.5F);

		DrawingHelper.drawRect(this.posX, this.posY, this.width, 1F, Color.WHITE, 1F);
		DrawingHelper.drawRect(this.posX, (this.posY + this.height) - 1F, this.width, 1F, Color.WHITE, 1F);

		DrawingHelper.drawRect(this.posX, this.posY, 1F, this.height, Color.WHITE, 1F);
		DrawingHelper.drawRect((this.posX + this.width) - 1F, this.posY, 1F, this.height, Color.WHITE, 1F);

		float barLength = this.width - 10;
		float barHeight = this.posY + 5 + (this.height / 2);

		if (this.showTitle)
			barHeight = this.posY + 5 + 8 + ((this.height - 8) / 2);

		if (this.showControls)
		{
			float playPauseDiameter = 15F;

			float playPausePosX = this.posX + 5;
			float playPausePosY = barHeight - playPauseDiameter / 2;

			this.drawPlayPauseControl(playPausePosX, playPausePosY, playPauseDiameter, playPauseDiameter, Color.WHITE, mouseX, mouseY);

			barLength -= (playPauseDiameter + 5) * 3;
		}

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

			if (playheadPosition > 1)
				playheadPosition = 1;
			else if (playheadPosition < 0)
				playheadPosition = 0;

			this.wasMouseClicked = true;
		}

		DrawingHelper.drawRect(this.posX + 5, barHeight, barLength, 1, Color.WHITE, 0.5F);
		DrawingHelper.drawRect(this.posX + 5, barHeight - 1, barLength * (float) playheadPosition, 3, Color.WHITE, 1F);

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

		DrawingHelper.drawRect(playHeadPosX, playHeadPosY, 5, 5, Color.WHITE, this.cubeAlpha);

		if (this.showTitle)
		{
			String title = LiteModMCPlayer.thread.getCurrentlyPlaying().getTitle(), artist = LiteModMCPlayer.thread.getCurrentlyPlaying().getArtist();

			minecraft.fontRenderer.drawString(title, this.posX + 10, this.posY + 10, 0xFFFFFF, true);
			minecraft.fontRenderer.drawString(" by " + artist, this.posX + 10 + minecraft.fontRenderer.getStringWidth(title), this.posY + 10, 0xBBBBBB, true);
		}*/
	}

	private void drawPlayPauseControl(float x, float y, float width, float height, Color color, int mouseX, int mouseY)
	{
		float alpha = .5F;

		if ((mouseY >= y && mouseY < y + height) && (mouseX >= x && mouseX < x + width))
			alpha = 1F;

		DrawingHelper.drawRect(x, y, width, height, Color.BLACK, .5F);

		DrawingHelper.drawRect(x, y, width, 1F, color, 1F);
		DrawingHelper.drawRect(x, y + height - 1, width, 1F, color, 1F);

		DrawingHelper.drawRect(x, y, 1F, height, color, 1F);
		DrawingHelper.drawRect(x + width - 1, y, 1F, height, color, 1F);

		if (LiteModMCPlayer.thread.isPaused())
			DrawingHelper.drawPlayIcon(x + ((width / 10) * 2), y + ((height / 10) * 2), width - ((width / 10) * 4), height - ((height / 10) * 4), color, alpha);
		else DrawingHelper.drawPauseIcon(x + ((width / 10) * 2), y + ((height / 10) * 2), width - ((width / 10) * 4), height - ((height / 10) * 4), color, alpha);
	}

	private void drawPlayNextControl(float x, float y, float width, float height, Color color, int mouseX, int mouseY)
	{
		float alpha = .5F;

		if ((mouseY >= y && mouseY < y + height) && (mouseX >= x && mouseX < x + width))
			alpha = 1F;

		DrawingHelper.drawRect(x, y, width, height, Color.BLACK, .5F);

		DrawingHelper.drawRect(x, y, width, 1F, color, 1F);
		DrawingHelper.drawRect(x, y + height - 1, width, 1F, color, 1F);

		DrawingHelper.drawRect(x, y, 1F, height, color, 1F);
		DrawingHelper.drawRect(x + width - 1, y, 1F, height, color, 1F);

		DrawingHelper.drawPlayIcon(x + ((width / 10) * 2), y + (height / 4), width / 3, height / 2, color, alpha);
		DrawingHelper.drawPlayIcon(x + ((width / 10) * 2) + (width / 3), y + (height / 4), width / 3, height / 2, color, alpha);
	}

	private void drawPlayPreviousControl(float x, float y, float width, float height, Color color, int mouseX, int mouseY)
	{
		float alpha = .5F;

		if ((mouseY >= y && mouseY < y + height) && (mouseX >= x && mouseX < x + width))
			alpha = 1F;

		DrawingHelper.drawRect(x, y, width, height, Color.BLACK, .5F);

		DrawingHelper.drawRect(x, y, width, 1F, color, 1F);
		DrawingHelper.drawRect(x, y + height - 1, width, 1F, color, 1F);

		DrawingHelper.drawRect(x, y, 1F, height, color, 1F);
		DrawingHelper.drawRect(x + width - 1, y, 1F, height, color, 1F);

		DrawingHelper.drawPlayIcon(x + width - (((width / 10) * 2)), y + height - (height / 4), -(width / 3), -(height / 2), color, alpha);
		DrawingHelper.drawPlayIcon(x + width - (((width / 10) * 2) + (width / 3)), y + height - (height / 4), -(width / 3), -(height / 2), color, alpha);

		//DrawingHelper.drawPlayIcon(x + ((width / 10) * 2), y + (height / 4), width / 3, height / 2, color, alpha);
		//DrawingHelper.drawPlayIcon(x + ((width / 10) * 2) + (width / 3), y + (height / 4), width / 3, height / 2, color, alpha);
	}
}
