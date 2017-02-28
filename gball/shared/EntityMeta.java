package gball.shared;

import java.io.Serializable;
import java.util.UUID;

public class EntityMeta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int m_entityID;
	public Vector2D m_position;
    public Vector2D m_direction;	// Should always be unit vector; determines the object's facing
	
    public EntityMeta(){
    	m_entityID = -1;
    	m_position = new Vector2D();
    	m_direction = new Vector2D();
    }

    public EntityMeta(int id, Vector2D position, Vector2D direction){
    	m_entityID = id;
    	m_position = position;
    	m_direction = direction;
    }

}
