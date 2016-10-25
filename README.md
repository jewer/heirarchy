##Hierarchy Example

###How to compile
I'm assuming everyone is using an IDE so just pick your poison, as long as it's IntelliJ IDEA :-) Otherwise, 

```
git clone https://
cd heirarchy
javac src/com/ewerj/*.java
java -classpath src com.ewerj.OrgParser orgs.txt users.txt 
```
###How to run

###Thoughts
- Assumptions:


- Algorithms and Alternatives:


- At scale:
There are a ton of problems with this at scale. It needs to throw the entire list in memory in a hashmap, and that's problematic. If I was going to do this at scale, there are two options.  Secondly, the search has to find the ID in a map, which ends up being a BigO problem at some threshold.  Other options:

Quick-and-dirty, a RDBMS is built to solve problems like this, so if this is something I wanted to do occasionally, this would be really simple to import into a DB then dump it out with a common table expression (in MSSQL) or subquery/view (in everything else).

A better solution is to do a mapreduce job (Hadoop/Spark/whatever). The map steps would generate all the orgs, then the reduce phase would do a shuffle to do the associations between orgs.  Benefit of that is I could just keep scaling this out beyond a single machine, so size isn't an issue (assuming you have machines handy).

Now that I think about it, another way to do this is just to rip through the files and add everything to a graph database like neo4j or orientDB.  Then there's no thinking and we can all go back to our day jobs :-)

