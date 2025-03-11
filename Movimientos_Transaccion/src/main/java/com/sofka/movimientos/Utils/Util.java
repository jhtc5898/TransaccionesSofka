package com.sofka.movimientos.Utils;

import com.sofka.movimientos.entities.Account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import static com.sofka.movimientos.Utils.Constants.DATE_FORMAT;

public class Util {

    public static Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
    }


    public static Date getStartOfDay(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(dateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }


    public static Date getEndOfDay(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(dateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public static BigDecimal controlValueZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static void validateInitialBalance(BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The movement value must be >= 0");
        }
    }

    public static byte[] compressData(String data) throws IOException {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(dataBytes);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static boolean isAccountActive(Account account) {
        return account != null && account.getStatus();
    }
}
