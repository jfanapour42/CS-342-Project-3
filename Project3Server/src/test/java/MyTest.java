import static org.junit.jupiter.api.Assertions.*;

//import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	private MorraInfo m;
	private MorraInfo m2;
	private MorraGame game;
	
	@BeforeEach
	void makeDefaultMorraInfo() {
		m = new MorraInfo();
	}
	
	@BeforeEach
	void setPlayerChoices() {
		m2 = new MorraInfo();
		m2.addPlayer("Logan");
		m2.addPlayer("Lily");
		m2.getPlayer("Logan").setGuess(1);
		m2.getPlayer("Logan").setPlay(2);
		m2.getPlayer("Lily").setGuess(3);
		m2.getPlayer("Lily").setPlay(4);
	}
	
	@BeforeEach
	void setUpMorraGame() {
		game = new MorraGame();
		game.stats.addPlayer("Kyle");
		game.stats.addPlayer("Ike");
	}
	
	//
	// MorraInfo Test Cases
	//
	@Test
	void MorraInfoConstructorGetPlayer1Test() {
		assertNull(m.getPlayer1(), "Player 1 should be null from contructor test");
	}
	
	@Test
	void MorraInfoConstructorGetPlayer2Test() {
		assertNull(m.getPlayer2(), "Player 2 should be null from contructor test");
	}
	
	@Test
	void MorraInfoConstructorGetScoreLimitTest() {
		assertEquals(5, m.getScoreLimit(), "Score limit should be 5 from contructor test");
	}
	
	@Test
	void MorraInfoSetScoreLimitTest() {
		m.setScoreLimit(10);
		assertEquals(10, m.getScoreLimit(), "Set score limit test failed.");
	}
	
	@Test
	void MorraInfoSetPlayer1Test() {
		assertEquals(true, m.setPlayer1("John"), "Set player 1 return value test failed");
		assertEquals("John", m.getPlayer1().getName(), "Set player 1 test failed.");
	}
	
	@Test
	void MorraInfoSetPlayer2Test() {
		assertEquals(true, m.setPlayer2("John"), "Set player 2 return value test failed");
		assertEquals("John", m.getPlayer2().getName(), "Set player 2 test failed.");
	}
	
	@Test
	void MorraInfoSetPlayer1Then2Test() {
		assertEquals(true, m.setPlayer1("John"), "Set player 1 return value test failed");
		assertEquals(true, m.setPlayer2("Joe"), "Set player 2 return value test failed");
		assertEquals("John", m.getPlayer1().getName(), "Set player 1 test failed.");
		assertEquals("Joe", m.getPlayer2().getName(), "Set player 2 test failed.");
	}
	
	@Test
	void MorraInfoSetPlayer2Then1Test() {
		assertEquals(true, m.setPlayer2("John"), "Set player 2 return value test failed");
		assertEquals(true, m.setPlayer1("Joe"), "Set player 1 return value test failed");
		assertEquals("John", m.getPlayer2().getName(), "Set player 1 test failed.");
		assertEquals("Joe", m.getPlayer1().getName(), "Set player 2 test failed.");
	}
	
	@Test
	void MorraInfoSetPlayer1Then2WithSameNameTest() {
		assertEquals(true, m.setPlayer1("John"), "Set player 1 return value test failed");
		assertEquals(false, m.setPlayer2("John"), "Set player 2 return value test failed");
		assertEquals("John", m.getPlayer1().getName(), "Set player 1 test failed.");
		assertNull(m.getPlayer2(), "Player 2 should be null. Test failed.");
	}
	
	@Test
	void MorraInfoAddPlayerTest1() {
		assertEquals(true, m.addPlayer("John"), "Add player return value test failed");
		assertEquals(m.getPlayer1(), m.getPlayer("John"), "Add player get player test 1 failed.");
	}
	
	@Test
	void MorraInfoAddPlayerTest2() {
		assertEquals(true, m.addPlayer("John"), "Add player return value test failed");
		assertEquals(true, m.addPlayer("Joe"), "Add player return value test failed");
		assertEquals(m.getPlayer1(), m.getPlayer("John"), "Add player get player test 2 failed.");
		assertEquals(m.getPlayer2(), m.getPlayer("Joe"), "Add player get player test 2 failed.");
	}
	
	@Test
	void MorraInfoAddPlayerSameNameTest() {
		assertEquals(true, m.addPlayer("John"), "Add player return value test failed");
		assertEquals(false, m.addPlayer("John"), "Add player return value test failed");
		assertEquals(m.getPlayer1(), m.getPlayer("John"), "Add player same name test failed.");
		assertNull(m.getPlayer2(), "Add player get player (null) test failed.");
	}
	
	@Test
	void MorraInfoAdd3Player() {
		m.addPlayer("Joe");
		m.addPlayer("John");
		assertEquals(false, m.addPlayer("Jake"), "Add 3rd player should be false");
	}
	
	@Test
	void MorraInfoGetPlayerNonExistentTest() {
		m.addPlayer("John");
		assertNull(m.getPlayer("Joe"), "Get non-existent player test failed");
	}
	
	@Test
	void MorraInfoRemovePlayer1Test() {
		m.addPlayer("John");
		assertEquals(true, m.removePlayer1(),"Remove player 1 return value test failed.");
		assertNull(m.getPlayer1(), "Get removed player 1 test failed");
	}
	
	@Test
	void MorraInfoRemovePlayer2Test() {
		m.addPlayer("John");
		m.addPlayer("Joe");
		assertEquals(true, m.removePlayer2(),"Remove player 2 return value test failed.");
		assertNull(m.getPlayer2(), "Get removed player 2 test failed");
	}
	
	@Test
	void MorraInfoRemovePlayer1ReturnFalseTest() {
		m.addPlayer("John");
		m.removePlayer1();
		assertEquals(false, m.removePlayer1(),"Remove player 1 return value test failed.");
	}
	
	@Test
	void MorraInfoRemovePlayer2ReturnFalseTest() {
		m.addPlayer("John");
		assertEquals(false, m.removePlayer2(),"Remove non existent player 2 return value test failed.");
	}
	
	@Test
	void MorraInfoRemovePlayerTest() {
		m.addPlayer("John");
		assertEquals(true, m.removePlayer("John"),"Remove player return value test failed.");
		assertNull(m.getPlayer("John"),"Remove player null test failed");
	}
	
	@Test
	void MorraInfoRemovePlayerTest2() {
		m.addPlayer("John");
		m.addPlayer("Joe");
		assertEquals(true, m.removePlayer("John"),"Remove player return value test failed.");
		assertNull(m.getPlayer("John"),"Remove player null test failed");
	}
	
	@Test
	void MorraInfoRemoveNonExistentPlayerTest() {
		m.addPlayer("John");
		assertEquals(false, m.removePlayer("Joe"),"Remove non existent player return value test failed.");
	}
	
	@Test
	void MorraInfoResetPlayerChoicesTest() {
		m2.resetPlayerChoices();
		assertEquals(0, m2.getPlayer1().getGuess(), "Player 1 guess reset test failed.");
		assertEquals(0, m2.getPlayer2().getGuess(), "Player 2 guess reset test failed.");
		assertEquals(0, m2.getPlayer1().getPlay(), "Player 1 play reset test failed.");
		assertEquals(0, m2.getPlayer2().getPlay(), "Player 2 play reset test failed.");
	}
	
	@Test
	void MorraInfoHave2PlayersTest() {
		assertEquals(true, m2.have2Players(), "Have 2 players test failed.");
	}
	
	@Test
	void MorraInfoHave2PlayersRemovePlayerTest() {
		m2.removePlayer1();
		assertEquals(false, m2.have2Players(), "Have 2 players remove 1 player test failed.");
	}
	
	@Test
	void MorraInfoHave2PlayersRemove2PlayersTest() {
		m2.removePlayer1();
		m2.removePlayer2();
		assertEquals(false, m2.have2Players(), "Have 2 players remove 2 player test failed.");
	}
	
	@Test
	void MorraInfoArePlayersReadyTest1() {
		assertEquals(true, m2.arePlayersReady(), "Are players ready test 1 failed");
	}
	
	@Test
	void MorraInfoArePlayersReadyTest2() {
		m2.resetPlayerChoices();
		assertEquals(false, m2.arePlayersReady(), "Are players ready test 2 failed");
	}
	
	@Test
	void MorraInfoArePlayersReadyTest3() {
		m2.removePlayer1();
		assertEquals(false, m2.arePlayersReady(), "Are players ready test 3 failed");
	}
	
	@Test
	void MorraInfoArePlayersReadyTest4() {
		m2.removePlayer2();
		assertEquals(false, m2.arePlayersReady(), "Are players ready test 4 failed");
	}
	
	@Test
	void MorraInfoArePlayersReadyTest5() {
		m2.removePlayer1();
		m2.removePlayer2();
		assertEquals(false, m2.arePlayersReady(), "Are players ready test 5 failed");
	}
	
	@Test
	void MorraInfoArePlayersReadyTest6() {
		m2.getPlayer1().resetChoices();
		assertEquals(false, m2.arePlayersReady(), "Are players ready test 6 failed");
	}
	
	@Test
	void MorraInfoArePlayersReadyTest7() {
		m2.getPlayer2().resetChoices();
		assertEquals(false, m2.arePlayersReady(), "Are players ready test 7 failed");
	}
	
	//
	// MorraInfo.Player Test cases
	//
	@Test 
	void playerGetNameTest() {
		MorraInfo.Player p = m2.getPlayer("Logan");
		assertEquals("Logan", p.getName(), "Player name test failed.");
	}
	
	@Test 
	void playerGetScoreTest() {
		MorraInfo.Player p = m2.getPlayer("Logan");
		assertEquals(0, p.getScore(), "Player get score test failed.");
	}
	
	@Test 
	void playerIncrementScoreTest() {
		MorraInfo.Player p = m2.getPlayer("Logan");
		p.incrementScore();
		assertEquals(1, p.getScore(), "Player increment score test failed.");
	}
	
	@Test 
	void playerResetScoreTest() {
		MorraInfo.Player p = m2.getPlayer("Logan");
		p.incrementScore();
		p.resetScore();
		assertEquals(0, p.getScore(), "Player reset score test failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4,5})
	void playerSetGuessTest1(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		assertEquals(true, p.setGuess(val), "Set guess return value test 1 failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 6, 7})
	void playerSetGuessTest2(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		assertEquals(false, p.setGuess(val), "Set guess return value test 2 failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4,5})
	void playerSetPlayTest1(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		assertEquals(true, p.setPlay(val), "Set play return value test 1 failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 6, 7})
	void playerSetPlayTest2(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		assertEquals(false, p.setPlay(val), "Set play return value test 2 failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4,5})
	void playerGetGuessTest1(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		p.setGuess(val);
		assertEquals(val, p.getGuess(), "Get guess return value test 1 failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 6, 7})
	void playerGetGuessTest2(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		p.setGuess(val);
		assertEquals(0, p.getGuess(), "Get guess return value test 2 failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4,5})
	void playerGetPlayTest1(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		p.setPlay(val);
		assertEquals(val, p.getPlay(), "Get play return value test 1 failed.");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 6, 7})
	void playerGetPlayTest2(int val) {
		m.addPlayer("Logan");
		MorraInfo.Player p = m.getPlayer("Logan");
		p.setPlay(val);
		assertEquals(0, p.getPlay(), "Get play return value test 2 failed.");
	}
	
	//
	// MorraGame Tests
	//
	void MorraGamePlay1RoundTest() {
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		p1.setGuess(1);
		p1.setPlay(2);
		p2.setGuess(2);
		p2.setPlay(4);
		assertEquals(game.playRound(), p2, "Play 1 game round test failed.");
	}
	
	@ParameterizedTest
	@CsvSource({"1,5,5,2", "2,3,3,4", "3,1,1,5", "1,2,2,4"})
	void MorraGamePlayMultipleRoundsTest(int p1g, int p1p, int p2g, int p2p) {
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		p1.setGuess(p1g);
		p1.setPlay(p1p);
		p2.setGuess(p2g);
		p2.setPlay(p2p);
		assertEquals(game.playRound(), p2, "Play multiple rounds test failed.");
	}
	
	@ParameterizedTest
	@CsvSource({"5,1,2,5", "3,2,4,3", "1,3,5,1", "2,1,4,2"})
	void MorraGamePlayMultipleRoundsTest2(int p1g, int p1p, int p2g, int p2p) {
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		p1.setGuess(p1g);
		p1.setPlay(p1p);
		p2.setGuess(p2g);
		p2.setPlay(p2p);
		assertEquals(game.playRound(), p1, "Play multiple rounds test 2 failed.");
	}
	
	@ParameterizedTest
	@CsvSource({"5,2,2,5", "3,2,2,3", "1,5,5,1", "2,4,4,2"})
	void MorraGamePlayMultipleRoundsTest3(int p1g, int p1p, int p2g, int p2p) {
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		p1.setGuess(p1g);
		p1.setPlay(p1p);
		p2.setGuess(p2g);
		p2.setPlay(p2p);
		assertNull(game.playRound(), "Play multiple rounds test 3 failed.");
	}
	
	@ParameterizedTest
	@CsvSource({"5,2,1,3", "3,2,4,1", "1,5,3,2", "2,4,5,1"})
	void MorraGamePlayMultipleRoundsTest4(int p1g, int p1p, int p2g, int p2p) {
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		p1.setGuess(p1g);
		p1.setPlay(p1p);
		p2.setGuess(p2g);
		p2.setPlay(p2p);
		assertNull(game.playRound(), "Play multiple rounds test 4 failed.");
	}
	
	@Test
	void MorraGamePlayMultipleRoundsSeeScoreTest() {
		int[][] data = {{1,5,5,2,1},{2,3,3,4,2},{3,1,1,5,3},{1,2,2,4,4}};
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		for (int i = 0; i < data.length; i++) {
			p1.setGuess(data[i][0]);
			p1.setPlay(data[i][1]);
			p2.setGuess(data[i][2]);
			p2.setPlay(data[i][3]);
			game.playRound();
			assertEquals(data[i][4], p2.getScore(), "Play multiple rounds see scores test 1 failed.");
		}
	}
	
	@Test
	void MorraGamePlayMultipleRoundsSeeScoreTest2() {
		int[][] data = {{5,1,2,5,1},{3,2,4,3,2},{1,3,5,1,3},{2,1,4,2,4}};
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		for (int i = 0; i < data.length; i++) {
			p1.setGuess(data[i][0]);
			p1.setPlay(data[i][1]);
			p2.setGuess(data[i][2]);
			p2.setPlay(data[i][3]);
			game.playRound();
			assertEquals(data[i][4], p1.getScore(), "Play multiple rounds see scores test 2 failed.");
		}
	}
	
	@ParameterizedTest
	@CsvSource({"5,2,2,5", "3,2,2,3", "1,5,5,1", "2,4,4,2"})
	void MorraGamePlayMultipleRoundsSeeScoreTest3(int p1g, int p1p, int p2g, int p2p) {
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		p1.setGuess(p1g);
		p1.setPlay(p1p);
		p2.setGuess(p2g);
		p2.setPlay(p2p);
		game.playRound();
		assertEquals(0, p1.getScore(), "Play multiple rounds see scores test 3 failed.");
		assertEquals(0, p2.getScore(), "Play multiple rounds see scores test 3 failed.");
	}
	
	@ParameterizedTest
	@CsvSource({"5,2,1,3", "3,2,4,1", "1,5,3,2", "2,4,5,1"})
	void MorraGamePlayMultipleRoundsSeeScoreTest4(int p1g, int p1p, int p2g, int p2p) {
		MorraInfo.Player p1 = game.stats.getPlayer("Kyle");
		MorraInfo.Player p2 = game.stats.getPlayer("Ike");
		p1.setGuess(p1g);
		p1.setPlay(p1p);
		p2.setGuess(p2g);
		p2.setPlay(p2p);
		game.playRound();
		assertEquals(0, p1.getScore(), "Play multiple rounds see scores test 4 failed.");
		assertEquals(0, p2.getScore(), "Play multiple rounds see scores test 4 failed.");
	}
}
