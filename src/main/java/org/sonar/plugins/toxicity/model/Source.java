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

import com.google.common.base.Preconditions;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.w3c.dom.NodeList;

import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ccoca
 *
 */
public class Source implements ToxicityNode, Comparable<Source> {

    /**
     *
     */
    private static final String NAME_ATTR = "name";
    private static final String TOTAL_ATTR = "total";
    public static final String NODE_NAME = "source";

    private List<Debt> debts;
    private String name;

    Source() {
        this("Unknown");
    }

    public Source(String name) {
        super();

        Preconditions.checkNotNull(name);

        this.name = name;
        this.debts = new ArrayList<Debt>();
    }

    /* (non-Javadoc)
     * @see org.sonar.plugins.toxicity.model.Node#convertToXml()
     */
    public Node convertToXml(Document xml) {

        Preconditions.checkNotNull(xml);

        Element node = xml.createElement(NODE_NAME);
        node.setAttribute(NAME_ATTR, getName());
        node.setAttribute(TOTAL_ATTR, getTotal().toPlainString());

        Collections.sort(debts);
        for (Debt debt : debts) {
            node.appendChild(debt.convertToXml(xml));
        }

        return node;
    }

    /* (non-Javadoc)
     * @see org.sonar.plugins.toxicity.model.ToxicityNode#readFromXml(org.w3c.dom.Node)
     */
    public void readFromXml(Node node) {

        Preconditions.checkNotNull(node);

        name = node.getAttributes().getNamedItem(NAME_ATTR).getNodeValue();

        debts.clear();

        NodeList nodes = node.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if(Debt.NODE_NAME.equals(n.getNodeName())) {
                Debt debt = new Debt();
                debt.readFromXml(n);
                addDebt(debt);
            }
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Source o) {

        if(o == null) {
            return 1;
        }

        return o.getTotal().compareTo(getTotal());
    }

    public void addDebt(Debt debt) {

        Preconditions.checkNotNull(debt);

        debts.add(debt);
    }

    public List<Debt> getDebts() {
        return Collections.unmodifiableList(debts);
    }

    public BigDecimal getTotal() {

        BigDecimal total = BigDecimal.ZERO;
        for (Debt debt : debts) {
            total = total.add(debt.getCost(), MathContext.DECIMAL32);
        }
        return total;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this).append("total", getTotal())
                .append(NAME_ATTR, name).append("debts", debts).toString();
    }

}
