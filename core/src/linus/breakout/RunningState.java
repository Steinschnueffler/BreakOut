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
            ballSize = 6,
            racketWidth;

    protected final float
            racketSpeed = 750,
            ballSpeed = 5,
            halfBallSize = ballSize / 2f,
            halfRacketWidth;

    protected boolean running = false;

    protected final Vector2
        racketDirection = Vector2.Zero,
        ballDirection = Vector2.Zero;

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
        racketSprite.setBounds(halfWidth - halfRacketWidth, racketY, racketWidth, racketHeight);

        ballSprite = new Sprite(new Texture("ball.png"));
        ballSprite.setBounds(halfWidth - halfBallSize, racketY + racketHeight, ballSize, ballSize);
    }

    @Override
    public void render(SpriteBatch batch, Camera cam, float delta) {
        updateRacket(delta);
        updateBall(delta);
        super.render(batch, cam, delta);
        borderLeftSprite.draw(batch);
        borderRightSprite.draw(batch);
        borderTopSprite.draw(batch);
        racketSprite.draw(batch);
        ballSprite.draw(batch);
    }

    protected void updateRacket(float delta){
        if(!Gdx.input.isTouched())
            return;

        float touch = Utils.getInputX(width);
        float old = racketSprite.getX() + halfRacketWidth;
        float newValue;

        float speed = racketSpeed * delta;

        if(touch < old){
            float withSpeed = old - speed;
            newValue = withSpeed > touch ? withSpeed : touch;
            racketDirection.set(-1, 0);
        }else if(touch > old){
            float withSpeed = old + speed;
            newValue = withSpeed < touch ? withSpeed : touch;
            racketDirection.set(1, 0);
        }else {
            return;
        }

        if(!running){
            ballDirection.y = MathUtils.random(1);
            ballDirection.x = MathUtils.random(-1, 1);
            running = true;
        }

        racketSprite.setCenterX(newValue);
    }

    protected void updateBall(float delta){
        if(!running)
            return;
    }
}
