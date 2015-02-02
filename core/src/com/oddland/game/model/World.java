package com.oddland.game.model;
// Librerías GDX
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
// Librerías Java
import java.util.ArrayList;
import java.util.List;

public class World{

	Array<Block> terrain = new Array<Block>();				// Array de bloques que conformarán el terreno
	Rick rick;											// Personaje jugable
	Level level;										// Nivel actual
	Array<Rectangle> collisionRects = new Array<Rectangle>();	// Array de colisionadores
	// CONSTRUCTOR --------------------------------------------------------------------------
	public World(){ createDemoWorld(); }
	// MÉTODOS ------------------------------------------------------------------------------
	public Array<Rectangle> getCollisionRects(){ return collisionRects; }	
	public Rick getRick(){ return rick; }	
	public Level getLevel(){ return level; }
	
	/** Devuelve los bloques que se necesita dibujar **/
	public List<Block> getDrawableBlocks(int width, int height){
		// Se toman las posiciones de los bordes izquierdo e inferior de la cámara (zona visualizada en pantalla)
		int x = (int)rick.getPosition().x - width;
		int y = (int)rick.getPosition().y - height;
		// Se limitan sus valores a un mínimo de 0
		if(x < 0){
			x = 0;
		}
		if(y < 0){
			y = 0;
		}
		// Se toman las posiciones de los bordes derecho y superior de la cámara (zona visualizada en pantalla)
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		// Se limitan sus valores a un máximo de la longitud y altura del nivel
		if(x2 > level.getWidth() - 1){
			x2 = level.getWidth() - 1;
		}
		if(y2 > level.getHeight() - 1){
			y2 = level.getHeight() - 1;
		}
		// En base a los par�metros, se devuelven los bloques necesarios para la visualizaci�n
		List<Block> blocks = new ArrayList<Block>();
		Block block;
		for(int col = x; col <= x2; col++){
			for(int row = y; row <= y2; row++){
				block = level.getBlocks()[col][row];
				if(block != null){
					blocks.add(block);
				}
			}
		}
		return blocks;
	}
	
	/** Devuelve los abismos (huecos) que se necesita dibujar **/
	public List<Abyss> getDrawableAbyss(int width, int height){
		int x = (int)rick.getPosition().x - width;
		int y = (int)rick.getPosition().y - height;
		if(x < 0){
			x = 0;
		}
		if(y < 0){
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if(x2 > level.getWidth() - 1){
			x2 = level.getWidth() - 1;
		}
		if(y2 > level.getHeight() - 1){
			y2 = level.getHeight() - 1;
		}
		
		List<Abyss> abisms = new ArrayList<Abyss>();
		Abyss abyss;
		for(int col = x; col <= x2; col++){
			for(int row = y; row <= y2; row++){
				abyss = level.getAbyss()[col][row];
				if(abyss != null){
					abisms.add(abyss);
				}
			}
		}
		return abisms;
	}
	
	/** Devuelve los puntos de control que se necesita dibujar **/
	public List<RespawnPoint> getDrawableChecks(int width, int height){
		int x = (int)rick.getPosition().x - width;
		int y = (int)rick.getPosition().y - height;
		if(x < 0){
			x = 0;
		}
		if(y < 0){
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if(x2 > level.getWidth() - 1){
			x2 = level.getWidth() - 1;
		}
		if(y2 > level.getHeight() - 1){
			y2 = level.getHeight() - 1;
		}
		
		List<RespawnPoint> checks = new ArrayList<RespawnPoint>();
		RespawnPoint check;
		for(int col = x; col <= x2; col++){
			for(int row = y; row <= y2; row++){
				check = level.getChecks()[col][row];
				if(check != null){
					checks.add(check);
				}
			}
		}
		return checks;
	}
	
	/** Devuelve los potes de vida que se necesita dibujar **/
	public List<PotLife> getDrawablePots(int width, int height){
		int x = (int)rick.getPosition().x - width;
		int y = (int)rick.getPosition().y - height;
		if(x < 0){
			x = 0;
		}
		if(y < 0){
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if(x2 > level.getWidth() - 1){
			x2 = level.getWidth() - 1;
		}
		if(y2 > level.getHeight() - 1){
			y2 = level.getHeight() - 1;
		}
		
		List<PotLife> pots = new ArrayList<PotLife>();
		PotLife pot;
		for(int col = x; col <= x2; col++){
			for(int row = y; row <= y2; row++){
				pot = level.getPots()[col][row];
				if(pot != null){
					pots.add(pot);
				}
			}
		}
		return pots;
	}
	
	/** Devuelve las tizas letales que se necesita dibujar **/
	public List<TizaLetal> getDrawableTizas(int width, int height){
		int x = (int)rick.getPosition().x - width;
		int y = (int)rick.getPosition().y - height;
		if(x < 0){
			x = 0;
		}
		if(y < 0){
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if(x2 > level.getWidth() - 1){
			x2 = level.getWidth() - 1;
		}
		if(y2 > level.getHeight() - 1){
			y2 = level.getHeight() - 1;
		}
		
		List<TizaLetal> tizas = new ArrayList<TizaLetal>();
		TizaLetal tiza;
		for(int col = x; col <= x2; col++){
			for(int row = y; row <= y2; row++){
				tiza = level.getTizas()[col][row];
				if(tiza != null){
					tizas.add(tiza);
				}
			}
		}
		return tizas;
	}
	
	// Método principal
	private void createDemoWorld() {
		// Carga el nivel
		level = new Level();
		// Sitúa al jugador en el inicio
		rick = new Rick(new Vector2(level.getSpawnX(), level.getSpawnY()));		
	}
}