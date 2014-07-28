package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.list.Playlist;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

/**
 * Created by MrSpring on 27-07-14 for MC Music Player.
 */
public class GuiScreenMusicManager extends GuiScreen
{
	private final GuiScreen onExit;

	GuiFancyButton button;
	MusicList list;
	MusicDetails details;
	GuiMusicScrubber scrubber;
	int detailWidth = 0;

	public GuiScreenMusicManager(GuiScreen previousScreen)
	{
		this.onExit = previousScreen;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.button = new GuiFancyButton(5, 5, 50, 39, "gui.done");
		this.list = new MusicList(0, 50, this.width, this.height - 100);
		this.details = new MusicDetails(this.width - detailWidth, 50, this.detailWidth, this.height - 100);
		this.scrubber = new GuiMusicScrubber(5, 5, this.width - 10, 30, true);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3)
	{
		super.drawScreen(mouseX, mouseY, par3);
		glDrawRect(0F, 0F, (float) this.width, this.height, ReadableColor.BLACK, 0.5F);

		if (this.detailWidth > 0)
			this.details.draw(this.mc);

		if (this.list.getFocused() != null)
			this.detailWidth += 10;
		else
			this.detailWidth -= 10;

		if (this.detailWidth < 0)
			this.detailWidth = 0;
		else if (this.detailWidth > this.width / 2)
			this.detailWidth = this.width / 2;

		this.list.setWidth(this.width - this.detailWidth);
		this.details.setWidth(this.detailWidth);
		this.details.setPosX(this.width - this.detailWidth);
		this.list.draw(this.mc, mouseX, mouseY);
		this.details.draw(this.mc);


		glDrawRect(0F, 0F, (float) this.width, 50F, ReadableColor.BLACK, 0.5F);
		glDrawRect(0F, (float) this.height - 50, (float) this.width, 50F, ReadableColor.BLACK, 0.5F);

		glDrawRect(0F, 49F, (float) this.width, 1F, ReadableColor.WHITE, 1F);
		glDrawRect(0F, (float) this.height - 50, (float) this.width, 1F, ReadableColor.WHITE, 1F);

		//this.button.drawButton(this.mc, mouseX, mouseY);
		this.scrubber.draw(this.mc, mouseX, mouseY);
	}

