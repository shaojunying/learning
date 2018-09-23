var app=new Vue({
    el:'#app',
    methods:{
        onClick: function(){
            console.log("点击了");
        },
        onEnter:function() {
            console.log("鼠标进入了");
        },
        onSubmit:function(e) {
            console.log("提交了");
        },
        onEnter:function(e) {
            console.log("按下了enter");
        }
    }
})
