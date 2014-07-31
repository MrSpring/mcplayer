package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.file.MusicFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.util.ReadableColor;

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

	public GuiMusicList(int x, int y, int width, int height)
	{
		this.posX = x;
		this.posY = y;

		this.width = width;
		this.height = height;

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
		int y = 0;
		for (int i = 0; i < LiteModMCPlayer.allFiles.size(); i++)
		{
			MusicFile file = LiteModMCPlayer.allFiles.get(i);
			float alpha = 0.5F;
			if (i == this.selected)
				alpha = 0.75F;
			DrawingHelper.drawRect(this.posX + 5F, this.posY + 5 + y - this.scrollHeight, this.width - 15 - this.scrollbarWidth, this.perFileHeight - 5, ReadableColor.BLACK, alpha);

			file.bindCover(minecraft);
			glDrawTexturedRect(this.posX + 10, this.posY + 10 + y - this.scrollHeight, this.perFileHeight - 15, this.perFileHeight - 15, 0, 0, 512, 512, 1F);

			FontRenderer renderer = minecraft.fontRenderer;

			renderer.drawString(file.getTitle(), this.perFileHeight, 15 + this.posY + y - this.scrollHeight, 0xFFFFFF, true);
			renderer.drawString(file.getAlbum(), this.perFileHeight, 25 + this.posY + y - this.scrollHeight, 0xBBBBBB, true);
			renderer.drawString(file.getArtist(),this.perFileHeight, 35 + this.posY + y - this.scrollHeight, 0xBBBBBB, true);

			DrawingHelper.drawRect(this.posX + this.width, this.posY + 5, 1, this.height - 10, ReadableColor.DKGREY, 0.75F);

			if (i == this.selected)
			{
				DrawingHelper.drawRect(this.posX + 5, this.posY + 5 + y - this.scrollHeight, this.width - 15 - this.scrollbarWidth, 1, ReadableColor.WHITE, 1F);
				DrawingHelper.drawRect(this.posX + 5, this.posY + y - this.scrollHeight + this.perFileHeight - 1, this.width - 15 - this.scrollbarWidth, 1, ReadableColor.WHITE, 1F);

				DrawingHelper.drawRect(this.posX + 5, this.posY + 5 + y - this.scrollHeight, 1, this.perFileHeight - 5, ReadableColor.WHITE, 1F);
				DrawingHelper.drawRect(this.posX + 5 + this.width - 15 - this.scrollbarWidth - 1, this.posY + 5 + y - this.scrollHeight, 1, this.perFileHeight - 5, ReadableColor.WHITE, 1F);
			}
			y += this.perFileHeight;
		}

		//if (this.canScrollbarScroll(1))
		//	this.scrollHeight++;

		this.drawScrollbar();
	}

	public void setSelected(int selected, boolean focus)
	{
		if (selected < LiteModMCPlayer.allFiles.size() && selected >= -1)
			this.selected = selected;

		if (focus && selected >= 0)
			this.scrollHeight = this.selected * this.perFileHeight;
	}

	public MusicFile getFocused()
	{
		if (this.selected != -1)
			return LiteModMCPlayer.allFiles.get(this.selected);
		else return null;
	}

	public boolean mouseClicked(int mouseX, int mouseY)
	{
		if ((mouseY > 50) && (mouseY < this.height + 50) && (mouseX > this.posX) && (mouseX < this.posX + this.width))
		{
			int listMouseY = (mouseY - 50) + this.scrollHeight;
			this.setSelected(listMouseY / this.perFileHeight, false);
			if (this.selected > LiteModMCPlayer.allFiles.size() - 1)
				this.setSelected(-1, false);
			return true;
		} else return false;
	}

	private void drawScrollbar()
	{
		DrawingHelper.drawRect(this.width - 10, this.posY + this.getScrollbarY(), 5, this.getScrollbarHeight(), ReadableColor.WHITE, 1F);
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
