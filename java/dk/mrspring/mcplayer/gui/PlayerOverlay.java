package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.file.MusicFile;
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
    public static float alpha = 0.5F;

    private static final ResourceLocation COVER_ART = new ResourceLocation("mcplayer", "textures/cover/cover.png");

    private static float originX = 5F;
    private static float originY = 5F;

    private static float width = 80F;
    private static float height = 80F;

    private static final float widthOverTitle = 10F;

    private static float additionalWidth = 0F;
    private static float oldAdditionalWidth = additionalWidth;

    private static String song = "UNTITLED";
    private static String album = "UNTITLED";
    private static String artist = "UNTITLED";

    public static void render(FontRenderer fontRenderer, boolean isSmall, Minecraft minecraft, MusicFile currentlyPlaying, MusicFile nextUp)
    {
        song = currentlyPlaying.getTitle();
        album = currentlyPlaying.getAlbum();
        artist = currentlyPlaying.getArtist();

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

        glDrawRect(5F, 5F, (width + additionalWidth), height, ReadableColor.BLACK, alpha);


        // minecraft.getTextureManager().bindTexture(file.getCoverLocation());
        currentlyPlaying.bindCover(minecraft);

        glDrawTexturedRect(5, 5, 80, 80, 0, 0, 512, 512);

        /*if (textureId != -1)
            glDeleteTextures(textureId);*/

        if (additionalWidth == oldAdditionalWidth && !isSmall)
        {
            fontRenderer.drawString(song, 5 + 80 + 5, 5 + 10, 0xFFFFFF, true);
            fontRenderer.drawString(album, 5 + 80 + 5, 5 + 21, 0xCCCCCC, true);
            fontRenderer.drawString(artist, 5 + 80 + 5, 5 + 32, 0xCCCCCC, true);
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

    private static void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2)
    {
        glDisable(GL_LIGHTING);
        glDisable(GL_BLEND);
        glAlphaFunc(GL_GREATER, 0.01F);
        glEnable(GL_TEXTURE_2D);
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float texMapScale = 0.001953125F;

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0,     y + height, 0, u  * texMapScale, v2 * texMapScale);
        tessellator.addVertexWithUV(x + width, y + height, 0, u2 * texMapScale, v2 * texMapScale);
        tessellator.addVertexWithUV(x + width, y + 0,      0, u2 * texMapScale, v  * texMapScale);
        tessellator.addVertexWithUV(x + 0,     y + 0,      0, u  * texMapScale, v  * texMapScale);
        tessellator.draw();
    }
}
