package pro1;
import org.junit.jupiter.api.Assertions;

class GcdTest1
{
    @org.junit.jupiter.api.Test
    void test()
    {
        Assertions.assertEquals(
                10,
                NumericUtils.gcd(20,50)
        );
        Assertions.assertEquals(
                1,
                NumericUtils.gcd(21,25)
        );
        Assertions.assertEquals(
                1,
                NumericUtils.gcd(1,1)
        );
        Assertions.assertEquals(
                22_000_000_000L,
                NumericUtils.gcd(66_000_000_000L,44_000_000_000L)
        );
        Assertions.assertEquals(
                111,
                NumericUtils.gcd(555,666)
        );
    }
}