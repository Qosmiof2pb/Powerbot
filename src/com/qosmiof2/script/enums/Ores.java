package com.qosmiof2.script.enums;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.qosmiof2.script.QMiner;
import com.qosmiof2.script.node.Mine;

public enum Ores {
	Copper(new int [] {436, 437,  11937, 11936, 11960, 11961, 11962,72098, 72099, 72100, 3027, 3229,   11937, 29230, 29231}),
	Tin(new int[] {438, 439, 11933,11957, 11958, 11959,72092, 72094,3038, 3245,11933, 11934, 11935,11933, 11934, 11935,29227, 29229}),
	Iron(new int[] {440, 441, 37309, 37308, 37307,11956, 11954,11955, 72081, 72082, 72083,37307, 37308, 37309, 29221, 29222, 29223,14856, 14857, 14858,2092, 2093,21281, 21282}),
	Clay(new int[]{434, 435, 15504, 15505,72075, 72077,10577, 10578}),
	Silver( new int [] {442, 443, /* Al Kharid */     37304, 37306,
    /* Varrock West */  11950, 11949, 11948,
    /* Fally Resource */29224, 29225, 29226,
    /* Edgeville */     229224, 29225, 29226}),
	Gold(new int[] { 444, 445, /* Al Kharid */     37310, 37312,
            /* Brimhaven */     2098, 2099}),
	Mithril(new int[] {447, 448,  /* Al Kharid */     11942, 11944,
            /* Draynor */       3041, 3280,
            /* Fally Resource */32428, 32429, 332440,
            /* Yanille */       11942, 11943}),
    Coal( new int[]{453, 454, 11930, 11932,
                /* Draynor */       3233, 3032,
                /* Barbarian */     11930, 11931, 11932,
                /* Fally Resource */32426, 32427, 32428,
                /* Yanille */       11930,
                /* Edgeville */     29215, 29216, 29217,
                /* NE Ardougne */   2096, 2097,
                /* SE Ardougne */   21287,
                /* Coal Trucks */   14850, 14851}),
	Adamantite(new int[] { 449, 450,               /* Al Kharid */     11939, 11941,
            /* Draynor */       3273, 3040,
            /* Edgeville */     29233, 29235}),
	Runite(new int[] {451, 452});

	public final int[] oreID;

	Ores(int[] id) {
		oreID = id;
	}

	public int[] oreID() {
		return oreID;
	}
	
	public enum Method {

		Bank("Bank"), Drop("Drop");

		private String chosen;

		Method(String chosenMethod) {
			chosen = chosenMethod;
		}

		public String toString() {
			return chosen;
		}
	}

	public static Method selectedMethod;
	public static Ores selected;

	public static String chosenMethodToUse;
	public static class GUI {
		
		public static JFrame frame = new JFrame("GUI");
		public static  JButton button = new JButton();
		public static JPanel panel = new JPanel();
		public static JLabel label = new JLabel();
		public static JComboBox<Ores> cb = new JComboBox<>(Ores.values());
		public static JComboBox<Method> cbMethod = new JComboBox<>(Method.values());
		public static ButtonGroup bg = new ButtonGroup();
		
		private static final Font arial = new Font("Arial", 1, 17);
		public static void showGUI(){
			
			frame.setBounds(350, 300, 265, 200);
			frame.setVisible(true);
			frame.add(panel);
			frame.setResizable(false);
			
			
			
			panel.setLayout(null);
			panel.add(label);
			panel.add(cb);
			panel.add(cbMethod);
			panel.add(button);
			
			label.setText("Please choose :");
			label.setBounds(70, 10, 400, 20);
			label.setFont(arial);
			
			cb.setBounds(10, 35, 120, 50);
			cb.setFont(arial);
			
			cbMethod.setBounds(135, 35, 107, 50);
			cbMethod.setFont(arial);
			
			button.setBounds(10, 100, 232, 55);
			button.setText("Start");
			button.setFont(arial);
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selected = (Ores) cb.getSelectedItem();
					selectedMethod = (Method) cbMethod.getSelectedItem();
					chosenMethodToUse = selectedMethod.toString();
					Mine.id = selected.oreID;
					System.out.println("Chosen ore: " + cb.getSelectedItem().toString());
					System.out.println("Chosen method: " + cbMethod.getSelectedItem());
					QMiner.started = true;
					frame.dispose();
				}
				
			});
			
		}
	}
}