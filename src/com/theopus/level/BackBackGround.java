package com.theopus.level;

import com.theopus.graphics.Texture;
import com.theopus.graphics.VertexArray;

public class BackBackGround {

    private VertexArray mesh;
    private Texture texture;

    public BackBackGround() {

        float[] vertices = new float[]{
                -10.0f, -10.0f * 9.0f / 16.0f, -0.1f,
                -10.0f,  10.0f * 9.0f / 16.0f, -0.1f,
                10.0f,  10.0f * 9.0f / 16.0f, -0.1f,
                10.0f, -10.0f * 9.0f / 16.0f, -0.1f
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
        texture = new Texture("res/sky.png");
    }

    public void render(){
        texture.bind();
        mesh.render();
        texture.unbind();
    }
}
