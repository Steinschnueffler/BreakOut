package linus.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Linus on 15.03.2018.
 */
public class RunningState extends State {

    protected final Sprite
            backgroundSprite,
            borderLeftSprite,
            borderRightSprite,
            borderTopSprite,
            racketSprite;

    protected final int
            racketHeight = 25,
            racketY = 30,
            borderStrength = 6;

    protected final int racketWidth;

    public RunningState(int racketWidth){
        super(175, 236);

        this.racketWidth = racketWidth;

        backgroundSprite = new Sprite(new Texture("background.png"));
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setSize(width, height);

        borderLeftSprite = new Sprite(new Texture("border_left.png"));
        borderLeftSprite.setPosition(0, 0);
        borderLeftSprite.setSize(borderStrength, height - borderStrength);

        borderRightSprite = new Sprite(new Texture("border_right.png"));
        borderRightSprite.setPosition(width - borderStrength, 0);
        borderRightSprite.setSize(borderStrength, height - borderStrength);

        borderTopSprite = new Sprite(new Texture("border_top.png"));
        borderTopSprite.setPosition(0, height - borderStrength);
        borderTopSprite.setSize(width, borderStrength);

        racketSprite = new Sprite(new Texture("racket.png"));
        racketSprite.setPosition((width / 2f) - (racketWidth / 2f), racketY);
        racketSprite.setSize(racketWidth, racketHeight);
    }

    @Override
    public void render(SpriteBatch batch, Camera cam, float delta) {
        updateRacket();
        super.render(batch, cam, delta);
        borderLeftSprite.draw(batch);
        borderRightSprite.draw(batch);
        borderTopSprite.draw(batch);
        racketSprite.draw(batch);
    }

    private void updateRacket(){
        racketSprite.setPosition((Gdx.input.getX() - (racketWidth / 2f)) / 10f, racketY);
    }
}
