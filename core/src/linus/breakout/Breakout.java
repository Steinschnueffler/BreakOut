package linus.breakout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import linus.breakout.level.Level1;

public class Breakout extends ApplicationAdapter {

	private static SpriteBatch batch;
	private static Camera cam;
	private static State state;

	@Override
	public void create() {
		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);

		state = new Level1();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		batch.begin();
		state.render(batch, cam, Gdx.graphics.getDeltaTime());
		batch.end();
		batch.flush();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public static void setState(State s){
		state = s;
	}
}
