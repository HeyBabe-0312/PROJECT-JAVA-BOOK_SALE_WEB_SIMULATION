package Body;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Admin extends Frame implements ActionListener, MouseListener{
		Button sale,qltk,qls,dthu,exit;
		Panel pn,pn1,pn2,pn3;
		static JTable tab;
		static Vector<String> columnNames ;
		static Vector<Vector<Object>> data;
		public void GUI() {
			sale = new Button("Giảm giá");
			qltk = new Button("Tài khoản");
			qls = new Button("Sách");
			dthu = new Button("Doanh Thu");
			exit = new Button("Thoát");

			sqlrun();
			tab = new JTable();
			update_table();
			JScrollPane scrollPane = new JScrollPane(tab);
			tab.setFillsViewportHeight(true);

			tab.setSelectionMode(0);
			tab.addMouseListener(this);
			qltk.addActionListener(this);
			qls.addActionListener(this);
			dthu.addActionListener(this);
			exit.addActionListener(this);
			sale.addActionListener(this);
			
			pn = new Panel(new BorderLayout());
			pn1 = new Panel(new FlowLayout());
			pn2 = new Panel(new BorderLayout());
			pn3 = new Panel(new FlowLayout());

			pn1.add(sale);
			pn1.add(qltk);
			pn1.add(qls);
			pn1.add(dthu);
			pn2.add(scrollPane,BorderLayout.CENTER);
			pn3.add(exit);
			pn.add(pn1,BorderLayout.NORTH);
			pn.add(pn2,BorderLayout.CENTER);
			pn.add(pn3,BorderLayout.SOUTH);
			add(pn);
			setSize(800,600);
			show();
		}
		public static void update_table(){
			tab.setModel(new DefaultTableModel(data, columnNames));
		}
		public static void sqlrun(){
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
				Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM LICHSUGD";
				ResultSet rs = stmt.executeQuery(sql);
				data = new Vector<Vector<Object>>();
				while (rs.next()) {
					Vector<Object> dr = new Vector<Object>();
					dr = new Vector<Object>();
					dr.add(rs.getString("login"));
					dr.add(rs.getDate("time")+" "+rs.getTime("time"));
					dr.add(rs.getInt("bid"));
					data.add(dr);
				}
				columnNames = new Vector<String>();
				columnNames.add("Tài Khoản");
				columnNames.add("Thời Gian");
				columnNames.add("ID Sách");
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==sale) new QL_Sale("Mở Đóng Giảm Giá");
			if(e.getSource()==qltk) {
				new QLTK("Quản lí tài khoản");
			}
			if(e.getSource()==qls) {
				new QLS("Quản lí sách");
			}
			if(e.getSource()==dthu) {
				new DoanhThu("Quản Lí Doanh Thu");
			}
			if(e.getSource()==exit) System.exit(0);
		}
		public Admin(String at) {
			super(at);
			GUI();
		}
		public static void main(String[] args) { new Admin("Hệ Thống Quản Lí"); }

		@Override
		public void mouseClicked(MouseEvent e) {
			String name = "", time = "", sach = "", gia = "";
			int index = tab.getSelectedRow();
			TableModel model = tab.getModel();
			if(index != -1) {
				String value = model.getValueAt(index, 0).toString();
				String id = model.getValueAt(index, 2).toString();
				time = model.getValueAt(index, 1).toString();
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
					Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
					Statement stmt = con.createStatement();
					Statement stmt1 = con.createStatement();
					String sql = "SELECT * FROM LICHSUGD WHERE login like '"+value+"'";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						name = rs.getString("login");
						String d = rs.getDate("time")+" "+rs.getTime("time");
						if(d.equals(time)) gia = rs.getString("price");
					}
					String sql1 = "SELECT * FROM BOOK WHERE bid like "+id;
					ResultSet rs1 = stmt1.executeQuery(sql1);
					while (rs1.next()) {
						sach = rs1.getString("title");
					}
					stmt.close();
					stmt1.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
				JOptionPane.showMessageDialog(this, "Tài Khoản: " + name + "\nThời gian: " + time + "\nSách: " + sach + "\nGiá: " + gia);
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) { }
}