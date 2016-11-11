package statsleech;

import de.inkvine.dota2stats.Dota2Stats;
import de.inkvine.dota2stats.impl.Dota2StatsImpl;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;

public class Leech {

	public static void main(String args []) {
		
		
		//создаём подключение к базе
		Leech2DB init = new Leech2DB("jdbc:postgresql://localhost:5432/Dota2stat","postgres","qwerty");
		
		//в случае, если база пустая, создаём структуру таблиц
		//init.Initialize();
		
		//подключение к АПИ с моим ключём
		Dota2Stats stats = new Dota2StatsImpl("D796311CB1B7596E851E183264FAB02A");
		
		
		
		
	}
}
