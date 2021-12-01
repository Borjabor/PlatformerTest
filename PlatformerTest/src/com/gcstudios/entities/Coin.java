package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Coin extends Entity{
	
	private int animFrames = 0;
	private int maxFrames = 15;
	private int maxSprite = 2;
	private int curSprite = 0;

	public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		animFrames++;
		if(animFrames == maxFrames) {
			curSprite++;
			animFrames = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		sprite = Entity.COIN[curSprite];
		super.render(g);
	}

}
