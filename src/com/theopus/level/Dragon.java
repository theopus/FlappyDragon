package com.theopus.level;

import com.theopus.graphics.Shader;
import com.theopus.graphics.Texture;
import com.theopus.graphics.VertexArray;
import com.theopus.input.Input;
import com.theopus.maths.Matrix4f;
import com.theopus.maths.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Dragon {

    private float SIZE = 1.0f;
    private VertexArray mesh;
    private Texture texture;

    private Vector3f position = new Vector3f(0.0f,0.0f, 0.0f);
    private float rot;
    private float deltaY = 0.0f;

    public Dragon() {
        float[] vertices = new float[]{
                -SIZE * 1.1f / 2.0f, -SIZE / 2.0f, 0.2f,
                -SIZE * 1.1f/ 2.0f,  SIZE / 2.0f, 0.2f,
                 SIZE * 1.1f/ 2.0f,  SIZE / 2.0f, 0.2f,
                 SIZE * 1.1f/ 2.0f, -SIZE / 2.0f, 0.2f
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

        mesh = new VertexArray(vertices, indices, tcs);
        texture = new Texture("res/vchmp.png");
    }

    public void update(){

        position.y -= deltaY;
        if (Input.isKeyDown(GLFW_KEY_SPACE))
            deltaY = -0.15f;
        else
            deltaY += 0.01;
        rot = -deltaY * 90.0f;
//        fall();


    }

    public void fall(){
        deltaY  = -0.15f;
    }

    public void render(){
        Shader.DRAGON.enable();
        Shader.DRAGON.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
        texture.bind();
        mesh.render();
        texture.unbind();
        Shader.DRAGON.disable();
    }

    public float getY(){
        return position.y;
    }

    public float getSIZE() {
        return SIZE;
    }

    public float getX() {
        return position.x;
    }
}
