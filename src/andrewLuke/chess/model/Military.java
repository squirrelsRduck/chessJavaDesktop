package andrewLuke.chess.model;

import andrewLuke.chess.application.ChessStart;
import andrewLuke.chess.audio.Sound;
import andrewLuke.chess.control.Mouse;
import andrewLuke.chess.networking.Client;
import andrewLuke.chess.networking.Host;
import andrewLuke.chess.time.PlayerTimer;
import static andrewLuke.chess.view.GameView.criticalGameMessage;
import andrewLuke.chess.settings.OptionsMenu;
import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * This contains an ArrayList of each player's units
 *
 * @author Andrew
 */
public class Military
{

    private final Rook rook1, rook2;
    private final Knight knight1, knight2;
    private final Bishop bishop1, bishop2;
    private final Queen queen;
    private final King king;
    private int militaryColor;
    private int militaryColor2;
    private boolean startAtTop = false;
    private final ArrayList<Militant> militants;
    private Player enemyOfThisPlayer;
    private Player activePlayer;
    private Player inactivePlayer;
    private final int playerID;
    private Player player1;
    private Player player2;
    private TileSet tileSet;
    private int selectedX;
    private int selectedY;
    private boolean militantSelected = false;
    private int startXtileSelection;
    private int startYtileSelection;
    private final JTextArea history;
    private Player thisPlayer;

    /**
     * Creates a Military object for a specified playerID
     *
     * @param playerID
     * @param history
     */
    public Military(int playerID, JTextArea history)
    {
        this.history = history;
        if (playerID == 1)
        {
            startAtTop = false;
        }
        else
        {
            startAtTop = true;
        }

        this.playerID = playerID;
        militaryColor = ChessStart.random.nextInt(0xffffff);
        militaryColor2 = ChessStart.random.nextInt(0xffffff);
        // Here we create 16 militants for the given player
        militants = new ArrayList<>();

        if (startAtTop)
        {
            for (int a = 0; a < 8; a++)
            {
                militants.add(new Pawn(playerID, a, 1, militaryColor, militaryColor2, a));
            }

            rook1 = new Rook(playerID, 0, 0, militaryColor, militaryColor2, 8);
            rook2 = new Rook(playerID, 7, 0, militaryColor, militaryColor2, 9);
            bishop1 = new Bishop(playerID, 2, 0, militaryColor, militaryColor2, 10);
            bishop2 = new Bishop(playerID, 5, 0, militaryColor, militaryColor2, 11);
            knight1 = new Knight(playerID, 1, 0, militaryColor, militaryColor2, 12);
            knight2 = new Knight(playerID, 6, 0, militaryColor, militaryColor2, 13);
            king = new King(playerID, 3, 0, militaryColor, militaryColor2, 14);
            queen = new Queen(playerID, 4, 0, militaryColor, militaryColor2, 15);
        }
        else
        {
            for (int a = 0; a < 8; a++)
            {
                militants.add(new Pawn(playerID, a, 6, militaryColor, militaryColor2, a));
            }

            rook1 = new Rook(playerID, 0, 7, militaryColor, militaryColor2, 8);
            rook2 = new Rook(playerID, 7, 7, militaryColor, militaryColor2, 9);
            bishop1 = new Bishop(playerID, 2, 7, militaryColor, militaryColor2, 10);
            bishop2 = new Bishop(playerID, 5, 7, militaryColor, militaryColor2, 11);
            knight1 = new Knight(playerID, 1, 7, militaryColor, militaryColor2, 12);
            knight2 = new Knight(playerID, 6, 7, militaryColor, militaryColor2, 13);
            king = new King(playerID, 4, 7, militaryColor, militaryColor2, 14);
            queen = new Queen(playerID, 3, 7, militaryColor, militaryColor2, 15);
        }

        militants.add(rook1);
        militants.add(rook2);
        militants.add(bishop1);
        militants.add(bishop2);
        militants.add(knight1);
        militants.add(knight2);
        militants.add(king);
        militants.add(queen);
    }

