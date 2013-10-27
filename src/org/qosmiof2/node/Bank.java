package org.qosmiof2.node;

import org.powerbot.script.lang.Filter;
import org.powerbot.script.lang.ItemQuery;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.Bank.Amount;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Tile;
import org.qosmiof2.gui.Food;

import com.qosmiof2.script.node.Mine;

public class Bank extends Node {

	private ItemQuery<Item> item;

	public Bank(MethodContext ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().name(Food.foodName).first().isEmpty();
	}

	@Override
	public void execute() {
			if (ctx.movement.getDistance(ctx.bank.getNearest().getLocation(), ctx.players.local()
					.getLocation()) >= 10) {
				ctx.movement.stepTowards(new Tile((ctx.bank.getNearest().getLocation().getLocation().x + 5),
						(ctx.bank.getNearest().getLocation().y + 1), 0));
				Timer walking = new Timer(120000);
				while (walking.isRunning() && ctx.players.local().isInMotion()) {
					sleep(500);
				}
			}

			for (int i = 0; i < 100 && !ctx.bank.isOpen(); i++) {
				ctx.bank.open();
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

		}

	}
