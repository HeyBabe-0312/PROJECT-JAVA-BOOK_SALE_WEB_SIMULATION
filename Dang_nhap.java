package Body;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dang_nhap extends Frame implements ActionListener {
		static String tk[] = new String[50],ps[] = new String[50];
		Label lb1,lb2,lb;
		TextField txta,txtb;
		Button ok,sign,exit;
		Panel pn,pn1,pn2,pn3;
		public void GUI() {
			lb = new Label("CHÀO MỪNG QUÝ KHÁCH!");
			lb1 = new Label("Tài Khoản: ");
			lb2 = new Label("Mật Khẩu: ");
			
			txta = new TextField();
			txtb = new TextField();
			
			ok = new Button("Đăng nhập");
			sign = new Button("Đăng kí");
			exit = new Button("Thoát");
			
			ok.addActionListener(this);
			sign.addActionListener(this);
			exit.addActionListener(this);
			
			pn = new Panel(new GridLayout(3,1));
			pn1 = new Panel(new FlowLayout());
			pn2 = new Panel(new GridLayout(2,2));
			pn3 = new Panel(new FlowLayout());
			
			pn1.add(lb);
			
			pn2.add(lb1);
			pn2.add(txta);
			pn2.add(lb2);
			pn2.add(txtb);
			
			pn3.add(ok);
			pn3.add(sign);
			pn3.add(exit);
			
			pn.add(pn1);
			pn.add(pn2);
			pn.add(pn3);
			add(pn);
			setSize(400,300);
			show();
		}
		public void sqlrun(){
			int i = 0;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
				Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM ACCOUNT";
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()) {
					tk[i] = rs.getString("login");
					ps[i] = rs.getString("password");
					i++;
				}
				rs.close();
				stmt.close();
			} catch(Exception e) {System.out.println("Error "+e);};
		}
		public void actionPerformed(ActionEvent e) {
			sqlrun();
			if(e.getSource()==ok){
				int i = 0,j = 0;
				char kt;
				int count = 0, count1 = 0;
				if(txta.getText().isEmpty() == false) {
					for (int m = 0; m < txta.getText().length(); m++) {
						kt = txta.getText().charAt(m);
						if (Character.isSpace(kt)) {
							count++;
						}
					}
					if(count==0) {

						while (tk[i] != null) {
							if (tk[i].equals(txta.getText())) {
								if (ps[i].equals(txtb.getText())) {
									JOptionPane.showMessageDialog(this, "Đăng nhập thành công");
									new User(txta.getText());
									this.dispose();
									j++;
								} else {
									JOptionPane.showMessageDialog(this, "Mật khẩu không chính xác");
									txtb.setText("");
									j++;
								}
							}
							i++;
						}
						if (j == 0) {
							JOptionPane.showMessageDialog(this, "Bạn chưa có tài khoản");
						}
					}else {
						JOptionPane.showMessageDialog(this, "Tên tài khoản không có dấu cách !");
						txta.setText("");
					}
				}else{
					JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ");
				}
			}
			if(e.getSource()==sign){
				new Dang_ki("Đăng kí");
				this.dispose();
			}
			if(e.getSource()==exit) {
				new APP("Web bán sách uy tín");
				this.dispose();
			}
		}
		public Dang_nhap(String at) {
			super(at);
			GUI();
		}
		public static void main(String[] args) {
			new Dang_nhap("Đăng nhập");
		}
	}
