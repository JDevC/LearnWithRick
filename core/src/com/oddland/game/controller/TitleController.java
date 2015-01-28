package com.oddland.game.controller;

import java.util.HashMap;
import java.util.Map;

import com.oddland.game.LearnWithRick;
import com.oddland.game.screens.GameScreen;

public class TitleController {
	// Clae principal del juego
	private LearnWithRick lwr;
	// Definici�n de los controles de Rick
	enum Keys{
		NEW_GAME, LOAD, OPTIONS, DOWNLOADS
	}

	static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
	static {
		keys.put(Keys.NEW_GAME, false);
		keys.put(Keys.LOAD, false);
		keys.put(Keys.OPTIONS, false);
		keys.put(Keys.DOWNLOADS, false);
	};
	// CONSTRUCTOR ------------------------------------------------------------------------------------------------------------------------
	public TitleController(LearnWithRick lwr){
		this.lwr = lwr;
	}
	// ** Key presses and touches **************** //
	public void newPressed(){
		keys.get(keys.put(Keys.NEW_GAME, true));
	}
	public void newReleased(){
		keys.get(keys.put(Keys.NEW_GAME, false));
	}
	public void loadPressed(){
		keys.get(keys.put(Keys.LOAD, true));
	}
	public void loadReleased(){
		keys.get(keys.put(Keys.LOAD, false));
	}
	public void optionPressed(){
		keys.get(keys.put(Keys.OPTIONS, true));
	}
	public void optionReleased(){
		keys.get(keys.put(Keys.OPTIONS, false));
	}
	public void downPressed(){
		keys.get(keys.put(Keys.DOWNLOADS, false));
	}
	public void downReleased(){
		keys.get(keys.put(Keys.DOWNLOADS, false));
	}
	/** Método principal del controlador **/
	public void update(float delta){
		// Controla eventos de pulsación de botones
		processInput();
	}
	/** Activa la pantalla pertinente en función de los controles **/
	private boolean processInput(){
		if(keys.get(Keys.NEW_GAME)){
			System.out.println("Pasando a pantalla de juego...");
			lwr.gameScreen = new GameScreen(lwr);
			lwr.setScreen(lwr.gameScreen);
		}
		if(keys.get(Keys.LOAD)){

		}
		if (keys.get(Keys.OPTIONS)){

		}
		if (keys.get(Keys.DOWNLOADS)){

		}
		return false;
	}
}
