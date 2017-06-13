package com.theopus.level;


import com.theopus.graphics.Shader;
import com.theopus.graphics.Texture;
import com.theopus.graphics.VertexArray;
import com.theopus.input.Input;
import com.theopus.maths.Matrix4f;
import com.theopus.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

/**
 * Created by theopus on 29.04.2017.
 */
public class Level {

	private VertexArray mesh, fade;
	private Texture texture;

	private int xScroll;
	private int map = 0;

	private Dragon dragon;
	private Tower[] towers = new Tower[5*2];
	private BackBackGround backBackGround;

	private Random random = new Random();

	private int index = 0;

	private float OFFSET = 5.0f;
	private boolean control = true;

	private float time = 0.0f;
	private boolean reset;

	public Level() {
		float[] vertices = new float[]{
				-10.0f, -10.0f * 9.0f / 16.0f, -0.2f,
				-10.0f,  10.0f * 9.0f / 16.0f, -0.2f,
				0.0f,  10.0f * 9.0f / 16.0f, -0.2f,
				0.0f, -10.0f * 9.0f / 16.0f, -0.2f
		};

		byte[] indices = new byte[]{
				0, 1, 2,
				2, 3, 0
		};

		float[] tcs = new float[]{
				0, 1,
				0, 0,
				1, 0,
				1, 1
		};

		fade = new VertexArray(6);
		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/sky.png");
		dragon = new Dragon();
//		backBackGround = new backBackGround();
		generateTowers();
	}

	public void update(){
		time += 0.01f;

		if (control) {
			xScroll--;
			if (-xScroll % 335 == 0) {
				System.out.println("map " + map);
				System.out.println("xScroll " + xScroll);
				map++;
			}
			if (-xScroll > 250 && -xScroll % 120 == 0) {
				updateTowers();
			}
		}


		dragon.update();

		if (colision() && control)
		{
			System.out.println("LOOSE");
			dragon.fall();
			control = false;
		}

		if (!control && Input.isKeyDown(GLFW.GLFW_KEY_SPACE)){
			reset = true;
		}

	}

	private void updateTowers(){
		towers[index % 10] = new Tower(OFFSET + index * 3.0f, 4.0f * random.nextFloat());
		towers[(index + 1) % 10] = new Tower(towers[index % 10].getX(),towers[index % 10].getY() - 12.0f);
		index += 2;


	}
	private void generateTowers (){
		Tower.create();
		for (int i = 0; i < towers.length; i += 2) {
			towers[i] = new Tower(OFFSET + index * 3.0f, 4.0f * random.nextFloat());
			towers[i+1] = new Tower(towers[i].getX(),towers[i].getY() - 12.0f);
			index += 2;
		}
	}

	private void renderTowers(){
		Shader.TOWER.enable();
		Shader.TOWER.setUniform2f("dragon", 0, dragon.getY());
		Shader.TOWER.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.05f,0.0f,0.0f)));
		Tower.getTexture().bind();
		Tower.getMesh().bind();

		for (int i = 0; i < 5 * 2; i++) {
			Shader.TOWER.setUniformMat4f("ml_matrix", towers[i].getMl_matrix());
			Shader.TOWER.setUniform1i("top", i % 2 == 0 ? 1 : 0);
			Tower.getMesh().draw();
		}

		Tower.getTexture().unbind();
		Tower.getMesh().unbind();
	}

	public boolean colision(){
		if (dragon.getY() > 10.0f * 9.0f / 16.0f || dragon.getY() < -10.0f * 9.0f / 16.0f)
			return true;

		for (int i = 0; i < 5 * 2; i++) {
			float drX = -xScroll * 0.05f;
			float drY = dragon.getY();
			float tX = towers[i].getX();
			float tY = towers[i].getY();

			float drx0 = drX - dragon.getSIZE()/2;
			float drx1 = drX + dragon.getSIZE()/2;
			float dry0 = drY - dragon.getSIZE()/2;
			float dry1 = drY + dragon.getSIZE()/2;

			float tx0 = tX;
			float tx1 = tX + Tower.getWidth();
			float ty0 = tY;
			float ty1 = tY + Tower.getHeight();

			if (drx1 > tx0 && drx0 < tx1) {
				if (dry1 > ty0 && dry0 < ty1) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isGameOver(){
		return reset;
	}

	public void render(){

		texture.bind();
		Shader.BG.enable();
		Shader.BG.setUniform2f("dragon", 0, dragon.getY());
		mesh.bind();
		for (int i = map; i < map + 4  ; i++) {
			Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f( i * 10 + xScroll * 0.03f,0.0f,0.0f)));
			mesh.draw();
		}
//		backBackGround.render();
		Shader.BG.disable();
		texture.unbind();

		renderTowers();
		dragon.render();

		Shader.FADE.enable();
		Shader.FADE.setUniform1f("time", time);
		fade.render();
		Shader.FADE.disable();


		Shader.FADE.enable();
		Shader.FADE.setUniform1f("time", time);
		fade.render();
		Shader.FADE.disable();


	}
}