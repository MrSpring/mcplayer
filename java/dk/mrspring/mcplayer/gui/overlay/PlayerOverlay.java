package dk.mrspring.mcplayer.gui.overlay;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class PlayerOverlay
{
	private static float originX = 0.5F;
	private static float originY = 0.5F;

	private static float width = 80F;
	private static float height = 80F;

	private static String songTitle = "Take Back The Night";
	private static String albumTitle = "In Real Life";
	private static String artist = "TryHardNinja";

	private static float alpha = 1.0F;

	public static void render(FontRenderer fontRenderer, boolean isSmall)
	{
		int titleWidth = 0;

		if (!isSmall)
			 titleWidth = fontRenderer.getStringWidth(songTitle);

		glDrawRect(5F, 5F, (width + titleWidth + (titleWidth != 0 ? 10 : 0)) + originX, height + originY, ReadableColor.BLACK, 0.5F);
		glDrawRect(5F, 5F, width + originX, height + originY, ReadableColor.BLUE, 0.75F);

		if (!isSmall)
		{
			fontRenderer.drawString(songTitle, 5 + 80 - 0, 5 + 10, 0xFFFFFF, true);
			fontRenderer.drawString(albumTitle, 5 + 80 - 0, 5 + 21, 0xCCCCCC, true);
			fontRenderer.drawString(artist, 5 + 80 - 0, 5 + 32, 0xCCCCCC, true);
		}

		alpha -= 0.1F;
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
}
