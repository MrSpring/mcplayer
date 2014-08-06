package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.ColorScheme;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.gui.Color;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by MrSpring on 31-07-14 for MC Music Player.
 */
public class GuiMusicList
{
	int posX, posY;
	int width, height;
	int scrollHeight = 0;
	int scrollbarWidth = 5;
	int perFileHeight = 80;
	int selected = -1;
	boolean isFocused = false;
	long timeOnFirstClick = -1;

	public GuiMusicList(int x, int y, int width, int height)
	{
		this.posX = x;
		this.posY = y;

		this.width = width;
		this.height = height;
	}

	public void shuffle()
	{
		for (int position = 1; position < LiteModMCPlayer.allFiles.size(); position++)
		{
			MusicFile file = LiteModMCPlayer.allFiles.remove(position);
			int newPosition = new Random().nextInt(LiteModMCPlayer.allFiles.size() - 1) + 1;
			LiteModMCPlayer.allFiles.add(newPosition, file);
		}

		LiteModMCPlayer.thread.updateQueue();
	}

	public void mouseWheel(int dWheel)
	{
		System.out.println(dWheel);
		int height = (-dWheel / 2) + this.scrollHeight;

		if (height < 0)
			height = 0;
		else if (height > ((LiteModMCPlayer.allFiles.size() - 1) * this.perFileHeight))
			height = ((LiteModMCPlayer.allFiles.size() - 1) * this.perFileHeight);

		this.scrollHeight = height;
		//else if (dWheel <= )
	}

	public void moveUp()
	{
		if (this.selected != -1)
		{
			if (this.selected == LiteModMCPlayer.allFiles.size() - 1)
			{
				MusicFile file = LiteModMCPlayer.allFiles.remove(this.selected);
				LiteModMCPlayer.allFiles.add(0, file);
				this.setSelected(0, true);
				LiteModMCPlayer.thread.updateQueue();
			} else
			{
				MusicFile file = LiteModMCPlayer.allFiles.remove(this.selected);
				LiteModMCPlayer.allFiles.add(this.selected + 1, file);
				this.setSelected(this.selected + 1, false);
				LiteModMCPlayer.thread.updateQueue();
			}
		}
	}

	public void moveDown()
	{
		if (this.selected != -1)
		{
			if (this.selected == 0)
			{
				MusicFile file = LiteModMCPlayer.allFiles.remove(0);
				LiteModMCPlayer.allFiles.add(file);
				this.setSelected(LiteModMCPlayer.allFiles.size() - 1, true);
				LiteModMCPlayer.thread.updateQueue();
			} else
			{
				MusicFile file = LiteModMCPlayer.allFiles.remove(this.selected);
				LiteModMCPlayer.allFiles.add(this.selected - 1, file);
				this.setSelected(this.selected - 1, false);
				LiteModMCPlayer.thread.updateQueue();
			}
		}
	}

