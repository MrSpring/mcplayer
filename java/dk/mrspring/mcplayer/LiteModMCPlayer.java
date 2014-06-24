package dk.mrspring.mcplayer;

import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import dk.mrspring.mcplayer.gui.overlay.PlayerOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.io.File;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class LiteModMCPlayer implements Tickable
{
	private static KeyBinding sizeToggler = new KeyBinding("key.mcplayer.toggle_size", Keyboard.KEY_F12, "key.categories.litemods");
	private boolean isSmall = false;

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{
		// TODO GUIs and stuffz

		if (sizeToggler.isPressed())
			isSmall = !isSmall;

		PlayerOverlay.render(minecraft.fontRenderer, isSmall);

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

		LiteLoader.getInput().registerKeyBinding(sizeToggler);
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) { }
}
