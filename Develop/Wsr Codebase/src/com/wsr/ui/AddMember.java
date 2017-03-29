package com.wsr.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class AddMember {

	private JFrame frame;
	private JTextField txtUserID;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmailId;
	private JTextField txtcontactnum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMember window = new AddMember();
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
	public AddMember() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	System.out.println(info.getName());
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		           
		            break;
		        }
		        
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 749, 644);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 140, 0));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(250, 235, 215));
		panel_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 165, 0)));
		panel_2.setBounds(88, 17, 556, 541);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(250, 235, 215));
		panel_1.setForeground(new Color(255, 255, 0));
		panel_1.setBounds(6, 13, 544, 515);
		panel_2.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAddMember = new JLabel("ADD MEMBER SCREEN");
		lblAddMember.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
		lblAddMember.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddMember.setBounds(141, 29, 238, 28);
		panel_1.add(lblAddMember);
		
		JLabel lblUserId = new JLabel("User ID :");
		lblUserId.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUserId.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserId.setBounds(71, 88, 120, 28);
		panel_1.add(lblUserId);
		
		txtUserID = new JTextField();
		txtUserID.setBounds(218, 88, 238, 28);
		panel_1.add(txtUserID);
		txtUserID.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name :");
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblFirstName.setBounds(71, 140, 120, 28);
		panel_1.add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(218, 140, 238, 28);
		panel_1.add(txtFirstName);
		
		JLabel lblLastName = new JLabel("Last Name :");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLastName.setBounds(71, 190, 120, 28);
		panel_1.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(218, 193, 238, 28);
		panel_1.add(txtLastName);
		
		JLabel lblEmailId = new JLabel("Email Id :");
		lblEmailId.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmailId.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmailId.setBounds(71, 243, 120, 28);
		panel_1.add(lblEmailId);
		
		txtEmailId = new JTextField();
		txtEmailId.setBounds(218, 245, 238, 26);
		panel_1.add(txtEmailId);
		txtEmailId.setColumns(10);
		
		JLabel lblContactNumber = new JLabel("Contact Number :");
		lblContactNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblContactNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblContactNumber.setBounds(59, 298, 134, 28);
		panel_1.add(lblContactNumber);
		
		txtcontactnum = new JTextField();
		txtcontactnum.setBounds(217, 300, 239, 25);
		panel_1.add(txtcontactnum);
		txtcontactnum.setColumns(10);
		
		JLabel lblRole = new JLabel("Role :");
		lblRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblRole.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblRole.setBounds(71, 353, 120, 28);
		panel_1.add(lblRole);
		
		JComboBox cmbrole = new JComboBox();
		cmbrole.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Manager", "Normal User", "SuperUser", "Application Specialist"}));
		cmbrole.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		cmbrole.setBounds(218, 356, 238, 22);
		panel_1.add(cmbrole);
		
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 12));
		btnAddMember.setBounds(124, 436, 120, 42);
		panel_1.add(btnAddMember);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 12));
		btnCancel.setBounds(278, 436, 120, 42);
		panel_1.add(btnCancel);
	}
}
