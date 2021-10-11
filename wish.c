#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdbool.h>
#include <sys/types.h>  
#include <sys/wait.h>

size_t MAXARGS = 128;
size_t MAXBUFR = 1024;
size_t PATHBUFR = 200;
char *prompt = "wish> ";
char *path[20];
int pathsize, line_size;
size_t line_buf_size;
int loopargc;
char *builtins[5] = {"not used", "exit", "cd", "path", "loop"};  
char error_message[30] = "An error has occurred\n";
// char buf[100];
// char loopbuf[100];
int isSyscall, rdrc, looptime;
FILE *fp;    
char *batchpath, *line_buf, *loopcmd;

struct command {
	int argc;
	char *argv[100];
	char *alterpath;
};

void parse(char *cmdline, struct command *cmd) {
	// check info
	// printf("\nparsing %s\n", cmdline);

	// inilize
	cmd->argc=0;
	char *tok;	
	// sep line via space
	while (strstr(cmdline, " ")) {
		tok = strsep(&cmdline, " ");
		cmd->argv[cmd->argc] = tok;
		cmd->argc++;
	}
	// add last one
	tok = cmdline;
	cmdline = "";
	cmd->argv[cmd->argc] = tok;
	cmd->argc++;
	cmd->argv[cmd->argc] = NULL; // mark the end

	// check info
	// printf("after parsing\n");
	// printf("cmd->argc: %d | ", cmd->argc);
	// printf("cmd->argv: ");
	// for (int i=0; i<cmd->argc; i++) printf("%s, ", cmd->argv[i]);
	// printf("\n");
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
			cmd->alterpath = (char *)malloc(PATHBUFR * sizeof(char));
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
					char * args[3];
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
						printf("detects > in %s | len=%d\n", cmd->argv[rdrc], strlen(cmd->argv[rdrc]));
						int position;
						char* beforeT;
						char* afterT;
						beforeT = (char *)malloc(MAXBUFR * sizeof(char));
						afterT = (char *)malloc(MAXBUFR * sizeof(char));
						for (int i=0; i<strlen(cmd->argv[rdrc]); i++) {
							if(strcmp(cmd->argv[rdrc][i], ">")==0) position=i;
						}
						for (int i=0; i<position; i++) {
							beforeT[i] = cmd->argv[rdrc][i];
						}
						for (int i=position; i<strlen(cmd->argv[rdrc]); i++) {
							afterT[i] = cmd->argv[rdrc][i];
						}
						// check
						printf("position: %d beforeT %s afterT %s", position, beforeT, afterT);		

						// close(STDOUT_FILENO);
						// open(cmd->argv[rdrc+1], O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU); // rdrc+1 = index of file
						// args[0] = cmd->alterpath;
						// if (rdrc==1) args[1] = NULL;
						// else if (rdrc==2) {
						// 	args[1] = cmd->argv[1];
						// 	args[2] = NULL;
						}				
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
					path[i] = cmd->argv[i+1];
				}
				// check
				// printf("path is overwritten to: ");
				// for (int i=0; i<pathsize; i++) printf("%s, ", path[i]);
				// printf("\n");
			} else if(bltn==4) { // loop
				// format loop 5 echo hello $loop
				// cmd->argv[0] loop
				// cmd->argv[1] looptime
				// cmd->argv[2] syscall 
				// there will not be builtin here

				looptime = atoi(cmd->argv[1]); 
				// printf("looptime: %d\n", looptime);
				// create another sturct to rec
				struct command *loop;	
				// reconstruct cmd
				char *loopbuf;
				loopbuf = (char *)malloc(MAXBUFR * sizeof(char));
				// printf("%d\n", strlen(loopbuf));
				// for (int i=0; i <100; i++) loopbuf[i] = '\0';
				// printf("container: %s\n", loopbuf);
				strcpy(loopbuf, "");
				for (int i=2; i<cmd->argc; i++) {					
					strcat(loopbuf, cmd->argv[i]);
					if (i!=cmd->argc-1) strcat(loopbuf, " ");
				}
				// printf("container: %s\n", loopbuf);
				parse(loopbuf, loop);
				// printf("container: %s\n", loopbuf);
				isSyscall = translate(loop);
				while (looptime>0) {
					childPid = fork();
					// printf("\nchildPid: %d\n", childPid);
					if (childPid<0) {
						write(STDERR_FILENO, error_message, strlen(error_message)); 
					}
					else if (childPid==0) { // child is here
						if (execv(loop->alterpath, loop->argv) < 0) {
							// printf("%s: Command not found\n", loop->alterpath);
							write(STDERR_FILENO, error_message, strlen(error_message)); 
							exit(1);
						}			
					}
					else { //  parent where the shell continues
						// printf("Child in background [%d]\n",childPid);
						wait(&childPid);
					}
					looptime--;	
				}				
			}
			// free(loopbuf);
			// printf("the builtin is executed\n");
		} else {
			// printf("not a valid command\n");
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
	if (argc==2) { // batch node
		batchpath = argv[1];
		if (file_exists(batchpath)==false) {
			write(STDERR_FILENO, error_message, strlen(error_message)); 
			exit(1);
		}
		fp = fopen(batchpath, "r");
		/* Get the first line of the file. */
        line_size = getline(&line_buf, &line_buf_size, fp);
        /* Loop through until we are done with the file. */
        while (line_size >= 0){
            /* trim */
            size_t len = strlen(line_buf);
            if (len > 0 && line_buf[len-1] == '\n') line_buf[--len] = '\0';

            path[0] = "/bin";
			pathsize=1;
			size_t bufsize = 32;
			size_t characters;
			// printf("Command this: '%s'\n", line_buf);

			struct command cmd;
			parse(line_buf, &cmd);
			isSyscall = translate(&cmd);
			rdrc = checkredirection(&cmd);
			runCommand(&cmd, isSyscall, rdrc);

			/* Get the next line */
            line_size = getline(&line_buf, &line_buf_size, fp);
        }
	}
	else {
		path[0] = "/bin";
		pathsize=1;
		while (1) {
			printf("%s", prompt);
			char *cmdline;
			size_t bufsize = 32;
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

			struct command cmd;
			parse(cmdline, &cmd);
			isSyscall = translate(&cmd);
			rdrc = checkredirection(&cmd);
			runCommand(&cmd, isSyscall, rdrc);
		}
	}
	return 0;
}