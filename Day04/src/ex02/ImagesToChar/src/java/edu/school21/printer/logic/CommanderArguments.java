package edu.school21.printer.logic;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")

public class CommanderArguments {

    @Parameter(names="--white", required = true)
    public String white;

    @Parameter(names="--black", required = true)
    public String black;

}
