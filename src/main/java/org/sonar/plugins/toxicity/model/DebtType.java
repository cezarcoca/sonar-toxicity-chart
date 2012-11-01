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

public enum DebtType {

  BOOLEAN_EXPRESSION_COMPLEXITY() {

    @Override
    public String getKey() {
      return "Boolean expression complexity";
    }

    @Override
    public String getColorHexCode() {
      return "#9999FF";
    }

  },

  CLASS_DATA_ABSTRACTION_COUPLING() {

    @Override
    public String getKey() {
      return "Class data abstraction coupling";
    }

    @Override
    public String getColorHexCode() {
      return "#AA4643";
    }
  },

  CLASS_FAN_OUT_COMPLEXITY() {

    @Override
    public String getKey() {
      return "Class fan out complexity";
    }

    @Override
    public String getColorHexCode() {
      return "#89A54E";
    }
  },

  CYCLOMATIC_COMPLEXITY() {

    @Override
    public String getKey() {
      return "Cyclomatic complexity";
    }

    @Override
    public String getColorHexCode() {
      return "#71588F";
    }
  },

  FILE_LENGTH() {

    @Override
    public String getKey() {
      return "File length";
    }

    @Override
    public String getColorHexCode() {
      return "#4198AF";
    }
  },

  METHOD_LENGTH() {

    @Override
    public String getKey() {
      return "Method length";
    }

    @Override
    public String getColorHexCode() {
      return "#DB843D";
    }
  },

  NESTED_IF_DEPTH() {

    @Override
    public String getKey() {
      return "Nested if depth";
    }

    @Override
    public String getColorHexCode() {
      return "#93A9CF";
    }
  },

  NESTED_TRY_DEPTH() {

    @Override
    public String getKey() {
      return "Nested try depth";
    }

    @Override
    public String getColorHexCode() {
      return "#BB9C68";
    }
  },

  ANON_INNER_LENGTH() {

    @Override
    public String getKey() {
      return "Anon inner length";
    }

    @Override
    public String getColorHexCode() {
      return "#D19392";
    }
  },

  PARAMETER_NUMBER() {

    @Override
    public String getKey() {
      return "Parameter number";
    }

    @Override
    public String getColorHexCode() {
      return "#B9CD96";
    }
  },

  MISSING_SWITCH_DEFAULT() {

    @Override
    public String getKey() {
      return "Missing switch default";
    }

    @Override
    public String getColorHexCode() {
      return "#A99BBD";
    }
  };

  public abstract String getKey();

  public abstract String getColorHexCode();

  public static final DebtType getDebtTypeByKey(String key) {
    for (DebtType debt : DebtType.values()) {
      if (debt.getKey().equals(key)) {
        return debt;
      }
    }

    return null;
  }
}
