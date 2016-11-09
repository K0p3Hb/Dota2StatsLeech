package statsleech;

public class Leech {

	public static void main(String args []) {
		
		
		//создаём подключение к базе
		Leech2DB init = new Leech2DB("jdbc:postgresql://localhost:5432/Dota2stat","postgres","qwerty");
		
		//в случае, если база пустая, создаём структуру таблиц
		//init.Initialize();
		
		
	}
}
