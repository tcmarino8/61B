package gitlet;

import java.io.File;
/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static Repo Model = new Repo();
    public static StagingArea stageArea = new StagingArea();
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }


        // FILL THIS IN
        String command = args[0];
        //StagingArea Model = new StagingArea();
        switch (command) {
            case "status":
                Model.status();
                break;
            case "find":
                Model.find(args[1]);
                break;
            case "rm":
                Model.rm(args[1]);
                break;
            case "checkout":
                if (args.length < 4) {
                    Model.basicCheckout(args[2]);
                }
                else {
                    Model.secondCheckout(args[1], args[3]);
                }
                break;
            case "commit":
                Model.inputCommit(args[1]);
                break;
            case "add":
                File file = new File(args[1]);
                Model.add(file);
                break;
            case "init":
                Model.init();
                break;
            case "log":
                Model.log();
                break;
        }
        //System.out.println("No command with that name exists.");
    }
}



