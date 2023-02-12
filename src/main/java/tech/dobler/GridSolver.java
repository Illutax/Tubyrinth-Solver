package tech.dobler;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class GridSolver {
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(GridSolver.class.getName());

    private GridSolver() {
    }

    public static Optional<Grid> solve(Grid grid, Piece piece) {
        final var ends = grid.openEnds();
        Supplier<Grid> gSupplier = () -> new Grid(grid);
        for (Position end : ends) {
            final var g = trySolveForPosition(gSupplier, end, piece);
            if (g != null) return Optional.of(g);
        }

        return Optional.empty();
    }

    private static Grid trySolveForPosition(Supplier<Grid> gSup, Position position, Piece piece) {
        boolean success = true;
        var g = gSup.get();
        var p = piece;
        for (int r = 0; r < 3; r++, p = p.rotate()) {
            for (int f = 0; f < 2; f++) {
                if (f > 0) p = p.flipped();
                final var cells = p.cells();
                for (int y = 0; y < cells.length; y++) {
                    final var row = cells[y];
                    for (int x = 0; x < row.length; x++) {
                        if (!g.add(position.plus(new Position(x, y)), row[x])) {
                            success = false;
                            break;
                        }
                    }
                }
                final var result = g.prettyPrint();
                if (!success) {
                    LOGGER.warning(() -> "FUCK%n%s".formatted(result));
                    g = gSup.get();
                    success = true;
                } else {
                    LOGGER.info(() -> "FUCK YEA!%n%s".formatted(result));
                    return g;
                }
            }
        }
        return null;
    }
}
