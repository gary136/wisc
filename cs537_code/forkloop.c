void parse(char *cmdline, struct command *cmd) {
	printf("\nparsing %s\n", cmdline);
	// inilize
	cmd->argc=0;
	printf("is this readonly\n");
	char *tok;	
	// check allspace
	if (allspace(cmdline)==1) {
		// cmd->argv[cmd->argc] = NULL; // mark the end
	} else {
		// sep line via space
		while (strstr(cmdline, " ")) {
			tok = strsep(&cmdline, " ");
			printf("tok:%s|\n", tok);
			if(strcmp(tok, "")!=0 && strcmp(tok, "\t")!=0) {
				cmd->argv[cmd->argc] = tok;
				cmd->argc++;
			} else {
				// printf("isspace\n");
			}
		}
		// add last one
		tok = cmdline;
		cmdline = "";
		if(strcmp(tok, "")!=0 && strcmp(tok, "\t")!=0) {
			cmd->argv[cmd->argc] = tok;
			cmd->argc++;
		} else {
			// printf("isspace\n");
		}
		cmd->argv[cmd->argc] = NULL; // mark the end
		// check info
		if (cmd->argc!=0) {
			printf("cmd->argc: %d | ", cmd->argc);
			printf("cmd->argv: ");
			for (int i=0; i<cmd->argc; i++) printf("%s, ", cmd->argv[i]);
			printf("\n");
		} else {
			// printf("cmd->argc: %d\n", cmd->argc);
		}
	}
}

int translate(struct command *cmd) { 
	// check if cmd->argv[0]) is a system call
	// if not then print error message
	// if yes concatenate it with the path

	// printf("syscall check for %s\n", cmd->argv[0]);
	for (int i=0; i<pathsize; i++) {
		char *buf;
		buf = (char *)malloc(MAXBUFR * sizeof(char));
		strcpy(buf, "");
		strcat(buf, path[i]); // /bin
		strcat(buf, "/"); // /bin/
		strcat(buf, cmd->argv[0]); // /bin/ls
		if (access(buf, X_OK)!=-1) { // path[i] work
			cmd->alterpath = (char *)malloc(PATHBUFR * sizeof(char));
			cmd->alterpath = buf; // replace i cmd->argv[0]
			return 1;
		}
	}
	// printf("not a system call\n");
	return 0;
}

