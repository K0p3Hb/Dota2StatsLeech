package statsleech;

public class Leech {

	public static void main(String args []) {
		
		
		
		DBInit init = new DBInit("jdbc:postgresql://localhost:5432/Dota2stat","postgres","qwerty");
		init.Initialize();
		
	}
}
