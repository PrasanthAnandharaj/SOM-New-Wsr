package com.wsr.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class WsrCustomTableStyle {
	
	public JTable setWsrTableStandard(JTable tktTable, String renderingTable){
		
		tktTable.setForeground(new Color(65, 105, 225));
		tktTable.setFont(new Font("Verdana", Font.PLAIN, 14));
		tktTable.setBackground(new Color(255, 255, 240));
		tktTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if(renderingTable.equals("TicketsTable")){
		
			tktTable.setRowHeight(60);
			tktTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tktTable.getColumnModel().getColumn(0).setPreferredWidth(100);
			tktTable.getColumnModel().getColumn(1).setPreferredWidth(500);
			tktTable.getColumnModel().getColumn(2).setPreferredWidth(80);
			tktTable.getColumnModel().getColumn(3).setPreferredWidth(150);
			tktTable.getColumnModel().getColumn(4).setPreferredWidth(100);
			tktTable.getColumnModel().getColumn(5).setPreferredWidth(150);
			tktTable.getColumnModel().getColumn(6).setPreferredWidth(100);
			tktTable.getColumnModel().getColumn(7).setPreferredWidth(120);
			tktTable.getColumnModel().getColumn(8).setPreferredWidth(120);
			tktTable.getColumnModel().getColumn(9).setPreferredWidth(120);
			tktTable.getColumnModel().getColumn(10).setPreferredWidth(120);
			tktTable.setToolTipText("Double Click on any row to provide update");
			tktTable.setBorder(new LineBorder(new Color(211, 211, 211), 2, true));
		
		}else if(renderingTable.equals("ProvideUpdate")){
			
			tktTable.setRowHeight(50);
			tktTable.setAutoResizeMode(JTable.WIDTH);
			tktTable.getColumnModel().getColumn(0).setPreferredWidth(100);
			tktTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			tktTable.getColumnModel().getColumn(2).setPreferredWidth(600);
			tktTable.setBorder(new LineBorder(new Color(211, 211, 211), 2, true));
			tktTable.setToolTipText("Click on any row and \'Invalidate button' to remove any button");
			
		}else if(renderingTable.equals("SuggestionTable")){
			
			tktTable.setRowHeight(60);
			tktTable.setAutoResizeMode(JTable.WIDTH);
			tktTable.getColumnModel().getColumn(0).setPreferredWidth(120);
			tktTable.getColumnModel().getColumn(1).setPreferredWidth(350);
			tktTable.setToolTipText("Double Click on any row to provide update");
			tktTable.setBorder(new LineBorder(new Color(211, 211, 211), 2, true));
			
		}
		
		return tktTable;

	}

}
