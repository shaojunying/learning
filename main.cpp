#include <iostream>
#include <fstream>
#include <cstring>
#include <cctype>
#include <fstream>
using namespace std;


void show(string& curr_string,ofstream& saveFile){
    saveFile<<curr_string<<endl;
    curr_string.clear();
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
        if (string1 == "bye") {
            break;
        }
        for (int i = 0; i < string1.length(); ++i) {
            char a = string1[i];
            if (isspace(string1[i])) {
                /*遇到空白字符*/
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
                            case '/':
                                curr_string.push_back(a);
                                state = 11;
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case 1:
                    if (isalpha(a) || isdigit(a)) {
                        curr_string.push_back(a);
                        state = 1;
                    } else {
                        show(curr_string,saveFile);
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
                        show(curr_string,saveFile);
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
                        show(curr_string,saveFile);
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
                        show(curr_string,saveFile);

                    }
                    break;
                case 8:
                    switch (a) {
                        case '=':
                            show(curr_string,saveFile);
                            break;
                        case '>':
                            show(curr_string,saveFile);
                            break;
                        default:
                            show(curr_string,saveFile);
                            break;
                    }
                    break;
                case 9:
                    if (a == '=') {
                        show(curr_string,saveFile);
                    } else{
                        show(curr_string,saveFile);
                    }
                    break;
                case 10:
                    if (a == '=') {
                        show(curr_string,saveFile);
                    } else{
                        show(curr_string,saveFile);
                    }
                    break;
                case 11:
                    if (a == '*') {
                        curr_string.push_back(a);
                        state = 12;
                    } else {
                        show(curr_string,saveFile);
                    }
                    break;
                case 12:
                    show(curr_string,saveFile);
                    break;
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