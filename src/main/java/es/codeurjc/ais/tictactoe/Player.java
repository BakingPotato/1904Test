package es.codeurjc.ais.tictactoe;

public class Player {

	private String label;
	private String name;
	private int id;
	private PlayerStats stats;

	public Player(int id, String label, String name) {
		this.id = id;
		this.label = label;
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setStats(PlayerStats stats) {
		this.stats = stats;
	}
}
