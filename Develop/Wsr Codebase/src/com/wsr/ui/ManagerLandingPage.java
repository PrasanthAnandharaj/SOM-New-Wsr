package com.wsr.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.BevelBorder;
import java.awt.Window.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.wsr.controller.CommonTasksController;
import com.wsr.controller.ManagerTasksController;
import com.wsr.controller.StaffActivitiesController;
import com.wsr.model.AuthoriseBean;
import com.wsr.model.IncidentsBean;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

@SuppressWarnings({"rawtypes","unchecked", "serial"})
public class ManagerLandingPage extends JTable{

	private JFrame frmManagerScreen;
	
	private JLabel lblRole;
	private JLabel lblWelcome;	
	private JLabel lblSrcById;
	private JLabel lblTotTkts;
	private JLabel lblTktsOpen;
	private JLabel lblNumOfRows;
	private JLabel lblTblrowCount;
	private JLabel lblTotTktCount;
	private JLabel lblTktOpenCount;
	private JLabel lblSearchByText;
	private JLabel lblSrchByMemeber;
	private JLabel lblSearchByDomain;
	private JLabel lblSearchBySubdomain;
	private JLabel lblSeperator1s,lblSeperator2,lblSeperator3,lblSeperator4,lblSeperator5,lblOr2;
	
	private JTextField txtSrchById;
	private JTextField txtSrchByText;
	
	private JButton btnSrcById;
	private JButton btnSrcByText;
	
	private JComboBox cmbSubDomain;
	private JComboBox cmbSrcByDomain;
	private JComboBox cmbSrcByMember;
	
	private JTable tblTkts;
	
	private JPanel pnlSearch;
	private JPanel pnlTblDisp;
	private JPanel pnlWelcome;
	private JPanel pnlTopPanel;
	private JPanel pnlMainPanel;
	private JPanel pnlTicketCounts;
	private JPanel pnlSrchByDrpDwn;
	private JPanel pnlSearchByTxtInp;
	
	private JScrollPane scrpnlTable;
	
	private JMenu mnProfiles,mnRefinedSearch,mnuPopulateTickets;
	private JMenuBar mnubrTopMenuBar;
	private JMenuItem mntmAddProfiles;
	private JMenuItem mntmEditProfile;
	private JMenuItem mntmInvalidateProfile,mnuItmRefinedSearch;
	private JMenu mnuBusRevSheet;
	private JMenuItem mnuItmBusRevSheet;

	String firstName;
	String lastName;
	String userLoggedInAs="";
	
	List<Integer> tktStatistics = new ArrayList<Integer>();
	List<String> mgrPageUtilLs = new ArrayList<String>();
	List<IncidentsBean> mgrIncBeanLs = new ArrayList<IncidentsBean>();
	
	uploadSheetUI uploadUiObj = new uploadSheetUI();
	
