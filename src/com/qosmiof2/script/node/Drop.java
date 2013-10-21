package com.qosmiof2.script.node;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Item;

import com.qosmiof2.script.enums.Ores;

public class Drop extends Node{

	public Drop(MethodContext ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public boolean activate() {
		return Ores.selectedMethod.toString().contains("Drop") 
				&& ctx.backpack.select().count() == 28
				&& !ctx.players.local().isInMotion()
				&& ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() {
        for(final Item oreToDrop : ctx.backpack.select().id(Mine.id)){
            oreToDrop.interact("Drop");
            sleep(500,1000);
        }
		
	}

}
