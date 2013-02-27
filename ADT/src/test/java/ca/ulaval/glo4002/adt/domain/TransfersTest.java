package ca.ulaval.glo4002.adt.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ca.ulaval.glo4002.adt.domain.Departement;
import ca.ulaval.glo4002.adt.domain.Transfers;

public class TransfersTest {

    @Test
    public void peutCreerUnTransfereAvecUneDateEtUnDepartement() {
        Date date = Calendar.getInstance().getTime();
        Departement departement = mock(Departement.class);

        Transfers transfers = new Transfers(date, departement);

        assertEquals(date, transfers.getDate());
        assertSame(departement, transfers.getDepartement());
    }
}
