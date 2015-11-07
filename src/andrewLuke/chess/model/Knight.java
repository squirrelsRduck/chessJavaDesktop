package andrewLuke.chess.model;

import andrewLuke.chess.graphics.Sprite;
import andrewLuke.chess.graphics.SpriteSheet;

/**
 * The knight and related functionality
 * @author Andrew
 */
public class Knight extends Militant
{

    /**
     * Creates a Knight object
     * @param playerID
     * @param xTile
     * @param yTile
     * @param militaryColor
     * @param militaryColor2
     * @param index
     */
    public Knight(int playerID, int xTile, int yTile, int militaryColor, int militaryColor2, int index)
    {
        this.index = index;
        this.playerID = playerID;
        this.xTile = xTile;
        this.yTile = yTile;
        militantSprite = new Sprite(100, 75, 2, 0, SpriteSheet.spriteSheet);
        this.militaryColor = militaryColor;
        this.militaryColor2 = militaryColor2;
    }

    @Override
    public void calculateAvailableMoves()
    {
        validXmoves.clear();
        validYmoves.clear();
        if ((xTile + 2 < 8) && (yTile - 1 >= 0))
        {
            if (tileSet.tiles[xTile + 2][yTile - 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile + 2][yTile - 1].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile + 2);
                    validYmoves.add(yTile - 1);
                }
            }
            else
            {
                validXmoves.add(xTile + 2);
                validYmoves.add(yTile - 1);
            }
        }
        if (xTile + 2 < 8 && (yTile + 1) < 8)
        {
            if (tileSet.tiles[xTile + 2][yTile + 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile + 2][yTile + 1].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile + 2);
                    validYmoves.add(yTile + 1); 
                }
            }
            else
            {
                validXmoves.add(xTile + 2);
                validYmoves.add(yTile + 1);
            }
        }
        if ((xTile + 1 < 8) && (yTile - 2) >= 0)
        {
            if (tileSet.tiles[xTile + 1][yTile - 2].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile + 1][yTile - 2].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile + 1);
                    validYmoves.add(yTile - 2);
                }
            }
            else
            {
                validXmoves.add(xTile + 1);
                validYmoves.add(yTile - 2); 
            }
        }
        if ((xTile + 1 < 8) && (yTile + 2) < 8)
        {
            if (tileSet.tiles[xTile + 1][yTile + 2].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile + 1][yTile + 2].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile + 1);
                    validYmoves.add(yTile + 2); 
                }
            }
            else
            {
                validXmoves.add(xTile + 1);
                validYmoves.add(yTile + 2);
            }
        }

        if ((xTile - 2 >= 0) && (yTile - 1 >= 0))
        {
            if (tileSet.tiles[xTile - 2][yTile - 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile - 2][yTile - 1].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile - 2);
                    validYmoves.add(yTile - 1);
                }
            }
            else
            {
                validXmoves.add(xTile - 2);
                validYmoves.add(yTile - 1);
            }
        }
        if (xTile - 2 >= 0 && (yTile + 1) < 8)
        {
            if (tileSet.tiles[xTile - 2][yTile + 1].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile - 2][yTile + 1].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile - 2);
                    validYmoves.add(yTile + 1);
                }
            }
            else
            {
                validXmoves.add(xTile - 2);
                validYmoves.add(yTile + 1);
            }
        }
        if ((xTile - 1 >= 0) && (yTile - 2) >= 0)
        {
            if (tileSet.tiles[xTile - 1][yTile - 2].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile - 1][yTile - 2].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile - 1);
                    validYmoves.add(yTile - 2);
                }
            }
            else
            {
                validXmoves.add(xTile - 1);
                validYmoves.add(yTile - 2);
            }
        }
        if ((xTile - 1 >= 0) && (yTile + 2) < 8)
        {
            if (tileSet.tiles[xTile - 1][yTile + 2].getAssociatedStatus())
            {
                if (tileSet.tiles[xTile - 1][yTile + 2].getAssociatedPlayerID() != playerID)
                {
                    validXmoves.add(xTile - 1);
                    validYmoves.add(yTile + 2);
                }
            }
            else
            {
                validXmoves.add(xTile - 1);
                validYmoves.add(yTile + 2);
            }
        }
    }
    
    @Override
    public boolean isCaptureReachable(int x, int y)
    {
        if ((xTile + 2 < 8) && (yTile - 1 >= 0))
        {
            if (tileSet.tiles[xTile + 2][yTile - 1].getAssociatedStatus())
            {
                if (xTile + 2 == x && yTile - 1 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile + 2 == x && yTile - 1 == y)
                {
                    return true;
                }
            }
        }
        if (xTile + 2 < 8 && (yTile + 1) < 8)
        {
            if (tileSet.tiles[xTile + 2][yTile + 1].getAssociatedStatus())
            {
                if (xTile + 2 == x && yTile + 1 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile + 2 == x && yTile + 1 == y)
                {
                    return true;
                }
            }
        }
        if ((xTile + 1 < 8) && (yTile - 2) >= 0)
        {
            if (tileSet.tiles[xTile + 1][yTile - 2].getAssociatedStatus())
            {
                if (xTile + 1 == x && yTile - 2 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile + 1 == x && yTile - 2 == y)
                {
                    return true;
                }
            }
        }
        if ((xTile + 1 < 8) && (yTile + 2) < 8)
        {
            if (tileSet.tiles[xTile + 1][yTile + 2].getAssociatedStatus())
            {
                if (xTile + 1 == x && yTile + 2 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile + 1 == x && yTile + 2 == y)
                {
                    return true;
                }
            }
        }

        if ((xTile - 2 >= 0) && (yTile - 1 >= 0))
        {
            if (tileSet.tiles[xTile - 2][yTile - 1].getAssociatedStatus())
            {
                if (xTile - 2 == x && yTile - 1 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile - 2 == x && yTile - 1 == y)
                {
                    return true;
                }
            }
        }
        if (xTile - 2 >= 0 && (yTile + 1) < 8)
        {
            if (tileSet.tiles[xTile - 2][yTile + 1].getAssociatedStatus())
            {
                if (xTile - 2 == x && yTile + 1 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile - 2 == x && yTile + 1 == y)
                {
                    return true;
                }
            }
        }
        if ((xTile - 1 >= 0) && (yTile - 2) >= 0)
        {
            if (tileSet.tiles[xTile - 1][yTile - 2].getAssociatedStatus())
            {
                if (xTile - 1 == x && yTile - 2 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile - 1 == x && yTile - 2 == y)
                {
                    return true;
                }
            }
        }
        if ((xTile - 1 >= 0) && (yTile + 2) < 8)
        {
            if (tileSet.tiles[xTile - 1][yTile + 2].getAssociatedStatus())
            {
                if (xTile - 1 == x && yTile + 2 == y)
                {
                    return true;
                }
            }
            else
            {
                if (xTile - 1 == x && yTile + 2 == y)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
