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

import org.w3c.dom.Element;

import org.w3c.dom.Document;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author ccoca
 *
 */
public class Debt implements ToxicityNode, Comparable<Debt> {

  private static final String COST_ATTR = "cost";
  private static final String KEY_ATTR = "key";
  private static final String COLOR_ATTR = "color";

  public static final String NODE_NAME = "debt";

  private BigDecimal cost;
  private DebtType debtType;

  Debt() {
    this.cost = BigDecimal.ZERO;
  }

  public Debt(DebtType debtType) {

    this();

    Preconditions.checkNotNull(debtType);
    this.debtType = debtType;
  }

  private BigDecimal normalize(BigDecimal in) {
    return in.setScale(2, RoundingMode.HALF_UP);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.sonar.plugins.toxicity.model.Node#convertToXml()
   */
  public Node convertToXml(Document xml) {

    Preconditions.checkNotNull(xml);

    Element node = xml.createElement(NODE_NAME);
    node.setAttribute(COST_ATTR, getCost().toPlainString());
    node.setAttribute(KEY_ATTR, getDebtType().getKey());
    node.setAttribute(COLOR_ATTR, getDebtType().getColorHexCode());

    return node;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public DebtType getDebtType() {
    return debtType;
  }

  public void addCost(BigDecimal value) {

    Preconditions.checkNotNull(value);

    cost = normalize(cost.add(value, MathContext.DECIMAL32));
  }

  @Override
  public int hashCode() {

    return Objects.hashCode(cost, debtType);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    Debt other = (Debt) obj;

    return Objects.equal(cost, other.cost)
        && Objects.equal(debtType, other.debtType);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Debt o) {

    if (o == null) {
      return 1;
    }

    return o.getCost().compareTo(cost);
  }

  @Override
  public String toString() {
    return "Debt{" +
        "cost=" + cost +
        ", debtType=" + debtType +
        '}';
  }
}
