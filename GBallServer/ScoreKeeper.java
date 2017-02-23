package GBallServer;

import java.awt.Graphics;

import Shared.Const;

public class ScoreKeeper {
	private static class ScoreKeeperSingletonHolder {
		public static final ScoreKeeper instance = new ScoreKeeper();
	}

	public static ScoreKeeper getInstance() {
		return ScoreKeeperSingletonHolder.instance;
	}

	private int m_team1Score;
	private int m_team2Score;

	public void changeScores(int deltaTeam1, int deltaTeam2) {
		m_team1Score += deltaTeam1;
		m_team2Score += deltaTeam2;
	}

	private ScoreKeeper() {
		m_team1Score = 0;
		m_team2Score = 0;
	}
}