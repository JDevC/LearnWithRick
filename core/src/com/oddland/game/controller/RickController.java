package com.oddland.game.controller;
// Librerías propias
import com.oddland.game.model.Abyss;
import com.oddland.game.model.PotLife;
import com.oddland.game.model.RespawnPoint;
import com.oddland.game.model.Rick;
import com.oddland.game.model.TizaLetal;
import com.oddland.game.model.Rick.State;
import com.oddland.game.model.World;
import com.oddland.game.model.Block;
// Librerías GDX
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Array;
import com.oddland.game.view.WorldRenderer;
// Librerías Java
import java.util.HashMap;
import java.util.Map;

public class RickController{
	// Definición de los controles de Rick
	enum Keys{
		LEFT, RIGHT, JUMP, FIRE
	}
	
	private static final long 	LONG_JUMP_PRESS = 150l; // Duración máxima de salto, medida en milisegundos. Original: 150 ms.
	private static final float 	ACCELERATION = 20f;     // Aceleración al correr
	private static final float 	GRAVITY = -20f; // -20f // Aceleración gravitacional
	private static final float 	MAX_JUMP_SPEED	= 7f;   // Velocidad máxima de salto
	private static final float 	DAMP = 0.90f;           // Suavizado de movimiento para el frenado
	private static final float 	MAX_VEL = 4f;           // Velocidad máxima al correr
	private World               world;
	private Rick                rick;
	private long	            jumpPressedTime;
	private boolean             jumpingPressed;
	private boolean             grounded = false;
	// Pila de colisionadores cercanos a la posición de Rick
	private Pool<Rectangle>     rectPool = new Pool<Rectangle>(){
		@Override
		protected Rectangle newObject(){
			return new Rectangle();
		}
	};
	static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
	}
	// Array de colisionadores (bloques) con los que Rick puede chocar
	private Array<Block> collidableBlocks = new Array<Block>();
	// Array de colisionadores (abismos) con los que Rick puede chocar
	private Array<Abyss> collidableAbisms = new Array<Abyss>();
	// Array de puntos de control con los que Rick puede chocar
	private Array<RespawnPoint> collidableChecks = new Array<RespawnPoint>();
	// Array de potes de vida con los que Rick puede chocar
	private Array<PotLife> collidablePots = new Array<PotLife>();
	// Array de tizas letales con las que Rick puede chocar
	private Array<TizaLetal> collidableTizas = new Array<TizaLetal>();
	// CONSTRUCTOR ------------------------------------------------------------------------------------------------------------------------
	public RickController(World world){
		this.world = world;
		this.rick = this.world.getRick();
	}
    // METODOS ----------------------------------------------------------------------------------------------------------------------------
    // getters
