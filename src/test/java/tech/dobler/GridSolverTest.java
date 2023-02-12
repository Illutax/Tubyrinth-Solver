package tech.dobler;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GridSolverTest {

    @Test
    void simple_onePiece() {
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
}
