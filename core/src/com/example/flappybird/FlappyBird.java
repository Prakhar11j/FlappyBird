package com.example.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background, toptube, bottomtube;
	Texture[] birds;
	int flapstate = 0;
	float birdY = 0;
	float velocity = 0;
	int gameState = 0;
	float gap = 400;
	float maxTubeOffset;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numberOfTubes = 10;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("Background flappy.png");
		toptube = new Texture("top tube.png");
		bottomtube = new Texture("bottom tube.png");
		birds = new Texture[2];
		birds[0] = new Texture("Bird1 fb.png");
		birds[1] = new Texture("Bird3 fb.png");
		birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
		maxTubeOffset = Gdx.graphics.getHeight()/2 - gap/2 -100;
		randomGenerator = new Random();
//		tubeX = Gdx.graphics.getWidth()/2 - toptube.getWidth()/2;
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;

		for (int i=0;i<numberOfTubes;i++) {
			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2 - toptube.getWidth()/2 + i*distanceBetweenTubes;
		}


	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState!=0) {

			if(Gdx.input.justTouched()) {
				velocity = -20;
			}

			for (int i=0;i<numberOfTubes;i++) {

				if(tubeX[i] < -toptube.getWidth()) {
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
					tubeX[i] += numberOfTubes * distanceBetweenTubes;
				}
				else {
					tubeX[i] = tubeX[i] - tubeVelocity;
				}
				batch.draw(toptube, tubeX[i] - toptube.getWidth() / 2, Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomtube, tubeX[i] - bottomtube.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeOffset[i]);
			}
			if(birdY>0 || velocity <0) {
				velocity = velocity + (float) 0.5;
				birdY-=velocity;
			}

		}
		else {
			if(Gdx.input.justTouched()) {
				gameState = 1;
			}
		}
		if(flapstate==0)
			flapstate = 1;
		else
			flapstate = 0;

		batch.draw(birds[flapstate], Gdx.graphics.getWidth()/2 - birds[flapstate].getWidth()/2, birdY );
		batch.end();
	}
}
