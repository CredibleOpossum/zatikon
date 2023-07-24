///////////////////////////////////////////////////////////////////////
// Name: AnimationLockdown
// Desc: Burst of flame for summon
// Date: 7/16/2011 - Julian Noble
// TODO:
///////////////////////////////////////////////////////////////////////
package leo.client.animation;

// imports

import leo.client.Client;
import leo.client.LeoComponent;
import leo.shared.BattleField;
import leo.shared.Constants;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class AnimationLockdown implements Animation {
    /////////////////////////////////////////////////////////////////
    // Constants
    /////////////////////////////////////////////////////////////////
    private int duration;
    private final short location;
    private int x;
    private int y;
    private final AffineTransform transform = new AffineTransform();
    private final double angle;
    private float alpha;
    private int delay;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public AnimationLockdown(short newLocation, boolean noisy) {
        location = newLocation;

        if (noisy) Client.getImages().playSound(Constants.SOUND_POOF);

        // Build coordinates
        int x = BattleField.getX(location);
        int y = BattleField.getY(location);

        // Initialize starting location
        x = (x * Constants.SQUARE_SIZE);
        y = (y * Constants.SQUARE_SIZE);
        transform.translate(x, y);

        // Calculate the direction the arrow faces
        angle = 1.0f;//Client.getRandom().nextFloat();
        alpha = 1.0f;

        // How long should this animation play
        duration = 12;
        delay = 0;
    }


    /////////////////////////////////////////////////////////////////
    // Perform the action on the client
    /////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g, Frame frame, LeoComponent surface) {

        //if (alpha < 1 && duration > 30) alpha+=0.1f;
        if (duration < 3) alpha -= 0.2f;
        if (alpha > 1) alpha = 1.0f;
        if (alpha < 0) alpha = 0.0f;

        // alpha
        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Margins
        float offX = surface.getScreenX();
        float offY = surface.getScreenY();

        // Margin move
        transform.translate(offX, offY);


        // Put it on the screen
        int anim = Constants.IMG_LOCKDOWN_1 + (12 - duration);
        if (anim >= Constants.IMG_LOCKDOWN_11) anim = Constants.IMG_LOCKDOWN_11;
        g.drawImage(Client.getImages().getImage(anim), transform, frame);


        // Unmargin
        transform.translate(-offX, -offY);

        g.setComposite(original);

        duration--;
    }


    /////////////////////////////////////////////////////////////////
    // Validate the action
    /////////////////////////////////////////////////////////////////
    public boolean finished() {
        return duration <= 0;
    }

    public void setDelay(int d) {
        delay = d;
    }

    public short getLocation() {
        return location;
    }

    public void setDirection(short b) {
        return;
    }
}
