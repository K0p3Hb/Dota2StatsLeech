package statsleech;

import java.sql.*;
import java.util.logging.*;

public class DBInit {

	private String url;
	private String name;
	private String password;
	
	public DBInit (String url, String name, String pass){
		
		this.url=url;
		this.name=name;
		this.password=pass;
		
	}
	
	public void Initialize() {
		
		//������ ������ ������� � ����
		Connection connect = null;
		
		try{
			//��������� �������
            Class.forName("org.postgresql.Driver");
            System.out.println("������� ���������");
            //������ ����������
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            System.out.println("���������� �����������");
		}
		
		catch (Exception ex){
			//������� �������� �������� ���������
            Logger.getLogger(DBInit.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try{
			//��������� ����������
			connect.close();
		}
		catch (Exception ex){
			//������� �������� �������� ���������
            Logger.getLogger(DBInit.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
}
