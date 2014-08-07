package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.ColorScheme;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

/**
 * Created by MrSpring on 06-08-14 for MC Music Player.
 */
public class GuiColorSchemeList extends Gui
{
	ArrayList<ColorScheme> schemes = new ArrayList<ColorScheme>();

	int posX, posY;
	int height, width;

	int selected;

	int perEntryHeight = 16;

	public GuiColorSchemeList(int x, int y, int width, int height)
	{
		this.schemes.add(ColorScheme.HIGH_CONTRAST);
		this.schemes.add(ColorScheme.RED);
		this.schemes.add(ColorScheme.CYAN);
		this.schemes.add(ColorScheme.GREEN);
		this.schemes.add(ColorScheme.INVERTED);

		this.selected = this.schemes.indexOf(LiteModMCPlayer.config.getColorScheme());

		this.posX = x;
		this.posY = y;

		this.height = this.schemes.size() * this.perEntryHeight;
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void draw(Minecraft minecraft)
	{
		//DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, Color.YELLOW, 1F);

		int y = 0;
		for (int i = 0; i < schemes.size(); i++)
		{
			ColorScheme scheme = schemes.get(i);
			float alpha = scheme.getBaseAlpha();
			if (i == this.selected)
				alpha += .5F;
			DrawingHelper.drawRect(this.posX + 1, this.posY + y + 1, this.width - 2, this.perEntryHeight, scheme.getBaseColor(), alpha);

			DrawingHelper.drawRect(this.posX + 1, this.posY + 1 + y, this.width - 2, 1F, scheme.getOutlineColor(), 1F);
			DrawingHelper.drawRect(this.posX + 1, this.posY + 1 + y + this.perEntryHeight - 1, this.width - 2, 1F, scheme.getOutlineColor(), 1F);

			DrawingHelper.drawRect(this.posX + 1, this.posY + 1 + y, 1F, this.perEntryHeight - 1, scheme.getOutlineColor(), 1F);
			DrawingHelper.drawRect(this.posX + 1 + this.width - 3, this.posY + 1 + y, 1F, this.perEntryHeight - 1, scheme.getOutlineColor(), 1F);

			int color = 0xBBBBBB;

			if (i == this.selected)
				color = 0xFFFFFF;

			minecraft.fontRenderer.drawString("gui.mcplayer.schemes." + scheme.name().toLowerCase(), this.posX + 4, this.posY + y + (this.perEntryHeight / 2) - 4, color, true);

			y += this.perEntryHeight;
		}
	}

	public boolean mousePressed(int mouseX, int mouseY)
	{
		if (mouseY > this.posY && mouseY < this.posY + this.height && mouseX > this.posX && mouseX < this.posX + this.width)
		{
			int listMouseY = mouseY - this.posY;
			this.selected = listMouseY / this.perEntryHeight;
			if (this.selected > this.schemes.size() - 1)
				this.selected = this.schemes.size() - 1;
			return true;
		} else return false;
	}

	public ColorScheme getSelected()
	{
		return this.schemes.get(this.selected);
	}
}
