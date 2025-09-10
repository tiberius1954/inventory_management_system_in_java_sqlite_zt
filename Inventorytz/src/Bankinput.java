import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Partner;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Btype;
import Databaseop.Databaseop.Cities;
import Databaseop.Databaseop.Subcategory;

public class Bankinput extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Partner prr;
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	String mybid = "";
	int mytid = 0;
	String mycode = "";
	String mymark = "B";
	JFrame myframe = this;
	Object[] billdata = null;

	Bankinput() {
		init();
		mybid = "1";
		mycode = "01";
		btable_update("bid=" + 1);
		hh.iconhere(this);
	}

	Bankinput(String sbid, String scode, String sname) {
		init();
		txbankname.setText(scode + " " + sname);
		mybid = sbid;
		mycode = scode;
		btable_update("bid=" + sbid);
		hh.iconhere(this);
	}

	void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Banking");
		getContentPane().setBackground(hh.citrom);
		setSize(1300, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("B A N K I N G");
		lbheader.setBounds(30, 10, 180, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		lbbankname = hh.clabel("Name of the bank");
		lbbankname.setBounds(570, 15, 150, 25);
		add(lbbankname);

		txbankname = hh.dTextField(50);
		txbankname.setBounds(730, 15, 250, 25);
		add(txbankname);

		lPanel = new JPanel(null);
		lPanel.setBounds(10, 50, 430, 600);
		lPanel.setBorder(hh.ztroundborder(hh.szold));
		lPanel.setBackground(hh.citrom);
		add(lPanel);

		rPanel = new JPanel(null);
		rPanel.setBounds(448, 50, 827, 600);
		rPanel.setBorder(hh.ztroundborder(hh.szold));
		rPanel.setBackground(hh.citrom);
		add(rPanel);

		lbdate = hh.clabel("Trans. date");
		lbdate.setBounds(0, 20, 110, 25);
		lPanel.add(lbdate);

		txbdate = hh.cTextField(2);
		txbdate.setBounds(120, 20, 150, 25);
		lPanel.add(txbdate);
		txbdate.addKeyListener(hh.Onlydate());

		lbdatehelp = hh.clabel("yyyy-mm-dd");
		lbdatehelp.setFont(new Font("Arial", 0, 14));
		lbdatehelp.setBounds(250, 20, 100, 25);
		lPanel.add(lbdatehelp);

		lbregister = hh.clabel("Register no");
		lbregister.setBounds(0, 60, 110, 25);
		lPanel.add(lbregister);

		txregister = hh.cTextField(2);
		txregister.setBounds(120, 60, 35, 25);
		txregister.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txregister);
		txregister.addKeyListener(hh.Onlynum());

		lbreghelp = hh.clabel("1 - 999");
		lbreghelp.setFont(new Font("Arial", 0, 14));
		lbreghelp.setBounds(170, 60, 50, 25);
		lPanel.add(lbreghelp);

		lbtype = hh.clabel("Trans. type");
		lbtype.setBounds(0, 100, 110, 25);
		lPanel.add(lbtype);

		cmbbtypes = hh.cbcombo();
		cmbbtypes.setModel(new DefaultComboBoxModel<>(new String[] { "Deposit", "Withdrawal" }));
		cmbbtypes.setName("btypes");
		cmbbtypes.setBounds(120, 100, 240, 25);
		lPanel.add(cmbbtypes);

		lbclaim = hh.clabel("Claim");
		lbclaim.setBounds(0, 140, 110, 25);
		lPanel.add(lbclaim);

		cmbclaims = hh.cbcombo();
		cmbclaims.setModel(new DefaultComboBoxModel<>(
				new String[] { "      ", "Customer bill payment", "Supplier Bill payment", "Check", "Others" }));
		cmbclaims.setName("claims");
		cmbclaims.setBounds(120, 140, 240, 25);
		lPanel.add(cmbclaims);

		lbamount = hh.clabel("Amount");
		lbamount.setBounds(0, 180, 110, 25);
		lPanel.add(lbamount);

		txamount = hh.cTextField(150);
		txamount.setBounds(120, 180, 280, 25);
		txamount.setHorizontalAlignment(JTextField.RIGHT);
		lPanel.add(txamount);
		txamount.addKeyListener(hh.Onlynum());

		lbpartner = hh.clabel("Partner");
		lbpartner.setBounds(0, 220, 110, 25);
		lPanel.add(lbpartner);

		txpartner = hh.dTextField(150);
		txpartner.setBounds(120, 220, 280, 25);
		lPanel.add(txpartner);

		txparid = hh.cTextField(10);
		txindb = hh.cTextField(10);

		btnpartners = gr.sbcs("Partners");
		btnpartners.setForeground(Color.black);
		btnpartners.setBackground(Color.ORANGE);
		btnpartners.setBounds(200, 250, 120, 32);
		btnpartners.setFocusable(false);
		lPanel.add(btnpartners);
		btnpartners.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Partners pa = new Partners(myframe, "bankinput");
				// pa.setLayout(null);
				// pa.setLocationRelativeTo(null);
				pa.setVisible(true);
			}
		});

		lbreceipt = hh.clabel("Receipt");
		lbreceipt.setBounds(0, 290, 110, 25);
		lPanel.add(lbreceipt);

		txreceipt = hh.cTextField(150);
		txreceipt.setBounds(120, 290, 280, 25);
		lPanel.add(txreceipt);

		btnreceipts = gr.sbcs("Receipts");
		btnreceipts.setForeground(Color.black);
		btnreceipts.setBackground(Color.ORANGE);
		btnreceipts.setBounds(200, 320, 120, 32);
		btnreceipts.setFocusable(false);
		lPanel.add(btnreceipts);
		btnreceipts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String claim = (String) cmbclaims.getSelectedItem();
				int trtype = 0;
				if (claim == "Customer bill payment") {
					trtype = 2;
				} else if (claim == "Supplier Bill payment") {
					trtype = 1;
				}
				if (trtype>0) {
				Partnerbills pa = new Partnerbills(myframe, "bankinput", trtype);		
				pa.setVisible(true);
				}
			}
		});

		lbreferences = hh.clabel("References");
		lbreferences.setBounds(0, 360, 110, 25);
		lPanel.add(lbreferences);

		txreferenc = hh.cTextField(150);
		txreferenc.setBounds(120, 360, 280, 25);
		lPanel.add(txreferenc);

		lbdescription = hh.clabel("Description");
		lbdescription.setBounds(0, 400, 110, 25);
		lPanel.add(lbdescription);

		txdescription = hh.cTextField(150);
		txdescription.setBounds(120, 400, 280, 25);
		lPanel.add(txdescription);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(75, 490, 110, 30);
		btnsave.setBackground(hh.lpiros);
		lPanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(190, 490, 110, 30);
		btncancel.setBackground(Color.yellow);
		lPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(305, 490, 110, 30);
		btndelete.setBackground(Color.green);
		lPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		btable = hh.ztable();
		btable.setFocusable(false);
		btable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) btable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		btable.setTableHeader(new JTableHeader(btable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(btable);
		bPane = new JScrollPane(btable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		btable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "btid", "bid", "indb", "Transaction_no", "Date", "Trans type", "Amount", "Claim", "Reg.",
						"parid", "Partner", "Receipt", "References", "Description", "tid" }));
		TableColumnModel cm = btable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(1).setWidth(0);
		cm.getColumn(1).setMinWidth(0);
		cm.getColumn(1).setMaxWidth(0);
		cm.getColumn(2).setWidth(0);
		cm.getColumn(2).setMinWidth(0);
		cm.getColumn(2).setMaxWidth(0);
		cm.getColumn(9).setWidth(0);
		cm.getColumn(9).setMinWidth(0);
		cm.getColumn(9).setMaxWidth(0);
		cm.getColumn(14).setWidth(0);
		cm.getColumn(14).setMinWidth(0);
		cm.getColumn(14).setMaxWidth(0);

		int[][] wcols = { { 3, 140 }, { 4, 90 }, { 5, 100 }, { 6, 90 }, { 7, 160 }, { 8, 50 }, { 10, 250 }, { 11, 200 },
				{ 12, 200 }, { 13, 250 } };

		hh.cellswidth(btable, wcols);
		int[] cols = { 6, 8 };
		hh.cellsright(btable, cols);

		bPane.setViewportView(btable);
		bPane.setBounds(10, 70, 807, 430);
		bPane.setBorder(hh.myRaisedBorder);
		rPanel.add(bPane);
		
		btnhistory = gr.sbcs("Transaction history");
		btnhistory.setForeground(Color.black);
		btnhistory.setBackground(Color.ORANGE);
		btnhistory.setBounds(340, 530, 185, 33);
		rPanel.add(btnhistory);
		btnhistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try {				
					String bankname = txbankname.getText();
					
					if (btable.getRowCount()>0) {					
						Banktrans_history history = new Banktrans_history();	
						history.setVisible(true);
					}
				} catch (Exception exx) {
					System.out.println("sql error!!!");
				}			
			}
		});	

		setVisible(true);
	}

	private void data_save() {
		String sql = "";
		String jel = "";
		DefaultTableModel d1 = (DefaultTableModel) btable.getModel();
		String btnumber = "";
		String bdate = txbdate.getText();
		String register = txregister.getText();
		String parid = txparid.getText();
		String receipt = txreceipt.getText();
		String referenc = txreferenc.getText();
		String amount = txamount.getText();
		String description = txdescription.getText();
		String claim = (String) cmbclaims.getSelectedItem();
		String btype = (String) cmbbtypes.getSelectedItem();
		String indb = "";
		String name = txpartner.getText();

		if (bankvalidation(bdate, register, amount, claim, name) == false) {
			return;
		}

		Object[] btnumberobj = (Object[]) gettnumber(bdate);
		btnumber = btnumberobj[0].toString();
		indb = btnumberobj[1].toString();
		sql = "insert into banktransactions (bid, mark, code, indb, register, btnumber, bdate, "
				+ " btype, claim, parid, receipt, referenc, amount, description, tid) values ( '" + mybid + "','"
				+ mymark + "','" + mycode + "','" + indb + "','" + register + "','" + btnumber + "','" + bdate + "','"
				+ btype + "','" + claim + "','" + parid + "','" + receipt + "','" + referenc + "','" + amount + "','"
				+ description + "'," + mytid + ")";

		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				myid = dd.table_maxid("SELECT MAX(btid) AS max_id FROM  banktransactions");
				d1.addRow(new Object[] { myid, mybid, indb, btnumber, bdate, btype, amount, claim, register, parid,
						name, receipt, referenc, description, mytid });
				hh.gotolastrow(btable);
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		if (mytid > 0) {
			bill_settle(mytid, claim, btype, amount);
		}
		clearFields(2);
	}

	private Boolean bankvalidation(String bdate, String register, String amount, String claim, String name) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (claim.equals("Customer bill payment") || claim.equals("Supplier Bill payment")) {
			if (mytid == 0) {
				err.add("You Do not choose receipt !");
				ret = false;
			}
		}

		if (!hh.validatedate(bdate, "N")) {
			err.add("Invalid date !");
			ret = false;
		}
		if (hh.zempty(register)) {
			err.add("Register is empty !");
			ret = false;
		}
		if (hh.zempty(amount)) {
			err.add("Amount is empty !");
			ret = false;
		}
		if (hh.zempty(name)) {
			err.add("Partner is empty !");
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}

	private void bill_settle(int bill_tid, String claim, String btype, String amount) {
		Double damount = hh.stodd(amount);
		// billdata
		if (claim.equals("Customer bill payment")) {
			if (btype.equals("Deposit")) {
				bill_update(bill_tid, damount);
			} else { // Withdrawal
				damount = -1.0 * damount;
				bill_update(bill_tid, damount);
			}
		} else { // "Supplier Bill payment"
			if (btype.equals("Deposit")) {
				damount = -1.0 * damount;
				bill_update(bill_tid, damount);
			} else { // Withdrawal
				bill_update(bill_tid, damount);
			}
		}
	}

	private void bill_update(int bill_tid, double damount)  {
		Double totalpaid=0.0;
		String sql = "select totalpaid from stransaction  where tid ="+bill_tid;
		rs = dh.GetData(sql);
		try {
			if (rs.next()) {
				totalpaid = rs.getDouble("totalpaid");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dh.CloseConnection();
       totalpaid = totalpaid + damount;
	     sql = " update  stransaction set totalpaid =" + totalpaid;
	     int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
			}		
	}

	private void data_cancel() {
		clearFields(1);
		txbdate.requestFocus(true);
	}

	private void data_delete() {
		
		String sql = "delete from banktransactions where btid=";
		data_delete(btable, sql);
		clearFields(1);
	}
	
	public int data_delete(JTable dtable, String sql) {		
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		String stid= d1.getValueAt(sIndex, 14).toString();
		String amount = d1.getValueAt(sIndex, 6).toString();
		String claim = d1.getValueAt(sIndex, 7).toString();
		String btype = d1.getValueAt(sIndex, 5).toString();
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = dtable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
			}
			if (dtable.getRowCount() > 0) {
				dtable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
			}
			int tid = hh.stoi(stid);
			if (tid> 0) {				
				amount ="-" + amount;				
				bill_settle(tid, claim, btype, amount);				
			}
		}
		return flag;
	}

	private void clearFields(int x) {
		if (x != 2) {
			txbdate.setText("");
			txregister.setText("");
		}
		txpartner.setText("");
		txreceipt.setText("");
		txreferenc.setText("");
		txamount.setText("");
		txdescription.setText("");
		txparid.setText("");
		txindb.setText("");
		cmbclaims.setSelectedIndex(0);
		billdata = null;
		rowid = "";
		myrow = 0;
		myid = 0;
		mytid = 0;
	}

	private void btable_update(String what) {
		DefaultTableModel model = (DefaultTableModel) btable.getModel();
		model.setRowCount(0);
		String Sql = "";
		Sql = "select  b.btid, b.bid,  b.indb,  b.register, b.btnumber, "
				+ "b.bdate, b.btype, b.amount, b.claim, b.parid, p.name, b.receipt, b.referenc,"
				+ " b.description, tid   from "
				+ "banktransactions b  left join partner p  on p.parid = b.parid  where " + what
				+ " order by  btnumber";

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String btid = rs.getString("btid");
				String bid = rs.getString("bid");
				String indb = rs.getString("indb");
				String register = rs.getString("register");
				String btnumber = rs.getString("btnumber");
				String bdate = rs.getString("bdate");
				String btype = rs.getString("btype");
				String amount = rs.getString("amount");
				String claim = rs.getString("claim");
				String parid = rs.getString("parid");
				String pname = rs.getString("name");
				if (hh.zempty(pname)) {
					pname = "";
				}
				String receipt = rs.getString("receipt");
				String referenc = rs.getString("referenc");
				String description = rs.getString("description");
				String tid = rs.getString("tid");
				model.addRow(new Object[] { btid, bid, indb, btnumber, bdate, btype, amount, claim, register, parid,
						pname, receipt, referenc, description, tid });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "btid", "bid", "indb", "Transaction_no", "Date", "Trans. type", "Amount", "Claim", "Reg.",
				"parid", "Partner", "Receipt", "References", "Description", "tid" };

		TableColumnModel cm = btable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(1).setWidth(0);
		cm.getColumn(1).setMinWidth(0);
		cm.getColumn(1).setMaxWidth(0);
		cm.getColumn(2).setWidth(0);
		cm.getColumn(2).setMinWidth(0);
		cm.getColumn(2).setMaxWidth(0);
		cm.getColumn(9).setWidth(0);
		cm.getColumn(9).setMinWidth(0);
		cm.getColumn(9).setMaxWidth(0);
		cm.getColumn(14).setWidth(0);
		cm.getColumn(14).setMinWidth(0);
		cm.getColumn(14).setMaxWidth(0);

		int[][] wcols = { { 3, 140 }, { 4, 90 }, { 5, 100 }, { 6, 90 }, { 7, 160 }, { 8, 50 }, { 10, 250 }, { 11, 200 },
				{ 12, 200 }, { 13, 250 } };

		hh.cellswidth(btable, wcols);
		int[] cols = { 6, 8 };
		hh.cellsright(btable, cols);

		btable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				btable.scrollRectToVisible(btable.getCellRect(btable.getRowCount() - 1, 0, true));
			}
		});
		if (btable.getRowCount() > 0) {
			int row = btable.getRowCount() - 1;
			btable.setRowSelectionInterval(row, row);
		}
	}

	private Object[] gettnumber(String date) {
		Object retobj[] = null;
		int number = hh.maketnumber(mybid, mymark, mycode, date);
		String snumber = hh.itos(number);
		String end = hh.padLeftZeros(snumber, 3);
		String sdate = date.replace("-", "");
		String ret = mymark + mycode + sdate.substring(2, 4) + '-' + sdate.substring(4, 8) + '/' + end;
		retobj = new Object[] { ret, snumber };
		return retobj;
	}

	public void passtopartner(int parid) {
		prr = dd.getpartnerrecord(parid);
		txpartner.setText(prr.getName());
		txparid.setText(hh.itos(parid));
	}

	public void passtobill(Object[] bill) {
		// tid, tran_type, tnumber,incomeno, parid, name, grandtotal, totalpaid, balance
		billdata = bill;
		mytid = hh.stoi(bill[0].toString());
		int tran_type = hh.stoi(bill[1].toString());
		txpartner.setText(bill[5].toString());
		txparid.setText(bill[4].toString());
		txreceipt.setText(bill[2].toString());
		txreferenc.setText(bill[3].toString());
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Bankinput();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbdate, lbregister, lbreghelp, lbtype, lbclaim, lbpartner, lbreceipt, lbreferences, lbamount,
			lbdescription, lbdatehelp, lbbankname;
	JTextField txbdate, txregister, txpartner, txreceipt, txreferenc, txamount, txdescription, txparid, txbankname,
			txindb;
	JComboBox cmbbtypes, cmbclaims;
	JPanel lPanel, rPanel;
	JButton btnpartners, btnreceipts, btnsave, btncancel, btndelete, btnhistory;
	JTable btable;
	JScrollPane bPane;
}
