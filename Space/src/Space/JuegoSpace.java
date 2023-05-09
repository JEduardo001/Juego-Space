package Space;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
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
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

import javax.swing.JPanel;

//import JuegoMio.Juego.MyGraphics;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class JuegoSpace {

	Timer timer = new Timer();
	private static JFrame frame =new JFrame();;
	JPanel tablero = new JPanel();

	//jugador
	static int jugadorX=200,jugadorY=380,speedJugador=10,vidaJugador=1000;
	static int cordJugador[][]= new int[2][4]; 

	//static JLabel jLabelVidaJugador = new JLabel("V I D A  ||   "+vidaJugador);
	//static int cordProyectilesJugador[][]= new int[50][3];
	static int balaJugadorX,balaJugadorY;
	//juego
	static boolean gameOver=false;
	static int limiteX=470;
	static int limiteY=410;
	static Random rand = new Random();

	//mapa
	Mapa cordBordesMapa[]= new Mapa[4];
	//Enemigo
	public static Enemigo enemigos[] =new Enemigo[68];
	public static int balaEnemigaX;
	public static int balaEnemigaY;
	//public static int proyectilEnemigo[][]=new int[5][2];
	public static int vidaBoss=1;

	private Image enemigo1,enemigo2,enemigo3,boss;
	private MediaTracker tracker;
	private int frameIndex = 0;
	private boolean isRunning = false;
	private Font Score = new Font("Arial", Font.PLAIN, 20);
	public static int score = 0;
	private Font Vidas = new Font("Arial", Font.PLAIN, 20);
	public static int vidasjugador = 3;
	//muro
    private final static int Filas = 4; // Número de filas del muro
    private final static int Columnas = 6; // Número de columnas del muro
    private final static int Ancho_lad = 10; // Ancho de cada ladrillo
    private final static int Alto_lad = 10; // Alto de cada ladrillo
    public static  Rectangle[][][] ladrillos = new Rectangle[4][Filas][Columnas];
    

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



	public void enemigos() {


		enemigo1=new ImageIcon("enemy1.gif").getImage();   
		enemigo2= new ImageIcon("enemy2.gif").getImage();
		enemigo3 = new ImageIcon("enemy3.gif").getImage();
		boss=new ImageIcon("boss.gif").getImage();
	    // Inicializa el MediaTracker
	    tracker = new MediaTracker(tablero);
	    tracker.addImage(enemigo1, 0);
	    tracker.addImage(enemigo2, 0);
	    tracker.addImage(enemigo3, 0);
	    tracker.addImage(boss, 0);
	    try {
	        tracker.waitForAll();
	    } catch (InterruptedException ex) {
	        ex.printStackTrace();
	    }

	    // Comienza la animación
	    isRunning = true;
	    Thread animationThread = new Thread();
	    animationThread.start();

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

        	//cambiarDireccionEnemiga();
        }
	};
	TimerTask genProyectilEnemigo = new TimerTask() {
        public void run() {

        	genProyectilEnemigo();
        }
	};
	TimerTask moverProyectilEnemigo = new TimerTask() {
        public void run() {

        	moverProyectilEnemigo();
        }
	};
	TimerTask moverBoss = new TimerTask() {
        public void run() {

        	moverBoss();
        }
	};

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */


	public static void genProyectilJugador() {

				if(balaJugadorX==0) {

					balaJugadorX=cordJugador[1][0]+4;
					balaJugadorY=cordJugador[1][1];
				}

	}

		//esto es para comprobar si la bala del jugador choco contra el enemigo
	public static void colicion() {


			for(int i2=0;i2<enemigos.length;i2++) {

				if(balaJugadorX<=enemigos[i2].x+30 && balaJugadorX>=enemigos[i2].x
						&& balaJugadorY>enemigos[i2].y && balaJugadorY<enemigos[i2].y+30) {
					
					//Audio
					 try {
					        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("AlienDestruido.wav").getAbsoluteFile());
					        Clip clip = AudioSystem.getClip();
					        clip.open(audioInputStream);
					        clip.start();
					    } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					         //System.out.println("Error al reproducir el sonido.");
					    }
					 
					System.out.println("impacto bala jugador con enemigo linea: 180");
					score=score+20;

					if(i2==0) {
						System.out.println("impacto con boss");
						score=score+100-20;
						
						//Audio
						 try {
						        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("AlienDestruido.wav").getAbsoluteFile());
						        Clip clip = AudioSystem.getClip();
						        clip.open(audioInputStream);
						        clip.start();
						    } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
						         //System.out.println("Error al reproducir el sonido.");
						    }
						 
						if(vidaBoss-1==0) {
							System.out.println("BOSS MUERTOOOOOO");
						}else {
							vidaBoss--;

						}


					}else {
						balaJugadorX=0;
						balaJugadorY=0;
						enemigos[i2].x=0;
						enemigos[i2].y=0;	
					}



				}
			}

	}

	public static void moverProyectilJugador() {

         		if(balaJugadorY-20>0) {

         			balaJugadorY--;
         		}else {
         			balaJugadorX=0;
         			balaJugadorY=0;
         		}

         		//comprobamos si la bala del jugador choco contra la del enemigo
         		colicion();
         		//comprobamos si al bala choco con un muro destruible, el 0 indica que la bala a analizar es del jugador
         		colicionBalaMuro(balaJugadorX,balaJugadorY,0);

	}
	//comprobamos una bala sea enemigo o del jugador  choco con un muro destruible
	public static void colicionBalaMuro(int posicionX,int posicionY,int balaDe) {
		
		for(int i=0;i<ladrillos.length;i++) {
			
			for(int i2=0;i2<Filas;i2++) {
				
				for(int i3=0;i3<Columnas;i3++) {
					if(posicionX!=0) {
						if(posicionX<=ladrillos[i][i2][i3].x+10 && posicionX>=ladrillos[i][i2][i3].x-3
								&& posicionY<=ladrillos[i][i2][i3].y+7 && posicionY-5>=ladrillos[i][i2][i3].y) {
							
							//si el dato balaDe = 0 la bala es del jugador si es 1 es del Enemigo, esto para saber que barr
							if(balaDe==0) {
								balaJugadorX=0;
								balaJugadorY=0;
								posicionX=0;
								posicionY=0;
							}else {
								balaEnemigaX=0;
								balaEnemigaY=0;
								posicionX=0;
								posicionY=0;
							}
							
							
							ladrillos[i][i2][i3].x=0;
							ladrillos[i][i2][i3].y=0;
							
							//Audio
							 try {
							        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("BloqueDestruido.wav").getAbsoluteFile());
							        Clip clip = AudioSystem.getClip();
							        clip.open(audioInputStream);
							        clip.start();
							    } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
							         //System.out.println("Error al reproducir el sonido.");
							    }
							System.out.println("choque con pared linea 270");
							
						}
					}
					
				}
			}
			
		}
	}
	public static void crearJugador() {

		//centro
		cordJugador[0][0]=225;
		cordJugador[0][1]=380;
		cordJugador[0][2]=40;
		cordJugador[0][3]=10;

		//cañon
		cordJugador[1][0]=240;
		cordJugador[1][1]=370;
		cordJugador[1][2]=10;
		cordJugador[1][3]=20;

	}

    private void Ladrillos() {

    	

        for (int muro = 0; muro < 4; muro++) {

            for (int fila = 0; fila < Filas; fila++) {

                for (int columna = 0; columna < Columnas; columna++) {

                    int x = columna * Ancho_lad + 60 + muro * 100;
                    int y = fila * Alto_lad + 320;

                    ladrillos[muro][fila][columna] = new Rectangle(x, y, Ancho_lad, Alto_lad);
                }
            }
        }
    }


	public void moverBoss() {
		
		if(enemigos[0].x<-1000) {
			enemigos[0].x=limiteX-50;
			//Audio
			 try {
			        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("MovimientoBoss.wav").getAbsoluteFile());
			        Clip clip = AudioSystem.getClip();
			        clip.open(audioInputStream);
			        clip.start();
			    } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			         //System.out.println("Error al reproducir el sonido.");
			    }
		}else {
			enemigos[0].x-=30;
		}

	}
	public void moverEnemigos() {
		for(int i=1;i<enemigos.length;i++) {
			switch(enemigos[i].direccion) {
			//derecha
			case 0:

				//resto de naves
				if(enemigos[i].y+20<limiteY) {
					enemigos[i].y+=20;

				}

			break;
			//izquierda
			case 1:
				if(enemigos[i].x-10>0) {
					enemigos[i].x-=10;

				}
			break;



			}
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
	public static void  genProyectilEnemigo() {

		//si te genera un numerode un enemigo que ya esta destruido este no d
		int enemigoQueDisparo = rand.nextInt(67+1);

			if(balaEnemigaX==0) {
				balaEnemigaX= enemigos[enemigoQueDisparo].x;
				balaEnemigaY= enemigos[enemigoQueDisparo].y;



			}



	}

	public static void moverProyectilEnemigo() {


			if(balaEnemigaX!=0) {
				if(balaEnemigaY+20<limiteY) {
					balaEnemigaY++;

				}else {
					balaEnemigaX=0;
					balaEnemigaY=0;

				}
				//comrobamos si la bala choco con un muro destruible
				colicionBalaMuro(balaEnemigaX,balaEnemigaY,1);
				//comrobar si la bala enemiga choco con el jugador
				if(balaEnemigaX<=cordJugador[0][0]+40 && balaEnemigaX>cordJugador[0][0]
						&& balaEnemigaY>=cordJugador[0][1] && balaEnemigaY<=cordJugador[0][1]+10 ) {
					
					//Audio
					 try {
					        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("NaveDestruida.wav").getAbsoluteFile());
					        Clip clip = AudioSystem.getClip();
					        clip.open(audioInputStream);
					        clip.start();
					    } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					         //System.out.println("Error al reproducir el sonido.");
					    }
					 
					//aqui vas a poner un contador el cual aumentara
					System.out.println("impacto bala enemiga con jugador linea: 330");
					balaEnemigaX=0;
					balaEnemigaY=0;
					vidasjugador=vidasjugador-1;
				}
			}



	}


	public void ponerEnemigo() {
		/*
		 *   
		  Aqui abajo le doy parametros del enemigo osea los datos que tendra. Cada dato se refiere a esto....
		 *  
		 *  posicion 0 = cordenada X  
		 *  posicion 1= cordenada Y   
		 *  posicion 2= largo
		 *  posicion 3 = alto 
		 *  posicion 4= color
		 *  posicion 5= direccion hacia donde va el enemigo
						
	
		 */
		//boss

		enemigos[0]=new Enemigo(230,35,10,30,Color.white,0);

		//pulpitos				   
		for(int i=1; i<17; i++ ) {
							//			 0   1  2  3     4        5
			enemigos[i]= new Enemigo(-10+i*30,70,10,30,Color.white,0);

		}
		//cucarachas1
		for(int i=16; i<27; i++) {

			enemigos[i]= new Enemigo(-615+i*40,90,10,30,Color.white,0);

		}

		//variable espacio para separar monitos
		int espacio=-15;
			for(int i=27; i<38; i++) {
				espacio+=40;
			enemigos[i]= new Enemigo(espacio,120,10,30,Color.white,0);

		}

			int espacio1=-10;
			for(int i=38; i<53; i++) {
				espacio1+=30;
				enemigos[i]= new Enemigo(espacio1,150,10,30,Color.white,0);

			}
			int espacio2=-10;
			for(int i=53; i<68; i++) {
				espacio2+=30;
				enemigos[i]= new Enemigo(espacio2,180,10,30,Color.white,0);

			}




		enemigos();

	}
	private void initialize() {
		pintarMapa();
		crearJugador();
		ponerEnemigo();
		Ladrillos();
		//tiempos
		timer.schedule(moverProyectilJugador, 0, 1);
		//timer.schedule(moverEnemigos, 0, 5000);
		timer.schedule(genProyectilEnemigo, 0, 10);
		timer.schedule(moverProyectilEnemigo, 0, 3);
		timer.schedule(moverBoss, 0, 450);



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

		frame.add(tablero);
		frame.getContentPane().add(tablero);
		tablero.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));



		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(gameOver!=true) {
					// TODO Auto-generated method stub
					//izquierda
					if(e.getKeyCode()==65) {
						if(cordJugador[0][0]-speedJugador>0+10) {
							for(int i=0;i<2;i++) {
								cordJugador[i][0]-=speedJugador;

							}

						}

					}
					//derecha
					if(e.getKeyCode()==68) {
						if(cordJugador[0][0]+speedJugador<limiteX-35) {
							for(int i=0;i<2;i++) {
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
					//System.out.println(cordJugador[0][0]+":    "+cordJugador[0][1]);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

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

    		// linea de abajo
    		g.setColor(Color.GREEN);
    		g.fillRect(10, 397, 460, 5);

    		// Score
    		g.setFont(Score);
    		g.setColor(Color.green);
    		g.drawString("SCORE : " + score, 330, 30);

    		// Vidas jugador
    		g.setFont(Vidas);
    		g.setColor(Color.green);
    		g.drawString("VIDAS : " + vidasjugador, 60, 30);



    		// Dibujar los ladrillos usando el array
            for (int muro = 0; muro < 4; muro++) {

                for (int fila = 0; fila < Filas; fila++) {

                    for (int columna = 0; columna < Columnas; columna++) {

                        Rectangle ladrillo = ladrillos[muro][fila][columna];

                        int x = ladrillo.x;
                        int y = ladrillo.y;

                        if(x!=0) {
                        	// Dibujar el ladrillo

                            g.setColor(Color.GREEN);
                            g.fillRect(x, y, Ancho_lad - 2, Alto_lad - 2);

                            // Dibujar la línea superior del ladrillo

                            g.setColor(Color.WHITE);
                            g.drawLine(x, y, x + Ancho_lad - 2, y);

                            // Dibujar la línea izquierda del ladrillo

                            g.drawLine(x, y, x, y + Alto_lad - 2);

                            // Dibujar la línea inferior del ladrillo

                            g.setColor(Color.DARK_GRAY);
                            g.drawLine(x, y + Alto_lad - 2, x + Ancho_lad - 2, y + Alto_lad - 2);

                            // Dibujar la línea derecha del ladrillo

                            g.drawLine(x + Ancho_lad - 2, y, x + Ancho_lad - 2, y + Alto_lad - 2);
                        }
                        

                    }
                }
            }


            //pintar balas jugador

            if(balaJugadorX!=0) {
            		 g.setColor(Color.cyan);
                     g.fillRect(balaJugadorX,balaJugadorY, 3, 10);

            }


            //pintar jugador
            for(int i=0;i<2;i++) {
        	    g.setColor(Color.green);
                g.fillRect(cordJugador[i][0],cordJugador[i][1], cordJugador[i][2], cordJugador[i][3]);
            }

            //pintarEnemigo
            //boss
        	if(enemigos[0].x!=0) {
                g.drawImage(boss,enemigos[0].x,enemigos[0].y,this);
    		}
          //pulpito
            	for(int i=1; i<16; i++) {

            		if(enemigos[i].x!=0) {
            			g.drawImage(enemigo3, enemigos[i].x, enemigos[i].y, this);
            		}


            	}

            	for(int i=16; i<27; i++) {
            		if(enemigos[i].x!=0) {
                		g.drawImage(enemigo2, enemigos[i].x, enemigos[i].y, this);
            		}

            	}

            	for(int i=17; i<38; i++) {

            		if(enemigos[i].x!=0) {
                		g.drawImage(enemigo2, enemigos[i].x, enemigos[i].y, this);
            		}


            	}

            	for(int i=38; i<53; i++) {
            			if(enemigos[i].x!=0) {
                    		g.drawImage(enemigo1, enemigos[i].x, enemigos[i].y, this);	
                		}


            	}

            		for(int i=53; i<68; i++) {

            			if(enemigos[i].x!=0) {
                    		g.drawImage(enemigo1, enemigos[i].x, enemigos[i].y, this);
                		}	
                	}
            //	g.drawImage(enemigo2,100,100,this);


           //pintar proyectiles enemigos

        	   if(balaEnemigaX!=0) {
                  	g.setColor(Color.red);
                      g.fillRect(balaEnemigaX,balaEnemigaY,5,10);

              	}



           tablero.repaint();

       }

        public void run() {
            while (isRunning) {
                // Actualiza el índice del frame y repinta el JPanel
                frameIndex = (frameIndex + 1) % enemigo1.getWidth(this);
                repaint();

                // Espera 100 milisegundos antes de mostrar el siguiente frame
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
	}

}