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

package org.sonar.plugins.toxicity.debts;

import org.sonar.plugins.toxicity.debts.cost.CheckStyleTwoParametersCostProcessor;

import org.sonar.plugins.toxicity.debts.cost.CheckStyleOneParameterCostProcessor;

import org.sonar.plugins.toxicity.model.DebtType;

import static org.junit.Assert.*;

import org.sonar.api.rules.Rule;

import org.sonar.api.rules.Violation;

import org.junit.Test;

/**
 * @author ccoca
 *
 */
public class ViolationsMapperTest {

    @Test
    public void givenMatchingViolationWhenGetDebtTypeIsInvokedThenCorrectDebtTypeIsReturned() {

        assertEquals(DebtType.ANON_INNER_LENGTH, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.ANON_INNER_LENGTH_CHECK_STYLE)));
        assertEquals(DebtType.FILE_LENGTH, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.FILE_LENGTH_CHECK_STYLE)));
        assertEquals(DebtType.METHOD_LENGTH, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.METHOD_LENGTH_CHECK_STYLE)));
        assertEquals(DebtType.PARAMETER_NUMBER, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.PARAMETER_NUMBER_CHECK_STYLE)));
        assertEquals(DebtType.MISSING_SWITCH_DEFAULT, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.MISSING_SWITCH_DEFAULT_CHECK_STYLE)));
        assertEquals(DebtType.NESTED_IF_DEPTH, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.NESTED_IF_DEPTH_CHECK_STYLE)));
        assertEquals(DebtType.NESTED_TRY_DEPTH, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.NESTED_TRY_DEPTH_CHECK_STYLE)));
        assertEquals(DebtType.BOOLEAN_EXPRESSION_COMPLEXITY, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.BOOLEAN_EXPRESSION_COMPLEXITY_CHECK_STYLE)));
        assertEquals(DebtType.CLASS_DATA_ABSTRACTION_COUPLING, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.CLASS_DATA_ABSTRACTION_COUPLING_CHECK_STYLE)));
        assertEquals(DebtType.CLASS_FAN_OUT_COMPLEXITY, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.CLASS_FAN_OUT_COMPLEXITY_CHECK_STYLE)));
        assertEquals(DebtType.CYCLOMATIC_COMPLEXITY, ViolationsMapper.getDebtType(createViolation(ViolationsMapper.CYCLOMATIC_COMPLEXITY_CHECK_STYLE)));
    }

    @Test(expected=NullPointerException.class)
    public void givenNullViolationWhenGetDebtTypeIsInvokedThenNullPointerExceptionShouldBeThrown() {

        ViolationsMapper.getDebtType(null);
    }

    @Test
    public void givenMatchingViolationWhenGetDebtCostProcessorIsInvokedThenCorrectDebtCostProcessorIsReturned() {

        Class<?> one = new CheckStyleOneParameterCostProcessor().getClass();
        Class<?> two = new CheckStyleTwoParametersCostProcessor().getClass();

        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.ANON_INNER_LENGTH_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.FILE_LENGTH_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.METHOD_LENGTH_CHECK_STYLE)).getClass());
        assertEquals(one, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.PARAMETER_NUMBER_CHECK_STYLE)).getClass());
        assertEquals(one, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.MISSING_SWITCH_DEFAULT_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.NESTED_IF_DEPTH_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.NESTED_TRY_DEPTH_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.BOOLEAN_EXPRESSION_COMPLEXITY_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.CLASS_DATA_ABSTRACTION_COUPLING_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.CLASS_FAN_OUT_COMPLEXITY_CHECK_STYLE)).getClass());
        assertEquals(two, ViolationsMapper.getDebtCostProcessor(createViolation(ViolationsMapper.CYCLOMATIC_COMPLEXITY_CHECK_STYLE)).getClass());
    }

    @Test(expected=NullPointerException.class)
    public void givenNullViolationWhenGetDebtCostProcessorIsInvokedThenNullPointerExceptionShouldBeThrown() {

        ViolationsMapper.getDebtCostProcessor(null);
    }

    private Violation createViolation(String key) {

        return Violation.create(Rule.create("", key, ""), null);
    }
}
