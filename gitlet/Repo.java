package gitlet;
import net.sf.saxon.trans.SymbolicName;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;

public class Repo {
    //TreeMap<String, String> branch = new TreeMap<>();

    File removedFiles = new File(".gitlet/Stage/removed");
    String HEAD = "master";
    StagingArea stagedFile = new StagingArea();
    TreeMap<String, String> staged;
    boolean exists;
    String finalCommitHash;
    ArrayList<String> removedList = new ArrayList<>();
    //private File workingDirectory = new File(System.getProperty());
    //File gitletRepo;
    //File stagingDir;
    //File commitDir;
    //File initialDir;
    //ArrayList<String> commits = new ArrayList<>();

    public void init() {
        File gitletRepo = new File(".gitlet");
        //exists = gitletRepo.exists();
        //Need to figure out this if else statement
        if (gitletRepo.exists()) {
            System.out.println("A Gitlet version-control system already " + " exists in the current directory.");
            return;
        } else {
            File blobDir = new File (".gitlet/Blobs");
            File commitDir = new File(".gitlet/commit");
            File stagingDir = new File (".gitlet/Stage");
            File branches = new File (".gitlet/branch");
            //File logs = new File (".gitlet/log");
            //logs.mkdirs();
            branches.mkdirs();
            blobDir.mkdirs();
            commitDir.mkdirs();
            gitletRepo.mkdir();
            stagingDir.mkdirs();
            Commit initialCommit = new Commit("initial commit", null, true,
                    null);
            String hashToAdd = Utils.sha1(Utils.serialize(initialCommit));
            File initialCommitFile = new File(".gitlet/commit/" + hashToAdd);
            Utils.writeObject(initialCommitFile,
                    initialCommit);

            File Head = new File (".gitlet/branch/HEAD.txt");
            Utils.writeContents(Head, hashToAdd);


            File master = new File (".gitlet/branch/master.txt");
            Utils.writeContents(master, "master");

            File Blob = new File(".gitlet/Blobs/blob");
            TreeMap<String, byte[]> BlobMapper = new TreeMap<>();
            Utils.writeObject(Blob, BlobMapper);

            Utils.writeObject(removedFiles, removedList);

            File StagingArea = new File(".gitlet/Stage/stage");
            Utils.writeContents(StagingArea, Utils.serialize(stagedFile));

        }
    }


    public void add(File file) {
        if (file.exists()) {
            String hashCode = Utils.sha1(Utils.readContents(file));

            File getBlob = new File(".gitlet/Blobs/blob");
            TreeMap<String, byte[]> blobMap = Utils.readObject(getBlob,
                    TreeMap.class);
            File stageATm = new File(".gitlet/Stage/stage");
            StagingArea currentStage = Utils.readObject(stageATm,
                    StagingArea.class);
            if (getHeadCommit().getBlob().containsValue(hashCode)) {
                if (currentStage.listToRemove.contains(file.getName())) {
                    currentStage.listToRemove.remove(file.getName());
                }
                else {
                    return;
                }
            }
            else {
                blobMap.put(hashCode, Utils.readContents(file));
                File saveBlob = new File(".gitlet/Blobs/blob");
                Utils.writeObject(saveBlob, blobMap);

                /**File newStage =
                 new File(".gitlet/Blobs/" + hashCode);
                 Utils.writeContents(newStage, Utils.readContents(file));*/

                currentStage.add(file);
            }
                String postAddHash = Utils.sha1(Utils.serialize(currentStage));
                File stageAfter = new File(".gitlet/Stage/stage");
                Utils.writeObject(stageAfter, currentStage);
        }
        else {
            System.out.println("File does not exist.");
        }
    }
    /*if (hashCode.equals(headHash())) {
                return;
                //this ckecks if they are the same object trying to be added
            }*/
            /*if (!getHead().equals("master")) {
                HashMap<String, String> lastSaved = getHead().getBlob();
                System.out.println(lastSaved);
                if(lastSaved.equals(hashCode)) {
                stagedFile.remove(file);
            }
            else {
                stagedFile.add(file);
            }*/
    public void status() {
        File gitletDir = new File(".gitlet/");
        if (!gitletDir.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        }
        System.out.println("=== Branches ===");
        File branches = new File(".gitlet/branch/master.txt");
        System.out.println("*" + Utils.readContentsAsString(branches));
        System.out.println();
        //still will need to get other branches

        File stagingFile = new File (".gitlet/Stage/stage");
        StagingArea stage = Utils.readObject(stagingFile, StagingArea.class);
        TreeMap<String, String> stageMap = stage.getAdded();
        System.out.println("=== Staged Files ===");
        for (String fileName : stageMap.keySet()) {
            System.out.println(fileName);
        }
        System.out.println();

        System.out.println("=== Removed Files ===");
        removedList = stage.listToRemove;
        for (int i = 0; i < removedList.size(); i++) {
            System.out.println(removedList.get(i));
        }
        System.out.println();

        System.out.println("=== Modifications Not Staged For Commit ===");
        /**ArrayList<String> modNotStaged = modifiedNotStaged();
        for (int i = 0; i < modNotStaged.size(); i++) {
            System.out.println(modNotStaged.get(i));
        }*/
        System.out.println();

        System.out.println("=== Untracked Files ===");
        //printUntrackedFiles();
    }

