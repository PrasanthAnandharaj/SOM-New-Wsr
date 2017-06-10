package com.wsr.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class relatedTktDetails extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBxDefectId;
	private JTextField txtBxProbId;
	private JTextField txtBxSecIncId;
	private JCheckBox chckbxDfcId;
	private JCheckBox chckbxSecInc;
	private JCheckBox chckbxPrbId;
	private JButton okButton;
	private JButton cancelButton;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			relatedTktDetails dialog = new relatedTktDetails();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public relatedTktDetails() {
		setBackground(new Color(230, 230, 250));
		setTitle("Provide Associated Ticket Details !!");
		setResizable(false);
		setBounds(100, 100, 500, 325);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(250, 250, 210));
		contentPanel.setForeground(new Color(250, 250, 210));
		contentPanel.setBorder(new LineBorder(new Color(255, 69, 0), 2));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		chckbxSecInc = new JCheckBox("Secondary Incident Id :");
		chckbxSecInc.setBackground(new Color(250, 250, 210));
		chckbxSecInc.setFont(new Font("DialogInput", Font.BOLD, 14));
		chckbxSecInc.setBounds(32, 187, 216, 25);
		contentPanel.add(chckbxSecInc);
		
		chckbxDfcId = new JCheckBox("Associated  Defect Id :");
		chckbxDfcId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxDfcId.isSelected() == false)				
					txtBxDefectId.setEnabled(false);
				else
					txtBxDefectId.setEnabled(true);
			}
		});
		chckbxDfcId.setFont(new Font("DialogInput", Font.BOLD, 14));
		chckbxDfcId.setBackground(new Color(250, 250, 210));
		chckbxDfcId.setBounds(32, 50, 216, 25);
		contentPanel.add(chckbxDfcId);
		{
			chckbxPrbId = new JCheckBox(" Problem Ticket Id    :");
			chckbxPrbId.setFont(new Font("DialogInput", Font.BOLD, 14));
			chckbxPrbId.setBackground(new Color(250, 250, 210));
			chckbxPrbId.setBounds(32, 110, 216, 37);
			contentPanel.add(chckbxPrbId);
		}
		{
			txtBxDefectId = new JTextField();
			txtBxDefectId.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					if(!("").equals(txtBxDefectId.getText().trim()) && okButton.isEnabled() == false){
						okButton.setEnabled(true);
					}
				}
			});
			txtBxDefectId.setBounds(256, 51, 197, 22);
			contentPanel.add(txtBxDefectId);
			txtBxDefectId.setColumns(10);
			txtBxDefectId.setEnabled(false);
		}
		{
			txtBxProbId = new JTextField();
			txtBxProbId.setColumns(10);
			txtBxProbId.setBounds(256, 117, 197, 22);
			contentPanel.add(txtBxProbId);
			txtBxProbId.setEnabled(false);
		}
		{
			txtBxSecIncId = new JTextField();
			txtBxSecIncId.setBounds(256, 188, 197, 22);
			contentPanel.add(txtBxSecIncId);
			txtBxSecIncId.setColumns(10);
			txtBxSecIncId.setEnabled(false);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 160, 122));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Update");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				okButton.setForeground(new Color(160, 82, 45));
				okButton.setBackground(SystemColor.inactiveCaptionBorder);
				okButton.setFont(new Font("Verdana", Font.BOLD, 14));
				okButton.setActionCommand("OK");
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setBackground(SystemColor.inactiveCaptionBorder);
				cancelButton.setForeground(new Color(160, 82, 45));
				cancelButton.setFont(new Font("Verdana", Font.BOLD, 14));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
