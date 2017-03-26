$(function () {
    $('#init1BtnConfirm').on('click', function () {
        $.ajax({
            url: "/init1",
            type: 'POST',
            data: {
                pwd: $('#password1').val()
            },
            success: function (data) {
                success();
                $('#init1BtnCancel').click();
                $('#password1').val("");
            },
            error: function (data) {
                error();
                $('#init1BtnCancel').click();
            }
        });
    });
    $('#init2BtnConfirm').on('click', function () {
        $.ajax({
            url: "/init2",
            type: 'POST',
            data: {
                pwd: $('#password2').val()
            },
            success: function (data) {
                success();
                $('#init2BtnCancel').click();
                $('#password2').val("");
            },
            error: function (data) {
                error();
                $('#init2BtnCancel').click();
            }
        });
    });
    $('#init3BtnConfirm').on('click', function () {
        $.ajax({
            url: "/init3",
            type: 'POST',
            data: {
                pwd: $('#password3').val()
            },
            success: function (data) {
                success();
                $('#init3BtnCancel').click();
                $('#password3').val("");
            },
            error: function (data) {
                error();
                $('#init3BtnCancel').click();
            }
        });
    });
    $('#init4BtnConfirm').on('click', function () {
        $.ajax({
            url: "/init4",
            type: 'POST',
            data: {
                pwd: $('#password4').val()
            },
            success: function (data) {
                success();
                $('#init4BtnCancel').click();
                $('#password4').val("");
            },
            error: function (data) {
                error();
                $('#init4BtnCancel').click();
            }
        });
    });

    $('#base1BtnConfirm').on('click', function () {
        $.ajax({
            url: "/base1",
            type: 'POST',
            success: function (data) {
                success();
                $('#base1BtnCancel').click();
            },
            error: function (data) {
                error();
                $('#base1BtnCancel').click();
            }
        });
    });

    $('#base2BtnConfirm').on('click', function () {
        $.ajax({
            url: "/base2",
            type: 'POST',
            success: function (data) {
                success();
                $('#base2BtnCancel').click();
            },
            error: function (data) {
                error();
                $('#base2BtnCancel').click();
            }
        });
    });

});

function success() {
    setTimeout(function () {
        toastr.options = {
            closeButton: true,
            progressBar: true,
            showMethod: 'slideDown',
            timeOut: 1000
        };
        toastr.success('操作成功', '任务已经被成功执行');
    }, 500);
}

function error() {
    setTimeout(function () {
        toastr.options = {
            closeButton: true,
            progressBar: true,
            showMethod: 'slideDown',
            timeOut: 1000
        };
        toastr.error('操作失败', '任务执行失败，请检查');
    }, 500);
}