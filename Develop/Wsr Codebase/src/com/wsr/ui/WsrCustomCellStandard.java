package com.wsr.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class WsrCustomCellStandard extends JTextArea implements TableCellRenderer {
	
	public WsrCustomCellStandard(){
		
		//Table cell Properties
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		
		setText((value == null) ? "" : value.toString());
		setEditable(true);
		
		return this;
	}
	
	

}
