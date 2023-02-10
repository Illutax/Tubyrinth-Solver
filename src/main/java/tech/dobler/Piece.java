package tech.dobler;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Piece(Cell[][] cells) {
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
