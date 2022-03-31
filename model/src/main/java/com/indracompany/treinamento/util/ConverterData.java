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

    public static Date convertStringDateInicial(String dataString) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy mm:ss");
        Date date = format.parse(dataString + " 00:01");
        return date;
    }

    public static Date convertStringDateFinal(String dataString) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy mm:ss");
        Date date = format.parse(dataString + " 23:59");
        return date;
    }

    public static String converDadaString(Date data) {

        String dateString = null;
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dateString = format.format(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dateString;
    }

}
