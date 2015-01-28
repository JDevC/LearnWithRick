package com.oddland.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
	
	public static final float SIZE = 1f;				// Constante de medida para las caras del bloque
	private boolean collidable;							// Propiedad para definir un bloque colisionable o no colisionable
    private Vector2 position = new Vector2();			// Vector de posición
	private Rectangle bounds = new Rectangle();			// Área de colisión
	// Constructor
	public Block(Vector2 pos, boolean collidable) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.collidable = collidable;
	}
	// M�todos
	public Vector2 getPosition() { return this.position; }	
	public Rectangle getBounds() { return this.bounds; }	
	public boolean isCollidable(){ return this.collidable; }
}