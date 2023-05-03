package Space;

import java.awt.Color;

public class Enemigo {


	int x,y,largo,alto;
	Color color;
	int direccion;
	
	public Enemigo(int x,int y,int largo,int alto,Color color,int direccion) {
		this.x=x;
		this.y=y;
		this.largo=largo;
		this.alto=alto;
		this.color=color;
		this.direccion=direccion;
	}
}
