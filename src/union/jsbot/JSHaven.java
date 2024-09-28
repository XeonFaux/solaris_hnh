package union.jsbot;

import haven.Audio;
import haven.BuddyWnd;
import haven.Charlist;
import haven.Coord;
import haven.LoginScreen;
import haven.MainFrame;
import haven.Music;
import haven.Partyview;
import haven.Resource;
import haven.UI;
import haven.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import union.APXUtils;
import union.JSBot;
import union.JSBotUtils;
import union.JSGUI;
import union.JSThread;

public class JSHaven {
	public static Coord unWrapCoord(Object obj) {
		if (obj instanceof org.mozilla.javascript.Wrapper) {
			Object temp = ((org.mozilla.javascript.Wrapper)obj).unwrap();
			if (temp instanceof Coord)
				return (Coord) temp;
		}
		return Coord.z;
	}
	
	@SuppressWarnings("unused")
	private static JSGob unWrapGob(Object obj) {
		if (obj instanceof org.mozilla.javascript.Wrapper) {
			Object temp = ((org.mozilla.javascript.Wrapper)obj).unwrap();
			if (temp instanceof JSGob)
				return (JSGob) temp;
		}
		return null;
	}
	
	private static String[] unWrapStringArray(Object obj) {
		if (obj instanceof String) 
			return new String[] { (String) obj };
		if (obj instanceof org.mozilla.javascript.NativeArray) {
			String[] temp = (String[]) ((org.mozilla.javascript.NativeArray)obj).toArray(new String[((org.mozilla.javascript.NativeArray)obj).size()]);
			return temp;
		}
		return new String[0];
	}
	
	
	/**
	 * Loads a script file into the current script environment.
	 *
	 * @param String filename - The name of the file without the extension. The file must have a {@code .japi} extension and reside in the "scripts" folder.
	 * @return A {@code Boolean} value. {@code true} if the include was successful, {@code false} otherwise.
	 * @since 7.1
	 */
	public static boolean include(String filename) {
		if (!(Thread.currentThread() instanceof JSThread)) return false;
		JSThread currentThread = (JSThread) Thread.currentThread();
		File japi = new File("scripts", filename + ".japi");
		
		try {
			FileReader freader = new FileReader(japi);
			BufferedReader reader = new BufferedReader(freader);
			StringBuilder builder = new StringBuilder();
			String buffer;
			while ((buffer = reader.readLine()) != null) {
				builder.append(buffer);
				builder.append('\n');
			}
			reader.close();
			freader.close();
			currentThread.jsContext.evaluateString(currentThread.jsScope, builder.toString(), filename, 1, null);
		} catch (Exception e) {
			JSBot.JSError(e);
			return false;
		}
		return true;
	}
	
	/**
	 * Returns a jCoord object representing the specified coordinates.
	 *
	 * @param Integer x - The x-coordinate.
	 * @param Integer y - The y-coordinate.
	 * @return A {@code jCoord} object.
	 */
	public static Coord jCoord(int x, int y){
		return new Coord(x, y);
	}

	/**
	 * Creates an input window for entering text or numerical values.
	 *
	 * @param Integer x - The x-coordinate for the window's position on the screen.
	 * @param Integer y - The y-coordinate for the window's position on the screen.
	 * @param String header - The title of the window.
	 * @param String label - The label text above the input field.
	 * @return A {@code JSInputWidget} object for user input.
	 */
	public static JSInputWidget jGetInputWidget(int x, int y, String header, String label){
		return new JSInputWidget(new Coord(x, y), header, label);
	}
	
	/**
	 * Overloaded method for creating an input window, using a jCoord position instead.
	 *
	 * @param jCoord pos - The coordinates for the window's position on the screen.
	 * @param String header - The title of the window.
	 * @param String label - The label text above the input field.
	 * @return A {@code JSInputWidget} object for user input.
	 */
	public static JSInputWidget jGetInputWidget(Coord pos, String header, String label){
		return new JSInputWidget(pos, header, label);
	}

	/**
	 * Pauses the current thread for a specified duration.
	 *
	 * @param Integer timeout - The duration of the pause in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the sleep completed without interruption, {@code false} otherwise.
	 */
	public static boolean jSleep(int timeout) {
		return JSBot.Sleep(timeout);
	}

	/**
	 * Outputs a message to the system console.
	 * This function also allows printing numbers and other objects without additional conversions.
	 *
	 * @param String str - The object to be printed to the console.
	 */
	public static void jPrint(String str) {
		System.out.println(str);
	}

	/**
	 * Outputs a message to the in-game console (Messages tab).
	 *
	 * @param String str - The message string to be displayed.
	 */
	public static void jToConsole(String str) {
		if(UI.instance.cons != null) UI.instance.cons.out.println(str);
	}

	/**
	 * Retrieves a JSWindow object representing the specified window.
	 * For details on working with windows, refer to the corresponding documentation.
	 *
	 * @param String name - The name of the window.
	 * @return The {@code JSWindow} object corresponding to the specified name.
	 */
	public static JSWindow jGetWindow(String name) {
		return JSBotUtils.getWindow(name);
	}
	
	/**
	 * Retrieves the player's study window, allowing interaction as with a standard inventory.
	 *
	 * @return A {@code JSInventory} object representing the study window's grid.
	 */
	public static JSInventory jGetStudy() {
		return JSBotUtils.getStudy();
	}

	/**
	 * Sends a party invitation to the specified player.
	 *
	 * @param String name - The name of the character to invite.
	 * @return  A {@code Boolean} value. {@code true} if the invitation was successfully sent, {@code false} otherwise.
	 */
	public static boolean jSendParty(String name) {
		return JSBotUtils.buddyAct(name, "inv"); 
	}

	/**
	 * Opens a private chat with the specified character from the friend list.
	 *
	 * @param String name - The name of the character to chat with.
	 * @return A {@code Boolean} value. {@code true} if the chat window was successfully opened, {@code false} otherwise.
	 */
	public static boolean jPrivateChat(String name) {
		return JSBotUtils.buddyAct(name, "chat"); 
	}

	/**
	 * Describes (or sends a friend request to) the specified character.
	 *
	 * @param String name - The name of the character to describe.
	 * @return A {@code Boolean} value. {@code true} if the description was successfully sent, {@code false} otherwise.
	 */
	public static boolean jDescribeChar(String name) {
		return JSBotUtils.buddyAct(name, "desc"); 
	}

