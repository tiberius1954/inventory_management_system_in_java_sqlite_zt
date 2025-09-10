import java.awt.*;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Unity;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop.Subcategory;
import Databaseop.Databaseop;

public class Products extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	JFrame myframe = this;
	String sfrom = "";
	JFrame pframe;

	Products(JFrame parent, String wfrom) {
		sfrom = wfrom;
		pframe = parent;
		init();
		hh.iconhere(this);
		txproductcode.requestFocus(true);
		dd.subcategoriescombofill(cmbsubcategories);
		dd.unitiescombofill(cmbunities);
		ptable_update("");	
		setAlwaysOnTop( true );
	}

	Products() {
		init();
		hh.iconhere(this);
		txproductcode.requestFocus(true);
		dd.subcategoriescombofill(cmbsubcategories);
		dd.unitiescombofill(cmbunities);
		ptable_update("");		
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Products");
		getContentPane().setBackground(hh.citrom);
		setSize(1280, 735);
		setLayout(null);
		setLocationRelativeTo(null);	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("PRODUCTS");
		lbheader.setBounds(30, 10, 290, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(610, 15, 70, 25);
		add(lbsearch);

		txsearch = gr.gTextField(25);
		txsearch.setBounds(688, 15, 200, 30);
		add(txsearch);

		btnclear = new JButton();
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
				ptable_update("");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(920, 15, 150, 30);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.setBackground(Color.ORANGE);
		cmbsearch.addItem("Name");
		cmbsearch.addItem("Code");
		cmbsearch.addItem("Subcategory");
		add(cmbsearch);

		btnsearch = gr.sbcs("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(1078, 15, 100, 33);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});

		lPanel = new JPanel(null);
		lPanel.setBounds(10, 60, 502, 630);
		lPanel.setBorder(hh.ztroundborder(hh.szold));
		lPanel.setBackground(hh.citrom);
		add(lPanel);

		lbproductcode = hh.clabel("Productcode");
		lbproductcode.setBounds(0, 10, 130, 25);
		lPanel.add(lbproductcode);

		txproductcode = hh.cTextField(150);
		txproductcode.setBounds(140, 10, 200, 25);
		lPanel.add(txproductcode);

		lbname = hh.clabel("Name");
		lbname.setBounds(0, 50, 130, 25);
		lPanel.add(lbname);

		txname = hh.cTextField(150);
		txname.setBounds(140, 50, 280, 25);
		lPanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		lbsubcategory = hh.clabel("Subcategory");
		lbsubcategory.setBounds(0, 90, 130, 25);
		lPanel.add(lbsubcategory);

		cmbsubcategories = hh.cbcombo();
		cmbsubcategories.setBounds(140, 90, 200, 25);
		cmbsubcategories.setName("subcategory");
		lPanel.add(cmbsubcategories);

		btnnewsubcat = gr.sbcs("New Subcategory");
		btnnewsubcat.setForeground(Color.black);
		btnnewsubcat.setBackground(Color.ORANGE);
		btnnewsubcat.setBounds(345, 90, 150, 28);
		btnnewsubcat.setFocusable(false);
		btnnewsubcat.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnnewsubcat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lPanel.add(btnnewsubcat);

		btnnewsubcat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Subcategories ca = new Subcategories(myframe, "products");
				ca.setLayout(null);
				ca.setLocationRelativeTo(null);
				ca.setVisible(true);
			}
		});

		lbdescription = hh.clabel("Description");
		lbdescription.setBounds(155, 120, 130, 25);
		lPanel.add(lbdescription);

		txadescription = hh.cTextarea();
		txadescription.setBackground(hh.tcolor);
		jPane = new JScrollPane(txadescription, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jPane.setBounds(20, 145, 460, 130);
		jPane.setViewportView(txadescription);
		jPane.setBorder(hh.borderf);
		lPanel.add(jPane);

		lbunity = hh.clabel("Unity");
		lbunity.setBounds(0, 290, 130, 25);
		lPanel.add(lbunity);

		cmbunities = hh.cbcombo();
		cmbunities.setName("unities");
		cmbunities.setBounds(140, 290, 200, 25);
		lPanel.add(cmbunities);

		lbcprice = hh.clabel("Cost price");
		lbcprice.setBounds(0, 330, 130, 25);
		lPanel.add(lbcprice);

		txcprice = hh.cTextField(150);
		txcprice.setBounds(140, 330, 200, 25);
		txcprice.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txcprice);
		txcprice.addKeyListener(hh.Onlynum());

		lbsprice = hh.clabel("Selling price");
		lbsprice.setBounds(0, 370, 130, 25);
		lPanel.add(lbsprice);

		txsprice = hh.cTextField(150);
		txsprice.setBounds(140, 370, 200, 25);
		txsprice.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txsprice);
		txsprice.addKeyListener(hh.Onlynum());

		lbdiscount = hh.clabel("Discount%");
		lbdiscount.setBounds(0, 410, 130, 25);
		lPanel.add(lbdiscount);

		txdiscountp = hh.cTextField(150);
		txdiscountp.setBounds(140, 410, 200, 25);
		txdiscountp.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txdiscountp);
		txdiscountp.addKeyListener(hh.Onlynum());

		lbvatp = hh.clabel("VAT%");
		lbvatp.setBounds(0, 450, 130, 25);
		lPanel.add(lbvatp);

		txvatp = hh.cTextField(150);
		txvatp.setBounds(140, 450, 200, 25);
		txvatp.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txvatp);
		txvatp.addKeyListener(hh.Onlynum());

		lbpmin = hh.clabel("Min");
		lbpmin.setBounds(0, 490, 130, 25);
		lPanel.add(lbpmin);

		txpmin = hh.cTextField(150);
		txpmin.setBounds(140, 490, 80, 25);
		txpmin.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txpmin);
		txpmin.addKeyListener(hh.Onlynum());

		lbpmax = hh.clabel("Max");
		lbpmax.setBounds(217, 490, 40, 25);
		lPanel.add(lbpmax);

		txpmax = hh.cTextField(150);
		txpmax.setBounds(260, 490, 80, 25);
		txpmax.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txpmax);
		txpmax.addKeyListener(hh.Onlynum());

		lbopenstock = hh.clabel("Opening stock");
		lbopenstock.setBounds(0, 530, 130, 25);
		lPanel.add(lbopenstock);

		txopenstock = hh.cTextField(150);
		txopenstock.setBounds(140, 530, 200, 25);
		txopenstock.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txopenstock);
		txopenstock.addKeyListener(hh.Onlynum());

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(75, 580, 110, 30);
		btnsave.setBackground(hh.lpiros);
		lPanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(190, 580, 110, 30);
		btncancel.setBackground(Color.green);
		lPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(305, 580, 110, 30);
		btndelete.setBackground(Color.yellow);
		lPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		rPanel = new JPanel(null);
		rPanel.setBounds(517, 60, 735, 632);
		rPanel.setBorder(hh.ztroundborder(hh.szold));
		rPanel.setBackground(hh.citrom);
		add(rPanel);

		ptable = hh.ztable();
		ptable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) ptable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		ptable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = ptable.getSelectedRow();
				if (row >= 0) {
					rowid = ptable.getValueAt(row, 0).toString();
					myrow = row;
					txproductcode.setText(ptable.getValueAt(row, 1).toString());
					txname.setText(ptable.getValueAt(row, 2).toString());

					txadescription.setText(ptable.getValueAt(row, 7).toString());
					txcprice.setText(ptable.getValueAt(row, 8).toString());
					txsprice.setText(ptable.getValueAt(row, 9).toString());
					txdiscountp.setText(ptable.getValueAt(row, 10).toString());
					txvatp.setText(ptable.getValueAt(row, 11).toString());
					txpmin.setText(ptable.getValueAt(row, 12).toString());
					txpmax.setText(ptable.getValueAt(row, 13).toString());
					txopenstock.setText(ptable.getValueAt(row, 14).toString());

					int number = 0;
					String cnum = ptable.getValueAt(row, 5).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
					}
					hh.setSelectedValue(cmbsubcategories, number);
					cmbsubcategories.updateUI();

					number = 0;
					cnum = ptable.getValueAt(row, 3).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
					}
					hh.setSelectedValue(cmbunities, number);
					cmbunities.updateUI();
				}
			}
		});

		hh.madeheader(ptable);
		pPane = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ptable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "pid", "Productcode", "Name", "uid", "Unit", "Scid", "Subcategory", "Description",
						"Cost price", "Sell price", "Discount%", "Vat%", "Min", "Max", " Opening stock" }));

		pPane.setViewportView(ptable);
		pPane.setBounds(10, 30, 715, 500);
		pPane.setBorder(hh.myRaisedBorder);
		rPanel.add(pPane);

		btnprint = gr.sbcs("Print product list");
		btnprint.setForeground(Color.black);
		btnprint.setBackground(Color.ORANGE);
		btnprint.setBounds(300, 570, 160, 30);
		rPanel.add(btnprint);
		btnprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) ptable.getModel();
				try {

					if (ptable.getRowCount() > 0) {
						Productprint pprint = new Productprint(ptable);
						pprint.setVisible(true);
					}
				} catch (Exception exx) {
					System.out.println("sql error!!!");
				}
			}
		});

		btnsendto = hh.cbutton("");
		btnsendto.setBounds(20, 570, 160, 30);
		btnsendto.setBackground(hh.narancs);
		if (sfrom == "purchase_sale") {
			btnsendto.setText("Send to back");
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
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		String cprice = txcprice.getText();
		String sprice = txsprice.getText();
		String productcode = txproductcode.getText();
		String name = txname.getText();
		String description = txadescription.getText();
		String openstock = txopenstock.getText();
		String discountp = txdiscountp.getText();
		String pmin = txpmin.getText();
		String pmax = txpmax.getText();
		String vatp = txvatp.getText();
		String scategory = (String) ((Subcategory) cmbsubcategories.getSelectedItem()).getSname();
		int scid = (int) ((Subcategory) cmbsubcategories.getSelectedItem()).getScid();
		String uname = (String) ((Unity) cmbunities.getSelectedItem()).getUname();
		int uid = (int) ((Unity) cmbunities.getSelectedItem()).getUid();
		String currentdate = hh.currentDate();

		if (hh.zempty(productcode) || hh.zempty(name) || scid == 0 || uid == 0 || hh.zempty(openstock)) {
			JOptionPane.showMessageDialog(null,
					"Please fill the productcode, name, subcategory, Unit, opening stock fields !");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  product set productcode= '" + productcode + "', pname= '" + name + "', " + " uid= " + uid
					+ ",scid = " + scid + " , description= '" + description + "', costprice = '" + cprice
					+ "',  sellprice='" + sprice + "'" + ", discountp='" + discountp + "', openstock='" + openstock
					+ "', vatp= '" + vatp + "', pmin= '" + pmin + "',pmax='" + pmax + "' where pid = " + rowid;
		} else {
			sql = "insert into product (productcode, pname, uid,scid, description, costprice, sellprice, discountp, "
					+ " vatp, pmin, pmax, openstock) values ( '" + productcode + "','" + name + "'," + uid + "," + scid
					+ ",'" + description + "','" + cprice + "','" + sprice + "','" + discountp + "','" + vatp + "','"
					+ pmin + "','" + pmax + "','" + openstock + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(pid) AS max_id FROM product");
					d1.addRow(new Object[] { myid, productcode, name, uid, uname, scid, scategory, description, cprice,
							sprice, discountp, vatp, pmin, pmax, openstock });
					hh.gotolastrow(ptable);
					sql = "select count(*) from inventory where pid =" + myid;
					Boolean found = dd.recordfound(sql);
					if (found != true) {
						sql = "insert into inventory (pid, inqty, stock,uid, pmin, pmax, last_updated, costprice) values ("
								+ myid + ", '" + openstock + "','" + openstock + "'," + uid + ",'" + pmin + "','" + pmax
								+ "','" + currentdate + "','" + cprice + "')";
						flag = dh.Insupdel(sql);
					}
				} else {
					myid = hh.stoi(rowid);
					d1.setValueAt(productcode, myrow, 1);
					d1.setValueAt(name, myrow, 2);
					d1.setValueAt(uid, myrow, 3);
					d1.setValueAt(uname, myrow, 4);
					d1.setValueAt(scid, myrow, 5);
					d1.setValueAt(scategory, myrow, 6);
					d1.setValueAt(description, myrow, 7);
					d1.setValueAt(cprice, myrow, 8);
					d1.setValueAt(sprice, myrow, 9);
					d1.setValueAt(discountp, myrow, 10);
					d1.setValueAt(vatp, myrow, 11);
					d1.setValueAt(pmin, myrow, 12);
					d1.setValueAt(pmax, myrow, 13);
					d1.setValueAt(openstock, myrow, 14);
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

	private void ptable_update(String what) {
		DefaultTableModel model = (DefaultTableModel) ptable.getModel();
		model.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  p.pid, p.productcode, p.pname,p.uid, u.uname, p.scid, s.sname, p.description,  p.costprice, p.sellprice, "
					+ "p.discountp, p.openstock, p.pmin, p.pmax, p.vatp   from product p  "
					+ " left join subcategory s  on p.scid = s.scid  left join unities u  on p.uid= u.uid "
					+ " order by upper(pname)";
		} else {
			Sql = "select  p.pid, p.productcode, p.pname, p.uid, u.uname, p.scid, s.sname, p.description, p.costprice, p.sellprice,  "
					+ " p.discountp, p.openstock, p.pmin, p.pmax, p.vatp from product p "
					+ " left join subcategory s  on p.scid = s.scid  left join unities u  on p.uid= u.uid where "
					+ what;
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String pid = rs.getString("pid");
				String productcode = rs.getString("productcode");
				String pname = rs.getString("pname");
				String uid = rs.getString("uid");
				String uname = rs.getString("uname");
				String scid = rs.getString("scid");
				String sname = rs.getString("sname");
				String description = rs.getString("description");
				String costprice = rs.getString("costprice");
				String sellprice = rs.getString("sellprice");
				String discountp = rs.getString("discountp");
				String openstock = rs.getString("openstock");
				String vatp = rs.getString("vatp");
				String pmin = rs.getString("pmin");
				String pmax = rs.getString("pmax");
				model.addRow(new Object[] { pid, productcode, pname, uid, uname, scid, sname, description, costprice,
						sellprice, discountp, vatp, pmin, pmax, openstock });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "pid", "Productcode", "Name", "uid", "Unit", "Scid", "Subcategory", "Description",
				"Cost price", "Sell price", "Discount%", "Vat%", "Min", "Max", " Opening stock" };
		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);
		TableColumnModel cm = ptable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(3).setWidth(0);
		cm.getColumn(3).setMinWidth(0);
		cm.getColumn(3).setMaxWidth(0);
		cm.getColumn(5).setWidth(0);
		cm.getColumn(5).setMinWidth(0);
		cm.getColumn(5).setMaxWidth(0);

		int[][] wcols = { { 1, 100 }, { 2, 250 }, { 4, 80 }, { 6, 100 }, { 7, 250 }, { 8, 100 }, { 9, 100 },
				{ 10, 100 }, { 11, 100 }, { 12, 90 }, { 13, 90 }, { 14, 100 } };

		hh.cellswidth(ptable, wcols);
		int[] cols = { 8, 9, 10, 11, 12, 13, 14 };
		hh.cellsright(ptable, cols);

		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});
		if (ptable.getRowCount() > 0) {
			int row = ptable.getRowCount() - 1;
			ptable.setRowSelectionInterval(row, row);
		}
	}

	private void clearFields() {
		rowid = "";
		myrow = 0;
		txproductcode.setText("");
		txname.setText("");
		txcprice.setText("");
		txsprice.setText("");
		txopenstock.setText("");
		txdiscountp.setText("");
		txpmin.setText("");
		txpmax.setText("");
		txvatp.setText("");
		txadescription.setText("");
		cmbsubcategories.setSelectedIndex(0);
		cmbunities.setSelectedIndex(0);
		rowid = "";
		myrow = 0;
		myid = 0;
		txproductcode.requestFocus(true);
	}

	private void sqlgyart() {
		String sql = "";
		String ss = txsearch.getText().trim().toLowerCase();
		if (hh.zempty(ss)) {
			return;
		}
		String scmbtxt = String.valueOf(cmbsearch.getSelectedItem());
		if (scmbtxt == "Name") {
			sql = " lower(p.pname) LIKE '%" + ss + "%' order by Upper(pname) " + " COLLATE NOCASE ASC";
		} else if (scmbtxt == "Code") {
			sql = " p.productcode LIKE '%" + ss + "%' order by productcode COLLATE NOCASE ASC";
		} else if (scmbtxt == "Subcategory") {
			sql = " s.sname LIKE '%" + ss + "%' order by upper(sname) COLLATE NOCASE ASC";
		}
		ptable_update(sql);
	}

	private void data_cancel() {
		clearFields();
		txproductcode.requestFocus(true);
	}

	private void data_delete() {
		String sql = "delete from product where pid=";
		dd.data_delete(ptable, sql);
		clearFields();
	}

	public void passtocmbc(int number) {
		dd.subcategoriescombofill(cmbsubcategories);
		hh.setSelectedValue(cmbsubcategories, number);
		cmbsubcategories.updateUI();
	}

	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		int row = ptable.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			cnum = d1.getValueAt(row, 0).toString();
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			if (sfrom == "purchase_sale") {
				((Purchase_sale) pframe).passtocmbproducts(number);
			}

			pframe.setVisible(true);
			dispose();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Products();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbproductcode, lbname, lbsubcategory, lbcprice, lbsprice, lbopenstock, lbdiscount, lbpmin, lbpmax,
			lbdescription, lbvatp, lbsearch, lbunity;
	JTextField txproductcode, txname, txcprice, txsprice, txopenstock, txdiscountp, txvatp, txsearch, txpmin, txpmax;
	JTextArea txadescription;
	JPanel lPanel, rPanel;
	JButton btnsearch, btnclear, btnsave, btncancel, btndelete, btnnewsubcat, btnsendto, btnprint;
	JComboBox cmbsearch, cmbsubcategories, cmbunities;
	JScrollPane jPane, pPane;
	JTable ptable;
}
