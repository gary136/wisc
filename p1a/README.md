
# Projects for a simple persistent key-value store

The program supports key-value store like a database. Persistence is implemented via storing information in a local "file.txt". There are several functions as follows. 

put: The format is p,key,value, where key is an integer, and value an arbitrary string (without commas in it).

get: The format is g,key, where key is an integer. If the key is present, the program prints out key,value\n. If not present, the program prints an error message "K not found" where K is the actual value of the key.

delete: The format is d,key, which either deletes the relevant key-value pair (and prints nothing), or fails to do so (and prints K not found where K is the actual value of the key).

clear: The format is c. This command simply removes all key-value pairs from the database(i.e. the "file.txt").

all: The format is a. This command prints out all key-value pairs in the database, in any order, with the format of key,value\n.
