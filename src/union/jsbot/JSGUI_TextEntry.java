package union.jsbot;

import haven.Button;
import haven.Coord;
import haven.TextEntry;
import haven.Window;
import union.JSGUI.JSGUI_Widget;

public class JSGUI_TextEntry extends JSGUI_Widget {
	public JSGUI_TextEntry(int lid) {
		super(lid);
	}
	
	/**
	 * Sets the text within the text box.
	 *
	 * @param String text - The new text to be displayed within the text box.
	 */
	public void setText(String text) {
		((TextEntry)wdg()).text = text;
	}
	
	/**
	 * Sets the position of the text box.
	 *
	 * @param Coord pos - The new position of the text box.
	 */
	public void setPosition(Coord pos) {
		((TextEntry)wdg()).setPosition(pos);
	}
	
	/**
	 * Sets the size of the text box.
	 *
	 * @param Coord size - The new size of the text box (in pixels).
	 */
	public void setSize(Coord size) {
		((TextEntry)wdg()).setSize(size);
	}
	
	/**
	 * Retrieves the text within the text box.
	 *
	 * @return A {@code String} containing the text of the text box.
	 */
	public String getText() {
		return ((TextEntry)wdg()).text;
	}
	
	/**
	 * Retrieves the (top left) position of the text box.
	 *
	 * @return A {@code Coord} containing the position of the text box.
	 */
	public Coord getPosition() {
		return ((TextEntry)wdg()).c;
	}
	
	/**
	 * Retrieves the size of the text box.
	 *
	 * @return A {@code Coord} containing the size of the text box.
	 */
	public Coord getSize() {
		return ((TextEntry)wdg()).sz;
	}
	
	/**
	 * Retrieves the text in the text box as an integer value.
	 *
	 * @return A {@code Integer} value of the text in the text box. If the text cannot be converted, then a {@code 0} is returned.
	 */
	public int getInt() {
		String textv = ((TextEntry)wdg()).text;
		try{
			Integer ival = Integer.parseInt(textv);
			return ival.intValue();
		}
		catch(NumberFormatException e){
			return 0;
		}
	}
}