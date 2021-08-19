package Body;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class Dang_ki extends Frame implements ActionListener {
		String tk[] = new String[50],ps[] = new String[50];
		Label lb1,lb2,lb3,lb;
		TextField txta,txtb,txtc;
		Button ok,exit;
		Panel pn,pn1,pn2,pn3;
		public void GUI1() {
			lb = new Label("Lập tài khoản");
			lb1 = new Label("Tài Khoản: ");
			lb2 = new Label("Mật Khẩu: ");
			lb3 = new Label("Nhập lại mật khẩu: ");
			
			txta = new TextField();
			txtb = new TextField();
			txtc = new TextField();
			
			ok = new Button("Ok");
			exit = new Button("Thoát");
			
			ok.addActionListener(this);
			exit.addActionListener(this);
			
			pn = new Panel(new GridLayout(3,1));
			pn1 = new Panel(new FlowLayout());
			pn2 = new Panel(new GridLayout(3,2));
			pn3 = new Panel(new FlowLayout());
			
			pn1.add(lb);
			
			pn2.add(lb1);
			pn2.add(txta);
			pn2.add(lb2);
			pn2.add(txtb);
			pn2.add(lb3);
			pn2.add(txtc);
			
			pn3.add(ok);
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
			char kt;
			int count = 0,count1=0;
			if(e.getSource()==ok){
				boolean check = true;
				if(txta.getText().isEmpty() == false) {
					if (txta.getText().matches("^[a-z A-Z 1-9]{1,50}$")) {
						for (int i = 0; i < txta.getText().length(); i++) {
							kt = txta.getText().charAt(i);
							if (Character.isSpace(kt)) {
								count++;
							}
						}
						if(count==0) {
							for (int i = 0; tk[i] != null; i++) {
								if (tk[i].equals(txta.getText())) check = false;
							}
							if (check) {
								if (txtb.getText().isEmpty() == false) {
									if (txtb.getText().matches("^[a-z A-Z 1-9]{1,50}$")) {
										for (int i = 0; i < txtb.getText().length(); i++) {
											kt = txtb.getText().charAt(i);
											if (Character.isSpace(kt)) {
												count1++;
											}
										}if(count1==0){
											if (txtb.getText().equals(txtc.getText())) {
												try {
													Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
													String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
													Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
													Statement stmt = con.createStatement();
													String sql121 = "INSERT INTO ACCOUNT(login,password,solan) Values('" + txta.getText() + "','" + txtb.getText() + "',0)";
													stmt.executeUpdate(sql121);
													stmt.close();
												} catch (Exception a) {
													System.out.println("Error " + a);
												}
												;
												JOptionPane.showMessageDialog(this, "Đăng kí thành công\nBạn có thể đăng nhập");
												new Dang_nhap("Đăng nhập");
												this.dispose();
											} else {
												JOptionPane.showMessageDialog(this, "Mật khẩu không khớp");
												txtb.setText("");
												txtc.setText("");
											}
										}else{
											JOptionPane.showMessageDialog(this, "Vui lòng không điền dấu cách !");
											txtb.setText("");
											txtc.setText("");
										}
									} else {
										JOptionPane.showMessageDialog(this, "Nhập sai kí tự !!!");
										txtb.setText("");
										txtc.setText("");
									}
								} else {
									JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ");
								}
							} else {
								JOptionPane.showMessageDialog(this, "Tài khoản đã có người sử dụng");
								txta.setText("");
								txtb.setText("");
								txtc.setText("");
							}
						}else{
							JOptionPane.showMessageDialog(this, "Vui lòng không điền dấu cách !");
							txta.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Nhập sai kí tự !!!");
						txta.setText("");
						txtb.setText("");
						txtc.setText("");
					}
				}else{
					JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ");
				}
			}
			if(e.getSource()==exit){
				new APP("Web ban sach uy tin");
				this.dispose();}
		}
	public Dang_ki(String at) {
		super(at);
		GUI1();
	}
	public static void main(String[] args) {}
}
