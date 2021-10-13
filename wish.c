#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdbool.h>
#include <sys/types.h>  
#include <sys/wait.h>
#include <ctype.h>

size_t MAXARGS = 64;
size_t MAXBUFR = 128;
size_t line_buf_size, characters;
char *prompt = "wish> ";
char *builtins[5] = {"not used", "exit", "cd", "path", "loop"};  
char error_message[30] = "An error has occurred\n";
char *path[20];
// char buf[100];
// char loopbuf[100];
int pathsize, line_size, loopargc, isSyscall, rdrc, looptime, rvscntr;
FILE *fp;    
char *batchpath, *line_buf, *loopcmd;

struct command {
	int argc;
	char *argv[100];
	char *alterpath;
};

int allspace(char *cmdline) {
	for (int i=0; i<strlen(cmdline); i++) {
		if (isspace(cmdline[i])==0) return 0;
	}
	// printf("allspace\n");
	return 1;
}

void parse(char *cmdline, struct command *cmd) {
	// printf("\nparsing %s\n", cmdline);
	cmd->argc=0;
	// printf("is this readonly\n");
	char *tok;	
	// check allspace
	if (allspace(cmdline)==1) {
		// printf("is all space\n");
	} else {
		// sep line via space
		while (strstr(cmdline, " ")) {
			tok = strsep(&cmdline, " ");
			// printf("tok:%s|\n", tok);
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

		// // check info
		// if (cmd->argc!=0) {
		// 	printf("cmd->argc: %d | ", cmd->argc);
		// 	printf("cmd->argv: ");
		// 	for (int i=0; i<cmd->argc; i++) printf("%s, ", cmd->argv[i]);
		// 	printf("\n");
		// } else {
		// 	// printf("cmd->argc: %d\n", cmd->argc);
		// }
	}
}

int translate(struct command *cmd) { 
	// check if cmd->argv[0]) is a system call
	// if not then print error message
	// if yes concatenate it with the path

	// printf("syscall check for %s\n", cmd->argv[0]);
	for (int i=0; i<pathsize; i++) {
		// for (int i=0; i < strlen(buf); i++) buf[i] = '\0';
		char *buf;
		buf = (char *)malloc(MAXBUFR * sizeof(char));
		strcpy(buf, "");
		strcat(buf, path[i]); // /bin
		strcat(buf, "/"); // /bin/
		strcat(buf, cmd->argv[0]); // /bin/ls
		// printf("try %s\n", buf);
		if (access(buf, X_OK)!=-1) { // path[i] work
			// cmd->argv[0] = buf;
			cmd->alterpath = (char *)malloc(MAXBUFR * sizeof(char));
			cmd->alterpath = buf; // replace i cmd->argv[0]
			// printf("is system call\n");
			return 1;
		}
	}
	// printf("not a system call\n");
	return 0;
}

int findBuiltin (char* s) {
	// printf("builtin check for %s\n", s);
	int len = sizeof(builtins)/sizeof(builtins[0]);
	for(int i = 1; i < len; i++){
		if(strcmp(builtins[i], s)==0) {
			// printf("is builtin\n");
			return i;
		}
	}
	// printf("not a builtin\n");
	return 0;
}

int checkredirection (struct command *cmd) { // find the position of ">" in cmd->argv
	// printf("redirection check for %s\n", cmd->argv[0]);
	for(int i = 0; i < cmd->argc; i++) {
		// if(strcmp(cmd->argv[i], ">")==0) { 
		if(strstr(cmd->argv[i], ">")) { 
			// printf("find > in position %d | str=%s\n", i, cmd->argv[i]);
			return i;
		}
	}
	// printf("none redirection\n");
	return 0;
}

int isBad(struct command *cmd) {
	int rc;
	if (cmd->argc>1) {
		// printf("%d %s %s\n", cmd->argc, cmd->argv[0], cmd->argv[1]);
		if (strcmp(cmd->argv[0], "ls")==0) {
			rc = checkredirection(cmd); // the position of ">" in cmd->argv
			if(rc!=0 && cmd->argc-rc==1) { // ls >, rc==1, cmd->argc==2
										   // ls -al >, rc==2, cmd->argc==3
				if (strcmp(cmd->argv[rc], ">")==0) {
					write(STDERR_FILENO, error_message, strlen(error_message));
					return 1;
				}
			}
			else if(cmd->argc-rc>2) { // ls > a aaa, rc==1, cmd->argc==4
									  // ls -al > a aaa, rc==2, cmd->argc==5
				if (strcmp(cmd->argv[rc], ">")==0) {
					write(STDERR_FILENO, error_message, strlen(error_message));
					return 1;
				}
			}
		}
	}
	return 0;
}

void runCommand(struct command *cmd, int isSyscall, int rdrc) {
	pid_t childPid;
	int rc, bltn;
	if (isSyscall==1) {	// can be found under /bin
		childPid = fork();
		if (childPid<0) write(STDERR_FILENO, error_message, strlen(error_message)); 
		else if (childPid==0) { // child is here
			int badc = isBad(cmd); // check if bad 
			if (badc!=1) { // filter bad cmnd
				if (rdrc==0) { // no > in code, execute some syscall
					if (execv(cmd->alterpath, cmd->argv) < 0) {
						write(STDERR_FILENO, error_message, strlen(error_message)); 
						exit(1);
					}
				} else { // there is > in code
					// check only > or contain >
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
					} else { // cmd->argv[rdrc] == tests/p2a-test>/tmp/output11
						int position;
						char *beforeT;
						char *afterT;						
						char *Trgt;
						char x;
						Trgt = (char *)malloc(MAXBUFR * sizeof(char));
						strcpy(Trgt, "");
						strcat(Trgt, cmd->argv[rdrc]); 
						beforeT = (char *)malloc(MAXBUFR * sizeof(char));
						afterT = (char *)malloc(MAXBUFR * sizeof(char));

						// printf("detects > in %s | len=%d\n", Trgt, strlen(Trgt));
						for (int i=0; i<strlen(Trgt); i++) {
							// printf("%d\n", i);
							x = Trgt[i];
							if(strcmp(&x, ">")==0) {
								// printf("find %c in %d\n", x, i);
								position=i;
							}
						}
						// printf("detects > in %s | len=%d\n", Trgt, strlen(Trgt));
						for (int i=0; i<position; i++) {
							// printf("%d\n", i);
							beforeT[i] = Trgt[i];
						}
						for (int i=position+1; i<strlen(Trgt); i++) {
							// printf("%d %d\n", i-position-1, i);
							afterT[i-position-1] = Trgt[i];
						}
						// check
						// printf("position: %lu | beforeT %s | afterT %s\n", position, beforeT, afterT);		

						close(STDOUT_FILENO);
						open(afterT, O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU); // afterT = file
						args[0] = cmd->alterpath;
						// if (rdrc==1) args[1] = NULL;
						// else if (rdrc==2) {
						args[1] = beforeT;
						args[2] = NULL;
						// }				
					}
					if (execv(args[0], args) < 0) {
						write(STDERR_FILENO, error_message, strlen(error_message)); 
						exit(1);
					}
				}
			} else { // find bad cmnd and will not executed
				// printf("find bad cmnd\n");
			}	
		} else { //  parent where the shell continues
			// printf("Child in background [%d]\n",childPid);
			wait(&childPid);
		}
	} else { // cannot be found under /bin
		bltn = findBuiltin(cmd->argv[0]); // index of builtin in builtins
		if (bltn!=0) {
			if(bltn==1) { // exit
				if (cmd->argc > 1) 
					write(STDERR_FILENO, error_message, strlen(error_message));
				exit(0);
			} else if(bltn==2) { // cd
				rc = chdir(cmd->argv[1]);
				if (rc != 0) 
					write(STDERR_FILENO, error_message, strlen(error_message)); 
			} else if(bltn==3) { // path
				// overwrite
				pathsize=cmd->argc-1;
				for (int i=0; i<pathsize; i++) {
					// path[i] = cmd->argv[i+1];
					path[i] = (char *)malloc(MAXBUFR * sizeof(char));
					strcpy(path[i], "");
					strcat(path[i], cmd->argv[i+1]); // /bin
				}
				// // check
				// printf("path is overwritten to: ");
				// for (int i=0; i<pathsize; i++) printf("%s, ", path[i]);
				// printf("| pathsize = %d\n", pathsize);	
			} else if(bltn==4) { // loop
				// filter bad cmnd
				if (cmd->argc<2) { // no argument
					write(STDERR_FILENO, error_message, strlen(error_message)); 
				} else {
					looptime = atoi(cmd->argv[1]); 
					if (looptime<=0) {
						write(STDERR_FILENO, error_message, strlen(error_message)); 
					} else {
						// format loop 5 echo hello $loop
						// cmd->argv[0] loop
						// cmd->argv[1] looptime
						// cmd->argv[2] syscall 
						// there will not be builtin here	

						int argc = cmd->argc-2;
						int var = 999; // record index of $loop
						char *buf;
						char *args[argc];
						for (int i=0; i<argc; i++) {
							if (strcmp(cmd->argv[i+2], "$loop")==0)	{
								// printf("find $loop in cmd->argv[%d]\n", i+2);								
								var = i; // "$loop" would be recorded in args[i]
								// printf("var is overwritten to %d\n", i);	
							} // also args[var] this
							args[i] = cmd->argv[i+2];
						}
						args[argc] = NULL;						

						// // check info
						// printf("buf: %s | ", buf);
						// printf("argc: %d | ", argc);
						// printf("args: ");
						// for (int i=0; i<argc; i++) printf("%s, ", args[i]);
						// printf("\n");

						rvscntr = 1;
						while (looptime>0) {
							// replace $loop with looptime
							if (var!=999) {
								// printf("rvscntr is %d\n", rvscntr);
								// args[var]=itoa(looptime,args[var],10);
								sprintf(args[var], "%d", rvscntr); 
								// printf("args[var] = args[%d] = the old $loop = %s\n", var, args[var]);
							}
							for (int i=0; i<pathsize; i++) {
								buf = (char *)malloc(MAXBUFR * sizeof(char));
								strcpy(buf, "");
								strcat(buf, path[i]); // /bin
								strcat(buf, "/"); // /bin/
								strcat(buf, args[0]); // /bin/ls
								if (access(buf, X_OK)!=-1) { // path[i] work
								// 	cmd->alterpath = (char *)malloc(MAXBUFR * sizeof(char));
								// 	cmd->alterpath = buf; // replace i cmd->argv[0]
								// 	return 1;
									// printf("%s is valid path\n", buf);
									break;
								} else {
									// printf("%s is not valid path\n", buf);
								}
							}
							// // check info
							// printf("buf: %s | ", buf);
							// printf("argc: %d | ", argc);
							// printf("args: ");
							// for (int i=0; i<argc; i++) printf("%s, ", args[i]);
							// printf("\n");

							childPid = fork();
							if (childPid<0) {
								write(STDERR_FILENO, error_message, strlen(error_message)); 
							} else if (childPid==0) { // child is here
								if (execv(buf, args) < 0) {
									write(STDERR_FILENO, error_message, strlen(error_message)); 
									exit(1);
								}			
							} else {
								wait(&childPid);}
							looptime--;	
							rvscntr++;
						}	
					}
				}			
			}
			// printf("the builtin is executed\n");
		} else {
			write(STDERR_FILENO, error_message, strlen(error_message)); 
		}
	}
	// printf("\n");
}

