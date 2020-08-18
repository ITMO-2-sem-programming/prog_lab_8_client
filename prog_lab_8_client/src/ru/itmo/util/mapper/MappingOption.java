package ru.itmo.util.mapper;


public enum MappingOption {
    INCLUDE_NO_ENDINGS (-1),
    INCLUDE_ONE_ENDING (0),
    INCLUDE_BOTH_ENDINGS (1);

    /**
     * Represent value to add to {@link MappingRange} object to get correct range size.
     */
    public final int offset;

    MappingOption(int offset) {
        this.offset = offset;
    }

}
