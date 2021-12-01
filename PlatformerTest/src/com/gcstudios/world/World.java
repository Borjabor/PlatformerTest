package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gcstudios.entities.Coin;
import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++){
				for(int yy = 0; yy < map.getHeight(); yy++){
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new SkyTile(xx*16,yy*16,Tile.TILE_SKY);
					if(pixelAtual == 0xFF00000) {
						//BG
						tiles[xx + (yy * WIDTH)] = new SkyTile(xx*16,yy*16,Tile.TILE_SKY);
					}else if(pixelAtual == 0xFFffffff) {
						//Floor
						tiles[xx + (yy * WIDTH)] = new GroundTile(xx * 16, yy * 16, Tile.TILE_GROUND);
						//Precisa da segunda verificação caso ele precise comparar algo na borda com algo fora da tela
						if(yy - 1 < 0 || pixels[xx + ((yy -1) * map.getWidth())] == 0xFFFFFFFF) {
							tiles[xx + (yy * WIDTH)] = new GroundTile(xx*16, yy*16, Game.spritesheet.getSprite(16,  16,  16,  16));
						}
					}else if (pixelAtual == 0xff0026ff) {
						//Player
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					}else if(pixelAtual == 0xffff0000) {
						//Enemy 01
						Enemy enemy = new Enemy(xx*16, yy*16, 16, 16, 1, Entity.ENEMY_01_LEFT);
						Game.entities.add(enemy);
					}else if(pixelAtual == 0xffffd541) {
						//Coins
						Coin coin = new Coin(xx*16, yy*16, 16, 16, 1, Entity.COIN[0]);
						Game.entities.add(coin);
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof GroundTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof GroundTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof GroundTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof GroundTile));
	}
	
	public static void restartGame(){
		//Apply a method to restart game (TODO is "to do")
		return;
	}
	
	public void render(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
