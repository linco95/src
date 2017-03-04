package gball.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gball.shared.EntityMeta;

public class StateUpdate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public final int[] m_scores;
	public final List<EntityMeta> m_entities;

	public StateUpdate(final int[] scores, final List<EntityMeta> entityInfo){
		m_scores = scores;
		m_entities = entityInfo;
	}	
}
