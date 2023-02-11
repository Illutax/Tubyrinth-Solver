package tech.dobler;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CellTest {

    @Nested
    class ToString {
        @Test
        void border() {
            assertThat(Cell.OBSTACLE).hasToString("x");
        }

        @Test
        void empty() {
            assertThat(Cell.makeEmpty()).hasToString("□");
        }

        @Test
        void emptyWithLabel() {
            assertThat(Cell.ofType(CellType.OBSTACLE, "z")).hasToString("z");
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

        @Test
        void horizontal() {
            assertThat(Cell.ofType(CellType.HORIZONTAL)).hasToString("↔");
        }

        @Test
        void vertical() {
            assertThat(Cell.ofType(CellType.VERTICAL)).hasToString("↕");
        }
    }

    @Nested
    class ConnectsTo {

        @Test
        void anyThingElse2Itself() {
            Cell cell = Cell.ofType(CellType.EMPTY);
            assertThat(Cell.connectsTo(cell, cell, true)).isTrue();
            assertThat(Cell.connectsTo(cell, cell, false)).isTrue();

            cell = Cell.ofType(CellType.OBSTACLE);
            assertThat(Cell.connectsTo(cell, cell, true)).isTrue();
            assertThat(Cell.connectsTo(cell, cell, false)).isTrue();

            cell = Cell.ofType(CellType.UP);
            assertThat(Cell.connectsTo(cell, cell, true)).isFalse();
            assertThat(Cell.connectsTo(cell, cell, false)).isTrue();

            cell = Cell.ofType(CellType.DOWN);
            assertThat(Cell.connectsTo(cell, cell, true)).isFalse();
            assertThat(Cell.connectsTo(cell, cell, false)).isTrue();

            cell = Cell.ofType(CellType.LEFT);
            assertThat(Cell.connectsTo(cell, cell, true)).isTrue();
            assertThat(Cell.connectsTo(cell, cell, false)).isFalse();

            cell = Cell.ofType(CellType.RIGHT);
            assertThat(Cell.connectsTo(cell, cell, true)).isTrue();
            assertThat(Cell.connectsTo(cell, cell, false)).isFalse();

            cell = Cell.ofType(CellType.VERTICAL);
            assertThat(Cell.connectsTo(cell, cell, true)).isTrue();
            assertThat(Cell.connectsTo(cell, cell, false)).isTrue();

            cell = Cell.ofType(CellType.HORIZONTAL);
            assertThat(Cell.connectsTo(cell, cell, true)).isTrue();
            assertThat(Cell.connectsTo(cell, cell, false)).isTrue();
        }

        @Test
        void obstacle2AnyThingElse() {
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.DOWN), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.UP), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.DOWN), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.LEFT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.RIGHT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.HORIZONTAL), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.VERTICAL), false)).isTrue();

            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.UP), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.LEFT), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.RIGHT), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.VERTICAL), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.ofType(CellType.HORIZONTAL), false)).isFalse();

            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.EMPTY, true)).isTrue();
            assertThat(Cell.connectsTo(Cell.makeObstacle(), Cell.EMPTY, false)).isTrue();
        }

        @Test
        void anyThingElse2Obstacle() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.makeObstacle(), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.makeObstacle(), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.makeObstacle(), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.makeObstacle(), false)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.makeObstacle(), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.makeObstacle(), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.makeObstacle(), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.makeObstacle(), true)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.makeObstacle(), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.makeObstacle(), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.makeObstacle(), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.makeObstacle(), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.makeObstacle(), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.makeObstacle(), false)).isTrue();
        }

        @Test
        void empty2AnyThingElse() {
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.UP), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.UP), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.DOWN), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.DOWN), false)).isTrue();

            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.LEFT), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.LEFT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.RIGHT), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.RIGHT), true)).isTrue();

            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.HORIZONTAL), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.HORIZONTAL), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.VERTICAL), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.EMPTY, Cell.ofType(CellType.VERTICAL), true)).isTrue();
        }

        @Test
        void anyThing2Empty() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.EMPTY, true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.EMPTY, false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.EMPTY, true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.EMPTY, false)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.EMPTY, false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.EMPTY, true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.EMPTY, false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.EMPTY, true)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.EMPTY, true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.EMPTY, false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.EMPTY, false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.EMPTY, true)).isTrue();
        }

        @Test
        void up() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.DOWN), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.VERTICAL), true)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.LEFT), false)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.DOWN), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.LEFT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.RIGHT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.RIGHT), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.VERTICAL), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.HORIZONTAL), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.UP), Cell.ofType(CellType.HORIZONTAL), false)).isFalse();
        }

        @Test
        void down() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.UP), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.VERTICAL), true)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.RIGHT), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.LEFT), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.LEFT), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.HORIZONTAL), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.HORIZONTAL), false)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.UP), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.RIGHT), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.DOWN), Cell.ofType(CellType.VERTICAL), false)).isTrue();
        }

        @Test
        void left() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.RIGHT), false)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.HORIZONTAL), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.UP), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.VERTICAL), true)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.RIGHT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.DOWN), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.DOWN), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.UP), false)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.HORIZONTAL), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.LEFT), Cell.ofType(CellType.VERTICAL), false)).isTrue();
        }

        @Test
        void right() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.LEFT), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.HORIZONTAL), false)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.UP), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.VERTICAL), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.VERTICAL), true)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.LEFT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.DOWN), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.DOWN), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.UP), false)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.HORIZONTAL), true)).isTrue();
        }

        @Test
        void horizontal() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.LEFT), false)).isTrue();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.RIGHT), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.UP), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.VERTICAL), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.VERTICAL), true)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.LEFT), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.DOWN), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.DOWN), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.UP), false)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.HORIZONTAL), Cell.ofType(CellType.RIGHT), true)).isTrue();
        }

        @Test
        void vertical() {
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.UP), true)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.DOWN), true)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.RIGHT), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.LEFT), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.LEFT), false)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.HORIZONTAL), true)).isFalse();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.HORIZONTAL), false)).isFalse();

            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.UP), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.RIGHT), false)).isTrue();
            assertThat(Cell.connectsTo(Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.DOWN), false)).isTrue();
        }
    }

    @Nested
    class Rotate {

        @Test
        void doubleEnded() {
            var cell = Cell.ofType(CellType.VERTICAL);
            assertThat(cell.rotate()).isEqualTo(Cell.ofType(CellType.HORIZONTAL));
            assertThat(cell.rotate().rotate()).isEqualTo(Cell.ofType(CellType.VERTICAL));
        }

        @Test
        void singleEnded() {
            var cell = Cell.ofType(CellType.UP);
            assertThat(cell.rotate()).isEqualTo(Cell.ofType(CellType.RIGHT));
            assertThat(cell.rotate().rotate()).isEqualTo(Cell.ofType(CellType.DOWN));
            assertThat(cell.rotate().rotate().rotate()).isEqualTo(Cell.ofType(CellType.LEFT));
            assertThat(cell.rotate().rotate().rotate().rotate()).isEqualTo(Cell.ofType(CellType.UP));
        }

        @Test
        void identity() {
            assertThat(Cell.makeEmpty().rotate()).isEqualTo(Cell.makeEmpty());
            assertThat(Cell.makeObstacle().rotate()).isEqualTo(Cell.makeObstacle());
        }
    }
}
