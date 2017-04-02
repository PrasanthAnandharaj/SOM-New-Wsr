package com.wsr.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.wsr.controller.StaffActivitiesController;
import com.wsr.model.TicketUpdateBean;


public class BussinessReviewSheet {

	private JFrame frame;

	
	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void BussinessReviewSheetUI() {
		frame = new JFrame();
		frame.setType(Type.NORMAL);
		frame.setResizable(false);
		frame.setTitle("Update Tickets");
		frame.setBounds(100, 100, 450, 232);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"\\Develop\\Wsr Codebase\\Resources\\logo_passbook.png"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 420, 178);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setForeground(new Color(148, 0, 211));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		textArea.setBackground(new Color(255, 250, 205));
		textArea.setBounds(30, 13, 269, 101);
		panel.add(textArea);
		
		final JButton btnSelect = new JButton("Select");
		btnSelect.setForeground(new Color(148, 0, 211));
		btnSelect.setBackground(new Color(211, 211, 211));
		btnSelect.setFont(new Font("Futura Medium", Font.PLAIN, 16));

		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fileChooser = new JFileChooser();

                int returnVal = fileChooser.showOpenDialog(btnSelect);

                fileChooser.setCurrentDirectory(new java.io.File("Libraries\\Documents"));

                fileChooser.setDialogTitle("Choose to upload !!");

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                textArea.setText("");

                if(returnVal == JFileChooser.APPROVE_OPTION){

                    

                    //      System.out.println("Selected File's Path : "+fileChooser.getSelectedFile().getAbsolutePath());

                	String selectedFileLocation = fileChooser.getSelectedFile().getAbsolutePath();
                         
							if(selectedFileLocation.contains(".xls")){

                                    
                            	textArea.setText(fileChooser.getSelectedFile().getAbsolutePath());

                                    

                            }else{

                                    
                                    JOptionPane.showMessageDialog(frame, "Please provide xls format file !!", "Wrong File", JOptionPane.ERROR_MESSAGE);

                            }

                    }

                    

            }

    });


		btnSelect.setBounds(311, 40, 97, 35);
		panel.add(btnSelect);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(148, 0, 211));
		btnUpdate.setBackground(new Color(211, 211, 211));
		btnUpdate.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		btnUpdate.setBounds(50, 80, 80, 20);
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = "";
				int incidentIDCellIndex = 0;
				int supportActivitiesCellIndex = 0;
				filePath = textArea.getText();
				int incidentcount = 0;
				int toBeUpdatedIncidentCount = 0;
				String incidentYetToBeUpdated = "empty";
				try{					
					File myFile = new File(filePath);
					FileInputStream fileInputStream = new FileInputStream(myFile);
					FileOutputStream os;
					
					
					//Finds the workbook instance for XLSX file
					HSSFWorkbook myWorkBook = new HSSFWorkbook (fileInputStream);
					
					// Return first sheet from the XLSX workbook
					HSSFSheet mySheet = myWorkBook.getSheetAt(0);
					
					//FormulaEvaluator myFormulaEvaluator = myWorkBook.getCreationHelper().createFormulaEvaluator();
					
					//Get iterator to all the rows in current sheet
					Iterator<Row> rowIterator = mySheet.iterator();
					
					//Traversing over each row of XLSX file					
					while (rowIterator.hasNext()) {
		                Row row = rowIterator.next();		                
		                	short rowLength =row.getLastCellNum();   
		                	//System.out.println("rowLength: "+ rowLength);
		                	
		                
		      		        String incidentID;
		                	
		      		        if(incidentIDCellIndex == 0 || supportActivitiesCellIndex ==0){
		                	for(int i=0; i<rowLength; i++){
		                		Cell getVal = row.getCell(i);
		                		if(getVal.toString().equalsIgnoreCase("Incident")){
		                			incidentIDCellIndex = row.getCell(i).getColumnIndex();
		                		}
		                		if(getVal.toString().equalsIgnoreCase("Support Activities")){
		                			supportActivitiesCellIndex = row.getCell(i).getColumnIndex();
		                		}
		                	}
		      		       }
		                	Cell incidentVal = row.getCell(incidentIDCellIndex);
		                	incidentID = incidentVal.toString();
		      		        //just skip the rows if row number is 0 or 1
		      		        	if(incidentID.startsWith("GIM")){
		      		        		
		      		        		StaffActivitiesController flowControllerObject = new StaffActivitiesController();
		      		        		List<TicketUpdateBean> listOfComments = flowControllerObject.getTicketUpdates(incidentID);	
		      		        		if(listOfComments == null){
		      		        			toBeUpdatedIncidentCount++;
		      		        			if(toBeUpdatedIncidentCount > 1){
		      		        				incidentYetToBeUpdated = "";
		      		        			incidentYetToBeUpdated = incidentYetToBeUpdated.concat(","+incidentID);
		      		        			}
		      		        			else{
		      		        				incidentYetToBeUpdated = "";
		      		        				incidentYetToBeUpdated = incidentYetToBeUpdated.concat(incidentID);	
		      		        			}
		      		        		}
		      		        		
		      		        		else{
		      		        		incidentcount++;
		      		        		//get Comments from TicketUpdateBean 
		      		        		TicketUpdateBean updateBean = new TicketUpdateBean();		               	
		      		        		int commentCount = listOfComments.size();	
		      		        		
		      		        		String updatedComments = "";
		      		        		commentCount=commentCount-1;
		      		        			      		        		
		      		        		for(int i = 0; commentCount >= i ; commentCount--){		            	   		            	  		      		        			
		      		        			updatedComments = updatedComments.concat(listOfComments.get(commentCount).getUpdateDate() + ": "  +listOfComments.get(commentCount).getComment()+"\n \n");		      		        			
		      		        		}
		      		        		
		      		        		Cell finalupdate = row.getCell(supportActivitiesCellIndex);
		      		        		finalupdate.setCellValue(updatedComments);
	      		        				      		        			
	      					     System.out.println("Writing on Excel file Finished ...");
	      					     
	      		        			os = new FileOutputStream(myFile);
	      		        			myWorkBook.write(os);
	      		        			os.close();		      		        				      		             
		      		        	}
		      		        	
		      		        	}
					}
					if(incidentYetToBeUpdated != "empty"){
						JOptionPane.showMessageDialog(frame, "Please Update Ticket: "+ incidentYetToBeUpdated + " in WSR", "Incident To Be Updated", JOptionPane.ERROR_MESSAGE);
					}
					myWorkBook.close();
		         	fileInputStream.close(); 
		         	JOptionPane.showMessageDialog(frame, "No of Tickets Updated: "+ incidentcount + "\nNo of Tickets yet to be updated:"+ toBeUpdatedIncidentCount, "Incident Count", JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
					//System.out.println(ex.getMessage());
				}			
			}
		});
		btnUpdate.setBounds(40, 127, 234, 25);
		panel.add(btnUpdate);
		frame.setVisible(true);
	}
}
