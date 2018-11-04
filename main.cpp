#include <iostream>
#include <fstream>
#include <cstring>
#include <cctype>
#include <fstream>
using namespace std;

string keyWords[]={"break","case","char","continue","do","default","double",
                   "else","float","for","if","int","include","long","main",
                   "return","switch","typedef","void","unsigned","while","iostream"};
class ComplicationPrinciple{

    int state = 0;  // 代表当前的状态
    ofstream saveFile;


    bool isKeyWord(const string &data){
                for (const string &keyWord : keyWords){
                    if (keyWord == data){
                        return true;
                    }
                }
                return false;
            }

//    单目运算符
    const char Monocular_Operator[20]={'+','-','*','/','!','%','~','&','|','^','='};   //单目运算符
//    双目运算符
    const char Binocular_Operator[20][5]={"++","--","&&","||","<=","!=","==",">=","+=","-=","*=","/="}; //双目运算符
//    界符
    const char Delimiter[20]={',','(',')','{','}',';','<','>','#'}; //界符


public:
    void show(string& curr_string,ofstream& saveFile,int& state, bool isKey = true){
        if (curr_string.length()!=0) {
            saveFile << curr_string << endl;
            curr_string.clear();
        }
        /*输出之后状态都置为0*/
        state = 0;
    }

    void show(const string &stringType, const string &stringValue){
        cout<<stringType<<"\t--------\t"<<stringValue<<endl;
    }

//    处理变量名
    void dealWithVariableName(int &i,const string& data){

//                            i可能是变量名的开头
        int j = i+1;
        for (;j<data.length()&&(isalnum(data[j])||data[j]=='_');j++);
//                            此时j将指向变量名的结尾
//                            截出变量名
        string subString = data.substr(static_cast<unsigned int>(i),
                                       static_cast<unsigned int>(j));
        if (isKeyWord(subString)){
//                                该值是关键词
            show("keyword",subString);
        } else{
//                                该值是普通变量名
            show("variableName",subString);
        }
        i = j;
    }

    void dealWithCompareOperator(int &i,const string &data){
        /*判断><=之后的情况*/
        if (i+1<data.length()&&data[i+1] == '=') {
            show("operator",data.substr(static_cast<unsigned int>(i),
                                        static_cast<unsigned int>(i + 2)));
            i+=2;
        } else{
            show("operator",data.substr(static_cast<unsigned int>(i),
                                        static_cast<unsigned int>(i + 1)));
            i++;
        }
    }

    void dealWithBinocularOperator(int &i,const string data){
        /*判断><=:之后的情况*/
        if (i+1<data.length()&&data[i+1] == '=') {
            show("operator",data.substr(static_cast<unsigned int>(i),
                                        static_cast<unsigned int>(i + 2)));
            i+=2;
        } else{
            show("operator",data.substr(static_cast<unsigned int>(i),
                                        static_cast<unsigned int>(i + 1)));
            i++;
        }
    }

    void dealWithNumber(int &i,const string& data){
        /*首字母为数字*/
        int j = i+1;
//                            找出所有的数字
        for (;j<data.length()&&isdigit(data[j]);j++);
//                            判断接下来是不是小数点后面接小数
        if (data[j]=='.'&&j+1<data.length()&&isdigit(data[j+1])){
            j++;
//                                再次找出小数点后面的所有小数
            for (;j<data.length()&&isdigit(data[j]);j++);
        }
//                            判断后面是不是指数
        if (data[j]=='E'&&j+1<data.length()&&(isdigit(data[j+1])||data[j+1]=='-'||data[j+1]=='+')){
            j+=2;
//                                再次找出小数点后面的所有小数
            for (;j<data.length()&&isdigit(data[j]);j++);
        }
        show("number",data.substr(static_cast<unsigned int>(i), static_cast<unsigned int>(j)));
        i = j;
    }

public:
    void lexical_analysis(string data){
        int i = 0;
        while (i < data.length()) {
                switch (state) {
                    case 0:
                        if (isalpha(data[i])||data[i] == '_') {
                            dealWithVariableName(i,data);
                        } else if (isdigit(data[i])) {
                            dealWithNumber(i,data);
                        } else {
                            switch (data[i]) {
                                case '<':
                                case '>':
                                case '=':
                                case '!':
                                    dealWithCompareOperator(i,data);
                                    break;
                                case '+':
                                case '-':
                                    dealWithBinocularOperator(i,data);
                                    break;
                                case '*':
//                                    curr_string.push_back(a);
//                                    state = 15;
//                                    break;
                                case '/':
                                    /*此处遇到了/符号,可能要开始处理注释*/
                                    if (i+1<data.length()) {
                                        switch (data[i+1]) {
                                            case '*':
                                                state = 16;
                                                break;
                                            case '/':
                                                /*单行注释的开始*/
                                                i = data.length();
                                                break;
                                            case '=':
                                                /* /= */
                                                show("operator",data.substr(static_cast<unsigned int>(i),
                                                                               static_cast<unsigned int>(i + 2)));
                                                i+=2;
                                                break;
                                            default:
                                                show("operator",data.substr(static_cast<unsigned int>(i),
                                                                               static_cast<unsigned int>(i + 1)));
                                                i++;
                                                break;
                                        }
                                    } else{
                                        show("operator",data.substr(static_cast<unsigned int>(i),
                                                                       static_cast<unsigned int>(i + 1)));
                                        i++;
                                    }
                                    break;
                                case ',':
                                case ';':
                                    /*此处处理逗号和分号*/
                                    break;
                                default:
                                    /*此处说明输入的内容无效,不用做任何处理*/
                                    break;
                            }
                        }
                        break;
//                    case 15:
//                        if (isdigit(a)){
//                            curr_string.push_back(a);
//                            state = 2;
//                        } else{
//                            switch (a){
//                                case '=':
//                                    curr_string.push_back(a);
//                                    show(curr_string,saveFile,state);
//                                    break;
//                                default:
//                                    show(curr_string,saveFile,state);
//                                    i--;
//                                    break;
//                            }
//                        }
//                        break;
//                    case 19:
//                        /*寻找多行注释的结尾*/
//                        switch (a){
//                            case '*':
//                                state = 19;
//                                break;
//                            case  '/':
//                                state = 0;
//                                break;
//                            default:break;
//                        }
//                        break;
                    default:
                        break;
                }
            }
    }
};



int main() {
    ComplicationPrinciple complicationPrinciple;
    ifstream infile;
    infile.open("../test.cpp");
    string data;
    while (!infile.eof()){
        data.clear();
        infile>>data;
        complicationPrinciple.lexical_analysis(data);
    }
    infile.close();
    return 0;
}