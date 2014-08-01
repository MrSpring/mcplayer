package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.gui.fancy.GuiColorDropdownMenu;
import dk.mrspring.mcplayer.gui.fancy.GuiFancyButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.util.ReadableColor;

/**
 * Created by MrSpring on 31-07-14 for MC Music Player.
 */
public class GuiWelcomeScreen extends GuiScreen
{
	GuiScreen onExit;

	GuiFancyButton done;
	GuiColorDropdownMenu colorSelector;

	public GuiWelcomeScreen(GuiScreen previousScreen)
	{
		this.onExit = previousScreen;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.done = new GuiFancyButton(this.width / 2, (this.height / 2) - 40, 40, 30, "gui.done");
		this.colorSelector = new GuiColorDropdownMenu(this.width / 3, (this.height / 3) + (this.height / 3), this.width / 3, 20);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		DrawingHelper.drawRect((this.width / 2) / 2, (this.height / 2) / 2, this.width / 2, this.height / 2, Color.BLACK, 0.5F);
		this.done.drawButton(Minecraft.getMinecraft(), par1, par2);
		this.colorSelector.draw(Minecraft.getMinecraft(), par1, par2);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
