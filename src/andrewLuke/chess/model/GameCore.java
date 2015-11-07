package andrewLuke.chess.model;

import andrewLuke.chess.audio.Sound;
import andrewLuke.chess.control.Keyboard;
import andrewLuke.chess.networking.Client;
import andrewLuke.chess.networking.Host;
import andrewLuke.chess.time.PixelUpdateTimer;
import andrewLuke.chess.time.PlayerTimer;
import andrewLuke.chess.view.GameView;
import andrewLuke.chess.settings.OptionsMenu;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This contains the main game loop
 *
 * @author Andrew
 */
public class GameCore implements Runnable
{

    private final GameView gameView;
    private static final long serialVersionUID = 1L;
    private Thread mainGameThread;
    private boolean isRunning = false;
    private static TileSet tileSet;
    private Player player1;
    private Player player2;
    private boolean isCheckMate;
    public static boolean firstCheckCheck = true;
    private PlayerTimer playerTimer;
    public static boolean gameOver =false;
    private OptionsMenu optionsMenu;
    private boolean firstUpdate = true;
    public static boolean gameStarted =false;
    public static int desiredMinutesPerPlayer = 28;
    private static boolean hostOrClientconfigured = false;

    /**
     * Creates a GameCore with the specified dimensions
     *
     * @param gameView
     * @precondition the main game frame has not started yet
     * @postcondition the main game frame has its dimensions established
     */
    public GameCore(GameView gameView)
    {
        this.gameView = gameView;
    }

