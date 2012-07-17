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

package org.sonar.plugins.toxicity.xml;

import org.sonar.plugins.toxicity.model.DebtType;

import org.junit.Test;
import org.sonar.plugins.toxicity.model.Debt;
import org.sonar.plugins.toxicity.model.Source;
import org.sonar.plugins.toxicity.model.Toxicity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author ccoca
 *
 */
public class ToxicityXmlBuilderTest {

    private static final String SOURCE_1 = Toxicity.class.getName();
    private static final String SOURCE_2 = Source.class.getName();

    private static final BigDecimal CYCLOMATIC_COMPLEXITY_COST = new BigDecimal("10.25");
    private static final BigDecimal METHOD_LENGTH_COST = new BigDecimal("89.75");

    @Test
    public void whenCallConvertToxicityToXmlThenWellFormedXmlIsCreated() {

        Toxicity expected = new Toxicity();

        Source smallTotal = new Source(SOURCE_1);
        smallTotal.addDebt(new Debt(CYCLOMATIC_COMPLEXITY_COST, DebtType.CYCLOMATIC_COMPLEXITY));

        Source largeTotal = new Source(SOURCE_2);
        largeTotal.addDebt(new Debt(METHOD_LENGTH_COST, DebtType.METHOD_LENGTH));
        largeTotal.addDebt(new Debt(CYCLOMATIC_COMPLEXITY_COST, DebtType.CYCLOMATIC_COMPLEXITY));

        Collection<Source> sources = new ArrayList<Source>();
        sources.add(smallTotal);
        sources.add(largeTotal);
        expected.setSources(sources);

        byte[] xml = ToxicityXmlBuilder.convertToxicityToXml(expected);
        Toxicity generated = ToxicityXmlParser.convertXmlToToxicity(xml);

        assertEquals(expected.getSources().size(), generated.getSources().size());

        checkExpectations(largeTotal, generated.getSources().get(0));
        checkExpectations(smallTotal, generated.getSources().get(1));

    }

    private void checkExpectations(Source expected, Source actual) {
        assertEquals(expected.getName(), actual.getName());
        assertTrue(expected.getTotal().compareTo(actual.getTotal()) == 0);
        for(int i = 0; i < expected.getDebts().size(); i++) {
            assertEquals(expected.getDebts().get(i), expected.getDebts().get(i));
        }
    }

}
