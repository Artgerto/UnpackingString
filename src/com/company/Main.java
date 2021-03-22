package com.company;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        String input = s.next();

        //Проверка входной строки
        int countOpen = 0;
        int countClose = 0;

        boolean lastDigit = false;

        for(char c : input.toCharArray()){
            if(Character.isDigit(c)){
                lastDigit = true;
            } else if(c == ']'){
                if(lastDigit){
                    throw new Exception("После цифры должна быть открывающая скобка");
                }
                lastDigit = false;
                countClose++;
                if(countOpen < countClose){
                    throw new Exception("Несоответсвие скобок");
                }
            } else if(c == '[') {
                if(!lastDigit){
                    throw new Exception("Открывающая скобка должна идти за цифрой");
                }
                lastDigit = false;
                countOpen++;
            } else if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')){
                if(lastDigit){
                    throw new Exception("После цифры должна быть открывающая скобка");
                }
                lastDigit = false;
            } else {
                throw new Exception("Строка содержит недопустимые символы");
            }
        }

        if(countClose!=countOpen){
            throw new Exception("Несоответсвие скобок");
        }

        //Код распаковки строки
        int start = input.lastIndexOf("[");

        int end = input.substring(start).indexOf("]")+start;

        while(start!=-1 && end != -1){
            String repeat = input.substring(start+1, end);
            Pattern p = Pattern.compile("\\d+$");

            String beforeWithNumber  = input.substring(0, start);

            Matcher m = p.matcher(beforeWithNumber);
            if (m.find()) {
                int positionStart = m.start();
                int count = Integer.parseInt(m.group(0));

                String repeated = repeat.repeat(count);

                input = beforeWithNumber.substring(0,positionStart)+repeated+input.substring(end+1, input.length());

                start = input.lastIndexOf("[");

                end = input.indexOf("]");
            }
        }

        System.out.println(input);
    }
}
