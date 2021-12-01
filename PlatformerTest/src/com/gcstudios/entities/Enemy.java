package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.World;

public class Enemy extends Entity{
	
	public boolean left = true, right = false, stomp = false;
	public int life = 1;
	
	private int animFrames = 0;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		if(World.isFree((int)x,  (int)(y + 3))) {
			y += 3;
		}
		
		if(left && World.isFree((int)(x + speed), (int)y)) {
			x += speed;
			if(World.isFree((int)(x + 16), (int)(y + 1))) {
				left = false;
				right = true;
			}
		}else if(right && World.isFree((int)(x - speed), (int)y)){
			x -= speed;
			if(World.isFree((int)(x - 16), (int)(y + 1))) {
				left = true;
				right = false;
			}
		}
		
		if(left && !World.isFree((int)(x + speed), (int)y)) {
			left = false;
			right = true;
		}else if(right && !World.isFree((int)(x - speed), (int)y)){
			left = true;
			right = false;
		}
	}
	
	public void render(Graphics g) {
		//Por algum motivo, o meu jogo resolveu inverter o sprite quando eu ativo esse codigo
		if(stomp) {
			sprite = Entity.ENEMY_01_STOMP;
			left = false;
			right = false;
			animFrames++;
			if(animFrames == 15) {
				animFrames = 0;
				Game.entities.remove(this);
			}		
		}else if(left) {
			sprite = Entity.ENEMY_01_RIGHT;
		}else if(right) {
			sprite = Entity.ENEMY_01_LEFT;
		}
		super.render(g);
		
	}

}
