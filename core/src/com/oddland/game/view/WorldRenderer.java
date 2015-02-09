package com.oddland.game.view;
// Paquetes propios
import com.oddland.game.model.Abyss;
import com.oddland.game.model.Block;
import com.oddland.game.model.PotLife;
import com.oddland.game.model.RespawnPoint;
import com.oddland.game.model.Rick;
import com.oddland.game.model.Rick.State;
import com.oddland.game.model.TizaLetal;
import com.oddland.game.model.World;
// Librería GDX
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * José Carlos García - 08/10/2014
 * 
 * Esta es la clase encargada de la visualización de todos los elementos en pantalla y de los ajustes de resolución
 * necesarios para cada dispositivo.
 * 
 **/

public class WorldRenderer{
	/** Medidas de la cámara **/
	public static final float	CAMERA_WIDTH = 10f; 				// Longitud de la cámara (96f = longitud total ; 10f = longitud adecuada)
	public static final float	CAMERA_HEIGHT = 7f; 				// Altura de la cámara (23f = altura total ; 7f = altura adecuada)
	private static final float	RUNNING_FRAME_DURATION = 0.08f; 	// Controla el tiempo de duración de los frames en el ciclo de carrera (0.06f)
	private static final float	JUMPING_FRAME_DURATION = 0.2f; 	    // Controla el tiempo de duración de los frames en el ciclo de salto // 0.4f
    private static final float	TIZA_FRAME_DURATION = 0.2f; 	    // Controla el tiempo de duración de los frames en el ciclo de salto // 0.4f
	
	/** Variables del mundo y la cámara **/
	private World 				world;
	private OrthographicCamera 	cam;
	float                       rickPosX, rickPosY;				        // Coordenadas del personaje
	ShapeRenderer               debugRenderer = new ShapeRenderer();	// Renderizador para las áreas de colisión
	
	/** Textos en pantalla **/
//	private BitmapFont deletreador = new BitmapFont();
	
	/** Texturas **/
	private Texture				background;								        // Fondo gráfico de la pantalla
	private TextureRegion 		rickFrame; 								        // TextureRegion de Rick a renderizar en el momento actual
    private TextureRegion       tizaFrame;                                      // TextureRegion de Tiza Letal a renderizar en el momento actual
	private TextureRegion 		blockTexture;							        // Bloques
	private TextureRegion		checkPoint;								        // Puntos de control
	private TextureRegion		potTxt;									        // Potes de vida
//	private TextureRegion		tizaTxt;								        // Tizas letales
	private TextureRegion 		rickIdleLeft, rickIdleRight,                    // Texturas de Rick estático
                                rickShootIdleLeft,                              // Estático disparando hacia la izquierda
                                rickShootIdleRight;			                    // Estático disparando hacia la derecha
	private TextureRegion[] 	walkRightFrames, walkLeftFrames,		        // Rick en carrera
								jumpRightFrames, jumpLeftFrames,		        // Rick en salto
								fallRightFrames, fallLeftFrames,                // Rick en caída
                                shootWalkRightFrames, shootWaltLeftFrames,      // Rick disparando mientras corre
                                shootJumpRightFrames,                           // Rick en disparo y salto simultáneos hacia la derecha
                                shootJumpLeftFrames,                            // Rick en disparo y salto simultáneos hacia la izquierda
                                shootFallRightFrames,                           // Rick en disparo y caída simultáneos hacia la derecha
                                shootFallLeftFrames;                            // Rick en disparo y caída simultáneos hacia la izquierda
    private TextureRegion[]     tizaIzdaTxt, tizaDchaTxt;                       // Tiza en movimiento
	private TextureRegion		arrowLeft, arrowRight, jumpButton, shootButton; // Joypad
	private TextureRegion		lifePoint, lifelessPoint;				        // Puntos de vida
	private TextureAtlas		atlas, joypad;							        // Mapas de texturas del juego
	