//    public Array<TizaLetal> getCollidableTizas(){
//        return this.collidableTizas;
//    }
	// ** Key presses and touches **************** //
	public void leftPressed(){
		keys.get(keys.put(Keys.LEFT, true));
	}
	public void rightPressed(){
		keys.get(keys.put(Keys.RIGHT, true));
	}
	public void jumpPressed(){
		keys.get(keys.put(Keys.JUMP, true));
	}
	public void firePressed(){
        keys.get(keys.put(Keys.FIRE, true));
	}
	public void leftReleased(){
		keys.get(keys.put(Keys.LEFT, false));
	}
	public void rightReleased(){
		keys.get(keys.put(Keys.RIGHT, false));
	}
	public void jumpReleased(){
		keys.get(keys.put(Keys.JUMP, false));
		jumpingPressed = false;
	}
	public void fireReleased(){
		keys.get(keys.put(Keys.FIRE, false));
	}
	/** Método principal del controlador **/
	public void update(float delta){
		// Chequeo del estado actual de Rick
		processInput();
		// Si Rick está en el suelo y su estado es JUMPING...
		if (grounded && rick.getState().equals(State.JUMPING)){
			rick.setState(State.IDLE);
		}
		// Asignamos un valor de aceleración gravitacional
		rick.getAcceleration().y = GRAVITY;
		// Convierte dicha aceleración a frames
		rick.getAcceleration().scl(delta); // <-------------------------------------------- rick.getAcceleration().mul(delta);
		// Se aplica la aceleración para aumentar su velocidad
		rick.getVelocity().add(rick.getAcceleration().x, rick.getAcceleration().y);
		// Comprueba si hay colisión dependiendo de la velocidad de Rick
		checkCollisionWithBlocks(delta);
		// Agrega el frenado suavizado
		rick.getVelocity().x *= DAMP;
        /* Suprime el efecto de damping en velocidades inapreciables. Esto
         * logra que el vector de velocidad en x se inicialice siempre desde cero,
         * con la intención de prevenir errores de ejecución
         */
        if(Math.abs(rick.getVelocity().x) < 0.002 && rick.getState()==State.IDLE){
            rick.getVelocity().x = 0;
        }
        //
		/* Limita el aumento de velocidad al máximo establecido
		 * (cosa que deberían hacer más conductores... pero eso es otra historia.
		 * Ayer estuvieron a punto de atropellarme y estoy algo afectado. Y su coche
		 * ahora también, tras cierta visita que le hice y... uy, espera, que estábamos
		 * programando. Olvida lo que he dicho) */
		if(rick.getVelocity().x > MAX_VEL){
			rick.getVelocity().x = MAX_VEL;
		}
		if(rick.getVelocity().x < -MAX_VEL){
			rick.getVelocity().x = -MAX_VEL;
		}
        for(TizaLetal t: world.getDrawableTizas((int)WorldRenderer.CAMERA_WIDTH,(int)WorldRenderer.CAMERA_HEIGHT)){
            if(t.isTurnDir()){
                t.getAcceleration().x = 10f;
            }else{
                t.getAcceleration().x = -10f;
            }
            t.getAcceleration().y = 0f;

            System.out.print("AB: "+t.getAcceleration()+"; ");
            t.getAcceleration().scl(delta);
            System.out.println("AA: "+t.getAcceleration());

            t.getVelocity().add(t.getAcceleration().x, t.getAcceleration().y);
            t.getVelocity().scl(delta);
            t.getPosition().add(t.getVelocity());
            t.getBounds().x = t.getPosition().x;
            t.getBounds().y = t.getPosition().y;
            // Devuelve el formato original al vector de velocidad (not in frame time)
            t.getVelocity().scl(1 / delta);
            if(t.getVelocity().x > 2f){
                t.getVelocity().x = 2f;
            }
            if(t.getVelocity().x < -2f){
                t.getVelocity().x = -2f;
            }
            t.update(delta);
            System.out.println("X: "+t.getPosition().x+"; Y: "+t.getPosition().y);
        }
		// Actualiza el estado de Rick
		rick.update(delta);
	}
	/** Comprobación de colisiones **/
	private void checkCollisionWithBlocks(float delta){
		// Escala su velocidad a frames
		rick.getVelocity().scl(delta); // <------------------------------------------------ Anteriormente: rick.getVelocity().mul(delta);
		// Obtiene los rectángulos colisionadores
		Rectangle rickRect = rectPool.obtain();
		// Ajusta el rectángulo a los vectores de Rick
		rickRect.set(rick.getBounds().x, rick.getBounds().y, rick.getBounds().width, rick.getBounds().height);
		int startX, endX;
		int startY = (int) rick.getBounds().y;
		int endY = (int) (rick.getBounds().y + rick.getBounds().height);
		// Comprobamos el movimiento horizontal en el eje X
		/* Si Rick mira hacia la izquierda, comprobamos si choca con el bloque de su izquierda.
		 * Lo mismo si mira hacia la derecha */
		if(rick.getVelocity().x < 0){
			startX = endX = (int) Math.floor(rick.getBounds().x + rick.getVelocity().x);
		}else{
			startX = endX = (int) Math.floor(rick.getBounds().x + rick.getBounds().width + rick.getVelocity().x);
		}
		// Obtiene los objetos con los que podría colisionar Rick
		populateColliders(startX, startY, endX, endY);
		// Simula el movimiento de Rick en el eje X
		rickRect.x += rick.getVelocity().x;
		// clear collision boxes in world
		world.getCollisionRects().clear();
		// Si Rick colisiona con bloques, deja su velocidad horizontal a 0
		for(Block block : collidableBlocks){
			if(block == null || !block.isCollidable()){ continue; }
			if(rickRect.overlaps(block.getBounds())){
				rick.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		// Si Rick colisiona con spawner, guarda su posición en el nivel
		for(RespawnPoint check : collidableChecks){
			if(check == null){ continue; }
			if(rickRect.overlaps(check.getBounds())){
				world.getLevel().setSpawn((int)rick.getPosition().x, (int)rick.getPosition().y);
				world.getCollisionRects().add(check.getBounds());
				break;
			}
		}
		// Si Rick colisiona con pote de vida, gana un punto de vida
		for(PotLife pot : collidablePots){
			if(pot == null){ continue; }
			if(rickRect.overlaps(pot.getBounds())){
				if(rick.getLife() < rick.getMaxLife()){
					rick.increaseLife();
				}
				world.getLevel().crashPot((int)pot.getPosition().x, (int)pot.getPosition().y);
				world.getCollisionRects().add(pot.getBounds());
				break;
			}
		}
		// Si Rick colisiona con tiza letal, pierde un punto de vida
		for(TizaLetal tiza : collidableTizas){
            if(tiza == null){ continue; }
			if(rickRect.overlaps(tiza.getBounds())){
				if(rick.getLife() > 0){
					rick.decreaseLife();
				}
                rick.inverseVelocity(rick.getVelocity()); // Invierte el vector de velocidad (rechazo ante colisión)
                world.getCollisionRects().add(tiza.getBounds());
				break;
			}
		}
		// Reinicia las posiciones X del colisionador de Rick
		rickRect.x = rick.getPosition().x;
		startX = (int) rick.getBounds().x;
		endX = (int) (rick.getBounds().x + rick.getBounds().width);
		// Se repite el proceso, esta vez en el eje Y
		if (rick.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(rick.getBounds().y + rick.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(rick.getBounds().y + rick.getBounds().height + rick.getVelocity().y);
		}
		populateColliders(startX, startY, endX, endY);
		rickRect.y += rick.getVelocity().y;
		// Comprueba colisiones con bloques
		for(Block block : collidableBlocks) {
			if(block == null || !block.isCollidable()){ continue; }
			if(rickRect.overlaps(block.getBounds())){
				if (rick.getVelocity().y < 0){
					grounded = true;
				}
				rick.getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
			    break;
			}
		}
		// Comprueba colisiones con abismos (huecos)
		for(Abyss abyss : collidableAbisms) {
			if(abyss == null){ continue; }
			if(rickRect.overlaps(abyss.getBounds())){
				// PTR (Pequeño Tumor Reparabugs)
				if(abyss.getPosition().x == 93 && abyss.getPosition().y == 1){
                    rick.getAcceleration().y = 0;
                    rick.getVelocity().y = 0;
					rick.getPosition().x = world.getLevel().getSpawnX();
					rick.getPosition().y = world.getLevel().getSpawnY();
				}else{
				// A partir de aquí, el resto es coherente
					rick.getVelocity().y = 0;
					rick.decreaseLife();
					rick.getPosition().x = world.getLevel().getSpawnX();
					rick.getPosition().y = world.getLevel().getSpawnY();
				}
				world.getCollisionRects().add(abyss.getBounds());
				break;
			}
		}
		// Si Rick colisiona con tiza letal, pierde un punto de vida
		for(TizaLetal tiza : collidableTizas){
			if(tiza == null){ continue; }
			if(rickRect.overlaps(tiza.getBounds())){
				if(rick.getLife() > 0){
					rick.decreaseLife();
				}
                rick.inverseVelocity(rick.getVelocity()); // Invierte el vector de velocidad (rechazo ante colisión)
                world.getCollisionRects().add(tiza.getBounds());
				break;
			}
		}
		// Reinicia la posición Y del colisionador de Rick
		rickRect.y = rick.getPosition().y;
		// Actualiza la posición de Rick
		rick.getPosition().add(rick.getVelocity());
		rick.getBounds().x = rick.getPosition().x;
		rick.getBounds().y = rick.getPosition().y;
		// Devuelve el formato original al vector de velocidad (not in frame time)
		rick.getVelocity().scl(1 / delta); // <-------------------------------------------------------------------- rick.getVelocity().mul(1 / delta);
	}
	/** Rellena el array de colisionadores con los bloques hallados en las coordenadas indicadas **/
	private void populateColliders(int startX, int startY, int endX, int endY){
		collidableBlocks.clear();
		for (int x = startX; x <= endX; x++){
			for (int y = startY; y <= endY; y++){
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()){
					collidableBlocks.add(world.getLevel().getBlock(x, y));
				}
			}
		}
		collidableAbisms.clear();
		for (int x = startX; x <= endX; x++){
			for (int y = startY; y <= endY; y++){
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()){
					collidableAbisms.add(world.getLevel().getAbyss(x, y));
				}
			}
		}
		collidableChecks.clear();
		for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (x >= 0 && x < world.getLevel().getWidth() && y >= 0 && y < world.getLevel().getHeight()) {
                    collidableChecks.add(world.getLevel().getCheck(x, y));
                }
            }
        }
		collidablePots.clear();
		for (int x = startX; x <= endX; x++){
			for (int y = startY; y <= endY; y++){
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()){
					collidablePots.add(world.getLevel().getPot(x, y));
				}
			}
		}
		collidableTizas.clear();
		for (int x = startX; x <= endX; x++){
			for (int y = startY; y <= endY; y++){
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()){
					collidableTizas.add(world.getLevel().getTiza(x, y));
				}
			}
		}
	}
    /** Cambia el estado de Rick en función de los controles **/
	private boolean processInput(){
        if(keys.get(Keys.FIRE)){
            if(!rick.getState().equals(State.SHOOTING)){
                rick.setState(State.SHOOTING);
            }
        }else if(keys.get(Keys.JUMP)){
			if(!rick.getState().equals(State.JUMPING)){
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				rick.setState(State.JUMPING);
				rick.getVelocity().y = MAX_JUMP_SPEED;
				grounded = false;
			}else{
				if(jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)){
					jumpingPressed = false;
				}else{
					if(jumpingPressed){
						rick.getVelocity().y = MAX_JUMP_SPEED;
					}
				}
			}
		}else{
            if (!rick.getState().equals(State.JUMPING)){
                rick.setState(State.IDLE);
            }
            rick.getAcceleration().x = 0;
        }
        if(keys.get(Keys.LEFT)){
			// Tecla izquierda pulsada
			rick.setFacingLeft(true);
			if(!rick.getState().equals(State.JUMPING) && !rick.getState().equals(State.SHOOTING)){
				rick.setState(State.WALKING);
			}
			rick.getAcceleration().x = -ACCELERATION;
		}else if (keys.get(Keys.RIGHT)){
			// Tecla derecha pulsada
			rick.setFacingLeft(false);
            if(!rick.getState().equals(State.JUMPING) && !rick.getState().equals(State.SHOOTING)){
                rick.setState(State.WALKING);
            }
			rick.getAcceleration().x = ACCELERATION;
		}
		return false;
	}
}