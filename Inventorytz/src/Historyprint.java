
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Hhelper.StringUtils;
import Classes.Printzt;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Historyprint extends JFrame {
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
	String sbtid = "";
	static StringUtils hss = new StringUtils();
	Vector<String> header = new Vector();
	String sbankname = "";
	String sname = "";
	String sdate1 = "";
	String sdate2 = "";
	String sregister = "";
	JTable ttable;

	Historyprint() {

	}

	Historyprint(String bankname, String name, String date1, String date2, String register, JTable btable) {
		sbankname = bankname;
		sname = name;
		sdate1 = date1;
		sdate2 = date2;
		sregister = register;
		ttable = btable;
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
			String head0 = space60 + "BANK TRANSACTION HISTORY \n";			

				String head1 = "  " + hh.padr("Bankname:", 10) + hh.padr(sbankname,400) + "\n"
						 +"  "+ hh.padr("Partner:", 10) + hh.padr(sname, 50) + " "+hh.padr("Date:", 7)
						+ hh.padr(sdate1, 10) + "- " + hh.padr(sdate2, 10) + "  " + hh.padr("Register:", 10)
						+ hh.padr(sregister, 5)  + "\n";

				String head2 = "  " + hh.padr("Transaction_no", 14) + " " + hh.padr("Date", 10) + " "
						+ hh.padr("Trans_type", 10) + " " + hh.padl("Amount", 9) + " " + hh.padr("Claim", 23) + " "
						+ hh.padl("Reg.", 4) + " " + hh.padr("Partner", 25) + " " + hh.padr("Receipt", 18) + " "
						+ hh.padr("References", 17)+ "\n";
				
				header.add(dline);
				header.add(head0);
	            header.add(head1);
				header.add(dline);
				header.add(head2);
				header.add(dline);

				rownumber = 0;
		       headerprint();
				rownumber = 8;
				
				for (int i = 0; i < ttable.getRowCount(); i++) {
				String  btnumber = ttable.getValueAt(i, 1).toString();
				String bdate = ttable.getValueAt(i, 2).toString();
				String btype = ttable.getValueAt(i, 3).toString();
				String amount =  ttable.getValueAt(i, 4).toString();
				String claim = ttable.getValueAt(i, 5).toString();
				String register = ttable.getValueAt(i, 6).toString();
				String name = ttable.getValueAt(i, 7).toString();
				String receipt = ttable.getValueAt(i, 8).toString();
				String referenc = ttable.getValueAt(i, 9).toString();
				txt_area.append("  " + hh.padr(btnumber, 14) + " "
						+ hh.padr(bdate, 10) + " " +hh.padr(btype, 10)+ hh.padl(hh.bsf(amount), 10) + " "
						+ hh.padr(claim, 23) + " " + hh.padl(register, 4) + " "
						+ hh.padr(name, 25) + " "+ hh.padr(receipt, 18) 
						+ " " + hh.padr(referenc, 17)  + "\n");	

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
		Historyprint ff = new Historyprint();
		ff.setBounds(0, 0, 1060, 640);
		ff.setLocationRelativeTo(null);
		ff.setVisible(true);
	}

	JTextArea txt_area;
	JScrollPane scrPane;
	JButton btnprint;
	Container cc;

}
