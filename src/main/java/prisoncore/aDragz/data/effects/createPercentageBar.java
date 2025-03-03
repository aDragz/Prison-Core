package prisoncore.aDragz.data.effects;

import org.bukkit.Bukkit;

public class createPercentageBar {
    public static String generateBar(Double percentage) {
        double decimal = toDecimal(percentage);

        /*
        Returns Decimal, because you need the number to be a decimal point for the percentage.
        Like 10.5 (Out of 11) -> .5, so you need .5 More (Half Way)
         */

        return decimalToString(decimal);
    }

    private static double toDecimal(double number) {
        if (number % 1 != 0) { // Check if the number is a decimal
            int integerPart = (int) number;
            return number - integerPart; // Subtract the integer part from the number
        }

        return number; // If the number is not a decimal, return it as is
    }

    private static String decimalToString(double percentage) {
        StringBuilder bar = new StringBuilder();

        String numberStr = Double.toString(percentage);
        char firstChar = numberStr.charAt(2); //Grabs first decimal number, so it can get the decimal.


        for (int i = 0; i < 10; i++) {
            if (i < Integer.parseInt(String.valueOf(firstChar))) {
                bar.append("§a▇"); // This will fill the bar
            } else {
                bar.append("§c▇"); // This will be the empty part of the bar
            }
        }
        return bar.toString();
    }

}
