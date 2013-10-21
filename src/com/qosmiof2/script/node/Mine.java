package com.qosmiof2.script.node;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Player;

import com.qosmiof2.script.enums.Ores;

public class Mine extends Node {

	public Mine(MethodContext ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public static int[] id;

	@Override
	public boolean activate() {
		return ctx.players.local().getAnimation() == -1
				&& ctx.backpack.select().count() <= 27
				&& !ctx.players.local().isInMotion();
	}

	@Override
	public void execute() {
		Player me = ctx.players.local();

		for (GameObject rock : ctx.objects.select().id(id).nearest().first()) {
			if (me.getLocation().distanceTo(rock) < 15) {
				if (rock.isOnScreen()) {
					if (rock.interact("Mine")) {
						Timer walk = new Timer(4000);
						while (walk.isRunning() && me.getAnimation() == -1
								&& rock.isValid()) {
							sleep(50);
						}

						Timer wait = new Timer(10000);
						while (wait.isRunning() && me.getAnimation() != -1
								&& rock.isValid()) {
							sleep(50);
						}
					}
				} else {
					ctx.camera.turnTo(rock);
					ctx.camera.setPitch(Random.nextInt(3, 99));
				}
			} else {
				ctx.movement.stepTowards(rock.getLocation());
				sleep(800, 1200);
				Timer walking = new Timer(6000);
				while (walking.isRunning()
						&& me.isInMotion()
						&& me.getLocation().distanceTo(
								ctx.movement.getDestination()) > 5) {
					sleep(50);
				}
			}
		}
	}

}