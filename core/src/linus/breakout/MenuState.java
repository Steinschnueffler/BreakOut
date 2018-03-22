package linus.breakout;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.List;

import linus.breakout.level.Level1;

/**
 * Created by Linus on 15.03.2018.
 */

public class MenuState extends State {

    private List<RunningState> levels = Arrays.asList(
            new Level1()
    );

    public MenuState(){
        super(200, 500);
    }

    @Override
    public void update(Camera cam, float delta) {
        super.update(cam, delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
