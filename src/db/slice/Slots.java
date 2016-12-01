package db.slice;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "all_player_slots")
public class Slots {
  private static final String MATCH_FIELD_MAME = "match_id";
  private static final String HERO_FIELD_MAME = "hero_id";
  private static final String ACCOUNT_FIELD_MAME = "account_id";
  private static final String NUMBER_FIELD_MAME = "slot_number";
  private static final String KILLS_FIELD_MAME = "kills";
  private static final String DEATH_FIELD_MAME = "deaths";
  private static final String ASSIST_FIELD_MAME = "assists";
  private static final String ITEM0_FIELD_MAME = "item_0";
  private static final String ITEM1_FIELD_MAME = "item_1";
  private static final String ITEM2_FIELD_MAME = "item_2";
  private static final String ITEM3_FIELD_MAME = "item_3";
  private static final String ITEM4_FIELD_MAME = "item_4";
  private static final String ITEM5_FIELD_MAME = "item_5";
  private static final String DAMAGE_FIELD_MAME = "hero_damage";
  private static final String DAMAGER_FIELD_MAME = "percent_damage";
  private static final String DESTROY_FIELD_MAME = "tower_damage";
  private static final String DESTROYER_FIELD_MAME = "percent_tower";
  private static final String CREEPS_FIELD_MAME = "last_hits";
  private static final String GOLD_FIELD_MAME = "gold";
  private static final String XP_FIELD_MAME = "xp";
  private static final String KILLER_FIELD_MAME = "percent_kills";
  
  @DatabaseField(columnName = MATCH_FIELD_MAME,foreign = true, uniqueCombo = true)
  private long match;
  @DatabaseField(columnName = HERO_FIELD_MAME, foreign = true)
  private int hero;
  @DatabaseField(columnName = ACCOUNT_FIELD_MAME,foreign = true, uniqueCombo = true)
  private long account;
  @DatabaseField(columnName = NUMBER_FIELD_MAME)
  private int number;
  @DatabaseField(columnName = KILLS_FIELD_MAME)
  private int kills;
  @DatabaseField(columnName = DEATH_FIELD_MAME)
  private int deaths;
  @DatabaseField(columnName = ASSIST_FIELD_MAME)
  private int assist;
  @DatabaseField(columnName = ITEM0_FIELD_MAME, foreign = true)
  private int item0;
  @DatabaseField(columnName = ITEM1_FIELD_MAME, foreign = true)
  private int item1;
  @DatabaseField(columnName = ITEM2_FIELD_MAME, foreign = true)
  private int item2;
  @DatabaseField(columnName = ITEM3_FIELD_MAME, foreign = true)
  private int item3;
  @DatabaseField(columnName = ITEM4_FIELD_MAME, foreign = true)
  private int item4;
  @DatabaseField(columnName = ITEM5_FIELD_MAME, foreign = true)
  private int item5;
  @DatabaseField(columnName = DAMAGE_FIELD_MAME)
  private long damage;
  @DatabaseField(columnName = DAMAGER_FIELD_MAME)
  private int damager;
  @DatabaseField(columnName = DESTROY_FIELD_MAME)
  private long destroy;
  @DatabaseField(columnName = DESTROYER_FIELD_MAME)
  private int destroyer;
  @DatabaseField(columnName = CREEPS_FIELD_MAME)
  private int creeps;
  @DatabaseField(columnName = GOLD_FIELD_MAME)
  private long gold;
  @DatabaseField(columnName = XP_FIELD_MAME)
  private long xp;
  @DatabaseField(columnName = KILLER_FIELD_MAME)
  private int killer;
  public long getMatch() {
    return match;
  }
  public void setMatch(long match) {
    this.match = match;
  }
  public int getHero() {
    return hero;
  }
  public void setHero(int hero) {
    this.hero = hero;
  }
  public long getAccount() {
    return account;
  }
  public void setAccount(long account) {
    this.account = account;
  }
  public int getNumber() {
    return number;
  }
  public void setNumber(int number) {
    this.number = number;
  }
  public int getKills() {
    return kills;
  }
  public void setKills(int kills) {
    this.kills = kills;
  }
  public int getDeaths() {
    return deaths;
  }
  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }
  public int getAssist() {
    return assist;
  }
  public void setAssist(int assist) {
    this.assist = assist;
  }
  public int getItem0() {
    return item0;
  }
  public void setItem0(int item0) {
    this.item0 = item0;
  }
  public int getItem1() {
    return item1;
  }
  public void setItem1(int item1) {
    this.item1 = item1;
  }
  public int getItem2() {
    return item2;
  }
  public void setItem2(int item2) {
    this.item2 = item2;
  }
  public int getItem3() {
    return item3;
  }
  public void setItem3(int item3) {
    this.item3 = item3;
  }
  public int getItem4() {
    return item4;
  }
  public void setItem4(int item4) {
    this.item4 = item4;
  }
  public int getItem5() {
    return item5;
  }
  public void setItem5(int item5) {
    this.item5 = item5;
  }
  public long getDamage() {
    return damage;
  }
  public void setDamage(long damage) {
    this.damage = damage;
  }
  public int getDamager() {
    return damager;
  }
  public void setDamager(int damager) {
    this.damager = damager;
  }
  public long getDestroy() {
    return destroy;
  }
  public void setDestroy(long destroy) {
    this.destroy = destroy;
  }
  public int getDestroyer() {
    return destroyer;
  }
  public void setDestroyer(int destroyer) {
    this.destroyer = destroyer;
  }
  public int getCreeps() {
    return creeps;
  }
  public void setCreeps(int creeps) {
    this.creeps = creeps;
  }
  public long getGold() {
    return gold;
  }
  public void setGold(long gold) {
    this.gold = gold;
  }
  public long getXp() {
    return xp;
  }
  public void setXp(long xp) {
    this.xp = xp;
  }
  public int getKiller() {
    return killer;
  }
  public void setKiller(int killer) {
    this.killer = killer;
  }
}
