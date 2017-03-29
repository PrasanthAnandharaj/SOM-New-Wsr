package com.wsr.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.wsr.controller.MemberManagementController;
import com.wsr.model.MemberBean;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberManagementScreen {

	private JFrame frame;
	private JTable tblMemberDet;
	
	MemberManagementController memberController = new MemberManagementController();
	
	private JTextField txtUserid;
	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtEmail;
	private JComboBox cmbRole;
	private JTextField txtContactnum;
	private JTextField txtAliasId;

	/**
	 * @param userLoggedInAs 
	 * @wbp.parser.entryPoint
	 */
	public void displayMemberManagementScreen(String userLoggedInAs) {
		
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
		frame.setBounds(100, 100, 1400, 700);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel pnlMember = new JPanel();
		pnlMember.setBounds(0, 13, 1382, 371);
		frame.getContentPane().add(pnlMember);
		pnlMember.setLayout(null);
		
		JLabel lblMemberHeading = new JLabel("Member Management Screen");
		lblMemberHeading.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblMemberHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblMemberHeading.setBounds(572, 13, 212, 21);
		pnlMember.add(lblMemberHeading);
		
		JScrollPane scrpnMemberTable = new JScrollPane();
		scrpnMemberTable.setBounds(12, 36, 1358, 322);
		pnlMember.add(scrpnMemberTable);
		
		tblMemberDet = new JTable();
		tblMemberDet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblMemberDet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				List<String> selectedRowValues = new ArrayList<>();
				selectedRowValues= getSelectedRowValues(selectedRowValues);
				
				editInputFields(selectedRowValues);
				//Locking the UserId as it should not be changed
				txtUserid.setEditable(false);
				
				//Clearing the List..
				selectedRowValues.clear();
				
			}

			private List<String> getSelectedRowValues(List<String> selectedRowValues) {
				
				for(int i=0;i<tblMemberDet.getColumnCount();i++){
					
					selectedRowValues.add(tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), i).toString());
				}
				/*
				String selected_UserId = tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), 0).toString();
				String selected_FirstName = tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), 1).toString();
				String selected_LastName = tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), 2).toString();
				String selected_Role = tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), 3).toString();
				String selected_EmailId = tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), 4).toString();
				String selected_ContactId = tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), 5).toString();
				String selected_AliasId = tblMemberDet.getValueAt(tblMemberDet.getSelectedRow(), 6).toString();*/

				return selectedRowValues;
				
			}
			
			private void editInputFields(List<String> selectedRowValues) {
				
				txtUserid.setText(selectedRowValues.get(0));
				txtFirstname.setText(selectedRowValues.get(1));
				txtLastname.setText(selectedRowValues.get(2));
				cmbRole.setSelectedItem(selectedRowValues.get(3));
				txtEmail.setText(selectedRowValues.get(4));
				txtContactnum.setText(selectedRowValues.get(5));
				txtAliasId.setText(selectedRowValues.get(6));
				
				selectedRowValues.clear();
			}
		});
		scrpnMemberTable.setViewportView(tblMemberDet);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panel.setBounds(12, 389, 1358, 253);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel pnlMemberDetails = new JPanel();
		pnlMemberDetails.setBackground(new Color(240, 248, 255));
		pnlMemberDetails.setBounds(12, 13, 1334, 227);
		panel.add(pnlMemberDetails);
		pnlMemberDetails.setLayout(null);
		
		JLabel lblUserId = new JLabel("User Id :");
		lblUserId.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserId.setBounds(46, 13, 105, 23);
		pnlMemberDetails.add(lblUserId);
		
		txtUserid = new JTextField();
		txtUserid.setBounds(187, 13, 180, 22);
		pnlMemberDetails.add(txtUserid);
		txtUserid.setColumns(10);
		
		JLabel lblFirstname = new JLabel("First Name :");
		lblFirstname.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstname.setBounds(46, 67, 105, 23);
		pnlMemberDetails.add(lblFirstname);
		
		txtFirstname = new JTextField();
		txtFirstname.setColumns(10);
		txtFirstname.setBounds(187, 67, 180, 22);
		pnlMemberDetails.add(txtFirstname);
		
		JLabel lblLastname = new JLabel("Last Name :");
		lblLastname.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastname.setBounds(46, 117, 105, 23);
		pnlMemberDetails.add(lblLastname);
		
		txtLastname = new JTextField();
		txtLastname.setColumns(10);
		txtLastname.setBounds(187, 127, 180, 22);
		pnlMemberDetails.add(txtLastname);
		
		JLabel lblEmail = new JLabel("Email Id :");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(46, 176, 105, 23);
		pnlMemberDetails.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(187, 176, 180, 22);
		pnlMemberDetails.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblRole = new JLabel("Role :");
		lblRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblRole.setBounds(482, 31, 105, 23);
		pnlMemberDetails.add(lblRole);
		
		cmbRole = new JComboBox();
		cmbRole.setForeground(Color.RED);
		cmbRole.setBackground(Color.WHITE);
		cmbRole.setModel(new DefaultComboBoxModel(new String[] {"<<<Please Select>>>", "Manager", "Admin", "Normal User", "SuperUser", "Application Specialist"}));
		cmbRole.setBounds(622, 29, 203, 26);
		pnlMemberDetails.add(cmbRole);
		
		JLabel lblContact = new JLabel("Contact Num :");
		lblContact.setHorizontalAlignment(SwingConstants.CENTER);
		lblContact.setBounds(482, 94, 105, 23);
		pnlMemberDetails.add(lblContact);
		
		txtContactnum = new JTextField();
		txtContactnum.setColumns(10);
		txtContactnum.setBounds(620, 94, 205, 22);
		pnlMemberDetails.add(txtContactnum);
		
		JLabel lblAliasid = new JLabel("Alias ID :");
		lblAliasid.setHorizontalAlignment(SwingConstants.CENTER);
		lblAliasid.setBounds(482, 159, 105, 23);
		pnlMemberDetails.add(lblAliasid);
		
		txtAliasId = new JTextField();
		txtAliasId.setColumns(10);
		txtAliasId.setBounds(622, 159, 203, 22);
		pnlMemberDetails.add(txtAliasId);
		
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtUserid.isEditable() == false){
				
					//To prevent, add clicking add member while using Edit Screen..
					JOptionPane.showMessageDialog(frame, "Please use the <<Add Member>> Screen for this purpose");
			
				}else{
				
					List<String> addMemberInputDetails = new ArrayList<String>();
				
					addMemberInputDetails.add(txtUserid.getText().trim());
					addMemberInputDetails.add(txtFirstname.getText().trim());
					addMemberInputDetails.add(txtLastname.getText().trim());
					addMemberInputDetails.add(txtEmail.getText().trim());
					addMemberInputDetails.add(cmbRole.getSelectedItem().toString());
					System.out.println("---"+addMemberInputDetails.get(4));
					addMemberInputDetails.add(txtContactnum.getText().trim());
					addMemberInputDetails.add(txtAliasId.getText().trim());
				
					try{
						
						if(checkInputs(addMemberInputDetails) == true){
				
							boolean insertSuccess = memberController.addMember(addMemberInputDetails);
							if(insertSuccess == true){
							
								displayDefaultTable();
								JOptionPane.showMessageDialog(frame, addMemberInputDetails.get(0)+" has been added Successfully !!");
							 
							}else{
							
								JOptionPane.showMessageDialog(frame, addMemberInputDetails.get(0)+" can not be added..If user exists, please delete adn then add (OR) Please check the input parameters..!!");
							}
						}
					}catch(Exception e){
						
						JOptionPane.showMessageDialog(frame, e.getMessage());
					
					}finally{
						
						txtUserid.setEditable(true);
						clearFieldValues();
					}
				}
		
			}
		});
		btnAddMember.setBackground(Color.WHITE);
		btnAddMember.setForeground(Color.RED);
		btnAddMember.setBounds(979, 21, 124, 42);
		pnlMemberDetails.add(btnAddMember);
		
		JButton btnEdit = new JButton("Update");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Checking whether the User ID is disabled to confirm that the flow is in Updating the 
				if(txtUserid.isEditable() != true){
					
					List<String> editMemberList = new ArrayList<>();
					
					editMemberList.add(txtUserid.getText().trim());
					editMemberList.add(txtFirstname.getText().trim());
					editMemberList.add(txtLastname.getText().trim());
					editMemberList.add(txtEmail.getText().trim());
					editMemberList.add(cmbRole.getSelectedItem().toString());
					editMemberList.add(txtContactnum.getText().trim());
					editMemberList.add(txtAliasId.getText().trim());
					
					if(checkInputs(editMemberList) == true){
						
						try{
							
							boolean updateSuccess = memberController.editMember(editMemberList);
							if(updateSuccess == true){
								
								displayDefaultTable();
								JOptionPane.showMessageDialog(frame, editMemberList.get(0)+" has been Updated Successfully !!");
							 
							}else{
							
								JOptionPane.showMessageDialog(frame, editMemberList.get(0)+" not Updated.. Please check the input parameters constrains..!!");
							}
							
						}catch(Exception e){
							
							JOptionPane.showMessageDialog(frame, e.getMessage());
						}finally{
							
							//changing the field to editable at last..
							txtUserid.setEditable(true);
							clearFieldValues();
						}
					}
						
				}else{
					
					JOptionPane.showMessageDialog(frame, "Please Select a User to Edit Details !!");
				}
			}

		});
		btnEdit.setForeground(Color.RED);
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setBounds(979, 87, 124, 36);
		pnlMemberDetails.add(btnEdit);
		
		JButton btnInvalidate = new JButton("Invalidate");
		btnInvalidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Checking whether the User ID is disabled to confirm that the flow is in Deleting the user. 
				if(txtUserid.isEditable() != true){
					
					String userToDelete = txtUserid.getText().trim();
					
					try{
						
						boolean invalidateSuccess = memberController.invalidateMember(userToDelete);
						if(invalidateSuccess == true){
							
							displayDefaultTable();
							JOptionPane.showMessageDialog(frame, userToDelete+" has been Invalidated Successfully !!");
						 
						}else{
						
							JOptionPane.showMessageDialog(frame, userToDelete+" not Invalidated.. Please check for DB constrains (or) Please Contact Tool admins..!!");
						}
						
					}catch(Exception e){
						
						JOptionPane.showMessageDialog(frame, e.getMessage());
						
					}finally{
						
						//changing the field to editable at last..
						txtUserid.setEditable(true);
						clearFieldValues();
					}
				}else{
					JOptionPane.showMessageDialog(frame, "Please Select a User to Delete..!!");
				}
			}
		});
		btnInvalidate.setForeground(Color.RED);
		btnInvalidate.setBackground(Color.WHITE);
		btnInvalidate.setBounds(979, 152, 124, 36);
		if(userLoggedInAs.equals("Manager")){
			btnInvalidate.setEnabled(false);
			btnInvalidate.setToolTipText("Sorry You are not authorised to delete !!");
		}
		pnlMemberDetails.add(btnInvalidate);
			
		displayDefaultTable();
		
		frame.setVisible(true);
	}
	
	//To display the default member table and to reuse again..
	private void displayDefaultTable() {
		
		List<MemberBean> allMembersList = new ArrayList<MemberBean>();
		try{
		
			allMembersList = memberController.getAllMemberList();
			
			if(!(allMembersList.isEmpty()) && allMembersList.size() > 0){
				
				displayMemberTable(allMembersList);
				
			}else{
				
				JOptionPane.showMessageDialog(frame, "No Member found in DB..!!");
			}
		}catch(Exception e){
			
			System.out.println("MemberManagementScreen :: displayDefaultTable"+e.getMessage());
		}
		finally{
			
			allMembersList.clear();
		}
	}

	private void displayMemberTable(List<MemberBean> allMembersList){
		
		DefaultTableModel dtm = new DefaultTableModel();
		
		tblMemberDet.setModel(dtm);
		
		//Resetting the row header and column header..				
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
		
		//Setting Table Header Props..
		JTableHeader header = tblMemberDet.getTableHeader();
	    header.setBackground(Color.BLUE);
	    header.setForeground(Color.WHITE);
	    
	    dtm.addColumn("UserID");
	    dtm.addColumn("First Name");
	    dtm.addColumn("Last Name");
	    dtm.addColumn("Role");
	    dtm.addColumn("Email");
	    dtm.addColumn("Contact Number");
	    dtm.addColumn("Alias IDs");
	    
	    for(int i=0;i<allMembersList.size();i++){
	    	
	    	dtm.addRow(new Object[]{allMembersList.get(i).getUserId(),allMembersList.get(i).getFirstName(),allMembersList.get(i).getLastName(),
	    			allMembersList.get(i).getRole(),allMembersList.get(i).getEmailId(),allMembersList.get(i).getContactnum(),allMembersList.get(i).getAliasId()});	
	    }
		tblMemberDet.setRowHeight(20);
		
	}
	

	private void clearFieldValues() {
		
		txtUserid.setText("");
		txtFirstname.setText("");
		txtLastname.setText("");
		txtEmail.setText("");
		cmbRole.setSelectedIndex(0);
		txtContactnum.setText("");
		txtAliasId.setText("");
		
	}
	
	private boolean checkInputs(List<String> addMemberInputDetails) {
		
		boolean inputs_correct = false;
		List<String> alertStrings = new ArrayList<String>();
		int iteratorCount = 0;
		alertStrings.add("User Id");
		alertStrings.add("First Name");
		alertStrings.add("Last Name");
		alertStrings.add("Email Id");
		alertStrings.add("Role");
		alertStrings.add("Contact Num");
		alertStrings.add("Alias ID");
		
		ListIterator<String> lstIter =((List<String>) addMemberInputDetails).listIterator();
		
		while(lstIter.hasNext()){
						
			if(iteratorCount == 4){
			
				if(lstIter.next().toString().equals("<<<Please Select>>>")){
					
					JOptionPane.showMessageDialog(frame, "Please Select Role..!!");	
					break;
				}
				
			}else{
				if(lstIter.next().equals("")){
					
					JOptionPane.showMessageDialog(frame, "Please Enter  "+alertStrings.get(iteratorCount)+"..!!");	
					break;
				}else{
					if(iteratorCount == 6){
						inputs_correct = true;
					}
				}
				
			}
			iteratorCount++;	
		}
		
		return inputs_correct;
	
	/*	if(txtUserid.getText().trim() != null){
			
			if(txtFirstname.getText().trim() != null){
				
				if(txtLastname.getText().trim() != null){
					
					if(txtEmail.getText().trim() != null){
						
						if(!cmbRole.getSelectedItem().toString().contains("Please Select")){
							
							if(txtContactnum.getText().toString() != null){
								
								if(textAliasId.getText().toString() != null){
									
									System.out.println("Inputs correct..");
									inputs_correct = true;
								}else{
									
									JOptionPane.showMessageDialog(frame, "Please Enter Alias ID..!!");
								}
							}else{
								
								JOptionPane.showMessageDialog(frame, "Please Enter Contact Num..!!");
							}
						}else{
							
							JOptionPane.showMessageDialog(frame, "Please Select Role..!!");							
						}
						
					}else{
						
						JOptionPane.showMessageDialog(frame, "Please Enter Email Id..!!");
					}
					
				}else{
					
					JOptionPane.showMessageDialog(frame, "Please Enter Last Name..!!");
				}
				
			}else{
				
				JOptionPane.showMessageDialog(frame, "Please Enter First Name..!!");
			}
			
		}else{
			
			JOptionPane.showMessageDialog(frame, "Please Enter User ID..!!");
		}*/

	}
}
