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

public class Banks extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;

	Banks() {
		init();
		clearFields();
		btable_update("");
		hh.iconhere(this);
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Banks");
		getContentPane().setBackground(hh.citrom);
		setSize(1000, 530);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("B A N K S");
		lbheader.setBounds(30, 10, 130, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		lPanel = new JPanel(null);
		lPanel.setBounds(10, 60, 422, 400);
		lPanel.setBorder(hh.ztroundborder(hh.szold));
		lPanel.setBackground(hh.citrom);
		add(lPanel);

		lbmark = hh.clabel("Mark");
		lbmark.setBounds(0, 20, 80, 25);
		lPanel.add(lbmark);

		txmark = hh.dTextField(2);
		txmark.setBounds(90, 20, 20, 25);
		lPanel.add(txmark);

		lbcode = hh.clabel("Code");
		lbcode.setBounds(0, 70, 80, 25);
		lPanel.add(lbcode);

		txcode = hh.cTextField(150);
		txcode.setBounds(90, 70, 30, 25);
		lPanel.add(txcode);
		txcode.addKeyListener(hh.Onlynum());

		lbcodehelp = hh.clabel("01 - 99");
		lbcodehelp.setBounds(125, 70, 45, 25);
		lbcodehelp.setFont(new Font("Arial", 0, 14));
		lPanel.add(lbcodehelp);

		lbname = hh.clabel("Name");
		lbname.setBounds(0, 120, 80, 25);
		lPanel.add(lbname);

		txname = hh.cTextField(150);
		txname.setBounds(90, 120, 310, 25);
		lPanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		lbaddress = hh.clabel("Address");
		lbaddress.setBounds(0, 170, 80, 25);
		lPanel.add(lbaddress);

		txaddress = hh.cTextField(150);
		txaddress.setBounds(90, 170, 310, 25);
		lPanel.add(txaddress);
		txaddress.addKeyListener(hh.MUpper());

		lbiban = hh.clabel("IBAN");
		lbiban.setBounds(0, 220, 80, 25);
		lPanel.add(lbiban);

		txiban = hh.cTextField(150);
		txiban.setBounds(90, 220, 310, 25);
		lPanel.add(txiban);

		lbbankaccnumber = hh.clabel("Bank account number");
		lbbankaccnumber.setBounds(125, 250, 200, 25);
		lPanel.add(lbbankaccnumber);

		txbankaccnumber = hh.cTextField(150);
		txbankaccnumber.setBounds(90, 280, 310, 25);
		lPanel.add(txbankaccnumber);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(90, 330, 100, 30);
		btnsave.setBackground(hh.lpiros);
		lPanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(195, 330, 100, 30);
		btncancel.setBackground(Color.green);
		lPanel.add(btncancel);
    	btncancel.addActionListener(e -> data_cancel());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(300, 330, 100, 30);
		btndelete.setBackground(Color.yellow);
		lPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		rPanel = new JPanel(null);
		rPanel.setBounds(440, 60, 530, 400);
		rPanel.setBorder(hh.ztroundborder(hh.szold));
		rPanel.setBackground(hh.citrom);
		add(rPanel);

		btable = hh.ztable();
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

		btable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = btable.getSelectedRow();
				if (row >= 0) {
					rowid = btable.getValueAt(row, 0).toString();
					myrow = row;
					txmark.setText(btable.getValueAt(row, 1).toString());
					txcode.setText(btable.getValueAt(row, 2).toString());
					txname.setText(btable.getValueAt(row, 3).toString());
					txaddress.setText(btable.getValueAt(row, 4).toString());
					txiban.setText(btable.getValueAt(row, 5).toString());
					txbankaccnumber.setText(btable.getValueAt(row, 6).toString());
				}
			}
		});

		hh.madeheader(btable);
		bPane = new JScrollPane(btable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		btable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "bid", "Mark", "Code", "Name", "Address", "Iban", "Bankaccount number" }));

		// ((DefaultTableModel) btable.getModel()).setColumnIdentifiers(fej);
		TableColumnModel cm = btable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		int[][] wcols = { { 1, 50 }, { 2, 50 }, { 3, 250 }, { 4, 250 }, { 5, 250 }, { 6, 250 } };
		hh.cellswidth(btable, wcols);

		bPane.setViewportView(btable);
		bPane.setBounds(10, 30, 505, 350);
		bPane.setBorder(hh.myRaisedBorder);
		rPanel.add(bPane);

		setVisible(true);
	}

	private void data_save() {
		String sql = "";
		String jel = "";
		DefaultTableModel d1 = (DefaultTableModel) btable.getModel();
		String mark = txmark.getText();
		String code = hh.padLeftZeros(txcode.getText(), 2);
		String bname = txname.getText();
		String address = txaddress.getText();
		String iban = txiban.getText();
		String bankaccountnumber = txbankaccnumber.getText();

		if (hh.zempty(code) || hh.zempty(bname) || hh.zempty(mark) || hh.zempty(bankaccountnumber)) {
			JOptionPane.showMessageDialog(null, "Please fill the mark, code, name, bankaccountnumber fields !");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  banks set mark= '" + mark + "', bname= '" + bname + "', code= '" + code + "' ,address = '"
					+ address + "' , iban= '" + iban + "',  bankaccountnumber = '" + bankaccountnumber
					+ "' where bid = " + rowid;
		} else {
			sql = "insert into banks (mark, bname, code, address, iban, bankaccountnumber) values " + "( '" + mark
					+ "','" + bname + "','" + code + "','" + address + "','" + iban + "','" + bankaccountnumber + "')";
		}
		try {
		
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(bid) AS max_id FROM banks");
					d1.addRow(new Object[] { myid, mark, code, bname, address, iban, bankaccountnumber });
					hh.gotolastrow(btable);			
			} else {
				d1.setValueAt(mark, myrow, 1);
				d1.setValueAt(code, myrow, 2);
				d1.setValueAt(bname, myrow, 3);
				d1.setValueAt(address, myrow, 4);
				d1.setValueAt(iban, myrow, 5);
				d1.setValueAt(bankaccountnumber, myrow, 6);
			}
		} 
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
	}
	
	private void data_cancel() {
		clearFields();
		txcode.requestFocus();
	}
	
	private void data_delete() {
		String sql ="delete from banks where bid=";
		dd.data_delete(btable,  sql);
		clearFields();
	}

	private void clearFields() {
		txmark.setText("B");
		txcode.setText("");
		txname.setText("");
		txaddress.setText("");
		txiban.setText("");
		txbankaccnumber.setText("");
		rowid = "";
		myrow = 0;
		myid = 0;		
	}

	private void btable_update(String what) {
		DefaultTableModel model = (DefaultTableModel) btable.getModel();
		model.setRowCount(0);
		String Sql = "";
		Sql = "select  bid, mark, code, bname, address, iban, bankaccountnumber" + "   from banks ";

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String bid = rs.getString("bid");
				String code = rs.getString("code");
				String bname = rs.getString("bname");
				String mark = rs.getString("mark");
				String address = rs.getString("address");
				String iban = rs.getString("iban");
				String bankaccountnumber = rs.getString("bankaccountnumber");
				model.addRow(new Object[] { bid, mark, code, bname, address, iban, bankaccountnumber });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "bid", "Mark", "Code", "Name", "Address", "Iban", "Bankaccount number" };
		((DefaultTableModel) btable.getModel()).setColumnIdentifiers(fej);
		TableColumnModel cm = btable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		int[][] wcols = { { 1, 50 }, { 2, 50 }, { 3, 250 }, { 4, 250 }, { 5, 250 }, { 6, 250 } };
		hh.cellswidth(btable, wcols);

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

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Banks();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbmark, lbcode, lbname, lbaddress, lbiban, lbbankaccnumber, lbcodehelp;
	JTextField txmark, txcode, txname, txaddress, txiban, txbankaccnumber;
	JPanel lPanel, rPanel;
	JButton btnsave, btncancel, btndelete;
	JTable btable;
	JScrollPane jPane, bPane;
}
