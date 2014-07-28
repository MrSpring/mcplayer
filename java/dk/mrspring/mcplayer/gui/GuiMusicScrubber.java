package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import javafx.util.Duration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

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
	float cubeRadius = 3;
	float cubeAlpha = 0F;
	boolean wasMouseClicked = false;
	double playheadPosition;

	float cubeX, cubeY;

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

	public void draw(Minecraft minecraft, int mouseX, int mouseY)
	{
		//if (mouseX >= this.posX + 5 && mouseX < this.posX + this.width - 5 && Mouse.isButtonDown(0) && mouseY >= this.posY + 5 && mouseY < this.posY + this.height - 5)
			//System.out.println(" Mouse was clicked! X: " + mouseX + ", Y: " + mouseY);
		this.isPlayheadClicked = mouseX >= this.posX + 5 && mouseX < this.posX + this.width - 5 && Mouse.isButtonDown(0) && mouseY >= this.posY + 5 && mouseY < this.posY + this.height - 5;

		if (this.solidBackground)
			glDrawRect(this.posX, this.posY, this.width, this.height, ReadableColor.BLACK, 1F);
		else glDrawRect(this.posX, this.posY, this.width, this.height, ReadableColor.BLACK, 0.5F);

		glDrawRect(this.posX, this.posY, this.width, 1F, ReadableColor.WHITE, 1F);
		glDrawRect(this.posX, (this.posY + this.height) - 1F, this.width, 1F, ReadableColor.WHITE, 1F);

		glDrawRect(this.posX, this.posY, 1F, this.height, ReadableColor.WHITE, 1F);
		glDrawRect((this.posX + this.width) - 1F, this.posY, 1F, this.height, ReadableColor.WHITE, 1F);

		int barLength = this.width - 10;
		if (!this.isPlayheadClicked)
		{
			if (this.wasMouseClicked)
			{
				double posInMillis = playheadPosition * LiteModMCPlayer.thread.getLength().toMillis();
				Duration resumeFrom = new Duration(posInMillis);
				System.out.println(" PosInMillis: " + posInMillis + ", Duration.toMillis(): " + resumeFrom.toMillis() + " length in millis: " + LiteModMCPlayer.thread.getLength().toMillis());
				LiteModMCPlayer.thread.resumeFrom(resumeFrom);
				this.wasMouseClicked = false;
			}
			playheadPosition = LiteModMCPlayer.thread.getPosition().toMillis() / LiteModMCPlayer.thread.getLength().toMillis();
		}
		else
		{
			LiteModMCPlayer.thread.pauseMusic();
			playheadPosition = (mouseX - this.posX + 5) / (double) barLength;
			this.wasMouseClicked = true;
		}

		//System.out.println(" Playhead position in millis: " + playheadPosition * LiteModMCPlayer.thread.getLength().toMillis() + ", Actual Length: " + LiteModMCPlayer.thread.getLength().toMillis());

		glDrawRect(this.posX + 5, this.posY + 10, this.width - 10, 1, ReadableColor.WHITE, 0.5F);
		glDrawRect(this.posX + 5, this.posY + 10, (this.width - 10) * (float) playheadPosition, 1, ReadableColor.WHITE, 1F);

		/*glDrawRect(this.posX, this.posY, this.width, this.height, ReadableColor.BLACK, 0.5F);

		glDrawRect(this.posX, this.posY, this.width, 1F, ReadableColor.WHITE, 1F);
		glDrawRect(this.posX, (this.posY + this.height) - 1F, this.width, 1F, ReadableColor.WHITE, 1F);

		glDrawRect(this.posX, this.posY, 1F, this.height, ReadableColor.WHITE, 1F);
		glDrawRect((this.posX + this.width) - 1F, this.posY, 1F, this.height, ReadableColor.WHITE, 1F);

		float barHeight = 0;

		if (this.showTitle)
		{
			String title = LiteModMCPlayer.allFiles.get(0).getTitle();
			String artist = LiteModMCPlayer.allFiles.get(0).getArtist();
			minecraft.fontRenderer.drawString(title, this.posX + 5 + 5, this.posY + 5, 0xFFFFFF, true);
			minecraft.fontRenderer.drawString(" by " + artist, this.posX + 5 + 5 + minecraft.fontRenderer.getStringWidth(title), this.posY + 5, 0xBBBBBB, true);
			barHeight = 10;
		}

		float extraBarHeight = ((this.height - barHeight) / 2);
		double throughSong = LiteModMCPlayer.thread.getPosition().toMillis() / LiteModMCPlayer.thread.getLength().toMillis();
		float barLength = this.width - 10;

		this.cubeX = this.posX + 5 + (float) (barLength * throughSong) - this.cubeRadius;
		this.cubeY = this.posY + extraBarHeight + barHeight - (this.cubeRadius / 2);

		boolean isMouseHoveringCube = mouseX >= this.cubeX && mouseY >= this.cubeY && mouseX < this.cubeX + (2 * this.cubeRadius) && mouseY < this.cubeY + (2 * this.cubeRadius);

		this.isPlayheadClicked = isMouseHoveringCube && this.mouseClicked;

		System.out.println(this.isPlayheadClicked);

		if (this.isPlayheadClicked)
			throughSong = (mouseX - this.posX + 5) / barLength;

		this.cubeX = this.posX + 5 + (float) (barLength * throughSong) - this.cubeRadius;
		this.cubeY = this.posY + extraBarHeight + barHeight - (this.cubeRadius / 2);

		glDrawRect(this.posX + 5, this.posY + 1 + extraBarHeight + barHeight, barLength, 1, ReadableColor.WHITE, 0.75F);
		glDrawRect(this.posX + 5, this.posY + 0 + extraBarHeight + barHeight, (float) (barLength * throughSong), 3, ReadableColor.CYAN, 1F);

		if (isMouseHoveringCube)
			this.cubeAlpha += 0.1F;
		else this.cubeAlpha -= 0.1F;

		if (this.cubeAlpha > 1)
			this.cubeAlpha = 1;
		else if (this.cubeAlpha < 0.5)
			this.cubeAlpha = 0.5F;

		glDrawRect(this.cubeX, this.cubeY, this.cubeRadius * 2, this.cubeRadius * 2, ReadableColor.WHITE, this.cubeAlpha);

		//glDrawRect(this.posX + 5 + (float) (barLength * throughSong), this.posY + 15 + extraBarHeight - this.cubeRadius, this.cubeRadius, this.cubeRadius);

		//glDrawRect(this.posX + 5 + (float) (barLength * throughSong), this.posY + 0 + extraBarHeight - this.cubeRadius, this.cubeRadius, this.cubeRadius, ReadableColor.BLACK, .9F);
		//glDrawRect(this.posX + 5 + (float) (barLength * throughSong), this.posY + 0 + extraBarHeight - this.cubeRadius, this.cubeRadius, this.cubeRadius, ReadableColor.WHITE, this.cubeAlpha);*/
	}

	private static void glDrawRect(float x, float y, float width, float height, ReadableColor colour, float alpha)
	{
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(colour.getRed(), colour.getGreen(), colour.getBlue(), alpha);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(x, y + height, 0);
		tessellator.addVertex(x + width, y + height, 0);
		tessellator.addVertex(x + width, y, 0);
		tessellator.addVertex(x, y, 0);
		tessellator.draw();

		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
}
