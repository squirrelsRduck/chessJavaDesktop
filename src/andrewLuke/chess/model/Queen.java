package andrewLuke.chess.model;

import andrewLuke.chess.graphics.Sprite;
import andrewLuke.chess.graphics.SpriteSheet;

/**
 * The queen and related functionality
 *
 * @author Andrew
 */
public class Queen extends Militant
{

    /**
     * Creates a Queen instance
     *
     * @param playerID
     * @param xTile
     * @param yTile
     * @param militaryColor
     * @param militaryColor2
     * @param index
     */
    public Queen(int playerID, int xTile, int yTile, int militaryColor, int militaryColor2, int index)
    {
        this.playerID = playerID;
        this.xTile = xTile;
        this.yTile = yTile;
        this.index = index;
        militantSprite = new Sprite(100, 75, 4, 0, SpriteSheet.spriteSheet);
        this.militaryColor = militaryColor;
        this.militaryColor2 = militaryColor2;
    }

    @Override
    public void calculateAvailableMoves()
    {
        validXmoves.clear();
        validYmoves.clear();
        if (!captured)
        {

            // Same as bishop and rook
            // Forwards right
            for (int a = 1; a < 8; a++)
            {
                if (xTile + a > 7 || yTile - a < 0)
                {
                    break;
                }

                if (tileSet.tiles[xTile + a][yTile - a].getAssociatedStatus())
                {
                    if (tileSet.tiles[xTile + a][yTile - a].getAssociatedPlayerID() != playerID)
                    {
                        validXmoves.add(xTile + a);
                        validYmoves.add(yTile - a);
                        break;
                    }
                    else if (tileSet.tiles[xTile + a][yTile - a].getAssociatedPlayerID() == playerID)
                    {
                        break;
                    }
                }
                else
                {
                    validXmoves.add(xTile + a);
                    validYmoves.add(yTile - a);
                }
            }

            // Forwards Left
            for (int a = 1; a < 8; a++)
            {
                if (xTile - a < 0 || yTile - a < 0)
                {
                    break;
                }
                if (tileSet.tiles[xTile - a][yTile - a].getAssociatedStatus())
                {
                    if (tileSet.tiles[xTile - a][yTile - a].getAssociatedPlayerID() != playerID)
                    {
                        validXmoves.add(xTile - a);
                        validYmoves.add(yTile - a);
                        break;
                    }
                    else if (tileSet.tiles[xTile - a][yTile - a].getAssociatedPlayerID() == playerID)
                    {
                        break;
                    }
                }
                else
                {
                    validXmoves.add(xTile - a);
                    validYmoves.add(yTile - a);
                }
            }

            // Backwards Left
            for (int a = 1; a < 8; a++)
            {
                if (xTile - a < 0 || yTile + a > 7)
                {
                    break;
                }
                if (tileSet.tiles[xTile - a][yTile + a].getAssociatedStatus())
                {
                    if (tileSet.tiles[xTile - a][yTile + a].getAssociatedPlayerID() != playerID)
                    {
                        validXmoves.add(xTile - a);
                        validYmoves.add(yTile + a);
                        break;
                    }
                    else if (tileSet.tiles[xTile - a][yTile + a].getAssociatedPlayerID() == playerID)
                    {
                        break;
                    }
                }
                else
                {
                    validXmoves.add(xTile - a);
                    validYmoves.add(yTile + a);
                }
            }
            // Backwards Right
            for (int a = 1; a < 8; a++)
            {
                if (xTile + a > 7 || yTile + a > 7)
                {
                    break;
                }
                if (tileSet.tiles[xTile + a][yTile + a].getAssociatedStatus())
                {
                    if (tileSet.tiles[xTile + a][yTile + a].getAssociatedPlayerID() != playerID)
                    {
                        validXmoves.add(xTile + a);
                        validYmoves.add(yTile + a);
                        break;
                    }
                    else if (tileSet.tiles[xTile + a][yTile + a].getAssociatedPlayerID() == playerID)
                    {
                        break;
                    }
                }
                else
                {
                    validXmoves.add(xTile + a);
                    validYmoves.add(yTile + a);
                }
            }
            // There are 4 possible directions for a rook to move
            // Backwards
            for (int a = 1; a < 8; a++)
            {
                if (yTile + a >= 8)
                {
                    break;
                }
                if (tileSet.tiles[xTile][yTile + a].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile][yTile + a].getAssociatedStatus())
                {
                    validXmoves.add(xTile);
                    validYmoves.add(yTile + a);
                    break;
                }
                else if (tileSet.tiles[xTile][yTile + a].getAssociatedPlayerID() == playerID)
                {
                    break;
                }
                else
                {
                    validXmoves.add(xTile);
                    validYmoves.add(yTile + a);
                }
            }

            // Forwards
            for (int a = 1; a < 8; a++)
            {
                if (yTile - a < 0)
                {
                    break;
                }
                if (tileSet.tiles[xTile][yTile - a].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile][yTile - a].getAssociatedStatus())
                {
                    validXmoves.add(xTile);
                    validYmoves.add(yTile - a);
                    break;
                }
                else if (tileSet.tiles[xTile][yTile - a].getAssociatedPlayerID() == playerID)
                {
                    break;
                }
                else
                {
                    validXmoves.add(xTile);
                    validYmoves.add(yTile - a);
                }
            }

