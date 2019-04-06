package de.gamue.fla;

import java.io.StringWriter;
import java.util.Arrays;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CliConfig.class);

    @Option(name = "-d", usage = "directory which should be scanned", required = true)
    private String directory;

    @Option(name = "-o", usage = "output file, if not set data will printed on console.")
    private String outputFile;


    public static CliConfig read(final String... args) {
        LOGGER.info("CLI arguments: {}", Arrays.toString(args));

        CliConfig cliConfig = new CliConfig();
        CmdLineParser parser = new CmdLineParser(cliConfig);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            LOGGER.error(e.getMessage());
            printUsage(parser);
        }

        return cliConfig;
    }

    private static void printUsage(CmdLineParser parser) {
        StringWriter writer = new StringWriter();
        parser.printUsage(writer, null);
        LOGGER.info(writer.toString());
    }

    public String getDirectory() {
        return directory;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
