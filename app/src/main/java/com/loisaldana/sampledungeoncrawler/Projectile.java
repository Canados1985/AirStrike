package com.loisaldana.sampledungeoncrawler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


public class Projectile {

    Bitmap enemyRocket[] = new Bitmap[4];
    Bitmap gunBullet[] = new Bitmap[5];
    Bitmap bulletBitmap[] = new Bitmap[4];
    Bitmap explosion[] = new Bitmap[5];
    Bitmap enemyRocketExplosion[] = new Bitmap[5];
    Bitmap bulletCurrentBitmap;
    Bitmap explosionCurrentBitmap;
    Bitmap explosionEnemyRocketCurrentBitmap;
    Enemy temp;

    public int enemyStartRocketPositionX;
    public int enemyStartRocketPositionY;

    public int enemyRocketX;
    public int enemyRocketY;

    public int enemyRocketExplosionX;
    public int enemyRocketExplosionY;


    public boolean enemyRocketActive = false;
    public boolean enemyRocketSetInPosition = false;
    public int enemyRocketSpriteStep = 1;
    public int enemyRocketTempBitmap = 0;
    public int enemyRocketSpeed = 25;


    public int bulletStartPositionX;
    public int bulletStartPositionY;

    private int bulletX;
    private int bulletY;

    public int gunBulletX[] = new int[5];
    public  int gunBulletY[] = new int[5];
    public boolean gunBulletActive[] = new boolean[5];
    public int gunBulletSpriteAngle = 0;

    public int gunBulletDelay = 0;

    int tempBitmap = 0;
    int spriteStep = 1;
    int bulletSpeed = 25;
    int gunBulletSpeed = 35;

    private int explosionX;
    private int explosionY;
    int tempExplosionBitmap = 0;
    int spriteExplosionStep = 1;

    private int explosionEnemyRocketX;
    private int explosionEnemyRocketY;
    int tempExplosionEnemyRocketBitmap = 0;
    int spriteExplosionEnemyRocketStep = 1;

    public boolean isActive = false;
    public boolean explosionIsActive = false;
    public boolean explosionEnemyRocket = false;
    public boolean setPlayerPosition = false;

    public int GetBulletPosX(){ return bulletX; }
    public void SetBulletPosX(int newBulletX ){ bulletX = newBulletX; }

    public int GetBulletPosY(){ return bulletY; }
    public void SetBulletPosY( int newBulletY ){ bulletY = newBulletY; }

    public int GetExplosionPosX(){ return explosionX; }
    public void SetExplosionPosX(int newExplosionX ){ explosionX = newExplosionX;}

    public int GetExplosionPosY(){ return explosionY; }
    public void SetExplosionPosY( int newExplosionY ){ explosionY = newExplosionY; }

