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

package org.sonar.plugins.toxicity.dao;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.MeasureData;
import org.sonar.api.database.model.MeasureModel;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ccoca
 *
 */
public class MeasureDaoTest {

    private MeasureDao dao;
    private DatabaseSession session;
    private Query query;

    @Before
    public void setUp() {
        session = mock(DatabaseSession.class);
        query = mock(Query.class);

        when(session.createQuery(any(String.class))).thenReturn(query);

        dao = new MeasureDao(session);
    }

    @After
    public void tearDown() {
        session = null;
        query = null;
        dao = null;
    }

    @Test
    public void givenValidIdWhenInvokeGetMeasureDataByIdThenMeasureDataIsLoaded() {

        String id = "1";

        MeasureModel model = mock(MeasureModel.class);
        MeasureData data = mock(MeasureData.class);

        when(query.getSingleResult()).thenReturn(model);
        when(model.getMeasureData()).thenReturn(data);

        dao.getMeasureDataById(id);

        verify(session, times(1)).start();
        verify(session, times(1)).createQuery(any(String.class));
        verify(session, times(1)).stop();

        verify(query, times(1)).setParameter(1, Long.valueOf(id));
    }

    @Test
    public void givenNoResultWhenInvokeGetMeasureDataByIdThenNullIsReturned() {

        when(query.getSingleResult()).thenThrow(new NoResultException());
        Assert.assertNull(dao.getMeasureDataById("1"));
    }

    @Test
    public void givenNonUniqueResultWhenInvokeGetMeasureDataByIdThenNullIsReturned() {

        when(query.getSingleResult()).thenThrow(new NonUniqueResultException());
        Assert.assertNull(dao.getMeasureDataById("1"));
    }
}
