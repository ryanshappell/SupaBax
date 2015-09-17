/**
 * 
 */
package crate;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Aaron
 *
 */
public class BodyBuilder {

	/**
	 * 
	 */
	public BodyBuilder() {
	}
	
	/**
	 * 
	 * @param world
	 * @param map
	 */
	public void createBodies(ArrayList<Entity> entities, World world, TiledMap map){
		MapObjects objects;
		
		objects = map.getLayers().get("ground").getObjects();
		for(MapObject object : objects){
			if(object instanceof RectangleMapObject){
				createRectangle(world, (RectangleMapObject) object, 0.5f, 0.4f, 0.6f);
			} else if(object instanceof PolygonMapObject){
				createPolygon(world, (PolygonMapObject) object, 0.5f, 0.4f, 0.6f);
			} else if(object instanceof PolylineMapObject){
				createPolyline(world, (PolylineMapObject) object, 0.5f, 0.4f, 0.6f);
			} else if(object instanceof EllipseMapObject){
				createEllipse(world, (EllipseMapObject) object, 0.5f, 0.4f, 0.6f);
			} else{
				Gdx.app.error("Error", "Invalid map object");
			}
		}
		
		/*
		objects = map.getLayers().get("dynamic").getObjects();
		for(MapObject object : objects){
			RectangleMapObject entity = (RectangleMapObject) object;
			Rectangle position = entity.getRectangle();
			entities.add(new Box(world, new Vector2(position.x / SupaBox.PPM, position.y / SupaBox.PPM), new Vector2(0f, 0f), 1f, 1f));
		}
		*/
	}
	
	/**
	 * 
	 * @param world
	 * @param rectangleObject
	 * @param density
	 * @param friction
	 * @param restitution
	 */
	private void createRectangle(World world, RectangleMapObject rectangleObject, float density, float friction, float restitution){
		Rectangle rect = rectangleObject.getRectangle();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(rect.width / SupaBox.PPM / 2f, rect.height / SupaBox.PPM / 2f);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(new Vector2((rect.x + rect.width / 2f) / SupaBox.PPM, (rect.y + rect.height / 2f) / SupaBox.PPM));
		
		Body body = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
		
		body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	/**
	 * 
	 * @param world
	 * @param polygonObject
	 * @param density
	 * @param friction
	 * @param restitution
	 */
	private void createPolygon(World world, PolygonMapObject polygonObject, float density, float friction, float restitution){
		Polygon polygon = polygonObject.getPolygon();
		PolygonShape shape = new PolygonShape();
		float[] vertices = polygon.getTransformedVertices();
		float[] worldVertices = new float[vertices.length];
		
		for(int i = 0; i < vertices.length; i++){
			worldVertices[i] = vertices[i] / SupaBox.PPM;
		}
		
		shape.set(worldVertices);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		
		Body body = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
		
		body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	/**
	 * 
	 * @param world
	 * @param polylineObject
	 * @param density
	 * @param friction
	 * @param restitution
	 */
	private void createPolyline(World world, PolylineMapObject polylineObject, float density, float friction, float restitution){
		Polyline polyline = polylineObject.getPolyline();
		ChainShape shape = new ChainShape();
		float[] vertices = polyline.getTransformedVertices();
		float[] worldVertices = new float[vertices.length];
		
		for(int i = 0; i < vertices.length; i++){
			worldVertices[i] = vertices[i] / SupaBox.PPM;
		}
		
		shape.createChain(worldVertices);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		
		Body body = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
		
		body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	/**
	 * 
	 * @param world
	 * @param ellipseObject
	 * @param density
	 * @param friction
	 * @param restitution
	 */
	private void createEllipse(World world, EllipseMapObject ellipseObject, float density, float friction, float restitution){
		Ellipse circle = ellipseObject.getEllipse();
		CircleShape shape = new CircleShape();
		shape.setRadius(circle.width / 2f / SupaBox.PPM);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(new Vector2((circle.x + circle.width / 2f) / SupaBox.PPM, (circle.y + circle.width / 2f) / SupaBox.PPM));
		
		Body body = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
		
		body.createFixture(fixtureDef);
		
		shape.dispose();
	}

}
