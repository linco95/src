package gball.server;

import java.io.Serializable;
import java.util.ArrayList;

import gball.shared.EntityMeta;

public class StateUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<EntityMeta> m_entities;

	public StateUpdate(){}
	
	public void addEntityMeta(EntityMeta entity){
		m_entities.add(entity);
	}
	
}
