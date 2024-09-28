package union.jsbot;

import haven.Button;
import haven.Coord;
import haven.Label;
import haven.TextEntry;
import haven.UI;
import haven.Widget;
import haven.Window;

public class JSInputWidget extends Window {

		private boolean okClicked = false;
		private TextEntry lineEdit;

		/**
		 * Constructor for JSInputWidget. This should not be modified. 
		 * A brief example of usage is provided below.
		 * 
		 * Example:
		 * var iw = jgetInputWidget(250, 250, "Input something", "Label text");
		 * var ID = 0;
		 * if (iw != null) {
		 *     if (iw.waitForPress(20000)) {
		 *         ID = iw.intValue();
		 *         iw.close();
		 *     } else {
		 *         iw.close();
		 *     }
		 * }
		 * This example shows how to read a numeric value into the variable ID. 
		 * When the "Ok" button is pressed in the window, it hides instead of closing, 
		 * so it is good practice to close the window using the close() method 
		 * when it is no longer needed.
		 */
		public JSInputWidget(Coord c, String header, String label) {
			super(c, Coord.z, UI.instance.root, header);
			cbtn.visible = false;
			new Label(Coord.z, this, label);
			lineEdit = new TextEntry(new Coord(0, 20), new Coord(180, 20),
					this, "");
			new Button(new Coord(185, 22), 40, this, "Ok") {
				public void click() {
					okClicked = true;
					hide();
				}
			};
			pack();
		}

		/**
		 * Waits for the "Ok" button to be pressed.
		 *
		 * @param Integer timeout - The duration to wait (in milliseconds).
		 * @return A {@code Boolean} value. {@code true} if the "Ok" button was pressed within the specified timeout period, {@code false} otherwise.
		 */
		public boolean waitForPress(int timeout) {
			if (timeout == 0)
				timeout = 0;
			int curr = 0;
			while (!okClicked) {
				if (curr > timeout)
					return false;
				curr += 25;
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return true;
		}

		/**
		 * Retrieves the text from the input field.
		 *
		 * @return A {@code String} containing the text input, or an empty {@code String} if the "Ok" button has not been pressed.
		 */
		public String textValue() {
			if (okClicked)
				return lineEdit.text;
			return "";
		}

		/**
		 * Retrieves the text in the input field as an integer value.
		 *
		 * @return An {@code Integer} value of the input. If the input cannot be converted, then a {@code 0} is returned.
		 */
		public int intValue() {
			if (okClicked) {
				try {
					Integer ival = Integer.parseInt(lineEdit.text);
					return ival.intValue();
				} catch (NumberFormatException e) {
					return 0;
				}
			}
			return 0;
		}

		/**
		 * Closes the input widget.
		 */
		public void close() {
			ui.destroy(this);
		}
		
		/**
		 * Method that should not be used/modified. [Not sure why this is included if it shouldn't be used. Too lazy to research at the moment.]
		 */
		public void wdgmsg(Widget sender, String msg, Object... args) {
			if (sender == cbtn) {
				close();
				return;
			}
			super.wdgmsg(sender, msg, args);
		}

		/**
		 * Method that should not be used/modified. [Not sure why this is included if it shouldn't be used. Too lazy to research at the moment.]
		 */
		public boolean type(char key, java.awt.event.KeyEvent ev) {
			if (key == 27) {
				close();
			}
			if (key == 10) {
				// Simulates clicking the "Ok" button when Enter is pressed.
				okClicked = true;
				hide();
			}
			return (super.type(key, ev));
		}
		
		/**
		 * Method that should not be used/modified. [Not sure why this is included if it shouldn't be used. Too lazy to research at the moment.]
		 */
		public void destroy() {
			super.destroy();
		}
	}