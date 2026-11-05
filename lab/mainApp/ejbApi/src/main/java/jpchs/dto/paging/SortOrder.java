package jpchs.dto.paging;

import lombok.Getter;

import java.io.Serializable;

public record SortOrder(String field, Direction direction) implements Serializable
{
    public enum Direction {
        ASC(true),
        DESC(false);

        @Getter
        private final boolean isAsc;

        Direction(boolean isAsc) {
            this.isAsc = isAsc;
        }

        public static Direction fromString(String source) {
            return Direction.valueOf(source.toUpperCase());
        }
    }
}
