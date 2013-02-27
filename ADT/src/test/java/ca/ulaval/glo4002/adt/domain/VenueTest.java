package ca.ulaval.glo4002.adt.domain;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.adt.domain.Departement;
import ca.ulaval.glo4002.adt.domain.Transfers;
import ca.ulaval.glo4002.adt.domain.Venue;

@RunWith(MockitoJUnitRunner.class)
public class VenueTest {

    private static final Date DATE = Calendar.getInstance().getTime();
    private static final String RAISON_DE_LA_VENUE = "raison";
    private static final Date DATE_DE_TRANSFERS = Calendar.getInstance().getTime();

    @Mock
    private Departement departement;

    private Venue venue;

    @Before
    public void setUp() {
        venue = new Venue(DATE, departement) {
            @Override
            Date obtenirDateCourante() {
                return DATE_DE_TRANSFERS;
            }
        };
    }

    @Test
    public void datePasseeAuConstructeurEstConservee() {
        assertEquals(DATE, venue.getDate());
    }

    @Test
    public void departementIdPasseAuConstructeurEstConserve() {
        assertSame(departement, venue.getDepartement());
    }

    @Test
    public void peutDonnerUneRaisonDeVenue() {
        venue.setRaisonDeVenue(RAISON_DE_LA_VENUE);

        assertEquals(RAISON_DE_LA_VENUE, venue.getRaisonDeVenue());
    }

    @Test
    public void peutAjouterUnTransfers() {
        venue.transfererMaintenantVers(departement);

        Transfers transfers = venue.getTransfers().get(0);

        assertEquals(DATE_DE_TRANSFERS, transfers.getDate());
        assertSame(departement, transfers.getDepartement());
    }
}
