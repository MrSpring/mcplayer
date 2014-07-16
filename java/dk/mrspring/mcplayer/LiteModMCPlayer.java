package dk.mrspring.mcplayer;

import com.mumfrey.liteloader.Tickable;
import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * Created by MrSpring on 24-06-14 for MC Music Player.
 */
public class LiteModMCPlayer implements Tickable
{
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

    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {

    }
}
