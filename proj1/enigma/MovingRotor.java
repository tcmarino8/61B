package enigma;


/** Class that represents a rotating rotor in the enigma machine.
 *  @author Tyler Marino
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _setting = 0;
        _notches = notches;
    }
    @Override
    boolean rotates() {
        return true;
    }
    @Override
    void advance() {
        if (_setting == permutation().size() - 1) {
            _setting = 0;
        } else {
            _setting++;
        }
    }
    @Override
    boolean atNotch() {
        for (int i = 0; i < _notches.length(); i++) {
            Character permAlpha = permutation().alphabet().toChar(_setting);
            if (_notches.charAt(i) == permAlpha) {
                return true;
            }
        }
        return false;
    }

    @Override
    String notches() {
        return _notches;
    }
    /** string of notches.*/
    private final String _notches;


}
