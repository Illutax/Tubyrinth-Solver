package tech.dobler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Test
    void emptyPiece() {
        assertThat(new Piece(new Cell[0][0])).hasToString("[]");
    }

    @Nested
    class VFlipped {
        @Test
        void upIsDown() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.UP)}});
            assertThat(piece).hasToString("[[^]]");
            assertThat(piece.vflipped()).hasToString("[[v]]");
        }

        @Test
        void rightIsRight() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.RIGHT)}});
            assertThat(piece).hasToString("[[>]]");
            assertThat(piece.vflipped()).hasToString("[[>]]");
        }

        @Test
        void upUpDownIsDownUpUp() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.UP)}, {Cell.ofType(CellType.UP)}, {Cell.ofType(CellType.DOWN)},});
            assertThat(piece).hasToString("""
                    [\
                    [^], \
                    [^], \
                    [v]]\
                    """);
            assertThat(piece.vflipped()).hasToString("""
                    [\
                    [^], \
                    [v], \
                    [v]]\
                    """);
        }

        @Test
        @Real
        void longPipe() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.UP), Cell.ofType(CellType.DOWN), Cell.ofType(CellType.UP), Cell.ofType(CellType.RIGHT)}});
            assertThat(piece).hasToString("[[^, v, ^, >]]");
            assertThat(piece.vflipped()).hasToString("[[v, ^, v, >]]");
        }

        @Test
        @Real
        void rPipe() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.LEFT), Cell.ofType(CellType.RIGHT)}, {Cell.EMPTY, Cell.ofType(CellType.LEFT)}});
            assertThat(piece).hasToString("[[<, >], [□, <]]");
            assertThat(piece.vflipped()).hasToString("[[□, <], [<, >]]");
        }
    }

    @Nested
    @Disabled
    class HFlipped {

        @Test
        void upIsUp() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.UP)}});
            assertThat(piece).hasToString("[[^]]");
            assertThat(piece.hflipped()).hasToString("[[^]]");
        }

        @Test
        @Real
        void shortPipe() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.UP)}, {Cell.ofType(CellType.LEFT)}, {Cell.ofType(CellType.RIGHT)}});
            assertThat(piece).hasToString("[[^], [<], [>]]");
            assertThat(piece.hflipped()).hasToString("[[^], [<], [>]]");
        }
    }

    @Test
    void oneShortPipe() {
        assertThat(new Piece(new Cell[][]{new Cell[]{Cell.ofType(CellType.LEFT), Cell.ofType(CellType.RIGHT)}})).hasToString("[[<, >]]");
    }

    @Test
    void longPipe() {
        assertThat(new Piece(new Cell[][]{new Cell[]{Cell.ofType(CellType.UP), Cell.ofType(CellType.DOWN), Cell.ofType(CellType.UP), Cell.ofType(CellType.RIGHT)}})).hasToString("[[^, v, ^, >]]");
    }

    @Test
    @Real
    void shortPipe() {
        final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.UP)}, {Cell.ofType(CellType.LEFT)}, {Cell.ofType(CellType.RIGHT)}});
        assertThat(piece).hasToString("[[^], [<], [>]]");
    }

    @Test
    @Real
    void z() {
        final var piece = new Piece(new Cell[][]{
                {Cell.EMPTY,                    Cell.ofType(CellType.OBSTACLE, "z"), Cell.ofType(CellType.UP)},
                {Cell.EMPTY,                    Cell.ofType(CellType.OBSTACLE, "z"), Cell.EMPTY},
                {Cell.ofType(CellType.RIGHT),   Cell.ofType(CellType.DOWN),                     Cell.EMPTY}
        });
        assertThat(piece).hasToString("[[□, x, ^], [□, x, □], [>, v, □]]");
    }
}

@interface Real {
}
