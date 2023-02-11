package tech.dobler;

public class Rotate2D {

    private Rotate2D() {}

    public static int[][] rotate(int[][] arr) {
        final var h = arr[0].length;
        final var w = arr.length;
        final var res = new int[h][w];
        for (int y = 0; y < w; y++) {
            for (int x = 0; x < h; x++) {
                res[x][w - 1 - y] = arr[y][x];
            }
        }
        return res;
    }
}
