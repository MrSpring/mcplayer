package dk.mrspring.mcplayer.gui.fancy;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.gui.DrawingHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 22-09-2014 for MusicPlayer.
 */
public class GuiFancyTextField extends Gui
{
	private GuiFancyButton finishButton;
	private FontRenderer fontRenderer;
	public int xPos;
	public int yPos;
	public int width;
	public int height;
	private String text = "";
	private int maxTextLength = 32;
	private int cursorCount;
	private boolean drawButton = true;
	private boolean field_146215_m = true;
	private boolean enabled = true;
	private boolean focused;
	private boolean field_146226_p = true;
	private int field_146225_q;
	private int field_146224_r;
	private int field_146223_s;
	private int field_146222_t = 14737632;
	private int field_146221_u = 7368816;
	private boolean drawTextBox = true;

	public GuiFancyTextField(FontRenderer par1FontRenderer, int x, int y, int width, int height)
	{
		this.fontRenderer = par1FontRenderer;
		this.xPos = x;
		this.yPos = y;
		this.width = width;
		this.height = height;
		this.finishButton = new GuiFancyButton(this.xPos, this.yPos, 10, 10, "text");
	}

	public void disableFinishButton()
	{
		this.drawButton = false;
	}

	public void enableFinishButton()
	{
		this.drawButton = true;
	}

	public void updateCursorCounter()
	{
		++this.cursorCount;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setXPos(int xPos)
	{
		this.xPos = xPos;
	}

	public void setYPos(int yPos)
	{
		this.yPos = yPos;
	}

	/**
	 * Sets the text of the textbox
	 */
	public void setText(String text)
	{
		if (text.length() > this.maxTextLength)
		{
			this.text = text.substring(0, this.maxTextLength);
		} else
		{
			this.text = text;
		}

		this.func_146202_e();
	}

	/**
	 * Returns the contents of the textbox
	 */
	public String getText()
	{
		return this.text;
	}

	public String func_146207_c()
	{
		int var1 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
		int var2 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
		return this.text.substring(var1, var2);
	}

	public void func_146191_b(String p_146191_1_)
	{
		String var2 = "";
		String var3 = ChatAllowedCharacters.filerAllowedCharacters(p_146191_1_);
		int var4 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
		int var5 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
		int var6 = this.maxTextLength - this.text.length() - (var4 - this.field_146223_s);
		boolean var7 = false;

		if (this.text.length() > 0)
		{
			var2 = var2 + this.text.substring(0, var4);
		}

		int var8;

		if (var6 < var3.length())
		{
			var2 = var2 + var3.substring(0, var6);
			var8 = var6;
		} else
		{
			var2 = var2 + var3;
			var8 = var3.length();
		}

		if (this.text.length() > 0 && var5 < this.text.length())
		{
			var2 = var2 + this.text.substring(var5);
		}

		this.text = var2;
		this.func_146182_d(var4 - this.field_146223_s + var8);
	}

	public void func_146177_a(int p_146177_1_)
	{
		if (this.text.length() != 0)
		{
			if (this.field_146223_s != this.field_146224_r)
			{
				this.func_146191_b("");
			} else
			{
				this.func_146175_b(this.func_146187_c(p_146177_1_) - this.field_146224_r);
			}
		}
	}

	public void func_146175_b(int p_146175_1_)
	{
		if (this.text.length() != 0)
		{
			if (this.field_146223_s != this.field_146224_r)
			{
				this.func_146191_b("");
			} else
			{
				boolean var2 = p_146175_1_ < 0;
				int var3 = var2 ? this.field_146224_r + p_146175_1_ : this.field_146224_r;
				int var4 = var2 ? this.field_146224_r : this.field_146224_r + p_146175_1_;
				String var5 = "";

				if (var3 >= 0)
				{
					var5 = this.text.substring(0, var3);
				}

				if (var4 < this.text.length())
				{
					var5 = var5 + this.text.substring(var4);
				}

				this.text = var5;

				if (var2)
				{
					this.func_146182_d(p_146175_1_);
				}
			}
		}
	}

	public int func_146187_c(int p_146187_1_)
	{
		return this.func_146183_a(p_146187_1_, this.func_146198_h());
	}

	public int func_146183_a(int p_146183_1_, int p_146183_2_)
	{
		return this.func_146197_a(p_146183_1_, this.func_146198_h(), true);
	}

	public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_)
	{
		int var4 = p_146197_2_;
		boolean var5 = p_146197_1_ < 0;
		int var6 = Math.abs(p_146197_1_);

		for (int var7 = 0; var7 < var6; ++var7)
		{
			if (var5)
			{
				while (p_146197_3_ && var4 > 0 && this.text.charAt(var4 - 1) == 32)
				{
					--var4;
				}

				while (var4 > 0 && this.text.charAt(var4 - 1) != 32)
				{
					--var4;
				}
			} else
			{
				int var8 = this.text.length();
				var4 = this.text.indexOf(32, var4);

				if (var4 == -1)
				{
					var4 = var8;
				} else
				{
					while (p_146197_3_ && var4 < var8 && this.text.charAt(var4) == 32)
					{
						++var4;
					}
				}
			}
		}

		return var4;
	}

