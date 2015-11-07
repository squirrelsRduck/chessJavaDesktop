package andrewLuke.chess.model;

import andrewLuke.chess.application.ChessStart;
import andrewLuke.chess.graphics.Sprite;
import andrewLuke.chess.view.GameView;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Each specific militant will extend this class
 *
 * @author Andrew
 */
public abstract class Militant
{

    protected int playerID;
    protected int xTile;
    protected int yTile;
    protected int index;
    protected boolean isAlreadySelected = false;
    protected ArrayList<Integer> validXmoves = new ArrayList();
    protected ArrayList<Integer> validYmoves = new ArrayList();
    protected Sprite militantSprite;
    protected static int spriteWidth = 100;
    protected static int spriteHeight = 75;
    protected int militaryColor;
    protected int militaryColor2;
    public boolean captured = false;
    protected boolean isKing = false;
    protected boolean isPawn = false;
    protected TileSet tileSet;
    protected Player thisPlayer;
    protected Player enemyOfThisPlayer;

    /**
     * Updates the player's military pixels on the tileSet
     *
     * @precondition The players and TileSet are already created so that their
     * pointers (reference variables) are properly received
     * @param player1
     * @param player2
     * @param tileSet
     */
    public void update(Player player1, Player player2, TileSet tileSet)
    {
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

        this.tileSet = tileSet;

        // Pawn upgrades
        if ((isPawn && yTile == 7) || (isPawn && yTile == 0))
        {
            int xTileMemory = xTile;
            int yTileMemory = yTile;
            thisPlayer.processCapture(index);
            tileSet.tiles[xTileMemory][yTileMemory].clearAssociatedStatus();
            thisPlayer.addMilitant(new Queen(playerID, xTileMemory, yTileMemory, thisPlayer.getColor1(), thisPlayer.getColor2(), thisPlayer.getMilitarySize()));
        }

        if (!captured)
        {
            tileSet.tiles[xTile][yTile].setAssociatedPlayerID(playerID);

            // This will update the pixel array with the sprite, on the given tile
            for (int y = 0; y < spriteHeight; y++)  // coordinate of each y-pixel on the actual cat-sprite
            {
                for (int x = 0; x < spriteWidth; x++) // coordinate of each x-pixel on the actual cat-sprite
                {
                    int xc = (int) (x + xTile * (double) (ChessStart.width) / 8.0); // where to place each x-pixel on the overall canvas
                    int yc = (int) (y + yTile * (double) (ChessStart.height) / 8.0); // where to place each y-pixel on the overall canvas

                    int spritePixel = militantSprite.pixelArray[y * spriteWidth + x]; // Gets the pixel from the sprite
                    // This next line of code ignores/doesn't-assign-to-pixelArray pixels that are of the militant's background color

                    //if((yc) * ChessStart.width + xc < ChessStart.height * ChessStart.width)
                    if (spritePixel != 0xff007F46 && spritePixel != 0xff007F47 && spritePixel != 0xff007F45) // Don't forget the ff prefix for alpha channel, can be used to make transparent cat
                    {
                        if (spritePixel == 0xff2DFF0C)
                        {
                            GameView.pixelArray[(yc) * ChessStart.width + xc] = militaryColor2;
                        }
                        else
                        {
                            GameView.pixelArray[(yc) * ChessStart.width + xc] = militaryColor;//0xffffffff;//spritePixel;
                        }
                    }
                }
            }
        }
    }

    /**
     * Relocates a militant to the specified tile
     *
     * @param xTile
     * @param yTile
     */
    public void relocateMilitant(int xTile, int yTile)
    {
        this.xTile = xTile;
        this.yTile = yTile;
        tileSet.tiles[xTile][yTile].setAssociatedPlayerID(playerID);
    }

    /**
     * Sets the given militant as captured
     *
     * @precondition The militant isn't captured
     * @postcondition The militant is labeled as captured and tile coordinates
     * are set to (-1,-1).
     */
    public void setCapturedStatus()
    {
        captured = true;
        xTile = -1;
        yTile = -1;
    }

