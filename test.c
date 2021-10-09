#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

// #include <fcntl.h>
// #include <errno.h>

#define MAXARGS 128

size_t MAXBUFR = 1024;
char *prompt = "wish> ";
char *syscalls[3] = {"ls", "pwd", "cd"}; 

struct command {
	int argc;
	char *argv[MAXARGS];
	enum builtin_t {NONE, QUIT, JOBS, BG, FG} builtin;
};

int isSyscall (char* s) {
	int len = sizeof(syscalls)/sizeof(syscalls[0]);
	for(int i = 0; i < len; ++i){
		if(!strcmp(syscalls[i], s)) return 0;
	}
	return 1;
}

char* parse(const char *cmdline, struct command *cmd) {
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
		printf("tok: %s, cmdline: [%s], argc:%d\n", cmd->argv[cmd->argc-1], cmdline, cmd->argc);
	}
	// add last one
	tok = cmdline;
	cmdline = "";
	cmd->argv[cmd->argc] = tok;
	cmd->argc++;
	printf("tok: %s, cmdline: [%s], argc:%d\n", tok, cmdline, cmd->argc);

	// cmd->argv[cmd->argc] = NULL; // mark the end

	// for (int i=0; i<cmd->argc; i++) {
	// 	printf("%d, cmd: %s\n", i, cmd->argv[i]);
	// }
	return tok;
}

// int parse(const char *cmdline, struct command *cmd){
	// char array[MAXBUFR];
	// const char delims[10] = " \t\r\\n";
	// char *line = array;
	// char *token, *endline;
	// int is_bg;

	// // if (cmdline==NULL)

	// (void) strncpy(line, cmdline, MAXBUFR);
	// endline = line + strlen(line);
	// printf("line: %s, endline: %s\n", line, endline);

	// cmd->argc=0;

	// while (line<endline) {
	// 	line +=strspn(line, delims);
	// 	if (line>=endline) break;

	// 	token = line + strcspn(line, delims);

	// 	*token = '\0';

	// 	cmd->argv[cmd->argc++] = line;
		
	// 	if (cmd->argc >= MAXARGS-1) break;
	// 	line = token + 1;
	// }

	// cmd->argv[cmd->argc] = NULL;

	// if (cmd->argc==0) return 1;

	// // cmd->builtin = parseBuiltin(cmd);

	// if ((is_bg = (*cmd->argv[cmd->argc-1]=='&')) != 0)
	// 	cmd->argv[--cmd->argc] = NULL;
	
	// return is_bg;	
// }

void translate(char* syscall, char* res) {
	if (isSyscall(syscall)==1){
	FILE *cmd=popen(syscall, "r");
    fgets(res, sizeof(res), cmd);  
    // printf("inside res: %s\n", res);
    pclose(cmd);}

}

void runSystemcommand(struct command *cmd, int rc) {
	pid_t childPid = fork();
	// printf("\nchildPid: %d\n", childPid);

	if (childPid<0) {
		printf("fork fails\n");
	}

	else if (childPid==0) { // child is here
		if (execvp(cmd->argv[0], cmd->argv) < 0) {
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

}
// void eval (char *cmdline) {
// 	int rc;
// 	struct command cmd;
// 	printf("evaluating command: %s\n", cmdline);
// 	rc = parse(cmdline, &cmd);

// 	printf("Found command %s\n", cmd.argv[0]);

// 	if (rc==-1) return;
// 	if (cmd.argv[0]==NULL) return;

// 	if (cmd.builtin==NULL)
// 		runSystemcommand(&cmd, rc);
// 	else
// 		// runBuiltinCommand(&cmd, rc);
// 		;
// }

int main(int argc, char *argv[]) 
{
	// char cmdline[MAXBUFR];

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

		runSystemcommand(&cmd, 1);

		// eval(cmdline); // evaluate and pars
		// printf("command you type: %s\n", argv[1]);

	}
      return 0;
}