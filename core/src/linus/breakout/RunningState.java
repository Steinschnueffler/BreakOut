package linus.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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
            randomFactor = 10,
            racketWidth,
            racketSpeed = 750,
            slowRacketSpeed = 50,
            ballSpeed,
            depthTimeout = 100;

    protected final float
            halfBallSize = ballSize / 2f,
            halfRacketWidth;

    protected final Vector2
        racketDirection = new Vector2(0, 0),
        ballDirection = new Vector2(0, 0),
        vecUp = new Vector2(1, 0),
        vecRight = new Vector2(0, 1);

    protected int depthFrameCounter = 0;

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
    public void update(Camera cam, float delta) {
        super.update(cam, delta);
        updateRacket(delta);
        updateBall(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
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
        float oldSlow = racketDirection.x;
        float newValue;
        float newSlowValue;
        float speed = racketSpeed * delta;
        float slowSpeed = slowRacketSpeed * delta;

        if(touch < old){
            float withSpeed = old - speed;
            newValue = withSpeed > touch ? withSpeed : touch;

            float withSlowSpeed = oldSlow - slowSpeed;
            newSlowValue = withSlowSpeed > touch ? withSlowSpeed : touch;
        }else if(touch > old){
            float withSpeed = old + speed;
            newValue = withSpeed < touch ? withSpeed : touch;

            float withSlowSpeed = oldSlow + slowSpeed;
            newSlowValue = withSlowSpeed < touch ? withSlowSpeed : touch;

        }else {
            return;
        }

        racketDirection.x = newSlowValue;
        racketSprite.setCenterX(newValue);
    }

    protected void updateBall(float delta){

        if(depthFrameCounter > 0){
            if(++depthFrameCounter == depthTimeout)
                Breakout.setState(new Level1());
            return;
        }

        float x = ballSprite.getX();
        float y = ballSprite.getY();

        //racket
        if(
                y - ballSize <= racketY &&
                y -ballSize >= racketY - racketHeight &&
                x >= racketSprite.getX() &&
                x <= racketSprite.getX() + racketSprite.getWidth()
            ){

            float rotation = 180;
            float angle = ballDirection.angle(vecRight) * -1;

            if(ballDirection.x < 0)
                rotation = 180 - 2 * angle;
            else if(ballDirection.x > 0)
                rotation = 180 + 2 * angle;

            ballDirection.rotate(rotation);
            ballDirection.x += racketDirection.x;
            ballDirection.x += MathUtils.random(-randomFactor, +randomFactor);
        }

        //left border
        if(x <= borderStrength){
            float angle = ballDirection.angle(vecUp);
            ballDirection.rotate(180 + 2 * angle);
            ballDirection.y += MathUtils.random(-randomFactor, +randomFactor);
        }

        //right border
        if(x + ballSize >= width - borderStrength){
            float angle = ballDirection.angle(vecUp);
            ballDirection.rotate(180 + 2 * angle);
            ballDirection.y += MathUtils.random(-randomFactor, +randomFactor);
        }

        //top border
        if(y + ballSize >= height - borderStrength){
            float angle = ballDirection.angle(vecRight);
            ballDirection.rotate(180 + 2 * angle);
            ballDirection.x += MathUtils.random(-randomFactor, +randomFactor);
        }

        //game finish
        if(y < -ballSize){
            depthFrameCounter = 1;
        }

        float newX = x + ballDirection.x * delta;
        float newY = y + ballDirection.y * delta;
        ballSprite.setPosition(newX, newY);

    }
}
