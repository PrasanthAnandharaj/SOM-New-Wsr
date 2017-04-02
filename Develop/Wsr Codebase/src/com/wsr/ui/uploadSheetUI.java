package com.wsr.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFileChooser;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.opencsv.CSVReader;
import com.wsr.controller.ManagerTasksController;


public class uploadSheetUI {

	private JButton btnOpen;
	private CSVReader reader;
	private JFileChooser fileChooser;
	private JTextArea tASelectedFileLoc;
	private JFrame frmUploadTicketSheets;
	
	private final String CSV_FORMAT = ".csv";
/*	private final String XL_FORMAT = ".xl";
	private final String XLSX_FORMAT = ".xls";
	private final String XLM_FORMAT = ".xlm";*/
	private String selectedFileLocation = "";
	
	ManagerTasksController managerTaskController = new ManagerTasksController();
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void showUploadScreen() {
		
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
		
		frmUploadTicketSheets = new JFrame();
		frmUploadTicketSheets.setType(Type.NORMAL);
		frmUploadTicketSheets.setResizable(false);
		frmUploadTicketSheets.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"\\Develop\\Wsr Codebase\\Resources\\logo_passbook.png"));
		frmUploadTicketSheets.setTitle("Upload Ticket Sheets");
		frmUploadTicketSheets.setBounds(100, 100, 450, 232);
		frmUploadTicketSheets.getContentPane().setLayout(null);
		
		
		//fileChooser.s
		
		JPanel pnlMainPnl = new JPanel();
		pnlMainPnl.setBounds(12, 13, 420, 178);
		frmUploadTicketSheets.getContentPane().add(pnlMainPnl);

		pnlMainPnl.setLayout(null);
		
		tASelectedFileLoc = new JTextArea();
		tASelectedFileLoc.setForeground(new Color(148, 0, 211));
		tASelectedFileLoc.setWrapStyleWord(true);
		tASelectedFileLoc.setLineWrap(true);
		tASelectedFileLoc.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		tASelectedFileLoc.setBackground(new Color(255, 250, 205));
		tASelectedFileLoc.setBounds(30, 13, 269, 101);
		pnlMainPnl.add(tASelectedFileLoc);
		
		btnOpen = new JButton("Select");
		btnOpen.setForeground(new Color(148, 0, 211));
		btnOpen.setBackground(new Color(211, 211, 211));
		btnOpen.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(btnOpen);
				fileChooser.setCurrentDirectory(new java.io.File("Libraries\\Documents"));
				fileChooser.setDialogTitle("Choose to upload !!");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				tASelectedFileLoc.setText("");
				
			//	System.out.println("Return val here :"+returnVal);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					
				//	System.out.println("Selected File's Path : "+fileChooser.getSelectedFile().getAbsolutePath());
					selectedFileLocation = fileChooser.getSelectedFile().getAbsolutePath();
					if(selectedFileLocation.contains(CSV_FORMAT)){
						
						tASelectedFileLoc.setText(fileChooser.getSelectedFile().getAbsolutePath());
						
					}else{
						
						JOptionPane.showMessageDialog(frmUploadTicketSheets, "Please provide CSV format file !!", "Wrong File", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		btnOpen.setBounds(311, 27, 97, 25);
		pnlMainPnl.add(btnOpen);
		
		JButton btnPopulate = new JButton("Populate New Tickets");
		btnPopulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean updateFlag = false;
				try{
					reader = new CSVReader(new FileReader(selectedFileLocation));
					
					// Passing the reader value to controller
					
					 updateFlag = managerTaskController.IterateThroughOpenSheet(reader);
					
				}
				catch (FileNotFoundException e) {
		                System.err.println(e.getMessage());
		        }
		        catch (IOException e) {
		                System.err.println(e.getMessage());
		        }
				catch (ParseException e) {
						System.err.println(e.getMessage());
				}
				
				if(updateFlag){
					
					JOptionPane.showMessageDialog(frmUploadTicketSheets, "Sheet Uploaded Successfuly", "uploaded", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(frmUploadTicketSheets, "Failed to Upload in WSR", "Failed uploading", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnPopulate.setForeground(new Color(148, 0, 211));
		btnPopulate.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		btnPopulate.setBounds(50, 127, 234, 25);
		pnlMainPnl.add(btnPopulate);
		
		JButton btnClose = new JButton("Close");
		btnClose.setForeground(new Color(148, 0, 211));
		btnClose.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		btnClose.setBackground(new Color(211, 211, 211));
		btnClose.setBounds(311, 71, 97, 25);
		pnlMainPnl.add(btnClose);
		
		frmUploadTicketSheets.setVisible(true);
		frmUploadTicketSheets.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
