package union.jsbot;

import union.JSGUI.JSGUI_Widget;
import haven.CheckBox;
import haven.Coord;
import haven.TextEntry;

public class JSGUI_CheckBox extends JSGUI_Widget {
	public JSGUI_CheckBox(int lid) {
		super(lid);
	}

	/**
	 * Sets the value of the checkbox.
	 *
	 * @param Boolean val - The new value of the checkbox.
	 */
	public void setChecked(boolean val) {
		((CheckBox)wdg()).a = val;
	}
	
	/**
	 * Sets the position of the checkbox.
	 *
	 * @param Coord pos - The new position of the checkbox.
	 */
	public void setPosition(Coord pos) {
		((CheckBox)wdg()).setPosition(pos);
	}
	
	/**
	 * Sets the size of the checkbox.
	 *
	 * @param Coord size - The new size of the checkbox (in pixels).
	 */
	public void setSize(Coord size) {
		((CheckBox)wdg()).setSize(size);
	}
	
	/**
	 * Sets the text of the checkbox.
	 *
	 * @param String text - The new text to be displayed on the checkbox.
	 */
	public void setText(String text) {
		((CheckBox)wdg()).setText(text);
	}

	/**
	 * Retrieves the value of the checkbox.
	 *
	 * @return A {@code Boolean} value. {@code true} if the checkbox is checked, {@code false} if the checkbox is not.
	 */
	public boolean isChecked() {
		return ((CheckBox)wdg()).a;
	}
	
	/**
	 * Retrieves the (top left) position of the checkbox.
	 *
	 * @return A {@code Coord} containing the position of the checkbox.
	 */
	public Coord getPosition() {
		return ((CheckBox)wdg()).c;
	}
	
	/**
	 * Retrieves the size of the checkbox.
	 *
	 * @return A {@code Coord} containing the size of the checkbox.
	 */
	public Coord getSize() {
		return ((CheckBox)wdg()).sz;
	}

	/**
	 * Retrieves the text of the checkbox.
	 *
	 * @return A {@code String} containing the text of the checkbox.
	 */
	public String getText() {
		return ((CheckBox)wdg()).lbl.text;
	}
}