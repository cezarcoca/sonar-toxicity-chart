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

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.sonar.api.issue.Issue;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ccoca
 *
 */
public class TwoValuesCostProcessorTest {

  private static final String MESSAGE_TWO_NUMERIC_VALUE = "Method length is 44 lines (max allowed is 30).";
  private static final String MESSAGE_WITH_DEVISOR_EQUALS_ZERO = "Reduce the number of conditional operators (4) used in the expression (maximum allowed 0).";
  private static final String MESSAGE_NO_NUMERIC_VALUE = "Method length.";
  private static final String MESSAGE_ONE_NUMERIC_VALUE = "Method length is 44.";
  private static final String MESSAGE_LARGE_VALUES = "This file has 90300 lines of code, which is greater than 50 authorized. Split it into smaller files.";

  @Test
  public void whenWellFormedMessageIsReceivedThenCorrectRatioIsCalculated() {

    assertTrue(new BigDecimal("1.466667")
        .compareTo(new TwoValuesCostProcessor()
            .getCost(createIssue(MESSAGE_TWO_NUMERIC_VALUE))) == 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenMessageWithNoNumericValueIsReceivedThenIllegalArgumentExceptionIsThrown() {

    new TwoValuesCostProcessor()
      .getCost(createIssue(MESSAGE_NO_NUMERIC_VALUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenMessageWithOneNumericValueIsReceivedThenIllegalArgumentExceptionIsThrown() {

    new TwoValuesCostProcessor()
      .getCost(createIssue(MESSAGE_ONE_NUMERIC_VALUE));
  }

  @Test
  public void whenMessageWithLargeValuesThenCostIsComputedCorrectly() {

    assertTrue(new BigDecimal("1806")
        .compareTo(new TwoValuesCostProcessor()
            .getCost(createIssue(MESSAGE_LARGE_VALUES))) == 0);
  }

  @Test
  public void whenDivisorIsZeroThenCostIsZero() {

    assertTrue("Cost is zero if devizor is zero", BigDecimal.ZERO
        .compareTo(new TwoValuesCostProcessor()
            .getCost(createIssue(MESSAGE_WITH_DEVISOR_EQUALS_ZERO))) == 0);
  }

  private Issue createIssue(String message) {

    Issue issue = mock(Issue.class);
    when(issue.message()).thenReturn(message);
    return issue;
  }
}
