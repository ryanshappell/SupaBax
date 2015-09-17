/**
 * 
 */
package crate;

/**
 * @author Aaron
 * @author Ryan
 *
 */
public abstract class Entity {

	/**
	 * 
	 */
	public Entity(){
	}
	
	/**
	 * Every entity object must have an update method
	 * @param delta
	 */
	public abstract void update(float delta);
	
	/**
	 * Every entity object must have a dispose method
	 */
	public abstract void dispose();

}
