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

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.toxicity.model.Toxicity;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author ccoca
 *
 */
public final class ToxicityXmlBuilder {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ToxicityXmlBuilder.class);

  /**
   * Avoid accidentally instantiation.
   */
  private ToxicityXmlBuilder() {
    throw new AssertionError();
  }

  public static byte[] convertToxicityToXml(Toxicity toxicity) {

    Preconditions.checkNotNull(toxicity);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document xml = builder.newDocument();

      xml.appendChild(toxicity.convertToXml(xml));
      writeXml(xml, baos);

    } catch (ParserConfigurationException e) {
      LOGGER.error(e.getMessage(), e);
    }

    return baos.toByteArray();
  }

  private static void writeXml(Document xml, OutputStream out) {

    try {

      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();

      transformer.transform(new DOMSource(xml), new StreamResult(out));

    } catch (TransformerConfigurationException e) {
      LOGGER.error(e.getMessage(), e);
    } catch (TransformerException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
