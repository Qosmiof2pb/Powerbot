package com.qosmiof2.script.node;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Npc;

import com.qosmiof2.script.enums.Ores;

public class Bank extends Node{

	public boolean bank = false;
	
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
		for(Npc npc : ctx.npcs.select().name("Bank").nearest().first()){
			if(bank){
				ctx.movement.stepTowards(npc.getLocation());
				Timer walking = new Timer(120000);
				while(walking.isRunning() && ctx.players.local().isInMotion()){
					sleep(500);
				}
			}
			
			
			if(ctx.movement.isReachable(ctx.players.local(), npc)){
				npc.interact("Bank");
				for(int i = 0; i < 100 && !ctx.bank.isOpen(); i++){
					sleep(1000);
				}
				if(ctx.bank.isOpen()){
					ctx.bank.deposit(1, 28);
				}
			}
		}
		
	}

}
