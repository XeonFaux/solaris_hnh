package union.jsbot;

import haven.Buff;
import haven.UI;

public class JSBuff {
	private int remote_id;

	public JSBuff(int rid) {
		remote_id = rid;
	}

	/**
	 * Returns the name of the buff.
	 *
	 * @return A {@code String} containing the name of the buff.
	 */
	public String name() {
		return wdg().GetName();
	}

	/**
	 * Returns the progress of the buff as a percentage.
	 *
	 * @return A {@code Integer} value of the progress of the buff (0 to 100).
	 */
	public int meter() {
		return wdg().ameter;
	}

	/**
	 * Returns the time remaining until the buff expires (0 to 100).
	 *
	 * @return A {@code Integer} value of the remaining time until the buff is completed.
	 */
	public int time() {
		return wdg().GetTimeLeft();
	}

	/**
	 * Checks if the buff object still exists.
	 *
	 * @return A {@code Boolean} value. {@code true} if the buff object is still active, {@code false} otherwise.
	 */
	public boolean isActual() {
		return wdg() != null;
	}
	
	private Buff wdg() {
		return UI.instance.sess.glob.buffs.get((Integer) remote_id);
	}
}