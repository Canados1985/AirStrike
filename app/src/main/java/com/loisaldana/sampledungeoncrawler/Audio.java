package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.media.MediaPlayer;

public class Audio {


    MediaPlayer bgTheme;
    MediaPlayer hit;
    MediaPlayer score;
    MediaPlayer restart;

    private boolean releaseAudio = false;

    public void PlayBgTheme(Context context)
    {
        releaseAudio = false;
        if(bgTheme == null)
        {
            bgTheme = MediaPlayer.create(context, R.raw.theme);
        }
        bgTheme.setLooping(true);
        bgTheme.start();
    }

    /*
    public void PlayPlaneEngine(Context context)
    {

        if(planeEngine == null)
        {
            planeEngine = MediaPlayer.create(context, R.raw.planeengine);
        }
        planeEngine.setLooping(false);
        planeEngine.start();

    }

    public void StopPlaneEngine()
    {
        if(planeEngine != null) {
            planeEngine.stop();
        }
    }

    public void PlayPlayerMoveUp(Context context)
    {
        if(playerUP == null)
        {
            playerUP = MediaPlayer.create(context, R.raw.player_up);
        }
        if(playerMoveUp == 3)
        {
            //playerUP.start();
            playerMoveUp = 0;
        }
        playerMoveUp = playerMoveUp + 1;
    }
*/

    public void PlayHit(Context context)
    {
        if(hit == null)
        {
            hit = MediaPlayer.create(context, R.raw.hit);
        }
        hit.start();
    }
/*
    public void PlayScore(Context context)
    {
        if(score == null)
        {
            score = MediaPlayer.create(context, R.raw.score);
        }
        score.setLooping(false);
        score.start();
    }


    public void PlayLevel(Context context)
    {
        if(level == null)
        {
            level = MediaPlayer.create(context, R.raw.level);
        }
        level.setLooping(false);
        level.start();

    }


    public void PlayBullet(Context context)
    {

        if(bullet == null)
        {
            bullet = MediaPlayer.create(context, R.raw.bullet);
        }
        bullet.setLooping(false);
        bullet.start();
    }


    public void PlayReload(Context context)
    {
        if(reload == null)
        {
            reload = MediaPlayer.create(context, R.raw.reload);
        }
        if(!realodIsPlayed)
        {
            reload.setLooping(false);
            reload.start();
            realodIsPlayed = true;
        }

    }
*/

    /*
    public void PlayExplosion(Context context)
    {
        if(explosion == null)
        {
            explosion = MediaPlayer.create(context, R.raw.expl);
        }
        explosion.setLooping(false);
        explosion.start();
    }
*/
    public void PlayRestart(Context context)
    {
        if(restart == null)
        {
            restart = MediaPlayer.create(context, R.raw.restart);
        }
        restart.setLooping(false);
        restart.start();

    }



    public void Update(Player character)
    {

        if(character.isDead && !releaseAudio)
        {
            Stop();
            releaseAudio = true;
        }

    }


    public void Pause()
    {


    }

    public void Stop()
    {

        if(bgTheme != null)
        {
            bgTheme.stop();
            bgTheme.release();
            bgTheme = null;
        }

    }
}


