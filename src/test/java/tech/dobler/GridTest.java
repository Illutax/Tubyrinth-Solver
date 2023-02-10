package tech.dobler;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class GridTest {

    @Test
    void equals() {
        assertThat(new Grid()).isEqualTo(new Grid());
    }

    @Test
    void cloneTest() {
        final var grid = new Grid();
        grid.put(new Position(0, 1), Cell.ofType(CellType.RIGHT));
        grid.put(new Position(9, 5), Cell.ofType(CellType.LEFT));

        assertThat(grid).isEqualTo(new Grid(grid));
    }

    @Test
    void ctor_empty_grid() {
        // Act
        var grid = new Grid();

        // Assert
        assertThat(grid).hasToString("""
                [\
                [x, x, x, x, x, x, x, x, x, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, x, x, x, x, x, x, x, x, x]]\
                """);
    }

    @Test
    void ctorWithAlteredBorders() {
        // Arrange
        Grid grid = gridWithTwoFaucets();

        // Assert
        assertThat(grid).hasToString("""
                [\
                [x, x, x, x, x, x, x, x, x, x], \
                [>, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, <], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, x, x, x, x, x, x, x, x, x]]\
                """);
    }

    @Test
    void openEnds() {
        // Arrange
        Grid grid = gridWithTwoFaucets();
        grid.put(new Position(3,3), Cell.ofType(CellType.HORIZONTAL));

        grid.put(new Position(5,5), Cell.ofType(CellType.VERTICAL));

        // Assert
        assertThat(grid.openEnds()).containsExactly(new Position(1,1), new Position(2,3), new Position(4,3), new Position(5,4), new Position(8, 5), new Position(5,6));
    }

    @Test
    void canAddPiece() {
        // Arrange
        var grid = new Grid();
        grid.put(new Position(0, 1), Cell.ofType(CellType.RIGHT));

        // Act
        assertThat(grid.add(new Position(1,1), Cell.ofType(CellType.HORIZONTAL))).isTrue();
        assertThat(grid.add(new Position(1,1), Cell.ofType(CellType.LEFT))).isFalse();
        grid.put(new Position(1,1), Cell.EMPTY);
        assertThat(grid.add(new Position(1,1), Cell.ofType(CellType.LEFT))).isTrue();

        // Assert
        assertThat(grid).hasToString("""
                [\
                [x, x, x, x, x, x, x, x, x, x], \
                [>, <, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, □, □, □, □, □, □, □, □, x], \
                [x, x, x, x, x, x, x, x, x, x]]\
                """);
    }

    @Test
    void rndGrid() {
        // Act
        final var grid = new Grid();
        final var rnd = new Random(0);
        final Supplier<Integer> rndCoordSupplier = () -> rnd.nextInt(1, 9);
        final Supplier<Cell> rndCellSupplier = () -> Cell.ofType(CellType.values()[rnd.nextInt(6)]);
        var i = 0;
        while (i < 57) {
            final var pos = new Position(rndCoordSupplier.get(), rndCoordSupplier.get());
            final var b = grid.add(pos, rndCellSupplier.get());
            if (b)
            {
                i++;
                System.out.println(i);
            }
        }

        // Assert
        assertThat(grid).hasToString(
                """
                        [\
                        [x, x, x, x, x, x, x, x, x, x], \
                        [x, >, <, □, >, ↔, <, >, <, x], \
                        [x, >, □, >, <, >, <, >, <, x], \
                        [x, v, ↕, >, ↔, ↔, ↔, ↔, <, x], \
                        [x, ^, ^, >, □, >, ↔, □, <, x], \
                        [x, >, ↔, <, ^, v, □, ↕, v, x], \
                        [x, v, v, >, ↔, □, >, □, ^, x], \
                        [x, ↕, ↕, v, >, <, >, ↔, <, x], \
                        [x, ^, ^, ^, >, ↔, ↔, ↔, <, x], \
                        [x, x, x, x, x, x, x, x, x, x]]\
                        """);
    }

    private static Grid gridWithTwoFaucets() {
        var grid = new Grid();
        grid.put(new Position(0, 1), Cell.ofType(CellType.RIGHT));
        grid.put(new Position(9, 5), Cell.ofType(CellType.LEFT));
        return grid;
    }
}
