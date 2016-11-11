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
	public void updateMatches(MatchDetail match){
		
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
		        statement = connect.prepareStatement("insert into matches (match_id, duration, game_mode, radiant_win,"
		        		+ "top_hero_damage, top_last_hits, top_killer, top_tower_damage)"
		        		+ " values (?,?,?,?,?,?,?,?)");
		        st=connect.createStatement();
		            
		        long id = match.getMatchId();
		        
		        long tmpkill = 0, tmplast = 0, tmptower = 0, tmpherod = 0, tmp = 1,
		        		top_hero = 0, top_tower = 0, top_last = 0, top_killer = 0;

		            
		        ResultSet rs = null;
		        
		        //���� ������ ����� ��� ��� � ������� �� ��������� ������ � �����
		        rs = st.executeQuery("select * from matches where match_id = " + id);
		        if (!rs.next()){
		        	
		        	for(MatchDetailPlayer player:match.getPlayers()){
		        		
		        		tmp  = player.getHeroDamageDealt();
		        		if(tmpherod < tmp){
		        			top_hero = player.getAccountId();
		        			tmpherod = tmp;
		        		}
		        		
		        		tmp = player.getLastHits();
		        		if(tmplast < tmp){
		        			top_last = player.getAccountId();
		        			tmplast = tmp;
		        		}
		        		
		        		tmp = player.getKills();
		        		if(tmpkill < tmp){
		        			top_killer = player.getAccountId();
		        			tmpkill = tmp;
		        		}
		        		
		        		tmp = player.getTowerDamageDealt();
		        		if(tmptower < tmp){
		        			top_tower = player.getAccountId();
		        			tmptower = tmp;
		        		}
		        		
		        	}
		        	statement.setLong(1, id);
		            statement.setInt(2, match.getDurationOfMatch());
		            statement.setInt(3, match.getGameMode().getValue());
		            statement.setBoolean(4, match.didRadianWin());
		            statement.setLong(5, top_hero);
		            statement.setLong(6, top_last);
		            statement.setLong(7, top_killer);
		            statement.setLong(8, top_tower);
		            statement.executeUpdate();
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

}