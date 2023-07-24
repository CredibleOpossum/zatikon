///////////////////////////////////////////////////////////////////////
// Name: Client
// Desc: The central client object. 
// Date: 2/4/2003 - Gabe Jones
// TODO:
///////////////////////////////////////////////////////////////////////
package leo.client;

// imports

import leo.shared.Action;
import leo.shared.Castle;
import leo.shared.Constants;
import leo.shared.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Client {
    /////////////////////////////////////////////////////////////////
    // Constants
    /////////////////////////////////////////////////////////////////
    //public static final int  SCREEN_WIDTH = 800; //moved to "leo/shared/Constants.java"
    //public static final int  SCREEN_HEIGHT = 600; //moved to "leo/shared/Constants.java"
    public static final String VERSION = "1.1.0";
    public static final String TITLE = "Zatikon ";
    public static final String CREDITS = " Chronic Logic 2023";
//    public static final String SERVER_NAME = "localhost";
//    public static final String SERVER_NAME = Optional.ofNullable(System.getProperty("server.name")).orElse("localhost");
    public static String serverName = "zatikon.chroniclogic.com";
    public static final int LOGIN_PORT = 6000;
    public static final int CHAT_PORT = 6001;
    //public static final int  BOARD_SIZE = 11; //moved to "leo/shared/Constants.java"
    //public static final int  SQUARE_SIZE = SCREEN_HEIGHT/BOARD_SIZE; //moved to "leo/shared/Constants.java"
    //public static final int  OFFSET  = (SCREEN_HEIGHT%BOARD_SIZE)/2; //moved to "leo/shared/Constants.java"
    public static final int FONT_HEIGHT = 10;
    private static final Font FONT = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 14);
    private static final Font FONT_BIG = new Font("SansSerif", Font.BOLD, 20);
    //public static final int  STATE_CASTLE = 0; //moved to "leo/shared/Constants.java"
    //public static final int  STATE_UNIT = 1; //moved to "leo/shared/Constants.java"
    //public static final int  STEP_SPEED = 9; //moved to "leo/shared/Constants.java"
    private static Random random;
    private static boolean web;
    private static boolean active = false;
    private static final long serialVersionUID = 1L;
    private static final short[] units = new short[Unit.UNIT_COUNT];


    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private static final CastleArchive[] castleArchives = new CastleArchive[10];
    private static GameMedia clientImages = null;
    private static ClientNetManager netManager = null;
    private static ClientGameData clientGameData = null;
    private static boolean rendering = false;
    private static int rating = 0;
    private static int rank = -1;
    private static long gold = -1;
    private static long lastGold = -1;
    private static final int count = 0;
    private static Vector<ChatPlayer> players;
    private static int insetX = 0;
    private static int insetY = 0;
    private static boolean fullScreen = false;
    private static ClientChatManager chatManager;
    private static Vector<String> messages;
    private static StringBuffer messageBuffer;
    private static int textTimer = 0;
    private static GameFrame gameFrame = null;
    private static ClientLoginDialog login = null;
    private static Label label;
    private static boolean drawing = false;
    private static boolean computing = false;
    private static ChatList chatList = null;
    private static ChatListPanel chatListPanel = null;
    private static ChatFrame chatFrame = null;
    private static ChatPanel chatPanel = null;
    private static String name = "";
    private static String password = "";
    private static boolean demo = true;
    private static Frame frame;
    private static boolean shuttingDown = false;
    private static boolean mute = false;
    private static boolean musicOff = false;
    private static boolean needEmail = false;
    private static Settings settings;
    private static boolean load = false;
    private static final Vector<String> blocks = new Vector<String>();
