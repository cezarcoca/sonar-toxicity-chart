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
import org.sonar.api.rules.Violation;
import org.sonar.plugins.toxicity.debts.ViolationsMapper;
import org.sonar.plugins.toxicity.model.Debt;
import org.sonar.plugins.toxicity.model.DebtType;
import org.sonar.plugins.toxicity.model.Source;
import org.sonar.plugins.toxicity.model.Toxicity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible to filter violations and to store the founds debts.
 * The Singleton pattern was used in order to support multi-module projects.
 *
 * @author ccoca
 *
 */
final class DebtsFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebtsFilter.class);

    /**
     * Eager initialization.
     */
    private static final DebtsFilter INSTANCE = new DebtsFilter();

    private final Map<String, Source> sources;

    private DebtsFilter() {
        super();
        sources = new HashMap<String, Source>();
    }

    static DebtsFilter getInstance() {
        return INSTANCE;
    }

    void filter(Violation violation) {
        DebtType debtType = ViolationsMapper.getDebtType(violation);
        if (debtType != null) {

            Source source = getSource(violation);
            Debt debt = getDebtByType(source, debtType);
            BigDecimal cost = ViolationsMapper.getDebtCostProcessor(violation).getCost(violation);

            debt.addCost(cost);

            LOGGER.debug("Match found. Debt type is: {} - for: {}.", debtType.getKey(), source.getName());
        }
    }

    private Source getSource(Violation violation) {

        String name = violation.getResource().getLongName();
        Source source = sources.get(name);
        if (source == null) {
            source = new Source(name);
            sources.put(name, source);
        }

        return source;
    }

    private Debt getDebtByType(Source source, DebtType type) {

        Debt result = null;
        for (Debt debt : source.getDebts()) {
            if(debt.getDebtType() == type) {
                result = debt;
                break;
            }
        }

        if(result == null) {
            result = new Debt(type);
            source.addDebt(result);
        }

        return result;
    }

    Toxicity getToxicity() {

        Toxicity toxicity = new Toxicity();
        toxicity.setSources(sources.values());

        return toxicity;
    }

    void cleanup() {
        sources.clear();
    }
}
