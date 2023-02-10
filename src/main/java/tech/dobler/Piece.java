package tech.dobler;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Piece(Cell[][] cells) {
    public static final Piece Z = new Piece(new Cell[][]{
            {Cell.EMPTY, Cell.ofType(CellType.OBSTACLE, "z"), Cell.ofType(CellType.UP)},
            {Cell.EMPTY, Cell.ofType(CellType.OBSTACLE, "z"), Cell.EMPTY},
            {Cell.ofType(CellType.RIGHT), Cell.ofType(CellType.DOWN), Cell.EMPTY}
    });
    public static final Piece SHORT = new Piece(new Cell[][]{{Cell.ofType(CellType.UP), Cell.ofType(CellType.LEFT), Cell.ofType(CellType.RIGHT)}});
    public static final Piece LONG = new Piece(new Cell[][]{{Cell.ofType(CellType.UP), Cell.ofType(CellType.DOWN), Cell.ofType(CellType.UP), Cell.ofType(CellType.RIGHT)}});
    public static final Piece R = new Piece(new Cell[][]{{Cell.ofType(CellType.LEFT), Cell.ofType(CellType.RIGHT)}, {Cell.EMPTY, Cell.ofType(CellType.LEFT)}});
    public static final Piece T = new Piece(new Cell[][]{
            {Cell.ofType(CellType.VERTICAL), Cell.ofType(CellType.OBSTACLE, "t"), Cell.ofType(CellType.RIGHT)},
            {Cell.makeEmpty(), Cell.ofType(CellType.RIGHT), Cell.makeEmpty()}
    });
    public static final Piece TWIN = new Piece(new Cell[][]{
            {Cell.ofType(CellType.UP), Cell.ofType(CellType.UP)},
            {Cell.ofType(CellType.DOWN), Cell.ofType(CellType.RIGHT)}
    });
    public static final Piece L = new Piece(new Cell[][]{
            {Cell.makeEmpty(),Cell.makeEmpty(),Cell.ofType(CellType.RIGHT)},
            {Cell.ofType(CellType.UP), Cell.ofType(CellType.DOWN), Cell.ofType(CellType.DOWN)}
    });

    public Piece {
        Objects.requireNonNull(cells);
    }

    @Override
    public String toString() {
        return Stream.of(cells)
                .map(Arrays::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public Piece flipped() {
        return vflipped();
    }

    Piece vflipped() {
        Cell[][] res = new Cell[cells.length][];
        for (int i = cells.length - 1; i >= 0; i--) {
            final var row = cells[i];
            final var k = cells.length - 1 - i;
            res[k] = new Cell[row.length];
            for (int j = 0; j < row.length; j++) {
                res[k][j] = row[j].vReversed();
            }
        }
        return new Piece(res);
    }

    Piece hflipped() {
        throw new UnsupportedOperationException("Not yet implemented and probably never will. :)");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;

        return Arrays.deepEquals(cells, piece.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cells);
    }
}
