package com.srini.feature.util;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

public class HibernateBasicSqlFormatter implements MessageFormattingStrategy {
  private final Formatter formatter = new BasicFormatterImpl();

  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql,
      String url) {
    String res = !sql.isEmpty() ? sql : prepared;
    if (res.isEmpty()) {
      return "";
    }
    String template = "Query: %s %s {Elapsed time: %sms}";
    String batch = "batch".equals(category) ? "batch operation" : "";
    return String.format(template, batch, formatter.format(res), elapsed);
  }
}