//    private static GameApplet applet = null;
    private static int chatID;
    private static boolean timeOut = false;
    private static JLabel fbText1 = null;
    private static JLabel fbText2 = null;

    // access
    private static final boolean[] access =
            {true,  // free
                    false,  // crusades
                    false,  // legions
                    false   // inquisitions
            };
    public static boolean standalone = false;

    private static ServerProcess serverProcess;


    /////////////////////////////////////////////////////////////////
    // Main module
    /////////////////////////////////////////////////////////////////
    public static void main(String[] args) {

        //for (int i = 0; i < args.length; i++)
        //{ System.out.println("argument " + i + ": " + args[i]);
        //}

        var succeeded = new File(Constants.LOCAL_DIR).mkdirs();

        // jnlp or not?
        web = false;
        try {
            web = (System.getProperty("jnlp.web").length() > 0);
            System.out.println(System.getProperty("jnlp.web"));

        } catch (Exception e) { //System.out.println(e);
        }

        if (!web) {
            // set some properties
            System.setProperty("sun.java2d.translaccel", "true");

        }

        // load local settings
        settings = new Settings();

        clientImages = new GameMedia();

        // preload graphics
        Client.getImages().preload();

        messages = new Vector<String>();
        messageBuffer = new StringBuffer();
        random = new Random();
        clientGameData = new ClientGameData();
        players = new Vector<ChatPlayer>();

        if (args.length == 3) {
            String server = args[0];
            String user = args[1];
            String pwd = args[2];

            // TODO check if the web thingies are useful
            web = true;
            login = new ClientLoginDialog();
            System.err.println("Attempting autologin");
            login.autoLogin(server, user, pwd);
        } else {
            System.err.println("Attempting normal login");
            login = new ClientLoginDialog();
            login.setVisible(true);
        }

        // tmp
        //java.applet.AppletContext.showDocument(new URL("google.com"));

        //ClientFrame clientFrame = new ClientFrame();
    }

    public static void startLocalServer() {
        /* TODO Challenges:
         *   - make sure server is initialized before login attempt
         *   - ensure clean shutdown (as much as possible)
         *   - make it a clean start with least possible guesses on java binary, classpath etc.
         * */

        System.err.println("Standalone mode - starting local server");

        serverProcess = ServerProcess.getInstance();
        serverProcess.waitUntilReady(10000);
    }


    /////////////////////////////////////////////////////////////////
    // FaceBook Applet init
    /////////////////////////////////////////////////////////////////
