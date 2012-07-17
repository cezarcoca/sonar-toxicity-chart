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

package org.sonar.plugins.toxicity.debts.cost;


import org.sonar.api.rules.Violation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckStyleTwoParametersCostProcessor implements DebtCostProcessor {

    private static final Pattern PATTERN = Pattern.compile("[0-9]{1,3}(,[0-9]{3})*");
    private static final int VALUE_INDEX = 0;
    private static final int REQUIRED_VALUE_INDEX = 1;

    public BigDecimal getCost(Violation violation) {

        String message = violation.getMessage();
        Matcher matcher = PATTERN.matcher(message);

        BigDecimal[] params = new BigDecimal[2];
        int i = 0;
        while(matcher.find() && i < params.length) {
            params[i++] = new BigDecimal(matcher.group().replace(",", ""));
        }

        if(params[VALUE_INDEX] == null || params[REQUIRED_VALUE_INDEX] == null) {
            throw new IllegalArgumentException("Invalid message " + message +". Two integral parameters was expected");
        }

        return params[VALUE_INDEX].divide(params[REQUIRED_VALUE_INDEX], MathContext.DECIMAL32);
    }
}
