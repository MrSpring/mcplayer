package dk.mrspring.mcplayer.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import dk.mrspring.mcplayer.LiteModMCPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;

import java.io.FileWriter;
import java.io.IOException;

public class MCPlayerConfigPanel extends Gui implements ConfigPanel
{
    private GuiButton sizeToggler;
    private GuiPercentageModifier overlayTransparency;
    private GuiPercentageModifier nextUpPercentage;
    private GuiTextField musicPath;
    private GuiButton reloadMusic;
    private GuiSlider volumeSlider;

    @Override
    public String getPanelTitle()
    {
        return "MC Music Player Configuration";
    }

    @Override
    public int getContentHeight()
    {
        return 200;
    }

    @Override
    public void onPanelShown(ConfigPanelHost host)
    {
        this.sizeToggler = new GuiButton(0, 1, 0, StatCollector.translateToLocal("gui.mcplayer.toggle_overlay_size"));
        this.overlayTransparency = new GuiPercentageModifier(1, 40, LiteModMCPlayer.config.getOverlayAlphaAsPercentage());
        this.nextUpPercentage = new GuiPercentageModifier(1, 80, LiteModMCPlayer.config.getNextUpPercentage());
        this.musicPath = new GuiTextField(Minecraft.getMinecraft().fontRenderer, 2, 120, 200, 20);
        this.musicPath.setText(LiteModMCPlayer.config.getMusicPath());
        this.reloadMusic = new GuiButton(0, 1, 141, StatCollector.translateToLocal("gui.mcplayer.reload_music"));
        this.volumeSlider = new GuiSlider(1, 170, "Volume");
    }

    @Override
    public void onPanelResize(ConfigPanelHost host)
    {

    }

    @Override
    public void onPanelHidden()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try
        {
            String json = gson.toJson(LiteModMCPlayer.config);
            LiteModMCPlayer.configFile.createNewFile();
            FileWriter writer = new FileWriter(LiteModMCPlayer.configFile);
            writer.write(json);
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onTick(ConfigPanelHost host)
    {

    }

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        this.sizeToggler.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.overlay_transparency"), 0, 30, 0xFFFFFF, true);
        this.overlayTransparency.draw(Minecraft.getMinecraft(), mouseX, mouseY);
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.next_up_percentage"), 0, 70, 0xFFFFFF, true);
        this.nextUpPercentage.draw(Minecraft.getMinecraft(), mouseX, mouseY);
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("gui.mcplayer.music_location"), 0, 110, 0xFFFFFF, true);
        this.musicPath.drawTextBox();
        this.reloadMusic.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.volumeSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        if (this.sizeToggler.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY))
            LiteModMCPlayer.config.toggleOverlaySize();
        else if (this.overlayTransparency.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY, mouseButton))
            LiteModMCPlayer.config.setOverlayTransparency(this.overlayTransparency.getValue());
        else if (this.nextUpPercentage.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY, mouseButton))
            LiteModMCPlayer.config.setNextUpPercentage(this.nextUpPercentage.getValue());
        else if (this.reloadMusic.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY))
        {
            LiteModMCPlayer.config.setMusicPath(this.musicPath.getText());
            LiteModMCPlayer.reloadMusic();
        } else if (this.volumeSlider.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY))
            ;

        this.musicPath.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        this.volumeSlider.mouseReleased(mouseX, mouseY);
    }

    @Override
    public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY)
    {

    }

    @Override
    public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
    {
        this.musicPath.textboxKeyTyped(keyChar, keyCode);
    }
}