//    public static void init(GameApplet newApplet, String userID, String passkey) {
//        shuttingDown = false;
//        timeOut = false;
//        applet = newApplet;
//        applet.setLayout(new BorderLayout());
//        web = true;
//
//        // load local settings
//        settings = new Settings();
//
//        clientImages = new GameMedia();
//
//        // preload graphics
//        Client.getImages().preload();
//
//        messages = new Vector<String>();
//        messageBuffer = new StringBuffer();
//        random = new Random();
//        clientGameData = new ClientGameData();
//        players = new Vector<ChatPlayer>();
//        login = new ClientLoginDialog();
//        login.autoLogin(userID, passkey);
//        if (applet != null) {
//            //applet.remove(chatPanel);
//            //applet.remove(chatListPanel);
//            //applet.removeAll();
//            fbText1 = new JLabel("<html><p style='text-align:center;'>Do not leave this page or your game will close.</p></html>");
//            fbText1.setBackground(Color.white);
//            fbText1.setForeground(Color.black);
//            applet.add(fbText1, BorderLayout.CENTER);
//            fbText1.setOpaque(true);
//
//            applet.validate();
//            //applet.dispose();
//            //applet.destroy();
//        }
//    }


    /////////////////////////////////////////////////////////////////
    // Reconnect
    /////////////////////////////////////////////////////////////////
    public static void reconnect() {
        if (shuttingDown) return;
        if (chatList != null) chatList.dispose();
        if (chatFrame != null) chatFrame.dispose();

        // kill the timer
        if (clientGameData != null && clientGameData.getTimer() != null) clientGameData.getTimer().end();

        try {
            Client.getNetManager().kill();
        } catch (Exception e) {
        }

        try {
            Client.getChat().kill();
        } catch (Exception e) {
        }

        Iterator<ChatPlayer> it = getPlayers().iterator();
        while (it.hasNext()) {
            ChatPlayer player = it.next();
            player.killChat();
        }

        players = new Vector<ChatPlayer>();
    }


    /////////////////////////////////////////////////////////////////
    // Load the chat list
    /////////////////////////////////////////////////////////////////
    public static void loadChatList() {
//        if (applet == null) {
            chatList = new ChatList(Client.getPlayers());
            chatListPanel = chatList.panel();
//        } else {
//            chatListPanel = new ChatListPanel(null, Client.getPlayers());
//            //applet.add(chatListPanel, BorderLayout.NORTH);
//            //applet.pack();
//            applet.validate();
//        }
        if (chatList != null)
            chatList.setVisible(false);
    }


    /////////////////////////////////////////////////////////////////
    // Load the chat frame
    /////////////////////////////////////////////////////////////////
    public static void loadChatFrame() {
//        if (applet == null) {
            chatFrame = new ChatFrame();
            chatPanel = chatFrame.getChatPanel();
//        } else {
//            chatPanel = new ChatPanel();
//            //applet.add(chatPanel, BorderLayout.CENTER);
//            //applet.pack();
//            applet.validate();
//        }
        if (chatFrame != null)
            chatFrame.setVisible(false);
    }


    /////////////////////////////////////////////////////////////////
    // Get the images
    /////////////////////////////////////////////////////////////////
    public static GameMedia getImages() {
        return clientImages;
    }


    /////////////////////////////////////////////////////////////////
    // Get the game data
    /////////////////////////////////////////////////////////////////
    public static ClientGameData getGameData() {
        return clientGameData;
    }


    /////////////////////////////////////////////////////////////////
    // Restart the game data
    /////////////////////////////////////////////////////////////////
    public static void restart() {
        if (Client.getGameData() == null) return;
        Client.getGameData().end();

        Castle units = Client.getGameData().getArmy();
        PlayerPanel oldPanel = Client.getGameData().getPlayerPanel();

        clientGameData = new ClientGameData();

        Client.getGameData().setArmy(units);
        if (oldPanel.getPlayers() != null) Client.getGameData().setPlayerPanel(oldPanel);
    }


    /////////////////////////////////////////////////////////////////
    // Start rendering
    /////////////////////////////////////////////////////////////////
    public static void startRendering() {
        rendering = true;
    }


    /////////////////////////////////////////////////////////////////
    // Stop rendering
    /////////////////////////////////////////////////////////////////
    public static void stopRendering() {
        rendering = false;
    }


    /////////////////////////////////////////////////////////////////
    // rendering?
    /////////////////////////////////////////////////////////////////
    public static boolean rendering() {
        return rendering;
    }


    /////////////////////////////////////////////////////////////////
    // Set the net manager
    /////////////////////////////////////////////////////////////////
    public static void setNetManager(ClientNetManager newNetManager) {
        netManager = newNetManager;
    }


    /////////////////////////////////////////////////////////////////
    // Get the net manager
    /////////////////////////////////////////////////////////////////
    public static ClientNetManager getNetManager() {
        return netManager;
    }

    /////////////////////////////////////////////////////////////////
    // Time Out
    /////////////////////////////////////////////////////////////////
    public static void timeOut() {
        timeOut = true;
        if (clientGameData != null && clientGameData.getTimer() != null) clientGameData.getTimer().end();
        getGameData().screenTimingOut();
    }

    /////////////////////////////////////////////////////////////////
    // Shutdown
    /////////////////////////////////////////////////////////////////
    public static void shutdown() {
        try {
            if (getImages() != null) {
                getImages().stopMusic();
                getImages().clean();
            }
            if (shuttingDown) return;
            shuttingDown = true;

//            if (applet != null) {
//                //applet.remove(chatPanel);
//                //applet.remove(chatListPanel);
//                applet.remove(fbText1);
//                fbText2 = new JLabel("<html><p style='text-align:center;'>Zatikon is closed, refresh to play again.</p></html>");
//                fbText2.setBackground(Color.white);
//                fbText2.setForeground(Color.black);
//                applet.add(fbText2, BorderLayout.CENTER);
//                fbText2.setOpaque(true);
//
//                applet.validate();
//                //applet.dispose();
//                //applet.destroy();
//            }

            if (Client.getNetManager() != null) {
                Client.getNetManager().sendAction(Action.QUIT, Action.NOTHING, Action.NOTHING);
            }

            if (!Client.standalone) {
                settings().setServer(serverName);
                settings().setUsername(getName());
            }
            settings().save();

            active = false;
            if (clientGameData != null && clientGameData.getTimer() != null) clientGameData.getTimer().end();
            if (netManager != null) netManager.kill();
            if (getChat() != null) getChat().kill();
            netManager = null;
            clientGameData = new ClientGameData();
            if (gameFrame != null && Client.demo()) {
                gameFrame.setVisible(false);
                if (chatList != null) chatList.setVisible(false);
                if (chatFrame != null) chatFrame.setVisible(false);
                AdFrame ad = new AdFrame(new Frame());
            }
            System.exit(0);

        } catch (Exception e) { //System.out.println("Client.Shutdown: " + e);
        }
    }


    /////////////////////////////////////////////////////////////////
    // Set the rating
    /////////////////////////////////////////////////////////////////
    public static void setRating(short hundreds, short ones) {
        rating = (hundreds * 100) + ones;
    }

    /////////////////////////////////////////////////////////////////
    // Set the rank
    /////////////////////////////////////////////////////////////////
    public static void setRank(short hundreds, short ones) {
        rank = (hundreds * 100) + ones;
    }

    /////////////////////////////////////////////////////////////////
    // Set gold
    /////////////////////////////////////////////////////////////////
 /*public static void setGold(short hundreds, short ones)
 { gold = (hundreds * 100) + ones;
 }*/
    public static void setGold(long newGold) {
        gold = newGold;
    }

    public static void lastGold(long newGold) {
        lastGold = newGold;
    }


    /////////////////////////////////////////////////////////////////
    // Get the rating
    /////////////////////////////////////////////////////////////////
    public static int getRating() {
        return rating;
    }

    /////////////////////////////////////////////////////////////////
    // Get the rank
    /////////////////////////////////////////////////////////////////
    public static int getRank() {
        return rank;
    }

    /////////////////////////////////////////////////////////////////
    // Get the gold
    /////////////////////////////////////////////////////////////////
    public static long getGold() {
        return gold;
    }


    /////////////////////////////////////////////////////////////////
    // Get the gold
    /////////////////////////////////////////////////////////////////
    public static long lastGold() {
        return lastGold;
    }


    /////////////////////////////////////////////////////////////////
    // Get the player count
    /////////////////////////////////////////////////////////////////
    public static int getPlayerCount() {
        return count;
    }


    /////////////////////////////////////////////////////////////////
    // Get the chatters
    /////////////////////////////////////////////////////////////////
    public static Vector<ChatPlayer> getPlayers() {
        return players;
    }


    /////////////////////////////////////////////////////////////////
    // Get the chatters
    /////////////////////////////////////////////////////////////////
    public static ChatPlayer getPlayer(int id) {
        Iterator<ChatPlayer> it = getPlayers().iterator();
        while (it.hasNext()) {
            ChatPlayer player = it.next();
            if (player.getID() == id)
                return player;
        }
        return null;
    }


    /////////////////////////////////////////////////////////////////
    // Add a chatter
    /////////////////////////////////////////////////////////////////
    public static void add(ChatPlayer player) {
        if (player.getID() == chatID) setName(player.getName());
        while (chatListPanel == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        players.add(player);
        updateChatList();
    }


    /////////////////////////////////////////////////////////////////
    // Update chat list
    /////////////////////////////////////////////////////////////////
    public static void updateChatList() {
        while (chatListPanel == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        chatListPanel.update();
        if (getGameData() != null)
            getGameData().getPlayerPanel().updateList();
        if (chatList != null)
            chatList.setVisible(false);
    }


    /////////////////////////////////////////////////////////////////
    // Remove a chatter
    /////////////////////////////////////////////////////////////////
    public static void removePlayer(int id) {
        while (chatListPanel == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }

        Iterator<ChatPlayer> it = getPlayers().iterator();
        while (it.hasNext()) {
            ChatPlayer player = it.next();
            if (player.getID() == id) {
                getPlayers().remove(player);
                player.closeChat();
                updateChatList();
                return;
            }
        }
    }

    /////////////////////////////////////////////////////////////////
    //  Update a specific chatter
    /////////////////////////////////////////////////////////////////

    public static void updatePlayer(int id) {
        //getdata
        // build Player
        removePlayer(id);
        //addPlayer(buildPlayer)
    }

    /////////////////////////////////////////////////////////////////
    // Get the insets
    /////////////////////////////////////////////////////////////////
    public static int getInsetX() {
        return insetX;
    }


    /////////////////////////////////////////////////////////////////
    // Get the insets
    /////////////////////////////////////////////////////////////////
    public static int getInsetY() {
        return insetY;
    }


    /////////////////////////////////////////////////////////////////
    // Set the insets
    /////////////////////////////////////////////////////////////////
    public static void setInsets(int x, int y) {
        insetX = x;
        insetY = y;
    }


    /////////////////////////////////////////////////////////////////
    // Set fullScreen
    /////////////////////////////////////////////////////////////////
    public static void setFullScreen(boolean state) {
        fullScreen = state;
    }


    /////////////////////////////////////////////////////////////////
    // Get fullScreen
    /////////////////////////////////////////////////////////////////
    public static boolean getFullScreen() {
        return fullScreen;
    }


    /////////////////////////////////////////////////////////////////
    // Get the chat manager
    /////////////////////////////////////////////////////////////////
    public static ClientChatManager getChat() {
        return chatManager;
    }


    /////////////////////////////////////////////////////////////////
    // Get the chat frame
    /////////////////////////////////////////////////////////////////
    public static ChatPanel getChatFrame() {
        return chatPanel;
    }

    /////////////////////////////////////////////////////////////////
    // Flash the chat frame
    /////////////////////////////////////////////////////////////////
    public static void flashChatFrame() {
        return; //ChatBlink.AlertOnWindow(chatFrame);
    }

    /////////////////////////////////////////////////////////////////
    // Get the chat list
    /////////////////////////////////////////////////////////////////
    //public static ChatList getChatList()
    //{ return chatList;
    //}


    /////////////////////////////////////////////////////////////////
    // Start the chat manager
    /////////////////////////////////////////////////////////////////
    public static void startChat(int newChatID) {
        chatID = newChatID;
        loadChatList();
        loadChatFrame();

        chatManager = new ClientChatManager(chatID, Client.shouldUseTls());
        if (chatFrame != null) return; //chatFrame.bump();
    }


    /////////////////////////////////////////////////////////////////
    // Add text
    /////////////////////////////////////////////////////////////////
    public static void addText(String message) {
        textTimer = 80;
        messages.add(message);
    }


    /////////////////////////////////////////////////////////////////
    // Get text
    /////////////////////////////////////////////////////////////////
    public static Vector<String> getText() {
        return messages;
    }


    /////////////////////////////////////////////////////////////////
    // Time is up?
    /////////////////////////////////////////////////////////////////
    public static boolean timeUp() {
        textTimer--;
        int done = 1;

        if (Client.getGameData().getEnemyCastle().isAI()) done = -300;

        return (textTimer < done);
    }


    /////////////////////////////////////////////////////////////////
    // Get message buffer
    /////////////////////////////////////////////////////////////////
    public static StringBuffer getMessageBuffer() {
        return messageBuffer;
    }


    /////////////////////////////////////////////////////////////////
    // Get random
    /////////////////////////////////////////////////////////////////
    public static Random getRandom() {
        return random;
    }


    /////////////////////////////////////////////////////////////////
    // Set the game frame
    /////////////////////////////////////////////////////////////////
    public static void setGameFrame(GameFrame newFrame) {
        gameFrame = newFrame;
    }


    /////////////////////////////////////////////////////////////////
    // Get a clipboard string
    /////////////////////////////////////////////////////////////////
    public static String getClipboard() {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

        try {
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) t.getTransferData(DataFlavor.stringFlavor);
                return text;
            }
        } catch (Exception e) {
        }
        return null;
    }


    /////////////////////////////////////////////////////////////////
    // Mute
    /////////////////////////////////////////////////////////////////
    public static boolean mute() {
        mute = !settings.getSoundState();
        return mute;
    }


    /////////////////////////////////////////////////////////////////
    // Mute
    /////////////////////////////////////////////////////////////////
    public static void mute(boolean newState) {
        settings.setSoundState(!newState);
        mute = newState;
    }


    /////////////////////////////////////////////////////////////////
    // Mute
    /////////////////////////////////////////////////////////////////
    public static boolean musicOff() {
        musicOff = !settings.getMusicState();
        return musicOff;
    }


    /////////////////////////////////////////////////////////////////
    // Mute
    /////////////////////////////////////////////////////////////////
    public static void musicOff(boolean newState) {
        settings.setMusicState(!newState);
        musicOff = newState;
    }


    /////////////////////////////////////////////////////////////////
    // Web based...
    /////////////////////////////////////////////////////////////////
    public static boolean isWeb() {
        return web;
    }


    public static void setName(String newName) { //name = newName;
        name = newName.substring(0, 1).toUpperCase() + newName.substring(1);
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
    }

    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setFrame(Frame newFrame) {
        frame = newFrame;
    }

    public static void setTimeOut(boolean to) {
        timeOut = to;
    }

    /////////////////////////////////////////////////////////////////
    // Register
    /////////////////////////////////////////////////////////////////
    public static void register(short game) {
        access[game] = true;
        demo = false;
    }


    /////////////////////////////////////////////////////////////////
    // Draw/compute
    /////////////////////////////////////////////////////////////////
    public static void setDraw(boolean newState) {
        drawing = newState;
    }

    public static void setComputing(boolean newState) {
        computing = newState;
    }

    public static boolean drawing() {
        return drawing;
    }

    public static boolean computing() {
        return computing;
    }

    public static Font getFont() {
        return FONT;
    }

    public static Font getFontBold() {
        return FONT_BOLD;
    }

    public static Font getFontBig() {
        return FONT_BIG;
    }

    public static CastleArchive[] getCastleArchives() {
        return castleArchives;
    }

    public static short[] getUnits() {
        return units;
    }

    public static Frame getFrame() {
        return frame;
    }

    public static Settings settings() {
        return settings;
    }

    public static GameFrame getGameFrame() {
        return gameFrame;
    }

    // registration
    public static boolean access(short game) {
        return access[game];
    }

    public static boolean demo() {
        return demo;
    }

    /////////////////////////////////////////////////////////////////
    // Temporary email harvester
    /////////////////////////////////////////////////////////////////
    public static boolean needEmail() {
        return needEmail;
    }

    public static void needEmail(boolean yes) {
        needEmail = yes;
    }

    /////////////////////////////////////////////////////////////////
    // load settings
    /////////////////////////////////////////////////////////////////
    public static void load(boolean newState) {
        load = newState;
    }

    public static boolean load() {
        return load;
    }

    public static boolean shuttingDown() {
        return shuttingDown;
    }

    public static boolean timingOut() {
        return timeOut;
    }

//    public static GameApplet applet() {
//        return applet;
//    }

    /////////////////////////////////////////////////////////////////
    // Ignores
    /////////////////////////////////////////////////////////////////
    public static void block(String user) {
        blocks.add(user);
    }

    public static void unblock(String user) {
        Iterator<String> it = blocks.iterator();
        while (it.hasNext()) {
            String u = it.next();
            if (u.equals(user)) {
                it.remove();
            }
        }
    }

    public static boolean blocked(String user) {
        Iterator<String> it = blocks.iterator();
        while (it.hasNext()) {
            String u = it.next();
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldUseTls() {
        return !Client.standalone;
    }
}
