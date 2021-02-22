package com.serpiente.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Controlador miControlador;


	@Override
	public void create () {
		batch = new SpriteBatch();

		int anchoPantalla=Gdx.graphics.getWidth();
		int altoPantalla=Gdx.graphics.getHeight();
		int chico = Math.min(anchoPantalla,altoPantalla);
		Gdx.graphics.setWindowedMode(chico,chico);




		miControlador = Controlador.getInstance(anchoPantalla/2,altoPantalla/2,chico/20,chico,anchoPantalla,altoPantalla);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		miControlador.render();
	}

	@Override
	public void dispose () {
		miControlador.dispose();

	}
}