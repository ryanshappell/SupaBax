package crate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 * @author Aaron
 * @author Ryan
 *
 */

public class SupaBax extends Game {
	SpriteBatch batch;
	
	//Screens
	public GameScreen gameScreen;
	
	//Pixels to meters conversion
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
