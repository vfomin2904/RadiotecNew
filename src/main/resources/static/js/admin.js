$(document).ready(function () {
    $('#select_journal_name').on('change', function (e) {
        // var optionSelected = $("option:selected", this);
        $(this).nextAll("select").prop('selectedIndex', 0);
        $(this).nextAll("select").find(".custom_option").remove();

        var valueSelected = this.value;
        $.ajax({
            url: "/admin/handler",
            method: "POST",
            data: {
                journal_id: valueSelected
            },
            dataType: "html"
        }).success(function (data) {
            var data = $(data);
            // $('#select_number_year').html(data);
            $('#select_number_year').html(data.find('.years_list').html());
        });
    });

    $('#select_number_year').on('change', function (e) {
        $(this).nextAll("select").prop('selectedIndex', 0);
        $(this).nextAll("select").find(".custom_option").remove();
        var valueYear = this.value;
        var valueJournal = $('#select_journal_name').val();
        $.ajax({
            url: "/admin/handler",
            method: "POST",
            data: {
                journal_id: valueJournal,
                number_year: valueYear
            }
        }).success(function (data) {
            var data = $(data);
            // $('#select_number_name').html(data);
            $('#select_number_name').html(data.find('.numbers_list').html());

        });
    });

    $('#select_number_name').on('change', function (e) {
        $(this).nextAll("select").prop('selectedIndex', 0);
        $(this).nextAll("select").find(".custom_option").remove();
        var valueNumber = this.value;
        $.ajax({
            url: "/admin/handler",
            method: "POST",
            data: {
                number_id: valueNumber
            }
        }).success(function (data) {
            var data = $(data);

            $('#select_section_name').html(data.find('.section_list').html());
        });
    });

    $('#select_section_name').on('change', function (e) {
        $(this).nextAll("select").prop('selectedIndex', 0);
        $(this).nextAll("select").find(".custom_option").remove();
        var valueSection = this.value;
        var action = $(".main_form").attr("action");
        action = action.substring(action.lastIndexOf("/") + 1);

        if (action == "article_add") {
            return;
        }
        $.ajax({
            url: "/admin/handler",
            method: "POST",
            data: {
                section_id: valueSection,
                action: action
            }
        }).success(function (data) {
            var data = $(data);
            if (action == "section_change") {
                $('.main_params').html(data.find('.section_main_params').html());
                init_checkbox();
            } else if (action == "article_change") {
                var data = $(data);
                $('#select_article_name').html(data.find('.articles_list').html());
            }
        });
    });

    $('#select_article_name').on('change', function (e) {
        $(this).nextAll("select").prop('selectedIndex', 0);
        $(this).nextAll("select").find(".custom_option").remove();

        var valueArticle = this.value;
        $.ajax({
            url: "/admin/handler",
            method: "POST",
            data: {
                art_id: valueArticle
            }
        }).success(function (data) {
            var data = $(data);
            $('.main_params').html(data.find('.article_main_params').html());
            init_checkbox();});
    });

    $('#book_select_section').on('change', function (e) {
        $(this).nextAll("select").prop('selectedIndex', 0);
        $(this).nextAll("select").find(".custom_option").remove();
        var action = $(".main_form").attr("action");
        action = action.substring(action.lastIndexOf("/") + 1);
        var valueBooksec = this.value;
        $.ajax({
            url: "/admin/handler",
            method: "POST",
            data: {
                booksec_id: valueBooksec,
                action: action
            }
        }).success(function (data) {
            var data = $(data);
            if(action == "book_change"){
                var data = $(data);
                $('#select_book_name').html(data.find('.books_list').html());
            }
            else{
                $('.booksec_main_params').html(data.find('.booksec_main_params').html());
                init_checkbox();
            }});
    });

    $('#select_book_name').on('change', function (e) {
        $(this).nextAll("select").prop('selectedIndex', 0);
        $(this).nextAll("select").find(".custom_option").remove();

        var valueBook = this.value;
        $.ajax({
            url: "/admin/handler",
            method: "POST",
            data: {
                book_id: valueBook
            }
        }).success(function (data) {
            var data = $(data);
            $('#books_options').html(data.find('.book_main_params').html());
            init_checkbox();});
    });



    init_checkbox();
});

function sendAjaxForm(result_form, ajax_form, url) {
    $.ajax({
        url:     url,
        type:     "POST", //метод отправки
        dataType: "html", //формат данных
        data: $("."+ajax_form).serialize(),
        processData: false,
        contentType: false,
        cache: false,
        success: function(response) { //Данные отправлены успешно
            // result = $.parseJSON(response);
            console.log(response);
            var response = $(response);
            if(response.find('.main_params').html() != undefined){
                $('.main_params').html(response.find('.main_params').html());
            }
            else{
                $('form').append('<div class="ajax_success">Данные успешно сохранены</div>');
            }
        },
        error: function(response) { // Данные не отправлены
            $(".form_message").html('<div class="ajax_error">Ошибка. Данные не отправлены.</div>');
        }
    });
}


function init_checkbox() {
    $(".checkbox_value").each(function () {
        if ($(this).val() == 1) {
            $(this).prev().prev(".custom-checkbox").prop('checked', true);
        } else {
            $(this).prev().prev(".custom-checkbox").prop('checked', false);
        }
    });
    $('.active').click(function (e) {
        if ($(this).is(':checked')) {
            console.log(1);
            $(this).next().next(".checkbox_value").val("1");
        } else {
            $(this).next().next(".checkbox_value").val("0");
        }
    });
}
