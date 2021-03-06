package dk.mrspring.mcplayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import dk.mrspring.mcplayer.data.Data;
import dk.mrspring.mcplayer.gui.ConfigPanelMCPlayer;
import dk.mrspring.mcplayer.gui.PlayerOverlay;
import dk.mrspring.mcplayer.gui.screen.GuiScreenAllMusic;
import dk.mrspring.mcplayer.gui.screen.GuiScreenQueueManager;
import dk.mrspring.mcplayer.thread.MusicManagerThread;
import javafx.embed.swing.JFXPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import javax.swing.*;
import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class LiteModMCPlayer implements Tickable, Configurable
{
	public static ModConfig config;
    public static KeyBinding sizeToggler = new KeyBinding("key.mcplayer.toggle_size", Keyboard.KEY_F12, "key.categories.litemods");
	public static KeyBinding pausePlay = new KeyBinding("key.mcplayer.pause_play", Keyboard.KEY_P, "key.categories.litemods");
	public static KeyBinding playNext = new KeyBinding("key.mcplayer.play_next", Keyboard.KEY_RIGHT, "key.categories.litemods");
	public static KeyBinding playPrev = new KeyBinding("key.mcplayer.play_prev", Keyboard.KEY_LEFT, "key.categories.litemods");
	public static KeyBinding openGui = new KeyBinding("key.mcplayer.open_gui", Keyboard.KEY_G, "key.categories.litemods");
	public static KeyBinding openWelcomeScreen = new KeyBinding("key.mcplayer.open_welcome_screen", Keyboard.KEY_R, "key.categories.litemods");

	//public static HashMap<String, MusicFile> musicFileHashMap = new HashMap<String, MusicFile>();

    public static File coverLocation = new File("mcplayer/covers");
    public static File configFile;
	//public static File musicDataFile;

    //public static Playlist<MusicFile> allFiles = new Playlist<MusicFile>("ALL_FILES");
	public static Data data;
    public static MusicManagerThread thread;

    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
    {
		/*String toCopy = "This is now copied";
		minecraft.fontRenderer.drawString(" Copied \"" + toCopy + "\" to your clipboard.", 10, 10, 0xFFFFFF);*/

		/*Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(toCopy);
		clipboard.setContents(selection, selection);*/

        if (sizeToggler.isPressed())
            config.toggleOverlaySize();
        if (pausePlay.isPressed())
            thread.togglePausePlay();
        if (playNext.isPressed())
            thread.scheduleNext();
        if (playPrev.isPressed())
            thread.schedulePrev();
        if (openGui.isPressed())
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenQueueManager(minecraft.currentScreen));

        thread.setVolume(config.getVolume());

		if (!(minecraft.currentScreen instanceof GuiScreenQueueManager))
        	PlayerOverlay.render(!config.getOverlaySize(), minecraft, thread);
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
		config = new ModConfig();

		configFile = new File(configPath.getAbsolutePath() + "\\MC Music Player.json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		if (configFile.exists())
		{
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(configFile));
				config = gson.fromJson(reader, ModConfig.class);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				String json = gson.toJson(config);
				configFile.createNewFile();
				FileWriter writer = new FileWriter(configFile);
				writer.write(json);
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

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
		LiteLoader.getInput().registerKeyBinding(playNext);
		LiteLoader.getInput().registerKeyBinding(playPrev);
		LiteLoader.getInput().registerKeyBinding(openGui);
		LiteLoader.getInput().registerKeyBinding(openWelcomeScreen);

        coverLocation.mkdirs();

        //FileLoader.addFiles(config.getMusicPath(), supportedExtensions, allFiles);
		File file = new File("mcplayer");
		file.mkdirs();
		data = new Data(config.getMusicPath(), file);
		data.addExtension(".mp3");
		try
		{
			data.load();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
        System.out.println(" Music Path: " + config.music_path);

        thread = new MusicManagerThread(data.createDefaultQueue());
        thread.start();
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {

    }

    public static void reloadMusic()
    {
		try
		{
			data.load();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

//        allFiles = new Playlist<MusicFile>("ALL_FILES");
//        FileLoader.addFiles(config.getMusicPath(), supportedExtensions, allFiles);

        if (thread.running)
            thread.stopMusic();

        thread = new MusicManagerThread(data.createDefaultQueue());
        thread.start();
    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass()
    {
        return ConfigPanelMCPlayer.class;
    }
}
