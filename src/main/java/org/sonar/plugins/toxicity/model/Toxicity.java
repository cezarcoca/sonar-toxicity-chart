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

import com.google.common.base.Preconditions;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author ccoca
 *
 */
public class Toxicity implements ToxicityNode {

  public static final String NODE_NAME = "toxicity";

  private List<Source> sources;

  public Toxicity() {
    super();
    sources = new ArrayList<Source>();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.sonar.plugins.toxicity.model.Node#convertToXml()
   */
  public Node convertToXml(Document xml) {

    Preconditions.checkNotNull(xml);

    Element node = xml.createElement(NODE_NAME);
    for (Source source : sources) {
      node.appendChild(source.convertToXml(xml));
    }

    return node;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.sonar.plugins.toxicity.model.Node#readFromXml()
   */
  public void readFromXml(Node node) {

    Preconditions.checkNotNull(node);

    sources.clear();

    NodeList nodes = node.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      Node n = nodes.item(i);
      if (Source.NODE_NAME.equals(n.getNodeName())) {
        Source source = new Source();
        source.readFromXml(n);
        sources.add(source);
      }
    }

    Collections.sort(sources);
  }

  public void setSources(Collection<Source> sources) {

    Preconditions.checkNotNull(sources);

    this.sources = new ArrayList<Source>(sources);
    Collections.sort(this.sources);
  }

  /**
   * Returns the list of sources with debts. The list is sorted by total cost of
   * debts.
   *
   * @return
   */
  public List<Source> getSources() {

    return Collections.unmodifiableList(sources);
  }

  /**
   * The list of sources for which the total debt is greater than the provided
   * threshold. The list is sorted by total cost of debts.
   *
   * @return
   */
  public List<Source> getSources(BigDecimal threshold) {

    List<Source> filtered = new ArrayList<Source>();
    for (Source source : sources) {
      if (source.getTotal().compareTo(threshold) < 0) {
        break;
      }
      filtered.add(source);
    }

    return Collections.unmodifiableList(filtered);
  }

  /**
   * Compute the average cost.
   *
   * @return Double - the average computed value or zero if no debts are found.
   */
  public Double getAverageCost() {

    if (sources.isEmpty()) {
      return Double.valueOf(0);
    }

    BigDecimal total = BigDecimal.ZERO;
    for (Source resource : sources) {
      total = total.add(resource.getTotal(), MathContext.DECIMAL32);
    }

    BigDecimal average = total.divide(new BigDecimal(sources.size()),
        MathContext.DECIMAL32);
    average = average.setScale(2, RoundingMode.HALF_UP);

    return average.doubleValue();
  }

  public BigDecimal getTotalCostByDebt(DebtType debtType) {

    BigDecimal total = BigDecimal.ZERO;
    for (Source source : sources) {
      for (Debt debt : source.getDebts()) {
        if (debt.getDebtType() == debtType) {
          total = total.add(debt.getCost(), MathContext.DECIMAL32);
        }
      }
    }

    return total;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("units", sources).toString();
  }

}
