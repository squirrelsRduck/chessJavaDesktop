package andrewLuke.chess.model;

import andrewLuke.chess.application.ChessStart;

/**
 * One of the 64 tiles in a chess game
 *
 * @author Andrew
 */
public class Tile
{
    private int associatedPlayerID = -1; //-1 implies the tile is not selected yet, else the value gives the player's ID
    private int tileColor;
    private int defaultTileColor;
    private int tempDefaultTileColor;
    private boolean isAlreadyAssociated = false;
    private boolean isPossibleEscapeStart = false;

    /**
     * Sets a playerID to the tile
     * @param playerID
     */
    public void setAssociatedPlayerID(int playerID)
    {
        associatedPlayerID = playerID;
        setAssociatedStatus();
    }

    /**
     * Gets the player ID associated with this tile
     *
     * @return the player's ID associated with this tile, -1 if nonexistent
     */
    public int getAssociatedPlayerID()
    {
        return associatedPlayerID;
    }

    /**
     * Sets the boolean to true, noting that this tile is associated with a militant
     */
    public void setAssociatedStatus()
    {
        isAlreadyAssociated = true;
    }

    /**
     * Clears the tile's association with any militant
     */
    public void clearAssociatedStatus()
    {
        isAlreadyAssociated = false;
        associatedPlayerID = -1;
    }

    /**
     * Determines if the tile is associated with a militant
     * @return
     */
    public boolean getAssociatedStatus()
    {
        return isAlreadyAssociated;
    }

    /**
     * Sets a random color to the tile
     */
    public void setRandomColor()
    {
        tileColor = ChessStart.random.nextInt(0xffffff);
    }

    /**
     * Sets the default color for the tile.  This is the main tile color.
     * Other types of coloring are used to remember possible moves for the player
     * even when the cursor is moving around and recoloring stuff
     * @param colorOfInterest
     */
    public void setDefaultColor(int colorOfInterest)
    {
        defaultTileColor = colorOfInterest;
        tempDefaultTileColor = colorOfInterest;
        tileColor = colorOfInterest;
    }

    /**
     * This stores possible moves for the player
     * even when the cursor is moving around and recoloring stuff
     * @precondition A tile is selected
     * @postcondition The tile displays color info based on possible moves
     * @param colorOfInterest
     */
    public void setTempDefaultColor(int colorOfInterest)
    {
        tempDefaultTileColor = colorOfInterest;
        tileColor = colorOfInterest;
    }

    /**
     * Resets to the default color, usually after a valid move or if a tile
     * is de-selected
     */
    public void resetDefaultColor()
    {
        tempDefaultTileColor = defaultTileColor;
        tileColor = defaultTileColor;
    }

    /**
     * Resets to the default color, usually after the cursor moves over the tiles
     * and temporarily re-colors it elsewise
     * @precondition A tile is selected
     * @postcondition A tile is selected
     */
    public void resetTempDefaultColor()
    {
        tileColor = tempDefaultTileColor;
    }

    /**
     * Gets the color of the tile
     *
     * @return Returns the color of the tile
     */
    public int getColor()
    {
        return tileColor;
    }
    
    /**
     * Gets the temp default tile color
     * @precondition A tile is selected
     * @postcondition Returns the tile color used to reset tiles colors when a
     * tile is selected and the cursor re-colors stuff.  A tile is still selected
     * @return
     */
    public int getTempDefaultTileColor()
    {
        return tempDefaultTileColor;
    }
    
    /**
     * Denotes that this tile can get the player out of check
     */
    public void setPossibleEscapeStart(boolean verity)
    {
        isPossibleEscapeStart = verity;
    }
    
    public boolean getIsPossibleEscapeStart()
    {
        return isPossibleEscapeStart;
    }
}