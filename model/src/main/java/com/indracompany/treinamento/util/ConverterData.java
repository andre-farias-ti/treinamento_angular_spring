package com.indracompany.treinamento.util;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverterData {

    public static Date convertStringDate(String dataString) throws Exception{

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format.parse(dataString);
            return date;
        }catch (Exception e){
            throw new AplicacaoException(ExceptionValidacoes.ERRO_DATA_INVALIDA);
        }
    }

    public static Date convertStringDateInicial(String dataString) throws Exception{

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy mm:ss");
            Date date = format.parse(dataString + " 00:01");
            return date;
        }catch (Exception e){
            throw new AplicacaoException(ExceptionValidacoes.ERRO_DATA_INVALIDA);
        }
    }

    public static Date convertStringDateFinal(String dataString) throws Exception{
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy mm:ss");
            Date date = format.parse(dataString + " 23:59");
            return date;
        }catch (Exception e){
            throw new AplicacaoException(ExceptionValidacoes.ERRO_DATA_INVALIDA);
        }
    }

    public static String converDadaString(Date data) {

        String dateString = null;
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dateString = format.format(data);
        }catch (Exception e){
            throw new AplicacaoException(ExceptionValidacoes.ERRO_DATA_INVALIDA);
        }
        return dateString;
    }

}
