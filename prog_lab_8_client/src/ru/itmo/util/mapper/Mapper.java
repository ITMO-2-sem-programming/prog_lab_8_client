package ru.itmo.util.mapper;


import ru.itmo.util.HashMapStrictKey;
import ru.itmo.util.mapper.exceptions.InvalidMappingRangeException;

public class Mapper {


    private MappingRange mapFromRange;
    private MappingRange mapToRange;
    private MappingOption mappingOption
            = MAPPING_OPTION_DEFAULT;

    public static final MappingOption MAPPING_OPTION_DEFAULT
            = MappingOption.INCLUDE_BOTH_ENDINGS;
    public static final HashMapStrictKey<Field, String> FIELDS_DESCRIPTION
            = new HashMapStrictKey<>();


    static {
        FIELDS_DESCRIPTION.put(
                Field.VALUE_TO_MAP,
                "_valueToMap_ may be any non-Null Number."
        );
    }
    
    
    
    
    public Mapper(MappingRange mapFromRange,
                  MappingRange mapToRange,
                  MappingOption mappingOption) {

        setMapFromRange(mapFromRange);
        setMapToRange(mapToRange);
        setMappingOption(mappingOption);

    }


    public Mapper(MappingRange mapFromRange,
                  MappingRange mapToRange) {

        this(
                mapFromRange,
                mapToRange,
                MAPPING_OPTION_DEFAULT
        );

    }




    public double map(Number valueToMap) {

        double valueToMapDouble = validateValueToMap(valueToMap);

        // // TODO: 8/15/20 Mention this moment in JavaDoc 
        if (valueToMapDouble < this.mapFromRange.getStart())
            valueToMapDouble = this.mapFromRange.getStart();
        else if (valueToMapDouble > this.mapFromRange.getEnd())
            valueToMapDouble = this.mapFromRange.getEnd();

        int offset = mappingOption.offset;

        return
                ( valueToMapDouble - mapFromRange.getStart() )
                        / ( mapFromRange.getEnd() - mapFromRange.getStart() + offset )

                        * ( mapToRange.getEnd() - mapToRange.getStart() + offset )
                        + mapToRange.getStart();

    }


    public long mapAndTruncate(Number valueToMap) {

        return (long) map(valueToMap);

    }


    public long mapAndRound(Number valueToMap) {

        return Math.round(map(valueToMap));

    }


//----------------------------------------------------------------------------------------------------------------------


    // Getters and setters section


    public MappingRange getMapFromRange() {
        return mapFromRange;
    }


    private void setMapFromRange(MappingRange mapFromRange) {
        this.mapFromRange = mapFromRange;
    }


    public MappingRange getMapToRange() {
        return mapToRange;
    }


    private void setMapToRange(MappingRange mapToRange) {
        this.mapToRange = mapToRange;
    }


    public void setMappingOption(MappingOption mappingOption) {
        this.mappingOption = mappingOption;
    }


    public MappingOption getMappingOptionDefault() {
        return MAPPING_OPTION_DEFAULT;
    }


    public HashMapStrictKey<Field, String> getFieldsDescription() {
        return FIELDS_DESCRIPTION;
    }


//----------------------------------------------------------------------------------------------------------------------


    // isValid section


    public static boolean isValidValueToMap(Number valueToMap) {

        try {

            parseValueToMap(valueToMap);

            return true;

        } catch (Exception e) {
            return false;
        }

    }


//----------------------------------------------------------------------------------------------------------------------


    // Other sections


    private static double parseValueToMap(Number valueToMap) {

        return valueToMap.doubleValue();

    }


    private double validateValueToMap(Number valueToMap) {

        if ( ! isValidValueToMap(valueToMap) )
            throw new InvalidMappingRangeException(
                    FIELDS_DESCRIPTION.get(Field.VALUE_TO_MAP) +
                            String.format(
                                    "_valueToMap_ value : '%s'",
                                    
                                    valueToMap
                            )
            );
        
        return parseValueToMap(valueToMap);
        
    }


//----------------------------------------------------------------------------------------------------------------------


    //Inner classes


    public enum Field {
        VALUE_TO_MAP
    }


}
