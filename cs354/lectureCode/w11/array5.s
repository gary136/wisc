	.file	"array5.c"
	.text
	.globl	main
	.type	main, @function
main:
	endbr32
	pushl	%ebp
	movl	%esp, %ebp
	subl	$80, %esp
	movl	$11, -72(%ebp)
	movl	$12, -68(%ebp)
	movl	$13, -64(%ebp)
	movl	$21, -60(%ebp)
	movl	$22, -56(%ebp)
	movl	$23, -52(%ebp)
	movl	$31, -48(%ebp)
	movl	$32, -44(%ebp)
	movl	$33, -40(%ebp)
	movl	$41, -36(%ebp)
	movl	$42, -32(%ebp)
	movl	$43, -28(%ebp)
	movl	$51, -24(%ebp)
	movl	$52, -20(%ebp)
	movl	$53, -16(%ebp)
	movl	$3, -4(%ebp)
	movl	$1, -8(%ebp)
	movl	-4(%ebp), %edx
	movl	%edx, %eax
	addl	%eax, %eax
	addl	%edx, %eax
	movl	-8(%ebp), %edx
	addl	%edx, %eax
	movl	-72(%ebp,%eax,4), %eax
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
