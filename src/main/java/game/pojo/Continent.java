package game.pojo;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

/**
 * Continent is a POJO representing the Continent in a map
 *
 * @author Shanmukha
 */
@Builder
public class Continent implements Serializable {

    /** The unique identifier of the continent. */
    private int d_id;

    /** The name of the continent */
    private String d_name;

    /** The bonus that the player controlling this continent receives */
    private int d_bonus;

    /** Constructor without arguments for Continent */
    public Continent() {}

    /**
     * Constructor with all arguments for Continent
     *
     * @param p_id id of the continent
     * @param p_name name of the continent
     * @param p_bonus bonus that the player controlling this continent gets
     */
    public Continent(int p_id, String p_name, int p_bonus) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_bonus = p_bonus;
    }

    /**
     * Getter for continent id
     *
     * @return ID of the continent
     */
    public int getD_id() {
        return d_id;
    }

    /**
     * Getter for continent name
     *
     * @return name of the continent
     */
    public String getD_name() {
        return d_name;
    }

    /**
     * Getter for bonus
     *
     * @return bonus that the player gets for this continent
     */
    public int getD_bonus() {
        return d_bonus;
    }

    /**
     * Equals method to check the equality between two continent objects
     *
     * @param p_other the object to which this object is compared
     * @return true if the contents of the objects are equal
     */
    @Override
    public boolean equals(Object p_other) {

        if (p_other == this) {
            return true;
        }

        if (!(p_other instanceof Continent)) {
            return false;
        }

        Continent l_otherContinent = (Continent) p_other;

        return Objects.equals(l_otherContinent.d_id, this.d_id)
                && Objects.equals(l_otherContinent.d_name, this.d_name)
                && Objects.equals(l_otherContinent.d_bonus, this.d_bonus);
    }

    /**
     * Gives the hashcode of a continent object
     *
     * @return hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_id, d_name, d_bonus);
    }
}
