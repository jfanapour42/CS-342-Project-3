import java.io.Serializable;

public class MorraInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Player p1;
	private Player p2;
	private int scoreLimit = 5;
	
	public MorraInfo() {
		p1 = null;
		p2 = null;
		//scoreLimit = 0;
	}
	
	public void setScoreLimit(int score) {
		scoreLimit = score;
	}
	
	public int getScoreLimit() {
		return scoreLimit;
	}
	
	public boolean setPlayer1(String name) {
		if (p2 == null || !p2.getName().equals(name)) {
			p1 = new Player(name);
			return true;
		}
		return false;
	}
	
	public Player getPlayer1() {
		return p1;
	}
	
	public boolean setPlayer2(String name) {
		if (p1 == null || !p1.getName().equals(name)) {
			p2 = new Player(name);
			return true;
		}
		return false;
	}
	
	public Player getPlayer2() {
		return p2;
	}
	
	public boolean addPlayer(String name) {
		if (!have2Players()) {
			if (p1 == null && (p2 == null || !p2.getName().equals(name))) {
				p1 = new Player(name);
				return true;
			} else if (p2 == null && (!p1.getName().equals(name))) {
				p2 = new Player(name);
				return true;
			}
		}
		return false;
	}
	
	public Player getPlayer(String name) {
		if (p1 != null && p1.getName().equals(name)) {
			return p1;
		} else if (p2 != null && p2.getName().equals(name)) {
			return p2;
		}
		return null;
	}
	
	public boolean removePlayer1() {
		if (p1 != null) {
			p1 = null;
			return true;
		}
		return false;
	}
	
	public boolean removePlayer2() {
		if (p2 != null) {
			p2 = null;
			return true;
		}
		return false;
	}
	
	public boolean removePlayer(String name) {
		if (p1 != null && p1.getName().equals(name)) {
			p1 = null;
			return true;
		} else if (p2 != null && p2.getName().equals(name)) {
			p2 = null;
			return true;
		}
		return false;
	}
	
	public void resetPlayerChoices() {
		if (p1 != null) {p1.resetChoices();}
		if (p2 != null) {p2.resetChoices();}
	}
	
	public boolean have2Players() {
		return p1 != null && p2 != null;
	}
	
	public boolean arePlayersReady() {
		if (p1 != null && p2 != null) {
			return p1.isReady() && p2.isReady();
		}
		return false;
	}
	
	
	public class Player implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		private boolean isWinner;
		private int score;
		private int play;
		private int guess;
		
		public Player(String name) {
			this.name = name;
			score = 0;
			play = 0;
			guess = 0;
		}
		
		public String getName() {
			return name;
		}
		
		public void incrementScore() {
			score++;
		}
		
		public void resetScore() {
			score = 0;
		}
		
		public int getScore() {
			return score;
		}
		
		public boolean setGuess(int g) {
			if(g > 0 && g <= 5) {
				guess = g;
				return true;
			}
			return false;
		}
		
		public int getGuess() {
			return guess;
		}
		
		public boolean setPlay(int p) {
			if(p > 0 && p <= 5) {
				play = p;
				return true;
			}
			return false;
		}
		
		public int getPlay() {
			return play;
		}
		
		public void resetChoices() {
			guess = 0;
			play = 0;
		}
		
		public boolean isReady() {
			return guess != 0 && play != 0;
		}
		
		public boolean isWinner() {
			return isWinner;
		}
		
		public void setWinner(boolean w) {
			isWinner = w;
		}
	}
}