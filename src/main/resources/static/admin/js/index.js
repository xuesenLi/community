

/**/

//点击当前的a添加on类，并给其他a清除类名
$("#tagAddClass li a").click(function(){
    $(this).addClass("active").parent().siblings().find("a").removeAttr("class");
})
