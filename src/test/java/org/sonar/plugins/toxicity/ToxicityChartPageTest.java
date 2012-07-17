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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ccoca
 *
 */
public class ToxicityChartPageTest {

    @Test
    public void checkTemplateIsInClasspath() {

        String path = new ToxicityChartPage().getTemplatePath();
        assertNotNull(getClass().getResource(path));
    }

    @Test
    public void checkPageTitleAndId() {

        assertEquals(ToxicityChartPlugin.PLUGIN_NAME, new ToxicityChartPage().getTitle());
        assertEquals(ToxicityChartPlugin.PLUGIN_KEY, new ToxicityChartPage().getId());
    }
}
