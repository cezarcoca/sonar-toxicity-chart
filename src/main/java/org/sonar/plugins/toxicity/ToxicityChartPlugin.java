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

import org.sonar.api.SonarPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.Extension;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.plugins.toxicity.chart.ToxicityChart;

import java.util.ArrayList;
import java.util.List;

@Properties({ @Property(key = ToxicityChartPlugin.TC_THRESHOLD, defaultValue = ToxicityChartPlugin.TC_THRESHOLD_DEFAULT,
  name = "Toxicity Chart threshold", description = "Toxicity Chart threshold.") })
public class ToxicityChartPlugin extends SonarPlugin {

  protected static final Logger LOGGER = LoggerFactory
      .getLogger(ToxicityChartPlugin.class);

  public static final String PLUGIN_NAME = "Toxicity Chart";
  public static final String PLUGIN_KEY = "toxicity-chart";

  public static final String TC_THRESHOLD = "threshold";
  public static final String TC_THRESHOLD_DEFAULT = "1";

  public ToxicityChartPlugin() {
    super();
    LOGGER.debug("Toxicity Chart Plugin successfully created.");
  }

  /**
   * @return the list of extensions of the plugin
   */
  public List<Class<? extends Extension>> getExtensions() {
    List<Class<? extends Extension>> extensions = new ArrayList<Class<? extends Extension>>();
    extensions.add(ToxicityChartDecorator.class);
    extensions.add(ToxicityChartAggregator.class);
    extensions.add(ToxicityChartPage.class);
    extensions.add(ToxicityChartMetrics.class);
    extensions.add(ToxicityChart.class);
    return extensions;
  }
}