	/** Animaciones **/
	private Animation 			walkLeftAnimation, walkRightAnimation;	// Animaciones de Rick en carrera
    private Animation           shootWaltLeftAnimation, shootWalkRightAnimation; //Animaciones de Rick disparando mientras camina
	private Animation 			jumpLeftAnimation, jumpRightAnimation;	// Animaciones de Rick en salto
	private Animation			fallLeftAnimation, fallRightAnimation;	// Animaciones de Rick en caída
    private Animation           shootJumpLeftAnimation, shootJumpRightAnimation; // animaciones de Rick disparando en salto
    private Animation           shootFallLeftAnimation, shootFallRightAnimation; //animaciones de Rick disparando en caida
    private Animation 			tizaLeftAnimation, tizaRightAnimation;	// Animaciones de tiza
	private SpriteBatch 		batch; 									// Se encarga del mapeado e impresión de los objetos del juego
	private boolean 			debug = false; 							// Estado para determinar si se muestran las áreas de colisión o no
	
	/** Dimensiones de la pantalla **/
	private int 				width;  		// Anchura de pantalla en pixels
	private int 				height; 		// Altura de pantalla en pixels
	private float 				ppuX = 0;		// pixels por unidad en el eje X
	private float 				ppuY = 0;		// pixels por unidad en el eje Y
	
	// CONSTRUCTOR ------------------------------------------------------------------------------------------
	public WorldRenderer(World world, boolean debug){
		// Referencia al mundo a renderizar
		this.world = world;
		/* Crea una "cámara", cuyas medidas son 10 unidades de largo y 7 de alto.
		 * Las medidas pueden variar en función del tamaño del mundo a visualizar,
		 * entre otras razones.
		 */
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		/* Sitúa el centro de la cámara en el centro del mundo a visualizar, el cual está
		 * a 5 unidades de largo y 3.5 de alto, dadas las medidas anteriores */
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		// Actualiza las propiedades de la cámara
		this.cam.update();
		this.debug = debug;
		this.batch = new SpriteBatch();
		loadTextures();
	}
	// MÉTODOS ----------------------------------------------------------------------------------------------
	// (Re)calcula las unidades en píxels cada vez que la pantalla cambia de tamaño
	public void setSize (int w, int h){
		this.width = w;
		this.height = h;
		// Se define la relación píxels/unidad para redimensionar las pantallas (sólo una vez)
		if(ppuX==0 && ppuY==0){
			ppuX = (float)width / CAMERA_WIDTH;
			ppuY = (float)height / CAMERA_HEIGHT;
		}
	}
	
