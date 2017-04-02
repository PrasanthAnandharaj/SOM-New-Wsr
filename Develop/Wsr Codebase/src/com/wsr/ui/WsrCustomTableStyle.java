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
		
		if(!renderingTable.equalsIgnoreCase("SuggestionTable")){
			System.out.println("inside");
			tktTable.setBorder(new LineBorder(new Color(211, 211, 211), 2, true));
			tktTable.setAutoResizeMode(JTable.WIDTH);
		}
		
		if(renderingTable.equalsIgnoreCase("ProvideUpdateTable")){
			tktTable.setToolTipText("Click on any row and \'Invalidate button' to remove any button");
		}else{
			tktTable.setToolTipText("Double Click on any row to provide update");
		}
		
		
		return tktTable;

	}

}
