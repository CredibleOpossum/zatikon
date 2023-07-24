///////////////////////////////////////////////////////////////////////
//	Name:	EventStun
//	Desc:	Apply stun to your victim
//	Date:	3/10/2009 - Gabe Jones
//	TODO:
///////////////////////////////////////////////////////////////////////
package leo.shared.crusades;

// imports

import leo.shared.*;

import java.util.Vector;


public class EventStun implements Event, Action {

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private final short eventType = Event.WITNESS_ACTION;
    private final short type = Action.SKILL;
    private final Unit owner;
    private final int priority = 100;
    private final String detail = Strings.EVENT_STUN_1;
    private short stunRange = -1;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public EventStun(Unit newOwner) {
        owner = newOwner;
    }

    public void setStunRange(short r) {
        stunRange = r;
    }


    /////////////////////////////////////////////////////////////////
    // Perform the event
    /////////////////////////////////////////////////////////////////
    public short perform(Unit source, Unit target, short val1, short val2, short recursive) {
        Unit victim = target;
        short actionType = val1;

        if (source != owner) return Event.OK;
        if (actionType != Action.ATTACK) return Event.OK;
        if (owner.isStunned()) return Event.OK;
        if (owner.isDead() || victim.isDead()) return Event.OK;
        if (!victim.getOrganic(owner)) return Event.OK;

        // Check if within range of stun effect
        if (stunRange >= 0) {
            ActionAttack checker = new ActionAttack(owner, (byte) 0, (byte) 0, TargetType.UNIT_AREA, stunRange, 0);
            if (!BattleField.inRange(checker, target.getLocation()))
                return Event.OK;
        }
        victim.stun();

        return Event.OK;
    }


    /////////////////////////////////////////////////////////////////
    // Refresh the event
    /////////////////////////////////////////////////////////////////
    public void refresh() {
    }


    /////////////////////////////////////////////////////////////////
    // Start turn
    /////////////////////////////////////////////////////////////////
    public void startTurn() {
    }


    /////////////////////////////////////////////////////////////////
    // Get the event type
    /////////////////////////////////////////////////////////////////
    public short getEventType() {
        return eventType;
    }


    /////////////////////////////////////////////////////////////////
    // Get the owner
    /////////////////////////////////////////////////////////////////
    public Unit getOwner() {
        return owner;
    }


    /////////////////////////////////////////////////////////////////
    // Get priority
    /////////////////////////////////////////////////////////////////
    public int getPriority() {
        return priority;
    }


    /////////////////////////////////////////////////////////////////
    // Descriptions
    /////////////////////////////////////////////////////////////////
    public String getDescription() {
        return Strings.EVENT_STUN_2;
    }

    public String getRangeDescription() {
        if (stunRange < 0)
            return "";
        else
            return Strings.EVENT_STUN_5 + stunRange;
    }

    public String getCostDescription() {
        return Strings.EVENT_STUN_3;
    }


    /////////////////////////////////////////////////////////////////
    // Action stubs
    /////////////////////////////////////////////////////////////////
    public String getName() {
        return Strings.EVENT_STUN_4;
    }

    public String perform(short target) {
        return "";
    }

    public boolean validate(short target) {
        return false;
    }

    public Vector<Short> getTargets() {
        return new Vector<Short>();
    }

    public Vector<Short> getClientTargets() {
        return getTargets();
    }

    public short getRemaining() {
        return (byte) 0;
    }

    public short getMax() {
        return (byte) 0;
    }

    public short getCost() {
        return (byte) 0;
    }

    public short getRange() {
        return (byte) 0;
    }

    public short getTargetType() {
        return (byte) 0;
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

    public String getDetail() {
        return detail;
    }
}
