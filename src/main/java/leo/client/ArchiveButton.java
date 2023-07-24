///////////////////////////////////////////////////////////////////////
// Name: ArchiveButton
// Desc: Archive the game
// Date: 6/27/2003 - Gabe Jones
// TODO:
///////////////////////////////////////////////////////////////////////
package leo.client;

// imports

import leo.shared.Constants;

import java.awt.*;


public class ArchiveButton extends LeoComponent {

    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private boolean inside = false;
    private final RosterText rosterText;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public ArchiveButton(LeoContainer parent, int x, int y, int width, int height) {
        super(x, y, width, height);
        rosterText = new RosterText(parent,
                "Save and load army configurations.",
                4, 455, 186, 142);
    }

    /////////////////////////////////////////////////////////////////
    // Temporary code
    /////////////////////////////////////////////////////////////////
    public boolean clickAt(int x, int y) {
        Client.getImages().playSound(Constants.SOUND_BUTTON);
        CastleArchiveList cal = new CastleArchiveList();
        //Client.shutdown();
        return true;
    }


    /////////////////////////////////////////////////////////////////
    // Draw the component
    /////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g, Frame mainFrame) {

        if (isWithin(Client.getGameData().getMouseX(), Client.getGameData().getMouseY())) {
            rosterText.draw(g, mainFrame);

            // test splitstring
            //SplitString t = new SplitString("test1 test2 test3 test4 test5. Test6 test7 test8 test9. Test10, test11, test12. Test13 test14 test15.", 200, g);
            //Vector<String> test = t.getStrings();
            //for (int i = 0; i < test.size(); i++)
            //{ System.out.println(test.elementAt(i));
            //}


            if (!inside) {
                Client.getImages().playSound(Constants.SOUND_MOUSEOVER);
            }
            inside = true;
            g.drawImage(Client.getImages().getImage(Constants.IMG_ARCHIVE), getScreenX(), getScreenY(), getWidth(), getHeight(), mainFrame);
        } else {
            inside = false;
            g.drawImage(Client.getImages().getImage(Constants.IMG_ARCHIVE_RED), getScreenX(), getScreenY(), getWidth(), getHeight(), mainFrame);
        }
        //g.drawImage(Client.getImages().getImage(Constants.IMG_ARCHIVE_TEXT), getScreenX(), getScreenY(), mainFrame);
        drawText(g, "Army Archive", getScreenX() + getWidth() + 4, getScreenY() + getHeight() - 12);
    }


    /////////////////////////////////////////////////////////////////
    // Draw the component
    /////////////////////////////////////////////////////////////////
    public void drawText(Graphics2D g, String text, int atX, int atY) {
        //if (inside)
        g.setFont(Client.getFontBold());
        g.setColor(Color.black);
        g.drawString(text, atX + 1, atY);
        g.drawString(text, atX - 1, atY);
        g.drawString(text, atX, atY + 1);
        g.drawString(text, atX, atY - 1);

        if (inside)
            g.setColor(Color.yellow);
        else
            g.setColor(Color.white);

        g.drawString(text, atX, atY);

        // set it back
        g.setFont(Client.getFont());

    }


    /////////////////////////////////////////////////////////////////
    // Are the coordinates within this component
    /////////////////////////////////////////////////////////////////
    @Override
    public boolean isWithin(int testX, int testY) {
        return (testX >= getScreenX() && testX - 100 <= (getScreenX() + getWidth())) &&
                (testY >= getScreenY() && testY <= (getScreenY() + getHeight()));
    }

}
