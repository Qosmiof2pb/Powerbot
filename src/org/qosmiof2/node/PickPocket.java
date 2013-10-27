package org.qosmiof2.node;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.Random;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Npc;

public class PickPocket extends Node {
	
	private int lvl = 1;
	private String npc;
	
	public PickPocket(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.players.local().getAnimation() == -1
				&& !ctx.players.local().isInMotion()
				&& ctx.players.local().getHealthPercent() >= 20;
	}

	@Override
	public void execute() {
		lvl = ctx.skills.getRealLevel(Skills.THIEVING);
		
		if(lvl <= 10){
			npc = "Man";
		} else if((lvl > 10) && (lvl <= 20)){	
			npc = "Farmer";
		} else if((lvl > 20) && (lvl <= 25)){
			npc = "Male H.A.M. follower";
		} else if((lvl > 25) && (lvl <= 38)){
			npc = "Warrior";
		} else if((lvl > 38) && (lvl <= 40)){
			npc = "Guard";
		} else if((lvl > 40) && (lvl <= 55)){
			npc = "Knight";
		} else if((lvl > 55) && (lvl <= 65)){
			npc = "Menaphite thug";
		} else if((lvl > 70) && (lvl <= 80)){
			npc = "Paladin";
		} else if((lvl > 80) && (lvl <= 90)){
			npc = "Hero";
		} else if((lvl > 90) && (lvl <= 99)){
			npc = "Dwarf trader";
		}
		
		
		for(Npc pocket : ctx.npcs.select().name(npc).first()){
			if (ctx.players.local().getLocation().distanceTo(pocket) < 15) {
				if (pocket.isOnScreen() && ctx.players.local().getAnimation() == -1) {
					if (pocket.interact("Pickpocket")) {
						Timer walk = new Timer(4000);
						while (walk.isRunning() && ctx.players.local().getAnimation() == -1
								&& pocket.isValid()) {
							sleep(50);
						}

						Timer wait = new Timer(5000);
						while (wait.isRunning() && ctx.players.local().getAnimation() != -1
								&& pocket.isValid()) {
							sleep(50);
						}
					}
				} else {
					ctx.camera.turnTo(pocket);
					ctx.camera.setPitch(Random.nextInt(3, 99));
				}
			} else {
				ctx.movement.stepTowards(pocket.getLocation());
				sleep(800, 1200);
				Timer walking = new Timer(6000);
				while (walking.isRunning()
						&& ctx.players.local().isInMotion()
						&& ctx.players.local().getLocation().distanceTo(
								ctx.movement.getDestination()) > 5) {
					sleep(50);
				}
			}
			}//On screen
		}
		
	}
