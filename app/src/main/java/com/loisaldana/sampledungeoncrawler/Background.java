package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Background {

    Bitmap bgBitmap_01;
    Bitmap bgBitmap_02;
    Bitmap bgBitmap_03;
    Bitmap sky_01;
    Bitmap sky_02;
    Bitmap sky_03;

    public  int sky_1_X;
    public  double sky_1_Y;
    public  int sky_2_X;
    public  double sky_2_Y;
    public  int sky_3_X;
    public  double sky_3_Y;

    private int bgSpeed = 20;

    public int bg_01_StartX;
    public int bg_01_StartY;
    public int bg_02_StartX;
    public int bg_02_StartY;
    public int bg_03_StartX;
    public int bg_03_StartY;

    public int bg_01_X;
    public int bg_01_Y;

    public int bg_02_X;
    public int bg_02_Y;

    public int bg_03_X;
    public int bg_03_Y;

    public Background(Context context)
    {
        bgBitmap_01 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg);
        bgBitmap_02 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg);
        bgBitmap_03 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg);

        sky_01 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky_1);
        sky_02 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky_2);
        sky_03 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky_3);
    }

    public  void StartBG(Canvas canvas, Player character)
    {
        bg_01_StartX = 0;
        bg_01_StartY = 0;

        bg_02_StartX = bg_01_StartX + bgBitmap_02.getWidth();
        bg_02_StartY = 0;

        bg_03_StartX = bg_03_StartX + bgBitmap_03.getWidth() + 2;
        bg_03_StartY = 0;

        bg_01_X = bg_01_StartX;
        bg_01_Y = bg_01_StartY;

        bg_02_X = bg_02_StartX;
        bg_02_Y = bg_02_StartY;

        bg_03_X = bg_03_X + bgBitmap_03.getWidth() * 2;
        bg_03_Y = bg_03_StartY;

        sky_1_X = canvas.getWidth()+ sky_01.getWidth() * 2;
        sky_1_Y = character.RandomNumber(0, canvas.getHeight() - sky_01.getHeight());

        sky_2_X = canvas.getWidth()+ sky_02.getWidth() * 3;
        sky_2_Y = character.RandomNumber(0, canvas.getHeight()  - sky_02.getHeight());

        sky_3_X = canvas.getWidth()+ sky_03.getWidth() * 2;
        sky_3_Y = character.RandomNumber(0, canvas.getHeight() - sky_03.getHeight());
    }

    public void DrawBG(Canvas canvas, Player character)
    {


        canvas.drawBitmap(GetResizeBitmap(bgBitmap_01, canvas.getWidth(), canvas.getHeight()), bg_01_X, bg_01_Y, null);
        canvas.drawBitmap(GetResizeBitmap(bgBitmap_02, canvas.getWidth(), canvas.getHeight()), bg_02_X, bg_02_Y, null);
        canvas.drawBitmap(GetResizeBitmap(bgBitmap_02, canvas.getWidth(), canvas.getHeight()), bg_03_X, bg_03_Y, null);
        canvas.drawBitmap(sky_01, sky_1_X, (int)sky_1_Y, null);
        canvas.drawBitmap(sky_02, sky_2_X, (int)sky_2_Y, null);
        canvas.drawBitmap(sky_03, sky_3_X, (int)sky_3_Y, null);


        if(!character.isDead)
        {Update(canvas, character);}

    }

    void Update(Canvas canvas, Player character)
    {


        if(bg_01_X < -1960)
        {
            bg_01_X = bg_03_StartX;
        }
        if(bg_02_X < -1960)
        {
            bg_02_X = bg_03_StartX;
        }
        if(bg_03_X < -1960)
        {
            bg_03_X = bg_03_StartX;
        }

        if(sky_1_X < -2000)
        {
            sky_1_X = bg_03_StartX;
            sky_1_Y = character.RandomNumber(0, canvas.getHeight()  - sky_01.getHeight());
        }

        if(sky_2_X < -2500)
        {
            sky_2_X = bg_03_StartX + sky_02.getWidth();
            sky_2_Y = character.RandomNumber(0, canvas.getHeight()  - sky_02.getHeight());
        }


        if(sky_3_X < -2200)
        {
            sky_3_X = bg_03_StartX + sky_03.getWidth();
            sky_3_Y = character.RandomNumber(0, canvas.getHeight()  - sky_03.getHeight());
        }

        bg_01_X = bg_01_X - bgSpeed;
        bg_02_X = bg_02_X - bgSpeed;
        bg_03_X = bg_03_X - bgSpeed;

        sky_1_X = sky_1_X - bgSpeed * 2;
        sky_2_X = sky_2_X - bgSpeed * 2 - 1;
        sky_3_X = sky_3_X - bgSpeed * 2 + 1;


    }

    // Function for resizing the bitmap before drawing on the canvas
    public Bitmap GetResizeBitmap(Bitmap bm, int newWidth, int newHeight)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth / width);
        float scaleHeight = ((float) newHeight / height);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizeBitmap = Bitmap.createBitmap(bm, 0,0,width, height, matrix, false);
        return resizeBitmap;
    }

}
