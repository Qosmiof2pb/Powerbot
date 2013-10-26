package org.qosmiof2.script;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.Random;
import org.powerbot.script.util.Timer;
import org.qosmiof2.enums.Food;
import org.qosmiof2.node.Bank;
import org.qosmiof2.node.Eat;
import org.qosmiof2.node.PickPocket;
import org.qosmiof2.node.Node;

@Manifest(authors = { "Qosmiof2" }, description = "Auto thiever", name = "QThiever")
public class QThiever extends PollingScript implements PaintListener {

	public static boolean started = false;
	private int xpStart;
	private int xpGained;

	private final ArrayList<Node> nodes = new ArrayList<>();

	public QThiever() {
		Collections.addAll(nodes, new Bank(ctx), new Eat(ctx), new PickPocket(
				ctx));
		Food.showGUI();
	}

	@Override
	public void start() {
		if (ctx.game.isLoggedIn()) {
			xpStart = ctx.skills.getExperience(Skills.THIEVING);
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

	private Timer running = new Timer(0);
	private final Font font = new Font("Arial", 2, 150);
	private final Font normalFont = new Font("Arial", 1, 50);

	@Override
	public void repaint(Graphics g1) {
		Graphics g = (Graphics) g1;
		xpGained = ctx.skills.getExperience(Skills.THIEVING) - xpStart;
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("" + running.toElapsedString(), 100, 300);
		g.setFont(normalFont);
		g.drawString("XP Gained: " + xpGained, 20, 80);

	}

}
