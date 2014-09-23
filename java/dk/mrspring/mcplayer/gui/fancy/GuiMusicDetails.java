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
	boolean editingTitle = false, editingAlbum = false, editingArtist = false;
	GuiFancyTextField titleEditingField, albumEditingField, artistEditingField;
	GuiFancyButton titleEditButton, albumEditButton, artistEditButton;

	public GuiMusicDetails(int x, int y, int width, int height)
	{
		this.posX = x;
		this.posY = y;

		this.width = width;
		this.height = height;

		this.titleEditingField = new GuiFancyTextField(Minecraft.getMinecraft().fontRenderer, this.posX, this.posY, this.width / 3, 11);
		this.albumEditingField = new GuiFancyTextField(Minecraft.getMinecraft().fontRenderer, this.posX, this.posY, this.width / 3, 11);
		this.artistEditingField = new GuiFancyTextField(Minecraft.getMinecraft().fontRenderer, this.posX, this.posY, this.width / 3, 11);

		this.titleEditButton = new GuiFancyButton(posX, posY, 10, 10, "text");
		this.albumEditButton = new GuiFancyButton(posX, posY, 10, 10, "text");
		this.artistEditButton = new GuiFancyButton(posX, posY, 10, 10, "text");
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

			//DrawingHelper.drawCheckMarkIcon(this.posX + this.width - 70, textBasePosY, 60, 60, Color.WHITE, 1F);

			minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":", this.posX + artWorkSize + 15, this.posY + 20, 0xEEEEEE, true);
			minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_album") + ":", this.posX + artWorkSize + 15, this.posY + 35, 0xEEEEEE, true);
			minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ":", this.posX + artWorkSize + 15, this.posY + 50, 0xEEEEEE, true);

			this.titleEditingField.xPos = this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":");
			this.titleEditingField.yPos = this.posY + 18;
			this.titleEditingField.width = this.width / 3;

			this.albumEditingField.xPos = this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_album") + ":");
			this.albumEditingField.yPos = this.posY + 34;
			this.albumEditingField.width = -50 + this.width / 3;

			this.artistEditingField.xPos = this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ":");
			this.artistEditingField.yPos = this.posY + 49;
			this.artistEditingField.width = -50 + this.width / 3;

			if (!this.editingArtist)
			{
				DrawingHelper.drawEditIcon(this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ": " + this.showing.getTitle()) + 20, this.posY + 19, 10, 10, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
				this.artistEditButton.posX = this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ": " + this.showing.getTitle()) + 20;
				this.artistEditButton.posY = this.posY + 29;
				minecraft.fontRenderer.drawString(this.showing.getArtist(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_artist") + ":"), this.posY + 50, 0xEEEEEE, true);
			} else this.artistEditingField.drawTextField();

			if (!this.editingAlbum)
			{
				DrawingHelper.drawEditIcon(this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_album") + ": " + this.showing.getAlbum()) + 20, this.posY + 34, 10, 10, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
				this.albumEditButton.posX = this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_album") + ": " + this.showing.getAlbum()) + 20;
				this.albumEditButton.posY = this.posY + 34;
				minecraft.fontRenderer.drawString(this.showing.getAlbum(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_album") + ":"), this.posY + 35, 0xEEEEEE, true);
			} else this.albumEditingField.drawTextField();

			if (!this.editingTitle)
			{
				DrawingHelper.drawEditIcon(this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ": " + this.showing.getArtist()) + 20, this.posY + 49, 10, 10, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
				this.titleEditButton.posX = this.posX + artWorkSize + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ": " + this.showing.getArtist()) + 20;
				this.titleEditButton.posY = this.posY + 49;
				minecraft.fontRenderer.drawString(this.showing.getTitle(), this.posX + artWorkSize + 20 + minecraft.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.mcplayer.music_title") + ":"), this.posY + 20, 0xEEEEEE, true);
			} else this.titleEditingField.drawTextField();

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

	public void onUpdate()
	{
		this.titleEditingField.updateCursorCounter();
		this.albumEditingField.updateCursorCounter();
		this.artistEditingField.updateCursorCounter();
	}

	public void handlerKeyboardInput(char character, int keyCode)
	{
		if (this.editingTitle && this.titleEditingField.isFocused())
			this.titleEditingField.textboxKeyTyped(character, keyCode);
		else if (this.editingAlbum && this.albumEditingField.isFocused())
			this.albumEditingField.textboxKeyTyped(character, keyCode);
		else if (this.editingArtist && this.artistEditingField.isFocused())
			this.artistEditingField.textboxKeyTyped(character, keyCode);
	}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		GuiFancyTextField.ClickedType type = this.titleEditingField.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.editingTitle)
		{
			if (type == GuiFancyTextField.ClickedType.FINISH_BUTTON)
			{
				this.showing.setCustomTitle(this.titleEditingField.getText());
				this.editingTitle = false;
				return true;
			}
		} else if (this.titleEditButton.mousePressed(mouseX, mouseY, mouseButton))
		{
			this.titleEditingField.setText(this.showing.getTitle());
			this.editingTitle = true;
			return true;
		}

		type = this.albumEditingField.mouseClicked(mouseX, mouseY, mouseButton);
		if (this.editingAlbum)
		{
			if (type == GuiFancyTextField.ClickedType.FINISH_BUTTON)
			{
				this.showing.setCustomAlbum(this.albumEditingField.getText());
				this.editingAlbum = false;
				return true;
			}
		} else if (this.albumEditButton.mousePressed(mouseX, mouseY, mouseButton))
		{
			this.albumEditingField.setText(this.showing.getAlbum());
			this.editingAlbum = true;
			return true;
		}

		type = this.artistEditingField.mouseClicked(mouseX, mouseY, mouseButton);
		if (this.editingArtist)
		{
			if (type == GuiFancyTextField.ClickedType.FINISH_BUTTON)
			{
				this.showing.setCustomArtist(this.artistEditingField.getText());
				this.editingArtist = false;
				return true;
			}
		} else if (this.artistEditButton.mousePressed(mouseX, mouseY, mouseButton))
		{
			this.artistEditingField.setText(this.showing.getArtist());
			this.editingArtist = true;
			return true;
		}

		return false;

		/*if (this.editingTitle)
		{
			if (this.titleEditingField.mouseClicked(mouseX, mouseY, mouseButton) == GuiFancyTextField.ClickedType.TEXT_FIELD)
			{
				this.showing.setCustomTitle(this.titleEditingField.getText());
				this.editingTitle = false;
				return true;
			}
		} else if (this.titleEditButton.mousePressed(mouseX, mouseY, mouseButton)
		{
			this.titleEditingField.setText(this.showing.getTitle());
			this.editingTitle = true;
		}

		if (this.editingAlbum)
		{
			if (this.albumEditingField.mouseClicked(mouseX, mouseY, mouseButton))
			{
				this.showing.setCustomAlbum(this.albumEditingField.getText());
				this.editingAlbum = false;
				return true;
			}
		} else if (this.albumEditButton.mousePressed(mouseX, mouseY, mouseButton))
		{
			this.albumEditingField.setText(this.showing.getAlbum());
			this.editingAlbum = true;
		}

		if (this.editingArtist)
		{
			if (this.artistEditingField.mouseClicked(mouseX, mouseY, mouseButton))
			{
				this.showing.setCustomArtist(this.artistEditingField.getText());
				this.editingArtist = false;
				return true;
			}
		} else if (this.artistEditButton.mousePressed(mouseX, mouseY, mouseButton))
		{
			this.artistEditingField.setText(this.showing.getArtist());
			this.editingArtist = true;
		}
		return false;*/
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