package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(5, 5,  210, 40);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 10,  200, 30);
		g.setColor(Color.RED);
		g.fillRect(10, 10,  Player.life * 2, 30);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 27));
		g.drawString("Score: " + Player.score, 250, 35);
	}
	
}
