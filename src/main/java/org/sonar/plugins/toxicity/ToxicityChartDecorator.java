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

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.plugins.toxicity.model.DebtType;
import org.sonar.plugins.toxicity.model.Toxicity;
import org.sonar.plugins.toxicity.xml.ToxicityXmlBuilder;

import com.google.common.annotations.VisibleForTesting;

@DependsUpon(DecoratorBarriers.ISSUES_TRACKED)
public class ToxicityChartDecorator implements Decorator {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ToxicityChartDecorator.class);

    private String projectKey;
    private final ResourcePerspectives perspectives;

    public ToxicityChartDecorator(ResourcePerspectives perspectives) {
        this.perspectives = perspectives;
    }

    @Override
    public boolean shouldExecuteOnProject(Project project) {
        projectKey = project.getKey();
        return projectKey != null;
    }

    @Override
    public void decorate(Resource resource, DecoratorContext context) {

        Issuable issuable = perspectives.as(Issuable.class, resource);
        if (issuable != null) {
            for (Issue issue : issuable.issues()) {
                DebtsFilter.getInstance().filter(issue);
            }
        }

        if (allResourcesAreProcessed(resource)) {
            saveMeasures(context);
        }
    }

    @VisibleForTesting
    boolean allResourcesAreProcessed(Resource resource) {
        return projectKey.equals(resource.getKey());
    }

    @VisibleForTesting
    void saveMeasures(DecoratorContext context) {

        LOGGER.info("Saving metrics for: {} project.", projectKey);

        Toxicity toxicity = DebtsFilter.getInstance().getToxicity();

        context.saveMeasure(new Measure(ToxicityChartMetrics.TOXICITY_STATUS,
                getToxicityAsXml(toxicity)));
        context.saveMeasure(new Measure(
                ToxicityChartMetrics.TOXICITY_AVERAGE_VALUE, toxicity
                        .getAverageCost()));

        for (DebtType debt : DebtType.values()) {
            context.saveMeasure(new Measure(ToxicityChartMetrics
                    .getMetricByDebtType(debt), toxicity.getTotalCostByDebt(
                    debt).doubleValue()));
        }

        LOGGER.info("Metrics saved successfully.");
    }

    private String getToxicityAsXml(Toxicity toxicity) {
        try {
            return new String(
                    ToxicityXmlBuilder.convertToxicityToXml(toxicity), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }
}
