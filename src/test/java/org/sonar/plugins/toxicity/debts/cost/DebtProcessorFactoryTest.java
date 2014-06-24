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

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.issue.Issue;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.toxicity.model.DebtType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ccoca
 *
 */
public class DebtProcessorFactoryTest {

  private DebtProcessorFactory cut;

  @Before
  public void setUp() {
    cut = new DebtProcessorFactory();
  }

  @Test
  public void givenMatchingViolationWhenGetDebtProcessorIsInvokedThenCorrectDebtTypeIsReturned() {

    assertEquals(DebtType.ANON_INNER_LENGTH, cut.getDebtProcessor(createIssue(DebtProcessorFactory.ANON_INNER_LENGTH_CHECK_STYLE)).getType());
    assertEquals(DebtType.FILE_LENGTH, cut.getDebtProcessor(createIssue(DebtProcessorFactory.FILE_LENGTH_CHECK_STYLE)).getType());
    assertEquals(DebtType.METHOD_LENGTH, cut.getDebtProcessor(createIssue(DebtProcessorFactory.METHOD_LENGTH_CHECK_STYLE)).getType());
    assertEquals(DebtType.PARAMETER_NUMBER, cut.getDebtProcessor(createIssue(DebtProcessorFactory.PARAMETER_NUMBER_CHECK_STYLE)).getType());
    assertEquals(DebtType.MISSING_SWITCH_DEFAULT, cut.getDebtProcessor(createIssue(DebtProcessorFactory.MISSING_SWITCH_DEFAULT_CHECK_STYLE)).getType());
    assertEquals(DebtType.NESTED_IF_DEPTH, cut.getDebtProcessor(createIssue(DebtProcessorFactory.NESTED_IF_DEPTH_CHECK_STYLE)).getType());
    assertEquals(DebtType.NESTED_TRY_DEPTH, cut.getDebtProcessor(createIssue(DebtProcessorFactory.NESTED_TRY_DEPTH_CHECK_STYLE)).getType());
    assertEquals(DebtType.BOOLEAN_EXPRESSION_COMPLEXITY, cut.getDebtProcessor(createIssue(DebtProcessorFactory.BOOLEAN_EXPRESSION_COMPLEXITY_CHECK_STYLE)).getType());
    assertEquals(DebtType.CLASS_DATA_ABSTRACTION_COUPLING, cut.getDebtProcessor(createIssue(DebtProcessorFactory.CLASS_DATA_ABSTRACTION_COUPLING_CHECK_STYLE)).getType());
    assertEquals(DebtType.CLASS_FAN_OUT_COMPLEXITY, cut.getDebtProcessor(createIssue(DebtProcessorFactory.CLASS_FAN_OUT_COMPLEXITY_CHECK_STYLE)).getType());
    assertEquals(DebtType.CYCLOMATIC_COMPLEXITY, cut.getDebtProcessor(createIssue(DebtProcessorFactory.CYCLOMATIC_COMPLEXITY_CHECK_STYLE)).getType());
  }

  @Test(expected = NullPointerException.class)
  public void givenNullViolationWhenGetDebtProcessorIsInvokedThenNullPointerExceptionShouldBeThrown() {

    cut.getDebtProcessor(null);
  }

  @Test
  public void givenMatchingViolationWhengetDebtProcessorIsInvokedThenCorrectDebtCostProcessorIsReturned() {

    Class<?> one = new ConstantCostProcessor().getClass();
    Class<?> two = new TwoValuesCostProcessor().getClass();

    assertEquals(two, cut.getDebtProcessor(createIssue(DebtProcessorFactory.ANON_INNER_LENGTH_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(two, cut.getDebtProcessor(createIssue(DebtProcessorFactory.FILE_LENGTH_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(two, cut.getDebtProcessor(createIssue(DebtProcessorFactory.METHOD_LENGTH_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(one, cut.getDebtProcessor(createIssue(DebtProcessorFactory.PARAMETER_NUMBER_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(one, cut.getDebtProcessor(createIssue(DebtProcessorFactory.MISSING_SWITCH_DEFAULT_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(one, cut.getDebtProcessor(createIssue(DebtProcessorFactory.NESTED_IF_DEPTH_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(one, cut.getDebtProcessor(createIssue(DebtProcessorFactory.NESTED_TRY_DEPTH_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(two, cut.getDebtProcessor(createIssue(DebtProcessorFactory.BOOLEAN_EXPRESSION_COMPLEXITY_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(two, cut.getDebtProcessor(createIssue(DebtProcessorFactory.CLASS_DATA_ABSTRACTION_COUPLING_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(two, cut.getDebtProcessor(createIssue(DebtProcessorFactory.CLASS_FAN_OUT_COMPLEXITY_CHECK_STYLE)).getCostProcessor().getClass());
    assertEquals(two, cut.getDebtProcessor(createIssue(DebtProcessorFactory.CYCLOMATIC_COMPLEXITY_CHECK_STYLE)).getCostProcessor().getClass());
  }

  private Issue createIssue(String key) {

    Issue issue = mock(Issue.class, key);
    when(issue.ruleKey()).thenReturn(RuleKey.of("repository", key));
    return issue;
  }
}
