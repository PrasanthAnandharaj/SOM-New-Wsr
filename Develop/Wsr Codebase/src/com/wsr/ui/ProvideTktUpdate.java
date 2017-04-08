package com.wsr.ui;

import javax.swing.JFrame;
import java.awt.event.FocusAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.wsr.controller.StaffActivitiesController;
import com.wsr.controller.CommonTasksController;
import com.wsr.controller.ManagerTasksController;
import com.wsr.controller.ProvideUpdateController;
import com.wsr.model.IncidentsBean;
import com.wsr.model.TicketUpdateBean;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("rawtypes")
public class ProvideTktUpdate {
	
	private JFrame frame;
	private JPanel pnlInsideTab;
	private JTextField txtTicketId;
	private JTabbedPane tbpTktView;
	private JTextArea textAreaUpdate;
	private JScrollPane scrpnlInsideTab;
	private JTable tblSuggestedInc,updatesTbl;
	private JTableHeader updatesTblHeader,suggestTblheader;
	private JComboBox cmbDomain,cmbClosureCode, cmbSubDomain,cmbRootCause,cmbCountry,cmbStatus;
	
	private String incidentToBeOpenedUpdates=null;
	private String incidentIdToBeClosed = null,incidentTitleToBeClosed = null;
	
	DefaultTableModel dtmUpdateTbl = new DefaultTableModel();
	DefaultTableModel dtmSuggestedTbl = new DefaultTableModel();
	
	WsrCustomTableStyle tblStyleObj;
	IncidentsBean setSearchedTktBean = new IncidentsBean();
	CommonTasksController commCntrlObj = new CommonTasksController();
	StaffActivitiesController staffCntrlObj = new StaffActivitiesController();
	ManagerTasksController mgrController = new ManagerTasksController();
	ProvideUpdateController provideUpdateObj = new ProvideUpdateController();
	
	List<TicketUpdateBean> ticketHistoryLs;
	List<String> dropDownList = new ArrayList<>();
	List<IncidentsBean> incInUserQueueList = null;
	Map<String,String> provideUpdateInputsMap = null;
	
	private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private static String SUCCESS = "success";
	private static String DEFAULTSELECTION = "~~Please Select~~";
	private static String STATUSCLOSED = "BAM : Closed";
	private static String STATUSINVESTIGATION = "BAM : Investigating";
	
