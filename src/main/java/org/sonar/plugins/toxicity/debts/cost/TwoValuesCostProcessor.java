/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.toxicity.debts.cost;

import org.sonar.api.issue.Issue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TwoValuesCostProcessor implements DebtCostProcessor {

  private static final Pattern PATTERN = Pattern.compile("[0-9]{1,3}(,[0-9]{3})*");
  private static final int VALUE_INDEX = 0;
  private static final int REQUIRED_VALUE_INDEX = 1;

  @Override
  public BigDecimal getCost(Issue issue) {

    List<BigDecimal> params = parseMessage(issue);
    if (params.size() < 2) {
      throw new IllegalArgumentException("Invalid message "
        + issue.message() + ". Two integral parameters was expected");
    }

    return params.get(VALUE_INDEX).divide(params.get(REQUIRED_VALUE_INDEX),
      MathContext.DECIMAL32);
  }

  private List<BigDecimal> parseMessage(Issue issue) {

    String message = issue.message();
    Matcher matcher = PATTERN.matcher(message);

    List<BigDecimal> params = new ArrayList<BigDecimal>();
    while (matcher.find()) {
      params.add(new BigDecimal(matcher.group().replace(",", "")));
    }

    return params;
  }
}
