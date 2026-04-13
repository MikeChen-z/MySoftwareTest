package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        // Remove the following placeholder:
        assertThat(unit).isNotNull();
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Tests that unit indeed has target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        // 创建实体类Square
        Square square = new BasicSquare();
        // 用unit占用square
        unit.occupy(square);
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit);
    }

    /**
     * Tests that when a unit reoccupies a different square,
     * it leaves the old square and occupies the new one.
     */
    @Test
    void testReoccupy() {
        // 创建两个实体类Square
        Square firstSquare = new BasicSquare();
        Square secondSquare = new BasicSquare();
        // unit两次占用不同的square
        unit.occupy(firstSquare);
        unit.occupy(secondSquare);
        // 判断unit的占用情况是否正确
        assertThat(unit.getSquare()).isEqualTo(secondSquare);
        assertThat(firstSquare.getOccupants()).doesNotContain(unit);
        assertThat(secondSquare.getOccupants()).contains(unit);
    }

}
