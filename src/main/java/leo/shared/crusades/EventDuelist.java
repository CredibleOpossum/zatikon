///////////////////////////////////////////////////////////////////////
// Name: EventDuelist
// Desc: Life steal
// Date: 3/10/2009 - Gabe Jones 
// TODO:
///////////////////////////////////////////////////////////////////////
package leo.shared.crusades;

// imports

import leo.shared.*;

import java.util.Vector;


public class EventDuelist implements Event, Action {

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private final short eventType = WITNESS_DAMAGE;
    private final short type = Action.SKILL;
    private final UnitDuelist owner;
    private final int priority = 100;
    private final String detail = Strings.EVENT_DUELIST_1;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public EventDuelist(UnitDuelist newOwner) {
        owner = newOwner;
    }


    /////////////////////////////////////////////////////////////////
    // Perform the event
    /////////////////////////////////////////////////////////////////
    public short perform(Unit source, Unit target, short val1, short val2, short recursive) {
        // initialize the values
        Unit damager = source;
        Unit victim = target;
        short inflicted = val1;

        // some tests
        if (damager != owner) return Event.OK;
        if (inflicted <= 0) return Event.OK;
        if (owner.isDead() || victim.isDead()) return Event.OK;
        if (!victim.getOrganic(owner)) return Event.OK;

        // get the goods
        if (!owner.damagedunits.contains(victim)) {
            owner.damagedunits.add(victim);

            // visual effect
            owner.getCastle().getObserver().abilityUsed(owner.getLocation(), owner.getLocation(), Constants.IMG_POOF);
        }

        // all done
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
        return Strings.EVENT_DUELIST_2;
    }

    public String getRangeDescription() {
        return "";
    }

    public String getCostDescription() {
        return Strings.EVENT_DUELIST_3;
    }

    /////////////////////////////////////////////////////////////////
    // Action stubs
    /////////////////////////////////////////////////////////////////
    public String getName() {
        return Strings.EVENT_DUELIST_4;
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
