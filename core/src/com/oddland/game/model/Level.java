package com.oddland.game.model;

// Librerías GDX
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Level {

	private int                         width,
                                        height;		    // Altura y longitud del nivel
	private float                       spawnX = 93,
                                        spawnY = 40; 	// Variables de punto de spawn de Rick ((1,3) para pruebas) (93,19)
	// Variables de relleno del nivel
	private Block[][]                   blocks;			// Plataformas (bloques)
	private Abyss[][]                   abyss;			// Abismos (eso que te mata cuando caes por un hueco por el que no debes)
	private RespawnPoint[][]            checkpoint;		// Puntos de control
	private PotLife[][]                 pots;			// Potes de vida
	private TizaLetal[][]               tizasLetales;	// Enemigos (tiza letal)
    private BorradorMaquiavelico[][]    borradores;	// Enemigos (borrador maquiavélico)
	// Selección de fondo musical del nivel
	private Music music = (Music) Gdx.audio.newMusic(Gdx.files.getFileHandle("data/arena.ogg", FileType.Internal));
	// CONSTRUCTOR ---------------------------------------------
	public Level() {
		loadDemoLevel();
		music.setLooping(true);
		music.setVolume(0);
	}
	// MÉTODOS -------------------------------------------------
	// Getters
	public int getWidth() { 
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public float getSpawnX(){
		return this.spawnX;
	}
	public float getSpawnY(){
		return this.spawnY;
	}
	public Block[][] getBlocks() {
		return blocks;
	}
	public Block getBlock(int x, int y) {
		return blocks[x][y];
	}
	public Abyss[][] getAbyss() {
		return abyss;
	}
	public Abyss getAbyss(int x, int y) {
		return abyss[x][y];
	}
	public RespawnPoint[][] getChecks() {
		return checkpoint;
	}
	public RespawnPoint getCheck(int x, int y) {
		return checkpoint[x][y];
	}
	public PotLife[][] getPots() {
		return pots;
	}
	public PotLife getPot(int x, int y) {
		return pots[x][y];
	}
	public TizaLetal[][] getTizas(){
		return tizasLetales;
	}
	public TizaLetal getTiza(int x, int y){
		return tizasLetales[x][y];
	}
    public BorradorMaquiavelico[][] getBorradores(){
        return this.borradores;
    }
    public BorradorMaquiavelico getBorrador(int x, int y){
        return this.borradores[x][y];
    }
	public Music getMusic() {
		return music;
	}
	// Setters
	public void setWidth(int width) {
		this.width = width;
	}	
	public void setHeight(int height) {
		this.height = height;
	}
	public void setSpawn(int x, int y) {
		this.spawnX = x;
		this.spawnY = y;
	}
	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
	public void setAbyss(Abyss[][] abyss) {
		this.abyss = abyss;
	}
	public void setChecks(RespawnPoint[][] respawn) {
		this.checkpoint = respawn;
	}
	public void setPots(PotLife[][] pots){
		this.pots = pots;
	}
	public void setPot(int x, int y){
		this.pots[x][y] = new PotLife(new Vector2(x,y));
	}
	public void crashPot(int x, int y){
		this.pots[x][y] = null;
	}
	public void setTizas(TizaLetal[][] tizasLetales){
		this.tizasLetales = tizasLetales;
	}
	public void setTiza(int x, int y){
		this.tizasLetales[x][y] = new TizaLetal(new Vector2(x,y));
	}
    public void setBorradores(BorradorMaquiavelico[][] borradores){
        this.borradores = borradores;
    }
    public void setBorrador(int x, int y){
        this.borradores[x][y] = new BorradorMaquiavelico(new Vector2(x,y));
    }
	
	private void loadDemoLevel() {
		// Inicialización de propiedades del nivel
		width = 96;
		height = 44; // 23
		blocks = new Block[width][height];
		abyss = new Abyss[width][height];
		checkpoint = new RespawnPoint[width][height];
		pots = new PotLife[width][height];
		tizasLetales = new TizaLetal[width][height];
		// Se inicializan arrays en vacío, para luego rellenarlos a nuestro gusto
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				blocks[x][y] = null;
				abyss[x][y] = null;
				checkpoint[x][y] = null;
				pots[x][y] = null;
				tizasLetales[x][y] = null;
			}
		}
		// POSICIONADO BLOQUES (De izquierda a derecha de la pantalla y de arriba abajo)
		// Extremos -------------------------------------------------------------------
		// - Suelo/Techo
		for (int x = 0; x < width; x++) {
//			blocks[x][0] = new Block(new Vector2(x, 0));
			if(x != (width - 3)){
				blocks[x][1] = new Block(new Vector2(x, 1), true);
			}else{
				abyss[x][1] = new Abyss(new Vector2(x, 1));
			}
			blocks[x][height-1] = new Block(new Vector2(x, height-1), true);
		}
		// - Paredes laterales
		for(int y = 0; y < height-1; y++){
			blocks[0][y] = new Block(new Vector2(0, y), true);
			blocks[width-1][y] = new Block(new Vector2(width-1, y), true);
		}
		// Filas continuas
		for (int x = 0; x < 16; x++) {
			blocks[x][38] = new Block(new Vector2(x, 38), true);
		}
			//
		int c = 0;
		for (int x = 19; x < width; x++) {
			blocks[x][38] = new Block(new Vector2(x, 38), true);
			c++;
			if(c == 4){
				x += 3;
				c = 0;
			}
		}
		for (int x = 0; x < 3; x++) {
			blocks[x + 92][39] = new Block(new Vector2(x + 92, 39), true);			
			abyss[x + 86][37] = new Abyss(new Vector2(x + 86, 37));
			abyss[x + 79][37] = new Abyss(new Vector2(x + 79, 37));
			abyss[x + 72][37] = new Abyss(new Vector2(x + 72, 37));
			abyss[x + 65][37] = new Abyss(new Vector2(x + 65, 37));
			abyss[x + 58][37] = new Abyss(new Vector2(x + 58, 37));
			abyss[x + 36][37] = new Abyss(new Vector2(x + 36, 37));
		}
		// Columnas continuas
//		blocks[5][4] = new Block(new Vector2(5, 4), false);
//		blocks[5][5] = new Block(new Vector2(5, 5), false);
//		blocks[5][6] = new Block(new Vector2(5, 6), false);
		// Bloques sueltos
		blocks[5][3] = new Block(new Vector2(5, 3), true);
		blocks[6][4] = new Block(new Vector2(6, 4), true);
		blocks[7][5] = new Block(new Vector2(7, 5), false);
		blocks[8][6] = new Block(new Vector2(8, 6), true);
		blocks[9][7] = new Block(new Vector2(9, 7), false);
		blocks[10][8] = new Block(new Vector2(10, 8), true);
		blocks[11][9] = new Block(new Vector2(11, 9), true);
		blocks[12][10] = new Block(new Vector2(12, 10), false);
		blocks[13][11] = new Block(new Vector2(13, 11), true);
		blocks[14][12] = new Block(new Vector2(14, 12), false);
		blocks[15][13] = new Block(new Vector2(15, 13), true);
		blocks[16][14] = new Block(new Vector2(16, 14), true);
		blocks[17][15] = new Block(new Vector2(17, 15), false);
		blocks[18][16] = new Block(new Vector2(18, 16), true);

		blocks[21][19] = new Block(new Vector2(21, 19), true);
		blocks[22][20] = new Block(new Vector2(22, 20), true);
		// Puntos de control
		checkpoint[62][39] = new RespawnPoint(new Vector2(62,39));
		// Potes de vida
		pots[70][39] = new PotLife(new Vector2(70,39)); // 2, 39
		// Tizas letales
		tizasLetales[4][39] = new TizaLetal(new Vector2(4,39));
        tizasLetales[6][39] = new TizaLetal(new Vector2(6,39));
	}
}