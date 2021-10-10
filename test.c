#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

// #include <fcntl.h>
// #include <errno.h>

#define MAXARGS 128

size_t MAXBUFR = 1024;
char *prompt = "wish> ";
char* path[20];
int pathsize;
// char *path1 = "/bin/";
// char *path2 = "/usr/bin/";
char *builtins[3] = {"exit", "cd", "path"};     
char error_message[30] = "An error has occurred\n";
char buf[45];
char buf1[45];
char buf2[45];
int isSyscall;

struct command {
	int argc;
	char *argv[MAXARGS];
	enum builtin_t {NONE, QUIT, JOBS, BG, FG} builtin;
};

// int isSyscall (char* s) {
int translate(struct command *cmd) { 
	// check if cmd->argv[0]) is a system call
	// if not then print error message
	// if yes concatenate it with the path

	printf("checking %s syscall\n", cmd->argv[0]);

	// try path1
	// tempsyscall = syscall; 
	// temppath = path1;
	for (int i=0; i<pathsize; i++) {
		strcpy(buf, "");
		strcat(buf, path[i]);
		strcat(buf, cmd->argv[0]);
		printf("try %s\n", buf);
		if (access(buf, X_OK)!=-1) { // path[i] work
			cmd->argv[0] = buf;
			return 1;
		}
	}
	// strcpy(buf1, "");
	// strcat(buf1, path1);
	// strcat(buf1, s);
	// printf("try %s\n", buf1);
	// if (access(buf1, X_OK)!=-1) { // path1 work
	// 	// printf("c\n");
	// 	return 1;
	// } else {	// try path2		
	// 	strcpy(buf2, "");	
	// 	strcat(buf2, path2);
	// 	strcat(buf2, s);
	// 	printf("try %s\n", buf1);
	// 	if (access(buf2, X_OK)!=-1) { // path2 work
	// 		return 2;
	// 	}
	// }
	printf("Not a system call\n");
	return 0;
}

int findBuiltin (char* s) {
	int len = sizeof(builtins)/sizeof(builtins[0]);
	for(int i = 0; i < len; ++i){
		if(strcmp(builtins[i], s)==0) return 1;
	}
	return 0;
}

void parse(const char *cmdline, struct command *cmd) {
	// inilize
	cmd->argc=0;
	char *tok, *ret;
	// ret = strstr(cmdline, " ");
	
	// sep line via space
	while (strstr(cmdline, " ")) {
		// char *argv[MAXARGS];
		tok = strsep(&cmdline, " ");
		cmd->argv[cmd->argc] = tok;
		cmd->argc++;
		// printf("tok: %s, cmdline: [%s], argc:%d\n", cmd->argv[cmd->argc-1], cmdline, cmd->argc);
	}
	// add last one
	tok = cmdline;
	cmdline = "";
	cmd->argv[cmd->argc] = tok;
	cmd->argc++;
	// printf("tok: %s, cmdline: [%s], argc:%d\n", tok, cmdline, cmd->argc);

	cmd->argv[cmd->argc] = NULL; // mark the end

	for (int i=0; i<cmd->argc; i++) {
		printf("%d, cmd: %s\n", i, cmd->argv[i]);
	}
	// return tok;
}



// void translate(struct command *cmd) {
// 	// if (isSyscall(syscall)==1){
// 	// FILE *cmd=popen(syscall, "r");
//     // fgets(res, sizeof(res), cmd);  
//     // // printf("inside res: %s\n", res);
//     // pclose(cmd);}
// 	// char* syscall

// 	char* temppath;
// 	int rc;

// 	rc = isSyscall(cmd->argv[0]);	
// 	printf("rc = %d\n", rc);
// 	if (rc==0) {
// 		printf("Not a system call\n");
// 	} else if (rc==1) {
// 		strcpy(buf1, "");
// 		strcat(buf1, path1);
// 		strcat(buf1, cmd->argv[0]);
// 		cmd->argv[0] = buf1;
// 	} else if (rc==2) {
// 		strcpy(buf2, "");
// 		strcat(buf2, path2);
// 		strcat(buf2, cmd->argv[0]);
// 		cmd->argv[0] = buf2;
// 	}

// 	// // check
// 	// printf("cmd: %s", temppath);
// 	// for (int i=0; i<cmd->argc; i++) {
// 	// 	printf("%d, cmd: %s\n", i, cmd->argv[i]);
// 	// }
// }

void runCommand(struct command *cmd, int isSyscall) {
	int rc;
	if (isSyscall==1) {	
		printf("%s a system call\n", cmd->argv[0]);
		pid_t childPid = fork();
		// printf("\nchildPid: %d\n", childPid);

		if (childPid<0) {
			printf("fork fails\n");
		}

		else if (childPid==0) { // child is here				
			if (execv(cmd->argv[0], cmd->argv) < 0) {
				printf("%s: Command not found\n", cmd->argv[0]);
				exit(0);
			}			
		}
		else { //  parent where the shell continues
			// if (rc)
			// 	printf("\nChild in background [%d]\n",childPid);
			// else
			// 	wait(&childPid);			
			printf("\nChild in background [%d]\n",childPid);
			wait(&childPid);
		}
	} else {
		if (findBuiltin(cmd->argv[0])==1) {
			printf("%s a builtin cmd\n", cmd->argv[0]);
			if(strcmp(cmd->argv[0], "exit")==0) {
				exit(0);
			} else if(strcmp(cmd->argv[0], "cd")==0) {
				rc = chdir(cmd->argv[1]);
				if (rc != 0) 
					// perror("chdir() to /usr failed");
					write(STDERR_FILENO, error_message, strlen(error_message)); 
			} else if(strcmp(cmd->argv[0], "path")==0) {
				execv(cmd->argv[0], cmd->argv);

				// overwrite
				// path[0] = "";
				pathsize=cmd->argc-1;
				for (int i=0; i<pathsize; i++) {
					path[i] = cmd->argv[i+1];
				}
				// check
				printf("path is overwritten to: ");
				for (int i=0; i<pathsize; i++) {
					printf("%s, ", path[i]);
				}
				printf("\n");
			}
		} else {
			printf("neither system call nor builtin\n");
		}
	}
}

int main(int argc, char *argv[]) 
{
	// char cmdline[MAXBUFR];

	path[0] = "/bin/";
	pathsize=1;
    while (1) {
		printf("%s", prompt);
		char *cmdline;
		size_t bufsize = 32;
		size_t characters;

		cmdline = (char *)malloc(MAXBUFR * sizeof(char));
		// if( buffer == NULL)
		// {
		// 	perror("Unable to allocate buffer");
		// 	exit(1);
		// }
		
		// get input
		// printf("Type something: ");
		characters = getline(&cmdline, &MAXBUFR, stdin);
		cmdline[strlen(cmdline)-1] = '\0'; // remove '\n' from input
		// printf("%zu characters were read.\n",characters);
		printf("You typed: '%s'\n", cmdline);
		
		// if ((characters==NULL) && ferror(stdin)) {
		// // 	// error("fgets error");
		// }

		// if (feof(stdin)) {
		// 	printf("\n");
		// 	exit(0);
		// }
		struct command cmd;
		parse(cmdline, &cmd);
		isSyscall = translate(&cmd);
		runCommand(&cmd, isSyscall);

		// eval(cmdline); // evaluate and pars
		// printf("command you type: %s\n", argv[1]);

	}
      return 0;
}