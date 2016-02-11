package com.sevendragons.hashcode2016.qualification;

import com.sevendragons.hashcode2016.qualification.pj.Model;
import com.sevendragons.hashcode2016.qualification.pj.Utility;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class QualificationTest_PJ {

    public static String example_entry = "100 100 3 50 500\n"+
            "3\n"+
            "100 5 450\n"+
            "2\n"+
            "0 0\n"+
            "5 1 0\n"+
            "5 5\n"+
            "0 10 2\n"+
            "3\n"+
            "1 1\n"+
            "2\n"+
            "2 0\n"+
            "3 3\n"+
            "3\n"+
            "0 0 0\n"+
            "5 6\n"+
            "1\n"+
            "2";

    @Test
    public void testEntry() {
        Model model = Utility.parseEntry(new Scanner(example_entry));
        assertEquals(100, model.getMapHeight());
        assertEquals(100, model.getMapWidth());
    }
}
