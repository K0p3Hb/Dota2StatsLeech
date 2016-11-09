package de.inkvine.dota2stats.domain.matchdetail.impl;

import de.inkvine.dota2stats.domain.matchdetail.Item;

public class ItemImpl implements Item{

	private int item_id;
	
	public ItemImpl (int id){
		item_id = id;
	}
	
	public ItemImpl (){
		item_id = (Integer) null;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("" + item_id);// + "\n");
		return sb.toString();
	}
	
	public int getId() {
		
		return item_id;
	}
}
