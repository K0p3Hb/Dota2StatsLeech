package de.inkvine.dota2stats.domain;

public enum GameMode {

	        None(0),All_Pick(1), Captains_Mode(2), Random_Draft(3), Single_Draft(4), All_Random(5),
			INTRO_DEATH(6), The_Diretide(7), Reverse_Captains_Mode(8), Greeviling(9),
			Tutorial(10), Mid_Only(11), Least_Played(12), New_PlayerPool(13),
			Compendium_Matchmaking(14), Co_op_vs_Bots(15),Captains_Draft(16),test4(17),
			Ability_Draft(18),test6(19),All_Random_Deathmatch(20), ONEvONE_Mid_Only(21),
			Ranked_Matchmaking(22),test10(23),test11(24),test12(25),test13(26);

	private final int value;

	private GameMode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public GameMode getType(int value) {

		return GameMode.values()[value];

	}

}