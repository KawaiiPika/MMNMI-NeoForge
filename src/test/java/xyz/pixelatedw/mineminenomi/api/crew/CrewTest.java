package xyz.pixelatedw.mineminenomi.api.crew;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CrewTest {

    private Crew crew;
    private UUID capId;
    private UUID mem1Id;
    private UUID mem2Id;

    @BeforeEach
    public void setup() {
        capId = UUID.randomUUID();
        mem1Id = UUID.randomUUID();
        mem2Id = UUID.randomUUID();

        // Setup a basic crew with a captain and 2 members
        crew = new Crew("Straw Hats", capId, "Luffy", 123456789L);
        crew.addMember(mem1Id, "Zoro");
        crew.addMember(mem2Id, "Nami");
    }

    @Test
    public void testRemoveMember_NonExistentMember() {
        UUID unknownId = UUID.randomUUID();
        assertEquals(3, crew.getMembers().size());

        // Removing an unknown member shouldn't crash or alter anything
        crew.removeMember(null, unknownId);
        crew.tick();

        assertEquals(3, crew.getMembers().size());
    }

    @Test
    public void testRemoveMember_NormalMember() {
        assertEquals(3, crew.getMembers().size());
        assertTrue(crew.hasMember(mem1Id));

        // Remove normal member
        crew.removeMember(null, mem1Id);

        // After removeMember, they are marked for deletion but not yet removed from list
        assertTrue(crew.hasMember(mem1Id));

        crew.tick();

        // After tick, they should be removed
        assertEquals(2, crew.getMembers().size());
        assertFalse(crew.hasMember(mem1Id));
        assertTrue(crew.hasMember(capId));
        assertTrue(crew.hasMember(mem2Id));

        // Captain should remain the same
        Crew.Member cap = crew.getCaptain();
        assertNotNull(cap);
        assertEquals(capId, cap.getUUID());
    }

    @Test
    public void testRemoveMember_CaptainWithOtherMembers() {
        assertEquals(3, crew.getMembers().size());
        assertTrue(crew.hasMember(capId));

        // Remove the captain
        crew.removeMember(null, capId);

        // Captain is marked for deletion, next available member is assigned captain
        Crew.Member cap = crew.getCaptain();
        assertNotNull(cap);
        assertNotEquals(capId, cap.getUUID());
        assertTrue(cap.getUUID().equals(mem1Id) || cap.getUUID().equals(mem2Id));

        crew.tick();

        // Old captain should be removed
        assertEquals(2, crew.getMembers().size());
        assertFalse(crew.hasMember(capId));
    }

    @Test
    public void testRemoveMember_CaptainWithNoOtherMembers() {
        // Create a crew with only the captain
        Crew soleCrew = new Crew("Lone Wolf", capId, "Mihawk", 123456789L);
        assertEquals(1, soleCrew.getMembers().size());

        // Remove the captain
        soleCrew.removeMember(null, capId);

        soleCrew.tick();

        // Crew should be empty
        assertEquals(0, soleCrew.getMembers().size());
        assertTrue(soleCrew.isEmpty());
        assertNull(soleCrew.getCaptain());
    }
}
