package com.example.maxm.playgroundgame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import BattleMap.IMap;
import BattleMap.ITile;
import BattleMap.TestMap;

/**
 * Created by MaxM on 1/2/2018.
 */

public class GameView extends SurfaceView implements Runnable {
        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        Thread gameThread = null;
        boolean playing = true;

        Canvas canvas;
        Paint paint;

        //History is initial point of contact
        float xOffset;
        float xHistory;
        float yOffset;
        float yHistory;

        private Bitmap battleMap;

        private int trim = 2;
        private int tileWidth = 100;
        private int tileHeight = 100;
        private IMap battleMap2;

        public GameView(Context context) {
            super(context);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            //Bitmap test = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
            paint = new Paint();

            xOffset = 0;
            yOffset = 0;

            this.battleMap = createBattleMap();
            this.battleMap2 = new TestMap(this.tileWidth, this.tileHeight);
        }

        @Override
        public void run()
        {
            while(true)
            {
                if(ourHolder.getSurface().isValid()) {
                    /**
                    // Lock the canvas ready to draw
                    canvas = ourHolder.lockCanvas();
                    // Draw the background color
                    //canvas.drawColor(Color.argb(255, 26, 128, 182));

                    // Choose the brush color for drawing
                    paint.setColor(Color.argb(255, 249, 129, 0));

                    // Make the text a bit bigger
                    paint.setTextSize(45);

                    // Display the current fps on the screen
                    canvas.drawText("FPS:" + 30, 20, 40, paint);

                    // Trouble shoot x and y
                    //canvas.drawText("X Position: " + xOffset, 20, 100, paint);
                    //canvas.drawText("Y Position: " + yOffset, 20, 150, paint);

                    //Rect source = new Rect(0, 0, getScreenWidth(), getScreenHeight());
                    //Rect target = new Rect(0, 0, getScreenWidth(), getScreenHeight());

                    canvas.drawBitmap(battleMap, 0, 0, paint);

                    ourHolder.unlockCanvasAndPost(canvas);
                     **/

                    //Fill canvas black for trim
                    canvas = ourHolder.lockCanvas();
                    Paint paintFill = new Paint();
                    paintFill.setColor(Color.rgb(0, 0, 0));
                    paintFill.setStyle(Paint.Style.FILL);
                    canvas.drawPaint(paintFill);

                    //Fill in Tiles
                    Paint tilePaint = new Paint();
                    for(int x = 0; x < this.battleMap2.getMapWidth(); x++)
                    {
                        for(int y = 0; y < this.battleMap2.getMapWidth(); y++)
                        {
                            ITile currentTile = this.battleMap2.getTile(x, y);
                            int xPos = x * tileWidth + x * trim;
                            int yPos = y* tileHeight + y * trim;
                            canvas.drawBitmap(currentTile.getBase(), xPos, yPos, tilePaint);
                        }
                    }

                    ourHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started then
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float xNow = event.getRawX();
            float yNow = event.getRawY();
            switch(event.getAction()){
                case(MotionEvent.ACTION_DOWN):
                    xHistory = xNow;
                    yHistory = yNow;
                    return true;
                case(MotionEvent.ACTION_MOVE):
                    xOffset += event.getRawX() - xHistory;
                    xOffset = checkBounds(xOffset, getScreenWidth());
                    xHistory = xNow;
                    yOffset += event.getRawY() - yHistory;
                    yOffset = checkBounds(yOffset, getScreenHeight());
                    yHistory = yNow;
                    return true;
                case(MotionEvent.ACTION_UP):

                    return true;
                default :
                    return super.onTouchEvent(event);
            }

        }

        private float checkBounds(float input, float bound)
        {
            if(input > bound)
            {
                return bound;
            }
            else if(input < 0)
            {
                return 0;
            }
            return input;
        }

    private Bitmap createBattleMap()
    {
        int tiles = 10;
        int squareWidth = 100;
        int squareHeight = 100;
        int mapWidth = squareWidth * tiles;
        int mapHeight = squareHeight * tiles;
        int trim = 1;
        Bitmap testMap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888);
        for(int w = 0; w < tiles; w++)
        {
            for(int h = 0; h < tiles; h++)
            {
                int tempWidth = w * squareWidth;
                int tempHeight = h * squareHeight;
                //Do trim
                for(int w2 = tempWidth; w2 < tempWidth + squareWidth; w2++) {
                    for (int h2 = tempHeight; h2 < tempHeight + squareHeight; h2++) {
                        testMap.setPixel(w2, h2, Color.argb(255, 0, 0, 0));
                    }
                }

                //Do inner square
                for(int w2 = tempWidth + trim; w2 < tempWidth + squareWidth - trim; w2++)
                {
                    for(int h2 = tempHeight + trim; h2 < tempHeight + squareHeight - trim; h2++){
                        testMap.setPixel(w2, h2, Color.argb(255, 0, 255, 0));
                    }
                }
            }
        }
        return testMap;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
