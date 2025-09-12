## desa's Ranker

This application can be used to rank a list of entries by comparing two entries at a time.

Requires installation of Java SE Development Kit. It's been developed on corretto-17 JDK
and tested with the currently most recent JDK 24 as well with fully available functionality.
Java SE Development Kits can be found here https://www.oracle.com/java/technologies/downloads/

To use the application, make sure to have a Tab-Seperated-Values list (.tsv file) in the same 
directory as the .jar file. You are then presented with two choices at a time and have to
pick which one you prefer.

For longer lists there is an automatic save function that applies whenever a new entry has been
fully established in its ranking. Reopening the application at a later time will then load
your progress.

### Files

Ranker.jar at this time requires a .tsv file with 2 columns. The first column presents the name
of the entries while the second column provides a description.

The output will then be two files that save your progress as well as a final ranking file that
contains the order of entries from worst to best along with a score for each entry.

### Future Updates

Future Updates will feature:

* An undo option to revert your most recent choice
* Alternative point distribution algorithms
* Merging of several rankings into one