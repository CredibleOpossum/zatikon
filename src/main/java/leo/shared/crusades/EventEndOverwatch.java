///////////////////////////////////////////////////////////////////////
//	Name:	EventEndOverwatch
//	Desc:	End overwatch
//	Date:	3/7/2009 - Gabe Jones
//	TODO:
///////////////////////////////////////////////////////////////////////
package leo.shared.crusades;

// imports

import leo.shared.Event;
import leo.shared.Unit;


public class EventEndOverwatch implements Event {

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private final short eventType = Event.WITNESS_MOVE;
    private final UnitBowman owner;
    private final int priority = 100;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public EventEndOverwatch(UnitBowman newOwner) {
        owner = newOwner;
    }


    /////////////////////////////////////////////////////////////////
    // Perform the event
    /////////////////////////////////////////////////////////////////
    public short perform(Unit source, Unit target, short val1, short val2, short recur) {
        Unit walker = source;

        if (walker != owner) return Event.OK;

        owner.endOverwatch();

        return Event.OK;
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
}