// check file exists
bool file_exists(char *filename) {
    fp = fopen(filename, "r");
    bool is_exist = false;
    if (fp != NULL)
    {
        is_exist = true;
        fclose(fp); // close the file
    }
    return is_exist;
}

int main(int argc, char *argv[]) 
{
	if (argc>2) { // fault
		write(STDERR_FILENO, error_message, strlen(error_message)); 
		exit(1);
	}
	
	if (argc==2) { // batch node
		batchpath = argv[1];
		// set path parameter, would be used in translate
		path[0] = "/bin";
		pathsize=1;
		if (file_exists(batchpath)==false) { // no batch file
			write(STDERR_FILENO, error_message, strlen(error_message)); 
			exit(1);
		}
		fp = fopen(batchpath, "r");		
        line_size = getline(&line_buf, &line_buf_size, fp); /* Get the first line of the file. */
        while (line_size >= 0){ /* Loop through until we are done with the file. */ 
            size_t len = strlen(line_buf);
            if (len > 0 && line_buf[len-1] == '\n') line_buf[--len] = '\0'; /* trim */
			// check
			// printf("current path is: ");
			// for (int i=0; i<pathsize; i++) printf("%s, ", path[i]);
			// printf("| pathsize = %d\n", pathsize);	

			struct command cmd;
			parse(line_buf, &cmd);
			// printf("done parsing\n");
			if (cmd.argc!=0) {
				isSyscall = translate(&cmd);
				rdrc = checkredirection(&cmd);
				runCommand(&cmd, isSyscall, rdrc);
			}	
			// getline washes path
            line_size = getline(&line_buf, &line_buf_size, fp); /* Get the next line */
			// printf("path after getline is: ");
			// for (int i=0; i<pathsize; i++) printf("%s, ", path[i]);
			// printf("| pathsize = %d\n", pathsize);	
        }
	}
	else { // interact
		// set path parameter, would be used in translate
		path[0] = "/bin";
		pathsize=1;

		while (1) {
			printf("%s", prompt);
			char *cmdline;
			size_t characters;
			cmdline = (char *)malloc(MAXBUFR * sizeof(char));
			
			// get input
			characters = getline(&cmdline, &MAXBUFR, stdin);
			cmdline[strlen(cmdline)-1] = '\0'; // remove '\n' from input
			characters--;
			// printf("%zu characters were read.\n", characters);
			// printf("You typed: '%s'\n", cmdline);			
			if (characters==0 && ferror(stdin)) {
				write(STDERR_FILENO, error_message, strlen(error_message)); 
				exit(1);
			}
			// // check
			// printf("current path is: ");
			// for (int i=0; i<pathsize; i++) printf("%s, ", path[i]);
			// printf("| pathsize = %d\n", pathsize);	

			struct command cmd;
			parse(cmdline, &cmd);
			// printf("done parsing\n");
			// filter if all space
			// printf("cmd->argc: %d\n", cmd.argc);
			if (cmd.argc!=0) {
				isSyscall = translate(&cmd);
				rdrc = checkredirection(&cmd);
				runCommand(&cmd, isSyscall, rdrc);
			}
		}
	}
	return 0;
}