package com.oddland.game.utils;
import java.util.Scanner;

// LibGDX
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureSetup {
	public static void main(String[] args) {
		/* OJO: TexturePacker.process tiene la siguiente sintaxis:
		 * TexturePacker.process("Ruta_origen_imágenes", "Ruta_destino", "Nombre_de_fichero.pack");
		 * 
		 * A la hora de probar el proyecto, recuerda ponerles las rutas adecuadas a tu estructura
		 * de archivos */

		// Array de direcciones asignadas
		String params[] = new String[4];
		
		Scanner input = new Scanner(System.in);
		// Asignación de fichero origen y nombre de archivo generado
		System.out.println("Selecciona fichero a empaquetar:");
		System.out.println("1: Pantalla Juego ; 2: Pantalla Título; 3: Joypad");
		int pack = input.nextInt();
		
		if(pack == 1){ // Pantalla Juego
			params[1] = "screens/game";
			params[3] = "game";
		}else if(pack == 2){ // Título
			params[1] = "screens/title";
            params[3] = "title";
		}else if(pack == 3){ // Joypad
			params[1] = "joypad";
			params[3] = "joypad";
		}else{
			System.out.println("Paquete no válido");
			System.exit(0);
		}
		// Asignación de destino
		System.out.println("Selecciona destino:");
		System.out.println("1: Local ; 2: Repositorio");
		int dir = input.nextInt();
		input.close();
		
		if(dir == 1){ // Local
			params[0] = "F:/Android/prueba2/android/assets/images/";
		}else if(dir == 2){ // Repositorio
			params[0] = "C:/Users/JC/gitEclipse/LearnWithRick/Learn With Rick-android/assets/images/";
		}else{
			System.out.println("Dirección no válida");
			System.exit(0);
		}
		params[2] = params[0]+"texturas";
		
		// Empaquetado final
		TexturePacker.process(params[0]+params[1] ,params[2] ,params[3]);		
	}
}