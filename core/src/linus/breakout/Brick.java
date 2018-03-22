package linus.breakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Linus on 22.03.2018.
 */

public class Brick{

    protected final Sprite[] sprites = {
            new Sprite(new Texture("block_blue.png")),
            new Sprite(new Texture("block_green.png")),
            new Sprite(new Texture("block_pink.png")),
            new Sprite(new Texture("block_red.png")),
            new Sprite(new Texture("block_yellow.png"))
    };

    protected int status;
    protected int width = 100;
    protected int height = 55;
    protected int x = 0, y = 0;

    public Brick(int status, int x, int y){
        if(status < 0 || status >= sprites.length)
            throw new ArrayIndexOutOfBoundsException(status + " in array of length " + sprites.length);
        this.status = status;
        this.x = x;
        this.y = y;
    }

    public void update(SpriteBatch batch, int ballX, int ballY, Vector2 balLDirection) {
        if(
            ballX >= x &&
            ballY >= y &&
            ballX <= x + width &&
            ballY <= y + height
        ){
            status--;
        }
    }

    public void render(SpriteBatch batch) {
        if(status >= 0)
            sprites[status].draw(batch);
    }
}
