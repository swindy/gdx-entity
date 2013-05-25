
package sx.richard.entity.test;

import sx.richard.entity.Asset;
import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Entity;
import sx.richard.entity.Scene2;
import sx.richard.entity.World;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.components.graphics.camera.Camera2;
import sx.richard.entity.components.graphics.core.ClearColor;
import sx.richard.entity.components.graphics.gfx2.DrawText;
import sx.richard.entity.components.graphics.gfx2.DrawTexture;
import sx.richard.entity.editor.Editable;
import sx.richard.entity.enginetasks.RenderScene;
import sx.richard.entity.enginetasks.SortRenderLayer;
import sx.richard.entity.enginetasks.SortUpdateLayer;
import sx.richard.entity.enginetasks.UpdateScene;
import sx.richard.entity.entities.FPSEntity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class Main extends ApplicationAdapter {
	
	public class StareAt extends ComponentAdapter<StareAt> {
		
		@Editable(name = "other target", description = "This isn't used yet")
		private Transform2 otherTarget;
		@Editable
		private Transform2 target;
		
		@Override
		public Component<StareAt> copy () {
			return this;
		}
		
		@Override
		public Class<?>[] getDependencies () {
			return new Class<?>[] { Transform2.class };
		}
		
		@Override
		public void update (float delta) {
			get(Transform2.class).rotate(180f * delta);
		}
		
	}
	
	public static void main (String[] args) {
		new LwjglApplication(new Main(), "Test", 320, 320, true);
	}
	
	Engine engine;
	
	@Override
	public void create () {
		engine = new Engine();
		
		Array<EngineTask> engineTasks = new Array<EngineTask>();
		engineTasks.add(new SortUpdateLayer());
		engineTasks.add(new UpdateScene());
		engineTasks.add(new SortRenderLayer());
		engineTasks.add(new RenderScene());
		engine.setEngineTasks(engineTasks);
		
		World world = new World();
		
		Scene2 scene = new Scene2();
		engine.setScene(scene);
		scene.setWorld(world);
		
		Entity camera = new Entity("camera");
		Camera2 camera2 = new Camera2();
		camera.add(camera2);
		world.add(camera);
		scene.setCamera(camera2);
		
		Entity clear = new Entity("clear");
		clear.add(new ClearColor());
		world.add(clear);
		
		Entity target = new Entity("target");
		
		Entity viewer = new Entity("viewer");
		StareAt stareAt = new StareAt();
		stareAt.target = target.get(Transform2.class);
		viewer.add(stareAt);
		
		Asset arrow = new Asset("arrow.png", Texture.class);
		DrawTexture drawTexture = new DrawTexture(arrow);
		viewer.add(drawTexture);
		
		Asset text = new Asset("debug.fnt", BitmapFont.class);
		DrawText drawText = new DrawText(text, "FPS!");
		drawText.color = Color.RED;
		viewer.add(drawText);
		
		world.add(target);
		world.add(viewer);
		
		world.add(FPSEntity.create("fps"));
		
		Engine.debug = true;
		
		// validate the entities
		Array<Entity> invalidEntities = world.getInvalidEntities();
		if (invalidEntities.size > 0) {
			System.out.println("invalid entites! size=" + invalidEntities.size);
			for (Entity entity : invalidEntities) {
				System.out.println("\tinvalid entity=" + entity);
			}
		}
		
	}
	
	@Override
	public void render () {
		engine.run();
	}
}
