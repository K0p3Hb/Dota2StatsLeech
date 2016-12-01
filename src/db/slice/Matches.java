package db.slice;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable
public class Matches {

  private static final String ID_FIELD_NAME = "match_id";
  private static final String DURATION_FIELD_NAME = "duration";
  private static final String MODE_FIELD_NAME = "game_mode";
  private static final String WIN_FIELD_NAME = "radiant_win";
  private static final String DAMAGER_FIELD_NAME = "top_hero_damage";
  private static final String CREEPER_FIELD_NAME = "top_last_hits";
  private static final String KILLER_FIELD_NAME = "top_kills";
  private static final String DESTROYER_FIELD_NAME = "top_tower_damage";
  
  @DatabaseField(columnName = ID_FIELD_NAME, id = true)
  private long id;
  @DatabaseField(columnName = DURATION_FIELD_NAME)
  private int duration;
  @DatabaseField(columnName = MODE_FIELD_NAME)
  private int mode;
  @DatabaseField(columnName = WIN_FIELD_NAME)
  private boolean win;
  @DatabaseField(columnName = DAMAGER_FIELD_NAME,foreign = true)
  private long damager;
  @DatabaseField(columnName = CREEPER_FIELD_NAME,foreign = true)
  private long creeper;
  @DatabaseField(columnName = KILLER_FIELD_NAME,foreign = true)
  private long killer;
  @DatabaseField(columnName = DESTROYER_FIELD_NAME,foreign = true)
  private long destroyer;
  
  public Matches(){}
  
  public void setID(long i){
    id = i;}
  public void setDuration(int i){
    duration = i;}
  public void setMode(int i){
    mode = i;}
  public void setWin(boolean b){
    win = b;}
  public void setDamager(long i){
    damager = i;}
  public void setCreeper(long i){
    creeper = i;}
  public void setKiller(long i){
    killer = i;}
  public void setDestroyer(long i){
    destroyer = i;}
  public long getID(){
    return id;}
  public int getDuration(){
    return duration;}
  public int getMode(){
    return mode;}
  public boolean getWin(){
    return win;}
  public long getDamager(){
    return damager;}
  public long getCreeper(){
    return creeper;}
  public long getKiller(){
    return killer;}
  public long getDestroyer(){
    return destroyer;}
}
