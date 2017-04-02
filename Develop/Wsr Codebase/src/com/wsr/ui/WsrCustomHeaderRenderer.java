package com.wsr.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class WsrCustomHeaderRenderer implements TableCellRenderer {

	DefaultTableCellRenderer tblHeader;
	public WsrCustomHeaderRenderer(JTable ticketsTbl, JTableHeader header) {
		
		try{
			tblHeader = new DefaultTableCellRenderer();
			
			tblHeader = (DefaultTableCellRenderer)ticketsTbl.getTableHeader().getDefaultRenderer();
			tblHeader.setHorizontalAlignment(JLabel.CENTER);
			header.setBackground(Color.decode("#f0f8ff"));
			header.setForeground(Color.BLUE);
			header.setFont(new Font("SansSerif", Font.BOLD, 14));
	
		}catch(Exception ex){	
			
			System.out.println("WsrCustomHeaderRenderer -- WsrCustomHeaderRenderer :: "+ex.getMessage());
		}
		
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		return tblHeader.getTableCellRendererComponent(table, value, isSelected, 
				hasFocus, row, column);
	}

}
