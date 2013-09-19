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

import org.sonar.api.rules.Violation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class ViolationMessageCostProcessor implements DebtCostProcessor {

  private static final Pattern PATTERN = Pattern.compile("[0-9]{1,3}(,[0-9]{3})*");
  protected static final int VALUE_INDEX = 0;

  public List<BigDecimal> parseMessage(Violation violation) {

    String message = violation.getMessage();
    Matcher matcher = PATTERN.matcher(message);

    List<BigDecimal> params = new ArrayList<BigDecimal>();
    while (matcher.find()) {
      params.add(new BigDecimal(matcher.group().replace(",", "")));
    }

    return params;
  }

}
