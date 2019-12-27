//在定义的瞬间直接触发了
//域从window变成了本地的
//最前面的;是为了防止之前引入的文件最后没有;
;(function () {
    'use strict';

    let $form_add_task = $('.add-task'),
        $delete_task,
        task_list = {}
    ;


    init();


    $form_add_task.on('submit',on_add_task_form_callback);

    function on_add_task_form_callback(e) {
        let new_task = {};
        /*禁用默认行为*/
        e.preventDefault();
        /*获取新Task的值*/
        let $input = $(this).find('input[name=content]');
        new_task.content = $input.val();
        /*如果新Task的值为空,直接返回,否则继续执行*/
        if (!new_task.content) return;
        /*存入新Task*/
        if (add_task(new_task)) {
            $input.val('');
        }
    }

    function listen_task_delete() {
        $delete_task.on('click',function () {
            let $this = $(this);
            let $item = $this.parent().parent();
            let index = $item.data('index');
            let temp = confirm("确定删除?");
            if (temp)
                delete_task(index);
        })
    }


    function delete_task(index) {
        //没有index或index对应的任务不存在
        if (index === undefined|| !task_list[index]) return;

        delete task_list[index];
        refresh_task_list();
    }

    /*
    * 刷新LocalStorage并渲染模板
    * */
    function refresh_task_list() {
        store.set('task_list',task_list);
        render_task_list();
    }

    function render_task_list() {
        let $task_list = $('.task-list');
        $task_list.html('');
        for (let i = 0; i < task_list.length; i++) {
            let $task = render_task_item(task_list[i], i);
            $task_list.append($task);
        }
        $delete_task = $('.action.delete')
        listen_task_delete();
    }
    
    function render_task_item(data,index) {
        if (!data || !index){
            return;
        }
        let list_item_tpl =
            '<div class="task-item" data-index="'+index+'"><!--任务开始-->' +
            '<span><input type="checkbox"></span>' +
            '<span class="task-content">'+data.content+'</span>' +
            '<span class="fl">'+
            '<span class="action delete"> 删除</span>' +
            '<span class="action"> 详情</span>' +
            '</span>'+
            '</div>';
        return $(list_item_tpl)
    }

    function add_task(new_task) {
        task_list.push(new_task);
        /*更新LocalStorage*/
        refresh_task_list();
        return true;
    }

    function init() {
        task_list = store.get('task_list')||[];
        if (task_list.length) render_task_list();
    }

})();