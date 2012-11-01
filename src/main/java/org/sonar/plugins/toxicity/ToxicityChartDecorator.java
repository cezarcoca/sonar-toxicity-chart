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

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sonar.api.measures.Measure;
import org.sonar.plugins.toxicity.model.DebtType;
import org.sonar.plugins.toxicity.model.Toxicity;
import org.sonar.plugins.toxicity.xml.ToxicityXmlBuilder;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.Violation;

public class ToxicityChartDecorator implements Decorator {

  protected static final Logger LOGGER = LoggerFactory
      .getLogger(ToxicityChartDecorator.class);

  private String projectKey;

  public boolean shouldExecuteOnProject(Project project) {
    projectKey = project.getKey();
    return projectKey != null;
  }

  public void decorate(@SuppressWarnings("rawtypes") Resource resource,
      DecoratorContext context) {

    for (Violation violation : context.getViolations()) {
      DebtsFilter.getInstance().filter(violation);
    }

    if (allResourcesAreProcessed(resource)) {
      saveMeasures(context);
    }
  }

  @VisibleForTesting
  boolean allResourcesAreProcessed(
      @SuppressWarnings("rawtypes") Resource resource) {
    return projectKey.equals(resource.getKey());
  }

  @VisibleForTesting
  void saveMeasures(DecoratorContext context) {

    LOGGER.info("Saving metrics for: {} project.", projectKey);

    Toxicity toxicity = DebtsFilter.getInstance().getToxicity();

    context.saveMeasure(new Measure(ToxicityChartMetrics.TOXICITY_STATUS,
        new String(ToxicityXmlBuilder.convertToxicityToXml(toxicity))));
    context.saveMeasure(new Measure(ToxicityChartMetrics.TOXICITY_AVERAGE_VALUE,
            toxicity.getAverageCost()));

    for (DebtType debt : DebtType.values()) {
      context.saveMeasure(new Measure(ToxicityChartMetrics
          .getMetricByDebtType(debt), toxicity.getTotalCostByDebt(debt).doubleValue()));

    }

    LOGGER.info("Metrics saved successfully.");
  }
}
