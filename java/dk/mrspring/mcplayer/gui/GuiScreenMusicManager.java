package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

/**
 * Created by MrSpring on 27-07-14 for MC Music Player.
 */
public class GuiScreenMusicManager extends GuiScreen
{
	private MusicList musicList;

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
	}
}
