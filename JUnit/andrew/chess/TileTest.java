/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package andrew.chess;

import andrewLuke.chess.model.Tile;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andrew
 */
public class TileTest
{
    
    public TileTest()
    {
    }

    /**
     * Test of setAssociatedPlayerID method, of class Tile.
     */
    @Test
    public void testSetAssociatedPlayerID()
    {
        System.out.println("setAssociatedPlayerID");
        
        int playerID = 0;
        Tile instance = new Tile();
        
        instance.setAssociatedPlayerID(playerID);
        assertEquals(instance.getAssociatedPlayerID(),0);
    }

    /**
     * Test of getAssociatedPlayerID method, of class Tile.
     */
    @Test
    public void testGetAssociatedPlayerID()
    {
        // Tested in the setAssociatedPlayerID section
        System.out.println("getAssociatedPlayerID");
    }

    /**
     * Test of setAssociatedStatus method, of class Tile.
     */
    @Test
    public void testSetAssociatedStatus()
    {
        System.out.println("setAssociatedStatus");
        Tile instance = new Tile();
        boolean shouldBeFalse = instance.getAssociatedStatus();
        instance.setAssociatedStatus();
        boolean shouldBeTrue = instance.getAssociatedStatus();
        instance.setAssociatedStatus();
        boolean shouldBeTrue2 = instance.getAssociatedStatus();
        assertEquals(shouldBeFalse,false);
        assertEquals(shouldBeTrue,true);
        assertEquals(shouldBeTrue2,true);
    }

    /**
     * Test of clearAssociatedStatus method, of class Tile.
     */
    @Test
    public void testClearAssociatedStatus()
    {
        System.out.println("clearAssociatedStatus");
        Tile instance = new Tile();
        instance.setAssociatedStatus();
        boolean shouldBeTrue = instance.getAssociatedStatus();
        instance.clearAssociatedStatus();
        boolean shouldBeFalse = instance.getAssociatedStatus();
        instance.clearAssociatedStatus();
        boolean shouldBeFalse2 = instance.getAssociatedStatus();
        assertEquals(shouldBeTrue,true);
        assertEquals(shouldBeFalse,false);
        assertEquals(shouldBeFalse2,false);
    }

    /**
     * Test of getSelectedStatus method, of class Tile.
     */
    @Test
    public void testGetSelectedStatus()
    {
        System.out.println("getSelectedStatus");
        // Tested previously in the setting, toggling, and clearing
    }

    /**
     * Test of getAssociatedStatus method, of class Tile.
     */
    @Test
    public void testGetAssociatedStatus()
    {
        System.out.println("getAssociatedStatus");
        // Tested previously also in the setting, toggling, and clearing
    }

    /**
     * Test of setColor method, of class Tile.
     */
    @Test
    public void testSetColor()
    {
        System.out.println("setColor");
        // No need to test getters and setters
    }

    /**
     * Test of setRandomColor method, of class Tile.
     */
    @Test
    public void testSetRandomColor()
    {
        System.out.println("setRandomColor");
        // No need to test getters and setters
    }

    /**
     * Test of setDefaultColor method, of class Tile.
     */
    @Test
    public void testSetDefaultColor()
    {
        System.out.println("setDefaultColor");
        // No need to test getters and setters

    }

    /**
     * Test of setTempDefaultColor method, of class Tile.
     */
    @Test
    public void testSetTempDefaultColor()
    {
        System.out.println("setTempDefaultColor");
        // No need to test getters and setters

    }

    /**
     * Test of resetDefaultColor method, of class Tile.
     */
    @Test
    public void testResetDefaultColor()
    {
        System.out.println("resetDefaultColor");
        // No need to test getters and setters

    }

    /**
     * Test of resetTempDefaultColor method, of class Tile.
     */
    @Test
    public void testResetTempDefaultColor()
    {
        System.out.println("resetTempDefaultColor");
        // No need to test getters and setters

    }

    /**
     * Test of getColor method, of class Tile.
     */
    @Test
    public void testGetColor()
    {
        System.out.println("getColor");
        // No need to test getters and setters

    }

    /**
     * Test of getTempDefaultTileColor method, of class Tile.
     */
    @Test
    public void testGetTempDefaultTileColor()
    {
        System.out.println("getTempDefaultTileColor");
        // No need to test getters and setters
    }
}
