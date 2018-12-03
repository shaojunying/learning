//#include <iostream>
//#include <fstream>
//#include <cstring>
//#include <cctype>
//#include <fstream>
//#include <assert.h>
//
//using namespace std;
//
//const string keyWords[]={"alignas","alignof","and","and_eq","asm","atomic_cancel","atomic_commit",
//                         "atomic_noexcept","auto","bitand","bitor","bool","break","case","catch",
//                         "char","char16_t","char32_t","class","compl","concept","const","constexpr",
//                         "const_cast","continue","co_await","co_return","co_yield","decltype","default",
//                         "delete","do","double","dynamic_cast","else","enum","explicit","export","extern",
//                         "false","float","for","friend","goto","if","import","inline","int","long","module",
//                         "mutable","namespace","new","noexcept","not","not_eq","nullptr","operator","or",
//                         "or_eq","private","protected","public","reflexpr","register","reinterpret_cast",
//                         "requires","return","short","signed","sizeof","static","static_assert","static_cast",
//                         "struct","switch","synchronized","template","this","thread_local","throw","true",
//                         "try","typedef","typeid","typename","union","unsigned","using","virtual","void",
//                         "volatile","wchar_t","while","xor","xor_eq"};
//
//
//class ComplicationPrinciple{
//    int errorCount = 0;
//
//    enum type{
//        operatorVariable,
//        otherSymbol,
//        number,
//        variableName,
//        keyWord
//    };
//    bool isKeyWord(const string &data) {
//        for (const string &keyWord : keyWords) {
//            if (keyWord == data) {
//                return true;
//            }
//        }
//        return false;
//    }
//public:
////    显示错误的函数
//    void showError(int i, int j,string line) {
//        errorCount++;
//        cout<<endl;
//        cout<<"error["<<errorCount<<"]----------------------------There is a error in row "<<i<<" col "<<j<<endl;
//        cout<<"error line is:   ";
//        cout<<line<<endl;
//        for (int k = 0; k < j+17; ++k) {
//            cout<<" ";
//        }
//        cout<<"^"<<endl;
//
//    }
////    显示每个元素对应的信息
//    void show(const int &stringType, const string &stringValue){
//        wordCountEveryKind[stringType]++;
//        switch (stringType){
//            case operatorVariable:
//                cout<<"operatorVariable  ----  ";
//                break;
//            case otherSymbol:
//                cout<<"otherSymbol  ---------  ";
//                break;
//            case number:
//                cout<<"number  --------------  ";
//                break;
//            case variableName:
//                cout<<"variableName  --------  ";
//                break;
//            case keyWord:
//                cout<<"keyWord  -------------  ";
//                break;
//            default:
//                break;
//        }
//        cout<<stringValue<<endl;
//    }
//
////    处理变量名
//    void dealWithVariableName(int &i,const string& data){
//
////                            i可能是变量名的开头
//        int j = i+1;
//        for (;j<data.length()&&(isalpha(data[j])||isdigit(data[j])||data[j]=='_');j++);
////                            此时j将指向变量名的结尾
////                            截出变量名
//        string subString = data.substr(static_cast<unsigned int>(i),
//                                       static_cast<unsigned int>(j-i));
//        if (isKeyWord(subString)){
////                                该值是关键词
//            show(keyWord,subString);
//        } else{
////                                该值是普通变量名
//            show(variableName,subString);
//        }
//        i = j;
//    }
////    处理数字
//    void dealWithNumber(int &i,const string& data){
//        /*首字母为数字*/
//        int j = i+1;
//        int digitCount = 0;
////                            找出所有的数字
//        for (;j<data.length()&&isdigit(data[j]);j++);
////                            判断接下来是不是小数点后面接小数
//        if (data[j]=='.'){
//            j++;
//            if (!(j<data.length()&&isdigit(data[j]))){
//                showError(lineCount, j,data);
//            }
//            j++;
////                                再次找出小数点后面的所有小数
//            for (;j<data.length()&&isdigit(data[j]);j++,digitCount++);
//        }
////                            判断后面是不是指数
//        if (data[j]=='E'){
//            j++;
//            if (!(j<data.length()&&(isdigit(data[j])||data[j]=='-'||data[j]=='+'))){
//                showError(lineCount,j,data);
//
//            }
//            j++;
////                                再次找出小数点后面的所有小数
//            for (;j<data.length()&&isdigit(data[j]);j++);
//        }
//        if (data[j]=='.'||isalpha(data[j])){
//            showError(lineCount,j,data);
//        }
//        show(number,data.substr(static_cast<unsigned int>(i), static_cast<unsigned int>(j-i)));
//        i = j;
//    }
////    显示一个最终结果的信息
//    void showOtherResultInfo(){
//        cout<<"-------------------------------------------------------------------"<<endl;
//        cout<<"-------------------------------------------------------------------"<<endl;
//        cout<<"The total number of words is "<<wordCount<<endl;
//        cout<<"The total number of lines is "<<lineCount<<endl;
//        cout<<"The total number of every kind word is:"<<endl;
//
//        cout<<"operatorVariable "<<wordCountEveryKind[operatorVariable]<<endl;
//        cout<<"otherSymbol      "<<wordCountEveryKind[otherSymbol]<<endl;
//        cout<<"number           "<<wordCountEveryKind[number]<<endl;
//        cout<<"variableName     "<<wordCountEveryKind[variableName]<<endl;
//        cout<<"keyWord          "<<wordCountEveryKind[keyWord]<<endl;
//    }
//    /*处理斜线/的情况*/
//    void dealSlash(int& i, const string& data){
//        if (i+1<data.length()) {
//            switch (data[i+1]) {
//                case '*':
//                    /*多行注释的开始*/
//                    isInMultiLineComment = true;
//                    break;
//                case '/':
//                    /*单行注释的开始*/
//                    i = data.length();
//                    break;
//                case '=':
//                    /* /= */
//                    show(operatorVariable,data.substr(static_cast<unsigned int>(i),2));
//                    i+=2;
//                    break;
//                default:
//                    show(operatorVariable,data.substr(static_cast<unsigned int>(i),1));
//                    i++;
//                    break;
//            }
//        } else{
//            show(operatorVariable,data.substr(static_cast<unsigned int>(i),1));
//            i++;
//        }
//    }
//
//private:
//    bool isInMultiLineComment = false;
////    总词数
//    int wordCount = 0;
////    总行数
//    int lineCount = 0;
////    各类单词的个数
//    int wordCountEveryKind[5]{0,0,0,0,0};
//
//
//public:
//    void lexical_analysis(string data){
//        int i = 0;
//        wordCount+=data.length();
//        lineCount++;
//        while (i < data.length()) {
//            if (isInMultiLineComment){
//                if (data[i]=='*'&&i+1<data.length()&&data[i+1]=='/'){
//                    i=i+2;
//                    isInMultiLineComment= false;
//                    continue;
//                } else{
//                    i++;
//                    continue;
//                }
//            }
//            if (isalpha(data[i])||data[i] == '_') {
////                处理变量名的情况
//                dealWithVariableName(i,data);
//            } else if (isdigit(data[i])) {
////                处理数字的情况
//                dealWithNumber(i,data);
//            } else {
//                switch (data[i]) {
//                    case '~':
//                    case '.':
////                    这个组合只判断自身
//                        show(operatorVariable,data.substr(static_cast<unsigned int>(i),1));
//                        i++;
//                        break;
//                    case '*':
//                    case '%':
//                    case '=':
//                    case '!':
//                    case '^':
////                            这个组合只判断自身,自身加=
//                        if (i+1<data.length()&&data[i+1] == '=') {
//                            show(operatorVariable,data.substr(static_cast<unsigned int>(i),2));
//                            i+=2;
//                        } else{
//                            show(operatorVariable,data.substr(static_cast<unsigned int>(i),1));
//                            i++;
//                        }
//                        break;
//                    case '+':
//                    case '-':
//                    case '&':
//                    case '|':
////                                这个组合判断自身,自身加=,自身加自身
//                        if (i+1<data.length()&&(data[i+1] == '='||data[i+1]==data[i])) {
//                            show(operatorVariable,data.substr(static_cast<unsigned int>(i),2));
//                            i+=2;
//                        } else{
//                            show(operatorVariable,data.substr(static_cast<unsigned int>(i),1));
//                            i++;
//                        }
//                        break;
//                    case '<':
//                    case '>':
//                        if(i+1<data.length()&&data[i+1]==data[i]&&i+2<data.length()&&data[i+2]=='='){
//                            show(operatorVariable,data.substr(static_cast<unsigned int>(i),3));
//                            i+=3;
//                        } else if (i+1<data.length()&&(data[i+1] == '='||data[i+1]==data[i])) {
//                            show(operatorVariable,data.substr(static_cast<unsigned int>(i),2));
//                            i+=2;
//                        } else{
//                            show(operatorVariable,data.substr(static_cast<unsigned int>(i),1));
//                            i++;
//                        }
//                        break;
//                    case '/':
//                        /*此处遇到了/符号,可能要开始处理注释*/
//                        dealSlash(i,data);
//                        break;
//                    default:
//                        if (!isspace(data[i])) {
//                            show(otherSymbol, data.substr(static_cast<unsigned int>(i), 1));
//                        }
//                        i++;
//                        break;
//                }
//            }
//        }
//    }
//};
//
//
//
//int main() {
//    ComplicationPrinciple complicationPrinciple;
//    ifstream infile;
//    infile.open("../in2.txt");
//    string data;
//    while (!infile.eof()){
//        getline(infile,data);
//        complicationPrinciple.lexical_analysis(data);
//    }
//    infile.close();
//    complicationPrinciple.showOtherResultInfo();
//    return 0;
//}