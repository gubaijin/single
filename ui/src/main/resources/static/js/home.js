$(function () {
    $('#homeSelectBtn').on('click', function () {
        $("#pageNum").val(1);
        $("#homeForm").submit();
    });
});
function pageForm(pageNo) {
    $("#pageNum").val(pageNo);
    $("#homeForm").submit();
}