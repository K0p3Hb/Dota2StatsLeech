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

	public Leech2DB(String url, String name, String pass) {
		this.url = url;
		this.name = name;
		this.password = pass;
	}

	// создаст структуру таблиц в пустой базе
	public void initialize() {
		// создаём пустой коннект к базе
		Connection connect = null;
		try {
			// Загружаем драйвер
			Class.forName("org.postgresql.Driver");
			// System.out.println("Драйвер подключен");
			// Создаём соединение
			connect = DriverManager.getConnection(this.url, this.name, this.password);
			// System.out.println("Соединение установлено");
			java.sql.Statement statement = null;
			statement = connect.createStatement();
			// создаём таблицу аккаунтов
			/* if(! */statement.execute(
					"create table if not exists accounts (account_id bigint primary key," + " account_name varchar)");// )
			// System.out.println("Базa аккаунтов создана");
			// создаём таблицу вещей
			/* if(! */statement.execute(
					"create table if not exists items (item_id int primary key, item_name varchar, image_url varchar)");// )
			// System.out.println("Базa вещей создана");
			// создаём таблицу героев
			/* if(! */statement.execute(
					"create table if not exists heroes (hero_id int primary key, hero_name varchar, image_url varchar)");// )
			// System.out.println("Базa героев создана");
			// создаём таблицу матчей
			/* if(! */statement.execute(
					"create table if not exists matches (match_id bigint primary key, duration int," + " game_mode int,"
							+ " radiant_win boolean, top_hero_damage bigint references accounts(account_id),"
							+ " top_last_hits bigint references accounts(account_id),"
							+ " top_killer bigint references accounts(account_id),"
							+ "top_tower_damage bigint references accounts(account_id))");// )
			// System.out.println("Базa матчей создана");
			// создаём таблицу всех слотов игроков
			/* if(! */statement.execute("create table if not exists all_player_slots"
					+ " (match_id bigint references matches(match_id),"
					+ " hero_id int references heroes(hero_id),"
					+ " account_id bigint references accounts(account_id),"
					+ " slot_number int," + " kills int,"
					+ " deaths int," + " assists int," + " item_0 int references items(item_id),"
					+ " item_1 int references items(item_id)," + " item_2 int references items(item_id),"
					+ " item_3 int references items(item_id)," + " item_4 int references items(item_id),"
					+ " item_5 int references items(item_id)," + " hero_damage bigint," + " percent_damage int,"
					+ " tower_damage bigint," + " percent_tower int," + " last_hits int," + " gold bigint,"
					+ " percent_kills int," + " unique (match_id, account_id))");// )
			// System.out.println("Базa всех слотов игроков создана");
		}
		catch (Exception ex) {
			// выводим наиболее значимые сообщения
			Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);}
		finally {
			try {
				// закрываем соединение
				connect.close();
			} catch (Exception ex) {
				// выводим наиболее значимые сообщения
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);}
		}
	}

	// добавление записей в таблицу аккаунтов
	public void updateAccounts(List<MatchDetailPlayer> List) {
		// создаём пустой коннект к базе
		Connection connect = null;
		try {
			// Загружаем драйвер
			Class.forName("org.postgresql.Driver");
			// System.out.println("Драйвер подключен");
			// Создаём соединение
			connect = DriverManager.getConnection(this.url, this.name, this.password);
			// System.out.println("Соединение установлено");
			PreparedStatement statement = null, st = null;
			// подготавливаем запрос на добавление записи с параметром
			statement = connect.prepareStatement("insert into accounts (account_id) values (?)");
			st = connect.prepareStatement("select * from accounts where account_id = ?");
			long id;
			ResultSet rs = null;
			for (MatchDetailPlayer player : List) {
				id = player.getAccountId();
				st.setLong(1, id);
				// если такого аккаунта ещё нет в таблице то добавляем запрос в
				// пакет
				rs = st.executeQuery();
				if (!rs.next()) {
					statement.setLong(1, id);
					statement.addBatch();}
			}
			statement.executeBatch();
		} catch (Exception ex) {
			// выводим наиболее значимые сообщения
			Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				// закрываем соединение
				connect.close();
			} catch (Exception ex) {
				// выводим наиболее значимые сообщения
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);}
		}
	}

	// добавление записей в таблицу вещей
	public void updateItems(List<Item> List) {
		// создаём пустой коннект к базе
		Connection connect = null;
		try {
			// Загружаем драйвер
			Class.forName("org.postgresql.Driver");
			// System.out.println("Драйвер подключен");
			// Создаём соединение
			connect = DriverManager.getConnection(this.url, this.name, this.password);
			// System.out.println("Соединение установлено");
			PreparedStatement statement = null, st = null;
			// подготавливаем запрос на добавление записи с параметром
			statement = connect.prepareStatement("insert into items (item_id) values (?)");
			st = connect.prepareStatement("select * from items where item_id = ?");
			int id;
			ResultSet rs = null;
			for (Item item : List) {
				id = item.getId();
				st.setLong(1, id);
				// если такого предмета ещё нет в таблице то добавляем запрос в
				// пакет
				rs = st.executeQuery();
				if (!rs.next()) {
					statement.setInt(1, id);
					statement.executeUpdate();
				}
			}
		} catch (Exception ex) {
			// выводим наиболее значимые сообщения
			Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				// закрываем соединение
				connect.close();
			} catch (Exception ex) {
				// выводим наиболее значимые сообщения
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	// добавление записей в таблицу героев
	public void updateHeroes(List<MatchDetailPlayer> List) {
		// создаём пустой коннект к базе
		Connection connect = null;
		try {
			// Загружаем драйвер
			Class.forName("org.postgresql.Driver");
			// System.out.println("Драйвер подключен");
			// Создаём соединение
			connect = DriverManager.getConnection(this.url, this.name, this.password);
			// System.out.println("Соединение установлено");
			PreparedStatement statement = null, st = null;
			// подготавливаем запрос на добавление записи с параметром
			statement = connect.prepareStatement("insert into heroes (hero_id) values (?)");
			st = connect.prepareStatement("select * from heroes where hero_id = ?");			
			int id;
			ResultSet rs = null;
			for (MatchDetailPlayer player : List) {
				id = player.getHeroId();
				st.setLong(1, id);
				// если такого героя ещё нет в таблице то добавляем запрос в
				// пакет
				rs = st.executeQuery();
				if (!rs.next()) {
					statement.setInt(1, id);
					statement.addBatch();
				}
			}
			statement.executeBatch();
		} catch (Exception ex) {
			// выводим наиболее значимые сообщения
			Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				// закрываем соединение
				connect.close();
			} catch (Exception ex) {
				// выводим наиболее значимые сообщения
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	// добавление записей в таблицу матчей
	public void updateMatchesAndSlots(MatchDetail match) {
		// создаём пустой коннект к базе
		Connection connect = null;
		try {
			// Загружаем драйвер
			Class.forName("org.postgresql.Driver");
			// System.out.println("Драйвер подключен");
			// Создаём соединение
			connect = DriverManager.getConnection(this.url, this.name, this.password);
			// System.out.println("Соединение установлено");
			PreparedStatement statement = null, statement1 = null, st = null;
			// подготавливаем запрос на добавление записи с параметром
			statement = connect.prepareStatement(
					"insert into matches"
					+ " (match_id,"	+ " duration,"
					+ " game_mode,"	+ " radiant_win,"
					+ "top_hero_damage," + " top_last_hits,"
					+ " top_killer,"+ " top_tower_damage)" + " values (?,?,?,?,?,?,?,?)");
			statement1 = connect.prepareStatement("insert into all_player_slots (match_id, hero_id, account_id,"
					+ "slot_number," + "kills, deaths, assists, item_0, item_1, item_2, item_3, item_4, item_5,"
					+ "hero_damage, percent_damage, tower_damage, percent_tower ," + " last_hits, gold, percent_kills)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			long id = match.getMatchId();
			long tmpKill = 0, tmpLast = 0, tmpTower = 0, tmpHeroDamage = 0, tmp = 1, tmpKill1 = 0, 
					tmpTower1 = 0, tmpHeroDamage1 = 0, topHero = 0, topTower = 0, topLast = 0, topKiller = 0,
					topHero1 = 0, topTower1 = 0, topKiller1 = 0, radiantHero = 0, direHero = 0, radiantTower = 1,
					direTower = 1;
			int radiantKill = 0, direKill = 0;

			List<MatchDetailPlayer> players = match.getPlayers();

			for (MatchDetailPlayer player : players) {
				// считаем общий урон команд по героям и ищем нанёсших больший
				// урон
				tmp = player.getHeroDamageDealt();
				// команда светлых
				if (player.getPlayerSlots() < 5) {
					radiantHero += tmp;
					if (tmpHeroDamage < tmp) {
						topHero = player.getAccountId();
						tmpHeroDamage = tmp;
					}
				}
				// команда тёмных
				if (player.getPlayerSlots() > 127) {
					direHero += tmp;
					if (tmpHeroDamage < tmp) {
						topHero1 = player.getAccountId();
						tmpHeroDamage1 = tmp;
					}
				}
				// ишем кто добил больше крипов
				tmp = player.getLastHits();
				if (tmpLast < tmp) {
					topLast = player.getAccountId();
					tmpLast = tmp;
				}
				// ищем наиболее активных участников убийств в командах и
				// считаем убийства команд
				tmp = player.getKills() + player.getAssists();
				// светлые
				if (player.getPlayerSlots() < 5) {
					radiantKill += player.getKills();
					if (tmpKill < tmp) {
						topKiller = player.getAccountId();
						tmpKill = tmp;
					}
				}
				// тёмные
				if (player.getPlayerSlots() > 127) {
					direKill += player.getKills();
					if (tmpKill < tmp) {
						topKiller1 = player.getAccountId();
						tmpKill1 = tmp;
					}
				}
				// ищем кто больше нанёс урона по строениям в командах и считаем
				// общий урон команд по строениям
				tmp = player.getTowerDamageDealt();
				// светлые
				if (player.getPlayerSlots() < 5) {
					radiantTower += tmp;
					if (tmpTower < tmp) {
						topTower = player.getAccountId();
						tmpTower = tmp;
					}
				}
				// тёмные
				if (player.getPlayerSlots() > 127) {
					direTower += tmp;
					if (tmpTower < tmp) {
						topTower1 = player.getAccountId();
						tmpTower1 = tmp;
					}
				}
			}
			// выясняем кто самый крутой забияка на деревне
			if (tmpHeroDamage / 500 + ((tmpHeroDamage * 100f) / radiantHero) / 2 > tmpHeroDamage1 / 500
					+ ((tmpHeroDamage1 * 100f) / direHero) / 2)
				tmpHeroDamage = topHero;
			else
				tmpHeroDamage = topHero1;
			// выясняем кто первый киллер на деревне
			if (tmpKill + ((tmpKill * 100f) / radiantKill) / 2 > tmpKill1 / 500 + ((tmpKill1 * 100f) / direKill) / 2)
				tmpKill = topKiller;
			else
				tmpKill = topKiller1;
			// выясняем кто лучше долбил по строениям
			if (tmpTower / 100 + ((tmpTower * 100f) / radiantTower) / 2 > tmpTower1 / 500
					+ ((tmpTower1 * 100f) / direTower) / 2)
				tmpTower = topTower;
			else
				tmpTower = topTower1;
			ResultSet rs = null;
			// если такого матча ещё нет в таблице то добавляем запрос в пакет
			st = connect.prepareStatement("select * from matches where match_id = ?");
			st.setLong(1,id);
			rs = st.executeQuery();
			if (!rs.next()) {
				// пишем в таблицу номер матча
				statement.setLong(1, id);
				// пишем в таблицу длительность матча в секундах
				statement.setInt(2, match.getDurationOfMatch());
				// пишем в таблицу тип игры матча
				statement.setInt(3, match.getGameMode().getValue());
				// пишем в таблицу кто победил в матче
				statement.setBoolean(4, match.didRadianWin());
				// пишем в таблицу номер аккаунта игрока который нанёс наибоьшее
				// количество урона и больший процент урона от своей команды по
				// героям
				statement.setLong(5, topHero);
				// пишем в таблицу номер аккаунта игрока который добил больше
				// всего крипов
				statement.setLong(6, topLast);
				// пишем в таблицу номер аккаунта игрока который убил и помог
				// убить больше всех
				statement.setLong(7, topKiller);
				// пишем в таблицу номер аккаунта игрока который нанёс наибоьшее
				// количество урона и больший процент урона от своей команды по
				// строениям
				statement.setLong(8, topTower);
				statement.executeUpdate();
			}
			// добавляем слоты в таблицу, если их ещё нет там
			int i;
			for (MatchDetailPlayer player : players) {
				id = player.getAccountId();
				i = 1;
				// если такого аккаунта ещё нет в таблице то добавляем запрос в
				// пакет
				st = connect.prepareStatement("select * from all_player_slots where match_id = ? and account_id = ?");
				st.setLong(1,match.getMatchId());
				st.setLong(2,id);
				rs = st.executeQuery();
				if (!rs.next()) {
					// пишем в таблицу номер матча для этого слота
					statement1.setLong(i++, match.getMatchId());
					// пишем в таблицу номер героя в слоте
					statement1.setInt(i++, player.getHeroId());
					// пишем в таблицу номер аккауньа игрока
					statement1.setLong(i++, id);
					// пишем в таблицу номер слота
					statement1.setInt(i++, player.getPlayerSlots());
					// пишем в таблицу количество убийств игрока
					statement1.setInt(i++, player.getKills());
					// пишем в таблицу количество смертей игрока
					statement1.setInt(i++, player.getDeaths());
					// пишем в таблицу количество ассистов игрока
					statement1.setInt(i++, player.getAssists());
					// пишем в таблицу все вещи игрока
					for (Item item : player.getItems())
						statement1.setInt(i++, item.getId());
					// пишем в таблицу сколько урона нанёс игрок другим игрокам
					statement1.setLong(i++, player.getHeroDamageDealt());
					// пишем в таблицу процент нанес ённого игроком урона от
					// командного по героям
					if (player.getPlayerSlots() < 5)
						statement1.setInt(i++, (int) ((player.getHeroDamageDealt() * 100f) / radiantHero));
					else
						statement1.setInt(i++, (int) ((player.getHeroDamageDealt() * 100f) / direHero));
					// пишем в таблицу количетво нанессённого игроком урона по
					// строениям
					statement1.setLong(i++, player.getTowerDamageDealt());
					// пишем в таблицу процент нанесённого игроком урона от
					// командного по строениям
					if (player.getPlayerSlots() < 5)
						statement1.setInt(i++, (int) ((player.getTowerDamageDealt() * 100f) / radiantTower));
					else
						statement1.setInt(i++, (int) ((player.getTowerDamageDealt() * 100f) / direTower));
					// пишем в таблицу количество добитых крипов
					statement1.setInt(i++, player.getLastHits());
					// пишем в таблицу количество заработанного золота
					statement1.setInt(i++, player.getGoldPerMinute() * (match.getDurationOfMatch() / 60));
					// пишем в таблицу процент участия игрока в убийствах
					if (player.getPlayerSlots() < 5)
						statement1.setInt(i++,
								(int) (((player.getKills() + player.getAssists()) * 100f) / radiantKill));
					else
						statement1.setInt(i++, (int) (((player.getKills() + player.getAssists()) * 100f) / direKill));
					statement1.addBatch();
				}
				statement1.executeBatch();
			}
		} catch (Exception ex) {
			// выводим наиболее значимые сообщения
			Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				// закрываем соединение
				connect.close();
			} catch (Exception ex) {
				// выводим наиболее значимые сообщения
				Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/*
	 * //добавление записей в таблицу слотов public void
	 * updatePlayerSlots(List<MatchDetailPlayer> List, long match_id){
	 * 
	 * //создаём пустой коннект к базе Connection connect = null;
	 * 
	 * try{ //Загружаем драйвер Class.forName("org.postgresql.Driver");
	 * //System.out.println("Драйвер подключен");
	 * 
	 * //Создаём соединение connect = DriverManager.getConnection(this.url,
	 * this.name, this.password); //System.out.println("Соединение установлено"
	 * );
	 * 
	 * PreparedStatement statement = null; java.sql.Statement st = null;
	 * 
	 * //подготавливаем запрос на добавление записи с параметром statement =
	 * connect.prepareStatement(
	 * "insert into all_player_slots (match_id, hero_id, account_id," +
	 * "kills, deaths, assists, item_0, item_1, item_2, item_3, item_4, item_5,"
	 * + "hero_damage, tower_damage, last_hits)" +
	 * " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); st=connect.createStatement();
	 * 
	 * long id; int i;
	 * 
	 * ResultSet rs = null;
	 * 
	 * for(MatchDetailPlayer player : List) {
	 * 
	 * id = player.getAccountId();
	 * 
	 * i=1;
	 * 
	 * //если такого аккаунта ещё нет в таблице то добавляем запрос в пакет rs =
	 * st.executeQuery("select * from all_player_slots where match_id = " +
	 * match_id + " and account_id = " + id);
	 * 
	 * if (!rs.next()){ statement.setLong(i++, id); statement.setInt(i++,
	 * player.getHeroId()); statement.setLong(i++, id); statement.setInt(i++,
	 * player.getKills()); statement.setInt(i++, player.getDeaths());
	 * statement.setInt(i++, player.getAssists());
	 * 
	 * for(Item item:player.getItems()) statement.setInt(i++, item.getId());
	 * 
	 * statement.setLong(i++, player.getHeroDamageDealt());
	 * statement.setLong(i++, player.getTowerDamageDealt());
	 * statement.setInt(i++, player.getLastHits());
	 * 
	 * statement.addBatch(); } } statement.executeBatch();
	 * 
	 * } catch(Exception ex){ //выводим наиболее значимые сообщения
	 * Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex); }
	 * finally{ try{ //закрываем соединение connect.close(); } catch (Exception
	 * ex){ //выводим наиболее значимые сообщения
	 * Logger.getLogger(Leech2DB.class.getName()).log(Level.SEVERE, null, ex); }
	 * }
	 * 
	 * }
	 */

}