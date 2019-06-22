package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Enemy {
    private Bitmap img[] = new Bitmap[3];
    private int x, y;
    public int count;
    private int xVel;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean hitPlayer;
    private boolean active;
    public boolean passPlayer; // <-- added by Andrey

    public int damageLimitGun = 0;
    public int randomNumberForShot = 0;
    public boolean enemyShots = false;


    //Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean getHit() { return hitPlayer; }
    public boolean getActive() { return active; }

    //Setters
    public void setHit(boolean hit){ hitPlayer = hit;}
    public void setActive(boolean value) { active = value;}

    public void increaseVel(int vel){xVel += vel;}

    //Init enemy
    public Enemy(Context context, boolean sonic) {
        //Assign images to array for animation
        if (sonic) {
            img[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonic);
            img[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonic2);
            img[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonic3);
        } else { //tails
            img[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tails);
            img[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tails2);
            img[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tails3);
        }
        //keeps track of current img for animation
        count = 0;
        //speed of bitmap?
        xVel = 50;
        //pos of enemy
        x = screenWidth;
        y = screenHeight / 2;

        //Hits player once per spawn
        hitPlayer = false;
        passPlayer = false;
    }

    public void Start(Projectile enemyRocket)
    {
        RNForShot(0,50);
    }


    //draws enemy
    public void draw(Canvas canvas, int playerY, Projectile enemyRocket){


        if (count == 2){
            count = 0;
        }
        canvas.drawBitmap(img[count], x, y, null);
        update(playerY, enemyRocket, canvas);
        count++;
    }

    public void update(int playerY, Projectile enemyRocket, Canvas canvas){

        //movement of enemy here
        if (x<0 || hitPlayer) {
            double temp;
            //Reset pos/respawn
            x = screenWidth;
            y = playerY;
            hitPlayer = false;
            passPlayer = true;
            damageLimitGun = 0;
            enemyShots = false;
            RNForShot(0,50);
        } else {
            x -= xVel;
            //y += yVel;
        }


    }

    public static double RNG(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public double RNForShot(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        randomNumberForShot = (int) x;
        return x;
    }

    //Collision check of bullet and enemy
    public void BulletCheck(int bulletX, int bulletY, Projectile projectile, Player character, Sound audioManager, Loot loot) {
        //Check if in range of pixels

        if (x <= (bulletX + 250) && y + 100 >= bulletY && y - 100 <= bulletY && projectile.isActive) {

            projectile.isActive = false;
            projectile.explosionIsActive = true;
            character.killScoreIsActive = true;
            character.spriteKillScoreX = bulletX + 50;
            character.spriteKillScoreY = bulletY + 50;
            projectile.bulletSpeed = 25;
            projectile.SetExplosionPosX(bulletX - 125);
            projectile.SetExplosionPosY(bulletY - 125);
            projectile.SetBulletPosX(projectile.bulletStartPositionX);
            projectile.SetBulletPosY(projectile.bulletStartPositionY);
            character.SetPlayerScore(character.GetPlayerScore() + 50);
            audioManager.PlayExplosion(false);

            if(!loot.lootIsActive && loot.lootChance > 5)
            {
                loot.ammoLootX = x;
                loot.ammoLootY = y;
                loot.lootIsActive = true;
            }

            //Reset enemy to random position at screenWidth
            x = screenWidth;
            double temp;
            temp = RNG(50, screenHeight-200);
            y = (int)Math.round(temp);



        }
    }


    //Collision check of bullet and enemy
    public void GunBulletCheck(int i, int canvasWidth, int bulletGunX, int bulletGunY, Projectile projectile, Player character, Sound audioManager, Loot loot) {
        //Check if in range of pixels


        if(x <= (bulletGunX + 250) && y + 75 >= bulletGunY && y - 75 <= bulletGunY  && bulletGunX <= canvasWidth && projectile.gunBulletActive[i])
        {

            projectile.gunBulletActive[i] = false;
            if(!audioManager.impactTrue)
            {
                audioManager.impactTrue = true;
            }
            damageLimitGun+=1;
        }

        if (damageLimitGun > 4 && audioManager.impactTrue)
        {
            character.spriteKillScoreX = x - 50;
            character.spriteKillScoreY = y + 50;
            character.killScoreIsActive = true;
            projectile.explosionIsActive = true;
            projectile.SetExplosionPosX(x - 125);
            projectile.SetExplosionPosY(y - 125);
            damageLimitGun = 0;
            audioManager.impactTrue = false;
            character.SetPlayerScore(character.GetPlayerScore() + 50);
            audioManager.PlayExplosion(false);

            if(!loot.lootIsActive && loot.lootChance > 5)
            {
                loot.ammoLootX = x;
                loot.ammoLootY = y;
                loot.lootIsActive = true;
            }

            x = screenWidth;
            double temp;
            temp = RNG(50, screenHeight-200);
            y = (int)Math.round(temp);
            damageLimitGun = 0;
        }
    }



    public void ResetAll(){
        //Reinitialize
        count = 0;
        xVel = 50;
        x = screenWidth;
        y = screenHeight / 2;
        hitPlayer = false;
        passPlayer = false;
    }

}