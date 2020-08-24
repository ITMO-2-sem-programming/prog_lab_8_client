package ru.itmo.util.mapper;


import ru.itmo.util.HashMapStrictKey;
import ru.itmo.util.mapper.exception.InvalidMappingRangeException;

public class MappingRange {


    //todo check setting values
    private double start;
    private double end;

    public static final HashMapStrictKey<Field, String> FIELDS_DESCRIPTION
            = new HashMapStrictKey<>();


    static {
        FIELDS_DESCRIPTION.put(
                Field.START,
                "_start_ may be any non-Null Number."
        );
        FIELDS_DESCRIPTION.put(
                Field.END,
                "_end_ may be any non-Null Number greater than the _start_ field."
        );
    }


    

    public MappingRange(Number start, Number end) {
        setStart(start);
        setEnd(end);
    }


    
    
    public double size() {
        return 
                start - end;
    }
    

//----------------------------------------------------------------------------------------------------------------------


    // Getters and setters section


    public double getStart() {
        return start;
    }


    private void setStart(Number start) {

        if ( ! isValidStart(start) )
            throw new InvalidMappingRangeException(
                    String.format(
                            FIELDS_DESCRIPTION.get(Field.START) +
                                    "\n    _start_ value : '%s'",
                            start
                    )
            );


        this.start = parseStart(start);

    }


    public double getEnd() {
        return end;
    }


    private void setEnd(Number end) {

        if (
            ! ((isValidEnd(end))
            && isValidMappingRange(this.start, parseEnd(end))) )

                throw new InvalidMappingRangeException(
                        String.format(
                                FIELDS_DESCRIPTION.get(Field.END) +
                                        "\n    _start_ value : '%s'" +
                                        "\n    _end_ value : . '%s'",
                                this.start,
                                end
                        )
                );


        this.end = parseEnd(end);

    }


//----------------------------------------------------------------------------------------------------------------------


    // isValid section


    public static boolean isValidStart(Number start) {

        try {
            parseStart(start);

            return true;

        } catch (Exception e) {
            return false;
        }

    }


    /**
     * Check whether the {@code end} is valid.
     *
     * Important : this method doesn't take into view the {@link #start} value.
     * That why when constructing {@code MappingRange} this {@code end} can be not valid.
     *
     * @param end end of the {@code MappingRange}
     * @return true if the end is valid
     *
     * @see #end
     */
    public static boolean isValidEnd(Number end) {

        try {
            parseEnd(end);

            return true;

        } catch (Exception e) {
            return false;
        }

    }


    /**
     * Check whether the {@code start} and {@code end} params
     * construct a valid {@code MappingRange}.
     * Important : the method doesn't check whether the params
     * {@code start} and {@code end} are valid.
     *
     * @param start - start of the {@code MappingRange}
     * @param end - end of the {@code MappingRange}
     * @return true if the {@code MappingRange} is valid
     */
    public static boolean isValidMappingRange(double start, double end) {

        return start < end;

    }


    private static double parseStart(Number start) {
        return
                start.doubleValue();
    }


    private static double parseEnd(Number end) {
        return
                end.doubleValue();
    }

//----------------------------------------------------------------------------------------------------------------------



    public enum Field {
        START,
        END
    }


}
