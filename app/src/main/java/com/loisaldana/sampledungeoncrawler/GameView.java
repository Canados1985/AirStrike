package com.loisaldana.sampledungeoncrawler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

/*
Created by Canados
*/

public class GameView extends View {

    Activity activity;

    Context gameViewContext;
    Canvas canvasRef;
    private int canvasWidth;
    private int canvasHeight;
    boolean gameRun = false;
    Typeface fontFaceLevel;
    Sound soundInst;
    Player character;
    Background bg;
    Weapon weapon;
    Projectile projectile; // creating bullet object
    Projectile enemyRocket;
    Audio audioManager = new Audio();

    Loot loot;

    Enemy enemy;
    Enemy tails;

    public GameView(Context context) {
        super(context);
        gameViewContext = context;

        character = new Player(context);
        weapon = new Weapon(context);
        projectile = new Projectile(context);
        enemyRocket = new Projectile(context);
        loot = new Loot(context);
        bg =  new Background(context);
        enemy = new Enemy(context, true);
        tails = new Enemy(context, false);


     }

    /* Here we can add images that we want to draw. This function also updates */
    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth(); // if we needed to reference to it
        canvasHeight = canvas.getHeight(); // if we needed to reference to it
        canvasRef = canvas;

        //canvas.drawBitmap(GetResizeBitmap(mapBitmap, canvasWidth, canvasHeight), 0, 0, null); // call background image on canvas with resize
        bg.DrawBG(canvas, character);

        if(!character.isDead)
        {
            //Draws and respawns enemy when it reaches the end
            enemy.draw(canvas, character.GetPlayerPosY(), projectile);
            if (tails.getActive()) {
                int temp = (int)tails.RNG(50, canvasHeight-500);
                tails.draw(canvas, temp, projectile);
            }
        }
        loot.DrawLoot(canvas);
        character.onDraw(canvas, enemy, tails, weapon);


        projectile.onDraw(canvas, character, projectile.GetBulletPosX(), projectile.GetBulletPosY());
        enemyRocket.onDraw(canvas, character, character.GetPlayerPosX(), character.GetPlayerPosY());
        weapon.drawButtonBG(canvas, character);
        weapon.drawButtonWeapon(canvas, character, canvasWidth, canvasHeight, character.shotIsReady, character.playerHasCannon);
        weapon.drawGunButton(canvas, character, projectile, canvasWidth, canvasHeight);
        weapon.drawTextButton(canvas, character);
        weapon.DrawFireShot(canvas, character, character.GetPlayerPosX(), character.GetPlayerPosY());
        projectile.drawGunBullet(canvas,character, weapon);

