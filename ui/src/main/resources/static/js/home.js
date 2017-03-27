$(function () {
    $('#homeSelectBtn').on('click', function () {
        $("#pageNum").val(1);
        $("#homeForm").submit();
    });
});
function selectHomeForm(pageNo) {
    $("#pageNum").val(pageNo);
    $("#homeForm").submit();
}