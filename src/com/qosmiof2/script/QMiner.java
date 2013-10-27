package com.qosmiof2.script;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.Random;
import org.powerbot.script.util.Timer;

import com.qosmiof2.script.enums.*;
import com.qosmiof2.script.node.*;;

@Manifest(authors = { "Qosmiof2" }, description = "Mines & banks everything!!!!", name = "AIO QMiner")
public class QMiner extends PollingScript implements PaintListener, MessageListener {

	private int startXP;
	private int xpGained;
	private int xpPerHour;
	private int oresMined;
	private int startMiningLvl;
	public static boolean started = false;
	private long startTime = System.currentTimeMillis();

	private final ArrayList<Node> nodes = new ArrayList<>();

	public QMiner(){
		Collections.addAll(nodes, new Mine(ctx), new Drop(ctx), new Bank(ctx));
		Ores.GUI.showGUI();
	}
	
	@Override
	public void start() {
		if (ctx.game.isLoggedIn()) {
			startXP = ctx.skills.getExperience(Skills.MINING);
			startMiningLvl = ctx.skills.getRealLevel(Skills.MINING);
		}
	}
	

	@Override
	public int poll() {
		if (started) {
			for (final Node node : nodes) {
				if (node.activate()) {
					node.execute();
					return Random.nextInt(500, 1500);
				}
			}
		}
		return 300;
	}

	private final Font arial = new Font("Arial", 1, 17);
	private final Color transGreen = new Color(24, 255, 0, 120);
	private final BasicStroke stroke1 = new BasicStroke(1);

	private Timer runTime = new Timer(0);

	private int x, y;
	
	Image paint = getImage("http://s21.postimg.org/4pnqn1jyf/Paint.png"); 

	//Method to import
	private Image getImage(String url) {
	         try { return ImageIO.read(new URL(url)); } 
	         catch(IOException e) { return null; }
	       }
	
	@Override
	public void repaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		xpGained = (ctx.skills.getExperience(Skills.MINING) - startXP);
		xpPerHour = (int) (((xpGained * 3600000D) / (System.currentTimeMillis() - startTime)));
		g.drawImage(paint, 100, 400, null);
		g.drawRect(10, 232, 186, 263);
		g.setFont(arial);
		g.drawString("" + xpGained, 490, 512);
		g.drawString("" + xpPerHour, 490, 555);
		g.drawString("" + (ctx.skills.getRealLevel(Skills.MINING) + "(" + (ctx.skills.getRealLevel(Skills.MINING) - startMiningLvl)+ ")"), 15, 310);
		g.drawString("" + oresMined, 200, 512);
		g.drawString("" + runTime.toElapsedString(), 320, 589);
		x = ctx.mouse.getLocation().x;
		y = ctx.mouse.getLocation().y;
		g.setColor(Color.white);
		g.drawLine(x + 5, y + 5, x - 5, y - 5);
		g.drawLine(x - 5, y + 5, x + 5, y - 5);

	}

	@Override
	public void messaged(MessageEvent message) {
		String msg = message.getMessage();
		if (msg.contains("You manage to mine") && message.getSender().equals("")) {
			oresMined++;
		}

	}

}
