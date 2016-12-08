package statsleech;

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
  public void countFightSP(MatchDetail match, int slot, JdbcConnectionSource connect, float killer) {
    float spDamage = 0, spActivity = 0, sp = 0;
    float currentDamage = 0, currentActivity = 0, allDamage = 0, allActivity = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      allDamage += (float) player.getHeroDamageDealt() / (match.getDurationOfMatch() / 60f);
      allActivity += player.getKills();
      if (player.getPlayerSlots() == slot) {
        currentDamage = (float) player.getHeroDamageDealt() / (match.getDurationOfMatch() / 60f);
        currentActivity = player.getKills();
      }
    }
    spDamage = (float) ((((currentDamage * 100f) / allDamage) - ((killer * 100) / allDamage)) * 0.3) + 3;
    spActivity = (float) (((currentActivity * 100f) / allActivity) * 0.25);
    sp = spDamage + spActivity;
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10;
    System.out.println(">----------------------------<\nslot number = " + slot + "\n" + "Fight SP = Damage SP + kill/assist SP\n" + sp + "  =  "
        + spDamage + "  +  " + spActivity +"\n---------------------------");
  }
  
  // функция считает СП, полученные за урон строениям
  public void countTowerSP(MatchDetail match, int slot, JdbcConnectionSource connect, float tower) {
    float sp = 0, current_damage = 0, allDamage = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      allDamage += player.getTowerDamageDealt() / (match.getDurationOfMatch() / 60f);
      if (player.getPlayerSlots() == slot)
        current_damage = player.getTowerDamageDealt() / (match.getDurationOfMatch() / 60f);
    }
    sp = (float) ((((current_damage * 100f) / allDamage) - ((tower * 100f) / allDamage)) * 0.5 + 5);
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10;
    System.out.println("Tower SP = " + sp+"\n---------------------------");
  }
  
  // считает СП, полученные за добычу золота и добивание крипов
  public void countGoldenSP(MatchDetail match, int slot, JdbcConnectionSource connect, float gold, float creeper) {
    float sp = 0, spGold = 0, spCreep = 0, allGold = 0, allCreeps = 0, currentGold = 0, currentCreeps = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      allCreeps += player.getLastHits() / (match.getDurationOfMatch() / 60f);
      allGold += player.getGoldPerMinute();
      if (player.getPlayerSlots() == slot) {
        currentCreeps = player.getLastHits() / (match.getDurationOfMatch() / 60f);
        currentGold = player.getGoldPerMinute();
      }
    }
    spGold = (float) ((((currentGold * 100f) / allGold) - ((gold * 100f) / allGold)) * 0.2 + 2);
    if (spCreep < 0)
      spCreep = (float) 0.01;
    spCreep = (float) ((((currentCreeps * 100f) / allCreeps)
        - (((creeper / (match.getDurationOfMatch() / 60f)) * 100f) / allCreeps)) * 0.3 + 3);
    if (spGold < 0)
      spGold = 0.01f;
    sp = spCreep + spGold;
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10f;
    System.out.println("Golden SP = Gold SP + Creeps SP\n" + sp + "  =  " + spGold
        + "  +  " + spCreep+"\n---------------------------");
  }
  
  // считает СП, полученные за выживаемость - меньше смертей, больше отхила
  public void countSurvivalSP(MatchDetail match, int slot, JdbcConnectionSource connect, float deaths, float heal){
    float sp = 0, spHeal = 0, spDeath = 0, allDeath = 0, allHeal = 0, currentDeath = 0, currentHeal = 0;
    for (MatchDetailPlayer player : match.getPlayers()) {
      allDeath += player.getDeaths()/ (match.getDurationOfMatch() / 60f);
      allHeal += player.getHeroDamageHealt()/ (match.getDurationOfMatch() / 60f);
      if(player.getPlayerSlots()==slot){
        currentDeath = player.getDeaths()/ (match.getDurationOfMatch() / 60f);
        currentHeal = player.getHeroDamageHealt()/ (match.getDurationOfMatch() / 60f);
      }
    }
    spDeath = (float) (((((deaths/(match.getDurationOfMatch() / 60f))*100f)/allDeath)-((currentDeath*100f)/allDeath))*0.3 +3);
    if(spDeath<0)
      spDeath=0.01f;
    spHeal = (float) ((((currentHeal*100f)/allHeal)-((heal*100f)/allHeal))*0.2 +2);
    if(spHeal<0)
      spHeal=0.01f;
    sp = spHeal+spDeath;
    if (sp < 0)
      sp = (float) 0.01;
    if (sp > 10)
      sp = 10f;
    System.out.println("Survival SP = Death SP + Heal SP\n" + sp + "  =  " + spDeath
        + "  +  " + spHeal +"\n>----------------------------<");
  }
}
