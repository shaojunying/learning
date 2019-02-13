//在定义的瞬间直接触发了
//域从window变成了本地的
//最前面的;是为了防止之前引入的文件最后没有;
;(function () {
    'use strict';
    console.log($)
})();