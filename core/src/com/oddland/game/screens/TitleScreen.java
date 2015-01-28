package com.oddland.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.oddland.game.LearnWithRick;
import com.oddland.game.controller.TitleController;
import com.oddland.game.view.TitleRenderer;

public class TitleScreen implements Screen, InputProcessor{
	// Puntero de la clase principal
	private LearnWithRick lwr;
	// Atributos de la pantalla
	private TitleRenderer	tRenderer;		// Renderizador del menú principal
	private TitleController tController;	// Controlador del menú principal
	private int 			width, height;	// Altura y longitud de pantalla empleadas por los eventos táctiles de Android
	// CONSTRUCTOR
	public TitleScreen(LearnWithRick lwr){
		// Indica la clase principal sobre la que se va a ejecutar
		this.lwr = lwr;
	}

	@Override
	public void show() {
		tRenderer = new TitleRenderer(); 			// true/false = muestra/oculta rejillas de objetos en pantalla (debug)
		tController = new TitleController(lwr);		// Se asigna el controlador al mundo que muestra la pantalla
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		tRenderer.dispose();
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		tRenderer.dispose();
	}

	@Override
	public void render(float delta) {
		// Limpian la pantalla poniéndola en negro
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tRenderer.render();
		// Actualiza el controlador
		tController.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		tRenderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void pause() {
		// TO-DO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TO-DO Auto-generated method stub
		
	}
	// Métodos de control de entradas (InputProcessor) -------------------------------------------------------------------------
	// Se lanza cuando un botón es pulsado
	@Override
	public boolean keyDown(int keycode) {
		// Limita el uso de la función a su versión PC
		if(!Gdx.app.getType().equals(ApplicationType.Desktop)){
			return false;
		}
		if (keycode == Keys.UP) {
			tController.newPressed();
		}
		if (keycode == Keys.DOWN) {
			tController.loadPressed();
		}
		if (keycode == Keys.LEFT) {
			tController.optionPressed();
		}
		if (keycode == Keys.RIGHT) {
			tController.downPressed();
		}
		return true;
	}
	// Se lanza cuando un bot�n pulsado es liberado
	@Override
	public boolean keyUp(int keycode) {
		// Limita el uso de la funci�n a su versi�n PC
		if(!Gdx.app.getType().equals(ApplicationType.Desktop)){
			return false;
		}
		if (keycode == Keys.UP) {
			tController.newReleased();
		}
		if (keycode == Keys.DOWN) {
			tController.loadReleased();
		}
		if (keycode == Keys.LEFT) {
			tController.optionReleased();
		}
		if (keycode == Keys.RIGHT) {
			tController.downReleased();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TO-DO Auto-generated method stub
		return false;
	}
	// Se lanza al detectar un evento táctil (para dispositivos Android)
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
//		System.out.println("X: "+x+"; Y: "+y+"; Xf:"+(x/tRenderer.getPpuX()+"; Yf:"+(y/tRenderer.getPpuY())));
		// Limita el uso de la función a su versión Android
		if (!Gdx.app.getType().equals(ApplicationType.Android)){
			return false;
		}
		
//			if (x < width / 2 && y > height / 2) {
//				controller.leftPressed();
//			}
//			if (x > width / 2 && y > height / 2) {
//				controller.rightPressed();
//			}
		
//		if (x > 178 && x < 461 && y < 280 && y > 240) {
//			tController.newPressed();
//		}
		
		if ((x/tRenderer.getPpuX()) > 2.8 && (x/tRenderer.getPpuX()) < 7.2
				&& (y/tRenderer.getPpuY()) > 2.3 && (y/tRenderer.getPpuY()) < 2.9) {
			tController.newPressed();
		}
		
//		if ((x / renderer.getPpuX()) > 2 && (x / renderer.getPpuX()) < 3
//				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
//			tController.loadPressed();
//		}
//		if ((x / renderer.getPpuX()) > 8 && (x / renderer.getPpuX()) < 9
//				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
//			tController.optionPressed();
//		}
//		if ((x / renderer.getPpuX()) > 8 && (x / renderer.getPpuX()) < 9
//				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
//			tController.downPressed();
//		}
		return true;
	}
	// Se lanza al finalizar detecci�n de un evento t�ctil (para dispositivos Android)
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// Limita el uso uso de la funci�n a su versi�n Android
//		if (!Gdx.app.getType().equals(ApplicationType.Android)){
//			return false;
//		}
		
//			if (x < width / 2 && y > height / 2) {
//				controller.leftReleased();
//			}
//			if (x > width / 2 && y > height / 2) {
//				controller.rightReleased();
//			}
		if ((x/tRenderer.getPpuX()) > 2.8 && (x/tRenderer.getPpuX()) < 7.2
				&& (y/tRenderer.getPpuY()) > 2.3 && (y/tRenderer.getPpuY()) < 2.9) {
			tController.newReleased();
		}
//		if ((x / renderer.getPpuX()) > 1 && (x / renderer.getPpuX()) < 2 
//				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
//			controller.leftReleased();
//		}
//		if ((x / renderer.getPpuX()) > 2 && (x / renderer.getPpuX()) < 3 
//				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
//			controller.rightReleased();
//		}
//		if ((x / renderer.getPpuX()) > 8 && (x / renderer.getPpuX()) < 9 
//				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
//			controller.jumpReleased();
//		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TO-DO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int x, int y) {
		// TO-DO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TO-DO Auto-generated method stub
		return false;
	}
}
