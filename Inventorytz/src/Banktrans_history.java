import javax.swing.*;

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
import java.util.ArrayList;
import java.util.Vector;

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
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Btype;
import Databaseop.Databaseop.Cities;
import Databaseop.Databaseop.Subcategory;

public class Banktrans_history extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	int rownumber = 0;
	int pagenumber = 0;
	String stid = "";
	static StringUtils hss = new StringUtils();
	Vector<String> header = new Vector();
	JFrame myframe = this;
	String mycode = "";
	String mymark = "B";
	String mybid = "";
	
	Banktrans_history(){
		mybid = "1";
		mycode = "01";
		init();
		dd.cmbpartnercombofill(cmbpartner);
		hh.iconhere(this);	
	}
	private void init(){
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Bank transaction history");
		getContentPane().setBackground(hh.citrom);
		setSize(1100, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("BANK TRANSACTION HISTORY");
		lbheader.setBounds(30, 10, 400, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		lbbankname = hh.clabel("Name of the bank");
		lbbankname.setBounds(570, 15, 150, 25);
		add(lbbankname);

		txbankname = hh.dTextField(50);
		txbankname.setBounds(730, 15, 250, 25);
		add(txbankname);		

		tPanel = new JPanel(null);
		tPanel.setBounds(10, 50, 1020, 530);
		tPanel.setBorder(hh.ztroundborder(hh.szold));
		tPanel.setBackground(hh.citrom);
		add(tPanel);
		
		lbpartner = hh.clabel("Partner:");
		lbpartner.setBounds(30, 30, 70, 25);
		tPanel.add(lbpartner);
		
		cmbpartner = hh.cbcombo();
		cmbpartner.setFocusable(true);
		cmbpartner.setBounds(106, 30, 250, 30);
		cmbpartner.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbpartner.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbpartner.setBackground(Color.ORANGE);
		cmbpartner.setName("partner");
		tPanel.add(cmbpartner);
		
		btnpartners = gr.sbcs("Partners");
		btnpartners.setFont(new java.awt.Font("Tahoma", 1, 16));	
		btnpartners.setBounds(365, 30, 110, 33);	
		btnpartners.setBackground(Color.ORANGE);	
		tPanel.add(btnpartners);
		btnpartners.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Partners pa = new Partners(myframe, "banktrans_history");			
				pa.setVisible(true);
			}
		});
	
		lbregister= hh.clabel("Register");
		lbregister.setBounds(480, 30, 70, 30);
		tPanel.add(lbregister);
		
		txregister = gr.gTextField(15);
		txregister.setBounds(555, 30, 50, 30);	
		txregister.setHorizontalAlignment(JTextField.RIGHT);
		tPanel.add(txregister);
	    txregister.addKeyListener(hh.Onlynum());
		
		lbdate = hh.clabel("Date:");
		lbdate.setBounds(610, 30, 50, 30);
		tPanel.add(lbdate);
		
		txdate1 = gr.gTextField(15);
		txdate1.setBounds(665, 30, 100, 30);		
		tPanel.add(txdate1);
	    txdate1.addKeyListener(hh.Onlydate());
		
		lbline = hh.clabel("-");
		lbline.setBounds(762, 30, 15, 30);
		tPanel.add(lbline);
		
		txdate2 = gr.gTextField(25);
		txdate2.setBounds(783, 30, 100, 30);	
		tPanel.add(txdate2);	
		txdate2.addKeyListener(hh.Onlydate());
		
		btnfilter = gr.sbcs("Filter");
		btnfilter.setForeground(Color.black);
		btnfilter.setBackground(Color.ORANGE);
		btnfilter.setBounds(890, 30, 100, 30);
		tPanel.add(btnfilter);
		btnfilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		
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
				new String[] { "btid","Transaction_no", "Date", "Trans type", "Amount", "Claim", "Reg.",
						"Partner", "Receipt", "References" }));
		TableColumnModel cm = btable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);		

		int[][] wcols = { { 1, 120 }, { 2, 100 }, { 3, 100 }, { 4, 90 }, { 5, 150 }, { 6, 50 }, { 7, 200 }, { 8, 200 },
				{ 9, 200 } };

		hh.cellswidth(btable, wcols);
		int[] cols = { 4, 6 };
		hh.cellsright(btable, cols);

		bPane.setViewportView(btable);
		bPane.setBounds(10, 90, 1000, 350);
		bPane.setBorder(hh.myRaisedBorder);
		tPanel.add(bPane);
		
		btnprint = gr.sbcs("Print history");
		btnprint.setForeground(Color.black);
		btnprint.setBackground(Color.ORANGE);
		btnprint.setBounds(440, 470, 130, 33);
		tPanel.add(btnprint);
		btnprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) btable.getModel();
				try {
					int parid = (int) ((Partner) cmbpartner.getSelectedItem()).getParid(); 	
					String name = ((Partner) cmbpartner.getSelectedItem()).getName();
					String date1 = txdate1.getText();				
					String date2 =  txdate2.getText();				
					String register=txregister.getText();			
					String bankname = txbankname.getText();
					
					if (btable.getRowCount()>0) {					
						Historyprint hprint = new Historyprint(bankname, name, date1,date2, register, btable);	
						hprint.setVisible(true);
					}
				} catch (Exception exx) {
					System.out.println("sql error!!!");
				}			
			}
		});	
		
		setVisible(true);		
	}
	
	private void sqlgyart() {
		String sql = " where bid='" + mybid +"'";
		String date1 = txdate1.getText();
		if (hh.zempty(date1)) {
			date1 = "2000-01-01";
		}
		String date2 =  txdate2.getText();
		if (hh.zempty(date2)) {
			date2 = "2030-12-31";
		}
		String register=txregister.getText();	
		int parid = (int) ((Partner) cmbpartner.getSelectedItem()).getParid(); 	
		
   if (!hh.validatedate(date1, "N") || !hh.validatedate(date2, "N")) {	
	   JOptionPane.showMessageDialog(null, "Incorrect date !");
			return;
		} 

		if(parid>0) {
			sql += " and b.parid ="+ parid;
		}
		if ( hh.twodate(date1, date2) == true) {
			sql += " and bdate >= date('" + date1 + "') and bdate <=date('" + date2 + "')"; 
		}
       if (!hh.zempty(register)) {
    	   sql += " and register='" +register+"'";
       }
       
		btable_update( sql);
		return;
}

