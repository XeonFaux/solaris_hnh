package union.jsbot;

import haven.Coord;
import haven.Inventory;
import haven.Item;
import haven.UI;
import haven.Widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class JSInventory {
	private int remote_id;

	public JSInventory(int rid) {
		remote_id = rid;
	}

	protected static class ItemQualityComparator implements Comparator<JSItem> {
		int mod = -1;

		@Override
		public int compare(JSItem obj1, JSItem obj2) {
			if (obj1.isActual() && obj2.isActual())
				return mod * (obj1.quality() - obj2.quality());
			else
				return 0;
		}

		public ItemQualityComparator(boolean desc) {
			mod = desc ? 1 : -1;
		}
	}

	protected static class ItemInnerQualityComparator implements
			Comparator<JSItem> {
		int mod = -1;

		@Override
		public int compare(JSItem obj1, JSItem obj2) {
			if (obj1.isActual() && obj2.isActual())
				return mod * (obj1.innerQuality() - obj2.innerQuality());
			else
				return 0;
		}

		public ItemInnerQualityComparator(boolean desc) {
			mod = desc ? 1 : -1;
		}
	}

	protected static class ItemAmountComparator implements Comparator<JSItem> {
		int mod = -1;

		@Override
		public int compare(JSItem obj1, JSItem obj2) {
			if (obj1.isActual() && obj2.isActual())
				return (int) (mod * (obj1.currentAmount() - obj2
						.currentAmount()));
			else
				return 0;
		}

		public ItemAmountComparator(boolean desc) {
			mod = desc ? 1 : -1;
		}
	}

	/**
	 * Returns a list of items based on specified criteria.
	 *
	 * @param itemmasks A list of item names to include in the result. 
	 *                  You can exclude an item by prefixing it with '!' (e.g., "!itemName").
	 * @return An array of JSItem objects that match the criteria.
	 */
	public JSItem[] getItems(String... itemmasks) {
		ArrayList<JSItem> items = new ArrayList<JSItem>();
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Item) {
				Item buf = (Item) i;
				if (itemmasks.length > 0 && itemmasks[0].equals(""))
					items.add(new JSItem(UI.instance.getId(buf)));
				else {
					for (String iname : itemmasks) {
						boolean exclude = iname.startsWith("!");
						if (buf.GetResName().contains(iname) && !exclude) {
							items.add(new JSItem(UI.instance.getId(buf)));
							break;
						}
					}
				}
			}
		}
		JSItem[] ret = new JSItem[items.size()];
		for (int i = 0; i < items.size(); i++)
			ret[i] = items.get(i);
		return ret;
	}
	
	/**
	 * Returns a list of items with an exact match on the resource name.
	 *
	 * @param itemmasks A list of item names to include in the result. 
	 *                  You can exclude an item by prefixing it with '!' (e.g., "!itemName").
	 * @return An array of JSItem objects that match the criteria.
	 */
	public JSItem[] getEqualItems(String... itemmasks) {
		ArrayList<JSItem> items = new ArrayList<JSItem>();
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Item) {
				Item buf = (Item) i;
				if (itemmasks.length > 0 && itemmasks[0].equals(""))
					items.add(new JSItem(UI.instance.getId(buf)));
				else {
					for (String iname : itemmasks) {
						boolean exclude = iname.startsWith("!");
						if (buf.GetResName().equalsIgnoreCase(iname) && !exclude) {
							items.add(new JSItem(UI.instance.getId(buf)));
							break;
						}
					}
				}
			}
		}
		JSItem[] ret = new JSItem[items.size()];
		for (int i = 0; i < items.size(); i++)
			ret[i] = items.get(i);
		return ret;
	}

	/**
	 * Sorts an array of items based on the specified criteria.
	 *
	 * @param items An array of JSItem objects to sort.
	 * @param type The type of sorting: "quality" for quality, 
	 *             "iquality" for internal quality (e.g., water quality in a bucket).
	 * @param desc A flag for sorting order: true for descending, false for ascending.
	 * @return true if sorting was successful, false otherwise.
	 */
	public static boolean sortItems(JSItem[] items, String type,
			boolean desc) {
		try {
			if (type.equals("quality"))
				Arrays.sort(items, new ItemQualityComparator(!desc));
			else if (type.equals("iquality"))
				Arrays.sort(items, new ItemInnerQualityComparator(!desc));
			else if (type.equals("amount"))
				Arrays.sort(items, new ItemAmountComparator(!desc));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Drops the item currently held at the specified inventory coordinates.
	 *
	 * @param c The coordinates where the item should be dropped.
	 */
	public void drop(Coord c) {
		wdg().wdgmsg("drop", c);
	}

	/**
	 * Overloaded method for dropping an item using separate x and y coordinates.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void drop(int x, int y) {
		drop(new Coord(x, y));
	}

	/**
	 * Returns the size of the inventory.
	 *
	 * @return The size of the inventory as a Coord object.
	 */
	public Coord size() {
		return wdg().size();
	}

	/**
	 * Returns the number of unoccupied slots in the inventory.
	 *
	 * @return The number of empty slots available in the inventory.
	 */
	public int freeSlots() {
		int takenSlots = 0;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Item) {
				Item buf = (Item) i;
				takenSlots += buf.size().x * buf.size().y;
			}
		}
		int allSlots = size().x * size().y;
		return allSlots - takenSlots;
	}

	/**
	 * Returns an array of coordinates for empty slots in the inventory.
	 * Slots are counted from the top-left corner (0,0).
	 *
	 * @return An array of Coord objects representing empty slots.
	 */
	public Coord[] freeSlotsCoords() {
		boolean[][] matrix = new boolean[wdg().size().x][wdg().size().y];
		for (int w = 0; w < wdg().size().x; w++)
			for (int h = 0; h < wdg().size().y; h++)
				matrix[w][h] = false;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Item) {
				Item tmpItem = (Item) i;
				Coord itemC = tmpItem.coord();
				for (int width = 0; width < tmpItem.size().x; width++)
					for (int height = 0; height < tmpItem.size().y; height++)
						matrix[itemC.x + width][itemC.y + height] = true;
			}
		}
		ArrayList<Coord> list = new ArrayList<Coord>();
		for (int w = 0; w < wdg().size().x; w++)
			for (int h = 0; h < wdg().size().y; h++)
				if (!matrix[w][h])
					list.add(new Coord(w, h));
		Coord[] ret = new Coord[list.size()];
		for (int i = 0; i < list.size(); i++)
			ret[i] = list.get(i);
		return ret;
	}
	
	/**
	 * Returns the coordinates of an empty slot in the inventory that can fit an item of the specified size.
	 *
	 * @param size The size of the item in slots (e.g., a 2x2 bucket).
	 * @return The coordinates of an empty slot for the item, or null if none are available.
	 */
	public Coord freeSlotSizeCoord(Coord size) {
		if (size == null || size.x < 1 || size.y < 1)
			return null;
		if (size.x == 1 && size.y == 1) {
			if (freeSlotsCoords().length > 0)
				return freeSlotsCoords()[0];
			else
				return null;
		}
		
		Coord ret= null;
		Coord[] fslots = freeSlotsCoords();
		for (int i = 0; i < fslots.length; ++i) {
			if (fslots[i].x+1+size.x > size().x || fslots[i].y+1+size.y > size().y)
				continue;
			boolean free = true;
			for (int w = 0; w < size.x; ++w) {
				for (int h = 0; h < size.y; ++h) {
					Coord tmp = fslots[i].add(new Coord(w, h));
					if (!isFreeSlot(tmp)) {
						free = false;
						break;
					}
				}
			}
			if (free) {
				ret = fslots[i];
				return ret;
			}
		}
		return ret;
	}

	/**
	 * Checks if the specified slot in the inventory is empty.
	 * Slots are counted from the top-left corner (0,0).
	 *
	 * @param slot The coordinates of the slot to check.
	 * @return true if the slot is empty; false otherwise.
	 */
	public boolean isFreeSlot(Coord slot) {
		boolean[][] matrix = new boolean[wdg().size().x][wdg().size().y];
		for (int w = 0; w < wdg().size().x; w++)
			for (int h = 0; h < wdg().size().y; h++)
				matrix[w][h] = false;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Item) {
				Item tmpItem = (Item) i;
				Coord itemC = tmpItem.coord();
				for (int width = 0; width < tmpItem.size().x; width++)
					for (int height = 0; height < tmpItem.size().y; height++)
						matrix[itemC.x + width][itemC.y + height] = true;
			}
		}
		if (matrix[slot.x][slot.y] == false)
			return true;
		else
			return false;
	}

	/**
	 * Overloaded function to check if a specified slot is free using separate x and y coordinates.
	 *
	 * @param x The x-coordinate of the slot.
	 * @param y The y-coordinate of the slot.
	 * @return true if the slot is empty; false otherwise.
	 */
	public boolean isFreeSlot(int x, int y) {
		return isFreeSlot(new Coord(x, y));
	}

	/**
	 * Checks if the inventory exists.
	 *
	 * @return true if the inventory window exists, false otherwise.
	 */
	public boolean isActual() {
		return wdg() != null;
	}
	
	private Inventory wdg() {
		Widget wdg = UI.instance.getWidget(remote_id);
		if (wdg instanceof Inventory) {
			return (Inventory) wdg;
		} else
			return null;
	}
}
