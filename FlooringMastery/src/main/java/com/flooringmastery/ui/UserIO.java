package com.flooringmastery.ui;

import java.math.BigDecimal;

public interface UserIO {

    void print(String msg);

    String readString(String prompt);

    Integer readInt(String prompt);

    Integer readInt(String prompt, Integer min, Integer max);

    BigDecimal readBigDecimal(String prompt);

    BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);

}
