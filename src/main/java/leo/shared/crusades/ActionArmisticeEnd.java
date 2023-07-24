///////////////////////////////////////////////////////////////////////
//	Name:	ActionArmisticeEnd
//	Desc:	The armistice is live
//	Date:	5/29/2009 - Gabe Jones
//	TODO:
///////////////////////////////////////////////////////////////////////
package leo.shared.crusades;

// imports

import leo.shared.Action;
import leo.shared.TargetType;
import leo.shared.Unit;

import java.util.Vector;


public class ActionArmisticeEnd implements Action {

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private final Unit owner;
    private final short type = Action.OTHER;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public ActionArmisticeEnd(Unit newOwner) {
        owner = newOwner;
        owner.armistice(true);
    }


    /////////////////////////////////////////////////////////////////
    // Perform the action on the client
    /////////////////////////////////////////////////////////////////
    public String perform(short target) {
        return "Invalid action";
    }


    /////////////////////////////////////////////////////////////////
    // Validate
    /////////////////////////////////////////////////////////////////
    public boolean validate(short target) {
        return false;
    }


    /////////////////////////////////////////////////////////////////
    // Get the targets
    /////////////////////////////////////////////////////////////////
    public Vector<Short> getTargets() {
        return new Vector<Short>();
    }


    /////////////////////////////////////////////////////////////////
    // Get the targets
    /////////////////////////////////////////////////////////////////
    public Vector<Short> getClientTargets() {
        return getTargets();
    }


    /////////////////////////////////////////////////////////////////
    // Get the description
    /////////////////////////////////////////////////////////////////
    public String getDescription() {
        return "Immune to enemies";
    }


    /////////////////////////////////////////////////////////////////
    // Get the description
    /////////////////////////////////////////////////////////////////
    public String getRangeDescription() {
        return "Enemies are immune to this";
    }


    /////////////////////////////////////////////////////////////////
    // Get the cost description
    /////////////////////////////////////////////////////////////////
    public String getCostDescription() {
        return "Lasts until start of turn";
    }


    /////////////////////////////////////////////////////////////////
    // Describe it
    /////////////////////////////////////////////////////////////////
    public String getDetail() {
        return "This unit is immune to all enemies, and all enemies are immune to it. This effect lasts until the start of its next turn.";
    }


    /////////////////////////////////////////////////////////////////
    // Get remaining actions
    /////////////////////////////////////////////////////////////////
    public short getRemaining() {
        return 0;
    }


    /////////////////////////////////////////////////////////////////
    // Refresh
    /////////////////////////////////////////////////////////////////
    public void refresh() {
    }


    /////////////////////////////////////////////////////////////////
    // Start turn
    /////////////////////////////////////////////////////////////////
    public void startTurn() {
        owner.armistice(false);
        owner.remove(this);
    }


    /////////////////////////////////////////////////////////////////
    // Gets
    /////////////////////////////////////////////////////////////////
    public String getName() {
        return "Armistice";
    }

    public short getMax() {
        return 0;
    }

    public short getCost() {
        return 0;
    }

    public short getRange() {
        return 0;
    }

    public short getTargetType() {
        return TargetType.NONE;
    }

    public Unit getOwner() {
        return owner;
    }

    public Unit getHiddenUnit() {
        return null;
    }

    public boolean passive() {
        return true;
    }

    public short getType() {
        return type;
    }
}
