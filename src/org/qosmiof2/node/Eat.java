package org.qosmiof2.node;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Item;
import org.qosmiof2.enums.Food;

public class Eat extends Node {

	public Eat(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.players.local().getHealthPercent() <= 19;
	}

	@Override
	public void execute() {
		for (Item food : ctx.backpack.select().name(Food.foodName).first()) {
			if (ctx.backpack.contains(food)) {
				food.interact("Eat");
				Timer wait = new Timer(2000);
				while (wait.isRunning()
						&& ctx.players.local().getHealthPercent() <= 30) {
					sleep(1000);
				}
			} else {
				Bank.bank = true;
			}
		}

	}

}
