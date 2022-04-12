	.file	"array4.c"
	.text
	.globl	main
	.type	main, @function
main:
	endbr32
	pushl	%ebp
	movl	%esp, %ebp
	subl	$32, %esp
	movl	$10, -32(%ebp)
	movl	$20, -28(%ebp)
	movl	$30, -24(%ebp)
	movl	$40, -20(%ebp)
	movl	$50, -16(%ebp)
	movl	$2, -4(%ebp)
	movl	-4(%ebp), %eax
	movl	-32(%ebp,%eax,4), %eax
	movl	%eax, -8(%ebp)
	movl	-4(%ebp), %eax
	leal	0(,%eax,4), %edx
	leal	-32(%ebp), %eax
	addl	%edx, %eax
	movl	%eax, -12(%ebp)
	movl	$0, %eax
	leave
	ret
	.size	main, .-main
	.ident	"GCC: (Ubuntu 9.3.0-10ubuntu2) 9.3.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 4
	.long	 1f - 0f
	.long	 4f - 1f
	.long	 5
0:
	.string	 "GNU"
1:
	.align 4
	.long	 0xc0000002
	.long	 3f - 2f
2:
	.long	 0x3
3:
	.align 4
4:
