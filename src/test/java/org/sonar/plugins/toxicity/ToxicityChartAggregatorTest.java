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

import org.sonar.api.resources.Project;

import org.sonar.plugins.toxicity.model.DebtType;

import org.junit.Test;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.Measure;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author ccoca
 *
 */
public class ToxicityChartAggregatorTest {

    @Test
    public void whenExecuteOnIsInvokedThenAllMetricsAreSaved() {

        SensorContext context = mock(SensorContext.class);
        Project project = new Project("JAVA project.");

        ToxicityChartAggregator aggregator = new ToxicityChartAggregator();
        aggregator.executeOn(project, context);

        int measures = DebtType.values().length + 2;

        verify(context, times(measures)).saveMeasure(any(Measure.class));
    }
}
