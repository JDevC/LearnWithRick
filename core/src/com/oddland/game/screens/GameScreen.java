package com.oddland.game.screens;
// Paquetes propios
import com.oddland.game.LearnWithRick;
import com.oddland.game.model.World;
import com.oddland.game.view.WorldRenderer;
import com.oddland.game.controller.RickController;
// Librer�as GDX
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Application.ApplicationType;

public class GameScreen implements Screen, InputProcessor{
	
	// Puntero de la clase principal
	private LearnWithRick lwr;

	private World 			world;			// Mundo que muestra la pantalla
	private WorldRenderer 	renderer;		// Renderizador de la pantalla
	private RickController	controller;		// Controlador del personaje
//	private OrthographicCamera cam;			// Cámara de la pantalla
	private int 			width, height;	// Altura y longitud de pantalla empleadas por los eventos táctiles de Android
	// CONSTRUCTOR
	public GameScreen(LearnWithRick lwr){
		this.lwr = lwr;
	}
	// Métodos de la pantalla de juego (Screen) ------------------------------------------------------------------
	@Override
	public void render(float delta){
		// Limpian la pantalla poniéndola en negro
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Actualiza el controlador
        controller.update(delta);
		// Llamada al método render() de la clase WorldRenderer
		renderer.render();
		/* Nota: El parámetro "delta" no lo generamos nosotros, ni le asignamos valor.
		 * Se trata de un parámetro variable en función de los fps que sea capaz de procesar
		 * el dispositivo en el que estemos ejecutando el juego, de forma que funcione a 
		 * la misma velocidad en ambos. La única diferencia perceptible será una mayor
		 * fluidez de movimiento en dispositivos con mayor capacidad de procesamiento
		 *  
		 * */
	}
	// Redimensiona la pantalla (sólo en versión de escritorio)
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	// Método llamado cuando LearnWithRick activa la pantalla actual
	public void show() {
		world = /* Brave */new World(); 			// Eddie approves it
		renderer = new WorldRenderer(world, true); // true/false = muestra/oculta rejillas de objetos en pantalla (debug)
		controller = new RickController(world);		// Se asigna el controlador al mundo que muestra la pantalla
		Gdx.input.setInputProcessor(this);
		world.getLevel().getMusic().play();			// Se inicia la reproducción del fondo musical
	}

	@Override
	// Método llamado cuando LearnWithRick activa otra pantalla
	public void hide() {
		Gdx.input.setInputProcessor(null);
		world.getLevel().getMusic().stop();
		world.getLevel().getMusic().dispose();
	}

	@Override
	public void pause() {
		// TO-DO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TO-DO Auto-generated method stub
	}
	// Método llamado cuando se abandona la aplicación
	@Override
	public void dispose() {
		// Liberamos la memoria ocupada por el InputProcessor
		Gdx.input.setInputProcessor(null);
		// Liberamos la memoria ocupada por el renderizador
		renderer.dispose();
		// Detenemos la música y liberamos la memoria ocupada
		world.getLevel().getMusic().stop();
		world.getLevel().getMusic().dispose();
	}
	// Métodos de control de entradas (InputProcessor) -------------------------------------------------------------------------
	// Se lanza cuando un botón es pulsado
	@Override
	public boolean keyDown(int keycode) {
		// Limita el uso de la función a su versión PC
		if(!Gdx.app.getType().equals(ApplicationType.Desktop)){
			return false;
		}
		if (keycode == Keys.LEFT) {
			controller.leftPressed();
		}
		if (keycode == Keys.RIGHT) {
			controller.rightPressed();
		}
		if (keycode == Keys.CONTROL_RIGHT) {
			controller.jumpPressed();
		}
		if (keycode == Keys.Z) {
            controller.firePressed();
		}
		return true;
	}
	// Se lanza cuando un botón pulsado es liberado
	@Override
	public boolean keyUp(int keycode) {
		// Limita el uso de la función a su versión PC
		if(!Gdx.app.getType().equals(ApplicationType.Desktop)){
			return false;
		}
		if (keycode == Keys.LEFT) {
			controller.leftReleased();
		}
		if (keycode == Keys.RIGHT) {
			controller.rightReleased();
		}
		if (keycode == Keys.CONTROL_RIGHT) {
			controller.jumpReleased();
		}
        if (keycode == Keys.Z) {
            controller.fireReleased();
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
		// Limita el uso de la función a su versión Android
		if (!Gdx.app.getType().equals(ApplicationType.Android)){
			return false;
		}		
//		if (x < width / 2 && y > height / 2) {
//			controller.leftPressed();
//		}
//		if (x > width / 2 && y > height / 2) {
//			controller.rightPressed();
//		}
		
		if ((x / renderer.getPpuX()) > 1 && (x / renderer.getPpuX()) < 2  
				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
			controller.leftPressed();
		}
		if ((x / renderer.getPpuX()) > 2 && (x / renderer.getPpuX()) < 3
				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
			controller.rightPressed();
		}
		if ((x / renderer.getPpuX()) > 8 && (x / renderer.getPpuX()) < 9
				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
			controller.jumpPressed();
		}
        if ((x / renderer.getPpuX()) > 7 && (x / renderer.getPpuX()) < 8
                && (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
            controller.firePressed();
        }
		return true;
	}
	// Se lanza al finalizar detección de un evento táctil (para dispositivos Android)
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// Limita el uso uso de la función a su versión Android
		if (!Gdx.app.getType().equals(ApplicationType.Android)){
			return false;
		}
//		if (x < width / 2 && y > height / 2) {
//			controller.leftReleased();
//		}
//		if (x > width / 2 && y > height / 2) {
//			controller.rightReleased();
//		}
		if ((x / renderer.getPpuX()) > 1 && (x / renderer.getPpuX()) < 2  
				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
			controller.leftReleased();
		}
		if ((x / renderer.getPpuX()) > 2 && (x / renderer.getPpuX()) < 3
				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
			controller.rightReleased();
		}
		if ((x / renderer.getPpuX()) > 8 && (x / renderer.getPpuX()) < 9
				&& (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
			controller.jumpReleased();
		}
        if ((x / renderer.getPpuX()) > 7 && (x / renderer.getPpuX()) < 8
                && (y / renderer.getPpuY()) < 6.5 && (y / renderer.getPpuY()) > 5.5) {
            controller.fireReleased();
        }
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