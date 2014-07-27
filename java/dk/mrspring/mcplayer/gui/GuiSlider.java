package dk.mrspring.mcplayer.gui;

import com.sun.org.apache.regexp.internal.recompile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;

public class GuiSlider extends GuiButton
{
    private double value = 1;
    public boolean isClicked = false;

    public GuiSlider(int posX, int posY, String title, double defaultValue)
    {
        super(0, posX, posY, 150, 20, title);
        this.enabled = false;
		this.value = defaultValue;
    }

    @Override
    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
    {
        if (this.field_146125_m && p_146116_2_ >= this.field_146128_h && p_146116_3_ >= this.field_146129_i && p_146116_2_ < this.field_146128_h + this.field_146120_f && p_146116_3_ < this.field_146129_i + this.field_146121_g)
            this.isClicked = true;
        return super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_);
    }

    @Override
    public void mouseReleased(int p_146118_1_, int p_146118_2_)
    {
        this.isClicked = false;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY)
    {
        super.mouseDragged(minecraft, mouseX, mouseY);

        if (this.isClicked)
        {
            double xValue = mouseX - this.field_146128_h;
            xValue /= field_146120_f;

			if (xValue > 1)
				this.value = 1;
			else if (xValue < 0)
				this.value = 0;
			else
	            this.value = xValue;
        }
    }

    public void drawSlider(Minecraft minecraft, int mouseX, int mouseY)
    {
        super.drawButton(minecraft, mouseX, mouseY);

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/widgets.png"));

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.field_146128_h + (int)(this.value * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
        this.drawTexturedModalRect(this.field_146128_h + (int)(this.value* (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if (this.isClicked)
            minecraft.fontRenderer.drawString(decimalFormat.format(this.value), mouseX, mouseY - 10, 0xFFFFFF, true);
    }

	public double getValue()
	{
		return this.value;
	}

	/*private double value;
    public boolean field_146135_o;
    private GameSettings.Options field_146133_q;
    private final float field_146132_r;
    private final float field_146131_s;
    private static final String __OBFID = "CL_00000680";

    public GuiSlider(int p_i45016_1_, int p_i45016_2_, int p_i45016_3_, GameSettings.Options p_i45016_4_)
    {
        this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0F, 1.0F);
    }

    public GuiSlider(int p_i45017_1_, int p_i45017_2_, int p_i45017_3_, GameSettings.Options p_i45017_4_, float p_i45017_5_, float p_i45017_6_)
    {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
        this.field_146134_p = 1.0F;
        this.field_146133_q = p_i45017_4_;
        this.field_146132_r = p_i45017_5_;
        this.field_146131_s = p_i45017_6_;
        Minecraft var7 = Minecraft.getMinecraft();
        this.field_146134_p = p_i45017_4_.normalizeValue(var7.gameSettings.getOptionFloatValue(p_i45017_4_));
        this.displayString = var7.gameSettings.getKeyBinding(p_i45017_4_);
    }

    protected int getHoverState(boolean p_146114_1_)
    {
        return 0;
    }
    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_)
    {
        if (this.field_146125_m)
        {
            if (this.field_146135_o)
            {
                this.field_146134_p = (float)(p_146119_2_ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);

                if (this.field_146134_p < 0.0F)
                {
                    this.field_146134_p = 0.0F;
                }

                if (this.field_146134_p > 1.0F)
                {
                    this.field_146134_p = 1.0F;
                }

                float var4 = this.field_146133_q.denormalizeValue(this.field_146134_p);
                p_146119_1_.gameSettings.setOptionFloatValue(this.field_146133_q, var4);
                this.field_146134_p = this.field_146133_q.normalizeValue(var4);
                this.displayString = p_146119_1_.gameSettings.getKeyBinding(this.field_146133_q);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.field_146134_p * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.field_146134_p * (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
        }
    }
    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
    {

    }
    public void mouseReleased(int p_146118_1_, int p_146118_2_)
    {
        this.field_146135_o = false;
    }*/
}
