package me.ienze.processing.hexaflexaspace.cli;

import org.apache.commons.cli.*;
import processing.core.PApplet;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Ienze
 */
public class HexaflexaspaceCli {

    public static void main(String[] args) throws ParseException, IOException {

        Options options = new Options();

        options.addOption("s", true, "Hexaflexagon radius in pixels");
        options.addOption("w", false, "Watch for file changes");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.getArgs().length != 2) {
            System.err.println("required arguments: [input file] [output file]");
            return;
        }

        Path in = Paths.get(cmd.getArgs()[0]);
        Path out = Paths.get(cmd.getArgs()[1]);
        double size = 0;
        if(options.getOption("s").getValue() != null) {
            size = Double.parseDouble(options.getOption("s").getValue());
        }
        boolean watch = options.getOption("w").getValue() != null;

        HexaflexagonImageSketch hex = new HexaflexagonImageSketch(in, out, size, watch);

        PApplet.runSketch(new String[]{"me.ienze.processing.hexaflexaspace.cli.HexaflexagonImageSketch"}, hex);
    }
}
