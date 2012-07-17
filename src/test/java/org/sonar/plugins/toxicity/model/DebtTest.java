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

import org.junit.Test;

import junit.framework.Assert;

import java.math.BigDecimal;

/**
 * @author ccoca
 *
 */
public class DebtTest {

    @Test
    public void givenTwoEquivalentDebtsWhenCallEqualThenTrueShouldBeReturned() {

        Debt first = new Debt(new BigDecimal("10.0"), DebtType.UNKNOWN);
        Debt second = new Debt(new BigDecimal("10.000"), DebtType.UNKNOWN);

        Assert.assertTrue(first.equals(second));
        Assert.assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void givenTwoNotEquivalentDebtsWhenCallEqualThenFalseShouldBeReturned() {

        Debt first = new Debt(new BigDecimal("10.01"), DebtType.UNKNOWN);
        Debt second = new Debt(new BigDecimal("10.00"), DebtType.UNKNOWN);

        Assert.assertFalse(first.equals(second));

        first = new Debt(new BigDecimal("10.00"), DebtType.UNKNOWN);
        second = new Debt(new BigDecimal("10.00"), DebtType.BOOLEAN_EXPRESSION_COMPLEXITY);

        Assert.assertFalse(first.equals(second));
    }

    @Test(expected=NullPointerException.class)
    public void givenNullCostWhenCallConstructorThenNullPointerExceptionShouldBeThrown() {
        new Debt(null, DebtType.CLASS_FAN_OUT_COMPLEXITY);
    }

    @Test(expected=NullPointerException.class)
    public void givenNullKeyWhenCallConstructorThenNullPointerExceptionShouldBeThrown() {
        new Debt(BigDecimal.ZERO, null);
    }
}
