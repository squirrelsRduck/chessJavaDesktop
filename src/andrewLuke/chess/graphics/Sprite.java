package andrewLuke.chess.graphics;

/**
 * Used to extract and contain pixel data from a SpriteSheet object
 * 
 * @author Andrew
 */
public class Sprite
{
    private final int xPixelOnSS,yPixelOnSS,sWidth,sHeight;
    public int[] pixelArray;
    
    /**
     * Creates and contains a sprite object
     * 
     * @precondition the SpriteSheet is valid with the specified dimensions
     * @postcondition the Sprite object's pixelArray contains the appropriate
     * color data
     * @param sWidth Sprite Width
     * @param sHeight Sprite Height
     * @param xIndexOnSS Sprite X-Index on SpriteSheet
     * @param yIndexOnSS Sprite Y-Index on SpriteSheet
     * @param spriteSheet The SpriteSheet to be loaded from
     */
    public Sprite(int sWidth,int sHeight,int xIndexOnSS,int yIndexOnSS, SpriteSheet spriteSheet)
    {
        this.sWidth = sWidth;
        this.sHeight = sHeight;        
        xPixelOnSS = xIndexOnSS*sWidth;
        yPixelOnSS = yIndexOnSS*sHeight;
        pixelArray = new int[sWidth*sHeight];
        load(spriteSheet); // This over-writes the portion in the pixelArray that we want to assign to the particular sprite
    }
    
    private void load(SpriteSheet spriteSheet)
    {
        for(int y=0;y<sHeight;y++)
        {
            for(int x= 0;x<sWidth;x++)
            {
                pixelArray[y*sWidth + x]= SpriteSheet.spriteSheet.ssPixelArray[(y+yPixelOnSS)*spriteSheet.ssWidth + (x+xPixelOnSS)];
            }
        }
    }
}
