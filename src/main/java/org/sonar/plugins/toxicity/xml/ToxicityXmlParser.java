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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sonar.plugins.toxicity.model.Toxicity;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author ccoca
 *
 */
public final class ToxicityXmlParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToxicityXmlParser.class);

    /**
     * Avoid accidentally instantiation.
     */
    private ToxicityXmlParser() {
        throw new AssertionError();
    }

    /**
     * Parse the XML input and create a new {@link Toxicity} object. If input
     * XML is invalid, an empty {@link Toxicity} is returned.
     *
     * @param xml
     * @return
     */
    public static Toxicity convertXmlToToxicity(@Nullable byte[] xml) {

        Toxicity toxicity = new Toxicity();

        if(xml == null) {
            return toxicity;
        }

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml));

            doc.getDocumentElement().normalize();
            toxicity.readFromXml(doc.getDocumentElement());

        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return toxicity;
    }
}
