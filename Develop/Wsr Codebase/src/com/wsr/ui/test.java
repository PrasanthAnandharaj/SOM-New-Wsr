package com.wsr.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class test {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 725, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 707, 448);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(null);
		
		JLabel test = new JLabel("Test");
		panel.add(test);
		System.out.println("Hello");
		
		tabbedPane.setLayout(null);
		
	/*	table = new JTable();
		table.setBounds(38, 47, 621, 312);
		
		DefaultTableModel drm = new DefaultTableModel();
		
		table.setModel(drm);
		
		JTableHeader updateHeader = table.getTableHeader();
		updateHeader.setBackground(Color.BLUE);
		updateHeader.setForeground(Color.WHITE);
	    
		drm.addColumn("Update #");
		drm.addColumn("Update Date");
		drm.addColumn("Update");
		
		panel.add(updateHeader);
		panel.add(table);*/
		
		frame.setVisible(true);
	}
}
