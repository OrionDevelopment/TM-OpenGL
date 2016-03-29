package com.smithsgaming.transportmanager.util.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Tim on 29/03/2016.
 */
public class Vector2i {

    public static Vector2i zero = new Vector2i(0, 0);

    public int x;
    public int y;

    public Vector2i() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2i(int d, int d1) {
        x = d;
        y = d1;
    }

    public Vector2i(Vector2i vec) {
        x = vec.x;
        y = vec.y;
    }

    public Vector2i copy() {
        return new Vector2i(this);
    }

    public Vector2i set(int d, int d1) {
        x = d;
        y = d1;
        return this;
    }

    public Vector2i set(Vector2i vec) {
        x = vec.x;
        y = vec.y;
        return this;
    }

    public Vector2i add(int d, int d1) {
        x += d;
        y += d1;
        return this;
    }

    public Vector2i add(Vector2i vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    public Vector2i add(int d) {
        return add(d, d);
    }

    public Vector2i sub(Vector2i vec) {
        return subtract(vec);
    }

    public Vector2i subtract(Vector2i vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    public Vector2i sub(int dX, int dY) {
        return subtract(dX, dY);
    }

    public Vector2i subtract(int dX, int dY) {
        x -= dX;
        y -= dY;
        return this;
    }

    public Vector2i negate(Vector2i vec) {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2i multiply(int d) {
        x *= d;
        y *= d;
        return this;
    }

    public Vector2i multiply(Vector2i f) {
        x *= f.x;
        y *= f.y;
        return this;
    }

    public Vector2i multiply(int fx, int fy) {
        x *= fx;
        y *= fy;
        return this;
    }

    public Vector2i abs() {
        if (x < 0) {
            x *= -1;
        }
        if (y < 0) {
            y *= -1;
        }
        return this;
    }

    public int manhattanDistance() {
        return this.x + this.y;
    }

    public double mag() {
        return java.lang.Math.sqrt(x * x + y * y);
    }

    public double magSquared() {
        return x * x + y * y;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Vector2i negate() {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector2i)) {
            return false;
        }
        Vector2i v = (Vector2i) o;
        return x == v.x && y == v.y;
    }

    @Override
    public String toString() {
        MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
        return "Vector2i(" + new BigDecimal(x, cont) + ", " + new BigDecimal(y, cont) + ")";
    }

    public double getDistanceTo(Vector2i location) {
        Vector2i diff = new Vector2i(this.sub(location));
        return diff.mag();
    }

    public Vector2d toDouble() {
        return new Vector2d(x, y);
    }
}
