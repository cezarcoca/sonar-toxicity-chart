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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Initializer;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.resources.Project;
import org.sonar.plugins.toxicity.model.DebtType;
import org.sonar.plugins.toxicity.model.Toxicity;
import org.sonar.plugins.toxicity.xml.ToxicityXmlBuilder;

import java.math.BigDecimal;

public class ToxicityChartAggregator extends Initializer implements PostJob {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ToxicityChartAggregator.class);

    private DebtsFilter debtsFilter;

    /* (non-Javadoc)
     * @see org.sonar.api.batch.Initializer#execute(org.sonar.api.resources.Project)
     */
    @Override
    public void execute(Project project) {

        LOGGER.info("Project {} initialized successfully.", project.getLongName());
        debtsFilter = new DebtsFilter();
    }

    public void executeOn(Project project, SensorContext context) {

        LOGGER.info("Starting post-job phase for: {} project.", context.toString());

        Toxicity toxicity = debtsFilter.getToxicity();

        context.saveMeasure(createXmlMeasure(new String(ToxicityXmlBuilder.convertToxicityToXml(toxicity))));
        context.saveMeasure(createAverageMeasure(toxicity.getAverageCost()));

        for (DebtType debt : DebtType.values()) {
            if(debt != DebtType.UNKNOWN) {
                context.saveMeasure(createToxicityMeasure(ToxicityChartMetrics.getMetricByDebtType(debt), toxicity.getTotalCostByDebt(debt)));
            }
        }

        LOGGER.info("Post-job phase finished successfully.");
    }

    private Measure createXmlMeasure(String data) {

        Measure measure = new Measure(ToxicityChartMetrics.TOXICITY_STATUS, data);
        measure.setPersistenceMode(PersistenceMode.FULL);

        return measure;
    }

    private Measure createAverageMeasure(Double average) {

        Measure measure = new Measure(ToxicityChartMetrics.TOXICITY_AVERAGE_VALUE, average);
        measure.setPersistenceMode(PersistenceMode.FULL);

        return measure;
    }

    private Measure createToxicityMeasure(Metric metric, BigDecimal value) {

        Measure measure = new Measure(metric, value.doubleValue());
        measure.setPersistenceMode(PersistenceMode.FULL);

        return measure;
    }

    public DebtsFilter getDebtsFilter() {
        return debtsFilter;
    }

}
