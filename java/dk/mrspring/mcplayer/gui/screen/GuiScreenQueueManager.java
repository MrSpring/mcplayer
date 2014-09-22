package dk.mrspring.mcplayer.gui.screen;

import dk.mrspring.mcplayer.ColorScheme;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import dk.mrspring.mcplayer.gui.fancy.GuiFancyButton;
import dk.mrspring.mcplayer.gui.fancy.GuiMusicList;
import dk.mrspring.mcplayer.gui.fancy.GuiMusicScrubber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.*;
import org.lwjgl.input.Cursor;
import org.lwjgl.util.ReadableColor;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

/**
 * Created by MrSpring on 27-07-14 for MC Music Player.
 */
public class GuiScreenQueueManager extends GuiScreen
{
	private final GuiScreen onExit;

	GuiFancyButton doneButton;
	GuiFancyButton moveUpButton;
	GuiFancyButton moveDownButton;
	GuiFancyButton shuffleButton;
    GuiFancyButton copyTitle;
	GuiFancyButton reloadNames;

	GuiMusicList list;
	MusicDetails details;
	GuiMusicScrubber scrubber;

	int detailWidth = 0;

	public GuiScreenQueueManager(GuiScreen previousScreen)
	{
		this.onExit = previousScreen;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.doneButton = new GuiFancyButton(5, this.height - 50 + 6, 50, 39, "gui.done");
		this.moveUpButton = new GuiFancyButton(70, this.height - 50 + 6, 60, 39, "gui.mcplayer.move_up").setDisabled();
		this.moveDownButton = new GuiFancyButton(135, this.height - 50 + 6, 60, 39, "gui.mcplayer.move_down").setDisabled();
		this.shuffleButton = new GuiFancyButton(210, this.height - 50 + 6, 80, 39, "gui.mcplayer.shuffle_music");
        this.copyTitle = new GuiFancyButton(300, this.height - 50 + 6, 80, 39, "Copy Song Title");
		this.reloadNames = new GuiFancyButton(370, this.height - 50 + 6, 80, 36, "Reload Titles");

		this.list = new GuiMusicList(0, 50, this.width, this.height - 100);
		this.details = new MusicDetails(this.width - detailWidth, 50, this.detailWidth, this.height - 100);
		this.scrubber = new GuiMusicScrubber(5, 5, this.width - 10, 39, true).enableControls();

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3)
	{
		ColorScheme scheme = LiteModMCPlayer.config.getColorScheme();

		super.drawScreen(mouseX, mouseY, par3);

		DrawingHelper.drawRect(0F, 0F, (float) this.width, this.height, scheme.getBaseColor(), scheme.getBaseAlpha());



		if (this.list.getFocused() != null)
			this.detailWidth += 10;
		else
			this.detailWidth -= 10;

		if (this.detailWidth < 0)
			this.detailWidth = 0;
		else if (this.width > 490)
		{
			if (this.detailWidth > this.width / 2)
				this.detailWidth = this.width / 2;
		} else if (this.detailWidth > this.width)
			this.detailWidth = this.width;
		/*if (this.detailWidth > this.width / 2)
			this.detailWidth = this.width / 2;*/

		this.list.setWidth(this.width - this.detailWidth);
		this.details.setWidth(this.detailWidth);
		this.details.setPosX(this.width - this.detailWidth);
        this.details.setShowing(LiteModMCPlayer.data.get(this.list.getFocused()));
		this.list.draw(this.mc, mouseX, mouseY);
        if (this.detailWidth > 0)
            this.details.draw(this.mc);
		//this.details.draw(this.mc);



		DrawingHelper.drawRect(0F, 0F, (float) this.width, 50F, scheme.getBaseColor(), scheme.getBaseAlpha());
		DrawingHelper.drawRect(0F, (float) this.height - 50, (float) this.width, 50F, scheme.getBaseColor(), scheme.getBaseAlpha());

		DrawingHelper.drawRect(0F, 49F, (float) this.width, 1F, scheme.getOutlineColor(), 1F);
		DrawingHelper.drawRect(0F, (float) this.height - 50, (float) this.width, 1F, scheme.getOutlineColor(), 1F);

		if (this.list.getSelected() != -1)
		{
			this.moveUpButton.setEnabled();
			this.moveDownButton.setEnabled();
		} else
		{
			this.moveUpButton.setDisabled();
			this.moveDownButton.setDisabled();
		}

		this.doneButton.drawButton(this.mc, mouseX, mouseY);
		this.moveUpButton.drawButton(this.mc, mouseX, mouseY);
		this.moveDownButton.drawButton(this.mc, mouseX, mouseY);
		this.shuffleButton.drawButton(this.mc, mouseX, mouseY);
		this.scrubber.draw(this.mc, mouseX, mouseY);
        this.copyTitle.drawButton(this.mc, mouseX, mouseY);
		this.reloadNames.drawButton(this.mc, mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);

		if (par1 < this.width - this.detailWidth)
			this.list.mouseClicked(par1, par2);
		else
			this.list.setSelected(-1, false);

		if (this.doneButton.mousePressed(par1, par2, par3))
			this.mc.displayGuiScreen(this.onExit);
		else if (this.moveUpButton.mousePressed(par1, par2, par3))
			this.list.moveDown();
		else if (this.moveDownButton.mousePressed(par1, par2, par3))
			this.list.moveUp();
		else if (this.shuffleButton.mousePressed(par1, par2, par3))
			this.list.shuffle();
        else if (this.copyTitle.mousePressed(par1, par2, par3))
            this.copySontTitle();
		else if (this.reloadNames.mousePressed(par1, par2, par3))
		{
			try
			{
				LiteModMCPlayer.data.saveChangedNames();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		this.scrubber.mousePressed(par1, par2);
	}

    protected void copySontTitle()
    {
		MusicFile file = LiteModMCPlayer.data.get(this.list.getSelectedFile());

		if (file != null)
		{
			file.setCustomTitle("Another song");
			LiteModMCPlayer.data.updateSong(file);
		}

        /*MusicFile file = this.list.getSelectedFile();

        if (file != null)
        {
            String toCopy = file.getTitle();

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(toCopy);
            clipboard.setContents(selection, selection);
        }*/
    }

	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par2 == Keyboard.KEY_UP)
			this.list.setSelected(this.list.getSelected() - 1, true);
		else if (par2 == Keyboard.KEY_DOWN)
			this.list.setSelected(this.list.getSelected() + 1, true);
		else if (par2 == Keyboard.KEY_SPACE)
			LiteModMCPlayer.thread.togglePausePlay();
		else if (par2 == LiteModMCPlayer.playNext.getKeyCode())
			LiteModMCPlayer.thread.scheduleNext();
		else if (par2 == LiteModMCPlayer.playPrev.getKeyCode())
			LiteModMCPlayer.thread.schedulePrev();
        else if (par2 == Keyboard.KEY_C && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            this.copySontTitle();
		else super.keyTyped(par1, par2);
	}

	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();
		int wheel = Mouse.getDWheel();
		if (wheel != 0)
			this.list.mouseWheel(wheel);
	}

