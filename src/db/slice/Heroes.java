package db.slice;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "heroes")
public class Heroes {
  public static final String ID_FIELD_NAME = "hero_id";
  public static final String NAME_FIELD_NAME = "hero_name";
  public static final String IMAGE_FIELD_NAME = "image_url";
  public static final String KILER_FIELD_NAME = "hero_damage";
  public static final String DESTROYER_FIELD_NAME = "tower_damage";
  public static final String CREEPER_FIELD_NAME = "last_hits";
  public static final String GOLD_FIELD_NAME = "gold";
  public static final String DEATH_FIELD_NAME = "deaths";
  public static final String HEALER_FIELD_NAME = "hero_heal";

  
  @DatabaseField(columnName = ID_FIELD_NAME, id = true)
  private int id;
  @DatabaseField(columnName = NAME_FIELD_NAME)
  private String name;
  @DatabaseField(columnName = IMAGE_FIELD_NAME)
  private String url;
  @DatabaseField(columnName = KILER_FIELD_NAME, canBeNull = false)
  private float killer;
  @DatabaseField(columnName = DESTROYER_FIELD_NAME, canBeNull = false)
  private float destroyer;
  @DatabaseField(columnName = CREEPER_FIELD_NAME, canBeNull = false)
  private float creeper;
  @DatabaseField(columnName = GOLD_FIELD_NAME, canBeNull = false)
  private float gold;
  @DatabaseField(columnName = DEATH_FIELD_NAME, canBeNull = false)
  private float death;
  @DatabaseField(columnName = HEALER_FIELD_NAME, canBeNull = false)
  private float healer;
  
  public Heroes(){}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public float getKiller() {
    return killer;
  }

  public void setKiller(float killer) {
    this.killer = killer;
  }

  public float getDestroyer() {
    return destroyer;
  }

  public void setDestroyer(float destroyer) {
    this.destroyer = destroyer;
  }

  public float getCreeper() {
    return creeper;
  }

  public void setCreeper(float creeper) {
    this.creeper = creeper;
  }

  public float getGold() {
    return gold;
  }

  public void setGold(float gold) {
    this.gold = gold;
  }

  public float getDeath() {
    return death;
  }

  public void setDeath(float death) {
    this.death = death;
  }

  public float getHealer() {
    return healer;
  }

  public void setHealer(float healer) {
    this.healer = healer;
  }
  
 
}
