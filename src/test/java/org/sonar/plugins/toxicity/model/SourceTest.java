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

package org.sonar.plugins.toxicity.model;

import junit.framework.Assert;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author ccoca
 *
 */
public class SourceTest {

  private BigDecimal BOOLEAN_COMPLEXITY_COST = new BigDecimal("3.25");
  private BigDecimal ANNON_INNER_LENGTH_COST = new BigDecimal("7.25");

  @Test(expected = NullPointerException.class)
  public void givenNullNameThenNullPointerShouldBeThrown() {

    new Source(null);
  }

  @Test
  public void whenCompareIsInvokedThenDescResultShouldBeReturned() {

    Source one = new Source("one");
    one.addDebt(ModelUtil.createDebt(BigDecimal.ONE,
        DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));
    Source two = new Source("two");
    two.addDebt(ModelUtil
        .createDebt(BigDecimal.TEN, DebtType.ANON_INNER_LENGTH));

    Assert.assertEquals(1, one.compareTo(null));
    Assert.assertEquals(1, one.compareTo(two));
    Assert.assertEquals(-1, two.compareTo(one));
    Assert.assertEquals(0, one.compareTo(one));

  }

  @Test
  public void whenGetTotalIsInvokedThenSumOfDebtsCostShouldBeReturned() {

    BigDecimal total = BOOLEAN_COMPLEXITY_COST.add(ANNON_INNER_LENGTH_COST);

    Source source = new Source("compile.unit");
    source.addDebt(ModelUtil.createDebt(BOOLEAN_COMPLEXITY_COST,
        DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));
    source.addDebt(ModelUtil.createDebt(ANNON_INNER_LENGTH_COST,
        DebtType.ANON_INNER_LENGTH));

    Assert.assertEquals(total, source.getTotal());
  }

  @Test(expected = NullPointerException.class)
  public void whenNullDebtIsAddedThenNullPointerExceptionShouldBeThrown() {

    new Source().addDebt(null);
  }

  @Test
  public void whenDebtTypeAlreadyExistsThenDebtCostIsUpdated() {

    Source source = new Source("compile.unit");
    source.addDebt(ModelUtil.createDebt(BOOLEAN_COMPLEXITY_COST,
        DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));
    source.addDebt(ModelUtil.createDebt(BOOLEAN_COMPLEXITY_COST,
        DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));

    BigDecimal sum = BOOLEAN_COMPLEXITY_COST.add(BOOLEAN_COMPLEXITY_COST);

    Assert.assertTrue(source.getDebts().get(0).getCost().compareTo(sum) == 0);
  }
}
