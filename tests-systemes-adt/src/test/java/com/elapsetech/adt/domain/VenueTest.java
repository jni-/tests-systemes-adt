package com.elapsetech.adt.domain;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VenueTest {

    private static final Date DATE = Calendar.getInstance().getTime();

    @Mock
    private Departement departement;

    private Venue venue;

    @Before
    public void setUp() {
        venue = new Venue(DATE, departement);
    }

    @Test
    public void datePasseeAuConstructeurEstConservee() {
        assertEquals(DATE, venue.getDate());
    }

    @Test
    public void departementIdPasseAuConstructeurEstConserve() {
        assertSame(departement, venue.getDepartement());
    }
}
