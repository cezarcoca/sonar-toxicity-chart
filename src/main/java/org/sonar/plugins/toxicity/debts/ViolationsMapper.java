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

package org.sonar.plugins.toxicity.debts;

import org.sonar.plugins.toxicity.model.DebtType;

import org.sonar.plugins.toxicity.debts.cost.CheckStyleTwoParametersCostProcessor;

import org.sonar.plugins.toxicity.debts.cost.CheckStyleOneParameterCostProcessor;

import org.sonar.plugins.toxicity.debts.cost.DebtCostProcessor;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.sonar.api.rules.Violation;

/**
 * @author ccoca
 *
 */
public final class ViolationsMapper {

    /**
     *
     */
    static final String CYCLOMATIC_COMPLEXITY_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck";
    /**
     *
     */
    static final String CLASS_FAN_OUT_COMPLEXITY_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck";
    /**
     *
     */
    static final String CLASS_DATA_ABSTRACTION_COUPLING_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck";
    /**
     *
     */
    static final String BOOLEAN_EXPRESSION_COMPLEXITY_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck";
    /**
     *
     */
    static final String NESTED_TRY_DEPTH_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheck";
    /**
     *
     */
    static final String NESTED_IF_DEPTH_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck";
    /**
     *
     */
    static final String MISSING_SWITCH_DEFAULT_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck";
    /**
     *
     */
    static final String PARAMETER_NUMBER_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck";
    /**
     *
     */
    static final String METHOD_LENGTH_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck";
    /**
     *
     */
    static final String FILE_LENGTH_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck";
    /**
     *
     */
    static final String ANON_INNER_LENGTH_CHECK_STYLE = "com.puppycrawl.tools.checkstyle.checks.sizes.AnonInnerLengthCheck";

    private static final Map<String, DebtType> VIOLATION_DEBT;
    private static final Map<String, DebtCostProcessor> VIOLATION_DEBT_COST_PROCESSOR;

    static {

        Map<String, DebtType> map = new HashMap<String, DebtType>();

        map.put(ANON_INNER_LENGTH_CHECK_STYLE, DebtType.ANON_INNER_LENGTH);
        map.put(FILE_LENGTH_CHECK_STYLE, DebtType.FILE_LENGTH);
        map.put(METHOD_LENGTH_CHECK_STYLE, DebtType.METHOD_LENGTH);
        map.put(PARAMETER_NUMBER_CHECK_STYLE, DebtType.PARAMETER_NUMBER);
        map.put(MISSING_SWITCH_DEFAULT_CHECK_STYLE, DebtType.MISSING_SWITCH_DEFAULT);
        map.put(NESTED_IF_DEPTH_CHECK_STYLE, DebtType.NESTED_IF_DEPTH);
        map.put(NESTED_TRY_DEPTH_CHECK_STYLE, DebtType.NESTED_TRY_DEPTH);
        map.put(BOOLEAN_EXPRESSION_COMPLEXITY_CHECK_STYLE, DebtType.BOOLEAN_EXPRESSION_COMPLEXITY);
        map.put(CLASS_DATA_ABSTRACTION_COUPLING_CHECK_STYLE, DebtType.CLASS_DATA_ABSTRACTION_COUPLING);
        map.put(CLASS_FAN_OUT_COMPLEXITY_CHECK_STYLE, DebtType.CLASS_FAN_OUT_COMPLEXITY);
        map.put(CYCLOMATIC_COMPLEXITY_CHECK_STYLE, DebtType.CYCLOMATIC_COMPLEXITY);

        VIOLATION_DEBT = Collections.unmodifiableMap(map);

        Map<String, DebtCostProcessor> processors = new HashMap<String, DebtCostProcessor>();
        DebtCostProcessor checkStyleOneParameter = new CheckStyleOneParameterCostProcessor();
        DebtCostProcessor checkStyleTwoParameters = new CheckStyleTwoParametersCostProcessor();

        processors.put(ANON_INNER_LENGTH_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(FILE_LENGTH_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(METHOD_LENGTH_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(PARAMETER_NUMBER_CHECK_STYLE, checkStyleOneParameter);
        processors.put(MISSING_SWITCH_DEFAULT_CHECK_STYLE, checkStyleOneParameter);
        processors.put(NESTED_IF_DEPTH_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(NESTED_TRY_DEPTH_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(BOOLEAN_EXPRESSION_COMPLEXITY_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(CLASS_DATA_ABSTRACTION_COUPLING_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(CLASS_FAN_OUT_COMPLEXITY_CHECK_STYLE, checkStyleTwoParameters);
        processors.put(CYCLOMATIC_COMPLEXITY_CHECK_STYLE, checkStyleTwoParameters);

        VIOLATION_DEBT_COST_PROCESSOR = Collections.unmodifiableMap(processors);
    }

    /**
     * Avoid accidentally instantiation.
     */
    private ViolationsMapper() {
        throw new AssertionError();
    }

    public static DebtType getDebtType(Violation violation) {

        Preconditions.checkNotNull(violation);

        return VIOLATION_DEBT.get(violation.getRule().getKey());
    }

    public static DebtCostProcessor getDebtCostProcessor(Violation violation) {

        Preconditions.checkNotNull(violation);

        return VIOLATION_DEBT_COST_PROCESSOR.get(violation.getRule().getKey());
    }

}
