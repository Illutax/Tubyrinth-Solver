package tech.dobler;

import java.util.Objects;

import static org.valid4j.Assertive.require;

public record Position(int x, int y) {
    public Position {
        require(x >= 0, "x should be positive but was %d", x);
        require(x < 10, "x shouldn't exceed 10 but was %d", x);

        require(y >= 0, "y should be positive but was %d", y);
        require(y < 10, "y shouldn't exceed 10 but was %d", y);
    }

    public Position plus(Position other) {
        Objects.requireNonNull(other);
        return new Position(x + other.x, y + other.y);
    }
}
