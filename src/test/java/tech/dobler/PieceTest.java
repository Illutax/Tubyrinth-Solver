package tech.dobler;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Nested
    class ToString {
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
                final var piece = Piece.LONG;
                assertThat(piece).hasToString("[[^, v, ^, >]]");
                assertThat(piece.vflipped()).hasToString("[[v, ^, v, >]]");
            }

            @Test
            @Real
            void rPipe() {
                final var piece = Piece.R;
                assertThat(piece).hasToString("[[<, >], [□, <]]");
                assertThat(piece.vflipped()).hasToString("[[□, <], [<, >]]");
            }
        }

        @Test
        void oneShortPipe() {
            assertThat(new Piece(new Cell[][]{new Cell[]{Cell.ofType(CellType.LEFT), Cell.ofType(CellType.RIGHT)}})).hasToString("[[<, >]]");
        }

        @Test
        @Real
        void shortPipe() {
            assertThat(Piece.SHORT).hasToString("[[<, v, ^]]");
        }

        @Test
        @Real
        void z() {
            assertThat(Piece.Z).hasToString("[[□, z, ^], [□, z, □], [>, v, □]]");
        }

        @Test
        @Real
        void t() {
            assertThat(Piece.T).hasToString("[[↕, t, >], [□, >, □]]");
        }

        @Test
        @Real
        void twin() {
            assertThat(Piece.TWIN).hasToString("[[^, ^], [v, >]]");
        }

        @Test
        @Real
        void l() {
            assertThat(Piece.L).hasToString("[[□, □, >], [^, v, v]]");
        }
    }

    @Nested
    class Rotate {

        @Test
        void single() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.LEFT)}});
            assertThat(piece).hasToString("[[<]]");
            assertThat(piece.rotate()).hasToString("[[^]]");
        }

        @Test
        void doublePiece() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.LEFT), Cell.ofType(CellType.UP)}});
            assertThat(piece).hasToString("[[<, ^]]");
            assertThat(piece.rotate()).hasToString("[[^], [>]]");
        }

        @Test
        void shortPiece() {
            assertThat(Piece.SHORT).hasToString("[[<, v, ^]]");
            assertThat(Piece.SHORT.rotate()).hasToString("""
                    [\
                    [^], \
                    [<], \
                    [>]]\
                    """);
        }
    }

    @Nested
    class OpenEnds {
        @Test
        void single() {
            final var piece = new Piece(new Cell[][]{{Cell.ofType(CellType.LEFT)}});
            assertThat(piece.openEnds()).isEqualTo(List.of(new Position(0,0)));
        }

        @Test
        void tPipe() {
            assertThat(Piece.T.openEnds()).isEqualTo(List.of(new Position(0,0),new Position(2,0),new Position(1,1)));
        }
    }
}
