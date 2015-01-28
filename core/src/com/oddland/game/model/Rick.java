package com.oddland.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rick{
	// Estados de Rick
	public enum State{
		IDLE, WALKING, JUMPING, SHOOTING, DYING
	}
	// Atributos del personaje
	private final float size = 0.8f; 					        // Tamaño del personaje
	private Vector2 	position = new Vector2();		        // Vector de posición
	private Vector2 	acceleration = new Vector2();	        // Vector de aceleración
	private Vector2 	velocity = new Vector2();		        // Vector de velocidad
	private Rectangle 	bounds = new Rectangle();		        // Área de colisión
	private State		state = State.IDLE;				        // Estado del personaje
	private boolean		facingLeft = true;				        // Orientación en pantalla del personaje
	private float		runStateTime = 0,
                        jumpStateTime = 0,
                        fallStateTime = 0;
//	private boolean		longJump = false;				        // Modificador del salto (sin implementar)
	// Atributos de vida
	private int maxLife = 4;							        // Vida máxima del personaje
	private int life = 4;								        // Vida actual del personaje
	// CONSTRUCTOR --------------------------------------------------------------------------------------------
	public Rick(Vector2 position){
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
	public float getRunStateTime(){ return this.runStateTime; }
    public float getJumpStateTime(){ return this.jumpStateTime; }
    public float getFallStateTime(){ return this.fallStateTime; }
	public int getLife(){ return this.life; }
	public int getMaxLife(){ return this.maxLife; }
	// Setters
	public void setFacingLeft(boolean facingLeft){ this.facingLeft = facingLeft; }
	public void setState(State newState){ this.state = newState; }
//	public void setLongJump(boolean longJump){ this.longJump = longJump; }
	public void setPosition(Vector2 position){
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
	}
    public void setPositionX(float x){ this.position.x = x; }
	public void setPositionY(float y){ this.position.y = y; }
	public void setAcceleration(Vector2 acceleration){ this.acceleration = acceleration; }
	public void setVelocity(Vector2 velocity){ this.velocity = velocity; }
	public void setBounds(Rectangle bounds){ this.bounds = bounds; }
	public void setRunStateTime(float runStateTime){ this.runStateTime = runStateTime; }
    public void setJumpStateTime(float jumpStateTime){ this.jumpStateTime = jumpStateTime; }
    public void setFallStateTime(float fallStateTime){ this.fallStateTime = fallStateTime; }
	// Comprobadores
//	public boolean isLongJump(){ return this.longJump; }
	public boolean isFacingLeft(){ return this.facingLeft; }
	// Métodos de vida
	public void decreaseLife(){ this.life--; }
	public void increaseLife(){ this.life++; }
	public void increaseMaxLife(){ this.maxLife += this.maxLife; }
    // Ante colisión con enemigo, sufre un rechazo de 20 veces la velocidad de carrera, y de igual dirección y sentido inverso al de colisión
    public void inverseVelocity(Vector2 vel){
        Vector2 res = vel;
        res.x = res.x * 20; // 10
//        res.y = res.y * 20; // Bug superguay de gigantes de Skyrim, añádelo bajo tu propia responsabilidad.... XD
        res.rotate(180);
        this.setVelocity(res);
    }
	// Método principal
	public void update(float delta){
        if(getState().equals(State.IDLE)){
            this.runStateTime = 0;
            this.jumpStateTime = 0;
            this.fallStateTime = 0;
        }
        if(getState().equals(State.WALKING)){
            this.runStateTime += delta;
        }
        if(getState().equals(State.JUMPING) && getVelocity().y > 0.5){
            this.fallStateTime = 0;
            this.jumpStateTime += delta;
        }else if(getState().equals(State.JUMPING) && getVelocity().y < 0){
            this.jumpStateTime = 0;
            this.fallStateTime += delta;
        }
//        System.out.println("VelX: "+getVelocity().x+"; VelY: "+getVelocity().y);
	}
}