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

package org.sonar.plugins.toxicity.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import org.junit.Test;

/**
 * @author ccoca
 *
 */
public class DebtTypeTest {

  @Test
  public void whenInvokeGetKeyThenCorrectValueIsReturned() {

    assertEquals("Boolean expression complexity", DebtType.BOOLEAN_EXPRESSION_COMPLEXITY.getKey());
    assertEquals("Class data abstraction coupling", DebtType.CLASS_DATA_ABSTRACTION_COUPLING.getKey());
    assertEquals("Class fan out complexity", DebtType.CLASS_FAN_OUT_COMPLEXITY.getKey());
    assertEquals("Cyclomatic complexity", DebtType.CYCLOMATIC_COMPLEXITY.getKey());
    assertEquals("File length", DebtType.FILE_LENGTH.getKey());
    assertEquals("Method length", DebtType.METHOD_LENGTH.getKey());
    assertEquals("Nested if depth", DebtType.NESTED_IF_DEPTH.getKey());
    assertEquals("Nested try depth", DebtType.NESTED_TRY_DEPTH.getKey());
    assertEquals("Anon inner length", DebtType.ANON_INNER_LENGTH.getKey());
    assertEquals("Parameter number", DebtType.PARAMETER_NUMBER.getKey());
    assertEquals("Missing switch default", DebtType.MISSING_SWITCH_DEFAULT.getKey());
  }

  @Test
  public void whenInvokeGetColorHexCodeThenCorrectColorIsReturned() {

    assertEquals("#9999FF", DebtType.BOOLEAN_EXPRESSION_COMPLEXITY.getColorHexCode());
    assertEquals("#AA4643", DebtType.CLASS_DATA_ABSTRACTION_COUPLING.getColorHexCode());
    assertEquals("#89A54E", DebtType.CLASS_FAN_OUT_COMPLEXITY.getColorHexCode());
    assertEquals("#71588F", DebtType.CYCLOMATIC_COMPLEXITY.getColorHexCode());
    assertEquals("#4198AF", DebtType.FILE_LENGTH.getColorHexCode());
    assertEquals("#DB843D", DebtType.METHOD_LENGTH.getColorHexCode());
    assertEquals("#93A9CF", DebtType.NESTED_IF_DEPTH.getColorHexCode());
    assertEquals("#BB9C68", DebtType.NESTED_TRY_DEPTH.getColorHexCode());
    assertEquals("#D19392", DebtType.ANON_INNER_LENGTH.getColorHexCode());
    assertEquals("#D19392", DebtType.ANON_INNER_LENGTH.getColorHexCode());
    assertEquals("#A99BBD", DebtType.MISSING_SWITCH_DEFAULT.getColorHexCode());
  }

  @Test
  public void whenInvokeGetDebtTypeByKeyWithInvalidKeyThenNullIsReturned() {
    DebtType actual = DebtType.getDebtTypeByKey("invalid");
    assertNull(actual);
  }

  @Test
  public void whenInvokeGetDebtTypeByKeyWithValidKeyThenRightTypeIsReturned() {
    DebtType actual = DebtType.getDebtTypeByKey(DebtType.METHOD_LENGTH.getKey());
    assertEquals(DebtType.METHOD_LENGTH, actual);
  }
}
