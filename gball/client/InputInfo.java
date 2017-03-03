package gball.client;

import java.io.Serializable;

public class InputInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public boolean m_rightKey;
	public boolean m_leftKey;
	public boolean m_accelerateKey;
	public boolean m_brakeKey;
	
	
	public InputInfo(){
		m_rightKey = false;
		m_leftKey = false;
		m_accelerateKey = false;
		m_brakeKey = false;
	}
}
