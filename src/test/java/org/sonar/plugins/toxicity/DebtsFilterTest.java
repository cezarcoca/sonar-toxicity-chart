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

package org.sonar.plugins.toxicity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.issue.Issue;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.toxicity.debts.cost.DebtProcessorFactory;
import org.sonar.plugins.toxicity.model.Source;
import org.sonar.plugins.toxicity.model.Toxicity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ccoca
 */
public final class DebtsFilterTest {

  private static final String SECOND = "second";
  private static final String FIRST = "first";
  private static final String THIRD = "third";
  private static final String MESSAGE_FILE_LENGTH = "Method length is 270 lines (max allowed is 30).";

  @Before
  public void setUp() {
    DebtsFilter.getInstance().cleanup();
  }

  @After
  public void tearDown() {
    DebtsFilter.getInstance().cleanup();
  }

  @Test
  public void whenInvokeFilterThenToxicityShouldBeUpdated() {

    Issue v1 = createIssue(MESSAGE_FILE_LENGTH, DebtProcessorFactory.FILE_LENGTH_CHECK_STYLE, FIRST);
    Issue v2 = createIssue("", DebtProcessorFactory.MISSING_SWITCH_DEFAULT_CHECK_STYLE, FIRST);
    Issue v3 = createIssue("", DebtProcessorFactory.MISSING_SWITCH_DEFAULT_CHECK_STYLE, SECOND);
    Issue v4 = createIssue("", "Other", THIRD);

    DebtsFilter.getInstance().filter(v1);
    DebtsFilter.getInstance().filter(v2);
    DebtsFilter.getInstance().filter(v3);
    DebtsFilter.getInstance().filter(v4);

    Toxicity toxicity = DebtsFilter.getInstance().getToxicity();

    assertEquals(2, toxicity.getSources().size());

    Source first = toxicity.getSources().get(0);

    assertEquals(FIRST, first.getName());
    assertEquals(2, first.getDebts().size());
    assertTrue(BigDecimal.TEN.compareTo(first.getTotal()) == 0);

    Source second = toxicity.getSources().get(1);
    assertEquals(SECOND, second.getName());
    assertEquals(1, second.getDebts().size());
    assertTrue(BigDecimal.ONE.compareTo(second.getTotal()) == 0);
  }

  private Issue createIssue(String message, String ruleKey, String resourceName) {

    Issue issue = mock(Issue.class, ruleKey);
    when(issue.ruleKey()).thenReturn(RuleKey.of("repository", ruleKey));
    when(issue.componentKey()).thenReturn(resourceName);
    when(issue.message()).thenReturn(message);
    return issue;
  }
}
