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

import org.sonar.plugins.toxicity.model.DebtType;

/**
 * @author ccoca
 *
 */
public final class DebtProcessor {

  private String key;
  private DebtCostProcessor costProcessor;
  private DebtType type;

  DebtProcessor(String key, DebtCostProcessor costProcessor, DebtType type) {
    super();
    this.key = key;
    this.costProcessor = costProcessor;
    this.type = type;
  }

  public String getKey() {
    return key;
  }

  public DebtCostProcessor getCostProcessor() {
    return costProcessor;
  }

  public DebtType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "DebtProcessor{" +
        "key='" + key + '\'' +
        ", costProcessor=" + costProcessor +
        ", type=" + type +
        '}';
  }
}