	public void func_146182_d(int p_146182_1_)
	{
		this.setCorsorPosition(this.field_146223_s + p_146182_1_);
	}

	public void setCorsorPosition(int p_146190_1_)
	{
		this.field_146224_r = p_146190_1_;
		int var2 = this.text.length();

		if (this.field_146224_r < 0)
		{
			this.field_146224_r = 0;
		}

		if (this.field_146224_r > var2)
		{
			this.field_146224_r = var2;
		}

		this.func_146199_i(this.field_146224_r);
	}

	public void func_146196_d()
	{
		this.setCorsorPosition(0);
	}

	public void func_146202_e()
	{
		this.setCorsorPosition(this.text.length());
	}

	/**
	 * Call this method from your GuiScreen to process the keys into the textbox
	 */
	public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_)
	{
		if (!this.focused)
		{
			return false;
		} else
		{
			switch (p_146201_1_)
			{
				case 1:
					this.func_146202_e();
					this.func_146199_i(0);
					return true;

				case 3:
					GuiScreen.setClipboardString(this.func_146207_c());
					return true;

				case 22:
					if (this.field_146226_p)
					{
						this.func_146191_b(GuiScreen.getClipboardString());
					}

					return true;

				case 24:
					GuiScreen.setClipboardString(this.func_146207_c());

					if (this.field_146226_p)
					{
						this.func_146191_b("");
					}

					return true;

				default:
					switch (p_146201_2_)
					{
						case 14:
							if (GuiScreen.isCtrlKeyDown())
							{
								if (this.field_146226_p)
								{
									this.func_146177_a(-1);
								}
							} else if (this.field_146226_p)
							{
								this.func_146175_b(-1);
							}

							return true;

						case 199:
							if (GuiScreen.isShiftKeyDown())
							{
								this.func_146199_i(0);
							} else
							{
								this.func_146196_d();
							}

							return true;

						case 203:
							if (GuiScreen.isShiftKeyDown())
							{
								if (GuiScreen.isCtrlKeyDown())
								{
									this.func_146199_i(this.func_146183_a(-1, this.func_146186_n()));
								} else
								{
									this.func_146199_i(this.func_146186_n() - 1);
								}
							} else if (GuiScreen.isCtrlKeyDown())
							{
								this.setCorsorPosition(this.func_146187_c(-1));
							} else
							{
								this.func_146182_d(-1);
							}

							return true;

						case 205:
							if (GuiScreen.isShiftKeyDown())
							{
								if (GuiScreen.isCtrlKeyDown())
								{
									this.func_146199_i(this.func_146183_a(1, this.func_146186_n()));
								} else
								{
									this.func_146199_i(this.func_146186_n() + 1);
								}
							} else if (GuiScreen.isCtrlKeyDown())
							{
								this.setCorsorPosition(this.func_146187_c(1));
							} else
							{
								this.func_146182_d(1);
							}

							return true;

						case 207:
							if (GuiScreen.isShiftKeyDown())
							{
								this.func_146199_i(this.text.length());
							} else
							{
								this.func_146202_e();
							}

							return true;

						case 211:
							if (GuiScreen.isCtrlKeyDown())
							{
								if (this.field_146226_p)
								{
									this.func_146177_a(1);
								}
							} else if (this.field_146226_p)
							{
								this.func_146175_b(1);
							}

							return true;

						default:
							if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_))
							{
								if (this.field_146226_p)
								{
									this.func_146191_b(Character.toString(p_146201_1_));
								}

								return true;
							} else
							{
								return false;
							}
					}
			}
		}
	}

	/**
	 * @return Returns true if the Button is pressed.
	 */
	public ClickedType mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		boolean isMouseHovering = mouseX >= this.xPos && mouseX < this.xPos + this.width && mouseY >= this.yPos && mouseY < this.yPos + this.height;

		if (this.enabled)
		{
			this.setFocused(isMouseHovering);
			return ClickedType.TEXT_FIELD;
		}

		if (this.focused && mouseButton == 0 && isMouseHovering)
		{
			int mouseXLocalPosition = mouseX - this.xPos;

			if (this.field_146215_m)
			{
				mouseXLocalPosition -= 4;
			}

			String var6 = this.fontRenderer.trimStringToWidth(this.text.substring(this.field_146225_q), this.func_146200_o());
			this.setCorsorPosition(this.fontRenderer.trimStringToWidth(var6, mouseXLocalPosition).length() + this.field_146225_q);
			return ClickedType.TEXT_FIELD;
		} else if (this.drawButton)
		{
			if (this.finishButton.mousePressed(mouseX, mouseY, mouseButton))
				return ClickedType.FINISH_BUTTON;
			else return ClickedType.NOTHING;
		}
		return ClickedType.NOTHING;
	}

	public void drawTextField()
	{
		if (this.getDrawTextField())
		{
			if (this.func_146181_i())
			{
				drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
				drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
			}

			if (drawButton)
			{
				this.finishButton.posX = this.xPos + width + 5;
				DrawingHelper.drawCheckMarkIcon(this.xPos + this.width + 5, this.yPos + 0.5F, 10, 10, LiteModMCPlayer.config.getColorScheme().getOutlineColor(), 1F);
			}

			int var1 = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
			int var2 = this.field_146224_r - this.field_146225_q;
			int var3 = this.field_146223_s - this.field_146225_q;
			String var4 = this.fontRenderer.trimStringToWidth(this.text.substring(this.field_146225_q), this.func_146200_o());
			boolean var5 = var2 >= 0 && var2 <= var4.length();
			boolean var6 = this.focused && this.cursorCount / 6 % 2 == 0 && var5;
			int var7 = this.field_146215_m ? this.xPos + 4 : this.xPos;
			int var8 = this.field_146215_m ? this.yPos + (this.height - 8) / 2 : this.yPos;
			int var9 = var7;

			if (var3 > var4.length())
			{
				var3 = var4.length();
			}

			if (var4.length() > 0)
			{
				String var10 = var5 ? var4.substring(0, var2) : var4;
				var9 = this.fontRenderer.drawStringWithShadow(var10, var7, var8, var1);
			}

			boolean var13 = this.field_146224_r < this.text.length() || this.text.length() >= this.getMaxTextLength();
			int var11 = var9;

			if (!var5)
			{
				var11 = var2 > 0 ? var7 + this.width : var7;
			} else if (var13)
			{
				var11 = var9 - 1;
				--var9;
			}

			if (var4.length() > 0 && var5 && var2 < var4.length())
			{
				this.fontRenderer.drawStringWithShadow(var4.substring(var2), var9, var8, var1);
			}

			if (var6)
			{
				if (var13)
				{
					Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
				} else
				{
					this.fontRenderer.drawStringWithShadow("_", var11, var8, var1);
				}
			}

			if (var3 != var2)
			{
				int var12 = var7 + this.fontRenderer.getStringWidth(var4.substring(0, var3));
				this.func_146188_c(var11, var8 - 1, var12 - 1, var8 + 1 + this.fontRenderer.FONT_HEIGHT);
			}
		}
	}

	private void func_146188_c(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_)
	{
		int var5;

		if (p_146188_1_ < p_146188_3_)
		{
			var5 = p_146188_1_;
			p_146188_1_ = p_146188_3_;
			p_146188_3_ = var5;
		}

		if (p_146188_2_ < p_146188_4_)
		{
			var5 = p_146188_2_;
			p_146188_2_ = p_146188_4_;
			p_146188_4_ = var5;
		}

		if (p_146188_3_ > this.xPos + this.width)
		{
			p_146188_3_ = this.xPos + this.width;
		}

		if (p_146188_1_ > this.xPos + this.width)
		{
			p_146188_1_ = this.xPos + this.width;
		}

		Tessellator var6 = Tessellator.instance;
		GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glLogicOp(GL11.GL_OR_REVERSE);
		var6.startDrawingQuads();
		var6.addVertex((double) p_146188_1_, (double) p_146188_4_, 0.0D);
		var6.addVertex((double) p_146188_3_, (double) p_146188_4_, 0.0D);
		var6.addVertex((double) p_146188_3_, (double) p_146188_2_, 0.0D);
		var6.addVertex((double) p_146188_1_, (double) p_146188_2_, 0.0D);
		var6.draw();
		GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void func_146203_f(int p_146203_1_)
	{
		this.maxTextLength = p_146203_1_;

		if (this.text.length() > p_146203_1_)
		{
			this.text = this.text.substring(0, p_146203_1_);
		}
	}

	public int getMaxTextLength()
	{
		return this.maxTextLength;
	}

	public int func_146198_h()
	{
		return this.field_146224_r;
	}

	public boolean func_146181_i()
	{
		return this.field_146215_m;
	}

	public void func_146185_a(boolean p_146185_1_)
	{
		this.field_146215_m = p_146185_1_;
	}

	public void func_146193_g(int p_146193_1_)
	{
		this.field_146222_t = p_146193_1_;
	}

	public void func_146204_h(int p_146204_1_)
	{
		this.field_146221_u = p_146204_1_;
	}

	/**
	 * Sets focus to this gui element
	 */
	public void setFocused(boolean focused)
	{
		if (focused && !this.focused)
		{
			this.cursorCount = 0;
		}

		this.focused = focused;
	}

	/**
	 * Getter for the focused field
	 */
	public boolean isFocused()
	{
		return this.focused;
	}

	public void func_146184_c(boolean p_146184_1_)
	{
		this.field_146226_p = p_146184_1_;
	}

	public int func_146186_n()
	{
		return this.field_146223_s;
	}

	public int func_146200_o()
	{
		return this.func_146181_i() ? this.width - 8 : this.width;
	}

	public void func_146199_i(int p_146199_1_)
	{
		int var2 = this.text.length();

		if (p_146199_1_ > var2)
		{
			p_146199_1_ = var2;
		}

		if (p_146199_1_ < 0)
		{
			p_146199_1_ = 0;
		}

		this.field_146223_s = p_146199_1_;

		if (this.fontRenderer != null)
		{
			if (this.field_146225_q > var2)
			{
				this.field_146225_q = var2;
			}

			int var3 = this.func_146200_o();
			String var4 = this.fontRenderer.trimStringToWidth(this.text.substring(this.field_146225_q), var3);
			int var5 = var4.length() + this.field_146225_q;

			if (p_146199_1_ == this.field_146225_q)
			{
				this.field_146225_q -= this.fontRenderer.trimStringToWidth(this.text, var3, true).length();
			}

			if (p_146199_1_ > var5)
			{
				this.field_146225_q += p_146199_1_ - var5;
			} else if (p_146199_1_ <= this.field_146225_q)
			{
				this.field_146225_q -= this.field_146225_q - p_146199_1_;
			}

			if (this.field_146225_q < 0)
			{
				this.field_146225_q = 0;
			}

			if (this.field_146225_q > var2)
			{
				this.field_146225_q = var2;
			}
		}
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean getDrawTextField()
	{
		return this.drawTextBox;
	}

	public void setDrawTextField(boolean draw)
	{
		this.drawTextBox = draw;
	}

	public enum ClickedType
	{
		NOTHING, TEXT_FIELD, FINISH_BUTTON
	}
}
