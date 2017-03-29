package com.wsr.ui;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import com.wsr.controller.CommonTasksController;
import com.wsr.controller.ManagerTasksController;
import com.wsr.model.IncidentsBean;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;

@SuppressWarnings({ "rawtypes" })
public class RefinedSearch {
               
               private JFrame frmRefinedSearch;
               private ButtonGroup OpenOrClosed;
               private JCheckBox[] checkBoxList;
               private JDateChooser dtChsrFromDate,dtChsrTillDate; 
               private JLabel lblSelectDomain,lblMemberSelectionHeading;
               private JRadioButton rdoBtnOpen,rdoBtnClose,rdobtnOpenOrClose;
               private JLabel lblSubDomain,lblDateFrom,lblTill,lblCause,lblCountry;
               private JComboCheckBox ccbDomain,ccbSubDomain,ccbRootCause,ccbCountry;
               private JPanel pnlMain,pnlInside,pnlMemberSelection,pnlParams,pnlCriteriaPreview,pnlStatus;
               private JLabel lblPrvDomain,lblPrvSubDomain,lblPrvRootCause,lblPrvCountry,lblPrvMembers,lblPrvStatus;
               private JTextArea txtArPrvDomain,txtArPrvSubDomain,txtArPrvRootCause,txtArPrvCountry,txtArPrvMembers,txtArPrvStatus;
               private JButton btnAddCriteria,btnListTickets,btnPrvDomain,btnPrvSubDomain,btnPrvRootCause,btnPrvCountry,btnPrvMembers,btnPrvStatus;
               private JLabel lblColon1,lblColon2,lblColon3,lblColon4,lblColon5,lblColon6,lblColon7,lblColon8,lblColon9,lblColon10,lblColon11,lblColon12;
               private JScrollPane scrPnlMembers,scrPnlCriteriaPrv,scrPnlPrvDomain,scrPnlPrvSubDomain,scrPnlPrvRootCause,scrPnlPrvMembers,scrPnlPrvCountry,scrPnlPrvStatus;
               
               Vector vctDomain = new Vector<>();
               Vector vctSubDomain = new Vector<>();
               Vector vctRtCaus = new Vector<>();
               Vector vctCountry = new Vector<>();
               List<String> searchUtilLs = new ArrayList<>();
               List<IncidentsBean> filteredTicketsLs = new ArrayList<>();
               Map<String,List<String>> searchFilterCriteriaMap = new HashMap<String,List<String>>();
               
               CommonTasksController commonControllerObj = new CommonTasksController();
               FilteredTicketsResult filteredTktsUiObj;
               ManagerTasksController managerControllerObj = new ManagerTasksController();
               
               
               /**
               * @wbp.parser.entryPoint
               */
               
