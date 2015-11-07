package andrewLuke.chess.model;

import andrewLuke.chess.graphics.Sprite;
import andrewLuke.chess.graphics.SpriteSheet;

/**
 * The bishop and related functionality
 *
 * @author Andrew
 */
public class Bishop extends Militant
{

    /**
     * Creates a Bishop object
     *
     * @param playerID
     * @param xTile
     * @param yTile
     * @param militaryColor
     * @param militaryColor2
     * @param index
     */
    public Bishop(int playerID, int xTile, int yTile, int militaryColor, int militaryColor2, int index)
    {
        this.index = index;
        this.playerID = playerID;
        this.xTile = xTile;
        this.yTile = yTile;
        militantSprite = new Sprite(100, 75, 3, 0, SpriteSheet.spriteSheet);
        this.militaryColor = militaryColor;
        this.militaryColor2 = militaryColor2;
    }

    @Override
    public void calculateAvailableMoves()
    {
        validXmoves.clear();
        validYmoves.clear();
        // There are 4 possible directions for a bishop to move
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
    }

    @Override
    public boolean isCaptureReachable(int x, int y)
    {
        if (captured)
        {
            return false;
        }
        else
        {
            // There are 4 possible directions for a bishop to move
            // Forwards right
            for (int a = 1; a < 8; a++)
            {
                if (xTile + a > 7 || yTile - a < 0)
                {
                    break;
                }

                if (tileSet.tiles[xTile + a][yTile - a].getAssociatedStatus())
                {
                    if (xTile + a == x && yTile - a == y)
                    {
                        return true;
                    }
                    if (tileSet.tiles[xTile + a][yTile - a].getAssociatedPlayerID() != playerID)
                    {
                        if (!enemyOfThisPlayer.getMilitant(enemyOfThisPlayer.identifyMilitant(xTile + a, yTile - a)).isKing)
                        {
                            break;
                        }
                    }
                    else
                    {
                        break;
                    }
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
                if (tileSet.tiles[xTile - a][yTile - a].getAssociatedStatus())
                {

                    if (xTile - a == x && yTile - a == y)
                    {
                        return true;
                    }
                    if (tileSet.tiles[xTile - a][yTile - a].getAssociatedPlayerID() != playerID)
                    {
                        if (!enemyOfThisPlayer.getMilitant(enemyOfThisPlayer.identifyMilitant(xTile - a, yTile - a)).isKing)
                        {
                            break;
                        }
                    }
                    else
                    {
                        break;
                    }
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
                if (tileSet.tiles[xTile - a][yTile + a].getAssociatedStatus())
                {
                    if (xTile - a == x && yTile + a == y)
                    {
                        return true;
                    }
                    if (tileSet.tiles[xTile - a][yTile + a].getAssociatedPlayerID() != playerID)
                    {
                        if (!enemyOfThisPlayer.getMilitant(enemyOfThisPlayer.identifyMilitant(xTile - a, yTile + a)).isKing)
                        {
                            break;
                        }
                    }
                    else
                    {
                        break;
                    }
                }
                else
                {
                    if (xTile - a == x && yTile + a == y)
                    {
                        return true;
                    }
                }
            }

            // Backwards right
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
            return false;
        }
    }
}
