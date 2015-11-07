package andrewLuke.chess.model;

import andrewLuke.chess.graphics.Sprite;
import andrewLuke.chess.graphics.SpriteSheet;

/**
 * The king and related functionality
 *
 * @author Andrew
 */
public class King extends Militant
{

    private static boolean movedAlready;
    private final int xTileStart;
    private final int yTileStart;

    /**
     * Creates a King object
     *
     * @param playerID
     * @param xTile
     * @param yTile
     * @param militaryColor
     * @param militaryColor2
     * @param index
     */
    public King(int playerID, int xTile, int yTile, int militaryColor, int militaryColor2, int index)
    {
        this.playerID = playerID;
        xTileStart = xTile;
        yTileStart = yTile;
        this.xTile = xTile;
        this.yTile = yTile;
        this.index = index;
        militantSprite = new Sprite(100, 75, 5, 0, SpriteSheet.spriteSheet);
        this.militaryColor = militaryColor;
        this.militaryColor2 = militaryColor2;
        isKing = true;
        movedAlready = false;
    }
    @Override
    public void calculateAvailableMoves()
    {
        validXmoves.clear();
        validYmoves.clear();
        // Castle calculations
        if (xTile != xTileStart || yTile != yTileStart)
        {
            movedAlready = true;
        }
        if (playerID == 1) // Team 1
        {
            if (!movedAlready)  // Makes sure king hasn't moved already
            {
                if (!((Rook) thisPlayer.getMilitant(9)).hasMovedAlready())
                {
                    // we need to check each tile inbetween the king and rook for both
                    // unassociated and unreachability by the enemy
                    if (!tileSet.tiles[xTile + 1][yTile].getAssociatedStatus() && !tileSet.tiles[xTile + 2][yTile].getAssociatedStatus())
                    {
                        if (!enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile) && !enemyOfThisPlayer.isCaptureReachable(xTile + 2, yTile))
                        {
                            validXmoves.add(xTile + 2);
                            validYmoves.add(yTile);
                        }
                    }
                }
                if (!((Rook) thisPlayer.getMilitant(8)).hasMovedAlready())
                {
                    // we need to check each tile inbetween the king and rook for both
                    // unassociated and unreachability by the enemy
                    if (!tileSet.tiles[xTile - 1][yTile].getAssociatedStatus() && !tileSet.tiles[xTile - 2][yTile].getAssociatedStatus() && !tileSet.tiles[xTile - 3][yTile].getAssociatedStatus())
                    {
                        if (!enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile) && !enemyOfThisPlayer.isCaptureReachable(xTile - 2, yTile) && !enemyOfThisPlayer.isCaptureReachable(xTile - 3, yTile))
                        {
                            validXmoves.add(xTile - 1);
                            validYmoves.add(yTile);
                        }
                    }
                }
            }
        }
        else if (playerID == 2) // Player 2 Castle Calculations
        {
            if (!movedAlready)  // Makes sure king hasn't moved already
            {
                if (!((Rook) thisPlayer.getMilitant(8)).hasMovedAlready())
                {
                    // we need to check each tile inbetween the king and rook for both
                    // unassociated and unreachability by the enemy
                    if (!tileSet.tiles[xTile - 1][yTile].getAssociatedStatus() && !tileSet.tiles[xTile - 2][yTile].getAssociatedStatus())
                    {
                        if (!enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile) && !enemyOfThisPlayer.isCaptureReachable(xTile - 2, yTile))
                        {
                            validXmoves.add(xTile - 2);
                            validYmoves.add(yTile);
                        }
                    }
                }
                if (!((Rook) thisPlayer.getMilitant(9)).hasMovedAlready())
                {
                    // we need to check each tile inbetween the king and rook for both
                    // unassociated and unreachability by the enemy
                    if (!tileSet.tiles[xTile + 3][yTile].getAssociatedStatus() && !tileSet.tiles[xTile + 2][yTile].getAssociatedStatus() && !tileSet.tiles[xTile + 1][yTile].getAssociatedStatus())
                    {
                        if (!enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile) && !enemyOfThisPlayer.isCaptureReachable(xTile + 2, yTile) && !enemyOfThisPlayer.isCaptureReachable(xTile + 3, yTile))
                        {
                            validXmoves.add(xTile + 2);
                            validYmoves.add(yTile);
                        }
                    }
                }
            }
        }
        // The king is very special.  We need to make sure that we don't
        // allow him to move into harm's way
        // To do that, we can check every potential path that other militants
        // can take to arrive at the king or at a space next to the king

        if ((xTile + 1 < 8) && (yTile - 1 >= 0))
        {
            if (tileSet.tiles[xTile + 1][yTile - 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile + 1][yTile - 1].getAssociatedPlayerID() != playerID)
                {
                    // Now we search all locations reachable to here via enemies
                    if (!enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile - 1))
                    {
                        validXmoves.add(xTile + 1);
                        validYmoves.add(yTile - 1);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile - 1))
                {
                    validXmoves.add(xTile + 1);
                    validYmoves.add(yTile - 1);
                }
            }
        }
        if (xTile + 1 < 8 && (yTile + 1) < 8)
        {
            if (tileSet.tiles[xTile + 1][yTile + 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile + 1][yTile + 1].getAssociatedPlayerID() != playerID)
                {
                    // Now we search all locations reachable to here via enemies
                    if (!enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile + 1))
                    {
                        validXmoves.add(xTile + 1);
                        validYmoves.add(yTile + 1);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!(enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile + 1)))
                {
                    validXmoves.add(xTile + 1);
                    validYmoves.add(yTile + 1);
                }
            }
        }
        if ((xTile + 1 < 8))
        {
            if (tileSet.tiles[xTile + 1][yTile].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile + 1][yTile].getAssociatedPlayerID() != playerID)
                {
                    if (!(enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile)))
                    // Now we search all locations reachable to here via enemies
                    {
                        validXmoves.add(xTile + 1);
                        validYmoves.add(yTile);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!(enemyOfThisPlayer.isCaptureReachable(xTile + 1, yTile)))
                {
                    validXmoves.add(xTile + 1);
                    validYmoves.add(yTile);
                }
            }
        }
        // These were the 3 moves to the right
        // Now, the 3 moves to the left

        if ((xTile - 1) >= 0)
        {
            if (tileSet.tiles[xTile - 1][yTile].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile - 1][yTile].getAssociatedPlayerID() != playerID)
                {
                    // Now we search all locations reachable to here via enemies
                    if (!(enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile)))
                    {
                        validXmoves.add(xTile - 1);
                        validYmoves.add(yTile);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!(enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile)))
                {
                    validXmoves.add(xTile - 1);
                    validYmoves.add(yTile);
                }
            }
        }
        if ((xTile - 1 >= 0) && (yTile - 1) >= 0)
        {
            if (tileSet.tiles[xTile - 1][yTile - 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile - 1][yTile - 1].getAssociatedPlayerID() != playerID)
                {
                    if (!(enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile - 1)))
                    // Now we search all locations reachable to here via enemies
                    {
                        validXmoves.add(xTile - 1);
                        validYmoves.add(yTile - 1);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!(enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile - 1)))
                {
                    validXmoves.add(xTile - 1);
                    validYmoves.add(yTile - 1);
                }
            }
        }
        if ((xTile - 1 >= 0) && (yTile + 1) < 8)
        {
            if (tileSet.tiles[xTile - 1][yTile + 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile - 1][yTile + 1].getAssociatedPlayerID() != playerID)
                {
                    // Now we search all locations reachable to here via enemies
                    if (!(enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile + 1)))
                    {
                        validXmoves.add(xTile - 1);
                        validYmoves.add(yTile + 1);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!(enemyOfThisPlayer.isCaptureReachable(xTile - 1, yTile + 1)))
                {
                    validXmoves.add(xTile - 1);
                    validYmoves.add(yTile + 1);
                }
            }
        }

        // Now, vertically
        if ((yTile - 1) >= 0)
        {
            if (tileSet.tiles[xTile][yTile - 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile][yTile - 1].getAssociatedPlayerID() != playerID)
                {
                    // Now we search all locations reachable to here via enemies
                    if (!(enemyOfThisPlayer.isCaptureReachable(xTile, yTile - 1)))
                    {
                        validXmoves.add(xTile);
                        validYmoves.add(yTile - 1);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!(enemyOfThisPlayer.isCaptureReachable(xTile, yTile - 1)))
                {
                    validXmoves.add(xTile);
                    validYmoves.add(yTile - 1);
                }
            }
        }
        if ((yTile + 1) < 8)
        {
            if (tileSet.tiles[xTile][yTile + 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile][yTile + 1].getAssociatedPlayerID() != playerID)
                {
                    // Now we search all locations reachable to here via enemies
                    if (!(enemyOfThisPlayer.isCaptureReachable(xTile, yTile + 1)))
                    {
                        validXmoves.add(xTile);
                        validYmoves.add(yTile + 1);
                    }
                }
            }
            else
            {
                // Now we search all locations reachable to here via enemies
                if (!(enemyOfThisPlayer.isCaptureReachable(xTile, yTile + 1)))
                {
                    validXmoves.add(xTile);
                    validYmoves.add(yTile + 1);
                }
            }
        }
    }

    // I don't think this should ever be used because a king can never move next to another king
    @Override
    public boolean isCaptureReachable(int x, int y)
    {
        if ((xTile + 1 < 8) && (yTile - 1 >= 0))
        {
            if (xTile + 1 == x && yTile - 1 == y)
            {
                return true;
            }
        }
        if (xTile + 1 < 8 && (yTile + 1) < 8)
        {
            if (xTile + 1 == x && yTile + 1 == y)
            {
                return true;
            }
        }
        if ((xTile + 1 < 8))
        {
            if (xTile + 1 == x && yTile == y)
            {
                return true;
            }
        }
        // These were the 3 moves to the right
        // Now, the 3 moves to the left

        if ((xTile - 1) >= 0)
        {
            if (xTile - 1 == x && yTile == y)
            {
                return true;
            }
        }
        if ((xTile - 1 >= 0) && (yTile - 1) >= 0)
        {
            if (xTile - 1 == x && yTile - 1 == y)
            {
                return true;
            }
        }
        if ((xTile - 1 >= 0) && (yTile + 1) < 8)
        {
            if (xTile - 1 == x && yTile + 1 == y)
            {
                return true;
            }
        }

        // Now, vertically
        if ((yTile - 1) >= 0)
        {
            if (xTile == x && yTile - 1 == y)
            {
                return true;
            }
        }
        if ((yTile + 1) < 8)
        {
            if (xTile == x && yTile + 1 == y)
            {
                return true;
            }
        }
        return false;
    }
}
