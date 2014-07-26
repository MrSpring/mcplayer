package dk.mrspring.mcplayer.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

public class GuiPercentageModifier extends Gui
{
    protected GuiButton increase;
    protected GuiButton decrease;
    protected GuiTextField textField;
    protected int value;
    protected int x, y;

    public GuiPercentageModifier(int xPosition, int yPosition, int defaultValue)
    {
        this.value = defaultValue;

        this.x = xPosition;
        this.y = yPosition;

        this.increase = new GuiButton(0, this.x, this.y, 20, 20, "+");
        this.decrease = new GuiButton(1, this.x + 57, this.y, 20, 20, "-");
        this.textField = new GuiTextField(Minecraft.getMinecraft().fontRenderer, x + 21, y, 35, 20);
        this.textField.setText(String.valueOf(this.value));
    }

    public void draw(Minecraft minecraft, int mouseX, int mouseY)
    {
        this.increase.drawButton(minecraft, mouseX, mouseY);
        this.decrease.drawButton(minecraft, mouseX, mouseY);
        this.textField.setText(String.valueOf(this.value) + "%");
        this.textField.drawTextBox();
    }

    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY, int mouseButton)
    {
        if (this.increase.mousePressed(minecraft, mouseX, mouseY))
        {
            switch (mouseButton)
            {
                case 0: if (this.value +  1 <= 100) this.value++; break;
                case 1: if (this.value + 10 <= 100) this.value += 10; break;
                default: if (this.value +  1 <= 100) this.value++; break;
            }
            return true;
        }
        else if (this.decrease.mousePressed(minecraft, mouseX, mouseY))
        {
            switch (mouseButton)
            {
                case 0: if (this.value -  1 >= 0) this.value--; break;
                case 1: if (this.value - 10 >= 0) this.value -= 10; break;
                default:if (this.value -  1 >= 0) this.value--; break;
            }
            return true;
        } else return false;
    }

    public int getValue()
    {
        return this.value;
    }
}