    public Projectile (Context context)
    {

        for(int i=0; i<gunBullet.length;i++)
        {
            gunBullet[i] = BitmapFactory.decodeResource(context.getResources(), R.drawable.gun_bullet);
            gunBulletActive[i] = false;
        }

        bulletBitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet1);
        bulletBitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet2);
        bulletBitmap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet3);
        bulletBitmap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet4);

        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_4);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_5);

        enemyRocketExplosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_1);
        enemyRocketExplosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_2);
        enemyRocketExplosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_3);
        enemyRocketExplosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_4);
        enemyRocketExplosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_5);

        enemyRocket[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet1);
        enemyRocket[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet2);
        enemyRocket[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet3);
        enemyRocket[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet4);
    }

    public void ProjectileStart(Player character, Canvas canvas)
    {
        for(int i=0; i<gunBullet.length;i++)
        {
            gunBulletX[i] = character.GetPlayerPosX();
            gunBulletY[i] = character.GetPlayerPosY();
        }

        SetBulletPosX(character.GetPlayerPosX());
        SetBulletPosY(character.GetPlayerPosY());
        bulletStartPositionX = GetBulletPosX();
        bulletStartPositionY = GetBulletPosY();


        for(int i =0; i<enemyRocket.length; i++)
        {
            enemyStartRocketPositionX = canvas.getWidth() + enemyRocket[i].getWidth();
            enemyStartRocketPositionY = canvas.getHeight() + enemyRocket[i].getHeight();
        }

    }

    public void onDraw(Canvas canvas, Player character, int posX, int posY)
    {
        //System.out.println("EXPLOSION IS ACTIVE    " + explosionIsActive);
        //Draw bullet here
        if(isActive)
        {
            if(!setPlayerPosition)
            {
                SetBulletPosX(posX + 25);
                SetBulletPosY(posY - 25);
                setPlayerPosition = true;
            }
            if(spriteStep < 4)
            {
                bulletCurrentBitmap = bulletBitmap[tempBitmap];
                drawBullet(canvas, character, bulletCurrentBitmap);
                tempBitmap = tempBitmap + 1;
                spriteStep = spriteStep + 1;
            }
            if(spriteStep == 4)
            {
                bulletCurrentBitmap = bulletBitmap[tempBitmap];
                drawBullet(canvas, character, bulletCurrentBitmap);
                tempBitmap = 0;
                spriteStep = 1;
            }
        }

        if(explosionIsActive)
        {
            //System.out.println("EXPLOSION IS ACTIVE");
            if(spriteExplosionStep < 5)
            {
                explosionCurrentBitmap = explosion[tempExplosionBitmap];
                drawExplosion(canvas, explosionCurrentBitmap);
                tempExplosionBitmap = tempExplosionBitmap + 1;
                spriteExplosionStep = spriteExplosionStep + 1;
            }
            if(spriteExplosionStep == 5)
            {

                explosionCurrentBitmap = explosion[tempExplosionBitmap];
                drawExplosion(canvas, explosionCurrentBitmap);
                tempExplosionBitmap = 0;
                spriteExplosionStep = 1;
                explosionX = bulletX;
                explosionY = bulletY;
                explosionIsActive = false;
            }
        }

        //Explosion for enemy rocket
        if(explosionEnemyRocket)
        {
            enemyRocketExplosionX = posX;
            enemyRocketExplosionY = posY - 50;

            if(spriteExplosionEnemyRocketStep < 5)
            {
                explosionEnemyRocketCurrentBitmap = enemyRocketExplosion[tempExplosionEnemyRocketBitmap];
                drawExplosionEnemyRocket(canvas, explosionEnemyRocketCurrentBitmap);
                tempExplosionEnemyRocketBitmap = tempExplosionEnemyRocketBitmap + 1;
                spriteExplosionEnemyRocketStep = spriteExplosionEnemyRocketStep + 1;
            }
            if(spriteExplosionEnemyRocketStep == 5)
            {

                explosionEnemyRocketCurrentBitmap = enemyRocketExplosion[tempExplosionEnemyRocketBitmap];
                drawExplosionEnemyRocket(canvas, explosionEnemyRocketCurrentBitmap);
                tempExplosionEnemyRocketBitmap = 0;
                spriteExplosionEnemyRocketStep = 1;
                explosionEnemyRocket = false;
            }
        }

    }

    /// Here we draw enemy rocket
    public void DrawEnemyRocket(Canvas canvas, Player character, Enemy currentEnemy, Sound audioManager)
    {

        if(!enemyRocketActive && currentEnemy.randomNumberForShot > 30 && !currentEnemy.enemyShots && currentEnemy.getX() > canvas.getWidth() / 2)
        {
            enemyRocketActive = true;
            currentEnemy.enemyShots = true;
        }


        if(enemyRocketActive && currentEnemy.enemyShots && !character.isDead)
        {

            if(!enemyRocketSetInPosition)
            {
                if(currentEnemy.getActive())
                {
                    enemyRocketX = currentEnemy.getX();
                    enemyRocketY = currentEnemy.getY();
                    temp = currentEnemy;
                    audioManager.PlayPlayerRocket();
                    currentEnemy.randomNumberForShot = 0;
                }
                else
                {
                    enemyRocketX = currentEnemy.getX();
                    enemyRocketY = currentEnemy.getY();
                    temp = currentEnemy;
                    audioManager.PlayPlayerRocket();
                    currentEnemy.randomNumberForShot = 0;

                }

                enemyRocketSetInPosition = true;

            }

            if(enemyRocketSpriteStep < 4)
            {

                canvas.drawBitmap(enemyRocket[enemyRocketTempBitmap], enemyRocketX, enemyRocketY,null);
                enemyRocketTempBitmap +=1;
                enemyRocketSpriteStep +=1;

            }
            if(enemyRocketSpriteStep == 4)
            {

                canvas.drawBitmap(enemyRocket[enemyRocketTempBitmap], enemyRocketX, enemyRocketY,null);
                enemyRocketTempBitmap = 0;
                enemyRocketSpriteStep = 1;
            }

            UpdateEnemyRocket(enemyRocket[enemyRocketTempBitmap], character, temp, audioManager);
        }


    }

    // Here we draw machine gun bullets
    void drawGunBullet(Canvas canvas, Player character, Weapon gun)
    {

        if(gunBulletActive[0] == true && !character.isDead)
        {
           canvas.drawBitmap(GetRotateBitmap(gunBullet[0], gunBulletSpriteAngle)  , gunBulletX[0] + 100, gunBulletY[0] + 25,null);
        }
        if(gunBulletActive[1] == true && !character.isDead)
        {
            canvas.drawBitmap(GetRotateBitmap(gunBullet[1], gunBulletSpriteAngle)  , gunBulletX[1] + 100, gunBulletY[1] + 25,null);
        }
        if(gunBulletActive[2] == true && !character.isDead)
        {
            canvas.drawBitmap(GetRotateBitmap(gunBullet[2], gunBulletSpriteAngle)  , gunBulletX[2] + 100, gunBulletY[2] + 25,null);
        }
        if(gunBulletActive[3] == true && !character.isDead)
        {
            canvas.drawBitmap(GetRotateBitmap(gunBullet[3], gunBulletSpriteAngle)  , gunBulletX[3] + 100, gunBulletY[3] + 25,null);
        }
        if(gunBulletActive[4] == true && !character.isDead)
        {
            canvas.drawBitmap(GetRotateBitmap(gunBullet[4], gunBulletSpriteAngle)  , gunBulletX[4] + 100, gunBulletY[4] + 25,null);
        }

        UpdateGunBullet(canvas, character, gun);
    }

    //here we draw explosion
    void drawExplosion(Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, explosionX, explosionY,null);
    }

    //here we draw explosion
    void drawExplosionEnemyRocket(Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, explosionEnemyRocketX, enemyRocketExplosionY,null);
    }

    void drawBullet(Canvas canvas, Player character, Bitmap mapBitmap){

        //System.out.println("BULLET X    " + bulletX);
        //System.out.println("BULLET Y    " + bulletY);

        if(isActive)
        {
            canvas.drawBitmap(mapBitmap, bulletX, bulletY,null);
        }


        //Reset bullet position
        if(bulletX > canvas.getWidth() + bulletCurrentBitmap.getWidth())
        {
            isActive = false;
            bulletX = 0;
            bulletY = 0;
            bulletSpeed = 5;
            setPlayerPosition = false;
        }

        Update();

    }

    void UpdateGunBullet(Canvas canvas, Player character, Weapon gun)
    {

        for(int i=0; i<gunBullet.length; i++)
        {
            if(gunBulletActive[i] == true)
            {
                gunBulletX[i] += gunBulletSpeed * 4;

            }

            if(gunBulletX[i] > canvas.getWidth())
            {
                gunBulletX[i] = character.GetPlayerPosX();
                gunBulletY[i] = character.GetPlayerPosY();
                gunBulletActive[i] = false;
            }
        }

            if(gunBulletDelay == 2)
            {
                gunBulletActive[0] = true;
                character.playerGunAmmo -=1;
            }
            if(gunBulletDelay == 4)
            {
                gunBulletActive[1] = true;
                character.playerGunAmmo -=1;
            }
            if(gunBulletDelay == 6)
            {
                gunBulletActive[2] = true;
                character.playerGunAmmo -=1;
            }
            if(gunBulletDelay == 8)
            {
                gunBulletActive[3] = true;
                character.playerGunAmmo -=1;
            }
            if(gunBulletDelay == 10)
            {
                gunBulletActive[4] = true;
                character.playerGunAmmo -=1;
            }


        if(gunBulletDelay > 10)
        {
            gunBulletDelay = 0;
            gunBulletSpeed = 35;
            gun.gunButtonClicked = false;
            character.playerGunShots = false;
        }
        if(gun.gunButtonClicked == true)
        {
            gunBulletDelay+=1;
        }

        //System.out.println("DELAY  " + gunBulletDelay);

    }

    void Update()
    {
        bulletSpeed = bulletSpeed + 2;
        bulletX += bulletSpeed;

    }

    //Update status for enemy Rocket
    void UpdateEnemyRocket(Bitmap enemyRocketCurrentBitmap, Player character, Enemy currentEnemy, Sound audioManager)
    {

        // x <= (bulletX + 250) && y + 100 >= bulletY && y - 100 <= bulletY && projectile.isActive

        if (enemyRocketActive) {
            //IF STATEMENT WIP
            if (enemyRocketX <= (character.GetPlayerPosX() + 150)
                    && enemyRocketY >= character.GetPlayerPosY()  && enemyRocketY < (character.GetPlayerPosY()+100) && !character.isDead)
            {
                enemyRocketActive = false;
                explosionEnemyRocket = true;
                audioManager.PlayExplosion(false);
                enemyRocketSpeed = 25;
                currentEnemy.enemyShots = false;
                currentEnemy.RNForShot(0,50);
                enemyRocketSetInPosition = false;
                enemyRocketX = enemyStartRocketPositionX;
                enemyRocketY = enemyStartRocketPositionY;
                character.CheckPlayerDeath();
            }
        }


        if(enemyRocketActive && enemyRocketX < 0 - enemyRocketCurrentBitmap.getWidth() * 3)
        {

            enemyRocketSpeed = 25;
            currentEnemy.enemyShots = false;

            enemyRocketActive = false;
            currentEnemy.RNForShot(0,50);

            enemyRocketSetInPosition = false;
            enemyRocketX = enemyStartRocketPositionX;
            enemyRocketY = enemyStartRocketPositionY;

        }
        else
        {
            enemyRocketSpeed = enemyRocketSpeed + 2;
            enemyRocketX -= enemyRocketSpeed * 2;
        }

    }

    //Player sprite rotation
    public static Bitmap GetRotateBitmap(Bitmap src, float degree)
    {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.setRotate(degree, src.getWidth()/2, src.getHeight()/2);
        Bitmap rotateBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return rotateBitmap;
    }
}
