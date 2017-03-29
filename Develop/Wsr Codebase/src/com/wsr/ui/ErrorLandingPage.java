package com.wsr.ui;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

public class ErrorLandingPage {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErrorLandingPage window = new ErrorLandingPage();
					window.initialize();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		
		
		JOptionPane.showMessageDialog(null, "You are not an Authorised User !! Kindly Contact the Administrator for Access ..", "Access Denied !!", JOptionPane.ERROR_MESSAGE);
		
		
	}

}
