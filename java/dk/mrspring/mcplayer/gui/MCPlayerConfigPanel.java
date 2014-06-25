package dk.mrspring.mcplayer.gui;

import com.mumfrey.liteloader.gui.GuiHoverLabel;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.gui.overlay.PlayerOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.settings.GameSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 25-06-14 for MC Music Player.
 */
public class MCPlayerConfigPanel extends Gui implements ConfigPanel
{
	private List<Gui> buttons = new ArrayList<Gui>();

	@Override
	public String getPanelTitle()
	{
		return "mcplayer.config.title";
	}

	@Override
	public int getContentHeight()
	{
		return -1;
	}

	@Override
	public void onPanelShown(ConfigPanelHost host)
	{
		this.buttons.add(new GuiButton(1, 5, 5, "Toggle Size"));
		this.buttons.add(new GuiButton(2, 5, 40, 20, 20, "-"));
		this.buttons.add(new GuiTextField(Minecraft.getMinecraft().fontRenderer, 5 + 20, 40, 5 + 40, 60));
		this.buttons.add(new GuiButton(4, 5 + 40, 40, 20, 20, "+"));
	}

	@Override
	public void onPanelResize(ConfigPanelHost host)
	{

	}

	@Override
	public void onPanelHidden()
	{

	}

	@Override
	public void onTick(ConfigPanelHost host)
	{

	}

	@Override
	public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
	{
		for (Gui button : this.buttons)
		{
			if (button instanceof GuiButton)
				((GuiButton) button).drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
			else if (button instanceof GuiTextField)
				((GuiTextField) button).drawTextBox();
		}
	}

	@Override
	public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
	{
		for(Gui button : buttons)
		{
			if (button instanceof GuiButton)
				if (((GuiButton) button).mousePressed(Minecraft.getMinecraft(), mouseX, mouseY))
				{
					switch (((GuiButton) button).id)
					{
						case 1: LiteModMCPlayer.isSmall = !LiteModMCPlayer.isSmall; break;
					}
				}
				else ;
			else if (button instanceof GuiTextField)
				{
					String text = ((GuiTextField) button).getText();
				}
		}
	}

	@Override
	public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
	{

	}

	@Override
	public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY)
	{

	}

	@Override
	public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
	{

	}
}
