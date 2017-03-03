package gball.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;

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

	public void addBall() {
		m_entities.add(new BallRepresentation());
	}

	public void updatePositions(/*packet from server?*/) {
		// getEntity(msg.getID()).update(
//		for (ListIterator<RenderableEntity> itr = m_entities.listIterator(); itr.hasNext();) {
//			// Update pos from network itr.next().move(); (compare ID)
//		}
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
	
	
	public void renderAll(Graphics g) {
		for (ListIterator<RenderableEntity> itr = m_entities.listIterator(0); itr.hasNext();) {
			itr.next().render(g);
		}
	}
	
	// This shouldn't be necessary here
//	public static LinkedList<RenderableEntity> getState() {
//		return m_entities;
//	}

}
