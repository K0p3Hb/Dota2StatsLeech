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
	
	//������� ��������� ������ � ������ ����
	public void initialize() {
		
		//������ ������ ������� � ����
		Connection connect = null;
		
		try{
			//��������� �������
            Class.forName("org.postgresql.Driver");
            //System.out.println("������� ���������");
            
            //������ ����������
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            //System.out.println("���������� �����������");
            
            java.sql.Statement statement = null;
            
            statement = connect.createStatement();
            
            //������ ������� ���������
            /*if(!*/statement.execute("create table if not exists accounts (account_id bigint primary key,"
            		+ " account_name varchar)");//)
            //	System.out.println("���a ��������� �������");
            
          //������ ������� �����
            /*if(!*/statement.execute("create table if not exists items (item_id int primary key, item_name varchar,"
            		+ " image_url varchar)");//)
            //	System.out.println("���a ����� �������");
            		
          //������ ������� ������
            /*if(!*/statement.execute("create table if not exists heroes (hero_id int primary key, hero_name varchar,"
            		+ " image_url varchar)");//)
            //	System.out.println("���a ������ �������");
            
          //������ ������� ������
            /*if(!*/statement.execute("create table if not exists matches (match_id bigint primary key, duration int,"
            		+ " game_mode int,"
            		+ " radiant_win boolean, top_hero_damage bigint references accounts(account_id),"
            		+ " top_last_hits bigint references accounts(account_id),"
            		+ " top_killer bigint references accounts(account_id),"
            		+ "top_tower_damage bigint references accounts(account_id))");//)
            //	System.out.println("���a ������ �������");
            
          //������ ������� ���� ������ �������
            /*if(!*/statement.execute("create table if not exists all_player_slots"
            		+ " (match_id bigint references matches(match_id),"
            		+ " hero_id int references heroes(hero_id),"
            		+ " account_id bigint references accounts(account_id),"
            		+ " slot_number int,"
            		+ " kills int,"
            		+ " deaths int,"
            		+ " assists int,"
            		+ " item_0 int references items(item_id),"
            		+ " item_1 int references items(item_id),"
            		+ " item_2 int references items(item_id),"
            		+ " item_3 int references items(item_id),"
            		+ " item_4 int references items(item_id),"
            		+ " item_5 int references items(item_id),"
            		+ " hero_damage bigint,"
            		+ " percent_damage int,"
            		+ " tower_damage bigint,"
            		+ " percent_tower int,"
            		+ " last_hits int,"
            		+ " gold bigint,"
            		+ " percent_kills int,"
            		+ " unique (match_id, account_id))");//)
           	//System.out.println("���a ���� ������ ������� �������");
            
            
		}
		
		catch (Exception ex){
			//������� �������� �������� ���������
            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		finally{
			try{
				//��������� ����������
				connect.close();
				}
			catch (Exception ex){
				//������� �������� �������� ���������
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}

	//���������� ������� � ������� ���������
	public void updateAccounts(List<MatchDetailPlayer> List){
		
		//������ ������ ������� � ����
		Connection connect = null;
		
		try{
			//��������� �������
            Class.forName("org.postgresql.Driver");
            //System.out.println("������� ���������");
            
            //������ ����������
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            //System.out.println("���������� �����������");
            
            PreparedStatement statement = null;
            java.sql.Statement st = null;
            
            //�������������� ������ �� ���������� ������ � ����������
            statement = connect.prepareStatement("insert into accounts (account_id) values (?)");
            st=connect.createStatement();
            
            long id; 
            
            ResultSet rs = null;
            
            for(MatchDetailPlayer player : List) {
            	
            	id = player.getAccountId();
            	
            	//���� ������ �������� ��� ��� � ������� �� ��������� ������ � �����
            	rs = st.executeQuery("select * from accounts where account_id = " + id);
            	if (!rs.next()){
            		 statement.setLong(1, id);
            		 statement.addBatch();
            	 }
            }
            
            statement.executeBatch();
		}
		catch(Exception ex){
			//������� �������� �������� ���������
            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally{
			try{
				//��������� ����������
				connect.close();
				}
			catch (Exception ex){
				//������� �������� �������� ���������
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	//���������� ������� � ������� �����
	public void updateItems(List<Item> List){
		//������ ������ ������� � ����
				Connection connect = null;
				
				try{
					//��������� �������
		            Class.forName("org.postgresql.Driver");
		            //System.out.println("������� ���������");
		            
		            //������ ����������
		            connect = DriverManager.getConnection(this.url, this.name, this.password);
		            //System.out.println("���������� �����������");
		            
		            PreparedStatement statement = null;
		            java.sql.Statement st = null;
		            
		            //�������������� ������ �� ���������� ������ � ����������
		            statement = connect.prepareStatement("insert into items (item_id) values (?)");
		            st=connect.createStatement();
		            
		            int id; 
		            
		            ResultSet rs = null;
		            
		            for(Item item : List) {
		            	
		            	id = item.getId();
		            	
		            	//���� ������ �������� ��� ��� � ������� �� ��������� ������ � �����
		            	rs = st.executeQuery("select * from items where item_id = " + id);
		            	if (!rs.next()){
		            		 statement.setInt(1, id);
		            		 statement.executeUpdate();
		            	 }
		            }
		            
				}
				catch(Exception ex){
					//������� �������� �������� ���������
		            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
				}
				finally{
					try{
						//��������� ����������
						connect.close();
						}
					catch (Exception ex){
						//������� �������� �������� ���������
						Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
	}
	
	//���������� ������� � ������� ������
	public void updateHeroes(List<MatchDetailPlayer> List){
				//������ ������ ������� � ����
				Connection connect = null;
				
				try{
					//��������� �������
		            Class.forName("org.postgresql.Driver");
		            //System.out.println("������� ���������");
		            
		            //������ ����������
		            connect = DriverManager.getConnection(this.url, this.name, this.password);
		            //System.out.println("���������� �����������");
		            
		            PreparedStatement statement = null;
		            java.sql.Statement st = null;
		            
		            //�������������� ������ �� ���������� ������ � ����������
		            statement = connect.prepareStatement("insert into heroes (hero_id) values (?)");
		            st=connect.createStatement();
		            
		            int id; 
		            
		            ResultSet rs = null;
		            
		            for(MatchDetailPlayer player : List) {
		            	
		            	id = player.getHeroId();
		            	
		            	//���� ������ ����� ��� ��� � ������� �� ��������� ������ � �����
		            	rs = st.executeQuery("select * from heroes where hero_id = " + id);
		            	if (!rs.next()){
		            		 statement.setInt(1, id);
		            		 statement.addBatch();
		            	 }
		            }
		            statement.executeBatch();
		            
				}
				catch(Exception ex){
					//������� �������� �������� ���������
		            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
				}
				finally{
					try{
						//��������� ����������
						connect.close();
						}
					catch (Exception ex){
						//������� �������� �������� ���������
						Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
	}

	//���������� ������� � ������� ������
	public void updateMatchesAndSlots(MatchDetail match){
		
		//������ ������ ������� � ����
		Connection connect = null;
				
			try{
				//��������� �������
		        Class.forName("org.postgresql.Driver");
		        //System.out.println("������� ���������");
		            
		        //������ ����������
		        connect = DriverManager.getConnection(this.url, this.name, this.password);
		        //System.out.println("���������� �����������");
		            
		        PreparedStatement statement = null, statement1 =null;
		        java.sql.Statement st = null;
		            
		        //�������������� ������ �� ���������� ������ � ����������
		        statement = connect.prepareStatement("insert into matches (match_id, duration, game_mode, radiant_win,"
		        		+ "top_hero_damage, top_last_hits, top_killer, top_tower_damage)"
		        		+ " values (?,?,?,?,?,?,?,?)");
		        
		        statement1 = connect.prepareStatement("insert into all_player_slots (match_id, hero_id, account_id,"
		        		+ "slot_number,"
	            		+ "kills, deaths, assists, item_0, item_1, item_2, item_3, item_4, item_5,"
	            		+ "hero_damage, percent_damage, tower_damage, percent_tower ,"
	            		+ " last_hits, gold, percent_kills)"
	            		+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		        
		        st=connect.createStatement();
		            
		        long id = match.getMatchId();
		        
		        long tmpkill = 0, tmplast = 0, tmptower = 0, tmpherod = 0, tmp = 1,
		        		tmpkill1 = 0, tmplast1 = 0, tmptower1 = 0, tmpherod1 = 0,
		        		top_hero = 0, top_tower = 0, top_last = 0, top_killer = 0,
		        		top_hero1 = 0, top_tower1 = 0, top_killer1 = 0,
		        		radiant_hero = 0, dire_hero = 0, radiant_tower = 1, dire_tower = 1;
		        
		        int radiant_kill = 0, dire_kill = 0;

		        List<MatchDetailPlayer> players = match.getPlayers();
		        
		        for(MatchDetailPlayer player:players){
	        		
		        	//������� ����� ���� ������ �� ������ � ���� ������� ������� ����
	        		tmp  = player.getHeroDamageDealt();
	        		
	        		//������� �������
	        		if(player.getPlayerSlots()<5){
	        			
	        			radiant_hero += tmp;
	        			if(tmpherod < tmp){
	        				top_hero = player.getAccountId();
	        				tmpherod = tmp;
	        			}	
	        		}
	        		
	        		//������� �����
	        		if(player.getPlayerSlots()>127 ){
	        			dire_hero += tmp;
	        			if(tmpherod < tmp){
	        				top_hero1 = player.getAccountId();
	        				tmpherod1 = tmp;
	        			}
	        		}
	        		
	        		//���� ��� ����� ������ ������
	        		tmp = player.getLastHits();
	        		if(tmplast < tmp){
	        			top_last = player.getAccountId();
	        			tmplast = tmp;
	        		}
	        		
	        		//���� �������� �������� ���������� ������� � �������� � ������� �������� ������
	        		tmp = player.getKills() + player.getAssists();
	        		//�������
	        		if(player.getPlayerSlots()<5){
	        			radiant_kill += player.getKills();
	        			if(tmpkill < tmp){
	        				top_killer = player.getAccountId();
	        				tmpkill = tmp;
	        			}
	        		}
	        		
	        		//�����
	        		if(player.getPlayerSlots()>127){
	        			dire_kill += player.getKills();
	        			if(tmpkill < tmp){
	        				top_killer1 = player.getAccountId();
	        				tmpkill1 = tmp;
	        			}
	        		}
	        		
	        		//���� ��� ������ ���� ����� �� ��������� � �������� � ������� ����� ���� ������ �� ���������
	        		tmp = player.getTowerDamageDealt();
	        		//�������
	        		if(player.getPlayerSlots()<5){
	        			radiant_tower += tmp;
	        			if(tmptower < tmp){
	        				top_tower = player.getAccountId();
	        				tmptower = tmp;
	        			}
	        		}
	        		
	        		//�����
	        		if(player.getPlayerSlots()>127){
	        			dire_tower += tmp;
	        			if(tmptower < tmp){
	        				top_tower1 = player.getAccountId();
	        				tmptower1 = tmp;
	        			}
	        		}
	        	}
		        
		        //�������� ��� ����� ������ ������� �� �������
		        if (tmpherod/500 + ((tmpherod*100f)/radiant_hero)/2 >
		        		tmpherod1/500 + ((tmpherod1*100f)/dire_hero)/2)
		        	tmpherod = top_hero;
		        else
		        	tmpherod = top_hero1;
		        
		        //�������� ��� ������ ������ �� �������
		        if (tmpkill + ((tmpkill*100f)/radiant_kill)/2 >
		        		tmpkill1/500 + ((tmpkill1*100f)/dire_kill)/2)
		        	tmpkill = top_killer;
		        else
		        	tmpkill = top_killer1;
		        
		        //�������� ��� ����� ������ �� ���������
		        if (tmptower/100 + ((tmptower*100f)/radiant_tower)/2 >
        				tmptower1/500 + ((tmptower1*100f)/dire_tower)/2)
		        	tmptower = top_tower;
		        else
		        	tmptower = top_tower1;

		        ResultSet rs = null;
		        
		        //���� ������ ����� ��� ��� � ������� �� ��������� ������ � �����
		        rs = st.executeQuery("select * from matches where match_id = " + id);
		        if (!rs.next()){
		        	//����� � ������� ����� �����
		        	statement.setLong(1, id);
		        	//����� � ������� ������������ ����� � ��������
		            statement.setInt(2, match.getDurationOfMatch());
		          //����� � ������� ��� ���� �����
		            statement.setInt(3, match.getGameMode().getValue());
		          //����� � ������� ��� ������� � �����
		            statement.setBoolean(4, match.didRadianWin());
		          //����� � ������� ����� �������� ������ ������� ���� ���������
		          //���������� ����� � ������� ������� ����� �� ����� �������  �� ������
		            statement.setLong(5, top_hero);
		          //����� � ������� ����� �������� ������ ������� ����� ������ ����� ������
		            statement.setLong(6, top_last);
		          //����� � ������� ����� �������� ������ ������� ���� � ����� ����� ������ ����
		            statement.setLong(7, top_killer);
		          //����� � ������� ����� �������� ������ ������� ���� ���������
			      //���������� ����� � ������� ������� ����� �� ����� ������� �� ���������
		            statement.setLong(8, top_tower);
		            
		            statement.executeUpdate();
		        }
		        
		        //��������� ����� � �������, ���� �� ��� ��� ���

	            int i;
	            
	            for(MatchDetailPlayer player : players) {
	            	
	            	id = player.getAccountId();
	            	
	            	i=1;
	            	
	            	//���� ������ �������� ��� ��� � ������� �� ��������� ������ � �����
	            	rs = st.executeQuery("select * from all_player_slots where match_id = " + match.getMatchId()
	            			+ " and account_id = " + id);
	            	
	            	if (!rs.next()){
	            		//����� � ������� ����� ����� ��� ����� �����
	            		 statement1.setLong(i++, match.getMatchId());
	            		//����� � ������� ����� ����� � �����
	            		 statement1.setInt(i++, player.getHeroId());
	            		//����� � ������� ����� �������� ������
	            		 statement1.setLong(i++, id);
	            		 //����� � ������� ����� �����
	            		 statement1.setInt(i++, player.getPlayerSlots());
	            		//����� � ������� ���������� ������� ������
	            		 statement1.setInt(i++, player.getKills());
	            		//����� � ������� ���������� ������� ������
	            		 statement1.setInt(i++, player.getDeaths());
	            		//����� � ������� ���������� �������� ������
	            		 statement1.setInt(i++, player.getAssists());
	            		//����� � ������� ��� ���� ������
	            		 for(Item item:player.getItems())
	            			 statement1.setInt(i++, item.getId());
	            		//����� � ������� ������� ����� ���� ����� ������ �������
	            		 statement1.setLong(i++, player.getHeroDamageDealt());
	            		//����� � ������� ������� �����	������ ������� ����� �� ���������� �� ������
	            		 if(player.getPlayerSlots()<5 )
	            			 statement1.setInt(i++, (int) ((player.getHeroDamageDealt()*100f)/radiant_hero));
	            		 else
	            			 statement1.setInt(i++, (int) ((player.getHeroDamageDealt()*100f)/dire_hero));
	            		 //����� � ������� ��������� ����������� ������� ����� �� ���������
	            		 statement1.setLong(i++, player.getTowerDamageDealt());
	            		//����� � ������� ������� ���������� ������� ����� �� ���������� �� ���������
	            		 if(player.getPlayerSlots()<5 )
	            			 statement1.setInt(i++, (int) ((player.getTowerDamageDealt()*100f)/radiant_tower));
	            		 else
	            			 statement1.setInt(i++, (int) ((player.getTowerDamageDealt()*100f)/dire_tower));
	            		 //����� � ������� ���������� ������� ������
	            		 statement1.setInt(i++, player.getLastHits());
	            		 //����� � ������� ���������� ������������� ������
	            		 statement1.setInt(i++, player.getGoldPerMinute()*(match.getDurationOfMatch()/60));
	            		//����� � ������� ������� ������� ������ � ���������
	            		 if(player.getPlayerSlots()<5 )
	            			 statement1.setInt(i++, (int) (((player.getKills()+player.getAssists())*100f)/radiant_kill));
	            		 else
	            			 statement1.setInt(i++, (int) (((player.getKills()+player.getAssists())*100f)/dire_kill));
	            		 
	            		 statement1.addBatch();
	            	 }
	            	statement1.executeBatch();
	            }
	            

			}
			catch(Exception ex){
				//������� �������� �������� ���������
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally{
				try{
					//��������� ����������
					connect.close();
					}
				catch (Exception ex){
					//������� �������� �������� ���������
					Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
	}

	/*//���������� ������� � ������� ������
	public void updatePlayerSlots(List<MatchDetailPlayer> List, long match_id){

		//������ ������ ������� � ����
		Connection connect = null;
		
		try{
			//��������� �������
            Class.forName("org.postgresql.Driver");
            //System.out.println("������� ���������");
            
            //������ ����������
            connect = DriverManager.getConnection(this.url, this.name, this.password);
            //System.out.println("���������� �����������");
            
            PreparedStatement statement = null;
            java.sql.Statement st = null;
            
            //�������������� ������ �� ���������� ������ � ����������
            statement = connect.prepareStatement("insert into all_player_slots (match_id, hero_id, account_id,"
            		+ "kills, deaths, assists, item_0, item_1, item_2, item_3, item_4, item_5,"
            		+ "hero_damage, tower_damage, last_hits)"
            		+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            st=connect.createStatement();
            
            long id; 
            int i;
            
            ResultSet rs = null;
            
            for(MatchDetailPlayer player : List) {
            	
            	id = player.getAccountId();
            	
            	i=1;
            	
            	//���� ������ �������� ��� ��� � ������� �� ��������� ������ � �����
            	rs = st.executeQuery("select * from all_player_slots where match_id = " + match_id
            			+ " and account_id = " + id);
            	
            	if (!rs.next()){
            		 statement.setLong(i++, id);
            		 statement.setInt(i++, player.getHeroId());
            		 statement.setLong(i++, id);
            		 statement.setInt(i++, player.getKills());
            		 statement.setInt(i++, player.getDeaths());
            		 statement.setInt(i++, player.getAssists());
            		 
            		 for(Item item:player.getItems())
            			 statement.setInt(i++, item.getId());
            		 
            		 statement.setLong(i++, player.getHeroDamageDealt());
            		 statement.setLong(i++, player.getTowerDamageDealt());
            		 statement.setInt(i++, player.getLastHits());
            		 
            		 statement.addBatch();
            	 }
            }
            statement.executeBatch();
            
		}
		catch(Exception ex){
			//������� �������� �������� ���������
            Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally{
			try{
				//��������� ����������
				connect.close();
				}
			catch (Exception ex){
				//������� �������� �������� ���������
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}*/
	
}