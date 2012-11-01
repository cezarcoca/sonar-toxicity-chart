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

import org.sonar.api.resources.Project;

import org.junit.After;

import org.junit.Before;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.Violation;
import org.sonar.plugins.toxicity.debts.ViolationsMapper;
import org.sonar.plugins.toxicity.model.DebtType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ccoca
 *
 */
public class ToxicityChartDecoratorTest {

  private ToxicityChartDecorator decorator;

  @Before
  public void setUp() {
    decorator = new ToxicityChartDecorator();
  }

  @After
  public void tearDown() {
    decorator = null;
  }

  @Test
  public void whenDecorateIsInvokedThenAllViolationsShouldBeProcessed() {

    int count = 10;

    DecoratorContext context = mock(DecoratorContext.class);
    Resource<?> resource = mock(Resource.class);
    configureProject("Java");

    List<Violation> violations = new ArrayList<Violation>();
    for (int i = 0; i < count; i++) {
      violations.add(Violation.create(
          Rule.create().setKey(ViolationsMapper.MISSING_SWITCH_DEFAULT_CHECK_STYLE), resource));
    }

    when(context.getViolations()).thenReturn(violations);
    when(resource.getLongName()).thenReturn("org.sonar.plugins.toxicity");

    decorator.decorate(resource, context);

    verify(resource, times(count)).getLongName();
  }

  @Test
  public void whenExecuteOnIsInvokedThenAllMetricsAreSaved() {

    DecoratorContext context = mock(DecoratorContext.class);

    decorator.saveMeasures(context);

    int measures = DebtType.values().length + 2;

    verify(context, times(measures)).saveMeasure(any(Measure.class));
  }

  @Test
  public void whenShouldExecuteOnProjectIsInvokedAndProjectKeyIsNullThenReturnFalse() {
    assertFalse(decorator.shouldExecuteOnProject(new Project(null)));
  }

  @Test
  public void whenShouldExecuteOnProjectIsInvokedAndProjectKeyIsValidThenReturnTrue() {
    assertTrue(decorator.shouldExecuteOnProject(new Project("Java")));
  }

  @Test
  public void givenPrjectKeyEqualsWithResourceWhenInvokeAllResourcesAreProcessedThenReturnTrue() {

    String key = "Sonar Plugin";

    configureProject(key);
    Resource<?> resource = configureResource(key);

    assertTrue(decorator.allResourcesAreProcessed(resource));
  }

  @Test
  public void givenPrjectKeyNotEqualsWithResourceWhenInvokeAllResourcesAreProcessedThenReturnFalse() {

    configureProject("Project");
    Resource<?> resource = configureResource("Resource");

    assertFalse(decorator.allResourcesAreProcessed(resource));
  }

  private void configureProject(String key) {

    Project project = new Project(key);
    decorator.shouldExecuteOnProject(project);
  }

  private Resource<?> configureResource(String key) {

    return new Project(key);
  }
}
