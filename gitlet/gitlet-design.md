# Gitlet Design Document
author: Tyler Marino

## Design Document Guidelines

Please use the following format for your Gitlet design document. Your design
document should be written in markdown, a language that allows you to nicely 
format and style a text file. Organize your design document in a way that 
will make it easy for you or a course-staff member to read.  

## 1. Classes and Data Structures
Main:
This class is the entry point of the program. It implements methods to set up persistence and support each command of the program.

Commit:
This class will handle storing and processing committed files. It will take 
in a file and go through all the necessary steps to properly commit each 
file. Cloning commit prior. Will poses a branches of commits. This is where 
the logs are stored.

Repo:
This is where the directories are stored, highest level.
Store these directories in hash table

Staging:
This is where objects that have been added are waiting for the commit will check if the item added has been modified in order for it to then be committed. Modifies the new commit.




Include here any class definitions. For each class list the instance
variables and static variables (if any). Include a ***brief description***
of each variable and its purpose in the class. Your explanations in
this section should be as concise as possible. Leave the full
explanation to the following sections. You may cut this section short
if you find your document is too wordy.


## 2. Algorithms


**Commit**:

Commit:
We will be using commit to save a snapshot of the files that we added using
add. Will only commit the items if they have been added to the staging area.
It will clear the staging area. It will never add, change or remove files in
the working directory. If no files were staged, print the line "No changes
added to the commit". Each commit has a log message, data, time. Updates the
"current commit" and the head pointer points to it.

log:
Display information about each commit backwards looks like a stack. FIFO,
newest commit on top. Commit "Tree". This displays commmit ID, date, time,
editor.

Global-log:
like log but displays info about all commits ever made. order does not
matter. useful method in gitlet.Utils to iterate over files within directory.

Checkout:
takes in either: [file name], [commit ID]--[file name], [branch name].
-Takes in the version of the file as it exists in the head commit, the front
of the
current branch and puts it into the working directory, overwriting the
version of the file that is currently there, new version not staged.
-takes the version of the file with the given id and puts it in the working
directory, overwriting the version of the file that is in there. new version
not staged
-NOT SURE HOW IT WORKS FOR [branch name] YET!!!

branch:
creates a new branch with the inputed name and poits it at the current head
node. just a reference name to a commit node. before calling this, your code
should be running with a default branch called master.

rm-branch:
Removes the branch with the name you input. Deletes just the pointer
associated with the branch, but do not delete all commits that were created
under the branch.

find:
input commit message and you will be able to find any commits with the
message. It prints out he ID of each commit.

reset:
checks out all the files tracked by given commit. Removes tracked files not
present in commit. moves current branches head to that commit node.


**Staging**:
rm:
Ustage the file if it is currently staged. If file is tracked in the current
commit, stage it for removal and remove the file from the directory. DO NOT
REMVE UNLESS IT IS TRACKED IN THE CURRENT COMMIT.

Serialize:
Will store the values with a unique hash value in a hashtable.

Add:
We add the current copy of the file we are working on to the staging area.
We do not need to add if the current and previously stored files are identical.


**Repo**
status:
displays the branches that exist, marks the current branch with a *. Will
also display the files that have been staged.


MERGE:
THIS IS THE BIGGEST ONE, WILL READ THE SPEC LATER.

init:
This will create a new nearly empty Gitlet version-control system in the
current directory. It will only posess an initial commit with no files and a
single branch


This is where you tell us how your code works. For each class, include
a high-level description of the methods in that class. That is, do not
include a line-by-line breakdown of your code, but something you would
write in a javadoc comment above a method, ***including any edge cases
you are accounting for***. We have read the project spec too, so make
sure you do not repeat or rephrase what is stated there.  This should
be a description of how your code accomplishes what is stated in the
spec.




The length of this section depends on the complexity of the task and
the complexity of your design. However, simple explanations are
preferred. Here are some formatting tips:

* For complex tasks, like determining merge conflicts, we recommend
  that you split the task into parts. Describe your algorithm for each
  part in a separate section. Start with the simplest component and
  build up your design, one piece at a time. For example, your
  algorithms section for Merge Conflicts could have sections for:

   * Checking if a merge is necessary.
   * Determining which files (if any) have a conflict.
   * Representing the conflict in the file.
  
* Try to clearly mark titles or names of classes with white space or
  some other symbols.

## 3. Persistence
As it is my first go at this, I will widen my net. We will need to save 
every time one calls add, commit, remove, rm branch, checkout, reset, branch.
While it is still Early I will be observing and adjusting where the state is 
saved.


Describe your strategy for ensuring that you don’t lose the state of your program
across multiple runs. Here are some tips for writing this section:

* This section should be structured as a list of all the times you
  will need to record the state of the program or files. For each
  case, you must prove that your design ensures correct behavior. For
  example, explain how you intend to make sure that after we call
       `java gitlet.Main add wug.txt`,
  on the next execution of
       `java gitlet.Main commit -m “modify wug.txt”`, 
  the correct commit will be made.
  
* A good strategy for reasoning about persistence is to identify which
  pieces of data are needed across multiple calls to Gitlet. Then,
  prove that the data remains consistent for all future calls.
  
* This section should also include a description of your .gitlet
  directory and any files or subdirectories you intend on including
  there.

## 4. Design Diagram
Will figure out how to upload the photo soon.
C:\Users\tyler\OneDrive\Documents\berkeley\Cs 61B\designdocdiagram1

Attach a picture of your design diagram illustrating the structure of your
classes and data structures. The design diagram should make it easy to 
visualize the structure and workflow of your program.

