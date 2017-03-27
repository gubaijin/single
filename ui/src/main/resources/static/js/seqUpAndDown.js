$(function () {
    $('#seqUpAndDownSelectBtn').on('click', function () {
        $("#pageNum").val(1);
        $("#seqUpForm").submit();
    });
    $('#type1Btn').on('click', chooseUpBtn);
    $('#type2Btn').on('click', chooseDownBtn);
});
function pageForm(pageNo) {
    $("#pageNum").val(pageNo);
    $("#seqUpForm").submit();
}
function init(){
    // $('#type1Btn').focus();
    chooseUpBtn();
    loadpage();
}

function chooseUpBtn() {
    $('#type1Btn').addClass("btn-danger");
    $('#type2Btn').removeClass("btn-primary")
}
function chooseDownBtn() {
    $('#type1Btn').removeClass("btn-danger")
    $('#type2Btn').addClass("btn-primary");
}