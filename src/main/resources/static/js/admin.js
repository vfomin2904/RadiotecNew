$(document).ready(function () {

    delete_img_activate();

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
                delete_img_activate();
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
            delete_img_activate();
            init_checkbox();
        });
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
            if (action == "book_change") {
                var data = $(data);
                $('#select_book_name').html(data.find('.books_list').html());
            } else {
                $('.booksec_main_params').html(data.find('.booksec_main_params').html());
                delete_img_activate();
                init_checkbox();
            }
        });
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
            delete_img_activate();
            init_checkbox();
        });
    });


    $(".up_arrow").click(function () {
        var currentBox = $(this).parent().parent(".article_box");
        var prevBox = $(this).parent().parent(".article_box").prev(".article_box");
        var current_id = currentBox.find(".element_id").val();
        var prev_id = prevBox.find(".element_id").val();
        var current_num = currentBox.find(".sort_num").val();
        var prev_num = prevBox.find(".sort_num").val();

        currentBox.find(".sort_num").val(prev_num);
        prevBox.find(".sort_num").val(current_num);

        var currentBoxcol = currentBox.find(".col1").html();
        var prevBoxcol = prevBox.find(".col1").html();

        prevBox.find(".col1").html(currentBoxcol);
        currentBox.find(".col1").html(prevBoxcol);

        var type = $(".element_type").val();


        sort(type, current_id, prev_num);
        sort(type, prev_id, current_num);
    });

    $(".down_arrow").click(function () {
        var currentBox = $(this).parent().parent(".article_box");
        var nextBox = $(this).parent().parent(".article_box").next(".article_box");
        var current_id = currentBox.find(".element_id").val();
        var next_id = nextBox.find(".element_id").val();

        var current_num = currentBox.find(".sort_num").val();
        var next_num = nextBox.find(".sort_num").val();

        currentBox.find(".sort_num").val(next_num);
        nextBox.find(".sort_num").val(current_num);

        var currentBoxcol = currentBox.find(".col1").html();
        var nextBoxcol = nextBox.find(".col1").html();


        nextBox.find(".col1").html(currentBoxcol);
        currentBox.find(".col1").html(nextBoxcol);

        var type = $(".element_type").val();

        sort(type, current_id, next_num);
        sort(type, next_id, current_num);
        console.log(current_id);
        console.log(next_num);
        console.log(next_id);
        console.log(current_num);
    });

    init_checkbox();
});

function sendAjaxForm(result_form, ajax_form, url) {

    var form = $('.main_form')[0];

    var data = new FormData(form);
    $('.form_message').html("");

    $.ajax({
        url: url,
        type: "POST", //метод отправки
        dataType: "html", //формат данных
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (response) { //Данные отправлены успешно
            // result = $.parseJSON(response);
            console.log(response);
            var response = $(response);
            if (response.find('.main_params').html() != undefined) {

                $('.main_params').html(response.find('.main_params').html());

                if (response.find('.input_error').html() != undefined) {
                    $(".form_message").html('<div class="ajax_error">Ошибка. Данные заполнены некорректно.</div>');
                } else {
                    $('.form_message').html('<div class="ajax_success">Данные успешно сохранены</div>');
                }
            } else {
                $('.form_message').html('<div class="ajax_success">Данные успешно сохранены</div>');
            }
        },
        error: function (response) { // Данные не отправлены
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


function delete_img_activate() {
    $(".remove_link").on("click", function (e) {
        e.preventDefault(); //отменяем переход по ссылке

        if (confirm("Вы уверены, что хотите удалить объект?")) {
            location.href = $(this).attr('href');
        }
    });
}

function sort(type, id, num) {
    $.ajax({
        url: "/admin/sort/",
        type: "POST", //метод отправки
        data: {
            type: type,
            id: id,
            num: num
        },
        success: function (response) { //Данные отправлены успешно

        },
        error: function (response) { // Данные не отправлены
        }
    });
}


function translit(word){
    var answer = '';
    var converter = {
        'а': 'a',    'б': 'b',    'в': 'v',    'г': 'g',    'д': 'd',
        'е': 'e',    'ё': 'e',    'ж': 'zh',   'з': 'z',    'и': 'i',
        'й': 'y',    'к': 'k',    'л': 'l',    'м': 'm',    'н': 'n',
        'о': 'o',    'п': 'p',    'р': 'r',    'с': 's',    'т': 't',
        'у': 'u',    'ф': 'f',    'х': 'h',    'ц': 'c',    'ч': 'ch',
        'ш': 'sh',   'щ': 'sch',  'ь': '',     'ы': 'y',    'ъ': '',
        'э': 'e',    'ю': 'yu',   'я': 'ya',

        'А': 'A',    'Б': 'B',    'В': 'V',    'Г': 'G',    'Д': 'D',
        'Е': 'E',    'Ё': 'E',    'Ж': 'Zh',   'З': 'Z',    'И': 'I',
        'Й': 'Y',    'К': 'K',    'Л': 'L',    'М': 'M',    'Н': 'N',
        'О': 'O',    'П': 'P',    'Р': 'R',    'С': 'S',    'Т': 'T',
        'У': 'U',    'Ф': 'F',    'Х': 'H',    'Ц': 'C',    'Ч': 'Ch',
        'Ш': 'Sh',   'Щ': 'Sch',  'Ь': '',     'Ы': 'Y',    'Ъ': '',
        'Э': 'E',    'Ю': 'Yu',   'Я': 'Ya'
    };

    for (var i = 0; i < word.length; ++i ) {
        if (converter[word[i]] == undefined){
            answer += word[i];
        } else {
            answer += converter[word[i]];
        }
    }

    return answer;
}