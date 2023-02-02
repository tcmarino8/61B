package enigma;

import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Tyler Marino
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numPawls = pawls;
        _numRotors = numRotors;
        if (allRotors == null) {
            throw new EnigmaException("no rotors");
        } else {
            for (Rotor each : allRotors) {
                rotorHashMap.put(each.name(), each);
            }
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls;
    }

    /** Return Rotor #K, where Rotor #0 is the reflector, and Rotor
     *  #(numRotors()-1) is the fast Rotor.  Modifying this Rotor has
     *  undefined results. */
    Rotor getRotor(int k) {
        return rotorArray[k];
    }

    Alphabet alphabet() {
        return _alphabet;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        if (rotors.length != _numRotors) {
            throw new EnigmaException("mismatched length of rotors");
        }
        rotorArray = new Rotor[rotors.length];
        for (int i = 0; i < rotors.length; i++) {
            if (rotorHashMap.containsKey(rotors[i])) {
                rotorArray[i] = rotorHashMap.get(rotors[i]);
            } else {
                throw new EnigmaException("rotor does not exist");
            }
        }
        for (int i = rotors.length - _numPawls; i < rotors.length - 1; i++) {
            if (!rotorArray[i].rotates()) {
                throw new EnigmaException("moving rotor not moving");
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        for (int i = 1; i < numRotors(); i++) {
            rotorArray[i].set(setting.charAt(i - 1));
        }
    }

    /** Return the current plugboard's permutation. */
    Permutation plugboard() {
        return _plugboard;
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        advanceRotors();
        if (Main.verbose()) {
            System.err.printf("[");
            for (int r = 1; r < numRotors(); r += 1) {
                System.err.printf("%c",
                        alphabet().toChar(getRotor(r).setting()));
            }
            System.err.printf("] %c -> ", alphabet().toChar(c));
        }
        c = plugboard().permute(c);
        if (Main.verbose()) {
            System.err.printf("%c -> ", alphabet().toChar(c));
        }
        c = applyRotors(c);
        c = plugboard().permute(c);
        if (Main.verbose()) {
            System.err.printf("%c%n", alphabet().toChar(c));
        }
        return c;
    }

    /** Advance all rotors to their next position. */
    private void advanceRotors() {
        boolean[] rotateArray = new boolean[rotorArray.length];
        rotateArray[rotorArray.length - 1] = true;
        for (int k = 1; k <= rotorArray.length - 2; k++) {
            if (rotorArray[k + 1].atNotch() && rotorArray[k].rotates()) {
                rotateArray[k] = true;
            }
        }
        for (int f = 1; f < rotorArray.length - 1; f++) {
            if (rotorArray[f - 1].rotates() && rotorArray[f].atNotch()) {
                rotateArray[f] = true;
            }
        }
        rotorArray[rotorArray.length - 1].advance();
        for (int j = 0; j <= rotorArray.length - 2; j++) {
            if (rotateArray[j]) {
                rotorArray[j].advance();
            }
        }
    }

    /** Return the result of applying the rotors to the character C (as an
     *  index in the range 0..alphabet size - 1). */
    private int applyRotors(int c) {
        for (int j = rotorArray.length - 1; j >= 0; j--) {
            c = rotorArray[j].convertForward(c);

        }
        for (int i = 1; i <= rotorArray.length - 1; i++) {
            c = rotorArray[i].convertBackward(c);
        }
        return c;
    }
    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String returnString = "";
        for (int i = 0; i < msg.length(); i++) {
            int helper = convert(alphabet().toInt(msg.charAt(i)));
            returnString += _alphabet.toChar(helper);

        }
        return returnString;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;
    /**num pawls.*/
    private final int _numPawls;
    /**num rotors.*/
    private final int _numRotors;
    /** plugboard permutation.*/
    private Permutation _plugboard;
    /** array consiting of rotors.*/
    private Rotor[] rotorArray;
    /** hashmap of rotor names as keys and rotors as values.*/
    private HashMap<String, Rotor> rotorHashMap = new HashMap<>();

}
