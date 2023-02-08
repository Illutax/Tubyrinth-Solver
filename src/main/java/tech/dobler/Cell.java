package tech.dobler;

public final class Cell {

    private static final Cell EMPTY = new Cell();
    private static final Cell OBSTACLE = new Cell(CellType.OBSTACLE);

    private final CellType type;

    private Cell(CellType type) {
        this.type = type;
    }

    private Cell() {
        this(null);
    }

    public static boolean connectsTo(Cell aboveOrLeft, Cell rightOrBelow, boolean isVertical) {
        if ((aboveOrLeft.type == null) ^ (rightOrBelow.type == null)) return false;
        if (aboveOrLeft.type == null) return true;

        return switch (aboveOrLeft.type)
        {
            case UP -> CellType.DOWN.equals(rightOrBelow.type);
            case RIGHT -> CellType.LEFT.equals(rightOrBelow.type);
            case DOWN -> CellType.UP.equals(rightOrBelow.type);
            case LEFT -> CellType.RIGHT.equals(rightOrBelow.type);
            case OBSTACLE -> isVertical ? !CellType.UP.equals(rightOrBelow.type) : !CellType.LEFT.equals(rightOrBelow.type);
        };
    }

    public static Cell ofType(CellType type) {
        if (CellType.OBSTACLE.equals(type)) return makeBorder();
        return new Cell(type);
    }

    public static Cell makeEmpty() {
        return EMPTY;
    }

    public static Cell makeBorder() {
        return OBSTACLE;
    }

    @Override
    public String toString() {
        if (type == null) return "□";

        return switch (type) {
            case OBSTACLE -> "x";
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
}
