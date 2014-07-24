package dk.mrspring.mcplayer;

import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import dk.mrspring.mcplayer.file.FileLoader;
import dk.mrspring.mcplayer.file.MusicFile;
import dk.mrspring.mcplayer.gui.PlayerOverlay;
import dk.mrspring.mcplayer.list.Playlist;
import dk.mrspring.mcplayer.thread.MusicManagerThread;
import javafx.embed.swing.JFXPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class LiteModMCPlayer implements Tickable
{
    private static KeyBinding sizeToggler = new KeyBinding("key.mcplayer.toggle_size", Keyboard.KEY_F12, "key.categories.litemods");
    private static KeyBinding pausePlay = new KeyBinding("key.mcplayer.pause_play", Keyboard.KEY_P, "key.categories.litemods");

    public static File coverLocation = new File("mcplayer/covers");

    public static boolean isPlaying = true;

    private boolean isSmall = false;

    public static Playlist<MusicFile> allFiles = new Playlist<MusicFile>("ALL_FILES");
    public static List<String> supportedExtensions = new ArrayList<String>();
    public static MusicManagerThread thread;

    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
    {
        if (sizeToggler.isPressed())
            isSmall =! isSmall;

        if (pausePlay.isPressed())
            thread.togglePausePlay();

        PlayerOverlay.render(minecraft.fontRenderer, isSmall, minecraft, thread.getCurrentlyPlaying(), thread.getNextInQueue());
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
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new JFXPanel();
                latch.countDown();
            }
        });
        try
        {
            latch.await();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        LiteLoader.getInput().registerKeyBinding(sizeToggler);
        LiteLoader.getInput().registerKeyBinding(pausePlay);

        coverLocation.mkdirs();

        supportedExtensions.add(".mp3");

        FileLoader.addFiles("E:/Music", supportedExtensions, allFiles);

        thread = new MusicManagerThread(allFiles);
        thread.start();
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {

    }
}
