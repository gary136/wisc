The content of commandline is parsed and stored in a struct that contains argc, argv and alterpath. argc is the number of argument. argv stores the parsed argument.alterpath is the concatenation of path and argv[0].

The struct will be checked if there is a redirection or the instruction is a builtin.

Main executing part is runCommand. If instruction can be found in path directory, fork and execv the instruction in child process. Redirection will be handled here. If instruction cannot be found in path directory, it should be a builtin. Check which builtin the instruction is and execute the correpondant builtin. 