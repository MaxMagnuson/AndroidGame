package BattleMap;

import java.util.ArrayList;

public class TestMap implements IMap {

    private int tileWidth;
    private int tileHeight;
    private ArrayList<ArrayList<ITile>> map;

    public TestMap(int tileWidth, int tileHeight)
    {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        int mapWidth = 10;
        int mapHeight = 10;
        this.map = new ArrayList<ArrayList<ITile>>();

        ArrayList<ITile> sample = new ArrayList<ITile>();
        for(int i = 0; i < mapHeight; i++)
        {
            sample.add(new TestTile(this.tileWidth, this.tileHeight));
        }

        for(int i = 0; i < mapWidth; i++)
        {
            this.map.add((ArrayList<ITile>) sample.clone());
        }
    }

    public int getMapWidth()
    {
        return this.map.size();
    }

    public int getMapHeight()
    {
        return this.map.get(0).size();
    }

    public ITile getTile(int x, int y)
    {
        return this.map.get(x).get(y);
    }
}
