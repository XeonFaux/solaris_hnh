package union.jsbot;

import haven.Button;
import haven.CheckBox;
import haven.Coord;
import haven.Label;
import haven.Widget;
import haven.Window;
import union.JSBot;
import union.JSGUI.JSGUI_Widget;

import java.awt.*;

import static union.jsbot.JSHaven.unWrapCoord;

public class JSGUI_Window extends JSGUI_Widget {
	public JSGUI_Window(int lid) {
		super(lid);
	}
	
	/**
	 * Toggles the visibility of the close button on the window.
	 */
	public void toggleCloseButton() {
		((Window)wdg()).cbtn.visible = !((Window)wdg()).cbtn.visible;
	}
	
	/**
	 * Closes the window by simulating a click on the close button.
	 */
	public void close() {
		((Window)wdg()).cbtn.click();
	}
	
	/**
	 * Sets the title of the window.
	 *
	 * @param String title - The new title for the window.
	 */
	public void setTitle(String title) { ((Window)wdg()).cap = Window.cf.render(title, Color.WHITE); }

	/**
	 * Sets the position of the window to the specified coordinates.
	 *
	 * @param jCoord pos - The new position for the window.
	 */
	public void setPosition(Coord pos) {
		((Window)wdg()).setPosition(pos);
	}
	
	/**
	 * Resizes the window to the specified dimensions.
	 *
	 * @param jCoord size - The new size of the window.
	 */
	public void setSize(Coord size) {
		((Window)wdg()).resize(size);
	}
	
	/**
	 * Retrieves the text of the window.
	 *
	 * @return A {@code String} containing the text of the window.
	 */
	public String getTitle() {
		return ((Window)wdg()).cap.text;
	}
	
	/**
	 * Retrieves the (top left) position of the window.
	 *
	 * @return A {@code Coord} containing the position of the window.
	 */
	public Coord getPosition() {
		return ((Window)wdg()).c;
	}
	
	/**
	 * Retrieves the size of the window.
	 *
	 * @return A {@code Coord} containing the size of the window.
	 */
	public Coord getSize() {
		return ((Window)wdg()).sz;
	}
	
	/**
	 * Waits for a button click in the window, with an optional timeout.
	 *
	 * @param Integer timeout - The maximum time to wait for a button click, in milliseconds.
	 *                If set to 0, the method will wait indefinitely for a button click.
	 * @return A {@code String} containing the text of the button that was clicked, or an empty {@code String} if no button was clicked within the timeout.
	 */
	public String waitButtonClick(int timeout) {
		boolean inf = false;
        if (timeout == 0)
        	inf = true;
        int cur = 0;
		while(cur <= timeout){
			for(Widget i = wdg().child; i != null; i = i.next) {
				if(i instanceof Button)
				{
					Button b = (Button) i;
					if(b.isChanged()) return b.text.text;
				}
				else if (i instanceof CheckBox)
				{
					CheckBox c = (CheckBox) i;
					if (c.isChanged()) return c.lbl.text;
				}
			}
			if (!JSBot.Sleep(25))
				break;
            if (!inf)
            	cur += 25;
		}
		return "";
	}
	
	/**
	 * Overloaded method for existing scripts, waits indefinitely for a button click.
	 *
	 * @return A {@code String} containing the text of the button that was clicked.
	 */
	public String waitButtonClick() {
		return waitButtonClick(0);
	}
}