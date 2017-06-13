package com.theopus.level;

import com.theopus.graphics.Texture;
import com.theopus.graphics.VertexArray;
import com.theopus.maths.Matrix4f;
import com.theopus.maths.Vector3f;

public class Tower {

    private Vector3f position = new Vector3f();
    private Matrix4f ml_matrix;

    private static VertexArray mesh;
    private static Texture texture;
    private static float width = 1f, height = 8.0f;

    public static void create(){
        float[] vertices = new float[]{
                0.0f,  0.0f, 0.1f,
                0.0f,  height, 0.1f,
                width, height, 0.1f,
                width, 0.0f, 0.1f
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
        texture = new Texture("res/tower.png");
    }

    public Tower(float x, float y) {
        position.x = x;
        position.y = y;
        ml_matrix = Matrix4f.translate(position);
    }

    public Matrix4f getMl_matrix() {
        return ml_matrix;
    }

    public float getX(){
        return position.x;
    }
    public float getY(){
        return position.y;
    }

    public static VertexArray getMesh(){
        return mesh;
    }

    public static Texture getTexture() {
        return texture;
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }
}
