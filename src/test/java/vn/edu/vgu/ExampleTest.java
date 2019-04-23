package vn.edu.vgu;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ExampleTest {

    @Before
    public void before() {

    }

    @BeforeClass
    public static void beforeClass() {

    }

    @After
    public void after() {

    }

    @AfterClass
    public static void afterClass() {

    }

    @Test
    public void foo() {
        // assert statements
        assertEquals(0, 0 );
    }

    @Test
    public void bar() {
        assertEquals(1, 2);
    }
}