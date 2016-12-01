package db.slice;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "heroes")
public class Heroes {
  public static final String ID_FIELD_NAME = "hero_id";
  public static final String NAME_FIELD_NAME = "hero_name";
  public static final String IMAGE_FIELD_NAME = "image_url";
  
  @DatabaseField(columnName = ID_FIELD_NAME, id = true)
  private int id;
  @DatabaseField(columnName = NAME_FIELD_NAME)
  private String name;
  @DatabaseField(columnName = IMAGE_FIELD_NAME)
  private String url;
  
  public Heroes(){}
  
  public void setID(int i){
    id=i;}
  public void setName(String n){
    name=n;}
  public void setURL(String u){
    url=u;}
  public int getID(){
    return id;}
  public String getName(){
    return name;}
  public String getURL(){
    return url;}
}
