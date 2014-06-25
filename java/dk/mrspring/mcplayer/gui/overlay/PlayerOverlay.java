package dk.mrspring.mcplayer.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class PlayerOverlay
{
	private static final ResourceLocation COVER_ART = new ResourceLocation("example", "textures/clock/face.png");

	private static float originX = 5F;
	private static float originY = 5F;

	private static float width = 80F;
	private static float height = 80F;

	private static float additionalWidth = 0F;

	private static String songTitle = "Take Back The Night";
	private static String albumTitle = "In Real Life";
	private static String artist = "TryHardNinja";

	public static void render(FontRenderer fontRenderer, boolean isSmall, Minecraft minecraft)
	{
		int titleWidth = fontRenderer.getStringWidth(songTitle);

		if (isSmall && additionalWidth > 0)
			additionalWidth -= 5F;

		if (!isSmall && additionalWidth < titleWidth + 10)
			additionalWidth += 5F;

		glDrawRect(5F, 5F, (width + additionalWidth) + originX, height + originY, ReadableColor.BLACK, 0.5F);

		minecraft.getTextureManager().bindTexture(COVER_ART);

		glDrawTexturedRect(5, 5, 80, 80, 0, 0, 512, 512);

		if (additionalWidth > titleWidth)
		{
			fontRenderer.drawString(songTitle, 5 + 80, 5 + 10, 0xFFFFFF, true);
			fontRenderer.drawString(albumTitle, 5 + 80, 5 + 21, 0xCCCCCC, true);
			fontRenderer.drawString(artist, 5 + 80, 5 + 32, 0xCCCCCC, true);
		}
	}

	private static void glDrawRect(float x1, float y1, float x2, float y2, ReadableColor colour, float alpha)
	{
		// Set GL modes
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(colour.getRed(), colour.getGreen(), colour.getBlue(), alpha);

		// Draw the quad
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(x1, y2, 0);
		tessellator.addVertex(x2, y2, 0);
		tessellator.addVertex(x2, y1, 0);
		tessellator.addVertex(x1, y1, 0);
		tessellator.draw();

		// Restore GL modes
		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}

	private static void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2)
	{
		// Set the appropriate OpenGL modes
		glDisable(GL_LIGHTING);
		glDisable(GL_BLEND);
		glAlphaFunc(GL_GREATER, 0.01F);
		glEnable(GL_TEXTURE_2D);
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		float texMapScale = 0.001953125F; // 512px

		// We use the tessellator rather than drawing individual quads because it uses vertex arrays to
		// draw the quads more efficiently.
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0,     y + height, 0, u  * texMapScale, v2 * texMapScale);
		tessellator.addVertexWithUV(x + width, y + height, 0, u2 * texMapScale, v2 * texMapScale);
		tessellator.addVertexWithUV(x + width, y + 0,      0, u2 * texMapScale, v  * texMapScale);
		tessellator.addVertexWithUV(x + 0,     y + 0,      0, u  * texMapScale, v  * texMapScale);
		tessellator.draw();
	}
}
