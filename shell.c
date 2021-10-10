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

		cmdline = (char *)malloc(MAXBUFR * sizeof(char));
		
		// if ((characters==NULL) && ferror(stdin)) {
		// // 	// error("fgets error");
		// }

		// if (feof(stdin)) {
		// 	printf("\n");
		// 	exit(0);
		// }

		// eval(cmdline); // evaluate and pars
		// printf("command you type: %s\n", argv[1]);

	}
      return 0;
}