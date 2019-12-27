var base = {
    methods:{
        show:function() {
            this.visible = true;
        },
        hide: function() {
            this.visible = false;
        },
        toggle:function() {
            this.visible = !this.visible
        }
    },
    data:function() {
        return {
            visible:false,
        }
    }
}
Vue.component("pip",{
    template:`
    <div>
    <span @mouseenter="show" @mouseleave ="hide">bys</span>
    <div v-if="visible">
        表严肃
    </div>
    </div>
    `,
    mixins:[base],
    // 会覆盖上面的东西
    data:function() {
        return {
            visible:false,
        }
    }
}),
Vue.component("popup",{
    template:`
    <div>
        <button @click="toggle">popup</button>
        <div v-if="visible">
            <span @click="hide">X</span>
            <h4>title</h4>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </div>
    </div>
    `,
    mixins:[base]
})

new Vue({
    el:"#app",
})
