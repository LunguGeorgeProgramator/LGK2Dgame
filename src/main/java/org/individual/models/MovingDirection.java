package org.individual.models;

import java.util.Arrays;
import java.util.Objects;

public enum MovingDirection
{
    LEFT("left"),
    RIGHT("right"),
    UP("up"),
    DOWN("down"),
    STATIONARY("STATIONARY");

    private final String directionString;

    MovingDirection(String directionString)
    {
        this.directionString = directionString;
    }

    public String getValue()
    {
        return this.directionString;
    }

    public static MovingDirection getValueByString(String movingStringValueToFilter)
    {
        return Arrays.stream(MovingDirection.values())
            .filter(s -> Objects.equals(s.getValue(), movingStringValueToFilter))
            .findFirst()
            .orElse(null);
    }

}
