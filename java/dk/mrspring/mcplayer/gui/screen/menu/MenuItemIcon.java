package dk.mrspring.mcplayer.gui.screen.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 19-09-2014 for MusicPlayer.
 */
public class MenuItemIcon
{
    public List<Vertex> vertecies = new ArrayList<Vertex>();
    public ColorType color_type = ColorType.OUTLINE;

    public class Vertex
    {
        public float x = 0, y = 0;
    }

    public enum ColorType
    {
        PROGRESS_BAR,
        BASE,
        OUTLINE
    }
}
