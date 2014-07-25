package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.thread.MusicManagerThread;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Konrad on 16-07-2014 for MC Music Player.
 */
public class PlayerOverlay
{
    private static float originX = 5F;
    private static float originY = 5F;

    private static float width = 80F;
    private static float height = 80F;

    private static final float widthOverTitle = 10F;

    private static float additionalWidth = 0F;
    private static float oldAdditionalWidth = additionalWidth;

	private static float nextUpHeight = 0F;

	public static void render(FontRenderer fontRenderer, boolean isSmall, Minecraft minecraft, MusicManagerThread thread)
    {
		String song = thread.getCurrentlyPlaying().getTitle();
		String album = thread.getCurrentlyPlaying().getAlbum();
		String artist = thread.getCurrentlyPlaying().getArtist();

        int songWidth = fontRenderer.getStringWidth(song);
        int albumWidth = fontRenderer.getStringWidth(album);
        int artistWidth = fontRenderer.getStringWidth(artist);

        int targetWidth = Math.max(songWidth, Math.max(albumWidth, artistWidth));

        if (!isSmall)
        {
            if (additionalWidth < targetWidth + widthOverTitle)
                additionalWidth += 5F;

            if (additionalWidth > targetWidth + widthOverTitle + 5)
                additionalWidth -= 5F;
        }
        else
        if (additionalWidth > 0)
            additionalWidth -= 5F;

		/*if (isSmall && additionalWidth > 0)
			additionalWidth -= 5F;

		if (!isSmall && additionalWidth < titleWidth + 10)
			additionalWidth += 5F;*/

        glDrawRect(5F, 5F, (width + additionalWidth), height, ReadableColor.BLACK, LiteModMCPlayer.config.getOverlayAlpha());


        // minecraft.getTextureManager().bindTexture(file.getCoverLocation());
		thread.getCurrentlyPlaying().bindCover(minecraft);

        glDrawTexturedRect(5, 5, 80, 80, 0, 0, 512, 512, 1F);

		if (thread.isPaused())
		{
			minecraft.getTextureManager().bindTexture(new ResourceLocation("mcplayer", "textures/overlay/pause_overlay.png"));
			glDrawTexturedRect(5, 5, 80, 80, 0, 0, 512, 512, 1F);
		}

		try
		{
			double progressThroughSong = thread.getPosition().toMillis() / thread.getLength().toMillis();
			float progressBerHeight = 2.5F;
			glDrawRect(5F, 5F + (height - progressBerHeight), (float) (width * progressThroughSong), progressBerHeight, ReadableColor.CYAN, 1F);

			String nextTitle = thread.getNextInQueue().getTitle();
			String nextArtist = thread.getNextInQueue().getArtist();

			int nextUpWidth = fontRenderer.getStringWidth(nextTitle + " by " + nextArtist);

			if (nextUpHeight != 0F)
			{
				glDrawRect(5F, 5F + height, nextUpWidth + 10, nextUpHeight, ReadableColor.BLACK, LiteModMCPlayer.config.getOverlayAlpha());
				glDrawRect(5F, 5F + height, nextUpWidth + 10, 1F, ReadableColor.BLACK, LiteModMCPlayer.config.getOverlayAlpha());
			}

			System.out.println(" Current through song: " + progressThroughSong + ", showing at: " + LiteModMCPlayer.config.getNextUpDecimal());

			if (progressThroughSong > LiteModMCPlayer.config.getNextUpDecimal())
			{
				if (nextUpHeight == 30F)
				{
					fontRenderer.drawString("Next Up:", 5 + 5, 5 + 80 + 5, 0xFFFFFF, true);
					fontRenderer.drawString(nextTitle, 5 + 5, 5 + 80 + 16, 0xFFFFFF, true);
					fontRenderer.drawString(" by " + nextArtist, 5 + 5 + fontRenderer.getStringWidth(nextTitle), 5 + 80 + 16, 0xBBBBBB, true);
				}

				if (nextUpHeight < 30F)
					nextUpHeight += 1F;
			}
			else if (nextUpHeight > 0F) nextUpHeight -= 1F;
		} catch (Exception e)
		{
			e.printStackTrace();
		}

        /*if (textureId != -1)
            glDeleteTextures(textureId);*/

        if (additionalWidth == oldAdditionalWidth && !isSmall)
        {
            fontRenderer.drawString(song, 5 + 80 + 5, 5 + 10, 0xFFFFFF, true);
            fontRenderer.drawString(album, 5 + 80 + 5, 5 + 21, 0xBBBBBB, true);
            fontRenderer.drawString(artist, 5 + 80 + 5, 5 + 32, 0xBBBBBB, true);
        }

        oldAdditionalWidth = additionalWidth;
    }

    private static void glDrawRect(float x, float y, float width, float height, ReadableColor colour, float alpha)
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

    private static void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2, float alpha)
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
}
