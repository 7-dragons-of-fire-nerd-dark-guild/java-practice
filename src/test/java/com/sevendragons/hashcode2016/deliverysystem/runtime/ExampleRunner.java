package com.sevendragons.hashcode2016.deliverysystem.runtime;

import com.sevendragons.hashcode2016.deliverysystem.DeliverySystem;
import com.sevendragons.hashcode2016.deliverysystem.solver.DeliverSmallEnoughOrdersWithManyDrones;
import com.sevendragons.hashcode2016.deliverysystem.solver.Solver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExampleRunner {
    public static void main(String[] args) throws IOException {
        Solver solver = new DeliverSmallEnoughOrdersWithManyDrones();

        List<String> names = Arrays.asList("busy_day", "mother_of_all_warehouses", "redundancy");

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
        String ts = dateFormat.format(new Date());
        File outputDir = new File("submissions/output/" + solver.getClass().getSimpleName(), ts);
        if (!outputDir.isDirectory() && !outputDir.mkdirs()) {
            throw new IOException("could not create output dir: " + outputDir);
        }

        for (String name : names) {
            String inputPath = String.format("src/main/resources/delivery-system/%s.in", name);
            String outputPath = String.format("%s/%s.out", outputDir, name);
            DeliverySystem.solveAndWriteOutput(inputPath, outputPath, solver);
        }
    }
}
