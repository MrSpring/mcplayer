package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
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

	public GuiScreenMusicManager(GuiScreen previousScreen)
	{
		this.onExit = previousScreen;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.button = new GuiFancyButton(5, 5, 50, 39, "gui.done");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3)
	{
		super.drawScreen(mouseX, mouseY, par3);
		glDrawRect(0F, 0F, (float) this.width, this.height, ReadableColor.BLACK, 0.5F);

		glDrawRect(0F, 0F, (float) this.width, 50F, ReadableColor.BLACK, 0.25F);
		glDrawRect(0F, (float) this.height - 50, (float) this.width, 50F, ReadableColor.BLACK, 0.25F);

		glDrawRect(0F, 49F, (float) this.width, 1F, ReadableColor.WHITE, 1F);
		glDrawRect(0F, (float) this.height - 50, (float) this.width, 1F, ReadableColor.WHITE, 1F);

		this.button.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
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

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
	}

	/*private MusicList musicList;

	@Override
	public void initGui()
	{
		super.initGui();
		this.musicList = new MusicList();
		this.func_146375_g();
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		//this.drawDefaultBackground();
		this.musicList.drawScreen(par1, par2, par3);
		super.drawScreen(par1, par2, par3);
	}

	public void func_146375_g()
	{
		boolean bool = this.isOptionClicked();
		// TODO Enable buttons
	}

	private boolean isOptionClicked()
	{
		return this.musicList.selectedSlot != -1 && !(this.musicList.selectedSlot > LiteModMCPlayer.allFiles.size());
	}


	public class MusicList extends GuiSlot
	{
		public int selectedSlot = -1;

		public MusicList()
		{
			super(GuiScreenMusicManager.this.mc, GuiScreenMusicManager.this.width, GuiScreenMusicManager.this.height, 20, GuiScreenMusicManager.this.height - 30, 50);
		}

		@Override
		protected int getSize()
		{
			return LiteModMCPlayer.allFiles.size();
		}

		@Override
		protected void elementClicked(int var1, boolean var2, int var3, int var4)
		{
			this.selectedSlot = var1;
			GuiScreenMusicManager.this.func_146375_g();
		}

		@Override
		protected boolean isSelected(int var1)
		{
			return var1 == this.selectedSlot;
		}

		@Override
		protected void drawBackground()
		{

		}

		@Override
		protected void drawSlot(int var1, int x, int y, int var4, Tessellator var5, int var6, int var7)
		{
			String title = LiteModMCPlayer.allFiles.get(var1).getTitle();
			String album = LiteModMCPlayer.allFiles.get(var1).getAlbum();
			String artist = LiteModMCPlayer.allFiles.get(var1).getArtist();

			LiteModMCPlayer.allFiles.get(var1).bindCover(Minecraft.getMinecraft());
			drawTexturedModalRect(5 + x, 5 + y, 40, 40, 40, 40);

			FontRenderer renderer = GuiScreenMusicManager.this.fontRendererObj;

			renderer.drawString(title, x, y, 0xFFFFFF, true);
			renderer.drawString(album, x, y + 10, 0xFFFFFF, true);
			renderer.drawString(artist, x, y + 20, 0xFFFFFF, true);
		}

		public void drawScreen(int par1, int par2, float par3)
		{
			super.func_148128_a(par1, par2, par3);
		}
	}*/
}
