package BattleMap;

import android.graphics.Bitmap;
import android.graphics.Color;

public class TestTile implements ITile {
    private Bitmap base;
    private Bitmap focused;
    private Bitmap targeted;

    public TestTile(int tileWidth, int tileHeight)
    {
        this.base = Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.ARGB_8888);
        this.base = ColorAll(Color.rgb(0, 255, 0), this.base);

        this.focused = Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.ARGB_8888);
        this.focused = ColorAll(Color.rgb(0, 0, 255), this.focused);

        this.targeted = Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.ARGB_8888);
        this.targeted = ColorAll(Color.rgb(255, 0, 0), this.targeted);
    }

    private Bitmap ColorAll(int color, Bitmap map)
    {
        Bitmap result = Bitmap.createBitmap(map.getWidth(), map.getHeight(), Bitmap.Config.ARGB_8888);
        for(int w = 0; w < result.getWidth(); w++)
        {
            for(int h = 0; h < result.getHeight(); h++)
            {
                result.setPixel(w, h, color);
            }
        }

        return result;
    }

    @Override
    public Bitmap getBase() {
        return this.base;
    }

    @Override
    public Bitmap getFocused()
    {
        return this.focused;
    }

    @Override
    public Bitmap getTargeted()
    {
        return this.targeted;
    }
}
