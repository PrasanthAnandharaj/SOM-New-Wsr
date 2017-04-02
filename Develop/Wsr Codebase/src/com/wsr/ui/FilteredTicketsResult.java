package com.wsr.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.wsr.model.IncidentsBean;

import javax.swing.JTable;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@SuppressWarnings("serial")
public class FilteredTicketsResult {

                private JFrame frame;
                private JPanel contentPane;
                private JLabel lblTktCount;
                private JTable tblFilteredTickets;
                private JButton btnGenerateReportSheet;
                
                File folderPath;
                DefaultTableModel dtm = new DefaultTableModel();
                /**
                * Launch the application.
                * @return 
                 */
                public void main(List<IncidentsBean> filteredTicketsLs) {
                                
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
                                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"\\Develop\\Wsr Codebase\\Resources\\logo_passbook.png"));
                                frame.setTitle("Your Filtered Results !");
                                frame.setForeground(Color.LIGHT_GRAY);
                                frame.setBackground(Color.PINK);
                                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                frame.setBounds(100, 100, 951, 600);
                                contentPane = new JPanel();
                                contentPane.setForeground(new Color(255, 228, 196));
                                contentPane.setBackground(new Color(250, 240, 230));
                                contentPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(173, 216, 230), new Color(216, 191, 216)));
                                frame.setContentPane(contentPane);
                                contentPane.setLayout(null);
                                
                                lblTktCount = new JLabel("No. of Rows affected : ");
                                lblTktCount.setHorizontalAlignment(SwingConstants.CENTER);
                                lblTktCount.setForeground(new Color(255, 20, 147));
                                lblTktCount.setBackground(new Color(240, 240, 240));
                                lblTktCount.setFont(new Font("Verdana", Font.PLAIN, 14));
                                lblTktCount.setBounds(651, 511, 217, 16);
                                contentPane.add(lblTktCount);
                                
                                btnGenerateReportSheet = new JButton("Generate as Excel Sheet");
                                btnGenerateReportSheet.setForeground(new Color(148, 0, 211));
                                btnGenerateReportSheet.setFont(new Font("Verdana", Font.PLAIN, 13));
                                btnGenerateReportSheet.setBackground(new Color(255, 228, 225));
                                btnGenerateReportSheet.setBounds(347, 511, 200, 25);
                                contentPane.add(btnGenerateReportSheet);
                                
                                JScrollPane scrPnlTicketsTbl = new JScrollPane();
                                scrPnlTicketsTbl.setBounds(12, 13, 909, 485);
                                contentPane.add(scrPnlTicketsTbl);
                                System.out.println("check : "+filteredTicketsLs.size());
                                tblFilteredTickets = new JTable();
                                scrPnlTicketsTbl.setViewportView(tblFilteredTickets);
                                tblFilteredTickets.setToolTipText("Sorting is enabled on columns");
                                tblFilteredTickets.setShowGrid(false);
                                tblFilteredTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                                tblFilteredTickets.setForeground(new Color(148, 0, 211));
                                tblFilteredTickets.setFont(new Font("Futura Medium", Font.PLAIN, 14));
                                tblFilteredTickets.setColumnSelectionAllowed(true);
                                tblFilteredTickets.setCellSelectionEnabled(true);
                                tblFilteredTickets.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(135, 206, 235), new Color(211, 211, 211)));
                                
                                //DefaultTableModel dtm = new DefaultTableModel();
                                dtm.setRowCount(0);
                                dtm.setColumnCount(0);
                                
                                tblFilteredTickets.setModel(dtm);
                                TableColumnModel columnModel = tblFilteredTickets.getColumnModel();
                                JTableHeader header = tblFilteredTickets.getTableHeader();
                                header.setBackground(Color.BLUE);
                    header.setForeground(Color.WHITE);
                    
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
                                                                                                                                                
                                                for(int i=0;i<filteredTicketsLs.size();i++){
                                                                                dtm.addRow(new Object[]{filteredTicketsLs.get(i).getIncidentID(),filteredTicketsLs.get(i).getTitle(),filteredTicketsLs.get(i).getSeverity(),
                                                                                                                filteredTicketsLs.get(i).getAssignee(),filteredTicketsLs.get(i).getSla_target_date()});
                                                                                
                                                }                                                                                                              
                                                //Setting the row Height..
                                                tblFilteredTickets.setRowHeight(70);
                                }catch(Exception exp){
                                                System.out.println("FilteredTicketsResults -- FilteredTicketsResult :"+exp.getMessage());
                                }finally{
                                                
                                                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dtm);
                                                tblFilteredTickets.setRowSorter(sorter);
                                                
                                                lblTktCount.setText(tblFilteredTickets.getRowCount()+ " record(s) listed");
                                                //clearing list at the end of method..
                                                filteredTicketsLs.clear();
                                }
                                
                                btnGenerateReportSheet.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent arg0) {
                                                                System.out.println("click but");
                                                                ExportToExcel(tblFilteredTickets,dtm);
                                                                
                                                                
                                                }
                                });

                                frame.setVisible(true);
                                
                }
                
                public void ExportToExcel(final JTable tblFilteredTickets, final DefaultTableModel dtm){
                                
                                
                                
                                frame = new JFrame();
                                //frame.setBounds(100, 100, 704, 554);
                                frame.setType(Type.NORMAL);
                                frame.setResizable(false);
                                frame.setTitle("Update Tickets");
                                frame.setBounds(100, 100, 450, 232);
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
                                btnSelect.setBounds(311, 40, 97, 35);
                                panel.add(btnSelect);
                                
                                JButton btnUpdate = new JButton("Export");
                                btnUpdate.setForeground(new Color(148, 0, 211));
                                btnUpdate.setBackground(new Color(211, 211, 211));
                                btnUpdate.setFont(new Font("Futura Medium", Font.PLAIN, 16));
                                btnUpdate.setBounds(50, 80, 80, 20);
                                btnUpdate.setBounds(40, 127, 234, 25);
                                panel.add(btnUpdate);
                                
                                frame.setVisible(true);
                                
                                btnSelect.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent arg0) {
                                                                JFileChooser fileChooser = new JFileChooser();
                                                                fileChooser.setCurrentDirectory(new java.io.File("Libraries\\Documents"));
                                                                fileChooser.setDialogTitle("Choose to Download !!");
                                                                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                                                int returnVal = fileChooser.showOpenDialog(btnGenerateReportSheet);
                                                                textArea.setText("");
                                                                if(returnVal == JFileChooser.APPROVE_OPTION){
                                                                                folderPath = fileChooser.getSelectedFile();
                                                                                if(folderPath.isDirectory()){
                                                                                                textArea.setText(folderPath.getAbsolutePath());
                                                                                }else{
                                                                                                JOptionPane.showMessageDialog(frame, "Please Select Folders only !!", "Wrong File", JOptionPane.ERROR_MESSAGE);
                                                                                }
                                                                }
                                                }
                });
                                
                                btnUpdate.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent arg0) {
                                                                try{
                                                                                
                                                                fillData(tblFilteredTickets,folderPath,dtm);
                                                                
                                                } catch (Exception ex) {
                ex.printStackTrace();
            }
                                                }                                              
                                                });           
                }
                
                void fillData(JTable table, File file,DefaultTableModel dtm) {
                                
                                try{
                                                String filePath = file.toString();
                                                filePath = filePath.concat("\\result.xls");
                                                File finalFilePath = new File(filePath);
                                                
                                                //Finds the workbook instance for XLSX file
                                                HSSFWorkbook myWorkBook = new HSSFWorkbook ();
                                                
                                                // create first sheet from the XLSX workbook
                                                HSSFSheet mySheet = myWorkBook.createSheet("Tickets Genearted");
                                                HSSFFont sheetTitleFont = myWorkBook.createFont();
                                                HSSFCellStyle cellStyle = myWorkBook.createCellStyle();
                                                
                                                TableModel model = table.getModel();
                                                // Adding Column Heading
                                                HSSFRow fRowHeading = mySheet.createRow((short) 0);
                                                
                                                for(int c=0; c < model.getColumnCount(); c++){
                HSSFCell cell = fRowHeading.createCell((short) c);
                cell.setCellValue(dtm.getColumnName(c));
                cell.setCellStyle(cellStyle);
                System.out.println(cell.toString());
            }
                                                for (int i = 0; i < model.getRowCount(); i++) {
                HSSFRow fRow = mySheet.createRow((short) i+1);
                
                for (int j = 0; j < model.getColumnCount(); j++) {
                    HSSFCell cell = fRow.createCell((short) j);
                    cell.setCellValue(model.getValueAt(i, j).toString());
                    cell.setCellStyle(cellStyle);
                   System.out.println(cell.toString());
                }
            }                                  
                                                FileOutputStream fileOutputStream;
            fileOutputStream = new FileOutputStream(finalFilePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            myWorkBook.write(bos);
            bos.close();
            fileOutputStream.close();
            JOptionPane.showMessageDialog(null, "Data saved at " + filePath +
                               "SUCCESSFULLY" , "Message",
                                JOptionPane.INFORMATION_MESSAGE);            
                                }catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Something went Wrong!.. ", "Error Occured", JOptionPane.ERROR_MESSAGE);
                                }              
                }
}
