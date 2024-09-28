package union.jsbot;

import union.JSBotUtils;
import haven.*;

public class JSGob {
	private int gob_id;
	
	public JSGob(int id) {
		gob_id = id;
	}
	
	/**
     * Checks if the object is a player character.
     *
     * @return A {@code Boolean} value. {@code true} if the object is a player, {@code false} otherwise.
     */
	public boolean isPlayer() {
		return gob().isPlayer();
	}
	
	/**
     * Checks if the object id is in a party with you.
     *
     * @return A {@code Boolean} value. {@code true} if the object is in your party, {@code false} otherwise.
     */
	public boolean isInParty() {
		synchronized (JSBotUtils.glob.party.memb) {
			for (Party.Member m : JSBotUtils.glob.party.memb.values()) {
				if (m.gobid == gob_id)
					return true;
			}
			return false;
		}
	}

	/**
     * Checks if the object is of a specific colored kin group in your kin list.
     *
     * @param Integer group -  The group number in your kin list (starting from 0).
     * [ 0 - White
     *   1 - Green
     *   2 - Red
     *   3 - Blue
     *   4 - Teal
     *   5 - Yellow
     *   6 - Purple
     *   7 - Orange ]
     * @return A {@code Boolean} value. {@code true} if the object is the specific color in your kin list, {@code false} otherwise.
     */
	public boolean isGroupKin(int group) {
		return gob().isGroupKin(group);
	}
	
	/**
     * Checks if the object is in your kin list.
     *
     * @return A {@code Boolean} value. {@code true} if the object is in your kin list, {@code false} otherwise.
     */
	public boolean isKin() {
		return gob().isKin();
	}
	
	/**
     * Checks if the object belongs to the same village as you.
     *
     * @return A {@code Boolean} value. {@code true} if the object is in your village, {@code false} otherwise.
     */
	public boolean isInYourVillage() {
		return gob().isInYourVillage();
	}
	
	/**
     * Retrieves the size of the object's hitbox, if available.
     *
     * @return A {@code jCoord} object containing the dimensions of the hitbox, or a zero-sized {@code jCoord} object if not available.
     */
	public Coord negSize() {
		if (gob().getneg() != null)
			return gob().getneg().bs;
		return new Coord(0, 0);
	}
	
	 /**
     * Retrieves the group to which this object is colored in your kin list.
     *
     * @return A {@code Integer} value of the group number (starting from 0), or -1 if the object is not in your kin list.
     */
	public int getKinGroup() {
		return gob().getKinGroup();
	}
	
	/**
     * Retrieves the kin type for this object (contains a village flag)... [Not entirely sure what this is tbh. Don't feel like researching.]
     *
     * @return A {@code Integer} value of the kin type for this object.
     */
	public int getKinType() {
		return gob().getKinType();
	}
	
	/**
     * Retrieves the identifier of the object.
     *
     * @return A {@code Integer} value of the object's ID.
     */
	public int getID() {
		return gob_id;
	}
	
	/**
     * Retrieves the health of the object as a percentage.
     *
     * @return A {@code Integer} value of the object's health percentage.
     */
	public int health() {
		return gob().getHealth();
	}
	
	/**
     * Retrieves a value from the blob at the specified index.
     *
     * @param Integer index - The index of the blob value.
     * @return A {@code Integer} of the value from the blob.
     */
	public int blob(int index) {
		return gob().GetBlob(index);
	}
	
	/**
     * Retrieves all of the values from the blob.
     *
     * @return An {@code Integer Array} containing all the values from the blob.
     */
	public int[] blobAll() {
		return gob().getBlob();
	}
	
	 /**
     * Checks if the object exists.
     *
     * @return A {@code Boolean} value. {@code true} if the object exists, {@code false} otherwise.
     */
	public boolean isActual() {
		return gob() != null;
	}
	
	/**
     * Retrieves the absolute coordinates of the object.
     *
     * @return A {@code jCoord} object containing the object's absolute coordinates.
     */
	public Coord position() {
		return gob().position();
	}
	
	/**
     * Checks for the presence of a substring in the resource layers.
     *
     * @param String layer - The substring to search for in the resource layers.
     * @return A {@code Boolean} value. {@code true} if one of the layers contains the specified substring, {@code false} otherwise.
     */
	public boolean hasLayer(String layer) {
		String[] lrs = gob().resnames();
		for(int i = 0; i < lrs.length; i++)
			if(lrs[i].contains(layer)) return true;
		return false;
	}
	
