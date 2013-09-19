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

import junit.framework.Assert;
import org.junit.Test;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.Violation;

import java.math.BigDecimal;

/**
 * @author ccoca
 *
 */
public class TwoValuesCostProcessorTest {

  private static final String MESSAGE_TWO_NUMERIC_VALUE = "Method length is 44 lines (max allowed is 30).";
  private static final String MESSAGE_NO_NUMERIC_VALUE = "Method length.";
  private static final String MESSAGE_ONE_NUMERIC_VALUE = "Method length is 44.";

  @Test
  public void whenWellFormedMessageIsReceivedThenCorrectRatioIsCalculated() {

    Assert.assertTrue(new BigDecimal("1.466667")
      .compareTo(new TwoValuesCostProcessor()
        .getCost(createViolation(MESSAGE_TWO_NUMERIC_VALUE))) == 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenMessageWithNoNumericValueIsReceivedThenIllegalArgumentExceptionIsThrown() {

    new TwoValuesCostProcessor()
      .getCost(createViolation(MESSAGE_NO_NUMERIC_VALUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenMessageWithOneNumericValueIsReceivedThenIllegalArgumentExceptionIsThrown() {

    new TwoValuesCostProcessor()
      .getCost(createViolation(MESSAGE_ONE_NUMERIC_VALUE));
  }

  private Violation createViolation(String message) {
    Violation violation = Violation.create(Rule.create(), null);
    violation.setMessage(message);
    return violation;
  }
}
