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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Bankchoose extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String sbid="";
	String scode = "";
	String sname ="";
		
	Bankchoose() {
		initcomponents();
		hh.iconhere(this);
		btable_update(btable);	
	}

	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setSize(700, 400);
		setLayout(null);
		setLocationRelativeTo(null);	
		getContentPane().setBackground(hh.citrom);
		lbheader = hh.fflabel("Bank choose"); 
		lbheader.setBounds(20, 10, 350, 35);
		add(lbheader);		
		setTitle("Bank choose");
		
		btable = hh.ztable();
		btable.setTableHeader(new JTableHeader(btable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(btable);
		btable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				btable.scrollRectToVisible(btable.getCellRect(btable.getRowCount() - 1, 0, true));
			}
		});

		btable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) btable.getModel();
				try {
					int row = btable.getSelectedRow();
					if (row >= 0) {				
						sbid = btable.getValueAt(row, 0).toString();
						String seg= btable.getValueAt(row, 1).toString();
						scode = seg.substring(1,3);				
						sname = btable.getValueAt(row, 2).toString();

					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		jScrollPane1 = new JScrollPane(btable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		btable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "bid", "Mark+Code", "Name","Account number" }));
		hh.setJTableColumnsWidth(btable, 640, 0, 20, 40,40);
		jScrollPane1.setViewportView(btable);
		jScrollPane1.setBounds(20, 60, 640, 200);
		jScrollPane1.setBorder(hh.borderf);
		add(jScrollPane1);	
		
		btnnext =hh.cbutton("Next");
		btnnext.setBounds(280, 290, 100, 30);
		btnnext.setForeground(Color.black);
		btnnext.setBackground(hh.lpiros);
		add(btnnext);	
		btnnext.addActionListener(listener);
		setVisible(true);
	}
	
	public void btable_update(JTable dtable) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "select  bid, mark,code, bname, bankaccountnumber from banks ";
	
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String bid= rs.getString("bid");
				String mark = rs.getString("mark");	
				String code= rs.getString("code");	
				String bname = rs.getString("bname");			
				String  bankaccount = rs.getString("bankaccountnumber");				
		
				m1.addRow(new Object[] { bid, mark+code, bname, bankaccount });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "bid", "Mark+Code", "Name","Accountnumber"};				
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(btable, 600, 0, 20,40, 40);
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {		
			if (!hh.zempty(sbid)) {
		            new Bankinput(sbid, scode, sname);
		      dispose();
			}
		}
	};
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			     new Bankchoose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JLabel lbheader;
	JTable btable;
    JScrollPane 	jScrollPane1;   
    JButton btnnext;
}
