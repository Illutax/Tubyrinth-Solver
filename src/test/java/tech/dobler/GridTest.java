package tech.dobler;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

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
        final Supplier<Cell> rndCellSupplier = () -> Cell.ofType(CellType.values()[rnd.nextInt(CellType.values().length)]);
        IntStream.range(0, 30)
                .forEach(__ -> grid.add(
                        new Position(rndCoordSupplier.get(), rndCoordSupplier.get()),
                        rndCellSupplier.get()));

        // Assert
        assertThat(grid).hasToString(
                """
                        [\
                        [x, x, x, x, x, x, x, x, x, x], \
                        [x, <, □, □, x, <, □, v, □, x], \
                        [x, □, □, <, >, □, v, ^, ^, x], \
                        [x, □, □, x, <, □, □, □, □, x], \
                        [x, □, v, □, □, □, v, >, x, x], \
                        [x, x, □, □, □, □, v, >, <, x], \
                        [x, □, □, □, ^, □, □, □, □, x], \
                        [x, □, □, >, □, □, □, <, □, x], \
                        [x, □, □, □, v, □, □, □, □, x], \
                        [x, x, x, x, x, x, x, x, x, x]]\
                        """);
    }
}
