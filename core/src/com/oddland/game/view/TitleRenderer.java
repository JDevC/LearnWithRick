package com.oddland.game.view;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TitleRenderer {
	
	/** Textos en pantalla **/
//	private BitmapFont deletreador = new BitmapFont();
	
	/** Texturas **/
//	private Texture				background;								// Fondo gráfico de la pantalla
	private TextureRegion 		newGame,								// Botón de Juego Nuevo
								loadGame,								// Botón de Cargar Juego
								options,								// Botón de Opciones
								downloads;								// Botón de Descargar Skins
	private TextureAtlas		title;									// Mapa de texturas de la pantalla de título
	
	private SpriteBatch 		batch; 									// Se encarga del mapeado e impresión de los objetos del juego
	
	/** Dimensiones de la pantalla **/
	private int 				width, height;  						// Anchura y altura de pantalla en pixels
	private float 				ppuX = 0, ppuY = 0;						// pixels por unidad en los ejes X e Y
	
	/** Dimensiones de los objetos en pantalla **/
	private float 				newGameX = 2.8f, newGameY = 4.1f;		// Coordenadas inicio letrero New Game
	private float 				loadGameX = 2.8f, loadGameY = 3.5f;		// Coordenadas inicio letrero Load Game
	private float 				optionsX = 2.8f, optionsY = 2.9f;		// Coordenadas inicio letrero Opciones
	private float 				downloadsX = 2.8f, downloadsY = 2.3f;	// Coordenadas inicio letrero Downloads
	private float				letterSizeX = 4.4f;
	private float				letterSizeY = 0.6f;
	
	// CONSTRUCTOR ------------------------------------------------------------------------------------------
	public TitleRenderer(){
		batch = new SpriteBatch();
		loadTextures();
	}
	// METHODS ----------------------------------------------------------------------------------------------
	// Getters
	public float getPpuX(){ return this.ppuX; }
	public float getPpuY(){ return this.ppuY; }
	public float getNewGameX(){ return this.newGameX; }
	public float getNewGameY(){ return this.newGameY; }
	// (Re)calcula las unidades en píxels cada vez que la pantalla cambia de tamaño
	public void setSize (int w, int h){
		this.width = w;
		this.height = h;
//		System.out.println("Ancho (px): "+width+"; (f): "+(10f*ppuX));
//		System.out.println("Alto: "+height+"; (f): "+(7f*ppuY));
//		System.out.println("Alto: "+(40/ppuY)+"; Ancho: "+(283/ppuX));
		// Se define la relación píxels/unidad para redimensionar las pantallas (sólo una vez)
		if(ppuX==0 && ppuY==0){
			ppuX = (float)width / 10f;
			ppuY = (float)height / 7f;
		}		
	}
	// Carga para cada objeto las texturas pertinentes desde el directorio escogido
	private void loadTextures(){
		// Agrega las texturas de los botones
		title = new TextureAtlas(Gdx.files.internal("images/texturas/title.pack"));
		newGame = title.findRegion("new_game");
		loadGame = title.findRegion("load_game");
		options = title.findRegion("options");
		downloads = title.findRegion("downloads");
		// Define las coordenadas de posición para cada botón

		// Carga del fondo gráfico
//		background = new Texture(Gdx.files.internal("images/background.jpg"));
	}
	// Método principal de la clase, llamado desde el método "render" de la clase "GameScreen"
	public void render(){
		// Comienza el renderizado
		batch.begin();
		// Dibuja los botones
		drawButtons();
		// Finaliza el renderizado
		batch.end();
	}
	// Método de borrado de texturas, llamado desde el método "dispose" de la clase "GameScreen"
	public void dispose(){
//        batch.dispose();
		title.dispose();
//		background.dispose();
	}
	// Dibuja los botones de menú
	public void drawButtons(){
		// Medidas de botones en píxeles: 283 x 40
		
		// Altura ocupada por los botones (unidades) : 4,6f
		
//		batch.draw(newGame, 178, 280, 283, 40);
//		batch.draw(loadGame, 178, 240, 283, 40);
//		batch.draw(options, 178, 200, 283, 40);
//		batch.draw(downloads, 178, 160, 283, 40);
		batch.draw(newGame, newGameX*ppuX, newGameY*ppuY, letterSizeX*ppuX, letterSizeY*ppuY);
		batch.draw(loadGame, loadGameX*ppuX, loadGameY*ppuY, letterSizeX*ppuX, letterSizeY*ppuY);
		batch.draw(options, optionsX*ppuX, optionsY*ppuY, letterSizeX*ppuX, letterSizeY*ppuY);
		batch.draw(downloads, downloadsX*ppuX, downloadsY*ppuY, letterSizeX*ppuX, letterSizeY*ppuY);
	}
}