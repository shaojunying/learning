var app=new Vue({
    el:'#app',
    data:{
        math:90,
        physics:90,
        english:10
    },
    computed:{
        sum:function() {
            return this.math+this.physics+this.english;
        },
        average:function() {
            return Math.round(this.sum/3);
        }
    }
})
