package tech.dobler;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class GridTest {

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

}
