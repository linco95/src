package gball.server;

public class ScoreKeeper {
	private static class ScoreKeeperSingletonHolder {
		public static final ScoreKeeper instance = new ScoreKeeper();
	}

	public static ScoreKeeper getInstance() {
		return ScoreKeeperSingletonHolder.instance;
	}

	private int[] m_scores;

	public void changeScores(int deltaTeam1, int deltaTeam2) {
		m_scores[0] += deltaTeam1;
		m_scores[1] += deltaTeam2;
	}

	private ScoreKeeper() {
		m_scores = new int[2];
		m_scores[0] = m_scores[1] = 0;
	}

	public int[] getScores() {
		return m_scores;
	}
}