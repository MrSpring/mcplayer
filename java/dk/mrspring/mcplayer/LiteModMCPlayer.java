package dk.mrspring.mcplayer;

import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import dk.mrspring.mcplayer.file.FileLoader;
import dk.mrspring.mcplayer.list.Playlist;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
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

    public static File coverLocation = new File("mcplayer\\covers");

    public static Playlist allFiles = new Playlist("ALL_FILES");
    public static List<String> supportedExtensions = new ArrayList<String>();

    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
    {

    }

    @Override
    public String getName()
    {
        return "MC Player Mod";
    }

    @Override
    public String getVersion()
    {
        return "0.1.0-BETA";
    }

    @Override
    public void init(File configPath)
    {
        LiteLoader.getInput().registerKeyBinding(sizeToggler);

        coverLocation.mkdirs();

        supportedExtensions.add(".mp3");

        FileLoader.addFiles("E:\\Music", supportedExtensions, allFiles);
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {

    }
}
