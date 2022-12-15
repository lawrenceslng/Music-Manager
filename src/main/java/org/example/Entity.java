package org.example;

import java.util.Date;

/**
 * This is the overarching class representing all the entities that exist in the library, including songs, artists, and albums.
 */
public class Entity {
    protected String name;
    protected static int counter = 0;
    protected int entityID;
    protected Date dateCreated;

    /**
     * Class Constructor
     */
    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    /**
     * Class Constructor overloaded with name
     *
     * @param name a String representing the name of this Entity to be created
     */
    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    /**
     * (UNUSED) Checks whether this entity is equal to another entity based on entity ID
     *
     * @param otherEntity an Entity object to be compared against this Entity
     * @return a boolean value indicating whether the two Entity objects are equal
     */
    public boolean equals(Entity otherEntity) {
        return entityID == otherEntity.entityID;
    }

    /**
     * (UNUSED) Returns the date when the Entity was created
     *
     * @return a Date object representing the date the Entity was created
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * (UNUSED) Sets the date when the Entity was created
     *
     * @param dateCreated a Date object representing the date the Entity is created
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Returns the name of the Entity
     *
     * @return a String representing the name of the Entity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Entity
     *
     * @param name a String representing the name to be assigned to the Entity
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a String providing name and entity ID of the Entity
     *
     * @return a String containing the name and entity ID of this Entity
     */
    public String toString() {
        return "Name: " + this.name + " Entity ID: " + this.entityID;
    }

    /**
     * Returns an xml formatted string containing this Entity's information
     *
     * @return a String object containing the XML output
     */
    public String toXML() {
        return "<entity><name>" + this.name + "</name><ID> " + this.entityID + "</ID></entity>";
    }
}
