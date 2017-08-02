package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by Admin on 07.10.2016.
 */
public class ConsoleHelper {
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common", Locale.ENGLISH);
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException{
        String result = "";
        try{
            result = bufferedReader.readLine();
        }
        catch (IOException ignored){}
        if (result.equalsIgnoreCase("EXIT")) throw new InterruptOperationException();
        return result;
    }

    public static void printExitMessage(){
        writeMessage(res.getString("the.end"));
    }

    public static String askCurrencyCode() throws InterruptOperationException{
        String code;
        while (true){
                writeMessage(res.getString("choose.currency.code"));
                code = readString();
                Pattern pattern = Pattern.compile("[^A-Za-z]");

                if (code.length() == 3 && !pattern.matcher(code).find()) break;
                else writeMessage(res.getString("invalid.data"));
        }

        return code.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException{
        String nominal = "";
        String quantity = "";

        while (true){
                System.out.printf(res.getString("choose.denomination.and.count.format"), currencyCode);
                System.out.println();
                String line = readString();
                String[] splitString = line.split(" ");
                if (splitString.length != 2){
                    writeMessage(res.getString("invalid.data"));
                    continue;
                }

                nominal = splitString[0];
                quantity = splitString[1];

                Pattern pattern = Pattern.compile("[^0-9]");
                if (!pattern.matcher(nominal).find() && !pattern.matcher(quantity).find()) break;
                else writeMessage(res.getString("invalid.data"));
        }

        return new String[]{nominal, quantity};
    }

    public static Operation askOperation() throws InterruptOperationException{
        Operation result = null;
        try{
            writeMessage(res.getString("choose.operation"));
            writeMessage("1 " + res.getString("operation.INFO"));
            writeMessage("2 " + res.getString("operation.DEPOSIT"));
            writeMessage("3 " + res.getString("operation.WITHDRAW"));
            writeMessage("4 " + res.getString("operation.EXIT"));

            int input = Integer.parseInt(readString());
            result = Operation.getAllowableOperationByOrdinal(input);
        }
        catch (IllegalArgumentException anyException){
            writeMessage(res.getString("invalid.data"));
            result = askOperation();
        }
        return result;
    }
}