        if(!character.isDead && tails.getActive())
        {
            enemyRocket.DrawEnemyRocket(canvas, character, tails, soundInst);

        }
        if(!character.isDead && !tails.getActive())
        {
            enemyRocket.DrawEnemyRocket(canvas, character, enemy, soundInst);

        }
        if(!gameRun)
        {OnStart(canvas, character); gameRun = true;}

    }

    void OnStart(Canvas canvas, Player character)
    {
        character.soundRef = soundInst;
        character.fontFaceLevel = fontFaceLevel;
        character.PlayerStart();
        projectile.ProjectileStart(character, canvas);
        weapon.WeaponStart(character);
        audioManager.PlayBgTheme(gameViewContext);
        bg.StartBG(canvas, character);
        loot.StartLoot(canvas);
        enemy.Start(projectile);

    }

    //Update function is here
    void Update()
    {



        System.out.println(loot.lootChance);
        System.out.println(loot.lootIsActive);
        if(tails.getActive())
        {
            soundInst.Update(tails, character);
        }
        else
        {
            soundInst.Update(enemy, character);
        }

        audioManager.Update(character);
        //System.out.println("BULLET STATUS    " + bullet.isActive);

        //hitbox check with enemy and tails
        character.enemyPlayerCheck(enemy, enemy.getX(), enemy.getY());
        character.enemyPlayerCheck(tails, tails.getX(), tails.getY());

        //hitbox check with enemy and projectile
        enemy.BulletCheck(projectile.GetBulletPosX(), projectile.GetBulletPosY(), projectile, character, soundInst, loot);
        tails.BulletCheck(projectile.GetBulletPosX(), projectile.GetBulletPosY(), projectile, character, soundInst, loot);

        //check collision for Gun's bullets
        for(int i=0; i<projectile.gunBullet.length; i++)
        {
            enemy.GunBulletCheck(i, canvasWidth, projectile.gunBulletX[i], projectile.gunBulletY[i], projectile, character, soundInst, loot);
            tails.GunBulletCheck(i, canvasWidth, projectile.gunBulletX[i], projectile.gunBulletY[i], projectile, character, soundInst, loot);
        }

        loot.CheckPlayer(character, soundInst);

        if(character.GetPlayerSpeed() < 0 && !character.isDead)
        {
           //
        }

        if(character.playerHasCannon)
        {
            weapon.SetWeaponPosX(character.GetPlayerPosX());
            weapon.SetWeaponPosY(character.GetPlayerPosY());
        }

        if(character.playerGunShots || character.playerShots)
        {
            character.SetPlayerSpeed(character.GetPlayerSpeed() + 1); // imitation player's gravity.
        }
        else
        {
            character.SetPlayerSpeed(character.GetPlayerSpeed() + 2); // imitation player's gravity.
        }

    }

    //If we touch screen we changing player's movement...
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int)event.getX();
        int y = (int)event.getY();


        //Button for launch rocket
        if(x > weapon.buttonX && y > weapon.buttonY && x < weapon.buttonX + weapon.buttonCurrent.getWidth() &&
                y < weapon.buttonY + weapon.buttonCurrent.getHeight() && character.reload >= 360 && !weapon.weaponButtonClicked)
        {
            if(!loot.lootIsActive)
            {
                loot.lootChance = (int)loot.RandomNumber(0,10);
            }

            character.shotIsReady = false;
            weapon.weaponIsReady = false;
            character.playerAmmo -=1;
            character.tempBitmap = 0;
            character.playerSpriteAngle = 0;
            projectile.SetBulletPosX(character.GetPlayerPosX());
            projectile.SetBulletPosY(character.GetPlayerPosY() + 50);
            projectile.isActive = true;
            soundInst.PlayPlayerRocket();
            character.playerShots = true;
            character.reload = 0;
            character.SetPlayerSpeed(2);
            weapon.weaponButtonClicked = true;

            //System.out.println("BUTTON IS PRESSED");
        }

        if(x > weapon.buttonGunX && y > weapon.buttonGunY && x < weapon.buttonGunX + weapon.gunButton.getWidth() &&
                y < weapon.buttonGunY + weapon.gunButton.getHeight() && !weapon.gunButtonClicked && !character.isDead && character.playerGunAmmo > 0)
        {
            if(!loot.lootIsActive)
            {
                loot.lootChance = (int)loot.RandomNumber(0,10);
            }

            soundInst.PlayGunShots(projectile);
            character.SetPlayerSpeed(2);
            for(int i=0; i<projectile.gunBulletActive.length; i++) {


                if(projectile.gunBulletActive[i] == false) {

                    weapon.gunButtonClicked = true;
                    character.playerGunShots = true;
                    character.playerSpriteAngle = 0;
                    for (int j = 0; j < projectile.gunBullet.length; j++) {
                        projectile.gunBulletX[j] = character.GetPlayerPosX();
                        projectile.gunBulletY[j] = character.GetPlayerPosY();
                        projectile.gunBulletSpriteAngle = character.playerSpriteAngle;
                    }
                }
            }

        }

        //Reset Button
        if(x > character.buttonPlayAgainX && y > character.buttonPlayAgainY && x < character.buttonPlayAgainX + character.playAgain.getWidth() &&
               y < character.buttonPlayAgainY + character.playAgain.getHeight() && character.isDead && !character.buttonPlayerAgainIsPressed)
        {
            character.buttonPlayerAgainIsPressed = true; // <--- we need to turn it back to false after
            audioManager.PlayRestart(gameViewContext);
            PlayAgain();
            soundInst.PlayerRestartButton(character);
            audioManager.PlayBgTheme(gameViewContext);


        }

        //Exit Button
        if(x > character.buttonExitX && y > character.buttonExitY && x < character.buttonExitX + character.exit.getWidth() &&
                y < character.buttonExitY + character.exit.getHeight() && character.isDead)
        {
            audioManager.PlayRestart(gameViewContext);
            activity.finish();
            System.exit(0);
            soundInst.Release();
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN && !character.playerShots && !character.playerGunShots)
        {
            character.SetPlayerSpeed(-25);
            character.SetAngleRotation(-20);
            soundInst.PlayPlayerMoveUp();


           /*
            if(y > canvasHeight / 2)
            {
                character.SetPlayerSpeed(20);
                character.SetAngleRotation(20);
            }
            if(y < canvasHeight / 2)
            {

            }
            */

        }
        return true;
    }


    //Reset all
    void PlayAgain() {

        character.ResetAll();
        //bg.StartBG(canvasRef, character);
        tails.ResetAll();
        tails.setActive(false);
        enemy.ResetAll();
        character.buttonPlayerAgainIsPressed = false;

    }
}