	/**
	 * Exiles the specified character from the village (available to the Lawspeaker).
	 *
	 * @param String name - The name of the character to be exiled.
	 * @return A {@code Boolean} value. {@code true} if the operation was successful, {@code false} otherwise.
	 */
	public static boolean jExileChar(String name) {
		return JSBotUtils.buddyAct(name, "exile"); 
	}

	/**
	 * Ends the kinship with the specified character from the friend list, functioning as "End kinship."
	 * A subsequent call to this method will act as "Forget."
	 *
	 * @param String name - The name of the character.
	 * @return A {@code Boolean} value. {@code true} if the operation was successful, {@code false} otherwise.
	 */
	public static boolean jEndKinshipForget(String name) {
		return JSBotUtils.buddyAct(name, "rm"); 
	}

	/**
	 * Retrieves an array of all the windows with the specified name (useful for Seedbags).
	 *
	 * @param String name - The name of the window.
	 * @return A {@code JSWindow[]} array of windows.
	 */
	public static JSWindow[] jGetWindows(String name) {
		return JSBotUtils.getWindows(name);
	}

	/**
	 * Terminates the execution of the script.
	 */
	public static void jExit() {
		JSBot.Stop();
	}

	/**
	 * Logs out the current character session.
	 */
	public static void jLogout() {
		JSBotUtils.logoutChar();
	}

	/**
	 * Logs in to the character with the specified account name.
	 *
	 * @param String acc - The login name of the character.
	 * @return A {@code Boolean} value. {@code true} if the login was successful, {@code false} otherwise.
	 */
	public static boolean jLogin(String acc) {
		return LoginScreen.login(acc);
	}

	/**
	 * Selects the character with the specified name.
	 *
	 * @param String name - The name of the character to select.
	 * @return A {@code Boolean} value. {@code true} if the character was successfully selected, {@code false} otherwise.
	 */
	public static boolean jSelectChar(String name) {
		return Charlist.choose_player(name);
	}
	
	/**
	 * Checks if a character selection list is currently displayed on the screen.
	 *
	 * @return A {@code Boolean} value. {@code true} if the character selection list is present, {@code false} otherwise.
	 */
	public static boolean jHaveCharlist(){
		return JSBotUtils.haveCharlist();
	}

	/**
	 * Checks if the character is currently under aggro.
	 *
	 * @return A {@code Boolean} value. {@code true} if the character is under aggro, {@code false} otherwise.
	 */
	public static boolean jHaveAggro() {
		return JSBotUtils.haveAggro;
	}

	/**
	 * Retrieves the belief counter value for a specified parameter.
	 * The belief window must be open.
	 *
	 * @param String name - The name of the belief. Acceptable values: life, night, civil, nature, martial, change.
	 * @return An {@code Integer} value of the belief value ranging from {@code -5} to {@code 5} if successful, or {@code -255} in case of an error.
	 */
	public static int jGetBelief(String name) {
		return JSBotUtils.get_belief(name);
	}

	/**
	 * Moves the belief slider. The belief window must be open.
	 *
	 * @param String name - The name of the belief. Acceptable values: life, night, civil, nature, martial, change.
	 * @param Integer val - The value to set, which must be -1 or 1.
	 * @return A {@code Boolean} value. {@code true} if the operation was successful, {@code false} otherwise.
	 */
	public static boolean jBuyBelief(String name, int val) {
		return JSBotUtils.buy_belief(name, val);
	}

	/**
	 * Retrieves a list of current open chats.
	 * For detailed operations with chats, refer to the corresponding documentation.
	 *
	 * @return A {@code JSChat[]} array representing the current open chats.
	 */
	public static JSChat[] jGetChats() {
		return JSBotUtils.getChats();
	}

	/**
	 * Selects the specified option from the context menu.
	 *
	 * @param String name - The name of the menu option to select.
	 */
	public static void jSelectContextMenu(String name) {
		JSBotUtils.selectPopupMenuOpt(name);
	}
	
	/**
	 * Selects the specified option from the popup menu.
	 *
	 * @param String name - The name of the menu option to select.
	 */
	public static void jSelectPopupMenu(String name) {
		jSelectContextMenu(name);
	}
	
	/**
	 * Closes the context menu if it is open.
	 */
	public static void jClosePopup() {
		JSBotUtils.closePopup();
	}

	/**
	 * Sends an action to the server.
	 *
	 * @param String name - The name of the action.
	 * Example: jSendAction("mine");
	 */
	public static void jSendAction(String name) {
		JSBotUtils.sendAction(name);
	}

	/**
	 * Sends an action to the server with two parameters.
	 *
	 * @param String name - The name of the first action.
	 * @param String name2 - The name of the second action.
	 * Example: jSendDoubleAction("craft", "axe");
	 */
	public static void jSendDoubleAction(String name, String name2) {
		JSBotUtils.sendAction(name, name2);
	}

	/**
	 * Outputs a message in-game (where messages like "This land is owned by someone" appear).
	 *
	 * @param String message - The text of the message to display.
	 */
	public static void jInGamePrint(String message) {
		JSBotUtils.slenPrint(message);
	}

	/**
	 * Toggles the player's inventory open or closed based on its current state.
	 */
	public static void jToggleInventory() {
		JSBotUtils.openInventory();
	}

	/**
	 * Toggles the player's equipment window open or closed based on its current state.
	 */
	public static void jToggleEquipment() {
		JSBotUtils.openEquipment();
	}

	/**
	 * Toggles the character sheet window open or closed based on its current state.
	 */
	public static void jToggleSheet() {
		JSBotUtils.openSheet();
	}

	/**
	 * Returns the tooltip value for the specified block in the building window.
	 *
	 * @param String name - The name of the window.
	 * @param Integer pos - The position of the resource block (1-based index, starting from 1).
	 * @return A {@code String} containing the text of the tooltip.
	 */
	public static String jGetBuildToolTip(String name, int pos) {
		return JSBotUtils.getISBoxValue(name, pos, 0);
	}

	/**
	 * Retrieves the name of the resource in the specified block of the building window.
	 *
	 * @param String name - The name of the window.
	 * @param Integer pos - The position of the resource block (numbering starts from 1).
	 * @return A {@code String} representing the name of the resource.
	 */
	public static String jGetBuildResName(String name, int pos) {
		return JSBotUtils.getISBoxValue(name, pos, 1);
	}

