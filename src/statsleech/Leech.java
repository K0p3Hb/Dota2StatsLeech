package statsleech;

import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import db.slice.Heroes;
import de.inkvine.dota2stats.Dota2Stats;
import de.inkvine.dota2stats.impl.Dota2StatsImpl;
import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;
import de.inkvine.dota2stats.exceptions.Dota2StatsAccessException;

public class Leech {
  
  public static void main(String args[]) {
    // создаём подключение к базе
    Leech2DB init = new Leech2DB("jdbc:postgresql://localhost:5432/Dota2stat", "postgres", "qwerty");
    // если база пустая, то создаём структуру таблиц
    // init.initialize();
    // подключение к API с моим ключём
    Dota2Stats stats = new Dota2StatsImpl("D796311CB1B7596E851E183264FAB02A");
    try {
     /*
      MatchHistory history = stats.getMostRecentMatchHistory();
      List<MatchOverview> overviews = history.getMatchOverviews();
      MatchDetail detail = null;
      List<MatchDetailPlayer> pldetail = null;
      for (MatchOverview match : overviews) {
        detail = stats.getMatchDetails(match.getMatchId());
        pldetail = detail.getPlayers();
        init.updateAccounts(pldetail);
        init.updateHeroes(pldetail);
        for (MatchDetailPlayer player : pldetail)
          init.updateItems(player.getItems());
        init.updateMatchesAndSlots(detail);
      }*/
      
      
        MatchDetail detail = stats.getMatchDetails(1762832017L);
        List<MatchDetailPlayer> players = detail.getPlayers();
        
        JdbcConnectionSource connectionSource = null;
        try {
          connectionSource = new JdbcConnectionSource("jdbc:postgresql://localhost:5432/Dota2stat", "postgres", "qwerty");
          Dao<Heroes, Integer> daoHero = DaoManager.createDao(connectionSource, Heroes.class);
          CounterSPRP co = new CounterSPRP();
          Heroes hero = new Heroes();
          for(MatchDetailPlayer player:players){
            hero = daoHero.queryForId(player.getHeroId());
            co.countFightSP(detail, player.getPlayerSlots(), connectionSource, hero.getKiller());
            co.countTowerSP(detail, player.getPlayerSlots(), connectionSource, hero.getDestroyer());
            co.countGoldenSP(detail, player.getPlayerSlots(), connectionSource, hero.getGold(),hero.getCreeper());
            co.countSurvivalSP(detail, player.getPlayerSlots(), connectionSource, hero.getDeath(), hero.getHealer());
          }
          //Heroes hero = new Heroes();
          //hero = daoHero.queryForId(100);
         // System.out.println(hero.getName());
          connectionSource.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        /*init.updateAccounts(players); init.updateHeroes(players); for
        (MatchDetailPlayer player : players)
        init.updateItems(player.getItems());
       init.updateMatchesAndSlots(detail);*/
       
      // init.updatePlayerSlots(players, detail.getMatchId());
    } catch (Dota2StatsAccessException e1) {
      e1.printStackTrace();
    }
  }
}
