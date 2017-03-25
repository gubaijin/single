$(function () {
    $('#homeSelectBtn').on('click', function () {
        $("#pageNum").val(1);
        $("#homeForm").submit();
    });
});
function selectHomeForm(pageNo) {
    /*$("#list tr:not(:first)").empty();*/
    $("#pageNum").val(pageNo);
    $("#homeForm").submit();
    /*$.ajax({
        url: "/up/list.html",
        type: 'POST',
        dataType: 'json',
        data: {
            num: $('#num').val(),
            pageNo: pageNo
        },
        success: function (data) {
            alert(data);
        },
        error: function (data) {
            alert(data);
        }
    });*/
}