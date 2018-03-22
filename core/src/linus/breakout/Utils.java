package linus.breakout;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.TimeUnit;

/**
 * Created by Linus on 17.03.2018.
 */

public class Utils {

    public static float map(float num, float oldMin, float oldMax, float newMin, float newMax){
        if(oldMax < oldMin)
            throw new IllegalArgumentException("oldMax < oldMin");
        if(newMax < newMin)
            throw new IllegalArgumentException("newMax < newMin");
        float oldDif = oldMax - oldMin;
        float newDif = newMax - newMin;

        float percent = num / oldDif;
        return percent * newDif + newMin;
    }

    public static float getInputX(float width){
        return map(Gdx.input.getX(), 0, Gdx.graphics.getWidth(), 0, width);
    }

}
