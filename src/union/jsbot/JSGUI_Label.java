package union.jsbot;

import haven.CheckBox;
import haven.Coord;
import haven.Label;
import union.JSGUI.JSGUI_Widget;

public class JSGUI_Label extends JSGUI_Widget {
	public JSGUI_Label(int lid) {
		super(lid);
	}
	
	/**
	 * Sets the position of the label.
	 *
	 * @param Coord pos - The new position of the label.
	 */
	public void setPosition(Coord pos) {
		((Label)wdg()).setPosition(pos);
	}
	
	/**
	 * Sets the text of the label.
	 *
	 * @param String text - The new text for the label to display.
	 */
	public void setText(String text) {
		((Label)wdg()).settext(text);
	}
	
	/**
	 * Retrieves the (top left) position of the label.
	 *
	 * @return A {@code Coord} containing the position of the label.
	 */
	public Coord getPosition() {
		return ((Label)wdg()).c;
	}
	
	/**
	 * Retrieves the text of the label.
	 *
	 * @return A {@code String} containing the text that the label is displaying.
	 */
	public String getText() {
		return ((Label)wdg()).text.text;
	}
}