char *args[3];
if(strcmp(cmd->argv[rdrc], ">")==0) { // rdrc = index of ">" in cmd->argv
    close(STDOUT_FILENO);
    open(cmd->argv[rdrc+1], O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU); // rdrc+1 = index of file
    args[0] = cmd->alterpath; 
    if (rdrc==1) args[1] = NULL;
    else if (rdrc==2) {
        args[1] = cmd->argv[1];
        args[2] = NULL;
	}

while (looptime>0) {
    childPid = fork();
    if (childPid<0) {
        write(STDERR_FILENO, error_message, strlen(error_message)); 
    }
    else if (childPid==0) {
        // execv(args[0], args);
        runCommand(loop, isSyscall, rdrc);
    }	
    else { //  parent where the shell continues
        printf("Child in background [%d]\n",childPid);
        wait(&childPid);
    }
    looptime--;			
}

while (looptime>0) {
    childPid = fork();
    if (childPid<0) {
        write(STDERR_FILENO, error_message, strlen(error_message)); 
    }
    else if (childPid==0) {
        // execv(args[0], args);
        runCommand(loop, isSyscall, rdrc);
    }	
    else { //  parent where the shell continues
        printf("Child in background [%d]\n",childPid);
        wait(&childPid);
    }	
    // do the runCommand?	
    if (isSyscall==1) {	
        childPid = fork();
        // printf("\nchildPid: %d\n", childPid);

        if (childPid<0) {
            write(STDERR_FILENO, error_message, strlen(error_message)); 
        }

        else if (childPid==0) { // child is here
            // check if redirection		
            if (rdrc!=0) { // rdrc=index of ">" in cmd->argv
                close(STDOUT_FILENO);
                open(loop->argv[rdrc+1], O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU);

                char * args[3];
                args[0] = loop->argv[0];
                args[1] = loop->argv[1];
                args[2] = NULL;
                if (execv(args[0], args) < 0) {
                    printf("%s: Command not found\n", args[0]);
                    write(STDERR_FILENO, error_message, strlen(error_message)); 
                    exit(1);
                }
            }else {
                if (execv(loop->argv[0], loop->argv) < 0) {
                    printf("%s: Command not found\n", cmd->argv[0]);
                    write(STDERR_FILENO, error_message, strlen(error_message)); 
                    exit(1);
                }	
            }		
        }
        else { //  parent where the shell continues
            printf("Child in background [%d]\n",childPid);
            wait(&childPid);
        }
    }

    looptime--;		
    printf("looptime remains: %d\n", looptime);				
}

						// check
						// printf("argc: %d\n", argc);
						// for (int i=0; i<argc; i++) {					
						// 	// strcat(loopbuf, cmd->argv[i]);
						// 	printf("args[%d]: %s\n", i, args[i]);
						// 	// if (i!=cmd->argc-1) strcat(loopbuf, " ");
						// }
						args[argc] = NULL;
						// // reconstruct cmd with space
						// char *loopbuf;
						// loopbuf = (char *)malloc(MAXBUFR * sizeof(char));
						// // printf("%d\n", strlen(loopbuf));
						// // for (int i=0; i <100; i++) loopbuf[i] = '\0';
						// // printf("container: %s\n", loopbuf);
						// strcpy(loopbuf, "");
						// for (int i=2; i<cmd->argc; i++) {					
						// 	strcat(loopbuf, cmd->argv[i]);
						// 	if (i!=cmd->argc-1) strcat(loopbuf, " ");
						// }
						// printf("container: %s\n", loopbuf);

						// // create another sturct to rec
						// struct command *loop;						
						// parse(loopbuf, loop);
						// printf("manully parse without struct\n");	
						// no need to parse becasue argv exists	

						// isSyscall = translate(loop);
						// printf("manully translate without struct\n");
						// no need to translate since argv[0] must be syscall

				// parse and save
				// loopargc = cmd->argc-2; // waive 0 1
				// char* loopargs[loopargc];
				// printf("cmd->argc: %d, loopargc: %d\n", cmd->argc, loopargc);
				// for (int i=0; i<loopargc; i++) {
				// 	loopargs[i] = cmd->argv[i+2]; // echo, hello, $loop
				// }
				// loopargs[loopargc] = NULL;	

				// loop->argc = cmd->argc-2; // waive 0 1
				// for(int i = 0; i < loop->argc; i++) {
				// 	loop->argv[i] = cmd->argv[i+2];
				// }
				// loop->argv[loop->argc] = NULL;
				// // check
				// printf("argc: %d\n", loop->argc);
				// for (int i=0; i<loop->argc; i++) {
				// 	printf("argv[%d]: %s\n", i,loop->argv[i]);
				// }
                // // create a struct
				// struct command *loop;	
				// loop->argc = loopargc;	
				// // loop->argv=loopargs; will not work
				// // copy the array
				// // loop->argv = malloc(47 * sizeof(char*));
				// for(int i = 0; i < loopargc; i++) {
				// 	loop->argv[i] = loopargs[i];
				// }
				// // check
				// printf("looptime: %d\n", loop->argc);
				// for (int i=0; i<loop->argc; i++) {
				// 	printf("argv[%d]: %s\n", i,loop->argv[i]);
				// }

				strcpy(buf, "");
				for (int i=0; i<loopargc; i++) {					
					strcat(buf, loopargs[i]);
					strcat(buf, " ");
				}
				buf[strlen(buf)-1] = '\0'; // remove " " from input
				// printf("%s\n", buf);
				// printf("\n");
				// isSyscall = translate(loop);
				// rdrc = checkredirection(loop);				
				while (looptime>0){
					system(buf);
					// system("echo hello");
					looptime--;	
				}	