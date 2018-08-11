package http.ClientConnectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ProgramArgParser {

    private final ArrayList<String> signifiers = new ArrayList<>();
    private final String portSignifier = "-p";
    private final String dirSignifier = "-d";
    private HashMap<String, String> programParameters;
    private int defaultPort;

    public ProgramArgParser(String[] programArgs, int defaultPort) {
        addSignifiers(Arrays.asList(portSignifier, dirSignifier));
        programParameters = new HashMap<>();
        this.defaultPort = defaultPort;
        parse(programArgs);
    }

    public void parse(String[] programArgs) {
        for (int i = 0; i < programArgs.length; i++) {
            if (signifiers.contains(programArgs[i])) {
                programParameters.put(programArgs[i], programArgs[i + 1]);
            }
        }
    }

    public int getPort() {
        return programParameters.containsKey(portSignifier) ?
                Integer.parseInt(programParameters.get(portSignifier)) : defaultPort;
    }

    public String getRootPath() {
        return programParameters.get(dirSignifier);
    }

    private void addSignifiers(List<String> signifiers) {
        for (String signifier : signifiers) {
            addSignifier(signifier);
        }
    }

    private void addSignifier(String signifier) {
        signifiers.add(signifier);
    }
}
