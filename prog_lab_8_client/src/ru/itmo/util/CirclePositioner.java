package ru.itmo.util;


import javafx.scene.shape.Circle;

public class CirclePositioner {


    private double radius;
    private double gap;
    private int columnNumber;

    private double x;
    private double y;

    private int columnCounter
            = 0;
    private int rowCounter
            = 0;


    public static final HashMapStrictKey<Field, String> FIELDS_DESCRIPTION
            = new HashMapStrictKey<>();


    static {
        FIELDS_DESCRIPTION.put(
                Field.RADIUS,
                "_radius_ can be any positive double"
        );
        FIELDS_DESCRIPTION.put(
                Field.GAP,
                "_gap_ may be any positive double or zero."
        );
        FIELDS_DESCRIPTION.put(
                Field.COLUMN_NUMBER,
                "_columnNumber_ may be any positive integer."
        );
    }


    public CirclePositioner(double radius,
                             double gap,
                             int columnNumber) {
        setRadius(radius);
        setGap(gap);
        setColumnNumber(columnNumber);
    }


    public void position(Circle... circles) {

        for (Circle circle : circles) {

            calculatePosition();

            circle.setCenterX(x);
            circle.setCenterY(y);
        }

    }


    private void calculatePosition() {

        x = (gap + radius) + (gap + 2 * radius) * columnCounter;

        y = (gap + radius) + (gap + 2 * radius) * rowCounter;

        columnCounter ++ ;

        if (columnCounter == columnNumber) {
            columnCounter = 0;
            rowCounter ++ ;
        }
    }


    // Ненайденная ошибка
//    public double getRowWidth() {
////        System.out.println("Row width : ");
//        return (gap + radius) * columnNumber + gap + radius;
//    }


//----------------------------------------------------------------------------------------------------------------------


    // Getters and setters section


    public double getRadius() {
        return radius;
    }


    private void setRadius(double radius) {

        if ( ! isValidRadius(radius))
            throw new IllegalArgumentException(
                    String.format(
                            FIELDS_DESCRIPTION.get(Field.RADIUS)
                                    + "\n_radius_ value : '%s'",

                            radius
                    )
            );
        this.radius = radius;
    }


    public double getGap() {
        return gap;
    }


    private void setGap(double gap) {

        if ( ! isValidGap(gap))
            throw new IllegalArgumentException(
                    String.format(
                            FIELDS_DESCRIPTION.get(Field.GAP)
                                    + "\n_gap_ value : '%s'",

                            gap
                    )
            );


        this.gap = gap;

    }


    public int getColumnNumber() {
        return columnNumber;
    }


    private void setColumnNumber(int columnNumber) {

        if ( ! isValidColumnNumber(columnNumber))
            throw new IllegalArgumentException(
                    String.format(
                            FIELDS_DESCRIPTION.get(Field.COLUMN_NUMBER)
                                    + "\n_columnNumber_ value : '%s'",

                            columnNumber
                    )
            );


        this.columnNumber = columnNumber;

    }


    public int getColumnCounter() {
        return columnCounter;
    }


    public int getRowCounter() {
        return rowCounter;
    }


    public HashMapStrictKey<Field, String> getFieldsDescription() {
        return FIELDS_DESCRIPTION;
    }


    //----------------------------------------------------------------------------------------------------------------------


    // isValid section


    public static boolean isValidGap(double gap) {

        return
                gap >= 0;

    }


    public static boolean isValidColumnNumber(int columnNumber) {

        return
                columnNumber > 0;

    }


    public static boolean isValidRadius(double radius) {

        return
                radius > 0;
    }


//----------------------------------------------------------------------------------------------------------------------


    // Inner classes section


    enum Field {
        RADIUS,
        GAP,
        COLUMN_NUMBER
    }
}
