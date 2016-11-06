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
		
		//создаём пустой коннект к базе
		Connection connect = null;
		
		try{
			//Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            //Создаём соединение
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            System.out.println("Соединение установлено");
		}
		
		catch (Exception ex){
			//выводим наиболее значимые сообщения
            Logger.getLogger(DBInit.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try{
			//закрываем соединение
			connect.close();
		}
		catch (Exception ex){
			//выводим наиболее значимые сообщения
            Logger.getLogger(DBInit.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
}