	/**
	 * Retrieves the values in the specified block of the building window.
	 *
	 * @param String name - The name of the window.
	 * @param Integer pos - The position of the resource block (numbering starts from 1).
	 * @return A {@code String} in the format a/b/c representing the values.
	 */
	public static String jGetBuildValues(String name, int pos) {
		return JSBotUtils.getISBoxValue(name, pos, 2);
	}

	/**
	 * Picks up the item from the building window.
	 *
	 * @param String name - The name of the building window.
	 * @param Integer pos - The position of the resource block.
	 */
	public static void jTakeBuildItem(String name, int pos) {
		JSBotUtils.isBoxAct(name, pos, 0);
	}

	/**
	 * Transfers an item (one at a time) from the building window to the player's inventory.
	 *
	 * @param String name - The name of the building window.
	 * @param Integer pos - The position of the resource block.
	 */
	public static void jTransferBuildItem(String name, int pos) {
		JSBotUtils.isBoxAct(name, pos, 1);
	}

	/**
	 * Checks if the specified window is open.
	 *
	 * @param String wnd - The name of the window.
	 * @return A {@code Boolean} value. {@code true} if the window is open, {@code false} otherwise.
	 */
	public static boolean jHaveWindow(String wnd) {
		return JSBotUtils.haveWindow(wnd);
	}

	/**
	 * Checks if the current cursor matches the specified name.
	 *
	 * @param String cur - The name of the cursor (can be viewed using Debug Mode [Ctrl + D] ).
	 * @return A {@code Boolean} value. {@code true} if the names match, {@code false} otherwise.
	 */
	public static boolean jIsCursor(String cur) {
		cur = cur.toLowerCase();
		return JSBotUtils.isCursorName(cur);
	}

	/**
	 * Retrieves the name of the current cursor.
	 *
	 * @return A {@code String} representing the name of the cursor.
	 */
	public static String jGetCursor() {
		return JSBotUtils.getCursorName();
	}

	/**
	 * Retrieves the Equipment Window if open.
	 * Available methods can be found in the corresponding documentation.
	 *
	 * @return An {@code JSEquip} object if the equipment window is open; {@code null} otherwise.
	 */
	public static JSEquip jGetJSEquip(){
		if(JSBotUtils.haveWindow("Equipment"))
			return new JSEquip();
		return null;
	}

	/**
	 * Drops the object currently held by the cursor onto the ground.
	 *
	 * @param Integer mod - The keyboard modifier (1 for SHIFT; 2 for CTRL; 4 for ALT; 8 for WIN).
	 */
	public static void jDropObject(int mod) {
		JSBotUtils.dropObj(mod);
	}

	/**
	 * Retrieves the item currently being dragged by the cursor.
	 *
	 * @return A {@code JSItem} object, or {@code null} if nothing is being held.
	 */
	public static JSItem jGetDraggingItem() {
		return JSBotUtils.getItemDrag();
	}

	/**
	 * Checks for the presence of a crafting window.
	 *
	 * @param String wnd - The name of the crafting window.
	 * @return A {@code Boolean} value. {@code true} if the crafting window exists; {@code false} otherwise.
	 */
	public static boolean jHaveCraft(String wnd) {
		return JSBotUtils.checkCraft(wnd);
	}

	/**
	 * Waits for the specified crafting window to appear.
	 *
	 * @param String wnd - The name of the crafting window.
	 * @param Integer timeout - The maximum time to wait in milliseconds.
	 */
	public static void jWaitCraft(String wnd, int timeout) {
		int cur = 0;
		while (true) {
			if(cur > timeout)
				break;
			if (UI.instance.make_window != null)
				if ((UI.instance.make_window.is_ready) &&
						(UI.instance.make_window.craft_name.equals(wnd))) return;
			if (!JSBot.Sleep(25)) return;
			cur += 25;
		}
	}
	
	/**
	 * Closes the crafting window if it is open.
	 */
	public static void jCloseCraft() {
		if(UI.instance.make_window != null)
			UI.instance.make_window.closeMe();
	}

	/**
	 * Crafts an item.
	 *
	 * @param Boolean all - If {@code true}, crafts all items; otherwise, crafts one item.
	 */
	public static void jCraftItem(boolean all) {
		if(all)
			JSBotUtils.craftItem(1);
		else
			JSBotUtils.craftItem(0);
	}

	/**
	 * Enables or disables rendering.
	 *
	 * @param Boolean b - If {@code true}, rendering is enabled; otherwise, it is disabled.
	 */
	public static void jSetRendering(boolean b) {
		JSBotUtils.setRenderMode(b);
	}

	/**
	 * Retrieves all current buffs.
	 * For operations with buffs, refer to the corresponding documentation.
	 *
	 * @return An array of {@code JSBuff[]} objects representing the current buffs.
	 */
	public static JSBuff[] jGetBuffs(){
		return JSBotUtils.getBuffs();
	}

	/**
	 * Checks for the presence of a context menu.
	 *
	 * @return A {@code Boolean} value. {@code true} if a context menu is open; {@code false} otherwise.
	 */
	public static boolean jHavePopup() {
		return JSBotUtils.havePopupMenu();
	}

	/**
	 * Checks if a specified option exists in the context menu.
	 *
	 * @param String opt - The name of the menu option.
	 * @return A {@code Boolean} value. {@code true} if the context menu contains the specified option; {@code false} otherwise.
	 */
	public static boolean jHavePopupOption(String opt) {
		if(!JSBotUtils.havePopupMenu()) return false;
		return JSBotUtils.popupBtn(opt);
	}

	/**
	 * Checks for the presence of a progress indicator (hourglass).
	 *
	 * @return A {@code Boolean} value. {@code true} if the hourglass is present; {@code false} otherwise.
	 */
	public static boolean jHaveHourglass() {
		return JSBotUtils.hourGlass;
	}

	/**
	 * Retrieves the current movement speed of the character (range 0 to 3).
	 *
	 * @return An {@code Integer} representing the speed.
	 */
	public static int jGetSpeed() {
		return JSBotUtils.getSpeed();
	}

