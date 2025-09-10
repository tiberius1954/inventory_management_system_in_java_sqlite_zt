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
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Category;

public class Subcategories extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	String sfrom="";
	JFrame pframe;
	

	Subcategories() {
		init();
		dd.categoriescombofill(cmbcategories);
	//	txproductcode.requestFocus(true);	
	ctable_update("");
	hh.iconhere(this);
	}
	Subcategories(JFrame parent, String wfrom) {
		sfrom =wfrom;
		pframe = parent;
		init();
		dd.categoriescombofill(cmbcategories);
		//	txproductcode.requestFocus(true);	
		ctable_update("");
		 setAlwaysOnTop(true);
	     hh.iconhere(this);
	}

	private void init(){
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Subcategories");
		getContentPane().setBackground(hh.citrom);
		setSize(497, 570);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("SUBCATEGORIES");
		lbheader.setBounds(30, 20, 290, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);
		
		mPanel = new JPanel(null);
		mPanel.setBounds(10, 10, 460, 510);
		mPanel.setBorder(hh.ztroundborder(hh.szold));

		mPanel.setBackground(hh.citrom);
		add(mPanel);		
		
		lbname = hh.clabel("Name");
		lbname.setBounds(0, 60, 100, 30);
		mPanel.add(lbname);

		txsname = hh.cTextField(40);
		txsname.setBounds(110, 60, 260, 30);
		mPanel.add(txsname);
		txsname.addKeyListener(hh.MUpper());
		
		lbcategories = hh.clabel("Categories");
		lbcategories.setBounds(0, 110, 100, 25);
		mPanel.add(lbcategories);

		cmbcategories = hh.cbcombo();
		cmbcategories.setBounds(110, 110, 260, 25);
		cmbcategories.setName("category");
		mPanel.add(cmbcategories);
		
		btnsave = gr.sbcs("Save");
		btnsave.setBounds(100, 180, 90, 35);
		btnsave.setBackground(hh.lpiros);
		mPanel.add(btnsave);
    	btnsave.addActionListener(e -> savebutt());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(195, 180, 90, 35);		
		btncancel.setBackground(Color.green);
		mPanel.add(btncancel);
	 	btncancel.addActionListener(e -> data_cancel());
		
		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(290, 180, 90, 35);		
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
					txsname.setText(ctable.getValueAt(row, 1).toString());
					int number = 0;
					String cnum = ctable.getValueAt(row, 2).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
					}
					hh.setSelectedValue(cmbcategories, number);
					cmbcategories.updateUI();
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

		ctable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, 
				new String[] { "scid", "Subcategory name","cid","Category name" }));
		hh.setJTableColumnsWidth(ctable, 360, 0, 50,0,50);
		jScrollPane1.setViewportView(ctable);
		jScrollPane1.setBounds(20, 240, 423, 210);	
		mPanel.add(jScrollPane1);
		
		btnsendto = hh.cbutton("Send to products");
		btnsendto.setBounds(150, 465, 180, 30);
		btnsendto.setBackground(hh.narancs);

		if (sfrom == "products") {
			mPanel.add(btnsendto);
		}
		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});

       setVisible(true);
	}
	private void savebutt() {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		String sql = "";
		int gid = 0;
		String name = txsname.getText();
		String category =  (String) ((Category) cmbcategories.getSelectedItem()).getCname();		
		int cid = (int) ((Category) cmbcategories.getSelectedItem()).getCid();		
		if (hh.zempty(name) == true) {
			return;
		}
		try {
			if (rowid != "") {
				sql = "update  subcategory set sname= '" + name + "', cid= "+ cid + "  where scid = " + rowid;
			} else {
				sql = "insert into subcategory (sname, cid) values " + " ('" + name + "', " + cid + ")";
			}
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(scid) AS max_id from subcategory");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, name, cid, category });
					hh.gotolastrow(ctable);
					if (ctable.getRowCount() > 0) {
						int row = ctable.getRowCount() - 1;
						ctable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh(name, cid, category);
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
	private void data_cancel() {
		clearFields();		
	}
	private int data_delete() {
		String sql = " delete from subcategory where scid=";
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
	
	private void table_rowrefresh(String name, int cid, String category) {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		d1.setValueAt(name, myrow, 1);
		d1.setValueAt(cid, myrow, 2);
		d1.setValueAt(category, myrow, 3);
	}
	
	private void clearFields() {
		txsname.setText("");
		cmbcategories.setSelectedIndex(0);
		myrow = 0;
		rowid = "";
		txsname.requestFocus(true);
	}
	private void ctable_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) ctable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  s.scid, s.sname, c.cid, c.cname from subcategory s "
					+ "left join category c on s.cid = c.cid  order by upper(sname)";
		} else {
			Sql = "select  s.scid, s.sname, c.cid, c.cname  from subcategory s "
					+ " left join category c on s.cid = c.cid  where " + what +" order by upper(sname)";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String scid = rs.getString("scid");
				String sname = rs.getString("sname");
				String cid = rs.getString("cid");
				String cname = rs.getString("cname");				
				m1.addRow(new Object[] { scid, sname,cid,cname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	//	String[] fej = { "scid", "Name","cid", "Category" };
	//	((DefaultTableModel) ctable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ctable, 360, 0, 50, 0,50);
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
	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		int row = ctable.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			cnum = d1.getValueAt(row, 0).toString();
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			((Products) pframe).passtocmbc(number);
			pframe.setVisible(true);	
			dispose();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Subcategories();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JLabel lbheader, lbname, lbcategories;
	JTextField txsname;
	JTable ctable;
	JButton btnsave, btncancel, btndelete, btnsendto;
	JPanel mPanel;
	JScrollPane jScrollPane1;
	JComboBox cmbcategories;

}
