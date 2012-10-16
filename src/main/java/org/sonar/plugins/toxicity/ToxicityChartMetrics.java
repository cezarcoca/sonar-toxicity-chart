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

import com.google.common.base.Preconditions;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.plugins.toxicity.model.DebtType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * The metrics definition for the plugin
 *
 * @author ccoca
 *
 */
public class ToxicityChartMetrics implements org.sonar.api.measures.Metrics {

  /**
   * The Toxicity Chart metric
   */
  public static final Metric TOXICITY_STATUS = new Metric.Builder(
      "toxicity_chart_status", "Toxicity Chart Status", Metric.ValueType.DATA)
      .setDescription("XML representation of project's toxicity.")
      .setDirection(Metric.DIRECTION_NONE).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart average value
   */
  public static final Metric TOXICITY_AVERAGE_VALUE = new Metric.Builder(
      "toxicity_chart_average", "Toxicity Chart average value",
      Metric.ValueType.FLOAT).setDescription("Toxicity Chart average value.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart boolean expression complexity
   */
  public static final Metric TOXICITY_BOOLEAN_EXPRESSION_COMPLEXITY_VALUE = new Metric.Builder(
      "toxicity_chart_boolean_expression_complexity",
      "Toxicity Chart boolean expression complexity", Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart boolean expression complexity.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart class data abstraction coupling
   */
  public static final Metric TOXICITY_CLASS_DATA_ABSTRACTION_COUPLING_VALUE = new Metric.Builder(
      "toxicity_chart_class_data_abstraction_coupling",
      "Toxicity Chart class data abstraction coupling", Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart class data abstraction coupling.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart class fan out complexity
   */
  public static final Metric TOXICITY_CLASS_FAN_OUT_COMPLEXITY_VALUE = new Metric.Builder(
      "toxicity_chart_class_fan_out_complexity",
      "Toxicity Chart class fan out complexity", Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart class fan out complexity.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart cyclomatic complexity
   */
  public static final Metric TOXICITY_CYCLOMATIC_COMPLEXITY_VALUE = new Metric.Builder(
      "toxicity_chart_cyclomatic_complexity",
      "Toxicity Chart cyclomatic complexity", Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart cyclomatic complexity.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart file length
   */
  public static final Metric TOXICITY_FILE_LENGTH_VALUE = new Metric.Builder(
      "toxicity_chart_file_length", "Toxicity Chart file length",
      Metric.ValueType.FLOAT).setDescription("Toxicity Chart file length.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart method length
   */
  public static final Metric TOXICITY_METHOD_LENGTH_VALUE = new Metric.Builder(
      "toxicity_chart_method_length", "Toxicity Chart method length",
      Metric.ValueType.FLOAT).setDescription("Toxicity Chart method length.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart nested if depth
   */
  public static final Metric TOXICITY_NESTED_IF_DEPTH_VALUE = new Metric.Builder(
      "toxicity_chart_nested_if_depth", "Toxicity Chart nested if depth",
      Metric.ValueType.FLOAT).setDescription("Toxicity Chart nested if depth.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart nested try depth
   */
  public static final Metric TOXICITY_NESTED_TRY_DEPTH_VALUE = new Metric.Builder(
      "toxicity_chart_nested_try_depth", "Toxicity Chart nested try depth",
      Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart nested try depth.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart anon inner length
   */
  public static final Metric TOXICITY_ANON_INNER_LENGTH_VALUE = new Metric.Builder(
      "toxicity_chart_anon_inner_length", "Toxicity Chart anon inner length",
      Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart anon inner length.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart parameter number
   */
  public static final Metric TOXICITY_PARAMETER_NUMBER_VALUE = new Metric.Builder(
      "toxicity_chart_parameter_number", "Toxicity Chart parameter number",
      Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart parameter number.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * The Toxicity Chart missing switch default
   */
  public static final Metric TOXICITY_MISSING_SWITCH_DEFAULT_VALUE = new Metric.Builder(
      "toxicity_chart_missing_switch_default",
      "Toxicity Chart missing switch default", Metric.ValueType.FLOAT)
      .setDescription("Toxicity Chart missing switch default.")
      .setDirection(Metric.DIRECTION_WORST).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  /**
   * @return the declare metrics
   */
  public List<Metric> getMetrics() {
    return Arrays.asList(TOXICITY_STATUS, TOXICITY_AVERAGE_VALUE,
        TOXICITY_ANON_INNER_LENGTH_VALUE,
        TOXICITY_BOOLEAN_EXPRESSION_COMPLEXITY_VALUE,
        TOXICITY_CLASS_DATA_ABSTRACTION_COUPLING_VALUE,
        TOXICITY_CLASS_FAN_OUT_COMPLEXITY_VALUE,
        TOXICITY_CYCLOMATIC_COMPLEXITY_VALUE, TOXICITY_FILE_LENGTH_VALUE,
        TOXICITY_METHOD_LENGTH_VALUE, TOXICITY_MISSING_SWITCH_DEFAULT_VALUE,
        TOXICITY_NESTED_IF_DEPTH_VALUE, TOXICITY_NESTED_TRY_DEPTH_VALUE,
        TOXICITY_PARAMETER_NUMBER_VALUE);
  }

  private static final EnumMap<DebtType, Metric> METRICS = new EnumMap<DebtType, Metric>(
      DebtType.class);

  static {

    METRICS.put(DebtType.ANON_INNER_LENGTH, TOXICITY_ANON_INNER_LENGTH_VALUE);
    METRICS.put(DebtType.BOOLEAN_EXPRESSION_COMPLEXITY,
        TOXICITY_BOOLEAN_EXPRESSION_COMPLEXITY_VALUE);
    METRICS.put(DebtType.CLASS_DATA_ABSTRACTION_COUPLING,
        TOXICITY_CLASS_DATA_ABSTRACTION_COUPLING_VALUE);
    METRICS.put(DebtType.CLASS_FAN_OUT_COMPLEXITY,
        TOXICITY_CLASS_FAN_OUT_COMPLEXITY_VALUE);
    METRICS.put(DebtType.CYCLOMATIC_COMPLEXITY,
        TOXICITY_CYCLOMATIC_COMPLEXITY_VALUE);
    METRICS.put(DebtType.FILE_LENGTH, TOXICITY_FILE_LENGTH_VALUE);
    METRICS.put(DebtType.METHOD_LENGTH, TOXICITY_METHOD_LENGTH_VALUE);
    METRICS.put(DebtType.MISSING_SWITCH_DEFAULT,
        TOXICITY_MISSING_SWITCH_DEFAULT_VALUE);
    METRICS.put(DebtType.NESTED_IF_DEPTH, TOXICITY_NESTED_IF_DEPTH_VALUE);
    METRICS.put(DebtType.NESTED_TRY_DEPTH, TOXICITY_NESTED_TRY_DEPTH_VALUE);
    METRICS.put(DebtType.PARAMETER_NUMBER, TOXICITY_PARAMETER_NUMBER_VALUE);
  }

  /**
   * Gets metric by provided debt type.
   *
   * @param debt
   * @return Metric associate with this debt
   */
  static final Metric getMetricByDebtType(DebtType debt) {

    Preconditions.checkNotNull(debt);

    return METRICS.get(debt);
  }
}
