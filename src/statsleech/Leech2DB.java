package statsleech;

import java.beans.Statement;
import java.sql.*;
import java.util.List;
import java.util.logging.*;

import de.inkvine.dota2stats.domain.matchdetail.Item;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;

public class Leech2DB {

	private String url;
	private String name;
	private String password;
	
	public Leech2DB (String url, String name, String pass){
		
		this.url=url;
		this.name=name;
		this.password=pass;
		
	}
	
	//создаст структуру таблиц в пустой базе
	public void initialize() {
		
		//создаём пустой коннект к базе
		Connection connect = null;
		
		try{
			//Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            
            //Создаём соединение
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            //System.out.println("Соединение установлено");
            
            java.sql.Statement statement = null;
            
            statement = connect.createStatement();
            
            //создаём таблицу аккаунтов
            /*if(!*/statement.execute("create table if not exists accounts (account_id bigint primary key,"
            		+ " account_name varchar)");//)
            //	System.out.println("Базa аккаунтов создана");
            
          //создаём таблицу вещей
            /*if(!*/statement.execute("create table if not exists items (item_id int primary key, item_name varchar,"
            		+ " image_url varchar)");//)
            //	System.out.println("Базa вещей создана");
            		
          //создаём таблицу героев
            /*if(!*/statement.execute("create table if not exists heroes (hero_id int primary key, hero_name varchar,"
            		+ " image_url varchar)");//)
            //	System.out.println("Базa героев создана");
            
          //создаём таблицу матчей
            /*if(!*/statement.execute("create table if not exists matches (match_id bigint primary key, duration int,"
            		+ " game_mode int,"
            		+ " radiant_win boolean, top_hero_damage bigint references accounts(account_id),"
            		+ " top_last_hits bigint references accounts(account_id),"
            		+ " top_killer bigint references accounts(account_id),"
            		+ "top_tower_damage bigint references accounts(account_id))");//)
            //	System.out.println("Базa матчей создана");
            
          //создаём таблицу всех слотов игроков
            /*if(!*/statement.execute("create table if not exists all_player_slots (match_id bigint primary key,"
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
            		+ " unique (match_id, account_id))");//)
           	//System.out.println("Базa всех слотов игроков создана");
            
            
		}
		
		catch (Exception ex){
			//выводим наиболее значимые сообщения
            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		finally{
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

	//добавление записей в таблицу аккаунтов
	public void updateAccounts(List<MatchDetailPlayer> List){
		
		//создаём пустой коннект к базе
		Connection connect = null;
		
		try{
			//Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            
            //Создаём соединение
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            //System.out.println("Соединение установлено");
            
            PreparedStatement statement = null;
            java.sql.Statement st = null;
            
            //подготавливаем запрос на добавление записи с параметром
            statement = connect.prepareStatement("insert into accounts (account_id) values (?)");
            st=connect.createStatement();
            
            long id; 
            
            for(MatchDetailPlayer player : List) {
            	
            	id = player.getAccountId();
            	
            	//если такого аккаунта ещё нет в таблице то добавляем запрос в пакет
            	if (!st.execute("select * from accounts where account_id = " + id)){
            		 statement.setLong(1, id);
            		 statement.addBatch();
            	 }
            }
            
            statement.executeLargeBatch();
		}
		catch(Exception ex){
			//выводим наиболее значимые сообщения
            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally{
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
	
	//добавление записей в таблицу вещей
	public void updateItems(List<Item> List){
		//создаём пустой коннект к базе
				Connection connect = null;
				
				try{
					//Загружаем драйвер
		            Class.forName("org.postgresql.Driver");
		            System.out.println("Драйвер подключен");
		            
		            //Создаём соединение
		            connect = DriverManager.getConnection(this.url, this.name, this.password);
		            //System.out.println("Соединение установлено");
		            
		            PreparedStatement statement = null;
		            java.sql.Statement st = null;
		            
		            //подготавливаем запрос на добавление записи с параметром
		            statement = connect.prepareStatement("insert into items (item_id) values (?)");
		            st=connect.createStatement();
		            
		            int id; 
		            
		            for(Item item : List) {
		            	
		            	id = item.getId();
		            	
		            	//если такого предмета ещё нет в таблице то добавляем запрос в пакет
		            	if (!st.execute("select * from items where item_id = " + id)){
		            		 statement.setInt(1, id);
		            		 statement.addBatch();
		            	 }
		            }
		            
		            statement.executeLargeBatch();
				}
				catch(Exception ex){
					//выводим наиболее значимые сообщения
		            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
				}
				finally{
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
	
	//добавление записей в таблицу героев
	public void updateHeroes(List<MatchDetailPlayer> List){
		//создаём пустой коннект к базе
				Connection connect = null;
				
				try{
					//Загружаем драйвер
		            Class.forName("org.postgresql.Driver");
		            System.out.println("Драйвер подключен");
		            
		            //Создаём соединение
		            connect = DriverManager.getConnection(this.url, this.name, this.password);
		            //System.out.println("Соединение установлено");
		            
		            PreparedStatement statement = null;
		            java.sql.Statement st = null;
		            
		            //подготавливаем запрос на добавление записи с параметром
		            statement = connect.prepareStatement("insert into heroes (hero_id) values (?)");
		            st=connect.createStatement();
		            
		            int id; 
		            
		            for(MatchDetailPlayer player : List) {
		            	
		            	id = player.getHeroId();
		            	
		            	//если такого героя ещё нет в таблице то добавляем запрос в пакет
		            	if (!st.execute("select * from heroes where hero_id = " + id)){
		            		 statement.setInt(1, id);
		            		 statement.addBatch();
		            	 }
		            }
		            
		            statement.executeBatch();
				}
				catch(Exception ex){
					//выводим наиболее значимые сообщения
		            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
				}
				finally{
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

	//добавление записей в таблицу матчей
	public void updateMatches(MatchDetail match){
		
		//создаём пустой коннект к базе
		Connection connect = null;
				
			try{
				//Загружаем драйвер
		        Class.forName("org.postgresql.Driver");
		        System.out.println("Драйвер подключен");
		            
		        //Создаём соединение
		        connect = DriverManager.getConnection(this.url, this.name, this.password);
		        //System.out.println("Соединение установлено");
		            
		        PreparedStatement statement = null;
		        java.sql.Statement st = null;
		            
		        //подготавливаем запрос на добавление записи с параметром
		        statement = connect.prepareStatement("insert into items (item_id) values (?)");
		        st=connect.createStatement();
		            
		        long id = match.getMatchId(); 
		            
		        //если такого матча ещё нет в таблице то добавляем запрос в пакет
		        if (!st.execute("select * from matches where match_id = " + id)){
		            statement.setLong(1, id);
		            statement.addBatch();
		            }
		         statement.executeBatch();

			}
			catch(Exception ex){
			//выводим наиболее значимые сообщения
		    Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally{
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

}