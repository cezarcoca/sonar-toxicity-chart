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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.ActiveRule;
import org.sonar.api.rules.ActiveRuleParam;
import org.sonar.api.rules.Violation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

class OneValueCostProcessor extends ViolationMessageCostProcessor {

  /**
   * The threshold value key.
   */
  static final String MAX = "max";

  private static final Logger LOGGER = LoggerFactory.getLogger(OneValueCostProcessor.class);

  private BigDecimal max;

  public OneValueCostProcessor(RulesProfile profile, String repository, String key) {
    ActiveRule rule = profile.getActiveRule(repository, key);
    if (rule == null) {
      LOGGER.warn("Rule not found. Repository: {} - Key: {}", repository, key);
      return;
    }
    max = getMaxValue(rule);
  }

  private BigDecimal getMaxValue(ActiveRule rule) {
    try {
      for (ActiveRuleParam param : rule.getActiveRuleParams()) {
        if (MAX.equals(param.getKey())) {
          return new BigDecimal(param.getValue());
        }
      }
    } catch (NumberFormatException e) {
      LOGGER.error("Max threshold value can not be computed for: " + rule.getRuleKey(), e);
    }
    return null;
  }

  public BigDecimal getCost(Violation violation) {

    if (max == null) {
      return BigDecimal.ZERO;
    }

    List<BigDecimal> params = parseMessage(violation);
    if (params.isEmpty()) {
      throw new IllegalArgumentException("Invalid message " + violation.getMessage()
        + ". One integral parameter was expected");
    }

    return params.get(VALUE_INDEX).divide(max, MathContext.DECIMAL32);
  }

}