       // Left
            for (int a = 1; a < 8; a++)
            {
                if (xTile - a < 0)
                {
                    break;
                }

                if (tileSet.tiles[xTile - a][yTile].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile - a][yTile].getAssociatedStatus())
                {
                    validXmoves.add(xTile - a);
                    validYmoves.add(yTile);

                    break;
                }
                else if (tileSet.tiles[xTile - a][yTile].getAssociatedPlayerID() == playerID)
                {
                    break;
                }
                else
                {
                    validXmoves.add(xTile - a);
                    validYmoves.add(yTile);

                }
            }

            // Right
            for (int a = 1; a < 8; a++)
            {
                if (xTile + a >= 8)
                {
                    break;
                }
                if (tileSet.tiles[xTile + a][yTile].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile + a][yTile].getAssociatedStatus())
                {
                    validXmoves.add(xTile + a);
                    validYmoves.add(yTile);
                    break;
                }
                else if (tileSet.tiles[xTile + a][yTile].getAssociatedPlayerID() == playerID)
                {
                    break;
                }
                else
                {
                    validXmoves.add(xTile + a);
                    validYmoves.add(yTile);
                }
            }
        }
    }

    @Override
    public boolean isCaptureReachable(int x, int y)
    {
        if (!captured)
        {
            // There are 4 possible directions for a bishop to move
            // Forwards right
            for (int a = 1; a < 8; a++)
            {
                if (xTile + a > 7 || yTile - a < 0)
                {
                    break;
                }

                if (tileSet.tiles[xTile + a][yTile - a].getAssociatedStatus() && (tileSet.tiles[xTile + a][yTile - a].getAssociatedPlayerID() != playerID))
                {
                    if (xTile + a == x && yTile - a == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile + a][yTile - a].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile + a == x && yTile - a == y)
                    {
                        return true;
                    }
                }
            }

            // Forwards Left
            for (int a = 1; a < 8; a++)
            {
                if (xTile - a < 0 || yTile - a < 0)
                {
                    break;
                }
                if (tileSet.tiles[xTile - a][yTile - a].getAssociatedStatus() && (tileSet.tiles[xTile - a][yTile - a].getAssociatedPlayerID() != playerID))
                {

                    if (xTile - a == x && yTile - a == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile - a][yTile - a].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile - a == x && yTile - a == y)
                    {
                        return true;
                    }
                }
            }

            // Backwards Left
            for (int a = 1; a < 8; a++)
            {
                if (xTile - a < 0 || yTile + a > 7)
                {
                    break;
                }
                if (tileSet.tiles[xTile - a][yTile + a].getAssociatedStatus() && (tileSet.tiles[xTile - a][yTile + a].getAssociatedPlayerID() != playerID))
                {
                    if (xTile - a == x && yTile + a == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile - a][yTile + a].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile - a == x && yTile + a == y)
                    {
                        return true;
                    }
                }
            }
            // Backwards Right
            for (int a = 1; a < 8; a++)
            {
                if (xTile + a > 7 || yTile + a > 7)
                {
                    break;
                }
                if (tileSet.tiles[xTile + a][yTile + a].getAssociatedStatus() && (tileSet.tiles[xTile + a][yTile + a].getAssociatedPlayerID() != playerID))
                {
                    if (xTile + a == x && yTile + a == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile + a][yTile + a].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile + a == x && yTile + a == y)
                    {
                        return true;
                    }
                }
            }

            // There are 4 possible directions for a rook to move
            // Backwards
            for (int a = 1; a < 8; a++)
            {
                if (yTile + a >= 8)
                {
                    break;
                }
                if ((tileSet.tiles[xTile][yTile + a].getAssociatedPlayerID() != playerID) && tileSet.tiles[xTile][yTile + a].getAssociatedStatus())
                {
                    if (xTile == x && yTile + a == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile][yTile + a].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile == x && yTile + a == y)
                    {
                        return true;
                    }
                }
            }

            // Forwards
            for (int a = 1; a < 8; a++)
            {
                if (yTile - a < 0)
                {
                    break;
                }
                if ((tileSet.tiles[xTile][yTile - a].getAssociatedPlayerID() != playerID) && tileSet.tiles[xTile][yTile - a].getAssociatedStatus())
                {
                    if (xTile == x && yTile - a == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile][yTile - a].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile == x && yTile - a == y)
                    {
                        return true;
                    }
                }
            }

            // Left
            for (int a = 1; a < 8; a++)
            {
                if (xTile - a < 0)
                {
                    break;
                }

                if ((tileSet.tiles[xTile - a][yTile].getAssociatedPlayerID() != playerID) && tileSet.tiles[xTile - a][yTile].getAssociatedStatus())
                {
                    if (xTile - a == x && yTile == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile - a][yTile].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile - a == x && yTile == y)
                    {
                        return true;
                    }
                }
            }

            // Right
            for (int a = 1; a < 8; a++)
            {
                if (xTile + a >= 8)
                {
                    break;
                }
                if ((tileSet.tiles[xTile + a][yTile].getAssociatedPlayerID() != playerID) && tileSet.tiles[xTile + a][yTile].getAssociatedStatus())
                {
                    if (xTile + a == x && yTile == y)
                    {
                        return true;
                    }
                    break;
                }
                else if (tileSet.tiles[xTile + a][yTile].getAssociatedStatus())
                {
                    break;
                }
                else
                {
                    if (xTile + a == x && yTile == y)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
