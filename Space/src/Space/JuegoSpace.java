package Space;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import JuegoMio.Juego.MyGraphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class JuegoSpace {
	
	Timer timer = new Timer();
	private static JFrame frame =new JFrame();;
	JPanel tablero = new JPanel();
	
	//jugador
	static int jugadorX=200,jugadorY=150,speedJugador=40,vidaJugador=1000;
	static int cordJugador[][]= new int[4][4]; 
	
	

	static JLabel lblNewLabelVidaJugador = new JLabel("V I D A  ||   "+vidaJugador);
	static int cordProyectilesJugador[][]= new int[50][3];
	//juego
	static boolean gameOver=false;
	static int limiteX=470;
	static int limiteY=410;
	static Random rand = new Random();
	//mapa
	Mapa cordBordesMapa[]= new Mapa[4];
	//Enemigo
	Enemigo enemigos[] =new Enemigo[1];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JuegoSpace window = new JuegoSpace();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JuegoSpace() {
		initialize();
	}
	
	TimerTask moverProyectilJugador = new TimerTask() {
        public void run() {
         
        	moverProyectilJugador();
        }
	};
	TimerTask moverEnemigos = new TimerTask() {
        public void run() {
         
        	moverEnemigos();
        }
	};
	
	
	TimerTask cambiarDireccionEnemiga = new TimerTask() {
        public void run() {
         
        	cambiarDireccionEnemiga();
        }
	};

	/**
	 * Initialize the contents of the frame.
	 */
	public static void genProyectilJugador() {
		for(int i=0;i<50;i++) {
			
				if(cordProyectilesJugador[i][0]<=0 || cordProyectilesJugador[i][0]>=limiteX || 
					cordProyectilesJugador[i][1]>=limiteY || cordProyectilesJugador[i][1]<=0  ) {
					
					cordProyectilesJugador[i][0]=cordJugador[1][0];
					cordProyectilesJugador[i][1]=cordJugador[1][1];
					cordProyectilesJugador[i][2]=rand.nextInt(4);
					break;	
				}
		}
	}
	
	
	public static void moverProyectilJugador() {
  	  
		 for(int i=0;i<cordProyectilesJugador.length;i++) {
         	//-1 es una cordenada fuera del tablero lo que va a indicar que no hay nada ahi 
         
         		if(cordProyectilesJugador[i][1]-20>0) {
         			
         			cordProyectilesJugador[i][1]-=20;
         		}else {
         			cordProyectilesJugador[i][0]=0;
         			cordProyectilesJugador[i][1]=0;
         		}
         		
         	
         	
         }

	}
	public static void crearJugador() {
		//lateral izqueirdo
		cordJugador[0][0]=200;
		cordJugador[0][1]=170;
		cordJugador[0][2]=7;
		cordJugador[0][3]=30;
		//centro
		cordJugador[1][0]=200;
		cordJugador[1][1]=180;
		cordJugador[1][2]=35;
		cordJugador[1][3]=10;
		//lateral derecho
		cordJugador[2][0]=235;
		cordJugador[2][1]=170;
		cordJugador[2][2]=7;
		cordJugador[2][3]=30;
		//cañon
		cordJugador[3][0]=216;
		cordJugador[3][1]=160;
		cordJugador[3][2]=10;
		cordJugador[3][3]=20;
	
	}
	
	public void moverEnemigos() {
		for(int i=0;i<enemigos.length;i++) {
			switch(enemigos[i].direccion) {
			//derecha
			case 0:
				//para que no se salga de la zona de juego
				if(enemigos[i].x+30<limiteX) {
					enemigos[i].x+=30;

				}
			break;
			//izquierda
			case 1:
				if(enemigos[i].x-30>0) {
					enemigos[i].x-=30;

				}
			break;
			
			case 2:
				
			break;
			case 3:
				
			break;
			
			}
		}
	}
	public void cambiarDireccionEnemiga() {
		for(int i=0;i<enemigos.length;i++) {
			enemigos[i].direccion=rand.nextInt(2);
		}
		
	}
	public void pintarMapa() {
		/*LOS PRIMEROS 2 VALORES SON LAS CORDENADAS. EL VALOR 3= LARGO DE LA BARRA. VALOR 4= ALTO DE LA BARRA
		 * 									1  2  3   4
		 * 									x, y, L,  A
		 * 		cordBordesMapa[0]= new Mapa(0, 0, 50, 50);

		 * 
		 */
	
		
		//barra horizontal arriba (la barra de hasta arriba)
		cordBordesMapa[0]= new Mapa(0,0,limiteX,10);
		
		//barra horizontal de abajo (la barra de hasta abajo)
		cordBordesMapa[1]= new Mapa(0,limiteY,limiteX,10);
		
		//barra vertical derecha (la barra a la derecha)
		cordBordesMapa[2]= new Mapa(limiteX,0,10,limiteY+20);
				
		//barra vertical izquierda (la barra a la izquierda)
		cordBordesMapa[3]= new Mapa(0,0,10,limiteY);
		
	}
	
	public void ponerEnemigo() {
		/*
		 *   
		  Aqui abajo le doy parametros del enemigo osea los datos que tendra. Cada dato se refiere a esto....
		 *  
		 *  posicion 0 = cordenada X  de la PRIMERA PARTE DEL CUERPO
		 *  posicion 1= cordenada Y   de la PRIMERA PARTE DEL CUERPO
		 *  posicion 2= largo de la parte 1 del cuerpo 
		 *  posicion 3 = alto de la parte 1 del cuerpo
		 *  posicion 4= color de la parte 1 del cuerpo
		 *  posicion 5= direccion hacia donde va el enemigo
						
	
		 */
						   //	 0   1  2  3     4     5
		enemigos[0]= new Enemigo(90,50,10,30,Color.red,0);

	}
	private void initialize() {
		pintarMapa();
		crearJugador();
		ponerEnemigo();
		//tiempos
		timer.schedule(moverProyectilJugador, 0, 10);
		timer.schedule(moverEnemigos, 0, 100);
		timer.schedule(cambiarDireccionEnemiga, 0, 400);
		
		
		frame.setBounds(100, 100, 834, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 818, 10);
		panel.setBackground(new Color(0, 255, 255));
		frame.getContentPane().add(panel);
		tablero.setBounds(0, 10, 818, 443);
		
		tablero.add(new MyGraphics());
		tablero.setBackground(new Color(0, 0, 0));
		frame.getContentPane().add(tablero);
		tablero.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(gameOver!=true) {
					// TODO Auto-generated method stub
					//arriba
					if(e.getKeyCode()==87) {
						
						if(cordJugador[1][1]-speedJugador>20) {
							for(int i=0;i<4;i++) {
								cordJugador[i][1]-=speedJugador;

							}
							
						}
						
		
					}
					//abajo
					if(e.getKeyCode()==83) {
						
							if(cordJugador[1][1]+speedJugador<limiteY) {
								for(int i=0;i<4;i++) {
									cordJugador[i][1]+=speedJugador;
								}
								System.out.println("dsda");
	
							}	
						
					}
					//izquierda
					if(e.getKeyCode()==65) {
						if(cordJugador[1][0]-speedJugador>0) {
							for(int i=0;i<4;i++) {
								cordJugador[i][0]-=speedJugador;

							}
							
						}
						
						
					}
					//derecha
					if(e.getKeyCode()==68) {
						if(cordJugador[1][0]+speedJugador<limiteX-40) {
							for(int i=0;i<4;i++) {
								cordJugador[i][0]+=speedJugador;
							}
	
						}
		
					}
					if(e.getKeyCode()==10) {
						
						//Audio
						 try {
						        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("disparoStarWsars.wav").getAbsoluteFile());
						        Clip clip = AudioSystem.getClip();
						        clip.open(audioInputStream);
						        clip.start();
						    } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
						         //System.out.println("Error al reproducir el sonido.");
						    }
						
						genProyectilJugador();
						
						
					}
				System.out.println(e.getKeyCode());
				}
			}});
		
		
	}
	
	public class MyGraphics extends JComponent {

        private static final long serialVersionUID = 1L;

        MyGraphics() {
            setPreferredSize(new Dimension(480, 420));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //pintar mapa
            for(int i=0;i<cordBordesMapa.length;i++) {
            	g.setColor(Color.blue);
                g.fillRect(cordBordesMapa[i].x,cordBordesMapa[i].y, cordBordesMapa[i].largo, cordBordesMapa[i].alto);
            	
            	
            }
            //pintar balas jugador
           
            for(int i=0;i<cordProyectilesJugador.length;i++) {
            
            	if(cordProyectilesJugador[i][0]!=0) {
            		 g.setColor(Color.cyan);
                     g.fillRect(cordProyectilesJugador[i][0],cordProyectilesJugador[i][1], 10, 20);
            	}
            	
            }
            //pintar jugador
            for(int i=0;i<4;i++) {
        	    g.setColor(Color.white);
                g.fillRect(cordJugador[i][0],cordJugador[i][1], cordJugador[i][2], cordJugador[i][3]);
            }
            
            //pintarEnemigo
            
            for(int i=0;i<enemigos.length;i++) {
            	//parte 1 del cuerpo. linea vertical
            	g.setColor(enemigos[i].color);
                g.fillRect(enemigos[i].x,enemigos[i].y, enemigos[i].largo, enemigos[i].alto);
                //parte 2 linea horizontal
                g.setColor(Color.blue);
                g.fillRect(enemigos[i].x-10,enemigos[i].y+10, 30, 10);
              
            }
           
           tablero.repaint();
      
       }
        
	}

}