Is_Number_Correct:
	endbr32
	pushl	%ebp
	movl	%esp, %ebp
	subl	$16, %esp
	movl	$0, -4(%ebp)
	jmp	.L2
.L3:
	subl	$1, 8(%ebp)
	addl	$1, -4(%ebp)
.L2:
	movl	8(%ebp), %eax
	cmpl	12(%ebp), %eax
	jg	.L3
	movl	-4(%ebp), %eax
	leave
	ret
	.size	Is_Number_Correct, .-Is_Number_Correct
	.globl	main
	.type	main, @function
main:
	endbr32
	pushl	%ebp
	movl	%esp, %ebp
	subl	$16, %esp
	pushl	$1
	pushl	$4
	call	Is_Number_Correct
	addl	$8, %esp
	movl	%eax, -4(%ebp)
	movl	$0, %eax
	leave
	ret
	.size	main, .-main
	.ident	"GCC: (Ubuntu 9.3.0-17ubuntu1~20.04) 9.3.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 4
	.long	 1f - 0f
	.long	 4f - 1f
	.long	 5
