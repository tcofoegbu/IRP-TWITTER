package uk.ac.dotrural.twitterstream;

import org.testng.annotations.*;


/**
 * Unit test for simple App.
 */
public class AppTest {
    // /**
    // * Create the test case
    // *
    // * @param testName name of the test case
    // */
    // public AppTest( String testName )
    // {
    // super( testName );
    // }
    //
    // /**
    // * @return the suite of tests being tested
    // */
    // public static Test suite()
    // {
    // return new TestSuite( AppTest.class );
    // }
    //
    // /**
    // * Rigourous Test :-)
    // */

//    public void testApp() {
//	assertTrue(true);
//    }

    @BeforeClass
    public void setUp() {
	// code that will be invoked when this test is instantiated
    }

    @Test(groups = { "fast" })
    public void aFastTest() {
	System.out.println("Fast test");
    }

    @Test(groups = { "slow" })
    public void aSlowTest() {
	System.out.println("Slow test");
    }

}
