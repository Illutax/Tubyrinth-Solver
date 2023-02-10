package tech.dobler;

import java.util.EnumSet;
import java.util.Objects;

public final class Cell {
    public static final Cell EMPTY = new Cell(CellType.EMPTY);
    public static final Cell OBSTACLE = new Cell(CellType.OBSTACLE);

    private final CellType type;
    private final String label;

    private Cell(CellType type) {
        this(type, "");
    }

    private Cell(CellType type, String label) {
        this.type = type;
        this.label = label;
    }

    public static boolean connectsTo(Cell aboveOrLeft, Cell rightOrBelow, boolean isVertical) {
        return switch (aboveOrLeft.type) {
            case EMPTY -> true;
            case VERTICAL -> isVertical
                    ? EnumSet.of(CellType.VERTICAL, CellType.EMPTY, CellType.UP).contains(rightOrBelow.type)
                    : !EnumSet.of(CellType.LEFT, CellType.HORIZONTAL).contains(rightOrBelow.type);
            case HORIZONTAL -> isVertical
                    ? !EnumSet.of(CellType.UP, CellType.VERTICAL).contains(rightOrBelow.type)
                    : EnumSet.of(CellType.EMPTY, CellType.LEFT, CellType.HORIZONTAL).contains(rightOrBelow.type);
            case UP, LEFT, OBSTACLE -> isVertical
                    ? !EnumSet.of(CellType.UP, CellType.VERTICAL).contains(rightOrBelow.type)
                    : !EnumSet.of(CellType.LEFT, CellType.HORIZONTAL).contains(rightOrBelow.type);
            case RIGHT -> isVertical
                    ? !EnumSet.of(CellType.VERTICAL, CellType.UP).contains(rightOrBelow.type)
                    : EnumSet.of(CellType.EMPTY, CellType.LEFT, CellType.HORIZONTAL).contains(rightOrBelow.type);
            case DOWN -> isVertical
                    ? EnumSet.of(CellType.VERTICAL, CellType.EMPTY, CellType.UP).contains(rightOrBelow.type)
                    : !EnumSet.of(CellType.LEFT, CellType.HORIZONTAL).contains(rightOrBelow.type);
        };
    }

    public static Cell ofType(CellType type) {
        return ofType(type, "");
    }

    public static Cell ofType(CellType type, String label) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(label);
        if (CellType.OBSTACLE.equals(type) && label.isEmpty()) return makeObstacle();
        return new Cell(type, label);
    }

    public static Cell makeEmpty() {
        return EMPTY;
    }

    public static Cell makeObstacle() {
        return OBSTACLE;
    }

    @Override
    public String toString() {
        return switch (type) {
            case EMPTY -> "□";
            case VERTICAL -> "↕";
            case HORIZONTAL -> "↔";
            case OBSTACLE -> label.isEmpty() ? "x" : label;
            case UP -> "^";
            case RIGHT -> ">";
            case DOWN -> "v";
            case LEFT -> "<";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell cell)) return false;

        return type == cell.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    public Cell vReversed() {
        return switch (type) {
            case EMPTY, RIGHT, OBSTACLE, LEFT, VERTICAL, HORIZONTAL -> this;
            case UP -> Cell.ofType(CellType.DOWN);
            case DOWN -> Cell.ofType(CellType.UP);
        };
    }

    public Cell hReversed() {
        return switch (type) {
            case EMPTY, DOWN, UP, OBSTACLE, VERTICAL, HORIZONTAL -> this;
            case RIGHT -> Cell.ofType(CellType.LEFT);
            case LEFT -> Cell.ofType(CellType.RIGHT);
        };
    }

    public CellType type() {
        return type;
    }
}