               @SuppressWarnings("unchecked")
               public void RefinedSearchUI() {
                              
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
                              
                              frmRefinedSearch = new JFrame();
                              frmRefinedSearch.getContentPane().setBackground(new Color(128, 128, 128));
                              frmRefinedSearch.setTitle("Refined Search");
                              frmRefinedSearch.setResizable(false);
                              frmRefinedSearch.setBounds(100, 100, 1400, 700);
                              frmRefinedSearch.getContentPane().setLayout(null);
                              
                              pnlMain = new JPanel();
                              pnlMain.setBackground(new Color(250, 235, 215));
                              pnlMain.setBounds(12, 13, 1370, 641);
                              frmRefinedSearch.getContentPane().add(pnlMain);
                              pnlMain.setLayout(null);
                              
                              scrPnlMembers = new JScrollPane();
                              scrPnlMembers.setBounds(1132, 13, 226, 615);
                              scrPnlMembers.setViewportBorder(new LineBorder(new Color(216, 191, 216), 2, true));
                              scrPnlMembers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                              pnlMain.add(scrPnlMembers);
                              
                              lblMemberSelectionHeading = new JLabel("::: Select Members :::");
                              lblMemberSelectionHeading.setHorizontalAlignment(SwingConstants.CENTER);
                              lblMemberSelectionHeading.setForeground(new Color(148, 0, 211));
                              lblMemberSelectionHeading.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              lblMemberSelectionHeading.setBackground(new Color(148, 0, 211));
                              scrPnlMembers.setColumnHeaderView(lblMemberSelectionHeading);
                              
                              pnlMemberSelection = new JPanel();
                              pnlMemberSelection.setForeground(new Color(148, 0, 211));
                              pnlMemberSelection.setBackground(new Color(240, 248, 255));
                              scrPnlMembers.setViewportView(pnlMemberSelection);
                              pnlMemberSelection.setLayout(new BoxLayout(pnlMemberSelection, BoxLayout.Y_AXIS));
                              
                              searchUtilLs.clear();
                              searchUtilLs = commonControllerObj.getAllMembers();
                              System.out.println("checkbox member size :"+searchUtilLs.size());
                              checkBoxList = new JCheckBox[searchUtilLs.size()+1];
                              for(int i=0;i<searchUtilLs.size();i++){
                                             checkBoxList[i] = new JCheckBox(searchUtilLs.get(i));
                                             pnlMemberSelection.add(checkBoxList[i]);
                                             checkBoxList[i].setSelected(true);
                                             checkBoxList[i].setBackground(pnlMemberSelection.getBackground());
                                             checkBoxList[i].setFont(new Font("Futura Medium", Font.PLAIN, 14));
                                             checkBoxList[i].setForeground(new Color(148, 0, 211));
                                             
                              }
                              System.out.println("No.of comp : "+pnlMemberSelection.getComponentCount());
                              
                              btnAddCriteria = new JButton("Add Filter");
                              btnAddCriteria.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                             
                                                            // <---- Member Selection checking starts here-->
                                                            searchUtilLs.clear();
                                                            boolean boxesUnchecked = false;
                                                            for(int i=0;i < pnlMemberSelection.getComponentCount();i++){
                                                                           if(pnlMemberSelection.getComponent(i) instanceof JCheckBox){
                                                                                          JCheckBox currentBox = (JCheckBox)pnlMemberSelection.getComponent(i);
                                                                                          if(currentBox.isSelected() == true){
                                                                                                         System.out.println(currentBox.getText() + " : "+currentBox.isSelected());
                                                                                                         searchUtilLs.add(currentBox.getText().toString());
                                                                                          }else{
                                                                                                         boxesUnchecked = true;
                                                                                          }
                                                                           }
                                                            
                                                            }
                                                            if(boxesUnchecked == true){
                                                                           String txtAreaReplacingValue = "";
                                                                           txtArPrvMembers.setText("");
                                                                           for(int a=0;a<searchUtilLs.size();a++){
                                                                                          System.out.println(searchUtilLs.get(a).toString());
                                                                           }
                                                                           for(int j=0;j<searchUtilLs.size();j++){
                                                                                          if(j == 0){
                                                                                                         txtAreaReplacingValue += searchUtilLs.get(j);
                                                                                          }else
                                                                                          {
                                                                                                         txtAreaReplacingValue += " ~ "+searchUtilLs.get(j);
                                                                                          }
                                                                           } txtArPrvMembers.setText(txtAreaReplacingValue);
                                                                           searchUtilLs.clear();
                                                                           //<---- Member Selection checking ends here-->
                                                                           //------------------------------------------------
                                                            }
                                             }
                              });
                              btnAddCriteria.setBackground(new Color(255, 228, 225));
                              btnAddCriteria.setForeground(new Color(148, 0, 211));
                              btnAddCriteria.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnAddCriteria.setBounds(975, 32, 121, 43);
                              pnlMemberSelection.add(btnAddCriteria);
                              
                              pnlParams = new JPanel();
                              pnlParams.setBounds(12, 13, 1108, 259);
                              pnlParams.setBorder(new LineBorder(new Color(148, 0, 211), 2, true));
                              pnlParams.setBackground(new Color(240, 248, 255));
                              pnlMain.add(pnlParams);
                              pnlParams.setLayout(null);
                              
                              lblSelectDomain = new JLabel("Domain");
                              lblSelectDomain.setHorizontalAlignment(SwingConstants.CENTER);
                              lblSelectDomain.setForeground(new Color(255, 127, 80));
                              lblSelectDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblSelectDomain.setBackground(new Color(255, 218, 185));
                              lblSelectDomain.setBounds(21, 32, 76, 16);
                              pnlParams.add(lblSelectDomain);
                              
                              lblColon1 = new JLabel(":");
                              lblColon1.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon1.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon1.setBounds(109, 32, 20, 16);
                              pnlParams.add(lblColon1);
                              
                              lblSubDomain = new JLabel("Sub-Domain");
                              lblSubDomain.setHorizontalAlignment(SwingConstants.CENTER);
                              lblSubDomain.setForeground(new Color(255, 127, 80));
                              lblSubDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblSubDomain.setBackground(new Color(255, 218, 185));
                              lblSubDomain.setBounds(493, 35, 89, 16);
                              pnlParams.add(lblSubDomain);
                              
                              lblColon2 = new JLabel(":");
                              lblColon2.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon2.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon2.setBounds(594, 35, 20, 16);
                              pnlParams.add(lblColon2);
                              
                              lblDateFrom = new JLabel("<html><B><font color=\"red\">From Date *</font></B></html>");
                              lblDateFrom.setHorizontalAlignment(SwingConstants.CENTER);
                              lblDateFrom.setForeground(new Color(255, 127, 80));
                              lblDateFrom.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblDateFrom.setBackground(new Color(255, 218, 185));
                              lblDateFrom.setBounds(21, 83, 76, 16);
                              pnlParams.add(lblDateFrom);
                              
                              lblColon3 = new JLabel(":");
                              lblColon3.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon3.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon3.setBounds(109, 83, 20, 16);
                              pnlParams.add(lblColon3);
                              
                              lblTill = new JLabel("<html><B><font color=\"red\">Till Date *</font></B></html>");
                              lblTill.setHorizontalAlignment(SwingConstants.CENTER);
                              lblTill.setForeground(new Color(255, 127, 80));
                              lblTill.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblTill.setBackground(new Color(255, 218, 185));
                              lblTill.setBounds(493, 83, 76, 16);
                              pnlParams.add(lblTill);
                              
                              lblColon4 = new JLabel(":");
                              lblColon4.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon4.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon4.setBounds(594, 83, 20, 16);
                              pnlParams.add(lblColon4);
                              
                              lblCause = new JLabel("Root Cause");
                              lblCause.setHorizontalAlignment(SwingConstants.CENTER);
                              lblCause.setForeground(new Color(255, 127, 80));
                              lblCause.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblCause.setBackground(new Color(255, 218, 185));
                              lblCause.setBounds(21, 135, 76, 16);
                              pnlParams.add(lblCause);
                              
                              lblColon5 = new JLabel(":");
                              lblColon5.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon5.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon5.setBounds(109, 135, 20, 16);
                              pnlParams.add(lblColon5);
                              
                              lblCountry = new JLabel("Country");
                              lblCountry.setHorizontalAlignment(SwingConstants.CENTER);
                              lblCountry.setForeground(new Color(255, 127, 80));
                              lblCountry.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblCountry.setBackground(new Color(255, 218, 185));
                              lblCountry.setBounds(493, 138, 76, 16);
                              pnlParams.add(lblCountry);
                              
                              lblColon6 = new JLabel(":");
                              lblColon6.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon6.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon6.setBounds(594, 138, 20, 16);
                              pnlParams.add(lblColon6);
                              
                              pnlStatus = new JPanel();
                              pnlStatus.setForeground(new Color(255, 127, 80));
                              pnlStatus.setBackground(new Color(240, 248, 255));
                              pnlStatus.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ticket Status", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 127, 80)));
                              pnlStatus.setBounds(21, 186, 1062, 55);
                              pnlParams.add(pnlStatus);
                              pnlStatus.setLayout(null);
                              
                              rdoBtnOpen = new JRadioButton("Show  Only Tickets Opened between Selected Dates only");
                              rdoBtnOpen.setBounds(6, 21, 327, 25);
                              rdoBtnOpen.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            txtArPrvStatus.setText("includes Only tickets opened between selected Dates");
                                             }
                              });
                              pnlStatus.add(rdoBtnOpen);
                              rdoBtnOpen.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              rdoBtnOpen.setBackground(new Color(240, 248, 255));
                              rdoBtnOpen.setActionCommand("TicketOpenedBetween");
                              
                              rdoBtnClose = new JRadioButton("Show Only Tickets Closed between Selected Dates only");
                              rdoBtnClose.setBounds(348, 21, 321, 25);
                              rdoBtnClose.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            txtArPrvStatus.setText("includes Only tickets Closed between selected Dates");
                                             }
                              });
                              pnlStatus.add(rdoBtnClose);
                              rdoBtnClose.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              rdoBtnClose.setBackground(new Color(240, 248, 255));
                              rdoBtnClose.setActionCommand("TicketClosedBetween");
                              
                              rdobtnOpenOrClose = new JRadioButton("Tickets alive between selected Dates..");
                              rdobtnOpenOrClose.setBounds(683, 18, 373, 30);
                              rdobtnOpenOrClose.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            txtArPrvStatus.setText("Tickets alive between selected Dates");
                                             }
                              });
                              pnlStatus.add(rdobtnOpenOrClose);
                              rdobtnOpenOrClose.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              rdobtnOpenOrClose.setBackground(new Color(240, 248, 255));
                              rdobtnOpenOrClose.setSelected(true);
                              rdobtnOpenOrClose.setActionCommand("BothBetweenDates");
                              
                              //Code to enable selection of one radioButton Only by assigning them to same group..
                              OpenOrClosed = new ButtonGroup();
                              OpenOrClosed.add(rdoBtnOpen);
                              OpenOrClosed.add(rdoBtnClose);
                              OpenOrClosed.add(rdobtnOpenOrClose);
                              
                              dtChsrFromDate = new JDateChooser();
                              dtChsrFromDate.getCalendarButton();
                              dtChsrFromDate.getCalendarButton().addMouseListener(new MouseAdapter() {
                                             @Override
                                             public void mouseClicked(MouseEvent e) {
                                                            //anyway clearing to Till field , if it is chosen intentionally..
                                                                           dtChsrTillDate.setDate(null);
                                                                           
                                             }
                              });
                              dtChsrFromDate.getCalendarButton().addActionListener(new ActionListener() {
                                             
                                             @Override
                                             public void actionPerformed(ActionEvent arg0) {
                                                            dtChsrTillDate.setMinSelectableDate(dtChsrFromDate.getDate());
                                             }
                              });
                              dtChsrFromDate.setForeground(new Color(148, 0, 211));
                              dtChsrFromDate.getCalendarButton().setBackground(new Color(148, 0, 211));
                              dtChsrFromDate.getCalendarButton().setForeground(new Color(148, 0, 211));
                              dtChsrFromDate.getCalendarButton().setFont(new Font("Verdana", Font.BOLD, 16));
                              dtChsrFromDate.setBackground(new Color(169, 169, 169));
                              dtChsrFromDate.setBounds(152, 77, 285, 22);
                              pnlParams.add(dtChsrFromDate);
                              dtChsrFromDate.setMaxSelectableDate(new Date());
                              //disabling the editing option in textfield of this datechooser..
                              JTextFieldDateEditor fromEditor = (JTextFieldDateEditor) dtChsrFromDate.getDateEditor();
                              fromEditor.setEditable(false);
                              
                              dtChsrTillDate = new JDateChooser();                      
                              dtChsrTillDate.getCalendarButton().addMouseListener(new MouseAdapter() {
                                             @Override
                                             public void mouseClicked(MouseEvent e) {
                                                            if(dtChsrFromDate.getDate() == null){
                                                                           JOptionPane.showMessageDialog(frmRefinedSearch, "Please Select From Date First..!", "Input please !!", JOptionPane.WARNING_MESSAGE);
                                                            }else{
                                                                           dtChsrTillDate.setMaxSelectableDate(new Date());
                                                                           dtChsrTillDate.setMinSelectableDate(dtChsrFromDate.getDate());
                                                            }
                                             }
                              });
                              dtChsrTillDate.getCalendarButton().setForeground(new Color(148, 0, 211));
                              dtChsrTillDate.getCalendarButton().setFont(new Font("Verdana", Font.BOLD, 16));
                              dtChsrTillDate.getCalendarButton().setBackground(new Color(148, 0, 211));
                              dtChsrTillDate.setForeground(new Color(148, 0, 211));
                              dtChsrTillDate.setBackground(new Color(169, 169, 169));
                              dtChsrTillDate.setBounds(663, 83, 287, 22);
                              pnlParams.add(dtChsrTillDate);
                              dtChsrTillDate.setMaxSelectableDate(new Date());
                              //disabling the editing option in textfield of this datechooser..
                              JTextFieldDateEditor tillEditor = (JTextFieldDateEditor) dtChsrTillDate.getDateEditor();
                              tillEditor.setEditable(false);
                              
                              searchUtilLs.clear();
                              searchUtilLs = commonControllerObj.getAllDomains();
                              if(searchUtilLs != null){    
                                             //adding the display to select..default option..
                                             vctDomain.add("Please select a Domain");
                              }
                              System.out.println("searchUtilLs Size : "+searchUtilLs.size());
                              for(int utilIter = 0;utilIter < searchUtilLs.size();utilIter++){
                                             vctDomain.add(new JCheckBox(searchUtilLs.get(utilIter).toString(), false));
                              }
                              ccbDomain = new JComboCheckBox(vctDomain);
                              ccbDomain.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            
                                                            System.out.println("1");
                                                            if(ccbDomain.getSelectedItem() instanceof JCheckBox){
                                                                           JCheckBox selectedDomainCheckBox = (JCheckBox)ccbDomain.getSelectedItem();
                                                                           System.out.println("Selected Domain : "+selectedDomainCheckBox.toString()+" Domain Value : "+selectedDomainCheckBox.isSelected());
                                                                           if(!(selectedDomainCheckBox.getText().toString().contains("Select"))){
                                                                                          System.out.println("2");                                                              
                                                                                          searchUtilLs.clear();
                                                                                          searchUtilLs = commonControllerObj.getDomainToSubDomainMap(selectedDomainCheckBox.getText().toString());
                                                                                          if(searchUtilLs.size() > 0){
                                                                                          ccbSubDomain.removeAllItems();
                                                                                          ccbSubDomain.addItem("Select Domain");
                                                                                                         for(int k=0;k<searchUtilLs.size();k++){
                                                                                                                        ccbSubDomain.addItem(new JCheckBox(searchUtilLs.get(k).toString(), false));
                                                                                                         }
                                                                                          }ccbSubDomain.setEnabled(true);                                            
                                                                           }
                                                                           /*Code to remove Sub Domain , if Domain is unselected..
                                                                           System.out.println("Sub Domain values number :"+ccbSubDomain.getItemCount());*/
                                                                           //if Domain box is selected..Selection working opposite way..
                                                                           if(ccbDomain.getSelectedItem() instanceof JCheckBox && selectedDomainCheckBox.isSelected() == true){
                                                                                          System.out.println("inside checking");
                                                                                          for(int k=0;k<ccbSubDomain.getItemCount();k++){
                                                                                                         if(ccbSubDomain.getItemAt(k) instanceof JCheckBox ){
                                                                                                                        JCheckBox pointingSubDomainBox = (JCheckBox)ccbSubDomain.getItemAt(k);
                                                                                                                        
                                                                                                                        if(txtArPrvSubDomain.getText().contains(pointingSubDomainBox.getText())){
                                                                                                                                       String currentSubDomainVal = txtArPrvSubDomain.getText();
                                                                                                                                       if(currentSubDomainVal.contains(" ~ "+pointingSubDomainBox.getText())){
                                                                                                                                                      System.out.println("3");
                                                                                                                                                      currentSubDomainVal.replace(" ~ "+pointingSubDomainBox.getText(), "");
                                                                                                                                                      txtArPrvSubDomain.setText(currentSubDomainVal);
                                                                                                                                       }else if(currentSubDomainVal.contains(pointingSubDomainBox.getText()+" ~ ")){
                                                                                                                                                      System.out.println("4");
                                                                                                                                                      currentSubDomainVal = currentSubDomainVal.replace(pointingSubDomainBox.getText()+" ~ ", "");
                                                                                                                                                      txtArPrvSubDomain.setText(currentSubDomainVal);
                                                                                                                                       }else{
                                                                                                                                                      System.out.println("5");
                                                                                                                                                      txtArPrvSubDomain.setText("ALL");
                                                                                                                                       }
                                                                                                                        }
                                                                                                         }
                                                                                          }ccbSubDomain.setEnabled(false);
                                                                           }
                                                                           
                                                                           //as it works in reverse form currently..When Domain is not selected..
                                                                           if(selectedDomainCheckBox.isSelected() == false && txtArPrvDomain.getText().equals("ALL") 
                                                                                                         && !txtArPrvDomain.getText().contains(selectedDomainCheckBox.getText())){
                                                                                          System.out.println("6");
                                                                                          txtArPrvDomain.setText(selectedDomainCheckBox.getText());
                                                                           }else if(selectedDomainCheckBox.isSelected() == false 
                                                                                                         && !txtArPrvDomain.getText().contains(selectedDomainCheckBox.getText())){
                                                                                          System.out.println("7");
                                                                                          txtArPrvDomain.setText(txtArPrvDomain.getText()+" ~ "+selectedDomainCheckBox.getText());
                                                                           }else{
                                                                                          System.out.println("8");
                                                                                          String currentSetVal = txtArPrvDomain.getText();
                                                                                          if(currentSetVal.contains(" ~ "+selectedDomainCheckBox.getText())){
                                                                                                         System.out.println("9");
                                                                                                         currentSetVal = currentSetVal.replace(" ~ "+selectedDomainCheckBox.getText(),"");
                                                                                                         txtArPrvDomain.setText(currentSetVal);
                                                                                          }else if(currentSetVal.contains(selectedDomainCheckBox.getText()+" ~ ")){
                                                                                                         System.out.println("10");
                                                                                                         currentSetVal = currentSetVal.replace(selectedDomainCheckBox.getText()+" ~ ","");
                                                                                                         txtArPrvDomain.setText(currentSetVal);
                                                                                          }else{
                                                                                                         System.out.println("11");
                                                                                                         txtArPrvDomain.setText("ALL");
                                                                                          }
                                                                           }
                                                            }else{
                                                                           System.out.println("12");
                                                                           ccbSubDomain.removeAllItems();
                                                                           ccbSubDomain.addItem("Please Select a Domain");
                                                            }
                                             }
                              });
               /*           cmbDomain.addItemListener(new ItemListener() {
                                             public void itemStateChanged(ItemEvent arg0) {
                                                            System.out.println("Selected :"+cmbDomain.getSelectedItem().toString());
                                             }
                              });*/
                              ccbDomain.setMaximumRowCount(100);
                              ccbDomain.setForeground(new Color(148, 0, 211));
                              ccbDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              ccbDomain.setBounds(152, 29, 287, 22);
                              pnlParams.add(ccbDomain);
                              
                              vctSubDomain.add("Please Select a Domain");
               
                              ccbSubDomain = new JComboCheckBox(vctSubDomain);
                              ccbSubDomain.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            
                                                            if(ccbSubDomain.getSelectedItem() instanceof JCheckBox){
                                                                           
                                                                           JCheckBox selectedSubDomainBox = (JCheckBox)ccbSubDomain.getSelectedItem();
                                                                           System.out.println("Selected SubDomain : "+selectedSubDomainBox.toString()+" Domain Value : "+selectedSubDomainBox.isSelected());
                                                                           //selected working opposite..When Sub Domain is not selected..
                                                                           if(selectedSubDomainBox.isSelected() == false && txtArPrvSubDomain.getText().equals("ALL") 
                                                                                                         && !txtArPrvSubDomain.getText().contains(selectedSubDomainBox.getText())){
                                                                                          JCheckBox markCurrentDomainAsSelected = (JCheckBox) ccbDomain.getSelectedItem();
                                                                                          markCurrentDomainAsSelected.setSelected(true);
                                                                                          System.out.println("13");
                                                                                          System.out.println("here");
                                                                                          txtArPrvSubDomain.setText(selectedSubDomainBox.getText());
                                                                           }else if(selectedSubDomainBox.isSelected() == false 
                                                                                                         && !txtArPrvSubDomain.getText().contains(selectedSubDomainBox.getText())){
                                                                                          JCheckBox markCurrentDomainAsSelected = (JCheckBox) ccbDomain.getSelectedItem();
                                                                                          markCurrentDomainAsSelected.setSelected(true);
                                                                                          System.out.println("14");
                                                                                          txtArPrvSubDomain.setText(txtArPrvSubDomain.getText()+" ~ "+selectedSubDomainBox.getText());
                                                                           }else{
                                                                                          System.out.println("15");
                                                                                          String currentSetVal = txtArPrvSubDomain.getText();
                                                                                          if(currentSetVal.contains(" ~ "+selectedSubDomainBox.getText())){
                                                                                                         System.out.println("16");
                                                                                                         currentSetVal = currentSetVal.replace(" ~ "+selectedSubDomainBox.getText(), "");
                                                                                                         txtArPrvSubDomain.setText(currentSetVal);
                                                                                          }else if(currentSetVal.contains(selectedSubDomainBox.getText()+" ~ ")){
                                                                                                         System.out.println("17");
                                                                                                         currentSetVal = currentSetVal.replace(selectedSubDomainBox.getText()+" ~ ", "");
                                                                                                         txtArPrvSubDomain.setText(currentSetVal);
                                                                                          }else{
                                                                                                         System.out.println("18");
                                                                                                         txtArPrvSubDomain.setText("ALL");
                                                                                          }
                                                                           }
                                                            }
                                             }              
                              });
                              ccbSubDomain.setMaximumRowCount(100);
                              ccbSubDomain.setForeground(Color.BLACK);
                              ccbSubDomain.getEditor().getEditorComponent().addMouseListener(new MouseAdapter() {
                                             @Override
                                             public void mousePressed(MouseEvent e) {
                                                                           System.out.println("Number of radios : "+ccbSubDomain.getComponentCount());
                                             }
                              });
                              ccbSubDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              ccbSubDomain.setBounds(663, 32, 287, 22);
                              pnlParams.add(ccbSubDomain);
                              
                              vctRtCaus.add("Please Select a Root Cause");
                              searchUtilLs.clear();
                              searchUtilLs = commonControllerObj.getAllRootCause();
                              searchUtilLs.add(0, "Select Root Cause");
                              for(int i=0;i<searchUtilLs.size();i++){
                                             vctRtCaus.add(new JCheckBox(searchUtilLs.get(i),false));
                              }
                              ccbRootCause = new JComboCheckBox(vctRtCaus);
                              ccbRootCause.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            
                                                            if(ccbRootCause.getSelectedItem() instanceof JCheckBox){
                                                                           
                                                                           JCheckBox selectedRootCauseBox = (JCheckBox)ccbRootCause.getSelectedItem();
                                                                           if(selectedRootCauseBox.isSelected() == false && txtArPrvRootCause.getText().equals("ALL") 
                                                                                                         && !txtArPrvRootCause.getText().contains(selectedRootCauseBox.getText())){
                                                                                          System.out.println("here");
                                                                                          txtArPrvRootCause.setText(selectedRootCauseBox.getText());
                                                                           }else if(selectedRootCauseBox.isSelected() == false 
                                                                                                         && !txtArPrvRootCause.getText().contains(selectedRootCauseBox.getText())){
                                                                                          
                                                                                          txtArPrvRootCause.setText(txtArPrvRootCause.getText()+" ~ "+selectedRootCauseBox.getText());
                                                                           }else{
                                                                                          
                                                                                          String currentSetVal = txtArPrvRootCause.getText();
                                                                                          if(currentSetVal.contains(" ~ "+selectedRootCauseBox.getText())){
                                                                                                         
                                                                                                         currentSetVal = currentSetVal.replace(" ~ "+selectedRootCauseBox.getText(), "");
                                                                                                         txtArPrvRootCause.setText(currentSetVal);
                                                                                          }else if(currentSetVal.contains(selectedRootCauseBox.getText()+" ~ ")){
                                                                                                         
                                                                                                         currentSetVal = currentSetVal.replace(selectedRootCauseBox.getText()+" ~ ", "");
                                                                                                         txtArPrvRootCause.setText(currentSetVal);
                                                                                          }else{
                                                                                                         txtArPrvRootCause.setText("ALL");
                                                                                          }
                                                                           }
                                                            }
                                             }              
                              });
                              ccbRootCause.setMaximumRowCount(100);
                              ccbRootCause.setForeground(Color.BLACK);
                              ccbRootCause.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              ccbRootCause.setBounds(152, 132, 287, 22);
                              pnlParams.add(ccbRootCause);
                              
                              searchUtilLs.clear();
                              searchUtilLs = commonControllerObj.getAllCountries();
                              vctCountry.add("Please Select a Country");
                              for(int i=0;i<searchUtilLs.size();i++){
                                             vctCountry.add(new JCheckBox(searchUtilLs.get(i),false));
                                             
                              }
                              ccbCountry = new JComboCheckBox(vctCountry);
                              ccbCountry.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            
                                                            if(ccbCountry.getSelectedItem() instanceof JCheckBox){
                                                                           
                                                                           JCheckBox selectedCountryBox = (JCheckBox)ccbCountry.getSelectedItem();
                                                                           if(selectedCountryBox.isSelected() == false && txtArPrvCountry.getText().equals("ALL") 
                                                                                                         && !txtArPrvCountry.getText().contains(selectedCountryBox.getText())){
                                                                                          System.out.println("here");
                                                                                          txtArPrvCountry.setText(selectedCountryBox.getText());
                                                                           }else if(selectedCountryBox.isSelected() == false 
                                                                                                         && !txtArPrvCountry.getText().contains(selectedCountryBox.getText())){
                                                                                          
                                                                                          txtArPrvCountry.setText(txtArPrvCountry.getText()+" ~ "+selectedCountryBox.getText());
                                                                           }else{
                                                                                          
                                                                                          String currentSetVal = txtArPrvCountry.getText();
                                                                                          if(currentSetVal.contains(" ~ "+selectedCountryBox.getText())){
                                                                                                         
                                                                                                         currentSetVal = currentSetVal.replace(" ~ "+selectedCountryBox.getText(), "");
                                                                                                         txtArPrvCountry.setText(currentSetVal);
                                                                                          }else if(currentSetVal.contains(selectedCountryBox.getText()+" ~ ")){
                                                                                                         
                                                                                                         currentSetVal = currentSetVal.replace(selectedCountryBox.getText()+" ~ ", "");
                                                                                                         txtArPrvCountry.setText(currentSetVal);
                                                                                          }else{
                                                                                                         txtArPrvCountry.setText("ALL");
                                                                                          }
                                                                           }
                                                            }
                                             }              
                              });
                              ccbCountry.setMaximumRowCount(100);
                              ccbCountry.setForeground(Color.BLACK);
                              ccbCountry.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              ccbCountry.setBounds(663, 135, 287, 22);
                              pnlParams.add(ccbCountry);
                              
                              btnListTickets = new JButton("List Tickets");
                              btnListTickets.setForeground(new Color(148, 0, 211));
                              btnListTickets.addActionListener(new ActionListener() {
                                             @SuppressWarnings("static-access")
                                             public void actionPerformed(ActionEvent e) {
                                                            
                                                            if(dtChsrFromDate.getDate() != null && dtChsrTillDate != null){
                                                            
                                                                           String fromDate,tillDate = "";
                                                                           filteredTicketsLs.clear();
                                                                           List dateChooserLs = new ArrayList<>();
                                                                           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                           
                                                                           
                                                                           try{
                                                                                          
                                                                                          fromDate = sdf.format(dtChsrFromDate.getDate());
                                                                                          
                                                                                          //below code to Increment 1 with till Date for making it inclusive in search.. 
                                                                                          Calendar c = Calendar.getInstance();
                                                                                          c.setTime((dtChsrTillDate.getDate()));
                                                                                          c.add(Calendar.DATE, 1);  
                                                                                          tillDate =  sdf.format(c.getTime());
                                                                                          
                                                                                          dateChooserLs.add(fromDate);
                                                                                          dateChooserLs.add(tillDate);
                                                                                          System.out.println("fro : "+dateChooserLs.get(0) + "To : "+dateChooserLs.get(1));
                                                                           
                                                                                          searchFilterCriteriaMap.put("SelectedDates", dateChooserLs);
                                                                                          searchFilterCriteriaMap.put("Domain", getSelectedFilterCriterias("Domain"));
                                                                                          searchFilterCriteriaMap.put("SubDomain", getSelectedFilterCriterias("SubDomain"));
                                                                                          searchFilterCriteriaMap.put("RootCause", getSelectedFilterCriterias("RootCause"));
                                                                                          searchFilterCriteriaMap.put("Country", getSelectedFilterCriterias("Country"));
                                                                                          searchFilterCriteriaMap.put("Member", getSelectedFilterCriterias("Member"));
                                                                                          searchFilterCriteriaMap.put("Status", getSelectedFilterCriterias("Status"));
                                                                           
                                                                                          for(Map.Entry<String, List<String>> val : searchFilterCriteriaMap.entrySet()){
                                                                                                         System.out.println("Key :"+val.getKey()+"--"+val.getValue());
                                                                                          }
                                                                                                         
                                                                                          filteredTicketsLs = managerControllerObj.refinedSearchQueryBuilder(searchFilterCriteriaMap);
                                                                                          //add extra code for table showing here..
                                                                                          if(filteredTicketsLs.size()>0){
                                                                                                         System.out.println(filteredTicketsLs.size());
                                                                                                         filteredTktsUiObj  = new FilteredTicketsResult();
                                                                                                         filteredTktsUiObj.main(filteredTicketsLs);
                                                                                          }else{
                                                                                                         JOptionPane.showMessageDialog(frmRefinedSearch, "Sorry !! Your Search Filters fetched No results !! Please try with new filters..", "No Result to Display..",
                                                                                                                                       JOptionPane.INFORMATION_MESSAGE);
                                                                                          }
                                                                                          
                                                                           }catch(Exception ex){
                                                                                          System.out.println("RefinedSearchui :: RefinedSearchUi -- "+ex.getMessage());
                                                                           }
                                                                           finally{
                                                                                          dateChooserLs.clear();
                                                                                          searchFilterCriteriaMap.clear();
                                                                                          
                                                                           }
                                                                           
                                                            }else{
                                                                           JOptionPane.showMessageDialog(frmRefinedSearch, "Please Provide Dates..!", "Input required !!", JOptionPane.ERROR_MESSAGE);
                                                            }
                                             }

                                             private List<String> getSelectedFilterCriterias(String selectionField) {
                                                            
                                                            searchUtilLs.clear();
                                                            String fieldVal = "";
                                                            switch(selectionField){
                                                            
                                                            case "Domain" : 
                                                                           fieldVal = txtArPrvDomain.getText();
                                                                           break;
                                                                           
                                                            case "SubDomain" :         
                                                                           fieldVal = txtArPrvSubDomain.getText();
                                                                           break;
                                                                           
                                                            case "RootCause" :                         
                                                                           fieldVal = txtArPrvRootCause.getText();
                                                                           break;
                                                                           
                                                            case "Country" :                
                                                                           fieldVal = txtArPrvCountry.getText();
                                                                           break;
                                                                           
                                                            case "Member" :               
                                                                           fieldVal = txtArPrvMembers.getText();
                                                                           break;
                                                                           
                                                            case "Status" :                                  
                                                                           fieldVal = txtArPrvStatus.getText(); 
                                                                           break;
                                                                           
                                                            }
                                                            return formSeperateValues(fieldVal);
                                             }

                                             private List<String> formSeperateValues(String fieldToScan) {
                                                            
                                                            searchUtilLs.clear();
                                                            List<String> mapUtilLs = new ArrayList<String>();
                                                            if(fieldToScan.contains("~")){
                                                                           String[] splitString = new String[100];
                                                                           splitString = fieldToScan.split("~");
                                                                           for(String fieldVal : splitString){
                                                                                          mapUtilLs.add(fieldVal.trim());
                                                                           }
                                                            }else{
                                                                           String selectedString = (fieldToScan.equals("ALL")) ? "ALL" : fieldToScan.trim();
                                                                           mapUtilLs.add(selectedString);
                                                            }
                                                            return mapUtilLs;
                                             }
                              });
                              btnListTickets.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnListTickets.setBackground(new Color(255, 235, 205));
                              btnListTickets.setBounds(975, 30, 100, 130);
                              pnlParams.add(btnListTickets);
                              
                              pnlCriteriaPreview = new JPanel();
                              pnlCriteriaPreview.setBorder(new LineBorder(new Color(148, 0, 211), 2, true));
                              pnlCriteriaPreview.setBackground(new Color(240, 248, 255));
                              pnlCriteriaPreview.setBounds(12, 284, 1108, 344);
                              pnlMain.add(pnlCriteriaPreview);
                              pnlCriteriaPreview.setLayout(null);
                              
                              scrPnlCriteriaPrv = new JScrollPane();
                              scrPnlCriteriaPrv.setBounds(12, 13, 1084, 324);
                              pnlCriteriaPreview.add(scrPnlCriteriaPrv);
                              
                              pnlInside = new JPanel();
                              pnlInside.setForeground(new Color(148, 0, 211));
                              pnlInside.setBackground(new Color(240, 248, 255));
                              scrPnlCriteriaPrv.setViewportView(pnlInside);
                              pnlInside.setLayout(null);
                              
                              lblPrvDomain = new JLabel("Domain");
                              lblPrvDomain.setHorizontalAlignment(SwingConstants.CENTER);
                              lblPrvDomain.setForeground(new Color(255, 127, 80));
                              lblPrvDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblPrvDomain.setBackground(new Color(255, 218, 185));
                              lblPrvDomain.setBounds(12, 11, 76, 16);
                              pnlInside.add(lblPrvDomain);
                              
                              lblColon7 = new JLabel(":");
                              lblColon7.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon7.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon7.setBounds(100, 11, 20, 16);
                              pnlInside.add(lblColon7);
                              
                              btnPrvDomain = new JButton("Clear");
                              btnPrvDomain.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                            txtArPrvDomain.setText("ALL");
                                                            ccbDomain.setSelectedIndex(0);
                                                            for(int k=0;k<vctDomain.size();k++){
                                                                           if(ccbDomain.getModel().getElementAt(k) instanceof JCheckBox){
                                                                           
                                                                                          JCheckBox jBoxtoClear = (JCheckBox)ccbDomain.getModel().getElementAt(k);
                                                                                          jBoxtoClear.setSelected(false);
                                                                           }
                                                            }
                                             }
                              });
                              btnPrvDomain.setForeground(new Color(148, 0, 211));
                              btnPrvDomain.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnPrvDomain.setBackground(new Color(255, 235, 205));
                              btnPrvDomain.setBounds(973, 7, 97, 25);
                              pnlInside.add(btnPrvDomain);
                              
                              lblColon8 = new JLabel(":");
                              lblColon8.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon8.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon8.setBounds(100, 61, 20, 16);
                              pnlInside.add(lblColon8);
                              
                              btnPrvSubDomain = new JButton("Clear");
                              btnPrvSubDomain.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent arg0) {
                                                            txtArPrvSubDomain.setText("ALL");
                                                            ccbSubDomain.setSelectedIndex(0);
                                                            for(int k=0;k<vctSubDomain.size();k++){
                                                                           if(ccbSubDomain.getModel().getElementAt(k) instanceof JCheckBox){
                                                                           
                                                                                          JCheckBox jBoxtoClear = (JCheckBox)ccbSubDomain.getModel().getElementAt(k);
                                                                                          jBoxtoClear.setSelected(false);
                                                                           }
                                                            }
                                             }
                              });
                              btnPrvSubDomain.setBounds(973, 57, 97, 25);
                              btnPrvSubDomain.setForeground(new Color(148, 0, 211));
                              btnPrvSubDomain.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnPrvSubDomain.setBackground(new Color(255, 235, 205));
                              pnlInside.add(btnPrvSubDomain);
                              
                              lblPrvSubDomain = new JLabel("Sub-Domain");
                              lblPrvSubDomain.setHorizontalAlignment(SwingConstants.CENTER);
                              lblPrvSubDomain.setForeground(new Color(255, 127, 80));
                              lblPrvSubDomain.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblPrvSubDomain.setBackground(new Color(255, 218, 185));
                              lblPrvSubDomain.setBounds(12, 61, 89, 16);
                              pnlInside.add(lblPrvSubDomain);
                              
                              lblColon9 = new JLabel(":");
                              lblColon9.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon9.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon9.setBounds(100, 111, 20, 16);
                              pnlInside.add(lblColon9);
                              
                              btnPrvRootCause = new JButton("Clear");
                              btnPrvRootCause.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                            txtArPrvRootCause.setText("ALL");
                                                            ccbRootCause.setSelectedIndex(0);                                                        
                                                            for(int k=0;k<vctRtCaus.size();k++){
                                                                           if(ccbRootCause.getModel().getElementAt(k) instanceof JCheckBox){
                                                                           
                                                                                          JCheckBox jBoxtoClear = (JCheckBox)ccbRootCause.getModel().getElementAt(k);
                                                                                          jBoxtoClear.setSelected(false);
                                                                           }
                                                            }
                                             }
                              });
                              btnPrvRootCause.setForeground(new Color(148, 0, 211));
                              btnPrvRootCause.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnPrvRootCause.setBackground(new Color(255, 235, 205));
                              btnPrvRootCause.setBounds(973, 107, 97, 25);
                              pnlInside.add(btnPrvRootCause);
                              
                              lblColon10 = new JLabel(":");
                              lblColon10.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon10.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon10.setBounds(100, 165, 20, 16);
                              pnlInside.add(lblColon10);
                              
                              btnPrvCountry = new JButton("Clear");
                              btnPrvCountry.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                            txtArPrvCountry.setText("ALL");
                                                            ccbCountry.setSelectedIndex(0);                
                                                            for(int k=0;k<vctCountry.size();k++){
                                                                           if(ccbCountry.getModel().getElementAt(k) instanceof JCheckBox){
                                                                                          
                                                                                          JCheckBox jBoxtoClear = (JCheckBox)ccbCountry.getModel().getElementAt(k);
                                                                                          jBoxtoClear.setSelected(false);
                                                                           }
                                                            }
                                             }
                              });
                              btnPrvCountry.setForeground(new Color(148, 0, 211));
                              btnPrvCountry.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnPrvCountry.setBackground(new Color(255, 235, 205));
                              btnPrvCountry.setBounds(973, 161, 97, 25);
                              pnlInside.add(btnPrvCountry);
                              
                              lblPrvRootCause = new JLabel("Root Cause");
                              lblPrvRootCause.setHorizontalAlignment(SwingConstants.CENTER);
                              lblPrvRootCause.setForeground(new Color(255, 127, 80));
                              lblPrvRootCause.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblPrvRootCause.setBackground(new Color(255, 218, 185));
                              lblPrvRootCause.setBounds(12, 111, 76, 16);
                              pnlInside.add(lblPrvRootCause);
                              
                              lblPrvCountry = new JLabel("Country");
                              lblPrvCountry.setHorizontalAlignment(SwingConstants.CENTER);
                              lblPrvCountry.setForeground(new Color(255, 127, 80));
                              lblPrvCountry.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblPrvCountry.setBackground(new Color(255, 218, 185));
                              lblPrvCountry.setBounds(12, 165, 76, 16);
                              pnlInside.add(lblPrvCountry);
                              
                              lblPrvMembers = new JLabel("Members");
                              lblPrvMembers.setHorizontalAlignment(SwingConstants.CENTER);
                              lblPrvMembers.setForeground(new Color(255, 127, 80));
                              lblPrvMembers.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblPrvMembers.setBackground(new Color(255, 218, 185));
                              lblPrvMembers.setBounds(12, 215, 76, 16);
                              pnlInside.add(lblPrvMembers);
                              
                              lblColon11 = new JLabel(":");
                              lblColon11.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon11.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon11.setBounds(100, 215, 20, 16);
                              pnlInside.add(lblColon11);
                              
                              btnPrvMembers = new JButton("Clear");
                              btnPrvMembers.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                            txtArPrvMembers.setText("ALL");
                                                            for(int i=0;i<pnlMemberSelection.getComponentCount();i++){
                                                                           if(pnlMemberSelection.getComponent(i) instanceof JCheckBox){
                                                                                          
                                                                                          JCheckBox jBoxtoClear = (JCheckBox)pnlMemberSelection.getComponent(i);
                                                                                          jBoxtoClear.setSelected(true);
                                                                           }
                                                            }
                                             }
                              });
                              btnPrvMembers.setBounds(973, 211, 97, 25);
                              btnPrvMembers.setForeground(new Color(148, 0, 211));
                              btnPrvMembers.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnPrvMembers.setBackground(new Color(255, 235, 205));
                              pnlInside.add(btnPrvMembers);
                              
                              lblColon12 = new JLabel(":");
                              lblColon12.setHorizontalAlignment(SwingConstants.CENTER);
                              lblColon12.setFont(new Font("Tahoma", Font.BOLD, 13));
                              lblColon12.setBounds(100, 265, 20, 16);
                              pnlInside.add(lblColon12);
                              
                              btnPrvStatus = new JButton("Clear");
                              btnPrvStatus.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                            txtArPrvStatus.setText("Tickets alive between selected Dates");
                                                            rdobtnOpenOrClose.setSelected(true);
                                             }
                              });
                              btnPrvStatus.setBounds(973, 261, 97, 25);
                              btnPrvStatus.setForeground(new Color(148, 0, 211));
                              btnPrvStatus.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                              btnPrvStatus.setBackground(new Color(255, 235, 205));
                              pnlInside.add(btnPrvStatus);
                              
                              lblPrvStatus = new JLabel("Status");
                              lblPrvStatus.setHorizontalAlignment(SwingConstants.CENTER);
                              lblPrvStatus.setForeground(new Color(255, 127, 80));
                              lblPrvStatus.setFont(new Font("Futura Medium", Font.BOLD, 14));
                              lblPrvStatus.setBackground(new Color(255, 218, 185));
                              lblPrvStatus.setBounds(12, 265, 76, 16);
                              pnlInside.add(lblPrvStatus);
                              
                              scrPnlPrvDomain = new JScrollPane();
                              scrPnlPrvDomain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                              scrPnlPrvDomain.setBounds(132, 7, 829, 38);
                              pnlInside.add(scrPnlPrvDomain);
                              
                              txtArPrvDomain = new JTextArea();
                              txtArPrvDomain.setText("ALL");
                              txtArPrvDomain.setForeground(new Color(148, 0, 211));
                              txtArPrvDomain.setFont(new Font("Verdana", Font.PLAIN, 12));
                              txtArPrvDomain.setBackground(new Color(250, 235, 215));
                              scrPnlPrvDomain.setViewportView(txtArPrvDomain);
                              
                              scrPnlPrvSubDomain = new JScrollPane();
                              scrPnlPrvSubDomain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                              scrPnlPrvSubDomain.setBounds(132, 57, 829, 38);
                              pnlInside.add(scrPnlPrvSubDomain);
                              
                              txtArPrvSubDomain = new JTextArea();
                              txtArPrvSubDomain.setText("ALL");
                              txtArPrvSubDomain.setBackground(new Color(250, 235, 215));
                              txtArPrvSubDomain.setForeground(new Color(148, 0, 211));
                              txtArPrvSubDomain.setFont(new Font("Verdana", Font.PLAIN, 12));
                              scrPnlPrvSubDomain.setViewportView(txtArPrvSubDomain);
                              
                              scrPnlPrvRootCause = new JScrollPane();
                              scrPnlPrvRootCause.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                              scrPnlPrvRootCause.setBounds(132, 104, 829, 38);
                              pnlInside.add(scrPnlPrvRootCause);
                              
                              txtArPrvRootCause = new JTextArea();
                              txtArPrvRootCause.setText("ALL");
                              txtArPrvRootCause.setBackground(new Color(250, 235, 215));
                              txtArPrvRootCause.setForeground(new Color(148, 0, 211));
                              txtArPrvRootCause.setFont(new Font("Verdana", Font.PLAIN, 12));
                              scrPnlPrvRootCause.setViewportView(txtArPrvRootCause);
                              
                              scrPnlPrvCountry = new JScrollPane();
                              scrPnlPrvCountry.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                              scrPnlPrvCountry.setBounds(132, 159, 829, 38);
                              pnlInside.add(scrPnlPrvCountry);
                              
                              txtArPrvCountry = new JTextArea();
                              txtArPrvCountry.setText("ALL");
                              txtArPrvCountry.setBackground(new Color(250, 235, 215));
                              txtArPrvCountry.setForeground(new Color(148, 0, 211));
                              txtArPrvCountry.setFont(new Font("Verdana", Font.PLAIN, 12));
                              scrPnlPrvCountry.setViewportView(txtArPrvCountry);
                              
                              scrPnlPrvMembers = new JScrollPane();
                              scrPnlPrvMembers.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                              scrPnlPrvMembers.setBounds(132, 209, 829, 38);
                              pnlInside.add(scrPnlPrvMembers);
                              
                              txtArPrvMembers = new JTextArea();
                              txtArPrvMembers.setText("ALL");
                              scrPnlPrvMembers.setViewportView(txtArPrvMembers);
                              txtArPrvMembers.setBackground(new Color(250, 235, 215));
                              txtArPrvMembers.setForeground(new Color(148, 0, 211));
                              txtArPrvMembers.setFont(new Font("Verdana", Font.PLAIN, 12));
                              
                              scrPnlPrvStatus = new JScrollPane();
                              scrPnlPrvStatus.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                              scrPnlPrvStatus.setBounds(132, 259, 829, 38);
                              pnlInside.add(scrPnlPrvStatus);
                              
                              txtArPrvStatus = new JTextArea();
                              txtArPrvStatus.setText("Tickets alive between selected Dates");
                              scrPnlPrvStatus.setViewportView(txtArPrvStatus);
                              txtArPrvStatus.setBackground(new Color(250, 235, 215));
                              txtArPrvStatus.setForeground(new Color(148, 0, 211));
                              txtArPrvStatus.setFont(new Font("Verdana", Font.PLAIN, 12));
                              
                              frmRefinedSearch.setVisible(true);
               }
}
