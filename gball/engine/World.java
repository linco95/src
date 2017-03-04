package gball.engine;

import java.util.ArrayList;
import java.util.List;

import gball.server.StateUpdate;
import gball.shared.Const;
import gball.shared.EntityMeta;
import gball.shared.Vector2D;

public class World {

	// public static final String SERVERIP = "127.0.0.1"; // 'Within' the
	// emulator!
	// public static final int SERVERPORT = 4444;

	private static class WorldSingletonHolder {
		public static final World instance = new World();
	}

	public static World getInstance() {
		return WorldSingletonHolder.instance;
	}

	private double m_lastTime = System.currentTimeMillis();
	private double m_actualFps = 0.0;

	private World() {

	}

	public void process() {
		initEntities();
		while (true) {
			if (newFrame()) {
				EntityManager.getInstance().updatePositions();
				EntityManager.getInstance().checkBorderCollisions(Const.DISPLAY_WIDTH, Const.DISPLAY_HEIGHT);
				EntityManager.getInstance().checkShipCollisions();
			}
		}
	}

	private boolean newFrame() {
		double currentTime = System.currentTimeMillis();
		double delta = currentTime - m_lastTime;
		boolean rv = (delta > Const.FRAME_INCREMENT);
		if (rv) {
			m_lastTime += Const.FRAME_INCREMENT;
			if (delta > 10 * Const.FRAME_INCREMENT) {
				m_lastTime = currentTime;
			}
			m_actualFps = 1000 / delta;
		}
		return rv;
	}

	private void initEntities() {
		// Team 1
		EntityManager.getInstance()
				.addEntity(new Ship(0, new Vector2D(Const.START_TEAM1_SHIP1_X, Const.START_TEAM1_SHIP1_Y),
						new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0), Const.TEAM1_COLOR));

		EntityManager.getInstance()
				.addEntity(new Ship(1, new Vector2D(Const.START_TEAM1_SHIP1_X, Const.START_TEAM1_SHIP1_Y),
						new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0), Const.TEAM1_COLOR));

		// Team 2
		EntityManager.getInstance()
				.addEntity(new Ship(2, new Vector2D(Const.START_TEAM1_SHIP1_X, Const.START_TEAM1_SHIP1_Y),
						new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0), Const.TEAM2_COLOR));

		EntityManager.getInstance()
				.addEntity(new Ship(3, new Vector2D(Const.START_TEAM1_SHIP1_X, Const.START_TEAM1_SHIP1_Y),
						new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0), Const.TEAM2_COLOR));

		// Ball
		EntityManager.getInstance()
				.addEntity(new Ball(new Vector2D(Const.BALL_X, Const.BALL_Y), new Vector2D(0.0, 0.0)));
	}

	public double getActualFps() {
		return m_actualFps;
	}



}