	/**
	 * @param userLoggedInAs 
	 * @param incToUpdateTitle 
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("unchecked")
	void updateIncident(final String calledIncId,final String calledIncTitle, final String userLoggedInAs) {
		
		
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
		
		incInUserQueueList = new ArrayList<>();
		incInUserQueueList.clear();
		incInUserQueueList = (userLoggedInAs.equals("Admin")) ?  mgrController.getIncidentsOpenWithTeam() :  staffCntrlObj.getLoggedUserIncidentsInQueue();
		System.out.println("check -- size : "+incInUserQueueList.size());
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"\\Develop\\Wsr Codebase\\Resources\\logo_passbook.png"));
		frame.getContentPane().addFocusListener(new FocusAdapter() {
			
		});
		frame.setBounds(100, 100, 1400, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Provide Incident Update");
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JPanel pnlMain = new JPanel();
		pnlMain.setBounds(12, 13, 1370, 641);
		frame.getContentPane().add(pnlMain);
		pnlMain.setLayout(null);		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(949, 13, 409, 351);
		pnlMain.add(panel);
		panel.setLayout(null);
		
		JLabel lblOtherInc = new JLabel("<html><B>: Other Incidents you may like to "+((userLoggedInAs.equals("Admin"))?"view":"provide")+" updates on :</B></html>");
		lblOtherInc.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		lblOtherInc.setHorizontalAlignment(SwingConstants.CENTER);
		lblOtherInc.setBounds(12, 0, 387, 18);
		panel.add(lblOtherInc);
		
		JScrollPane scrSuggestedInc = new JScrollPane();
		scrSuggestedInc.setToolTipText("Double click any row to provide update on particular ticket.. ");
		scrSuggestedInc.setBounds(0, 25, 409, 326);
		panel.add(scrSuggestedInc);
		
		tblSuggestedInc = new JTable();
		
		tblSuggestedInc.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount() == 2){
					
					incidentToBeOpenedUpdates = tblSuggestedInc.getValueAt(tblSuggestedInc.getSelectedRow(),0).toString().trim();
					
					if(incidentToBeOpenedUpdates.contains("No Row Present")){
						
						JOptionPane.showMessageDialog(frame, "No Suggested Ticket to update..");
					
					}else{
						
						if(isTktNotInUserQueue(tbpTktView.getTitleAt(0).trim()) == true){
							incidentIdToBeClosed = calledIncId;
							incidentTitleToBeClosed = calledIncTitle;
						}

						//removing the existing tab from the tab panel first..
						tbpTktView.remove(0);
						ShowTktUpdatesInTab(incidentToBeOpenedUpdates);
						diplaySuggestedInicidentsTable(incidentIdToBeClosed,incidentTitleToBeClosed,userLoggedInAs);
						loadUpdatePanelWithTktDetails(incidentToBeOpenedUpdates);
					}
				}
			}
			
		});
		tblSuggestedInc.setFont(new Font("Verdana", Font.ITALIC, 12));
		tblSuggestedInc.setToolTipText("Double click any row to provide update on particular ticket.. ");
		tblSuggestedInc.setBackground(new Color(255, 250, 250));
		tblSuggestedInc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tblSuggestedInc.setDefaultRenderer(Object.class, new WsrCustomCellStandard());
		
		
		scrSuggestedInc.setViewportView(tblSuggestedInc);
		
		tbpTktView = new JTabbedPane(JTabbedPane.TOP);
		tbpTktView.setFont(new Font("Verdana", Font.PLAIN, 14));
		tbpTktView.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tbpTktView.setBackground(new Color(176, 196, 222));
		tbpTktView.setForeground(new Color(240, 128, 128));
		tbpTktView.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(192, 192, 192), new Color(240, 255, 255)));
		tbpTktView.setBounds(12, 13, 925, 351);
		pnlMain.add(tbpTktView);
		
		JPanel pnlInputsForUpdates = new JPanel();
		pnlInputsForUpdates.setForeground(new Color(238, 232, 170));
		pnlInputsForUpdates.setBorder(new LineBorder(new Color(255, 218, 185), 2, true));
		pnlInputsForUpdates.setBackground(new Color(240, 248, 255));
		pnlInputsForUpdates.setBounds(12, 375, 1346, 253);
		pnlMain.add(pnlInputsForUpdates, BorderLayout.CENTER);
		pnlInputsForUpdates.setLayout(null);
		
		JLabel lblTktId = new JLabel("Ticket ID");
		lblTktId.setForeground(new Color(255, 127, 80));
		lblTktId.setBackground(new Color(255, 218, 185));
		lblTktId.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblTktId.setHorizontalAlignment(SwingConstants.CENTER);
		lblTktId.setBounds(45, 43, 76, 16);
		pnlInputsForUpdates.add(lblTktId);
		
		txtTicketId = new JTextField();
		txtTicketId.setBackground(new Color(250, 235, 215));
		txtTicketId.setBounds(165, 40, 202, 22);
		pnlInputsForUpdates.add(txtTicketId);
		txtTicketId.setColumns(10);
		
		JLabel lblDomain = new JLabel("Domain");
		lblDomain.setHorizontalAlignment(SwingConstants.CENTER);
		lblDomain.setForeground(new Color(255, 127, 80));
		lblDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblDomain.setBackground(new Color(255, 218, 185));
		lblDomain.setBounds(45, 115, 76, 16);
		pnlInputsForUpdates.add(lblDomain);
		
		JLabel lblSeperatorDomain = new JLabel(":");
		lblSeperatorDomain.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperatorDomain.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeperatorDomain.setBounds(133, 115, 20, 16);
		pnlInputsForUpdates.add(lblSeperatorDomain);
		
		dropDownList.clear();
		dropDownList = commCntrlObj.getAllDomains();
		dropDownList.add(0, DEFAULTSELECTION);
		
		cmbDomain = new JComboBox(dropDownList.toArray());
		cmbDomain.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				dropDownList.clear();
				String selectedOpt = cmbDomain.getSelectedItem().toString();
				if(!selectedOpt.contains("Select")){
				
					dropDownList = commCntrlObj.getDomainToSubDomainMap(cmbDomain.getSelectedItem().toString().trim());				
					dropDownList.add(0, DEFAULTSELECTION);
					cmbSubDomain.removeAllItems();
					for( String optionVal : dropDownList ){
						cmbSubDomain.addItem(optionVal.trim());
					}
				}
			}
		});
		cmbDomain.setSelectedItem(DEFAULTSELECTION);
		cmbDomain.setForeground(new Color(0, 0, 0));
		cmbDomain.setMaximumRowCount(40);
		cmbDomain.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbDomain.setBounds(165, 112, 202, 22);
		pnlInputsForUpdates.add(cmbDomain);
		
		JLabel lblSeperatorActionOn = new JLabel(":");
		lblSeperatorActionOn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperatorActionOn.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeperatorActionOn.setBounds(133, 186, 20, 16);
		pnlInputsForUpdates.add(lblSeperatorActionOn);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setForeground(new Color(255, 127, 80));
		lblStatus.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblStatus.setBackground(new Color(255, 218, 185));
		lblStatus.setBounds(472, 43, 94, 16);
		pnlInputsForUpdates.add(lblStatus);
		
		JLabel lblSeperatorStatus = new JLabel(":");
		lblSeperatorStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperatorStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeperatorStatus.setBounds(565, 43, 20, 16);
		pnlInputsForUpdates.add(lblSeperatorStatus);
		
		JLabel lblClosureCode = new JLabel("Closure Code");
		lblClosureCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblClosureCode.setForeground(new Color(255, 127, 80));
		lblClosureCode.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblClosureCode.setBackground(new Color(255, 218, 185));
		lblClosureCode.setBounds(924, 40, 94, 16);
		pnlInputsForUpdates.add(lblClosureCode);
		
		JLabel lblSeperatorClosureCode = new JLabel(":");
		lblSeperatorClosureCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperatorClosureCode.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeperatorClosureCode.setBounds(1017, 40, 20, 16);
		pnlInputsForUpdates.add(lblSeperatorClosureCode);
		
		dropDownList.clear();
		dropDownList = provideUpdateObj.getdropDownOptionsAsList("ClosureCode");
		dropDownList.add(DEFAULTSELECTION);
		cmbClosureCode = new JComboBox();
		cmbClosureCode.setModel(new DefaultComboBoxModel());
		for(int clsrcodeIter = 0; clsrcodeIter < dropDownList.size(); clsrcodeIter++){
			cmbClosureCode.addItem(dropDownList.get(clsrcodeIter).trim());
		}
		cmbClosureCode.setSelectedItem(DEFAULTSELECTION);
		cmbClosureCode.setMaximumRowCount(40);
		cmbClosureCode.setForeground(new Color(0, 0, 0));
		cmbClosureCode.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbClosureCode.setBounds(1049, 37, 274, 22);
		pnlInputsForUpdates.add(cmbClosureCode);
		
		//Clearing the Obj before getting the List from DB..
				dropDownList.clear();
				dropDownList = provideUpdateObj.getdropDownOptionsAsList("Status");
				dropDownList.add(DEFAULTSELECTION);
				cmbStatus = new JComboBox();
				cmbStatus.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						String selectedStatus = cmbStatus.getSelectedItem().toString();
						if(!selectedStatus.equals(STATUSCLOSED)){
							cmbClosureCode.setEnabled(false);
						}else{
							cmbClosureCode.setEnabled(true);
						}
					}
				});
				cmbStatus.setModel(new DefaultComboBoxModel());
				for(int statusIter = 0; statusIter < dropDownList.size(); statusIter++){
					cmbStatus.addItem(dropDownList.get(statusIter).trim());
				}
				cmbStatus.setSelectedItem(DEFAULTSELECTION);
				cmbStatus.setMaximumRowCount(40);
				cmbStatus.setForeground(new Color(0, 0, 0));
				cmbStatus.setFont(new Font("Verdana", Font.PLAIN, 14));
				cmbStatus.setBounds(597, 40, 202, 22);
				pnlInputsForUpdates.add(cmbStatus);
		
		
		JLabel lblRootCause = new JLabel("Root Cause");
		lblRootCause.setHorizontalAlignment(SwingConstants.CENTER);
		lblRootCause.setForeground(new Color(255, 127, 80));
		lblRootCause.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblRootCause.setBackground(new Color(255, 218, 185));
		lblRootCause.setBounds(472, 186, 94, 16);
		pnlInputsForUpdates.add(lblRootCause);
		
		JLabel lblSeperatorRootCause = new JLabel(":");
		lblSeperatorRootCause.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperatorRootCause.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeperatorRootCause.setBounds(565, 186, 20, 16);
		pnlInputsForUpdates.add(lblSeperatorRootCause);
		
		dropDownList.clear();
		dropDownList = commCntrlObj.getAllRootCause();
		dropDownList.add(DEFAULTSELECTION);
		cmbRootCause = new JComboBox(dropDownList.toArray());
		cmbRootCause.setSelectedItem(DEFAULTSELECTION);
		cmbRootCause.setMaximumRowCount(40);
		cmbRootCause.setForeground(new Color(0, 0, 0));
		cmbRootCause.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbRootCause.setBounds(597, 183, 202, 22);
		pnlInputsForUpdates.add(cmbRootCause);
		
		JLabel lblUpdate = new JLabel("Update");
		lblUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdate.setForeground(new Color(255, 127, 80));
		lblUpdate.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblUpdate.setBackground(new Color(255, 218, 185));
		lblUpdate.setBounds(909, 116, 94, 14);
		pnlInputsForUpdates.add(lblUpdate);
		
		JLabel lblSeperatorUpdate = new JLabel(":");
		lblSeperatorUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperatorUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeperatorUpdate.setBounds(1020, 116, 20, 14);
		pnlInputsForUpdates.add(lblSeperatorUpdate);
		
		JScrollPane scrpnlUpdate = new JScrollPane();
		scrpnlUpdate.setBounds(1052, 96, 271, 106);
		pnlInputsForUpdates.add(scrpnlUpdate);
		
		textAreaUpdate = new JTextArea();
		textAreaUpdate.setWrapStyleWord(true);
		textAreaUpdate.setLineWrap(true);
		textAreaUpdate.setFont(new Font("Verdana", Font.PLAIN, 14));
		textAreaUpdate.setBackground(new Color(250, 235, 215));
		scrpnlUpdate.setViewportView(textAreaUpdate);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frame.setCursor(waitCursor);
				String inputValidations = updateFormValidation();
				if(inputValidations.equalsIgnoreCase(SUCCESS)){
					
					boolean commentsUpdateSuccess = false;
					provideUpdateInputsMap = new HashMap<>();
					provideUpdateInputsMap = fetchInputParamsInMap(provideUpdateInputsMap);
					commentsUpdateSuccess = provideUpdateObj.TicketProgressUpdate(provideUpdateInputsMap);
					if(commentsUpdateSuccess == true){
						String tabToRefresh=tbpTktView.getTitleAt(0).toString();
						tbpTktView.remove(0);
						ShowTktUpdatesInTab(tabToRefresh);
						JOptionPane.showMessageDialog(frame, "Comments Updated successfully !!", "Update Success!", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(frame, "Update Failure!! please contact Administrator", "Update Failed!", JOptionPane.ERROR_MESSAGE);
					}	frame.setCursor(defaultCursor);		
				}else{
					frame.setCursor(defaultCursor);
					JOptionPane.showMessageDialog(frame, " Inputs missing..Please provide \""+inputValidations+"\" field");
				}
			}
		});
		btnUpdate.setFont(new Font("Futura Medium", Font.BOLD, 14));
		btnUpdate.setBackground(SystemColor.control);
		btnUpdate.setBounds(1216, 215, 97, 25);
		btnUpdate.setForeground(new Color(138, 43, 226));
		pnlInputsForUpdates.add(btnUpdate);
		
		JButton btnInvalidateComment = new JButton("Invalidate an Update");
		btnInvalidateComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int selectedRowIndex = updatesTbl.convertRowIndexToModel(updatesTbl.getSelectedRow());
				//JOptionPane.showMessageDialog(frame, "Selected row is"+selectedRowIndex);
				frame.setCursor(waitCursor);
				if(selectedRowIndex < 0){
					JOptionPane.showMessageDialog(frame, "Please select a row to Invalidate..");
				}else{
				
					JDialog.setDefaultLookAndFeelDecorated(true);
					int selectedOption =JOptionPane.showConfirmDialog(frame, "Are you sure to invalidate this comment ?",
							"Invalidate Comment ?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						
					if(selectedOption == JOptionPane.YES_OPTION){
				
						System.out.println("INc ID for which the comments has to be invlaidated : "+tbpTktView.getTitleAt(0));
						ticketHistoryLs = staffCntrlObj.getTicketUpdates(tbpTktView.getTitleAt(0));
			
						//Getting the bean of the selected 
						int updateIdToDelete = ticketHistoryLs.get(selectedRowIndex).getUpdateId();
				
						try{
							boolean invalidateSuccess = provideUpdateObj.invalidateAnUpdateComment(updateIdToDelete);
							if(invalidateSuccess == true){
								
								String tabToRefresh=tbpTktView.getTitleAt(0).toString();
								tbpTktView.remove(0);
								ShowTktUpdatesInTab(tabToRefresh);
							
							}else{
								JOptionPane.showMessageDialog(frame, "Cannot Remove comments !!");
							}
					
						}catch(Exception exp){

							System.out.println("ProvideTktUpdate -- updateIncident :"+exp.getMessage());
						}
					}
					frame.setCursor(defaultCursor);
				}
			}
		});
		btnInvalidateComment.setToolTipText("Select a row in table and click here for Deleting a comment.");
		btnInvalidateComment.setForeground(new Color(138, 43, 226));
		btnInvalidateComment.setFont(new Font("Futura Medium", Font.BOLD, 14));
		btnInvalidateComment.setBackground(SystemColor.control);
		btnInvalidateComment.setBounds(968, 215, 176, 25);
		pnlInputsForUpdates.add(btnInvalidateComment);
		
		JLabel lblTktIdColon = new JLabel(":");
		lblTktIdColon.setHorizontalAlignment(SwingConstants.CENTER);
		lblTktIdColon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTktIdColon.setBounds(133, 43, 20, 16);
		pnlInputsForUpdates.add(lblTktIdColon);
		
		JLabel lblSubDomain = new JLabel("Sub Domain");
		lblSubDomain.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubDomain.setForeground(new Color(255, 127, 80));
		lblSubDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblSubDomain.setBackground(new Color(255, 218, 185));
		lblSubDomain.setBounds(37, 186, 84, 16);
		pnlInputsForUpdates.add(lblSubDomain);
		
		cmbSubDomain = new JComboBox();
		cmbSubDomain.addItem(DEFAULTSELECTION);
		cmbSubDomain.setSelectedItem(DEFAULTSELECTION);
		cmbSubDomain.setMaximumRowCount(40);
		cmbSubDomain.setForeground(Color.BLACK);
		cmbSubDomain.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbSubDomain.setBounds(165, 183, 202, 22);
		
		pnlInputsForUpdates.add(cmbSubDomain);
		
		JLabel lblCountry = new JLabel("Issue In");
		lblCountry.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountry.setForeground(new Color(255, 127, 80));
		lblCountry.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblCountry.setBackground(new Color(255, 218, 185));
		lblCountry.setBounds(472, 115, 94, 16);
		pnlInputsForUpdates.add(lblCountry);
		
		JLabel lblIssueInColon = new JLabel(":");
		lblIssueInColon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIssueInColon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIssueInColon.setBounds(565, 115, 20, 16);
		pnlInputsForUpdates.add(lblIssueInColon);
		
		dropDownList.clear();
		dropDownList = commCntrlObj.getAllCountries();
		dropDownList.add(DEFAULTSELECTION);
		cmbCountry = new JComboBox(dropDownList.toArray());
		cmbCountry.setMaximumRowCount(40);
		cmbCountry.setForeground(Color.BLACK);
		cmbCountry.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbCountry.setBounds(597, 112, 202, 22);
		cmbCountry.setSelectedItem(DEFAULTSELECTION);
		pnlInputsForUpdates.add(cmbCountry);
		
		if(userLoggedInAs.equals("Admin")){
			pnlInputsForUpdates.setEnabled(false);
			for(Component childComp : (Component[])pnlInputsForUpdates.getComponents()){
				childComp.setEnabled(false);
			}
		}
		
		/*checking..
		tbpTktView.add("tab1", new JPanel());
		tbpTktView.add("tab2", new JPanel());
	/	tbpTktView.getTabComponentAt(0).setName("testComp1");
		
		System.out.println("num_of_comp :"+tbpTktView.getTabCount() + "  "+tbpTktView.getTitleAt(0)+"  "+tbpTktView.getTitleAt(1));
		
	//	tbpTktView.getTabComponentAt(1).setVisible(true);
		
		tbpTktView.setSelectedIndex(1);*/
		
