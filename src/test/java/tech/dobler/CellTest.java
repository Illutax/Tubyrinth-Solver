package tech.dobler;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class CellTest {

    @Nested
    class Ctor {
        @Test
        void border() {
            assertThat(Cell.makeBorder()).hasToString("x");
        }

        @Test
        void noDir() {
            assertThat(Cell.makeEmpty()).hasToString("□");
        }

        @Test
        void upEdge() {
            assertThat(Cell.ofType(CellType.UP)).hasToString("^");
        }

        @Test
        void downEdge() {
            assertThat(Cell.ofType(CellType.DOWN)).hasToString("v");
        }

        @Test
        void rightEdge() {
            assertThat(Cell.ofType(CellType.RIGHT)).hasToString(">");
        }

        @Test
        void leftEdge() {
            assertThat(Cell.ofType(CellType.LEFT)).hasToString("<");
        }
    }

    @Nested
    class ConnectsTo {

        @Test
        void null2Null() {
            assertThat(Cell.connectsTo(Cell.makeEmpty(), Cell.makeEmpty(), true)).isTrue();
        }

        @ParameterizedTest
        @EnumSource(CellType.class)
        void null2AnyThingElse_isFalse(CellType type) {
            assertThat(Cell.connectsTo(Cell.makeEmpty(), Cell.ofType(type), true)).isFalse();
        }

        @ParameterizedTest
        @EnumSource(CellType.class)
        void anyThingElse2Null_isFalse(CellType type) {
            assertThat(Cell.connectsTo(Cell.ofType(type), Cell.makeEmpty(), true)).isFalse();
        }

        @ParameterizedTest
        @EnumSource(CellType.class)
        void anyThingElse2Itself_isFalse(CellType type) {
            final Cell cell = Cell.ofType(type);
            assertThat(Cell.connectsTo(cell, cell, true)).isEqualTo(CellType.OBSTACLE.equals(type));
        }

        @ParameterizedTest
        @EnumSource(CellType.class)
        void up2Down(CellType type) {
            final Cell cell = Cell.ofType(CellType.UP);
            assertThat(Cell.connectsTo(cell, Cell.ofType(CellType.DOWN), true)).isTrue();
            if (!(CellType.DOWN.equals(type))) {
                assertThat(Cell.connectsTo(cell, Cell.ofType(type), true)).isFalse();
            }
        }

        @ParameterizedTest
        @EnumSource(CellType.class)
        void down2Up(CellType type) {
            final Cell cell = Cell.ofType(CellType.DOWN);
            assertThat(Cell.connectsTo(cell, Cell.ofType(CellType.UP), true)).isTrue();
            if (!(CellType.UP.equals(type))) {
                assertThat(Cell.connectsTo(cell, Cell.ofType(type), true)).isFalse();
            }
        }

        @ParameterizedTest
        @EnumSource(CellType.class)
        void left2Right(CellType type) {
            final Cell cell = Cell.ofType(CellType.LEFT);
            assertThat(Cell.connectsTo(cell, Cell.ofType(CellType.RIGHT), false)).isTrue();
            if (!(CellType.RIGHT.equals(type))) {
                assertThat(Cell.connectsTo(cell, Cell.ofType(type), false)).isFalse();
            }
        }

        @ParameterizedTest
        @EnumSource(CellType.class)
        void right2Left(CellType type) {
            final Cell cell = Cell.ofType(CellType.RIGHT);
            assertThat(Cell.connectsTo(cell, Cell.ofType(CellType.LEFT), false)).isTrue();
            if (!(CellType.LEFT.equals(type))) {
                assertThat(Cell.connectsTo(cell, Cell.ofType(type), false)).isFalse();
            }
        }
    }
}
