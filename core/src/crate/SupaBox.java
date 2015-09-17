package crate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SupaBox extends Game {
	SpriteBatch batch;
	
	public GameScreen gameScreen;
	
	public static final float PPM = 32;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this);
		
		this.setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		gameScreen.dispose();
	}
}
