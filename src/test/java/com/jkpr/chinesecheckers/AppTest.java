package com.example.chinesecheckers;

import com.jkpr.chinesecheckers.server.CCBuilder;
import com.jkpr.chinesecheckers.server.Director;
import com.jkpr.chinesecheckers.server.Game;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Game game= Director.createGame(new CCBuilder(),2);
        assertNotNull(game);
    }
}
