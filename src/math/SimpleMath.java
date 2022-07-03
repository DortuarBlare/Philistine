package math;

public class SimpleMath {
    public static int getCountsOfDigits(long number) {
        return (number == 0) ? 1 : (int) Math.ceil(Math.log10(Math.abs(number) + 0.5));
    }
}
