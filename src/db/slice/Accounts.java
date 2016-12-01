package db.slice;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable
public class Accounts {
  private static final String ACCOUNT_ID_FIELD_NAME = "account_id";
  private static final String ACCOUNT_NAME_FIELD_NAME = "account_name";
  
  @DatabaseField(columnName = ACCOUNT_ID_FIELD_NAME, id = true)
  private long id;
  @DatabaseField(columnName = ACCOUNT_NAME_FIELD_NAME)
  private String name;

  public Accounts(){}
  
  public void setID(long i){
    id = i;}
  public void setName(String n){
    name = n;}
  
  public long getID(){
    return id;}
  public String getName(){
    return name;}
}
