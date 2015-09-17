/**
 * 
 */
package crate;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Aaron
 *
 */
public class GameScreen implements Screen, InputProcessor {
	private SupaBox game;
	
	private boolean debug = false;
	
	private Box2DDebugRenderer debugRenderer;
	private World world;
	private Array<Body> bodies = new Array<Body>();
	private BodyBuilder bodyBuilder;
	
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();

	/**
	 * 
	 */
	public GameScreen(SupaBox game) {
		this.game = game;
		
		//float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(24, 16, camera);
		viewport.apply();
		
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -9.8f), true);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("crate.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / SupaBox.PPM);
		
		bodyBuilder = new BodyBuilder();
		bodyBuilder.createBodies(entities, world, map);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}
	
	/**
	 * 
	 * @param delta
	 */
	public void update(float delta){
		world.step(delta, 6, 2);
		camera.update();
		
		for(Entity entity : entities){
			entity.update(delta);
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(!debug){
			mapRenderer.setView(camera);
			mapRenderer.render();
			
			game.batch.setProjectionMatrix(camera.combined);
			game.batch.begin();
			world.getBodies(bodies);
			for(Body body : bodies){
				if(body.getUserData() != null && body.getUserData() instanceof Sprite){
					Sprite sprite = (Sprite) body.getUserData();
					sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
					sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					sprite.draw(game.batch);
				}
			}
			game.batch.end();
		} else{
			debugRenderer.render(world, camera.combined);
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		for(Entity entity : entities){
			entity.dispose();
		}
		world.dispose();
		debugRenderer.dispose();
		mapRenderer.dispose();
		map.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.ESCAPE){
			Gdx.app.exit();
		}
		if(keycode == Input.Keys.GRAVE){
			if(debug){
				debug = false;
			} else{
				debug = true;
			}
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
