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

import com.google.common.annotations.VisibleForTesting;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.AbstractChart;
import org.sonar.api.charts.ChartParameters;
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.MeasureData;
import org.sonar.plugins.toxicity.ToxicityChartPlugin;
import org.sonar.plugins.toxicity.dao.MeasureDao;
import org.sonar.plugins.toxicity.model.Debt;
import org.sonar.plugins.toxicity.model.DebtType;
import org.sonar.plugins.toxicity.model.Source;
import org.sonar.plugins.toxicity.model.Toxicity;
import org.sonar.plugins.toxicity.xml.ToxicityXmlParser;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ToxicityChart extends AbstractChart {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ToxicityChart.class);

  static final String KEY = "toxicitychart";
  private static final int DEFAULT_WIDTH = 850;
  private static final int DEFAULT_HEIGHT = 576;
  private static final Float X_AXIS_FONT_SIZE = Float.valueOf("7");

  @VisibleForTesting
  static final String MEASURE_ID = "v";

  private Settings settings;
  private MeasureDao dao;

  public ToxicityChart(DatabaseSession session, Settings settings) {
    super();
    this.settings = settings;
    this.dao = new MeasureDao(session);
  }

  public String getKey() {

    return KEY;
  }

  @Override
  protected Plot getPlot(ChartParameters params) {

    MeasureData data = dao.getMeasureDataById(params.getValue(MEASURE_ID));
    Toxicity toxicity = ToxicityXmlParser.convertXmlToToxicity(data != null ? data.getData() : null);

    LinkedHashSet<String> seriesOrderedByFirstUse = new LinkedHashSet<String>();
    CategoryDataset dataset = createDataset(toxicity, seriesOrderedByFirstUse);

    JFreeChart chart = getChart(dataset);

    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    CategoryItemRenderer renderer = plot.getRenderer();
    plot.setNoDataMessage("No debts found");

    int i = 0;
    for (String name : seriesOrderedByFirstUse) {
      DebtType type = DebtType.getDebtTypeByKey(name);
      renderer.setSeriesPaint(i++, Color.decode(type.getColorHexCode()));
    }

    CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
    xAxis.setLabelFont(xAxis.getLabelFont().deriveFont(X_AXIS_FONT_SIZE));
    xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

    chart.setBackgroundPaint(ChartColor.WHITE);

    return plot;
  }

  private JFreeChart getChart(CategoryDataset dataset) {
    JFreeChart chart = createChart(dataset);
    final ChartPanel chartPanel = new ChartPanel(chart);

    java.awt.Dimension size = new java.awt.Dimension(DEFAULT_WIDTH,
        DEFAULT_HEIGHT);
    chartPanel.setPreferredSize(size);
    chartPanel.setMinimumSize(size);
    chartPanel.setMouseZoomable(true);
    return chart;
  }

  @VisibleForTesting
  CategoryDataset createDataset(Toxicity toxicity, Set<String> series) {

    DefaultCategoryDataset result = new DefaultCategoryDataset();

    List<Source> resources = toxicity.getSources(getThreshold());
    for (int i = 0; i < resources.size(); i++) {
      Source item = resources.get(i);
      String name = Integer.valueOf(i + 1).toString();
      for (Debt debt : item.getDebts()) {
        result.addValue(debt.getCost().doubleValue(), debt.getDebtType()
            .getKey(), name);
        series.add(debt.getDebtType().getKey());
      }
    }

    return result;
  }

  private JFreeChart createChart(final CategoryDataset dataset) {
    final JFreeChart chart = ChartFactory.createStackedBarChart("", "", "",
        dataset, PlotOrientation.VERTICAL, true, true, false);
    return chart;
  }

  @VisibleForTesting
  BigDecimal getThreshold() {

    String threshold = settings.getString(ToxicityChartPlugin.TC_THRESHOLD);

    BigDecimal value;
    try {
      value = new BigDecimal(threshold);
    } catch (NumberFormatException e) {
      value = new BigDecimal(ToxicityChartPlugin.TC_THRESHOLD_DEFAULT);
      LOGGER.debug("Failed to convert threshold to valid number.", e);
    }

    LOGGER.info("Threshold set for Toxicity Chart is: {}",
        value.toPlainString());

    return value;
  }

  @VisibleForTesting
  void setDao(MeasureDao dao) {
    this.dao = dao;
  }

}
