package andrewLuke.chess.model;

import andrewLuke.chess.settings.OptionsMenu;
import javax.swing.JTextArea;

/**
 * Manages each player and their associated data
 *
 * @author Andrew
 */
public class Player
{

    private int playerID = -1; // self-explanatory: in actual game, id's 1 and 2 are used
    private final Military military;

    /**
     * Sets the playerID
     *
     * @param playerofInterestID The ID to be set
     */
    public Player(int playerofInterestID,JTextArea history)
    {
        playerID = playerofInterestID;
        military = new Military(playerofInterestID,history);
    }

    /**
     * Returns the player's ID
     *
     * @return
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Updates each player's military, mostly just pixel updates for the
     * BufferedImage
     *
     * @param player1
     * @param player2
     * @param tileSet
     */
    public void update(Player player1, Player player2, TileSet tileSet, boolean bypassBoolean, boolean onlineDenier, OptionsMenu optionsMenu)
    {
        military.update(player1, player2, tileSet, bypassBoolean, onlineDenier,optionsMenu);
    }

    /**
     * Add a specified militant to the player's military
     *
     * @param militantOfInterest
     */
    public void addMilitant(Militant militantOfInterest)
    {
        military.addMilitant(militantOfInterest);
    }

    /**
     * Gets the player's 1st random color
     *
     * @return
     */
    public int getColor1()
    {
        return military.getColor1();
    }

    /**
     * Determines if there exists a militant in the player's military, on the
     * specified tile
     *
     * @param xTile
     * @param yTile
     * @return
     */
    public boolean existsMilitant(int xTile, int yTile)
    {
        return military.existsMilitant(xTile, yTile);
    }

    /**
     * Identifies the index of a militant in the military, at the specified tile
     *
     * @param xTile
     * @param yTile
     * @return
     */
    public int identifyMilitant(int xTile, int yTile)
    {
        return military.identifyMilitant(xTile, yTile);
    }

    /**
     * Calculates and displays the available moves for the militant with the
     * specified index
     *
     * @param a
     */
    public void calculateAvailableMoves(int a)
    {
        military.calculateAvailableMoves(a);
    }

    /**
     * Relocates a militant from a given tile to another
     *
     * @param startXtileSelection
     * @param startYtileSelection
     * @param xTile
     * @param yTile
     */
    public void relocateMilitant(int startXtileSelection, int startYtileSelection, int xTile, int yTile)
    {
        military.relocateMilitant(startXtileSelection, startYtileSelection, xTile, yTile);
    }

    /**
     * Processes the capture of a militant specified by index
     *
     * @param militantIndex
     */
    public void processCapture(int militantIndex)
    {
        military.processCapture(militantIndex);
    }

    /**
     * Selects new random colors for the player's militants
     *
     * @throws InterruptedException
     */
    public void selectNewRandomColors() throws InterruptedException
    {
        military.selectNewRandomColors();
    }

    /**
     * Determines if the given tile is reachable
     *
     * @param xTile
     * @param yTile
     * @param byTeammates
     * @return
     */
    public boolean isCaptureReachable(int xTile, int yTile)
    {
        return military.isCaptureReachable(xTile, yTile);
    }

    /**
     * Determines if the player's king is in check
     *
     * @return
     */
    public boolean isKingInCheck()
    {
        return military.isKingInCheck();
    }

    /**
     * Returns the player's military's size
     *
     * @return
     */
    public int getMilitarySize()
    {
        return military.getMilitarySize();
    }

    /**
     * Returns the Militant at the specified index
     *
     * @param a
     * @return
     */
    public Militant getMilitant(int a)
    {
        return military.getMilitant(a);
    }

    /**
     * Processes the uncapturing of a militant specified by index and tile.
     * Useful in calculating if a certain move gets the king out of check.
     *
     * @param captureID
     * @param xTile
     * @param yTile
     */
    public void processUnCapture(int captureID, int xTile, int yTile)
    {
        military.processUnCapture(captureID, xTile, yTile);
    }

    /**
     * Returns the military's second color
     *
     * @return
     */
    public int getColor2()
    {
        return military.getColor2();
    }

    /**
     * Determines if the king will be safe after the specified move
     *
     * @param startXtileSelection
     * @param startYtileSelection
     * @param xTile
     * @param yTile
     * @return
     */
    public boolean isKingSafeInThisMove(int startXtileSelection, int startYtileSelection, int xTile, int yTile)
    {
        return military.isKingSafeInThisMove(startXtileSelection, startYtileSelection, xTile, yTile);
    }

    /**
     * Processes the move that is already determined as king-safe.
     *
     * @param startXtileSelection
     * @param startYtileSelection
     * @param xTile
     * @param yTile
     */
    public void processKingSafeMove(int startXtileSelection, int startYtileSelection, int xTile, int yTile)
    {
        military.processKingSafeMove(startXtileSelection, startYtileSelection, xTile, yTile);
    }

    /**
     * Determines if the player's king is checkmated
     *
     * @return
     */
    public boolean isCheckMated()
    {
        return military.isCheckMated();
    }

    /**
     * Displays the available moves for the given militant specified by index
     * @param identifyMilitant
     */
    public void displayAvailableMoves(int identifyMilitant)
    {
        military.displayAvailableMoves(identifyMilitant);
    }

    /**
     * Associates the proper tiles with an escape relation
     */
    public void setPossibleEscapes()
    {
        military.setPossibleEscapes();
    }

    /**
     * Selects a militant at the given tile
     * @param startX
     * @param startY
     */
    public void selectMilitant(int startX, int startY)
    {
        military.selectMilitant(startX, startY);
    }

    /**
     * Determines if a specified move is valid.  If valid, it processes it.
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param optionsMenu
     * @param onlineDenier
     */
    public void processMoveIfValid(int startX, int startY, int endX, int endY, OptionsMenu optionsMenu, boolean onlineDenier)
    {
        military.processMoveIfValid(startX, startY, endX, endY, optionsMenu, onlineDenier);
    }

    /**
     * Determines if a militant is selected
     * @return
     */
    public boolean hasSelection()
    {
        return military.hasSelection();
    }
    
    /**
     * Gets the selected militant's x-value
     * @return
     */
    public int getSelectedX()
    {
        return military.getSelectedX();
    }
    
    /**
     * Gets the selected militant's y-value
     * @return
     */
    public int getSelectedY()
    {
        return military.getSelectedY();
    }
}