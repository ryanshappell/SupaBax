/**
 * 
 */
package crate;

/**
 * @author Aaron
 *
 */
public abstract class Entity {

	/**
	 * 
	 */
	public Entity(){
	}
	
	public abstract void update(float delta);
	
	public abstract void dispose();

}
