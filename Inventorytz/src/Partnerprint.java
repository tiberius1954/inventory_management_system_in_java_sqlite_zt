import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.*;

import Classes.Grlib;
import Classes.Hhelper;
import Classes.Printzt;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Partnerprint extends JFrame {
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

	Partnerprint() {

	}

	Partnerprint(JTable ttable) {
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
		String head0 = space60 + "PARTNER LIST \n";
		String head1 = "   " + hh.padr("Name", 40) +" "+ hh.padr("Phone", 30) +" "+ hh.padr("Email", 30)+" "
		+hh.padr("Role", 10)+" "+ hh.padr("Gender", 6)+" "+ hh.padr("Dob", 8)+" "+ hh.padr("Reg. date", 8)+"\n";
		String head2 = "   " + hh.padr("Country", 30) + " " + hh.padr("City", 20) + " " + hh.padr("Address", 40)
				+ " " + hh.padr("Postcode", 10) + " " + hh.padr("Remark", 40)  + "\n";
		
		header.add(dline);
		header.add(head0);
		header.add(dline);
		header.add(head1);		
		header.add(head2);
		header.add(dline);

		rownumber = 0;
		headerprint();
		rownumber = 8;
	
		for (int i = 0; i < ptable.getRowCount(); i++) {
			String name = ptable.getValueAt(i, 1).toString();
			String phone = ptable.getValueAt(i, 2).toString();
			String email= ptable.getValueAt(i, 3).toString();
			String role = ptable.getValueAt(i, 4).toString();
			String country = ptable.getValueAt(i, 5).toString();
			String city = ptable.getValueAt(i, 7).toString();
			String address= ptable.getValueAt(i, 8).toString();
			String postcode = ptable.getValueAt(i, 9).toString();
			String remark = ptable.getValueAt(i, 10).toString();
			String gender = ptable.getValueAt(i, 11).toString();
			String dob = ptable.getValueAt(i, 12).toString();
			String regdate = ptable.getValueAt(i, 13).toString();
			
			txt_area.append("   " + hh.padr(name, 40) + " " + hh.padr(phone, 30) + " " + hh.padr(email, 30)
					+ hh.padr(role, 10) + " " + hh.padr(gender, 6) + " " + hh.padr(dob, 8) + " "
					+ hh.padr(regdate, 8) + "\n");
			
			txt_area.append("   " + hh.padr(country, 30) + " " + hh.padr(city, 20) + " " + hh.padr(address, 40)
			+ hh.padr(postcode, 10) + " " + hh.padr(remark, 40) + "\n");

			rownumber= rownumber + 2;
			int mrow = rownumber % 72;
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
		Partnerprint ff = new Partnerprint();
		ff.setBounds(0, 0, 1060, 640);
		ff.setLocationRelativeTo(null);
		ff.setVisible(true);
	}

	JTextArea txt_area;
	JScrollPane scrPane;
	JButton btnprint;
	Container cc;
	JTable ptable;

}
