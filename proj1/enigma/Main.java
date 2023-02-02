package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ucb.util.CommandArgs;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Tyler Marino
 */
public final class Main {

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            CommandArgs options =
                    new CommandArgs("--verbose --=(.*){1,3}", args);
            if (!options.ok()) {
                throw error("Usage: java enigma.Main [--verbose] "
                        + "[INPUT [OUTPUT]]");
            }

            _verbose = options.contains("--verbose");
            new Main(options.get("--")).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Open the necessary files for non-option arguments ARGS (see comment
     * on main).
     */
    Main(List<String> args) {
        _config = getInput(args.get(0));

        if (args.size() > 1) {
            _input = getInput(args.get(1));
        } else {
            _input = new Scanner(System.in);
        }

        if (args.size() > 2) {
            _output = getOutput(args.get(2));
        } else {
            _output = System.out;
        }
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    private void process() {
        Machine systemMachine;
        systemMachine = readConfig();
        String inputSettingTemp;
        String inputSetting = "";
        String convertedMsg = "";
        while (_input.hasNext()) {
            inputSettingTemp = _input.nextLine();
            if (inputSettingTemp.indexOf('*') > 0) {
                inputSetting = inputSettingTemp.substring(1);
                setUp(systemMachine, inputSetting);
            } else {
                convertedMsg = systemMachine.convert(inputSettingTemp);
                printMessageLine(convertedMsg);
            }

        }
    }

    /**
     * Return an Enigma machine configured from the contents of configuration
     * file _config.
     */
    private Machine readConfig() {
        try {
            String confAlpha = _config.nextLine();
            System.out.println(confAlpha);
            _alphabet = new Alphabet(confAlpha);
            Integer numRotors = _config.nextInt();
            Integer numPawls = _config.nextInt();
            if (numPawls >= numRotors) {
                throw new EnigmaException("too many moving rotors");
            }
            ArrayList allRotors = new ArrayList<>();
            while (_config.hasNext()) {
                allRotors.add(readRotor());
            }
            return new Machine(_alphabet, 2, 1, null);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /**
     * Return a rotor, reading its description from _config.
     */
    private Rotor readRotor() {
        try {
            String rotorName = _config.next();
            String typeNotch = _config.next();
            Character rotorType = typeNotch.charAt(0);
            String notchs;
            Permutation mRotorPerm;
            Permutation fRotorPerm;
            Permutation rRotorPerm;
            if (rotorType == 'M') {
                if (typeNotch.length() == 1) {
                    notchs = "";
                } else {
                    notchs = typeNotch.substring(1);
                }
                String mPermutations = "";
                while (_config.hasNext("([\\(][^()]+[\\)])+")) {
                    mPermutations += _config.next() + " ";
                }
                mRotorPerm = new Permutation(mPermutations, _alphabet);
                return new MovingRotor(rotorName, mRotorPerm, notchs);

            } else if (rotorType == 'N') {
                if (typeNotch.length() > 1) {
                    throw new EnigmaException("Fixed rotor has notches");
                } else {
                    String fPermutations = "";
                    while (_config.hasNext("([\\(][^()]+[\\)])+")) {
                        fPermutations += _config.next() + " ";
                    }
                    fRotorPerm = new Permutation(fPermutations, _alphabet);
                }
                return new FixedRotor(rotorName, fRotorPerm);

            } else {
                if (!rotorType.equals('R')) {
                    throw new EnigmaException("Rotor is not a valid rotor "
                            + "type");
                }
                String rPermutations = "";
                while (_config.hasNext("([\\(][^()]+[\\)])+")) {
                    rPermutations += _config.next() + " ";
                }
                rRotorPerm = new Permutation(rPermutations, _alphabet);
                return new Reflector(rotorName, rRotorPerm);

            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        Permutation plugboardPerm;
        String withOReflect = settings.substring(1);
        Scanner usefulScanner = new Scanner(withOReflect);
        String[] rotorStrArray = new String[M.numRotors()];
        for (int i = 0; i < M.numRotors(); i++) {
            rotorStrArray[i] = usefulScanner.next();
        }
        M.insertRotors(rotorStrArray);
        String settingsOnly = usefulScanner.next();
        M.setRotors(settingsOnly);
        String plugboardPermStr = "";
        while (usefulScanner.hasNext("([\\(][^()]+[\\)])+")) {
            plugboardPermStr += usefulScanner.next();
        }
        plugboardPerm = new Permutation(plugboardPermStr, _alphabet);
        M.setPlugboard(plugboardPerm);
    }


    /** Return true iff verbose option specified. */
    static boolean verbose() {
        return _verbose;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        int msgLength = msg.length();
        msg = msg.replaceAll(" ", "");
        msg += "     ";
        String helper = "";
        for (int j = 0; j < msgLength - 1; j += 5) {
            for (int i = j; i < j + 5; i++) {
                if (Character.isWhitespace(msg.charAt(i))) {
                    break;
                }
                helper += msg.charAt(i);

            }
            if (j + 6 > msgLength - 1) {
                break;
            }
            helper += " ";
        }
        _output.println(helper);
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** True if --verbose specified. */
    private static boolean _verbose;
}
