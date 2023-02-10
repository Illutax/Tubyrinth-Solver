package tech.dobler;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Grid {

    private static final int SIZE = 10;
    private final Cell[][] gridInternal;

    public Grid(Grid grid) {
        gridInternal = Arrays.stream(grid.gridInternal)
                .map(Cell[]::clone)
                .toArray($ -> grid.gridInternal.clone());
    }

    public Grid() {
        gridInternal = new Cell[SIZE][SIZE];
        for (int i = 0; i < gridInternal.length; i++) {
            Cell[] cells = gridInternal[i];
            for (int j = 0; j < cells.length; j++) {
                final Cell c;
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    c = Cell.makeObstacle();
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

    public boolean add(Position position, Cell cell) {
        final var y = position.y();
        final var x = position.x();
        if (gridInternal[y][x] != Cell.EMPTY) return false;
        if (!Cell.connectsTo(gridInternal[y - 1][x], cell, true)) return false;
        if (!Cell.connectsTo(cell, gridInternal[y + 1][x], true)) return false;
        if (!Cell.connectsTo(gridInternal[y][x - 1], cell, false)) return false;
        if (!Cell.connectsTo(cell, gridInternal[y][x + 1], false)) return false;
        put(cell, y, x);
        return true;
    }

    private void put(Cell cell, int y, int x) {
        gridInternal[y][x] = cell;
    }

    public void put(Position pos, Cell cell) {
        gridInternal[pos.y()][pos.x()] = cell;
    }

    public List<Position> openEnds() {
        final var res = new LinkedHashSet<Position>();
        for (int y = 1; y < gridInternal.length - 1; y++) {
            Cell[] cells = gridInternal[y];
            for (int x = 1; x < cells.length - 1; x++) {
                final Cell c = cells[x];
                if (!Cell.EMPTY.equals(c)) continue;

                var b = EnumSet.of(CellType.HORIZONTAL, CellType.RIGHT).contains(cells[x - 1].type());
                b = b || EnumSet.of(CellType.HORIZONTAL, CellType.LEFT).contains(cells[x + 1].type());
                b = b || EnumSet.of(CellType.VERTICAL, CellType.UP).contains(gridInternal[y + 1][x].type());
                b = b || EnumSet.of(CellType.VERTICAL, CellType.DOWN).contains(gridInternal[y - 1][x].type());
                if (b) res.add(new Position(x, y));
            }
        }
        return new ArrayList<>(res);
    }
}
