package com.oddland.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PotLife {
	
	public static final float SIZE = 0.5f;				// Constante de medida para las caras del bloque
	private Vector2 position = new Vector2();			// Vector de posición
	private Rectangle bounds = new Rectangle();			// Área de colisión
	// CONSTRUCTOR
	public PotLife(Vector2 pos) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	// MÉTODOS
	public Vector2 getPosition() { return this.position; }
	public Rectangle getBounds() { return this.bounds; }
}