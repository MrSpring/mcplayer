package dk.mrspring.mcplayer.gui;

import dk.mrspring.mcplayer.LiteModMCPlayer;
import dk.mrspring.mcplayer.thread.MusicManagerThread;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

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

	public static void render(boolean isSmall, Minecraft minecraft, MusicManagerThread thread)
    {
		String song = thread.getCurrentlyPlaying().getTitle();
		String album = thread.getCurrentlyPlaying().getAlbum();
		String artist = thread.getCurrentlyPlaying().getArtist();

        int songWidth = minecraft.fontRenderer.getStringWidth(song);
        int albumWidth = minecraft.fontRenderer.getStringWidth(album);
        int artistWidth = minecraft.fontRenderer.getStringWidth(artist);

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

        DrawingHelper.drawRect(5F, 5F, (width + additionalWidth), height, Color.BLACK, LiteModMCPlayer.config.getOverlayAlpha());


        // minecraft.getTextureManager().bindTexture(file.getCoverLocation());
		thread.getCurrentlyPlaying().bindCover(minecraft);

        DrawingHelper.drawTexturedRect(5, 5, 80, 80, 0, 0, 512, 512, 1F);

		if (thread.isPaused())
		{
			minecraft.getTextureManager().bindTexture(new ResourceLocation("mcplayer", "textures/overlay/pause_overlay.png"));
			DrawingHelper.drawTexturedRect(5, 5, 80, 80, 0, 0, 512, 512, 1F);
		}

		try
		{
			double progressThroughSong = thread.getPosition().toMillis() / thread.getLength().toMillis();
			float progressBerHeight = 2.5F;
			DrawingHelper.drawRect(5F, 5F + (height - progressBerHeight), (float) (width * progressThroughSong), progressBerHeight, Color.CYAN, 1F);

			String nextTitle = thread.getNextInQueue().getTitle();
			String nextArtist = thread.getNextInQueue().getArtist();

			int nextUpWidth = minecraft.fontRenderer.getStringWidth(nextTitle + " by " + nextArtist);

			if (nextUpHeight != 0F)
			{
				DrawingHelper.drawRect(5F, 5F + height, nextUpWidth + 10, nextUpHeight, Color.BLACK, LiteModMCPlayer.config.getOverlayAlpha());
				DrawingHelper.drawRect(5F, 5F + height, nextUpWidth + 10, 1F, Color.BLACK, LiteModMCPlayer.config.getOverlayAlpha());
			}

			if (progressThroughSong > LiteModMCPlayer.config.getNextUpDecimal())
			{
				if (nextUpHeight == 30F)
				{
                    minecraft.fontRenderer.drawString("Next Up:", 5 + 5, 5 + 80 + 5, 0xFFFFFF, true);
                    minecraft.fontRenderer.drawString(nextTitle, 5 + 5, 5 + 80 + 16, 0xFFFFFF, true);
                    minecraft.fontRenderer.drawString(" by " + nextArtist, 5 + 5 + minecraft.fontRenderer.getStringWidth(nextTitle), 5 + 80 + 16, 0xBBBBBB, true);
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
            minecraft.fontRenderer.drawString(song, 5 + 80 + 5, 5 + 10, 0xFFFFFF, true);
            minecraft.fontRenderer.drawString(album, 5 + 80 + 5, 5 + 21, 0xBBBBBB, true);
            minecraft.fontRenderer.drawString(artist, 5 + 80 + 5, 5 + 32, 0xBBBBBB, true);
        }

        oldAdditionalWidth = additionalWidth;
    }
}