	public void draw(Minecraft minecraft, int mouseX, int mouseY)
	{
		ColorScheme scheme = LiteModMCPlayer.config.getColorScheme();

		if (this.width > 200)
		{
			int y = 0;
			for (int i = 0; i < LiteModMCPlayer.allFiles.size(); i++)
			{
				// TODO Only Render if inside view, for better performance

				MusicFile file = LiteModMCPlayer.allFiles.get(i);
				float alpha = scheme.getBaseAlpha();
				if (i == this.selected)
					alpha += .25F;
				DrawingHelper.drawRect(this.posX + 5F, this.posY + 5 + y - this.scrollHeight, this.width - 15 - this.scrollbarWidth, this.perFileHeight - 5, scheme.getBaseColor(), alpha);

				file.bindCover(minecraft);
				glDrawTexturedRect(this.posX + 10, this.posY + 10 + y - this.scrollHeight, this.perFileHeight - 15, this.perFileHeight - 15, 0, 0, 512, 512, 1F);

				FontRenderer renderer = minecraft.fontRenderer;

				String title = file.getTitle();
				String album = file.getAlbum();
				String artist = file.getArtist();

				int maxLength = this.width - this.perFileHeight - 15;
				int textHeightExtra = 15;

				renderer.drawSplitString(title, this.perFileHeight + 1, textHeightExtra + this.posY + y - this.scrollHeight + 1, maxLength, 0x333333);
				renderer.drawSplitString(title, this.perFileHeight, textHeightExtra + this.posY + y - this.scrollHeight, maxLength, 0xFFFFFF);

				if (renderer.getStringWidth(title) > maxLength)
					textHeightExtra += 10;

				textHeightExtra += 15;

				renderer.drawSplitString(album, this.perFileHeight + 1, textHeightExtra + this.posY + y - this.scrollHeight + 1, maxLength, 0x333333);
				renderer.drawSplitString(album, this.perFileHeight, textHeightExtra + this.posY + y - this.scrollHeight, maxLength, 0xBBBBBB);

				if (renderer.getStringWidth(album) > maxLength)
					textHeightExtra += 10;

				textHeightExtra += 15;

				renderer.drawSplitString(artist, this.perFileHeight + 1, textHeightExtra + this.posY + y - this.scrollHeight + 1, maxLength, 0x333333);
				renderer.drawSplitString(artist, this.perFileHeight, textHeightExtra + this.posY + y - this.scrollHeight, maxLength, 0xBBBBBB);

				/*renderer.drawString(file.getTitle(), this.perFileHeight, 15 + this.posY + y - this.scrollHeight, 0xFFFFFF, true);
				renderer.drawString(file.getAlbum(), this.perFileHeight, 25 + this.posY + y - this.scrollHeight, 0xBBBBBB, true);
				renderer.drawString(file.getArtist(),this.perFileHeight, 35 + this.posY + y - this.scrollHeight, 0xBBBBBB, true);
*/

				if (i == this.selected)
				{
					DrawingHelper.drawRect(this.posX + 5, this.posY + 5 + y - this.scrollHeight, this.width - 15 - this.scrollbarWidth, 1, scheme.getOutlineColor(), 1F);
					DrawingHelper.drawRect(this.posX + 5, this.posY + y - this.scrollHeight + this.perFileHeight - 1, this.width - 15 - this.scrollbarWidth, 1, scheme.getOutlineColor(), 1F);

					DrawingHelper.drawRect(this.posX + 5, this.posY + 5 + y - this.scrollHeight, 1, this.perFileHeight - 5, scheme.getOutlineColor(), 1F);
					DrawingHelper.drawRect(this.posX + 5 + this.width - 15 - this.scrollbarWidth - 1, this.posY + 5 + y - this.scrollHeight, 1, this.perFileHeight - 5, scheme.getOutlineColor(), 1F);
				}
				y += this.perFileHeight;
			}

			//if (this.canScrollbarScroll(1))
			//	this.scrollHeight++;

			this.drawScrollbar(scheme);
		}
		if (this.width != 0)
			DrawingHelper.drawRect(this.posX + this.width, this.posY + 5, 1, this.height - 10, Color.DKGREY, 0.75F);
	}

	public void setSelected(int selected, boolean focus)
	{
		if (this.selected != selected)
			this.isFocused = false;

		if (selected < LiteModMCPlayer.allFiles.size() && selected >= -1)
			this.selected = selected;

		if (focus && selected >= 0)
			this.scrollHeight = this.selected * this.perFileHeight;
	}

	public int getSelected()
	{
		return selected;
	}

	public MusicFile getFocused()
	{
		if (this.selected != -1 && this.isFocused)
			return LiteModMCPlayer.allFiles.get(this.selected);
		else return null;
	}

	public boolean mouseClicked(int mouseX, int mouseY)
	{
		if ((mouseY > 50) && (mouseY < this.height + 50) && (mouseX > this.posX) && (mouseX < this.posX + this.width))
		{
			long systemTime = Minecraft.getSystemTime();
			int listMouseY = (mouseY - 50) + this.scrollHeight;
			this.setSelected(listMouseY / this.perFileHeight, false);
			if (this.selected > LiteModMCPlayer.allFiles.size() - 1)
				this.setSelected(-1, false);
			System.out.println(" Time difference: " + (systemTime - this.timeOnFirstClick));
			this.isFocused = systemTime - this.timeOnFirstClick < 500 && this.selected != -1;

			this.timeOnFirstClick = systemTime;
			return true;
		} else return false;
	}

	private void drawScrollbar(ColorScheme scheme)
	{
		DrawingHelper.drawRect(this.width - 10, this.posY + this.getScrollbarY(), 5, this.getScrollbarHeight(), scheme.getOutlineColor(), 1F);
	}

	private float getScrollbarY()
	{
		float range = this.height - this.getScrollbarHeight() - 10;
		float fileHeights = (LiteModMCPlayer.allFiles.size() - 1) * this.perFileHeight;
		float decimal = ((float) this.scrollHeight) / fileHeights;
		return (decimal * range) + 5;
	}

	private int getScrollbarHeight()
	{
		return ((this.height - 10) / 100) * 30;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	private static void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2, float alpha)
	{
		glDisable(GL_LIGHTING);
		glEnable(GL_BLEND);
		glAlphaFunc(GL_GREATER, 0.01F);
		glEnable(GL_TEXTURE_2D);
		glColor4f(1.0F, 1.0F, 1.0F, alpha);

		float texMapScale = 0.001953125F;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0,     y + height, 0, u  * texMapScale, v2 * texMapScale);
		tessellator.addVertexWithUV(x + width, y + height, 0, u2 * texMapScale, v2 * texMapScale);
		tessellator.addVertexWithUV(x + width, y + 0,      0, u2 * texMapScale, v  * texMapScale);
		tessellator.addVertexWithUV(x + 0,     y + 0,      0, u  * texMapScale, v  * texMapScale);
		tessellator.draw();

		glDisable(GL_BLEND);
	}
}
