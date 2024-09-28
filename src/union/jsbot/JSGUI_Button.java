package union.jsbot;

import haven.Button;
import haven.CheckBox;
import haven.Coord;
import union.JSBot;
import union.JSGUI.JSGUI_Widget;

public class JSGUI_Button extends JSGUI_Widget {
	public JSGUI_Button(int lid) {
		super(lid);
	}
	
	/**
	 * Sets the text displayed on the button.
	 *
	 * @param String text - The new text to be displayed on the button.
	 */
	public void setText(String text) {
		((Button)wdg()).settext(text);
	}
	
	/**
	 * Sets the position of the button.
	 *
	 * @param Coord pos - The new position of the button.
	 */
	public void setPosition(Coord pos) {
		((Button)wdg()).setPosition(pos);
	}
	
	/**
	 * Sets the size of the button.
	 *
	 * @param Coord size - The new size of the button (in pixels).
	 */
	public void setSize(Coord size) {
		((Button)wdg()).setSize(size);
	}
	
	/**
	 * Sets the width of the button, maintaining a fixed height of 19 pixels.
	 *
	 * @param Integer width - The new width of the button (in pixels).
	 */
	public void setWidth(int width) {
		((Button)wdg()).setSize(new Coord(width, 19));
	}
	
	/**
	 * Retrieves the (top left) position of the button.
	 *
	 * @return A {@code Coord} containing the position of the button.
	 */
	public Coord getPosition() {
		return ((Button)wdg()).c;
	}
	
	/**
	 * Retrieves the size of the button.
	 *
	 * @return A {@code Coord} containing the size of the button.
	 */
	public Coord getSize() {
		return ((Button)wdg()).sz;
	}

	/**
	 * Retrieves the text of the button.
	 *
	 * @return A {@code String} containing the text of the button.
	 */
	public String getText() {
		return ((Button)wdg()).text.text;
	}
	
	/**
	 * Waits for the button to be clicked.
	 *
	 * @return A {@code Boolean} value. {@code true} if the button was clicked, {@code false} if the wait has timed-out.
	 */
	public boolean waitClick() {
		while (true) {
			if (((Button)wdg()).isChanged()) return true;
			if (!JSBot.Sleep(25)) return false;
		}
	}
}