package andrewLuke.chess.model;

import andrewLuke.chess.application.ChessStart;
import andrewLuke.chess.control.Keyboard;
import andrewLuke.chess.control.Mouse;
import andrewLuke.chess.time.PlayerTimer;
import andrewLuke.chess.view.GameView;

/**
 * This is the set of 64 tiles and related properties
 *
 * @author Andrew
 */
public class TileSet
{

    private final int tileWidth;
    private final int tileHeight;
    public Tile tiles[][];

    boolean tileColorSelector = true;
    public boolean tileSelected = false;

    private Player activePlayer;

    /**
     * This creates the set of 64 tiles
     *
     */
    public TileSet()
    {
        tileWidth = ChessStart.width / 8;
        tileHeight = ChessStart.height / 8;
        tiles = new Tile[8][8];
        // Creates each tile and sets to a random color
        int color1 = ChessStart.random.nextInt(0xffffff);
        int color2 = ChessStart.random.nextInt(0xffffff);
        for (int a = 0; a < 8; a++)
        {
            for (int b = 0; b < 8; b++)
            {
                tiles[a][b] = new Tile();
                if (tileColorSelector)
                {
                    tiles[a][b].setDefaultColor(color1);
                    tileColorSelector = !tileColorSelector;
                }
                else
                {
                    tiles[a][b].setDefaultColor(color2);
                    tileColorSelector = !tileColorSelector;
                }
            }
            tileColorSelector = !tileColorSelector;
        }
    }

    // Getters for the index of each tile, when given a pixel-coordinate
    /**
     * Gets the x-index for the tile over which the mouse resides
     *
     * @param x X-pixel on JFrame
     * @return The x-index for the tile over which the mouse resides
     */
    public int getTileXIndex(int x)
    {
        return (x / (ChessStart.width / 8));
    }

    /**
     * Gets the y-index for the tile over which the mouse resides
     *
     * @param y Y-pixel on JFrame
     * @return The y-index for the tile over which the mouse resides
     */
    public int getTileYIndex(int y)
    {
        return (y / ((ChessStart.height) / 8));
    }

    /**
     * Gets the index for the tile over which the mouse resides Not used in this
     * program but useful anyways
     *
     * @param x The x-index for the tile of interest
     * @param y The y-index for the tile of interest
     * @return
     */
    public Tile getTile(int x, int y)
    {
        return tiles[x][y];
    }

    private void resetOtherTileColorsToTempDefault(int xTile, int yTile)
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (xTile != x || yTile != y)
                {
                    tiles[x][y].resetTempDefaultColor();
                }
            }
        }
    }

    /**
     * Resets the tile colors to their default colors
     */
    public void resetTileColorsToDefault()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                tiles[x][y].resetDefaultColor();
            }
        }
    }

    /**
     * Manages tile selection and overall move making.
     *
     * @precondition Both players are instantiated
     * @postcondition the TileSet is updated
     * @param player1 Player 1
     * @param player2 Player 2
     * @throws java.lang.InterruptedException
     */
    public void update(Player player1, Player player2) throws InterruptedException
    {
        if (PlayerTimer.p1Turn)
        {
            activePlayer = player1;
        }
        else
        {
            activePlayer = player2;
        }

        // Then we determine if user input is occuring
        int x = Mouse.getMouseX();
        int y = Mouse.getMouseY();
        int xTile = getTileXIndex(x);
        int yTile = getTileYIndex(y);

        resetOtherTileColorsToTempDefault(xTile, yTile);

        if ('t' == Keyboard.getCharOfInterest())
        {
            setNewRandomColors();
        }
        if (activePlayer.hasSelection())
        {
            tiles[activePlayer.getSelectedX()][activePlayer.getSelectedY()].setTempDefaultColor(ChessStart.random.nextInt());
            resetOtherTileColorsToTempDefault(activePlayer.getSelectedX(), activePlayer.getSelectedY());
        }
        else if (activePlayer.isKingInCheck())
        {  
            resetTileColorsToDefault();
            highlightPossibleEscapes();
        }
        else
        {
            resetTileColorsToDefault();
        }

        tiles[xTile][yTile].setRandomColor();

        // Draw the tiles
        for (int yc = 0; yc < 600; yc++)
        {
            for (int xc = 0; xc < 800; xc++)
            {
                GameView.pixelArray[yc * 800 + xc] = (tiles[getTileXIndex(xc)][getTileYIndex(yc)]).getColor(); //  0x  RR GG BB
            }
        }
    }

    private void setNewRandomColors() throws InterruptedException
    {
        int color1 = ChessStart.random.nextInt(0xffffff);
        int color2 = ChessStart.random.nextInt(0xffffff);
        for (int a = 0; a < 8; a++)
        {
            for (int b = 0; b < 8; b++)
            {
                if (tileColorSelector)
                {
                    tiles[a][b].setDefaultColor(color1);
                    tileColorSelector = !tileColorSelector;
                }
                else
                {
                    tiles[a][b].setDefaultColor(color2);
                    tileColorSelector = !tileColorSelector;
                }
            }
            tileColorSelector = !tileColorSelector;
        }
        Thread.sleep(190);
    }

    private void highlightPossibleEscapes()
    {
        for(int x=0;x<8;x++)
        {
            for(int y=0;y<8;y++)
            {
                if(tiles[x][y].getIsPossibleEscapeStart())
                {
                    tiles[x][y].setTempDefaultColor(0xffFCA3FF);
                }
            }
        }
    }
}