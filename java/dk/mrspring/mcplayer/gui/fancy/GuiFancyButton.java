package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.ColorScheme;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.gui.Color;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.StatCollector;

/**
 * Created by MrSpring on 28-07-14 for MC Music Player.
 */
public class GuiFancyButton extends Gui
{
	int posX, posY;
	int width, height;
	public String title;
	boolean enabled = true;

	public GuiFancyButton(int x, int y, int width, int height, String label)
	{
		this.posX = x;
		this.posY = y;

		this.width = width;
		this.height = height;

		this.title = label;
	}

	public boolean mousePressed(int mouseX, int mouseY, int moouseButton)
	{
		return this.isEnabled() && mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height;
	}

	public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
	{
		ColorScheme scheme = LiteModMCPlayer.config.getColorScheme();

		float alpha = 1;

		if (!this.isEnabled())
			alpha = 0.5F;

		DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, scheme.getBaseColor(), 0.25F);

		DrawingHelper.drawRect(this.posX, this.posY, this.width, 1F, scheme.getOutlineColor(), alpha);
		DrawingHelper.drawRect(this.posX, (this.posY + this.height) - 1F, this.width, 1F, scheme.getOutlineColor(), alpha);

		DrawingHelper.drawRect(this.posX, this.posY, 1F, this.height, scheme.getOutlineColor(), alpha);
		DrawingHelper.drawRect((this.posX + this.width) - 1F, this.posY, 1F, this.height, scheme.getOutlineColor(), alpha);

		boolean isMouseHovering = mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height;
		if (isMouseHovering && this.isEnabled())
			DrawingHelper.drawRect(this.posX + 2, this.posY + 2, this.width - 4F, this.height - 4F, Color.LTGREY, 0.75F);

		this.drawCenteredString(minecraft.fontRenderer, this.getTitle(), this.posX + (this.width / 2), this.posY + ((this.height / 2) - 4), this.getTextColor(mouseX, mouseY), !isMouseHovering);
	}

	public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5, boolean drawShadow)
	{
		par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5, drawShadow);
	}

	public int getTextColor(int mouseX, int mouseY)
	{
		boolean isMouseHovering = mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height;
		int textColor = 0xFFFFFF;

		if (isMouseHovering || !this.isEnabled())
			textColor = 0x808080;

		return textColor;
	}

	public String getTitle()
	{
		if (StatCollector.canTranslate(this.title))
			return StatCollector.translateToLocal(this.title);
		else return this.title;
	}

	public GuiFancyButton setDisabled()
	{
		this.enabled = false;
		return this;
	}

	public GuiFancyButton setEnabled()
	{
		this.enabled = true;
		return this;
	}

	public boolean isEnabled()
	{
		return this.enabled;
	}
}
