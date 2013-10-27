package com.qosmiof2.script.node;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Tile;

import com.qosmiof2.script.enums.Ores;

public class Bank extends Node {

	public Bank(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return Ores.selectedMethod.toString().contains("Bank")
				&& ctx.backpack.select().count() == 28
				&& ctx.players.local().getAnimation() == -1;
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
				for (final Item ores : ctx.backpack.select().id(Mine.id)) {
					ctx.bank.depositInventory();
					sleep(1000, 2000);
				}
			}
			
			if (ctx.backpack.select().count() <= 1 && ctx.bank.isOpen()) {
				for (int j = 0; j < 100 && ctx.bank.isOpen(); j++) {
					ctx.bank.close();
					sleep(700, 1000);
				}
			}

		}

	}

}
