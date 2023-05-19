public class MorraGame {
	MorraInfo stats;
	
	public MorraGame() {
		stats = new MorraInfo();
	}
	
	//
	// Checks that the game has two players and both players are ready.
	// Then it plays a round of the game, and checks if either player won the game.
	// if so, their "isWinner" field is updated. The player choices are reseted and
	// the winner of the round is then returned.
	//
	public MorraInfo.Player playRound() {
		if (stats.have2Players() && stats.arePlayersReady()) {
			MorraInfo.Player p1 = stats.getPlayer1();
			MorraInfo.Player p2 = stats.getPlayer2();
			boolean p1GuessCorrect = p1.getGuess() == p2.getPlay();
			boolean p2GuessCorrect = p2.getGuess() == p1.getPlay();
			stats.resetPlayerChoices();
			if (p1GuessCorrect && !p2GuessCorrect) {
				p1.incrementScore();
				if (p1.getScore() == stats.getScoreLimit()) {
					p1.setWinner(true);
				}
				return p1;
			} else if (p2GuessCorrect && !p1GuessCorrect) {
				p2.incrementScore();
				if (p2.getScore() == stats.getScoreLimit()) {
					p2.setWinner(true);
				}
				return p2;
			}
		}
		return null;
	}
	
	//
	// Resets both players scores to 0, their "isWinner" tag to false, and their
	// player choices tp 0.
	//
	public void resetGame() {
		MorraInfo.Player p1 = stats.getPlayer1();
		MorraInfo.Player p2 = stats.getPlayer2();
		stats.resetPlayerChoices();
		if (p1 != null) {
			p1.setWinner(false);
			p1.resetScore();
		}
		if (p2 != null) {
			p2.setWinner(false);
			p2.resetScore();
		}
		//stats.setScoreLimit(0);
	}
}
