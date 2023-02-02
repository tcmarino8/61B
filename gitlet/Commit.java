package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TreeMap;


public class Commit implements Serializable {
    String CommitMessage;
    TreeMap<String, String> blob;
    Date date;
    Timestamp timeStamp;
    String timeStampStr;
    String parent;
    String serialized;

    public Commit(String message, TreeMap<String, String> staged,
                  boolean first, String parentHash) {
        parent = parentHash;
        CommitMessage = message;
        blob = staged;
        //serialized = Utils.sha1(Utils.serialize(this));
        //Parent file; (should be the current commit that master points too
        if (first) {
            date = new Date(0);
            timeStamp = new Timestamp(date.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd " + "HH:mm:ss");
            timeStampStr = formatter.format(timeStamp);
        } else {
            date = new Date();
            timeStamp = new Timestamp(date.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd " + "HH:mm:ss");
            timeStampStr = formatter.format(timeStamp);
        }
    }
    public void initialCommit() {
        blob = null;
        timeStampStr =  "1970-01-01 00:00:00";
        CommitMessage = "initial commit";
    }
    //Date: Wed Dec 31 16:00:00 1969 -0800


    public String getTimeStamp() {
        SimpleDateFormat newFormat = new SimpleDateFormat("E MMM dd HH:mm:ss " +
                "yyyy");
        String timeStamper = newFormat.format(timeStamp) + " -0800";
        return  timeStamper;
    }

    public TreeMap<String, String> getBlob() {
        if (blob == null) {
            blob = new TreeMap<>();
        }
        return blob;
    }

    public String getCommitMessage() {
        return CommitMessage;
    }

    public String getParent() {
        return parent;
    }




}