	/**
	 * Sets the current movement speed of the character.
	 *
	 * @param Integer speed - The movement speed of the character (range 0 to 3).
	 */
	public static void jSetSpeed(int speed) {
		if(speed < 0)
			speed = 0;
		else if(speed > 3)
			speed = 3;
		JSBotUtils.setSpeed(speed);
	}

	/**
	 * Retrieves the absolute hunger level of the character.
	 *
	 * @return An {@code Integer} representing the hunger level.
	 */
	public static int jGetHungry() {
		return JSBotUtils.playerHungry;
	}

	/**
	 * Retrieves the soft HP (Health Points) of the player.
	 *
	 * @return An {@code Integer} representing the soft HP.
	 */
	public static int jGetSHP() {
		return JSBotUtils.playerSHP;
	}

	/**
	 * Retrieves the hard HP (Health Points) of the player.
	 *
	 * @return An {@code Integer} representing the hard HP.
	 */
	public static int jGetHHP() {
		return JSBotUtils.playerHHP;
	}

	/**
	 * Retrieves the maximum HP (Health Points) of the player.
	 *
	 * @return An {@code Integer} representing the maximum HP.
	 */
	public static int jGetMHP() {
		return JSBotUtils.playerMHP;
	}

	/**
	 * Retrieves the stamina level of the character.
	 *
	 * @return An {@code Integer} representing the stamina.
	 */
	public static int jGetStamina() {
		return JSBotUtils.playerStamina;
	}

	/**
	 * Retrieves the unique identifier of the character.
	 *
	 * @return An {@code Integer} representing the character ID.
	 */
	public static int jGetMyID() {
		return JSBotUtils.playerID;
	}
	
	/**
	 * Retrieves the character object as a JSGob.
	 *
	 * @return A {@code JSGob} object representing the character.
	 */
	public static JSGob jGetMyGob() {
		return new JSGob(jGetMyID());
	}

	/**
	 * Checks if the character is currently moving.
	 *
	 * @return A {@code Boolean} value. {@code true} if the character is moving; {@code false} otherwise.
	 */
	public static boolean jIsMoving() {
		return JSBotUtils.isMoving();
	}

	/**
	 * Checks if the character is holding an item (on the cursor).
	 *
	 * @return A {@code Boolean} value. {@code true} if the character is dragging an item; {@code false} otherwise.
	 */
	public static boolean jIsDragging() {
		return JSBotUtils.isDragging();
	}

	/**
	 * Checks if the crafting window is ready.
	 *
	 * @return A {@code Boolean} value. {@code true} if the crafting window is ready; {@code false} otherwise.
	 */
	public static boolean jIsCraftReady() {
		return JSBotUtils.isCraftReady();
	}

