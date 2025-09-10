import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Cities;
import Databaseop.Databaseop.Subcategory;

public class Partners extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	protected String[] rolenames = dd.rolnames();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	JFrame myframe = this;
	String sfrom = "";
	JFrame pframe;

	Partners() {
		init();
		hh.iconhere(this);
		txname.requestFocus(true);
		dd.countrycombofill(cmbcountries);
		dd.cmbcitiescombofill(cmbcities);
		cmbroles.setModel(new DefaultComboBoxModel(rolenames));
		partable_update("");
	}

	Partners(JFrame parent, String wfrom) {
		sfrom = wfrom;
		pframe = parent;
		init();
		hh.iconhere(this);
		txname.requestFocus(true);
		dd.countrycombofill(cmbcountries);
		dd.cmbcitiescombofill(cmbcities);
		cmbroles.setModel(new DefaultComboBoxModel(rolenames));
		partable_update("");
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Partners");
		getContentPane().setBackground(hh.citrom);
		setSize(1300, 710);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		lbheader = new JLabel("PARTNERS");
		lbheader.setBounds(30, 10, 290, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(610, 15, 70, 25);
		add(lbsearch);

		txsearch = gr.gTextField(25);
		txsearch.setBounds(688, 15, 200, 30);
	//	txsearch.setFocusable(false);
		add(txsearch);

		btnclear = new JButton();
	//	btnclear.setFocusable(false);
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(888, 15, 25, 30);
		btnclear.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnclear.setText("x");
		add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				partable_update("");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(false);
		cmbsearch.setBounds(920, 15, 120, 30);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.setBackground(Color.ORANGE);
		cmbsearch.addItem("Name");
		cmbsearch.addItem("Phone");
		cmbsearch.addItem("Email");
		add(cmbsearch);

		btnsearch = gr.sbcs("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(1048, 15, 100, 33);
		btnsearch.setFocusable(false);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		lPanel = new JPanel(null);
		lPanel.setBounds(10, 60, 460, 590);
		lPanel.setBorder(hh.ztroundborder(hh.szold));
		lPanel.setBackground(hh.citrom);
		add(lPanel);

		lbname = hh.clabel("Name");
		lbname.setBounds(0, 20, 100, 25);
		lPanel.add(lbname);

		txname = hh.cTextField(150);
		txname.setBounds(110, 20, 280, 25);
		lPanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(0, 60, 100, 25);
		lPanel.add(lbphone);

		txphone = hh.cTextField(150);
		txphone.setBounds(110, 60, 280, 25);
		lPanel.add(txphone);

		lbemail = hh.clabel("Email");
		lbemail.setBounds(0, 100, 100, 25);
		lPanel.add(lbemail);

		txemail = hh.cTextField(150);
		txemail.setBounds(110, 100, 280, 25);
		lPanel.add(txemail);

		lbrole = hh.clabel("Role");
		lbrole.setBounds(0, 140, 100, 25);
		lPanel.add(lbrole);

		cmbroles = hh.cbcombo();
		cmbroles.setFont(hh.textf);
		cmbroles.setBounds(110, 140, 250, 27);
		lPanel.add(cmbroles);

		lbcountry = hh.clabel("Country");
		lbcountry.setBounds(0, 180, 100, 25);
		lPanel.add(lbcountry);

		cmbcountries = hh.cbcombo();
		cmbcountries.setFont(hh.textf);
		cmbcountries.setBounds(110, 180, 250, 27);
		lPanel.add(cmbcountries);

		lbcity = hh.clabel("City");
		lbcity.setBounds(0, 220, 100, 25);
		lPanel.add(lbcity);

		cmbcities = hh.cbcombo();
		cmbcities.setName("cities");
		cmbcities.setFont(hh.textf);
		cmbcities.setBounds(110, 220, 250, 27);
		lPanel.add(cmbcities);

		btnnewcity = gr.sbcs("New");
		btnnewcity.setFocusable(false);
		btnnewcity.setForeground(Color.black);
		btnnewcity.setBackground(Color.ORANGE);
		btnnewcity.setBounds(370, 220, 80, 28);
		btnnewcity.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnnewcity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lPanel.add(btnnewcity);
		btnnewcity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Lcities ci = new Lcities(myframe, "partners");
				ci.setLayout(null);
				ci.setLocationRelativeTo(null);
				ci.setVisible(true);
			}
		});

		lbaddress = hh.clabel("Address");
		lbaddress.setBounds(0, 260, 100, 25);
		lPanel.add(lbaddress);

		txaddress = hh.cTextField(300);
		txaddress.setBounds(110, 260, 280, 25);
		lPanel.add(txaddress);
		txaddress.addKeyListener(hh.MUpper());

		lbpostcode = hh.clabel("Postcode");
		lbpostcode.setBounds(0, 300, 110, 25);
		lPanel.add(lbpostcode);

		txpostcode = hh.cTextField(150);
		txpostcode.setBounds(110, 300, 280, 25);
		lPanel.add(txpostcode);

		lbgender = hh.clabel("Gender");
		lbgender.setBounds(0, 340, 100, 25);
		lPanel.add(lbgender);

		cmbgender = hh.cbcombo();
		cmbgender.setModel(new DefaultComboBoxModel(new String[] { "Female", "Male" }));
		cmbgender.setBounds(110, 340, 150, 27);
		cmbgender.setFont(hh.textf);
		lPanel.add(cmbgender);

		lbdob = hh.clabel("Dob");
		lbdob.setBounds(0, 380, 100, 25);
		lPanel.add(lbdob);

		txdob = hh.cTextField(150);
		txdob.setBounds(110, 380, 280, 25);
		lPanel.add(txdob);
		txdob.addKeyListener(hh.Onlydate());

		lbregdate = hh.clabel("Reg. date");
		lbregdate.setBounds(0, 420, 100, 25);
		lPanel.add(lbregdate);

		txregdate = hh.cTextField(150);
		txregdate.setBounds(110, 420, 280, 25);
		lPanel.add(txregdate);
		txregdate.addKeyListener(hh.Onlydate());

		lbremark = hh.clabel("Remark");
		lbremark.setBounds(0, 460, 100, 25);
		lPanel.add(lbremark);

		txremark = hh.cTextField(150);
		txremark.setBounds(110, 460, 280, 25);
		lPanel.add(txremark);
		txremark.addKeyListener(hh.MUpper());

		lbbankacc = hh.clabel("Bank acc.");
		lbbankacc.setBounds(0, 500, 100, 25);
		lPanel.add(lbbankacc);

		txbankacc = hh.cTextField(150);
		txbankacc.setBounds(110, 500, 280, 25);
		lPanel.add(txbankacc);
		txbankacc.addKeyListener(hh.MUpper());

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(75, 545, 110, 30);
		btnsave.setBackground(hh.lpiros);
		lPanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(190, 545, 110, 30);
		btncancel.setBackground(Color.yellow);
		lPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(305, 545, 110, 30);
		btndelete.setBackground(Color.green);
		lPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		rPanel = new JPanel(null);
		rPanel.setBounds(480, 60, 790, 590);
		rPanel.setBorder(hh.ztroundborder(hh.szold));
		rPanel.setBackground(hh.citrom);
		add(rPanel);

		partable = hh.ztable();
		partable.setFocusable(false);
		partable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) partable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		partable.setTableHeader(new JTableHeader(partable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		partable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = partable.getSelectedRow();
				if (row >= 0) {
					rowid = partable.getValueAt(row, 0).toString();
					myrow = row;
					txname.setText(partable.getValueAt(row, 1).toString());
					txphone.setText(partable.getValueAt(row, 2).toString());
					txemail.setText(partable.getValueAt(row, 3).toString());
					cmbroles.setSelectedItem(partable.getValueAt(row, 4).toString());
					cmbcountries.setSelectedItem(partable.getValueAt(row, 5).toString());
					txaddress.setText(partable.getValueAt(row, 8).toString());
					txpostcode.setText(partable.getValueAt(row, 9).toString());
					txremark.setText(partable.getValueAt(row, 10).toString());
					cmbgender.setSelectedItem(partable.getValueAt(row, 11).toString());
					txdob.setText(partable.getValueAt(row, 12).toString());
					txregdate.setText(partable.getValueAt(row, 13).toString());
					txbankacc.setText(partable.getValueAt(row, 14).toString());
				
					int number = 0;
					String cnum = partable.getValueAt(row, 6).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
					}
					hh.setSelectedValue(cmbcities, number);
					cmbcities.updateUI();
				}
			}
		});

		hh.madeheader(partable);
		pPane = new JScrollPane(partable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		partable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "parid", "Name", "Phone", "Email", "Role", "Country", "cid", "City", "Address",
						"Postcode", "Remark", "Gender", " Dob", "Reg. date", "Bankaccount" }));

		pPane.setViewportView(partable);
		pPane.setBounds(10, 30, 768, 460);
		pPane.setBorder(hh.myRaisedBorder);
		rPanel.add(pPane);
		
		btnprint = gr.sbcs("Print partner list");
		btnprint.setForeground(Color.black);
		btnprint.setBackground(Color.ORANGE);
		btnprint.setBounds(340, 525, 160, 30);
		rPanel.add(btnprint);
		btnprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) partable.getModel();
				try {
					if (partable.getRowCount() > 0) {
						Partnerprint pprint = new Partnerprint(partable);
						pprint.setVisible(true);
					}
				} catch (Exception exx) {
					System.out.println("sql error!!!");
				}
			}
		});
		
		btnsendto = hh.cbutton("");
		btnsendto.setBounds(20, 525, 160, 30);
		btnsendto.setBackground(hh.narancs);
		if (sfrom == "purchase_sale" || sfrom == "bankinput" || sfrom == "banktrans_history") {
			btnsendto.setText("Send to  back");
			rPanel.add(btnsendto);
		}

		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});
		setVisible(true);
	}

	private void data_save() {
		String sql = "";
		String jel = "";
		DefaultTableModel d1 = (DefaultTableModel) partable.getModel();
		String name = txname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String address = txaddress.getText();
		String postcode = txpostcode.getText();
		String remark = txremark.getText();
		String dob = txdob.getText();
		String regdate = txregdate.getText();
		String country = cmbcountries.getSelectedItem().toString();
		String gender = cmbgender.getSelectedItem().toString();
		String role = cmbroles.getSelectedItem().toString();
		int cid = (int) ((Cities) cmbcities.getSelectedItem()).getCid();
		String city = (String) ((Cities) cmbcities.getSelectedItem()).toString();
		String bankacc = txbankacc.getText();

		if (hh.zempty(name) || hh.zempty(phone) || hh.zempty(email)) {
			JOptionPane.showMessageDialog(null, "Please fill the name, phone, email fields !");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  partner set name= '" + name + "',  gender = '" + gender + "' , dob= '" + dob + "', phone = '"
					+ phone + "',  email='" + email + "'" + ", regdate='" + regdate + "', country='" + country
					+ "', cid= " + cid + ", address= '" + address + "', postcode='" + postcode + "', role='" + role
					+ "', remark='" + remark + "', bankaccount= '" + bankacc + "' where parid = " + rowid;
		} else {
			sql = "insert into partner (name, gender, dob, phone, email, regdate, country, "
					+ " cid, address, postcode, role, remark, bankaccount) values ( '" + name + "','" + gender + "','"
					+ dob + "','" + phone + "','" + email + "','" + regdate + "','" + country + "'," + cid + ",'"
					+ address + "','" + postcode + "','" + role + "','" + remark + "','" + bankacc + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(parid) AS max_id FROM partner");
					d1.addRow(new Object[] { myid, name, phone, email, role, country, cid, city, address, postcode,
							remark, gender, dob, regdate, bankacc });
					hh.gotolastrow(partable);
				} else {
					d1.setValueAt(name, myrow, 1);
					d1.setValueAt(phone, myrow, 2);
					d1.setValueAt(email, myrow, 3);
					d1.setValueAt(role, myrow, 4);
					d1.setValueAt(country, myrow, 5);
					d1.setValueAt(cid, myrow, 6);
					d1.setValueAt(city, myrow, 7);
					d1.setValueAt(address, myrow, 8);
					d1.setValueAt(postcode, myrow, 9);
					d1.setValueAt(remark, myrow, 10);
					d1.setValueAt(gender, myrow, 11);
					d1.setValueAt(dob, myrow, 12);
					d1.setValueAt(regdate, myrow, 13);
					d1.setValueAt(bankacc, myrow, 14);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
	}

	private void clearFields() {
		txname.setText("");
		txphone.setText("");
		txemail.setText("");
		txaddress.setText("");
		txpostcode.setText("");
		txremark.setText("");
		txdob.setText("");
		txregdate.setText("");
		cmbcountries.setSelectedIndex(0);
		cmbgender.setSelectedIndex(0);
		cmbroles.setSelectedIndex(0);
		cmbcities.setSelectedIndex(0);
		txbankacc.setText("");
		rowid = "";
		myrow = 0;
		myid = 0;
	}

	private void partable_update(String what) {
		DefaultTableModel model = (DefaultTableModel) partable.getModel();
		model.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  p.parid, p.name, p.phone, p.email, p.role,  p.country, p.cid, "
					+ "c.name as city, p.address, p.postcode, p.remark, p.gender, p.dob, p.regdate, p.bankaccount  from partner p  "
					+ " left join cities c  on p.cid = c.cid ";
		} else {
			Sql = "select  p.parid, p.name, p.phone, p.email, p.role,  p.country, p.cid, "
					+ "c.name as city, p.address, p.postcode, p.remark, p.gender, p.dob, p.regdate, p.bankaccount  from partner p  "
					+ " left join cities c  on p.cid = c.cid  where " + what;
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String parid = rs.getString("parid");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String role = rs.getString("role");
				String country = rs.getString("country");
				String cid = rs.getString("cid");
				String city = rs.getString("city");
				String address = rs.getString("address");
				String postcode = rs.getString("postcode");
				String regdate = rs.getString("regdate");
				String dob = rs.getString("dob");
				String gender = rs.getString("gender");
				String remark = rs.getString("remark");
				String bankaccount = rs.getString("bankaccount");
				model.addRow(new Object[] { parid, name, phone, email, role, country, cid, city, address, postcode,
						remark, gender, dob, regdate, bankaccount });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "parid", "Name", "Phone", "Email", "Role", "Country", "cid", "City", "Address", "Postcode",
				"Remark", "Gender", " Dob", "Reg. date", "Bankaccount" };

		((DefaultTableModel) partable.getModel()).setColumnIdentifiers(fej);
		TableColumnModel cm = partable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(6).setWidth(0);
		cm.getColumn(6).setMinWidth(0);
		cm.getColumn(6).setMaxWidth(0);

		int[][] wcols = { { 1, 250 }, { 2, 120 }, { 3, 170 }, { 4, 100 }, { 5, 150 }, { 7, 150 }, { 8, 250 },
				{ 9, 100 }, { 10, 200 }, { 11, 90 }, { 12, 90 }, { 13, 90 }, { 14, 200 } };
		hh.cellswidth(partable, wcols);

		partable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				partable.scrollRectToVisible(partable.getCellRect(partable.getRowCount() - 1, 0, true));
			}
		});
		if (partable.getRowCount() > 0) {
			int row = partable.getRowCount() - 1;
			partable.setRowSelectionInterval(row, row);
		}
	}

	private void sqlgyart() {
		String sql = "";
		String ss = txsearch.getText().trim().toLowerCase();
		if (hh.zempty(ss)) {
			return;
		}
		String scmbtxt = String.valueOf(cmbsearch.getSelectedItem());
		if (scmbtxt == "Name") {
			sql = " lower(p.name) LIKE '%" + ss + "%' order by p.name " + " COLLATE NOCASE ASC";
		} else if (scmbtxt == "Phone") {
			sql = " phone LIKE '%" + ss + "%' order by phone COLLATE NOCASE ASC";
		} else if (scmbtxt == "Email") {
			sql = " email LIKE '%" + ss + "%' order by email COLLATE NOCASE ASC";
		}
		partable_update(sql);
	}

	public void passtocmbcity(int number) {
		dd.cmbcitiescombofill(cmbcities);
		hh.setSelectedValue(cmbcities, number);
		cmbcities.updateUI();
	}

	private void data_cancel() {
		clearFields();
		txname.requestFocus(true);
	}

	private void data_delete() {
		String sql = "delete from partner where parid=";
		dd.data_delete(partable, sql);
		clearFields();
	}

	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) partable.getModel();
		int row = partable.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			cnum = d1.getValueAt(row, 0).toString();
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			if (sfrom == "bankinput") {
				((Bankinput) pframe).passtopartner(number);
			} else if (sfrom == "purchase_sale") {
				((Purchase_sale) pframe).passtopartner(number);
			} else if (sfrom == "banktrans_history") {
				((Banktrans_history) pframe).passtopartner(number);
			}
			pframe.setVisible(true);
			dispose();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Partners();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbsearch, lbname, lbphone, lbemail, lbrole, lbcountry, lbcity, lbaddress, lbpostcode, lbgender,
			lbdob, lbregdate, lbremark, lbbankacc;
	JComboBox cmbsearch, cmbroles, cmbcountries, cmbcities, cmbgender;
	JButton btnsearch, btnclear, btnsave, btncancel, btndelete, btnnewcity, btnsendto, btnprint;
	JTextField txsearch, txname, txphone, txemail, txaddress, txpostcode, txdob, txregdate, txremark, txbankacc;
	JPanel lPanel, rPanel;
	JTable partable;
	JScrollPane pPane;
}
