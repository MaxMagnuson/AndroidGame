package BattleMap;

import java.util.ArrayList;

public interface IMap {
    public int getMapWidth();
    public int getMapHeight();
    public ITile getTile(int x, int y);
}
