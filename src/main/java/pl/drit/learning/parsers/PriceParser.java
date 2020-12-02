package pl.drit.learning.parsers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PriceParser {
    public static double parse(final String amount) throws ParseException {
        return parse(amount, Locale.forLanguageTag("pl-PL"));
    }
    private static double parse(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return format.parse(amount.replaceAll("[^\\d.,]","")).doubleValue();
    }
}
