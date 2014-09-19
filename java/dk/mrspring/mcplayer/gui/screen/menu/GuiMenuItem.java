package dk.mrspring.mcplayer.gui.screen.menu;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Konrad on 19-09-2014 for MusicPlayer.
 */
public interface GuiMenuItem
{
    public int getHeight();

    public int getWidth();

    public ResourceLocation getIconLocation();

    public void draw(int mouseX, int mouseY);

    public GuiScreen getGuiScreen();
}
