package dk.mrspring.mcplayer;

import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import dk.mrspring.mcplayer.file.FileLoader;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.file.Playlist;
import dk.mrspring.mcplayer.gui.overlay.PlayerOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.jaudiotagger.audio.mp3.MP3File;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class LiteModMCPlayer implements Tickable
{
	private static KeyBinding sizeToggler = new KeyBinding("key.mcplayer.toggle_size", Keyboard.KEY_F12, "key.categories.litemods");
	private boolean isSmall = false;
    private Playlist<MusicFile> allFiles = new Playlist<MusicFile>("mcplayer.list.all");
    public static List<String> extensions = new ArrayList<String>();

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{
		// TODO GUIs and stuffz

		if (sizeToggler.isPressed())
			isSmall = !isSmall;

		PlayerOverlay.render(minecraft.fontRenderer, isSmall, minecraft);

	}

	@Override
	public String getName()
	{
		return ModInfo.name;
	}

	@Override
	public String getVersion()
	{
		return ModInfo.version;
	}

	@Override
	public void init(File configPath)
	{
		// TODO Config and stuffz

        extensions.add(".mp3");
        extensions.add(".wav");

		LiteLoader.getInput().registerKeyBinding(sizeToggler);
        FileLoader.addFiles("E:\\Music", extensions, allFiles);

        System.out.println("");
        System.out.println(" Found files:");

        for(MusicFile file : allFiles)
        {
            System.out.println(file.getTitle());
        }
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) { }
}
