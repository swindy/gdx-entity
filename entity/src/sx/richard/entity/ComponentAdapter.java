
package sx.richard.entity;

import sx.richard.entity.components.Transform2;

import com.badlogic.gdx.graphics.GL20;

/** A basic {@link Component} adapter, does not provide a copy method and
 * requires the {@link Transform2}
 * @author Richard Taylor */
public abstract class ComponentAdapter<T extends Component<T>> extends Component<T> {
	
	@Override
	public void added () {}
	
	@Override
	public void postRender (GL20 gl, Render render) {}
	
	@Override
	public void preRender (GL20 gl, Render render) {}
	
	@Override
	public void removed () {}
	
	@Override
	public void render (GL20 gl, Render render) {}
	
	@Override
	public void started () {}
	
	@Override
	public void update (float delta) {}
	
}
