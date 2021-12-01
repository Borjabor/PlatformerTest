package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;


public class Player extends Entity{
		
	public boolean right, left;
	public int dir = 1;
	private double gravity = 2;
	private double vspd = 0;
	
	public static int life = 100;
	private boolean damaged = false;
	private int iframes = 0;
	
	public boolean walking = false;
	public boolean jump = false;
	public boolean isJumping = false;
	public int jumpHeight = 40;
	public int jumpFrames = 0;
	
	private int animFrames = 0;
	private int maxFrames = 15;
	private int maxSprite = 2;
	private int curSprite = 0;
	
	public static int score = 0;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;
		if(World.isFree((int)x,  (int)(y + gravity)) && isJumping == false) {
			y += gravity;
			//Collision from top; damage/remove enemies
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(Entity.isColidding(this, e)) {
						isJumping = true;
						((Enemy)e).life--; //Casting 'e' into Enemy type, since it is an Entity and not all entities have 'life' parameter
						if(((Enemy)e).life == 0) {
							((Enemy)e).stomp = true;
							score += 100;
							break;
						}
					}
				}
				
			}
		}
		
		//Smooth jump -- ignorei pois ele mostrou como colocar isso, mas ignorou completamente tudo que quebra ao fazer a mudança
		/*vspd += gravity;
		if(!World.isFree((int)x,  (int)(y + 1)) && jump) {
			vspd = -6;
			jump = false;
		}
		
		if(!World.isFree((int)x,  (int)(y + vspd))) {
			int signVsp = 0;
			if(vspd >= 0) {
				signVsp = 1;
			}else {
				signVsp = -1;
			}
			while(World.isFree((int)x,  (int)(y + signVsp))) {
				y = y + signVsp;
			}
			vspd = 0;
		}
		
		y = y + vspd;*/
		
		//movement
		if(right && World.isFree((int)(x + speed), (int) y)) {
			x += speed;
			dir = 1;
		}else if(left && World.isFree((int) (x - speed),  (int) y)) {
			x -= speed;
			dir = -1;
		}
		
		if(jump) {
			if(!World.isFree(this.getX(), this.getY() + 1)) {
				isJumping = true;
			}else {
				jump = false;
			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY() - 2)) {
				y -= 2;
				jumpFrames += 2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		
		//collision from sides; take damage
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this, e) && damaged == false) {
					life -= 50;
					damaged = true;
					if(life < 0) {
						life = 0;
					}
				}
			}
			if(e instanceof Coin) {
				if(Entity.isColidding(this, e)) {
					Game.entities.remove(e);
					score += 50;
				}
			}
		}
		
		
		
		//iframes test
		if(damaged) {
			iframes++;
			if(iframes == 20) {
				iframes = 0;
				damaged = false;
			}
			
		}
		
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
		
	}
	
	public void render(Graphics g) {
		if(walking) {
			animFrames++;
			if(animFrames == maxFrames) {
				curSprite++;
				animFrames = 0;
				if(curSprite == maxSprite) {
					curSprite = 0;
				}
			}
		}
		if(dir == 1 && !isJumping) {
			sprite = Entity.PLAYER_RIGHT_SPRITE[curSprite];
		}else if(dir == 1 && isJumping){
			sprite = Entity.PLAYER_RIGHT_SPRITE[2];
		}
		if(dir == -1 && !isJumping) {
			sprite = Entity.PLAYER_LEFT_SPRITE[curSprite];
		}else if(dir == -1 && isJumping){
			sprite = Entity.PLAYER_LEFT_SPRITE[2];
		}
		super.render(g);
	}


	


}
