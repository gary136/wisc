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