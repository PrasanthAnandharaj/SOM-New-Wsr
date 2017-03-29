package com.wsr.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class test2 extends JFrame{

	private JFrame frame;
	JPanel panel2,panelTest; 
	JTabbedPane tabs;
	
	JLabel L1,L2;
	JTable table;
	JScrollPane panel1,scrPane;
	JTableHeader header;
	DefaultTableModel dtm = new DefaultTableModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test2 window = new test2();
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
	public test2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabs = new JTabbedPane();
		
		JPanel topPane  = new JPanel();
		topPane.setLayout(new BorderLayout());
		frame.getContentPane().add(topPane);
		
		
		insideTab();
		
		tabs.addTab("Tb1", panelTest);	
		tabs.addTab("Tb2", null);
		topPane.add(tabs);
		
		frame.add(tabs);
	
	}

	private void insideTab() {
		
		panelTest = new JPanel();
	    panelTest.setLayout( new BorderLayout() );
	    
	    JScrollPane scrollPanel = new JScrollPane();
	    table = new JTable();
		table.setModel(dtm);
		header = table.getTableHeader();
		
		dtm.addColumn("Incident ID");
		dtm.addColumn("Title");
	  
		
		//scrollPanel.setViewportView(table);
		scrollPanel.setViewportView(new JLabel("hellossssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"));
		
	    panelTest.add(scrollPanel,BorderLayout.CENTER);
	}

}
