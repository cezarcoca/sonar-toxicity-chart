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

import static junit.framework.Assert.assertEquals;

import org.sonar.plugins.toxicity.model.DebtType;

import org.junit.Test;

/**
 * @author ccoca
 *
 */
public class ToxicityChartMetricsTest {

    @Test
    public void whenGetMetricsThenNoEmptyListIsReturn() {

        assertEquals(13, new ToxicityChartMetrics().getMetrics().size());
    }

    @Test(expected=NullPointerException.class)
    public void whenGetMetricByDebtTypeIsInvokedWithNullDebtThenNullPointerExceptionShouldBeThrown() {

        ToxicityChartMetrics.getMetricByDebtType(null);
    }

    @Test
    public void whenGetMetricByDebtTypeIsInvokedWithValidDebtThenAppropriateMetricShouldBeReturned() {

        assertEquals(ToxicityChartMetrics.TOXICITY_ANON_INNER_LENGTH_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.ANON_INNER_LENGTH));
        assertEquals(ToxicityChartMetrics.TOXICITY_BOOLEAN_EXPRESSION_COMPLEXITY_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));
        assertEquals(ToxicityChartMetrics.TOXICITY_CLASS_DATA_ABSTRACTION_COUPLING_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.CLASS_DATA_ABSTRACTION_COUPLING));
        assertEquals(ToxicityChartMetrics.TOXICITY_CLASS_FAN_OUT_COMPLEXITY_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.CLASS_FAN_OUT_COMPLEXITY));
        assertEquals(ToxicityChartMetrics.TOXICITY_CYCLOMATIC_COMPLEXITY_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.CYCLOMATIC_COMPLEXITY));
        assertEquals(ToxicityChartMetrics.TOXICITY_FILE_LENGTH_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.FILE_LENGTH));
        assertEquals(ToxicityChartMetrics.TOXICITY_METHOD_LENGTH_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.METHOD_LENGTH));
        assertEquals(ToxicityChartMetrics.TOXICITY_MISSING_SWITCH_DEFAULT_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.MISSING_SWITCH_DEFAULT));
        assertEquals(ToxicityChartMetrics.TOXICITY_NESTED_IF_DEPTH_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.NESTED_IF_DEPTH));
        assertEquals(ToxicityChartMetrics.TOXICITY_NESTED_TRY_DEPTH_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.NESTED_TRY_DEPTH));
        assertEquals(ToxicityChartMetrics.TOXICITY_PARAMETER_NUMBER_VALUE, ToxicityChartMetrics.getMetricByDebtType(DebtType.PARAMETER_NUMBER));
    }
}
