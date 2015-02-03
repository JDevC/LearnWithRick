package com.oddland.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BorradorMaquiavelico {
    // Estados de borrador maquiavélico
    public enum State{
        IDLE, JUMPING, DYING
    }
    // Atributos del personaje
    private final float size = 0.6f; 					// Tamaño del personaje (0.6f)
    private Vector2     position = new Vector2();		// Vector de posición
    private Vector2 	acceleration = new Vector2();	// Vector de aceleración
    private Vector2 	velocity = new Vector2();		// Vector de velocidad
    private Rectangle   bounds = new Rectangle();		// Área de colisión
    private State		state = State.IDLE;				// Estado del personaje
    private boolean		facingLeft = true;				// Orientación en pantalla del personaje
    private boolean     turnDir = true;
    private float		stateTime = 0;					//
    // CONSTRUCTOR --------------------------------------------------------------------------------------------
    public BorradorMaquiavelico(Vector2 position){
        this.position = position;
        this.bounds.x = position.x;
        this.bounds.y = position.y;
        this.bounds.height = size;
        this.bounds.width = size;
    }
    // MÉTODOS ------------------------------------------------------------------------------------------------
    // Getters
    public float getSize(){ return this.size; }
    public Vector2 getPosition(){ return this.position; }
    public Vector2 getAcceleration(){ return this.acceleration; }
    public Vector2 getVelocity(){ return this.velocity; }
    public Rectangle getBounds(){ return this.bounds; }
    public State getState(){ return this.state; }
    public float getStateTime(){ return this.stateTime; }
    // Setters
    public void setFacingLeft(boolean facingLeft){ this.facingLeft = facingLeft; }
    public void setState(State newState){ this.state = newState; }
    public void setPosition(Vector2 position){
        this.position = position;
        this.bounds.setX(position.x);
        this.bounds.setY(position.y);
    }
    public void setPositionY(float y){ this.position.y = y; }
    public void setAcceleration(Vector2 acceleration){ this.acceleration = acceleration; }
    public void setVelocity(Vector2 velocity){ this.velocity = velocity; }
    public void setBounds(Rectangle bounds){ this.bounds = bounds; }
    public void setStateTime(float stateTime){ this.stateTime = stateTime; }
    // Comprobadores
    public boolean isFacingLeft(){ return this.facingLeft; }
    public boolean isTurnDir(){ return this.turnDir; }
    // Método principal
    public void update(float delta){
        this.stateTime += delta;
        if((stateTime - (int)stateTime != 0) && ((int)stateTime % 2 != 0)){
            if(turnDir){

            }else{

            }
        }else{
            if(turnDir){
                this.turnDir = false;
            }else{
                this.turnDir = true;
            }
            stateTime += 0.1;
        }
    }
}
