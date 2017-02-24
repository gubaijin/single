$(function () {
    $('#select').on('click', function () {
        $("#pageNum").val("");
        $("#seqUpForm").submit();
    });
});
function selectSeqUpForm(pageNo) {
    /*$("#list tr:not(:first)").empty();*/
    $("#pageNum").val(pageNo);
    $("#seqUpForm").submit();
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