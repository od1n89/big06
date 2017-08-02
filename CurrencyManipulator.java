package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;
import java.util.*;

/**
 * Created by Admin on 09.10.2016.
 */
public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count){
        if (denominations.containsKey(denomination)) denominations.put(denomination, denominations.get(denomination) + count);
        else denominations.put(denomination, count);
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> result = new HashMap<>();
        Map<Integer, Integer> denominationsCopy = new HashMap<>();
        denominationsCopy.putAll(denominations);

        ArrayList<Integer> denominationsList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> i : denominationsCopy.entrySet()
             ) {
            for (int j = 0; j < i.getValue(); j++) denominationsList.add(i.getKey());
        }
        Collections.sort(denominationsList, Collections.<Integer>reverseOrder());

        for (int i : denominationsList) {
            if (expectedAmount == 0) break;
            if (i <= expectedAmount){
                expectedAmount -=i;
                if (denominationsCopy.get(i) == 1) denominationsCopy.remove(i);
                else denominationsCopy.put(i, denominationsCopy.get(i) - 1);

                if(result.containsKey(i)) result.put(i, result.get(i) + 1);
                else result.put(i, 1);
            }
        }
        if (expectedAmount != 0) throw new NotEnoughMoneyException();
        denominations = denominationsCopy;
        return result;
    }

    public int getTotalAmount(){
        int result = 0;
        for (Map.Entry<Integer, Integer> i : denominations.entrySet()
             ) {
            result += i.getKey() * i.getValue();
        }
        return result;
    }

    public Boolean hasMoney(){
        return getTotalAmount() > 0;
    }

    public boolean isAmountAvailable(int expectedAmount){
        return getTotalAmount() >= expectedAmount;
    }
}
