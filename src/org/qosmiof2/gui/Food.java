package org.qosmiof2.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.qosmiof2.script.QThiever;


public class Food {
	private static JFrame frame = new JFrame(" QThiever by Qosmiof2");
	private static JPanel panel = new JPanel();
	private static JButton button = new JButton("Start");
	private static JTextField tf = new JTextField("Please enter the name of your food:");
	public static String foodName;
	private final static Font arial = new Font("Arial", 1, 12);
	
	public static void showGUI(){
		
		frame.setBounds(570, 300, 250, 190);
		frame.add(panel);
		frame.setVisible(true);
		
		panel.setLayout(null);
		panel.add(button);
		panel.add(tf);
		
		button.setBounds(10, 70, 210, 50);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				QThiever.started = true;
				foodName = tf.getText();
				frame.dispose();
				
			}
			
		});
		
		tf.setFont(arial);
		tf.setBounds(10, 10, 210, 30);
		
	}
}
