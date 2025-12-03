package org.unpar.project;

import org.junit.jupiter.api.Test;
import org.unpar.project.service.BimbinganService;

import static org.junit.jupiter.api.Assertions.*;

public class BimbinganServiceTest {
    private final BimbinganService bimbinganService;

    public BimbinganServiceTest() {
        this.bimbinganService = new BimbinganService();
    }

    @Test
    public void testMemenuhiBimbinganSebelumUTS_BelowBoundary() {
        boolean result =  bimbinganService.hasMetMinimumSebelumUTS(1);
        assertFalse(result);
    }

    @Test
    public void testMemenuhiBimbinganSebelumUTS_Boundary() {
        boolean result =  bimbinganService.hasMetMinimumSebelumUTS(2);
        assertTrue(result);
    }

    @Test
    public void testMemenuhiBimbinganSebelumUTS_AboveBoundary() {
        boolean result =  bimbinganService.hasMetMinimumSebelumUTS(3);
        assertTrue(result);
    }

    @Test
    public void testMemenuhiBimbinganSetelahUTS_BelowBoundary() {
        boolean result =  bimbinganService.hasMetMinimumSetelahUTS(1);
        assertFalse(result);
    }

    @Test
    public void testMemenuhiBimbinganSetelahUTS_Boundary() {
        boolean result =  bimbinganService.hasMetMinimumSetelahUTS(2);
        assertTrue(result);
    }

    @Test
    public void testMemenuhiBimbinganSetelahUTS_AboveBoundary() {
        boolean result =  bimbinganService.hasMetMinimumSetelahUTS(3);
        assertTrue(result);
    }
}
