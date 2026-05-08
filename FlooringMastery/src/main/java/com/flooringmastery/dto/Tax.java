package com.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return stateAbbreviation + "\t" + stateName + "\t" + taxRate.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Tax tax = (Tax) obj;
        return Objects.equals(stateAbbreviation, tax.stateAbbreviation) &&
                Objects.equals(stateName, tax.stateName) &&
                Objects.equals(taxRate, tax.taxRate);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