    public ArrayList<String> modifiedNotStaged(){
        ArrayList<String> modNotStagedArr = new ArrayList<>();
        Commit current = getHeadCommit();
        TreeMap<String, String> currentBlob = current.getBlob();

        TreeMap<String, String> stageMap = getStageMap();
        for (String fileName : currentBlob.keySet()) {
            File workingDirFile = new File(fileName);
            String workFileHash = Utils.sha1(Utils.serialize(workingDirFile));
            String commitedHash = currentBlob.get(fileName);
            if (!commitedHash.equals(workFileHash) && !commitedHash.equals(stageMap.get(fileName))) {
                modNotStagedArr.add(fileName);
            }
            if (!stageMap.get(fileName).equals(workFileHash)) {
                modNotStagedArr.add(fileName);
            }
            if (stageMap.containsKey(fileName) && !workingDirFile.exists()) {
                modNotStagedArr.add(fileName);
            }
            if (!stageMap.containsKey(fileName) && currentBlob.containsKey(fileName)
            && !workingDirFile.exists()) {
                modNotStagedArr.add(fileName);
            }
        }
        return modNotStagedArr;
    }

    public static Commit getCommit(String hash) {
        File currCommit = new File(".gitlet/commit/" + hash);
        return Utils.readObject(currCommit, Commit.class);
    }

    public Commit getHead() {
        File Head = new File(".gitlet/branch/HEAD.txt");
        String headStr = Utils.readContentsAsString(Head);
        return getCommit(headStr);
    }

    public TreeMap<String, String> getStageMap() {
        File stageFile = new File(".gitlet/Stage/stage");
        StagingArea stage = Utils.readObject(stageFile, StagingArea.class);
        TreeMap<String, String> stageMap = stage.getAdded();
        return stageMap;
    }

    /**public String headHash() {
        return getHead().serialized;
    }*/

    public void printForLog(Commit commit) {
        String commitHash = Utils.sha1(Utils.serialize(commit));
        System.out.println("===");
        System.out.println("commit " + commitHash);
        System.out.println("Date: " + commit.getTimeStamp());
        System.out.println(commit.getCommitMessage());
        System.out.println();
    }

    public void log() {
        Commit toDisplay = getHeadCommit();
        while (toDisplay != null) {
            printForLog(toDisplay);
            if (toDisplay.getParent() == null) {
                break;
            }
            else {
                //printForLog(toDisplay);
                toDisplay = getCommit(toDisplay.getParent());
            }
        }
    }