	/**
	 * Plays a beep sound for the specified duration without interrupting script execution.
	 *
	 * @param Integer msec - The duration in milliseconds to play the beep sound.
	 */
	public static void jPlayBeep(final int msec) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Music.startPlayBeep();
				JSBot.Sleep(msec);
				Music.stopPlayBeep();
			}
		}).run();
	}
	
	/**
	 * Plays a sound a specified number of times with a defined delay between plays.
	 *
	 * @param String resname - The name of the resource from the res/sfx folder (e.g., jPlaySound("sfx/chop", 500, 5);).
	 * @param Integer delay - The delay in milliseconds between each sound play.
	 * @param Integer times - The number of times to repeat the sound.
	 */
	public static void jPlaySound(final String resname, final int delay, final int times) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Resource alert_sound = Resource.fromFile(resname);
				if (alert_sound == null) {
						System.out.println("file does not exists.");
						return;
				}
				int ltimes;
				if (times < 1)
					ltimes = 1;
				else
					ltimes = times;
				int t = 0;
				while (t != ltimes) {
					Audio.play(alert_sound);
					JSBot.Sleep(delay);
					++t;
				}
			}
		}).run();
	}

	/**
	 * Waits for the context menu to appear.
	 *
	 * @param Integer timeout - The maximum time to wait in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the menu appears within the timeout; {@code false} otherwise.
	 */
	public static boolean jWaitPopup(int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(!JSBotUtils.havePopupMenu())
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for the player's character to start moving.
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the movement starts within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitStartMove(int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(!JSBotUtils.isMoving())
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for the player's character to stop moving.
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the movement stops within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitEndMove(int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(JSBotUtils.isMoving())
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for the player's character to start moving, then waits for it to stop.
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 */
	public static void jWaitMove(int timeout) {
		if (!jWaitStartMove(timeout)) return;
		jWaitEndMove(timeout);
	}

	/**
	 * Waits for the specified object (Gob) to start moving.
	 *
	 * @param Integer gob - The ID of the object.
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the object starts moving within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitStartMoveGob(int gob, int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(!jGob(gob).isMoving())
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for the specified object (Gob) to stop moving.
	 *
	 * @param Integer gob - The ID of the object.
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the object stops moving within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitEndMoveGob(int gob, int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(jGob(gob).isMoving())
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for the specified object (Gob) to start moving, then waits for it to stop.
	 *
	 * @param Integer gob - The ID of the object.
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 */
	public static void jWaitMoveGob(int gob, int timeout) {
		if (!jWaitStartMoveGob(gob, timeout)) return;
		jWaitEndMoveGob(gob, timeout);
	}

	/**
	 * Waits for the specified cursor to appear.
	 *
	 * @param String name - The name of the cursor.
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the cursor appears within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitCursor(String name, int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(!JSBotUtils.getCursorName().equals(name))
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for the specified window to appear.
	 *
	 * @param String name - The name of the window.
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the window appears within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitWindow(String name, int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(curr < timeout)
		{
			if(JSBotUtils.lastCreatedWindow != null &&
					JSBotUtils.lastCreatedWindow.cap.text != null &&
					JSBotUtils.lastCreatedWindow.cap.text.equals(name)){
				JSBotUtils.lastCreatedWindow = null;
				return true;
			}
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return false;
	}

	/**
	 * Waits for the specified window to appear and returns it if it appears within the timeout period.
	 *
	 * @param String name - The name of the window.
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return The {@code JSWindow} object if the window appears, or {@code null} otherwise.
	 */
	public static JSWindow jWaitNewWindow(String name, int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(curr < timeout) {
			if (JSBotUtils.lastCreatedWindow != null &&
				JSBotUtils.lastCreatedWindow.cap.text != null &&
				JSBotUtils.lastCreatedWindow.cap.text.equals(name)) {
				
				int rid = UI.instance.getId(JSBotUtils.lastCreatedWindow);
				if (!(UI.instance.getWidget(rid) instanceof Window)) continue;
				JSWindow ret = new JSWindow(rid);
				JSBotUtils.lastCreatedWindow = null;
				return ret;
			}
			if (!JSBot.Sleep(25)) return null;
			curr += 25;
		}
		return null;
	}

	/**
	 * Clears the last created window. It is recommended to call this at the beginning of the script if working with windows.
	 */
	public static void jDropLastWindow() {
		JSBotUtils.lastCreatedWindow = null;
	}

	/**
	 * Waits for the progress action to start.
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if progress starts within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitStartProgress(int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(!JSBotUtils.hourGlass)
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for a full progress cycle to complete.
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 */
	public static void jWaitProgress(int timeout) {
		if (!jWaitStartProgress(timeout)) return;
		jWaitEndProgress(timeout);
	}

	/**
	 * Waits for the progress action to end.
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if progress ends within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitEndProgress(int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(JSBotUtils.hourGlass)
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for an item to appear on the cursor (e.g., when something is picked up).
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if an item appears on the cursor within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitDrag(int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(!JSBotUtils.isDragging())
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}

	/**
	 * Waits for an item to disappear from the cursor (e.g., when something is dropped).
	 *
	 * @param Integer timeout - The maximum waiting time in milliseconds.
	 * @return A {@code Boolean} value. {@code true} if the item is dropped within the timeout period, {@code false} otherwise.
	 */
	public static boolean jWaitDrop(int timeout) {
		int curr = 0; if(timeout == 0) timeout = 10000;
		while(JSBotUtils.isDragging())
		{
			if(curr > timeout)
				return false;
			if (!JSBot.Sleep(25)) return false;
			curr += 25;
		}
		return true;
	}
	
	/***************************************************************
	 * Below is map-related functionality
	 ***************************************************************/

	/**
	 * Retrieves an array of {@code JSGob} objects within the specified radius and offset from the player (in tiles).
	 * 
	 * @param Integer rad - The search radius (in tiles).
	 * @param jCoord offset - The offset from the player (in tiles).
	 * @param String[] mask - The enumeration of required object names (substring of resource; 
	 *                      "!" at the beginning excludes the object from the search).
	 * @return A {@code JSGob[]} array of objects.
	 */
	public static JSGob[] jGetObjects(int rad, Object offset, Object mask) {
		return JSBotUtils.objectIdList(rad, unWrapCoord(offset), unWrapStringArray(mask));
	}
	
	/**
	 * Retrieves an array of {@code JSGob} objects with the specified name, BLOB, and within the specified rectangle.
	 * 
	 * @param jCoord absPos - Absolute coordinates of the rectangle's starting point (northwest corner).
	 * @param jCoord size - The size of the rectangle.
	 * @param Integer blob - The BLOB of the object.
	 * @param String[] mask - The enumeration of required object names (substring of resource;
	 *                      "!" at the beginning excludes the object from the search).
	 * @return A {@code JSGob[]} array of objects.
	 */
	public static JSGob[] jGetObjectsInRect(Object absPos, Object size, int blob, Object mask) {
		return JSBotUtils.getObjectsInRect(unWrapCoord(absPos), unWrapCoord(size), blob,
				unWrapStringArray(mask));
	}

	/**
	 * Retrieves the tile type at the specified offset from the player (in tiles).
	 * 
	 * @param jCoord offset - The offset from the player (in tiles).
	 * @return An {@code Integer} representing the tile type, or {@code -1} if unable to get the tile type.
	 */
	public static int jGetTileType(Object offset) {
		Coord c = unWrapCoord(offset);
		return JSBotUtils.tileType(c.x, c.y);
	}
	
	/**
	 * Retrieves the tile type at the specified absolute coordinates.
	 * 
	 * @param jCoord absPos - The absolute coordinates.
	 * @return An {@code Integer} representing the tile type.
	 */
	public static int jAbsTileType(Object absPos) {
		Coord c = unWrapCoord(absPos);
		return JSBotUtils.absTileType(c.x, c.y);
	}

	/**
	 * Sends a mouse click event to the server for the specified object.
	 * 
	 * @param Integer objid - The object identifier.
	 * @param Integer btn - The mouse button (1 for left, 3 for right).
	 * @param Integer mod - The keyboard modifier (1 for SHIFT, 2 for CTRL, 4 for ALT, 8 for WIN).
	 */
	public static void jDoClick(int objid, int btn, int mod) {
		JSBotUtils.doClick(objid, btn, mod);
	}

	/**
	 * Moves towards the specified point using pathfinding (PF). Coordinates should be absolute.
	 * 
	 * @param jCoord pos - The coordinates of the destination point.
	 * @return An {@code Integer} value representing the number of path segments. Returns {@code 0} if the path is not found.
	 */		
	public static int jPFMove(Object pos) {
		return UI.instance.mapview.map_pf_move(unWrapCoord(pos));
	}

	/**
	 * Moves toward the specified object using pathfinding (PF) and right-clicks on the it.
	 * 
	 * @param int id - The object identifier.
	 * @return An {@code Integer} value representing the number of path segments. Return
	 */
	public static int jPFClick(int id) {
		return UI.instance.mapview.map_pf_interact(id);
	}

	/**
	 * Sends a click event to the map at the specified coordinates relative to the player (in tiles).
	 * 
	 * @param jCoord pos - The coordinates.
	 * @param Integer btn - The mouse button (1 for left, 3 for right).
	 * @param Integer mod - The keyboard modifier (1 for SHIFT, 2 for CTRL, 4 for ALT, 8 for WIN).
	 */
	public static void jOffsetClick(Object pos, int btn, int mod) {
		Coord c = unWrapCoord(pos);
		JSBotUtils.mapClick(c.x, c.y, btn, mod);
	}

	/**
	 * Sends a click event to the map at the specified absolute coordinates.
	 * 
	 * @param jCoord pos - The coordinates.
	 * @param Integer btn - The mouse button (1 for left, 3 for right).
	 * @param Integer mod - The keyboard modifier (1 for SHIFT, 2 for CTRL, 4 for ALT, 8 for WIN).
	 */
	public static void jAbsClick(Object pos, int btn, int mod) {
		Coord c = unWrapCoord(pos);
		JSBotUtils.mapAbsClick(c.x, c.y, btn, mod);
	}

	/**
	 * Moves the player by the specified number of tiles relative to their current position.
	 * 
	 * @param jCoord pos - The movement coordinates.
	 */
	public static void jMoveStep(Object pos) {
		Coord c = unWrapCoord(pos);
		JSBotUtils.mapMoveStep(c.x, c.y);
	}

	/**
	 * Returns the player's coordinates.
	 * 
	 * @return A {@code jCoord} containing the player's coordinates.
	 */
	public static Coord jMyCoords() {
		return JSBotUtils.MyCoord();
	}

	/**
	 * Interacts with a point on the map (in tiles) relative to the player using an item held in hand (on cursor).
	 * 
	 * @param jCoord pos - The offset coordinates.
	 * @param Integer mod - The keyboard modifier (1 for SHIFT, 2 for CTRL, 4 for ALT, 8 for WIN).
	 */
	public static void jInteractClick(Object pos, int mod) {
		Coord c = unWrapCoord(pos);
		JSBotUtils.mapInteractClick(c.x, c.y, mod);
	}

	/**
	 * Interacts with a point on the map (in absolute coordinates) using an item held in hand.
	 * 
	 * @param jCoord pos - The absolute coordinates.
	 * @param Integer mod - The keyboard modifier (1 for SHIFT, 2 for CTRL, 4 for ALT, 8 for WIN).
	 */
	public static void jAbsInteractClick(Object pos, int mod) {
		Coord c = unWrapCoord(pos);
		JSBotUtils.mapAbsInteractClick(c.x, c.y, mod);
	}

	/**
	 * Places the object being built at the specified coordinates (in tiles) relative to the player.
	 * 
	 * @param jCoord pos - The offset coordinates from the player.
	 * @param Integer btn - The mouse button (1 for left, 3 for right).
	 * @param Integer mod - The keyboard modifier (1 for SHIFT, 2 for CTRL, 4 for ALT, 8 for WIN).
	 */
	public static void jPlace(Object pos, int btn, int mod) {
		Coord c = unWrapCoord(pos);
		JSBotUtils.mapPlace(c.x, c.y, btn, mod);
	}

	/**
	 * Checks if the path to the specified point is free of obstacles.
	 * 
	 * @param jCoord pos - The absolute coordinates of the point.
	 * @return A {@code Boolean} value. {@code true} if the path is clear, {@code false} otherwise.
	 */
	public static boolean jIsPathFree(Object pos) {
		return APXUtils.isPathFree(unWrapCoord(pos));
	}

	/**
	 * Prompts the player to select an object with the mouse. The script will pause until an object is selected.
	 * 
	 * @param String text - The in-game message to be displayed.
	 * @return A {@code JSGob} object representing the selected object, or {@code null} if no object is selected.
	 */
	public static JSGob jSelectObject(String text) {
		if (UI.instance.mapview == null)
			return null;
		JSBotUtils.slenPrint(text);
		UI.instance.mapview.objectSelecting = true;
		while (UI.instance.mapview.objectSelecting) {
			if (!JSBot.Sleep(100)) return null;
		}
		if (UI.instance.mapview.objectUnderMouse != null) {
			return new JSGob(UI.instance.mapview.objectUnderMouse.id);
		}
		return null;
	}

	/**
	 * Retrieves a {@code JSGob} object with the specified resource name within the specified radius (in tiles) from the player.
	 * 
	 * @param String name - The resource name.
	 * @param Integer rad - The radius from the player (in tiles).
	 * @return A {@code JSGob} object, or {@code null} if none is found.
	 */
	public static JSGob jFindObjectByName(String name, int rad) {
		return JSBotUtils.findObjectByName(name, rad);
	}

	/**
	 * Finds a {@code JSGob} object within the specified radius and offset from the player.
	 * 
	 * @param String name - The resource name.
	 * @param Integer rad - The search radius (in map points).
	 * @param jCoord pos - The offset coordinates from the player.
	 * @return The nearest {@code JSGob} object, or {@code null} if none is found.
	 */
	public static JSGob jFindObjectWithOffset(String name, int rad, Object pos) {
		Coord c = unWrapCoord(pos);
		return JSBotUtils.findMapObject(name, rad, c.x, c.y);
	}

	/**
	 * Retrieves the 'centered' tile coordinates based on the provided coordinates.
	 * 
	 * @param jCoord pos - The input coordinates.
	 * @return A {@code jCoord} containing the centered tile coordinates.
	 */
	public static Coord jTilify(Object pos) {
		Coord c = unWrapCoord(pos);
		return JSBotUtils.tilify(c);
	}
	
	/**
	 * Retrieves the absolute coordinates of the nearest tile with the specified type.
	 * 
	 * @param Integer radius - The search radius (in tiles).
	 * @param Integer type - The tile type.
	 * @return A {@code jCoord} containing the absolute coordinates of the tile.
	 */
	public static Coord jGetNearestTileAbs(int raduis, int type) {
		return jMyCoords().add(jGetNearestTileCoord(raduis, type).mul(11));
	}
	
	/**
	 * Retrieves the coordinates of the nearest tile with the specified type, offset from the player.
	 * 
	 * @param Integer radius - The search radius (in tiles).
	 * @param Integer type - The tile type.
	 * @return A {@code jCoord} containing the offset coordinates from the player, or {@code null} if no matching tile is found.
	 */
	public static Coord jGetNearestTileCoord(int raduis, int type) {
		double maxRealRad = 100000;
		Coord ret = null;
		for (int step = 1; step < raduis; step++) {
			for (int i = 0; i < step; i++) {
				int x, y;
				x = i - step;
				y = i;
				if (JSBotUtils.tileType(x, y) == type) {
					double realRad = Math.sqrt(x * x + y * y);
					if (realRad < maxRealRad) {
						maxRealRad = realRad;
						ret = new Coord(x, y);
						raduis += 2;
						continue;
					}
				}
				x = i;
				y = step - i;
				if (JSBotUtils.tileType(x, y) == type) {
					double realRad = Math.sqrt(x * x + y * y);
					if (realRad < maxRealRad) {
						maxRealRad = realRad;
						ret = new Coord(x, y);
						raduis += 2;
						continue;
					}
				}
				x = step - i;
				y = -i;
				if (JSBotUtils.tileType(x, y) == type) {
					double realRad = Math.sqrt(x * x + y * y);
					if (realRad < maxRealRad) {
						maxRealRad = realRad;
						ret = new Coord(x, y);
						raduis += 2;
						continue;
					}
				}
				x = -i;
				y = i - step;
				if (JSBotUtils.tileType(x, y) == type) {
					double realRad = Math.sqrt(x * x + y * y);
					if (realRad < maxRealRad) {
						maxRealRad = realRad;
						ret = new Coord(x, y);
						raduis += 2;
						continue;
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Draws a rectangular area on the ground. Purely a visual feature.
	 * To disable the area drawing, call this function with zero values for size.
	 * 
	 * @param jCoord offset - The offset from the player (in tiles).
	 * @param jCoord size - The size of the rectangle.
	 */
	public static void jDrawGroundRect(Object offset, Object size) {
		JSBotUtils.drawGroundRect(unWrapCoord(offset), unWrapCoord(size));
	}
	
	/**
	 * Creates a window.
	 * 
	 * @param jCoord position - The position of the window relative to the screen (in pixels).
	 * @param jCoord size - The size of the window (in pixels).
	 * @param String caption - The title of the window.
	 * @return A {@code JSGUI_Window} representing the window created.
	 */
	public static JSGUI_Window jGUIWindow(Object position, Object size, String caption) {
		return JSGUI.createWindow(unWrapCoord(position), unWrapCoord(size), caption);
	}
	
	/**
	 * Creates a text label.
	 * 
	 * @param JSGUI_Window parent - The parent element (window).
	 * @param jCoord position - The position within the parent (in pixels).
	 * @param String text - The text for the label.
	 * @return A {@code JSGUI_Label} representing the label created.
	 */
	public static JSGUI_Label jGUILabel(Object parent, Object position, String text) {
		return JSGUI.createLabel(JSGUI.unWrapGUI_Widget(parent), unWrapCoord(position), text);
	}
	
	/**
	 * Creates a button.
	 * 
	 * @param JSGUI_Window parent - The parent element (window).
	 * @param jCoord position - The position within the parent (in pixels).
	 * @param Integer size - The width of the button (in pixels).
	 * @param String caption - The text in the button.
	 * @return A {@code JSGUI_Button} representing the button created.
	 */
	public static JSGUI_Button jGUIButton(Object parent, Object position, int size, String caption) {
		return JSGUI.createButton(JSGUI.unWrapGUI_Widget(parent), unWrapCoord(position), size, caption);
	}
	
	/**
	 * Creates a text entry field.
	 * 
	 * @param JSGUI_Window parent - The parent element (window).
	 * @param jCoord position - The position within the parent (in pixels).
	 * @param jCoord size - The size of the entry field (in pixels).
	 * @param String deftext - The default text in the entry field.
	 * @return A {@code JSGUI_TextEntry} representing the text entry field created.
	 */
	public static JSGUI_TextEntry jGUIEntry(Object parent, Object position, Object size, String deftext) {
		return JSGUI.createEntry(JSGUI.unWrapGUI_Widget(parent), unWrapCoord(position),
				unWrapCoord(size), deftext);
	}
	
	/**
	 * Creates a checkbox.
	 * 
	 * @param JSGUI_Window parent - The parent element (window).
	 * @param jCoord position - The position within the parent (in pixels).
	 * @param String text - The text for the checkbox.
	 * @return A {@code JSGUI_CheckBox} representing the checkbox created.
	 */
	public static JSGUI_CheckBox jGUICbox(Object parent, Object position, String text) {
		return JSGUI.createBox(JSGUI.unWrapGUI_Widget(parent), unWrapCoord(position), text);
	}
	
	
	/**
	 * Retrieves the resource name of the object from a container inventory (e.g., boats, carts).
	 * 
	 * @param String window - The name of the window.
	 * @param Integer pos - The position in the window.
	 * @return A {@code String} value containing the full resource name of the object.
	 */
	public static String jImgName(String window, int pos) {
		return JSBotUtils.getWindowImg(window, pos);
	}
	
	/**
	 * Retrieves the total number of slots in a container (e.g., boats, carts).
	 * 
	 * @param String window - The name of the window.
	 * @return An {@code Integer} value representing the total number of slots.
	 */
	public static int jImgSlotsCount(String window) {
		return JSBotUtils.windowImgs(window, true);
	}
	
	/**
	 * Retrieves the number of empty slots in a container (e.g., boats, carts).
	 * 
	 * @param String window - The name of the window.
	 * @return An {@code Integer} value representing the number of empty slots.
	 */
	public static int jImgFreeSlots(String window) {
		return (JSBotUtils.windowImgs(window, true) - JSBotUtils.windowImgs(window, false));
	}
	
	/**
	 * Sends a click to the specified slot in the container window.
	 * 
	 * @param String window - The name of the window.
	 * @param Integer pos - The position.
	 * @param Integer btn - The mouse button (1 for left, 3 for right).
	 * @param Integer mod - The keyboard modifier (1 for SHIFT, 2 for CTRL, 4 for ALT, 8 for WIN).
	 */
	public static void jImgClick(String window, int pos, int btn, int mod) {
		JSBotUtils.imgClick(window, pos, btn, mod);
	}
	
	/**
	 * Sets the hearth secret string in the kin window.
	 * 
	 * @param String hs - The Hearth Secret (HS) string.
	 */
	public static void jBuddySetHS(String hs) {
		BuddyWnd.instance.setHSText(hs);
	}
	
	/**
	 * Retrieves the name of the currently selected kin.
	 * 
	 * @return A {@code String} containing the name of the kin.
	 */
	public static String jBuddyDumpCurrentName() {
		return BuddyWnd.instance.dumpCurrentSelectedName();
	}
	
	/**
	 * Retrieves information about the clothing for the currently selected kin.
	 * 
	 * @return A {@code String} containing the clothing information for the kin.
	 */
	public static String jBuddyDumpCurrentInfo() {
		return BuddyWnd.instance.dumpCurrentSelectedInfo();
	}
	
	/**
	 * Checks if the information about the kins have changed (the flag resets if it has).
	 * 
	 * @return A {@code Boolean} value. {@code true} if the information has changed, otherwise {@code false}.
	 */
	public static boolean jBuddyInfoChanged() {
		return BuddyWnd.instance.buddyInfoChanged();
	}
	
	/**
	 * Forgets the currently selected kin.
	 */
	public static void jBuddyForgetCurrent() {
		BuddyWnd.instance.forgetCur();
	}
	
	/**
	 * Toggles the display of warnings for deprecated functions.
	 * 
	 * @param Boolean show - {@code false} disables the warnings, {@code true} enables them.
	 */
	public static void jShowWarnings(boolean show) {
		JSBotUtils.sWarnings = show;
	}
	
	/**
	 * Retrieves an array of all of the kins from the kin list.
	 * 
	 * @return A {@code String[]} array containing the list of kins.
	 */
	public static String[] jBuddyList() {
		return BuddyWnd.instance.allBuddies();
	}
	
	/**
	 * Retrieves the JSGob object by its identifier (wrapper for deprecated functions).
	 * 
	 * @param Integer gob - The object ID.
	 * @return A {@code JSGob} representing the object with the specified ID.
	 */
	public static JSGob jGob(int gob) {
		return new JSGob(gob);
	}
	
	/**
	 * Checks if the player is in a party with others.
	 * 
	 * @return A {@code Boolean} value. {@code true} if the player is in a party, otherwise {@code false}.
	 */
	public static boolean jHaveParty() {
		return JSBotUtils.haveParty();
	}
	
	/**
	 * Sends a click to a party portrait.
	 * 
	 * @param Integer index - The index of the portrait.
	 * @param Integer btn - The mouse button.
	 */
	public static void jPartyClick(int index, int btn) {
		if (Partyview.instace != null) {
			Partyview.instace.click(index, btn);
		}
	}
	
	/**
	 * Checks if the player has aggroed an entity by its ID.
	 * 
	 * @param Integer id - The ID of the entity.
	 * @return A {@code Boolean} value. {@code true} if the entity is aggroed, otherwise {@code false}.
	 */
	public static boolean jYouAggroedId(int id) {
		if (UI.instance.fight != null) {
			return UI.instance.fight.haveID(id);
		}
		return false;
	}
	
	/**
	 * Retrieves the client version.
	 * 
	 * @return A {@code String} containing the client version.
	 */
	public static String jGetVersion() {
		return MainFrame.clientVersion;
	}
	
	/**
	 * Finds a map object matching the name near the given absolute coordinates within the specified radius.
	 * 
	 * @param jCoord coord - The absolute coordinates.
	 * @param Integer radius - The search radius (in pixels).
	 * @param String name - The name of the object.
	 * @return A {@code JSGob} object representing the found map object.
	 */
	public static JSGob jFindMapObjectNearAbs(Object coord, int radius, String name) {
		return JSBotUtils.findMapObjectAbs(name, radius, unWrapCoord(coord));
	}
	
	/**
	 * Leaves the party if one exists.
	 */
	public static void jLeaveParty() {
		if (jHaveParty())
			JSBotUtils.leaveParty();
	}
	
	/**
	 * Prompts the user to select an area on the map with the mouse (like the ruler with [CTRL + L] ).
	 * Returns an array of four coordinates:
	 * 1 - Absolute coordinates of the top-left corner of the area.
	 * 2 - Absolute coordinates of the bottom-right corner of the area.
	 * 3 - Offset from the player in tiles (to the top-left corner).
	 * 4 - Size of the area in tiles.
	 * If canceled, the array contains four {@code null} values.
	 * 
	 * @param jCoord wndpos - Coordinates of the window's position on the screen.
	 * @return A {@code Coord[]} array containing the selected area coordinates.
	 */
	public static Coord[] jAreaSelector(Object wndpos) {
		Coord[] ret = new Coord[4];
		ret = JSBotUtils.areaSelector(unWrapCoord(wndpos));
		while (jHaveWindow("Area Selector"))
			jSleep(500);
		return ret;
	}
	
	/**
	 * Retrieves the current list of FEPs (Food Event Points) in the FEP bar.
	 * 
	 * @return A {@code String[]} array containing the FEPs (possible values: "str", "agil", "intel", "cons", "perc", "csm", "dxt", "psy").
	 */
	public static String[] jGetFepCurrentList() {
		return JSBotUtils.getFepList();
	}
	
	/**
	 * Retrieves the value of the specified FEP in the FEP bar.
	 * 
	 * @param String fepName - The name of the FEP in the FEP bar (possible values: "str", "agil", "intel", "cons", "perc", "csm", "dxt", "psy").
	 * @return A {@code Double} value of the specified FEP, or {@code 0} if not found.
	 */
	public static double jGetFepCurrentAmount(String fepName) {
		return JSBotUtils.getFepByName(fepName);
	}
	
	/**
	 * Retrieves the current cap (maximum value) of the FEP bar (After food).
	 * 
	 * @return A {@code Double} value representing the FEP bar cap.
	 */
	public static double jGetFepCurrentCap() {
		return JSBotUtils.getCurrentFepCap();
	}
	
	/**
	 * Retrieves the maximum value of the FEP bar (Before any food).
	 * 
	 * @return The maximum FEP value.
	 */
	public static int jGetFepMaxValue() {
		return JSBotUtils.getMaxStatValue();
	}
	
	/**
	 * Retrieves the name of the stat with the highest FEP value.
	 * 
	 * @return A {@code String} representing the name of the stat with highest FEP value. (possible values: "str", "agil", "intel", "cons", "perc", "csm", "dxt", "psy").
	 */
	public static String jGetFepMaxName() {
		return JSBotUtils.getMaxStatName();
	}
	
	/**
	 * Returns the base value (without buffs) of the specified stat.
	 * 
	 * @param String name - The name of the stat. Possible values:
	 * FEPs: "str", "agil", "intel", "cons", "perc", "csm", "dxt", "psy"
	 * Skills: "unarmed", "melee", "ranged", "explore", "stealth", "sewing", "smithing", "carpentry", "cooking", "farming", "survive"
	 * Beliefs: "life", "night", "civil", "nature", "martial", "change"
	 * LA: "expmod"
	 * @return An {@code Integer} representing the current value of the stat, or {@code 0} if not found.
	 */
	public static int jGetStatByName(String name) {
		return JSBotUtils.getStat(name);
	}
}