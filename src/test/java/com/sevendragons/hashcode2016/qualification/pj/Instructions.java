package com.sevendragons.hashcode2016.qualification.pj;

/**
 * Created by animus on 11/02/16.
 */
public class Instructions {

    public static class Deliver extends Instructions {

        private int droneId;
        private int warehouseId;
        private int productType;
        private int numberOfItems;

    }

    public static class Load extends Instructions {

        private int droneId;
        private int warehouseId;
        private int productType;
        private int numberOfItems;

    }

    public static class Unload extends Instructions {

        private int droneId;
        private int warehouseId;
        private int productType;
        private int numberOfItems;

    }

    public static class Wait extends Instructions {

    }

}
