.file   "junk.c++"
    .text
.globl _Z6addtwoi
    .type   _Z6addtwoi, @function
_Z6addtwoi:
.LFB2:
    pushl   %ebp
.LCFI0:
    movl    %esp, %ebp
.LCFI1:
    subl    $16, %esp
.LCFI2:
    movl    $2, -4(%ebp)
    movl    -4(%ebp), %edx
    movl    8(%ebp), %eax
    addl    %edx, %eax
    leave
    ret
.LFE2:
    .size   _Z6addtwoi, .-_Z6addtwoi
    .ident  "GCC: (Ubuntu 4.3.3-5ubuntu4) 4.3.3"
    .section    .note.GNU-stack,"",@progbits

pushl   %ebp
movl    %esp, %ebp

subl    $4, %esp
movl    %ebp, (%esp)
movl    %esp, %ebp