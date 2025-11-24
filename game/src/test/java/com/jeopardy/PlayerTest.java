package com.jeopardy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private ScoreDisplay observer;

    @BeforeEach
    void setUp() {
        player = new Player("P1");
        observer = new ScoreDisplay("D1");
        player.addObserver(observer);
    }

    @Test
    void testPlayerCreation() {
        assertEquals("P1", player.getName());
        assertEquals(0, player.getPoints());
    }

    @Test
    void testAddPoints() {
        player.updatePoints(200);
        assertEquals(200, player.getPoints());
    }

    @Test
    void testSubtractPoints() {
        player.updatePoints(300);
        player.updatePoints(-100);
        assertEquals(200, player.getPoints());
    }
    
    @Test
    void testPointsZero() {
        player.updatePoints(0);
        assertEquals(0, player.getPoints());
    }

    @Test
    void testObserverManagement(){
        ScoreDisplay newObserver = new ScoreDisplay("D2");
        player.addObserver(newObserver);

        player.updatePoints(150);

        assertDoesNotThrow(() -> player.updatePoints(50));
        
        player.removeObserver(newObserver);
        assertDoesNotThrow(() -> player.updatePoints(25));
    }

    @Test
    void testPlayerWithEmptyName() {
        Player emptyPlayer = new Player("");
        assertEquals("", emptyPlayer.getName());
        assertEquals(0, emptyPlayer.getPoints());
    }

    @Test
    void testMultipleObserversReceiveNotifications() {

        ScoreDisplay observer1 = new ScoreDisplay("Observer1");
        ScoreDisplay observer2 = new ScoreDisplay("Observer2");
        
        player.addObserver(observer1);
        player.addObserver(observer2);

        assertDoesNotThrow(() -> player.updatePoints(100));
        assertDoesNotThrow(() -> player.updatePoints(-25));
        
        player.removeObserver(observer2);
        assertDoesNotThrow(() -> player.updatePoints(50));
    }

    @Test
    void testRemoveNonExistentObserver() {
        ScoreDisplay nonAddedObserver = new ScoreDisplay("NotAdded");
        
        assertDoesNotThrow(() -> player.removeObserver(nonAddedObserver));
        
        player.updatePoints(100);
        assertEquals(100, player.getPoints());
    }
}