    /**
     * Updates the military's militants, which basically just sets their pixels
     * in the BufferedImage/integer-array.
     *
     * @param bypassBoolean
     * @param onlineDenier
     * @param optionsMenu
     * @precondition Each militant is already created
     * @param player1
     * @param player2
     * @param tileSet
     */
    public void update(Player player1, Player player2, TileSet tileSet, boolean bypassBoolean, boolean onlineDenier, OptionsMenu optionsMenu)
    {
        // Then we determine if user input is occuring
        int x = Mouse.getMouseX();
        int y = Mouse.getMouseY();
        int xTile = tileSet.getTileXIndex(x);
        int yTile = tileSet.getTileYIndex(y);

        if (playerID == 1)
        {
            thisPlayer = player1;
            enemyOfThisPlayer = player2;
        }
        else
        {
            thisPlayer = player2;
            enemyOfThisPlayer = player1;
        }
        if (PlayerTimer.p1Turn)
        {
            activePlayer = player1;
            inactivePlayer = player2;
        }
        else
        {
            activePlayer = player2;
            inactivePlayer = player1;
        }

        if ((thisPlayer == activePlayer) && (Mouse.getMouseLeftClickStatus() == true && GameCore.gameStarted && !onlineDenier) || bypassBoolean)
        {

            // If a tile is clicked on & no other tile is already selected, we
            // need to notify the military for each person, and select the tile
            // if there is that person's teammate is on that tile
            if (!militantSelected)// || (this.getSelectedX()==xTile &&this.getSelectedY()==yTile))
            {
                selectMilitant(xTile, yTile);

                // we need to notify the military for each person, and select
                // the tile if there is that person's teammate is on that tile
            }
            else  // This is when a tile is already selected
            {
                //Now that a tile is selected, if they select the same tile, we de-select
                if (xTile == startXtileSelection && yTile == startYtileSelection)
                {
                    militantSelected = !militantSelected;

                    //militants.get(identifyMilitant(xTile, yTile)).toggleSelectedStatus();
                    this.selectMilitant(xTile, yTile);
                    //militants.get(identifyMilitant(xTile, yTile))
                    try
                    {
                        Thread.sleep(190);
                    }
                    catch (InterruptedException ex)
                    {
                        Logger.getLogger(TileSet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    processMoveIfValid(startXtileSelection, startYtileSelection, xTile, yTile, optionsMenu, onlineDenier);
                }
            }
        }
        this.tileSet = tileSet;
        for (Militant militant : militants)
        {
            militant.update(player1, player2, tileSet);
        }
        this.player1 = player1;
        this.player2 = player2;

    }

    /**
     * Adds a Militant to the Military
     *
     * @param militantOfInterest
     */
    public void addMilitant(Militant militantOfInterest)
    {
        militants.add(militantOfInterest);
    }

    /**
     * Determines if the current military has a militant on a specified tile
     *
     * @param xTile
     * @param yTile
     * @return The verity of the condition
     */
    public boolean existsMilitant(int xTile, int yTile)
    {
        for (int a = 0; a < militants.size(); a++)
        {
            if (militants.get(a).xTile == xTile && militants.get(a).yTile == yTile)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Identifies the index of the current military's unit on the specified tile
     * if such exists. Else, specifies -1
     *
     * @param xTile
     * @param yTile
     * @return
     */
    public int identifyMilitant(int xTile, int yTile)
    {
        for (int a = 0; a < militants.size(); a++)
        {
            if (militants.get(a).xTile == xTile && militants.get(a).yTile == yTile)
            {
                return a;
            }
        }
        return -1;
    }

    /**
     * Calculates the available moves for the militant with the specified index
     *
     * @precondition The militant isn't captured
     * @postcondition Green means open tile
     * @param a The militant's index
     */
    public void calculateAvailableMoves(int a)
    {
        if (!militants.get(a).captured)
        {
            militants.get(a).calculateAvailableMoves();
            militants.get(a).eliminateUnsafeMoves();
        }
    }

    /**
     * Relocates a militant from source to destination
     *
     * @precondition A militant exists in this military, on the specified tile
     * @postcondition The militant moves accordingly
     * @param startXtileSelection
     * @param startYtileSelection
     * @param xTile
     * @param yTile
     */
    public void relocateMilitant(int startXtileSelection, int startYtileSelection, int xTile, int yTile)
    {
        militants.get(identifyMilitant(startXtileSelection, startYtileSelection)).relocateMilitant(xTile, yTile);
    }

    /**
     * Processes the capture of the militant specified by the index
     *
     * @precondition The militant specified by index, isn't captured
     * @postcondition The militant is set to captured and tile indices are
     * (-1,-1)
     * @param militantIndex
     */
    public void processCapture(int militantIndex)
    {
        militants.get(militantIndex).setCapturedStatus();
    }

    /**
     * Selects and assigns new random colors for the current military
     *
     * @throws InterruptedException
     */
    public void selectNewRandomColors() throws InterruptedException
    {
        militaryColor = ChessStart.random.nextInt(0xffffff);
        militaryColor2 = ChessStart.random.nextInt(0xffffff);
        for (Militant militant : militants)
        {
            militant.setNewRandomColors(militaryColor, militaryColor2);
        }
    }

    /**
     * Determines if the specified tile is reachable via capture related
     * movement. Right now, the byTeammates is always false, but maybe for AI
     * development in the future, I'm going to leave that boolean option in
     * there
     *
     * @param xTile
     * @param yTile
     * @return the verity of the condition
     */
    public boolean isCaptureReachable(int xTile, int yTile)
    {
        boolean isReachable = false;
        for (int a = 0; a < getMilitarySize(); a++)
        {
            if (!getMilitant(a).captured)
            {
                if (getMilitant(a).isCaptureReachable(xTile, yTile))
                {
                    isReachable = true;
                }
            }
        }
        return isReachable;
    }

    /**
     * Determines if king is in check
     *
     * @return the verity of the condition
     */
    public boolean isKingInCheck()
    {
        boolean kingIsInCheck;
        if (!GameCore.gameOver)
        {
            kingIsInCheck = enemyOfThisPlayer.isCaptureReachable(militants.get(14).xTile, militants.get(14).yTile);
        }
        else
        {
            kingIsInCheck = false;
        }
        return kingIsInCheck;
    }

    /**
     * Returns the size of the military, used when adding to the military
     *
     * @return
     */
    public int getMilitarySize()
    {
        return militants.size();
    }

    /**
     * Gets the Militant specified with the index
     *
     * @precondition The specified Militant exists
     * @param a the Militant's index
     * @return
     */
    public Militant getMilitant(int a)
    {
        return militants.get(a);
    }

    /**
     * Processes the uncapturing of a Militant specified by index and tile. Used
     * when determining if the king will be in check after a given move.
     *
     * @param captureID
     * @param xTile
     * @param yTile
     */
    public void processUnCapture(int captureID, int xTile, int yTile)
    {
        militants.get(captureID).captured = false;
        militants.get(captureID).xTile = xTile;
        militants.get(captureID).yTile = yTile;
        tileSet.tiles[xTile][yTile].setAssociatedPlayerID(playerID);
    }

    /**
     * Gets the first random color of the military
     *
     * @return
     */
    public int getColor1()
    {
        return militaryColor;
    }

    /**
     * Gets the second random color of the military
     *
     * @return
     */
    public int getColor2()
    {
        return militaryColor2;
    }

    /**
     * Makes sure that a given move doesn't leave the king in check.
     *
     * @param startXtileSelection
     * @param startYtileSelection
     * @param xTile
     * @param yTile
     * @return The verity of the condition
     */
    public boolean isKingSafeInThisMove(int startXtileSelection, int startYtileSelection, int xTile, int yTile)
    {
        Integer captureID = enemyOfThisPlayer.identifyMilitant(xTile, yTile);
        if (tileSet.tiles[xTile][yTile].getAssociatedStatus() && captureID != -1)
        {
            // Delete the enemy militant
            enemyOfThisPlayer.processCapture(captureID);
        }
        thisPlayer.relocateMilitant(startXtileSelection, startYtileSelection, xTile, yTile);
        tileSet.tiles[startXtileSelection][startYtileSelection].clearAssociatedStatus();

        if (thisPlayer.isKingInCheck())
        {
            // Return player to original location, this method just determines possibility
            thisPlayer.relocateMilitant(xTile, yTile, startXtileSelection, startYtileSelection);
            tileSet.tiles[xTile][yTile].clearAssociatedStatus();
            tileSet.tiles[startXtileSelection][startYtileSelection].setAssociatedPlayerID(playerID);
            if (captureID != -1)
            {
                enemyOfThisPlayer.processUnCapture(captureID, xTile, yTile);
            }
            return false;
        }
        else
        {
            // Return player to original location, this method just determines possibility
            relocateMilitant(xTile, yTile, startXtileSelection, startYtileSelection);
            tileSet.tiles[xTile][yTile].clearAssociatedStatus();
            tileSet.tiles[startXtileSelection][startYtileSelection].setAssociatedPlayerID(thisPlayer.getPlayerID());
            if (captureID != -1)
            {
                enemyOfThisPlayer.processUnCapture(captureID, xTile, yTile);
            }
            return true;
        }
    }

    /**
     * After checking to make sure the king is safe after a given move, use this
     * to process the move
     *
     * @precondition isKingSafeInThisMove returns true
     * @param startXtileSelection
     * @param startYtileSelection
     * @param xTile
     * @param yTile
     */
    public void processKingSafeMove(int startXtileSelection, int startYtileSelection, int xTile, int yTile)
    {
        if (tileSet.tiles[xTile][yTile].getAssociatedStatus())
        {
            // Delete the enemy militant
            enemyOfThisPlayer.processCapture(enemyOfThisPlayer.identifyMilitant(xTile, yTile));
        }
        relocateMilitant(startXtileSelection, startYtileSelection, xTile, yTile);
        tileSet.tiles[startXtileSelection][startYtileSelection].clearAssociatedStatus();
    }

    /**
     * Determines if the military's king is checkmated
     *
     * @return
     */
    public boolean isCheckMated()
    {
        // This method uses each militant's list of possible moves, tries each
        // and returns true if none of them lead to a non-check state
        for (int a = 0; a < militants.size(); a++)
        {
            calculateAvailableMoves(a);
            militants.get(a).eliminateUnsafeMoves();
        }
        for (int a = 0; a < militants.size(); a++)
        {
            Militant militantOfInterest = militants.get(a);
            if (!militantOfInterest.captured)
            {
                for (int index = 0; index < militantOfInterest.getValidXmoves().size(); index++)
                {
                    if (isKingSafeInThisMove(militantOfInterest.getXtile(), militantOfInterest.getYtile(), militantOfInterest.getValidXmoves().get(index), militantOfInterest.getValidYmoves().get(index)))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Displays the available moves for the militant with the specified index
     *
     * @param identifyMilitant
     */
    public void displayAvailableMoves(int identifyMilitant)
    {
        militants.get(identifyMilitant).displayAvailableMoves();
    }

    /**
     * Associates the proper tiles with an escape relation
     */
    public void setPossibleEscapes()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                tileSet.tiles[x][y].setPossibleEscapeStart(false);
            }
        }
        for (int a = 0; a < militants.size(); a++)
        {
            if (!militants.get(a).captured)
            {
                if (!militants.get(a).getValidXmoves().isEmpty())
                {
                    tileSet.tiles[militants.get(a).getXtile()][militants.get(a).getYtile()].setPossibleEscapeStart(true);
                }
            }
        }
    }

    /**
     * Selects/de-selects a given tile for the specified player
     *
     * @param xTile
     * @param yTile
     */
    public void selectMilitant(int xTile, int yTile)
    {
        if (existsMilitant(xTile, yTile))
        {
            if (!militants.get(identifyMilitant(xTile, yTile)).getSelectedStatus())
            {
                militantSelected = true;
                selectedX = xTile;
                selectedY = yTile;
                calculateAvailableMoves(identifyMilitant(xTile, yTile));
                displayAvailableMoves(identifyMilitant(xTile, yTile));
                startXtileSelection = xTile;
                startYtileSelection = yTile;
                try
                {
                    Thread.sleep(190);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(TileSet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            militants.get(identifyMilitant(xTile, yTile)).toggleSelectedStatus();
        }
    }

    /**
     * Completely processes the move if tile (startX,startY) was already
     * selected
     *
     * @param startX
     * @param startY
     * @param xTile
     * @param yTile
     * @param optionsMenu
     * @param onlineDenier Doesn't send via internet the move if already sent
     */
    public void processMoveIfValid(int startX, int startY, int xTile, int yTile, OptionsMenu optionsMenu, boolean onlineDenier)
    {
        if (getMilitant(identifyMilitant(startX, startY)).hasPossibleMove(xTile, yTile))
        {
            // Castle calculations when the king is not in check
            if (!isKingInCheck())
            {
                if (getMilitant(14).getXtile() == startX && getMilitant(14).getYtile() == startY)
                {
                    if (startX == 4) // Player 1 castle stuff
                    {
                        // King side castle
                        if (startX - xTile == -2)
                        {
                            relocateMilitant(7, 7, 5, 7);
                            tileSet.tiles[7][7].clearAssociatedStatus();
                        }
                        else if (startX - xTile == 2) // Queen side castle
                        {
                            relocateMilitant(0, 7, 3, 7);
                            tileSet.tiles[0][7].clearAssociatedStatus();
                        }
                    }
                    else if (startX == 3) // Player 2 castle stuff
                    {
                        // King side castle
                        if (startX - xTile == 2)
                        {
                            relocateMilitant(0, 0, 2, 0);
                            tileSet.tiles[0][0].clearAssociatedStatus();
                        }
                        else if (startX - xTile == -2)
                        {
                            relocateMilitant(7, 0, 4, 0);
                            tileSet.tiles[7][0].clearAssociatedStatus();
                        }
                    }
                }
            }

            if (!isKingSafeInThisMove(startX, startY, xTile, yTile))
            {
                return;
            }
            processKingSafeMove(startX, startY, xTile, yTile);

            militantSelected = false;
            militants.get(identifyMilitant(xTile, yTile)).toggleSelectedStatus();

            history.append(getCorrespondingRowLabel(startY) + Integer.toString(startX + 1) + "-" + getCorrespondingRowLabel(yTile) + Integer.toString(xTile + 1));

            if (OptionsMenu.onlineMode)
            {
                if (PlayerTimer.p1Turn && optionsMenu.isHostFirst())
                {
                    history.append("  ");
                }
                else if (!PlayerTimer.p1Turn && optionsMenu.isHostFirst())
                {
                    history.append("\n");
                }
                else if (!PlayerTimer.p1Turn && !optionsMenu.isHostFirst())
                {
                    history.append("  ");
                    System.out.println("stuff");
                }
                else if (PlayerTimer.p1Turn && !optionsMenu.isHostFirst())
                {
                    history.append("\n");
                }
            }
            else
            {
                if (PlayerTimer.p1Turn)
                {
                    history.append("  ");
                }
                else if (!PlayerTimer.p1Turn)
                {
                    history.append("\n");
                }
            }
            if (OptionsMenu.hostMode && OptionsMenu.onlineMode && !onlineDenier)
            {
                Host.getHost().send("" + startX + startY + xTile + yTile + "");
            }
            else if (OptionsMenu.onlineMode && !OptionsMenu.hostMode && !onlineDenier)
            {
                Client.getClient().send("" + startX + startY + xTile + yTile + "");
            }
            history.setCaretPosition(history.getDocument().getLength());
            toggleCriticalMessageColors();
            GameCore.firstCheckCheck = true;
            Sound playerMovedSound = new Sound("playerMovedSound.wav");
            playerMovedSound.playSound();
            try
            {
                Thread.sleep(190);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(TileSet.class.getName()).log(Level.SEVERE, null, ex);
            }
            PlayerTimer.p1Turn = !PlayerTimer.p1Turn;
        }
    }

    private static void toggleCriticalMessageColors()
    {
        if (PlayerTimer.p1Turn)
        {
            criticalGameMessage.setBackground(Color.getColor("", 0x2E19BD));
            criticalGameMessage.setForeground(Color.getColor("", 0x5CF7E5));
        }
        else
        {
            criticalGameMessage.setBackground(Color.getColor("", 0x5CF7E5));
            criticalGameMessage.setForeground(Color.getColor("", 0x2E19BD));
        }
    }

    private String getCorrespondingRowLabel(int row)
    {
        if (row == 0)
        {
            return "a";
        }
        else if (row == 1)
        {
            return "b";
        }
        else if (row == 2)
        {
            return "c";
        }
        else if (row == 3)
        {
            return "d";
        }
        else if (row == 4)
        {
            return "e";
        }
        else if (row == 5)
        {
            return "f";
        }
        else if (row == 6)
        {
            return "g";
        }
        else
        {
            return "h";
        }
    }

    /**
     * Returns true if there is a militant selected
     *
     * @return
     */
    public boolean hasSelection()
    {
        return militantSelected;
    }

    /**
     * Returns the x coordinate of the selected militant
     *
     * @return
     */
    public int getSelectedX()
    {
        return selectedX;
    }

    /**
     * Returns the y coordinate of the selected militant
     *
     * @return
     */
    public int getSelectedY()
    {
        return selectedY;
    }
}