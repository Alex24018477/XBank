package com.alex.xbank.json;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Rates {

    private Rate[] rates;

    public Rates() {
    }

    public Rates(Rate[] rates) {
        this.rates = rates;
    }

    public Rate[] getRates() {
        return rates;
    }

    public void setRates(Rate[] rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rates rates1 = (Rates) o;
        return Arrays.equals(rates, rates1.rates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rates);
    }

    @Override
    public String toString() {
        return "Rates{" +
                "rates=" + Arrays.toString(rates) +
                '}';
    }
}
