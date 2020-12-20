package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 640, HEIGHT = 480;
	
	private int tickCount;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int [] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private boolean running = false;

	public Game() {
		//Definindo tamanho do frame
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		//Alterando propriedades do frame
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		//Mostrando o frame
		setVisible(true);
		
		this.start();
		
	}
	
	//Inicia a thread
	public void start() { 
		running = true;
		new Thread(this).start();
	}
	//Para o loop
	public void stop() {
		running = false;
	}
	

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double nanoSecond = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		int updates = 0;
		int frames = 0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoSecond;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void tick() {
		tickCount++;
		
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		g.drawString("Hello World!", getWidth()/2 - 40, getHeight()/2);
		
		g.dispose();
		bs.show();
		
	}
	
	
	
	public static void main(String[] args) {
		Game game = new Game();
	}
}
