package me.ienze.processing.hexaflexaspace.cli;

import org.apache.commons.cli.*;

/**
 * @author Ienze
 */
public class HexaflexaspaceCli {

    public static void main(String[] args) throws ParseException {

        Options options = new Options();

        options.addOption("s", true, "Hexaflexagon radius in pixels");
        options.addOption("w", false, "Watch for file changes");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.getArgs().length != 2) {
            System.err.println("required arguments: [input file] [output file]");
        }


    }
}
