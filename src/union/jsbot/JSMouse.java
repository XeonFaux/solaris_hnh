package union.jsbot;

import haven.Coord;
import haven.MainFrame;
import haven.UI;

public class JSMouse {
	// Eventually move over other mouse related functions like getdraggingitem, absclick, etc...
	
	/**
	 * Checks if any mouse button is currently pressed down.
	 *
	 * @return {@code true} if a mouse button is pressed, {@code false} otherwise.
	 */
	public static boolean jIsMouseDown() {
		if (MainFrame.havenPanel.mouseDown) { return true; }
		return false;
	}
	
	/**
	 * Retrieves the identifier of the currently pressed mouse button.
	 *
	 * @return An {@code Integer} representing the pressed mouse button: 
	 *         {@code 1} for Left mouse button, 
	 *         {@code 2} for Middle mouse button, 
	 *         {@code 3} for Right mouse button. 
	 *         Returns {@code 0} if no mouse button is pressed.
	 */
	public static int jGetMouseButton() {
		return MainFrame.havenPanel.mouseButton;
	}
	
	/**
	 * Retrieves the current direction of mouse wheel scrolling.
	 *
	 * @return An {@code Integer} representing the scroll direction: 
	 *         {@code -1} for zooming inward, 
	 *         {@code 1} for zooming outward.
	 */
	public static int jGetMouseWheelDir() {
		return MainFrame.havenPanel.mouseWheelDir;
	}
	
	/**
	 * Retrieves the current screen position of the mouse cursor.
	 *
	 * @return A {@code jCoord} object representing the screen coordinates of the mouse.
	 */
	public static Coord jMouseScreenPosition() {
		return MainFrame.havenPanel.mousepos;
	}
	
	/**
	 * Retrieves the current map tile position beneath the mouse cursor.
	 *
	 * @return A {@code jCoord} object representing the tile coordinates of the mouse.
	 */
	public static Coord jMouseTilePosition() {
		return UI.instance.mapview.mouseAtTile;
	}
}