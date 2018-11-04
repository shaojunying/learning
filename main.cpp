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
    bool isKeyWord(const string &data){
                for (const string &keyWord : keyWords){
                    if (keyWord == data){
                        return true;
                    }
                }
                return false;
            }

public:
    void show(const string &stringType, const string &stringValue){
        cout<<stringType<<"\t--------\t"<<stringValue<<endl;
    }

//    处理变量名
    void dealWithVariableName(int &i,const string& data){

//                            i可能是变量名的开头
        int j = i+1;
        for (;j<data.length()&&(isalpha(data[j])||isdigit(data[j])||data[j]=='_');j++);
//                            此时j将指向变量名的结尾
//                            截出变量名
        string subString = data.substr(static_cast<unsigned int>(i),
                                       static_cast<unsigned int>(j-i));
        if (isKeyWord(subString)){
//                                该值是关键词
            show("keyword",subString);
        } else{
//                                该值是普通变量名
            show("variableName",subString);
        }
        i = j;
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
        show("number",data.substr(static_cast<unsigned int>(i), static_cast<unsigned int>(j-i)));
        i = j;
    }

private:
    bool isInMultiLineComment = false;

public:
    void lexical_analysis(string data){
        int i = 0;
        while (i < data.length()) {
            if (isInMultiLineComment){
                if (data[i]=='*'&&i+1<data.length()&&data[i+1]=='/'){
                    i=i+2;
                } else{
                    i++;
                    continue;
                }
            }
            if (isalpha(data[i])||data[i] == '_') {
                dealWithVariableName(i,data);
            } else if (isdigit(data[i])) {
                dealWithNumber(i,data);
            } else {
                switch (data[i]) {
                    case '~':
                    case '.':
//                    这个组合只判断自身
                        show("operator",data.substr(static_cast<unsigned int>(i),1));
                        i++;
                        break;
                    case '*':
                    case '%':
                    case '=':
                    case '!':
                    case '^':
//                            这个组合只判断自身,自身加=
                        if (i+1<data.length()&&data[i+1] == '=') {
                            show("operator",data.substr(static_cast<unsigned int>(i),2));
                            i+=2;
                        } else{
                            show("operator",data.substr(static_cast<unsigned int>(i),1));
                            i++;
                        }
                        break;
                    case '+':
                    case '-':
                    case '&':
                    case '|':
//                                这个组合判断自身,自身加=,自身加自身
                        if (i+1<data.length()&&(data[i+1] == '='||data[i+1]==data[i])) {
                            show("operator",data.substr(static_cast<unsigned int>(i),2));
                            i+=2;
                        } else{
                            show("operator",data.substr(static_cast<unsigned int>(i),1));
                            i++;
                        }
                        break;
                    case '<':
                    case '>':
                        if(i+1<data.length()&&data[i+1]==data[i]&&i+2<data.length()&&data[i+2]=='='){
                            show("operator",data.substr(static_cast<unsigned int>(i),3));
                            i+=3;
                        } else if (i+1<data.length()&&(data[i+1] == '='||data[i+1]==data[i])) {
                            show("operator",data.substr(static_cast<unsigned int>(i),2));
                            i+=2;
                        } else{
                            show("operator",data.substr(static_cast<unsigned int>(i),1));
                            i++;
                        }
                        break;
                    case '/':
                        /*此处遇到了/符号,可能要开始处理注释*/
                        if (i+1<data.length()) {
                            switch (data[i+1]) {
                                case '*':
                                    /*多行注释的开始*/
                                    isInMultiLineComment = true;
                                    break;
                                case '/':
                                    /*单行注释的开始*/
                                    i = data.length();
                                    break;
                                case '=':
                                    /* /= */
                                    show("operator",data.substr(static_cast<unsigned int>(i),2));
                                    i+=2;
                                    break;
                                default:
                                    show("operator",data.substr(static_cast<unsigned int>(i),1));
                                    i++;
                                    break;
                            }
                        } else{
                            show("operator",data.substr(static_cast<unsigned int>(i),1));
                            i++;
                        }
                        break;

                    default:
                        if (!isspace(data[i])) {
                            show("otherSymbol", data.substr(static_cast<unsigned int>(i), 1));
                        }
                        i++;
                        break;
                }
            }
        }
    }
};



int main() {
    ComplicationPrinciple complicationPrinciple;
    ifstream infile;
    infile.open("../in2.txt");
    string data;
    while (!infile.eof()){
        getline(infile,data);
        complicationPrinciple.lexical_analysis(data);
    }
    infile.close();
    return 0;
}