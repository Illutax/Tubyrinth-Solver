package tech.dobler;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.dobler.Rotate2D.rotate;

class Rotate2DTest {
    @Test
    void cw2() {
        int[][] arr = {{0, 1}};
        int[][] exp = {{0}, {1}};
        assertThat(rotate(arr)).isEqualTo(exp);
    }

    @Test
    void cw4() {
        int[][] arr = {{0, 1}, {2, 3}};
        int[][] exp = {{2, 0}, {3, 1}};
        assertThat(rotate(arr)).isEqualTo(exp);
    }

    @Test
    void cw9() {
        int[][] arr = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[][] exp = {{6, 3, 0}, {7, 4, 1}, {8, 5, 2}};
        assertThat(rotate(arr)).isEqualTo(exp);
    }

}
