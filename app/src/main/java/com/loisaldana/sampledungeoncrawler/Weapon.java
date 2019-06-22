package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Weapon {

    Bitmap weaponBitmap;
    Bitmap weaponReadyIcon;
    Bitmap weaponActiveButton;
    Bitmap weaponNotactiveButton;
    Bitmap buttonCurrent;
    Bitmap gunButton;
    Bitmap gunButtonReady;
    Bitmap fireShot[] = new Bitmap[2];

    public int fireShotStep = 1;

    public int buttonX;
    public int buttonY;

    public int buttonGunX;
    public int buttonGunY;

    public boolean gunButtonClicked = false;
    public boolean weaponButtonClicked = false;
    public boolean weaponIsReady = false;
    private int weaponX;
    private int weaponY;

    private  Canvas indicatorCanvas;
    private Canvas buttonBGCanvas;
    private Canvas textBGCanvas;
    private Paint indicatorWeapon = new Paint();
    private Paint buttonBG = new Paint();
    private Paint textButton = new Paint();

    public int GetWeaponPosX(){ return weaponX; }
    public void SetWeaponPosX(int newWeaponX){ weaponX = newWeaponX; }

    public int GetWeaponPosY(){ return weaponY; }
    public void SetWeaponPosY(int newWeaponY){ weaponY = newWeaponY; }


    //Constructor
    public Weapon(Context context)
    {
        weaponBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        weaponReadyIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bicon);
        weaponActiveButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_bt_ac);
        weaponNotactiveButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_bt_bw);
        gunButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.aim);
        gunButtonReady = BitmapFactory.decodeResource(context.getResources(), R.drawable.aim_r);
        fireShot[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_shot_1);
        fireShot[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_shot_2);

    }


    public void WeaponStart(Player character)
    {
        SetWeaponPosX(character.GetPlayerPosX());
        SetWeaponPosY(character.GetPlayerPosY());
    }


    public void DrawFireShot(Canvas canvas, Player character, int x, int y)
    {
        if(character.playerGunShots && !character.isDead)
        {

            if(fireShotStep == 1)
            {
                canvas.drawBitmap(character.GetRotateBitmap(fireShot[0], character.GetAngleRotation()), x, y,null);
                fireShotStep = fireShotStep + 1;
            }
            else
            {
                canvas.drawBitmap(character.GetRotateBitmap(fireShot[1], character.GetAngleRotation()), x, y,null);
                fireShotStep = 1;
            }


        }

    }


    //Draw button on canvas
    void drawButtonWeapon(Canvas canvas, Player character, int x, int y, boolean status, boolean statusCharacter)
    {
        buttonX = x - 200;
        buttonY = y - 200;

        //System.out.println(status);

        if(statusCharacter && !character.isDead)
        {
            if(!weaponButtonClicked && status)
            {
                buttonCurrent = weaponActiveButton;
                canvas.drawBitmap(weaponActiveButton, buttonX, buttonY,null);
            }
            else
            {
                buttonCurrent = weaponNotactiveButton;
                canvas.drawBitmap(weaponNotactiveButton, buttonX, buttonY,null);
                if(!status)
                {
                    weaponButtonClicked = false;
                }
            }
        }

    }

    void drawGunButton(Canvas canvas, Player character, Projectile gun, int x, int y)
    {

        if(!character.isDead)
        {
            buttonGunX = x - gunButton.getWidth() * 2 - 90;
            buttonGunY = y  - 200;

            buttonBG.setColor(Color.LTGRAY);
            buttonBG.setAlpha(50);
            buttonBG.setShadowLayer(5,5,5, Color.BLACK);
            buttonBGCanvas.drawCircle(x - gunButton.getWidth() * 2 ,y - 115,90, buttonBG);


            if(gun.gunBulletDelay == 0)
            {
                canvas.drawBitmap(gunButtonReady, buttonGunX, buttonGunY,null);
            }
            else
            {
                canvas.drawBitmap(gunButton, buttonGunX, buttonGunY,null);
            }

        }

    }


    void drawButtonBG(Canvas canvas, Player character)
    {
        if(character.playerHasCannon && !character.isDead)
        {
            buttonBGCanvas = canvas;


            if(weaponIsReady && character.playerAmmo > 0)
            {
                buttonBG.setColor(Color.RED);
                buttonBG.setAlpha(150);

            }
            else
            {

                buttonBG.setColor(Color.LTGRAY);
                buttonBG.setAlpha(50);
            }

            buttonBG.setShadowLayer(5,5,5, Color.BLACK);
            buttonBGCanvas.drawCircle(buttonX + 90,buttonY + 90,90, buttonBG);


        }

    }

    void drawTextButton(Canvas canvas, Player character)
    {
        textBGCanvas = canvas;
        if(weaponIsReady && character.playerAmmo > 0 && !character.isDead)
        {
            textButton.setColor(Color.YELLOW);
            textButton.setTextSize(45);
            textButton.setTypeface(character.fontFaceLevel);
            textButton.setShadowLayer(5,5,5, Color.BLACK);
            textButton.setAntiAlias(true);

            textBGCanvas.drawText("ROCKET", buttonX, buttonY + 115, textButton);
        }

    }

    //Draw weapon function
    void drawWeapon(Canvas canvas, Bitmap mapBitmap){

        //canvas.drawBitmap(mapBitmap, weaponX-80, weaponY+5,null);
    }

    //Draw weapon indicator function
    void drawWeaponReadyIcon(Canvas canvas, Player charater, Bitmap mapBitmap, int x,int y){


        if(!charater.isDead)
        {
            canvas.drawBitmap(mapBitmap, x, y,null);
        }

    }


    //Draw weapon indicator on HUD
    void drawIndicator(Canvas canvas, Player character, int x, int y, int r){

        indicatorCanvas = canvas;

        if(!character.isDead)
        {
            indicatorWeapon.setColor(Color.WHITE);
            indicatorCanvas.drawCircle(x + 50,y + 50,55,indicatorWeapon);
            if(r < 360)
            {
                indicatorWeapon.setColor(Color.RED);
                weaponIsReady = false;
            }
            else
            {
                indicatorWeapon.setColor(Color.GREEN);
                weaponIsReady = true;
            }

            indicatorWeapon.setShadowLayer(5,5,5, Color.BLACK);
            indicatorCanvas.drawArc(x,y, x+100, y+100, 0, r, true, indicatorWeapon);

        }


        }



}
