package gball.client;

import java.awt.Color;
import java.util.LinkedList;
import java.util.ListIterator;

import gball.gui.BallRepresentation;
import gball.gui.RenderableEntity;
import gball.gui.ShipRepresentation;
import gball.shared.Vector2D;

public class RenderableEntityManager {

	private static LinkedList<RenderableEntity> m_entities = new LinkedList<RenderableEntity>();

	private static class SingletonHolder {
		public static final RenderableEntityManager instance = new RenderableEntityManager();
	}

	public static RenderableEntityManager getInstance() {
		return SingletonHolder.instance;
	}

	private RenderableEntityManager() {
	}

	 public void addShip(final int ID, final Color col) {
	 m_entities.add(new ShipRepresentation(ID, col));
	 }

	public void addBall(final Vector2D position, final Vector2D speed) {
		m_entities.add(new BallRepresentation());
	}

	public void updatePositions(/*packet from server?*/) {
		for (ListIterator<RenderableEntity> itr = m_entities.listIterator(); itr.hasNext();) {
			// Update pos from network itr.next().move();
		}
	}
	
	public RenderableEntity getEntity(final int ID){
		for(ListIterator<RenderableEntity> itr = m_entities.listIterator(); itr.hasNext();){
			RenderableEntity nextEntity = itr.next();
			if(nextEntity.getID() == ID){
				return nextEntity;
			}
		}
		return null;
	}
	
	// This shouldn't be necessary here
//	public static LinkedList<RenderableEntity> getState() {
//		return m_entities;
//	}

}
