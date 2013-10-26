package org.qosmiof2.node;

import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.Bank.Amount;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Tile;
import org.qosmiof2.enums.Food;

import com.qosmiof2.script.node.Mine;

public class Bank extends Node {

	public static boolean bank = false;

	public Bank(MethodContext ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean activate() {
		return bank;
	}

	@Override
	public void execute() {
		for (Npc npc : ctx.npcs.select().name("Banker").nearest().first()) {
			if (ctx.movement.getDistance(npc.getLocation(), ctx.players.local()
					.getLocation()) >= 10) {
				ctx.movement.stepTowards(new Tile((npc.getLocation().x + 5),
						(npc.getLocation().y + 1), 0));
				Timer walking = new Timer(120000);
				while (walking.isRunning() && ctx.players.local().isInMotion()) {
					sleep(500);
				}
			}

			for (int i = 0; i < 100 && !ctx.bank.isOpen(); i++) {
				npc.interact("Bank");
				sleep(1000);
			}
			if (ctx.bank.isOpen()) {

				for (Item i : ctx.bank.select()
						.name(Food.foodName)
						.first()) {
					ctx.bank.withdraw(i.getId(), Amount.ALL);
				}

				if (ctx.backpack.select().count() >= 27 && ctx.bank.isOpen()) {
					for (int j = 0; j < 100 && ctx.bank.isOpen(); j++) {
						ctx.bank.close();
						sleep(700, 1000);
					}
				}

			}
			bank = false;

		}

	}

}
