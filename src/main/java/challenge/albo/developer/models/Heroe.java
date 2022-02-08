package challenge.albo.developer.models;

public enum Heroe {
    ironman(1L),
    capamerica(2L);

    private final Long value;

    Heroe(final Long newValue) {
        value = newValue;
    }

    public Long getValue() { return value; }
}
