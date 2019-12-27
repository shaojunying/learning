// 全局变量
// Vue.component('alert',{
//     template: '<button @click="on_click">弹弹弹</button>',
//     methods:{
//         on_click:function() {
//             alert("Yo");
//         }
//     }
// });

var alert = {
    template: '<button @click="on_click">弹弹弹</button>',
    methods:{
        on_click:function() {
            alert("Yo");
        }
    }
};

new Vue({
    el: '#app1',
    components: {
        'alert':alert,
    }
});
new Vue({
    el: '#app2',
})
