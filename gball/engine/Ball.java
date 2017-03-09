package gball.engine;

import java.awt.Color;
import java.awt.Graphics;

import gball.shared.Const;
import gball.shared.Vector2D;

public class Ball extends GameEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color m_color;

	public Ball(final Vector2D position, final Vector2D speed) {
		super(0, position, speed, new Vector2D(0, 0), Const.BALL_MAX_ACCELERATION, Const.BALL_MAX_SPEED,
				Const.BALL_FRICTION);
		m_color = Const.BALL_COLOR;
	}

	@Override
	public double getRadius() {
		return Const.BALL_RADIUS;
	}

	@Override
	public boolean givesPoints() {
		return true;
	};
}