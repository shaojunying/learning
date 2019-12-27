#include <list>
#include <iostream>

using namespace std;
//
// Created by shao on 2018/11/29.
//

// 声明包含所有字符的枚举
enum Character{
    add,
    subtract,
    multiply,
    divide,
    left_bracket,
    right_bracket,
    num,
    dollar,
    E,
    T,
    F,
    E_,
};

string input = "(num+num*num)";
string index_to_char[] = {"+","-","*","/","(",")","num","$","E","T","F","E_"};

/*
 * 声明状态表
 * 纵向共11个字符
 * 横向共29个状态
 * 0    表示出错
 * 1xx  表示sxx
 * 2xx  表示rxx
 * 300  表示acc
 * */
int table[30][11]={{0,0,0,0,104,0,105,0,101,102,103},
                   {106,107,0,0,0,0,0,300,0,0,0},
                   {203,203,108,109,0,0,0,203,0,0,0},
                   {206,206,206,206,0,0,0,206,0,0,0},
                   {0,0,0,0,113,0,114,0,110,111,112},
                   {208,208,208,208,0,0,0,208,0,0,0},
                   {0,0,0,0,104,0,105,0,0,115,103},
                   {0,0,0,0,104,0,105,0,0,116,103},
                   {0,0,0,0,104,0,105,0,0,0,117},
                   {0,0,0,0,104,0,105,0,0,0,118},
                   {120,121,0,0,0,119,0,0,0,0,0},
                   {203,203,122,123,0,203,0,0,0,0,0},
                   {206,206,206,206,0,206,0,0,0,0,0},
                   {0,0,0,0,113,0,114,0,124,111,112},
                   {208,208,208,208,0,208,0,0,0,0,0},
                   {201,201,108,109,0,0,0,201,0,0,0},
                   {202,202,108,109,0,0,0,202,0,0,0},
                   {204,204,204,204,0,0,0,204,0,0,0},
                   {205,205,205,205,0,0,0,205,0,0,0},
                   {207,207,207,207,0,0,0,207,0,0,0},
                   {0,0,0,0,113,0,114,0,0,125,112},
                   {0,0,0,0,113,0,114,0,0,126,112},
                   {0,0,0,0,113,0,114,0,0,0,127},
                   {0,0,0,0,113,0,114,0,0,0,128},
                   {120,121,0,0,0,129,0,0,0,0,0},
                   {201,201,122,123,0,201,0,0,0,0,0},
                   {202,202,122,123,0,202,0,0,0,0,0},
                   {204,204,204,204,0,204,0,0,0,0,0},
                   {205,205,205,205,0,205,0,0,0,0,0},
                   {207,207,207,207,0,207,0,0,0,0,0}};

/*
 * 记录几个规约过程
 * 9行表示9个规约式
 * 4列中第1列为规约式左部分,从第二列开始的非-1部分为规约式的右部分
 * */
int reduce[9][4]={{E_,E,-1,-1},
                   {E,E,add,T},
                   {E,E,subtract,T},
                   {E,T,-1,-1},
                   {T,T,multiply,F},
                   {T,T,divide,F},
                   {T,F,-1,-1},
                   {F,left_bracket,E,right_bracket},
                   {F,num,-1,-1}};

string index_to_reduce[] = {
        "E' -> E",
        "E -> E+T",
        "E -> E-T",
        "E -> T",
        "T -> T*F",
        "T -> T/F",
        "T -> F",
        "F -> (E)",
        "F -> num"
};
int input_size = 0;
void showStack(list<int> state_stack, list<int> character_stack, list<int> input_stack, int operation) {
//    cout<<"state"<<endl;
    int num = 0;
    for (int &state : state_stack) {
        if (state<10){
            cout<<" ";
        }
        cout<<state;
        cout<<" ";
        num++;
    }
    for (int i = 0; i < 10-num; ++i) {
        cout<<"   ";
    }
    num = 0;
    for (int &character : character_stack) {
        num+= index_to_char[character].size()+1;
        cout<< index_to_char[character];
        cout<<" ";
    }

    for (int i = 0; i < 20-num+input_size; ++i) {
        cout<<" ";
    }
    num = 0;
    for (int &input : input_stack){
        num+= index_to_char[input].size();
        cout<< index_to_char[input];
    }
    for (int i = 0; i < 5; ++i) {
        cout<<" ";
    }

    if (operation/100==1){
        cout<<"shift "<<operation%100;
    } else if(operation/100 == 2) {
        cout<<"reduce by "<<index_to_reduce[operation%100];
    } else if (operation/100 == 3){
        cout<<"ACC";
    }

//    cout<<operation;
    cout<<endl;
}

int main(){
    // 状态栈
    list<int> state_stack;
    state_stack.push_back(0);

    // 符号栈
    list<int> character_stack;
    character_stack.push_back(1);

    // 将输入字符全部入栈
    list<int> input_stack;
    input_stack.push_front(dollar);
    for (int i = input.size()-1;i>-1;i--) {
        for (int j = 0; j < sizeof(index_to_char)/ sizeof(index_to_char[0]); ++j) {
            if (input[i] == index_to_char[j][0]){
                input_stack.push_front(j);
                break;
            }
        }
    }
    bool success = false;

    while (!input_stack.empty()){
        int current_character = input_stack.front();
        // 记录当前状态栈的栈顶元素,并不取出
        int current_state = state_stack.back();
        // 记录当前输入和状态下在表中查到的对应操作
        int operation = table[current_state][current_character];
        showStack(state_stack, character_stack, input_stack, operation);
        if (operation==300){
            // 此处说明到达ACC,返回操作成功
            success=true;
            break;
        } else if (operation/100==1){
            /*
             * 此处说明需要进行移进操作
             * 从输入串中弹出当前字符
             * 将对应的符号和状态压入栈中
             * */
            input_stack.pop_front();
            input_size += index_to_char[current_character].size();

            character_stack.push_back(current_character);
            state_stack.push_back(operation%100);
        } else if (operation/100==2){
            /*
            * 此处说明需要进行规约操作
            * 首先循环地将当前状态栈和字符栈中的元素弹出
            * */
            int reduce_index = operation %200;
            for (int i = sizeof(reduce[reduce_index])/ sizeof(reduce[reduce_index][0]) -1;i>0;i--) {
                if (reduce[reduce_index][i] == -1) {
                    continue;
                }
                // 判断当前栈顶元素与规约式中对应元素是否相等
                if (character_stack.back() != reduce[reduce_index][i]) {
                    // 此处说明发生错误
                    cout << "There has an error in reducing" << endl;
                    return -1;
                }
                // 从状态和符号栈弹出栈顶元素
                character_stack.pop_back();
                state_stack.pop_back();
            }
            character_stack.push_back(reduce[reduce_index][0]);
            state_stack.push_back(table[state_stack.back()][character_stack.back()]%100);
        } else{
            // 此处说明出错
            cout<<"run a null cell in state table"<<endl;
            return -1;
        }
    }
    return success?0:-1;
}