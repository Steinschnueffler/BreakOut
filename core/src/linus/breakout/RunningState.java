package linus.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Linus on 15.03.2018.
 */
public class RunningState extends State{

    protected final Sprite
            backgroundSprite,
            borderLeftSprite,
            borderRightSprite,
            borderTopSprite,
            racketSprite,
            ballSprite;

    protected final int
            racketHeight = 10,
            racketY = 30,
            borderStrength = 6,
            ballSize = 6;

    protected final float
            racketSpeed = 500;

    protected Vector2
        ballDirection = Vector2.Zero;

    private int racketWidth;
    private float halfRacketWidth;

    public RunningState(int useRacketWidth){
        super(175, 236);

        this.racketWidth = useRacketWidth;
        this.halfRacketWidth = racketWidth / 2f;

        backgroundSprite = new Sprite(new Texture("background.png"));
        backgroundSprite.setBounds(0, 0, width, height);

        borderLeftSprite = new Sprite(new Texture("border_left.png"));
        borderLeftSprite.setBounds(0, 0, borderStrength, height - borderStrength);

        borderRightSprite = new Sprite(new Texture("border_right.png"));
        borderRightSprite.setBounds(width - borderStrength, 0, borderStrength, height - borderStrength);

        borderTopSprite = new Sprite(new Texture("border_top.png"));
        borderTopSprite.setBounds(0, height - borderStrength, width, borderStrength);

        racketSprite = new Sprite(new Texture("racket.png"));
        racketSprite.setBounds((width / 2f) - (racketWidth / 2f), racketY, racketWidth, racketHeight);

        ballSprite = new Sprite(new Texture("ball.png"));
        ballSprite.setBounds((width / 2f) - (ballSize - 2f), racketY + racketHeight + (ballSize / 2f), ballSize, ballSize);
    }

    @Override
    public void render(SpriteBatch batch, Camera cam, float delta) {
        updateRacket(delta);
        super.render(batch, cam, delta);
        borderLeftSprite.draw(batch);
        borderRightSprite.draw(batch);
        borderTopSprite.draw(batch);
        racketSprite.draw(batch);
        ballSprite.draw(batch);
    }

    private void updateRacket(float delta){
        if(!Gdx.input.isTouched())
            return;

        float touch = Utils.getInputX(width);
        float old = racketSprite.getX() + halfRacketWidth;
        float newValue;

        if(touch < old)
            newValue = old - (racketSpeed * delta);
        else if(touch > old)
            newValue = old + (racketSpeed * delta);
        else
            return;

        racketSprite.setCenterX(newValue);
    }
}
