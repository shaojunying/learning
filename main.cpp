#include <iostream>
#include <fstream>
#include <cstring>
#include <cctype>
#include <fstream>
using namespace std;


void show(string& curr_string,ofstream& saveFile,int& state){
    if (curr_string.length()!=0) {
        saveFile << curr_string << endl;
        curr_string.clear();
    }
    state = 0;
}
/*
 * 识别每个单词符号,用记号的形式输出每个单词符号
 * 识别并跳过程序中的注释
 * 统计程序语句的行数,各类单词个数,字符总数
 * 检查词法错误
 * 对原程序出现的错误进行适当的回复
 * */
void lexical_analysis(){
    int state = 0;  // 代表当前的状态
    ofstream saveFile("../out.txt");
    string curr_string;
    while (true) {
        string string1;
        getline(cin, string1);
        if (state == 17){
            state = 0;
            curr_string.clear();
        }
        saveFile<<curr_string<<endl;
        curr_string.clear();
        if (string1 == "bye") {
            if (curr_string.length()!=0){
                show(curr_string,saveFile,state);
            }
            break;
        }
        for (int i = 0; i < string1.length(); ++i) {
            char a = string1[i];
            if (isspace(string1[i])) {
                /*遇到空白字符*/
                if (curr_string.length()!=0){
                    show(curr_string,saveFile,state);
                }
                continue;
            }
            switch (state) {
                case 0:
                    if (isalpha(a)) {
                        curr_string.push_back(a);
                        state = 1;
                    } else if (isdigit(a)) {
                        curr_string.push_back(a);
                        state = 2;
                    } else {
                        switch (a) {
                            case '<':
                                curr_string.push_back(a);
                                state = 8;
                                break;
                            case '>':
                                curr_string.push_back(a);
                                state = 9;
                                break;
                            case ':':
                                curr_string.push_back(a);
                                state = 10;
                                break;
                            case '+':
                                curr_string.push_back(a);
                                state = 14;
                                break;
                            case '-':
                                curr_string.push_back(a);
                                state = 13;
                                break;
                            case '*':
                                curr_string.push_back(a);
                                state = 15;
                                break;
                            case '/':
                                curr_string.push_back(a);
                                state = 11;
                                break;
                            case '=':
                            default:
                                /*此处说明输入的内容无效,不用做任何处理*/
                                break;
                        }
                    }
                    break;
                case 1:
                    if (isalpha(a) || isdigit(a)) {
                        /*此处一直循环读取字母和数字*/
                        curr_string.push_back(a);
                        state = 1;
                    } else {
                        /*遇到其他内容,需要输出并回退一格*/
                        show(curr_string,saveFile,state);
                        i--;
                    }
                    break;
                case 2:
                    if (a == 'E') {
                        curr_string.push_back(a);
                        state = 5;
                    } else if (a == '.') {
                        curr_string.push_back(a);
                        state = 3;
                    } else if (isdigit(a)) {
                        curr_string.push_back(a);
                        state = 2;
                    } else {
                        /*需要回退*/
                        show(curr_string,saveFile,state);
                        i--;
                    }
                    break;
                case 3:
                    if (isdigit(a)) {
                        curr_string.push_back(a);
                        state = 4;
                    }
                    break;
                case 4:
                    if (isdigit(a)) {
                        curr_string.push_back(a);
                        state = 4;
                    } else if (a == 'E') {
                        curr_string.push_back(a);
                        state = 5;
                    } else {
                        /*回退*/
                        show(curr_string,saveFile,state);
                        i--;
                    }
                    break;
                case 5:
                    if (isdigit(a)) {
                        curr_string.push_back(a);
                        state = 7;
                    } else if (a == '+' || a == '-') {
                        curr_string.push_back(a);
                        state = 6;
                    }
                    break;
                case 6:
                    if (isdigit(a)) {
                        curr_string.push_back(a);
                        state = 7;
                    }else {
                        /*回退*/
                        show(curr_string,saveFile,state);
                        i--;
                    }
                    break;
                case 8:
                    switch (a) {
                        case '=':
                            show(curr_string,saveFile,state);
                            break;
                        case '>':
                            show(curr_string,saveFile,state);
                            break;
                        default:
                            /*回退*/
                            show(curr_string,saveFile,state);
                            i--;
                            break;
                    }
                    break;
                case 9:
                    if (a == '=') {
                        curr_string.push_back(a);
                        show(curr_string,saveFile,state);
                    } else{
                        /*回退*/
                        show(curr_string,saveFile,state);
                        i--;
                    }
                    break;
                case 10:
                    if (a == '=') {
                        curr_string.push_back(a);
                        show(curr_string,saveFile,state);
                    } else{
                        /*回退*/
                        show(curr_string,saveFile,state);
                        i--;
                    }
                    break;
                case 11:
                    /*此处遇到了/符号,可能要开始处理注释*/
                    switch (a){
                        case '*':
                            /*多行注释的开始*/
                            curr_string.clear();
                            state = 16;
                            break;
                        case '/':
                            /*单行注释的开始*/
                            curr_string.push_back(a);
                            curr_string.clear();
                            state = 17;
                            break;
                        case '=':
                            /*简写的除法*/
                            curr_string.push_back(a);
                            state = 18;
                            break;
                        default:
                            show(curr_string,saveFile,state);
                            i--;
                            break;
                    }
                    break;
                    /* 初始state输入了减号,需要判断
                     * -
                     * -=
                     * --
                     * 负数
                     * */
                case 13:
                    if (isdigit(a)){
                        curr_string.push_back(a);
                        state = 2;
                    } else{
                        switch (a){
                            case '-':
                                curr_string.push_back(a);
                                show(curr_string,saveFile,state);
                                break;
                            case '=':
                                curr_string.push_back(a);
                                show(curr_string,saveFile,state);
                                break;
                            default:
                                show(curr_string,saveFile,state);
                                i--;
                                break;
                        }
                    }
                    break;
                case 14:
                    if (isdigit(a)){
                        curr_string.push_back(a);
                        state = 2;
                    } else{
                        switch (a){
                            case '+':
                                curr_string.push_back(a);
                                show(curr_string,saveFile,state);
                                break;
                            case '=':
                                curr_string.push_back(a);
                                show(curr_string,saveFile,state);
                                break;
                            default:
                                show(curr_string,saveFile,state);
                                i--;
                                break;
                        }
                    }
                    break;
                case 15:
                    if (isdigit(a)){
                        curr_string.push_back(a);
                        state = 2;
                    } else{
                        switch (a){
                            case '=':
                                curr_string.push_back(a);
                                show(curr_string,saveFile,state);
                                break;
                            default:
                                show(curr_string,saveFile,state);
                                i--;
                                break;
                        }
                    }
                    break;
                case 16:
                    /*多行注释*/
                    if (a=='*'){
                        curr_string.clear();
                        state=19;
                    }
                    break;
                case 17:
                    /*单行注释
                     * 我们这里不进行处理,只需要在每一次读取完一行之后判断是否处于17,
                     * 如果是,就重置为0
                     * */
                    curr_string.clear();
                    break;
                case 18:
                    /*简写除法/= */
                    break;
                case 19:
                    switch (a){
                        case '*':
                            state = 19;
                            break;
                        case  '/':
                            state = 0;
                            break;
                        default:break;
                    }
                default:
                    break;
            }
        }
    }
    saveFile.close();
}

int main() {
    lexical_analysis();
    return 0;
}