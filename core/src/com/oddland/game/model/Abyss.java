package com.oddland.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Abyss {

	public static final float SIZE = 1f;	// Constante de medida para las caras del abismo
	Vector2 position = new Vector2();		// Vector de posición
	Rectangle bounds = new Rectangle();		// Área de colisión
	// Constructor
	public Abyss(Vector2 pos) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	// M�todos
	public Vector2 getPosition() { return position; }	
	public Rectangle getBounds() { return bounds; }
}