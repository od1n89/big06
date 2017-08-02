package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;
import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Admin on 09.10.2016.
 */
class WithdrawCommand implements Command {
    ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw", Locale.ENGLISH);
    @Override
    public void execute() throws InterruptOperationException{
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        ConsoleHelper.writeMessage(res.getString("before"));

        int amount = 0;
        while (true){
            try{
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                String line = ConsoleHelper.readString();
                amount = Integer.parseInt(line);
                if (amount > 0 && currencyManipulator.isAmountAvailable(amount)){
                    currencyManipulator.withdrawAmount(amount);
                    System.out.printf(res.getString("success.format"), amount, currencyCode);
                    System.out.println();
                    break;
                }
                else if (amount > 0 && !currencyManipulator.isAmountAvailable(amount)) {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                }
                    else throw new IllegalArgumentException();
            }
            catch (NotEnoughMoneyException e1){
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
            catch (IllegalArgumentException e2){
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            }
        }

    }
}
