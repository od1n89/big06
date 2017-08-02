package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by Admin on 10.10.2016.
 */
public class LoginCommand implements Command {
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login", Locale.ENGLISH);
    @Override
    public void execute() throws InterruptOperationException {
        String inputCcNumber;
        String inputPin;
        Pattern pattern = Pattern.compile("[^0-9]");
        ConsoleHelper.writeMessage(res.getString("before"));

        while (true){
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            inputCcNumber = ConsoleHelper.readString();
            inputPin = ConsoleHelper.readString();

            if (!pattern.matcher(inputCcNumber).find() && !pattern.matcher(inputPin).find() && inputCcNumber.length() == 12 && inputPin.length() == 4){
                if (validCreditCards.containsKey(inputCcNumber) && inputPin.equals(validCreditCards.getObject(inputCcNumber))){
                    System.out.printf(res.getString("success.format"), inputCcNumber);
                    System.out.println();
                    break;
                } else {
                    System.out.printf(res.getString("not.verified.format"), inputCcNumber);
                    System.out.println();
                }
            } else ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
        }
    }
}