	/**
     * Simulates a click on the object.
     *
     * @param Integer btn - The mouse button (1 for Left Mouse Button, 3 for Right Mouse Button).
     * @param Integer mod - The keyboard modifier (1 for SHIFT; 2 for CTRL; 4 for ALT; 8 for WIN).
     */
	public void doClick(int btn, int mod) {
		if(!isActual()) return;
		if (UI.instance.mapview != null) {
			Coord sz = UI.instance.mapview.sz;
			Coord sc = new Coord((int) Math.round(Math.random() * 200 + sz.x / 2
					- 100), (int) Math.round(Math.random() * 200 + sz.y / 2
					- 100));
			Coord oc = position();
			UI.instance.mapview.wdgmsg("click", sc, oc, btn, mod, gob_id,
					oc);
		}
	}
	
	/**
     * Clicks at a specified offset from the object (in map tiles).
     *
     * @param Coord offset - The offset from the object's position.
     */
	public void offsetMove(Coord offset) {
		if(!isActual()) return;
		if(UI.instance.mapview != null) {
			Coord sz = UI.instance.mapview.sz;
			Coord sc = new Coord((int) Math.round(Math.random() * 200 + sz.x / 2 - 100),
					(int) Math.round(Math.random() * 200 + sz.y / 2 - 100));
			Coord oc = position().add(offset);
			UI.instance.mapview.wdgmsg("click", sc, oc, 1, 0, gob_id, oc);
		}
	}
	
	/**
     * Simulate a click on the object with the item currently in the player's hands (cursor held object).
     *
     * @param Integer mod - The keyboard modifier (1 for SHIFT; 2 for CTRL; 4 for ALT; 8 for WIN).
     */
	public void interactClick(int mod) {
		if(!isActual()) return;
		if (UI.instance.mapview != null) {
			UI.instance.mainview.wdgmsg("itemact", JSBotUtils.getCenterScreenCoord(), position(),
					mod, gob_id, position());
		}
	}
	
	/**
     * Retrieves the full resource name of the object.
     *
     * @return A {@code String} containing the resource name of the object.
     */
	public String name() {
		return gob().resname();
	}
	
	/**
     * Checks if the object is sitting.
     *
     * @return A {@code Boolean} value. {@code true} if the object is sitting, {@code false} otherwise.
     */
	public boolean isSitting() {
		Layered layered = gob().getattr(Layered.class);
		if (layered != null) {
			return layered.containsLayerName("gfx/borka/body/sitting/");
		}
		return false;
	}
	
	 /**
     * Checks if the object is carrying something in its hands (above its head).
     *
     * @return A {@code Boolean} value. {@code true} if the object is carrying something, {@code false} otherwise.
     */
	public boolean isCarrying() {
		Layered layered = gob().getattr(Layered.class);
		if (layered != null) {
			return layered.containsLayerName("gfx/borka/body/standing/arm/banzai/") ||
					layered.containsLayerName("gfx/borka/body/walking/arm/banzai/");
		}
		return false;
	}
	
	 /**
     * Checks if the object is currently moving.
     *
     * @return A {@code Boolean} value. {@code true} if the object is moving, {@code false} otherwise.
     */
	public boolean isMoving() {
		Moving m = gob().getattr(Moving.class);
		if(m == null)
			return false;
		else return true;
	}
	
	/**
     * Checks if the object is lying down.
     *
     * @return A {@code Boolean} value. {@code true} if the object is lying down, {@code false} otherwise.
     */
	public boolean isLaying() {
		Layered layered = gob().getattr(Layered.class);
		if (layered != null) {
			return layered.containsLayerName("gfx/borka/body/dead/");
		}
		return false;
	}
	
	/**
     * Checks if the object is being highlighted.
     *
     * @return A {@code Boolean} value. {@code true} if the object is highlighted, {@code false} otherwise.
     */
	public boolean isOverlayed() {
		return gob().getDrawOlay();
	}
	
	/**
     * Enables or disables the highlight of an object.
     *
     * @param Boolean enabled - {@code true} to enable highlighting, {@code false} to disable.
     */
	public void setOverlay(boolean b) {
		gob().setDrawOlay(b);
	}
	
	private Gob gob() {
		synchronized (JSBotUtils.glob.oc) {
			return JSBotUtils.glob.oc.getgob(gob_id);
		}
	}
}