		ShowTktUpdatesInTab(calledIncId);
		diplaySuggestedInicidentsTable(null,null,userLoggedInAs);
		loadUpdatePanelWithTktDetails(calledIncId);
		
		//Finally showing the screen
		frame.setVisible(true);
		
	}

	private void diplaySuggestedInicidentsTable(String closedTabId, String closedTabTitle, String userLoggedInAs) {
		
		List<IncidentsBean> suggestedTblIncList = new ArrayList<>();
		List<IncidentsBean> incList = null;
		
		try{
			
			incList = new ArrayList<>();
			incList = (userLoggedInAs.equals("Admin")) ?  mgrController.getIncidentsOpenWithTeam() :  staffCntrlObj.getLoggedUserIncidentsInQueue();
			
			if(closedTabId != null){
			
				//setting bean incase of searched Ticket and not an IncidentInQueue ticket..
				setSearchedTktBean.setIncidentID(closedTabId);
				setSearchedTktBean.setTitle(closedTabTitle);
			
				incList.add(setSearchedTktBean);
			}

			System.out.println("incident list : "+incList.size());
			for(int j=0;j < incList.size();j++){
				if(tbpTktView.getTabCount() != 0){
					
					if(!tbpTktView.getTitleAt(0).toString().equals(incList.get(j).getIncidentID().toString().trim())){
						suggestedTblIncList.add(incList.get(j));
					}else {
						
						continue;
					}
				}else{
					//simply copy the incList into SuggestedList..
					suggestedTblIncList = new ArrayList<>(incList);
				}
			}
			
		}catch(Exception exp){
			System.out.println("ProvideTktUpdate -- diplaySuggestedInicidentsTable :"+exp.getMessage());
		}finally{
			incList.clear();
			
		}		
		
		dtmSuggestedTbl.setRowCount(0);
		dtmSuggestedTbl.setColumnCount(0);
		
		//Setting Table Header Props..
		suggestTblheader = tblSuggestedInc.getTableHeader();
		suggestTblheader.setDefaultRenderer(new WsrCustomHeaderRenderer(tblSuggestedInc,suggestTblheader));
	    
		tblSuggestedInc.setModel(dtmSuggestedTbl);
	    dtmSuggestedTbl.addColumn("TicketID");
	    dtmSuggestedTbl.addColumn("Title");
	    
	    //adding rows..
	    try{
			
			if(suggestedTblIncList.size() == 0){
				{
					System.out.println("No row ");
					dtmSuggestedTbl.addRow(new Object[]{"No Row Present.."});

				}
				}else{						
					
					for(int i=0;i<suggestedTblIncList.size();i++){
					
						dtmSuggestedTbl.addRow(new Object[]{suggestedTblIncList.get(i).getIncidentID(),suggestedTblIncList.get(i).getTitle()});	

					}	
					//Setting the row Height..
					tblSuggestedInc.setRowHeight(50);
					
			}
			
		}catch(Exception exp){

			System.out.println("ProvideTktUpdate -- setTable :"+exp.getMessage());
		}finally{
			suggestedTblIncList.clear();
			
		}
		
	}
	
	private void ShowTktUpdatesInTab(String incidentToBeUpdated) {
		
		ticketHistoryLs = null;
		try{
			
			ticketHistoryLs = staffCntrlObj.getTicketUpdates(incidentToBeUpdated);
			System.out.println("FIX : Ticket's update size is : "+ticketHistoryLs.size());
			
			if(ticketHistoryLs.size() > 0){
				
				//adding a tab to TabPanel with ( name,panel component) parameters. 
				tbpTktView.addTab(ticketHistoryLs.get(0).getIncidentId(),showUpdatesInTable(ticketHistoryLs));
				//adding the ticket name to Editing ticket name..
				String openingTktId = tbpTktView.getTitleAt(0);
				txtTicketId.setText(openingTktId);
				txtTicketId.setEnabled(false);
				loadUpdatePanelWithTktDetails(openingTktId);
				
				
			}else{
				
				System.out.println("Ticket Comment list is received as null");
			}
			
		}catch(Exception e){
			System.out.println("ProvideTktUpdate -- ShowTktUpdatesInTab :"+e.getMessage());
		}finally{
			
		}
	}

	private JPanel showUpdatesInTable(List<TicketUpdateBean> ticketHistoryLs) {
		
		pnlInsideTab = new JPanel();
		pnlInsideTab.setLayout(new BorderLayout());		
		scrpnlInsideTab = new JScrollPane();
		
		updatesTbl = new JTable();
		tblStyleObj = new WsrCustomTableStyle();
		tblStyleObj.setWsrTableStandard(updatesTbl,"ProvideUpdateTable");
		
		updatesTbl.setModel(dtmUpdateTbl);
		updatesTbl.setDefaultRenderer(Object.class, new WsrCustomCellStandard());
		
		updatesTblHeader = updatesTbl.getTableHeader();
		updatesTblHeader.setDefaultRenderer(new WsrCustomHeaderRenderer(updatesTbl,updatesTblHeader));
		
		dtmUpdateTbl.setRowCount(0);
		dtmUpdateTbl.setColumnCount(0);
		dtmUpdateTbl.addColumn("Date");
		dtmUpdateTbl.addColumn("Status");
		dtmUpdateTbl.addColumn("Updates");
		
		try{
			for(int iter=0;iter<ticketHistoryLs.size();iter++){
				
				dtmUpdateTbl.addRow(new Object[]{getFormattedTblDate(ticketHistoryLs.get(iter).getUpdateDate()),
						ticketHistoryLs.get(iter).getStatus(),ticketHistoryLs.get(iter).getComment()});
			}
			
			updatesTbl.setRowHeight(50);
			updatesTbl.setForeground(Color.RED);
			updatesTbl.setBackground(Color.lightGray);
			
		}catch(Exception exp){

			System.out.println("ProvideTktUpdate -- showUpdatesInTable :"+exp.getMessage());
		}
		
		scrpnlInsideTab.setViewportView(updatesTbl);		
		pnlInsideTab.add(scrpnlInsideTab,BorderLayout.CENTER);
		
		return pnlInsideTab;
		
	}
	
	public boolean isTktNotInUserQueue(String tabClosed) {
		
		boolean isTktOutOfUsrQueue =true;
		
		for(int i=0;i<incInUserQueueList.size();i++){
			
			if((tabClosed).equals(incInUserQueueList.get(i).getIncidentID())){
				isTktOutOfUsrQueue = false;
				break;
			}
		}
		return isTktOutOfUsrQueue;
	}
	
	private String getFormattedTblDate(String collatedDate) {
		String date="",month="",year="";
		String charMonth[] = {"Jan","Feb","Mar","Apr","may","Jun","July","Aug","Sep","Oct","Nov","Dec"};

		if(collatedDate.length() == 8){
			date = collatedDate.substring(6);
			month = collatedDate.substring(4,6);
			year = collatedDate.substring(0,4);

			month = charMonth[Integer.parseInt(month) - 1];

		}
		String corectDateFormat =  date+"-"+month+"-"+year;
		return corectDateFormat;
	}

	private void loadUpdatePanelWithTktDetails(String selectedTktId) {
		
		incInUserQueueList.clear();
		
		incInUserQueueList = commCntrlObj.searchIncById(selectedTktId);
		if(!incInUserQueueList.isEmpty()){
	//		System.out.println("ticket title :"+incInUserQueueList.get(0).getIncidentID()+ "--" +incInUserQueueList.get(0).getupdateCountryName()+"--"+incInUserQueueList.get(0).getdomainName());
		
			//For Domain Field..
			if(incInUserQueueList.get(0).getdomainName() != null){ System.out.println("Inside");
				cmbDomain.setSelectedItem(incInUserQueueList.get(0).getdomainName().trim());
			}else{
				cmbDomain.setSelectedItem(DEFAULTSELECTION);
			}
			
			//For Sub Domain Field..
			if(incInUserQueueList.get(0).getsubDomainName() != null){ 
				cmbSubDomain.setSelectedItem(incInUserQueueList.get(0).getsubDomainName().trim());
			}else{
				cmbSubDomain.setSelectedItem(DEFAULTSELECTION);
			}
			
			//For WSR Status field..
			cmbStatus.setSelectedItem(STATUSINVESTIGATION);
			
			//For Issue in country field
			if(incInUserQueueList.get(0).getupdateCountryName() != null){ 
				cmbCountry.setSelectedItem(incInUserQueueList.get(0).getupdateCountryName().trim());
			}else{
				cmbCountry.setSelectedItem(DEFAULTSELECTION);
			}	
			
			// For Root Cause Field..
			if(incInUserQueueList.get(0).getrootCauseName() != null){ 
				cmbRootCause.setSelectedItem(incInUserQueueList.get(0).getrootCauseName().trim());
			}else{
				cmbRootCause.setSelectedItem(DEFAULTSELECTION);
			}	
			
		}
		
	}
	
	private String updateFormValidation() {
		
		String inputMissing = "",emptyStringVal="";
		String selectedStatus = cmbStatus.getSelectedItem().toString();
		String updateCmmnt = textAreaUpdate.getText().trim();
		
		if(!selectedStatus.equals(STATUSCLOSED) 
				&& !cmbStatus.getSelectedItem().toString().contains(DEFAULTSELECTION)){
			
			if(!updateCmmnt.equals(emptyStringVal)){
				return SUCCESS;
			}else{
				return "Update";
			}
			
		}else if(cmbStatus.getSelectedItem().toString().equals(STATUSCLOSED)){
			
			if(!cmbDomain.getSelectedItem().toString().contains(DEFAULTSELECTION)){
				if(!cmbSubDomain.getSelectedItem().toString().trim().equals(emptyStringVal) && 
						!cmbSubDomain.getSelectedItem().toString().contains(DEFAULTSELECTION)){
					if(!selectedStatus.contains(DEFAULTSELECTION)){
						if(cmbCountry.isEnabled() == true && !cmbCountry.getSelectedItem().toString().contains(DEFAULTSELECTION)){
							if(cmbRootCause.isEnabled() == true && !cmbRootCause.getSelectedItem().toString().contains(DEFAULTSELECTION)){
								if(cmbClosureCode.isEnabled() == true && !cmbClosureCode.getSelectedItem().toString().contains(DEFAULTSELECTION)){
									if(!updateCmmnt.equals(emptyStringVal)){
										return "SUCCESS";
									}else{
										inputMissing = "Update";
									}
								}else{
									inputMissing = "Closure Code";
								}
							}else{
								inputMissing = "Root Cause";
							}
						}else{
							inputMissing = "Issue In";
						}
					}else{
						inputMissing = "Status";
					}
				}else{
					inputMissing = "Sub Domain";
				}
			}else{
				inputMissing = "Domain";
			}
			return inputMissing;
		}else{
			return "Status";
		}	
	}
	
	private Map<String, String> fetchInputParamsInMap(Map<String, String> provideUpdateInputsMap) {
		
		provideUpdateInputsMap.put("TicketId", txtTicketId.getText().toString().trim());
		provideUpdateInputsMap.put("Domain", cmbDomain.getSelectedItem().toString().trim());
		provideUpdateInputsMap.put("SubDomain", cmbSubDomain.getSelectedItem().toString().trim());
		provideUpdateInputsMap.put("Status", cmbStatus.getSelectedItem().toString().trim());
		provideUpdateInputsMap.put("IssueIn", cmbCountry.getSelectedItem().toString().trim());
		provideUpdateInputsMap.put("RootCause", cmbRootCause.getSelectedItem().toString().trim());
		provideUpdateInputsMap.put("ClosureCode", cmbClosureCode.getSelectedItem().toString().trim());
		System.out.println("+++ "+textAreaUpdate.getText().toString().trim());
		String getText = textAreaUpdate.getText();
		if(getText.contains("\'")){
			getText = getText.replace("'", "\''");
		}
		provideUpdateInputsMap.put("Update", getText.toString().trim());
		
		return provideUpdateInputsMap;
		
		
	}
	
}
