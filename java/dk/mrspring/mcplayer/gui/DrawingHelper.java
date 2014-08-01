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
	public static void drawRect(float x, float y, float width, float height, Color colour, float alpha)
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

	public static void drawPlayIcon(float xPos, float yPos, float width, float height, Color color, float alpha)
	{
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

	public static void drawPauseIcon(float xPos, float yPos, float width, float height, Color color, float alpha)
	{
		drawRect(xPos, yPos, width / 3, height, color, alpha);
		drawRect(xPos + ((width / 3) * 2), yPos, width / 3, height, color, alpha);
	}

	public static void drawCustomQuad(float v1PosX, float v1PosY, float v2PosX, float v2PosY, float v3PosX, float v3PosY, float v4PosX, float v4PosY, Color color, float alpha)
	{
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(color.getRed(), color.getGreen(), color.getBlue(), alpha);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		tessellator.addVertex(v1PosX, v1PosY, 0);
		tessellator.addVertex(v2PosX, v2PosY, 0);
		tessellator.addVertex(v3PosX, v3PosY, 0);
		tessellator.addVertex(v4PosX, v4PosY, 0);

		tessellator.draw();

		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
}
