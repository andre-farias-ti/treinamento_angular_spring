package com.indracompany.treinamento.util;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverterData {

    public static void main(String[] args) throws Exception {

        System.out.println(convertStringDate("23/10/1988"));

    }

    public static Date convertStringDate(String dataString) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(dataString);
        return date;
    }

    public static String converDadaString(Date data) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(data);
        return dateString;
    }

}
