import java.awt.*;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Category;

public class Categories extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	Categories() {
		init();
	txname.requestFocus(true);	
	ctable_update("");
	hh.iconhere(this);
	}

	private void init(){
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Categories");
		getContentPane().setBackground(hh.citrom);
		setSize(497, 475);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("CATEGORIES");
		lbheader.setBounds(30, 20, 290, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);
		
		mPanel = new JPanel(null);
		mPanel.setBounds(10, 10, 460, 410);
		mPanel.setBorder(hh.ztroundborder(hh.szold));

		mPanel.setBackground(hh.citrom);
		add(mPanel);
		
		lbname = hh.clabel("Name");
		lbname.setBounds(10, 70, 100, 30);
		mPanel.add(lbname);

		txname = hh.cTextField(40);
		txname.setBounds(120, 70, 280, 30);
		mPanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(120, 125, 90, 35);
		btnsave.setBackground(hh.lpiros);
		mPanel.add(btnsave);
	    btnsave.addActionListener(e -> savebutt());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(215, 125, 90, 35);		
		btncancel.setBackground(Color.green);
		mPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());
		
		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(310, 125, 90, 35);		
		btndelete.setBackground(Color.yellow);
		mPanel.add(btndelete);
    	btndelete.addActionListener(e -> data_delete());

		ctable = hh.ztable();
		ctable.setTableHeader(new JTableHeader(ctable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		ctable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = ctable.getSelectedRow();
				if (row >= 0) {
					rowid = ctable.getValueAt(row, 0).toString();
					txname.setText(ctable.getValueAt(row, 1).toString());
					myrow = row;
				}
			}
		});

		hh.madeheader(ctable);
		ctable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ctable.scrollRectToVisible(ctable.getCellRect(ctable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(ctable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ctable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "cid", "Category name" }));
		hh.setJTableColumnsWidth(ctable, 360, 0, 100);
		jScrollPane1.setViewportView(ctable);
		jScrollPane1.setBounds(50, 180, 360, 200);	
		mPanel.add(jScrollPane1);
		setVisible(true);
	}
	
	private int data_delete() {
		String sql = " delete from category where cid=";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		int sIndex = ctable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String gid = d1.getValueAt(sIndex, 0).toString();
		if (gid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = ctable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + "'" + gid + "'";
			try {
			flag = dh.Insupdel(vsql);
			d1.removeRow(sIndex);
			if (ctable.getRowCount() > 0) {
				ctable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
			}
			} catch (Exception e1) {
				if (ctable.getRowCount() > 0)
					ctable.setRowSelectionInterval(0, 0);
			}
			clearFields();
		}
		return flag;
	}
	private void savebutt() {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		String sql = "";
		int gid = 0;
		String name = txname.getText();
		if (hh.zempty(name) == true) {
			return;
		}
		try {
			if (rowid != "") {
				sql = "update  category set cname= '" + name + "' where cid = " + rowid;
			} else {
				sql = "insert into category (cname) values" + " ('" + name + "')";
			}
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(cid) AS max_id from category");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, name });
					hh.gotolastrow(ctable);
					if (ctable.getRowCount() > 0) {
						int row = ctable.getRowCount() - 1;
						ctable.setRowSelectionInterval(row, row);
					}

				} else {
					table_rowrefresh(name);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
	}
	private void table_rowrefresh(String name) {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		d1.setValueAt(name, myrow, 1);
	}
	private void data_cancel() {
		clearFields();
		txname.requestFocus(true);
	}


	private void clearFields() {
		txname.setText("");
		myrow = 0;
		rowid = "";
		txname.requestFocus(true);
	}
	private void ctable_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) ctable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  cid, cname from category order by upper( cname)";
		} else {
			Sql = "select  cid, cname  from category where " + what +" order by upper(cname)";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String cid = rs.getString("cid");
				String cname = rs.getString("cname");
				m1.addRow(new Object[] { cid, cname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
//		String[] fej = { "cid", "Name" };
//		((DefaultTableModel) ctable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ctable, 360, 0, 100);
//			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
		// renderer.setHorizontalAlignment(SwingConstants.LEFT);

		ctable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ctable.scrollRectToVisible(ctable.getCellRect(ctable.getRowCount() - 1, 0, true));
			}
		});
		if (ctable.getRowCount() > 0) {
			int row = ctable.getRowCount() - 1;
			ctable.setRowSelectionInterval(row, row);
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Categories();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
JLabel lbheader, lbname;
JTextField txname;
JTable ctable;
JButton btnsave, btncancel, btndelete;
JPanel mPanel;
JScrollPane jScrollPane1;
}