    public void inputCommit(String commitMessage){
        if (commitMessage.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        TreeMap<String, String> finalBlob = new TreeMap<>();
        File StageArea = new File(".gitlet/Stage/stage");
        StagingArea stage = Utils.readObject(StageArea, StagingArea.class);
        TreeMap<String, String> toCommit = stage.getAdded();
        // get remove list from remove file, then use that to check if it is
        // empty.
        ArrayList<String> listForRemoval = Utils.readObject(removedFiles,
                ArrayList.class);
        if (toCommit.isEmpty() && listForRemoval.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        File headCommit = new File(".gitlet/branch/HEAD.txt");
        String parentHash = Utils.readContentsAsString(headCommit);

        Commit prevCommit = getHeadCommit();
        TreeMap<String, String> prevBlob = prevCommit.getBlob();

        if (prevBlob.isEmpty()) {
            finalBlob = toCommit;
        }
        else {
            finalBlob.putAll(prevBlob);
            finalBlob.putAll(toCommit);
        }
        //just added to remove files from previous commit that were staged
        // for removal
        for (String fileToRemove : stage.listToRemove) {
            finalBlob.remove(fileToRemove);
            //stage.listToRemove.remove(fileToRemove);
        }

        String hashForBlob = Utils.sha1(Utils.serialize(finalBlob));

        Commit finalCommit = new Commit(commitMessage, finalBlob, false,
                parentHash);
        String finalCommitHashing = Utils.sha1(Utils.serialize(finalCommit));
        File finalCommitFile =
                new File(".gitlet/commit/" + finalCommitHashing);
        Utils.writeObject(finalCommitFile, finalCommit);
        resetStage(stage);



        File Head = new File(".gitlet/branch/HEAD.txt");
        Utils.writeContents(Head, finalCommitHashing);
    }

    public void basicCheckout(String fileName) {
        Commit Head = getHeadCommit();
        TreeMap<String, String> headBlob = Head.getBlob();
        if (!headBlob.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        String HashOfFile = headBlob.get(fileName);
        File newFileOfBlob = new File (".gitlet/Blobs/blob");
        TreeMap<String, byte[]> BlobMap = Utils.readObject(newFileOfBlob,
                TreeMap.class);
        byte[] fileContents = BlobMap.get(HashOfFile);
        File forCheckout = new File(fileName);
        Utils.writeContents(forCheckout, fileContents);
    }

    public void secondCheckout(String commitID, String fileName) {
        //get commit from commits folder if the id exists.
        File commitPath = new File(".gitlet/commit/" + commitID);
        if (!commitPath.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit commitToGrab = Utils.readObject(commitPath, Commit.class);
        TreeMap<String, String> commitBlob = commitToGrab.getBlob();
        /**if (!headBlob.containsKey(fileName)) {
         System.out.println("File does not exist in that commit.");
         return;
         }*/
        String HashOfFile = commitBlob.get(fileName);
        File newFileOfBlob = new File (".gitlet/Blobs/blob");
        TreeMap<String, byte[]> BlobMap = Utils.readObject(newFileOfBlob,
                TreeMap.class);
        byte[] fileContents = BlobMap.get(HashOfFile);
        File forCheckout = new File(fileName);
        Utils.writeContents(forCheckout, fileContents);
    }
//Still will need to make it remove from current commit
    public void rm(String fileName) {
        File StageArea = new File(".gitlet/Stage/stage");
        StagingArea stage = Utils.readObject(StageArea, StagingArea.class);
        TreeMap<String, String> StagingMap = stage.getAdded();
        ArrayList<String> listToRemove = stage.getListToRemove();

        Commit head = getHead();
        TreeMap<String, String> headBlob = head.getBlob();

        if (!StagingMap.containsKey(fileName) && !headBlob.containsKey(fileName)) {
            System.out.println("No reason to remove the file.");
        }

        if (StagingMap.containsKey(fileName)) {
            StagingMap.remove(fileName);
            //File reStageFile = new File(".gitlet/Stage/stage");
            //Utils.writeObject(reStageFile, stage);
        }

        if (headBlob.containsKey(fileName)) {
            if (!listToRemove.contains(fileName)) {
                listToRemove.add(fileName);
                File fileToRemove = new File(fileName);
                //fileToRemove.delete();
                Utils.restrictedDelete(fileToRemove);
            }

        }
        File reStageFile = new File(".gitlet/Stage/stage");
        Utils.writeObject(reStageFile, stage);
        Utils.writeObject(removedFiles, listToRemove);
        /**
        else {
            removedList.add(fileName);
            File removedFile = new File(".gitlet/Removed");
            Utils.writeObject(removedFile, removedList);
        }*/
    }

    public void find(String commitMessage) {
        File commits = new File (".gitlet/commit");
        File[] commitFileArray = commits.listFiles();
        int count = 0;
        for (File file : commitFileArray) {
            Commit commit = Utils.readObject(file, Commit.class);
            if (commit.getCommitMessage().equals(commitMessage)) {
                count++;
                System.out.println(Utils.sha1(Utils.serialize(commit)));
            }
        }
        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    /**public void updateHead(){
        File Head = new File (".gitlet/branch/HEAD.txt");
        Utils.writeContents(Head, finalCommitHash);
    }*/

    public void resetStage(StagingArea stage) {
        stage.clear();
        File stageAfterCommit = new File (".gitlet/Stage/stage");
        Utils.writeObject(stageAfterCommit, stage);
    }



    public Commit getHeadCommit() {
        File headCommit = new File(".gitlet/branch/HEAD.txt");
        String parentHash = Utils.readContentsAsString(headCommit);
        File currentCommitFile = new File (".gitlet/commit/" + parentHash);
        // currentCommitFile = new File (".gitlet/commit/");
        Commit currentCommit = Utils.readObject(currentCommitFile, Commit.class);
        return currentCommit;
    }

    public void printUntrackedFiles() {
        TreeMap<String, String> stageMap = getStageMap();
        Commit currentCommit = getHeadCommit();
        TreeMap<String, String> commitBlob = currentCommit.getBlob();
        for (String fileName : Utils.plainFilenamesIn(System.getProperty(
                "user.dir"))) {
            if (!stageMap.containsKey(fileName) && !commitBlob.containsKey(fileName)) {
                System.out.println(fileName);
            }
        }
    }

}