    /**
     * Stop method for mainGameThread
     *
     * @precondition The main thread has started already
     * @postcondition The main thread stops
     */
    public synchronized void stop()
    {
        isRunning = false;
        try
        {
            mainGameThread.join();
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run()  // called implicitly by mainGameThread.start() in the start() method below
    {
        PixelUpdateTimer timer = new PixelUpdateTimer();
        timer.startTimer();
        try
        {
            update();
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (isRunning)
        {
            timer.updateElapsedTime();
            if (timer.sixtiethSecondElapsed)
            {
                try
                {
                    update();
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.startTimer();
            }
        }
    }

    /**
     * Updates the game state
     * <p>
     * This includes modifying the pixel array
     *
     * @precondition The game state is not updated
     * @postcondition The game state is updated with any relevant calculations
     * @throws java.lang.InterruptedException
     */
    public void update() throws InterruptedException
    {
//        Psychadelic chess
//        int nextRandom = ChessStart.random.nextInt();
//        {
//            outerFrame.getContentPane().setBackground(Color.getColor("", nextRandom));
//            rowLabels.setBackground(Color.getColor("",nextRandom));
//            columnLabels.setBackground(Color.getColor("",nextRandom));
//            mainFrame.setBackground(Color.getColor("",nextRandom));
//        }
        String possibleMove = "";
        if (gameStarted)
        {
            if (OptionsMenu.hostMode && OptionsMenu.onlineMode)
            {
                possibleMove = Host.getHost().receive();
            }
            else if (!OptionsMenu.hostMode && OptionsMenu.onlineMode)
            {
                possibleMove = Client.getClient().receive();
            }
            if (possibleMove.equals("autoReset"))
            {
                gameStarted = false;
                gameView.getResetButton().doClick();
                return;
            }
            else if (possibleMove.equals("autoResetAll"))
            {
                gameStarted = false;
                gameView.getCriticalText().setText("Opponent Disconnected");
                gameView.getResetButton().setEnabled(false);
                return;
            }
        }
        if (!gameOver)
        {
            // This allows the autostart
            String autoStart = "";
            if (hostOrClientconfigured && !gameStarted)
            {
                if (OptionsMenu.hostMode && OptionsMenu.onlineMode)
                {
                    autoStart = Host.getHost().receive();
                }
                else if (!OptionsMenu.hostMode && OptionsMenu.onlineMode)
                {
                    autoStart = Client.getClient().receive();
                }
                String autoStartTokens[] = autoStart.split(";");
                if (autoStartTokens.length > 1)
                {
                    if (autoStartTokens[0].equals("autoStart"))
                    {
                        gameStarted = true; // this makes sure not to send the autoStart command back to the other player
                        gameView.getStartButton().setEnabled(true);
                        gameView.getStartButton().doClick();
                    }
                    if (autoStartTokens[1].equals("2"))
                    {
                        PlayerTimer.p1Turn = !PlayerTimer.p1Turn;
                        optionsMenu.setClientFirst();
                    }
                    if (autoStartTokens[2].equals("2"))
                    {
                        // Timer stuff
                    }
                    return;
                }
            }

            if (gameStarted)
            {
                if (OptionsMenu.getTimerOn())
                {
                    if (firstUpdate)
                    {
                        firstUpdate = false;
                        playerTimer = new PlayerTimer(GameCore.desiredMinutesPerPlayer);
                    }

                    playerTimer.updateElapsedTime();
                    gameView.setTimerText(1, "Time\nRemaining:\nMinutes: " + playerTimer.p1minutesRemaining + "\nSeconds: " + Integer.toString(playerTimer.p1SecondsDigits));
                    gameView.setTimerText(2, "Time\nRemaining:\nMinutes: " + playerTimer.p2minutesRemaining + "\nSeconds: " + Integer.toString(playerTimer.p2SecondsDigits));
                    if (playerTimer.p2secondsRemaining < 0)
                    {
                        Sound gameOverSound = new Sound("gameOverSound.wav");
                        gameOverSound.playSound();
                        gameView.getCriticalText().setText("Player 1 Wins");
                        gameOver = true;
                    }
                    else if (playerTimer.p1secondsRemaining < 0)
                    {
                        Sound gameOverSound = new Sound("gameOverSound.wav");
                        gameOverSound.playSound();
                        gameView.getCriticalText().setText("Player 2 Wins");
                        gameOver = true;
                    }
                }
            }

            // Online enemy movement processing
            if (OptionsMenu.onlineMode && OptionsMenu.hostMode && !PlayerTimer.p1Turn && gameStarted)
            {
                // String possibleMove = optionsMenu.getHost().receive();
                if (!possibleMove.equals(""))
                {
                    int startX = Character.getNumericValue(possibleMove.charAt(0));
                    int startY = Character.getNumericValue(possibleMove.charAt(1));
                    int endX = Character.getNumericValue(possibleMove.charAt(2));
                    int endY = Character.getNumericValue(possibleMove.charAt(3));
                    System.out.println("Enemy moved" + startX + startY + endX + endY);

                    player2.selectMilitant(startX, startY);
                    player1.update(player1, player2, tileSet, false, true, optionsMenu);
                    player2.update(player1, player2, tileSet, false, true, optionsMenu);
                    player2.processMoveIfValid(startX, startY, endX, endY, optionsMenu, true);

                }
            }
            else if (OptionsMenu.onlineMode && !OptionsMenu.hostMode && PlayerTimer.p1Turn && gameStarted)
            {
                //String possibleMove = optionsMenu.getClient().receive();
                if (!possibleMove.equals(""))
                {
                    int startX = Character.getNumericValue(possibleMove.charAt(0));
                    int startY = Character.getNumericValue(possibleMove.charAt(1));
                    int endX = Character.getNumericValue(possibleMove.charAt(2));
                    int endY = Character.getNumericValue(possibleMove.charAt(3));
                    System.out.println("Enemy moved" + startX + startY + endX + endY);

                    player1.selectMilitant(startX, startY);
                    player1.update(player1, player2, tileSet, false, true, optionsMenu);
                    player2.update(player1, player2, tileSet, false, true, optionsMenu);
                    player1.processMoveIfValid(startX, startY, endX, endY, optionsMenu, true);
                }
            }
            // Denies the sending of a move to the enemy, if it is the move that is received from the enemy
            boolean onlineDenier = false;
            if (PlayerTimer.p1Turn && OptionsMenu.onlineMode && !OptionsMenu.hostMode)
            {
                onlineDenier = true;
            }
            else if (!PlayerTimer.p1Turn && OptionsMenu.onlineMode && OptionsMenu.hostMode)
            {
                onlineDenier = true;
            }

            // Modifies the pixel array
            player1.update(player1, player2, tileSet, false, onlineDenier, optionsMenu);
            player2.update(player1, player2, tileSet, false, onlineDenier, optionsMenu);
            tileSet.update(player1, player2);
            player1.update(player1, player2, tileSet, false, onlineDenier, optionsMenu);
            player2.update(player1, player2, tileSet, false, onlineDenier, optionsMenu);
            if ('m' == Keyboard.getCharOfInterest())
            {
                player1.selectNewRandomColors();
                player2.selectNewRandomColors();
            }

            // Sets the check/checkmate/not-check status correctly
            if (firstCheckCheck)
            {
                firstCheckCheck = false;
                if (player1.isKingInCheck() || player2.isKingInCheck())
                {
                    if (PlayerTimer.p1Turn)
                    {
                        isCheckMate = player1.isCheckMated();
                    }
                    else
                    {
                        isCheckMate = player2.isCheckMated();
                    }
                    int playerID;
                    int otherPlayerID;
                    if (PlayerTimer.p1Turn)
                    {
                        playerID = 1;
                        otherPlayerID = 2;
                    }
                    else
                    {
                        playerID = 2;
                        otherPlayerID = 1;
                    }
                    if (!isCheckMate)
                    {
                        if (playerID == 1)
                        {
                            tileSet.resetTileColorsToDefault();
                            player1.setPossibleEscapes();
                        }
                        else
                        {
                            tileSet.resetTileColorsToDefault();
                            player2.setPossibleEscapes();
                        }
                        Sound checkSound = new Sound("checkSound.wav");
                        checkSound.playSound();
                        gameView.getCriticalText().setText("Player " + playerID + ": CHECK");
                    }
                    else
                    {
                        Sound gameOverSound = new Sound("gameOverSound.wav");
                        gameOverSound.playSound();
                        gameView.getCriticalText().setText("CHECKMATE: Player " + otherPlayerID + " wins!");
                        gameOver = true;
                        System.out.println("checkmate");
                        // Neatens the display after a checkmate
                        tileSet.update(player1, player2);
                        player1.update(player1, player2, tileSet, false, true, optionsMenu);
                        player2.update(player1, player2, tileSet, false, true, optionsMenu);
                    }
                }
                else
                {
                    gameView.getCriticalText().setText("");
                }
            }
            gameView.refreshActualChessImage();
        }
    }

    /**
     * Starts the game loop
     * <p>
     * This includes creating the pixel array to use, creating the main thread,
     * the JFrame, other necessary objects, and starting the main game thread.
     *
     * @precondition The game loop has not yet started
     * @postcondition The window is created and the game loop thread is started
     */
    public synchronized void start()
    {
        // Create the players
        player1 = new Player(1, gameView.getHistory()); // Creates player with playerID 1
        player2 = new Player(2, gameView.getHistory()); // Creates player with playerID 2
        tileSet = new TileSet();
        mainGameThread = new Thread(this);
        isRunning = true;
        mainGameThread.start();
    }

    /**
     * This resets the game
     *
     * @precondition The window is open and the main game thread is running
     * @postcondition The game state is reset
     */
    public void resetGame()
    {

        gameOver = true;
        gameView.getHistory().setText("");
        tileSet = new TileSet();
        player1 = new Player(1, gameView.getHistory());
        player2 = new Player(2, gameView.getHistory());
        gameStarted = false;
        firstUpdate = true;
        gameView.setTimerText(1, "");
        gameView.setTimerText(2, "");
        PlayerTimer.p1Turn = true;
        gameView.getCriticalText().setText("");
        gameView.getCriticalText().setBackground(Color.getColor("", 0x5CF7E5));
        gameView.getCriticalText().setForeground(Color.getColor("", 0x2E19BD));
        gameView.getChessImageContainer().requestFocusInWindow();
        gameOver = false;
        gameView.getStartButton().setEnabled(true);
    }

    /**
     * Denotes that the host/client is setup
     */
    public static void setHostClientSetup()
    {
        hostOrClientconfigured = true;
    }

    /**
     * Denotes that the host/client is setup
     */
    public static void unsetHostClientSetup()
    {
        hostOrClientconfigured = false;
    }
    
    /**
     * Gets the options menu
     * @return
     */
    public OptionsMenu getOptionsMenu()
    {
        return optionsMenu;
    }
    
    /**
     * Sets the options menu
     * @param optionsMenu
     */
    public void setOptionsMenu(OptionsMenu optionsMenu)
    {
        this.optionsMenu = optionsMenu;
    }
}