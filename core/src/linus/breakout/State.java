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

public abstract class State {

    protected static final Graphics graphics = Gdx.graphics;

    protected final Sprite backgroundSprite;

    protected final int width;
    protected final int height;

    public State(int width, int height){

        this.width = width;
        this.height = height;

        backgroundSprite = new Sprite(new Texture("background.png"));
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(width, height);
    }

    public void render(SpriteBatch batch, Camera cam, float delta){
        updateCam(cam);
        backgroundSprite.draw(batch);
   }

   protected void updateCam(Camera cam){
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.position.set(width / 2f, height / 2f, 0);
   }

}
