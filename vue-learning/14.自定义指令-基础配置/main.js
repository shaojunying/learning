Vue.directive('pin',function(el,binding) {
    var pinned = binding.value;
    var position = binding.modifiers;
    var warnning =  binding.arg;
    console.log(position);
    if (pinned) {
        el.style.position="fixed";

        for (var key in position){
            if (position[key]) {
                el.style[key] = "10px";
            }
        }
        if (warnning === "true") {
            el.style.background = "yellow";
        }
        //
        // el.style.top = '10px';
        // el.style.left = '10px';
    }else{
        el.style.position="static";
    }
})
new Vue({
    el:"#app",
    data:{
        card1:{
            pinned:false
        },
        card2:{
            pinned:false
        },
    }
})
