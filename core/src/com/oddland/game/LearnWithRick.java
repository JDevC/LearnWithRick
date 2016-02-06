package com.oddland.game;
/*
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/
// Our own classes
import com.oddland.game.screens.GameScreen;
import com.oddland.game.screens.TitleScreen;
// GDX library
import com.badlogic.gdx.Game;

public class LearnWithRick extends Game{
    // Screen attributes
    public TitleScreen titleScreen = null;
    public GameScreen gameScreen = null;

    // Screen load method
    @Override
    public void create() {
        titleScreen = new TitleScreen(this);
        this.setScreen(titleScreen);
//		gameScreen = new GameScreen(this);
//		this.setScreen(gameScreen);
    }
    // Memory release method
    @Override
    public void dispose() {
        super.dispose();
        System.out.println("Saliendo del juego....");
//		this.getScreen().dispose();
        if(this.getScreen() instanceof TitleScreen){
            System.out.println("Limpiando title....");
            this.getScreen().dispose();
            titleScreen = null;
            gameScreen = null;
        }else if(this.getScreen() instanceof GameScreen){
            System.out.println("Limpiando game....");
            this.getScreen().dispose();
            gameScreen = null;
            titleScreen = null;
        }
    }
}
