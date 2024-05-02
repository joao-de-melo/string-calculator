package com.esg;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {
    private final StringCalculator underTest = new StringCalculator();

    @Test
    void emptyStringShouldReturn0() {
        int result = underTest.add("");

        assertThat(result, is(0));
    }

    @Test
    void oneNumberShouldBeReturned() {
        int result = underTest.add("1");

        assertThat(result, is(1));
    }

    @Test
    void twoNumbersShouldBeSummed() {
        int result = underTest.add("1,2");

        assertThat(result, is(3));
    }

    @Test
    void addShouldHandleAnArbitraryNumberOfNumbers() {
        int result = underTest.add("1,2,3,4,5");

        assertThat(result, is(15));
    }

    @Test
    void addShouldHandleNewLinesAsSeparatorsToo() {
        int result = underTest.add("1\n2,3");

        assertThat(result, is(6));
    }

    @Test
    void customDelimiter() {
        int result = underTest.add("//;\n1;2");

        assertThat(result, is(3));
    }

    @Test
    void negativeNumbersNotAllowed() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.add("-1,2"));

        assertThat(exception.getMessage(), is("Negatives not allowed: -1"));
    }

    @Test
    void multipleNegativeNumbers() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.add("2,-4,3,-5"));

        assertThat(exception.getMessage(), is("Negatives not allowed: -4,-5"));
    }

    @Test
    void numbersGreaterThan1000ShouldBeIgnored() {
        int result = underTest.add("1001,2");

        assertThat(result, is(2));
    }

    @Test
    void number1000ShouldNotBeIgnored() {
        int result = underTest.add("1000,2");

        assertThat(result, is(1002));
    }

    @Test
    void delimitersCanBeOfAnyLength() {
        int result = underTest.add("//[***]\n1***2***3");

        assertThat(result, is(6));
    }

    @Test
    void multipleDelimiters() {
        int result = underTest.add("//[*][%]\n1*2%3");

        assertThat(result, is(6));
    }

    @Test
    void multipleDelimitersOfAnyLength() {
        int result = underTest.add("//[***][%%%]\n1***2%%%3");

        assertThat(result, is(6));
    }
}