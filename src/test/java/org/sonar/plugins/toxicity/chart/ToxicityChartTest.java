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

import org.junit.Test;
import org.sonar.api.charts.ChartParameters;
import org.sonar.api.config.Settings;
import org.sonar.plugins.toxicity.ToxicityChartPlugin;
import org.sonar.plugins.toxicity.dao.MeasureDao;
import org.sonar.plugins.toxicity.model.DebtType;
import org.sonar.plugins.toxicity.model.ModelUtil;
import org.sonar.plugins.toxicity.model.Source;
import org.sonar.plugins.toxicity.model.Toxicity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static junit.framework.Assert.assertEquals;
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

        ToxicityChart chart = new ToxicityChart(null, createMockSettings("10.0"));
        assertTrue(BigDecimal.TEN.compareTo(chart.getThreshold()) == 0);
    }

    @Test
    public void givenTCThresholdInalidNumberWhenInvokeGetThresholdThenDefaultThresholdIsReturned() {

        ToxicityChart chart = new ToxicityChart(null, createMockSettings("10.A"));
        assertTrue(new BigDecimal(ToxicityChartPlugin.TC_THRESHOLD_DEFAULT).compareTo(chart.getThreshold()) == 0);
    }

    @Test
    public void givenInvalidMeasureIdWhenGetPlotIsInvokedThenNoExceptionIsThrown() {

        String id = "1";

        ChartParameters params = mock(ChartParameters.class);
        when(params.getValue(ToxicityChart.MEASURE_ID)).thenReturn(id);

        MeasureDao dao = mock(MeasureDao.class);
        when(dao.getMeasureDataById(id)).thenReturn(null);

        ToxicityChart chart = new ToxicityChart(null, createMockSettings("10.0"));
        chart.setDao(dao);

        chart.getPlot(params);
    }

    @Test
    public void whenCreateDatasetThenSeriesAreRenderedByFirstUse() {

      Source s1 = new Source("source1");
      s1.addDebt(ModelUtil.createDebt(BigDecimal.TEN, DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));
      s1.addDebt(ModelUtil.createDebt(BigDecimal.TEN, DebtType.ANON_INNER_LENGTH));

      Source s2 = new Source("source12");
      s2.addDebt(ModelUtil.createDebt(BigDecimal.TEN, DebtType.METHOD_LENGTH));
      s2.addDebt(ModelUtil.createDebt(BigDecimal.ONE, DebtType.ANON_INNER_LENGTH));
      s2.addDebt(ModelUtil.createDebt(BigDecimal.ONE, DebtType.BOOLEAN_EXPRESSION_COMPLEXITY));

      Toxicity toxicity = new Toxicity();
      toxicity.setSources(Arrays.asList(new Source[]{s1, s2}));

      LinkedHashSet<String> series = new LinkedHashSet<String>();
      new ToxicityChart(null, createMockSettings("1")).createDataset(toxicity, series);

      String[] debts = series.toArray(new String[]{});
      assertEquals(DebtType.BOOLEAN_EXPRESSION_COMPLEXITY.getKey(), debts[0]);
      assertEquals(DebtType.ANON_INNER_LENGTH.getKey(), debts[1]);
      assertEquals(DebtType.METHOD_LENGTH.getKey(), debts[2]);

    }

    @Test
    public void whenGetKeyIsInvokedThenCorrectKeyIsReturned() {
      assertEquals(ToxicityChart.KEY, new ToxicityChart(null, createMockSettings("1")).getKey());
    }

    private Settings createMockSettings(String returnedValue) {

        Settings settings = new Settings();
        settings.setProperty(ToxicityChartPlugin.TC_THRESHOLD, returnedValue);

        return settings;
    }

}
