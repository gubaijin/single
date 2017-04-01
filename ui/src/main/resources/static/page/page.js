function exeData(num, type) {
    loadpage();
}
function loadpage() {
    var myPageCount = parseInt($("#PageCount").val());
    if(myPageCount < 1){
        myPageCount = 1;
    }
    var myPageSize = parseInt($("#PageSize").val());
    var pageNo = $('#pageNo').val();
    var totalPage = $('#totalPage').val();
    var countindex = myPageCount % myPageSize > 0 ? (myPageCount / myPageSize) + 1 : (myPageCount / myPageSize);
    $("#countindex").val(parseInt(countindex));

    $.jqPaginator('#pagination', {
        totalPages: parseInt($("#totalPage").val()),
        visiblePages: parseInt($("#visiblePages").val()),
        currentPage: parseInt(pageNo),
        first: firstBtn(pageNo),
        prev: prevBtn(pageNo),
        next: nextBtn(pageNo, totalPage),
        last: lastBtn(pageNo, totalPage),
        page: '<li class="page"><a onclick="pageForm({{page}})">{{page}}</a></li>',
        onPageChange: function (num, type) {
            if (type == "change") {
                exeData(num, type);
            }
        }
    });
}
function firstBtn(pageNo){
    if(parseInt(pageNo) == 1){
        return '<li class="first"><a>首页</a></li>';
    }else{
        return '<li class="first"><a onclick="pageForm(1)">首页</a></li>';
    }
}
function prevBtn(pageNo){
    if(parseInt(pageNo) == 1){
        return '<li class="prev"><a><i class="arrow arrow2"></i>上一页</a></li>';
    }else{
        return '<li class="prev"><a onclick="pageForm(parseInt('+ pageNo + '- 1))"><i class="arrow arrow2"></i>上一页</a></li>';
    }
}
function nextBtn(pageNo, totalPage){
    if(parseInt(pageNo) == parseInt(totalPage)){
        return '<li class="next"><a>下一页<i class="arrow arrow3"></i></a></li>';
    }else{
        return '<li class="next"><a onclick="pageForm(parseInt('+ pageNo + '+ 1))">下一页<i class="arrow arrow3"></i></a></li>';
    }
}
function lastBtn(pageNo, totalPage){
    if(parseInt(pageNo) == parseInt(totalPage)){
        return '<li class="last"><a>末页</a></li>';
    }else{
        return '<li class="last"><a onclick="pageForm(parseInt('+ totalPage + '))">末页</a></li>';
    }
}