package statsleech;

import java.sql.SQLException;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import db.slice.*;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;

public class CounterSPRP {
  
  public static void main(String[] args) {
    ConnectionSource connectionSource = null;
    try {
      connectionSource = new JdbcConnectionSource("jdbc:postgresql://localhost:5432/Dota2stat", "postgres", "qwerty");
      Dao<Heroes, Integer> daoHero = DaoManager.createDao(connectionSource, Heroes.class);
      Heroes hero = new Heroes();
      hero = daoHero.queryForId(100);
      System.out.println(hero.getName());
      connectionSource.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  // функция считает СП для выбранного слота, полученные за урон героям и
  // процент участия в командных убийствах
  public void countFightSP(MatchDetail match, int slot, JdbcConnectionSource connect, float killer, Dao<Heroes, Integer> daoHero ) {
    float spDamage = 0, spActivity = 0, sp = 0;
    float currentDamage = 0, currentActivity = 0, allDamage = 0, allActivity = 0, allIdealDamage = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      if (slot < 5 && player.getPlayerSlots() < 5 || slot > 127 && player.getPlayerSlots() > 127) {
        allDamage += (float) player.getHeroDamageDealt() / (match.getDurationOfMatch() / 60f);
        allActivity += player.getKills();
        try {
          allIdealDamage+=daoHero.queryForId(player.getHeroId()).getKiller();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if (player.getPlayerSlots() == slot) {
          currentDamage = (float) player.getHeroDamageDealt() / (match.getDurationOfMatch() / 60f);
          currentActivity = player.getKills() + player.getAssists();
        }
      }
    }
    spDamage = (float) ((((currentDamage * 100f) / allDamage) - ((killer * 100f) / allIdealDamage)) * 0.3);
    spActivity = (float) (((currentActivity * 100f) / allActivity) * 0.025);
    sp = spDamage + spActivity + 5;
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10f;
    System.out.println(
        ">----------------------------<\nslot number = " + slot + "\n" + "Fight SP =5SP + Damage SP + kill/assist SP\n" + sp
            + "  =  5 + " + spDamage + "  +  " + spActivity + "\n---------------------------");
  }
  
  // функция считает СП, полученные за урон строениям
  public void countTowerSP(MatchDetail match, int slot, JdbcConnectionSource connect, float tower, Dao<Heroes, Integer> daoHero ) {
    float sp = 0, current_damage = 0, allDamage = 0, allIdealDamage = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      if (slot < 5 && player.getPlayerSlots() < 5 || slot > 127 && player.getPlayerSlots() > 127) {
        allDamage += player.getTowerDamageDealt() / (match.getDurationOfMatch() / 60f);
        try {
          allIdealDamage += daoHero.queryForId(player.getHeroId()).getDestroyer();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if (player.getPlayerSlots() == slot)
          current_damage = player.getTowerDamageDealt() / (match.getDurationOfMatch() / 60f);
      }
    }
    sp = (float) ((((current_damage * 100f) / allDamage) - ((tower * 100f) / allIdealDamage)) * 0.5);
    sp+=5;
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10;
    System.out.println("Tower SP = 5 + " + sp + "\n---------------------------");
  }
  
  // считает СП, полученные за добычу золота и добивание крипов
  public void countGoldenSP(MatchDetail match, int slot, JdbcConnectionSource connect, float gold, float creeper, Dao<Heroes, Integer> daoHero ) {
    float sp = 0, spGold = 0, spCreep = 0, allGold = 0, allCreeps = 0, currentGold = 0, currentCreeps = 0;
    float allIdealGold = 0, allIdealCreeps = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      if (slot < 5 && player.getPlayerSlots() < 5 || slot > 127 && player.getPlayerSlots() > 127) {
        allCreeps += player.getLastHits() / (match.getDurationOfMatch() / 60f);
        allGold += player.getGoldPerMinute();
        try {
          allIdealCreeps += daoHero.queryForId(player.getHeroId()).getCreeper();
          allIdealGold += daoHero.queryForId(player.getHeroId()).getGold();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if (player.getPlayerSlots() == slot) {
          currentCreeps = player.getLastHits() / (match.getDurationOfMatch() / 60f);
          currentGold = player.getGoldPerMinute();
        }
      }
    }
    spGold = (float) ((((currentGold * 100f) / allGold) - ((gold * 100f) / allIdealGold)) * 0.2);
    spCreep = (float) ((((currentCreeps*100f)/allCreeps) -((creeper*100f)/allIdealCreeps))*0.3);
    sp = spCreep + spGold + 5;
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10f;
    System.out.println("Golden SP = 5SP + Gold SP + Creeps SP\n" + sp + "  =  5 + " + spGold + "  +  " + spCreep
        + "\n---------------------------");
  }
  
  // считает СП, полученные за выживаемость - меньше смертей, больше отхила
  public void countSurvivalSP(MatchDetail match, int slot, JdbcConnectionSource connect, float deaths, float heal, Dao<Heroes, Integer> daoHero ) {
    float sp = 0, spHeal = 0, spDeath = 0, allDeath = 0, allHeal = 0, currentDeath = 0, currentHeal = 0;
    float allIdealDeath = 0, allIdealHeal = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      if (slot < 5 && player.getPlayerSlots() < 5 || slot > 127 && player.getPlayerSlots() > 127) {
        allDeath += player.getDeaths();
        allHeal += player.getHeroDamageHealt() / (match.getDurationOfMatch() / 60f);
        try{
          allIdealDeath += daoHero.queryForId(player.getHeroId()).getDeath();
          allIdealHeal += daoHero.queryForId(player.getHeroId()).getHealer();
        }catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if (player.getPlayerSlots() == slot) {
          currentDeath = player.getDeaths();
          currentHeal = player.getHeroDamageHealt() / (match.getDurationOfMatch() / 60f);
        }
      }
    }
    spDeath = (float) ((((deaths  * 100f) / allIdealDeath)- ((currentDeath * 100f) / allDeath)) * 0.3);
    spHeal =(float) ((((currentHeal*100f)/allHeal)-((heal*100f)/allIdealHeal))*0.2) ;
    sp = spHeal + spDeath + 5;
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10f;
    System.out.println("Survival SP = 5SP + Death SP + Heal SP\n" + sp + "  =  5 + " + spDeath + "  +  " + spHeal
        + "\n>----------------------------<");
  }
}
