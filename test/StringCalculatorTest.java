
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class StringCalculatorTest {
    
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
     
    @Test
    public void testAdd() throws Exception {        
        assertEquals(8, new StringCalculator().add("1,2,5"));
    }    
    
    @Test
    public void testAddWithSpaces() throws Exception {        
        assertEquals(8, new StringCalculator().add("1 ,2  ,5"));
    }
    
    @Test
    public void testAddNothing() throws Exception {        
        assertEquals(0, new StringCalculator().add(""));
    }   
    
    @Test
    public void testAddWithNewline() throws Exception {        
        assertEquals(6, new StringCalculator().add("1\n,2,3"));
    } 
    
    @Test
    public void testAddWithNewline2() throws Exception {        
        assertEquals(7, new StringCalculator().add("1,\n2,4"));
    } 
    
    @Test
    public void testCustomDelimiter() throws Exception {        
        assertEquals(8, new StringCalculator().add("//;\n1;3;4"));
    }
    
    @Test
    public void testCustomDelimiter2() throws Exception {        
        assertEquals(6, new StringCalculator().add("//$\n1$2$3"));
    }
    
    @Test
    public void testCustomDelimiter3() throws Exception {        
        assertEquals(13, new StringCalculator().add("//@\n2@3@8"));
    }
        
    @Test
    public void testNegative() throws Exception {
        exceptionRule.expect(Exception.class);
        new StringCalculator().add("1,-5");
    }
    
    @Test
    public void testAbove1000() throws Exception {     
        assertEquals(2, new StringCalculator().add("2,1001"));
    }
    
    @Test
    public void testLongDelimiter() throws Exception {     
        assertEquals(6, new StringCalculator().add("//***\n1***2***3"));
    }
    
    @Test
    public void testMultipleDelimiters() throws Exception {     
        assertEquals(6, new StringCalculator().add("//$,@\n1$2@3"));
    }
    
    @Test
    public void testMultipleLongDelimiters() throws Exception {     
        assertEquals(15, new StringCalculator().add("//$,@@,***\n1$2@@3***4***5"));
    }
}