	BussinessReviewSheet businessSheetUiObj = new BussinessReviewSheet();
	MemberManagementScreen manageScreenObj = new MemberManagementScreen();
	CommonTasksController commonControllerObj = new CommonTasksController();
	ManagerTasksController managerControllerObj = new ManagerTasksController();
	StaffActivitiesController staffControllerObj = new StaffActivitiesController();
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	JTableHeader header;
	TableColumnModel columnModel;
	DefaultTableModel dtm = new DefaultTableModel();

	
	/**
	 * @param loggedUserBean 
	 * @wbp.parser.entryPoint
	 */
	public void diplayManagerPageUI(final AuthoriseBean loggedUserBean){
		
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
		
		userLoggedInAs= loggedUserBean.getRole();
		
		frmManagerScreen = new JFrame();
		frmManagerScreen.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"\\Develop\\Wsr Codebase\\Resources\\logo_passbook.png"));
		frmManagerScreen.setForeground(new Color(255, 127, 80));
		frmManagerScreen.setFont(new Font("Futura Medium", Font.BOLD | Font.ITALIC, 14));
		frmManagerScreen.setTitle("Welcome " +loggedUserBean.getFirstName()+ " "+loggedUserBean.getLastName() +" !!");
		frmManagerScreen.setType(Type.POPUP);
		frmManagerScreen.setResizable(false);
		frmManagerScreen.getContentPane().setBackground(new Color(169, 169, 169));
		frmManagerScreen.setBounds(100, 100, 1400, 700);
		frmManagerScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManagerScreen.getContentPane().setLayout(null);
		
		pnlMainPanel = new JPanel();
		pnlMainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(211, 211, 211), new Color(169, 169, 169), new Color(169, 169, 169), new Color(211, 211, 211)));
		pnlMainPanel.setBackground(new Color(250, 235, 215));
		pnlMainPanel.setBounds(12, 13, 1370, 641);
		frmManagerScreen.getContentPane().add(pnlMainPanel);
		pnlMainPanel.setLayout(null);
		
		mnubrTopMenuBar = new JMenuBar();
		mnubrTopMenuBar.setBounds(0, 0, 1370, 26);
		mnubrTopMenuBar.setForeground(new Color(128, 0, 128));
		mnubrTopMenuBar.setEnabled(false);
		pnlMainPanel.add(mnubrTopMenuBar);
		
		mnProfiles = new JMenu("][ Profiles ][");
		mnProfiles.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		mnProfiles.setForeground(new Color(148, 0, 211));
		mnProfiles.setHorizontalAlignment(SwingConstants.CENTER);
		mnubrTopMenuBar.add(mnProfiles);
		
		mntmAddProfiles = new JMenuItem("Add Profiles");
		mntmAddProfiles.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				AddMember addMemObj = new  AddMember();
				addMemObj.main(null);
			}
		});
		mntmAddProfiles.setHorizontalAlignment(SwingConstants.LEFT);
		mntmAddProfiles.setForeground(new Color(148, 0, 211));
		mntmAddProfiles.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		mnProfiles.add(mntmAddProfiles);
		
		mntmEditProfile = new JMenuItem("Edit Profile");
		mntmEditProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageScreenObj.displayMemberManagementScreen(userLoggedInAs);
			}
		});
		mntmEditProfile.setHorizontalAlignment(SwingConstants.LEFT);
		mntmEditProfile.setForeground(new Color(148, 0, 211));
		mntmEditProfile.setFont(new Font("Futura Medium", Font.PLAIN, 15));
		mnProfiles.add(mntmEditProfile);
				
		mntmInvalidateProfile = new JMenuItem("Invalidate profile");
		mntmInvalidateProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageScreenObj.displayMemberManagementScreen(userLoggedInAs);
			}
		});
		mntmInvalidateProfile.setForeground(new Color(148, 0, 211));
		mntmInvalidateProfile.setHorizontalAlignment(SwingConstants.LEFT);
		mntmInvalidateProfile.setFont(new Font("Futura Medium", Font.PLAIN, 15));
		mnProfiles.add(mntmInvalidateProfile);
		
		mnRefinedSearch = new JMenu("][ Refined Search ][");
		mnRefinedSearch.setHorizontalAlignment(SwingConstants.CENTER);
		mnRefinedSearch.setForeground(new Color(148, 0, 211));
		mnRefinedSearch.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		mnubrTopMenuBar.add(mnRefinedSearch);
		
		mnuItmRefinedSearch = new JMenuItem("Refined Search");
		mnuItmRefinedSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				RefinedSearch refSrchObj = new RefinedSearch();
				refSrchObj.RefinedSearchUI();
			}
		});
		mnuItmRefinedSearch.setHorizontalAlignment(SwingConstants.CENTER);
		mnuItmRefinedSearch.setForeground(new Color(148, 0, 211));
		mnuItmRefinedSearch.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		mnRefinedSearch.add(mnuItmRefinedSearch);
		
		mnuPopulateTickets = new JMenu("][Populate Tickets][");
		mnuPopulateTickets.setForeground(new Color(148, 0, 211));
		mnuPopulateTickets.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		mnubrTopMenuBar.add(mnuPopulateTickets);
		
		JMenuItem mntmUploadtktSheet = new JMenuItem("Upload Ticket Sheet");
		mntmUploadtktSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				uploadUiObj.showUploadScreen();
				
			}
		});
		mntmUploadtktSheet.setForeground(new Color(148, 0, 211));
		mntmUploadtktSheet.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		mnuPopulateTickets.add(mntmUploadtktSheet);
		
		mnuBusRevSheet = new JMenu("][Business Review Sheet][\r\n");
		mnuBusRevSheet.setForeground(new Color(148, 0, 211));
		mnuBusRevSheet.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		mnubrTopMenuBar.add(mnuBusRevSheet);
		
		mnuItmBusRevSheet = new JMenuItem("Update Business Sheet");
		mnuItmBusRevSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				businessSheetUiObj.BussinessReviewSheetUI();
			}
		});
		mnuItmBusRevSheet.setForeground(new Color(148, 0, 211));
		mnuItmBusRevSheet.setFont(new Font("Futura Medium", Font.PLAIN, 16));
		mnuBusRevSheet.add(mnuItmBusRevSheet);
		
		pnlTopPanel = new JPanel();
		pnlTopPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(169, 169, 169), new Color(169, 169, 169), new Color(169, 169, 169), new Color(169, 169, 169)));
		pnlTopPanel.setBackground(new Color(245, 245, 245));
		pnlTopPanel.setBounds(10, 34, 1348, 200);
		pnlMainPanel.add(pnlTopPanel);
		pnlTopPanel.setLayout(null);
		
		pnlWelcome = new JPanel();
		pnlWelcome.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(148, 0, 211), new Color(148, 0, 211), new Color(148, 0, 211), new Color(148, 0, 211)));
		pnlWelcome.setBounds(12, 13, 217, 174);
		pnlTopPanel.add(pnlWelcome);
		pnlWelcome.setLayout(null);
		
		firstName = loggedUserBean.getFirstName();
		lastName = loggedUserBean.getLastName();
		lblWelcome = new JLabel("<HTML><B><center>Welcome !!</center></B></br><br><B><center> "+firstName.toUpperCase()+" "+lastName.toUpperCase()+" </center></B></HTML>");
		lblWelcome.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(12, 24, 193, 74);
		pnlWelcome.add(lblWelcome);
		
		lblRole = new JLabel(userLoggedInAs);
		lblRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblRole.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblRole.setBounds(12, 111, 193, 33);
		pnlWelcome.add(lblRole);
		
		pnlTicketCounts = new JPanel();
		pnlTicketCounts.setLayout(null);
		pnlTicketCounts.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(148, 0, 211), new Color(148, 0, 211), new Color(148, 0, 211), new Color(148, 0, 211)));
		pnlTicketCounts.setBounds(1065, 13, 271, 174);
		pnlTopPanel.add(pnlTicketCounts);
		
		lblTotTkts = new JLabel("Tickets Totally Handled :");
		lblTotTkts.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotTkts.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblTotTkts.setBounds(12, 32, 183, 46);
		pnlTicketCounts.add(lblTotTkts);
		
		lblTktsOpen = new JLabel("Tickets In Open :");
		lblTktsOpen.setHorizontalAlignment(SwingConstants.CENTER);
		lblTktsOpen.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblTktsOpen.setBounds(12, 91, 183, 46);
		pnlTicketCounts.add(lblTktsOpen);
		
		tktStatistics = commonControllerObj.getTicketStatistics(userLoggedInAs,loggedUserBean.getUserId());
		
		lblTotTktCount = new JLabel(String.valueOf(tktStatistics.get(0)));
		lblTotTktCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotTktCount.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblTotTktCount.setBounds(191, 31, 68, 46);
		pnlTicketCounts.add(lblTotTktCount);
		
		lblTktOpenCount = new JLabel(String.valueOf(tktStatistics.get(1)));
		lblTktOpenCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblTktOpenCount.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblTktOpenCount.setBounds(191, 91, 68, 46);
		pnlTicketCounts.add(lblTktOpenCount);
		
		pnlSearch = new JPanel();
		pnlSearch.setLayout(null);
		pnlSearch.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(255, 255, 0), new Color(255, 255, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
		pnlSearch.setBounds(241, 13, 812, 174);
		pnlTopPanel.add(pnlSearch);
		
		pnlSearchByTxtInp = new JPanel();
		pnlSearchByTxtInp.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search By  ID / Keyword:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(153, 50, 204)));
		pnlSearchByTxtInp.setBounds(12, 13, 390, 148);
		pnlSearch.add(pnlSearchByTxtInp);
		pnlSearchByTxtInp.setLayout(null);
		
		cmbSrcByMember = new JComboBox();
		cmbSrcByMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				mgrIncBeanLs.clear();
				String selection = cmbSrcByMember.getSelectedItem().toString();
				try{
					if(!selection.contains("Please Select")){
					
							mgrIncBeanLs = managerControllerObj.getMemberSpecificOpenTicket(selection);
							displayTable(mgrIncBeanLs);							
						
					}else{
						mgrIncBeanLs = managerControllerObj.getMemberSpecificOpenTicket(loggedUserBean.getUserId());
						displayTable(mgrIncBeanLs);	
					}
				}catch(Exception ex){
					System.out.println("ManagerLandingPage :: diplayManagerPageUI -- "+ex.getMessage());
				}finally{
					cmbSrcByMember.setSelectedItem(selection);
				}	
			}
		});
		
		cmbSrcByMember.setForeground(new Color(148, 0, 211));
		cmbSrcByMember.setMaximumRowCount(50);
		cmbSrcByMember.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbSrcByMember.setBounds(154, 116, 224, 22);
		
		//clearing the LS for having only current elements..
		mgrPageUtilLs.clear();
		cmbSrcByMember.addItem("-- Please Select --");
		mgrPageUtilLs = commonControllerObj.getAllMembers();
		for(int cmbMbrIter=0;cmbMbrIter < mgrPageUtilLs.size();cmbMbrIter++){
			cmbSrcByMember.addItem(mgrPageUtilLs.get(cmbMbrIter));
		}
		pnlSearchByTxtInp.add(cmbSrcByMember);
		
		
		lblSrchByMemeber = new JLabel(" Tickets of Member");
		lblSrchByMemeber.setHorizontalAlignment(SwingConstants.LEFT);
		lblSrchByMemeber.setForeground(new Color(25, 25, 112));
		lblSrchByMemeber.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		lblSrchByMemeber.setBounds(6, 119, 115, 16);
		pnlSearchByTxtInp.add(lblSrchByMemeber);
		
		lblSrcById = new JLabel("Search by ID");
		lblSrcById.setBounds(6, 36, 95, 16);
		pnlSearchByTxtInp.add(lblSrcById);
		lblSrcById.setForeground(new Color(25, 25, 112));
		lblSrcById.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		lblSrcById.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblSearchByText = new JLabel("Search by Keyword");
		lblSearchByText.setBounds(6, 77, 124, 16);
		pnlSearchByTxtInp.add(lblSearchByText);
		lblSearchByText.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearchByText.setForeground(new Color(25, 25, 112));
		lblSearchByText.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		
		lblSeperator2 = new JLabel(":");
		lblSeperator2.setBounds(126, 76, 17, 16);
		pnlSearchByTxtInp.add(lblSeperator2);
		lblSeperator2.setForeground(new Color(0, 0, 139));
		lblSeperator2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperator2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblSeperator1s = new JLabel(":");
		lblSeperator1s.setBounds(126, 36, 17, 16);
		pnlSearchByTxtInp.add(lblSeperator1s);
		lblSeperator1s.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperator1s.setForeground(new Color(0, 0, 139));
		lblSeperator1s.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		txtSrchByText = new JTextField();
		txtSrchByText.setBounds(154, 73, 136, 22);
		pnlSearchByTxtInp.add(txtSrchByText);
		txtSrchByText.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtSrchByText.setBackground(new Color(255, 255, 240));
		txtSrchByText.setColumns(10);
		
		txtSrchById = new JTextField();
		txtSrchById.setBounds(154, 32, 136, 22);
		pnlSearchByTxtInp.add(txtSrchById);
		txtSrchById.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtSrchById.setColumns(10);
		txtSrchById.setBackground(new Color(255, 255, 240));
		
		lblSeperator3 = new JLabel(":");
		lblSeperator3.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperator3.setForeground(new Color(0, 0, 139));
		lblSeperator3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSeperator3.setBounds(126, 119, 17, 16);
		pnlSearchByTxtInp.add(lblSeperator3);
		
		btnSrcById = new JButton("Search");
		btnSrcById.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				mgrIncBeanLs.clear();
				
				String searchId = txtSrchById.getText().trim();
				System.out.println("Search ID :"+searchId);	
				try{
					
					mgrIncBeanLs = commonControllerObj.searchIncById(searchId);
					//Successfully ticket is fetched case..
					if(mgrIncBeanLs.size() == 1){	
						displayTable(mgrIncBeanLs);
					}else{
						JOptionPane.showMessageDialog(frmManagerScreen, "Sorry !! No Incident found with the given ID..");
					}
				}catch(Exception ex){
						System.out.println("ManagerLandingPage :: diplayManagerPageUI -- "+ex.getMessage());
				}finally{
					txtSrchById.setText("");
				}
				
			}
		});
		btnSrcById.setForeground(new Color(153, 50, 204));
		btnSrcById.setFont(new Font("Futura Medium", Font.PLAIN, 12));
		btnSrcById.setBounds(299, 33, 76, 25);
		pnlSearchByTxtInp.add(btnSrcById);
		
		btnSrcByText = new JButton("Search");
		btnSrcByText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				mgrIncBeanLs.clear();
				String SearchKeyword = txtSrchByText.getText().trim();
				System.out.println("Search text : "+SearchKeyword);
				try{
					mgrIncBeanLs = commonControllerObj.searchTicketByKeyword(SearchKeyword);
					if(mgrIncBeanLs.size() != 0){
						displayTable(mgrIncBeanLs);
					}else{
						JOptionPane.showMessageDialog(frmManagerScreen, "Sorry !! No Incident found with the given Keyword..");
					}
				}catch(Exception ex){
					System.out.println("ManagerLandingPage :: diplayManagerPageUI -- "+ex.getMessage());
				}finally{
					txtSrchByText.setText("");
				}
			}
		});
		btnSrcByText.setForeground(new Color(153, 50, 204));
		btnSrcByText.setFont(new Font("Futura Medium", Font.PLAIN, 12));
		btnSrcByText.setBounds(299, 73, 76, 25);
		pnlSearchByTxtInp.add(btnSrcByText);
		
		pnlSrchByDrpDwn = new JPanel();
		pnlSrchByDrpDwn.setLayout(null);
		pnlSrchByDrpDwn.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search By  Domain / Sub - Domain :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(153, 50, 204)));
		pnlSrchByDrpDwn.setBounds(402, 13, 398, 148);
		pnlSearch.add(pnlSrchByDrpDwn);
		
		lblSearchByDomain = new JLabel("Search by Domain");
		lblSearchByDomain.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchByDomain.setForeground(new Color(25, 25, 112));
		lblSearchByDomain.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		lblSearchByDomain.setBounds(6, 36, 153, 16);
		pnlSrchByDrpDwn.add(lblSearchByDomain);
		
		lblSearchBySubdomain = new JLabel("Search by Sub-Domain");
		lblSearchBySubdomain.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchBySubdomain.setForeground(new Color(25, 25, 112));
		lblSearchBySubdomain.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		lblSearchBySubdomain.setBounds(6, 98, 153, 16);
		pnlSrchByDrpDwn.add(lblSearchBySubdomain);
		
		lblSeperator5 = new JLabel(":");
		lblSeperator5.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperator5.setForeground(new Color(0, 0, 139));
		lblSeperator5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSeperator5.setBounds(156, 97, 17, 16);
		pnlSrchByDrpDwn.add(lblSeperator5);
		
		lblSeperator4 = new JLabel(":");
		lblSeperator4.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeperator4.setForeground(new Color(0, 0, 139));
		lblSeperator4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSeperator4.setBounds(156, 35, 17, 16);
		pnlSrchByDrpDwn.add(lblSeperator4);
		
		lblOr2 = new JLabel("( OR )");
		lblOr2.setHorizontalAlignment(SwingConstants.CENTER);
		lblOr2.setForeground(new Color(0, 0, 128));
		lblOr2.setFont(new Font("Futura Medium", Font.PLAIN, 14));
		lblOr2.setBounds(166, 68, 39, 16);
		pnlSrchByDrpDwn.add(lblOr2);
		
		cmbSrcByDomain = new JComboBox();
		cmbSrcByDomain.setMaximumRowCount(50);
		cmbSrcByDomain.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbSrcByDomain.setBounds(185, 33, 198, 22);
		pnlSrchByDrpDwn.add(cmbSrcByDomain);
		
		cmbSubDomain = new JComboBox();
		cmbSubDomain.setMaximumRowCount(50);
		cmbSubDomain.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbSubDomain.setBounds(185, 95, 198, 22);
		pnlSrchByDrpDwn.add(cmbSubDomain);
		
		pnlTblDisp = new JPanel();
		pnlTblDisp.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(148, 0, 211), new Color(148, 0, 211), new Color(148, 0, 211), new Color(148, 0, 211)));
		pnlTblDisp.setBackground(new Color(176, 196, 222));
		pnlTblDisp.setBounds(12, 247, 1346, 381);
		pnlMainPanel.add(pnlTblDisp);
		pnlTblDisp.setLayout(null);
		
		scrpnlTable = new JScrollPane();
		scrpnlTable.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0)));
		scrpnlTable.setBounds(12, 13, 1322, 337);
		pnlTblDisp.add(scrpnlTable);
		
		lblNumOfRows = new JLabel("Number of rows :");
		lblNumOfRows.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumOfRows.setForeground(new Color(255, 0, 0));
		lblNumOfRows.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblNumOfRows.setBounds(555, 352, 113, 16);
		pnlTblDisp.add(lblNumOfRows);
		
		lblTblrowCount = new JLabel("");
		lblTblrowCount.setHorizontalAlignment(SwingConstants.LEFT);
		lblTblrowCount.setForeground(Color.RED);
		lblTblrowCount.setFont(new Font("Futura Medium", Font.BOLD, 14));
		lblTblrowCount.setBounds(676, 352, 207, 16);
		pnlTblDisp.add(lblTblrowCount);
		
		tblTkts = new JTable();
		tblTkts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTkts.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent em) {
					
				if(em.getClickCount() == 2){
			//	JOptionPane.showMessageDialog(frmManagerScreen, "Double click !!");
				
				String IncToBeUpdated = tblTkts.getValueAt(tblTkts.getSelectedRow(), 0).toString().trim();
				String IncToUpdateTitle = tblTkts.getValueAt(tblTkts.getSelectedRow(), 1).toString().trim();
				
				try{	
					ProvideTktUpdate commentUpdateObj =new ProvideTktUpdate();
					commentUpdateObj.updateIncident(IncToBeUpdated,IncToUpdateTitle,userLoggedInAs);
					
				}catch(Exception ex){
					
				}finally{
					//frame.setVisible(true);
				}
				
			}
				
		}
		});
		tblTkts.setForeground(new Color(65, 105, 225));
		tblTkts.setFont(new Font("Verdana", Font.PLAIN, 14));
		tblTkts.setBorder(new LineBorder(new Color(211, 211, 211), 2, true));
		tblTkts.setToolTipText("Double Click on any row to provide update");
		tblTkts.setBackground(new Color(255, 255, 240));
		//change below..
		tblTkts.setAutoResizeMode(JTable.WIDTH);
		scrpnlTable.setViewportView(tblTkts);
		//sending to table code starts here..
		mgrIncBeanLs.clear();
		try{
			mgrIncBeanLs = (userLoggedInAs.equals("Admin"))  ?managerControllerObj.getIncidentsOpenWithTeam() : staffControllerObj.getLoggedUserIncidentsInQueue();
			displayTable(mgrIncBeanLs);
		}catch(Exception ex){
				System.out.println("ManagerLandingPage :: diplayManagerPageUI -- "+ex.getMessage());
		}
			
		userPrivilegeFilter();
	
		frmManagerScreen.setVisible(true);
	}


	private void userPrivilegeFilter() {
		if(userLoggedInAs.equals("Manager")){
			
			mntmInvalidateProfile.setVisible(false);
			mnuPopulateTickets.setVisible(false);
		}
		
	}


	@SuppressWarnings({ })
	private void displayTable(List<IncidentsBean> mgrIncBeanLs) {
		
		//Emptying the rows and columns to purge prvious table Settings.. 
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
		
		tblTkts.setModel(dtm);
		columnModel = tblTkts.getColumnModel();
		header = tblTkts.getTableHeader();
		header.setBackground(Color.BLUE);
	    header.setForeground(Color.WHITE);
	    
	    //Setting Column names..
	    dtm.addColumn("Incident ID");
	    columnModel.getColumn(0).setMinWidth(500);
	    
		dtm.addColumn("Title");
		columnModel.getColumn(1).setMinWidth(1000);


		dtm.addColumn("Severity");
		columnModel.getColumn(2).setMinWidth(20);
		
		dtm.addColumn("Asssignee");
		columnModel.getColumn(3).setMinWidth(60);
		
		dtm.addColumn("SLA Breach");
		columnModel.getColumn(4).setMinWidth(80);
		
		try{
			if(mgrIncBeanLs.size() == 0){
				{
					//dtm.addRow(new Object[]{"No Row Present.."});
					JOptionPane.showMessageDialog(frmManagerScreen, "No Row to display");
				}
			}else{						
				for(int i=0;i<mgrIncBeanLs.size();i++){
					dtm.addRow(new Object[]{mgrIncBeanLs.get(i).getIncidentID(),mgrIncBeanLs.get(i).getTitle(),mgrIncBeanLs.get(i).getSeverity(),
							mgrIncBeanLs.get(i).getAssignee(),mgrIncBeanLs.get(i).getSla_target_date()});
					
				//checkAlertStatus(mgrIncBeanLs.get(i).getSla_target_date());
				}						
			}
			//Setting the row Height..
			tblTkts.setRowHeight(70);
			
			
		}catch(Exception exp){
			System.out.println("ManagerLandingPage -- setTable :"+exp.getMessage());
		}finally{
			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dtm);
			tblTkts.setRowSorter(sorter);
			
			lblTblrowCount.setText(tblTkts.getRowCount()+ " record(s) listed");
			mgrIncBeanLs.clear();
		}
	}


	private void checkAlertStatus(String sla_target_date) throws ParseException {
		
	        Date today = new Date();
			Date sla = sdf.parse(sla_target_date);
	        if(sla.before(today) || ((int)((sla.getTime() - today.getTime())/(24*60*60*1000)) < 5)){
			
			
			}
	        
	}
}
