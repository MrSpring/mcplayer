package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.gui.Color;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;

import static dk.mrspring.mcplayer.gui.Color.*;

/**
 * Created by MrSpring on 31-07-14 for MC Music Player.
 */
public class GuiColorDropdownMenu extends Gui
{
	int posX, posY, width, height;

	Color selected;
	Color[] colors;
	boolean open = true;
	int perEntryHeight = 10;

	public GuiColorDropdownMenu(int x, int y, int width, int height)
	{
		colors = new Color[] { BLACK, BLUE, CYAN, GREEN, GREY, ORANGE, PURPLE, RED, WHITE, YELLOW };
		selected = colors[0];

		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
	}

	public void draw(Minecraft minecraft, int mouseX, int mouseY)
	{
		DrawingHelper.drawRect(this.posX, this.posY, this.width, this.height, this.selected, .5F);

		DrawingHelper.drawRect(this.posX, this.posY, this.width, 1F, WHITE, 1F);
		DrawingHelper.drawRect(this.posX, this.posY + this.height - 1, this.width, 1F, WHITE, 1F);

		DrawingHelper.drawRect(this.posX, this.posY, 1F, this.height, WHITE, 1F);
		DrawingHelper.drawRect(this.posX + this.width - 1, this.posY, 1F, this.height, WHITE, 1F);

		minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.color_" + this.selected.getName()), this.posX + 5, this.posY + (this.height / 2) - 4, 0xFFFFFF, true);

		if (this.open)
		{
			int y = this.posY + this.height;

			for (Color color : this.colors)
			{
				DrawingHelper.drawRect(this.posX, y, this.width, this.perEntryHeight, color, 0.75F);

				DrawingHelper.drawRect(this.posX, y, this.width, 1F, color, .5F);
				DrawingHelper.drawRect(this.posX, y + this.perEntryHeight - 1, this.width, 1F, color, .5F);

				DrawingHelper.drawRect(this.posX, y, 1F, this.perEntryHeight, color, .5F);
				DrawingHelper.drawRect(this.posX + this.width, y, 1F, this.perEntryHeight, color, .5F);

				System.out.println(y);

				minecraft.fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.color_" + color.getName()), this.posX + 5, y + 1, 0xFFFFFF, true);

				y += this.perEntryHeight;
			}
		}
	}
}
