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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import Classes.Grlib;
import Classes.Hhelper;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Inventory extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	static StringUtils hss = new StringUtils();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	JFrame myframe = this;
	String sfrom = "";
	JFrame pframe;

	Inventory() {
		init();
		itable_update("");
		hh.iconhere(this);
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Inventory");
		getContentPane().setBackground(hh.citrom);
		setSize(1280, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		// setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("INVENTORY");
		lbheader.setBounds(30, 10, 290, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(380, 15, 70, 25);
		add(lbsearch);

		txsearch = gr.gTextField(25);
		txsearch.setBounds(458, 15, 200, 30);
		add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(658, 15, 25, 30);
		btnclear.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnclear.setText("x");
		add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				itable_update("");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(690, 15, 150, 30);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.setBackground(Color.ORANGE);
		cmbsearch.addItem("Productname");
		cmbsearch.addItem("Productcode");		
		add(cmbsearch);

		btnsearch = gr.sbcs("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(848, 15, 100, 33);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		lPanel = new JPanel(null);
		lPanel.setBounds(15, 60, 1235, 580);
		lPanel.setBorder(hh.ztroundborder(hh.szold));
		lPanel.setBackground(hh.citrom);
		add(lPanel);

		itable = hh.ztable();
		itable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) itable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		itable.setTableHeader(new JTableHeader(itable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(itable);
		iPane = new JScrollPane(itable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		itable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "tsid", "pid", "Productcode", "Productname", "uid", "Unit", "In qty", "Out qty", "Stock",
						"Last updated", "Cost price", "Min", "Max" }));

		TableColumnModel cm = itable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(1).setWidth(0);
		cm.getColumn(1).setMinWidth(0);
		cm.getColumn(4).setMaxWidth(0);
		cm.getColumn(4).setWidth(0);
		cm.getColumn(4).setMinWidth(0);
		cm.getColumn(4).setMaxWidth(0);

		int[][] wcols = { { 2, 120 }, { 3, 250 }, { 5, 90 }, { 6, 110 }, { 7, 110 }, { 8, 110 }, { 9, 120 }, { 10, 95 },
				{ 11, 95 }, { 12, 95 } };

		hh.cellswidth(itable, wcols);
		int[] cols = { 5, 6, 7, 8, 9, 10, 11, 12 };
		hh.cellsright(itable, cols);

		iPane.setViewportView(itable);
		iPane.setBounds(15, 30, 1205, 450);
		iPane.setBorder(hh.myRaisedBorder);
		lPanel.add(iPane);
		
		btnprint = gr.sbcs("Print");	
		btnprint.setBounds(600, 515, 130, 35);
		btnprint.setBackground(hh.lpiros);
		lPanel.add(btnprint);
		btnprint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					DefaultTableModel m1 = (DefaultTableModel) itable.getModel();
					if (m1.getRowCount() <= 0) {
						return;
					}
					PrinterJob job = PrinterJob.getPrinterJob();
					job.setJobName("inventorylist");
					HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
					attr.add(new MediaPrintableArea(10, 10, 190, 275, MediaPrintableArea.MM));
					job.print(attr);

					MessageFormat[] header = new MessageFormat[2];
					header[0] = new MessageFormat(hss.center("INVENTORY LIST", 170));
					header[1] = new MessageFormat("");
				//	header[2] = new MessageFormat("");
				//	header[3] = new MessageFormat("");

					MessageFormat[] footer = new MessageFormat[1];
					footer[0] = new MessageFormat(hss.center("Page {0,number,integer}", 170));
					job.setPrintable(hh.new MyTablePrintable(itable, JTable.PrintMode.FIT_WIDTH, header, footer));
					job.printDialog();
					job.print();
					// dispose();

				} catch (java.awt.print.PrinterAbortException e) {
				} catch (PrinterException ex) {
					System.err.println("Error printing: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});

		setVisible(true);
	}

	private void itable_update(String what) {
		DefaultTableModel model = (DefaultTableModel) itable.getModel();
		model.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select i.tsid, i.pid, p.productcode, p.pname, i.uid, u.uname, i.inqty,  i.outqty, i.stock, "
					+ "i.last_updated, i.costprice, i.pmin, i.pmax  from inventory i "
					+ "left join product p  on i.pid = p.pid left join unities u on i.uid =u.uid order by upper(pname)";
		} else {
			Sql = "select  i.tsid, i.pid, p.productcode, p.pname, i.uid, u.uname, i.inqty, i.outqty,  i.stock,"
					+ " i.last_updated, i.costprice, i.pmin, i.pmax from inventory i  "
					+ " left join product p  on i.pid = p.pid  left join unities u on i.uid =u.uid where " + what
					+ " order by upper(pname)";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String tsid = rs.getString("tsid");
				String pid = rs.getString("pid");
				String productcode = rs.getString("productcode");
				String name = rs.getString("pname");
				String uid = rs.getString("uid");
				String uname = rs.getString("uname");
				String inqty =hh.bsf( rs.getString("inqty"));
				String outqty = hh.bsf(rs.getString("outqty"));
				String stock =hh.bsf(rs.getString("stock"));
				String last_updated = rs.getString("last_updated");
				String costprice = hh.bsf(rs.getString("costprice"));
				String pmin =hh.bsf(rs.getString("pmin"));
				String pmax = hh.bsf(rs.getString("pmax"));
				model.addRow(new Object[] { tsid, pid, productcode, name, uid, uname, inqty, outqty, stock,
						last_updated, costprice, pmin, pmax });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "tsid", "pid", "Productcode", "Productname", "uid", "Unit", "In qty", "Out qty", "Stock",
				"Last updated", "Cost price", "Min", "Max" };

		((DefaultTableModel) itable.getModel()).setColumnIdentifiers(fej);
		TableColumnModel cm = itable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);
		cm.getColumn(1).setWidth(0);
		cm.getColumn(1).setMinWidth(0);
		cm.getColumn(1).setMaxWidth(0);
		cm.getColumn(4).setWidth(0);
		cm.getColumn(4).setMinWidth(0);
		cm.getColumn(4).setMaxWidth(0);
		int[][] wcols = { { 2, 120 }, { 3, 250 }, { 5, 90 }, { 6, 110 }, { 7, 110 }, { 8, 110 }, { 9, 120 }, { 10, 95 },
				{ 11, 95 }, { 12, 100 } };

		hh.cellswidth(itable, wcols);
		int[] cols = { 6, 7, 8, 9, 10, 11, 12 };
		hh.cellsright(itable, cols);

		itable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				itable.scrollRectToVisible(itable.getCellRect(itable.getRowCount() - 1, 0, true));
			}
		});
		if (itable.getRowCount() > 0) {
			int row = itable.getRowCount() - 1;
			itable.setRowSelectionInterval(row, row);
		}
	}
	private void sqlgyart() {
		String sql = "";
		String ss = txsearch.getText().trim().toLowerCase();
		if (hh.zempty(ss)) {
			return;
		}
		String scmbtxt = String.valueOf(cmbsearch.getSelectedItem());
		if (scmbtxt == "Productname") {
			sql = " lower(pname) LIKE '%" + ss + "%' order by pname " + " COLLATE NOCASE ASC";
		} else if (scmbtxt == "Productcode") {
			sql = " productcode LIKE '%" + ss + "%' order by productcode COLLATE NOCASE ASC";		
		}
		itable_update(sql);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Inventory();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbsearch;
	JTextField txsearch;
	JComboBox cmbsearch;
	JButton btnsearch, btnclear, btnprint;
	JPanel lPanel;
	JTable itable;
	JScrollPane iPane;
}
