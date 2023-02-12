# Tubyrinth Solver

The project is currently in development.

This is a CLI based solver for the board game [Tubyrinth](https://boardgamegeek.com/boardgame/193161/tubyrinth).

The goal is to supply the given board to the tool via

```shell
$ java -jar solver.jar "xvxxxxxxxxx________xx________xx________xx________xx________xx________xx________xx________xxxxxxxxx^x"
```

where the grid looks like that:
```text
xvxxxxxxxx
x________x
x________x
x________x
x________x
x________x
x________x
x________x
x________x
xxxxxxxx^x
```

and it returns the solved grid.

## How to build it
```shell
$ mvn clean package
```
