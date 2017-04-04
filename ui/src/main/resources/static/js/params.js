$(function () {
    $('#paramsSelectBtn').on('click', function () {
        $("#pageNum").val(1);
        $("#paramsForm").submit();
    });

    $('#upDays').on('change', function () {
        $('#downDays').val('');
    });
    $('#downDays').on('change', function () {
        $('#upDays').val('');
    });
});

function pageForm(pageNo) {
    $("#pageNum").val(pageNo);
    $("#paramsForm").submit();
}
