package enigma;
import java.util.HashMap;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Tyler Marino
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored.
     *  (AED)(CB)*/
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        char[][] arrayOfCycles = strToCharArray(cycles);
        _permHashMap = createPermHashmap(arrayOfCycles);
        _invHashMap = createInvHashmap(arrayOfCycles);

    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char permutedChar;
        int searchVal = wrap(p);
        char permChar = _alphabet.toChar(searchVal);
        if (!_alphabet.contains(permChar)) {
            throw new EnigmaException("char not in alphabet");
        }
        if (_permHashMap.containsKey(permChar)) {
            permutedChar = _permHashMap.get(permChar);
        } else {
            permutedChar = permChar;
        }
        return _alphabet.toInt(permutedChar);
    }


    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char charPermuted;
        int searchVal = wrap(c);
        char permChar = _alphabet.toChar(searchVal);
        if (!_alphabet.contains(permChar)) {
            throw new EnigmaException("char not in alphabet");
        }
        if (_invHashMap.containsKey(permChar)) {
            charPermuted = _invHashMap.get(permChar);
        } else {
            charPermuted = permChar;
        }
        return _alphabet.toInt(charPermuted);
    }


    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int a1 = _alphabet.toInt(p);
        int a2 = permute(a1);
        return _alphabet.toChar(a2);
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        return _alphabet.toChar(invert(_alphabet.toInt(c)));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int i = 0; i < _alphabet.size(); i++) {
            if (_permHashMap.containsKey(_alphabet.toChar(i))) {
                counter++;
            }

        }
        return (counter == _alphabet.size());

    }
    /** Creates an array of characters we wish to make a HashMap with.
     * @param cyclic - cycles
     * @return 2dimensional character array
     */
    private char[][] strToCharArray(String cyclic) {
        String cyclicMut = cyclic;
        for (int i = 0; i < cyclic.length() - 1; i++) {
            Character loc = cyclic.charAt(i);
            if (loc != '(' && loc != ' ' && loc != ')') {
                for (int j = i + 1; j < cyclic.length() - 1; j++) {
                    Character loc2 = cyclic.charAt(j);
                    if (loc2 != '(' && loc2 != ' ' && loc2 != ')') {
                        if (cyclic.charAt(i) == cyclic.charAt(j)) {
                            throw new EnigmaException("duplicate keys");
                        }
                    }
                }
            }
        }
        char[] cyclicAsArray = cyclic.toCharArray();
        int count = 0, start = 0, end = 0, lenOfArray = 0, lenOfArrayCheck = 0;
        for (Character c : cyclicAsArray) {
            if (c == '(') {
                lenOfArray++;
            }
            if (c == ')') {
                lenOfArrayCheck++;
            }
        }
        if (lenOfArray != lenOfArrayCheck) {
            throw new EnigmaException("uneven parenthesis");
        }
        char[][] charArray = new char[lenOfArray][];
        for (int filleR = 0; filleR <= charArray.length - 1; filleR++) {
            char[] helper = cyclicMut.toCharArray();
            for (Character c : helper) {
                if (c == '(') {
                    start = count;
                }
                if (c == ')') {
                    end = count;
                    break;
                }
                count++;
            }
            if (end == (start + 1)) {
                throw new EnigmaException("Empty cycle");
            }
            char[] fillerArray = new char[end - start - 1];
            for (int fillInPos = start; fillInPos < end - 1; fillInPos++) {
                if (helper[fillInPos + 1] == ' ') {
                    throw new EnigmaException("empty spot in cycle");
                }
                if (!_alphabet.contains(helper[fillInPos + 1])) {
                    throw new EnigmaException("cycle map not in alphabet");
                }
                fillerArray[fillInPos - start] = helper[fillInPos + 1];
            }
            cyclicMut = cyclicMut.substring(end + 1);
            charArray[filleR] = fillerArray;
            count = 0;
        }
        return charArray;
    }

    /** creates a permuted hash map.
     * @param chaRray - cycles
     *@return hashmap with permutations*/
    private HashMap<Character, Character> createPermHashmap(char[][] chaRray) {
        HashMap<Character, Character> permHashMap = new HashMap<>();
        int lenCharArray = chaRray.length - 1;
        for (int i = 0; i <= lenCharArray; i++) {
            int lenInnerArray = chaRray[i].length - 1;
            if (lenInnerArray == 0) {
                permHashMap.put(chaRray[i][0],
                        chaRray[i][0]);
            }
            for (int j = 0; j <= lenInnerArray; j++) {
                if (j == lenInnerArray) {
                    permHashMap.put(chaRray[i][j], chaRray[i][0]);
                } else {
                    permHashMap.put(chaRray[i][j], chaRray[i][j + 1]);
                }
            }
        }
        return permHashMap;
    }
    /**creates an inverse hash map.
     *@param charRay - cycles
     *@return hashmap with inversions*/
    private HashMap<Character, Character> createInvHashmap(char[][] charRay) {
        HashMap<Character, Character> invHashmap = new HashMap<>();
        int lenCharArray = charRay.length - 1;
        for (int i = 0; i <= lenCharArray; i++) {
            int lenInnerArray = charRay[i].length - 1;
            if (lenInnerArray == 0) {
                invHashmap.put(charRay[i][0],
                        charRay[i][0]);
            }
            for (int j = 0; j <= lenInnerArray; j++) {
                if (j == 0) {
                    invHashmap.put(charRay[i][j],
                            charRay[i][lenInnerArray]);
                } else {
                    invHashmap.put(charRay[i][j],
                            charRay[i][j - 1]);
                }
            }
        }
        return invHashmap;
    }
    /**alphabet.*/
    private Alphabet _alphabet;
    /**counting num keys in hashmap.*/
    private int counter;
    /**create a hashmap for me to look through.*/
    private HashMap<Character, Character> _permHashMap = new HashMap<>();
    /**creates an inverse hashmap for me to look through.*/
    private HashMap<Character, Character> _invHashMap = new HashMap<>();

}
