import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
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
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Product;
import Classes.Partner;
import Classes.Unity;
import Classes.Invoice;

public class Partnerbills extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Invoice inv = new Invoice();
	protected String[] paymodes = dd.paymodes();
	Partner prr;
	Product prod;
	String rowid = "";
	int myrow = 0;
	String sfrom = "";
	JFrame pframe;
	JFrame myframe = this;
	static int Trtype = 2;
	String tranname;
	String partnername;
	String tranheader[];

	Partnerbills() {
		if (Trtype == 1) {
			tranname = "PURCHASE BILLS";
			partnername = "Supplier";
		} else {
			tranname = "SALE BILLS";
			partnername = "Customer";
		}
		tranheader = new String[] { "tid", "Trans_type", "Transaction_no", "Income_no", "Date", "Payment mode", "parid",
				partnername, "Grandtotal", "Totalpaid", "Balance" };
		init();
		trantable_update("transaction_type ='" + Trtype + "' and  ptype ='By cheque'");
		hh.iconhere(this);
	}

	Partnerbills(JFrame parent, String wfrom, int trtype) {
		sfrom = wfrom;
		pframe = parent;
		Trtype = trtype;
		if (Trtype == 1) {
			tranname = "PURCHASE BILLS";
			partnername = "Supplier";
		} else {
			tranname = "SALE BILLS";
			partnername = "Customer";
		}
		tranheader = new String[] { "tid", "Trans_type", "Transaction_no", "Income_no", "Date", "Payment mode", "parid",
				partnername, "Grandtotal", "Totalpaid", "Balance" };
		init();
		trantable_update("transaction_type ='" + Trtype + "' and  ptype ='By cheque'");
		hh.iconhere(this);
	}

	private void init() {
		setTitle(tranname);
		getContentPane().setBackground(hh.citrom);
		setSize(1100, 700);
		setLayout(null);
		setLocationRelativeTo(null);

		tPanel = new JPanel(null);
		tPanel.setBounds(10, 50, 1060, 600);
		tPanel.setBorder(hh.ztroundborder(hh.szold));
		tPanel.setBackground(hh.citrom);
		add(tPanel);
		lbheader = new JLabel(tranname);
		lbheader.setBounds(30, 20, 290, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(250, 20, 70, 25);
		tPanel.add(lbsearch);

		txsearch = gr.gTextField(25);
		txsearch.setBounds(325, 20, 200, 30);
		tPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(525, 20, 25, 30);
		btnclear.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnclear.setText("x");
		tPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				trantable_update("transaction_type ='" + Trtype + "' and  ptype ='By cheque'");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(560, 20, 180, 30);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.setBackground(Color.ORANGE);
		if (Trtype == 1) {
			cmbsearch.addItem("Supplier");
		} else {
			cmbsearch.addItem("Customer");
		}
		cmbsearch.addItem("Income_no");
		cmbsearch.addItem("Transaction_no");
		tPanel.add(cmbsearch);

		btnsearch = gr.sbcs("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(750, 20, 130, 33);
		tPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		trantable = hh.ztable();
		trantable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) trantable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		trantable.setTableHeader(new JTableHeader(trantable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		trantable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) trantable.getModel();
				try {
					int row = trantable.getSelectedRow();
					if (row > -1) {
						String myparid = model.getValueAt(row, 6).toString();
						partnersearch(myparid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(trantable);
		trantable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		tranPane = new JScrollPane(trantable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);		

		trantable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null } }, tranheader));
		TableColumnModel cm = trantable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(1).setWidth(0);
		cm.getColumn(1).setMinWidth(0);
		cm.getColumn(1).setMaxWidth(0);
		cm.getColumn(6).setWidth(0);
		cm.getColumn(6).setMinWidth(0);
		cm.getColumn(6).setMaxWidth(0);
		int[][] wcols = { { 2, 120 }, { 3, 120 }, { 4, 100 }, { 5, 100 }, { 7, 250 }, { 8, 105 }, { 9, 100 },
				{ 10, 100 } };
		hh.cellswidth(trantable, wcols);
		int[] cols = { 8, 9, 10 };
		hh.cellsright(trantable, cols);

		tranPane.setViewportView(trantable);
		tranPane.setBounds(30, 60, 1000, 350);
		tranPane.setBorder(hh.myRaisedBorder);
		tPanel.add(tranPane);

		lbpartner = hh.clabel((Trtype == 1) ? "Supplier name" : "Customer name");
		lbpartner.setBounds(60, 450, 130, 25);
		tPanel.add(lbpartner);

		txpartner = hh.dTextField(150);
		txpartner.setBounds(200, 450, 250, 25);
		tPanel.add(txpartner);

		lbcountry = hh.clabel("Country ");
		lbcountry.setBounds(450, 450, 90, 25);
		tPanel.add(lbcountry);

		txcountry = hh.dTextField(150);
		txcountry.setBounds(550, 450, 450, 25);
		tPanel.add(txcountry);

//		txcid = hh.dTextField(50);
//		txparid = hh.dTextField(40);

		lbcity = hh.clabel("City");
		lbcity.setBounds(60, 490, 130, 25);
		tPanel.add(lbcity);

		txcity = hh.dTextField(150);
		txcity.setBounds(200, 490, 250, 25);
		tPanel.add(txcity);

		lbaddress = hh.clabel("Address");
		lbaddress.setBounds(450, 490, 90, 25);
		tPanel.add(lbaddress);

		txaddress = hh.dTextField(150);
		txaddress.setBounds(550, 490, 450, 25);
		tPanel.add(txaddress);

		btnsendto = hh.cbutton("");
		btnsendto.setBounds(400, 550, 200, 30);
		btnsendto.setBackground(hh.narancs);
		if (sfrom == "bankinput") {
			btnsendto.setText("Send to back");
			tPanel.add(btnsendto);
		}

		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});

		setVisible(true);
	}

	public void trantable_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) trantable.getModel();
		m1.setRowCount(0);
		String sql = "";

		if (what == "") {
			sql = "select t.tid, t.transaction_type, t.tnumber, t.incomeno, t.tdate, t.ptype, t.parid, s.name, "
					+ " t.grandtotal, t.totalpaid, t.balance"
					+ " from stransaction t left  join partner s on t.parid = s.parid";
		} else {
			sql = "select t.tid, t.transaction_type, t.tnumber, t.incomeno, t.tdate, t.ptype, t.parid, s.name,"
					+ " t.grandtotal, t.totalpaid, t.balance"
					+ " from stransaction t  left  join partner s on t.parid = s.parid where " + what;
		}
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String tid = rs.getString("tid");
				String tran_type = rs.getString("transaction_type");
				String tnumber = rs.getString("tnumber");
				String incomeno = rs.getString("incomeno");
				String tdate = rs.getString("tdate");
				String ptype = rs.getString("ptype");
				String parid = rs.getString("parid");
				String name = rs.getString("name");
				String grandtotal = hh.bsf(rs.getString("grandtotal").toString());
				String totalpaid = hh.bsf(rs.getString("totalpaid").toString());
				String balance = hh.bsf(rs.getString("balance").toString());
				m1.addRow(new Object[] { tid, tran_type, tnumber, incomeno, tdate, ptype, parid, name, grandtotal,
						totalpaid, balance });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		((DefaultTableModel) trantable.getModel()).setColumnIdentifiers(tranheader);
		TableColumnModel cm = trantable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(1).setWidth(0);
		cm.getColumn(1).setMinWidth(0);
		cm.getColumn(1).setMaxWidth(0);
		cm.getColumn(6).setWidth(0);
		cm.getColumn(6).setMinWidth(0);
		cm.getColumn(6).setMaxWidth(0);
		int[][] wcols = { { 2, 120 }, { 3, 120 }, { 4, 100 }, { 5, 100 }, { 7, 250 }, { 8, 105 }, { 9, 100 },
				{ 10, 100 } };
		hh.cellswidth(trantable, wcols);
		int[] cols = { 8, 9, 10 };
		hh.cellsright(trantable, cols);

		trantable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				trantable.scrollRectToVisible(trantable.getCellRect(trantable.getRowCount() - 1, 0, true));
			}
		});
		if (trantable.getRowCount() > 0) {
			int row = trantable.getRowCount() - 1;
			trantable.setRowSelectionInterval(row, row);
		}
	}

	private void partnersearch(String myparid) {
		int parid = hh.stoi(myparid);
		prr = dd.getpartnerrecord(parid);
		txcountry.setText(prr.getCountry());
		txcity.setText(prr.getCity());
		txaddress.setText(prr.getAddress());
		txpartner.setText(prr.getName());
	}

	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) trantable.getModel();
		int row = trantable.getSelectedRow();
		if (row > -1) {
			String tid = d1.getValueAt(row, 0).toString();
			String tran_type = d1.getValueAt(row, 1).toString();
			String tnumber = d1.getValueAt(row, 2).toString();
			String incomeno = d1.getValueAt(row, 3).toString();
			String parid = d1.getValueAt(row, 6).toString();
			String name = d1.getValueAt(row, 7).toString();
			String grandtotal = d1.getValueAt(row, 8).toString();
			String totalpaid = d1.getValueAt(row, 9).toString();
			String balance = d1.getValueAt(row, 10).toString();
			if (sfrom == "bankinput") {
				Object[] obj = { tid, tran_type, tnumber, incomeno, parid, name, grandtotal, totalpaid, balance };
				((Bankinput) pframe).passtobill(obj);
			}
			pframe.setVisible(true);
			dispose();
		}
	}

	private void sqlgyart() {
		String sql = "";
		String ss = txsearch.getText().trim().toLowerCase();
		if (hh.zempty(ss)) {
			return;
		}
		String scmbtxt = String.valueOf(cmbsearch.getSelectedItem());
		if (scmbtxt == "Customer" || scmbtxt == "Supplier") {
			sql = " lower(s.name) LIKE '%" + ss + "%' order by s.name " + " COLLATE NOCASE ASC";
		} else if (scmbtxt == "Income_no") {
			sql = " incomeno LIKE '%" + ss + "%' order by incomeno COLLATE NOCASE ASC";
		} else if (scmbtxt == "Transaction_no") {
			sql = " tnumber LIKE '%" + ss + "%' order by tnumber COLLATE NOCASE ASC";
		}
		trantable_update("transaction_type ='" + Trtype + "' and  ptype ='By cheque'  and " + sql);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Partnerbills();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JPanel tPanel;
	JLabel lbheader, lbsearch, lbpartner, lbcountry, lbcity, lbaddress;
	JTextField txsearch, txpartner, txcountry, txcity, txaddress;
	JButton btnsearch, btnclear, btnsendto;
	JComboBox cmbsearch;
	JTable trantable;
	JScrollPane tranPane;

}
