package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Admin on 09.10.2016.
 */
class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info", Locale.ENGLISH);
    @Override
    public void execute() {
        boolean hasMoney = false;
        ConsoleHelper.writeMessage(res.getString("before"));

        for (CurrencyManipulator i : CurrencyManipulatorFactory.getAllCurrencyManipulators()){
            String currencyCode = i.getCurrencyCode();
            CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
            if (currencyManipulator.hasMoney()){
                ConsoleHelper.writeMessage(currencyCode.toUpperCase() + " - " + String.valueOf(currencyManipulator.getTotalAmount()));
                hasMoney = true;
            }
        }
        if (!hasMoney){
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
