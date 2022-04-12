int Is_Number_Correct(int par1,int par2,int par3){
                        // par1 = ebp+8
                        // par2 = ebp+0xc
                        // par3 = ebp+0x10
    if(par2 == par1){ // cmp  0xc(%ebp),%eax
        // jne 1239 如果 !=，會跳去 1339 (else)
        // 不然會往下做 121f

        //121f
        // esp -= 8;
        // push par3;
        // push 0x2008;
        // call   122b
        // 這邊有點奇怪，因為程式碼不完整，但不重要

        return 1; // 把1搬到eax準備return , leave , ret
    }else{
        // 1239
        // esp -= 8
        // push par3
        // push 0x2027
        // call 1245
        // 這邊有點奇怪，因為程式碼不完整，但不重要
        return 0; // 把0搬到eax準備return , leave , ret
    }
}


int f0(){
    return 0x554a0c;
}

int f1(){
    int a = 0x61c3;  // ebp-4
    int b = 0xc882;  // ebp-8
    int c = 0x107dc; // ebp-0xc
    int d = 0x76c4;  // ebp-0x10 
    int e = 0x31;    // ebp-0x14
    int f = 0x11;    // ebp-0x18
    if(b+c <= a){
        // a = ebp-0x4
        // b = edx = ebp-0x8
        // c = eax = ebp-0xc
        // eax = eax + edx = b + c
        // "cmp %eax,-0x4(%ebp)" 代表 b + c 跟 a 比較 

        // jle 12ab <f1+0x4a> 如果小於等於，會跳到 12ab (C語言往下做)
        // 不然的話會跳到 else (組合語言往下做 12b7)

        
        if(a*b >= d){ //12ab
            // a = ebp-0x4
            // b = edx = ebp-0x8
            // d = ebp-0x10
            // "imul -0x8(%ebp),%eax" 代表 a*b， 放到 eax
            // "cmp %eax,-0x10(%ebp)" 代表 a*b 的結果跟 d比

            // jge 12c6 <f1+0x65> 結果如果 >= 的話，跳到12c6 (C語言往下做)

            //12c6
            // d = ebp-0x10
            // f = 0x11
            // eax = d
            // cltd 把 eax 的內容擴張成 64bit ，高位元放到 edx，低位元在 eax
            // idivl 拿 {edx,eax} 共64bit 當作被除數
            // "idivl  -0x18(%ebp)" 代表 把 d / f ，並把商放 eax , 餘數放 edx

            // "mov  %eax,-0x10(%ebp)" 把結果放回d
            d = d / f;
            
            // 把 d 移到 eax 準備 return
            // leave , ret
            return d;
        }else{ //12b7
            // c = ebp-0xc
            // e = ebp-0x14
            // cltd 擴展 c 到 {edx,eax}
            // idivl代表 {edx,eax} / (ebp-0x14) = c/e 並把商放eax , 餘放 edx
            // "mov %edx,-0xc(%ebp)" 代表把餘數放回 c
            c = c % e;

            // 把 c 移到 eax 準備 return
            // leave , ret
            return c;  
        }
    }else{ //12a2
        // a = ebp -4
        // "sarl  $0x4,-0x4(%ebp)" 代表 把 a 右移 4 bit
        a = a >> 4; 

        // 把 a 移到 eax 準備 return
        // leave , ret
        return a; 
    }
}

int f2(int par1){ //par1 = ebp+8
    int a=0; // ebp-4
    int b=0; // ebp-8


    while(0x1b <= a){  // 1303 
                       // cmp 0x1b,ebp-4
        // 12ef
        // 把 a 移到 eax
        // edx = a*a
        // 把 par1 放到 eax
        // eax = eax + edx  (eax = par1 + a*a)
        // ebp-8 += eax (b += a*a + par1)
        b = b + a*a + par1;

        // addl   $0x1,-0x4(%ebp) 
        a += 1;
    }

    // 把 ebp-8 移到 eax 準備 return
    // leave , ret
    return b; 
}



void f3_helper(int* par1){ //par1 = ebp+8
    int a = 3; // ebp-4

    // "mov 0x8(%ebp),%eax" 取出 par1 放到 eax
    // "mov (%eax),%edx" 把 eax "裡面存的記憶體位址" 中的值 存到 edx
    // "mov -0x4(%ebp),%eax" 把 a 移到 eax
    // "add %eax,%edx" 把 a 加上 edx 的值 存到 edx
    // "mov 0x8(%ebp),%eax" 把 par1 的值存到 eax
    // "mov %edx,(%eax)" 把 edx 的值存進 eax 指向的位址
    *par1 = (*par1 + a);
}


void f3(int par1,int* par2){ //par1 = ebp+8
                           //par2 = ebp+0xc
    // "lea 0x8(%ebp),%eax" 把 par1指向的 "位址"ebp+8 存到 eax 
    // 把 eax push 到 stack 作為參數 傳遞給f3_helper
    f3_helper(&par1);
    // "add $0x4,%esp" 把 esp + 4


    // "lea 0x8(%ebp),%eax" 把 par1指向的 "位址"ebp+8 存到 eax 
    // 把 eax push 到 stack 作為參數 傳遞給f3_helper
    f3_helper(&par1);
    // "add $0x4,%esp" 把 esp + 4

    // "0xc(%ebp),%eax" 把 ebp+0xc 的值移到 eax
    // "mov (%eax),%eax" 把 eax 指向的值 傳到 eax
    // "mov 0x8(%ebp),%edx" 把 par1 移到 edx
    // "shl $0x3,%edx" 把 edx 左移 3bit "par1<<3"
    // "add %eax,%edx" 把 eax 的值加上 edx 存到 edx (edx = *par2+ (par1<<3))

    // "mov 0xc(%ebp),%eax" 把 par2 的值(指標位址) 存到 eax
    // "mov %edx,(%eax)" 把剛剛 edx的運算結果 存到 eax 指向的位址
    *par2 = *par2+ (par1<<3);
}