    /**
     * Sets new colors to the militant, randomized in the calling class
     *
     * @param militaryColor
     * @param militaryColor2
     * @throws InterruptedException
     */
    public void setNewRandomColors(int militaryColor, int militaryColor2) throws InterruptedException
    {
        this.militaryColor = militaryColor;
        this.militaryColor2 = militaryColor2;
        Thread.sleep(10);
    }

    /**
     * Calculates the available moves for the specific militant yet does not
     * take into consideration the possibility that the current player's king is
     * in check. Such is determined elsewhere, in the Military class method
     * isKingSafeInThisMove(), which is used in the eliminateUnsafeMoves()
     * method which should be called right after this calculateAvailableMoves()
     * method
     *
     * @precondition Both players' militaries have been created
     * @postcondition The militant now contains two arraylists of the same size.
     * validXmoves contains the x-coordinates, validYmoves contains the
     * y-coordinates
     */
    public void calculateAvailableMoves()
    {
    }

    /**
     * @return the x tile of the militant
     */
    public int getXtile()
    {
        return xTile;
    }

    /**
     *
     * @return the y tile of the militant
     */
    public int getYtile()
    {
        return yTile;
    }

    /**
     * @precondition Both militaries are created
     * @return The ArrayList of Integers representing the x-coordinates of valid
     * moves for the militant to move
     */
    public ArrayList<Integer> getValidXmoves()
    {
        return validXmoves;
    }

    /**
     * @precondition Both militaries are created
     * @return The ArrayList of Integers representing the y-coordinates of valid
     * moves for the militant to move
     */
    public ArrayList<Integer> getValidYmoves()
    {
        return validYmoves;
    }

    /**
     * Determines if the militant has a specified move available to try
     *
     * @param xTile
     * @param yTile
     * @return
     */
    public boolean hasPossibleMove(int xTile, int yTile)
    {
        for (int a = 0; a < validXmoves.size(); a++)
        {
            if (validXmoves.get(a) == xTile && validYmoves.get(a) == yTile)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays the possible moves
     */
    public void displayAvailableMoves()
    {
        for (int a = 0; a < validXmoves.size(); a++)
        {
            tileSet.tiles[validXmoves.get(a)][validYmoves.get(a)].setTempDefaultColor(Color.green.getRGB());
        }
    }

    /**
     * This determines if a unit can reach a given tile. Used only for
     * determining if any enemy unit can reach the king or a tile surrounding
     * the king. The reason for this is because the pawn has very unique
     * behavior. Whenever determining if a pawn can make a certain tile
     * hazardous for a king, it's different than for any other character.
     * Because, the pawn only captures diagonally, whereas it can move forwards
     * but not capture. This way, we are able to test if the pawn can move
     * diagonally to a given tile even if there is no enemy unit on that tile.
     * We exclude pawn intra-columnal motion from this calculation.
     *
     * @precondition Both militaries are created
     * @param xTile
     * @param yTile
     * @return True implies that the current militant can reach the specified
     * tile.
     */
    public boolean isCaptureReachable(int xTile, int yTile)
    {
        return false;
    }

    /**
     * Whenever possible moves are calculated, we also eliminate the moves that
     * are unsafe for the king. This does that.
     */
    public void eliminateUnsafeMoves()
    {
        if (!captured)
        {
            int quantityOfValidMoves = validXmoves.size();
            for (int a = quantityOfValidMoves - 1; a >= 0; a--)
            {
                if (!thisPlayer.isKingSafeInThisMove(xTile, yTile, validXmoves.get(a), validYmoves.get(a)))
                {
                    tileSet.tiles[validXmoves.get(a)][validYmoves.get(a)].resetDefaultColor();
                    validXmoves.remove(a);
                    validYmoves.remove(a);
                }
            }
        }
    }

    /**
     * Returns true if the militant is already selected
     *
     * @return
     */
    public boolean getSelectedStatus()
    {
        return isAlreadySelected;
    }

    /**
     * Toggles the militant's selected status
     */
    public void toggleSelectedStatus()
    {
        isAlreadySelected = !isAlreadySelected;
    }
}
