package gball.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputListener extends KeyAdapter {
	private final KeyConfig m_keys;
	private final InputInfo m_inputs;

	public InputListener() {
		this(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_W);
	}

	public InputListener(int left, int right, int brake, int accelerate) {
		m_keys = new KeyConfig(left, right, brake, accelerate);
		m_inputs = new InputInfo();
	}
	
	public InputInfo getInputs(){
		return m_inputs;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (e.getKeyCode() == m_keys.rightKey()) {
			m_inputs.m_rightKey = true;
		} else if (e.getKeyCode() == m_keys.leftKey()) {
			m_inputs.m_leftKey = true;
		} else if (e.getKeyCode() == m_keys.accelerateKey()) {
			m_inputs.m_accelerateKey = true;
		} else if (e.getKeyCode() == m_keys.brakeKey()) {
			m_inputs.m_brakeKey = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == m_keys.rightKey()) {
			m_inputs.m_rightKey = false;
		} else if (e.getKeyCode() == m_keys.leftKey()) {
			m_inputs.m_leftKey = false;
		} else if (e.getKeyCode() == m_keys.accelerateKey()) {
			m_inputs.m_accelerateKey = false;
		} else if (e.getKeyCode() == m_keys.brakeKey()) {
			m_inputs.m_brakeKey = false;
		}
	}
}

class KeyConfig {
	private final int m_leftKey, m_rightKey, m_brakeKey, m_accelerateKey;

	public KeyConfig(int left, int right, int brake, int accelerate) {
		m_leftKey = left;
		m_rightKey = right;
		m_brakeKey = brake;
		m_accelerateKey = accelerate;
	}

	public final int leftKey() {
		return m_leftKey;
	}

	public final int rightKey() {
		return m_rightKey;
	}

	public final int brakeKey() {
		return m_brakeKey;
	}

	public final int accelerateKey() {
		return m_accelerateKey;
	}

}