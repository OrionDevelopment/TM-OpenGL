package com.smithsgaming.transportmanager.util.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Tim on 29/03/2016.
 */
public class Vector2d {

    public static Vector2d zero = new Vector2d(0, 0);

    public double x;
    public double y;

    public Vector2d() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2d(double d, double d1) {
        x = d;
        y = d1;
    }

    public Vector2d(Vector2d vec) {
        x = vec.x;
        y = vec.y;
    }


    public Vector2d copy() {
        return new Vector2d(this);
    }

    public Vector2d set(double d, double d1) {
        x = d;
        y = d1;
        return this;
    }

    public Vector2d set(Vector2d vec) {
        x = vec.x;
        y = vec.y;
        return this;
    }

    public Vector2d add(double d, double d1) {
        x += d;
        y += d1;
        return this;
    }

    public Vector2d add(Vector2d vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    public Vector2d add(double d) {
        return add(d, d);
    }

    public Vector2d sub(Vector2d vec) {
        return subtract(vec);
    }

    public Vector2d subtract(Vector2d vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    public Vector2d sub(double dX, double dY) {
        return subtract(dX, dY);
    }

    public Vector2d subtract(double dX, double dY) {
        x -= dX;
        y -= dY;
        return this;
    }

    public Vector2d negate(Vector2d vec) {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2d multiply(double d) {
        x *= d;
        y *= d;
        return this;
    }

    public Vector2d multiply(Vector2d f) {
        x *= f.x;
        y *= f.y;
        return this;
    }

    public Vector2d multiply(double fx, double fy) {
        x *= fx;
        y *= fy;
        return this;
    }

    public Vector2d abs() {
        if (x < 0) {
            x *= -1;
        }
        if (y < 0) {
            y *= -1;
        }
        return this;
    }

    public double manhattanDistance() {
        return this.x + this.y;
    }

    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    public double magSquared() {
        return x * x + y * y;
    }

    public Vector2d normalize() {
        double d = mag();
        if (d != 0) {
            multiply(1 / d);
        }

        return this;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Vector2d negate() {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x + y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector2d)) {
            return false;
        }
        Vector2d v = (Vector2d) o;
        return x == v.x && y == v.y;
    }

    @Override
    public String toString() {
        MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
        return "Vector2d(" + new BigDecimal(x, cont) + ", " + new BigDecimal(y, cont) + ")";
    }

    public double getDistanceTo(Vector2d location) {
        Vector2d diff = new Vector2d(this.sub(location));
        return diff.mag();
    }

    public void rotate(double n) {
        double rx = (this.x * Math.cos(n)) - (this.y * Math.sin(n));
        double ry = (this.x * Math.sin(n)) + (this.y * Math.cos(n));
        x = rx;
        y = ry;
    }

    public void makePositive () {
        if (x < 0)
            x *= -1;

        if (y < 0)
            y *= -1;
    }

    public Vector2i toInt () {
        return new Vector2i((int) x, (int) y);
    }

}