private void btable_update(String what) {
	DefaultTableModel model = (DefaultTableModel) btable.getModel();
	model.setRowCount(0);	
	String Sql = "select  b.btid, b.register, b.btnumber, "
			+ "b.bdate, b.btype, b.amount, b.claim, p.name, b.receipt, b.referenc, b.parid from "
			+ "banktransactions b  left join partner p  on b.parid = p.parid " + what
			+ " order by  btnumber";

	try {
		rs = dh.GetData(Sql);
		while (rs.next()) {
			String btid = rs.getString("btid");		
			String register = rs.getString("register");
			String btnumber = rs.getString("btnumber");
			String bdate = rs.getString("bdate");
			String btype = rs.getString("btype");
			String amount = rs.getString("amount");
			String claim = rs.getString("claim");		
			String pname = rs.getString("name");
			String parid= rs.getString("parid");
			if (hh.zempty(pname)) {
				pname = "";
			}
			String receipt = rs.getString("receipt");
			String referenc = rs.getString("referenc");
		
			model.addRow(new Object[] { btid, btnumber, bdate, btype, amount, claim, register,pname, 
					receipt, referenc, parid });
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		dh.CloseConnection();
	}

	String[] fej = { "btid","Transaction_no", "Date", "Trans type", "Amount", "Claim", "Reg.",
	"Partner", "Receipt", "References"};
	TableColumnModel cm = btable.getColumnModel();
	cm.getColumn(0).setWidth(0);
	cm.getColumn(0).setMinWidth(0);
	cm.getColumn(0).setMaxWidth(0);		

	int[][] wcols = { { 1, 120 }, { 2, 100 }, { 3, 100 }, { 4, 90 }, { 5, 150 }, { 6, 50 }, { 7, 200 }, { 8, 200 },
			{ 9, 200 } };

	hh.cellswidth(btable, wcols);
	int[] cols = { 4, 6 };
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

	public void passtopartner(int parid) {
		dd.cmbpartnercombofill(cmbpartner);
		hh.setSelectedValue(cmbpartner, parid);
		cmbpartner.updateUI();
	}
	
	public static void main(String[] args) {
		 Banktrans_history ff = new Banktrans_history();
//		ff.setBounds(0, 0, 1060, 640);
//		ff.setLocationRelativeTo(null);
		ff.setVisible(true);
	}
JPanel tPanel;
JTable btable;
JScrollPane bPane;
JLabel lbheader, lbbankname, lbpartner, lbdate,lbregister, lbline;
JTextField txbankname, txdate1, txdate2,txpartner,txsearch, txregister;
JButton btnpartners, btnfilter, btnprint;
JComboBox cmbpartner;
}
