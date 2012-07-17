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

package org.sonar.plugins.toxicity.chart;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;
import org.sonar.plugins.toxicity.ToxicityChartPlugin;

import java.math.BigDecimal;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ccoca
 *
 */
public class ToxicityChartTest {

    @Test
    public void givenTCThresholdValidNumberWhenInvokeGetThresholdThenCorrectNumberIsReturned() {

        ToxicityChart chart = new ToxicityChart(null, createMockConfiguration("10.0"));
        assertTrue(BigDecimal.TEN.compareTo(chart.getThreshold()) == 0);
    }

    @Test
    public void givenTCThresholdInalidNumberWhenInvokeGetThresholdThenDefaultThresholdIsReturned() {

        ToxicityChart chart = new ToxicityChart(null, createMockConfiguration("10.A"));
        assertTrue(new BigDecimal(ToxicityChartPlugin.TC_THRESHOLD_DEFAULT).compareTo(chart.getThreshold()) == 0);
    }


    private Configuration createMockConfiguration(String returnedValue) {

        Configuration configuration = mock(Configuration.class);
        when(configuration.getString(ToxicityChartPlugin.TC_THRESHOLD,
                ToxicityChartPlugin.TC_THRESHOLD_DEFAULT)).thenReturn(returnedValue);

        return configuration;
    }

}
