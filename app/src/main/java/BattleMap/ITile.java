package BattleMap;

import android.graphics.Bitmap;

public interface ITile {
    public Bitmap getBase();
    public Bitmap getFocused();
    public Bitmap getTargeted();
}
