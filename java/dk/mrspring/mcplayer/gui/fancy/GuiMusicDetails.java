package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.gui.Color;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

/**
 * Created by Konrad on 22-09-2014 for MusicPlayer.
 */
public class GuiMusicDetails
{
    int posX, posY;
    int width, height;
    MusicFile showing;
    boolean editingTitle = true, editingAlbum = false, editingArtist = false;
    GuiFancyTextField titleEditingField, albumEditingField, artistEditingField;

    public GuiMusicDetails(int x, int y, int width, int height)
    {
        this.posX = x;
        this.posY = y;

        this.width = width;
        this.height = height;

        this.titleEditingField = new GuiFancyTextField(this.width - 10 - this.width / 3, "text");
        this.albumEditingField = new GuiFancyTextField(this.width - 10 - this.width / 3, "text");
        this.artistEditingField = new GuiFancyTextField(this.width - 10 - this.width / 3, "text");
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

            DrawingHelper.drawCheckMarkIcon(this.posX + this.width - 70, textBasePosY, 60, 60, Color.WHITE, 1F);

            minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":", this.posX + artWorkSize + 15, this.posY + 20, 0xEEEEEE, true);
            minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_album") + ":", this.posX + artWorkSize + 15, this.posY + 35, 0xEEEEEE, true);
            minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ":", this.posX + artWorkSize + 15, this.posY + 50, 0xEEEEEE, true);

            if (!this.editingTitle)
                minecraft.fontRenderer.drawString(this.showing.getTitle(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":"), this.posY + 20, 0xEEEEEE, true);
            if (!this.editingAlbum)
                minecraft.fontRenderer.drawString(this.showing.getAlbum(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_album") + ":"), this.posY + 35, 0xEEEEEE, true);
            if (!this.editingArtist)
                minecraft.fontRenderer.drawString(this.showing.getArtist(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ":"), this.posY + 50, 0xEEEEEE, true);

            this.titleEditingField.xPos = this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":");
            this.titleEditingField.yPos = this.posY + 20;
            this.titleEditingField.width = this.width / 3;

            this.albumEditingField.xPos = this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":");
            this.albumEditingField.yPos = this.posY + 35;
            this.albumEditingField.width = -50 + this.width / 3;

            this.artistEditingField.xPos = this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":");
            this.artistEditingField.yPos = this.posY + 50;
            this.artistEditingField.width = -50 + this.width / 3;

            if (!this.editingTitle)
                DrawingHelper.drawCheckMarkIcon(this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ": " + this.showing.getTitle()) + 20, this.posY + 19, 10, 10, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
            else this.titleEditingField.draw();

            if (!this.editingAlbum)
                DrawingHelper.drawCheckMarkIcon(this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_album") + ": " + this.showing.getAlbum()) + 20, this.posY + 34, 10, 10, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
            else this.albumEditingField.draw();

            if (!this.editingArtist)
                DrawingHelper.drawCheckMarkIcon(this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ": " + this.showing.getArtist()) + 20, this.posY + 49, 10, 10, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
            else this.artistEditingField.draw();

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
			*/
        }
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