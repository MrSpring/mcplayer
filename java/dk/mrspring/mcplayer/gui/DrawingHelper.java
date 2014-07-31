package dk.mrspring.mcplayer.gui;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

/**
 * Created by MrSpring on 31-07-14 for MC Music Player.
 */
public class DrawingHelper
{
	public static void drawRect(float x, float y, float width, float height, ReadableColor colour, float alpha)
	{
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(colour.getRed(), colour.getGreen(), colour.getBlue(), alpha);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(x, y + height, 0);
		tessellator.addVertex(x + width, y + height, 0);
		tessellator.addVertex(x + width, y, 0);
		tessellator.addVertex(x, y, 0);
		tessellator.draw();

		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}

	public static void drawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2, float alpha)
	{
		glDisable(GL_LIGHTING);
		glEnable(GL_BLEND);
		glAlphaFunc(GL_GREATER, 0.01F);
		glEnable(GL_TEXTURE_2D);
		glColor4f(1.0F, 1.0F, 1.0F, alpha);

		float texMapScale = 0.001953125F;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0,     y + height, 0, u  * texMapScale, v2 * texMapScale);
		tessellator.addVertexWithUV(x + width, y + height, 0, u2 * texMapScale, v2 * texMapScale);
		tessellator.addVertexWithUV(x + width, y + 0,      0, u2 * texMapScale, v  * texMapScale);
		tessellator.addVertexWithUV(x + 0,     y + 0,      0, u  * texMapScale, v  * texMapScale);
		tessellator.draw();

		glDisable(GL_BLEND);
	}

	public static void drawPlayIcon(float xPos, float yPos, float width, float height, ReadableColor color, float alpha)
	{
		/*boolean isMouseHovering = ((mouseY >= y && mouseY < y + h) && (mouseX >= x && mouseX < x + w));

		float width = w;
		float height = h;

		float xPos = x;
		float yPos = y;

		float alpha = a;

		if (!isMouseHovering)
			alpha = .5F;

		drawRect(xPos, yPos, width, height, ReadableColor.BLACK, .5F);

		drawRect(xPos, yPos, width, 1F, color, a);
		drawRect(xPos, yPos + height - 1, width, 1F, color, a);

		drawRect(xPos, yPos, 1F, height, color, a);
		drawRect(xPos + width - 1, yPos, 1F, height, color, a);

		height = (h / 10) * 6;
		width = (w / 10) * 6;

		yPos += (h / 10) * 2;
		xPos += (w / 10) * 2;*/

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(color.getRed(), color.getGreen(), color.getBlue(), alpha);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		tessellator.addVertex(xPos, yPos + height, 0);
		tessellator.addVertex(xPos + width, yPos + (height / 2), 0);
		tessellator.addVertex(xPos + width, yPos + (height / 2), 0);
		tessellator.addVertex(xPos, yPos, 0);

		tessellator.draw();

		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}

	public static void drawPauseIcon(float xPos, float yPos, float width, float height, ReadableColor color, float alpha)
	{
		/*boolean isMouseHovering = ((mouseY >= y && mouseY < y + h) && (mouseX >= x && mouseX < x + w));

		float width = w;
		float height = h;

		float xPos = x;
		float yPos = y;

		float alpha = a;

		if (!isMouseHovering)
			alpha = .5F;

		drawRect(xPos, yPos, width, height, ReadableColor.BLACK, .5F);

		drawRect(xPos, yPos, width, 1F, color, a);
		drawRect(xPos, yPos + height - 1, width, 1F, color, a);

		drawRect(xPos, yPos, 1F, height, color, a);
		drawRect(xPos + width - 1, yPos, 1F, height, color, a);

		height = (h / 10) * 6;
		width = (w / 10) * 6;

		yPos += (h / 10) * 2;
		xPos += (w / 10) * 2;*/

		drawRect(xPos, yPos, width / 3, height, color, alpha);
		drawRect(xPos + ((width / 3) * 2), yPos, width / 3, height, color, alpha);
	}
}
