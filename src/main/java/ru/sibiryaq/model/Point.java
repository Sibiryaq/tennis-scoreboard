package ru.sibiryaq.model;

public enum Point {
    LOVE, FIFTEEN, THIRTY, FORTY, ADVANTAGE;

    public Point next() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            default -> this;
        };
    }
}
