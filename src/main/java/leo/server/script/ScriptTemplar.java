///////////////////////////////////////////////////////////////////////
//	Name:	ScriptTemplar
//	Desc:	Run a Templar
//	Date:	10/30/2008 - Gabe Jones
//	TODO:
///////////////////////////////////////////////////////////////////////
package leo.server.script;

// imports

import leo.server.AI;
import leo.server.game.Game;
import leo.shared.Log;
import leo.shared.Action;
import leo.shared.Castle;
import leo.shared.Script;
import leo.shared.Unit;
import leo.shared.crusades.UnitTemplar;

import java.util.Iterator;
import java.util.Vector;

public class ScriptTemplar implements Script {
    /////////////////////////////////////////////////////////////////
    // Constants
    /////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private final UnitTemplar unit;
    private final Game game;
    private final Castle castle;
    private final Castle enemyCastle;
    private final Action heal;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public ScriptTemplar(Unit newUnit, Game newGame, Castle newCastle, Castle newEnemyCastle) {
        unit = (UnitTemplar) newUnit;
        game = newGame;
        castle = newCastle;
        enemyCastle = newEnemyCastle;
        heal = unit.getHeal();
    }


    /////////////////////////////////////////////////////////////////
    // Iteration of a routine
    /////////////////////////////////////////////////////////////////
    private void rote() {
        // try to win
        win();

        // kill stuff
        while (act(unit.getAttack())) {
        }

        // heal
        while (act(heal)) {
        }

        // walk
        move();
    }


    /////////////////////////////////////////////////////////////////
    // Use unit
    /////////////////////////////////////////////////////////////////
    public void perform() {
        rote();
        rote();
        rote();
    }


    /////////////////////////////////////////////////////////////////
    // Perform an action
    /////////////////////////////////////////////////////////////////
    private boolean act(Action act) {
        if (game.over()) return false;

        if (!unit.ready()) return false;

        if (act.getRemaining() < 1) return false;

        Vector<Short> targets = act.getTargets();

        if (targets.size() < 1) return false;

        Short finalTarget;

        // Get a random target
        finalTarget = targets.elementAt(game.random().nextInt(targets.size()));

        // get ready to strike
        short action = unit.getAction(act);
        short location = unit.getLocation();

        // Make the unit do it
        Log.game("Artificial Opponent: " + unit.process(action, location, finalTarget.byteValue()));
        interpretAction(action, location, finalTarget.byteValue());

        return true;
    }


    /////////////////////////////////////////////////////////////////
    // Move
    /////////////////////////////////////////////////////////////////
    private boolean move() {
        if (game.over()) return false;

        if (!unit.ready()) return false;

        if (unit.getMove() == null) return false;

        if (unit.getMove().getRemaining() < 1) return false;

        // Get the move targets
        Vector<Short> moves = unit.getMove().getTargets();

        // If empty go home
        if (moves.size() < 1) return false;

        // Set the target
        short targetLocation = enemyCastle.getLocation();

        // Whats the closest possible range
        int closest = AI.getClosestRange(moves, targetLocation);

        // Refine to closest range
        moves = AI.refineRange(moves, closest, targetLocation);

        // Get the final target
        Short finalTarget = moves.elementAt(game.random().nextInt(moves.size()));
        short action = unit.getAction(unit.getMove());
        short location = unit.getLocation();

        // Make the unit do it
        Log.game("Artificial Opponent: " + unit.process(action, location, finalTarget.byteValue()));
        interpretAction(action, location, finalTarget.byteValue());

        return true;
    }


    /////////////////////////////////////////////////////////////////
    // Try to win
    /////////////////////////////////////////////////////////////////
    private void win() {
        if (game.over()) return;

        if (!unit.ready()) return;

        if (unit.getMove() == null) return;

        if (unit.getMove().getRemaining() < 1) return;

        // Get the move targets
        Vector<Short> moves = unit.getMove().getTargets();

        Iterator<Short> it = moves.iterator();
        while (it.hasNext()) {
            Short loc = it.next();
            if (loc.byteValue() == enemyCastle.getLocation()) {
                // Get the final target
                short action = unit.getAction(unit.getMove());
                short location = unit.getLocation();

                // Make the unit do it
                Log.game("Artificial Opponent: " + unit.process(action, location, loc.byteValue()));
                interpretAction(action, location, loc.byteValue());
                return;
            }
        }
        return;
    }


    /////////////////////////////////////////////////////////////////
    // interpretAction wrapper
    /////////////////////////////////////////////////////////////////
    private void interpretAction(short action, short actor, short target) {
        try {
            game.interpretAction(null, action, actor, target);
            Thread.sleep(game.getDelay());
        } catch (Exception e) {
            Log.error("ScriptTemplar.interpretAction " + e);
        }
    }
}
