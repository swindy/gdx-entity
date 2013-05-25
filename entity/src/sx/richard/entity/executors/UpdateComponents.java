
package sx.richard.entity.executors;

import sx.richard.entity.AbstractEntity;
import sx.richard.entity.AbstractWorld;
import sx.richard.entity.Component;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;

/** Runs the update method on entity components and systems
 * @author Richard Taylor */
public class UpdateComponents implements EngineTask {
	
	// Warnings are safe here
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute (Engine engine) {
		float delta = 1f / 60f;//FIXME Gdx.graphics.getDeltaTime();
		AbstractWorld world = engine.getWorld();
		for (AbstractEntity entity : world.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.update(delta);
			}
		}
	}
}
