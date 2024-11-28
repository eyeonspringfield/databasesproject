package org.personal.markcs.Control;

public class NumberFormat {

    public static String formatWithSpaces(String number) {
        String cleanNumber = number.replaceAll("\\D", "");
        return cleanNumber.replaceAll("(\\d)(?=(\\d{3})+$)", "$1 ");
    }

}