	public float getPpuX(){ return this.ppuX; }
	public float getPpuY(){ return this.ppuY; }
//	public OrthographicCamera getCam(){ return cam; }
//	public boolean isDebug(){ return debug; }
//	public void setDebug(boolean debug){ this.debug = debug; }
	// Carga para cada objeto las texturas pertinentes desde el directorio escogido
	private void loadTextures(){
		joypad = new TextureAtlas(Gdx.files.internal("images/texturas/joypad.atlas"));
        // Agrega las texturas de los botones del joypad (sólo si la aplicación se ejecuta en Android)
		if (Gdx.app.getType().equals(ApplicationType.Android)){
			jumpButton = joypad.findRegion("jump");
            shootButton = joypad.findRegion("shoot");
			arrowLeft = joypad.findRegion("leftPad");
			arrowRight = new TextureRegion(arrowLeft);
			arrowRight.flip(true, false);
		}
		lifePoint = joypad.findRegion("life_point");
		lifelessPoint = joypad.findRegion("lifeless_point");
		// Carga el mapa de texturas
		atlas = new TextureAtlas(Gdx.files.internal("images/texturas/game.atlas"));
		// Textura de terreno
		blockTexture = atlas.findRegion("block");
		// Textura de puntos de control
		checkPoint = atlas.findRegion("respawn");
		// Textura de potes de vida
		potTxt = atlas.findRegion("pot");
		// TEXTURAS: TIZAS LETALES ---------------------------------------------------------------------------------------
//		tizaTxt = atlas.findRegion("tizaLetal1");
        tizaDchaTxt = new TextureRegion[4];
        for(int i = 0; i < tizaDchaTxt.length; i++){
            tizaDchaTxt[i] = atlas.findRegion("tizaLetal" + (i+1));
        }
        tizaIzdaTxt = new TextureRegion[4];
        for(int i = 0; i < tizaIzdaTxt.length; i++){
            tizaIzdaTxt[i] = new TextureRegion(tizaDchaTxt[i]);
            tizaIzdaTxt[i].flip(true, false);
        }
        tizaRightAnimation = new Animation(TIZA_FRAME_DURATION, tizaDchaTxt);
        tizaLeftAnimation = new Animation(TIZA_FRAME_DURATION, tizaIzdaTxt);
		// TEXTURAS: RICK ------------------------------------------------------------------------------------------------
		rickIdleRight = atlas.findRegion("Rick");
		rickIdleLeft = new TextureRegion(rickIdleRight);
		rickIdleLeft.flip(true, false);
        // Texturas de Rick disparando en reposo -------------------------------------------------------------------------
        rickShootIdleRight = atlas.findRegion("rickDisparo");
        rickShootIdleLeft = new TextureRegion(rickShootIdleRight);
        rickShootIdleLeft.flip(true, false);
		// Texturas de Rick en salto y caída -----------------------------------------------------------------------------
		jumpRightFrames = new TextureRegion[3];
		for(int i = 0; i < jumpRightFrames.length; i++){
			jumpRightFrames[i] = atlas.findRegion("saltoRick" + (i+1));
		}
		fallRightFrames = new TextureRegion[4];
		for(int i = 0; i < fallRightFrames.length; i++){
			fallRightFrames[i] = atlas.findRegion("saltoRick" + (i+4));
		}
		jumpLeftFrames = new TextureRegion[3];
		for(int i = 0; i < jumpLeftFrames.length; i++){
			jumpLeftFrames[i] = new TextureRegion(jumpRightFrames[i]);
			jumpLeftFrames[i].flip(true, false);
		}
		fallLeftFrames = new TextureRegion[4];
		for(int i = 0; i < fallLeftFrames.length; i++){
			fallLeftFrames[i] = new TextureRegion(fallRightFrames[i]);
			fallLeftFrames[i].flip(true, false);
		}
		jumpLeftAnimation = new Animation(JUMPING_FRAME_DURATION, jumpLeftFrames);
		jumpRightAnimation = new Animation(JUMPING_FRAME_DURATION, jumpRightFrames);
		fallLeftAnimation = new Animation(JUMPING_FRAME_DURATION, fallLeftFrames);
		fallRightAnimation = new Animation(JUMPING_FRAME_DURATION, fallRightFrames);
		// Texturas de Rick corriendo hacia la derecha, y animación -------------------------------------------------------
//		walkRightFrames = new TextureRegion[7];
        walkRightFrames = new TextureRegion[6];
		for(int i = 0; i < walkRightFrames.length; i++){
//			walkRightFrames[i] = atlas.findRegion("RickCorrer" + (i + 2));
            walkRightFrames[i] = atlas.findRegion("RickCorrer" + (i + 3));
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		// Texturas de Rick corriendo hacia la izquierda, y animación
//		walkLeftFrames = new TextureRegion[7];
        walkLeftFrames = new TextureRegion[6];
		for(int i = 0; i < walkLeftFrames.length; i++){
			walkLeftFrames[i] = new TextureRegion(walkRightFrames[i]);
			walkLeftFrames[i].flip(true, false);
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

        //Texturas de Rick corriendo hacia la derecha disparando y animacion ------------------------------------------------
        shootWalkRightFrames = new TextureRegion[6];
        for(int i = 0; i < shootWalkRightFrames.length;i++){
            shootWalkRightFrames[i] = atlas.findRegion("rickCorrerDisparo"+(i+3));
        }
        shootWalkRightAnimation = new Animation(RUNNING_FRAME_DURATION, shootWalkRightFrames);

        //Texturas de Rick corriendo hacia la izquierda disparando y animacion--------------------------------------------
        shootWaltLeftFrames = new TextureRegion[6];
        for (int i = 0; i < shootWaltLeftFrames.length; i++) {
            shootWaltLeftFrames[i] = new TextureRegion(shootWalkRightFrames[i]);
            shootWaltLeftFrames[i].flip(true,false);
        }
        shootWaltLeftAnimation = new Animation(RUNNING_FRAME_DURATION, shootWaltLeftFrames);

        //

        shootJumpRightFrames = new TextureRegion[3];
        for(int i = 0; i < shootJumpRightFrames.length; i++){
            shootJumpRightFrames[i] = atlas.findRegion("saltoRickDisparo" + (i+1));
        }
        shootFallRightFrames = new TextureRegion[4];
        for(int i = 0; i < shootFallRightFrames.length; i++){
            shootFallRightFrames[i] = atlas.findRegion("saltoRickDisparo" + (i+4));
        }
        shootJumpLeftFrames = new TextureRegion[3];
        for(int i = 0; i < shootJumpLeftFrames.length; i++){
            shootJumpLeftFrames[i] = new TextureRegion(shootJumpRightFrames[i]);
            shootJumpLeftFrames[i].flip(true, false);
        }
        shootFallLeftFrames = new TextureRegion[4];
        for(int i = 0; i < shootFallLeftFrames.length; i++){
            shootFallLeftFrames[i] = new TextureRegion(shootFallRightFrames[i]);
            shootFallLeftFrames[i].flip(true, false);
        }
        shootJumpLeftAnimation = new Animation(JUMPING_FRAME_DURATION, jumpLeftFrames);
        shootJumpRightAnimation = new Animation(JUMPING_FRAME_DURATION, jumpRightFrames);
        shootFallLeftAnimation = new Animation(JUMPING_FRAME_DURATION, fallLeftFrames);
        shootFallRightAnimation = new Animation(JUMPING_FRAME_DURATION, fallRightFrames);



        // TEXTURAS: FONDO DE PANTALLA -------------------------------------------------------------------------------------
		background = new Texture(Gdx.files.internal("images/background.jpg"));
	}
	// Método principal de la clase, llamado desde el método "render" de la clase "GameScreen"
	public void render(){
		// Desplaza la cámara a su posición
		setCam();
		// Comienza el renderizado
		batch.begin();
		batch.disableBlending();
		// Sitúa el background a visualizar en el nivel
		batch.draw(background, this.cam.position.x/100f, this.cam.position.y/100f, 
				this.world.getLevel().getWidth(), this.world.getLevel().getHeight());
		batch.enableBlending();
		// Renderizado de bloques
		drawBlocks();
		// Renderizado de puntos de control
		drawChecks();
		// Renderizado de potes de vida
		drawPots();
		// Renderizado de tizas letales
		drawTizas();
		// Renderizado del personaje
		drawRick();
		// Renderizado del joypad
		drawPad();
		// Finaliza el renderizado
		batch.end();
		// Rellena los bloques con los que está colisionando el personaje
		drawCollisionBlocks();
		// Si el debug está activo, dibuja los contornos de colisionadores
		if (debug){
			drawDebug();
		}
	}
	// Método de borrado de texturas, llamado desde el método "dispose" de la clase "GameScreen"
	public void dispose(){
		atlas.dispose();
		joypad.dispose();
		background.dispose();
		batch.dispose();
		debugRenderer.dispose();
	}
	// Desplaza la cámara con el personaje, en base a las dimensiones del nivel
	private void setCam(){
		// Obtiene las coordenadas de Rick
		rickPosX = world.getRick().getPosition().x;
		rickPosY = world.getRick().getPosition().y;		
		// Si la posición de Rick en el eje X es mayor o igual que la de la cámara en su momento inicial...
		if(CAMERA_WIDTH/2f <= rickPosX && (this.world.getLevel().getWidth() - CAMERA_WIDTH/2f) >= rickPosX){
			this.cam.position.set(world.getRick().getPosition().x, this.cam.position.y, 0);
		}else if(CAMERA_WIDTH/2f > rickPosX){
			this.cam.position.x = CAMERA_WIDTH / 2f;
		}else{
			this.cam.position.x = this.world.getLevel().getWidth() - CAMERA_WIDTH / 2f;
		}
		// Si la posición de Rick en el eje Y es mayor o igual que la de la cámara en su momento inicial...
		if(CAMERA_HEIGHT / 2f <= rickPosY && (this.world.getLevel().getHeight() - CAMERA_HEIGHT / 2f) >= rickPosY){
			this.cam.position.set(this.cam.position.x, this.world.getRick().getPosition().y, 0);
		}else if(CAMERA_HEIGHT / 2f > rickPosY){
			this.cam.position.y = CAMERA_HEIGHT / 2f;
		}else{
			this.cam.position.y = this.world.getLevel().getHeight() - CAMERA_HEIGHT / 2f;
		}
		
		this.cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	// Dibuja los bloques del terreno
	private void drawBlocks(){
		for(Block block : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)){
			batch.draw(blockTexture, block.getPosition().x, block.getPosition().y, Block.SIZE, Block.SIZE);
		}
	}
	// Dibuja los puntos de control del terreno
	private void drawChecks(){
		for(RespawnPoint check : world.getDrawableChecks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)){
			batch.draw(checkPoint, check.getPosition().x, check.getPosition().y, check.getBounds().width, check.getBounds().height);
		}
	}
	// Dibuja los potes de vida del terreno
	private void drawPots(){
		for(PotLife pot : world.getDrawablePots((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)){
			batch.draw(potTxt, pot.getPosition().x, pot.getPosition().y, pot.getBounds().width, pot.getBounds().height);
		}
	}
	// Dibuja las tizas asesinas
	private void drawTizas(){
		for(TizaLetal tiza : world.getDrawableTizas((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)){
//			batch.draw(tizaTxt, tiza.getPosition().x, tiza.getPosition().y, tiza.getBounds().width, tiza.getBounds().height);
            tizaFrame = tiza.isFacingLeft() ? tizaRightAnimation.getKeyFrame(tiza.getStateTime(), true) : tizaLeftAnimation.getKeyFrame(tiza.getStateTime(), true);
            batch.draw(tizaFrame, tiza.getPosition().x, tiza.getPosition().y, tiza.getBounds().width, tiza.getBounds().height);
		}
	}
	// Dibuja al personaje jugable
	private void drawRick(){
		Rick rick = world.getRick();
        if(rick.getState().equals(State.SHOOTING)){
            rickFrame = rick.isFacingLeft() ? shootWaltLeftAnimation.getKeyFrame(rick.getShootStateTime(), true)
                    : shootWalkRightAnimation.getKeyFrame(rick.getShootStateTime(),true);
        }else if(rick.getState().equals(State.IDLE)){
            rickFrame = rick.isFacingLeft() ? rickIdleLeft : rickIdleRight; // Selecciona un frame de "Idle" en función de la orientación de Rick
        }else if(rick.getState().equals(State.WALKING)){
            rickFrame = rick.isFacingLeft() ? walkLeftAnimation.getKeyFrame(rick.getRunStateTime(), true)
                    : walkRightAnimation.getKeyFrame(rick.getRunStateTime(), true);



		}else if(rick.getState().equals(State.JUMPING)){
            if (rick.getVelocity().y > 0){
//				rickFrame = rick.isFacingLeft() ? rickJumpLeft : rickJumpRight;
				if(rick.isFacingLeft()){
					rickFrame = jumpLeftAnimation.getKeyFrame(rick.getJumpStateTime(), true);
				}else{
					rickFrame = jumpRightAnimation.getKeyFrame(rick.getJumpStateTime(),true);
				}
			} else {
//				rickFrame = rick.isFacingLeft() ? rickFallLeft : rickFallRight;
				if(rick.isFacingLeft()){
					rickFrame = fallLeftAnimation.getKeyFrame(rick.getFallStateTime(), true);
				}else{
					rickFrame = fallRightAnimation.getKeyFrame(rick.getFallStateTime(), true);
				}
			}
        }
//        System.out.println("Vx: "+rick.getVelocity().x
//                +"; Vy: "+rick.getVelocity().y
//                +"; Ax: "+rick.getAcceleration().x
//                +"; Ay: "+rick.getAcceleration().y);
		// Pequeño tumor para evitar bug de caída del nivel (y origen del bug de caída infinita... -.-)
		if(rick.getPosition().y < 0){
            rick.getAcceleration().y = 0;
            rick.getVelocity().y = 0;
            rick.setPositionX(world.getLevel().getSpawnX());
			rick.setPositionY(world.getLevel().getSpawnY());
		}
		batch.draw(rickFrame, rick.getPosition().x, rick.getPosition().y, rick.getSize(), rick.getSize());
	}
	// Dibuja el pad del juego y el visor de datos
	private void drawPad(){
		// Dibuja el pad (si se ejecuta en Android)
		if (Gdx.app.getType().equals(ApplicationType.Android)){
	        batch.draw(arrowLeft, (this.cam.position.x - 4), (this.cam.position.y - 3), 1, 1);
	        batch.draw(arrowRight, (this.cam.position.x - 3), (this.cam.position.y - 3), 1, 1);
	        batch.draw(jumpButton, (this.cam.position.x + 3), (this.cam.position.y - 3), 1, 1);
            batch.draw(shootButton, (this.cam.position.x + 2), (this.cam.position.y - 3), 1, 1);
		}
		// Creación del indicador de vida
		TextureRegion[] lifeTexture = new TextureRegion[4];
		for(int n = 0; n < lifeTexture.length; n++){
			// Agrega texturas en función de los puntos de vida que tiene Rick
			if((world.getRick().getLife() - 1) >= n){
				// Este punto de vida sigue en posesión de Rick
				lifeTexture[n] = lifePoint;
			}else{
				// Rick ha perdido este punto de vida
				lifeTexture[n] = lifelessPoint;
			}
		}
		batch.draw(lifeTexture[0], (this.cam.position.x - 2), (this.cam.position.y + (float)2.5), (float)0.5, (float)0.5);
		batch.draw(lifeTexture[1], (this.cam.position.x - 1), (this.cam.position.y + (float)2.5), (float)0.5, (float)0.5);
		batch.draw(lifeTexture[2], (this.cam.position.x), (this.cam.position.y + (float)2.5), (float)0.5, (float)0.5);
		batch.draw(lifeTexture[3], (this.cam.position.x + 1), (this.cam.position.y + (float)2.5), (float)0.5, (float)0.5);
	}
	// Dibuja las áreas de colisión de todos los elementos del juego visibles en pantalla
	private void drawDebug(){
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Block block : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			Rectangle rect = block.getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1)); // rojo
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		// render abyss
		for (Abyss abyss : world.getDrawableAbyss((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			Rectangle rect = abyss.getBounds();
			debugRenderer.setColor(new Color(0, 0, 1, 1)); // azul
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		// render tizas letales
		for(TizaLetal tiza : world.getDrawableTizas((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)){
			Rectangle rect = tiza.getBounds();
			debugRenderer.setColor(new Color(1, 0, 1, 1)); // morado
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		// render Rick
		Rick rick = world.getRick();
		Rectangle rect = rick.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1)); // verde
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		debugRenderer.end();
	}
	// Dibuja aquellos elementos con los que se produce colisión
	private void drawCollisionBlocks() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		debugRenderer.end();
	}
}