	@Override
	protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_)
	{
		super.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
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

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		//System.out.println(" Mouse was clicked at: X" + par1 + ", Y" + par2 + ", the button was: " + par3);
		if (par1 < this.width - this.detailWidth)
			this.list.mouseClicked(par1, par2);
		else
			this.list.setSelected(-1);
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		super.keyTyped(par1, par2);
		//System.out.println(" Key was pressed: " + par1 + ", ID: " + par2);
	}

	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();
		int wheel = Mouse.getDWheel();
		if (wheel != 0)
			this.list.mouseWheel(wheel);
	}

	class MusicList
	{
		int posX, posY;
		int width, height;
		int scrollHeight = 0;
		Playlist<MusicFile> list;
		int scrollbarWidth = 5;
		int perFileHeight = 80;
		int selected = -1;

		public MusicList(int x, int y, int width, int height)
		{
			this.posX = x;
			this.posY = y;

			this.width = width;
			this.height = height;

			this.list = LiteModMCPlayer.allFiles;
		}

		public void mouseWheel(int dWheel)
		{
			System.out.println(dWheel);
			int height = (-dWheel / 10) + this.scrollHeight;

			if (height < 0)
				height = 0;
			else if (height > ((this.list.size() - 1) * this.perFileHeight))
				height = ((this.list.size() - 1) * this.perFileHeight);

			this.scrollHeight = height;
			//else if (dWheel <= )
		}

		public void draw(Minecraft minecraft, int mouseX, int mouseY)
		{
			int y = 0;
			for (int i = 0; i < this.list.size(); i++)
			{
				MusicFile file = this.list.get(i);
				float alpha = 0.5F;
				if (i == this.selected)
					alpha = 0.75F;
				glDrawRect(this.posX + 5F, this.posY + 5 + y - this.scrollHeight, this.width - 15 - this.scrollbarWidth, this.perFileHeight - 5, ReadableColor.BLACK, alpha);

				file.bindCover(minecraft);
				glDrawTexturedRect(this.posX + 10, this.posY + 10 + y - this.scrollHeight, this.perFileHeight - 15, this.perFileHeight - 15, 0, 0, 512, 512, 1F);

				FontRenderer renderer = minecraft.fontRenderer;

				renderer.drawString(file.getTitle(), this.perFileHeight + 5, 15 + this.posY + y - this.scrollHeight, 0xFFFFFF, true);
				renderer.drawString(file.getAlbum(), this.perFileHeight + 5, 25 + this.posY + y - this.scrollHeight, 0xBBBBBB, true);
				renderer.drawString(file.getArtist(),this.perFileHeight + 5, 35 + this.posY + y - this.scrollHeight, 0xBBBBBB, true);

				glDrawRect(this.posX + this.width, this.posY + 5, 1, this.height - 10, ReadableColor.DKGREY, 0.75F);

				if (i == this.selected)
				{
					glDrawRect(this.posX + 5, this.posY + 5 + y - this.scrollHeight, this.width - 15 - this.scrollbarWidth, 1, ReadableColor.WHITE, 1F);
					glDrawRect(this.posX + 5, this.posY + y - this.scrollHeight + this.perFileHeight - 1, this.width - 15 - this.scrollbarWidth, 1, ReadableColor.WHITE, 1F);

					glDrawRect(this.posX + 5, this.posY + 5 + y - this.scrollHeight, 1, this.perFileHeight - 5, ReadableColor.WHITE, 1F);
					glDrawRect(this.posX + 5 + this.width - 15 - this.scrollbarWidth - 1, this.posY + 5 + y - this.scrollHeight, 1, this.perFileHeight - 5, ReadableColor.WHITE, 1F);
				}
				y += this.perFileHeight;
			}

			//if (this.canScrollbarScroll(1))
			//	this.scrollHeight++;

			this.drawScrollbar();
		}

		public void setSelected(int selected)
		{
			this.selected = selected;
		}

		public MusicFile getFocused()
		{
			if (this.selected != -1)
				return this.list.get(this.selected);
			else return null;
		}

		public boolean mouseClicked(int mouseX, int mouseY)
		{
			if ((mouseY > 50) && (mouseY < this.height + 50) && (mouseX > this.posX) && (mouseX < this.posX + this.width))
			{
				int listMouseY = (mouseY - 50) + this.scrollHeight;
				this.selected = listMouseY / this.perFileHeight;
				return true;
			} else return false;
		}

		private void drawScrollbar()
		{
			glDrawRect(this.width - 10, this.posY + this.getScrollbarY(), 5, this.getScrollbarHeight(), ReadableColor.WHITE, 1F);
		}

		private float getScrollbarY()
		{
			float range = this.height - this.getScrollbarHeight() - 10;
			float fileHeights = (this.list.size() - 1) * this.perFileHeight;
			float decimal = ((float) this.scrollHeight) / fileHeights;
			return (decimal * range) + 5;
		}

		private int getScrollbarHeight()
		{
			return ((this.height - 10) / 100) * 30;
		}

		private boolean canScrollbarScroll(int height)
		{
			if (height > 0)
				return (height + this.scrollHeight) < ((this.list.size() - 1) * this.perFileHeight);
			else
				return (height + this.scrollHeight) > 0;
		}

		public void setWidth(int width)
		{
			this.width = width;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}
	}

	class MusicDetails
	{
		int posX, posY;
		int width, height;
		MusicFile showing;

		public MusicDetails(int x, int y, int width, int height)
		{
			this.posX = x;
			this.posY = y;

			this.width = width;
			this.height = height;
		}

		public void draw(Minecraft minecraft)
		{
			if (this.width > 80)
				minecraft.fontRenderer.drawString("Details", this.posX + (this.width / 2) - (minecraft.fontRenderer.getStringWidth("Details") / 2), this.posY + 5, 0xFFFFFF, true);
		}

		public void setWidth(int width)
		{
			this.width = width;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}

		public void setPosX(int posX)
		{
			this.posX = posX;
		}

		public void setShowing(MusicFile showing)
		{
			this.showing = showing;
		}
	}
}
