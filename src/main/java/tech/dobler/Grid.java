package tech.dobler;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Grid {

    private static final int SIZE = 10;
    private final Cell[][] gridInternal = new Cell[SIZE][SIZE];

    public Grid() {
        for (int i = 0; i < gridInternal.length; i++) {
            Cell[] cells = gridInternal[i];
            for (int j = 0; j < cells.length; j++) {
                final Cell c;
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    c = Cell.makeBorder();
                } else {
                    c = Cell.makeEmpty();
                }
                cells[j] = c;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grid grid)) return false;

        return Arrays.deepEquals(gridInternal, grid.gridInternal);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(gridInternal);
    }

    @Override
    public String toString() {
        return Stream.of(gridInternal)
                .map(Arrays::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public void add(Position position, Cell cell) {
        gridInternal[position.y()][position.x()] = cell;
    }
}
