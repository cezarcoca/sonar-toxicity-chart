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
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.ActiveRule;
import org.sonar.api.rules.ActiveRuleParam;
import org.sonar.api.rules.Violation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.sonar.plugins.toxicity.debts.cost.DebtProcessorFactory.AVOID_COMPLEX_METHODS_RULE_GENDARME;
import static org.sonar.plugins.toxicity.debts.cost.DebtProcessorFactory.GENDARME;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ccoca
 *
 */
public class OneValueCostProcessorTest {

  private RulesProfile profile;
  private Violation violation;
  private static final String KEY = AVOID_COMPLEX_METHODS_RULE_GENDARME;
  private static final String REPOSITORY = GENDARME;
  private static final String MESSAGE = "Method's cyclomatic complexity : 26.";
  private static final String INVALID_MESSAGE = "Method's cyclomatic complexity : _.";

  @Before
  public void setUp() {
    profile = mock(RulesProfile.class);
    violation = mock(Violation.class);
  }

  @Test
  public void givenInactiveRuleWhenCostIsComputedThenCostIsZero() {

    when(profile.getActiveRule(REPOSITORY, KEY)).thenReturn(null);
    OneValueCostProcessor cut = new OneValueCostProcessor(profile, REPOSITORY, KEY);
    assertEquals(BigDecimal.ZERO, cut.getCost(violation));
  }

  @Test
  public void givenActiveRuleAndMaxPropertyIsNotSetWhenCostIsComputedThenCostIsZero() {
    ActiveRule mockActivateRule = mockActivateRule("other", "NaN");
    when(profile.getActiveRule(REPOSITORY, KEY)).thenReturn(mockActivateRule);
    OneValueCostProcessor cut = new OneValueCostProcessor(profile, REPOSITORY, KEY);
    assertEquals(BigDecimal.ZERO, cut.getCost(violation));
  }

  @Test
  public void givenActiveRuleAndMaxPropertyIsNotANumberWhenCostIsComputedThenCostIsZero() {
    ActiveRule mockActivateRule = mockActivateRule(OneValueCostProcessor.MAX, "NaN");
    when(profile.getActiveRule(REPOSITORY, KEY)).thenReturn(mockActivateRule);
    OneValueCostProcessor cut = new OneValueCostProcessor(profile, REPOSITORY, KEY);
    assertEquals(BigDecimal.ZERO, cut.getCost(violation));
  }

  @Test
  public void givenActiveRuleAndMaxPropertySetWhenCostIsComputedThenCorrectValueIsReturned() {
    ActiveRule mockActivateRule = mockActivateRule(OneValueCostProcessor.MAX, "20");
    when(profile.getActiveRule(REPOSITORY, KEY)).thenReturn(mockActivateRule);
    when(violation.getMessage()).thenReturn(MESSAGE);
    OneValueCostProcessor cut = new OneValueCostProcessor(profile, REPOSITORY, KEY);
    assertEquals(0, cut.getCost(violation).compareTo(new BigDecimal("1.3")));
  }

  @Test(expected=IllegalArgumentException.class)
  public void givenActiveRuleAndMaxPropertySetAndInvalidMessageWhenCostIsComputedThenExceptionIsThrown() {
    ActiveRule mockActivateRule = mockActivateRule(OneValueCostProcessor.MAX, "20");
    when(profile.getActiveRule(REPOSITORY, KEY)).thenReturn(mockActivateRule);
    when(violation.getMessage()).thenReturn(INVALID_MESSAGE);
    OneValueCostProcessor cut = new OneValueCostProcessor(profile, REPOSITORY, KEY);
    cut.getCost(violation);
  }

  private ActiveRule mockActivateRule(String key, String value) {
    ActiveRule activeRule = mock(ActiveRule.class);
    ActiveRuleParam param = mock(ActiveRuleParam.class);

    List<ActiveRuleParam> params = new ArrayList<ActiveRuleParam>();
    params.add(param);

    when(param.getKey()).thenReturn(key);
    when(param.getValue()).thenReturn(value);
    when(activeRule.getActiveRuleParams()).thenReturn(params);

    return activeRule;
  }
}
