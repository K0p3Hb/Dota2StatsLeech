package statsleech;

import java.beans.Statement;
import java.sql.*;
import java.util.logging.*;

public class Leech2DB {

	private String url;
	private String name;
	private String password;
	
	public Leech2DB (String url, String name, String pass){
		
		this.url=url;
		this.name=name;
		this.password=pass;
		
	}
	
	public void Initialize() {
		
		//метод создаст нужную структуру таблиц в пустой базе
		
		//создаём пустой коннект к базе
		Connection connect = null;
		
		try{
			//Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            //Создаём соединение
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            System.out.println("Соединение установлено");
            
            java.sql.Statement statement = null;
            
            statement = connect.createStatement();
            
            if(!statement.execute("create table if not exists accounts (account_id bigint primary key,"
            		+ " account_name varchar)"))
            	System.out.println("Базa аккаунтов создана");
            
            if(!statement.execute("create table if not exists items (item_id int primary key, item_name varchar,"
            		+ " image_url varchar)"))
            	System.out.println("Базa вещей создана");
            		
            if(!statement.execute("create table if not exists heroes (hero_id int primary key, hero_name varchar,"
            		+ " image_url varchar)"))
            	System.out.println("Базa героев создана");
            
            if(!statement.execute("create table if not exists matches (match_id bigint primary key, duration int,"
            		+ " game_mode int,"
            		+ " radiant_win boolean, top_hero_damage bigint references accounts(account_id),"
            		+ " top_last_hits bigint references accounts(account_id),"
            		+ " top_killer bigint references accounts(account_id),"
            		+ "top_tower_damage bigint references accounts(account_id))"))
            	System.out.println("Базa матчей создана");
            
            if(!statement.execute("create table if not exists all_player_slots (match_id bigint primary key,"
            		+ " hero_id bigint references heroes(hero_id),"
            		+ " account_id bigint references accounts(account_id),"
            		+ " kills int, deaths int, assists int,"
            		+ " item_0 int references items(item_id),"
            		+ " item_1 int references items(item_id),"
            		+ " item_2 int references items(item_id),"
            		+ " item_3 int references items(item_id),"
            		+ " item_4 int references items(item_id),"
            		+ " item_5 int references items(item_id),"
            		+ " hero_damage bigint, tower_damage bigint, last_hits int,"
            		+ " unique (match_id, account_id))"))
            	System.out.println("Базa всех слотов игроков создана");
		}
		
		catch (Exception ex){
			//выводим наиболее значимые сообщения
            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try{
			//закрываем соединение
			connect.close();
		}
		catch (Exception ex){
			//выводим наиболее значимые сообщения
            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
}
