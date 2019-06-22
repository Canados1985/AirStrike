package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Loot {


    Bitmap ammoLoot[] = new Bitmap[7];
    Bitmap ammoLootCurrentBitmap;

    public int ammoLootX;
    public int ammoLootY;

    public int lootStartX;
    public int lootStartY;

    public int lootSpeed;

    public int tempAmmoLootBitmap = 0;
    public int spriteAmmoLootStep = 1;

    private boolean ammoAnimBackward = false;
    public  boolean lootIsActive = false;

    public int lootChance = 0;

    public Loot(Context context)
    {
        ammoLoot[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_drop_01);
        ammoLoot[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_drop_02);
        ammoLoot[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_drop_03);
        ammoLoot[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_drop_04);
        ammoLoot[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_drop_05);
        ammoLoot[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_drop_06);
        ammoLoot[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_drop_07);
    }

    public void StartLoot(Canvas canvas)
    {
        lootStartX = canvas.getWidth() * 2;
        lootStartY = canvas.getHeight() * 2;
        lootIsActive = false;

        RandomNumberForRocket(0, 50); // here we get random chance for getting rockets
        RandomNumberForLootType(0,50); // here we get random chance for getting loot types
    }

    public void DrawLoot(Canvas canvas)
    {
        if(lootChance > 5)
        {
                 if(lootIsActive && !ammoAnimBackward)
                {

                    ammoLootCurrentBitmap = ammoLoot[tempAmmoLootBitmap];
                    canvas.drawBitmap(ammoLootCurrentBitmap, ammoLootX, ammoLootY, null);
                    spriteAmmoLootStep +=1;
                    tempAmmoLootBitmap +=1;

                    if(spriteAmmoLootStep == 7)
                    {
                        ammoAnimBackward = true;
                    }
                }
                if(lootIsActive && ammoAnimBackward)
                {

                    ammoLootCurrentBitmap = ammoLoot[tempAmmoLootBitmap];
                    canvas.drawBitmap(ammoLootCurrentBitmap, ammoLootX, ammoLootY, null);
                    spriteAmmoLootStep -=1;
                    tempAmmoLootBitmap -=1;

                    if(spriteAmmoLootStep == 1)
                    {
                        ammoAnimBackward = false;
                    }
                }

            Update(canvas);
        }

    }

    public void Update(Canvas canvas)
    {

        if(lootIsActive)
        {
            ammoLootX -= lootSpeed * 3;
            ammoLootY += lootSpeed;
        }
        if(!lootIsActive)
        {
            ammoLootX = lootStartX;
            ammoLootY = lootStartY;
            //lootSpeed = 2;
        }

        if(lootIsActive && ammoLootY > canvas.getHeight() + ammoLootCurrentBitmap.getHeight() * 2 ||
                lootIsActive && ammoLootX < 0 - ammoLootCurrentBitmap.getWidth() / 2)
        {
            ammoLootX = lootStartX;
            ammoLootY = lootStartY;
            //lootSpeed = 2;
            lootIsActive = false;
        }

    }


    public void CheckPlayer(Player character, Sound audioManager)
    {

        if(lootIsActive)
        {
            // x <= (bulletGunX + 250) &&
            // y + 75 >= bulletGunY && y - 75 <= bulletGunY

            if (ammoLootX <= (character.GetPlayerPosX() + 150) &&
                    ammoLootY  >= character.GetPlayerPosY() && ammoLootY <= character.GetPlayerPosY() + 100 && !character.isDead)
            {

                audioManager.PlayScore();
                //Try to spawn rockets
                if((int)RandomNumber(0, 50) > 35)
                {
                    int x = (int)RandomNumber(5, 10);
                    character.quntatyRcketsfromLoot = x;
                    character.playerAmmo = character.playerAmmo + x;
                    character.PickUpRockets = true;
                }
                else
                {

                        int x = (int)RandomNumber(25, 50);
                        character.quntatyAmmofromLoot = x;
                        character.playerGunAmmo = character.playerGunAmmo + x;
                        character.PickUpAmmo = true;

                }


                character.spriteScoreX = character.GetPlayerPosX() - 50;
                character.spriteScoreY = character.GetPlayerPosY() - 50;
                character.playerPickLoot = true;

                RandomNumberForLootType(0,50);
                lootIsActive = false;

            }
        }

    }

    //Why is this function name so long
    public double RandomNumber(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        lootSpeed = (int)x;
        return x;
    }

    public double RandomNumberForRocket(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        lootSpeed = (int)x;
        return x;
    }

    public double RandomNumberForLootType(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        lootSpeed = (int)x;
        return x;
    }
}
