package dk.mrspring.mcplayer.gui;

import net.minecraft.client.renderer.Tessellator;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by MrSpring on 31-07-14 for MC Music Player.
 */
public class DrawingHelper
{
    public static void drawEditIcon(float x, float y, float width, float height, Color color, float alpha)
    {
        drawRect(x, y, width, 1, color, alpha);
        drawRect(x, y + height - 1, width, 1, color, alpha);

        drawRect(x, y, 1, height, color, alpha);
        drawRect(x + width - 1, y, 1, height, color, alpha);

        float tenthWidth = width / 10, tenthHeight = height / 10;

        drawCustomQuad(
                x + 2 * tenthWidth, y + height - 4 * tenthHeight,
                x + 4 * tenthWidth, y + height - 2 * tenthHeight,
                x + tenthWidth, y + height - tenthHeight,
                x + tenthWidth, y + height - tenthHeight,
                color, alpha
        );

        drawCustomQuad(
                x + 7 * tenthWidth, y + tenthHeight,
                x + width - tenthWidth, y + 3 * tenthHeight,
                x + 5 * tenthWidth, y + height - 3 * tenthHeight,
                x + 3 * tenthWidth, y + height - 5 * tenthHeight,
                color, alpha
        );
    }

    public static void drawCheckMarkIcon(float x, float y, float width, float height, Color color, float alpha)
    {
        drawRect(x, y, width, 1, color, alpha);
        drawRect(x, y + height - 1, width, 1, color, alpha);

        drawRect(x, y, 1, height, color, alpha);
        drawRect(x + width - 1, y, 1, height, color, alpha);

        float tenthWidth = width / 10, tenthHeight = height / 10;

        drawCustomQuad(
                x+2*tenthWidth,y+height-5*tenthHeight,
                x+4*tenthWidth,y+height-3*tenthHeight,
                x+4*tenthWidth,y+height-1*tenthHeight,
                x+1*tenthWidth,y+height-4*tenthHeight,
                color, alpha
        );

        drawCustomQuad(
                x+8*tenthWidth,y+1*tenthHeight,
                x+9*tenthWidth,y+2*tenthHeight,
                x+4*tenthWidth,y+height-1*tenthHeight,
                x+3*tenthWidth,y+height-2*tenthHeight,
                color, alpha
        );
    }

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
        tessellator.addVertexWithUV(x + 0, y + height, 0, u * texMapScale, v2 * texMapScale);
        tessellator.addVertexWithUV(x + width, y + height, 0, u2 * texMapScale, v2 * texMapScale);
        tessellator.addVertexWithUV(x + width, y + 0, 0, u2 * texMapScale, v * texMapScale);
        tessellator.addVertexWithUV(x + 0, y + 0, 0, u * texMapScale, v * texMapScale);
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
