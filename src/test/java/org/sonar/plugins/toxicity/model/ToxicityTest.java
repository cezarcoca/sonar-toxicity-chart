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
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ccoca
 *
 */
public class ToxicityTest {

    private static final BigDecimal ANON_INNER_LENGTH_COST = new BigDecimal("1");
    private static final BigDecimal BOOLEAN_EXPRESSION_COMPLEXITY_COST = new BigDecimal("2");
    private static final BigDecimal CLASS_DATA_ABSTRACTION_COUPLING_COST = new BigDecimal("3");
    private static final BigDecimal CLASS_FAN_OUT_COMPLEXITY_COST = new BigDecimal("4");
    private static final BigDecimal CYCLOMATIC_COMPLEXITY_COST = new BigDecimal("5");
    private static final BigDecimal FILE_LENGTH_COST = new BigDecimal("6");
    private static final BigDecimal METHOD_LENGTH_COST = new BigDecimal("7");
    private static final BigDecimal MISSING_SWITCH_DEFAULT_COST = new BigDecimal("8");
    private static final BigDecimal NESTED_IF_DEPTH_COST = new BigDecimal("9");
    private static final BigDecimal NESTED_TRY_DEPTH_COST = new BigDecimal("10");

    private static final BigDecimal TWO = new BigDecimal("2");

    @Test
    public void whenGetTotalDebtCostIsInvokedThenCorrectCostShouldBeReturned() {

        Toxicity toxicity = new Toxicity();

        Collection<Source> sources = new ArrayList<Source>();
        sources.add(createSource("toxicity"));
        sources.add(createSource("chart"));
        toxicity.setSources(sources);

        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.ANON_INNER_LENGTH).compareTo(ANON_INNER_LENGTH_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.BOOLEAN_EXPRESSION_COMPLEXITY).compareTo(BOOLEAN_EXPRESSION_COMPLEXITY_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.CLASS_DATA_ABSTRACTION_COUPLING).compareTo(CLASS_DATA_ABSTRACTION_COUPLING_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.CLASS_FAN_OUT_COMPLEXITY).compareTo(CLASS_FAN_OUT_COMPLEXITY_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.CYCLOMATIC_COMPLEXITY).compareTo(CYCLOMATIC_COMPLEXITY_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.FILE_LENGTH).compareTo(FILE_LENGTH_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.METHOD_LENGTH).compareTo(METHOD_LENGTH_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.MISSING_SWITCH_DEFAULT).compareTo(MISSING_SWITCH_DEFAULT_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.NESTED_IF_DEPTH).compareTo(NESTED_IF_DEPTH_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.NESTED_TRY_DEPTH).compareTo(NESTED_TRY_DEPTH_COST.multiply(TWO)) == 0);
        Assert.assertTrue(toxicity.getTotalCostByDebt(DebtType.PARAMETER_NUMBER).compareTo(BigDecimal.ZERO) == 0);

    }

    private Source createSource(String name) {

        Source source = new Source(name);

        source.addDebt(new Debt(ANON_INNER_LENGTH_COST, DebtType.ANON_INNER_LENGTH));
        source.addDebt(new Debt(BOOLEAN_EXPRESSION_COMPLEXITY_COST, DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));
        source.addDebt(new Debt(CLASS_DATA_ABSTRACTION_COUPLING_COST, DebtType.CLASS_DATA_ABSTRACTION_COUPLING));
        source.addDebt(new Debt(CLASS_FAN_OUT_COMPLEXITY_COST, DebtType.CLASS_FAN_OUT_COMPLEXITY));
        source.addDebt(new Debt(CYCLOMATIC_COMPLEXITY_COST, DebtType.CYCLOMATIC_COMPLEXITY));
        source.addDebt(new Debt(FILE_LENGTH_COST, DebtType.FILE_LENGTH));
        source.addDebt(new Debt(METHOD_LENGTH_COST, DebtType.METHOD_LENGTH));
        source.addDebt(new Debt(MISSING_SWITCH_DEFAULT_COST, DebtType.MISSING_SWITCH_DEFAULT));
        source.addDebt(new Debt(NESTED_IF_DEPTH_COST, DebtType.NESTED_IF_DEPTH));
        source.addDebt(new Debt(NESTED_TRY_DEPTH_COST, DebtType.NESTED_TRY_DEPTH));

        return source;
    }

}
