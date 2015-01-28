package com.oddland.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class RespawnPoint {

	public static final float SIZE = 1f;	// Tamaño del punto de control
	Vector2 position = new Vector2();		// Vector de posición
	Rectangle bounds = new Rectangle();		// Área de colisión
	// Constructor
	public RespawnPoint(Vector2 pos) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	// Métodos
	public Vector2 getPosition() { return position; }	
	public Rectangle getBounds() { return bounds; }
}