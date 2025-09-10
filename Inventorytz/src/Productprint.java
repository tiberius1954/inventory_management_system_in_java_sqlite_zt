import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.*;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;

import Classes.Grlib;
import Classes.Hhelper;
import Classes.Printzt;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Productprint extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	int rownumber = 0;
	int pagenumber = 0;
	static StringUtils hss = new StringUtils();
	Vector<String> header = new Vector();
	JTable ptable;
	Productprint() {

	}
	
	Productprint(JTable ttable) {	
		ptable = ttable;
		init();
		hh.iconhere(this);
	}
	private void init() {
	 setBounds(0, 0, 1060, 640);
		setLocationRelativeTo(null);
		cc = getContentPane();
		cc.setLayout(null);
		cc.setSize(1030, 600);
		txt_area = new JTextArea();
		txt_area.setFont(new Font("Monospaced", Font.BOLD, 12));
		txt_area.setBounds(10, 10, 1030, 470);
		scrPane = new JScrollPane();
		scrPane.setBounds(10, 10, 1030, 500);
		scrPane.setViewportView(txt_area);
		cc.add(scrPane);
		btnprint = new JButton("Print");
		btnprint.setBounds(500, 540, 80, 30);
		cc.add(btnprint);
		btnprint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Printzt pri = new Printzt();
				pri.ztprintel(txt_area);
				dispose();
			}
		});
			
		String dline = "  " + hh.repeat("=", 140) + "\n";
		String space60 = hh.repeat(" ", 60);
		String space70 = hh.repeat(" ", 70);
		String head0 = space60 + "PRODUCT LIST \n";		
			String head1 = "  " + hh.padr("Date:", 7) + hh.padr(hh.currentDate(),20) + "\n";					
			String head2 = "   " + hh.padr("Productcode", 12) + " " + hh.padr("Productname", 30) + " "
					+ hh.padr("Unit", 6) + " " + hh.padr("Subcategory", 15) + " " + hh.padl("Cost price", 10) + " "
					+ hh.padl("Sell price", 10) + " " + hh.padl("Discount%", 10) + " " + hh.padl("Vat%", 10) + " "
					+ hh.padr("Description", 40)+ "\n";			
			header.add(dline);
			header.add(head0);
            header.add(head1);
			header.add(dline);
			header.add(head2);
			header.add(dline);

			rownumber = 0;
	       headerprint();
			rownumber = 8;
			
			for (int i = 0; i < ptable.getRowCount(); i++) {
			String  productcode = ptable.getValueAt(i, 1).toString();
			String name = ptable.getValueAt(i, 2).toString();		
			String unit =  ptable.getValueAt(i, 4).toString();
			String subcategory =  ptable.getValueAt(i, 6).toString();
			String description = ptable.getValueAt(i, 7).toString();
			String costprice = ptable.getValueAt(i, 8).toString();
			String sellprice = ptable.getValueAt(i, 9).toString();
			String discount = ptable.getValueAt(i, 10).toString();
			String vat = ptable.getValueAt(i, 11).toString();
			txt_area.append("   " + hh.padr(productcode, 12) + " "
					+ hh.padr(name, 30) + " " +hh.padr(unit, 6)+ hh.padr(subcategory, 15) + " "
					+ hh.padl(costprice, 10) + " " + hh.padl(sellprice, 10) + " "
					+ hh.padl(discount, 10) + " "+ hh.padl(vat, 10) 
					+ " " + hh.padr(description, 40)  + "\n");	

				rownumber++;		
				int mrow = rownumber % 73;
				if (mrow == 0) {
					txt_area.append(dline);
					pagenumber++;
					txt_area.append("*" + space70 + "Page " + pagenumber + "\n");
					txt_area.append(" \n");
					headerprint();
					rownumber = rownumber + 7;
				}
			}
			txt_area.append(dline);		
		
	}
	void headerprint() {
		txt_area.append((String) header.get(0));
		txt_area.append((String) header.get(1));
		txt_area.append((String) header.get(2));
		txt_area.append((String) header.get(3));
		txt_area.append((String) header.get(4));
	    txt_area.append((String) header.get(5));
	}
	
	
	public static void main(String[] args) {
		Productprint ff = new Productprint();
		ff.setBounds(0, 0, 1060, 640);
		ff.setLocationRelativeTo(null);
		ff.setVisible(true);
	}

	JTextArea txt_area;
	JScrollPane scrPane;
	JButton btnprint;
	Container cc;
	
}
