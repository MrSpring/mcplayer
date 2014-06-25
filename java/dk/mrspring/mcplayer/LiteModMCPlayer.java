package dk.mrspring.mcplayer;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import dk.mrspring.mcplayer.file.FileLoader;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.gui.MCPlayerConfigPanel;
import dk.mrspring.mcplayer.list.Playlist;
import dk.mrspring.mcplayer.gui.overlay.PlayerOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import sun.awt.image.PNGImageDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class LiteModMCPlayer implements Tickable//, Configurable
{
	private static KeyBinding sizeToggler = new KeyBinding("key.mcplayer.toggle_size", Keyboard.KEY_F12, "key.categories.litemods");
	public static boolean isSmall = false;
    private Playlist<MusicFile> allFiles = new Playlist<MusicFile>("mcplayer.list.all");
    public static List<String> extensions = new ArrayList<String>();
    private int timer = 0, index = 0;
	private boolean hasInitialised = false;

    @Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{
		// TODO GUIs and stuffz

		if (hasInitialised)
		{
			timer++;
			if (timer > 160)
			{
				index++;
				if (index > allFiles.size())
					index = 0;
				timer = 0;
			}
			if (sizeToggler.isPressed())
				isSmall = !isSmall;
			PlayerOverlay.render(minecraft.fontRenderer, isSmall, minecraft, index, allFiles);
		}
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
        FileLoader.addFiles("C:\\Users\\Konrad\\SkyDrive\\Music", extensions, allFiles);

        System.out.println("");
        System.out.println(" Found files:");

        for(MusicFile file : allFiles)
        {
            System.out.println(file.getTitle());
        }

		allFiles.get(0).getCover();

		hasInitialised = true;
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) { }
/*
	@Override
	public Class<? extends ConfigPanel> getConfigPanelClass()
	{
		return MCPlayerConfigPanel.class;
	}*/
}
