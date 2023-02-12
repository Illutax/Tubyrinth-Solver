package tech.dobler;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GridSolverTest {

    @Test
    void simple_onePiece_needsOneRotate() {
        // Arrange
        final var grid = new Grid(4);
        grid.put(new Position(1, 0), Cell.ofType(CellType.DOWN));
        final var p = new Piece(new Cell[][]{{Cell.ofType(CellType.LEFT), Cell.OBSTACLE}});

        // Act
        final var solution = GridSolver.solve(grid, p);

        // Assert
        assertThat(solution).map(Grid::prettyPrint)
                .isPresent().get()
                .isEqualTo("""
                [x, v, x, x]
                [x, ^, □, x]
                [x, x, □, x]
                [x, x, x, x]\
                """);
    }

    @Test
    void simple_onePiece_needsRotateAndFlip() {
        // Arrange
        final var grid = new Grid(4);
        grid.put(new Position(2, 0), Cell.ofType(CellType.DOWN));
        grid.put(new Position(0, 1), Cell.ofType(CellType.RIGHT));
        grid.put(new Position(0, 2), Cell.ofType(CellType.RIGHT));
        final var p = new Piece(new Cell[][]{{Cell.ofType(CellType.UP), Cell.ofType(CellType.UP)},{Cell.ofType(CellType.LEFT), Cell.EMPTY}});
        System.out.println(grid.prettyPrint());

        // Act
        final var solution = GridSolver.solve(grid, p);

        // Assert
        assertThat(solution).map(Grid::prettyPrint)
                .isPresent().get()
                .isEqualTo("""
                [x, x, v, x]
                [>, <, ^, x]
                [>, <, □, x]
                [x, x, x, x]\
                """);
    }
}
