package com.oddland.game;
/*
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/
// Paquetes propios
import com.oddland.game.screens.GameScreen;
//import com.oddland.game.screens.TitleScreen;
// Librer�a GDX
import com.badlogic.gdx.Game;
import com.oddland.game.screens.Splash;

public class LearnWithRick extends Game{
    // Atributos de pantalla

    public GameScreen gameScreen = null;
    //public MainMenu mainMenu = null;
    // Método para cargar la pantalla pertinente
    @Override
    public void create() {
        this.setScreen(new Splash());
        //titleScreen = new TitleScreen(this);
        //this.setScreen(titleScreen);
		//gameScreen = new GameScreen(this);
		//this.setScreen(gameScreen);
    }
    // M굯do para liberar memoria al finalizar
    @Override
    public void dispose() {
        super.dispose();
        System.out.println("Saliendo del juego....");
//		this.getScreen().dispose();
        //if(this.getScreen() instanceof TitleScreen){
            //System.out.println("Limpiando title....");
            //this.getScreen().dispose();
            //titleScreen = null;
          //  gameScreen = null;
        //}else

        if(this.getScreen() instanceof GameScreen){
            System.out.println("Limpiando game....");
            this.getScreen().dispose();
            gameScreen = null;
            //titleScreen = null;
        }
    }
}
