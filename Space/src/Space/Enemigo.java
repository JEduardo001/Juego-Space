package Space;

import java.awt.Color;

public class Enemigo {


	int x,y,largo,alto, vida;
	Color color;
	int direccion;
	
	public Enemigo(int x,int y,int largo,int alto,Color color,int direccion,int vida) {
		this.x=x;
		this.y=y;
		this.largo=largo;
		this.alto=alto;
		this.color=color;
		this.direccion=direccion;
		this.vida=vida;
	}
}
