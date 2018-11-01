#include <iostream>
using namespace std;
int graph[5][5]={
        0,1,1,0,0,
        1,0,1,1,1,
        1,1,0,0,1,
        0,1,0,0,1,
        0,1,1,1,0
};
int color[5]={0,0,0,0,0};

bool check(int dot){
    for(int i=0;i<5;i++){
        if(graph[dot][i]==1){
            if(color[i]!=color[dot]){
                continue;
            }
            else return false;
        }
    }return true;

}
int main(){
    int k=0;
    while(color[k]<=3 && k<=5) {
        cout << "为第" << k << "个节点上颜色" << color[k] << endl;
        if (check(k) && color[k]!=0)
            k++;
            continue;
        } else color[k]++;
        if (color[k] > 3) {
            color[k] = 0;
            k--;
        }
    }
    for(int i=0;i<=4;i++){
        cout<<color[i]<<endl;
    }
}