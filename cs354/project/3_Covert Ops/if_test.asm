Is_Number_Correct:
	endbr32
	pushl	%ebp
	movl	%esp, %ebp
	movl	8(%ebp), %eax -> eax = ans

	cmpl	12(%ebp), %eax 
	-> Evaluates if %eax-12(%ebp) < 0
	-> Evaluates if ans-guess < 0
	-> reverse the sequence in condional
    if (guess>ans)

	assume the opposite when first jump

	not true then jump L2
	jle	.L2 

	true then execute this
	movl	$0, %eax -> return 0
	jmp	.L3

.L2:
	movl	$1, %eax -> return 1 

.L3:
	popl	%ebp
	ret
	.size	Is_Number_Correct, .-Is_Number_Correct
	.globl	main
	.type	main, @function

main:
	endbr32
	pushl	%ebp
	movl	%esp, %ebp
	subl	$16, %esp
	pushl	$8
	pushl	$2
	pushl	$1
	call	Is_Number_Correct
	addl	$12, %esp
	movl	%eax, -4(%ebp)
	movl	$0, %eax
	leave
	ret