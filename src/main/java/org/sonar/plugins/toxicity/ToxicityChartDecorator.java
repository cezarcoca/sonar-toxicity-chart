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
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.Violation;

public class ToxicityChartDecorator implements Decorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToxicityChartDecorator.class);

    private ToxicityChartAggregator aggregator;

    public ToxicityChartDecorator(ToxicityChartAggregator aggregator) {
        super();
        this.aggregator = aggregator;
        LOGGER.info("Toxicity Chart decorator - successfully created.");
    }

    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }

    public void decorate(@SuppressWarnings("rawtypes") Resource resource,
            DecoratorContext context) {

        for (Violation violation : context.getViolations()) {
            aggregator.getDebtsFilter().filter(violation);
        }
    }
}
