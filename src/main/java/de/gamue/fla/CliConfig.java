package de.gamue.fla;

import java.io.StringWriter;
import java.util.Arrays;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

@Slf4j
@Getter
public class CliConfig {

    @Option(name = "-d", usage = "directory which should be scanned", required = true)
    private String directory;

    @Option(name = "-o", usage = "output file, if not set data will printed on console.")
    private String outputFile = "";

    @Option(name = "-s", usage = "if set to true, the result will be split by used camera (default: false).")
    private boolean splitByCamera = false;

    @Option(name = "-ff", usage = "if set to true, the focal lengths are returned in 35mm film equivalent (default: false).")
    private boolean fullFrameEquivalent = false;

    public static CliConfig read(final String... args) {
        log.info("CLI arguments: {}", Arrays.toString(args));

        CliConfig cliConfig = new CliConfig();
        CmdLineParser parser = new CmdLineParser(cliConfig);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            log.error(e.getMessage());
            printUsage(parser);
        }

        return cliConfig;
    }

    private static void printUsage(CmdLineParser parser) {
        StringWriter writer = new StringWriter();
        parser.printUsage(writer, null);
        log.info(writer.toString());
    }
}
