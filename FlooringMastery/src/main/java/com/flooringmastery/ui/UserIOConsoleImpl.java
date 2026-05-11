package com.flooringmastery.ui;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO{
    Scanner input = new Scanner(System.in);
    String stringInput;

    @Override
    public void print(String message){
        System.out.println(message);
    }


    public String readString(String prompt){
        System.out.println(prompt);
        stringInput = input.nextLine().trim();

        return stringInput;
    }

    @Override
    public Integer readInt(String prompt) {
        System.out.println(prompt);
        stringInput = input.nextLine().trim();
        return Integer.parseInt(stringInput);
    }

    @Override
    public Integer readInt(String prompt, Integer min, Integer max) {
        Integer bigDecimalInput = min - 1;

        // Keep prompting the user until the input is valid (within the min-max range)
        while(bigDecimalInput<min || bigDecimalInput>max){
            System.out.println(prompt);
            stringInput = input.nextLine().trim();
            bigDecimalInput = Integer.parseInt(stringInput);
        }

        return bigDecimalInput;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        System.out.println(prompt);
        stringInput = input.nextLine().trim();

        return new BigDecimal(stringInput);
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        // Initialize the Big Decimal to be out of bounds
        BigDecimal bigDecimalInput = min.subtract(new BigDecimal("1"));

        // Keep prompting the user until the input is valid (within the min-max range)
        while(bigDecimalInput.compareTo(min) < 0){
            System.out.println(prompt);
            stringInput = input.nextLine().trim();
            bigDecimalInput = new BigDecimal(stringInput);
        }

        return bigDecimalInput;
    }

}
