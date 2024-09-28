package union.jsbot;

import haven.Button;
import haven.Inventory;
import haven.Label;
import haven.TextEntry;
import haven.UI;
import haven.VMeter;
import haven.Widget;
import haven.Window;

import java.util.ArrayList;

public class JSWindow {
	private int remote_id;

	public JSWindow(int rid) {
		remote_id = rid;
	}

	/**
	 * Retrieves an array of inventory windows. For example, there may be 
	 * two inventories in a table or in a smelter.
	 *
	 * @return A {@code JSInventory} array containing the inventories of the window.
	 */
	public JSInventory[] getInventories() {
		ArrayList<JSInventory> items = new ArrayList<JSInventory>();
		try {
			for (Widget i = wdg().child; i != null; i = i.next) {
				if (i instanceof Inventory) {
					items.add(new JSInventory(UI.instance.getId(i)));
				}
			}
		} catch (Exception ex) {
			// Do nothing
		}
		JSInventory[] ret = new JSInventory[items.size()];
		for (int i = 0; i < items.size(); i++)
			ret[i] = items.get(i);
		return ret;
	}

	/**
	 * Clicks the button with the specified name in this window.
	 *
	 * @param String bname - The exact name of the button to click.
	 */
	public void pushButton(String bname) {
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Button) {
				Button b = (Button) i;
				if (b.text.text.equals(bname)) {
					b.click();
					break;
				}
			}
		}
	}

	/**
	 * Clicks the button at the specified position in the order they were created in the window.
	 * Useful for crossroads where buttons are stacked vertically.
	 *
	 * @param Integer pos - The position of the button in the window (1-based index).
	 */
	public void pushButton(int pos) {
		int current = 0;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Button) {
				current++;
				if (current == pos) {
					Button b = (Button) i;
					b.click();
					break;
				}
			}
		}
	}
	
	/**
	 * Retrieves the text from the TextEntry at the specified position.
	 *
	 * @param Integer pos - The position of the input field (1-based index).
	 * @return A {@code String} containing the text from the input field, or an empty {@code String} if the field is empty or invalid.
	 */
	public String getEntryText(int pos) {
		int current = 0;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof TextEntry) {
				current++;
				if (current == pos) {
					TextEntry te = (TextEntry) i;
					return te.text;
				}
			}
		}
		return "";
	}
	
	/**
	 * Sets the text in the TextEntry at the specified position.
	 *
	 * @param String text - The text to set in the TextEntry.
	 * @param Integer pos - The position of the input field (1-based index).
	 */
	public void setEntryText(String text, int pos) {
		int current = 0;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof TextEntry) {
				current++;
				if (current == pos) {
					TextEntry te = (TextEntry) i;
					te.settext(text);
					return;
				}
			}
		}
	}
	
	/**
	 * Activates the TextEntry at the specified position. If an empty string is specified, the current text in the input field is sent to the server.
	 * [Not entirely sure what this would be useful for tbh. Don't feel like researching.]
	 *
	 * @param String text - The text to send to the server. If an empty string is provided, the current text will be sent instead.
	 * @param Integer pos - The position of the input field (1-based index).
	 */
	public void activateEntry(String text, int pos) {
		int current = 0;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof TextEntry) {
				current++;
				if (current == pos) {
					TextEntry te = (TextEntry) i;
					if (text.equals(""))
						te.activate(te.text);
					else
						te.activate(text);
					return;
				}
			}
		}
	}

	/**
	 * Retrieves the text of the label at the specified position in the window (e.g., in a barrel window).
	 *
	 * @param Integer pos - The position of the label in the window (1-based index).
	 * @return A {@code String} containing the text of the label, or an empty {@code String} if the label is not found.
	 */
	public String getLabelText(int pos) {
		if (pos < 1)
			pos = 1;
		int labelPos = 1;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof Label) {
				if (labelPos == pos) {
					Label l = (Label) i;
					return l.text.text;
				}
				else {
					labelPos++;
					continue;
				}
			}
		}
		return "";
	}

	/**
	 * Retrieves the value of the resource amount from the specified meter (e.g., a progress bar).
	 *
	 * @param Integer pos - The position of the meter in the window (1-based index).
	 * @return An {@code Integer} value of the meter, or {@code 0} if the meter is not found.
	 */
	public int getMeterValue(int pos) {
		if (pos < 1)
			pos = 1;
		int meterPos = 1;
		for (Widget i = wdg().child; i != null; i = i.next) {
			if (i instanceof VMeter) {
				if (meterPos == pos) {
					VMeter v = (VMeter) i;
					return v.amount;
				}
				else {
					meterPos++;
					continue;
				}
			}
		}
		return 0;
	}

	/**
	 * Closes the current window.
	 *
	 * @return A {@code Boolean} value. {@code true} if the window was successfully closed, {@code false} otherwise.
	 */
	public boolean close() {
		try {
			wdg().cbtn.click();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * Checks if the window exists.
	 *
	 * @return A {@code Boolean} value. {@code true} if the window exists, {@code false} otherwise.
	 */
	public boolean isActual() {
		return wdg() != null;
	}
	
	private Window wdg() {
		Widget wdg = UI.instance.getWidget(remote_id);
		if (wdg instanceof Window) {
			return (Window) wdg;
		} else {
			return null;
		}
	}
}