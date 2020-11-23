package pl.drit.learning;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.util.stream.Stream;

class PriceParserTest {

    @ParameterizedTest
    @MethodSource("provideParameters")
    void parseTest(String amount, Double expectedPrice) throws ParseException {
        double actualPrice = PriceParser.parse(amount);
        Assertions.assertThat(actualPrice).isEqualTo(expectedPrice);
    }
    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("119,95 zł / szt.", 119.95),
                Arguments.of("75,00 zł / szt.", 75.00),
                Arguments.of("22,74 zł", 22.74),
                Arguments.of("16,96 zł", 16.96),
                Arguments.of("61,00 zł", 61.00)
        );
    }
}