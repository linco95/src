package gball.client;

import java.io.Serializable;

public class InputInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public final boolean m_rightKey;
	public final boolean m_leftKey;
	public final boolean m_accelerateKey;
	public final boolean m_brakeKey;
	
	
	public InputInfo(final boolean rightKey, final boolean leftKey, final boolean accelerateKey, final boolean brakeKey){
		m_rightKey = rightKey;
		m_leftKey = leftKey;
		m_accelerateKey = accelerateKey;
		m_brakeKey = brakeKey;
	}
}
