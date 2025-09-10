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

public class Invoiceprint extends JFrame {
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
	String stid = "";
	static StringUtils hss = new StringUtils();
	Vector<String> header = new Vector();

	Invoiceprint() {
	}

	Invoiceprint(String tid) {
		stid = tid;
		init();
		hh.iconhere(this);
	}

	void init() {
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
		String tnumber = "", tdate = "", ptype = "", name = "", country = "", address = "", city = "", postcode = "",
				subtotal = "", vat = "", grandtotal = "", remark = "", bankaccount = "";
		String cname = "", ccountry = "", caddress = "", ccity = "", cpostcode = "", cbankaccount = "";

		String dline = "  " + hh.repeat("=", 140) + "\n";
		String space60 = hh.repeat(" ", 60);
		String space70 = hh.repeat(" ", 70);
		String head0 = space60 + "I N V O I C E \n";

		try {
			String sql = "select t.tid, t.tnumber, t.incomeno,  t.tdate, t.ptype, t.parid, p.name,"
					+ "p.country, p.address, p.postcode, p.bankaccount, c.name as city, t.subtotal, t.vat,"
					+ " t.grandtotal,  t.remark from stransaction t left  join partner p on t.parid = p.parid"
					+ "  left  join cities c on p.cid = c.cid where tid='" + stid + "'";
			rs = dh.GetData(sql);
			while (rs.next()) {
				tnumber = rs.getString("tnumber");
				tdate = rs.getString("tdate");
				ptype = rs.getString("ptype");
				name = rs.getString("name");
				country = rs.getString("country");
				address = rs.getString("address");
				city = rs.getString("city");
				postcode = rs.getString("Postcode");
				subtotal = hh.bsf(rs.getString("subtotal"));
				vat = hh.bsf(rs.getString("vat"));
				grandtotal = hh.bsf(rs.getString("grandtotal"));
				remark = rs.getString("remark");
				bankaccount = rs.getString("bankaccount");
			}
			dh.CloseConnection();
			sql = "select name, country, address, city, postcode, bankaccount from params";
			rs = dh.GetData(sql);
			while (rs.next()) {
				cname = rs.getString("name");
				ccountry = rs.getString("country");
				caddress = rs.getString("address");
				ccity = rs.getString("city");
				cpostcode = rs.getString("postcode");
				cbankaccount = rs.getString("bankaccount");
			}
			dh.CloseConnection();

			String head1 = "  " + hh.padr("Customer:", 10) + hh.padr(name, 80) + "  " + hh.padr("Seller:", 8) + "  "
					+ hh.padr(cname, 60) + "\n" + "  " + hh.padr("Address:", 10) + hh.padr(country, 15) + " "
					+ hh.padr(city, 15) + " " + hh.padr(address, 30) + "  " + hh.padr(ccountry, 15) + " "
					+ hh.padr(ccity, 15) + " " + hh.padr(caddress, 30) + "\n" + "  " + hh.padr("bankaccount: ", 13)
					+ hh.padr(bankaccount, 100) + hh.padr(cbankaccount, 24) + "\n" + "  " + hh.padr("Date:", 6)
					+ hh.padr(tdate, 11) + "  " + "Paymode: " + hh.padr(ptype, 50) + hh.padl("Invoiceno: ", 47)
					+ hh.padr(tnumber, 10) + "\n";

			String head2 = "  " + hh.padr("Productcode", 14) + "  " + hh.padr("Productname", 30) + "  "
					+ hh.padl("Quantity", 10) + "  " + hh.padr("Unit", 10) + "  " + hh.padl("Price", 10) + "  "
					+ hh.padl("Discountprice", 13) + " " + hh.padl("Amount", 10) + " " + hh.padl("Vat%", 7) + " "
					+ hh.padl("Vat", 10) + " " + hh.padl("Totalamount", 11) + "\n";

			header.add(dline);
			header.add(head0);
			header.add(head1);
			header.add(dline);
			header.add(head2);
			header.add(dline);

			rownumber = 0;
			headerprint();
			rownumber = 8;
			sql = "select p.productcode, p.pname, t.qty, u.uname, t.price, t.discountprice, t.amount, t.vatp,"
					+ "t.vat, t.totalamount from stransaction_detail t  left  join product p on t.pid = p.pid "
					+ "left join unities u on t.uid =u.uid  where tid='" + stid + "'";
			rs = dh.GetData(sql);
			while (rs.next()) {
				txt_area.append("  " + hh.padr(rs.getString("productcode"), 14) + "  "
						+ hh.padr(rs.getString("pname"), 30) + "  " + hh.padl(hh.bsf(rs.getString("qty")), 10) + "  "
						+ hh.padr(rs.getString("uname"), 10) + "  " + hh.padl(hh.bsf(rs.getString("price")), 10) + "  "
						+ hh.padl(hh.bsf(rs.getString("discountprice")), 13) + " "
						+ hh.padl(hh.bsf(rs.getString("amount")), 10) + " " + hh.padl(rs.getString("vatp"), 7) + " "
						+ hh.padl(hh.bsf(rs.getString("vat")), 10) + " "
						+ hh.padl(hh.bsf(rs.getString("totalamount")), 11) + "\n");
				rownumber++;
		//		int mrow = rownumber % 75;
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
			dh.CloseConnection();
			txt_area.append(dline);
			txt_area.append(hh.padl(subtotal, 110) + "  " + hh.padl(vat, 17) + " " + hh.padl(grandtotal, 11));

		} catch (SQLException sqle) {
			System.out.println(sqle.toString());
		}
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
		Invoiceprint ff = new Invoiceprint("47");
		ff.setBounds(0, 0, 1060, 640);
		ff.setLocationRelativeTo(null);
		ff.setVisible(true);
	}

	JTextArea txt_area;
	JScrollPane scrPane;
	JButton btnprint;
	Container cc;
}
