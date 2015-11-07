package andrewLuke.chess.model;

import andrewLuke.chess.graphics.Sprite;
import andrewLuke.chess.graphics.SpriteSheet;

/**
 * The Pawn and related functionality
 *
 * @author Andrew
 */
public class Pawn extends Militant
{

    /**
     * Creates a Pawn instance
     *
     * @param playerID
     * @param xTile
     * @param yTile
     * @param militaryColor
     * @param militaryColor2
     * @param index
     */
    public Pawn(int playerID, int xTile, int yTile, int militaryColor, int militaryColor2, int index)
    {
        this.index = index;
        this.playerID = playerID;
        this.xTile = xTile;
        this.yTile = yTile;
        militantSprite = new Sprite(100, 75, 0, 0, SpriteSheet.spriteSheet);
        this.militaryColor = militaryColor;
        this.militaryColor2 = militaryColor2;
        isPawn = true;
    }

    @Override
    public void calculateAvailableMoves()
    {
        validXmoves.clear();
        validYmoves.clear();
        int direction;
        if (playerID == 1)
        {
            direction = 1;
        }
        else
        {
            direction = -1;
        }
        // First need to calculate available moves
        // For an unmoved pawn, available moves include 2 tiles forward
        if ((direction == 1 && yTile == 6) || (direction == -1 && yTile == 1))
        {
            if (!tileSet.tiles[xTile][yTile - 2 * direction].getAssociatedStatus() && !tileSet.tiles[xTile][yTile - 1 * direction].getAssociatedStatus())
            {
                validXmoves.add(xTile);
                validYmoves.add(yTile - 2 * direction);
            }
        }
        // Or, a capture via 1 diagonal unit left
        if (xTile - 1 * direction >= 0 && xTile - 1 * direction <= 7)
        {
            if (tileSet.tiles[xTile - 1 * direction][yTile - 1 * direction].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile - 1 * direction][yTile - 1 * direction].getAssociatedStatus())
            {
                validXmoves.add(xTile - 1 * direction);
                validYmoves.add(yTile - 1 * direction);
            }
        }
        // Or, a capture via 1 diagonal unit right
        if (xTile + 1 * direction < 8 && yTile - 1 * direction >= 0 && xTile + 1 * direction >= 0 && yTile - 1 * direction <= 7)
        {
            if (tileSet.tiles[xTile + 1 * direction][yTile - 1 * direction].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile + 1 * direction][yTile - 1 * direction].getAssociatedStatus())
            {
                validXmoves.add(xTile + 1 * direction);
                validYmoves.add(yTile - 1 * direction);
            }
        }
        // Regular move forward
        if (!tileSet.tiles[xTile][yTile - 1 * direction].getAssociatedStatus())
        {
            validXmoves.add(xTile);
            validYmoves.add(yTile - 1 * direction);
        }
    }

    @Override
    public boolean isCaptureReachable(int x, int y)
    {
        int direction;
        if (playerID == 1)
        {
            direction = 1;
        }
        else
        {
            direction = -1;
        }
        // First need to calculate available moves

        // capture via 1 diagonal unit left
        if ((xTile - 1 * direction >= 0) && (xTile - 1 * direction <= 7) && (yTile - 1 * direction <= 7) && (yTile - 1 * direction >= 0))
        {
            if ((xTile - 1 * direction == x) && (yTile - 1 * direction == y))
            {
                if (tileSet.tiles[xTile - 1 * direction][yTile - 1 * direction].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile - 1 * direction][yTile - 1 * direction].getAssociatedStatus())
                {
                    return true;
                }
                else if (!tileSet.tiles[xTile - 1 * direction][yTile - 1 * direction].getAssociatedStatus())
                {
                    return true;
                }
            }
        }

        // Or, a capture via 1 diagonal unit right
        if (xTile - 1 * direction >= 0 && yTile - 1 * direction >= 0 && xTile + 1 * direction <= 7 && yTile + 1 * direction <= 7)
        {
            if ((xTile + 1 * direction == x) && (yTile - 1 * direction == y))
            {
                if (tileSet.tiles[xTile + 1 * direction][yTile - 1 * direction].getAssociatedPlayerID() != playerID && tileSet.tiles[xTile + 1 * direction][yTile - 1 * direction].getAssociatedStatus())
                {
                    return true;
                }
                else if (!tileSet.tiles[xTile + 1 * direction][yTile - 1 * direction].getAssociatedStatus())
                {
                    return true;
                }
            }
        }
        return false;
    }
}