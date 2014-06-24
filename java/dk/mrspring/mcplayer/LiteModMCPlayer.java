package dk.mrspring.mcplayer;

import com.mumfrey.liteloader.Tickable;
import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * Created by MrSpring on 24-06-14 for MCP.
 */
public class LiteModMCPlayer implements Tickable
{
	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{
		// TODO GUIs and stuffz
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
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) { }
}
