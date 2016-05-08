# ParseAndIndex

JSON Parser and Indexing

This is a part of CIS 550 Final Project

The input of this part is various files on S3 (aws).
First we need to parse it into JSON file or String.
Then we do the indexing.

The Indexing is in several parts below:
1. Create new Nodes and add them into NodeTable
2. Create new Links and add them into LinkTable
3. Updata inverted index in the WordTable
4. Set the start and end nodeID
5. Send all the stuff into MongoDB

TODO:
1. Set the indexer to be static and create a new "Status" var since we cannot do this multi-threaded (Doing)
2. Don't create link between nodes in one file (Done)
3. Update the linkTable when we fetch the existed WordList
4. Save the start and end nodeID for the file