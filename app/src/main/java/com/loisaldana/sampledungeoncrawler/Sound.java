package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
@SuppressWarnings("deprecation")

public class Sound {

    private static SoundPool soundPool;

    private static int playerRocket;
    private static int explosionPlayer;
    private static int explosionEnemy;
    private static int reload;
    private static int playerLevelUp;
    private static int engineSound;
    private static int impact;
    private static int restart;
    private static int gunShots;
    private static int score;

    public boolean realodIsPlayed = false;
    public boolean impactTrue = false;
    public int impactDelay = 0;

    public Sound(Context context) {
        //SoundPool (int maxStreams, int streamType, int srcQuality)
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        playerRocket = soundPool.load(context, R.raw.bullet, 3);
        explosionPlayer = soundPool.load(context, R.raw.expl, 3);
        explosionEnemy = soundPool.load(context, R.raw.expl, 3);
        reload = soundPool.load(context, R.raw.reload, 4);
        engineSound = soundPool.load(context, R.raw.planeengine, 1);
        playerLevelUp = soundPool.load(context, R.raw.level, 1);
        restart = soundPool.load(context, R.raw.restart, 1);
        gunShots = soundPool.load(context, R.raw.gun, 1);
        score = soundPool.load(context, R.raw.score, 1);
        impact = soundPool.load(context, R.raw.impact, 5);
    }


    public void PlayPlayerRocket() {

        soundPool.play(playerRocket, 1, 1, 1, 0, 1);
    }

    public void PlayExplosion(boolean character) {

        if(character)
        {
            soundPool.play(explosionPlayer, 1, 1, 1, 0, 1);
        }
        if(!character)
        {
            soundPool.play(explosionEnemy, 1, 1, 1, 0, 1);
        }

    }

    public void PlayReload() {
        if (!realodIsPlayed) {
            soundPool.play(reload, 2, 1, 1, 0, 1);
        }
        realodIsPlayed = true;
    }

    public void PlayPlayerMoveUp() {
        soundPool.play(engineSound, 1, 1, 1, 0, 1);
    }

    public void PlayScore()
    {
        soundPool.play(score, 1, 1, 1, 0, 1);
    }

    public void PlayBulletImpact(Enemy enemy)
    {

        impactDelay +=1;
        soundPool.play(impact, 1, 1, 1, 0, 1);

    }

    public  void PlayLevelUp()
    {
        soundPool.play(playerLevelUp, 1, 1, 1, 0, 1);
    }

    public void PlayerRestartButton(Player character)
    {
        if(!character.buttonPlayerAgainIsPressed)
        {
            soundPool.play(restart, 1, 1, 1, 0, 1);
        }

    }

    public void PlayGunShots(Projectile bullet)
    {
        double x = RandomNumber(0,1);

        for(int i=0; i< bullet.gunBulletActive.length; i++)
        {
            if(!bullet.gunBulletActive[i])
            {
                //System.out.println("JUST FOR COUNT");


                if((int)x==0)
                {
                    soundPool.play(gunShots, 0.2f, 1, 1, 0, 1);
                }
                if((int)x==1)
                {
                    soundPool.play(gunShots, 1, 0.2f, 1, 0, 1);
                }
            }

        }


    }

    public void Update(Enemy enemy, Player character)
    {
        if(impactTrue)
        {
           PlayBulletImpact(enemy);
        }
        if(impactDelay > 4)
        {
            impactDelay = 0;
            impactTrue = false;
        }

        if(character.isDead)
        {
            soundPool.stop(engineSound);
        }


    }

    public void Release()
    {
        soundPool.stop(20);
        soundPool.release();
    }

    //Why is this function name so long
    public double RandomNumber(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

}




