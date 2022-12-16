package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    Entity e1, e2;

    @BeforeEach
    void setup() {
        e1 = new Entity();
        e2 = new Entity("Entity 2");
    }

    @Test
    void testEquals() {
        assertTrue(e1.equals(e1));
        assertFalse(e1.equals(e2));
    }

    @Test
    void getDateCreated() {
        Date newDate = new Date();
        e1.dateCreated = newDate;

        assertEquals(e1.getDateCreated(), newDate);
    }

    @Test
    void setDateCreated() {
        Date newDate = new Date();
        e1.setDateCreated(newDate);

        assertEquals(e1.dateCreated, newDate);
    }

    @Test
    void getName() {
        assertTrue(e1.getName().equals(""));
        assertEquals(e2.getName(),"Entity 2");
    }

    @Test
    void setName() {
        assertTrue(e1.getName().equals(""));

        e1.setName("New Name");
        assertEquals(e1.name, "New Name");
    }

    @Test
    void testToString() {
        int e1ID = e1.entityID;
        int e2ID = e2.entityID;
        String e1Query = "Name:  Entity ID: " + e1ID;
        String e2Query = "Name: Entity 2 Entity ID: " + e2ID;

        assertEquals(e1.toString(), e1Query);
        assertEquals(e2.toString(), e2Query);
    }

    @Test
    void toXML() {
        int e1ID = e1.entityID;
        int e2ID = e2.entityID;
        String e1Query = "<entity><name></name><ID> " + e1ID + "</ID></entity>";
        String e2Query = "<entity><name>Entity 2</name><ID> " + e2ID + "</ID></entity>";

        assertEquals(e1.toXML(), e1Query);
        assertEquals(e2.toXML(), e2Query);
    }
}