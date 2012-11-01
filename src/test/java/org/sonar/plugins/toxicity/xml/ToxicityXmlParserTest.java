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

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.sonar.plugins.toxicity.model.DebtType;
import org.sonar.plugins.toxicity.model.Source;
import org.sonar.plugins.toxicity.model.Toxicity;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author ccoca
 *
 */
public class ToxicityXmlParserTest {

    @Test
    public void givenValidXmlInputWhenParseThenToxicityStructureIsCorrectCreated() throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copy(ToxicityXmlParserTest.class.getResourceAsStream("/toxicity.xml"), output);
        Toxicity toxicity = ToxicityXmlParser.convertXmlToToxicity(output.toByteArray());

        assertEquals(2, toxicity.getSources().size());

        Source first = toxicity.getSources().get(0);
        assertEquals("net.sf.jmoney.entrytable.VirtualRowTable", first.getName());
        assertTrue(first.getTotal().compareTo(new BigDecimal("22.25")) == 0);
        assertEquals(3, first.getDebts().size());

        assertEquals(DebtType.FILE_LENGTH.getKey(), first.getDebts().get(0).getDebtType().getKey());
        assertTrue(first.getDebts().get(0).getCost().compareTo(new BigDecimal("2.95")) == 0);

        assertEquals(DebtType.METHOD_LENGTH.getKey(), first.getDebts().get(1).getDebtType().getKey());
        assertTrue(first.getDebts().get(1).getCost().compareTo(new BigDecimal("16.86")) == 0);

        assertEquals(DebtType.CYCLOMATIC_COMPLEXITY.getKey(), first.getDebts().get(2).getDebtType().getKey());
        assertTrue(first.getDebts().get(2).getCost().compareTo(new BigDecimal("2.44")) == 0);

        Source seconde = toxicity.getSources().get(1);
        assertEquals("net.sf.jmoney.entrytable.EntriesTable", seconde.getName());
        assertTrue(seconde.getTotal().compareTo(new BigDecimal("1.3")) == 0);
        assertEquals(1, seconde.getDebts().size());

        assertEquals(DebtType.CYCLOMATIC_COMPLEXITY.getKey(), seconde.getDebts().get(0).getDebtType().getKey());
        assertTrue(seconde.getDebts().get(0).getCost().compareTo(new BigDecimal("1.3")) == 0);
    }


    @Test
    public void givenInvalidInputWhenParseThenEmptyToxicityStructureIsCreated() {

        Toxicity toxicity = ToxicityXmlParser.convertXmlToToxicity(null);
        assertEquals(0, toxicity.getSources().size());

        toxicity = ToxicityXmlParser.convertXmlToToxicity(new byte[]{});
        assertEquals(0, toxicity.getSources().size());
    }
}
