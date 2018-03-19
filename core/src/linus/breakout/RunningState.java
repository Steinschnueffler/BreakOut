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

import java.util.concurrent.TimeUnit;

import linus.breakout.level.Level1;

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
            randomFactor = 20,
            racketWidth,
            ballSpeed;

    protected final float
            racketSpeed = 750,
            halfBallSize = ballSize / 2f,
            halfRacketWidth;

    protected final Vector2
        racketDirection = new Vector2(0, 0),
        ballDirection = new Vector2(0, 0),
        vecUp = Vector2.X,
        vecLeft = Vector2.Y;

    public RunningState(int useRacketWidth, int useBallSpeed){
        super(175, 236);

        this.racketWidth = useRacketWidth;
        this.halfRacketWidth = racketWidth / 2f;
        this.ballSpeed = useBallSpeed;

        ballDirection.set(0, -ballSpeed);

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
        ballSprite.setBounds(halfWidth - halfBallSize, halfHeight, ballSize, ballSize);
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
        }else if(touch > old){
            float withSpeed = old + speed;
            newValue = withSpeed < touch ? withSpeed : touch;
        }else {
            return;
        }

        racketDirection.set(touch - newValue, 0);
        racketSprite.setCenterX(newValue);
    }

    protected void updateBall(float delta){
        float x = ballSprite.getX();
        float y = ballSprite.getY();

        if(
                y - ballSize <= racketY &&
                y -ballSize >= racketY - racketHeight &&
                x >= racketSprite.getX() &&
                x <= racketSprite.getX() + racketSprite.getWidth()
            ){
            float angle = ballDirection.angle(vecLeft);
            ballDirection.rotate(360 - angle);
            ballDirection.x += racketDirection.x * 5;
            ballDirection.x += MathUtils.random(-randomFactor, randomFactor);
        }

        if(x <= borderStrength){
            float angle = ballDirection.angle(vecUp);
            ballDirection.rotate(180 - angle);
        }

        if(x >= width - borderStrength){
            float angle = ballDirection.angle(vecUp);
            ballDirection.rotate(180 + angle);
        }

        if(y + ballSize >= height - borderStrength){
            float angle = ballDirection.angle(vecLeft);
            ballDirection.rotate(180 + angle);
        }

        if(y < -ballSize){
            Utils.sleepSeconds(2);
            Breakout.setState(new Level1());
            return;
        }

        float newX = x + ballDirection.x * delta;
        float newY = y + ballDirection.y * delta;
        ballSprite.setPosition(newX, newY);

    }
}
