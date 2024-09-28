package union.jsbot;

import union.JSBotUtils;
import haven.Coord;
import haven.Item;
import haven.UI;
import haven.Widget;

public class JSItem {
	private int remote_id;

	public JSItem(int rid) {
		remote_id = rid;
	}

	/**
	 * Retrieves the quality of the item.
	 *
	 * @return An {@code Integer} value of the quality of the item.
	 */
	public int quality() {
		return wdg().quality;
	}
	
	/**
	 * Checks if the item is a curiosity.
	 *
	 * @return A {@code Boolean} value. {@code true} if the item is a curiosity (defined in curio.conf), {@code false} otherwise.
	 */
	public boolean isCurio() {
		return wdg().curio_stat != null;
	}
	
	/**
	 * Retrieves the attention value of the curiosity.
	 *
	 * @return An {@code Integer} value of the attention value, or -1 if not applicable.
	 */
	public int curioAttention() {
		if (wdg().curio_stat != null)
			return wdg().curio_stat.attention;
		return -1;
	}
	
	/**
	 * Retrieves the study time of the curiosity.
	 *
	 * @return An {@code Integer} value of the study time, or {@code -1} if not applicable.
	 */
	public int curioStudyTime() {
		if (wdg().curio_stat != null)
			return wdg().curio_stat.studyTime;
		return -1;
	}
	
	/**
	 * Retrieves the multiplier for the item based on its quality.
	 *
	 * @return A {@code Double} value for the quality multiplier of the item.
	 */
	public double multiply() {
		return wdg().qmult;
	}
	
	/**
	 * Retrieves the base LP (learning points) of the curiosity, as defined in curio.conf.
	 *
	 * @return A {@code Double} value for the base LP of the curiosity, or {@code -1} if not applicable.
	 */
	public double baseCurioLP() {
		if (wdg().curio_stat != null) {
			return wdg().curio_stat.baseLP;
		}
		return -1;
	}
	
	/**
	 * Retrieves the current LP (learning points) of the curiosity, considering the multiplier and current experience mode.
	 *
	 * @return A {@code Long} value for the current LP of the curiosity, or {@code -1} if not applicable.
	 */
	public long currentCurioLP() {
		if (wdg().curio_stat != null) {
			return Math.round(wdg().curio_stat.baseLP * wdg().qmult * UI.instance.wnd_char.getExpMode());
		}
		return -1;
	}

	/**
	 * Retrieves the name of the item (full tooltip without resource name).
	 *
	 * @return A {@code String} containing the name of the item.
	 */
	public String name() {
		return wdg().name();
	}

	/**
	 * Retrieves the coordinates of the item in the inventory.
	 *
	 * @return A {@code jCoord} containing the coordinates of the item within the inventory grid.
	 */
	public Coord coord() {
		return wdg().c.div(31);
	}

	/**
	 * Retrieves the full resource name of the item.
	 *
	 * @return A {@code String} containing the resource name of the item.
	 */
	public String resName() {
		return wdg().GetResName();
	}

	/**
	 * Retrieves the progress meter value of the item.
	 *
	 * @return An {@code Integer} value of the progress meter.
	 */
	public int meter() {
		return wdg().meter;
	}

	/**
	 * Retrieves the current stage of the item (e.g., for silkworms).
	 *
	 * @return An {@code Integer} value of the current stage of the item.
	 */
	public int stage() {
		return wdg().num;
	}

	/**
	 * Retrieves the "internal" quality of the item (e.g., the quality of water in a bucket).
	 *
	 * @return An {@code Integer} value of the "internal" quality.
	 */
	public int innerQuality() {
		return wdg().get_inner_quality();
	}

	/**
	 * Retrieves the current amount of contents in the container item. (e.g., 10.0 liters of water in a bucket).
	 *
	 * @return A {@code Double} value of the current amount of contents.
	 */
	public double currentAmount() {
		return wdg().count_of_value;
	}

	/**
	 * Drops the item on the ground. Does not work if the item is currently being held.
	 */
	public void drop() {
		wdg().wdgmsg("drop", JSBotUtils.getCenterScreenCoord());
	}

	/**
	 * Transfers the item to another inventory.
	 */
	public void transfer() {
		wdg().wdgmsg("transfer", JSBotUtils.getCenterScreenCoord());
	}

	/**
	 * Picks up the item to the cursor.
	 */
	public void take() {
		wdg().wdgmsg("take", JSBotUtils.getCenterScreenCoord());
	}

	/**
	 * Interacts with the item while holding another item (right-clicking the current item).
	 *
	 * @param mod The keyboard modifier.
	 */
	public void itemact(int mod) {
		if (!JSBotUtils.isDragging())
			return;
		wdg().wdgmsg("itemact", mod);
	}

	/**
	 * Calls the context menu for the item. Does not work if the item is currently being held.
	 */
	public void iact() {
		wdg().wdgmsg("iact", JSBotUtils.getCenterScreenCoord());
	}

	/**
	 * Retrieves the maximum capacity of the item (e.g., 10.0 liters for a bucket).
	 *
	 * @return A {@code Double} value of the maximum capacity of the item.
	 */
	public double maxAmount() {
		return wdg().count_of_maximum;
	}

	/**
	 * Transfers all items of this type to the next inventory.
	 */
	public void transferSuchAll() {
		wdg().wdgmsg("transfer_such_all", wdg().GetResName());
	}

	/**
	 * Drops all items of this type from the inventory.
	 */
	public void dropSuchAll() {
		wdg().wdgmsg("drop_such_all", wdg().GetResName());
	}

	/**
	 * Retrieves the size of the item (in inventory cells).
	 *
	 * @return A {@code jCoord} size of the item, at least 1x1 (jCoord(1, 1)).
	 */
	public Coord size() {
		return wdg().size();
	}

	/**
	 * Checks if the item exists.
	 *
	 * @return A {@code Boolean} value. {@code true} if the item exists, {@code false} otherwise.
	 */
	public boolean isActual() {
		return wdg() != null;
	}
	
	private Item wdg() {
		Widget wdg = UI.instance.getWidget(remote_id);
		if (wdg instanceof Item) {
			return (Item) wdg;
		} else
			return null;
	}

}