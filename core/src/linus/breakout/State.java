package linus.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Linus on 15.03.2018.
 */

public abstract class State{

    protected static final Graphics graphics = Gdx.graphics;

    protected final Sprite backgroundSprite;

    protected final int
            width,
            height;

    protected final float
            halfWidth,
            halfHeight;

    public State(int width, int height){

        this.width = width;
        this.halfWidth = this.width / 2f;
        this.height = height;
        this.halfHeight = this.height / 2f;

        backgroundSprite = new Sprite(new Texture("background.png"));
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(width, height);
    }

    public void nextFrame(SpriteBatch batch, Camera cam, float delta){
        update(cam, delta);
        render(batch);
    }

    public void update(Camera cam, float delta){
        updateCam(cam);
    }

    protected void updateCam(Camera cam){
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.position.set(width / 2f, height / 2f, 0);
    }

    public void render(SpriteBatch batch){
        backgroundSprite.draw(batch);
   }

}
