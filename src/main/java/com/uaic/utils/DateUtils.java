package com.uaic.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

  private DateUtils() {
    // do nothing
  }

  public static LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    if (date != null && date.length() > 0) {
      LocalDate dateTime = LocalDate.parse(date, formatter);
      return dateTime;
    } else {
      return LocalDate.now();
    }
  }

  public static String formateDate(Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyy-MM-dd");
    return simpleDateFormat.format(date);
  }
}
