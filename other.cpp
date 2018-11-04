////
//// Created by shao on 2018/11/4.
////
//#include <fstream>
//#include <iostream>
//
//using namespace std;
//
//string keyWords[]={"break","case","char","continue","do","default","double",
//                 "else","float","for","if","int","include","long","main",
//                 "return","switch","typedef","void","unsigned","while","iostream"};
//
//
//bool isKeyWord(string data){
//    for (const string &keyWord : keyWords){
//        if (keyWord == data){
//            return true;
//        }
//    }
//    return false;
//}
//
//void show(string stringType,string stringValue){
//    cout<<stringType<<"--------"<<stringValue<<endl;
//}
//
//bool isInMultilineComment = false;
//void lexical_analysis(const string &data){
//    int i = 0;
//
//    while (i<data.length()){
//        if (isInMultilineComment){
////          当前还处于多行注释的内部
//            if (data[i] == '*'&&i+1<data.length()&&data[i+1]=='/'){
//                isInMultilineComment = false;
//            } else {
//                i++;
//                continue;
//            }
//        }
//        if (isalpha(data[i])||data[i] == '_'){
////            i可能是变量名的开头
//            int j = i+1;
//            for (;j<data.length()&&(isalnum(data[j])||data[j]=='_');j++);
////            此时j将指向变量名的结尾
////            截出变量名
//            string subString = data.substr(static_cast<unsigned int>(i), static_cast<unsigned int>(j));
//            if (isKeyWord(subString)){
////                该值是关键词
//                show("keyword",subString);
//            } else{
////                该值是普通变量名
//                show("variableName",subString);
//            }
//            i = j;
//        } else if (data[i]=='/'){
//            if (i+1<data.length()) {
//                if (data[i + 1] == '/') {
//                    cout<<"this is zhushi"<<endl;
////                遇到单行注释
////                直接让i为行内最后一个元素,从而忽略该行
//                    i = data.length();
//                    break;
//                } else if (data[i+1] == '*') {
////                说明遇到多行注释
//                    isInMultilineComment = true;
//                    i+=2;
//                } else if (data[i+1] == '='){
////                    说明遇到了运算符/=
//                    show("operator",data.substr(static_cast<unsigned int>(i), static_cast<unsigned int>(i + 2)));
//                    i+=2;
//                } else{
////                    只有可能遇到了除号
//                    show("operator",data.substr(static_cast<unsigned int>(i), static_cast<unsigned int>(i + 1)));
//                    i++;
//                }
//            }
//        }
//    }
//
//}
//
//int main(){
//    ifstream infile;
//    infile.open("../test.cpp");
//    string data;
//    while (!infile.eof()){
//        infile>>data;
////        对data行进行词法分析
//        lexical_analysis(data);
//    }
//    infile.close();
//
//    return 0;
//}