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
    runCommand(loop, isSyscall, rdrc);	

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