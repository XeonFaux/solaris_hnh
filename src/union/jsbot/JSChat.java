package union.jsbot;

import haven.ChatHW;
import haven.UI;

public class JSChat {
	private int remote_id;

	public JSChat(int rid) {
		remote_id = rid;
	}

	/**
	 * Returns the name of the chat.
	 *
	 * @return A {@code String} containing the name of the chat.
	 */
	public String chatName() {
		return wdg().title;
	}

	/**
	 * Sends a message to the chat.
	 *
	 * @param String message - The message to be sent.
	 */
	public void sendMessage(String message) {
		wdg().sendmsg(message);
	}

	/**
	 * Checks if there are any new messages in the chat.
	 *
	 * @return A {@code Boolean} value. {@code true} if there is an unread message, {@code false} otherwise.
	 */
	public boolean haveNewMessage() {
		return wdg().hasNewMessage();
	}
	
	/**
	 * Retrieves the last received message from the chat.
	 *
	 * @return A {@code String} containing the last message received from the chat.
	 */
	public String getLastMessage() {
		return wdg().getLastMessage();
	}

	/**
	 * Closes the current chat.
	 */
	public void closeChat() {
		wdg().closeChat();
	}
	
	/**
	 * Checks if the chat object still exists.
	 *
	 * @return A {@code Boolean} value. {@code true} if the chat object is still active, {@code false} otherwise.
	 */
	public boolean isActual() {
		return wdg() != null;
	}
	
	private ChatHW wdg() {
		return (ChatHW) UI.instance.getWidget(remote_id);
	}
}