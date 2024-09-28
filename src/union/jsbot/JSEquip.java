package union.jsbot;

import haven.Coord;
import haven.Inventory;
import haven.UI;

public class JSEquip {
	public JSEquip() {
	}

	/**
	 * Retrieves the number of empty equipment slots.
	 *
	 * @return An {@code Integer} value of the count of empty slots in the equipment.
	 */
	public int emptyCount() {
		int count = 0;
		if (UI.instance.equip.equed != null) {
			for (int i = 0; i < UI.instance.equip.equed.size(); i++)
				if (UI.instance.equip.equed.get(i) != null)
					count++;
		}
		return (16 - count);
	}

	/**
	 * Retrieves the quality of the item in the specified equipment slot.
	 *
	 * @param Integer slot - The index of the equipment slot (0 to 15).
	 * @return An {@code Integer} value of the quality of the item, or {@code -1} if the slot is empty or invalid.
	 */
	public int quality(int slot) {
		if (slot < 0)
			slot = 0;
		if (slot > 15)
			slot = 15;
		int q = -1;
		if (UI.instance.equip.equed != null)
			if (UI.instance.equip.equed.get(slot) != null)
				q = UI.instance.equip.equed.get(slot).quality;
		return q;
	}

	/**
	 * Retrieves the full resource name of the item in the specified equipment slot.
	 *
	 * @param Integer slot - The index of the equipment slot (0 to 15).
	 * @return A {@code String} containing the resource name of the item, or an empty {@code String} if the slot is empty or invalid.
	 */
	public String resName(int slot) {
		if (slot < 0)
			slot = 0;
		if (slot > 15)
			slot = 15;
		String n = "";
		if (UI.instance.equip.equed != null)
			if (UI.instance.equip.equed.get(slot) != null)
				n = UI.instance.equip.equed.get(slot).GetResName();
		return n;
	}

	/**
	 * Retrieves the name of the item in the specified equipment slot.
	 *
	 * @param Integer slot - The index of the equipment slot (0 to 15).
	 * @return A {@code String} containing the name of the item (as in tooltip, without quality), or an empty {@code String} if the slot is empty or invalid.
	 */
	public String name(int slot) {
		if (slot < 0)
			slot = 0;
		if (slot > 15)
			slot = 15;
		String n = "";
		if (UI.instance.equip.equed != null)
			if (UI.instance.equip.equed.get(slot) != null)
				n = UI.instance.equip.equed.get(slot).name();
		return n;
	}

	/**
	 * Drops an item from the player's hands (cursor held object) into the specified equipment slot.
	 *
	 * @param Integer slot - The index of the slot to drop the item into (0 to 15).
	 */
	public void dropTo(int slot) {
		if (slot < 0)
			slot = 0;
		if (slot > 15)
			slot = 15;
		if (UI.instance.equip.epoints != null)
			if (UI.instance.equip.epoints.get(slot) != null) {
				Inventory i = UI.instance.equip.epoints.get(slot);
				Coord c = new Coord(0, 0);
				i.wdgmsg("drop", c);
			}
	}

	/**
	 * Takes an item from the specified equipment slot, transferring it to the player's hands (cursor held object).
	 *
	 * @param Integer slot - The index of the equipment slot (0 to 15).
	 */
	public void takeAt(int slot) {
		itemAction(slot, "take");
	}

	/**
	 * Interacts with the item in the specified equipment slot using the item in the player's hands (cursor held object).
	 *
	 * @param Integer slot - The index of the equipment slot (0 to 15).
	 */
	public void itemact(int slot) {
		itemAction(slot, "itemact");
	}

	/**
	 * Transfers an item from the equipment to the active inventory.
	 *
	 * @param Integer slot - The index of the equipment slot (0 to 15).
	 */
	public void transfer(int slot) {
		itemAction(slot, "transfer");
	}

	/**
	 * Opens the context menu for the item in the specified equipment slot.
	 *
	 * @param Integer slot - The index of the equipment slot (0 to 15).
	 */
	public void iact(int slot) {
		itemAction(slot, "iact");
	}

	private void itemAction(int slot, String act) {
		if (slot < 0)
			slot = 0;
		if (slot > 15)
			slot = 15;
		Coord c = new Coord(0, 0);
		if (UI.instance.equip.equed != null)
			if (UI.instance.equip.equed.get(slot) != null) {
				if (act.equals("itemact")) {
					UI.instance.equip.wdgmsg("itemact", slot);
				} else
					UI.instance.equip.wdgmsg(act, slot, c);
			}
	}
}