	class MusicDetails
	{
		int posX, posY;
		int width, height;
		MusicFile showing;
        GuiFancyButton copyTitle;

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
				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.details"), this.posX + (this.width / 2) - (minecraft.fontRenderer.getStringWidth("Details") / 2), this.posY + 5, 0xFFFFFF, true);

			if (this.width != 0 && this.showing != null)
			{
				this.showing.bindCover(minecraft);

				int artWorkSize = this.width / 3;

				if (artWorkSize > this.height - 20)
					artWorkSize = this.height - 20;

				DrawingHelper.drawTexturedRect(this.posX + 10, this.posY + 10, artWorkSize, artWorkSize, 0, 0, 512, 512, 1F);

				int textBasePosX, textBasePosY;
				if (this.height - (artWorkSize) <= 45)
				{
					textBasePosX = this.posX + artWorkSize + 15;
					textBasePosY = this.posY + 5 + 60;
				} else
				{
					textBasePosX = this.posX + 10;
					textBasePosY = this.posY + artWorkSize + 15;
				}

				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":", this.posX + artWorkSize + 15, this.posY + 20, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_album") + ":", this.posX + artWorkSize + 15, this.posY + 35, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ":", this.posX + artWorkSize + 15, this.posY + 50, 0xEEEEEE, true);

				minecraft.fontRenderer.drawString(this.showing.getTitle(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":"), this.posY + 20, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getAlbum(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_album") + ":"), this.posY + 35, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getArtist(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ":"), this.posY + 50, 0xEEEEEE, true);


				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_genre") + ":", textBasePosX, textBasePosY, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getGenre(), textBasePosX + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_genre")) + 5, textBasePosY, 0xFFFFFF, true);

				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_year") + ":", textBasePosX, textBasePosY + 10, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getYear(), textBasePosX + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_year")) + 5, textBasePosY + 10, 0xFFFFFF, true);

				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_composer") + ":", textBasePosX, textBasePosY + 20, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getComposer(), textBasePosX + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_composer")) + 5, textBasePosY + 20, 0xFFFFFF, true);

				/*minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_genre") + ":", this.posX + 10, this.posY + (this.width / 3) + 15, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getGenre(), this.posX + 15 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_genre") + ":"), this.posY + (this.width / 3) + 15, 0xEEEEEE, true);

				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_year") + ":", this.posX + 10, this.posY + (this.width / 3) + 25, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getYear(), this.posX + 15 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_year") + ":"), this.posY + (this.width / 3) + 25, 0xEEEEEE, true);

				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_composer") + ":", this.posX + 10, this.posY + (this.width / 3) + 35, 0xEEEEEE, true);
				minecraft.fontRenderer.drawString(this.showing.getComposer(), this.posX + 15 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_composer") + ":"), this.posY + (this.width / 3) + 35, 0xEEEEEE, true);
			*/}
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

        public MusicFile getShowing()
        {
            return showing;
        }
    }
}
