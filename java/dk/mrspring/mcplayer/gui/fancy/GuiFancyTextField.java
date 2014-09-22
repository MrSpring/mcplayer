package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

/**
 * Created by Konrad on 22-09-2014 for MusicPlayer.
 */
public class GuiFancyTextField extends Gui
{
    int width;
    int xPos, yPos;
    String text;
    GuiTextField textField;
    GuiFancyButton button;

    public GuiFancyTextField(int width)
    {
        this(width, null);
    }

    public GuiFancyTextField(int width, String text)
    {
        this.width = width;
        this.textField = new GuiTextField(Minecraft.getMinecraft().fontRenderer, this.xPos, this.yPos, this.width, 20);
        this.button = new GuiFancyButton(this.xPos, this.yPos + this.width + 2, 20, 20, "");
        this.textField.setText(text);
    }

    public void draw()
    {
        this.textField.drawTextBox();
        DrawingHelper.drawCheckMarkIcon(this.xPos + this.width + 2, this.yPos, 20, 20, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
    }

    public String getText()
    {
        return text;
    }

    public String setText(String text)
    {
        String oldText = this.text;
        this.text = text;
        this.textField.setText(this.getText());
        return oldText;
    }

    public ElementClicked mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseX >= this.xPos && mouseY >= this.yPos && mouseX < this.xPos + this.width && mouseY < this.yPos + 20)
        {
            this.textField.mouseClicked(mouseX, mouseY, mouseButton);
            return ElementClicked.TEXT_FIELD;
        } else if (this.button.mousePressed(mouseX, mouseY, mouseButton))
            return ElementClicked.BUTTON;

        return ElementClicked.NOTHING;
    }

    public enum ElementClicked
    {
        NOTHING,
        TEXT_FIELD,
        BUTTON
    }
}
